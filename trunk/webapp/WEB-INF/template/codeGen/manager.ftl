/* ${managerPath}.${managerName}.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		${createDate}, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2011 Cutty Corporation. All Rights Reserved.

*/
package ${managerPath};

import org.springframework.stereotype.Service;

import com.cutty.bravo.core.manager.BaseManager;
import ${entityPath}.${entityName};
/**
 *
 * <p>
 * <a href="${managerName}.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Service("${managerName?uncap_first}")
public class ${managerName} extends BaseManager<${entityName}>{

}
