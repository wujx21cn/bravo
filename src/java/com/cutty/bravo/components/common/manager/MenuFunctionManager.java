/* com.cutty.bravo.components.common.web.MenuFunctionManager.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-1 上午03:41:51, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.springframework.stereotype.Service;


import com.cutty.bravo.components.common.domain.MenuFunction;
import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.dao.support.Page;
import com.cutty.bravo.core.manager.BaseManager;
import com.cutty.bravo.core.security.domain.User;
import com.cutty.bravo.core.web.handler.RequestHandler;

/**
 *
 * <p>
 * <a href="MenuFunctionManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Service("menuFunctionManager")
public class MenuFunctionManager extends BaseManager<MenuFunction>{
	private String menuIconCCS = null;
	
	/**
	 * 将所有的菜单图标转化成CCS，系统只初始化一次，如果新增菜单需要重启服务器，如果增加界面新增菜单功能，需要将MenuFunctionManager.menuIconCCS 字段清空 add by wujx 20110718
	 * @return
	 */
	public String getMenuIconCCS(){
		if (StringUtils.isNotEmpty(menuIconCCS)) return menuIconCCS;
		List<MenuFunction> menus =this.getAll();
		Set<String> menuIconSet = new HashSet<String>();
		StringBuffer sb = new StringBuffer();
		for (MenuFunction menu:menus){
			menuIconSet.add(menu.getIconSrc());

		}
		String bravoHome =  RequestHandler.getContextRequestHandler().getRequest().getContextPath();
		String extjsWidgetPath = ConfigurableConstants.getProperty("ui.extjs.widget.path", "widgets/ext-3.3.1");
		for (String icon:menuIconSet){
			sb.append(".menu-").append(icon).append("-icon {").append("\n").append("		background-image: url(/")
					.append(bravoHome).append("/").append(extjsWidgetPath).append("/resources/bravo-images/desktop/layout16x16.gif) !important;").append("\n}");
			sb.append("\n");
			sb.append(".menu-").append(icon).append("-shortcut img {").append("\n").append("		background-image: url(")
					.append(bravoHome).append("/").append(extjsWidgetPath).append("/resources/bravo-images/desktop/layout48x48.png);").append("		");
			sb.append("filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='/")
					.append(bravoHome).append("/").append(extjsWidgetPath).append("/resources/bravo-images/desktop/layout48x48.png', sizingMethod='scale');").append("\n}");
			sb.append("\n");
		}
		menuIconCCS = sb.toString();
		return menuIconCCS;
	}
	public List<MenuFunction> getChildMenu(Long nodeId){
		List<String> menuKey = (List<String>)ServletActionContext.getRequest().getSession().getAttribute("___bravo___menu___key");
		//获取当前登陆用户所有的菜单ID
		if (null == menuKey){
			menuKey = new ArrayList<String>();
			StringBuffer sqlBF = new StringBuffer();
			sqlBF.append("select fun_permis.sys_id from bravo_user_role user_role ")
			.append("inner join  bravo_role_permis role_permis on role_permis.role_id = user_role.role_id ")
			.append("inner join  bravo_fun_permis fun_permis on fun_permis.per_id = role_permis.permis_id ")
			.append("where user_role.user_id = :userId");
			Query sqlQuery =  baseDao.getHibernate().getSessionFactory().getCurrentSession().createSQLQuery(sqlBF.toString());
			sqlQuery.setBigDecimal("userId",BigDecimal.valueOf(RequestHandler.getContextRequestHandler().getCurrentUser().getId()));
			sqlQuery.setFirstResult(0);
			sqlQuery.setMaxResults(10000);
			List<BigDecimal> menus = sqlQuery.list();
			for (int i=0;i<menus.size();i++){
				menuKey.add(menus.get(i).toString());
			}
			ServletActionContext.getRequest().getSession().setAttribute("___bravo___menu___key", menuKey);
		}
		
		//获取子菜单
		String hql = "from MenuFunction where parentMenuFunction.id ="+nodeId+"  order by sequences asc";
		List<MenuFunction> menuFunctions = this.find(null, hql,true);
		List<MenuFunction> ret = new ArrayList<MenuFunction>();
		for (int i=0;i<menuFunctions.size();i++){
			MenuFunction menu = menuFunctions.get(i);
			//if (true || menuKey.contains(menu.getId().toString())){
			//根据user的权限去设置menufunction的checked属性，以便设置TreeNode的checked属性.
				if(menuKey.contains(menu.getId().toString())){
					menu.setChecked("true");
				}else{
					menu.setChecked("false");
				}
				ret.add(menu);
			//}
		}


		return ret;
		
	}
	
	/**
	 * 根据权限获取子菜单
	 * @param nodeId
	 * @param autorities
	 * @return
	 */
	public MenuFunction fillChildMenu(MenuFunction menuFunction,User loginUser){	
		//获取子菜单
		Map<String,Object> paramenter = new HashMap<String,Object>();
		paramenter.put("menuFunction", menuFunction);
		paramenter.put("loginUser", loginUser);
		Page page = null;
		try {
			page = this.findPageBySqlId("menu.getChildMenu", paramenter, null);
		} catch (Exception e) {
			logger.error(e);
		}
		if (null != page && null != page.getResult()){
			List<MenuFunction> resultList = (List<MenuFunction>)page.getResult();
			menuFunction.setChildMenuFunction(new   LinkedHashSet(resultList));
			Iterator<MenuFunction> resultIT = resultList.iterator();
			while (resultIT.hasNext()){
				MenuFunction currentMenuFunction = resultIT.next();
				fillChildMenu(currentMenuFunction , loginUser);
			}
		}
		
		return menuFunction;
	}
	
	public List<MenuFunction> getChildForPermisMenuTree(Long nodeId,Long permisId){
		//获取当前权限的所有的菜单ID
		   List<String> menuKey = new ArrayList<String>();
			StringBuffer sqlBF = new StringBuffer();
			sqlBF.append("SELECT bravo_fun_permis.sys_id FROM bravo_fun_permis ");
			sqlBF.append("WHERE bravo_fun_permis.per_id = "+permisId);
			Query sqlQuery =  baseDao.getHibernate().getSessionFactory().getCurrentSession().createSQLQuery(sqlBF.toString());
			sqlQuery.setFirstResult(0);
			sqlQuery.setMaxResults(10000);
			List<BigDecimal> menus = sqlQuery.list();
			for (int i=0;i<menus.size();i++){
				menuKey.add(menus.get(i).toString());
			}

		//获取子菜单
		String hql = "from MenuFunction where parentMenuFunction.id ="+nodeId+"  order by sequences asc";
		List<MenuFunction> menuFunctions = this.find(null, hql,true);
		List<MenuFunction> ret = new ArrayList<MenuFunction>();
		for (int i=0;i<menuFunctions.size();i++){
			MenuFunction menu = menuFunctions.get(i);
			//if (true || menuKey.contains(menu.getId().toString())){
			//根据user的权限去设置menufunction的checked属性，以便设置TreeNode的checked属性.
				if(menuKey.contains(menu.getId().toString())){
					menu.setChecked("true");
				}else{
					menu.setChecked("false");
				}
				ret.add(menu);
			//}
		}
		return ret;
	}	
	
	public List<String> getPermisMenuKey(Long permisId){
		//获取当前权限的所有的菜单ID
		   List<String> menuKey = new ArrayList<String>();
			StringBuffer sqlBF = new StringBuffer();
			sqlBF.append("SELECT bravo_fun_permis.sys_id FROM bravo_fun_permis ");
			sqlBF.append("WHERE bravo_fun_permis.per_id = "+permisId);
			Query sqlQuery =  baseDao.getHibernate().getSessionFactory().getCurrentSession().createSQLQuery(sqlBF.toString());
			sqlQuery.setFirstResult(0);
			sqlQuery.setMaxResults(10000);
			List<BigDecimal> menus = sqlQuery.list();
			for (int i=0;i<menus.size();i++){
				menuKey.add(menus.get(i).toString());
			}
			return menuKey;
	}

	public List<MenuFunction> getChildForPermisMenuTree(Long nodeId,List<String> menuKey){
		//获取子菜单
		String hql = "from MenuFunction where parentMenuFunction.id ="+nodeId+"  order by sequences asc";
		List<MenuFunction> menuFunctions = this.find(null, hql,true);
		List<MenuFunction> ret = new ArrayList<MenuFunction>();
		for (int i=0;i<menuFunctions.size();i++){
			MenuFunction menu = menuFunctions.get(i);
			//if (true || menuKey.contains(menu.getId().toString())){
			//根据user的权限去设置menufunction的checked属性，以便设置TreeNode的checked属性.
				if(menuKey.contains(menu.getId().toString())){
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
