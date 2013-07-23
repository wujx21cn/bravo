/* com.cutty.bravo.components.concurrent.pool.BoundedBuffer.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Nov 11, 2010 2:56:54 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 cutty Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.concurrent.pool;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * <p>
 * 
 * <a href="BoundedBuffer.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class BoundedBuffer {
	  final Lock lock = new ReentrantLock();    
	  final Condition notFull  = lock.newCondition();     
	  final Condition notEmpty = lock.newCondition();     
	   
	  final Object[] items = new Object[100];    
	  int putptr, takeptr, count;    
	   
	  public void put(Object x) throws InterruptedException {    
	    lock.lock();    
	    try {    
	      while (count == items.length)     
	        notFull.await();    
	      items[putptr] = x;     
	      if (++putptr == items.length) putptr = 0;    
	      ++count;    
	      notEmpty.signal();    
	    } finally {    
	      lock.unlock();    
	    }    
	  }    
	   
	  public Object take() throws InterruptedException {    
	    lock.lock();    
	    try {    
	      while (count == 0)     
	        notEmpty.await();    
	      Object x = items[takeptr];     
	      if (++takeptr == items.length) takeptr = 0;    
	      --count;    
	      notFull.signal();    
	      return x;    
	    } finally {    
	      lock.unlock();    
	    }    
	  }     

}
