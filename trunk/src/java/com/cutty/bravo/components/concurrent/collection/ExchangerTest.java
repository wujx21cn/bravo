package com.cutty.bravo.components.concurrent.collection;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 可以在对中对元素进行配对和交换的线程的同步点。每个线程将条目上的某个方法呈现给 exchange 方法，
 * 与伙伴线程进行匹配，并且在返回时接收其伙伴的对象。Exchanger 可能被视为 SynchronousQueue 的双向形式。 Exchanger
 * 可能在应用程序（比如遗传算法和管道设计）中很有用。
 * 
 * @author lei 2011-11-15
 */
public class ExchangerTest {

	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		// 新建一个交换String对象的Exchanger
		final Exchanger<String> exchanger = new Exchanger<String>();
		for (int i = 1; i <= 5; i++) {
			service.execute(new Runnable() {
				public void run() {
					try {
						String data1 = "【" + Thread.currentThread().getName() + "的数据】";
						System.out.println("线程" + Thread.currentThread().getName() + "正在把数据" + data1 + "换出去");
						Thread.sleep((long) (Math.random() * 10000));
						// 等待另一个线程到达此交换点（除非当前线程被中断），然后将给定的对象传送给该线程，并接收该线程的对象
						String data2 = (String) exchanger.exchange(data1);
						System.out.println("线程" + Thread.currentThread().getName() + "换回的数据为" + data2);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		service.shutdown();
	}
}