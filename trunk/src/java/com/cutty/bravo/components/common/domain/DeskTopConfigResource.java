/* com.cutty.bravo.components.common.domain.DeskTopConfigResource.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Apr 29, 2009 11:14:01 AM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cutty.bravo.core.domain.BaseDomain;

/**
 *
 * <p>
 * <a href="DeskTopConfigResource.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Entity
@Table(name = "bravo_user_desktop_resource")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeskTopConfigResource  extends BaseDomain {

	private static final long serialVersionUID = 616831134957146499L;
	

	private String name;
	private String url;
	private String thumbnail;
	private String file;
	private Enumeration type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Column(name = "path_to_thumbnail")
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	@Column(name = "path_to_file")
	public String getFile() {
		return file;
	}
	

	public void setFile(String file) {
		this.file = file;
	}
	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "type",referencedColumnName="id")
	public Enumeration getType() {
		return type;
	}
	public void setType(Enumeration type) {
		this.type = type;
	}
	
	

}
