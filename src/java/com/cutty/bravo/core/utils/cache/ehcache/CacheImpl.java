/* com.cutty.bravo.core.utils.cache.ehcache.CacheImpl.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-15 上午01:06:16, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.utils.cache.ehcache;

import java.io.Serializable;
import java.util.List;

import net.sf.ehcache.Element;

import com.cutty.bravo.core.exception.CacheException;
import com.cutty.bravo.core.utils.cache.Cache;

/**
 *
 * <p>
 * <a href="CacheImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class CacheImpl implements Cache{
	
	private net.sf.ehcache.Cache cache;


    public CacheImpl(String name, int maxElementsInMemory, boolean overflowToDisk,
            boolean eternal, long timeToLiveSeconds, long timeToIdleSeconds) {
    	cache = new net.sf.ehcache.Cache(name,maxElementsInMemory,overflowToDisk,eternal,timeToLiveSeconds,timeToIdleSeconds);
    }
    
	public CacheImpl(net.sf.ehcache.Cache ehCache){
		cache = ehCache;
	}
	
	public void evictExpiredElements() {
		// TODO Auto-generated method stub
		cache.evictExpiredElements();
		
	}

	public void flush() throws CacheException {
		cache.flush();
		// TODO Auto-generated method stub
		
	}

	public Object get(Serializable key) throws CacheException {
		Element element = cache.get(key);
		if (null == element) return null;
		return element.getObjectValue();
	}

	public Object get(Object key) throws CacheException {
		Element element = cache.get(key);
		if (null == element) return null;
		return element.getObjectValue();
	}

	public List<Object> getKeys() throws CacheException {
		return(cache.getKeys());
	}

	public String getName() {
		return(cache.getName());
	}

	public int getSize() throws CacheException {
		return(cache.getSize());
	}

	public void put(Serializable key, Serializable value) throws CacheException {
		Element element = new Element(key, value);
		cache.put(element);
		
	}
	
	public void put(Serializable key, Serializable value, long version)
			throws CacheException {
		Element element = new Element(key, value);
		//暂时不实现version
		cache.put(element);
		
	}

	public void put(Object key, Object value) throws CacheException {
		Element element = new Element(key, value);
		cache.put(element);
		
	}

	public void put(Object key, Object value, long version)
			throws CacheException {
		Element element = new Element(key, value);
		cache.put(element);
		
	}
	
	public boolean remove(Serializable key) throws CacheException {
		return cache.remove(key);
	}

	public boolean remove(Object key) throws CacheException {
		return cache.remove(key);
	}

	public void removeAll() throws CacheException {
		cache.removeAll();
		
	}

	public void setName(String name) throws CacheException {
		cache.setName(name);
	}

	public Object getCacheObject() throws CacheException {
		return cache;
	}

	public Object getCache() {
		return cache;
	}

	public void setCache(Object cache) {
		this.cache = (net.sf.ehcache.Cache)cache;
	}

}