/* com.cutty.bravo.components.common.domain.CodeGenDomain.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2011-7-1 下午08:57:05, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.domain;

/**
 *
 * <p>
 * <a href="CodeGenDomain.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class CodeGenDomain {
	public static final String FIELD_TYPE_ID="ID";
	public static final String FIELD_TYPE_DATE="DATE";
	public static final String FIELD_TYPE_STRING="STRING";
	public static final String FIELD_TYPE_MANY_2_ONE="M2O";
	public static final String FIELD_TYPE_INT="INT";
	public static final String FIELD_TYPE_DOUBLE="DOUBLE";
	public static final String FIELD_TYPE_FLOAT="FLOAT";
	private String columnName;
	private String fieldName;
	private String labelName;
	private String fieldType;
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
}
