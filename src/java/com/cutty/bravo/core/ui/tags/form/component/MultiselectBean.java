/* com.cutty.bravo.core.ui.tags.form.component.MultiselectBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Sep 11, 2008 11:35:27 AM, Created by kukuxia.hw
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.form.component;

/**
 * <p> EXT标签Multiselect属性集合类 </p>
 * <p>
 * <a href="MultiselectBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:huangw100@126.com">kukuxia.hw</a>
 */
public class MultiselectBean extends FieldBean {

	private static final long serialVersionUID = 2441591923227994977L;
    private String appendOnly;
    private String width;
    private String height;
    private String displayField;
    private String valueField;
    private String allowBlank;
    private String minLength;
    private String maxLength;
    private String blankText;
    private String minLengthText;
    private String maxLengthText;
    
	private String dataProxy;
	public String getDataProxy() {
		return dataProxy;
	}
	public void setDataProxy(String dataProxy) {
		this.dataProxy = dataProxy;
	}

	public String getAppendOnly() {
		return appendOnly;
	}
	public void setAppendOnly(String appendOnly) {
		this.appendOnly = appendOnly;
	}
	@Override
	public String getWidth() {
		return width;
	}
	@Override
	public void setWidth(String width) {
		this.width = width;
	}
	@Override
	public String getHeight() {
		return height;
	}
	@Override
	public void setHeight(String height) {
		this.height = height;
	}
	public String getDisplayField() {
		return displayField;
	}
	public void setDisplayField(String displayField) {
		this.displayField = displayField;
	}
	public String getValueField() {
		return valueField;
	}
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}
	public String getAllowBlank() {
		return allowBlank;
	}
	public void setAllowBlank(String allowBlank) {
		this.allowBlank = allowBlank;
	}
	public String getMinLength() {
		return minLength;
	}
	public void setMinLength(String minLength) {
		this.minLength = minLength;
	}
	public String getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}
	public String getBlankText() {
		return blankText;
	}
	public void setBlankText(String blankText) {
		this.blankText = blankText;
	}
	public String getMinLengthText() {
		return minLengthText;
	}
	public void setMinLengthText(String minLengthText) {
		this.minLengthText = minLengthText;
	}
	public String getMaxLengthText() {
		return maxLengthText;
	}
	public void setMaxLengthText(String maxLengthText) {
		this.maxLengthText = maxLengthText;
	}
    
}
