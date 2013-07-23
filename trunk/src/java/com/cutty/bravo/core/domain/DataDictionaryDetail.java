/* com.cutty.bravo.core.domain.DataDictionaryDetail.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-12-4 上午11:08:25, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * <p>
 * <a href="DataDictionaryDetail.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Entity
@Table(name = "bravo_data_dictionary_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DataDictionaryDetail extends BaseDomain {
	private static final long serialVersionUID = -2038739185000291352L;
	
	private DataDictionary dataDictionary;
	private String tableFieldName;
	private String tableFieldType;
	private String entityFieldName;
	private String entityFieldType;
	private String entityFieldTitle;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "data_dictionary",referencedColumnName="id")
	public DataDictionary getDataDictionary() {
		return dataDictionary;
	}
	public void setDataDictionary(DataDictionary dataDictionary) {
		this.dataDictionary = dataDictionary;
	}
	

	@Column(name = "table_field_name")
	public String getTableFieldName() {
		return tableFieldName;
	}
	public void setTableFieldName(String tableFieldName) {
		this.tableFieldName = tableFieldName;
	}
	

	@Column(name = "table_field_type")
	public String getTableFieldType() {
		return tableFieldType;
	}
	public void setTableFieldType(String tableFieldType) {
		this.tableFieldType = tableFieldType;
	}

	@Column(name = "entity_field_name")
	public String getEntityFieldName() {
		return entityFieldName;
	}
	
	public void setEntityFieldName(String entityFieldName) {
		this.entityFieldName = entityFieldName;
	}

	@Column(name = "entity_field_type")
	public String getEntityFieldType() {
		return entityFieldType;
	}
	
	public void setEntityFieldType(String entityFieldType) {
		this.entityFieldType = entityFieldType;
	}

	@Column(name = "entity_field_title")
	public String getEntityFieldTitle() {
		return entityFieldTitle;
	}
	
	public void setEntityFieldTitle(String entityFieldTitle) {
		this.entityFieldTitle = entityFieldTitle;
	}

}
