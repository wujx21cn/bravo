/* com.cutty.bravo.core.utils.cache.redis.KeyElement.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Jul 30, 2013 3:54:15 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.utils.cache.redis;

import java.io.Serializable;

/**
 *
 * <p>
 * <a href="KeyElement.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class KeyElement implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8600012486919402539L;
	private Object keyObj;
	public KeyElement(Object key){
		keyObj = key;
	}
	
	public Object getKey(){
		return keyObj;
	}

}
