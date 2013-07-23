/* com.cutty.bravo.core.domain.annotation.M2MEntity.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-1-8 上午11:41:40, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.domain.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * <p>
 * <a href="M2MEntity.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface M2MEntity {
	String[] actionClasses() ;//当前m2m所对应的action类
	String[] fieldNames();//当前m2m所对应的字段名
}
