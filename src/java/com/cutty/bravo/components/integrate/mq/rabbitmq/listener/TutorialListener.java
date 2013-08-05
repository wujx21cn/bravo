/* com.cutty.bravo.components.integrate.mq.rabbitmq.listener.TutorialListener.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Aug 2, 2013 2:05:04 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008  Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.integrate.mq.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 *
 * <p>
 * <a href="TutorialListener.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class TutorialListener  implements MessageListener {
	
    public void onMessage(Message message) {
    	String messageBody= new String(message.getBody());
        System.out.println("Listener received message----->"+messageBody);
    }
    
}