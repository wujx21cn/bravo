/* com.cutty.bravo.core.ui.bean.HtmlEditorBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-22 上午02:29:19, Created by Cathy.Lin
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.form.component;

import org.apache.commons.lang.StringUtils;


/**
 * <p> EXT标签HtmlEditor属性集合类 </p>
 * <p>
 * <a href="HtmlEditorBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:linjuan0125@gmail.com">Cathy.Lin</a>
 */
public class HtmlEditorBean extends FieldBean{

	private static final long serialVersionUID = -3732193482804531552L;
	private String createLinkText;
	private String defaultLinkValue ;
	private String enableAlignments;
	private String enableColors ;
	private String enableFont ;
	private String enableFontSize ;
	private String enableFormat ;
	private String enableLinks ;
	private String enableLists ;
	private String enableSourceEdit ;
	private String fontFamilies ;
	private String value ;
	/**
	 * @return the createLinkText
	 */
	public String getCreateLinkText() {
		return createLinkText;
	}
	/**
	 * @param createLinkText the createLinkText to set
	 */
	public void setCreateLinkText(String createLinkText) {
		this.createLinkText = createLinkText;
	}
	/**
	 * @return the defaultLinkValue
	 */
	public String getDefaultLinkValue() {
		return defaultLinkValue;
	}
	/**
	 * @param defaultLinkValue the defaultLinkValue to set
	 */
	public void setDefaultLinkValue(String defaultLinkValue) {
		this.defaultLinkValue = defaultLinkValue;
	}
	/**
	 * @return the enableAlignments
	 */
	public String getEnableAlignments() {
		return enableAlignments;
	}
	/**
	 * @param enableAlignments the enableAlignments to set
	 */
	public void setEnableAlignments(String enableAlignments) {
		this.enableAlignments = enableAlignments;
	}
	/**
	 * @return the enableColors
	 */
	public String getEnableColors() {
		return enableColors;
	}
	/**
	 * @param enableColors the enableColors to set
	 */
	public void setEnableColors(String enableColors) {
		this.enableColors = enableColors;
	}
	/**
	 * @return the enableFont
	 */
	public String getEnableFont() {
		return enableFont;
	}
	/**
	 * @param enableFont the enableFont to set
	 */
	public void setEnableFont(String enableFont) {
		this.enableFont = enableFont;
	}
	/**
	 * @return the enableFontSize
	 */
	public String getEnableFontSize() {
		return enableFontSize;
	}
	/**
	 * @param enableFontSize the enableFontSize to set
	 */
	public void setEnableFontSize(String enableFontSize) {
		this.enableFontSize = enableFontSize;
	}
	/**
	 * @return the enableFormat
	 */
	public String getEnableFormat() {
		return enableFormat;
	}
	/**
	 * @param enableFormat the enableFormat to set
	 */
	public void setEnableFormat(String enableFormat) {
		this.enableFormat = enableFormat;
	}
	/**
	 * @return the enableLinks
	 */
	public String getEnableLinks() {
		return enableLinks;
	}
	/**
	 * @param enableLinks the enableLinks to set
	 */
	public void setEnableLinks(String enableLinks) {
		this.enableLinks = enableLinks;
	}
	/**
	 * @return the enableLists
	 */
	public String getEnableLists() {
		return enableLists;
	}
	/**
	 * @param enableLists the enableLists to set
	 */
	public void setEnableLists(String enableLists) {
		this.enableLists = enableLists;
	}
	/**
	 * @return the enableSourceEdit
	 */
	public String getEnableSourceEdit() {
		return enableSourceEdit;
	}
	/**
	 * @param enableSourceEdit the enableSourceEdit to set
	 */
	public void setEnableSourceEdit(String enableSourceEdit) {
		this.enableSourceEdit = enableSourceEdit;
	}
	/**
	 * @return the fontFamilies
	 */
	public String getFontFamilies() {
		return fontFamilies;
	}
	/**
	 * @param fontFamilies the fontFamilies to set
	 */
	public void setFontFamilies(String fontFamilies) {
		this.fontFamilies = fontFamilies;
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
