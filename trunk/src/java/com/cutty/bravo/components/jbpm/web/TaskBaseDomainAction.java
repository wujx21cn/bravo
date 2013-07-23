/* com.cutty.bravo.components.jbpm.web.TaskBaseDomainAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Apr 9, 2009 5:24:48 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jbpm.web;



import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.components.jbpm.domain.TaskBaseDomain;
import com.cutty.bravo.core.web.struts2.EntityAction;

/**
 *
 * <p>
 * <a href="TaskBaseDomainAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Namespace("/workflow")   
@ParentPackage("bravo")
public class TaskBaseDomainAction  extends EntityAction<TaskBaseDomain>{

	private static final long serialVersionUID = -6534388329721629562L;
	public String assignTask() throws Exception {
		return "assignTask";
	}
}
