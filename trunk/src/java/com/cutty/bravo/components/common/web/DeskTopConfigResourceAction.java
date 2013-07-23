/* com.cutty.bravo.components.common.web.DeskTopConfigResourceAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Apr 29, 2009 11:54:08 AM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.web;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.components.common.domain.Attachment;
import com.cutty.bravo.components.common.domain.DeskTopConfigResource;
import com.cutty.bravo.components.common.manager.DeskTopConfigResourceManager;
import com.cutty.bravo.core.web.struts2.EntityAction;

/**
 *
 * <p>
 * <a href="DeskTopConfigResourceAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Namespace("/common")   
@ParentPackage("bravo")
public class DeskTopConfigResourceAction  extends EntityAction<DeskTopConfigResource>{

	private static final long serialVersionUID = 4086955319067693356L;
	private DeskTopConfigResourceManager deskTopConfigResourceManager;
	
	/**
     * 从数据库中获取主题，并把结果以json格式返回
     * @return
     * @throws Exception
     */
    public String findAndRendThemesJsonData() throws Exception {
    	List<DeskTopConfigResource> deskTopConfigResources = deskTopConfigResourceManager.getAll();
    	StringBuffer sb = new StringBuffer("{\"images\":[");
    	for(int i=0; i<deskTopConfigResources.size(); i++){
    		if( !deskTopConfigResources.get(i).getType().getName().equalsIgnoreCase("桌面程序主题") ) continue;
    		StringBuffer rsb = new StringBuffer("{");
    		rsb.append("\"id\":").append("\""+deskTopConfigResources.get(i).getId().toString()+"\",");
    		rsb.append("\"name\":").append("\""+deskTopConfigResources.get(i).getName()+"\",");
    		rsb.append("\"pathtothumbnail\":").append("\""+ServletActionContext.getRequest().getContextPath()+deskTopConfigResources.get(i).getThumbnail()+"\",");
    		rsb.append("\"pathtofile\":").append("\""+ServletActionContext.getRequest().getContextPath()+deskTopConfigResources.get(i).getFile()+"\"");
    		rsb.append("}");
    		if(11<sb.length()){
    			sb.append(",");
    			sb.append(rsb);
    		}else{
    			sb.append(rsb);
    		}
    	}
    	sb.append("]}");
    	ServletActionContext.getRequest().setAttribute("jsonStr",sb.toString());
    	return "blank";
    }
    
    /**
     * 从数据库中获取桌面背景，并把结果以json格式返回
     * @return
     * @throws Exception
     */
    public String findAndRendWallpapersJsonData() throws Exception {
    	List<DeskTopConfigResource> deskTopConfigResources = deskTopConfigResourceManager.getAll();
    	StringBuffer sb = new StringBuffer("{\"images\":[");
    	for(int i=0; i<deskTopConfigResources.size(); i++){
    		if( !deskTopConfigResources.get(i).getType().getName().equalsIgnoreCase("桌面程序背景") ) continue;
    		StringBuffer rsb = new StringBuffer("{");
    		rsb.append("\"id\":").append("\""+deskTopConfigResources.get(i).getId().toString()+"\",");
    		rsb.append("\"name\":").append("\""+deskTopConfigResources.get(i).getName()+"\",");
    		rsb.append("\"pathtothumbnail\":").append("\""+ServletActionContext.getRequest().getContextPath()+deskTopConfigResources.get(i).getThumbnail()+"\",");
    		rsb.append("\"pathtofile\":").append("\""+ServletActionContext.getRequest().getContextPath()+deskTopConfigResources.get(i).getFile()+"\"");
    		rsb.append("}");
    		if(11<sb.length()){
    			sb.append(",");
    			sb.append(rsb);
    		}else{
    			sb.append(rsb);
    		}
    	}
    	sb.append("]}");
    	ServletActionContext.getRequest().setAttribute("jsonStr",sb.toString());
    	return "blank";
    }

	/**
	 * @param deskTopConfigResourceManager the deskTopConfigResourceManager to set
	 */
	public void setDeskTopConfigResourceManager(
			DeskTopConfigResourceManager deskTopConfigResourceManager) {
		this.deskTopConfigResourceManager = deskTopConfigResourceManager;
	}

	/**
	 * @return the deskTopConfigResourceManager
	 */
	public DeskTopConfigResourceManager getDeskTopConfigResourceManager() {
		return deskTopConfigResourceManager;
	}

}
