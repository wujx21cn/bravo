/* com.cutty.bravo.core.security.domain.Button.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-3-18 下午04:14:46, Created by Yeon
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cutty.bravo.core.domain.BaseDomain;

/**
 *
 * <p>
 * <a href="Button.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:YeonOrchid@gmail.com">Yeon</a>
 */

@Entity
@Table(name = "BRAVO_BUTTON")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ButtonResource extends BaseDomain {

	private static final long serialVersionUID = 925528040841723942L;
	
	private String name;
	private String operCondition;
	private String comments;
	private Set<Permission> permissions ;
	
	@Column(name = "BUTTON_NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "OPER_CONDITION")
	public String getOperCondition() {
		return operCondition;
	}
	public void setOperCondition(String operCondition) {
		this.operCondition = operCondition;
	}
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "bravo_button_permis", joinColumns = { @JoinColumn(name = "button_id") }, inverseJoinColumns = { @JoinColumn(name = "permis_id") })
	public Set<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

}
