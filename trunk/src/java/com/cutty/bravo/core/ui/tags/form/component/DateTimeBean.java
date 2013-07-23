/* com.cutty.bravo.core.ui.tags.form.component.DateTimeBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-12-18 下午09:13:52, Created by Yeon
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.form.component;

/**
 *
 * <p>
 * <a href="DateTimeBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:YeonOrchid@gmail.com">Yeon</a>
 */
public class DateTimeBean extends DateFieldBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5636799007897929338L;
	String format = "Y-m-d H:i:s"; //以免开发者没写format时出现只有日期没有时间的情况
	private String linkAgeField;//联动属性
	
	public String getLinkAgeField() {
		return linkAgeField;
	}
	public void setLinkAgeField(String linkAgeField) {
		this.linkAgeField = linkAgeField;
	}
	
	@Override
	public String getFormat() {
		return format;
	}
	@Override
	public void setFormat(String format) {
		this.format = format;
	}
	
}
