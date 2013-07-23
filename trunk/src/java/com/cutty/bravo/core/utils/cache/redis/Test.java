package com.cutty.bravo.core.utils.cache.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import redis.clients.jedis.Jedis;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ExecutorService pool = Executors.newFixedThreadPool(30);
		Jedis jedis = new Jedis("192.168.42.130"); 
		List<testTask> taskList = new ArrayList<testTask>();
		for (int i=0;i<100;i++) {
			taskList.add(new testTask("Thread"+i,jedis));
		}
		try {
			pool.invokeAll(taskList);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
