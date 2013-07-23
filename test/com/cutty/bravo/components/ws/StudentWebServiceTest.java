/* com.cutty.bravo.components.ws.StudentWebServiceTest.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Mar 17, 2009 2:52:08 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.ws;

import java.util.Iterator;
import java.util.List;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import com.cutty.bravo.components.demo.domain.Student;
import com.cutty.bravo.components.ws.student.StudentWebService;

/**
 *
 * <p>
 * <a href="StudentWebServiceTest.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class StudentWebServiceTest extends AbstractTransactionalDataSourceSpringContextTests {
	@Override
	protected String[] getConfigLocations() {
		return new String[] { "classpath:/spring/dataAccessContext.xml", "classpath:/spring/applicationContext.xml", "classpath:/spring/applicationContext-components.xml" };
	}
	

	public void testsayHi() {
		StudentWebService studentClient = (StudentWebService)super.getApplicationContext().getBean("studentClient");
		String abc = studentClient.sayHi("oh my father");
		System.out.println(abc);
	}
	
	
	public void testgetStudents() {
		StudentWebService studentClient = (StudentWebService)super.getApplicationContext().getBean("studentClient");
		List<Student> abc = studentClient.getStudents();
		Iterator<Student> cba1 = abc.iterator();
		while (cba1.hasNext()){
			System.out.println(cba1.next().getName());
			
		}
	}
}
