/* com.cutty.bravo.core.security.intercept.method.Matcher.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-18 下午02:00:38, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.intercept.method;

/**
 *
 * <p>
 * <a href="Matcher.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public interface Matcher <P, T> {

	/**
	 * 判断指定资源描述符和指定目标对象资源是否匹配
	 * 
	 * @param pattern 资源描述符
	 * @param target 目标对象
	 * @return 是否匹配
	 */
	boolean match(P pattern, T target);
}