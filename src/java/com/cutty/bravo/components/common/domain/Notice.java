/* com.cutty.bravo.components.common.domain.Notice.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Nov 3, 2008 4:39:10 PM, Created kukuxia.kevin
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.domain;

import java.util.Date;
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

import com.cutty.bravo.core.domain.BaseDomain;
import com.cutty.bravo.core.security.domain.User;

/**
 *
 * <p>
 * <a href="Notice.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:huangw100@126.com">kukuxia.kevin</a>
 */
@Entity
@Table(name = "bravo_notice")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Notice extends BaseDomain {

	private static final long serialVersionUID = -7762424461892618023L;
	
	private String title;
    private String content;
    private User publisher ;
    private Date createDate ;
    private Date publishStartDate ;
    private Date publishEndDate ;
    private Enumeration status ;
    private Set<User> recivers;
    
    
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Column(name = "publish_start_dt")
	public Date getPublishStartDate() {
		return publishStartDate;
	}
	public void setPublishStartDate(Date publishStartDate) {
		this.publishStartDate = publishStartDate;
	}
	
	@Column(name = "publish_end_dt")
	public Date getPublishEndDate() {
		return publishEndDate;
	}
	public void setPublishEndDate(Date publishEndDate) {
		this.publishEndDate = publishEndDate;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)	
	@JoinColumn(name = "status",referencedColumnName="id")	
	public Enumeration getStatus() {
		return status;
	}
	public void setStatus(Enumeration status) {
		this.status = status;
	}
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "bravo_notice_receiver", joinColumns = { @JoinColumn(name = "notice_id") }, inverseJoinColumns = { @JoinColumn(name = "receiver_id") })
	public Set<User> getRecivers() {
		return recivers;
	}

	public void setRecivers(Set<User> recivers) {
		this.recivers = recivers;
	}
    
}
