/* com.cutty.bravo.core.dao.sql.operationWrapper.DeleteSQLWrapper.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Mar 3, 2009 11:10:20 AM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.dao.sql.operationWrapper;

/**
 * 保存删除操作的原生sql， 一条delete语句（即一个xml文件中的<delete>标签)对应一个wrapper,
 * 参数id为<id>标签的值,sqlStr为<delete>标签的内容
 * <p>
 * <a href="DeleteSQLWrapper.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class DeleteSQLWrapper {

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
