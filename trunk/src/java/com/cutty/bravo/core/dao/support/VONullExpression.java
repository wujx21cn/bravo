/* com.cutty.bravo.core.dao.support.VONullExpression.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-20 上午02:05:00, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.dao.support;

import org.hibernate.criterion.NullExpression;

/**
 *
 * <p>
 * <a href="VONullExpression.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class VONullExpression extends NullExpression {
	private static final long serialVersionUID = -5375678654318474062L;

	protected VONullExpression(String propertyName) {
		super(propertyName);
	}
}
