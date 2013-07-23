/* com.cutty.bravo.core.security.manager.Permission2RoleTrigger.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-1-13 上午10:54:34, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.manager;

import java.util.Iterator;
import java.util.Set;

import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.domain.M2MEntityTrigger;
import com.cutty.bravo.core.exception.BizException;
import com.cutty.bravo.core.security.domain.Permission;
import com.cutty.bravo.core.security.domain.Resource;
import com.cutty.bravo.core.security.manager.cache.ResourceCacheManager;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;

/**
 *
 * <p>
 * <a href="Permission2RoleTrigger.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class Permission2RoleTrigger implements M2MEntityTrigger {

	/* (non-Javadoc)
	 * @see com.cutty.bravo.core.domain.M2MEntityTrigger#trigger(java.lang.String, java.lang.String[])
	 */
	public void trigger(String M2M_ENTITY_ID, String[] ids,String operation) throws BizException {
		BaseDao baseDao = (BaseDao)ApplicationContextKeeper.getAppCtx().getBean("baseDao");
		ResourceCacheManager resourceCacheManager = (ResourceCacheManager) ApplicationContextKeeper.getAppCtx().getBean("resourceCacheManager");
		Permission permission = baseDao.get(Permission.class, Long.valueOf(M2M_ENTITY_ID));
		Set<Resource> resources = permission.getResources();
		if (null != resources){
			Iterator<Resource> resourceIT = resources.iterator();
			while (resourceIT.hasNext()){
				Resource resource = resourceIT.next();
				resourceCacheManager.refreshResourceDetailCache(resource);
			}
		}

	}

}
