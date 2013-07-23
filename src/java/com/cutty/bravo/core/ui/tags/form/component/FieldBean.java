/* com.cutty.bravo.core.ui.bean.FieldBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-22 上午02:29:19, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.form.component;

import org.apache.commons.lang.StringUtils;

import com.cutty.bravo.core.ui.component.BoxComponent;

/**
 * <p> EXT标签Field属性集合类 </p>
 * <p>
 * <a href="FieldBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class FieldBean extends BoxComponent{

	private static final long serialVersionUID = 2245651987937105586L;

	private String autoCreate;
	private String clearCls ;
	private String cls ;
	private String disabled ;
	private String fieldClass ;
	private String fieldLabel ;
	private String focusClass ;
	private String hideLabel ;
	private String inputType ;
	private String invalidClass ;
	private String invalidText ;
	private String itemCls ;
	private String labelSeparator ;
	private String labelStyle ;
	private String msgFx ;
	private String readOnly  ;
	private String tabIndex  ;
	private String validateOnBlur  ;
	private String validationDelay  ;
	private String validationEvent  ;
	private String value  ;
	private String name  ;
	
	//默认field的style
	private String style;

	//扩展字段,表示该field传向服务器的字段名.
	private String fieldName;
	public String getAutoCreate() {
		return autoCreate;
	}
	public void setAutoCreate(String autoCreate) {
		this.autoCreate = autoCreate;
	}
	public String getClearCls() {
		return clearCls;
	}
	public void setClearCls(String clearCls) {
		this.clearCls = clearCls;
	}
	@Override
	public String getCls() {
		return cls;
	}
	@Override
	public void setCls(String cls) {
		this.cls = cls;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public String getFieldClass() {
		return fieldClass;
	}
	public void setFieldClass(String fieldClass) {
		this.fieldClass = fieldClass;
	}
	public String getFieldLabel() {
		return fieldLabel;
	}
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
	public String getFocusClass() {
		return focusClass;
	}
	public void setFocusClass(String focusClass) {
		this.focusClass = focusClass;
	}
	public String getHideLabel() {
		return hideLabel;
	}
	public void setHideLabel(String hideLabel) {
		this.hideLabel = hideLabel;
	}
	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	public String getInvalidClass() {
		return invalidClass;
	}
	public void setInvalidClass(String invalidClass) {
		this.invalidClass = invalidClass;
	}
	public String getInvalidText() {
		return invalidText;
	}
	public void setInvalidText(String invalidText) {
		this.invalidText = invalidText;
	}
	public String getItemCls() {
		return itemCls;
	}
	public void setItemCls(String itemCls) {
		this.itemCls = itemCls;
	}
	public String getLabelSeparator() {
		return labelSeparator;
	}
	public void setLabelSeparator(String labelSeparator) {
		this.labelSeparator = labelSeparator;
	}
	public String getLabelStyle() {
		return labelStyle;
	}
	public void setLabelStyle(String labelStyle) {
		this.labelStyle = labelStyle;
	}
	public String getMsgFx() {
		return msgFx;
	}
	public void setMsgFx(String msgFx) {
		this.msgFx = msgFx;
	}
	public String getReadOnly() {
		return readOnly;
	}
	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}
	public String getTabIndex() {
		return tabIndex;
	}
	public void setTabIndex(String tabIndex) {
		this.tabIndex = tabIndex;
	}
	public String getValidateOnBlur() {
		return validateOnBlur;
	}
	public void setValidateOnBlur(String validateOnBlur) {
		this.validateOnBlur = validateOnBlur;
	}
	public String getValidationDelay() {
		return validationDelay;
	}
	public void setValidationDelay(String validationDelay) {
		this.validationDelay = validationDelay;
	}
	public String getValidationEvent() {
		return validationEvent;
	}
	public void setValidationEvent(String validationEvent) {
		this.validationEvent = validationEvent;
	}
	public String getValue() {
		if (StringUtils.isEmpty(value)){
			this.value = "%{formValue."+this.getFieldName()+"?trim}";
		}
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the style
	 */
	@Override
	public String getStyle() {
		return style;
	}
	/**
	 * @param style the style to set
	 */
	@Override
	public void setStyle(String style) {
		this.style = style;
	}
	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		if (StringUtils.isEmpty(fieldName)){
			fieldName = this.getName().replace("___",".");
		}
		return fieldName;
	}
	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	/**
	 * @return the name
	 */
	@Override
	public String getName() {
//		System.out.println(this.getParentComponent().getName());
		if (null == name) {
			String beanName = this.getClass().getSimpleName();
			if (beanName.endsWith("Bean")){
				beanName = beanName.substring(0, beanName.length()-4);
			}
			this.name = beanName + this.hashCode();
		} else if (name.indexOf(".")>-1){
			return name.replace(".","___");
		}
	
		return name;
	}
	/**
	 * @param name the name to set
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	
	
}
