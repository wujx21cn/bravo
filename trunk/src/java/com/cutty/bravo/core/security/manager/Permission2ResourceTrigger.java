/* com.cutty.bravo.core.security.manager.Permission2ResourceTrigger.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-1-12 上午11:33:03, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.manager;

import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.domain.M2MEntityTrigger;
import com.cutty.bravo.core.exception.BizException;
import com.cutty.bravo.core.security.domain.Resource;
import com.cutty.bravo.core.security.manager.cache.ResourceCacheManager;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;

/**
 *
 * <p>
 * <a href="Permission2ResourceTrigger.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class Permission2ResourceTrigger implements M2MEntityTrigger {

	/* (non-Javadoc)
	 * @see com.cutty.bravo.core.domain.M2MEntityTrigger#trigger(java.lang.String, java.lang.String[])
	 */
	public void trigger(String M2M_ENTITY_ID, String[] ids,String operation) throws BizException {
		BaseDao baseDao = (BaseDao)ApplicationContextKeeper.getAppCtx().getBean("baseDao");
		ResourceCacheManager resourceCacheManager = (ResourceCacheManager) ApplicationContextKeeper.getAppCtx().getBean("resourceCacheManager");
		for (int i=0;i<ids.length;i++){
			String id = ids[i];
			Resource resource = baseDao.get(Resource.class, Long.valueOf(id));
			resourceCacheManager.refreshResourceDetailCache(resource);
		}
	}

}
