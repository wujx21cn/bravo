/* com.cutty.bravo.core.security.domain.Resource.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-9 上午07:59:36, Created by Jason.Wu
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cutty.bravo.components.common.domain.Enumeration;
import com.cutty.bravo.core.domain.BaseDomain;

/**
 * 该类为系统资源表BRAVO_RESOURCE的实体类
 * <p>
 * <a href="Resource.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Entity
@Table(name = "BRAVO_RESOURCE")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class Resource  extends BaseDomain {
	private static final long serialVersionUID = 7490947215086256719L;
    private String name;

    private Module module;

    private Enumeration resType;

    private String resString;

    private String comments;
    
    private Set<Permission> permission ;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

	@ManyToOne
	@JoinColumn(name = "res_type",referencedColumnName="id")
    public Enumeration getResType() {
        return this.resType;
    }

    public void setResType(Enumeration resType) {
        this.resType = resType;
    }

	@Column(name = "res_string")
    public String getResString() {
        return this.resString;
    }

    public void setResString(String resString) {
        this.resString = resString;
    }

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "bravo_permis_resc", joinColumns = { @JoinColumn(name = "resc_id") }, inverseJoinColumns = { @JoinColumn(name = "permis_id") })
    public Set<Permission> getPermission() {
        return this.permission;
    }

    public void setPermission(Set<Permission> permissions) {
        this.permission = permissions;
    }

	@ManyToOne
	@JoinColumn(name = "module",referencedColumnName="id")
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
