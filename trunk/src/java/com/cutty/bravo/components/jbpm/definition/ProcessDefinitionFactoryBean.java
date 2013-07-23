/* com.cutty.bravo.components.jbpm.definition.ProcessDefinitionFactoryBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-3 下午01:17:55, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jbpm.definition;

import java.io.InputStream;
import java.util.List;

import org.jbpm.graph.def.ProcessDefinition;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.xml.sax.InputSource;

import com.cutty.bravo.components.jbpm.parser.JpdlXmlPlugReader;
import com.cutty.bravo.components.jbpm.task.domain.TaskVariable;

/**
 *
 * 该类实现FactoryBean和InitializingBean接口，实现工作流定义描述的读取控制
 * <p>
 * <a href="ProcessDefinitionFactoryBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class ProcessDefinitionFactoryBean   implements FactoryBean, InitializingBean {

	private ProcessDefinition processDefinition;

	private Resource definitionLocation;	

	private List<TaskVariable> taskVariableList;
	
	/**
	 * 设置工作流定义描述资源的定位
	 * @param definitionLocation-工作流定义描述的资源定位
	 */
	public void setDefinitionLocation(Resource definitionLocation) {
		this.definitionLocation = definitionLocation;
	}
	
	/**
	 * 根据工作流定义描述的资源定位读取工作流定义
	 */
	public void afterPropertiesSet() throws Exception {
		if (this.definitionLocation == null) {
			throw new FatalBeanException("Property [definitionLocation] of class ["
					+ ProcessDefinitionFactoryBean.class.getName()
					+ "] is required.");
		}

		InputStream inputStream = null;
		try {
			inputStream = this.definitionLocation.getInputStream();
			JpdlXmlPlugReader jpdlReader = new JpdlXmlPlugReader(new InputSource(inputStream)); 
			this.processDefinition = jpdlReader.readProcessDefinition(); 
			//taskVariableList= jpdlReader.getTaskVariableList();

		}
		finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	/**
	 * 返回当前实现FactoryBean接口的对象引用，接口实现需要
	 */
	public Object getObject() throws Exception {
		return this;
	}

	/**
	 * 返回当前实现FactoryBean接口的类名，接口实现需要
	 */
	public Class getObjectType() {
		return ProcessDefinitionFactoryBean.class;
	}

	/**
	 * 返回当前接口对象为单态模式
	 */
	public boolean isSingleton() {
		return true;
	}

	/**
	 * 获取工作流定义描述
	 * @return-工作流定义描述
	 */
	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	/**
	 * 获取任务变量列表
	 * @return-任务变量列表
	 */
	public List getTaskVariableList() {
		return taskVariableList;
	}

	
	
}
