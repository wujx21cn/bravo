/* com.cutty.bravo.components.common.domain.Enumeration.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-9 上午07:52:29, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cutty.bravo.core.domain.BaseDomain;
import com.cutty.bravo.core.domain.annotation.AuditEntity;
/**
 *
 * <p>
 * <a href="Enumeration.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
@Entity
@Table(name = "bravo_enumeration")
//@AuditEntity(fieldNames = {"name","code","comments"})
public class Enumeration  extends BaseDomain{
	private static final long serialVersionUID = 5741029506550860037L;

	private EnumerationType enumType;
	private Integer sequences;
	private String name;
	private String code;
	private String comments;
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENUM_TYPE",referencedColumnName="id")	
	public EnumerationType getEnumType() {
		return enumType;
	}
	public void setEnumType(EnumerationType enumType) {
		this.enumType = enumType;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSequences() {
		return sequences;
	}
	public void setSequences(Integer sequences) {
		this.sequences = sequences;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
}
