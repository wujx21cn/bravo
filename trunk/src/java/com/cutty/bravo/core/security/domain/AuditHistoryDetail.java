package com.cutty.bravo.core.security.domain;

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
@Table(name = "bravo_audit_history_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AuditHistoryDetail extends BaseDomain{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4060017342246498946L;
	private String fieldName;
	private String fieldType;
	private String orignalValue;
	private String finalValue;
	private AuditHistory auditHistory;
	
	@Column(name = "field_name")
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	@Column(name = "field_type")
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	@Column(name = "orignal_value")
	public String getOrignalValue() {
		return orignalValue;
	}
	public void setOrignalValue(String orignalValue) {
		this.orignalValue = orignalValue;
	}
	@Column(name = "final_value")
	public String getFinalValue() {
		return finalValue;
	}
	public void setFinalValue(String finalValue) {
		this.finalValue = finalValue;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "audit_history",referencedColumnName="id")
	public AuditHistory getAuditHistory() {
		return auditHistory;
	}
	public void setAuditHistory(AuditHistory auditHistory) {
		this.auditHistory = auditHistory;
	}
}
