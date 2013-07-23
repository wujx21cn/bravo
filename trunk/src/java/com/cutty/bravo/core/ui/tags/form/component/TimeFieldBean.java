/* com.cutty.bravo.core.ui.bean.TimeFieldBean.java

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
 * <p> EXT标签TimeField属性集合类 </p>
 * <p>
 * <a href="TimeFieldBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:linjuan0125@gmail.com">Cathy Lin</a>
 */
public class TimeFieldBean extends ComboBoxBean{

	private static final long serialVersionUID = -6945327946958346912L;
	private String altFormats;
	private String format ;
	private String increment ;
	private String invalidText ;
	private String maxText ;
	private String maxValuel ;
	private String minText ;
	private String minValue ;
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
	 * @return the increment
	 */
	public String getIncrement() {
		return increment;
	}
	/**
	 * @param increment the increment to set
	 */
	public void setIncrement(String increment) {
		this.increment = increment;
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
	 * @return the maxValuel
	 */
	public String getMaxValuel() {
		return maxValuel;
	}
	/**
	 * @param maxValuel the maxValuel to set
	 */
	public void setMaxValuel(String maxValuel) {
		this.maxValuel = maxValuel;
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

	
	
	
}
