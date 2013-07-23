/* com.cutty.bravo.core.security.manager.LogoutLogManager.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Feb 26, 2009 10:12:19 AM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.manager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.Authentication;
import org.springframework.security.ui.logout.LogoutHandler;

import com.cutty.bravo.core.manager.BaseManager;
import com.cutty.bravo.core.security.domain.Resource;
import com.cutty.bravo.core.security.domain.UserLoginLog;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
/**
 *
 * <p>
 * <a href="LogoutLogManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class LogoutLogManager extends BaseManager<Resource> implements LogoutHandler{
	
	//在线用户队列，用于保存当前在线用户的登陆信息
	private static ArrayList<UserLoginLog> onlineUsers = new ArrayList<UserLoginLog>();
	//在在线用户队列上添加一条新的用户登陆信息，一般在用户登陆成功后调用
	public static void addUser(UserLoginLog userLoginLog) {
		onlineUsers.add(userLoginLog);
	}
	//在在线用户队列上删除一条已有的用户登陆信息，一般在用户退出时调用
	public void deleteUser(UserLoginLog userLoginLog) {
		onlineUsers.remove(userLoginLog);
	}
	//获取当前在线用户的数量
	public static int getCount() {
		return onlineUsers.size();
	}
	//获取当前在线用户列表
	public static ArrayList<UserLoginLog> getOnlineUsers() {
		return onlineUsers;
	}
	
	
	//用户退出时调用该方法，主要用户从当前在线用户列表中删除该退出的用户，同时为对应的用户登陆日志写入退出时间。
	public void logout(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) {
		
		HttpSession session = request.getSession();
		UserLoginLog userLoginLog = (UserLoginLog) session.getAttribute("userLoginLog");
		if (userLoginLog != null) {
			deleteUser(userLoginLog);
			userLoginLog.setLogoutTime(new Timestamp(new Date().getTime()));
			UserLoginLogManager userLoginLogManager = (UserLoginLogManager) ApplicationContextKeeper.getAppCtx().getBean("userLoginLogManager");
			userLoginLogManager.save(userLoginLog);
		}
	}

}
