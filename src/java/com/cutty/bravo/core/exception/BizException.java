/* com.cutty.bravo.core.exception.BizException.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2011-9-13 下午03:14:39, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.exception;

/**
 *
 * <p>
 * <a href="BizException.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class BizException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1653702174460786872L;
    public BizException(String message) {
    	super(message);
        }
}
