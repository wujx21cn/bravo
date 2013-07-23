/* com.cutty.bravo.core.ui.bean.DateFieldBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-22 上午02:29:19, Created by Cathy.Lin
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.form.component;

/**
 * <p> EXT标签DateField属性集合类 </p>
 * <p>
 * <a href="DateFieldBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:linjuan0125@gmail.com">Cathy.Lin</a>
 */
public class DateFieldBean extends TriggerFieldBean{

	private static final long serialVersionUID = 1669866305008967681L;
	private String altFormats;
	private String autoCreate ;
	private String disabledDates ;
	private String disabledDatesText ;
	private String disableDays ;
	private String disabledDaysText ;
	private String format="Y-m-d" ;
	private String invalidText ;
	private String maxText ;
	private String maxValue ;
	private String minText ;
	private String minValue;
	private String triggerClass;
	private String startDateField;
	private String endDateField;
	private String linkAgeField;//联动属性
	
	public String getLinkAgeField() {
		return linkAgeField;
	}
	public void setLinkAgeField(String linkAgeField) {
		this.linkAgeField = linkAgeField;
	}
	/**
	 * @return the altFormats
	 */
	public String getAltFormats() {
		return altFormats;
	}
	/**
	 * @param altFormats the altFormats to set
	 */
	public void setAltFormats(String altFormats) {
		this.altFormats = altFormats;
	}
	/**
	 * @return the autoCreate
	 */
	@Override
	public String getAutoCreate() {
		return autoCreate;
	}
	/**
	 * @param autoCreate the autoCreate to set
	 */
	@Override
	public void setAutoCreate(String autoCreate) {
		this.autoCreate = autoCreate;
	}
	/**
	 * @return the disabledDates
	 */
	public String getDisabledDates() {
		return disabledDates;
	}
	/**
	 * @param disabledDates the disabledDates to set
	 */
	public void setDisabledDates(String disabledDates) {
		this.disabledDates = disabledDates;
	}
	/**
	 * @return the disabledDatesText
	 */
	public String getDisabledDatesText() {
		return disabledDatesText;
	}
	/**
	 * @param disabledDatesText the disabledDatesText to set
	 */
	public void setDisabledDatesText(String disabledDatesText) {
		this.disabledDatesText = disabledDatesText;
	}
	/**
	 * @return the disableDays
	 */
	public String getDisableDays() {
		return disableDays;
	}
	/**
	 * @param disableDays the disableDays to set
	 */
	public void setDisableDays(String disableDays) {
		this.disableDays = disableDays;
	}
	/**
	 * @return the disabledDaysText
	 */
	public String getDisabledDaysText() {
		return disabledDaysText;
	}
	/**
	 * @param disabledDaysText the disabledDaysText to set
	 */
	public void setDisabledDaysText(String disabledDaysText) {
		this.disabledDaysText = disabledDaysText;
	}
	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}
	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	/**
	 * @return the invalidText
	 */
	@Override
	public String getInvalidText() {
		return invalidText;
	}
	/**
	 * @param invalidText the invalidText to set
	 */
	@Override
	public void setInvalidText(String invalidText) {
		this.invalidText = invalidText;
	}
	/**
	 * @return the maxText
	 */
	public String getMaxText() {
		return maxText;
	}
	/**
	 * @param maxText the maxText to set
	 */
	public void setMaxText(String maxText) {
		this.maxText = maxText;
	}
	/**
	 * @return the maxValue
	 */
	public String getMaxValue() {
		return maxValue;
	}
	/**
	 * @param maxValue the maxValue to set
	 */
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	/**
	 * @return the minText
	 */
	public String getMinText() {
		return minText;
	}
	/**
	 * @param minText the minText to set
	 */
	public void setMinText(String minText) {
		this.minText = minText;
	}
	/**
	 * @return the minValue
	 */
	public String getMinValue() {
		return minValue;
	}
	/**
	 * @param minValue the minValue to set
	 */
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	/**
	 * @return the triggerClass
	 */
	@Override
	public String getTriggerClass() {
		return triggerClass;
	}
	/**
	 * @param triggerClass the triggerClass to set
	 */
	@Override
	public void setTriggerClass(String triggerClass) {
		this.triggerClass = triggerClass;
	}
	public String getStartDateField() {
		return startDateField;
	}
	public void setStartDateField(String startDateField) {
		this.startDateField = startDateField;
	}
	public String getEndDateField() {
		return endDateField;
	}
	public void setEndDateField(String endDateField) {
		this.endDateField = endDateField;
	}
	
	
	
}
