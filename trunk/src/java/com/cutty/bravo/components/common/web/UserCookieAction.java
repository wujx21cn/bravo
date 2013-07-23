/* com.cutty.bravo.components.common.web.UserCookieAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-12-2 下午06:46:57, Created by Yeon
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.web;

import java.util.List;

import javax.servlet.http.Cookie;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.components.common.domain.UserCookie;
import com.cutty.bravo.components.common.manager.UserCookieManager;
import com.cutty.bravo.core.security.domain.User;
import com.cutty.bravo.core.web.handler.RequestHandler;
import com.cutty.bravo.core.web.struts2.EntityAction;

/**
 * 该类用于响应主界面上的个人设置下拉菜单点击事件，对"BROVO_USER_COOKIE"表进行读写等操作
 *
 * <p>
 * <a href="UserCookieAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:yeonorchid@gmail.com">Yeon</a>
 */
@Namespace("/common")   
@ParentPackage("bravo")
public class UserCookieAction extends EntityAction<UserCookie> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3672639626561338248L;
	private UserCookieManager userCookieManager;
	
	public UserCookieManager getUserCookieManager(){
		return userCookieManager;	
	}
	
	public void setUserCookieManager(UserCookieManager userCookieManager){
		this.userCookieManager = userCookieManager;
	}
	
	/**
	 * 删除当前用户所有已有cookie
	 */
	public void deleteUserCookie(){
		User user = RequestHandler.getContextRequestHandler().getCurrentUser();
		List<UserCookie> oldUserCookies = userCookieManager.findBy(null, "user.id", user.getId(),true);
		
		if (null != oldUserCookies && 0 < oldUserCookies.size()){
			for (int i=0;i<oldUserCookies.size();i++){
				userCookieManager.remove(oldUserCookies.get(i));
			}		
		}
	}
	
	/**
	 * 响应主页面中个人设置下拉菜单中保存个人设置操作
	 */
	public void saveUserCookie(){
		String cookieString = ServletActionContext.getRequest().getParameter("cookieString");		
		String[] cookies = cookieString.split("; ");		
		if(null != cookies){
			
			//先删除当前用户留在在服务器中的所有cookie
			deleteUserCookie();
			
			for(int i=0; i<cookies.length; i++){				
				String[] cookie = cookies[i].split("=");				
				if(false == "JSESSIONID".equalsIgnoreCase(cookie[0])){				
					UserCookie newUserCookie = new UserCookie();
					if(cookie.length==2)       //有值的cookie
					{
						newUserCookie.setName(cookie[0]);
						newUserCookie.setValue(cookie[1]);
					}
					else if(cookie.length==1)  //无值的cookie
					{
						newUserCookie.setName(cookie[0]);
						newUserCookie.setValue("");
					}
					newUserCookie.setUser(RequestHandler.getContextRequestHandler().getCurrentUser());
					
					userCookieManager.save(newUserCookie);
				}
			}
		}

		/*用下面方法获取客户端cookie时，不知为何cookie的值会不一致，所以这里用了上面的方法进行cookie的传递
		Cookie[] cookies = ServletActionContext.getRequest().getCookies();
		if(null != cookies){
			
			deleteUserCookie();
			
			for(int i = 0; i<cookies.length; i++){
				//对于JSESSIONID不做保存，该cookie让服务器知道客户端建立过连接，避免重复登陆
				if(false == "JSESSIONID".equalsIgnoreCase(cookies[i].getName())){
					UserCookie newUserCookie = new UserCookie();					
					String  name    = cookies[i].getName();
					String  value   = cookies[i].getValue();
					String  domain  = cookies[i].getDomain();
					Integer maxAge  = new Integer(10000);
					String path     = "/";
					String  secure  = Boolean.toString(cookies[i].getSecure());
					Integer version = new Integer(cookies[i].getVersion());
					User    user    = RequestHandler.getContextRequestHandler().getCurrentUser();
					
					newUserCookie.setName(name);
					newUserCookie.setValue(value);
					newUserCookie.setDomain(domain);
					newUserCookie.setMaxAge(maxAge);
					newUserCookie.setPath(path);
					newUserCookie.setSecure(secure);
					newUserCookie.setVersion(version);
					newUserCookie.setUser(user);
					
					userCookieManager.save(newUserCookie);
				}
			}
		}*/
	}
	
	/**
	 * 响应主页面中个人设置下拉菜单中恢复个人设置操作
	 */
	public void loadUserCookie(){
		User user = RequestHandler.getContextRequestHandler().getCurrentUser();
		List<UserCookie> userCookies = userCookieManager.findBy(null, "user.id", user.getId(),true);
		
		if (null != userCookies && 0 < userCookies.size()){
			for (int i=0;i<userCookies.size();i++){
				String cookieName = userCookies.get(i).getName();
				String cookieValue= userCookies.get(i).getValue();
				Cookie cookie = new Cookie(cookieName, cookieValue);
				
				cookie.setMaxAge(60*60*24*7*1000);
				cookie.setPath("/");
				
				ServletActionContext.getResponse().addCookie(cookie);
			}		
		}
	}

}
