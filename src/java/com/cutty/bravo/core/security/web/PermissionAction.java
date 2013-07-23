/* com.cutty.bravo.components.common.web.PermissionAction.java

{{IS_NOTE
 * <p>
 * <a href="PermissionAction.java.html"><i>View Source</i></a>
 * </p>
		
	History:
		2008-10-15 ����10:48:53, Created by Cathy.Lin
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.web;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.core.security.domain.Permission;
import com.cutty.bravo.core.security.manager.PermissionManager;
import com.cutty.bravo.core.ui.Constants;
import com.cutty.bravo.core.web.struts2.EntityAction;


@Namespace("/security")   
@ParentPackage("bravo")
public class PermissionAction extends EntityAction<Permission>{
	private static final long serialVersionUID = -299433496846951204L;
    private PermissionManager permissionManager;
	public PermissionManager getPermissionManager() {
		return permissionManager;
	}
	public void setPermissionManager(PermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}
	
    public String checkedMenuTreeSave(){
		ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_KEY, Constants.AJAX_HANDLE_VALUE);	    //可以查看的菜单ID数组
		String[] ids = ServletActionContext.getRequest().getParameterValues("checkedTreeIDs");
		//权限ID
		String permisID = ServletActionContext.getRequest().getParameter("permisID");    	
    	try {
			permissionManager.checkedMenuTreeSaveSql(ids,permisID);//保存权限对应可以查看的菜单
		} catch (Exception e) {
			logger.error(e);
			ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_STATUS,Constants.AJAX_HANDLE_STATUS_FAILURE);
			ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_MSG, e.getMessage());
		}
		ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_STATUS,Constants.AJAX_HANDLE_STATUS_SUCCESS);
		return "jsonDataRenderChain";
    }
    
    public String entinyOpPermisTreeSave(){
    	ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_KEY, Constants.AJAX_HANDLE_VALUE);	  
    	String[] entityNames = ServletActionContext.getRequest().getParameterValues("checkedTreeNames");
    	String permisID = ServletActionContext.getRequest().getParameter("permisID");
    	
    	Map meta = baseDao.getHibernate().getSessionFactory().getAllClassMetadata();
    	Set entityNameSet = meta.keySet();
    	

    	//找出从UI传来的实体名的全名，再保存之
    	
    		for(int i = 0; i < entityNames.length; i ++){
    			String name = entityNames[i];
    			if("".equals(name)){
    				entityNames = null;
    				break;
    			}
    			String atrName = name.substring(0, name.indexOf("."));
    			Iterator itSet = entityNameSet.iterator();
    			while(itSet.hasNext()){
    	    		String fullEntityName = (String)itSet.next();
    	    		if(fullEntityName.substring(fullEntityName.lastIndexOf(".")+1, fullEntityName.length()).equals(atrName)){
    	    			entityNames[i] = fullEntityName + "." + name.substring(name.indexOf(".")+1, name.length());
    	    		}
    	    	 
    			}
    	}
    	try {
			permissionManager.entityOpPermisTreeSave(entityNames, permisID);
		} catch (Exception e) {
			logger.error(e);
			ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_STATUS,Constants.AJAX_HANDLE_STATUS_FAILURE);
			ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_MSG, e.getMessage());
		}
		ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_STATUS,Constants.AJAX_HANDLE_STATUS_SUCCESS);
    	return "jsonDataRenderChain";
    }

}
