/* com.cutty.bravo.core.utils.cache.CacheFactory.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-14 下午03:58:59, Created by Jason.Wu
}}IS_NOTE
Copyright (C) 2008 Bravo Corporation. All Rights Reserved.
*/
package com.cutty.bravo.core.utils.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.exception.CacheException;


/**
 * cache工厂,格式具体cache的实现框架,,,,
 * <p>
 * <a href="CacheFactory.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class CacheFactory {
	private static final Log logger = LogFactory.getLog(CacheFactory.class);
	
	private static CacheManager cacheManager ;
	
	private CacheFactory() {
	}
	
	public static final CacheManager getCacheManager()throws CacheException{
		if (null == cacheManager){
			String cacheManagerClassName = ConfigurableConstants.getProperty("cache.manager.class", "com.cutty.bravo.core.utils.cache.ehcache.CacheManagerImpl");
			try {
				cacheManager = (CacheManager)Class.forName(cacheManagerClassName).newInstance();
			} catch (InstantiationException e) {
				logger.error(e);
				throw new CacheException(e.getLocalizedMessage());
			} catch (IllegalAccessException e) {
				logger.error(e);
				throw new CacheException(e.getLocalizedMessage());
			} catch (ClassNotFoundException e) {
				logger.error(e);
				throw new CacheException(e.getLocalizedMessage());
			}
		}
		return cacheManager;
	}

}
