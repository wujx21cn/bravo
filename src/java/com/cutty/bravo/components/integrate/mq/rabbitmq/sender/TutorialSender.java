/* com.cutty.bravo.components.integrate.mq.rabbitmq.sender.TutorialSender.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Aug 2, 2013 2:07:47 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.integrate.mq.rabbitmq.sender;

import org.springframework.amqp.core.AmqpTemplate;

/**
 *
 * <p>
 * <a href="TutorialSender.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class TutorialSender {
	
	AmqpTemplate aTemplate;

	public void setaTemplate(AmqpTemplate aTemplate) {
		this.aTemplate = aTemplate;
	}

	

}
