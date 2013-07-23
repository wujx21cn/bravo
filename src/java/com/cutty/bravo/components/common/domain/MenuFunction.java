/* com.cutty.bravo.components.common.domain.MenuFunction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-21 上午01:55:44, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cutty.bravo.core.domain.BaseDomain;
import com.cutty.bravo.core.security.domain.Permission;

/**
 *
 * <p>
 * <a href="MenuFunction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Entity
@Table(name = "bravo_menu_function")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class MenuFunction extends BaseDomain {
	
	private static final long serialVersionUID = 6446823585407211237L;
	
	private MenuFunction parentMenuFunction;
	private Set<MenuFunction> childMenuFunction;
	private Integer sequences;
	private String name;
	private String comments;
	private String action;
	private String iconSrc;
	
	//checked字段是非持久化字段，用来根据某个用户的权限去标志它是否可读该menuFunction,值为"true"or"false".
	//直接作用决定了treenode里的checked属性是true还是false.
	private String checked;
    private Set<Permission> permissions;
    
    @Transient
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	@Column(name = "ICON_SRC")
	public String getIconSrc() {
		return iconSrc;
	}
	public void setIconSrc(String iconSrc) {
		this.iconSrc = iconSrc;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "PARENT_FUN",referencedColumnName="id")
	public MenuFunction getParentMenuFunction() {
		return parentMenuFunction;
	}
	public void setParentMenuFunction(MenuFunction parentMenuFunction) {
		this.parentMenuFunction = parentMenuFunction;
	}
	public Integer getSequences() {
		return sequences;
	}
	public void setSequences(Integer sequences) {
		this.sequences = sequences;
	}
	
	@OneToMany(mappedBy = "parentMenuFunction", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = MenuFunction.class) 
	@OrderBy("sequences")
	public Set<MenuFunction> getChildMenuFunction() {
		return childMenuFunction;
	}
	public void setChildMenuFunction(Set<MenuFunction> childMenuFunction) {
		this.childMenuFunction = childMenuFunction;
	}
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "bravo_fun_permis", joinColumns = { @JoinColumn(name = "SYS_ID") }, inverseJoinColumns = { @JoinColumn(name = "PER_ID") })
	public Set<Permission> getPermissions() {
		return permissions;
	}
	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	
}
