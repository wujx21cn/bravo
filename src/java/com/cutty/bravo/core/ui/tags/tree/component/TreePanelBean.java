/* com.cutty.bravo.core.ui.tags.container.component.TreePanelBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-31 下午08:18:14, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.tree.component;

import com.cutty.bravo.core.ui.tags.container.component.PanelBean;

/**
 * <p> 自定义TreePanel标签属性集合类 </p>
 * <p>
 * <a href="TreePanelBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class TreePanelBean extends PanelBean{

	private static final long serialVersionUID = -4920999414493934261L;
	

	private String animate ;
	private String containerScroll ;
	private String ddAppendOnly ;
	private String ddGroup ;
	private String ddScroll ;
	private String dragConfig ;
	private String dropConfig ;
	private String enableDD ;
	private String enableDrag ;
	private String enableDrop ;
	private String hlColor ;
	private String hlDrop ;
	private String lines ;
	private String pathSeparator ;
	private String rootVisible = "true";
	private String selModel ;
	private String singleExpand ;
	
	
	
	
	private String dataProxy;
	private String text;
	private String iconCls;
	private String draggable;
	private String nodeId;
	private String contextData;
	
	//2008-11-04 kukuxia.kevin.hw 扩展的带checkbox的树
	private String checkModel;
	private String onlyLeafCheckable;
	private String checkTreeDataProxy;

	private String autoScroll="true";
	
	public String getContextData() {
		return contextData;
	}
	public void setContextData(String contextData) {
		this.contextData = contextData;
	}
	public String getDataProxy() {
		return dataProxy;
	}
	public void setDataProxy(String dataProxy) {
		this.dataProxy = dataProxy;
	}
	@Override
	public String getDraggable() {
		return draggable;
	}
	@Override
	public void setDraggable(String draggable) {
		this.draggable = draggable;
	}
	@Override
	public String getIconCls() {
		return iconCls;
	}
	@Override
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
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
	/**
	 * @return the animate
	 */
	public String getAnimate() {
		return animate;
	}
	/**
	 * @param animate the animate to set
	 */
	public void setAnimate(String animate) {
		this.animate = animate;
	}
	/**
	 * @return the containerScroll
	 */
	public String getContainerScroll() {
		return containerScroll;
	}
	/**
	 * @param containerScroll the containerScroll to set
	 */
	public void setContainerScroll(String containerScroll) {
		this.containerScroll = containerScroll;
	}
	/**
	 * @return the ddAppendOnly
	 */
	public String getDdAppendOnly() {
		return ddAppendOnly;
	}
	/**
	 * @param ddAppendOnly the ddAppendOnly to set
	 */
	public void setDdAppendOnly(String ddAppendOnly) {
		this.ddAppendOnly = ddAppendOnly;
	}
	/**
	 * @return the ddGroup
	 */
	public String getDdGroup() {
		return ddGroup;
	}
	/**
	 * @param ddGroup the ddGroup to set
	 */
	public void setDdGroup(String ddGroup) {
		this.ddGroup = ddGroup;
	}
	/**
	 * @return the ddScroll
	 */
	public String getDdScroll() {
		return ddScroll;
	}
	/**
	 * @param ddScroll the ddScroll to set
	 */
	public void setDdScroll(String ddScroll) {
		this.ddScroll = ddScroll;
	}
	/**
	 * @return the dragConfig
	 */
	public String getDragConfig() {
		return dragConfig;
	}
	/**
	 * @param dragConfig the dragConfig to set
	 */
	public void setDragConfig(String dragConfig) {
		this.dragConfig = dragConfig;
	}
	/**
	 * @return the dropConfig
	 */
	public String getDropConfig() {
		return dropConfig;
	}
	/**
	 * @param dropConfig the dropConfig to set
	 */
	public void setDropConfig(String dropConfig) {
		this.dropConfig = dropConfig;
	}
	/**
	 * @return the enableDD
	 */
	public String getEnableDD() {
		return enableDD;
	}
	/**
	 * @param enableDD the enableDD to set
	 */
	public void setEnableDD(String enableDD) {
		this.enableDD = enableDD;
	}
	/**
	 * @return the enableDrag
	 */
	public String getEnableDrag() {
		return enableDrag;
	}
	/**
	 * @param enableDrag the enableDrag to set
	 */
	public void setEnableDrag(String enableDrag) {
		this.enableDrag = enableDrag;
	}
	/**
	 * @return the enableDrop
	 */
	public String getEnableDrop() {
		return enableDrop;
	}
	/**
	 * @param enableDrop the enableDrop to set
	 */
	public void setEnableDrop(String enableDrop) {
		this.enableDrop = enableDrop;
	}
	/**
	 * @return the hlColor
	 */
	public String getHlColor() {
		return hlColor;
	}
	/**
	 * @param hlColor the hlColor to set
	 */
	public void setHlColor(String hlColor) {
		this.hlColor = hlColor;
	}
	/**
	 * @return the hlDrop
	 */
	public String getHlDrop() {
		return hlDrop;
	}
	/**
	 * @param hlDrop the hlDrop to set
	 */
	public void setHlDrop(String hlDrop) {
		this.hlDrop = hlDrop;
	}
	/**
	 * @return the lines
	 */
	public String getLines() {
		return lines;
	}
	/**
	 * @param lines the lines to set
	 */
	public void setLines(String lines) {
		this.lines = lines;
	}
	/**
	 * @return the pathSeparator
	 */
	public String getPathSeparator() {
		return pathSeparator;
	}
	/**
	 * @param pathSeparator the pathSeparator to set
	 */
	public void setPathSeparator(String pathSeparator) {
		this.pathSeparator = pathSeparator;
	}
	/**
	 * @return the rootVisible
	 */
	public String getRootVisible() {
		return rootVisible;
	}
	/**
	 * @param rootVisible the rootVisible to set
	 */
	public void setRootVisible(String rootVisible) {
		this.rootVisible = rootVisible;
	}
	/**
	 * @return the selModel
	 */
	public String getSelModel() {
		return selModel;
	}
	/**
	 * @param selModel the selModel to set
	 */
	public void setSelModel(String selModel) {
		this.selModel = selModel;
	}
	/**
	 * @return the singleExpand
	 */
	public String getSingleExpand() {
		return singleExpand;
	}
	/**
	 * @param singleExpand the singleExpand to set
	 */
	public void setSingleExpand(String singleExpand) {
		this.singleExpand = singleExpand;
	}
	public String getCheckModel() {
		return checkModel;
	}
	public void setCheckModel(String checkModel) {
		this.checkModel = checkModel;
	}
	public String getOnlyLeafCheckable() {
		return onlyLeafCheckable;
	}
	public void setOnlyLeafCheckable(String onlyLeafCheckable) {
		this.onlyLeafCheckable = onlyLeafCheckable;
	}
	public String getCheckTreeDataProxy() {
		return checkTreeDataProxy;
	}
	public void setCheckTreeDataProxy(String checkTreeDataProxy) {
		this.checkTreeDataProxy = checkTreeDataProxy;
	}

	
	
}
