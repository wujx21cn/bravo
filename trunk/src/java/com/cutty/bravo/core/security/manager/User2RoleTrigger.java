/* com.cutty.bravo.core.security.manager.User2RoleTrigger.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-1-14 上午11:17:31, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.manager;

import java.util.List;

import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.domain.M2MEntityTrigger;
import com.cutty.bravo.core.exception.BizException;
import com.cutty.bravo.core.security.manager.cache.UserDetailsCacheManager;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;

/**
 *
 * <p>
 * <a href="User2RoleTrigger.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class User2RoleTrigger implements M2MEntityTrigger {

	/* (non-Javadoc)
	 * @see com.cutty.bravo.core.domain.M2MEntityTrigger#trigger(java.lang.String, java.lang.String[], java.lang.String)
	 */
	public void trigger(String M2M_ENTITY_ID, String[] ids, String operation) throws BizException {
		BaseDao baseDao = (BaseDao)ApplicationContextKeeper.getAppCtx().getBean("baseDao");
		UserDetailsCacheManager userDetailsCacheManager = (UserDetailsCacheManager)ApplicationContextKeeper.getAppCtx().getBean("userDetailsService");
		String hql = "select user.loginid from User user  where user.id = "+M2M_ENTITY_ID;
		List<String> userNames = baseDao.find(null, hql,true);
		if (null != userNames && 0 < userNames.size()){
			userDetailsCacheManager.refreshUserDetailsCache(userNames.get(0));
		}
	}

}
