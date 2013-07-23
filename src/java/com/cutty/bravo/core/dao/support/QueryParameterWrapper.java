/* com.cutty.bravo.core.utils.QueryParameterWrapper.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-12-15 上午11:00:46, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.dao.support;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.type.CollectionType;
import org.hibernate.type.Type;

import com.cutty.bravo.core.dao.impl.HibernateBaseDao;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;

/**
 *
 * <p>
 * <a href="QueryParameterWrapper.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class QueryParameterWrapper {
	private static final long serialVersionUID = -7422952900198450802L;
	protected final Log logger = LogFactory.getLog(QueryParameterWrapper.class);
	private Class entityClass;
	private Map<String,List<QueryParameterValue>> queryParameterMap;
	private class QueryValueBean{
		private String fieldName;
		private Class fieldType;
		/**
		 * @return the fieldName
		 */
		public String getFieldName() {
			return fieldName;
		}
		/**
		 * @param fieldName the fieldName to set
		 */
		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
		/**
		 * @return the fieldType
		 */
		public Class getFieldType() {
			return fieldType;
		}
		/**
		 * @param fieldType the fieldType to set
		 */
		public void setFieldType(Class fieldType) {
			this.fieldType = fieldType;
		}
		
	}
	
	/**
	 * 初始化函数,需要设置为那个Class作为查询实体
	 * @param entityClass
	 */
	public QueryParameterWrapper (Class entityClass){
		this.entityClass = entityClass;
		queryParameterMap = rendQueryParameterMap();
	}
	
	
	private Map<String,List<QueryParameterValue>> rendQueryParameterMap() {
    	Enumeration<String> parameterNames = ServletActionContext.getRequest().getParameterNames();
    	//定义一个map，键为某个表的属性名称，对应的值为一个存放约束该字段的约束条件的对象.
    	//例如：约束条件为where name = test and ...那么"name"作为key,值则为存放类似"="这些约束条件的类，见QueryParameterValue类的定义
    	Map<String,List<QueryParameterValue>> queryParameterMap = new HashMap<String,List<QueryParameterValue>>();
    	while (parameterNames.hasMoreElements()){
    		String parameterName = parameterNames.nextElement();
    		//参数命名格式必须等于字段名或者为XXX_index_1,,,,xxx_index_2.......  		
    		QueryValueBean queryValueBean = null;
			try {
				queryValueBean = getQueryParameterFieldName(this.entityClass,null,parameterName);
			} catch (Exception e) {
				logger.error(e);
			}
			if (null != queryValueBean){
				String[] parameterValues = ServletActionContext.getRequest().getParameterValues(parameterName);
				if (null == parameterValues || (1 == parameterValues.length && StringUtils.isEmpty(parameterValues[0]))) break;
				QueryParameterValue queryParameterValue = null;
				String queryParameterFieldName = parameterName;
				//如果是带_index_的参数,则需要把查询参数转化为字段名
				if (parameterName.indexOf("_index_")>-1) {
					queryParameterFieldName = parameterName.split("_index_")[0];
				}
				//如果传入的是操作符号，并且为判断null或非null.则转化为字段名.
				if (parameterName.indexOf("_restriction")>-1 && 
						(VORestrictions.IS_NOT_NULL.equals(ServletActionContext.getRequest().getParameter(parameterName))
						|| VORestrictions.IS_NULL.equals(ServletActionContext.getRequest().getParameter(parameterName)))){
					queryParameterFieldName = parameterName.split("_restriction")[0];
					//null或非null比较特殊,需要重新创建该查询参数封装.
					queryParameterValue = new QueryParameterValue();
					queryParameterValue.setFieldName(queryValueBean.getFieldName());
					queryParameterValue.setValueType(queryValueBean.getFieldType());
					String parameterOperator = ServletActionContext.getRequest().getParameter(queryParameterFieldName+Operators.KEY);
					if (StringUtils.isEmpty(parameterOperator)) parameterOperator = Operators.AND;
					queryParameterValue.setOperator(parameterOperator);
					queryParameterValue.setRestriction(ServletActionContext.getRequest().getParameter(parameterName));
				} else {
					queryParameterValue = renderQueryParameterValueFromParameter(parameterName,queryValueBean);
				}
				List<QueryParameterValue> queryParameterList = queryParameterMap.get(queryParameterFieldName);
				if (null == queryParameterList) queryParameterList = new ArrayList<QueryParameterValue>();
				queryParameterList.add(queryParameterValue);
				queryParameterMap.put(queryParameterFieldName, queryParameterList);
			} 
    	}
		return queryParameterMap;
	}
	private QueryParameterValue renderQueryParameterValueFromParameter(String parameterName,QueryValueBean  queryValueBean){
		QueryParameterValue queryParameterValue = new QueryParameterValue();
		queryParameterValue.setFieldName(queryValueBean.getFieldName());
		queryParameterValue.setValueType(queryValueBean.getFieldType());
		String parameterOperator = ServletActionContext.getRequest().getParameter(parameterName+Operators.KEY);
		if (StringUtils.isEmpty(parameterOperator)) parameterOperator = Operators.AND;
		String parameterRestriction = ServletActionContext.getRequest().getParameter(parameterName+VORestrictions.KEY);
		//*获得该field的order_field参数("asc"或者"desc")，非空则加到该字段的QueryParameterValue中的order属性中去,实现排序功能
		//* liangg 09.03.04
		String orderFieldRestriction = ServletActionContext.getRequest().getParameter(VORestrictions.ORDER_KEY + parameterName);
		if (StringUtils.isNotEmpty(orderFieldRestriction)){
			queryParameterValue.setOrder(orderFieldRestriction);
		}
		//* 
		if (StringUtils.isEmpty(parameterRestriction)){
			if(queryValueBean.getFieldType().getSimpleName().equals("String")){
				parameterRestriction = VORestrictions.LIKE;
			}
			else{
				parameterRestriction = VORestrictions.EQ;
			}
		}
		queryParameterValue.setOperator(parameterOperator);
		queryParameterValue.setRestriction(parameterRestriction);
		queryParameterValue.setValues(ServletActionContext.getRequest().getParameterValues(parameterName));
		return queryParameterValue;
	}
	
	private QueryValueBean getQueryParameterFieldName(Class modelClass,String prefix,String parameterName) throws Exception{
		HibernateBaseDao baseDao = (HibernateBaseDao)ApplicationContextKeeper.getAppCtx().getBean("baseDao");
    	SessionFactoryImplementor sessionFactoryImplementor = (SessionFactoryImplementor)baseDao.getSessionFactoryFormHibernateTemplate();
    	if (null == sessionFactoryImplementor) throw new Exception("sessionFactory of hibernate not found,!\n找不到sessionFactory!!!");
    	EntityPersister meta = sessionFactoryImplementor.getEntityPersister(modelClass.getName());

    	
		String[] propertyNames = meta.getPropertyNames();
		Type[] propertyTypes = meta.getPropertyTypes();
		propertyNames = (String[])ArrayUtils.add(propertyNames, meta.getIdentifierPropertyName());
		propertyTypes = (Type[])ArrayUtils.add(propertyTypes, meta.getIdentifierType());
		
		if (parameterName.indexOf(".")>0){
			String[] queryParameterNamArray = parameterName.split("\\.");
			if (null == prefix) {
				prefix = queryParameterNamArray[0];
			} else {
				prefix = prefix + "." + queryParameterNamArray[0];
			}
			
			String queryParameterName =parameterName.substring(0,parameterName.indexOf("."));

			for (int i=0;i<propertyNames.length;i++){
				if (propertyNames[i].equals(queryParameterName)){
					if (propertyTypes[i].isCollectionType()){
						CollectionType collectionType = (CollectionType)propertyTypes[i];
						CollectionPersister collectionPersister = sessionFactoryImplementor.getCollectionPersister(collectionType.getRole());
						return getQueryParameterFieldName(collectionPersister.getElementType().getReturnedClass(),prefix,parameterName.substring(parameterName.indexOf(".")+1,parameterName.length()));
					} else {
						return getQueryParameterFieldName(propertyTypes[i].getReturnedClass(),prefix,parameterName.substring(parameterName.indexOf(".")+1,parameterName.length()));
					}
				}
			}
		}
		//处理查询参数,与查询操作符号.
		for (int i=0;i<propertyNames.length;i++){
			if (parameterName.equals(propertyNames[i]) 
					|| ((parameterName.indexOf(propertyNames[i])> -1 && parameterName.indexOf("_index_")>-1 
							&& parameterName.indexOf(VORestrictions.KEY) < 0 && parameterName.indexOf(Operators.KEY)<0))) {
				QueryValueBean queryValueBean = new QueryValueBean();
				queryValueBean.setFieldName(propertyNames[i]);
				queryValueBean.setFieldType(propertyTypes[i].getReturnedClass());
				return queryValueBean;
			} else {
				//如果已经分解了前缀,则需要恢复前缀以获取操作符的参数.
				String queryParameterName = parameterName;
				if (!StringUtils.isEmpty(prefix)){
					queryParameterName =  prefix + "." + parameterName;
				}
				if (parameterName.indexOf(propertyNames[i])> -1 && parameterName.indexOf(VORestrictions.KEY) > -1 &&
						(VORestrictions.IS_NOT_NULL.equals(ServletActionContext.getRequest().getParameter(queryParameterName))
								|| VORestrictions.IS_NULL.equals(ServletActionContext.getRequest().getParameter(queryParameterName)))){
					String parameterPropertyName = parameterName.substring(0,parameterName.indexOf(VORestrictions.KEY));
					if (propertyNames[i].equals(parameterPropertyName)){
						QueryValueBean queryValueBean = new QueryValueBean();
						queryValueBean.setFieldName(propertyNames[i]);
						queryValueBean.setFieldType(propertyTypes[i].getReturnedClass());
						return queryValueBean;
					}
				}
			}
		}
		return null;
	}


	public Map<String, List<QueryParameterValue>> getQueryParameterMap() {
		return queryParameterMap;
	}

	public Class getEntityClass() {
		return entityClass;
	}
	
	public void removeQueryParameter(String parameterName){
		queryParameterMap.remove(parameterName);
	}
}
