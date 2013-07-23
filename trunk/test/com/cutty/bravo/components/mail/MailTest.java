/* com.cutty.bravo.components.mail.MailTest.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Mar 24, 2009 2:08:58 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


/**
 *
 * <p>
 * <a href="MailTest.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class MailTest  extends AbstractTransactionalDataSourceSpringContextTests {
	@Override
	protected String[] getConfigLocations() {
		return new String[] { "classpath:/spring/dataAccessContext.xml", "classpath:/spring/applicationContext.xml", "classpath:/spring/applicationContext-components.xml" };
	}
	
	public void testMail() {
		JavaMailSender mailSender= (JavaMailSender) super.getApplicationContext().getBean("mailSender");
		SimpleMailMessage mail = new SimpleMailMessage();   
        mail.setTo("wujx21cn@gmail.com");   
        mail.setSubject(" 测试spring Mail");   
        mail.setText("hello,java");   
        mailSender.send(mail);
        System.out.println("ddd");
	}
}
