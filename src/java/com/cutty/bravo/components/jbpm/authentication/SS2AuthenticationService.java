/* com.cutty.bravo.components.jbpm.authentication.SS2AuthenticationService.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-3 下午12:13:55, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jbpm.authentication;

import org.jbpm.security.AuthenticationService;

import com.cutty.bravo.core.security.domain.User;
import com.cutty.bravo.core.web.handler.RequestHandler;

/**
 * 该类实现了jbpm角色认证服务的接口，通过该类可以对当前用户进行角色认证，返回其用户名信息
 *
 * <p>
 * <a href="SS2AuthenticationService.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class SS2AuthenticationService implements AuthenticationService {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 获取当前用户的用户名
	 */
	public String getActorId() {
		if (null == RequestHandler.getContextRequestHandler())return "匿名";
		User userDetails = RequestHandler.getContextRequestHandler().getCurrentUser();
		if (userDetails != null) {
			return userDetails.getId().toString();
		} else {
			return "匿名";
		}
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

};
