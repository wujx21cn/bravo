/* com.cutty.bravo.components.common.web.UserCookieManager.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-12-2 下午06:46:57, Created by Yeon
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.manager;

import org.springframework.stereotype.Service;

import com.cutty.bravo.components.common.domain.UserCookie;
import com.cutty.bravo.core.manager.BaseManager;

@Service( "userCookieManager")
public class UserCookieManager extends BaseManager<UserCookie> {
	
}
