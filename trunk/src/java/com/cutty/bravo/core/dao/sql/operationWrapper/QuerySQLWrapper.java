/* com.cutty.bravo.core.dao.sql.QuerySQLWrapper.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Mar 2, 2009 2:54:00 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.dao.sql.operationWrapper;

import com.cutty.bravo.core.dao.sql.ResultMap;

/**
 * 该类存放xml的<query>标签的所有信息;
 * 属性包括：
 * id，自定义的<id>标签得到的值；
 * ResultMap，查询的结果集，从<resultmap>标签得到；
 * resultClass，从<query>标签的resultClass属性得到; 
 * queryStr,存放查询的原生sql语句，如果有参数，存放的为freemarker解析前的String。
 * <p>
 * <a href="QuerySQLWrapper.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class QuerySQLWrapper {
	
	private String id;
	private ResultMap resultMap;
	private String resultClass;
	private String queryStr;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ResultMap getResultMap() {
		return resultMap;
	}
	public void setResultMap(ResultMap resultMap) {
		this.resultMap = resultMap;
	}
	public String getResultClass() {
		return resultClass;
	}
	public void setResultClass(String resultClass) {
		this.resultClass = resultClass;
	}
	public String getQueryStr() {
		return queryStr;
	}
	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}
	
	
}
