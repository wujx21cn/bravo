/* com.cutty.bravo.core.security.manager.Role2PermissionTrigger.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-1-13 上午10:55:06, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.manager;

import java.util.Iterator;
import java.util.List;

import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.domain.M2MEntityTrigger;
import com.cutty.bravo.core.exception.BizException;
import com.cutty.bravo.core.security.manager.cache.UserDetailsCacheManager;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;

/**
 *
 * <p>
 * <a href="Role2PermissionTrigger.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class Role2PermissionTrigger implements M2MEntityTrigger {

	public void trigger(String M2M_ENTITY_ID, String[] ids,String operation) throws BizException {
		BaseDao baseDao = (BaseDao)ApplicationContextKeeper.getAppCtx().getBean("baseDao");
		UserDetailsCacheManager userDetailsCacheManager = (UserDetailsCacheManager)ApplicationContextKeeper.getAppCtx().getBean("userDetailsService");
		String hql = "select user.loginid from User user left outer join user.roles roles where roles.id = "+M2M_ENTITY_ID;
		List<String> userNames = baseDao.find(null, hql,true);
		if (null != userNames){
			Iterator<String> userNamesIT = userNames.iterator();
			while (userNamesIT.hasNext()){
				String userName = userNamesIT.next();
				userDetailsCacheManager.refreshUserDetailsCache(userName);
			}
		}
	}

}
