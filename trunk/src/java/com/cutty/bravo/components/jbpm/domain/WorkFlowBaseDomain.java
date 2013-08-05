/* com.cutty.bravo.components.jbpm.domain.WorkFlowBaseDomain.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-3 下午02:02:44, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jbpm.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.cutty.bravo.components.common.domain.Enumeration;
import com.cutty.bravo.core.domain.BaseDomain;
import com.cutty.bravo.core.security.domain.User;

/**
 * 该类用于对应表"BRAVO_WF_BASE"的四个表段，对该表进行读写操作
 * <p>
 * <a href="WorkFlowBaseDomain.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
@Entity(name = "bravo_wf_base") 
@Inheritance(strategy = InheritanceType.JOINED) 
public class WorkFlowBaseDomain extends BaseDomain {
	private static final long serialVersionUID = -198873448851922608L;
	//获取流程实例
	private ProcessInstance processInstance;
	//获取流程实例的事务列表
	private Set<TaskBaseDomain> taskBaseDomains;
	//获取流程实例的发起人
	private User processStarter;
	//获取流程实例的发起时间
	private Date processStartDate;
	
	private String entityClass;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "processInstance",referencedColumnName="id_")	
	public ProcessInstance getProcessInstance() {
		return processInstance;
	}
	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}

	@OneToMany(mappedBy = "workFlowBaseDomain", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = TaskBaseDomain.class) 
	public Set<TaskBaseDomain> getTaskBaseDomains() {
		return taskBaseDomains;
	}
	public void setTaskBaseDomains(Set<TaskBaseDomain> taskBaseDomains) {
		this.taskBaseDomains = taskBaseDomains;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "process_starter",referencedColumnName="id")	
	public User getProcessStarter() {
		return processStarter;
	}
	public void setProcessStarter(User processStarter) {
		this.processStarter = processStarter;
	}
	
	@Column(name = "process_start_dt")
	public Date getProcessStartDate() {
		return processStartDate;
	}
	public void setProcessStartDate(Date processStartDate) {
		this.processStartDate = processStartDate;
	}
	

	@Column(name = "process_entity_class")
	public String getEntityClass() {
		return entityClass;
	}
	
	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}
	
	
	
}
