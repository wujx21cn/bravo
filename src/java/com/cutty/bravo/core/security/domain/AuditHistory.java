package com.cutty.bravo.core.security.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

@Entity
@Table(name = "bravo_audit_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AuditHistory extends BaseDomain{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8782663936520929948L;
	private String entityName;
	private String entityId;
	private User updater;
	private Date updateDt;
	private Set<AuditHistoryDetail> auditHistoryDetail;
	
	@Column(name = "entity_name")
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	@Column(name = "entity_id")
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updater",referencedColumnName="id")
	public User getUpdater() {
		return updater;
	}
	public void setUpdater(User updater) {
		this.updater = updater;
	}
	
	@Column(name = "update_dt")
	public Date getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}
	@OneToMany(mappedBy = "auditHistory", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = AuditHistoryDetail.class) 
	@OrderBy("id")
	public Set<AuditHistoryDetail> getAuditHistoryDetail() {
		return auditHistoryDetail;
	}
	public void setAuditHistoryDetail(Set<AuditHistoryDetail> auditHistoryDetail) {
		this.auditHistoryDetail = auditHistoryDetail;
	}
}
