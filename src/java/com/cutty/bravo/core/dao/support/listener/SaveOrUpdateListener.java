package com.cutty.bravo.core.dao.support.listener;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.event.def.DefaultSaveOrUpdateEventListener;
import org.hibernate.proxy.HibernateProxy;

import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.domain.annotation.AuditEntity;
import com.cutty.bravo.core.domain.annotation.UniquePropertyEntity;
import com.cutty.bravo.core.security.domain.AuditHistory;
import com.cutty.bravo.core.security.domain.AuditHistoryDetail;
import com.cutty.bravo.core.security.domain.User;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.web.handler.RequestHandler;

/**
 * 保存或更新操作的监听器，目的要于要在保存或更新前判断权限或者增加某种安全信息，
 * 例如： <p>UniquePropertyEntity标签标明的实体属性为唯一性的字段，若插入或者更新操作
 * 出现该属性名相同，则抛出异常。</p>
 * <p>AuditEntity标签标明的实体是修改信息时必须记录到数据库的实体，必须添加信息到数据库.</p>
 *
 * <p>
 * <a href="SaveOrUpdateListener.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author 
 */
public class SaveOrUpdateListener  extends DefaultSaveOrUpdateEventListener {

 
	private static final long serialVersionUID = 6589581060381391095L;
	protected static final Log logger = LogFactory
			.getLog(SaveOrUpdateListener.class);

	/**
	 * 保存时候调用的触发事件
	 * @param entity
	 */
	public static void onSaveOrUpdate(Object entity) {
		boolean isInsert = false;
		BaseDao baseDao = (BaseDao) ApplicationContextKeeper.getAppCtx().getBean("baseDao");
		Class classEntity = entity.getClass();
		String classEntityName = classEntity.getName();
		Session session = baseDao.getHibernate().getSessionFactory().getCurrentSession();
		// 查出ENTITY的ID，看是事为空，空说明是新插入对象，非空证明是修改操作
		Serializable entityID = null;
		
		if (entity.getClass().getName().indexOf("$$") > -1) {
			if (entity instanceof HibernateProxy){
				try {
					String[] classEntityNames = classEntityName.split("\\$");
					if (null != classEntityNames && 0 < classEntityNames.length){
						if (classEntityNames[0].substring(classEntityNames[0].length()-2, classEntityNames[0].length()-1).equals("_")){
							classEntity = Class.forName(classEntityNames[0].substring(0, classEntityNames[0].length()-2));
						}
					} else {
						classEntity = Class.forName(session.getEntityName(entity));
					}
				} catch (ClassNotFoundException e1) {
					logger.error(e1);
					throw new HibernateException(e1.getMessage());
				}	
			} else {
				return ;
			}
		}
		UniquePropertyEntity uniquePropertyEntity = (UniquePropertyEntity) classEntity.getAnnotation(UniquePropertyEntity.class);
		AuditEntity auditAnnotation = (AuditEntity) classEntity.getAnnotation(AuditEntity.class);
		
		// 定义一个变量存放老的数据,oldValues是脱离session的一个实体，用来传递值
		Object oldEntity = null;
		Object oldValues = null;
		
		if (uniquePropertyEntity != null || auditAnnotation != null){
			try {
				entityID = baseDao.getId(classEntity, entity);
				if (entityID == null) isInsert = true;

				// 当非插入的时候拿到原来的数据
				if (!isInsert) {
					oldEntity = baseDao.get(classEntity, entityID);
				}
	
				if (oldEntity != null) {
					oldValues = classEntity.newInstance();
					BeanUtils.copyProperties(oldValues, oldEntity);
					session.evict(oldEntity);
				}
			} catch (NoSuchMethodException e1) {
				logger.error(e1);
				throw new HibernateException(e1.getMessage());
			} catch (IllegalAccessException e1) {
				logger.error(e1);
				throw new HibernateException(e1.getMessage());
			} catch (InvocationTargetException e1) {
				logger.error(e1);
				throw new HibernateException(e1.getMessage());
			} catch (InstantiationException e) {
				logger.error(e);
				throw new HibernateException(e.getMessage());
			}
			checkForUniqueField(entity, classEntity, session, isInsert, oldValues);
			//根据标签决定是否要增加修改的信息,若插入或更新不成功，增加的修改信息也跟着回滚
			addAuditInfo(entityID, entity, classEntity, session, isInsert, oldValues);
		}

	}
	
	
	/**
	 * 检查是否有唯一性字段的存在，再对其进行判断，确定是否唯一，
	 * 若不唯一，则抛出异常，若唯一则正常插入
	 * @param entity 更新或插入的实体
	 * @param classEntity 更新或插入的实体类型
	 * @param session hibernate session
	 * @param isInsert 是否为插入操作
	 * @param oldEntity （唯一性字段的）旧值，用于与新值做对比，若唯一性字段的新旧值相同
	 * 则正常更新
	 */
	private static void checkForUniqueField(Object entity, Class classEntity, Session session, boolean isInsert, Object oldValues) {

		// 获得唯一属性标签:
		UniquePropertyEntity uniquePropertyEntity = (UniquePropertyEntity) classEntity.getAnnotation(UniquePropertyEntity.class);
		if (uniquePropertyEntity != null) {
			BaseDao baseDao = (BaseDao) ApplicationContextKeeper.getAppCtx()
					.getBean("baseDao");
			String[] uniqueFieldNames = uniquePropertyEntity.fieldNames();
			for (String fieldName : uniqueFieldNames) {
				try {
					String value = BeanUtils.getProperty(entity, fieldName);
					value = value.trim();
					// 根据要修改的字段查询数据库得到List result，result里有值说明有冲突
					List result = baseDao.findBy(classEntity,null, fieldName, value,false);
					if (null != result && 0 < result.size()){
						for (int i=0;i<result.size();i++){
							session.evict(result.get(i));
						}
					}
					if (!isInsert) {
						String orgValue = BeanUtils.getProperty(oldValues,fieldName);
						if (orgValue != null)
							orgValue = orgValue.trim();
						// 当唯一性属性没改变时不抛异常
						if (value.equals(orgValue))
							continue;
						if (session.contains(entity)) {
							if (result.size() > 1)
								throw new HibernateException("the value of unique field existed, update failure!  “唯一性”的字段的值已经存在，更新失败！");
						} else if (result.size() > 0) {
							throw new HibernateException("the value of unique field existed, update failure!  “唯一性”的字段的值已经存在，更新失败！");
						}
					} else if (result.size() > 0) throw new HibernateException("the value of unique field existed, insert failure!  “唯一性”的字段的值已经存在，插入失败！");
					
				} catch (IllegalAccessException e) {
					logger.error(e);
					throw new HibernateException(e.getMessage());
				} catch (InvocationTargetException e) {
					logger.error(e);
					throw new HibernateException(e.getMessage());
				} catch (NoSuchMethodException e) {
					logger.error(e);
					throw new HibernateException(e.getMessage());
				}
			}
		}
	}
	

	/**
	 * 根据AuditEntity标签确定是否要增加修改实体的信息,若需要,该函数将向数据库操作两个表：
	 * audit_History与audit_History_detail表,插入的信息的条数则视AuditEntity标签所标的
	 * 属性的多少及实际修改的属性的多少而定
	 * @param id 实体的主键
	 * @param entity 操作的实体
	 * @param entityClass 操作的实体类型
	 * @param event 事件
	 * @param isInsert 是否是插入操作
	 * @param oldEntity 旧实体，更新操作时用来与新值对比，当插入操作时该实体为空
	 */
	private static void addAuditInfo(Serializable id, Object entity,
			Class entityClass, Session session, boolean isInsert,
			Object oldEntity) {

		// 获得修改标志属性的标签
		AuditEntity auditAnnotation = (AuditEntity) entityClass
				.getAnnotation(AuditEntity.class);
		if (auditAnnotation != null) {

			AuditHistory auditHistory = new AuditHistory();
			BaseDao baseDao = (BaseDao) ApplicationContextKeeper.getAppCtx()
					.getBean("baseDao");
			RequestHandler handler = RequestHandler.getContextRequestHandler();

			// 获得当前user:
			if (handler != null) {
				User currentUser = handler.getCurrentUser();
				auditHistory.setUpdater(currentUser);
			}

			auditHistory.setUpdateDt(new Date());

			auditHistory.setEntityName(entityClass.getName());
			if (isInsert) {
				try {
					id = baseDao.getId(entityClass, entity);
				} catch (NoSuchMethodException e) {
					logger.error(e);
					throw new HibernateException(e.getMessage());
				} catch (IllegalAccessException e) {
					logger.error(e);
					throw new HibernateException(e.getMessage());
				} catch (InvocationTargetException e) {
					logger.error(e);
					throw new HibernateException(e.getMessage());
				}
			}
			if (id != null) {
				auditHistory.setEntityId(id.toString());
			}
			// 定义一个list用来存储将存进数据库的AuditHistoryDetail
			// 当没修改时list为的大小为0，此时不增加auditHistory;
			List<AuditHistoryDetail> auditDetailList = new ArrayList<AuditHistoryDetail>();
			String fieldNames[] = auditAnnotation.fieldNames();

			for (String fieldName : fieldNames) {
				try {
					String finalValue = BeanUtils
							.getProperty(entity, fieldName);
					String orignalValue = null;
					if (oldEntity != null && !isInsert) {
						orignalValue = BeanUtils.getProperty(oldEntity,
								fieldName);

					}
					if (orignalValue == null && "".equals(finalValue)) {
						// 让null与""相等，别插入数据库
						orignalValue = "";
					}
					// 当新老值不相等时才存储修改的详细记录
					if (!finalValue.equals(orignalValue)) {
						AuditHistoryDetail auditDetail = new AuditHistoryDetail();// 要存进数据库的记录修改操作的实体
						auditDetail.setFieldName(fieldName);
						String fieldType = fieldName.getClass().getName()
								.toString();
						auditDetail.setFieldType(fieldType);
						auditDetail.setFinalValue(finalValue);
						auditDetail.setOrignalValue(orignalValue);
						auditDetail.setAuditHistory(auditHistory);
						auditDetailList.add(auditDetail);
					}

				} catch (Exception e) {
					logger.error(e);
					throw new HibernateException(e.getMessage());
				}
			}
			Iterator<AuditHistoryDetail> i = auditDetailList.iterator();
			if (auditDetailList.size() > 0) {
				// 先保存被修改的实体的信息进数据库
				baseDao.save(auditHistory);
				while (i.hasNext()) {
					baseDao.save(i.next());
				}
			}

		}

	}
}