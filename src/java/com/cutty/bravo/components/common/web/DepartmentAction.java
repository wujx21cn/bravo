/* com.cutty.bravo.components.common.web.DepartmentAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-19 下午01:50:14, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.web;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.components.common.domain.Department;
import com.cutty.bravo.core.web.struts2.EntityAction;

/**
 *
 * <p>
 * <a href="DepartmentAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
@ParentPackage("bravo")
@Namespace("/common")   
public class DepartmentAction extends EntityAction<Department>{

	private static final long serialVersionUID = -2464800770941847067L;

}
