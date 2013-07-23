/* com.cutty.bravo.components.common.web.EnumerationAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-19 下午01:48:53, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.web;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.components.common.domain.Enumeration;
import com.cutty.bravo.core.web.struts2.EntityAction;

/**
 *
 * <p>
 * <a href="EnumerationAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Namespace("/common")   
@ParentPackage("bravo")
public class EnumerationAction extends EntityAction<Enumeration>{
	private static final long serialVersionUID = 4414334106225082567L;
}
