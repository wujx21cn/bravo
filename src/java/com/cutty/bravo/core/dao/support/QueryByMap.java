/* com.cutty.bravo.core.dao.support.QueryByMap.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-27 上午09:48:22, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.dao.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.TypedValue;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.type.Type;

import com.opensymphony.xwork2.ActionInvocation;




/**
 * <p>
 * 该类用于根据一个存放条件约束的Map来生成一个Criterion，以便查询时使用,
 * {@link #toSqlString(Criteria, CriteriaQuery)}方法与{@link #getTypedValues(Criteria, CriteriaQuery)}
 * 分别负责生成sql语句与获得条件语句中参数所需的值.
 * </p>
 * <p>
 * <a href="QueryByMap.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class QueryByMap implements Criterion {
	private static final long serialVersionUID = -8432301456603724744L;
	
	/**查询的实体类*/
	private final Class entityClass;
	
	/**存放约束条件的Map*/
	private final  Map<String,List<QueryParameterValue>> queryValueMap;
	
	/**sql前缀*/
	private final String prefix;
	
	/**
	 * 构造函数，初始化使用
	 * @param prefix
	 * @param entityClass
	 * @param queryValueMap
	 */
	protected QueryByMap(String prefix, Class entityClass,Map<String,List<QueryParameterValue>> queryValueMap) {
		if (null == entityClass){
			throw new NullPointerException("null entityClass\n你必须定义查询实体对应的class");
		} else {
			if (StringUtils.isEmpty(prefix)){
				this.prefix = "";
			} else {
				this.prefix = prefix;
			}
			this.entityClass = entityClass;
			this.queryValueMap = queryValueMap;
			
		} 
	}
	/**
	 * 通过queryValueMap中存放的约束条牛去创建一个QueryByMap, queryValueMap的构成见
	 * {@link ModelPrepareInterceptor#intercept(ActionInvocation invocation)}
	 *  
	 * @param entityClass 查询的实体类型
	 * @param queryValueMap 存放查询条件的MAP参数
	 * @return Criterion
	 * @see QueryParameterValue
	 */
	public static QueryByMap create(Class entityClass,Map<String,List<QueryParameterValue>> queryValueMap) {
		return create(null,entityClass,queryValueMap);
	}
	
	public static QueryByMap create(String prefix,Class entityClass,Map<String,List<QueryParameterValue>> queryValueMap) {
		if (null == entityClass)
			throw new NullPointerException("null entityClass\n你必须定义查询实体对应的class");
		return new QueryByMap(prefix,entityClass,queryValueMap);
	}
	
	/**
	 * 根据queryParameterValue添加查询条件，形成sql语句的约束条件
	 * 
	 * @param queryParameterValue 存放字段名与约束条件信息的类
	 * @param criteria
	 * @param cq
	 * @param buf 条件约束语句生成后存放的地方
	 * @see QueryParameterValue
	 * @throws HibernateException
	 * 
	 */
	protected void appendPropertyCondition(QueryParameterValue queryParameterValue, Criteria criteria, CriteriaQuery cq,
			StringBuffer buf) throws HibernateException {
		Criterion crit;
		String[] propertyValues = queryParameterValue.getValues();
		//该字段可能有多个约束值，将非空的值添加到where条件的后面，该where条件的形成还需要用到
		//queryParameterValue中的其他字段的值,例如and或者or等操作符,循环结束后形成一个条件约束语句
		//存放于StringBuffer buf中
		if (VORestrictions.IS_NULL.equals(queryParameterValue.getRestriction()) 
				|| VORestrictions.IS_NOT_NULL.equals(queryParameterValue.getRestriction())){
			crit =  VORestrictions.getInstance().renderCriterion(queryParameterValue.getFieldName(), null, queryParameterValue.getRestriction());
			String critCondition = crit.toSqlString(criteria, cq);
			if (buf.length() > 1 && critCondition.trim().length() > 0)
				buf.append("  " + queryParameterValue.getOperator()+ "  " );
			buf.append(critCondition);
		} else {
			//当该属性值为空时不追加该属性的约束条件
			if (null == propertyValues || 0 == propertyValues.length) return;
			for (int i=0;i<propertyValues.length;i++){
				if (isNullValue(propertyValues[i])  ) break;
				Object value = ConvertUtils.convert(propertyValues[i], queryParameterValue.getValueType());
				crit =  VORestrictions.getInstance().renderCriterion(queryParameterValue.getFieldName(), value, queryParameterValue.getRestriction());
				String critCondition = crit.toSqlString(criteria, cq);
				if (buf.length() > 1 && critCondition.trim().length() > 0)
					buf.append("  " + queryParameterValue.getOperator()+ "  " );
				buf.append(critCondition);
			}
		}
		

	}
	
	/**
	 * 根据queryParameterValue添加查询条件，形成sql语句的约束条件,实际是通过调用
	 * {@link #appendPropertyCondition(QueryParameterValue, Criteria, CriteriaQuery, StringBuffer)}
	 * 来完成.
	 * 
	 * @param criteria
	 * @param criteriaQuery
	 * @see QueryParameterValue
	 * @return 返回sql语句的where条件用()包括起来
	 */
	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
			throws HibernateException {
		StringBuffer buf = new StringBuffer().append('(');
		EntityPersister meta = criteriaQuery.getFactory().getEntityPersister( criteriaQuery.getEntityName(criteria) );
		
		//获得主键
		String identifierName = meta.getIdentifierPropertyName();
		//获得主键名所对应的QueryParameterValue
		List<QueryParameterValue> identifierParameters = queryValueMap.get(identifierName);
		if (null != identifierParameters && 0 < identifierParameters.size()){
			Iterator<QueryParameterValue>  queryParameterValueIT = identifierParameters.iterator();
			while (queryParameterValueIT.hasNext()){
				QueryParameterValue identifierParameterValue = queryParameterValueIT.next();
				appendPropertyCondition(identifierParameterValue,criteria,criteriaQuery,buf);
			}
		}
		//获得字段名称与字段类型
		String[] propertyNames = meta.getPropertyNames();
		Type[] propertyTypes = meta.getPropertyTypes();
		
		//循环对各字段进行处理，当从queryValueMap中取得到值的字段则说明需要对其进行添加条件约束
		for (int i=0; i<propertyNames.length; i++) {
			if (!propertyTypes[i].isComponentType() ){
				List<QueryParameterValue> propertyParameters = queryValueMap.get(propertyNames[i]);
				if (null != propertyParameters && 0 < propertyParameters.size()){
					Iterator<QueryParameterValue>  propertyParameterValueIT = propertyParameters.iterator();
					//一个字段可能有多个约束值，故循环处理
					while (propertyParameterValueIT.hasNext()){
						QueryParameterValue propertyParameterValue = propertyParameterValueIT.next();
						if (!propertyTypes[i].isAssociationType()){
							appendPropertyCondition(propertyParameterValue,criteria,criteriaQuery,buf);
						} else if (VORestrictions.IS_NOT_NULL.equals(propertyParameterValue.getRestriction()) || 
								VORestrictions.IS_NULL.equals(propertyParameterValue.getRestriction())){
							appendPropertyCondition(propertyParameterValue,criteria,criteriaQuery,buf);
						}
						
					}
				}
			}
		}
		

		if ( buf.length()==1 ) buf.append("1=1"); 
		return buf.append(')').toString();
	}
	
	/**
	 * 返回where条件里所需要的参数值的数组.通过调用
	 * {@link #appendPropertyValue(QueryParameterValue, Type, List)}来实现
	 * 
	 * @param criteria
	 * @param criteriaQuery
	 * @return 
	 */
	public TypedValue[] getTypedValues(Criteria criteria,
			CriteriaQuery criteriaQuery) throws HibernateException {
		EntityPersister meta = criteriaQuery.getFactory()
		.getEntityPersister( criteriaQuery.getEntityName(criteria) );
		List list = new ArrayList();
		
		//获得主键名称
		String identifierName = meta.getIdentifierPropertyName();
		//获得主键类型
		Type identifierType = meta.getIdentifierType();
		List<QueryParameterValue> identifierParameters = queryValueMap.get(identifierName);
		if (null != identifierParameters && 0 < identifierParameters.size()){
			Iterator<QueryParameterValue>  queryParameterValueIT = identifierParameters.iterator();
			while (queryParameterValueIT.hasNext()){
				appendPropertyValue(queryParameterValueIT.next(),identifierType,list);
			}
		}
		//获得其他字段的名称与类型
		String[] propertyNames = meta.getPropertyNames();
		Type[] propertyTypes = meta.getPropertyTypes();
		
		//循环进行赋值,类似于toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)方法
		//当List<QueryParameterValue>中存在该字段的时候则需要进行赋值.
		for (int i=0; i<propertyNames.length; i++) {
			if (!propertyTypes[i].isComponentType() && !propertyTypes[i].isAssociationType()){
				List<QueryParameterValue> propertyParameters = queryValueMap.get(propertyNames[i]);
				if (null != propertyParameters && 0 < propertyParameters.size()){
					Iterator<QueryParameterValue>  propertyParameterValueIT = propertyParameters.iterator();
					
					//一个字段可能会赋多个值，因为可能有多个约束
					while (propertyParameterValueIT.hasNext()){
						appendPropertyValue(propertyParameterValueIT.next(),propertyTypes[i],list);
					}
				}
			}
		}
		
	
		return (TypedValue[]) list.toArray(TYPED_VALUES);
	}
	
	/**
	 * 该方法在{@link #getTypedValues(Criteria, CriteriaQuery)}中调用，
	 * 用于赋于where条件语句中参数所需的值
	 * 
	 * @param queryParameterValue 存放字段名与约束条件信息的类
	 * @param type 实体字段的类型
	 * @param list 存放所需的值的列表
	 */
	protected void appendPropertyValue(QueryParameterValue queryParameterValue,Type type, List list){
		String[] propertyValues = queryParameterValue.getValues();
		if (null == propertyValues || 0 == propertyValues.length) return;
		for (int i=0;i<propertyValues.length;i++){
			if (isNullValue(propertyValues[i])) break;
			Object value = ConvertUtils.convert(propertyValues[i], queryParameterValue.getValueType());
			
			//左匹配
			if(VORestrictions.LLIKE.equalsIgnoreCase(queryParameterValue.getRestriction())) {
				addPropertyTypedValue(value + "%", type, list);
			}
			//右匹配
			else if(VORestrictions.RLIKE.equalsIgnoreCase(queryParameterValue.getRestriction())){
				addPropertyTypedValue("%" + value, type, list);
			}
			//模糊匹配
			else if(VORestrictions.LIKE.equalsIgnoreCase(queryParameterValue.getRestriction())){
				addPropertyTypedValue("%" + value + "%", type, list);
			}
			else {
				addPropertyTypedValue(value,type, list);
			}
		}
	}
	
	/**
	 * 添加条件语句中的参数所需的值
	 * 
	 * @param value
	 * @param type
	 * @param list
	 */
	protected void addPropertyTypedValue(Object value, Type type, List list) {
		if (value != null) {
			if (value instanceof String) {
				String string = (String) value;
				//string = string.toLowerCase();
				if (isNullValue(value)) {
					return;
				}
				value = string;
			}
			list.add(new TypedValue(type, value, null));
		}
	}
	
	/**
	 * 判断value是否为空
	 * @param value
	 * @return
	 */
	private boolean isNullValue(Object value) {
		if (value instanceof String) {
			if (StringUtils.isEmpty(StringUtils.trim((String) value))) {
				return true;
			}
		}
		return false;
	}
	
	private static final Object[] TYPED_VALUES = new TypedValue[0];
}
