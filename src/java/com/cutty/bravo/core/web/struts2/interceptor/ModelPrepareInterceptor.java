/* com.cutty.bravo.core.web.struts2.interceptor.ModelPrepareInterceptor.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-29 下午11:41:45, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.web.struts2.interceptor;

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
import com.cutty.bravo.core.dao.support.Operators;
import com.cutty.bravo.core.dao.support.QueryParameterValue;
import com.cutty.bravo.core.dao.support.VORestrictions;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.web.handler.RequestHandler;
import com.cutty.bravo.core.web.struts2.EntityAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 *模型参数装载,该拦截器只针对hibernate,更换ORM需要重写该拦截器
 * <p>
 * <a href="ModelPrepareInterceptor.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class ModelPrepareInterceptor extends AbstractInterceptor {
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

	private static final long serialVersionUID = -7422952900198450802L;
	protected final Log logger = LogFactory.getLog(ModelPrepareInterceptor.class);
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		//获取当前参数如果需要才进行处理
        if (action instanceof EntityAction) {
        	Long startTime = System.currentTimeMillis();
        	EntityAction entityAction = (EntityAction) action;
        	Object model = entityAction.getModel();
        	if (null == model)invocation.invoke();
        	//获取从UI传过来的参数的迭代器，下面的循环中将从这些参数中找出需要进行处理的，添加到一个map中,以便做为查询的条件
        	Enumeration<String> parameterNames = ServletActionContext.getRequest().getParameterNames();
        	//定义一个map，键为某个表的属性名称，对应的值为一个存放约束该字段的约束条件的对象.
        	//例如：约束条件为where name = test and ...那么"name"作为key,值则为存放类似"="这些约束条件的类，见QueryParameterValue类的定义
        	Map<String,List<QueryParameterValue>> queryParameterMap = new HashMap<String,List<QueryParameterValue>>();
        	while (parameterNames.hasMoreElements()){
        		String parameterName = parameterNames.nextElement();
        		//参数命名格式必须等于字段名或者为XXX_index_1,,,,xxx_index_2.......  		
        		QueryValueBean  queryValueBean= getQueryParameterFieldName(model.getClass(),null,parameterName);
    			if (null != queryValueBean){
    				String[] parameterValues = ServletActionContext.getRequest().getParameterValues(parameterName);
    				if (null == parameterValues || (1 == parameterValues.length && StringUtils.isEmpty(parameterValues[0]))) break;
    				QueryParameterValue queryParameterValue = renderQueryParameterValueFromParameter(parameterName,queryValueBean);
    				String queryParameterFieldName = parameterName;
    				if (parameterName.indexOf("_index_")>-1) {
    					queryParameterFieldName = parameterName.split("_index_")[0];
    				}
    				List<QueryParameterValue> queryParameterList = queryParameterMap.get(queryParameterFieldName);
    				if (null == queryParameterList) queryParameterList = new ArrayList<QueryParameterValue>();
    				queryParameterList.add(queryParameterValue);
    				queryParameterMap.put(queryParameterFieldName, queryParameterList);
    			} 
        	}
        	RequestHandler requestHandler = RequestHandler.getContextRequestHandler();
        	if (null == requestHandler){
        		requestHandler = RequestHandler.getRequestHandler(ServletActionContext.getRequest());
        	}
       
        	Long endTime = System.currentTimeMillis();
        	logger.debug("Totle time is:::: " + ( endTime  - startTime) +  "   ::::milliseconds");
        }
        return invocation.invoke();
	}
	
	private QueryParameterValue renderQueryParameterValueFromParameter(String parameterName,QueryValueBean  queryValueBean){
		QueryParameterValue queryParameterValue = new QueryParameterValue();
		queryParameterValue.setFieldName(queryValueBean.getFieldName());
		queryParameterValue.setValueType(queryValueBean.getFieldType());
		String parameterOperator = ServletActionContext.getRequest().getParameter(parameterName+Operators.KEY);
		if (StringUtils.isEmpty(parameterOperator)) parameterOperator = Operators.AND;
		String parameterRestriction = ServletActionContext.getRequest().getParameter(parameterName+VORestrictions.KEY);
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
					|| (parameterName.indexOf(propertyNames[i])> -1 && parameterName.indexOf("_index_")>-1 
							&& parameterName.indexOf(VORestrictions.KEY) < 0 && parameterName.indexOf(Operators.KEY)<0)) {
				QueryValueBean queryValueBean = new QueryValueBean();
				queryValueBean.setFieldName(propertyNames[i]);
				queryValueBean.setFieldType(propertyTypes[i].getReturnedClass());
				return queryValueBean;
			} else {
				if (propertyNames[i].equals("processInstance")){
					System.out.println("sdasd");
				}
				if (parameterName.indexOf(propertyNames[i])> -1 && parameterName.indexOf(VORestrictions.KEY) > -1 &&
						(VORestrictions.IS_NOT_NULL.equals(ServletActionContext.getRequest().getParameterValues(parameterName))
								|| VORestrictions.IS_NULL.equals(ServletActionContext.getRequest().getParameterValues(parameterName)))){
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
}
