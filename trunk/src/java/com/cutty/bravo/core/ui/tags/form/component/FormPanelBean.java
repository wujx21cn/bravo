/* com.cutty.bravo.core.ui.tags.form.component.FormPanelBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-3 下午08:33:59, Created by Jason.Wu
        2008-09-24 kukuxia.hw 移除"ajaxPass"字段
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.form.component;

import java.util.ArrayList;
import java.util.List;

import com.cutty.bravo.core.ui.tags.container.component.PanelBean;

/**
 * <p> EXT标签FormPanel属性集合类 </p>
 * <p>
 * <a href="FormPanelBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 * 
 */
public class FormPanelBean  extends PanelBean{
	private static final long serialVersionUID = -8070937804873639085L;

	private String itemCls ;
	
	//所有标题默认为左对齐
	private String labelAlign="left" ;
	//标题默认长度为四个汉字宽,
	private String labelWidth ="55";
	private String monitorPoll ;
	private String monitorValid ;
    private String border="false";
    private String autoScroll="true";

	
	private String frame="true";

	//重载width属性,设置默认值为935
	private String width="822";
	
	//扩展属性,数据提交的路径
	private String dataProxy;
	
	//扩展属性，所有fieldSet组件均注册到该属性中。
	private List<String> childfieldSetNames = new ArrayList<String>();
	
	//扩展属性，所有formSet组件均注册到该属性中。
	private List<String> childFormSetNames = new ArrayList<String>();
	
	public List<String> getChildFormSetNames() {
		return childFormSetNames;
	}
	public void setChildFormSetNames(List<String> childFormSetNames) {
		this.childFormSetNames = childFormSetNames;
	}
	public String getItemCls() {
		return itemCls;
	}
	public void setItemCls(String itemCls) {
		this.itemCls = itemCls;
	}
	public String getLabelAlign() {
		return labelAlign;
	}
	public void setLabelAlign(String labelAlign) {
		this.labelAlign = labelAlign;
	}
	public String getLabelWidth() {
		return labelWidth;
	}
	public void setLabelWidth(String labelWidth) {
		this.labelWidth = labelWidth;
	}

	public String getMonitorPoll() {
		return monitorPoll;
	}
	public void setMonitorPoll(String monitorPoll) {
		this.monitorPoll = monitorPoll;
	}
	public String getMonitorValid() {
		return monitorValid;
	}
	public void setMonitorValid(String monitorValid) {
		this.monitorValid = monitorValid;
	}

	public String getDataProxy() {
		return dataProxy;
	}
	public void setDataProxy(String dataProxy) {
		this.dataProxy = dataProxy;
	}
	/**
	 * @return the childfieldSetNames
	 */
	public List<String> getChildfieldSetNames() {
		return childfieldSetNames;
	}
	/**
	 * @param childfieldSetNames the childfieldSetNames to set
	 */
	public void setChildfieldSetNames(List<String> childfieldSetNames) {
		this.childfieldSetNames = childfieldSetNames;
	}
	/**
	 * @return the frame
	 */
	@Override
	public String getFrame() {
		return frame;
	}
	/**
	 * @param frame the frame to set
	 */
	@Override
	public void setFrame(String frame) {
		this.frame = frame;
	}
	/**
	 * @return the width
	 */
	@Override
	public String getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	@Override
	public void setWidth(String width) {
		this.width = width;
	}
	/**
	 * @return the border
	 */
	@Override
	public String getBorder() {
		return border;
	}
	/**
	 * @param border the border to set
	 */
	@Override
	public void setBorder(String border) {
		this.border = border;
	}
	/**
	 * @return the autoScroll
	 */
	@Override
	public String getAutoScroll() {
		return autoScroll;
	}
	/**
	 * @param autoScroll the autoScroll to set
	 */
	@Override
	public void setAutoScroll(String autoScroll) {
		this.autoScroll = autoScroll;
	}


	
}
