/* com.cutty.bravo.components.jbpm.assignment.SS2AssignmentHandler.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-3 下午02:17:24, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jbpm.handler;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;
import org.apache.commons.lang.StringUtils;

import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.security.domain.User;
import com.cutty.bravo.core.security.manager.UserManager;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.utils.render.FreemarkerTemplateEngine;
import com.cutty.bravo.core.web.handler.RequestHandler;

/**
 * 该类实现了AssignmentHandler接口，用于指派任务实例到对应的角色
 *
 * <p>
 * <a href="SS2AssignmentHandler.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class SS2AssignmentHandler implements AssignmentHandler {
	private static final long serialVersionUID = 4651842198029330833L;

	private String roleId; 
	private String actorId;
	private String expression;
	
	/**
	 * 将任务实例指派给单个角色或角色组
	 * @param assignable-待指派的任务实例，executionContext-任务执行上下文
	 */
	public void assign(Assignable assignable, ExecutionContext executionContext) throws Exception {
		Set<String> assigneeSet = new HashSet<String>();
		UserManager userManager = (UserManager)ApplicationContextKeeper.getAppCtx().getBean("userManager");
		
		//通过角色ID获取指派对象，一组一组地增加
		if (StringUtils.isNotEmpty(roleId)){
			if (roleId.indexOf(",") > -1){
				String[] roleArray = roleId.split(",");
				for (int i = 0;i < roleArray.length; i++){
					assigneeSet.addAll(userManager.findUseridsByRoleId(roleArray[i]));
				}
			} else {
				assigneeSet.addAll(userManager.findUseridsByRoleId(roleId));
			}
		}
		
		//通过人物ID获取指派对象，一个一个地增加
		if (StringUtils.isNotEmpty(actorId)){
			if (actorId.indexOf(",")>0){
				String[] userArray = actorId.split(",");
				for (int i = 0;i<userArray.length;i++){
					assigneeSet.add(userArray[i]);
				}
			} else {
				assigneeSet.add(actorId);
			}
		}
		
		/*expression的书写格式: role[${x.y}],actor[${p.q}],role[${u.v}],actor[${n.m}]*/
		//通过表达式获取指派对象
		if (StringUtils.isNotEmpty(expression)){
			String[] expressArray = expression.split(",");
			if (null != expressArray && 0 < expressArray.length){
				FreemarkerTemplateEngine freemarkerTemplateEngine = FreemarkerTemplateEngine.getInstance();
				Map context = new HashMap();
				RequestHandler requestHandler = RequestHandler.getContextRequestHandler();
				ServletContext servletContext = ApplicationContextKeeper.getServletContext();
				/*可解析令牌(Execution)上下文中已设定的参数*/
				context.putAll(executionContext.getTaskInstance().getVariables());
				/*可解析当前用户相关信息"currentUser" ${currentUser}*/
				if (null != requestHandler) {
					User currentUser = userManager.get(requestHandler.getCurrentUser().getId());
					context.put("currentUser",currentUser);
				}

				Serializable  entityIdentiferValue = (Serializable)executionContext.getTaskInstance().getVariable("entityIdentiferValue");
				String entityName = (String)executionContext.getTaskInstance().getVariable("entityName");
				if (null != entityIdentiferValue && StringUtils.isNotEmpty(entityName)){
					BaseDao baseDao = (BaseDao)ApplicationContextKeeper.getAppCtx().getBean("baseDao");
					Object formValue = baseDao.get(Class.forName(entityName), entityIdentiferValue);
					/*可解析表达相关信息"formValue "${formValue}*/
					context.put("formValue",formValue);
				}
				
				for (int i=0;i<expressArray.length;i++){
					String expreeValue = expressArray[i];
					if (expreeValue.startsWith("role[") && expreeValue.endsWith("]")){
						expreeValue = "<#escape x as (x)!>" + expreeValue.substring(5, expreeValue.length() - 1)+ "</#escape>";
						expreeValue = freemarkerTemplateEngine.renderFtl(servletContext,expreeValue,context);
						assigneeSet.addAll(userManager.findUseridsByRoleId(expreeValue));
					} else if (expreeValue.startsWith("actor[") && expreeValue.endsWith("]")){
						expreeValue = "<#escape x as (x)!>" +  expreeValue.substring(6, expreeValue.length() - 1)+ "</#escape>";
						expreeValue = freemarkerTemplateEngine.renderFtl(servletContext,expreeValue,context);
						assigneeSet.add(expreeValue);
					}
				}
			}
		}
		
		if (assigneeSet.size() == 1){
			assignable.setActorId(assigneeSet.iterator().next());
		} else if (assigneeSet.size() > 1){
			assignable.setPooledActors(assigneeSet.toArray(new String[0]));
		}
	}

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the actorId
	 */
	public String getActorId() {
		return actorId;
	}

	/**
	 * @param actorId the actorId to set
	 */
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}


}
