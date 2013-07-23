/* com.cutty.bravo.components.jbpm.task.action.TaskStartAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-3 下午02:47:57, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jbpm.task.action;

import java.util.List;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.cutty.bravo.components.jbpm.Constants;
import com.cutty.bravo.components.jbpm.JbpmTemplate;
import com.cutty.bravo.components.jbpm.domain.TaskBaseDomain;
import com.cutty.bravo.components.jbpm.domain.WorkFlowBaseDomain;
import com.cutty.bravo.components.jbpm.task.domain.TaskVariable;
import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;

/**
 * 该类实现了一个Action事件响应接口，用于在任务启动时调用，写在默认的事务节点中
 *
 * <p>
 * <a href="TaskStartAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class TaskStartAction implements ActionHandler{
	private static final long serialVersionUID = -1663673376277040601L;

	/**
	 * 事件的执行体，开始执行事务的实例
	 */
	public void execute(ExecutionContext executionContext) throws Exception {
		JbpmTemplate jbpmTemplate = (JbpmTemplate)ApplicationContextKeeper.getAppCtx().getBean("jbpmTemplate");
		BaseDao baseDao = (BaseDao)ApplicationContextKeeper.getAppCtx().getBean("baseDao");
		Task task = executionContext.getTask();
		TaskVariable taskVariable = jbpmTemplate.getVariablebyTask(task.getId(),Constants.TASK_URL);
		TaskInstance taskInstance = executionContext.getTaskInstance();
		if (null != taskVariable){
			taskInstance.setDescription(taskVariable.getValue());
		} else {
			ProcessDefinition processDefinition = task.getProcessDefinition();
			taskInstance.setDescription(processDefinition.getDescription());
		}
		jbpmTemplate.saveTaskInstances(taskInstance);
		Long domainId = (Long)taskInstance.getProcessInstance().getContextInstance().getVariable("entityIdentiferValue");
		if (null == domainId || 0 == domainId.intValue()){
			return;
		}
		WorkFlowBaseDomain workFlowBaseDomain = new WorkFlowBaseDomain();
		workFlowBaseDomain.setId(domainId);
		
		List taskBaseDomainList =baseDao.find(null, "from TaskBaseDomain WHERE workFlowBaseDomain.id=? and taskInstance.id=?", true, domainId,taskInstance.getId());
		if (null != taskBaseDomainList && 0 < taskBaseDomainList.size()) return;
		TaskBaseDomain taskBaseDomain = new TaskBaseDomain();
		taskBaseDomain.setTaskInstance(taskInstance);
		taskBaseDomain.setWorkFlowBaseDomain(workFlowBaseDomain);
		baseDao.save(taskBaseDomain);
	}

}
