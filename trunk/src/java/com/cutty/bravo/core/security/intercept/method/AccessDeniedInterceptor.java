/* com.cutty.bravo.core.security.intercept.method.AccessDeniedInterceptor.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-18 下午01:33:08, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.intercept.method;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.security.AccessDeniedException;

/**
 * 
 * <p>
 * <a href="AccessDeniedInterceptor.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class AccessDeniedInterceptor implements ThrowsAdvice {

    public void afterThrowing(Method method, Object[] args, Object target,
            AccessDeniedException exception) {
        System.out.println("access denied.....");
        //TODO 通过DatabaseMethodDefinitionSource拒绝访问后的处理
    }
}
