/* com.cutty.bravo.core.security.domain.User.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-9 上午08:14:47, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cutty.bravo.components.common.domain.Department;
import com.cutty.bravo.components.common.domain.Enumeration;
import com.cutty.bravo.core.domain.BaseDomain;
import com.cutty.bravo.core.domain.annotation.M2MEntity;
import com.cutty.bravo.core.domain.annotation.UniquePropertyEntity;

/**
 * 该类为系统用户表BRAVO_USER的实体类
 * <p>
 * <a href="User.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Entity
@Table(name = "bravo_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@UniquePropertyEntity(fieldNames={"loginid"})
@M2MEntity(actionClasses = {"com.cutty.bravo.core.security.manager.User2RoleTrigger"},fieldNames={"roles"})
public class User extends BaseDomain {

	private static final long serialVersionUID = -3363272643527085018L;
	private String loginid;
	private String passwd;
	private String userName;
	private String engName;
	private Enumeration gender;
	private Enumeration duty;
	private String labour;
	private Date accession;
	private Department department;
	private Enumeration education;
	private Enumeration country;
	private Enumeration province;
	private Enumeration city;
	private String address;
	private String postalcode;
	private String telephone;
	private String mobilephone;
	private String email;
	private String fax;
	private String comments;
	private User lastUpdater;
	private Date lastUpdateDate;
    private String status;
	private Set<Role> roles;
	/**
	 * JPA Lob指定一个属性作为数据库支持的大对象类型在数据库中存储
	 * BLOB 二进制大对象，byte[]或者Serializable的类型可以指定为BLOB
	 * CLOB 字符型大对象，char[]、Character[]或String类型可以指定为CLOB
	 */
	@Lob
	@Column(name="photo")
	private byte[] photo;
	
	@Column(name = "accession_dt")
	public Date getAccession() {
		return accession;
	}
	public void setAccession(Date accession) {
		this.accession = accession;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "city",referencedColumnName="id")
	public Enumeration getCity() {
		return city;
	}
	public void setCity(Enumeration city) {
		this.city = city;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "country",referencedColumnName="id")
	public Enumeration getCountry() {
		return country;
	}
	public void setCountry(Enumeration country) {
		this.country = country;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "department",referencedColumnName="id")	
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "duty",referencedColumnName="id")
	public Enumeration getDuty() {
		return duty;
	}
	public void setDuty(Enumeration duty) {
		this.duty = duty;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "education",referencedColumnName="id")	
	public Enumeration getEducation() {
		return education;
	}
	public void setEducation(Enumeration education) {
		this.education = education;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "eng_name")
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "gender",referencedColumnName="id")	
	public Enumeration getGender() {
		return gender;
	}
	public void setGender(Enumeration gender) {
		this.gender = gender;
	}
	
	public String getLabour() {
		return labour;
	}
	public void setLabour(String labour) {
		this.labour = labour;
	}
	
	@Column(name = "last_updt")
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "last_updter",referencedColumnName="id")		
	public User getLastUpdater() {
		return lastUpdater;
	}
	public void setLastUpdater(User lastUpdater) {
		this.lastUpdater = lastUpdater;
	}
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)	
	@JoinColumn(name = "province",referencedColumnName="id")		
	public Enumeration getProvince() {
		return province;
	}
	public void setProvince(Enumeration province) {
		this.province = province;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "bravo_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	

    public boolean checkEnabled() {
        return (this.status != null && !this.status.equals("0"));
    }
    
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
}
