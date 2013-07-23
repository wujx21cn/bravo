/* com.cutty.bravo.components.common.domain.SystemInformation.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-3-19 下午02:15:08, Created by Yeon
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cutty.bravo.core.domain.BaseDomain;

/**
 *
 * <p>
 * <a href="SystemInformation.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:YeonOrchid@gmail.com">Yeon</a>
 */

@Entity
@Table(name = "bravo_system_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SystemInformation extends BaseDomain {

	private static final long serialVersionUID = -9026342793134561028L;
	private String systemName;
	private String version;
	private Date   releaseDate;
	private String comments;
	
	@Column(name = "system_name")
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	@Column(name = "release_date")
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getComments() {
		return comments;
	}
	
	
}
