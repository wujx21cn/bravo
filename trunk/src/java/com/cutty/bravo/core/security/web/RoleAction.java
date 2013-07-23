/* com.cutty.bravo.components.common.web.RoleAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-15 ����10:48:53, Created by Cathy.Lin
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.web;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.core.security.domain.Role;
import com.cutty.bravo.core.web.struts2.EntityAction;

/**
 *
 * <p>
 * <a href="RoleAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:linjuan0125@gmail.com">Cathy Lin</a>
 */

@Namespace("/security")   
@ParentPackage("bravo")
public class RoleAction extends EntityAction<Role>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8394200671205725671L;
	 

}
