/* com.cutty.bravo.core.ui.dataRender.web.DataManageMentAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-17 上午10:27:09, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

 */
package com.cutty.bravo.core.ui.dataRender.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.KeyValue;
import org.hibernate.mapping.ManyToOne;
import org.hibernate.mapping.Table;

import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.domain.M2MEntityTrigger;
import com.cutty.bravo.core.domain.annotation.M2MEntity;
import com.cutty.bravo.core.ui.Constants;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.utils.AutoLoadSessionFactoryBean;
import com.cutty.bravo.core.web.handler.SaasCodeHandler;
import com.cutty.bravo.core.web.struts2.BaseActionSupport;

/**
 * <p> 数据处理方法集合 </p>
 * <p>
 * <a href="DataManageMentAction.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
@Namespace("/ui")
@ParentPackage("bravo")
public class DataManageMentAction extends BaseActionSupport {
	private static final long serialVersionUID = -3818865076793947879L;

	public BaseDao baseDao;

	/**
	 * @param baseDao
	 *            the baseDao to set
	 */
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * 该函数处理选择回填多对多关系.
	 * 
	 * @return jsonDataRenderChain 交由json渲染器处理渲染
	 * @throws Exception
	 */
	public String m2mDataManageMent() throws Exception {
		ServletActionContext.getRequest().setAttribute(	Constants.GRID_BATCH_SAVE_KEY, Constants.GRID_BATCH_SAVE_VALUE);
		String[] ids = ServletActionContext.getRequest().getParameterValues(Constants.M2M_SELECT_IDS);
		String M2M_ENTITY_NAME = ServletActionContext.getRequest().getParameter(Constants.M2M_ENTITY_NAME);
		String M2M_ENTITY_ID = ServletActionContext.getRequest().getParameter(Constants.M2M_ENTITY_ID);
		String M2M_FIELD_NAME = ServletActionContext.getRequest().getParameter(Constants.M2M_FIELD_NAME);

		Map classMetadataMap = baseDao.getHibernate().getSessionFactory().getAllClassMetadata();
		// 如果只传实体名,不传包名,则循环处理,选择出该实体的包名。
		if (M2M_ENTITY_NAME.indexOf(".") < 0) {
			M2M_ENTITY_NAME = "." + M2M_ENTITY_NAME;
			Iterator classMetadataKeyIT = classMetadataMap.keySet().iterator();
			while (classMetadataKeyIT.hasNext()) {
				String className = (String) classMetadataKeyIT.next();
				int entityNameLen = (M2M_ENTITY_NAME.length()+className.indexOf(M2M_ENTITY_NAME));
				//该方法需优化，或需确保不同类包下的类名不会重复
				if (className.indexOf(M2M_ENTITY_NAME) > -1 && entityNameLen == className.length()) {
					M2M_ENTITY_NAME = className;
				}
			}
		}
		AutoLoadSessionFactoryBean autoLoadSessionFactoryBean = (AutoLoadSessionFactoryBean) ApplicationContextKeeper.getAppCtx().getBean("&sessionFactory");
		Collection collection = autoLoadSessionFactoryBean.getConfiguration().getCollectionMapping(M2M_ENTITY_NAME + "." + M2M_FIELD_NAME);

		ManyToOne element = (ManyToOne) collection.getElement();
		Iterator it = element.getColumnIterator();
		String elementName = element.getReferencedEntityName();
		Class inverseClass = null;
		String columnName = null;
		if (it.hasNext()) {
			Column column = (Column) it.next();
			columnName = column.getName();
		}
		KeyValue kv = collection.getKey();
		it = kv.getColumnIterator();
		String keyColumnName = null;
		if (it.hasNext()) {
			keyColumnName = ((Column) it.next()).getName();
		}
		Table midTable = collection.getCollectionTable();
		String midTableName = midTable.getName();
		Connection conn = null;
		PreparedStatement prepareStatement = null;
		Session session = null;
		try {
			session = baseDao.getHibernate().getSessionFactory()
					.getCurrentSession();
			conn = session.connection();
			prepareStatement = conn.prepareStatement("insert into "
					+ midTableName + "(" + keyColumnName + "," + columnName
					+ ") values (?,?)");
			Query query = session.createSQLQuery("select * from "
					+ midTableName + " where " + keyColumnName
					+ " = :keyColName and " + columnName + " = :colName");
			for (int i = 0; i < ids.length; i++) {
				query.setString("keyColName", M2M_ENTITY_ID);
				query.setString("colName", ids[i]);
				// 若数据库中已经存在该数据，则不插入
				List result = query.list();
				if (result.size() > 0)
					continue;
				prepareStatement.setString(1, M2M_ENTITY_ID);
				prepareStatement.setString(2, ids[i]);
				prepareStatement.executeUpdate();
			}
		} catch (Exception e) {
			logger.error(e);
			ServletActionContext.getRequest().setAttribute(
					Constants.GRID_BATCH_SAVE_STATUS,
					Constants.GRID_BATCH_SAVE_STATUS_FAILURE);
			ServletActionContext.getRequest().setAttribute(
					Constants.GRID_BATCH_SAVE_MSG, e.getMessage());
			return "jsonDataRenderChain";
		} finally {
			prepareStatement.close();
			conn.close();
			session.clear();
		}


		// 清除缓存
		baseDao.getHibernate().getSessionFactory().evictQueries();
		baseDao.getHibernate().getSessionFactory().evict(
				Class.forName(elementName));
		baseDao.getHibernate().getSessionFactory().evict(
				Class.forName(M2M_ENTITY_NAME));
		Class entityClass = Class.forName(M2M_ENTITY_NAME);
		M2MEntity m2MEntity = (M2MEntity) entityClass.getAnnotation(M2MEntity.class);
		if (m2MEntity != null) {
			String[] actionClasses = m2MEntity.actionClasses();
			String[] fieldNames = m2MEntity.fieldNames();
			for (int i=0;i<fieldNames.length;i++){
				if (M2M_FIELD_NAME.equals(fieldNames[i])){
					String actionClassStr = null;
					if (actionClasses.length ==1 && fieldNames.length > 1){
						actionClassStr = actionClasses[0];
					} else {
						actionClassStr = actionClasses[i];
					}
					M2MEntityTrigger m2MEntityTrigger =(M2MEntityTrigger)Class.forName(actionClassStr).newInstance();
					m2MEntityTrigger.trigger(M2M_ENTITY_ID,ids,M2MEntityTrigger.OPERATION_ADD);
				}
			}
		}
		
		ServletActionContext.getRequest().setAttribute(
				Constants.GRID_BATCH_SAVE_STATUS,
				Constants.GRID_BATCH_SAVE_STATUS_SUCCESS);
		return "jsonDataRenderChain";
	}

	/**
	 * 该函数处理选择删除多对多关系.
	 * 
	 * @return jsonDataRenderChain 交由json渲染器处理渲染
	 * @throws Exception
	 */
	public String m2mDataManageMentRemove() throws Exception {
		ServletActionContext.getRequest().setAttribute(
				Constants.GRID_BATCH_REMOVE_KEY, Constants.GRID_BATCH_REMOVE_VALUE);
		String[] ids = ServletActionContext.getRequest().getParameterValues(
				Constants.M2M_SELECT_IDS);
		String M2M_ENTITY_NAME = ServletActionContext.getRequest()
				.getParameter(Constants.M2M_ENTITY_NAME);
		String M2M_ENTITY_ID = ServletActionContext.getRequest().getParameter(
				Constants.M2M_ENTITY_ID);
		String M2M_FIELD_NAME = ServletActionContext.getRequest().getParameter(
				Constants.M2M_FIELD_NAME);

		Map classMetadataMap = baseDao.getHibernate().getSessionFactory()
				.getAllClassMetadata();
		// 如果只传实体名,不传包名,则循环处理,选择出该实体的包名。
		if (M2M_ENTITY_NAME.indexOf(".") < 0) {
			M2M_ENTITY_NAME = "." + M2M_ENTITY_NAME;
			Iterator classMetadataKeyIT = classMetadataMap.keySet().iterator();
			while (classMetadataKeyIT.hasNext()) {
				String className = (String) classMetadataKeyIT.next();
				if (className.lastIndexOf(M2M_ENTITY_NAME) > -1 && (className.length() - className.lastIndexOf(M2M_ENTITY_NAME) == M2M_ENTITY_NAME.length())) {
					M2M_ENTITY_NAME = className;
				}
			}
		}
		AutoLoadSessionFactoryBean autoLoadSessionFactoryBean = (AutoLoadSessionFactoryBean) ApplicationContextKeeper
				.getAppCtx().getBean("&sessionFactory");
		Collection collection = autoLoadSessionFactoryBean.getConfiguration()
				.getCollectionMapping(M2M_ENTITY_NAME + "." + M2M_FIELD_NAME);

		ManyToOne element = (ManyToOne) collection.getElement();
		Iterator it = element.getColumnIterator();
		String elementName = element.getReferencedEntityName();
		Class inverseClass = null;
		String columnName = null;
		if (it.hasNext()) {
			Column column = (Column) it.next();
			columnName = column.getName();
		}
		KeyValue kv = collection.getKey();
		it = kv.getColumnIterator();
		String keyColumnName = null;
		if (it.hasNext()) {
			keyColumnName = ((Column) it.next()).getName();
		}
		Table midTable = collection.getCollectionTable();
		String midTableName = midTable.getName();
		Connection conn = null;
		PreparedStatement prepareStatement = null;
		Session session = null;
		try {
			session = baseDao.getHibernate().getSessionFactory()
					.getCurrentSession();
			conn = session.connection();
			prepareStatement = conn.prepareStatement("delete from "
					+ midTableName + " where " + keyColumnName + " = ? and "
					+ columnName + " = ?");
			for (int i = 0; i < ids.length; i++) {
				prepareStatement.setString(1, M2M_ENTITY_ID);
				prepareStatement.setString(2, ids[i]);
				prepareStatement.executeUpdate();
			}
		} catch (Exception e) {
			logger.error(e);
			ServletActionContext.getRequest().setAttribute(
					Constants.GRID_BATCH_REMOVE_STATUS,
					Constants.GRID_BATCH_REMOVE_STATUS_FAILURE);
			ServletActionContext.getRequest().setAttribute(
					Constants.GRID_BATCH_REMOVE_MSG, e.getMessage());
			return "jsonDataRenderChain";
		} finally {
			prepareStatement.close();
			conn.close();
			session.clear();
		}

		// 清除缓存
		baseDao.getHibernate().getSessionFactory().evictQueries(SaasCodeHandler.getSaasCode());
		baseDao.getHibernate().getSessionFactory().evict(
				Class.forName(elementName));
		baseDao.getHibernate().getSessionFactory().evict(
				Class.forName(M2M_ENTITY_NAME));
		Class entityClass = Class.forName(M2M_ENTITY_NAME);
		M2MEntity m2MEntity = (M2MEntity) entityClass.getAnnotation(M2MEntity.class);
		if (m2MEntity != null) {
			String[] actionClasses = m2MEntity.actionClasses();
			String[] fieldNames = m2MEntity.fieldNames();
			for (int i=0;i<fieldNames.length;i++){
				if (M2M_FIELD_NAME.equals(fieldNames[i])){
					String actionClassStr = null;
					if (actionClasses.length ==1 && fieldNames.length > 1){
						actionClassStr = actionClasses[0];
					} else {
						actionClassStr = actionClasses[i];
					}
					//清cache缓存
					M2MEntityTrigger m2MEntityTrigger =(M2MEntityTrigger)Class.forName(actionClassStr).newInstance();
					m2MEntityTrigger.trigger(M2M_ENTITY_ID,ids,M2MEntityTrigger.OPERATION_REMOVE);
				}
			}
		}
		
		ServletActionContext.getRequest().setAttribute(
				Constants.GRID_BATCH_REMOVE_STATUS,
				Constants.GRID_BATCH_REMOVE_STATUS_SUCCESS);
		return "jsonDataRenderChain";
	}
}
