package com.cutty.bravo.core.utils.cache.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;


import com.cutty.bravo.core.exception.CacheException;
import com.cutty.bravo.core.utils.SerializeUtils;
import com.cutty.bravo.core.utils.cache.Cache;

public class CacheImpl implements Cache{

	public static final String REDIS_GROUP_DELIMITER="|||:::";
	private String cacheName;
	private ShardedJedisPool shardedJedisPool ;
	//整个cache框架需要重构
	public CacheImpl(String cacheName){
		this.cacheName = cacheName;
	}
	
	//Object对象做key太麻烦，全部强制转换成String
	@Override
	public Object get(Object key) throws CacheException {
		ShardedJedis  shardedJedis=null;
		Object retVal = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			byte[] retArray = shardedJedis.get(SerializeUtils.serialize(REDIS_GROUP_DELIMITER+key.toString()));
			if (ArrayUtils.isEmpty(retArray))return retVal;
			retVal = ((ValueElement)SerializeUtils.unserialize(retArray)).getValue();
		} finally {
			if (null != shardedJedis) shardedJedisPool.returnResource(shardedJedis);
		}
		return retVal;
	}

	@Override
	public List<Object> getKeys() throws CacheException {
		List<Object>  retKeys = new ArrayList<Object> ();
		ShardedJedis  shardedJedis=null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			for (Jedis jedis : shardedJedis.getAllShards()) {
				Set<byte[]> keys = jedis.keys(SerializeUtils.serialize(REDIS_GROUP_DELIMITER+"*"));
				for (byte[] keyByte:keys){
					retKeys.add(SerializeUtils.unserialize(keyByte));
				}
			}
		} finally {
			if (null != shardedJedis) shardedJedisPool.returnResource(shardedJedis);
		}

		return retKeys;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return cacheName;
	}

	@Override
	public int getSize() throws CacheException {
		return getKeys() .size();
	}


	@Override
	public void put(Object key, Object value) throws CacheException {
		ShardedJedis  shardedJedis=null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			byte[] keyByte = SerializeUtils.serialize(REDIS_GROUP_DELIMITER+key.toString());
			byte[] valyeByte =  SerializeUtils.serialize(new ValueElement(value));
			if (ArrayUtils.isNotEmpty(keyByte) && ArrayUtils.isNotEmpty(valyeByte)) 
				shardedJedis.set(keyByte,valyeByte);
		} finally {
			if (null != shardedJedis) shardedJedisPool.returnResource(shardedJedis);
		}
	}

	@Override
	public void put(Object key, Object value, long version)
			throws CacheException {
		//jedis doesn't support version
		put(key,value);
		
	}

	@Override
	public boolean remove(Object key) throws CacheException {
		ShardedJedis  shardedJedis=null;
		Long relCounnt =0L;
		try {
			shardedJedis = shardedJedisPool.getResource();
			byte[] keyByte = SerializeUtils.serialize(REDIS_GROUP_DELIMITER+key.toString());
			Jedis jedis =shardedJedis.getShard(keyByte);
			relCounnt = jedis.del(keyByte);
		} finally {
			if (null != shardedJedis) shardedJedisPool.returnResource(shardedJedis);
		}
		if (relCounnt > 0 ) return true;
		return false;
	}

	@Override
	public void removeAll() throws CacheException {
		ShardedJedis  shardedJedis=null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			for (Jedis jedis : shardedJedis.getAllShards()) {
				Set<String> keys = jedis.keys(REDIS_GROUP_DELIMITER+"*");
				jedis.del(keys.toArray(new String[keys.size()]));
			}
		} finally {
			if (null != shardedJedis) shardedJedisPool.returnResource(shardedJedis);
		}
	
		
	}

	@Override
	public void setName(String name) throws CacheException {
		this.cacheName = name;
		
	}

	@Override
	public void evictExpiredElements() {
		//不清楚redis有没相关的功能
		/*
		ShardedJedis  shardedJedis=null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			for (Jedis jedis : shardedJedis.getAllShards()) {
				jedis.d
			}
		} finally {
			if (null != shardedJedis) shardedJedisPool.returnResource(shardedJedis);
		}
		*/
	}

	@Override
	public void flush() throws CacheException {
		ShardedJedis  shardedJedis=null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			for (Jedis jedis : shardedJedis.getAllShards()) {
				jedis.flushAll();
			}
		} finally {
			if (null != shardedJedis) shardedJedisPool.returnResource(shardedJedis);
		}
		
	}

	@Override
	public Object getCacheObject() throws CacheException {
		// TODO Auto-generated method stub
		return shardedJedisPool;
	}

	@Override
	public Object getCache() {
		// TODO Auto-generated method stub
		return shardedJedisPool;
	}

	@Override
	public void setCache(Object cache) {
		if (cache instanceof ShardedJedisPool) this.shardedJedisPool = (ShardedJedisPool)cache;
		return;
	}
	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}
}
