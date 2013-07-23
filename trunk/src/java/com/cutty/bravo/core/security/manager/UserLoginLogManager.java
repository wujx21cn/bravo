/* com.cutty.bravo.core.security.manager.UserLoginLogManager.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-2-26 下午02:14:09, Created by Yeon
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.manager;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.cutty.bravo.components.common.domain.Department;
import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.manager.BaseManager;
import com.cutty.bravo.core.security.domain.User;
import com.cutty.bravo.core.security.domain.UserLoginLog;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.web.handler.RequestHandler;

/**
 *
 * <p>
 * <a href="UserLoginLogManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:YeonOrchid@gmail.com">Yeon</a>
 */
@Service("userLoginLogManager")
public class UserLoginLogManager extends BaseManager<UserLoginLog> {
	
	public UserLoginLog addUserLoginLog(HttpServletRequest request){
		HttpSession session = request.getSession();
		String ip = getRemoteAddress(request);
		String userName = (String)request.getSession().getAttribute(RequestHandler.SPRING_SECURITY_LAST_USERNAME_KEY);
		BaseDao baseDao= (BaseDao)ApplicationContextKeeper.getAppCtx().getBean("baseDao");
		User currentUser = baseDao.findUniqueBy(User.class, "loginid", userName,true);
		
		UserLoginLog userLoginLog = new UserLoginLog();
		userLoginLog.setChnName(currentUser.getUserName());
		Department department = currentUser.getDepartment();
		
		if((null != department) && (null != department.getId()))
		{
			userLoginLog.setDepartment((currentUser.getDepartment()).getName());
		}
		userLoginLog.setLoginId(currentUser.getLoginid());
		userLoginLog.setLoginTime(new Timestamp(new Date().getTime()));
		userLoginLog.setLoginIp(ip);
		return userLoginLog;
	}
	
	/**
	 * get the remote address
	 * <p>@param request
	 * <p>@return</p>
	 */
	public String getRemoteAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("Proxy-Client-IP");
			logger.debug("Proxy-Client-IP : "+ip);
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			logger.debug("WL-Proxy-Client-IP : "+ip);
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            logger.debug("HTTP_CLIENT_IP : "+ip);
        }
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            logger.debug("HTTP_X_FORWARDED_FOR : "+ip);
        }
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getRemoteAddr();
			logger.debug("getRemoteAddr : "+ip);
		}
		

		return ip;
	}

}
