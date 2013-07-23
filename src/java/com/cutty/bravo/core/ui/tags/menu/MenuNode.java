package com.cutty.bravo.core.ui.tags.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cutty.bravo.components.common.domain.MenuFunction;
import com.cutty.bravo.components.common.manager.MenuFunctionManager;

/**
 *
 * <p>
 * <a href="MenuNode.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:YeonOrchid@gmail.com">Yeon</a>
 */
public class MenuNode implements Serializable {

	private static final long serialVersionUID = 4496374869235727065L;
	
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
	
	private List<MenuNode> childMenuNodeList = new ArrayList<MenuNode>();
	
	public MenuNode(String menuId, MenuFunctionManager menuFunctionManager){
		MenuFunction menuFunction = menuFunctionManager.get(Long.valueOf(menuId));
		this.setId(menuId);
		this.setText(menuFunction.getName());		
		List<MenuFunction> childList = menuFunctionManager.getChildMenu(Long.valueOf(menuId));
		if (childList.size()>0){
			for(int i=0;i<childList.size();i++)
			{
				MenuFunction childValue = childList.get(i);
				this.setIconCls(childValue.getIconSrc());
				MenuNode childMenuNode = new MenuNode(childValue.getId().toString(),menuFunctionManager);
				if("false".equals(childValue.getChecked())) continue;
				this.childMenuNodeList.add(childMenuNode);
			}
		}
		else
		{   this.setIconCls(menuFunction.getIconSrc());
			this.setLeaf("true");
			this.setHref(menuFunction.getAction());
		}
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

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
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

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
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

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsTarget() {
		return isTarget;
	}

	public void setIsTarget(String isTarget) {
		this.isTarget = isTarget;
	}

	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUiProvider() {
		return uiProvider;
	}

	public void setUiProvider(String uiProvider) {
		this.uiProvider = uiProvider;
	}

	public List<MenuNode> getChildMenuNodeList() {
		return childMenuNodeList;
	}

	public void setChildMenuNodeList(List<MenuNode> childMenuNodeList) {
		this.childMenuNodeList = childMenuNodeList;
	}
}
