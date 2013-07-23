/* com.cutty.bravo.components.jbpm.web.ProcessInstanceAction.java
{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-11-26 下午03:28:51, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.
*/
package com.cutty.bravo.components.jbpm.web;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.jbpm.graph.exe.ProcessInstance;

import com.cutty.bravo.components.jbpm.domain.WorkFlowBaseDomain;
import com.cutty.bravo.core.dao.support.Page;
import com.cutty.bravo.core.dao.support.QueryParameterWrapper;
import com.cutty.bravo.core.web.struts2.EntityAction;

/**
 *
 * <p>
 * <a href="ProcessInstanceAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
@Namespace("/workflow")   
@ParentPackage("bravo")
public class ProcessInstanceAction  extends EntityAction<ProcessInstance>{
	private static final long serialVersionUID = -5356613282836597811L;
	
	public String workingProcess() throws Exception {
		return "workingProcess";
	}
	
	public String finishedProcess() throws Exception {
		return "finishedProcess";
	}
	
	public String suspendedProcess() throws Exception {
		return "suspendedProcess";
	}
	
	public String findProcess() throws Exception {
    	Page page = Page.getInstance(ServletActionContext.getRequest());
    	try {
    		baseDao.findByPage(new QueryParameterWrapper(WorkFlowBaseDomain.class), page,true);
		} catch (Exception e) {
			logger.error(e);
			throw e; 
		}
		renderContextForFind(page);
		return JSON_DATA_RENDER_CHAIN;
	}
}
