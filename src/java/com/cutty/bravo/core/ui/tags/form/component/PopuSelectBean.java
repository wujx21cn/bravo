/* com.cutty.bravo.core.ui.tags.form.component.PopuSelectBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Nov 10, 2008 11:11:49 AM, Created kukuxia.kevin.hw
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.form.component;

/**
 * <p> EXT标签PopuSelect属性集合类 </p>
 * <p>
 * <a href="PopuSelectBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:huangw100@126.com">kukuxia.kevin.hw</a>
 */
public class PopuSelectBean extends TriggerFieldBean {

	private static final long serialVersionUID = 5805334779847419207L;
	private String targetProxy;
	private String displayField;
	private String valueField;
	private String targetGridName;
	private String hiddenName;//用来匹配需找该控件对应的hidden字段
	private String text;
	
	public String getTargetProxy() {
		return targetProxy;
	}
	public void setTargetProxy(String targetProxy) {
		this.targetProxy = targetProxy;
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
	public String getHiddenName() {
		return hiddenName;
	}
	public void setHiddenName(String hiddenName) {
		this.hiddenName = hiddenName;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTargetGridName() {
		return targetGridName;
	}
	public void setTargetGridName(String targetGridName) {
		this.targetGridName = targetGridName;
	}
	

}
