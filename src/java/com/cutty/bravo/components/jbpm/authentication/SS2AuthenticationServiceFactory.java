/* com.cutty.bravo.components.jbpm.authentication.SS2AuthenticationServiceFactory.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-3 下午12:13:13, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jbpm.authentication;

import org.jbpm.svc.Service;
import org.jbpm.svc.ServiceFactory;

/**
 * 该类实现了jbpm的ServiceFactory接口，用于控制启动和关闭用户认证服务
 *
 * <p>
 * <a href="SS2AuthenticationServiceFactory.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class SS2AuthenticationServiceFactory  implements ServiceFactory {

	private static final long serialVersionUID = -8914203153959526897L;
	
	private static SS2AuthenticationService authenticationService;
	
	/**
	 * 关闭用户认证服务
	 */
	public void close() {
		authenticationService = null;
		
	}

	/**
	 * 开启用户认证服务
	 */
	public Service openService() {
		if (authenticationService == null) {
			authenticationService = new SS2AuthenticationService();
		}
		return authenticationService;
	}

}
