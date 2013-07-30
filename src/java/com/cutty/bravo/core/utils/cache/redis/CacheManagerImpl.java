package com.cutty.bravo.core.utils.cache.redis;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.cutty.bravo.core.exception.CacheException;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.utils.cache.Cache;
import com.cutty.bravo.core.utils.cache.CacheManager;

public class CacheManagerImpl implements CacheManager{

	private static final Log logger = LogFactory.getLog(CacheManagerImpl.class);
	
	//诡异，spring没有注入，赶时间，先手动注入
	private ShardedJedisPool shardedJedisPool=(ShardedJedisPool) ApplicationContextKeeper.getAppCtx().getBean("shardedJedisPool");
	private Set<Cache> caches = new HashSet<Cache>();
	
	public CacheManagerImpl() throws CacheException{
		this.createCache("BRAVO_USER_CACHE", 0, true, true, 0, 0);
		this.createCache("BRAVO_RESOURCE_CACHE", 0, true, true, 0, 0);
		this.createCache("BRAVO_BUTTONRESOURCE_CACHE", 0, true, true, 0, 0);
		this.createCache("BRAVO_ENTITY_OPERATION_CACHE", 0, true, true, 0, 0);
		this.createCache("BRAVO_PAGE_CACHE", 0, true, true, 0, 0);
		this.createCache("org.hibernate.cache.StandardQueryCache", 0, true, true, 0, 0);
		this.createCache("org.hibernate.cache.UpdateTimestampsCache", 0, true, true, 0, 0);
		
	}
	@Override
	public Cache createCache(String cacheName, int maxElementsInMemory,
			boolean overflowToDisk, boolean eternal, long timeToLiveSeconds,
			long timeToIdleSeconds) throws CacheException {
		CacheImpl retVal = new CacheImpl(cacheName);
		retVal.setShardedJedisPool(shardedJedisPool);
		caches.add(retVal);
		return retVal;
	}

	@Override
	public Cache getCache(String cacheName) {
		for (Cache cache:caches)  {
			if (cacheName.equals(cache.getName())) return cache;
		}
		return null;
	}

	@Override
	public void addCache(Cache cache) throws CacheException {
		caches.add(cache);
		return;
		
	}

	@Override
	public void removeCache(String cacheName) {
		for (Cache cache:caches) 
			if (cacheName.equals(cache.getName())) return;
		ShardedJedis shardedJedis = null;
		try {
			String prefixKey = cacheName + CacheImpl.REDIS_GROUP_DELIMITER + "*";
			shardedJedis =shardedJedisPool.getResource();
			Collection<Jedis> jedises=shardedJedis.getAllShards();
			for (Jedis jedis:jedises){
				Set<String> keys=jedis.keys(prefixKey);
				jedis.del(keys.toArray(new String[keys.size()]));
			}
		} finally {
			if (null != shardedJedis) {
				shardedJedisPool.returnResource(shardedJedis);
			}
		}

		
	}

	@Override
	public void removalAll() {
		for (Cache cache:caches)  {
			removeCache(cache.getName());
		}
		
	}

	@Override
	public void shutdown() {
		shardedJedisPool.destroy();
	}


	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}

	
	
}
