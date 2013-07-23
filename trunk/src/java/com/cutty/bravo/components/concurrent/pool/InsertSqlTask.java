/* com.cutty.bravo.components.concurrent.pool.InsertSqlTask.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Jun 15, 2013 10:21:09 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.concurrent.pool;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.Callable;


/**
 *
 *
 * <p>
 * <a href="InsertSqlTask.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class InsertSqlTask implements Callable<Long>{
	
	private String threadName;
	private long rowSize;
	private static String sql = "INSERT insertTest(name,insert_time,row_num) VALUES(?,?,?)";
	public InsertSqlTask(String threadName,long rowSize){
		this.threadName = threadName;
		this.rowSize = rowSize;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public Long call() throws Exception {
		Connection conn = null;
		PreparedStatement prest = null;
		long batchInsertRow = 0 ;
		try {
			conn = DriverManager.getConnection(SqlHelpClient.URL, SqlHelpClient.USER_NAME, SqlHelpClient.PASSWORD);  
		    conn.setAutoCommit(false); 
		    prest = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		    for(int x = 0; x < rowSize; x++){  
		    	 batchInsertRow++;
		         prest.setString(1, threadName+":"+batchInsertRow);  
		         prest.setDate(2, new Date(new java.util.Date().getTime()));
		         prest.setLong(3, batchInsertRow);  
		         prest.addBatch();  
		         if (batchInsertRow % 1000 == 0) {
				     System.out.println(threadName+" insert rowNum===="+batchInsertRow);
		        	 prest.executeBatch();  
		             conn.commit();  
				     Thread.sleep(1000);
		         }
		      }  
		     prest.executeBatch();  
		     conn.commit();  
		     System.out.println(threadName+" finish...................");
		} catch(Exception e){
			System.out.println(e);
		}finally{
			if (prest != null) prest.close();
			if (conn != null) conn.close();
		}
		return batchInsertRow;
	}
	
	public String getThreadName() {
		return threadName;
	}
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	public long getRowSize() {
		return rowSize;
	}
	public void setRowSize(long rowSize) {
		this.rowSize = rowSize;
	}
	
	
	

}
