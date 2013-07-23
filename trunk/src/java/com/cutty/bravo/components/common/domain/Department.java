/* com.cutty.bravo.components.common.domain.Department.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-9 上午08:17:36, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.domain;

import java.util.List;

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
import com.cutty.bravo.core.security.domain.User;

/**
 *
 * <p>
 * <a href="Department.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Entity
@Table(name = "bravo_department")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Department extends BaseDomain {
	private static final long serialVersionUID = 5796311522976156313L;

	private String code;
	private Integer sequences;
	private String name;
	private String address;
	private String postalcode;
	private String telephone;
	private String fax;
	private Department parentDepartment;
	private User manager;
	private String comments;
	private List<User> staff;	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_dep",referencedColumnName="id")		
	public Department getParentDepartment() {
		return parentDepartment;
	}
	public void setParentDepartment(Department parentDepartment) {
		this.parentDepartment = parentDepartment;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public Integer getSequences() {
		return sequences;
	}
	public void setSequences(Integer sequences) {
		this.sequences = sequences;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = User.class) 
	@OrderBy("lastUpdater")
	public List<User> getStaff() {
		return staff;
	}
	public void setStaff(List<User> staff) {
		this.staff = staff;
	}
	/**
	 * @return the manager
	 */
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manager",referencedColumnName="id")			
	public User getManager() {
		return manager;
	}
	/**
	 * @param manager the manager to set
	 */
	public void setManager(User manager) {
		this.manager = manager;
	}
}
