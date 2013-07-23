/* com.cutty.bravo.core.ui.tags.button.component.WorkflowButtonBean.java
{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-11-13 上午10:42:02, Created by Jason.Wu
}}IS_NOTE
Copyright (C) 2008 Bravo Corporation. All Rights Reserved.
*/
package com.cutty.bravo.core.ui.tags.button.component;


/**
 *
 * <p>
 * <a href="WorkflowButtonBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class WorkflowButtonBean  extends ButtonBean  {
	private static final long serialVersionUID = 5657641352710369111L;
	private String entityName;
	private String entityId;
	private String workflowName;
	private String formName;
	private String operation;

	
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getWorkflowName() {
		return workflowName;
	}
	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}	
	
	
}
