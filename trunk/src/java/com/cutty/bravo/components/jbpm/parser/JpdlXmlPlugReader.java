/* com.cutty.bravo.components.jbpm.parser.JpdlXmlPlugReader.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-3 下午12:43:05, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jbpm.parser;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.jbpm.graph.action.ActionTypes;
import org.jbpm.graph.def.Action;
import org.jbpm.graph.def.Event;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.instantiation.Delegation;
import org.jbpm.jpdl.xml.ProblemListener;
import org.jbpm.taskmgmt.def.Swimlane;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.def.TaskMgmtDefinition;
import org.xml.sax.InputSource;

import com.cutty.bravo.components.jbpm.task.domain.TaskVariable;
import com.cutty.bravo.core.web.handler.RequestHandler;

/**
 * 
 * 该类重写了JBPM自带的JpdlXmlReader类的两个方法：readTask和readAssignmentDelegation方法
 * 用于自定义这两个节点的读取方法，另外补充了一个为task添加默认事件响应的方法
 * 
 * <p>
 * <a href="JpdlXmlPlugReader.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class JpdlXmlPlugReader extends org.jbpm.jpdl.xml.JpdlXmlReader {

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(JpdlXmlPlugReader.class);

	
	public JpdlXmlPlugReader(InputSource inputSource) {
		super(inputSource);
	}

	public JpdlXmlPlugReader(InputSource inputSource, ProblemListener problemListener) {
		super(inputSource, problemListener);
	}

	public JpdlXmlPlugReader(Reader reader) {
		super(reader);
	}
	/**
	 * 重写readTask方法
	 */
	@Override
	public Task readTask(Element taskElement, TaskMgmtDefinition taskMgmtDefinition, TaskNode taskNode) {
		  Task task = new Task();
		  task.setProcessDefinition(processDefinition);
		    
		  // get the task name
	    String name = taskElement.attributeValue("name");
	    if (name!=null) {
	      task.setName(name);
	      taskMgmtDefinition.addTask(task);
	    } else if (taskNode!=null) {
	      task.setName(taskNode.getName());
	      taskMgmtDefinition.addTask(task);
	    }
	    
	    // parse common subelements
	    readTaskTimers(taskElement, task);
	    readEvents(taskElement, task);
	    
	    addDefaultTaskEvent(task);
	    
	    readExceptionHandlers(taskElement, task);
	    
	  

	    // description and duration
	    task.setDescription(taskElement.attributeValue("description"));
	    String duedateText = taskElement.attributeValue("duedate");
	    if (duedateText==null) {
	      duedateText = taskElement.attributeValue("dueDate");
	    }
	    task.setDueDate(duedateText);
	    String priorityText = taskElement.attributeValue("priority");
	    if (priorityText!=null) {
	      task.setPriority(Task.parsePriority(priorityText));
	    }
	    
	    // if this task is in the context of a taskNode, associate them
	    if (taskNode!=null) {
	      taskNode.addTask(task);
	    }

	    // blocking
	    String blockingText = taskElement.attributeValue("blocking");
	    if (blockingText!=null) {
	      if ( ("true".equalsIgnoreCase(blockingText))
	           || ("yes".equalsIgnoreCase(blockingText))
	           || ("on".equalsIgnoreCase(blockingText)) ) {
	        task.setBlocking(true);
	      }
	    }
	    
	    // signalling
	    String signallingText = taskElement.attributeValue("signalling");
	    if (signallingText!=null) {
	      if ( ("false".equalsIgnoreCase(signallingText))
	           || ("no".equalsIgnoreCase(signallingText))
	           || ("off".equalsIgnoreCase(signallingText)) ) {
	        task.setSignalling(false);
	      }
	    }
	    
	    // assignment
	    String swimlaneName = taskElement.attributeValue("swimlane");
	    Element assignmentElement = taskElement.element("assignment");

	    // if there is a swimlane attribute specified
	    if (swimlaneName!=null) {
	      Swimlane swimlane = taskMgmtDefinition.getSwimlane(swimlaneName);
	      if (swimlane==null) {
	        addWarning("task references unknown swimlane '"+swimlaneName+"':"+taskElement.asXML());
	      } else {
	        task.setSwimlane(swimlane);
	      }

	    // else if there is a direct assignment specified
	    } else if (assignmentElement!=null) {
	        Delegation assignmentDelegation = readAssignmentDelegation(assignmentElement);
	        task.setAssignmentDelegation(assignmentDelegation);
	    // if no assignment or swimlane is specified
	    } else {
	      // the user has to manage assignment manually, so we better warn him/her.
	      addWarning("warning: no swimlane or assignment specified for task '"+taskElement.asXML()+"'");
	    }
	    
	    // task controller
	    Element taskControllerElement = taskElement.element("controller");
	    if (taskControllerElement!=null) {
	      task.setTaskController(readTaskController(taskControllerElement));
	    }
	    RequestHandler requestHandler = RequestHandler .getContextRequestHandler();
	    List<TaskVariable> taskVariableList =  (List<TaskVariable>)requestHandler.getRequestAttribute("taskVariableList");
	    if (null == taskVariableList) taskVariableList = new ArrayList<TaskVariable>();
	    Iterator variableElements = taskElement.elements("variable").iterator();
	    while(variableElements.hasNext()){
	    	Element variableElement = (Element) variableElements.next();
	    	TaskVariable taskVariable = new TaskVariable();
	    	taskVariable.setTask(task);
	    	taskVariable.setTaskNode(taskNode);
	    	taskVariable.setName(variableElement.attributeValue("name"));
	    	taskVariable.setValue(variableElement.getStringValue());
	    	//taskVariable.setValue(value);
	    	taskVariableList.add(taskVariable);
	    }
	    requestHandler.setRequestAttribute("taskVariableList", taskVariableList);
	    return task;
	  }
	  /**
	   * 重写readAssignmentDelegation方法，只读取三个标签:expression、actor-id、pooled-actors的内容
	   * @param assignmentElement-afasfasf
	   */
	  @Override
	protected Delegation readAssignmentDelegation(Element assignmentElement) {
		    Delegation assignmentDelegation = new Delegation();
		    String expression = assignmentElement.attributeValue("expression");
		    String actorId = assignmentElement.attributeValue("actor-id");
		    String pooledActors = assignmentElement.attributeValue("pooled-actors");
		    if (expression!=null || actorId!=null || pooledActors!=null){
		    	String configuration = "";
		        assignmentDelegation.setProcessDefinition(processDefinition);
		        assignmentDelegation.setClassName("com.cutty.bravo.components.jbpm.handler.SS2AssignmentHandler");
		    	if (expression!=null){
		    		configuration += "<expression>"+expression+"</expression>";
			    } 
		    	if (actorId!=null) {
			          configuration += "<actorId>"+actorId+"</actorId>";
			    }
		    	if (pooledActors!=null) {
			          configuration += "<roleId>"+pooledActors+"</roleId>";
			    }
		    	assignmentDelegation.setConfiguration(configuration);
		    } else {
		        assignmentDelegation.read(assignmentElement, this);
	      	}
	    return assignmentDelegation;
  	}

	/**
	 * 为事务添加默认的事件响应
	 * @param task-被添加默认事件的事务
	 */
	public void addDefaultTaskEvent(Task task){
		
	    Event taskCreateEvent = task.getEvent("task-create");
	    Event taskEndEvent = task.getEvent("task-create");
	    if (null == taskCreateEvent){
	    	taskCreateEvent = new Event("task-create");
	    	task.addEvent(taskCreateEvent);
	    }
	    if (null == taskEndEvent){
	    	taskEndEvent = new Event("task-end");
	    	task.addEvent(taskEndEvent);
	    }
	    
	    try {
	    	 Class actionType = ActionTypes.getActionType("action");
	    	 Action taskCreateAction = (Action) actionType.newInstance();
	    	 Action TaskEndAction = (Action) actionType.newInstance();
	    	Delegation taskCreateActionDelegation = new Delegation();
	    	taskCreateActionDelegation.setProcessDefinition(this.getProcessDefinition());
	    	taskCreateActionDelegation.setClassName("com.cutty.bravo.components.jbpm.task.action.TaskStartAction");
			taskCreateAction.setActionDelegation(taskCreateActionDelegation);
			taskCreateAction.setEvent(taskCreateEvent);
			taskCreateEvent.addAction(taskCreateAction);
			
	    	Delegation taskEndActionDelegation = new Delegation();
	    	taskEndActionDelegation.setProcessDefinition(this.getProcessDefinition());
	    	taskEndActionDelegation.setClassName("com.cutty.bravo.components.jbpm.task.action.TaskEndAction");
	    	TaskEndAction.setActionDelegation(taskEndActionDelegation);
	    	TaskEndAction.setEvent(taskEndEvent);
	    	taskEndEvent.addAction(TaskEndAction);
		} catch (InstantiationException e) {
			log.error(e);
		} catch (IllegalAccessException e) {
			log.error(e);
		}
		
	}
	  
	  
}
