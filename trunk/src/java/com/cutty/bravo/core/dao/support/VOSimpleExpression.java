/* com.cutty.bravo.core.dao.support.VOSimpleExpression.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-20 上午02:06:59, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

 */
package com.cutty.bravo.core.dao.support;

import org.hibernate.criterion.SimpleExpression;

/**
 * <p>
 * 根据字段名、字段值及约束条件生成一个Criterion对象. 见：
 * {@link VORestrictions#renderCriterion(String, Object, String)}
 * </p>
 * <p>
 * <a href="VOSimpleExpression.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 * @see VORestrictions
 */
public class VOSimpleExpression extends SimpleExpression {

	private static final long serialVersionUID = -5538910080725230501L;
	
	/**
	 * 
	 * @param propertyName 字段名
	 * @param value 字段值
	 * @param op 约束条件
	 */
	protected VOSimpleExpression(String propertyName, Object value, String op) {
		super(propertyName, value, op);
	}
	
	/**
	 * 
	 * @param propertyName 字段名
	 * @param value 字段值
	 * @param op 约束条件
	 * @param ignoreCase 是否不区分大小写
	 */
	protected VOSimpleExpression(String propertyName, Object value, String op,
			boolean ignoreCase) {
		super(propertyName, value, op, ignoreCase);
	}

}