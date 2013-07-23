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

@Entity
@Table(name = "bravo_attachment_relation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AttachmentRelation extends BaseDomain{

	private static final long serialVersionUID = -3093073700224355210L;
	private Attachment attachment;
	private String entityName;
	private Long entity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "attachment",referencedColumnName="id")	
	public Attachment getAttachment() {
		return attachment;
	}
	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}
	
	@Column(name = "entity_name")
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public Long getEntity() {
		return entity;
	}
	public void setEntity(Long entity) {
		this.entity = entity;
	}

}
