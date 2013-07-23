/* com.cutty.bravo.core.dao.support.VONotNullExpression.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-12-15 上午09:59:24, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.dao.support;

import org.hibernate.criterion.NotNullExpression;

/**
 *
 * <p>
 * <a href="VONotNullExpression.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class VONotNullExpression extends NotNullExpression{

	protected VONotNullExpression(String propertyName) {
		super(propertyName);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2563840472682633187L;

}
