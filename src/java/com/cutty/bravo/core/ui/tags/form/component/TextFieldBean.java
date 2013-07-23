/* com.cutty.bravo.core.ui.tags.form.component.TextFieldBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-6 上午06:35:23, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.form.component;

/**
 * <p> EXT标签TextField属性集合类 </p>
 * <p>
 * <a href="TextFieldBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:huangw100@126.com">kukuxia.kevin.hw</a>
 */
public class TextFieldBean extends FieldBean{

	private static final long serialVersionUID = -2432934102611277660L;

	private String allowBlank;
	private String blankText;
	private String disableKeyFilter;
	private String emptyClass ;
	private String emptyText ;
	private String grow ;
	private String growMax ;
	private String growMin ;
	private String maskRe ;
	private String maxLength ;
	private String maxLengthText ;
	private String minLength ;
	private String minLengthText ;
	private String regex ;
	private String regexText ;
	private String selectOnFocus ;
	private String validator ;
	private String vtype ;
	private String vtypeText ;
	//响应键盘事件
	private String enableKeyEvents ;
	
	public String getEnableKeyEvents() {
		return enableKeyEvents;
	}
	public void setEnableKeyEvents(String enableKeyEvents) {
		this.enableKeyEvents = enableKeyEvents;
	}
	/**
	 * @return the allowBlank
	 */
	public String getAllowBlank() {
		return allowBlank;
	}
	/**
	 * @param allowBlank the allowBlank to set
	 */
	public void setAllowBlank(String allowBlank) {
		this.allowBlank = allowBlank;
	}
	/**
	 * @return the blankText
	 */
	public String getBlankText() {
		return blankText;
	}
	/**
	 * @param blankText the blankText to set
	 */
	public void setBlankText(String blankText) {
		this.blankText = blankText;
	}
	/**
	 * @return the disableKeyFilter
	 */
	public String getDisableKeyFilter() {
		return disableKeyFilter;
	}
	/**
	 * @param disableKeyFilter the disableKeyFilter to set
	 */
	public void setDisableKeyFilter(String disableKeyFilter) {
		this.disableKeyFilter = disableKeyFilter;
	}
	/**
	 * @return the emptyClass
	 */
	public String getEmptyClass() {
		return emptyClass;
	}
	/**
	 * @param emptyClass the emptyClass to set
	 */
	public void setEmptyClass(String emptyClass) {
		this.emptyClass = emptyClass;
	}
	/**
	 * @return the emptyText
	 */
	public String getEmptyText() {
		return emptyText;
	}
	/**
	 * @param emptyText the emptyText to set
	 */
	public void setEmptyText(String emptyText) {
		this.emptyText = emptyText;
	}
	/**
	 * @return the grow
	 */
	public String getGrow() {
		return grow;
	}
	/**
	 * @param grow the grow to set
	 */
	public void setGrow(String grow) {
		this.grow = grow;
	}
	/**
	 * @return the growMax
	 */
	public String getGrowMax() {
		return growMax;
	}
	/**
	 * @param growMax the growMax to set
	 */
	public void setGrowMax(String growMax) {
		this.growMax = growMax;
	}
	/**
	 * @return the growMin
	 */
	public String getGrowMin() {
		return growMin;
	}
	/**
	 * @param growMin the growMin to set
	 */
	public void setGrowMin(String growMin) {
		this.growMin = growMin;
	}
	/**
	 * @return the maskRe
	 */
	public String getMaskRe() {
		return maskRe;
	}
	/**
	 * @param maskRe the maskRe to set
	 */
	public void setMaskRe(String maskRe) {
		this.maskRe = maskRe;
	}
	/**
	 * @return the maxLength
	 */
	public String getMaxLength() {
		return maxLength;
	}
	/**
	 * @param maxLength the maxLength to set
	 */
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}
	/**
	 * @return the maxLengthText
	 */
	public String getMaxLengthText() {
		return maxLengthText;
	}
	/**
	 * @param maxLengthText the maxLengthText to set
	 */
	public void setMaxLengthText(String maxLengthText) {
		this.maxLengthText = maxLengthText;
	}
	/**
	 * @return the minLength
	 */
	public String getMinLength() {
		return minLength;
	}
	/**
	 * @param minLength the minLength to set
	 */
	public void setMinLength(String minLength) {
		this.minLength = minLength;
	}
	/**
	 * @return the minLengthText
	 */
	public String getMinLengthText() {
		return minLengthText;
	}
	/**
	 * @param minLengthText the minLengthText to set
	 */
	public void setMinLengthText(String minLengthText) {
		this.minLengthText = minLengthText;
	}
	/**
	 * @return the regex
	 */
	public String getRegex() {
		return regex;
	}
	/**
	 * @param regex the regex to set
	 */
	public void setRegex(String regex) {
		this.regex = regex;
	}
	/**
	 * @return the regexText
	 */
	public String getRegexText() {
		return regexText;
	}
	/**
	 * @param regexText the regexText to set
	 */
	public void setRegexText(String regexText) {
		this.regexText = regexText;
	}
	/**
	 * @return the selectOnFocus
	 */
	public String getSelectOnFocus() {
		return selectOnFocus;
	}
	/**
	 * @param selectOnFocus the selectOnFocus to set
	 */
	public void setSelectOnFocus(String selectOnFocus) {
		this.selectOnFocus = selectOnFocus;
	}
	/**
	 * @return the validator
	 */
	public String getValidator() {
		return validator;
	}
	/**
	 * @param validator the validator to set
	 */
	public void setValidator(String validator) {
		this.validator = validator;
	}
	/**
	 * @return the vtype
	 */
	public String getVtype() {
		return vtype;
	}
	/**
	 * @param vtype the vtype to set
	 */
	public void setVtype(String vtype) {
		this.vtype = vtype;
	}
	/**
	 * @return the vtypeText
	 */
	public String getVtypeText() {
		return vtypeText;
	}
	/**
	 * @param vtypeText the vtypeText to set
	 */
	public void setVtypeText(String vtypeText) {
		this.vtypeText = vtypeText;
	}
	
	
}
