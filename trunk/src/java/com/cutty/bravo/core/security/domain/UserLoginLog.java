/* com.cutty.bravo.core.security.domain.UserLoginLog.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-2-26 下午12:09:45, Created by Yeon
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.domain;



import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cutty.bravo.core.domain.BaseDomain;


/**
 *
 * <p>
 * <a href="UserLoginLog.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:YeonOrchid@gmail.com">Yeon</a>
 */
@Entity
@Table(name = "bravo_user_login_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserLoginLog extends BaseDomain {

	private static final long serialVersionUID = -4629280965699924553L;

	private	String loginId;
	private	String chnName;
	private	String department;
	private String loginIp;
	private Timestamp loginTime;
	private Timestamp logoutTime;
	
	private String sessionId;
	
	@Transient
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	@Column(name="login_id")
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	@Column(name="chn_name")
	public String getChnName() {
		return chnName;
	}
	public void setChnName(String chnName) {
		this.chnName = chnName;
	}
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	@Column(name="login_ip")
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	@Column(name="login_time")
	public Timestamp getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}
	
	@Column(name="logout_time")
	public Timestamp getLogoutTime() {
		return logoutTime;
	}
	public void setLogoutTime(Timestamp logoutTime) {
		this.logoutTime = logoutTime;
	}
}
