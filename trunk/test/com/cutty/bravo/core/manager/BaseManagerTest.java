/* com.cutty.bravo.core.manager.BaseManagerTest.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-1-22 下午01:12:06, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.manager;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


/**
 *
 * <p>
 * <a href="BaseManagerTest.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public abstract class BaseManagerTest<T> extends AbstractTransactionalDataSourceSpringContextTests {
	
	@Override
	protected String[] getConfigLocations() {
		return new String[] { "classpath:/spring/dataAccessContext.xml", "classpath:/spring/applicationContext.xml", "classpath:/spring/applicationContext-components.xml" };
	}
}
