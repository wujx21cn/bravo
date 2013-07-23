package com.cutty.bravo.components.demo.web;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.components.demo.domain.Teacher;
import com.cutty.bravo.core.web.struts2.EntityAction;


@Namespace("/common")   
@ParentPackage("bravo")
public class TeacherAction extends EntityAction<Teacher> {

	private static final long serialVersionUID = 4269440644971689660L;

}
