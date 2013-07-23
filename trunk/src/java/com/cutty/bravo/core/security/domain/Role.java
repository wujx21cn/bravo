/* com.cutty.bravo.core.security.domain.Role.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-9 上午08:10:12, Created by Jason.Wu
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
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cutty.bravo.core.domain.BaseDomain;
import com.cutty.bravo.core.domain.annotation.M2MEntity;

/**
 * 该类为系统角色表BRAVO_ROLE的实体类
 * <p>
 * <a href="Role.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Entity
@Table(name = "bravo_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@M2MEntity(actionClasses = {"com.cutty.bravo.core.security.manager.Role2PermissionTrigger","com.cutty.bravo.core.security.manager.Role2UserTrigger"},fieldNames={"permissions","users"})
public class Role extends BaseDomain {
	private static final long serialVersionUID = -6676036422244823889L;

    private String name;
    private String comments;
    private Set<User> users ;
    private Set<Permission> permissions ;

    // Constructors

    /**
     * default constructor
     */
    public Role() {
    }

    /**
     * minimal constructor
     */
    public Role(String name) {
        this.name = name;
    }

    /**
     * full constructor
     */
    public Role(String name, String comments, Set<User> users, Set<Permission> permissions) {
        this.name = name;
        this.comments = comments;
        this.users = users;
        this.permissions = permissions;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "bravo_user_role", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
    
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "bravo_role_permis", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "permis_id") })
    public Set<Permission> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

}
