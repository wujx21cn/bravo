/* com.cutty.bravo.core.security.intercept.method.SimpleAspectJMethodMatcher.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-18 下午02:01:11, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.intercept.method;

import java.lang.reflect.Method;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;

/**
 * Method匹配器实现
 * 用AspectJ PointCut表达式匹配目标方法
 * 
 * <p>
 * <a href="SimpleAspectJMethodMatcher.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class SimpleAspectJMethodMatcher implements Matcher<String, Method> {

    private PointcutParser pointcutParser = PointcutParser
                                                  .getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();

    public boolean match(String pattern, Method method) {
        pattern = "execution(" + pattern + ")";
        PointcutExpression pe = pointcutParser.parsePointcutExpression(pattern);
        ShadowMatch match = pe.matchesMethodExecution(method);
        return match.alwaysMatches();
    }

    public void setPointcutParser(PointcutParser pointcutParser) {
        this.pointcutParser = pointcutParser;
    }

    public PointcutParser getPointcutParser() {
        return pointcutParser;
    }
}
