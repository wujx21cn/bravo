/* com.cutty.bravo.components.jbpm.domain.WorkFlowDiagram.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-27 上午11:06:26, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jbpm.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jbpm.graph.def.ProcessDefinition;

import com.cutty.bravo.core.domain.BaseDomain;

/**
 * 该类用于与工作流表"bravo_wf_diagram"中具有对应ID的PROCESS_DEFINITION和CONTENT字段进行读和写操作
 *
 * <p>
 * <a href="WorkFlowDiagram.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Entity
@Table(name = "bravo_wf_diagram")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkFlowDiagram  extends BaseDomain {
	private static final long serialVersionUID = -2994545432544742994L;
	private ProcessDefinition processDefinition;
	private String content;
	
	/**
	 * 获取PROCESS_DEFINITION字段的内容
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "process_definition",referencedColumnName="id_")	
	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}
	/**
	 * 设置PROCESS_DEFINITION字段的内容
	 * @param processDefinition the processDefinition to set
	 */
	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}
	/**
	 * 获取CONTENT字段的内容
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置CONTENT字段的内容
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
