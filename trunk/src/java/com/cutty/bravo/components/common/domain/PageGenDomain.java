/* com.cutty.bravo.components.common.domain.PageGenDomain.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2011-7-13 下午01:50:20, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.domain;

/**
 *
 * <p>
 * <a href="PageGenDomain.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class PageGenDomain {

	private String fieldName;
	private String labelName;
	private String fieldType;
	private String fieldM2nRefField;
	private String fieldM2nDataproxy;
	private String fieldM2nRefDisplay;
	private String fieldM2nRefValue;
	
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
	public String getFieldM2nRefField() {
		return fieldM2nRefField;
	}
	public void setFieldM2nRefField(String fieldM2nRefField) {
		this.fieldM2nRefField = fieldM2nRefField;
	}
	public String getFieldM2nDataproxy() {
		return fieldM2nDataproxy;
	}
	public void setFieldM2nDataproxy(String fieldM2nDataproxy) {
		this.fieldM2nDataproxy = fieldM2nDataproxy;
	}
	public String getFieldM2nRefDisplay() {
		return fieldM2nRefDisplay;
	}
	public void setFieldM2nRefDisplay(String fieldM2nRefDisplay) {
		this.fieldM2nRefDisplay = fieldM2nRefDisplay;
	}
	public String getFieldM2nRefValue() {
		return fieldM2nRefValue;
	}
	public void setFieldM2nRefValue(String fieldM2nRefValue) {
		this.fieldM2nRefValue = fieldM2nRefValue;
	}
	
	
}