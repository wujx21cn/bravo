/* com.cutty.bravo.components.demo.webservice.impl.StudentWebServiceImpl.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Mar 17, 2009 1:41:43 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.integrate.ws.student.impl;

import java.util.List;

import javax.jws.WebService;
import javax.servlet.ServletContext;

import com.cutty.bravo.components.demo.domain.Student;
import com.cutty.bravo.components.demo.manager.StudentManager;
import com.cutty.bravo.components.integrate.ws.student.StudentWebService;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;



/**
 *
 * <p>
 * <a href="StudentWebServiceImpl.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@WebService(endpointInterface="com.cutty.bravo.components.integrate.ws.student.StudentWebService") 
public class StudentWebServiceImpl implements StudentWebService {
	private StudentManager studentManager;
	
	public StudentManager getStudentManager() {
		return studentManager;
	}

	public void setStudentManager(StudentManager studentManager) {
		this.studentManager = studentManager;
	}

	public String sayHi(String text) {
		ServletContext sc = ApplicationContextKeeper.getServletContext();
		System.out.println(sc);
		// TODO Auto-generated method stub
		return "儿子乖";
	}
	
	public List<Student> getStudents(){
		return studentManager.getAll();
	}
}
