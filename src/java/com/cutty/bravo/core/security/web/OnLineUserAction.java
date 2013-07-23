/* com.cutty.bravo.core.security.web.OnLineUserAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-2-26 下午11:11:18, Created by Yeon
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.web;

import java.util.ArrayList;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.core.dao.support.Page;
import com.cutty.bravo.core.security.domain.UserLoginLog;
import com.cutty.bravo.core.security.manager.LogoutLogManager;
import com.cutty.bravo.core.web.struts2.EntityAction;

/**
 *
 * <p>
 * <a href="OnLineUserAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:YeonOrchid@gmail.com">Yeon</a>
 */
@Namespace("/security")   
@ParentPackage("bravo")
public class OnLineUserAction extends EntityAction<UserLoginLog> {

	private static final long serialVersionUID = -3145267828327865634L;


	@Override
	public String find() throws Exception {
		int onLineUserNum = LogoutLogManager.getCount();
		ArrayList<UserLoginLog> onLineUsers = LogoutLogManager.getOnlineUsers();
    	Page page = new Page(0,onLineUserNum,onLineUserNum,onLineUsers);
		renderContextForFind(page);
        return FIND;
	}

	
}
