/* com.cutty.bravo.components.common.web.TestAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Aug 2, 2013 2:30:41 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.web;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.amqp.core.AmqpTemplate;

import com.cutty.bravo.core.web.struts2.BaseActionSupport;

/**
 *
 * <p>
 * <a href="TestAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@ParentPackage("bravo")
@Namespace("/common")   
public class TestAction  extends BaseActionSupport{

	private static final long serialVersionUID = -7195085171005097619L;
	private AmqpTemplate topicMQTemplate;
	private AmqpTemplate directMQTemplate;
	
	public String sendMq(){
		topicMQTemplate.convertAndSend("my.1.fuck18M","fuck18m");
		return "mq";
	}

	public void setTopicMQTemplate(AmqpTemplate topicMQTemplate) {
		this.topicMQTemplate = topicMQTemplate;
	}

	public void setDirectMQTemplate(AmqpTemplate directMQTemplate) {
		this.directMQTemplate = directMQTemplate;
	}


	

}
