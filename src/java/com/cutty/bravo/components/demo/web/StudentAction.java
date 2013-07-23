package com.cutty.bravo.components.demo.web;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.components.demo.domain.Student;
import com.cutty.bravo.core.web.struts2.EntityAction;


@Namespace("/common")   
@ParentPackage("bravo")
public class StudentAction extends EntityAction<Student> {

	private static final long serialVersionUID = 7289780652732288049L;

}
