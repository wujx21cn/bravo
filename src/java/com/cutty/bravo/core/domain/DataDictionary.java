/* com.cutty.bravo.core.domain.DataDictionary.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-12-4 上午11:08:25, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
*
* <p>
* <a href="DataDictionary.java.html"><i>View Source</i></a>
* </p>
*
* @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
*/

@Entity
@Table(name = "bravo_data_dictionary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DataDictionary extends BaseDomain{
	private static final long serialVersionUID = -1245220894058858052L;
	
	private String tableName;
	private String entityName;
	private String packageName;
	private String entityTitle;
	private Set<DataDictionaryDetail> dataDictionaryDetails;
	
	@Column(name = "table_name")
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	@Column(name = "entity_name")
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	@Column(name = "package_name")
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	@Column(name = "entity_title")
	public String getEntityTitle() {
		return entityTitle;
	}
	public void setEntityTitle(String entityTitle) {
		this.entityTitle = entityTitle;
	}
	
	@OneToMany(mappedBy = "dataDictionary", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = DataDictionaryDetail.class) 
	@OrderBy("id")
	public Set<DataDictionaryDetail> getDataDictionaryDetails() {
		return dataDictionaryDetails;
	}
	public void setDataDictionaryDetails(
			Set<DataDictionaryDetail> dataDictionaryDetails) {
		this.dataDictionaryDetails = dataDictionaryDetails;
	}
}
