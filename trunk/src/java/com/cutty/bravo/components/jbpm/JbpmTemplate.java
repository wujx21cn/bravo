/* com.cutty.bravo.components.jbpm.JbpmTemplate.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-3 下午01:16:21, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jbpm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.jpdl.xml.JpdlXmlReader;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.xml.sax.InputSource;

import com.cutty.bravo.components.jbpm.definition.ProcessDefinitionFactoryBean;
import com.cutty.bravo.components.jbpm.domain.WorkFlowBaseDomain;
import com.cutty.bravo.components.jbpm.parser.JpdlXmlPlugReader;
import com.cutty.bravo.components.jbpm.task.domain.TaskVariable;
import com.cutty.bravo.core.dao.support.Page;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.web.handler.RequestHandler;

/**
 * 该类为一个工作流管理的模板，用于对已发布工作流的执行和控制
 *
 * <p>
 * <a href="JbpmTemplate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class JbpmTemplate  {
	

	/**
	 * 获取当前JBPM Contrxt
	 * @return JBPM上下文
	 */
	public JbpmContext getContext() {
		JbpmContext context = JbpmContext.getCurrentJbpmContext();
		return context;
	}


	/**
	 * 根据处理人获取任务
	 * @param actorId-处理人ID
	 * @return 处理人的任务实例列表
	 */
	public List findTaskInstances(final String actorId) {
		return getContext().getTaskMgmtSession().findTaskInstances(actorId);
	}
	
	/**
	 * 根据任务ID获取任务
	 * @param taskInstanceId-任务ID
	 * @return 任务实例
	 */
	public TaskInstance getTaskInstance(final long taskInstanceId) {
		return getContext().getTaskMgmtSession().getTaskInstance(taskInstanceId);
	}

	/**
	 * 根据处理人数组获取任务
	 * @param actorIds-处理人数组
	 * @return 处理人数组的任务实例列表
	 */
	public List findTaskInstances(final String[] actorIds) {
		return getContext().getTaskMgmtSession().findTaskInstances(actorIds);
	}


	/**
	 * 根据处理人列表获取任务
	 * @param actorIds-处理人列表
	 * @return 处理人列表的任务实例列表
	 */
	public List findTaskInstances(final List actorIds) {
		return getContext().getTaskMgmtSession().findTaskInstances(actorIds);
	}

	/**
	 * 根据令牌获取任务
	 * @param token-令牌
	 * @return 令牌的任务列表
	 */
	public List findTaskInstancesByToken(Token token) {
		return findTaskInstancesByToken(token.getId());
	}

	/**
	 * 根据令牌的ID获取任务
	 * @param tokenId-令牌ID
	 * @return 令牌的任务列表
	 */
	public List findTaskInstancesByToken(final long tokenId) {
		return getContext().getTaskMgmtSession().findTaskInstancesByToken(tokenId);
	}

	/**
	 * 工作流推进
	 * @param processInstance-工作流实例
	 */
	public void signal(final ProcessInstance processInstance) {
		processInstance.signal();
	}

	/**
	 * 按转移ID确定工作流实例推进的方向
	 * @param processInstance-工作流实例
	 * @param transitionId-转移ID
	 */
	public void signal(final ProcessInstance processInstance, final String transitionId) {
		processInstance.signal(transitionId);
	}

	/**
	 * 
	 * @param processInstance
	 * @param transition
	 */
	public void signal(final ProcessInstance processInstance, final Transition transition) {
		processInstance.signal(transition);
	}


	/**
	 * 
	 * @param processInstance
	 * @param tokenName
	 */
    public void signalToken(final ProcessInstance processInstance, final String tokenName) {
		Token token = processInstance.getRootToken().findToken(tokenName);
        if (token == null) {
            processInstance.signal();
        } else {
            token.signal();
        }
	}

	/**
	 * 
	 * @param processInstance
	 * @param tokenName
	 * @param transitionId
	 */
    public void signalToken(final ProcessInstance processInstance, final String tokenName, final String transitionId) {
		Token token = processInstance.getRootToken().findToken(tokenName);
        if (token == null) {
            processInstance.signal(transitionId);
        } else {
            token.signal(transitionId);
        }
	}
    
	/**
	 * 根据当前处理人列表获取所有当前任务
	 * @param actorIds
	 * @return
	 */
	public List findPooledTaskInstances(final List actorIds) {
		return getContext().getTaskMgmtSession().findPooledTaskInstances(actorIds);
	}
	
	/**
	 * 根据当前处理人获取所有当前任务
	 * @param actorId
	 * @return
	 */
	public List findPooledTaskInstances(String actorId) {
		return getContext().getTaskMgmtSession().findPooledTaskInstances(actorId);
	}
	
	/**
	 * 保存当前流程实例
	 * @param processInstance
	 * @return
	 */
	public ProcessInstance saveProcessInstance(ProcessInstance processInstance) {
		getContext().save(processInstance);
		return processInstance;
	}
	
	/**
	 * 根据流程定义ID获取所有流程实例
	 * @param processDefinitionId
	 * @return
	 */
	public List findProcessInstances(Long processDefinitionId) {
		return getContext().getGraphSession().findProcessInstances(processDefinitionId);
	}
	
	
	/**
	 * 根据流程ID获取当前流程
	 * @param processInstanceId
	 * @return
	 */
	public ProcessInstance findProcessInstance(Long processInstanceId) {
		return getContext().loadProcessInstance(processInstanceId);
	}
	
	/**
	 * 根据流程定义名称获取流程最近版本
	 * @param processDefinitionName
	 * @return
	 */
	public ProcessDefinition findLatestProcessDefinition(String processDefinitionName){
		return getContext().getGraphSession().findLatestProcessDefinition(processDefinitionName);
	}
	
	/**
	 * 根据designer提交的流程定义代码，发布流程定义
	 * @param   processDefinitionString-jpdl代码
	 * @return  ProcessDefinition-工作流定义
	 */
	public ProcessDefinition deployProcessDefinitionFromDesigner(String processDefinitionString) {
		StringReader stringReader = new StringReader(processDefinitionString);
	    JpdlXmlReader jpdlReader = new JpdlXmlPlugReader(new InputSource(stringReader));
	    ProcessDefinition processDefinition = jpdlReader.readProcessDefinition();
		deployProcessDefinition(processDefinition);
		//接下来将流程中所包含的变量列表保存到数据库表JBPM_TASKVARIABLE中去，其中每个TaskVariable为一行表记录
		RequestHandler requestHandler = RequestHandler .getContextRequestHandler();
		List<TaskVariable> taskVariableList = (List<TaskVariable>)requestHandler.getRequestAttribute("taskVariableList");
		if (null == taskVariableList)return processDefinition; 
		Iterator<TaskVariable> taskVariableIT = taskVariableList.iterator();
		while (taskVariableIT.hasNext()){
			TaskVariable TaskVariable = taskVariableIT.next();
			Session session = getContext().getSessionFactory().getCurrentSession();
			session.save(TaskVariable);
		}
		
		return processDefinition; 
	}
	
	public ProcessDefinition deployProcessDefinition(String processDefinitionBeanName) {
		ProcessDefinitionFactoryBean processDefinitionBean = (ProcessDefinitionFactoryBean)ApplicationContextKeeper.getAppCtx().getBean(processDefinitionBeanName);
		ProcessDefinition processDefinition = deployProcessDefinition(processDefinitionBean.getProcessDefinition());
		List<TaskVariable> taskVariableList = processDefinitionBean.getTaskVariableList();
		Iterator<TaskVariable> taskVariableIT = taskVariableList.iterator();
		while (taskVariableIT.hasNext()){
			TaskVariable TaskVariable = taskVariableIT.next();
			Session session = getContext().getSessionFactory().getCurrentSession();
			session.save(TaskVariable);
		}
		return processDefinition; 
	}
	
	/**
	 * 根据spring定义的BEAN获取流程定义文件，发布流程定义
	 * @param processDefinition
	 * @return
	 */
	public ProcessDefinition deployProcessDefinition(ProcessDefinition processDefinition) {
		getContext().deployProcessDefinition(processDefinition);
		return processDefinition; 
	}
	
	/**
	 * 根据流程定义文件直接发布流程定义
	 * @param processDefinitionFile
	 * @return
	 * @throws FileNotFoundException
	 */
	public long deployProcessDefinition(File processDefinitionFile) throws FileNotFoundException,IOException {
		InputStream is = null;
		ProcessDefinition processDefinition = null;
		try {
		    is = new FileInputStream(processDefinitionFile);
			JpdlXmlPlugReader jpdlReader = new JpdlXmlPlugReader(new InputSource(is)); 
	        processDefinition = jpdlReader.readProcessDefinition(); 
	        getContext().deployProcessDefinition(processDefinition);
		} finally {
			if (is != null) {
				is.close();

			}
		}
		if (null != processDefinition)return processDefinition.getId(); 
		
		return 0;
		
	}
	
	/**
	 * 根据taskInstanceId判断是否当前处理人组
	 * @param processInstanceId
	 * @return
	 */
	public boolean isTheAssignPooledTaskInstances(Long taskInstanceId){
		StringBuffer sqlBF = new StringBuffer();
		sqlBF.append("select taskinstance.id_ from jbpm_taskinstance taskinstance ")
		.append("left join jbpm_taskactorpool taskactorpool on taskinstance.id_ = taskactorpool.taskinstance_ ")
		.append("left join jbpm_pooledactor pooledactor on taskactorpool.pooledactor_ = pooledactor.id_ ")
		.append(" where taskinstance.id_ =:taskinstanceid and pooledactor.actorid_=:actorid ")
		.append("and taskinstance.issuspended_ = 0 ")
		.append("and taskinstance.isopen_ = 1 ");
		Session session = getContext().getSessionFactory().getCurrentSession();
		Query sqlQuery = session.createSQLQuery(sqlBF.toString());
		sqlQuery.setLong("taskinstanceid",taskInstanceId);
		sqlQuery.setString("actorid", getContext().getActorId());
		sqlQuery.setFirstResult(10000);
		sqlQuery.setMaxResults(10000);
		List countResult = sqlQuery.list();
		 if (null != countResult && 0 <countResult.size()){
			return true;
		 }
		return false;
	}
	
	/**
	 * 根据任务ＩＤ判断当前登陆人是否正在处理该任务
	 * @param taskInstanceId
	 * @return
	 */
	public boolean isTheAssignTaskInstances(Long taskInstanceId){
		StringBuffer sqlBF = new StringBuffer();
		sqlBF.append("select ti.id_ from jbpm_taskinstance ti ")
		.append("where ti.actorid_ = :actorId ")
		.append("and ti.id_ = :taskInstanceId ")
		.append("and ti.issuspended_ != 1 ")
		.append("and ti.isopen_ = 1");
		Session session = getContext().getSessionFactory().getCurrentSession();
		Query sqlQuery = session.createSQLQuery(sqlBF.toString());
		sqlQuery.setBigDecimal("taskInstanceId",BigDecimal.valueOf(taskInstanceId.longValue()));
		sqlQuery.setString("actorId", getContext().getActorId());
		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(10000);
		List countResult = sqlQuery.list();
		 if (null != countResult && 0 <countResult.size()){
			return true;
		 }
		return false;
	}
	
	/**
	 * 根据流程名称发起新流程，返回新流程ＩＤ	
	 * @param processDefinitionName-流程名称
	 * @param model-实体对象
	 * @return 流程实例的Id
	 */
	public Long startProcess(String processDefinitionName,Object model) {
		String entityName = null;
		Serializable identifier = null;
		if ( model instanceof HibernateProxy ) {
			LazyInitializer li = ( (HibernateProxy) model ).getHibernateLazyInitializer();
			entityName = li.getEntityName();
			identifier = li.getIdentifier();
		} else {
			EntityPersister meta = ((SessionFactoryImplementor)getContext().getSessionFactory()).getEntityPersister(model.getClass().getName());
			entityName = meta.getEntityName();
			identifier = meta.getIdentifier(model, meta.guessEntityMode(model));
		}
		

		ProcessDefinition processDefinition = findLatestProcessDefinition(processDefinitionName);
		//建立流程实例
		ProcessInstance processInstance = processDefinition.createProcessInstance();
		processInstance.setKey(String.valueOf(processInstance.getId()));
		//记录流程的发起者
	//	processInstance.getContextInstance().setVariable("processStarter", RequestHandler.getContextRequestHandler().getCurrentUser());
		processInstance.getContextInstance().setVariable("entityName",entityName);
		processInstance.getContextInstance().setVariable("entityIdentiferValue",identifier);
		//流程开始
		processInstance.signal();
		//保存当前流程实例
		saveProcessInstance(processInstance);
		if (model instanceof WorkFlowBaseDomain){
			WorkFlowBaseDomain workFlowBaseDomain = (WorkFlowBaseDomain)model;
			workFlowBaseDomain.setEntityClass(entityName);
			workFlowBaseDomain.setProcessInstance(processInstance);
			workFlowBaseDomain.setProcessStartDate(new Date());
			if (null != RequestHandler.getContextRequestHandler()){
				workFlowBaseDomain.setProcessStarter(RequestHandler.getContextRequestHandler().getCurrentUser());
			}
			
		}
		return processInstance.getId();
	}
	
	/**
	 * 根据流程名称发起新流程，返回新流程ＩＤ	
	 * @param processDefinitionName-流程名称
	 * @return 流程实例的Id
	 */
	public Long startProcess(String processDefinitionName) {
		ProcessDefinition processDefinition = findLatestProcessDefinition(processDefinitionName);
		ProcessInstance processInstance = processDefinition.createProcessInstance();
		processInstance.signal();
		saveProcessInstance(processInstance);
		return processInstance.getId();
	}
	
	/**
	 * 根据processInstanceId获取当前用户在该流程下待处理的（非专人处理）任务列表
	 * @param processInstanceId-流程实例的Id
	 * @return 该流程实例下的任务列表
	 */
	public List getAssignPooledTaskInstanceIds(Long processInstanceId){
		StringBuffer sqlBF = new StringBuffer();
		sqlBF.append("select taskinstance.id_,taskinstance.name_ from jbpm_taskinstance taskinstance ")
		.append("left join jbpm_taskactorpool taskactorpool on taskinstance.id_ = taskactorpool.taskinstance_ ")
		.append("inner join jbpm_pooledactor pooledactor on taskactorpool.pooledactor_ = pooledactor.id_ ")
		.append(" where taskinstance.procinst_ =:processInstanceId and pooledactor.actorid_=:actorid and taskinstance.start_ is null ")
		.append("and taskinstance.issuspended_ = 0 ")
		.append("and taskinstance.isopen_ = 1 ");
		Session session = getContext().getSessionFactory().getCurrentSession();
		Query sqlQuery = session.createSQLQuery(sqlBF.toString());
		sqlQuery.setBigDecimal("processInstanceId",BigDecimal.valueOf(processInstanceId.longValue()));
		sqlQuery.setString("actorid", getContext().getActorId());
		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(10000);
		return sqlQuery.list();
	}
	
	/**
	 * 根据processInstanceId获取当前用户在该流程下待处理的（专人处理）任务列表
	 * @param processInstanceId-流程实例的Id
	 * @return 该流程实例下的任务列表
	 */
	public List getAssignTaskInstanceIds(Long processInstanceId){
		StringBuffer sqlBF = new StringBuffer();
		sqlBF.append("select ti.id_,ti.name_ from jbpm_taskinstance ti ")
		.append("where ti.actorid_ = :actorId ")
		.append("and ti.procinst_ = :processInstanceId ")
		.append("and ti.issuspended_ = 0 ")
		.append("and ti.isopen_ = 1");
		Session session = getContext().getSessionFactory().getCurrentSession();
		Query sqlQuery = session.createSQLQuery(sqlBF.toString());
		sqlQuery.setBigDecimal("processInstanceId",BigDecimal.valueOf(processInstanceId.longValue()));
		sqlQuery.setString("actorId", getContext().getActorId());
		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(10000);
		return sqlQuery.list();
	}
	
	/**
	 * 根据处理人Id获取其事务列表
	 * @param actorId-处理人Id
	 * @return 事务列表
	 */
	public List findTaskInstances(final String actorId,final String processDefinitionName,Page page) {
		
		return getContext().getTaskMgmtSession().findTaskInstances(actorId);
	}
	/**
	 * 在数据库中获取事务的相关变量
	 * @param task-事务
	 * @param keyName-变量名
	 * @return 待获取的事务变量
	 */
	public TaskVariable getVariablebyTask(long taskId,String keyName){
		Session session = getContext().getSessionFactory().getCurrentSession();
		Query query = session.createQuery("from TaskVariable taskTaskVariable where taskTaskVariable.task.id = "+taskId
					+" and taskTaskVariable.name = '" + keyName +"'");
		List valueList = query.list();
		if (null!= valueList && 0 <valueList.size()){
			return (TaskVariable)valueList.get(0);
		}
		return null;
	}
	/**
	 * 将事务实例保存到数据库中待下次处理
	 * @param taskInstance-当前事务实例
	 */
	public void saveTaskInstances(TaskInstance taskInstance) {
		Session session = getContext().getSessionFactory().getCurrentSession();
		session.save(taskInstance);
	}
}
