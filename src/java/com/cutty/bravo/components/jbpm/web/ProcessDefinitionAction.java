/* com.cutty.bravo.components.jbpm.web.ProcessDefinitionAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-9 下午04:56:07, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jbpm.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.web.util.JavaScriptUtils;

import com.cutty.bravo.components.jbpm.JbpmTemplate;
import com.cutty.bravo.components.jbpm.domain.WorkFlowDiagram;
import com.cutty.bravo.components.jbpm.domain.WorkFlowDomain;
import com.cutty.bravo.components.jbpm.manager.ProcessDefinitionManager;
import com.cutty.bravo.components.jbpm.task.domain.TaskInstanceInfo;
import com.cutty.bravo.core.dao.support.Page;
import com.cutty.bravo.core.security.domain.User;
import com.cutty.bravo.core.ui.Constants;
import com.cutty.bravo.core.web.struts2.EntityAction;

/**
 *  该类供页面调用，用于控制工作流编辑器与数据工作流表信息的交互
 *
 * <p>
 * <a href="ProcessDefinitionAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Namespace("/widgets/workflow/editors")   
@ParentPackage("bravo")
@Results( {   
    @Result(name = "designer", location= "/WEB-INF/modules/workflow/processDefinition-designer.ftl",type = "freemarker" ),
	@Result(name = "query",location= "/WEB-INF/modules/workflow/processDefinition-query.ftl" ,type = "freemarker"),
	@Result(name = "preview", location= "/WEB-INF/modules/workflow/processDefinition-preview.ftl",type = "freemarker" )
     }) 
public class ProcessDefinitionAction  extends EntityAction<ProcessDefinition>{
	private static final long serialVersionUID = -8730880469207583390L;
	private JbpmTemplate jbpmTemplate;
	private ProcessDefinitionManager processDefinitionManager;
	
	/**
	 * 设置一个用于管理工作流定义存取的对象
	 * @param processDefinitionManager-用于管理工作流定义存取的对象
	 */
	public void setProcessDefinitionManager(ProcessDefinitionManager processDefinitionManager) {
		this.processDefinitionManager = processDefinitionManager;
	}

	/**
	 * 设置管理工作流的模板对象
	 * @param jbpmTemplate the jbpmTemplate to set
	 */
	public void setJbpmTemplate(JbpmTemplate jbpmTemplate) {
		this.jbpmTemplate = jbpmTemplate;
	}

	/**
	 * 打开工作流设计页面
	 * @return 设计页面解析入口
	 */
	public String designer() {
		ServletActionContext.getRequest().setAttribute("bravoHome",ServletActionContext.getRequest().getContextPath());
		ServletActionContext.getRequest().setAttribute("workFlowDiagram","");
		return "designer";
	}
	/**
	 * 发布工作流定义
	 * @return 发布状态
	 */
	public String deployProcessDefinition(){
		String processDefinitionStr = ServletActionContext.getRequest().getParameter("processdefinition");
		String processDigramStr = ServletActionContext.getRequest().getParameter("processdigram");
		ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_KEY, Constants.AJAX_HANDLE_VALUE);
		
		if(StringUtils.isNotEmpty(processDefinitionStr) && StringUtils.isNotEmpty(processDigramStr)){
			try {
				ProcessDefinition processDefinition= jbpmTemplate.deployProcessDefinitionFromDesigner(processDefinitionStr);
				WorkFlowDiagram workFlowDiagram = new WorkFlowDiagram();
				workFlowDiagram.setProcessDefinition(processDefinition);
				workFlowDiagram.setContent(processDigramStr);
				baseDao.save(workFlowDiagram);
				ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_STATUS, Constants.AJAX_HANDLE_STATUS_SUCCESS);			
			} catch(Exception e){
				logger.error(e);
			    ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_STATUS, Constants.AJAX_HANDLE_STATUS_FAILURE);		
			    ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_MSG,e.getMessage());			
			}
		}else{
		    ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_STATUS, Constants.AJAX_HANDLE_STATUS_FAILURE);			
		    ServletActionContext.getRequest().setAttribute(Constants.AJAX_HANDLE_MSG, "传递流程定义出错!");			
		}
	    return JSON_DATA_RENDER_CHAIN;	
	}
	/**
	 * 调用工作流设计页面查看工作流定义的图示
	 * @return 设计页面解析入口
	 */
	public String viewDefinition(){
		String id = ServletActionContext.getRequest().getParameter("id");
		if (StringUtils.isNotEmpty(id)){
			List<WorkFlowDiagram> workFlowDiagrams = baseDao.findBy(WorkFlowDiagram.class,null, "processDefinition.id",Long.parseLong(id),true);
			if (null != workFlowDiagrams && 0 < workFlowDiagrams.size()){
				String workFlowDiagram = JavaScriptUtils.javaScriptEscape(workFlowDiagrams.get(0).getContent());
				ServletActionContext.getRequest().setAttribute("workFlowDiagram",workFlowDiagram);
			}
		} else {
			ServletActionContext.getRequest().setAttribute("workFlowDiagram","");
		}
		return "designer";
	}
	/**
	 * 浏览工作流设计
	 * @return 浏览页面解析入口
	 */
	public String previewDefinition(){
		String id = ServletActionContext.getRequest().getParameter("id");
		if (StringUtils.isNotEmpty(id)){
			List<WorkFlowDiagram> workFlowDiagrams = baseDao.findBy(WorkFlowDiagram.class,null, "processDefinition.id",Long.parseLong(id),true);
			if (null != workFlowDiagrams && 0 < workFlowDiagrams.size()){
				String workFlowDiagram = JavaScriptUtils.javaScriptEscape(workFlowDiagrams.get(0).getContent());
				ServletActionContext.getRequest().setAttribute("workFlowDiagram",workFlowDiagram);
			}
		} else {
			ServletActionContext.getRequest().setAttribute("workFlowDiagram","");
		}
		return "preview";
	}
	/**
	 * 浏览流程实例的进展情况
	 * @return 浏览页面解析入口
	 */
	public String previewDefinitionByWorkFlowDomainId(){
		//由页面传入的实例表单id
		String id = ServletActionContext.getRequest().getParameter("id");
		if (StringUtils.isNotEmpty(id)){
			WorkFlowDomain workFlowDomain = baseDao.findUniqueBy(WorkFlowDomain.class, "id", Long.parseLong(id),true);
			//获取该流程实例所有发起过的任务实例
			Long processInstanceId = workFlowDomain.getProcessInstance().getId();
			List<TaskInstance> taskInstances = baseDao.findBy(TaskInstance.class, null, "processInstance.id", processInstanceId,true);
			List<TaskInstanceInfo> finishedTasksInfo = new ArrayList<TaskInstanceInfo>();
			List<TaskInstanceInfo> unfinishedTasksInfo = new ArrayList<TaskInstanceInfo>();
			if( null != taskInstances && 0 < taskInstances.size() )
			{
				SimpleDateFormat simpleDateFormat=new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss"); 
				for( int i=0; i<taskInstances.size(); i++ )
				{
					if( null!=taskInstances.get(i).getEnd() )
					{
						TaskInstanceInfo taskInstanceInfo = new TaskInstanceInfo();
						taskInstanceInfo.setName(taskInstances.get(i).getName());
						taskInstanceInfo.setCreateTime(simpleDateFormat.format(taskInstances.get(i).getCreate()));
						taskInstanceInfo.setEndTime(simpleDateFormat.format(taskInstances.get(i).getEnd()));
						if(null!=taskInstances.get(i).getActorId())
						{
							User user = baseDao.findUniqueBy(User.class, "id", Long.parseLong(taskInstances.get(i).getActorId()),true);
							if( null!=user ) taskInstanceInfo.setActorName(user.getUserName());
						}						
						
						finishedTasksInfo.add(taskInstanceInfo);
					}
					else
					{
						TaskInstanceInfo taskInstanceInfo = new TaskInstanceInfo();
						taskInstanceInfo.setName(taskInstances.get(i).getName());
						taskInstanceInfo.setCreateTime(simpleDateFormat.format(taskInstances.get(i).getCreate()));
						if(null!=taskInstances.get(i).getActorId())
						{
							User user = baseDao.findUniqueBy(User.class, "id", Long.parseLong(taskInstances.get(i).getActorId()),true);
							if( null!=user ) taskInstanceInfo.setActorName(user.getUserName());
						}
						else
						{
							taskInstanceInfo.setActorName("(暂待接收)");
						}
						
						unfinishedTasksInfo.add(taskInstanceInfo);
					}
				}
				ServletActionContext.getRequest().setAttribute("finishedTasksInfo",finishedTasksInfo);
				ServletActionContext.getRequest().setAttribute("unfinishedTasksInfo",unfinishedTasksInfo);
				ServletActionContext.getRequest().setAttribute("processStarterName",workFlowDomain.getProcessStarter().getUserName());
				ServletActionContext.getRequest().setAttribute("processStartTime",simpleDateFormat.format(workFlowDomain.getProcessStartDate()));
				if( null!=workFlowDomain.getProcessInstance().getEnd() )
				{
					ServletActionContext.getRequest().setAttribute("processEndTime",simpleDateFormat.format(workFlowDomain.getProcessInstance().getEnd()));
				}
			}
			
			//获取该流程实例对应的流程定义图
			Long processDefinitionId = workFlowDomain.getProcessInstance().getProcessDefinition().getId();
			List<WorkFlowDiagram> workFlowDiagrams = baseDao.findBy(WorkFlowDiagram.class,null, "processDefinition.id", processDefinitionId,true );
			if (null != workFlowDiagrams && 0 < workFlowDiagrams.size()){
				String workFlowDiagram = JavaScriptUtils.javaScriptEscape(workFlowDiagrams.get(0).getContent());
				ServletActionContext.getRequest().setAttribute("workFlowDiagram",workFlowDiagram);
			}
		} else {
			ServletActionContext.getRequest().setAttribute("workFlowDiagram","");
		}
		return "preview";
	}
	/**
	 * 用于刷新工作流定义列表，让提交的流程定义出现在列表上
	 */
	@Override
	public String findAndRendJsonData() throws Exception {
	   String name= ServletActionContext.getRequest().getParameter("name");
	   String description = ServletActionContext.getRequest().getParameter("description");
	   Page page = Page.getInstance(ServletActionContext.getRequest());
	   processDefinitionManager.findLatestProcessDefinition(name,description,page);
		String contextDataName = ServletActionContext.getRequest().getParameter("contextDataName");
		if (StringUtils.isEmpty(contextDataName)){
			contextDataName = getEntityListName();
			ServletActionContext.getRequest().setAttribute("contextDataName", contextDataName);
		}
		ServletActionContext.getRequest().setAttribute(contextDataName,page.getResult());
		ServletActionContext.getRequest().setAttribute("totalCount",String.valueOf(page.getTotalCount()));
	   	
        return "jsonDataRenderChain";
   }
}
