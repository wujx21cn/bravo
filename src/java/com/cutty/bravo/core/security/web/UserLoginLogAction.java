/* com.cutty.bravo.core.security.web.UserLoginLogAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-2-26 下午11:12:14, Created by Yeon
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.web;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.core.security.domain.UserLoginLog;
import com.cutty.bravo.core.web.struts2.EntityAction;

/**
 *
 * <p>
 * <a href="UserLoginLogAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:YeonOrchid@gmail.com">Yeon</a>
 */
@Namespace("/security")   
@ParentPackage("bravo")
public class UserLoginLogAction extends EntityAction<UserLoginLog> {

	private static final long serialVersionUID = 6076874075343642002L;

}
