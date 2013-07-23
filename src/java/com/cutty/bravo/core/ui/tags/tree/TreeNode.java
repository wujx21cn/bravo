/* com.cutty.bravo.core.ui.tags.tree.TreeNode.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-1 上午04:19:58, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.tree;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * <p> 自定义TreeNode标签属性集合类 </p>
 * <a href="TreeNode.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class TreeNode implements Serializable{

	private static final long serialVersionUID = 1865327316738888946L;
	private static final Log logger = LogFactory.getLog(TreeNode.class);

	private String allowChildren;
	private String allowDrag ;
	private String allowDrop ;
	private String checked ;
	private String cls ;
	private String disabled ;
	private String draggable ;
	private String expandable ;
	private String expanded ;
	private String href ;
	private String hrefTarget ;
	private String icon ;
	private String iconCls ;
	private String id ;
	private String isTarget ;
	private String leaf ;
	private String qtip ;
	private String qtipCfg ;
	private String singleClickExpand ;
	private String text ;
	private String uiProvider ;
	
	private List<TreeNode> childTreeNodeList;
	
	private String fullEntityName = "";//liangg 090324  for entityOperatePermissAction get entity's full name
	
	public String getFullEntityName() {
		return fullEntityName;
	}
	public void setFullEntityName(String fullEntityName) {
		this.fullEntityName = fullEntityName;
	}
	/**
	 * @return the cls
	 */
	public String getCls() {
		return cls;
	}
	/**
	 * @param cls the cls to set
	 */
	public void setCls(String cls) {
		this.cls = cls;
	}
	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}
	/**
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
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
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the leaf
	 */
	public String getLeaf() {
		return leaf;
	}
	/**
	 * @param leaf the leaf to set
	 */
	public void setLeaf(String leaf) {
		this.leaf = leaf;
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
	public String getAllowChildren() {
		return allowChildren;
	}
	public void setAllowChildren(String allowChildren) {
		this.allowChildren = allowChildren;
	}
	public String getAllowDrag() {
		return allowDrag;
	}
	public void setAllowDrag(String allowDrag) {
		this.allowDrag = allowDrag;
	}
	public String getAllowDrop() {
		return allowDrop;
	}
	public void setAllowDrop(String allowDrop) {
		this.allowDrop = allowDrop;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public String getDraggable() {
		return draggable;
	}
	public void setDraggable(String draggable) {
		this.draggable = draggable;
	}
	public String getExpandable() {
		return expandable;
	}
	public void setExpandable(String expandable) {
		this.expandable = expandable;
	}
	public String getExpanded() {
		return expanded;
	}
	public void setExpanded(String expanded) {
		this.expanded = expanded;
	}
	public String getHrefTarget() {
		return hrefTarget;
	}
	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIsTarget() {
		return isTarget;
	}
	public void setIsTarget(String isTarget) {
		this.isTarget = isTarget;
	}
	public String getQtip() {
		return qtip;
	}
	public void setQtip(String qtip) {
		this.qtip = qtip;
	}
	public String getQtipCfg() {
		return qtipCfg;
	}
	public void setQtipCfg(String qtipCfg) {
		this.qtipCfg = qtipCfg;
	}
	public String getSingleClickExpand() {
		return singleClickExpand;
	}
	public void setSingleClickExpand(String singleClickExpand) {
		this.singleClickExpand = singleClickExpand;
	}
	public String getUiProvider() {
		return uiProvider;
	}
	public void setUiProvider(String uiProvider) {
		this.uiProvider = uiProvider;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public List<TreeNode> getChildTreeNodeList() {
		return childTreeNodeList;
	}
	public void setChildTreeNodeList(List<TreeNode> childTreeNodeList) {
		this.childTreeNodeList = childTreeNodeList;
	}

}
