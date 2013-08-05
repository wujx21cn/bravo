/* com.cutty.bravo.components.concurrent.pool.Client.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2012-2-16 下午12:07:57, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.concurrent.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 *
 *
 * <p>
 * <a href="Client.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class Client {
    /**   
     * 
     * 线程池大小为10，初始化执行一次，随后并发三个验证   
     */   
    public static void main(String[] args) {    
        ThreadPoolService threadPoolService = new ThreadPoolService(10);    
        ValidationService validationService = new ValidationService(threadPoolService);    
        List<String> entries = new ArrayList<String>();    
        CountDownLatch signal = new CountDownLatch(3);    
        long start;    
        long stop;    
   
        for (Node node : MockNodeValidator.ENTRIES) {    
            entries.add(node.getWsdl());    
        }    
   
        start = System.currentTimeMillis();    
   
        validationService.validate(entries);    
        threadPoolService.execute(new ValidationStarter(entries, validationService, signal));    
        threadPoolService.execute(new ValidationStarter(entries, validationService, signal));    
        threadPoolService.execute(new ValidationStarter(entries, validationService, signal));    
   
        try {    
            signal.await();    
        } catch (InterruptedException e) {    
            e.printStackTrace();    
        }    
   
        stop = System.currentTimeMillis();    
        threadPoolService.destoryExecutorService(1000);    
        System.out.println("实际执行验证次数: " + MockNodeValidator.getCount());    
        System.out.println("实际执行时间: " + (stop - start) + "ms");    
    }    
   
}
