/* com.cutty.bravo.core.dao.sql.ResultMap.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Mar 2, 2009 2:56:33 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.dao.sql;

import java.util.HashMap;
import java.util.Map;

/**
 * 该类存放xml的<resultmap>标签的信息，包括id，entityClass，
 * 及一个存放结果属性的map，该map根据<result>标签获得。
 * <p>
 * <a href="ResultMap.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class ResultMap {
	
	private String id;
	private String entityClass;
	private Map<String,Result> resultProperty = new HashMap<String,Result>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEntityClass() {
		return entityClass;
	}
	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}
	public Map<String, Result> getResultProperty() {
		return resultProperty;
	}
	public void setResultProperty(Map<String, Result> resultProperty) {
		this.resultProperty = resultProperty;
	}
}
