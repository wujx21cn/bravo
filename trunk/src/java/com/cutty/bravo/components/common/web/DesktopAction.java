/* com.cutty.bravo.components.common.web.DesktopAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-21 上午01:39:43, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.web;


import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.components.common.domain.DeskTopConfig;
import com.cutty.bravo.components.common.domain.DeskTopConfigResource;
import com.cutty.bravo.components.common.domain.MenuFunction;
import com.cutty.bravo.components.common.domain.UserCookie;
import com.cutty.bravo.components.common.manager.DeskTopConfigManager;
import com.cutty.bravo.components.common.manager.DeskTopConfigResourceManager;
import com.cutty.bravo.components.common.manager.MenuFunctionManager;
import com.cutty.bravo.components.common.manager.UserCookieManager;
import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.security.domain.User;
import com.cutty.bravo.core.web.handler.RequestHandler;
import com.cutty.bravo.core.web.struts2.BaseActionSupport;

/**
 *
 * <p>
 * <a href="DesktopAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@ParentPackage("bravo")
@Namespace("/common")   
public class DesktopAction extends BaseActionSupport{

	private static final long serialVersionUID = -4161147582896533466L;
	private MenuFunctionManager menuFunctionManager;
	private DeskTopConfigManager deskTopConfigManager;
	private DeskTopConfigResourceManager deskTopConfigResourceManager;
	private UserCookieManager userCookieManager;
	
	public String loginRedirect() throws Exception {
		ServletActionContext.getRequest().setAttribute("bravoHome",ServletActionContext.getRequest().getContextPath());
		ServletActionContext.getRequest().setAttribute("extjsWidgetPath",ConfigurableConstants.getProperty("ui.extjs.widget.path", "widgets/ext-3.3.1"));
		String _bravo_window_model = (String)ServletActionContext.getRequest().getSession().getAttribute("_bravo_window_model");
		if (StringUtils.isNotEmpty(_bravo_window_model)){
			ServletActionContext.getRequest().setAttribute("_bravo_window_model", _bravo_window_model);
		}
		
		User user = RequestHandler.getContextRequestHandler().getCurrentUser();
		List<UserCookie> userCookies = userCookieManager.findBy(null, "user.id", user.getId(),true);
		ServletActionContext.getRequest().setAttribute("userCookies",userCookies);
		
		return "loginRedirect";
    }
	
	public String index() throws Exception {
		return "index";
    }
	
	public String view() throws Exception {
		return "view";
    }
	
	public String qoDesk() throws Exception {
		MenuFunction menuFunction = menuFunctionManager.get(Long.parseLong("10001"));	
		MenuFunction menuFunction10001 = new MenuFunction();
		menuFunction10001.setId(menuFunction.getId());
		menuFunction10001.setAction(menuFunction.getAction());
		menuFunction10001.setIconSrc(menuFunction.getIconSrc());
		menuFunction10001.setName(menuFunction.getName());
		menuFunction10001.setSequences(menuFunction.getSequences());
		menuFunctionManager.fillChildMenu(menuFunction10001, RequestHandler.getContextRequestHandler().getCurrentUser());
		menuFunction = menuFunctionManager.get(Long.parseLong("10002"));	
		MenuFunction menuFunction10002 = new MenuFunction();
		menuFunction10002.setId(menuFunction.getId());
		menuFunction10002.setAction(menuFunction.getAction());
		menuFunction10002.setIconSrc(menuFunction.getIconSrc());
		menuFunction10002.setName(menuFunction.getName());
		menuFunction10002.setSequences(menuFunction.getSequences());
		menuFunctionManager.fillChildMenu(menuFunction10002, RequestHandler.getContextRequestHandler().getCurrentUser());
		menuFunction = menuFunctionManager.get(Long.parseLong("10003"));	
		MenuFunction menuFunction10003 = new MenuFunction();
		menuFunction10003.setId(menuFunction.getId());
		menuFunction10003.setAction(menuFunction.getAction());
		menuFunction10003.setIconSrc(menuFunction.getIconSrc());
		menuFunction10003.setName(menuFunction.getName());
		menuFunction10003.setSequences(menuFunction.getSequences());
		menuFunctionManager.fillChildMenu(menuFunction10003, RequestHandler.getContextRequestHandler().getCurrentUser());
		menuFunction = menuFunctionManager.get(Long.parseLong("10004"));	
		MenuFunction menuFunction10004 = new MenuFunction();
		menuFunction10004.setId(menuFunction.getId());
		menuFunction10004.setAction(menuFunction.getAction());
		menuFunction10004.setIconSrc(menuFunction.getIconSrc());
		menuFunction10004.setName(menuFunction.getName());
		menuFunction10004.setSequences(menuFunction.getSequences());
		menuFunctionManager.fillChildMenu(menuFunction10004, RequestHandler.getContextRequestHandler().getCurrentUser());
		menuFunction = menuFunctionManager.get(Long.parseLong("10005"));	
		MenuFunction menuFunction10005 = new MenuFunction();
		menuFunction10005.setId(menuFunction.getId());
		menuFunction10005.setAction(menuFunction.getAction());
		menuFunction10005.setIconSrc(menuFunction.getIconSrc());
		menuFunction10005.setName(menuFunction.getName());
		menuFunction10005.setSequences(menuFunction.getSequences());
		menuFunctionManager.fillChildMenu(menuFunction10005, RequestHandler.getContextRequestHandler().getCurrentUser());
		MenuFunction topmenuFunction = new MenuFunction();
		topmenuFunction.setName("菜单顶层根节点");
		topmenuFunction.setId(Long.parseLong("0"));
		Set<MenuFunction> childMenuSet = new LinkedHashSet<MenuFunction>();
		childMenuSet.add(menuFunction10003);
		childMenuSet.add(menuFunction10002);
		childMenuSet.add(menuFunction10001);
		childMenuSet.add(menuFunction10004);
		childMenuSet.add(menuFunction10005);
		topmenuFunction.setChildMenuFunction(childMenuSet);
		DeskTopConfig deskTopConfig = deskTopConfigManager.findUniqueBy("user", RequestHandler.getContextRequestHandler().getCurrentUser(), false);
		//如果当前用户没有Config则,初始化
		if (null == deskTopConfig){
			deskTopConfig = new DeskTopConfig();
			deskTopConfig.setUser(RequestHandler.getContextRequestHandler().getCurrentUser());
			deskTopConfig.setAutoRunApps("[]");
			deskTopConfig.setQuickStartApps("[]");
			deskTopConfig.setShortcuts("[]");
			deskTopConfig.setBackgroundColor("390A0A");
			deskTopConfig.setFontColor("FFFFFF");
			deskTopConfig.setTaskbarTransparency(100);
			deskTopConfig.setWallpaperLayout("tile");
			//系统必须初始化DeskTopConfigResource实体ID分别为1,2两条资源,否则抛出异常
			DeskTopConfigResource theme = deskTopConfigResourceManager.findUniqueBy("id", Long.parseLong("2"), true);
			DeskTopConfigResource wallpaper = deskTopConfigResourceManager.findUniqueBy("id", Long.parseLong("1"), true);
			if (null == theme || null == wallpaper){
				logger.error("系统必须初始化DeskTopConfigResource实体ID分别为1,2两条资源,否则抛出异常!!!");
				throw new Exception("系统必须初始化DeskTopConfigResource实体ID分别为1,2两条资源,否则抛出异常!!!");
			}
			deskTopConfig.setTheme(theme);
			deskTopConfig.setWallpaper(wallpaper);
			deskTopConfigManager.save(deskTopConfig);
		}
		ServletActionContext.getRequest().setAttribute("deskTopConfig",deskTopConfig);
		ServletActionContext.getRequest().setAttribute("menuFunction",topmenuFunction);
		return "qoDesk";
    }
	
	//根据菜单项静态化生成桌面图标等的CCS
	public String qoDeskCCS() throws Exception {
		ServletActionContext.getRequest().setAttribute("menuIconCCS",menuFunctionManager.getMenuIconCCS());

		return "qoDeskCCS";
	}
	/**
	 * 监测session是否过期，并在Console上产生日志提示
	 * @return
	 */
	public String syncSessionWithLog(){
		String isSessionExpired = ServletActionContext.getRequest().getParameter("isSessionExpired");
		
		if(StringUtils.isNotEmpty(isSessionExpired)){
			//如果session已过期，返回字符串“expired”
			if("true".equalsIgnoreCase(isSessionExpired) || null == RequestHandler.getContextRequestHandler().getCurrentUser() ){
				ServletActionContext.getRequest().setAttribute("jsonStr","expired");
				logger.debug("Session has been checked: expired");
			}
			//如果session未过期，返回字符串“notExpired”
			else{
				ServletActionContext.getRequest().setAttribute("jsonStr","notExpired");
				logger.debug("Session has been checked: not expired");
			}
		}		
		
		return "blank";
	}
	
	/**
	 * 监测session是否过期，不会在Console上产生日志提示
	 * @return
	 */
	public String syncSessionWithoutLog(){
		String isSessionExpired = ServletActionContext.getRequest().getParameter("isSessionExpired");
		
		if(StringUtils.isNotEmpty(isSessionExpired)){
			//如果session已过期，返回字符串“expired”
			if("true".equalsIgnoreCase(isSessionExpired)){
				ServletActionContext.getRequest().setAttribute("jsonStr","expired");
			}
			//如果session未过期，返回字符串“notExpired”
			else{
				ServletActionContext.getRequest().setAttribute("jsonStr","notExpired");	
			}
		}
		
		return "blank";
	}

	public void setMenuFunctionManager(MenuFunctionManager menuFunctionManager) {
		this.menuFunctionManager = menuFunctionManager;
	}

	public void setDeskTopConfigManager(DeskTopConfigManager deskTopConfigManager) {
		this.deskTopConfigManager = deskTopConfigManager;
	}

	public void setDeskTopConfigResourceManager(
			DeskTopConfigResourceManager deskTopConfigResourceManager) {
		this.deskTopConfigResourceManager = deskTopConfigResourceManager;
	}

	/**
	 * @param userCookieManager the userCookieManager to set
	 */
	public void setUserCookieManager(UserCookieManager userCookieManager) {
		this.userCookieManager = userCookieManager;
	}

	/**
	 * @return the userCookieManager
	 */
	public UserCookieManager getUserCookieManager() {
		return userCookieManager;
	}
	
	
}
