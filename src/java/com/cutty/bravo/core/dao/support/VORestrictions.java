/* com.cutty.bravo.core.dao.support.VORestrictions.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-20 上午02:05:46, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.dao.support;

import org.hibernate.criterion.Criterion;

import com.cutty.bravo.core.ConfigurableConstants;

/**
 * <p>该类存放查询条件的约束信息 </p>
 * <p>
 * <a href="VORestrictions.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class VORestrictions {
	private static VORestrictions voRestrictions;
	private VORestrictions() {
		
	}

	public static final String KEY = "_restriction"; 
	
	public static final String EQ = "eq"; //＝ 
	public static final String GT = "gt";//＞ 
	public static final String GE = "ge"; //＞＝ 
	public static final String LT = "lt"; //＜
	public static final String LE = "le"; //＜＝ 
	public static final String LLIKE = "llike"; //字符开始于...
	public static final String RLIKE = "rlike"; //字符结束于...
	public static final String LIKE = "like"; //字符包含于...
	public static final String IN = "in"; //in
	public static final String SQL_RESTRICTION = "sqlRestriction"; //用SQL限定查询 
	public static final String ALL_Eq  = "allEq "; //利用Map来进行多个等于的限制 
	public static final String BETWEEN = "between"; // BETWEEN
	public static final String IS_NULL = "isNull"; //in
	public static final String IS_NOT_NULL = "isNotNull"; //in	
	
	//*添加升降序的字符串，在QueryParameterWrapper中进行使用  liangg 09.03.04
	public static final String ORDER_KEY = "order_field_";//在QueryParameterWrapper的renderQueryParameterValueFromParameter函数中使用.
	public static final String ORDER_BY_ASC = "asc";
	public static final String ORDER_BY_DESC = "desc";
	//*
	/**
	 * 获取一个VORestrictions实例
	 * @return 返回一个VORestrictions实例
	 */
	public static VORestrictions getInstance() {
		if (null == voRestrictions){
			voRestrictions = new VORestrictions();
		}
		return voRestrictions;
	}
	
	/**
	 * 根据字段名、字段值及约束条件生成一个Criterion对象,见
	 * {@link QueryByMap#appendPropertyCondition(QueryParameterValue, org.hibernate.Criteria, org.hibernate.criterion.CriteriaQuery, StringBuffer)}
	 * @param propertyName 字段名
	 * @param propertyValue 字段值
	 * @param restriction 约束条件信息
	 * @return 返回一个Criterion对象
	 */
	public Criterion renderCriterion(String propertyName, Object propertyValue,String restriction){
		boolean isString = propertyValue instanceof String;
		String caseSensitiveStr = ConfigurableConstants.getProperty("query.common.case.sensitive","false");
		boolean caseSensitive = new Boolean(caseSensitiveStr).booleanValue();
		if (propertyValue != null) {
			if (isString){
				String propertyValueStr = (String)propertyValue;
				if (LLIKE.equalsIgnoreCase(restriction)){
					//将propertyValueStr+"%" 改为propertyValueStr测试, 这里加%起不了作用
					return new VOSimpleExpression(propertyName, propertyValueStr, " like ",caseSensitive && isString);
				} else if (RLIKE.equalsIgnoreCase(restriction)){
					return new VOSimpleExpression(propertyName, "%"+propertyValueStr, " like ",caseSensitive && isString);
				} else if (EQ.equalsIgnoreCase(restriction)){
					return new VOSimpleExpression(propertyName, propertyValue, " = ",caseSensitive && isString);
				} else {
					return new VOSimpleExpression(propertyName, "%"+propertyValueStr+"%", " like ",caseSensitive && isString);
				}
			} else if (GT.equalsIgnoreCase(restriction)){
				return new VOSimpleExpression(propertyName, propertyValue, " > ",caseSensitive && isString);
			} else if (GE.equalsIgnoreCase(restriction)){
				return new VOSimpleExpression(propertyName, propertyValue, " >= ",caseSensitive && isString);
			} else if (LT.equalsIgnoreCase(restriction)){
				return new VOSimpleExpression(propertyName, propertyValue, " < ",caseSensitive && isString);
			} else if (LE.equalsIgnoreCase(restriction)){
				return new VOSimpleExpression(propertyName, propertyValue, " <= ",caseSensitive && isString);
			} else {
				return new VOSimpleExpression(propertyName, propertyValue, " = ",caseSensitive && isString);
			}
		} else {
			if (IS_NULL.equalsIgnoreCase(restriction)){
				return new VONullExpression(propertyName);
			} else if (IS_NOT_NULL.equalsIgnoreCase(restriction)){
				return new VONotNullExpression(propertyName);
			}
			return new VONullExpression(propertyName);
		}
	}
	
		
}
