package com.cutty.bravo.core.dao.support.listener;

import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PostUpdateListener implements PostUpdateEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2088736281550097755L;
	protected static final Log logger = LogFactory.getLog(PostUpdateListener.class);

	public void onPostUpdate(PostUpdateEvent event) {

 
//		Object entity = event.getEntity();
//		Class<?> entityClass = entity.getClass();
//		Object oldValues[] = event.getOldState();
//
//		Object values[] = event.getState();
//		String propertyNames[] = event.getPersister().getPropertyNames();
//
//		if (entity.getClass().getName().indexOf("$$") > -1) {
//			try {
//				entityClass = Class.forName(event.getPersister()
//						.getEntityName());
//			} catch (ClassNotFoundException e1) {
//				logger.error(e1);
//				throw new HibernateException(e1.getMessage());
//			}
//		}
//		//获得唯一属性的标签
//		AuditEntity auditAnnotation = (AuditEntity) entityClass
//				.getAnnotation(AuditEntity.class);
//		if (auditAnnotation != null) {
////			event.getSession().getPersistenceContext().setFlushing(true);
////			event.getSession().getJDBCContext().getConnectionManager().flushBeginning(); 
////			event.getSession().getActionQueue().prepareActions(); 
////			event.getSession().getActionQueue().executeActions();
//			AuditHistory auditHistory = new AuditHistory();
//			BaseDao baseDao = (BaseDao) ApplicationContextKeeper.getAppCtx()
//					.getBean("baseDao");
//			RequestHandler handler = RequestHandler.getContextRequestHandler();
//		
//			// 获得当前user:
//			if (handler != null) {
//				User currentUser = handler.getCurrentUser();
//				auditHistory.setUpdater(currentUser);
//			}
//
//			auditHistory.setUpdateDt(new Date());
//
//			try {
//				if(null == oldValues){
//					Object entityValues = baseDao.get(entityClass, baseDao.getId(entityClass, entity));
//					System.out.println(entityValues.toString());
//				}
//				auditHistory.setEntityId(baseDao.getId(entityClass, entity)
//						.toString());
//			} catch (NoSuchMethodException e1) {
//				logger.error(e1);
//				throw new HibernateException(e1.getMessage());
//			} catch (IllegalAccessException e1) {
//				logger.error(e1);
//				throw new HibernateException(e1.getMessage());
//			} catch (InvocationTargetException e1) {
//				logger.error(e1);
//				throw new HibernateException(e1.getMessage());
//			}
//
//			auditHistory.setEntityName(entityClass.getName());
//
//			// 先保存被修改的实体的信息进数据库
//			baseDao.save(auditHistory);
// 
//			String fieldNames[] = auditAnnotation.fieldNames();
//			for (String fieldName : fieldNames) {
//				try {
//					AuditHistoryDetail auditDetail = new AuditHistoryDetail();// 要存进数据库的记录修改操作的实体
//					auditDetail.setFieldName(fieldName);
//					String fieldType = fieldName.getClass().getName()
//							.toString();
//					auditDetail.setFieldType(fieldType);
//					String finalValue = null;
//					String oldValue = null;
//					// 获到新修改和修改前的值,根据propertyName与fieldname是否相等去拿到index下标，取得新旧值保存。
//					for (int index = 0; index < propertyNames.length; index++) {
//						if (fieldName.equals(propertyNames[index])) {
//							if (values[index] != null) {
//
//								finalValue = values[index].toString();
//								auditDetail.setFinalValue(finalValue);
//							}
//							if (oldValues[index] != null) {
//								oldValue = oldValues[index].toString();
//								auditDetail.setOrignalValue(oldValue);
//							}
//							break;
//						}
//					}
//					auditDetail.setAuditHistory(auditHistory);
//					// 保存详细信息（包括实体的属性跟属性类型、修改前修改后的值）进数据库,因实体的某些属性可能没被修改，无必要全部存储
//					if (finalValue != null && !finalValue.equals(oldValue)
//							|| oldValue != null && !oldValue.equals(finalValue)) {
//						baseDao.save(auditDetail);
//					}
//				} catch (Exception e) {
//					logger.error(e);
//					throw new HibernateException(e.getMessage());
//				}
//				
//			}
////			event.getSession().getPersistenceContext().setFlushing(false);
////			event.getSession().getJDBCContext().getConnectionManager().flushEnding(); 
//		}

	}

}
