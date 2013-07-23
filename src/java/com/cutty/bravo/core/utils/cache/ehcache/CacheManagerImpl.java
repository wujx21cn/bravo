/* com.cutty.bravo.core.utils.cache.ehcache.CacheManagerImpl.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-14 下午04:29:59, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.utils.cache.ehcache;

import java.util.Map;

import net.sf.ehcache.ObjectExistsException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cutty.bravo.core.exception.CacheException;
import com.cutty.bravo.core.utils.cache.Cache;
import com.cutty.bravo.core.utils.cache.CacheManager;

/**
 *
 * <p>
 * <a href="CacheManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class CacheManagerImpl implements CacheManager{


	private static final Log logger = LogFactory.getLog(CacheManagerImpl.class);
	
	private net.sf.ehcache.CacheManager ehcacheManager ;
	
	

	public CacheManagerImpl(){
		ehcacheManager = net.sf.ehcache.CacheManager.getInstance();
	}

	public void addCache(Cache cache) throws CacheException {
		try {
			ehcacheManager.addCache((net.sf.ehcache.Cache)cache.getCacheObject());
		} catch (ObjectExistsException e) {
			logger.error(e);
			throw new CacheException(e.getMessage());
		} catch (IllegalStateException e) {
			logger.error(e);
			throw new CacheException(e.getMessage());
		} catch (net.sf.ehcache.CacheException e) {
			logger.error(e);
			throw new CacheException(e.getMessage());
		} catch (CacheException e) {
			logger.error(e);
			throw new CacheException(e.getMessage());
		}
		
	}

	public Cache createCache(String cacheName,int maxElementsInMemory,boolean overflowToDisk,boolean eternal,long timeToLiveSeconds,long timeToIdleSeconds )
			throws CacheException {
		Cache cache = new CacheImpl(cacheName,maxElementsInMemory,overflowToDisk,eternal,timeToLiveSeconds,timeToIdleSeconds);
		addCache(cache);
		return cache;
	}

	public Cache getCache(String cacheName) {
		net.sf.ehcache.Cache cache = ehcacheManager.getCache(cacheName);
		return new CacheImpl(cache);
	}

	public void removalAll() {
		ehcacheManager.removalAll();
	}

	public void removeCache(String cacheName) {
		ehcacheManager.removeCache(cacheName);
	}

	public void shutdown() {
		ehcacheManager.shutdown();
	}

}
