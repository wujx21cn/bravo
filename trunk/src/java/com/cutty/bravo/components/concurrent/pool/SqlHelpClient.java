/* com.cutty.bravo.components.concurrent.pool.SqlHelpClient.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Jun 15, 2013 10:53:31 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.concurrent.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * <p>
 * 
 * <a href="SqlHelpClient.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class SqlHelpClient {

	public static final String URL="jdbc:mysql://192.168.230.128/bravo?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=utf-8";
	public static final String USER_NAME="root";
	public static final String PASSWORD="root";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		ExecutorService  executorService = Executors.newFixedThreadPool(50); 
		List<InsertSqlTask> InsertSqlTaskList = new ArrayList<InsertSqlTask>();
		for (int i=0;i<100000;i++) {
			InsertSqlTaskList.add(new InsertSqlTask("Thread"+i,1000000000));
		}
		try {
			executorService.invokeAll(InsertSqlTaskList);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
