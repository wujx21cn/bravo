/* com.cutty.bravo.components.concurrent.ArrayBlockingQueue.TestMain.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Nov 11, 2010 3:53:43 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 cutty Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.concurrent.ArrayBlockingQueue;


import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * <p>
 * <a href="TestMain.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class TestMain {
    public static void main(String[] args) throws InterruptedException { 

        //timer queuen
        ArrayBlockingQueue<TimerTask> queue1 = new ArrayBlockingQueue<TimerTask>(1000, true); 
        //treat quenen
        ArrayBlockingQueue<TimerTask> queue2 = new ArrayBlockingQueue<TimerTask>(1000, true); 
        //can add CheckThread DisposeThread to improve the speed of process
        Thread t1 = new Thread(new AddThread(queue1)); 
        Thread t2 = new Thread(new CheckThread(queue1, queue2)); 
        Thread t3 = new Thread(new DisposeThread(queue2, new TestMain())); 
        t1.start(); 
        t2.start(); 
        t3.start(); 
    } 
    
    public void test(){
    	System.out.println("stupid pig!!!");
    }

    
} 
class AddThread implements Runnable { 
    private ArrayBlockingQueue<TimerTask> queue; 
    public AddThread(ArrayBlockingQueue<TimerTask> queue) { 
        this.queue = queue; 
    } 

    public void run() { 
        while (true) { 
            for (int i = 0; i < 500; i++) { 
                int time = 0; 
                if(i%3==0){ 
                    time = 60*1000; 
                }else if(i%3==1){ 
                    time = 2*60*1000; 
                }else if(i%3==2){ 
                    time = 5*60*1000; 
                } 
                TimerTask tt = new TimerTask(i, System.currentTimeMillis() + time); 
                try { 
                    queue.put(tt); 
                    System.out.println("add time task" + i); 
                } catch (InterruptedException e) { 
                    e.printStackTrace(); 
                } 
            } 
            try { 
                Thread.sleep(10 * 60 * 1000); 
            } catch (InterruptedException e) { 
                e.printStackTrace(); 
            } 
        } 
    } 
} 

/** 
 * 此线程扫描计时任务队列，检查计时是否完成 
 */ 
class CheckThread implements Runnable { 
    ArrayBlockingQueue<TimerTask> queue1; 
    ArrayBlockingQueue<TimerTask> queue2; 
    public CheckThread(ArrayBlockingQueue<TimerTask> queue1, ArrayBlockingQueue<TimerTask> queue2) { 
        this.queue1 = queue1; 
        this.queue2 = queue2; 
    } 

    public void run() { 
        while (true) { 
            for (int i = 0; i < 100; i++) { 
                TimerTask tt = null; 
                try { 
                    tt = queue1.take(); 
                    System.out.println("检查计时任务" + tt.getRoleID()); 
                    if (tt.getEndTime() <= System.currentTimeMillis()) { 
                    	System.out.println("添加执行任务" + tt.getRoleID()); 
                        queue2.put(tt); 
                    } else { 
                        queue1.put(tt); 
                    } 
                } catch (InterruptedException e) { 
                    e.printStackTrace(); 
                } 
            } 
            try { 
                Thread.sleep(100); 
            } catch (InterruptedException e) { 
                e.printStackTrace(); 
            } 
        } 
    } 
} 

/** 
 * 此线程从计时任务完成的队列中不断取出计时任务并执行逻辑 
 */ 
class DisposeThread implements Runnable { 
    TestMain tm; 
    ArrayBlockingQueue<TimerTask> queue2; 
    public DisposeThread(ArrayBlockingQueue<TimerTask> queue2,TestMain tm) { 
        this.queue2 = queue2; 
        this.tm = tm; 
    } 
    public void run() { 
        while (true) { 
            TimerTask tt = null; 
            try { 
                tt = queue2.take(); 
                System.out.println("提取处理任务" + tt.getRoleID()); 
                tm.test(); 
            } catch (InterruptedException e) { 
                e.printStackTrace(); 
            } 
            if (tt != null) { 
                System.out.println(tt.getRoleID() + ":" + (tt.getEndTime() - System.currentTimeMillis())); 
            } 
        } 
    } 
} 

/** 
 * time task
 */ 
class TimerTask { 
    private Integer roleID; 
    private Long endTime; 
    public TimerTask(Integer roleID, Long endTime) { 
        this.roleID = roleID; 
        this.endTime = endTime; 
    } 

    public Integer getRoleID() { 
        return roleID; 
    } 

    public void setRoleID(Integer roleID) { 
        this.roleID = roleID; 
    } 

    public Long getEndTime() { 
        return endTime; 
    } 

    public void setEndTime(Long endTime) { 
        this.endTime = endTime; 
    } 

}
