package com.cutty.bravo.components.common.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cutty.bravo.core.domain.BaseDomain;
import com.cutty.bravo.core.security.domain.User;


@Entity
@Table(name = "bravo_attachment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attachment extends BaseDomain{
	private static final long serialVersionUID = 5059462631659075026L;
	private String name;
	private String relativePath;
	private String originalFilename;
	private Integer fileSize;
	private User uploader;
	private Date uploadt;
	private Set<AttachmentRelation> attachmentRelations;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "relative_Path")
	public String getRelativePath() {
		return relativePath;
	}
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	
	@Column(name = "original_filename")
	public String getOriginalFilename() {
		return originalFilename;
	}
	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}
	
	@Column(name = "file_size")
	public Integer getFileSize() {
		return fileSize;
	}
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uploader",referencedColumnName="id")	
	public User getUploader() {
		return uploader;
	}
	public void setUploader(User uploader) {
		this.uploader = uploader;
	}
	public Date getUploadt() {
		return uploadt;
	}
	public void setUploadt(Date uploadt) {
		this.uploadt = uploadt;
	}
	
	@OneToMany(mappedBy = "attachment", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = AttachmentRelation.class)
	public Set<AttachmentRelation> getAttachmentRelations() {
		return attachmentRelations;
	}
	public void setAttachmentRelations(Set<AttachmentRelation> attachmentRelations) {
		this.attachmentRelations = attachmentRelations;
	}
	
}
