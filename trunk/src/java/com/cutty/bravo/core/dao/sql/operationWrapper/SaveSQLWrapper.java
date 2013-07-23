/* com.cutty.bravo.core.dao.sql.SaveSQLWrapper.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Mar 2, 2009 5:48:14 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.dao.sql.operationWrapper;

/**
 * 该wrapper存放操作为保存的sql语句，一条sql，即一个<save>标签对应着一个wrapper;
 * 属性：id 为定义在<id>标签中的值，sqlStr存放sql语句
 * <p>
 * <a href="SaveSQLWrapper.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class SaveSQLWrapper {

	private String id;
	private String sqlStr;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSqlStr() {
		return sqlStr;
	}
	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	
	
}
