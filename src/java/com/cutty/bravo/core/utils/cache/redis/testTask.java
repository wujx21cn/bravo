package com.cutty.bravo.core.utils.cache.redis;

import java.util.concurrent.Callable;

import redis.clients.jedis.Jedis;

public class testTask implements Callable<Object> {
	Jedis jedis = null;
	String threadName = null;

	public testTask(String threadName,Jedis jedis){
		this.jedis = jedis;
		this.threadName = threadName;
	}

	@Override
	public Object call() throws Exception {
		System.out.println(threadName + ":::::::::start to work");
		for (int i=0;i<100;i++)
			jedis.set(threadName+i, threadName+"value:::::::111111111111111111111");
		System.out.println(threadName + ":::::::::finish  work");
		return null;
	}
	
}
