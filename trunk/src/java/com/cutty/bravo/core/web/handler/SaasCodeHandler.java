/* com.cutty.bravo.core.web.handler.SaasCodeHandler.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2011-9-14 上午10:34:53, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.web.handler;

/**
 *
 * <p>
 * <a href="SaasCodeHandler.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class SaasCodeHandler {
	public static final String CURRENT_LOGIN_USER_SAAS_KEY = "LOGIN_USER_SAAS_GROUP";
	public static final String DEFAULT_SAAS_KEY = "SAAS_BRAVO";
	private static ThreadLocal<SaasCodeHandler> contextHolder = new ThreadLocal<SaasCodeHandler>();
	private String saasCode;

	
	public static SaasCodeHandler initSaasCodeHandler(String saasCode){
		SaasCodeHandler saasCodeHandler = new SaasCodeHandler();
		saasCodeHandler.saasCode = saasCode;
		contextHolder.set(saasCodeHandler);
		return saasCodeHandler;
	}
	
	public static String getSaasCode(){
		if (null == contextHolder.get()) throw new RuntimeException("获取不到saas code！！！");
		if (null != contextHolder.get()) return contextHolder.get().saasCode;
		return null;
	}

}
