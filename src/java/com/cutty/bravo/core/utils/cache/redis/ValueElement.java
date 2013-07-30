/* com.cutty.bravo.core.utils.cache.redis.ValueElement.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Jul 30, 2013 3:57:03 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.utils.cache.redis;

import java.io.Serializable;

/**
 *
 * <p>
 * <a href="ValueElement.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class ValueElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5529322183035660227L;
	private Object valueObj;
	
	public ValueElement(Object value){
		valueObj = value;
	}
	
	public Object getValue(){
		return valueObj;
	}


}
