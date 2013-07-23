/* com.cutty.bravo.core.dao.support.QueryParameterValue.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-20 上午02:12:21, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.dao.support;


/**
 * <p>该类存放查询条件中的约束参数，例如where中的连接是and或者or, 约束是完全匹配、左匹配还是右匹配
 *    同时存放着查询条件中的字段名称、字段类型还有它的值.该类一般作为查询条件的Map的值，该类的fieldName
 *    作为Map的key.定义如下：{@code Map<String,List<QueryParameterValue>>}
 * </p>
 * <p>
 * <a href="QueryParameterValue.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class QueryParameterValue  {
	
	/**查询条件中的字段名称*/
	private String fieldName;
	
	/**查询条件中的操作类型，有"and" 或者"or" */
	private String operator;
	
	/**查询条件中的约束条件，例如左匹配、右匹配等*/
	private String restriction;
	
	/**字段的类型*/
	private Class valueType;
	
	/**字段的值*/
	private String[] values;
	
	/**是否按该fieldName升降序排列,asc为升，desc为降，null为不按该fieldName排序. liangg 09.03.04*/
	private String order = null;

	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	/**
	 * 
	 * @return 字段名
	 */
	public String getFieldName() {
		return fieldName;
	}
	/**
	 * 
	 * @param fieldName  设置的字段名
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	/**
	 * @return the restriction
	 */
	public String getRestriction() {
		return restriction;
	}
	/**
	 * @param restriction the restriction to set
	 */
	public void setRestriction(String restriction) {
		this.restriction = restriction;
	}

	/**
	 * @return the values
	 */
	public String[] getValues() {
		return values;
	}
	/**
	 * @param values the values to set
	 */
	public void setValues(String[] values) {
		this.values = values;
	}
	/**
	 * @return the valueType
	 */
	public Class getValueType() {
		return valueType;
	}
	/**
	 * @param valueType the valueType to set
	 */
	public void setValueType(Class valueType) {
		this.valueType = valueType;
	}
	
}
