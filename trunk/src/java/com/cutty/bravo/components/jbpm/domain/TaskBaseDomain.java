/* com.cutty.bravo.components.jbpm.domain.TaskBaseDomain.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-3 下午02:02:44, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jbpm.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.cutty.bravo.core.domain.BaseDomain;

/**
 * 该类用于将对象序列化，标示一个任务序列
 * 
 * <p>
 * <a href="TaskBaseDomain.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Entity
@Table(name = "bravo_wf_base_task")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskBaseDomain extends BaseDomain {
	private static final long serialVersionUID = -198873448851922608L;
	
	private WorkFlowBaseDomain workFlowBaseDomain;
	private TaskInstance taskInstance;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "workflow_base",referencedColumnName="id")	
	public WorkFlowBaseDomain getWorkFlowBaseDomain() {
		return workFlowBaseDomain;
	}
	public void setWorkFlowBaseDomain(WorkFlowBaseDomain workFlowBaseDomain) {
		this.workFlowBaseDomain = workFlowBaseDomain;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "task",referencedColumnName="id_")	
	public TaskInstance getTaskInstance() {
		return taskInstance;
	}
	public void setTaskInstance(TaskInstance taskInstance) {
		this.taskInstance = taskInstance;
	}
	
	
}
