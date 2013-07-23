/* com.cutty.bravo.core.security.domain.Permission.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-9 上午08:07:29, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cutty.bravo.components.common.domain.MenuFunction;
import com.cutty.bravo.core.domain.BaseDomain;
import com.cutty.bravo.core.domain.annotation.M2MEntity;

/**
 * 该类为系统权限表BRAVO_PERMISSION的实体类
 * <p>
 * <a href="Permission.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Entity
@Table(name = "bravo_permission")
@Cache(usage = CacheConcurrencyStrategy.NONE)
@M2MEntity(actionClasses = {"com.cutty.bravo.core.security.manager.Permission2RoleTrigger",
							"com.cutty.bravo.core.security.manager.Permission2ResourceTrigger",
							"com.cutty.bravo.core.security.manager.Permission2ButtonResourceTrigger",
							"com.cutty.bravo.core.security.manager.Permission2EntityOperationTrigger"},
			  fieldNames = {"roles","resources","buttonResources","entityOperatePermissionRelations"})
public class Permission extends BaseDomain {
	
	private static final long serialVersionUID = -4575868689098898990L;
    private String name;
    private String comments;
    private Set<Role> roles;
    private Set<Resource> resources;
    private Set<MenuFunction> menuFunctions;
    private Set<ButtonResource> buttonResources;
    private Set<EntityOperatePermissionRelation> entityOperatePermissionRelations ;

    /**
     * default constructor
     */
    public Permission() {
    }

    /**
     * minimal constructor
     */
    public Permission(String name) {
        this.name = name;
    }

    /**
     * full constructor
     */
    public Permission(String name, String comments, String operation,
                      String status, Set<Role> roles, Set<Resource> resources, Set<MenuFunction> menuFunctions) {
        this.name = name;
        this.comments = comments;
        this.roles = roles;
        this.resources = resources;
        this.menuFunctions = menuFunctions;
    }



    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 从中间表BRAVO_ROLE_PERMIS中获取当前Permission所面向的Role，放在集合对象roles中
     * @return
     */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "bravo_role_permis", joinColumns = { @JoinColumn(name = "permis_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })	
    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * 从中间表BRAVO_PERMIS_RESC中获取当前Permission所面向的Resource，放在集合对象resources中
     * @return
     */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "bravo_permis_resc", joinColumns = { @JoinColumn(name = "PERMIS_ID") }, inverseJoinColumns = { @JoinColumn(name = "RESC_ID") })	
    public Set<Resource> getResources() {
        return this.resources;
    }

	/**
	 * 从中间表BRAVO_FUN_PERMIS中获取当前Permission所面向的Function，放在集合对象MenuFunctions里面
	 * @return
	 */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "bravo_fun_permis", joinColumns = { @JoinColumn(name = "PER_ID") }, inverseJoinColumns = { @JoinColumn(name = "SYS_ID") })	
	public Set<MenuFunction> getMenuFunctions() {
		return menuFunctions;
	}

	/**
	 * @param menuFunctions the menuFunctions to set
	 */
	public void setMenuFunctions(Set<MenuFunction> menuFunctions) {
		this.menuFunctions = menuFunctions;
	}

	public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
     * 从中间表BRAVO_BUTTON_PERMIS中获取当前Permission所面向的Button，放在集合对象buttons中
     * @return
     */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "bravo_button_permis", joinColumns = { @JoinColumn(name = "permis_id") }, inverseJoinColumns = { @JoinColumn(name = "button_id") })	
	public Set<ButtonResource> getButtonResources() {
		return buttonResources;
	}
	public void setButtonResources(Set<ButtonResource> buttonResources) {
		this.buttonResources = buttonResources;
	}

	@OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = EntityOperatePermissionRelation.class)
	public Set<EntityOperatePermissionRelation> getEntityOperatePermissionRelations() {
		return entityOperatePermissionRelations;
	}
	public void setEntityOperatePermissionRelations(Set<EntityOperatePermissionRelation> entityOperatePermissionRelations) {
		this.entityOperatePermissionRelations = entityOperatePermissionRelations;
	}
}
