/* com.cutty.bravo.core.utils.cache.Cache.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-14 下午04:02:57, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.utils.cache;

import java.io.Serializable;
import java.util.List;

import com.cutty.bravo.core.exception.CacheException;

/**
 *
 * <p>
 * <a href="Cache.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public interface Cache extends Serializable{
	
	public static final int DEFAULT_MAX_ELEMENTS_IN_MEMORY=1000;
	public static final boolean DEFAULT_OVERFLOW_TO_DISK=true;
	public static final boolean DEFAULT_ETERNAL=true;
	public static final long DEFAULT_TIME_TO_LIVE_SECONDS=300;
	public static final long DEFAULT_TIME_TO_IDLE_SECONDS=4200;
	
	public Object get(Object key) throws CacheException; 
	
	public List getKeys() throws CacheException; 
	
	public String getName();
	
	public int getSize() throws CacheException; 
	
	public void put(Object key, Object value)throws CacheException;
	
	public void put(Object key, Object value, long version)throws CacheException;
	 
	public boolean remove(Object key) throws CacheException;
	
	public void removeAll() throws CacheException;
	
	public void setName(String name) throws CacheException ;
	
	public void evictExpiredElements();
	
	public void flush() throws  CacheException ;
	
	public Object getCacheObject() throws CacheException; 
	
	public Object getCache() ;

	public void setCache(Object cache) ;
}
