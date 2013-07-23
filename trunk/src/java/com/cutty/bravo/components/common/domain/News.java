/* com.cutty.bravo.components.common.domain.News.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-11-3 下午08:17:36, Created by Cathy.Lin
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.domain;

import java.util.Date;
import javax.persistence.Column;
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
 * <a href="News.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:linjuan0125@gmail.com">Cathy Lin</a>
 */

@Entity
@Table(name = "bravo_news")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class News extends BaseDomain {
	
	private static final long serialVersionUID = 7603024612111540272L;
	private String title;
	private String content;
	private User publisher;
	private Date createDt ;
	private Enumeration status;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "publisher",referencedColumnName="id")		
	public User getPublisher() {
		return publisher;
	}
	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}
	@Column(name = "create_dt")
	public Date getCreateDt() {
		return createDt;
	}
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status",referencedColumnName="id")	
	public Enumeration getStatus() {
		return status;
	}
	public void setStatus(Enumeration status) {
		this.status = status;
	}
}
