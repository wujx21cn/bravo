/* com.cutty.bravo.components.demo.webservice.StudentWebService.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Mar 17, 2009 1:41:02 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.integrate.ws.student;

import java.util.List;

import javax.jws.WebService;

import com.cutty.bravo.components.demo.domain.Student;

/**
 *
 * <p>
 * <a href="StudentWebService.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@WebService
public interface StudentWebService {
	public String sayHi(String text);
	public List<Student> getStudents();
}
