/* com.cutty.bravo.core.ui.bean.NumberFieldBean.java

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
 * <p> EXT标签NumberField属性集合类 </p>
 * <p>
 * <a href="NumberFieldBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:linjuan0125@gmail.com">Cathy.Lin</a>
 */
public class NumberFieldBean extends TextFieldBean{
	private static final long serialVersionUID = -7840016045252592958L;
	private String allowDecimals;
	private String allowNegative ;
	private String baseChars ;
	private String decimalPrecision ;
	private String decimalSeparator ;
	private String fieldClass ;
	private String maxText ;
	private String maxValuel ;
	private String minText ;
	private String minValue ;
	private String nanText ;
	/**
	 * @return the allowDecimals
	 */
	public String getAllowDecimals() {
		return allowDecimals;
	}
	/**
	 * @param allowDecimals the allowDecimals to set
	 */
	public void setAllowDecimals(String allowDecimals) {
		this.allowDecimals = allowDecimals;
	}
	/**
	 * @return the allowNegative
	 */
	public String getAllowNegative() {
		return allowNegative;
	}
	/**
	 * @param allowNegative the allowNegative to set
	 */
	public void setAllowNegative(String allowNegative) {
		this.allowNegative = allowNegative;
	}
	/**
	 * @return the baseChars
	 */
	public String getBaseChars() {
		return baseChars;
	}
	/**
	 * @param baseChars the baseChars to set
	 */
	public void setBaseChars(String baseChars) {
		this.baseChars = baseChars;
	}
	/**
	 * @return the decimalPrecision
	 */
	public String getDecimalPrecision() {
		return decimalPrecision;
	}
	/**
	 * @param decimalPrecision the decimalPrecision to set
	 */
	public void setDecimalPrecision(String decimalPrecision) {
		this.decimalPrecision = decimalPrecision;
	}
	/**
	 * @return the decimalSeparator
	 */
	public String getDecimalSeparator() {
		return decimalSeparator;
	}
	/**
	 * @param decimalSeparator the decimalSeparator to set
	 */
	public void setDecimalSeparator(String decimalSeparator) {
		this.decimalSeparator = decimalSeparator;
	}
	/**
	 * @return the fieldClass
	 */
	@Override
	public String getFieldClass() {
		return fieldClass;
	}
	/**
	 * @param fieldClass the fieldClass to set
	 */
	@Override
	public void setFieldClass(String fieldClass) {
		this.fieldClass = fieldClass;
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
	/**
	 * @return the nanText
	 */
	public String getNanText() {
		return nanText;
	}
	/**
	 * @param nanText the nanText to set
	 */
	public void setNanText(String nanText) {
		this.nanText = nanText;
	}
	
	
	
}
