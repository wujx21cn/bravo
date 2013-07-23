/* com.cutty.bravo.core.security.manager.Permission2EntityOperationTrigger.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-3-24 下午02:05:04, Created by Yeon
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.manager;

import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.domain.M2MEntityTrigger;
import com.cutty.bravo.core.exception.BizException;
import com.cutty.bravo.core.security.domain.EntityOperatePermissionRelation;
import com.cutty.bravo.core.security.manager.cache.EntityOperationCacheManager;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;

/**
 *
 * <p>
 * <a href="Permission2EntityOperationTrigger.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:YeonOrchid@gmail.com">Yeon</a>
 */
public class Permission2EntityOperationTrigger implements M2MEntityTrigger {

	/* (non-Javadoc)
	 * @see com.cutty.bravo.core.domain.M2MEntityTrigger#trigger(java.lang.String, java.lang.String[], java.lang.String)
	 */
	public void trigger(String M2M_ENTITY_ID, String[] ids, String operation) throws BizException {
		BaseDao baseDao = (BaseDao)ApplicationContextKeeper.getAppCtx().getBean("baseDao");
		EntityOperationCacheManager entityOperationCacheManager = (EntityOperationCacheManager) ApplicationContextKeeper.getAppCtx().getBean("entityOperationCacheManager");
		for (int i=0;i<ids.length;i++){
			String id = ids[i];
			EntityOperatePermissionRelation entityOperatePermissionRelation = baseDao.get(EntityOperatePermissionRelation.class, Long.valueOf(id));
			entityOperationCacheManager.refreshEntityOperationDetailCache(entityOperatePermissionRelation);
		}

	}

}
