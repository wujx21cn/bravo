/* com.cutty.bravo.components.common.web.MenuFunctionAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-21 上午08:11:45, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.components.common.domain.MenuFunction;
import com.cutty.bravo.components.common.manager.MenuFunctionManager;
import com.cutty.bravo.core.ui.tags.tree.TreeNode;
import com.cutty.bravo.core.web.handler.RequestHandler;
import com.cutty.bravo.core.web.struts2.EntityAction;

/**
 *
 * <p>
 * <a href="MenuFunctionAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Namespace("/common")   
@ParentPackage("bravo")
public class MenuFunctionAction  extends EntityAction<MenuFunction>{

	private static final long serialVersionUID = 2118351277267815990L;
	private MenuFunctionManager menuFunctionManager;
	
	public String viewTree() {
		String node = ServletActionContext.getRequest().getParameter("node");
		String setNodeHref = "yes";
		if(ServletActionContext.getRequest().getParameter("setNodeHref")!=null&&
		   !ServletActionContext.getRequest().getParameter("setNodeHref").equals("")){
			setNodeHref = ServletActionContext.getRequest().getParameter("setNodeHref");
		}
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		List<MenuFunction> childList = menuFunctionManager.getChildMenu(Long.valueOf(node));		
		if (StringUtils.isNotEmpty(node) &&childList.size()>0){
			for(int i=0;i<childList.size();i++)
			{
				MenuFunction childValue = childList.get(i);
				TreeNode nodeValue = new TreeNode();
				nodeValue.setIconCls(childValue.getIconSrc());
				if(menuFunctionManager.getChildMenu(childValue.getId()).size()==0)
				{
					nodeValue.setIconCls(childValue.getIconSrc());
					nodeValue.setLeaf("true");
					if("yes".equals(setNodeHref))
					nodeValue.setHref(childValue.getAction());
					
				}
				else
				{
					nodeValue.setIconCls("icon-pkg");					
				}
				nodeValue.setId(childValue.getId().toString());
				nodeValue.setText(childValue.getName());
				nodeValue.setCls("cls");
				if("false".equals(childValue.getChecked())) continue;
				treeList.add(nodeValue);
			}
		}
		ServletActionContext.getRequest().setAttribute("treeData",treeList);
		return "jsonDataRenderChain";
	}
	
	public String viewCombobox() {
		String query = ServletActionContext.getRequest().getParameter("query");
		List  treeList = menuFunctionManager.getAll();
		ServletActionContext.getRequest().setAttribute("menuData",treeList);
		return "jsonDataRenderChain";
	}

	public String viewCheckedTree(){
		
		Set<String> sss = RequestHandler.getContextRequestHandler().getCurrentUserAutorities();
		String node = ServletActionContext.getRequest().getParameter("node");
		String permisId = ServletActionContext.getRequest().getParameter("permisId");
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		List<MenuFunction> childList = menuFunctionManager.getChildForPermisMenuTree(Long.valueOf(node),Long.valueOf(permisId));		
		if (StringUtils.isNotEmpty(node) &&childList.size()>0){
			for(int i=0;i<childList.size();i++)
			{
				MenuFunction childValue = childList.get(i);
				TreeNode nodeValue = new TreeNode();
				nodeValue.setIconCls(childValue.getIconSrc());
				if(menuFunctionManager.getChildMenu(childValue.getId()).size()==0)
				{
					nodeValue.setIconCls("icon-cls");
					nodeValue.setLeaf("true");
					nodeValue.setHref(childValue.getAction());
					
				}
				else
				{
					nodeValue.setIconCls("icon-pkg");					
				}
				//根据menuFunction的checked属性设置treeNode的checked属性
				nodeValue.setChecked(childValue.getChecked());
				nodeValue.setId(childValue.getId().toString());
				nodeValue.setText(childValue.getName());
				nodeValue.setCls("cls");
				treeList.add(nodeValue);
			}
		}
		ServletActionContext.getRequest().setAttribute("treeData",treeList);
		return "jsonDataRenderChain";
	}
	
	//查看人员权限树 kukuxia.kevin.hw 2008-11-07
	public String viewCheckedMenuTree(){
		String node = ServletActionContext.getRequest().getParameter("node");
		String permisId = ServletActionContext.getRequest().getParameter("permisId");
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		
/**下面的2种方法都可以使用，读取和加载数据的方式不同，可对比参考。得到的数据都交给：JasonCheckedTreeDataRenderComponent处理，返回json格式的数据即可。
 *1.带checkBox的树，所有父节点异步加载数据，每次只读取该父节点下的所有数据 		
 *      List<MenuFunction> childList = menuFunctionManager.getChildForPermisMenuTree(Long.valueOf(node),Long.valueOf(permisId));		
		if (StringUtils.isNotEmpty(node) &&childList.size()>0){
			for(int i=0;i<childList.size();i++)
			{
				MenuFunction childValue = childList.get(i);
				TreeNode nodeValue = new TreeNode();
				nodeValue.setIconCls(childValue.getIconSrc());
				if(menuFunctionManager.getChildMenu(childValue.getId()).size()==0)
				{
					nodeValue.setIconCls("icon-cls");
					nodeValue.setLeaf("true");
//					nodeValue.setHref(childValue.getAction());
					
				}
				else
				{
					nodeValue.setIconCls("icon-pkg");					
				}
				//根据menuFunction的checked属性设置treeNode的checked属性
				nodeValue.setChecked(childValue.getChecked());
				nodeValue.setId(childValue.getId().toString());
				nodeValue.setText(childValue.getName());
				nodeValue.setCls("cls");
				treeList.add(nodeValue);
			}
		}
	*/
/*2.根节点异步加载，一次读取所有数据*/
		//获取当前权限的所有的菜单ID
		List<String> menuKey = new ArrayList<String>();
		menuKey = menuFunctionManager.getPermisMenuKey(Long.valueOf(permisId));
		//获得根节点下面的第一级的所有树节点，就是父节点为0的那些节点
		List<MenuFunction> firstClassChildMenuList = menuFunctionManager.getChildForPermisMenuTree(Long.valueOf("0"), menuKey);		
		if (firstClassChildMenuList.size()>0){
			for(int i=0;i<firstClassChildMenuList.size();i++)
			{	
				MenuFunction firstClassChildMenuValue = firstClassChildMenuList.get(i);	
				TreeNode firstClassNodeValue = new TreeNode();
				firstClassNodeValue.setIconCls(firstClassChildMenuValue.getIconSrc());	
				//递归获得所有的子节点
				getMenuChildren(firstClassNodeValue, firstClassChildMenuValue, menuKey);
				treeList.add(firstClassNodeValue);
			}	
		}
		ServletActionContext.getRequest().setAttribute("treeData",treeList);
		return "jsonDataRenderChain";
	}

	//递归获得该节点的所有子节点 kukuxia.kevin.hw
	public TreeNode getMenuChildren(TreeNode nodeValue, MenuFunction childMenuValue, List<String> menuKey) {
		
		if(menuFunctionManager.getChildForPermisMenuTree(childMenuValue.getId(),menuKey).size()==0)
		{
			nodeValue.setIconCls("icon-cls");
			nodeValue.setLeaf("true");
//			nodeValue.setHref(childMenuValue.getAction());
			
		}
		else
		{
			nodeValue.setIconCls("icon-pkg");	
			nodeValue.setExpanded("true");
			List<MenuFunction> childMenuList = menuFunctionManager.getChildForPermisMenuTree(childMenuValue.getId(), menuKey);		
			for(int i=0;i<childMenuList.size();i++)
			 {		
				MenuFunction childChildMenuValue = childMenuList.get(i);
				TreeNode childNodeValue = new TreeNode();
				childNodeValue.setIconCls(childChildMenuValue.getIconSrc());	
				List<TreeNode> childTreeNodeList = nodeValue.getChildTreeNodeList();
				if(childTreeNodeList==null){
					childTreeNodeList = new ArrayList<TreeNode>();
				}
				childTreeNodeList.add(getMenuChildren(childNodeValue, childChildMenuValue, menuKey));
				nodeValue.setChildTreeNodeList(childTreeNodeList);
			 }
		}
		//根据menuFunction的checked属性设置treeNode的checked属性
		nodeValue.setChecked(childMenuValue.getChecked());
		nodeValue.setId(childMenuValue.getId().toString());
		nodeValue.setText(childMenuValue.getName());
		nodeValue.setCls("cls");
		return nodeValue;
	}	
	
	/**
	 * @return the menuFunctionManager
	 */
	public MenuFunctionManager getMenuFunctionManager() {
		return menuFunctionManager;
	}
	/**
	 * @param menuFunctionManager the menuFunctionManager to set
	 */
	public void setMenuFunctionManager(MenuFunctionManager menuFunctionManager) {
		this.menuFunctionManager = menuFunctionManager;
	}	
}
