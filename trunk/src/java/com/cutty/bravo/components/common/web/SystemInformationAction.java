/* com.cutty.bravo.components.common.web.SystemInformationAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-3-19 下午02:46:03, Created by Yeon
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.web;

import java.text.SimpleDateFormat;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.components.common.domain.SystemInformation;
import com.cutty.bravo.components.common.manager.SystemInformationManager;
import com.cutty.bravo.core.web.struts2.EntityAction;

/**
 *
 * <p>
 * <a href="SystemInformationAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:YeonOrchid@gmail.com">Yeon</a>
 */
@Namespace("/common")
@ParentPackage("bravo")
public class SystemInformationAction extends EntityAction<SystemInformation> {

	private static final long serialVersionUID = 7812399617012011381L;
	
	private SystemInformationManager systemInformationManager;

	public SystemInformationManager getSystemInformationManager() {
		return systemInformationManager;
	}

	public void setSystemInformationManager(
			SystemInformationManager systemInformationManager) {
		this.systemInformationManager = systemInformationManager;
	}

	@Override
	public String view() throws Exception {
		SystemInformation systemInformation = systemInformationManager.findLatestSystemInfo();
		if( null!=systemInformation )
		{
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat ("yyyy-MM-dd");
			if( null!=systemInformation.getSystemName() ) ServletActionContext.getRequest().setAttribute("name",systemInformation.getSystemName());
			if( null!=systemInformation.getVersion() ) ServletActionContext.getRequest().setAttribute("version",systemInformation.getVersion());
			if( null!=systemInformation.getReleaseDate() ) ServletActionContext.getRequest().setAttribute("releaseDate",simpleDateFormat.format(systemInformation.getReleaseDate()));
			if( null!=systemInformation.getComments() ) ServletActionContext.getRequest().setAttribute("comments",systemInformation.getComments());
		}
		return VIEW;
	}
	
	

}
