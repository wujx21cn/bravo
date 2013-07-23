/* com.cutty.bravo.core.ui.tags.button.component.M2MSelectButtonBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-17 上午09:34:13, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.button.component;

/**
 * 该标签实现many2many弹出窗口选择子项功能.
 * <p> EXT标签M2MSelectButton属性集合类 </p>
 * <p>
 * <a href="M2MSelectButtonBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class M2MSelectButtonBean extends ButtonBean{
	private static final long serialVersionUID = 7551886758770042682L;

	//需要弹出的grid的URL地址
	private String targetProxy;
	//对应URL地址中grid的名称
	private String targetGridName;
	//调用的grid名
	private String originGridName;
	//需要增加many关系的entityName
	private String entityName;
	//entity中对应的many字段
	private String fieldName;
	//当前需要做m2m处理的实体ID
	private String entityId;

	/**
	 * @return the entityName
	 */
	public String getEntityName() {
		return entityName;
	}
	/**
	 * @param entityName the entityName to set
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}
	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	/**
	 * @return the targetProxy
	 */
	public String getTargetProxy() {
		return targetProxy;
	}
	/**
	 * @param targetProxy the targetProxy to set
	 */
	public void setTargetProxy(String targetProxy) {
		this.targetProxy = targetProxy;
	}
	/**
	 * @return the targetGridName
	 */
	public String getTargetGridName() {
		return targetGridName;
	}
	/**
	 * @param targetGridName the targetGridName to set
	 */
	public void setTargetGridName(String targetGridName) {
		this.targetGridName = targetGridName;
	}
	/**
	 * @return the originGridName
	 */
	public String getOriginGridName() {
		return originGridName;
	}
	/**
	 * @param originGridName the originGridName to set
	 */
	public void setOriginGridName(String originGridName) {
		this.originGridName = originGridName;
	}
	/**
	 * @return the entityId
	 */
	public String getEntityId() {
		return entityId;
	}
	/**
	 * @param entityId the entityId to set
	 */
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	
}
