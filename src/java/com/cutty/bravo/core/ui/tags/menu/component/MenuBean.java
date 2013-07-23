/* com.cutty.bravo.core.ui.tags.menu.component.MenuBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-2 上午01:19:03, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.menu.component;


/**
 *
 * <p> 自定义Menu标签属性集合类 </p>
 * <a href="MenuBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class MenuBean extends BaseItemBean{

	private static final long serialVersionUID = -8564891466048705255L;

	private String href;	
	private String hrefTarget;	
	private String icon;	
	private String iconCls;	
	private String itemCls;	
	private String showDelay;	
	private String text;
	
	private String hidden;
	private String menuId;
	private String addMenuStr;
	
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
	 * @return the hrefTarget
	 */
	public String getHrefTarget() {
		return hrefTarget;
	}
	/**
	 * @param hrefTarget the hrefTarget to set
	 */
	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}
	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
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
	 * @return the itemCls
	 */
	public String getItemCls() {
		return itemCls;
	}
	/**
	 * @param itemCls the itemCls to set
	 */
	public void setItemCls(String itemCls) {
		this.itemCls = itemCls;
	}
	/**
	 * @return the showDelay
	 */
	public String getShowDelay() {
		return showDelay;
	}
	/**
	 * @param showDelay the showDelay to set
	 */
	public void setShowDelay(String showDelay) {
		this.showDelay = showDelay;
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
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}
	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	/**
	 * @return the hidden
	 */
	public String getHidden() {
		return hidden;
	}
	/**
	 * @param hidden the hidden to set
	 */
	public void setHidden(String hidden) {
		this.hidden = hidden;
	}
	/**
	 * @return the addMenuStr
	 */
	public String getAddMenuStr() {
		return addMenuStr;
	}
	/**
	 * @param addMenuStr the addMenuStr to set
	 */
	public void setAddMenuStr(String addMenuStr) {
		this.addMenuStr = addMenuStr;
	}
	
}
