/* com.cutty.bravo.core.security.domain.Module.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-9 上午07:57:51, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cutty.bravo.core.domain.BaseDomain;

/**
 * 该类为系统模块表BRAVO_MODULE的实体类
 * <p>
 * <a href="Module.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Entity
@Table(name = "bravo_module")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

public class Module extends BaseDomain {

	private static final long serialVersionUID = 981298142320021058L;
	private Module parent;
    private String title;
    private String comments;
    private Set<Resource> resources ;
    private Set<Module> childmodules ;



    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Module.class) 
	@OrderBy("id")
    public Set<Module> getChildmodules() {
		return childmodules;
	}


	public void setChildmodules(Set<Module> childmodules) {
		this.childmodules = childmodules;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent",referencedColumnName="id")
	public Module getParent() {
		return parent;
	}

	public void setParent(Module parent) {
		this.parent = parent;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}
	
	@OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Resource.class) 
	@OrderBy("id")
	public Set<Resource> getResources() {
		return resources;
	}


}
