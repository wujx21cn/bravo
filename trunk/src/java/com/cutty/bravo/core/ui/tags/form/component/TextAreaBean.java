/* com.cutty.bravo.core.ui.tags.form.component.TextAreaBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-6 上午06:35:23, Created by kukuxia.hw
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.form.component;

import org.apache.commons.lang.StringUtils;

/**
 * <p> EXT标签TextArea属性集合类 </p>
 * <p>
 * <a href="TextAreaBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:huangw100@126.com">kukuxia.kevin.hw</a>
 */
public class TextAreaBean extends TextFieldBean{

	private static final long serialVersionUID = -87406858209701124L;
	private String autoCreate;
	private String growMax;
	private String growMin;
	private String preventScrollbars;
	private String TextArea;
	private String autoSize;
	private String value;

	
	@Override
	public String getAutoCreate() {
		return autoCreate;
	}
	@Override
	public void setAutoCreate(String autoCreate) {
		this.autoCreate = autoCreate;
	}
	@Override
	public String getGrowMax() {
		return growMax;
	}
	@Override
	public void setGrowMax(String growMax) {
		this.growMax = growMax;
	}
	@Override
	public String getGrowMin() {
		return growMin;
	}
	@Override
	public void setGrowMin(String growMin) {
		this.growMin = growMin;
	}
	public String getPreventScrollbars() {
		return preventScrollbars;
	}
	public void setPreventScrollbars(String preventScrollbars) {
		this.preventScrollbars = preventScrollbars;
	}
	public String getTextArea() {
		return TextArea;
	}
	public void setTextArea(String textArea) {
		TextArea = textArea;
	}
	public String getAutoSize() {
		return autoSize;
	}
	public void setAutoSize(String autoSize) {
		this.autoSize = autoSize;
	}
	/**
	 * @return the value
	 */
	@Override
	public String getValue() {
		if (StringUtils.isEmpty(value)){
			this.value = "%{Static[\"org.springframework.web.util.JavaScriptUtils\"].javaScriptEscape(formValue."+this.getFieldName()+")}";
		}
		return value;
	}
	/**
	 * @param value the value to set
	 */
	@Override
	public void setValue(String value) {
		this.value = value;
	}

}
