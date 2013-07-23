/* com.cutty.bravo.core.security.filter.AuthenticationProcessingFilter.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Feb 26, 2009 9:30:11 AM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.Authentication;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;

import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.domain.SaasGroup;
import com.cutty.bravo.core.saas.SaasGroupUtils;
import com.cutty.bravo.core.security.domain.UserLoginLog;
import com.cutty.bravo.core.security.manager.LogoutLogManager;
import com.cutty.bravo.core.security.manager.UserLoginLogManager;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.web.handler.RequestHandler;

/**
 *
 * <p>
 * <a href="AuthenticationProcessingFilter.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class AuthenticationFilter extends AuthenticationProcessingFilter{
	
	@Override
	protected void onSuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, Authentication authResult)
			throws IOException {
		HttpSession session = request.getSession();
		UserLoginLogManager userLoginManager = (UserLoginLogManager)ApplicationContextKeeper.getAppCtx().getBean("userLoginLogManager");
		UserLoginLog userLoginLog = userLoginManager.addUserLoginLog(request);
		//将该条“用户登陆日志”与该用户的session捆绑在一起,当该用户退出时可以通过session找到其对应的“用户登陆日志”。
		session.setAttribute("userLoginLog", userLoginLog);
		String _bravo_window_model = request.getParameter("_bravo_window_model");
		if (StringUtils.isNotEmpty(_bravo_window_model)){
			session.setAttribute("_bravo_window_model", _bravo_window_model);
		}
		
		//将该条“用户登陆日志”添加到在线用户登陆日志队列上去。
		LogoutLogManager.addUser(userLoginLog);
	}
	
	


}
