/* com.cutty.bravo.components.common.web.ResourceAction.java

{{IS_NOTE
 * <p>
 * <a href="ResourceAction.java.html"><i>View Source</i></a>
 * </p>
		
	History:
		2008-10-15 ����10:48:53, Created by Cathy.Lin
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.web;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.core.security.domain.Resource;
import com.cutty.bravo.core.security.manager.cache.ResourceCacheManager;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.web.struts2.EntityAction;


@Namespace("/security")   
@ParentPackage("bravo")
public class ResourceAction extends EntityAction<Resource>{
	private static final long serialVersionUID = 5882060935838695078L;

	@Override
	public String batchRemove() throws Exception {
		String resultCode =  super.batchRemove();
		ResourceCacheManager ResourceCacheManager = (ResourceCacheManager)ApplicationContextKeeper.getAppCtx().getBean("resourceCacheManager");
		ResourceCacheManager.refreshCache();
		return resultCode;
	}

	@Override
	public String batchSave() throws Exception {
		String resultCode =   super.batchSave();
		ResourceCacheManager ResourceCacheManager = (ResourceCacheManager)ApplicationContextKeeper.getAppCtx().getBean("resourceCacheManager");
		ResourceCacheManager.refreshCache();
		return resultCode;
	}
	
}
