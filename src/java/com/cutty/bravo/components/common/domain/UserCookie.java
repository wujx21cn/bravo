/* com.cutty.bravo.components.common.domain.UserCookie.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-11-27 下午03:57:27, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cutty.bravo.core.domain.BaseDomain;
import com.cutty.bravo.core.security.domain.User;

/**
 *
 * <p>
 * <a href="UserCookie.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Entity
@Table(name = "bravo_user_cookie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserCookie  extends BaseDomain {
	
	private static final long serialVersionUID = 8816361641393200724L;
	private String name;
	private String value;
	private String domain;
	private Integer maxAge;
	private String path;
	private String secure;
	private Integer version;
	private User user;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public Integer getMaxAge() {
		return maxAge;
	}
	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getSecure() {
		return secure;
	}
	public void setSecure(String secure) {
		this.secure = secure;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid",referencedColumnName="id")	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
