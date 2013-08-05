/* com.cutty.bravo.components.jbpm.web.TaskAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-30 下午03:43:02, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.
*/
package com.cutty.bravo.components.jbpm.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.jbpm.graph.def.Transition;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.cutty.bravo.components.common.domain.Department;
import com.cutty.bravo.components.jbpm.Constants;
import com.cutty.bravo.components.jbpm.JbpmTemplate;
import com.cutty.bravo.components.jbpm.domain.WorkFlowBaseDomain;
import com.cutty.bravo.components.jbpm.domain.WorkFlowDomain;
import com.cutty.bravo.components.jbpm.manager.TaskManager;
import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.dao.support.Page;
import com.cutty.bravo.core.dao.support.QueryParameterWrapper;
import com.cutty.bravo.core.security.domain.Role;
import com.cutty.bravo.core.security.domain.User;
import com.cutty.bravo.core.security.manager.UserManager;
import com.cutty.bravo.core.ui.tags.menu.component.MenuBean;
import com.cutty.bravo.core.ui.tags.tree.TreeNode;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.utils.render.FreemarkerTemplateEngine;
import com.cutty.bravo.core.web.handler.RequestHandler;
import com.cutty.bravo.core.web.struts2.EntityAction;

/**
 * 该类提供对Task的基本存取操作
 *
 * <p>
 * <a href="TaskAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
@Namespace("/workflow")   
@ParentPackage("bravo")
public class TaskAction extends EntityAction<TaskInstance>{
	private static final long serialVersionUID = 8328941922395704571L;
	private JbpmTemplate jbpmTemplate;
	private TaskManager taskManager;
	private UserManager userManager;
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}


	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}


	public void setJbpmTemplate(JbpmTemplate jbpmTemplate) {
		this.jbpmTemplate = jbpmTemplate;
	}


	/**
	 * 根据传递参数获取工作流的按钮
	 *  @return 获取工作流按钮
	 */
	public String generWorkflowButton() {
		ServletActionContext.getRequest().setAttribute(com.cutty.bravo.core.ui.Constants.WORKFLOW_BUTTON_KEY, com.cutty.bravo.core.ui.Constants.WORKFLOW_BUTTON_VALUE);
		String entityName = ServletActionContext.getRequest().getParameter(Constants.ENTITY_NAME);
		String entityId = ServletActionContext.getRequest().getParameter(Constants.ENTITY_ID);
		String definitionName = ServletActionContext.getRequest().getParameter(Constants.DEFINITION_NAME);
		String buttonId = ServletActionContext.getRequest().getParameter(Constants.BUTTON_ID);
		if (StringUtils.isEmpty(entityName) || StringUtils.isEmpty(entityId) || StringUtils.isEmpty(definitionName)){
			return JSON_DATA_RENDER_CHAIN;
		}
		ServletActionContext.getRequest().setAttribute(Constants.BUTTON_ID,buttonId);
		List<MenuBean> menuBeans = new ArrayList<MenuBean>();
		BaseDao baseDao = super.baseDao;
		Map classMetadataMap = baseDao.getHibernate().getSessionFactory().getAllClassMetadata();
		// 如果只传实体名,不传包名,则循环处理,选择出该实体的包名。
		if (entityName.indexOf(".") < 0) {
			entityName = "." + entityName;
			Iterator classMetadataKeyIT = classMetadataMap.keySet().iterator();
			while (classMetadataKeyIT.hasNext()) {
				String className = (String) classMetadataKeyIT.next();
				if (className.indexOf(entityName) > -1) {
					entityName = className;
				}
			}
		}
		try {
			Object model = baseDao.get(Class.forName(entityName), (Serializable)ConvertUtils.convert(entityId,Long.class));
			if (model instanceof WorkFlowDomain){
				WorkFlowDomain workFlowDomain = (WorkFlowDomain)model;
				if (null == workFlowDomain.getProcessInstance()){
					MenuBean menu = new MenuBean();
					menu.setText("保存并创建流程");
					menu.setHandler("function(o){openWorkflowLogWin(\'"+entityName+"\',\'"+entityId+"\',\'"+definitionName+"\',\'"+Constants.CREATE+"\') }");
					menuBeans.add(menu);
					
				} else {
					long processInstanceId = workFlowDomain.getProcessInstance().getId();
					List assignPooledTaskInstanceIds= jbpmTemplate.getAssignPooledTaskInstanceIds(processInstanceId);
					List assignTaskInstanceIds= jbpmTemplate.getAssignTaskInstanceIds(processInstanceId);
					if (null != assignPooledTaskInstanceIds && 0 < assignPooledTaskInstanceIds.size()){
						Iterator assignPooledTaskInstanceIdsIT = assignPooledTaskInstanceIds.iterator();
						while ( assignPooledTaskInstanceIdsIT.hasNext()){
							Object[] objects = (Object[])assignPooledTaskInstanceIdsIT.next();
							MenuBean menu = new MenuBean();
							menu.setText("接受任务["+(String)objects[1]+"]");
							menu.setHandler("function(o){openWorkflowLogWin(\'"+entityName+"\',\'"+entityId+"\',\'"+definitionName+"\',\'"+Constants.ACCEPT+"\',\'"+objects[0].toString()+"\') }");
							menuBeans.add(menu);
						}
					}
					if (null != assignTaskInstanceIds && 0 < assignTaskInstanceIds.size()){
						Iterator assignTaskInstanceIdsIT = assignTaskInstanceIds.iterator();
						while (assignTaskInstanceIdsIT.hasNext()){
							Object[] objects = (Object[])assignTaskInstanceIdsIT.next();
							MenuBean menu = new MenuBean();
							menu.setText("完成任务["+(String)objects[1]+"]");
							menu.setHandler("function(o){openWorkflowLogWin(\'"+entityName+"\',\'"+entityId+"\',\'"+definitionName+"\',\'"+Constants.FINISH+"\',\'"+objects[0].toString()+"\') }");
							menuBeans.add(menu);
						}
					}
				}
					
			}
		} catch (ClassNotFoundException e) {
			logger.error(e);
		}
		ServletActionContext.getRequest().setAttribute(com.cutty.bravo.core.ui.Constants.WORKFLOW_BUTTON_CONTEXT_NAME, menuBeans);
		return JSON_DATA_RENDER_CHAIN;
	}
	
	public String admin() {
		ServletActionContext.getRequest().setAttribute("bravoHome", RequestHandler.getContextRequestHandler().getRequest().getContextPath());
		String entityName = ServletActionContext.getRequest().getParameter(Constants.ENTITY_NAME);
		ServletActionContext.getRequest().setAttribute(Constants.ENTITY_NAME, entityName);
		String entityId = ServletActionContext.getRequest().getParameter(Constants.ENTITY_ID);
		ServletActionContext.getRequest().setAttribute(Constants.ENTITY_ID, entityId);
		try{
			ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		}catch(Exception e){
			logger.error(e);
		}
		String definitionName = ServletActionContext.getRequest().getParameter(Constants.DEFINITION_NAME);
		try{
			definitionName = java.net.URLDecoder.decode(definitionName);
		}catch(Exception e){
			logger.error(e);
		}
		ServletActionContext.getRequest().setAttribute(Constants.DEFINITION_NAME, definitionName);
		String operation = ServletActionContext.getRequest().getParameter(Constants.OPERATION); 
		ServletActionContext.getRequest().setAttribute(Constants.OPERATION, operation);
		String taskId = ServletActionContext.getRequest().getParameter(Constants.TASK_ID); 
		ServletActionContext.getRequest().setAttribute(Constants.TASK_ID, taskId);
		if (Constants.CREATE.equalsIgnoreCase(operation)){
			return "create";
		} else if (Constants.ACCEPT.equalsIgnoreCase(operation)){
			return "accept";
		}else if (Constants.FINISH.equalsIgnoreCase(operation)){
			List<Transition> transitions = jbpmTemplate.getTaskInstance(Long.parseLong(taskId)).getAvailableTransitions();
			ServletActionContext.getRequest().setAttribute(Constants.AVIABLE_TRANSITION_NAME, transitions);
			return "finish";
		}
		return "create";
	}
	
	public String assignTasks() throws Exception {
    	Page page = Page.getInstance(ServletActionContext.getRequest());
    	try {
    		QueryParameterWrapper queryParameterWrapper =new QueryParameterWrapper(getEntityClass()); 
    		//start属性是由GRID附加上去的，与taskInstance的属性冲突，需要去除
    		queryParameterWrapper.removeQueryParameter("start");
    		baseDao.findByPage(queryParameterWrapper, page,true);
		} catch (Exception e) {
			logger.error(e);
			throw e; 
		}
		renderContextForFind(page);
		return JSON_DATA_RENDER_CHAIN;
	}
	
	public String viewTask() throws Exception {
		TaskInstance taskInstance = this.getModel();
		if (0 != taskInstance.getId()){
			taskInstance = jbpmTemplate.getTaskInstance(taskInstance.getId());
			if (StringUtils.isNotEmpty(taskInstance.getDescription())){
				FreemarkerTemplateEngine freemarkerTemplateEngine = FreemarkerTemplateEngine.getInstance();
				Map contextMap = new HashMap();
				contextMap.put("bravoHome", ServletActionContext.getRequest().getContextPath());
				WorkFlowBaseDomain formValue = baseDao.findUniqueBy(WorkFlowBaseDomain.class, "processInstance", taskInstance.getProcessInstance(),true);
				contextMap.put("formValue", formValue);
				String redirectURL = freemarkerTemplateEngine.changeText2MacroAndRenderFtl(ApplicationContextKeeper.getServletContext(), "<#escape x as (x)!>"+taskInstance.getDescription()+"</#escape>",contextMap);
				System.out.println(redirectURL);
				ServletActionContext.getRequest().setAttribute(Constants.TASK_URL, redirectURL);
			}
		}
		return "redirect";
	}
	
	/**
	 * 人员部门树
	 * @return
	 */
	public String userDeptCheckedTree(){
		
		String node = ServletActionContext.getRequest().getParameter("node");
		String IDString = ServletActionContext.getRequest().getParameter("IDString");
		String deptNode = node.substring(4);
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		List<Department> childDeptList = taskManager.getChildDeptTree(Long.valueOf(deptNode));		
		List<User> childUserList = taskManager.getChildUser(Long.valueOf(deptNode));
		if (StringUtils.isNotEmpty(node) &&childDeptList.size()>0){
			for(int i=0;i<childDeptList.size();i++)
			{
				Department childDeptValue = childDeptList.get(i);
				TreeNode nodeValue = new TreeNode();
				List<User> childNodeList = taskManager.getChildUser(childDeptValue.getId());
				if(taskManager.getChildDeptTree(childDeptValue.getId()).size()==0&&
				   childNodeList.size()==0)
				{
					continue;
				}

				nodeValue.setIconCls("icon-pkg");
				nodeValue.setId("dept" + childDeptValue.getId().toString());
				nodeValue.setText(childDeptValue.getName());
				//如果该父节点的子节点有被勾选中，该父节点默认打开
				
				for(int j=0;j<childNodeList.size();j++){
					User childUserValue = childNodeList.get(j);	
					if(null!=IDString && IDString.contains(childUserValue.getId().toString())){
						nodeValue.setExpanded("true");
					}
				}
				treeList.add(nodeValue);	
			}
		}
		if (StringUtils.isNotEmpty(node) &&childUserList.size()>0){
			for(int i=0;i<childUserList.size();i++){
				User childUserValue = childUserList.get(i);
				TreeNode nodeValue = new TreeNode();
				nodeValue.setIconCls("x-tree-node-icon-noneDisplay");	
				nodeValue.setLeaf("true");				
				if(null!=IDString && IDString.contains(childUserValue.getId().toString())){
					nodeValue.setChecked("true");
				}
				nodeValue.setId("user" + childUserValue.getId().toString());
				nodeValue.setText(childUserValue.getUserName());
				treeList.add(nodeValue);
			}

		}
		ServletActionContext.getRequest().setAttribute("treeData",treeList);
		return "jsonDataRenderChain";
	}
	
	/**
	 * 处理在完成该弹出树功能前，Actor只有ID，而没有对应的name的情况
	 */
	public String userIDForName(){
		ServletActionContext.getRequest().setAttribute(com.cutty.bravo.core.ui.Constants.AJAX_HANDLE_KEY, com.cutty.bravo.core.ui.Constants.AJAX_HANDLE_VALUE);
		String oldIDFrom = ServletActionContext.getRequest().getParameter("oldIDs");
		String oldNames = "";
		String oldIDs = "";
		User user = new User();
		if (oldIDFrom.indexOf(",")>-1){
			String[] oldIDsArray = oldIDFrom.split(",");
			for (int i = 0;i<oldIDsArray.length;i++){
				if(StringUtils.isEmpty(oldIDsArray[i])) continue;
				user = userManager.get(Long.valueOf(oldIDsArray[i]));
				if(oldNames.equals("")){
					oldNames += user.getUserName();
					oldIDs += oldIDsArray[i];
				}else{
					oldNames += ("," + user.getUserName());
					oldIDs += ("," + oldIDsArray[i]);
				}
			}
		}else if(!("").equals(oldIDFrom)){
			    user = userManager.get(Long.valueOf(oldIDs));
			    oldNames += user.getUserName();
			    oldIDs += oldIDFrom;
		}
		
		StringBuffer sb = new StringBuffer(); 
		sb.append("{oldIDs:'" + oldIDs + "', oldNames:'" + oldNames + "'}" );
		
		ServletActionContext.getRequest().setAttribute(com.cutty.bravo.core.ui.Constants.AJAX_HANDLE_STATUS,"successReturn");
		ServletActionContext.getRequest().setAttribute(com.cutty.bravo.core.ui.Constants.AJAX_HANDLE_MSG,sb.toString());
		return "jsonDataRenderChain";
	}
	

	/**
	 * 人员角色树
	 * @return
	 */
	public String userRoleCheckedTree(){
		
		String IDString = ServletActionContext.getRequest().getParameter("IDString");
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		List<Role> childRoleList = taskManager.getChildRoleTree();		
			for(int i=0;i<childRoleList.size();i++)
			{
				Role childRoleValue = childRoleList.get(i);
				TreeNode nodeValue = new TreeNode();
				nodeValue.setIconCls("x-tree-node-icon-noneDisplay");	
				nodeValue.setLeaf("true");				
				if(null!=IDString && IDString.contains(childRoleValue.getId().toString())){
					nodeValue.setChecked("true");
				}
				nodeValue.setId(childRoleValue.getId().toString());
				nodeValue.setText(childRoleValue.getName());
				treeList.add(nodeValue);
			}

		ServletActionContext.getRequest().setAttribute("treeData",treeList);
		return "jsonDataRenderChain";
	}
	
	/**
	 * 处理在完成该弹出树功能前，role只有ID，而没有对应的name的情况
	 */
	public String roleIDForName(){
		ServletActionContext.getRequest().setAttribute(com.cutty.bravo.core.ui.Constants.AJAX_HANDLE_KEY, com.cutty.bravo.core.ui.Constants.AJAX_HANDLE_VALUE);
		String oldIDFrom = ServletActionContext.getRequest().getParameter("oldIDs");
		String oldNames = "";
		String oldIDs = "";
		Role role = new Role();
		if (oldIDFrom.indexOf(",")>-1){
			String[] oldIDsArray = oldIDFrom.split(",");
			for (int i = 0;i<oldIDsArray.length;i++){
				if(StringUtils.isEmpty(oldIDsArray[i])) continue;
				role = taskManager.get(Long.valueOf(oldIDsArray[i]));
				if(oldNames.equals("")){
					oldNames += role.getName();
					oldIDs += oldIDsArray[i];
				}else{
					oldNames += ("," + role.getName());
					oldIDs += ("," + oldIDsArray[i]);
				}
			}
		}else if(!("").equals(oldIDFrom)){
		        role = taskManager.get(Long.valueOf(oldIDs));
			    oldNames += role.getName();
			    oldIDs += oldIDFrom;
		}
		
		StringBuffer sb = new StringBuffer(); 
		sb.append("{oldIDs:'" + oldIDs + "', oldNames:'" + oldNames + "'}" );
		
		ServletActionContext.getRequest().setAttribute(com.cutty.bravo.core.ui.Constants.AJAX_HANDLE_STATUS,"successReturn");
		ServletActionContext.getRequest().setAttribute(com.cutty.bravo.core.ui.Constants.AJAX_HANDLE_MSG,sb.toString());
		return "jsonDataRenderChain";
	}
	public String assignPooledTask() throws Exception {
		return "assignPooledTask";
	}
	
	public String assignTask() throws Exception {
		return "assignTask";
	}
	
	public String finishedTask() throws Exception {
		return "finishedTask";
	}
	
	public String closedTask() throws Exception {
		return "closedTask";
	}
	
	public String taskProcess(){
		/*表单实体名*/
		String entityName = ServletActionContext.getRequest().getParameter(Constants.ENTITY_NAME);
		/*表单实体对象id*/
		String entityId = ServletActionContext.getRequest().getParameter(Constants.ENTITY_ID);
		/*流程定义名称*/
		String definitionName = ServletActionContext.getRequest().getParameter(Constants.DEFINITION_NAME);
		/*任务实例操作方法*/
		String operation = ServletActionContext.getRequest().getParameter(Constants.OPERATION);

		JbpmTemplate jbpmTemplate = (JbpmTemplate)ApplicationContextKeeper.getAppCtx().getBean("jbpmTemplate");

		if (StringUtils.isEmpty(operation) || StringUtils.isEmpty(definitionName)){
			return "jsonDataRenderChain";
		}
		
		//发起流程
		if (Constants.CREATE.equalsIgnoreCase(operation)){
			WorkFlowBaseDomain workFlowBaseDomain = null;
			try{
				workFlowBaseDomain = (WorkFlowBaseDomain)baseDao.get(Class.forName(entityName), Long.parseLong(entityId));
			}catch(ClassNotFoundException e){
				logger.error(e);
			}
			jbpmTemplate.startProcess(definitionName, workFlowBaseDomain);
			baseDao.save(workFlowBaseDomain);
			jbpmTemplate.getContext().getSession().flush();
		} 
		//接收任务
		else if (Constants.ACCEPT.equalsIgnoreCase(operation)){
			String taskId = ServletActionContext.getRequest().getParameter(Constants.TASK_ID);
			TaskInstance taskInstance = jbpmTemplate.getTaskInstance(Long.parseLong(taskId));
			taskInstance.start(jbpmTemplate.getContext().getActorId());
			jbpmTemplate.getContext().getSession().flush();
		}
		//结束任务
		else if (Constants.FINISH.equalsIgnoreCase(operation)){
			Enumeration<String> enu = ServletActionContext.getRequest().getParameterNames();
			String taskId = ServletActionContext.getRequest().getParameter(Constants.TASK_ID);
			TaskInstance taskInstance = jbpmTemplate.getTaskInstance(Long.parseLong(taskId));
			String transactionName = ServletActionContext.getRequest().getParameter("transitionName");
			if (StringUtils.isNotEmpty(transactionName)){
				taskInstance.end(transactionName);
			} else {
				taskInstance.end();
			}
			jbpmTemplate.getContext().getSession().flush();
		}
		return "jsonDataRenderChain";
	}
}
