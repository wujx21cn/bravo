package com.cutty.bravo.core.security.web;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.core.security.domain.ButtonResource;
import com.cutty.bravo.core.web.struts2.EntityAction;


@Namespace("/security")   
@ParentPackage("bravo")
public class ButtonResourceAction extends EntityAction<ButtonResource> {
 
	private static final long serialVersionUID = 6474178096579782080L;

}
