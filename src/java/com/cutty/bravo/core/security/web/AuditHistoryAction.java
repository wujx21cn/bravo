/* com.cutty.bravo.components.common.web.AuditHistoryAction.java

{{IS_NOTE
 * <p>
 * <a href="AuditHistoryAction.java.html"><i>View Source</i></a>
 * </p>
		
	History:
		2008-10-16 ����13:48:53, Created by Cathy.Lin
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.web;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.core.security.domain.AuditHistory;
import com.cutty.bravo.core.web.struts2.EntityAction;



@Namespace("/security")   
@ParentPackage("bravo")
public class AuditHistoryAction extends EntityAction<AuditHistory>{
	private static final long serialVersionUID = 4630955410414669667L;
}
