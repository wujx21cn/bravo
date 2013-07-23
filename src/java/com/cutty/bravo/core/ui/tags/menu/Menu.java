/* com.cutty.bravo.core.ui.tags.menu.Menu.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-2 上午01:18:32, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.menu;

import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;

import com.cutty.bravo.components.common.manager.MenuFunctionManager;
import com.cutty.bravo.core.ui.tags.BaseTag;
import com.cutty.bravo.core.ui.tags.menu.component.MenuBean;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;

/**
 * <p> 自定义EXT Menu标签类 </p>
 * <p>
 * <a href="Menu.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class Menu extends BaseTag{

	private static final long serialVersionUID = 1567160988617028792L;

	/**
	 * 根据用户ID加载相应的菜单项到控件的addMenuStr属性中去
	 */
	@Override
	public int doEndTag() throws JspException {
		MenuBean menuBean = (MenuBean)this.getComponent();
		String buttonId = menuBean.getName();
		String menuId = menuBean.getMenuId();
		MenuFunctionManager menuFunctionManager = (MenuFunctionManager)ApplicationContextKeeper.getAppCtx().getBean("menuFunctionManager");
		
		if( StringUtils.isNotEmpty(menuId) && null != menuFunctionManager.get(Long.valueOf(menuId)) ){
			//调用MenuNode的构造函数，递归构造Menu树
			MenuNode menuNodeRoot = new MenuNode(menuId,menuFunctionManager);
			StringBuffer sb = new StringBuffer();
			List<MenuNode> childMenuNodeList = menuNodeRoot.getChildMenuNodeList();
			Iterator<MenuNode> childMenuNodeIT = childMenuNodeList.iterator();
			while (childMenuNodeIT.hasNext()){
				MenuNode menuNode = childMenuNodeIT.next();
				String menuNodeText = menuNode.getText();
				String menuNodeId = "menuFunction" + menuNode.getId();
				String menuNodeIconCls = menuNode.getIconCls();
				sb.append("var ").append(menuNodeId).append(" = new Ext.menu.Item({ id:'").append(menuNodeId).append("'");
				sb.append(",text:'").append(menuNodeText).append("'");
				sb.append(",iconCls:'").append(menuNodeIconCls).append("'");
				if( 0 == menuNode.getChildMenuNodeList().size() ){
					sb.append(",href:\"").append(menuNode.getHref()).append("\"});");
				}
				else
				{
					//递归添加叶子节点			
					sb.append(buildMenuLeaf(menuNode));
				}
				//将组装好的菜单项添加到菜单按钮上			
				sb.append(buttonId).append(".menu.add(").append(menuNodeId).append(");");
			}
			//如果有加载菜单项则显示该菜单按钮，初始时是隐藏的
			if( 0 < childMenuNodeList.size() ) sb.append(buttonId).append(".setVisible(true);");
			
			menuBean.setAddMenuStr(sb.toString());
		}
		
		return super.doEndTag();
	}

	/**
	 * 为菜单按钮递归组装菜单项时调用，用于生成菜单项的javascript代码
	 * @param menuNode 菜单项
	 * @return 组装好的javascript代码
	 */
	public String buildMenuLeaf(MenuNode menuNode){
		if( 0 == menuNode.getChildMenuNodeList().size() ){
			return ",{text:'" + menuNode.getText() + "', iconCls:'"+ menuNode.getIconCls() + "', href:'" + menuNode.getHref() + "'}";
		}
		else
		{
			StringBuffer sb = new StringBuffer();
			sb.append(",menu:{items:[");			
			List<MenuNode> grandChildMenuNodeList = menuNode.getChildMenuNodeList();
			Iterator<MenuNode> grandChildMenuNodeIT = grandChildMenuNodeList.iterator();
			MenuNode menuLeaf = grandChildMenuNodeIT.next();
			sb.append("{text:'").append(menuLeaf.getText()).append("',iconCls:'").append(menuLeaf.getIconCls()).append("',href:\"").append(menuLeaf.getHref()).append("\"}");
			
			while (grandChildMenuNodeIT.hasNext()){
				menuLeaf = grandChildMenuNodeIT.next();
				sb.append(buildMenuLeaf(menuLeaf));
			}			
			sb.append("]}});");
			
			return sb.toString();
		}
	}
	
}
