/* com.cutty.bravo.components.jbpm.task.domain.TaskVariable.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-3 下午01:09:30, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jbpm.task.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.taskmgmt.def.Task;

/**
 * 该类用于读取和写入事务变量表"JBPM_TASKVARIABLE"
 *
 * <p>
 * <a href="TaskVariable.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Entity
@Table(name = "JBPM_TASKVARIABLE")
public class TaskVariable implements Serializable{

	private static final long serialVersionUID = -7227563093315609357L;

	private Long id;
	private TaskNode taskNode;
	private Task task;
	private String name;
	private String Value;
	
	@Id
	@GeneratedValue(generator = "Bravo-Generator")
    @GenericGenerator(name = "Bravo-Generator", 
    					strategy = "com.cutty.bravo.core.utils.BravoIdGenerator"
    )
	@Column(name="id_")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TASKNODEID_",referencedColumnName="ID_")	
	public TaskNode getTaskNode() {
		return taskNode;
	}
	
	public void setTaskNode(TaskNode taskNode) {
		this.taskNode = taskNode;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TASKID_",referencedColumnName="ID_")	
	public Task getTask() {
		return task;
	}
	
	public void setTask(Task task) {
		this.task = task;
	}
	
	@Column(name = "NAME_")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "VALUE_")
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
		
}

