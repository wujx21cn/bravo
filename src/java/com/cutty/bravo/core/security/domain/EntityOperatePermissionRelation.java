/* com.cutty.bravo.core.security.domain.EntityOperPermisRelation.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-3-18 下午05:12:57, Created by Yeon
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cutty.bravo.core.domain.BaseDomain;

/**
 *
 * <p>
 * <a href="EntityOperPermisRelation.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:YeonOrchid@gmail.com">Yeon</a>
 */

@Entity
@Table(name = "bravo_entity_oper_permis")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EntityOperatePermissionRelation extends BaseDomain {

	private static final long serialVersionUID = 4231333675486264710L;

	private Permission permission;
	private String entityName;
	private String operType;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "permis_id",referencedColumnName="id")
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	
	@Column(name = "entity_name")
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	@Column(name = "oper_type")
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
}
