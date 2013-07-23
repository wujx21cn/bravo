/* com.cutty.bravo.components.common.domain.EnumerationType.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-9 上午07:54:04, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.cutty.bravo.core.domain.BaseDomain;
import com.cutty.bravo.core.domain.annotation.UniquePropertyEntity;

/**
 *
 * <p>
 * <a href="EnumerationType.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Entity
@Table(name = "bravo_enumeration_type")
@UniquePropertyEntity(fieldNames={"name"})
public class EnumerationType  extends BaseDomain{

	private static final long serialVersionUID = 6800829433932441200L;
	private String name;
	private String comments;
	private Set<Enumeration> childEnum;
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(mappedBy = "enumType", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Enumeration.class) 
	@OrderBy("sequences")
	public Set<Enumeration> getChildEnum() {
		return childEnum;
	}
	public void setChildEnum(Set<Enumeration> childEnum) {
		this.childEnum = childEnum;
	}
}
