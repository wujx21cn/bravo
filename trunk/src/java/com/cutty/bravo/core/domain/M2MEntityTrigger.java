/* com.cutty.bravo.core.domain.M2MEntityTrigger.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-1-8 下午03:19:28, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.domain;

import com.cutty.bravo.core.exception.BizException;

/**
 *
 * <p>
 * <a href="M2MEntityTrigger.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public interface M2MEntityTrigger {
	public static final String OPERATION_ADD="add";
	public static final String OPERATION_REMOVE="remove";
	public void trigger(String M2M_ENTITY_ID,String[] ids,String operation) throws BizException;
}
