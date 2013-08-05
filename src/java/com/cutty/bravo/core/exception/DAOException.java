/* com.cutty.bravo.core.exception.DAOException.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2011-9-13 下午03:16:39, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.exception;

/**
 *
 * <p>
 * <a href="DAOException.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class DAOException  extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4008550814462019034L;
    public DAOException(String message) {
    	super(message);
        }
}
