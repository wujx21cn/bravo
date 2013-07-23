package com.cutty.bravo.components.concurrent.locks;

import java.io.IOException;

public class ThreadStopTest {

	public static void main(String[] args) {  
		  
        try {  
        	
            System.out.println("try");  
            Thread thread = new MyThread();  
            thread.start();  
            // thread.stop();  
            thread.interrupt();  
        } catch (Exception e) {  
            System.out.println("exception2");  
        } finally {  
            System.out.println("finally");  
        }  
    }  
}  
  
class MyThread extends Thread {  
  
    public void run() {  
            System.out.println("run");  
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				System.out.println("in Interrupted");
			}
			System.out.println("After Interrupted");
	
 
    }  
}  