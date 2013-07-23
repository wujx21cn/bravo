/* com.cutty.bravo.core.ui.tags.button.component.M2MRemoveButtonBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-29 上午11:35:06, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.button.component;

/**
 * 
 * <p>EXT标签M2MRemoveButton属性集合类</p>
 * <p>
 * <a href="M2MRemoveButtonBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class M2MRemoveButtonBean extends ButtonBean{
	private static final long serialVersionUID = -6871673351145069035L;
	private String dataProxy="${bravoHome}/ui/dataManageMent!m2mDataManageMent.action";
	private  String originGridName ;
	private  String entityName;
	private  String fieldName;
	private  String entityId;
	/**
	 * @return the dataProxy
	 */
	public String getDataProxy() {
		return dataProxy;
	}
	/**
	 * @param dataProxy the dataProxy to set
	 */
	public void setDataProxy(String dataProxy) {
		this.dataProxy = dataProxy;
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
