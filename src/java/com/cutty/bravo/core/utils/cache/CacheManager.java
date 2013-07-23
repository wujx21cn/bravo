/* com.cutty.bravo.core.utils.cache.CacheManager.java

{{IS_NOTE

	Purpose:
	
	Description:
	
	History:
		2008-9-14 下午04:02:12, Created by Jason.Wu

}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.utils.cache;

import java.util.Map;

import com.cutty.bravo.core.exception.CacheException;


/**
 *
 * <p>
 * <a href="CacheManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public interface CacheManager {
	
	//创建cache
	public Cache createCache(String cacheName,int maxElementsInMemory,boolean overflowToDisk,boolean eternal,long timeToLiveSeconds,long timeToIdleSeconds) throws CacheException;
	
	//获取cache
	public Cache getCache(String cacheName);
	
	//添加cache
	public void addCache(Cache cache) throws CacheException;
	
	//删除cache
	public void  removeCache(String cacheName);
	
	//删除所有cache
	public  void removalAll() ;
	
	//删除所有cache
	public void shutdown();
	
}
