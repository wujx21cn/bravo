/* com.cutty.bravo.components.jbpm.Constants.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-11-12 下午05:11:03, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jbpm;

/**
 * 该接口定义了工作流操作的常量命名，用于调用所对应的工作流操作
 * 
 * <p>
 * <a href="Constants.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public interface Constants {
	//工作流操作传递的参数的名称
	public static final String OPERATION = "workflow_operation";
	//工作流定义名传递的参数名
	public static final String DEFINITION_NAME = "workflow_definition_name";
	//新建流程的操作的参数名称
	public static final String CREATE = "workflow_operation_create";
	//接受任务的操作的参数名称
	public static final String ACCEPT = "workflow_operation_accept";
	//完成任务的操作的参数名称
	public static final String FINISH = "workflow_operation_finish";
	//传递流程相关实体的参数名称
	public static final String ENTITY_NAME = "workflow_entity_name";
	//传递流程相关实体ID的参数名称
	public static final String ENTITY_ID = "workflow_entity_id";
	//传递流程相关按钮ID的参数名称
	public static final String BUTTON_ID = "workflow_button_id";
	//传递流程相关任务ID的参数名称
	public static final String TASK_ID = "workflow_task_id";
	//传递流程相关路径ID的参数名称
	public static final String TRANSITION_NAME = "workflow_transItion_name";
	//传递流程相关有效路径的参数名称
	public static final String AVIABLE_TRANSITION_NAME = "workflow_aviable_transition_name";
	
	//当前节点变量_节点对应表单名
	public static final String TASK_URL = "workflow_task_url";
}
