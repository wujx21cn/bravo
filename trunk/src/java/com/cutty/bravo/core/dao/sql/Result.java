/* com.cutty.bravo.core.dao.sql.Result.java

{{IS_NOTE
	Purpose:
	Description:
	History:
		Mar 3, 2009 4:06:23 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.dao.sql;

/**
 * 根据xml配置文件中的<ResutlMap>标签下的子标签<result>获得各属性值，映射到本类
 * 属性说明：
 * property, 对应pojo的属性名
 * column, 对应数据库的字段名
 * loadEntity, 当该属性是另一个对应到数据库的pojo时，是否装载其其他字段信息
 * entityClass, 实体类名.
 * <p>
 * <a href="Result.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class Result {
	
	private String property;
	private String column;
	private boolean loadEntity;
	private String entityClass;
	
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public boolean isLoadEntity() {
		return loadEntity;
	}
	public void setLoadEntity(boolean loadEntity) {
		this.loadEntity = loadEntity;
	}
	public String getEntityClass() {
		return entityClass;
	}
	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}
}
