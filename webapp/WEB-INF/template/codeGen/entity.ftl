/* ${entityPath}.${entityName}.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		${createDate}, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2011 Cutty Corporation. All Rights Reserved.

*/
package ${entityPath};


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

<#list importPackages as importPackage>
import ${importPackage};
</#list>

import com.cutty.bravo.core.domain.BaseDomain;
/**
 *
 * <p>
 * <a href="${entityName}.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Entity
@Table(name = "${tableName}")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ${entityName} extends BaseDomain {
	
	private static final long serialVersionUID = 1L;
	<#list entityGenDomains as entityGenDomain>
	<#if entityGenDomain.entityFieldType == "M2O">private ${entityGenDomain.entityFieldRefShortName} ${entityGenDomain.eneityFieldName};
	<#elseif entityGenDomain.entityFieldType == "INT">private Integer ${entityGenDomain.eneityFieldName};
	<#elseif entityGenDomain.entityFieldType == "DATE">private Date ${entityGenDomain.eneityFieldName};
	<#elseif entityGenDomain.entityFieldType == "DOUBLE">private Double ${entityGenDomain.eneityFieldName};
	<#elseif entityGenDomain.entityFieldType == "FLOAT">private Float ${entityGenDomain.eneityFieldName};
	<#else>private String ${entityGenDomain.eneityFieldName};</#if>
	</#list>

	<#list entityGenDomains as entityGenDomain>
	<#if entityGenDomain.entityFieldType == "M2O">@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "${entityGenDomain.eneityColumnJoinName}",referencedColumnName="${entityGenDomain.eneityColumnRefName}")
	public ${entityGenDomain.entityFieldRefShortName} get${entityGenDomain.eneityFieldName?cap_first}() {
	<#else>
<#if entityGenDomain.eneityFieldName != entityGenDomain.eneityColumnName>@Column(name = "${entityGenDomain.eneityColumnName}")</#if>
	<#if entityGenDomain.entityFieldType == "DATE">public Date get${entityGenDomain.eneityFieldName?cap_first}() {
	<#elseif entityGenDomain.entityFieldType == "INT">public Integer get${entityGenDomain.eneityFieldName?cap_first}() {
	<#elseif entityGenDomain.entityFieldType == "DOUBLE">public Double get${entityGenDomain.eneityFieldName?cap_first}() {
	<#elseif entityGenDomain.entityFieldType == "FLOAT">public Float get${entityGenDomain.eneityFieldName?cap_first}() {
	<#else>public String get${entityGenDomain.eneityFieldName?cap_first}() {</#if></#if>
		return ${entityGenDomain.eneityFieldName};
	}
	<#if entityGenDomain.entityFieldType == "M2O">
	public void set${entityGenDomain.eneityFieldName?cap_first}(${entityGenDomain.entityFieldRefShortName} ${entityGenDomain.eneityFieldName}) {
	<#elseif entityGenDomain.entityFieldType == "DATE">public void set${entityGenDomain.eneityFieldName?cap_first}(Date ${entityGenDomain.eneityFieldName}) {
	<#elseif entityGenDomain.entityFieldType == "INT">public void set${entityGenDomain.eneityFieldName?cap_first}(Integer ${entityGenDomain.eneityFieldName}) {
	<#elseif entityGenDomain.entityFieldType == "DOUBLE">public void set${entityGenDomain.eneityFieldName?cap_first}(Double ${entityGenDomain.eneityFieldName}) {
	<#elseif entityGenDomain.entityFieldType == "FLOAT">public void set${entityGenDomain.eneityFieldName?cap_first}(Float ${entityGenDomain.eneityFieldName}) {
	<#else>public void set${entityGenDomain.eneityFieldName?cap_first}(String ${entityGenDomain.eneityFieldName}) {
	</#if>
		this.${entityGenDomain.eneityFieldName} = ${entityGenDomain.eneityFieldName};
	}
	</#list>
}
