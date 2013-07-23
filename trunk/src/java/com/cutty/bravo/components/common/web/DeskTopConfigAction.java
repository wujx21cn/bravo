/* com.cutty.bravo.components.common.web.DeskTopConfigAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Apr 29, 2009 1:34:00 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.components.common.domain.DeskTopConfig;
import com.cutty.bravo.components.common.domain.DeskTopConfigResource;
import com.cutty.bravo.components.common.domain.MenuFunction;
import com.cutty.bravo.components.common.manager.DeskTopConfigManager;
import com.cutty.bravo.components.common.manager.DeskTopConfigResourceManager;
import com.cutty.bravo.components.common.manager.MenuFunctionManager;
import com.cutty.bravo.core.ui.tags.tree.TreeNode;
import com.cutty.bravo.core.web.handler.RequestHandler;
import com.cutty.bravo.core.web.struts2.EntityAction;

/**
 *
 * <p>
 * <a href="DeskTopConfigAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Namespace("/common")   
@ParentPackage("bravo")
public class DeskTopConfigAction  extends EntityAction<DeskTopConfig>{

	private static final long serialVersionUID = -836441687011319511L;
	private DeskTopConfigManager deskTopConfigManager;
	private DeskTopConfigResourceManager deskTopConfigResourceManager;
	private MenuFunctionManager menuFunctionManager;

	public MenuFunctionManager getMenuFunctionManager() {
		return menuFunctionManager;
	}

	public void setMenuFunctionManager(MenuFunctionManager menuFunctionManager) {
		this.menuFunctionManager = menuFunctionManager;
	}

	public String connect() throws Exception {
		String action = ServletActionContext.getRequest().getParameter("action");
		String moduleId = ServletActionContext.getRequest().getParameter("moduleId");
		System.out.println(action);
		System.out.println(moduleId);
		return "connect";
    }
	
	public String load() throws Exception {
		String action = ServletActionContext.getRequest().getParameter("action");
		String moduleId = ServletActionContext.getRequest().getParameter("moduleId");
		System.out.println(action);
		System.out.println(moduleId);
		return "connect";
    }
	
	public String saveConfig() throws Exception {
		ServletActionContext.getRequest().setAttribute(com.cutty.bravo.core.ui.Constants.AJAX_HANDLE_KEY, com.cutty.bravo.core.ui.Constants.AJAX_HANDLE_VALUE);
		RequestHandler  requestHandler = RequestHandler.getContextRequestHandler();
		String ids = ServletActionContext.getRequest().getParameter("ids");
		DeskTopConfig deskTopConfig =deskTopConfigManager.findUniqueBy("user", requestHandler.getCurrentUser(), true);
		String action = ServletActionContext.getRequest().getParameter("action");
		if ("saveShortcut".equals(action)){
			deskTopConfig.setShortcuts(ids);
		} else if ("saveAutorun".equals(action)){
			deskTopConfig.setAutoRunApps(ids);
		} else if ("saveQuickstart".equals(action)){
			deskTopConfig.setQuickStartApps(ids);
		} else if ("saveAppearance".equalsIgnoreCase(action) || "saveBackground".equalsIgnoreCase(action)){
			String transparency = ServletActionContext.getRequest().getParameter("transparency");
			String wallpaper = ServletActionContext.getRequest().getParameter("wallpaper");
			String theme = ServletActionContext.getRequest().getParameter("theme");
			String wallpaperposition = ServletActionContext.getRequest().getParameter("wallpaperposition");
			String fontcolor = ServletActionContext.getRequest().getParameter("fontcolor");
			String backgroundcolor = ServletActionContext.getRequest().getParameter("backgroundcolor");
			if (StringUtils.isNotEmpty(transparency)) deskTopConfig.setTaskbarTransparency(Integer.parseInt(transparency));
			if (StringUtils.isNotEmpty(wallpaper)){
				DeskTopConfigResource wallpaperResource = deskTopConfigResourceManager.findUniqueBy("id",Long.parseLong(wallpaper), true);
				if (null != wallpaperResource) deskTopConfig.setWallpaper(wallpaperResource);
			}
			if (StringUtils.isNotEmpty(theme)){
				DeskTopConfigResource themeResource = deskTopConfigResourceManager.findUniqueBy("id",Long.parseLong(theme), true);
				if (null != themeResource) deskTopConfig.setTheme(themeResource);
			}
			if (StringUtils.isNotEmpty(wallpaperposition)) deskTopConfig.setWallpaperLayout(wallpaperposition);
			if (StringUtils.isNotEmpty(fontcolor)) deskTopConfig.setFontColor(fontcolor);
			if (StringUtils.isNotEmpty(backgroundcolor)) deskTopConfig.setBackgroundColor(backgroundcolor);
		}
		deskTopConfigManager.save(deskTopConfig);
		ServletActionContext.getRequest().setAttribute(com.cutty.bravo.core.ui.Constants.AJAX_HANDLE_STATUS,"successReturn");
		ServletActionContext.getRequest().setAttribute(com.cutty.bravo.core.ui.Constants.AJAX_HANDLE_MSG,"{success:'scs'}");
		return "jsonDataRenderChain";
	}

	public void setDeskTopConfigManager(DeskTopConfigManager deskTopConfigManager) {
		this.deskTopConfigManager = deskTopConfigManager;
	}

	public void setDeskTopConfigResourceManager(
			DeskTopConfigResourceManager deskTopConfigResourceManager) {
		this.deskTopConfigResourceManager = deskTopConfigResourceManager;
	}
	
	//liangg
	public String viewShortCuts(){
		RequestHandler  requestHandler = RequestHandler.getContextRequestHandler();
		 //获得当前user的id ,查询得到该user的shortCuts.
		DeskTopConfig deskTopConfig =deskTopConfigManager.findUniqueBy("user", requestHandler.getCurrentUser(), true);
		//解析该user的shortCuts
		String [] shortCutsArray = deskTopConfig.getShortcuts().substring(1, deskTopConfig.getShortcuts().length() - 1).split(",");
		
		//添加到List中，以便调用getChildForPermisMenuTree方法时使用
		List<String> shortCutsList = new ArrayList<String>();
		for(String s : shortCutsArray){
			shortCutsList.add(s.replaceAll("\"", ""));
		}
		
		renderTreeNodeForPreferences(shortCutsList);
		return "jsonDataRenderChain";
	}

	public String viewAutoRun(){
		RequestHandler  requestHandler = RequestHandler.getContextRequestHandler();
		 //获得当前user的id ,查询得到该user的shortCuts.
		DeskTopConfig deskTopConfig =deskTopConfigManager.findUniqueBy("user", requestHandler.getCurrentUser(), true);
		//解析该user的shortCuts
		String [] autoRunArray = deskTopConfig.getAutoRunApps().substring(1, deskTopConfig.getAutoRunApps().length() - 1).split(",");
		
		//添加到List中，以便调用getChildForPermisMenuTree方法时使用
		List<String> autoRunList = new ArrayList<String>();
		for(String s : autoRunArray){
 
			autoRunList.add(s.replaceAll("\"", ""));
		}
		
		renderTreeNodeForPreferences(autoRunList);
		return "jsonDataRenderChain";
	}
	public String viewQuickStart(){
		RequestHandler  requestHandler = RequestHandler.getContextRequestHandler();
		 //获得当前user的id ,查询得到该user的shortCuts.
		DeskTopConfig deskTopConfig =deskTopConfigManager.findUniqueBy("user", requestHandler.getCurrentUser(), true);
		//解析该user的shortCuts
		String [] quickStartArray = deskTopConfig.getQuickStartApps().substring(1, deskTopConfig.getQuickStartApps().length() - 1).split(",");
		
		//添加到List中，以便调用getChildForPermisMenuTree方法时使用
		List<String> quickStartList = new ArrayList<String>();
		for(String s : quickStartArray){
 
			quickStartList.add(s.replaceAll("\"", ""));
		}
		
		renderTreeNodeForPreferences(quickStartList);
		return "jsonDataRenderChain";
	}
	private void renderTreeNodeForPreferences(List<String> menuKey) {
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		
		
		TreeNode preference = new TreeNode();
		preference.setId("qo-preferences");
		preference.setText("个人设置");
		preference.setLeaf("true");
		if(menuKey.contains("qo-preferences")){
			preference.setChecked("true");
		}
		else preference.setChecked("false");
		treeList.add(preference);
//		menuKey = menuFunctionManager.getPermisMenuKey(Long.valueOf(permisId));
		//获得根节点下面的第一级的所有树节点，就是父节点为0的那些节点
		List<MenuFunction> firstClassChildMenuList = getChildAndSetCheck(Long.valueOf("0"), menuKey);		
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
	}

	//递归获得该节点的所有子节点 kukuxia.kevin.hw
	private TreeNode getMenuChildren(TreeNode nodeValue, MenuFunction childMenuValue, List<String> menuKey) {
		
		if(getChildAndSetCheck(childMenuValue.getId(),menuKey).size()==0)
		{
			nodeValue.setIconCls("icon-cls");
			nodeValue.setLeaf("true");
//			nodeValue.setHref(childMenuValue.getAction());
			
		}
		else
		{
			nodeValue.setIconCls("icon-pkg");	
			nodeValue.setExpanded("true");
			List<MenuFunction> childMenuList = getChildAndSetCheck(childMenuValue.getId(), menuKey);		
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
		nodeValue.setId( "moduleId" + childMenuValue.getId().toString());
		nodeValue.setText(childMenuValue.getName());
		nodeValue.setCls("cls");
		return nodeValue;
	}	
	
	private List<MenuFunction> getChildAndSetCheck(Long nodeId,List<String> menuKey){
		String hql = "from MenuFunction where parentMenuFunction.id ="+nodeId+"  order by sequences asc";
		List<MenuFunction> menuFunctions = menuFunctionManager.find(null, hql,true);
		List<MenuFunction> ret = new ArrayList<MenuFunction>();
		for (int i=0;i<menuFunctions.size();i++){
			MenuFunction menu = menuFunctions.get(i);
			//if (true || menuKey.contains(menu.getId().toString())){
			//根据user的权限去设置menufunction的checked属性，以便设置TreeNode的checked属性.
				if(menuKey.contains("moduleId" + menu.getId().toString())){
					menu.setChecked("true");
				}else{
					menu.setChecked("false");
				}
				ret.add(menu);
			//}
		}
		return ret;
	}
}
