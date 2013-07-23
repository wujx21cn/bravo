/* com.cutty.bravo.core.ui.bean.view.PanelBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-23 下午11:35:49, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.container.component;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> EXT容器标签Panel 属性集合类 </p>
 * <p>
 * <a href="PanelBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class PanelBean extends Container{

	private static final long serialVersionUID = -8930673699618733025L;
	private String animCollapse;
	private String applyTo;
	private String autoLoad;
	private String autoScroll;
	private String baseCls;
	private String bbar; 
	private String bodyBorder; 
	private String bodyStyle; 
	private String border; 
	private String buttonAlign; 
	private String buttons; 
	private String collapseFirst; 
	private String collapsed; 
	private String collapsedCls; 
	private String collapsible; 
	private String contentEl; 
	private String draggable; 
	private String elements; 
	private String floating ; 
	private String footer; 
	private String frame; 
	private String header; 
	private String headerAsText; 
	private String hideCollapseTool ; 
	private String html; 
	private String iconCls; 
	private String keys; 
	private String maskDisabled; 
	private String minButtonWidth; 
	private String shadow; 
	private String shadowOffset; 
	private String shim; 
	private String tbar; 
	private String title; 
	private String titleCollapse; 
	private String tools; 
	//kukuxia.kevin.hw 2008-11-08
	//使该控件在viewPort的layout=border的布局下可以使实现拖拉（继承于Panel的子类控件，例如TablePanel,FormPanel,TreePanel,FieldSet,GridPanel.....皆可）
	private String split; 
	
	//扩展属性，所有BUTTON组件均注册到该属性中。
	private List<String> childButtonNames = new ArrayList<String>();
	
	/**
	 * @return the animCollapse
	 */
	public String getAnimCollapse() {
		
		return animCollapse;
	}
	/**
	 * @param animCollapse the animCollapse to set
	 */
	public void setAnimCollapse(String animCollapse) {
		this.animCollapse = animCollapse;
	}
	/**
	 * @return the applyTo
	 */
	@Override
	public String getApplyTo() {
		return applyTo;
	}
	/**
	 * @param applyTo the applyTo to set
	 */
	@Override
	public void setApplyTo(String applyTo) {
		this.applyTo = applyTo;
	}
	/**
	 * @return the autoLoad
	 */
	public String getAutoLoad() {
		return autoLoad;
	}
	/**
	 * @param autoLoad the autoLoad to set
	 */
	public void setAutoLoad(String autoLoad) {
		this.autoLoad = autoLoad;
	}
	/**
	 * @return the autoScroll
	 */
	public String getAutoScroll() {
		return autoScroll;
	}
	/**
	 * @param autoScroll the autoScroll to set
	 */
	public void setAutoScroll(String autoScroll) {
		this.autoScroll = autoScroll;
	}
	/**
	 * @return the baseCls
	 */
	public String getBaseCls() {
		return baseCls;
	}
	/**
	 * @param baseCls the baseCls to set
	 */
	public void setBaseCls(String baseCls) {
		this.baseCls = baseCls;
	}
	/**
	 * @return the bbar
	 */
	public String getBbar() {
		return bbar;
	}
	/**
	 * @param bbar the bbar to set
	 */
	public void setBbar(String bbar) {
		this.bbar = bbar;
	}
	/**
	 * @return the bodyBorder
	 */
	public String getBodyBorder() {
		return bodyBorder;
	}
	/**
	 * @param bodyBorder the bodyBorder to set
	 */
	public void setBodyBorder(String bodyBorder) {
		this.bodyBorder = bodyBorder;
	}
	/**
	 * @return the bodyStyle
	 */
	public String getBodyStyle() {
		return bodyStyle;
	}
	/**
	 * @param bodyStyle the bodyStyle to set
	 */
	public void setBodyStyle(String bodyStyle) {
		this.bodyStyle = bodyStyle;
	}
	/**
	 * @return the border
	 */
	public String getBorder() {
		return border;
	}
	/**
	 * @param border the border to set
	 */
	public void setBorder(String border) {
		this.border = border;
	}
	/**
	 * @return the buttonAlign
	 */
	public String getButtonAlign() {
		return buttonAlign;
	}
	/**
	 * @param buttonAlign the buttonAlign to set
	 */
	public void setButtonAlign(String buttonAlign) {
		this.buttonAlign = buttonAlign;
	}
	/**
	 * @return the buttons
	 */
	public String getButtons() {
		return buttons;
	}
	/**
	 * @param buttons the buttons to set
	 */
	public void setButtons(String buttons) {
		this.buttons = buttons;
	}
	/**
	 * @return the collapsed
	 */
	public String getCollapsed() {
		return collapsed;
	}
	/**
	 * @param collapsed the collapsed to set
	 */
	public void setCollapsed(String collapsed) {
		this.collapsed = collapsed;
	}
	/**
	 * @return the collapsedCls
	 */
	public String getCollapsedCls() {
		return collapsedCls;
	}
	/**
	 * @param collapsedCls the collapsedCls to set
	 */
	public void setCollapsedCls(String collapsedCls) {
		this.collapsedCls = collapsedCls;
	}
	/**
	 * @return the collapseFirst
	 */
	public String getCollapseFirst() {
		return collapseFirst;
	}
	/**
	 * @param collapseFirst the collapseFirst to set
	 */
	public void setCollapseFirst(String collapseFirst) {
		this.collapseFirst = collapseFirst;
	}
	/**
	 * @return the collapsible
	 */
	public String getCollapsible() {
		return collapsible;
	}
	/**
	 * @param collapsible the collapsible to set
	 */
	public void setCollapsible(String collapsible) {
		this.collapsible = collapsible;
	}
	/**
	 * @return the draggable
	 */
	public String getDraggable() {
		return draggable;
	}
	/**
	 * @param draggable the draggable to set
	 */
	public void setDraggable(String draggable) {
		this.draggable = draggable;
	}
	/**
	 * @return the elements
	 */
	public String getElements() {
		return elements;
	}
	/**
	 * @param elements the elements to set
	 */
	public void setElements(String elements) {
		this.elements = elements;
	}
	/**
	 * @return the floating
	 */
	public String getFloating() {
		return floating;
	}
	/**
	 * @param floating the floating to set
	 */
	public void setFloating(String floating) {
		this.floating = floating;
	}
	/**
	 * @return the footer
	 */
	public String getFooter() {
		return footer;
	}
	/**
	 * @param footer the footer to set
	 */
	public void setFooter(String footer) {
		this.footer = footer;
	}
	/**
	 * @return the frame
	 */
	public String getFrame() {
		return frame;
	}
	/**
	 * @param frame the frame to set
	 */
	public void setFrame(String frame) {
		this.frame = frame;
	}
	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}
	/**
	 * @param header the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}
	/**
	 * @return the headerAsText
	 */
	public String getHeaderAsText() {
		return headerAsText;
	}
	/**
	 * @param headerAsText the headerAsText to set
	 */
	public void setHeaderAsText(String headerAsText) {
		this.headerAsText = headerAsText;
	}
	/**
	 * @return the hideCollapseTool
	 */
	public String getHideCollapseTool() {
		return hideCollapseTool;
	}
	/**
	 * @param hideCollapseTool the hideCollapseTool to set
	 */
	public void setHideCollapseTool(String hideCollapseTool) {
		this.hideCollapseTool = hideCollapseTool;
	}
	/**
	 * @return the html
	 */
	public String getHtml() {
		return html;
	}
	/**
	 * @param html the html to set
	 */
	public void setHtml(String html) {
		this.html = html;
	}
	/**
	 * @return the iconCls
	 */
	public String getIconCls() {
		return iconCls;
	}
	/**
	 * @param iconCls the iconCls to set
	 */
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	/**
	 * @return the keys
	 */
	public String getKeys() {
		return keys;
	}
	/**
	 * @param keys the keys to set
	 */
	public void setKeys(String keys) {
		this.keys = keys;
	}
	/**
	 * @return the maskDisabled
	 */
	public String getMaskDisabled() {
		return maskDisabled;
	}
	/**
	 * @param maskDisabled the maskDisabled to set
	 */
	public void setMaskDisabled(String maskDisabled) {
		this.maskDisabled = maskDisabled;
	}
	/**
	 * @return the minButtonWidth
	 */
	public String getMinButtonWidth() {
		return minButtonWidth;
	}
	/**
	 * @param minButtonWidth the minButtonWidth to set
	 */
	public void setMinButtonWidth(String minButtonWidth) {
		this.minButtonWidth = minButtonWidth;
	}
	/**
	 * @return the shadow
	 */
	public String getShadow() {
		return shadow;
	}
	/**
	 * @param shadow the shadow to set
	 */
	public void setShadow(String shadow) {
		this.shadow = shadow;
	}
	/**
	 * @return the shadowOffset
	 */
	public String getShadowOffset() {
		return shadowOffset;
	}
	/**
	 * @param shadowOffset the shadowOffset to set
	 */
	public void setShadowOffset(String shadowOffset) {
		this.shadowOffset = shadowOffset;
	}
	/**
	 * @return the shim
	 */
	public String getShim() {
		return shim;
	}
	/**
	 * @param shim the shim to set
	 */
	public void setShim(String shim) {
		this.shim = shim;
	}
	/**
	 * @return the tbar
	 */
	public String getTbar() {
		return tbar;
	}
	/**
	 * @param tbar the tbar to set
	 */
	public void setTbar(String tbar) {
		this.tbar = tbar;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the titleCollapse
	 */
	public String getTitleCollapse() {
		return titleCollapse;
	}
	/**
	 * @param titleCollapse the titleCollapse to set
	 */
	public void setTitleCollapse(String titleCollapse) {
		this.titleCollapse = titleCollapse;
	}
	/**
	 * @return the tools
	 */
	public String getTools() {
		return tools;
	}
	/**
	 * @param tools the tools to set
	 */
	public void setTools(String tools) {
		this.tools = tools;
	}
	/**
	 * @return the childButtonNames
	 */
	public List<String> getChildButtonNames() {
		return childButtonNames;
	}
	/**
	 * @param childButtonNames the childButtonNames to set
	 */
	public void setChildButtonNames(List<String> childButtonNames) {
		this.childButtonNames = childButtonNames;
	}
	/**
	 * @return the contentEl
	 */
	public String getContentEl() {
		return contentEl;
	}
	/**
	 * @param contentEl the contentEl to set
	 */
	public void setContentEl(String contentEl) {
		this.contentEl = contentEl;
	}
	public String getSplit() {
		return split;
	}
	public void setSplit(String split) {
		this.split = split;
	} 
	
	
}
