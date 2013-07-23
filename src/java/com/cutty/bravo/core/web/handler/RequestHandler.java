/* com.cutty.bravo.core.web.handler.RequestHandler.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-7-29 上午09:20:14, Created by Jason.Wu
		2008-11-03 kukuxia.kevin.hw 获取当前系统时间
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.web.handler;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.security.domain.User;
import com.cutty.bravo.core.security.manager.cache.UserDetailsCacheManager;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;

/**
 * <p>该类存放着一个当前的HttpServletRequest，当前登陆的用户，系统当前时间，
 *
 * <p>
 * <a href="RequestHandler.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public final class RequestHandler {
private static ThreadLocal<RequestHandler> contextHolder = new ThreadLocal<RequestHandler>();
	protected static final Log logger = LogFactory.getLog(RequestHandler.class);
	public static final String CURRENT_LOGIN_USER_KEY = "BRAVO_CURRENT_LOGIN_USER";
	public static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";
	
	private HttpServletRequest request = null;
	private User currentUser ;
	private Timestamp currentDate ;
	
	private RequestHandler(HttpServletRequest request) {
		this.request = request;
		contextHolder.set(this);
	}

	/**
	 * 获取requestHandler,根据当前request构造.
	 * @param request
	 * @return
	 */
	public static RequestHandler getRequestHandler(HttpServletRequest request) {
		RequestHandler requestHandler = (RequestHandler) request.getAttribute("requestHandler");
		if (requestHandler == null) {
			requestHandler = new RequestHandler(request);
			request.setAttribute("requestHandler", requestHandler);
		}
		return requestHandler;
	}

	public static RequestHandler getContextRequestHandler() {

		return contextHolder.get();
	}
	
	/**
	 * 获取当前登陆人.
	 * @return the currentUser
	 */
	public User getCurrentUser() {
		currentUser = (User)getSessionAttribute(RequestHandler.CURRENT_LOGIN_USER_KEY);
		if (null == currentUser){
			String UserName = (String)ServletActionContext.getRequest().getSession().getAttribute(RequestHandler.SPRING_SECURITY_LAST_USERNAME_KEY);
			if (StringUtils.isNotEmpty(UserName)){
				BaseDao baseDao= (BaseDao)ApplicationContextKeeper.getAppCtx().getBean("baseDao");
				currentUser = baseDao.findUniqueBy(User.class, "loginid", UserName,true);
				baseDao.evict(currentUser);
				setSessionAttribute(RequestHandler.CURRENT_LOGIN_USER_KEY,currentUser);
			}
		}
		return currentUser;
	}

	/**
	 * 获取当前系统时间.
	 * @return the currentUser
	 */
	public Timestamp getCurrentDate() {
		currentDate = new Timestamp(System.currentTimeMillis());
		return currentDate;
	}	
	
	/**
	 * 获取当前登陆人权限数组
	 * @return
	 */
	public Set<String> getCurrentUserAutorities(){
		currentUser = getCurrentUser();
		UserDetailsCacheManager userDetailsCacheManager= (UserDetailsCacheManager)ApplicationContextKeeper.getAppCtx().getBean("userDetailsService");
		return userDetailsCacheManager.loadUserAuthorityByUsername(currentUser.getLoginid());
	}

	public Map getParameterMapWithPrefix(String prefix, String suffix) {
		return getMapWithPrefix(getParameterMap(), prefix, suffix);
	}
	

	public static Map getMapWithPrefix(Map map, String prefix, String suffix) {
		Map ret = new HashMap();

		String key = "";
		for (Iterator keyIt = map.keySet().iterator(); keyIt.hasNext();) {
			key = (String) keyIt.next();
			if (prefix != null && prefix.length() > 0) {
				if (key.length() < prefix.length())
					continue;
				if (key.startsWith(prefix)) {
					if (suffix != null && suffix.length() > 0) {
						if (key.length() < suffix.length())
							continue;
						if (!key.endsWith(suffix))
							continue;
						try {
							if (map.get(key) instanceof String[]){
								ret.put(key.substring(prefix.length(), key.length() - suffix.length()), ((String[])map.get(key))[0]);
							} else {
								ret.put(key.substring(prefix.length()), map.get(key));
							}
							
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} else {
						String retValue = null;
						if (map.get(key) instanceof String[]){
							ret.put(key.substring(prefix.length()), ((String[])map.get(key))[0]);
						} else {
							ret.put(key.substring(prefix.length()), map.get(key));
						}
						
					}
				}
			} else {
				try {
					if (key.length() < suffix.length())
						continue;
					if (!key.endsWith(suffix))
						continue;
					if (map.get(key) instanceof String[]){
						ret.put(key.substring(0, key.length() - suffix.length()),
								((String[])map.get(key))[0]);
					} else {
						ret.put(key.substring(0, key.length() - suffix.length()),
								map.get(key));
					}
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return ret;
	}
	
	public Map getParameterMap() {
		return ServletActionContext.getRequest().getParameterMap();
	}

	/**
	 * 获取当前的 HttpServletRequest
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	
	/**
	 * 获取当前的 HttpSession
	 * @return
	 */
	public HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}
	
	public Object getRequestAttribute(String name){
		return ServletActionContext.getRequest().getAttribute(name);
	}
	
	public void setRequestAttribute(String name,Object value){
		ServletActionContext.getRequest().setAttribute(name,value);
	}
	
	public Object getSessionAttribute(String name){
		return ServletActionContext.getRequest().getSession().getAttribute(name);
	}
	
	public void setSessionAttribute(String name,Object value){
		ServletActionContext.getRequest().getSession().setAttribute(name,value);
	}
}
