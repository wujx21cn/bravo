/* com.cutty.bravo.components.jbpm.task.action.TaskEndAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-3 下午02:50:12, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jbpm.task.action;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

/**
 * 该类实现了一个Action事件响应接口，用于在任务结束后调用，写在默认的事务节点中
 *
 * <p>
 * <a href="TaskEndAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class TaskEndAction implements ActionHandler{
	private static final long serialVersionUID = 504247466941152634L;

	/**
	 * 事件的执行体部分
	 */
	public void execute(ExecutionContext executionContext) throws Exception {
		System.out.println("任务结束。。。。");
		
	}

}
