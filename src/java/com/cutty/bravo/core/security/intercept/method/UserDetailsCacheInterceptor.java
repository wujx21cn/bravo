/* com.cutty.bravo.core.security.intercept.method.UserDetailsCacheInterceptor.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-1-4 上午10:58:07, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.intercept.method;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 *
 * <p>
 * <a href="UserDetailsCacheInterceptor.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Aspect  
public class UserDetailsCacheInterceptor {
	
	@Pointcut("execution(* com.cutty.bravo.components..*Manager.*(..))")   
	public void simplePointcut() { }  
	
	@AfterReturning(pointcut="simplePointcut()")   
	public void simpleAdvice() {   
		System.out.println("Merry Christmas");
		System.out.println("Merry Christmas");  
		System.out.println("Merry Christmas");  
		System.out.println("Merry Christmas");  
		System.out.println("Merry Christmas");  
		System.out.println("Merry Christmas");  
		System.out.println("Merry Christmas");  
		System.out.println("Merry Christmas");  
		System.out.println("Merry Christmas");  
		System.out.println("Merry Christmas");  
		System.out.println("Merry Christmas");  
		System.out.println("Merry Christmas");     
	}
}
