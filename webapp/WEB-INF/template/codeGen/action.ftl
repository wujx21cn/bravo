/* ${actionnPath}.${actionnName}.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		${createDate}, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2011 Cutty Corporation. All Rights Reserved.

*/
package ${actionnPath};

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.core.web.struts2.EntityAction;
import ${entityPath}.${entityName};

/**
 *
 * <p>
 * <a href="${actionnName}.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Namespace("/${moduleName}")
@ParentPackage("bravo")
public class ${actionnName} extends EntityAction<${entityName}> {
	private static final long serialVersionUID = 1L;
}