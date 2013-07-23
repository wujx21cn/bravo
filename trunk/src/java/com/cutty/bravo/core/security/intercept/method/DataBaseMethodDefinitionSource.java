/* com.cutty.bravo.core.security.intercept.method.DataBaseMethodDefinitionSource.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-17 下午05:19:47, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.intercept.method;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.intercept.method.MethodDefinitionSource;

import com.cutty.bravo.core.exception.BizException;
import com.cutty.bravo.core.security.Constants;
import com.cutty.bravo.core.security.manager.cache.ResourceCacheManager;


/**
 *
 * <p>
 * <a href="DataBaseMethodDefinitionSource.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class DataBaseMethodDefinitionSource  implements MethodDefinitionSource {

	protected final Log logger = LogFactory.getLog(DataBaseMethodDefinitionSource.class);
	
	private ResourceCacheManager resourceCacheManager;
	
	public ConfigAttributeDefinition getAttributes(Method method, Class targetClass) {
		// TODO Auto-generated method stub
		return null;
	}

	public ConfigAttributeDefinition getAttributes(Object object) {
		if (object instanceof MethodInvocation) {
			MethodInvocation miv = (MethodInvocation) object;
			try {
				return this.lookupAttributes(miv.getThis().getClass(), miv.getMethod());
			} catch (BizException e) {
				 throw new IllegalArgumentException(e.getMessage());
			}
		}

		if (object instanceof JoinPoint) {
			JoinPoint jp = (JoinPoint) object;
			Class targetClazz = jp.getTarget().getClass();
			String targetMethodName = jp.getStaticPart().getSignature().getName();
			Class[] types = ((CodeSignature) jp.getStaticPart().getSignature()).getParameterTypes();

			if (logger.isDebugEnabled()) {
				logger.debug("Target Class: " + targetClazz);
				logger.debug("Target Method Name: " + targetMethodName);

				for (int i = 0; i < types.length; i++) {
					logger.debug("Target Method Arg #" + i + ": " + types[i]);
				}
			}

			try {
				return this.lookupAttributes(targetClazz, targetClazz.getMethod(targetMethodName, types));
			} catch (NoSuchMethodException nsme) {
				throw new IllegalArgumentException("Could not obtain target method from JoinPoint: " + jp, nsme);
			} catch (SecurityException e) {
				throw new IllegalArgumentException(e.getMessage());
			} catch (BizException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		}

		throw new IllegalArgumentException("Object must be a MethodInvocation or JoinPoint");
	}

	public Collection getConfigAttributeDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean supports(Class clazz) {
		return (MethodInvocation.class.isAssignableFrom(clazz) || JoinPoint.class.isAssignableFrom(clazz));
	}
	

	protected ConfigAttributeDefinition lookupAttributes(Class clszz, Method mi) throws BizException {
		// 获取所有的method类型的资源
		List<String> methodStrings = resourceCacheManager.getResourcesStrByTypeFromCache(Constants.METHOD);
		if (null == methodStrings)return null;
		Set auths = new HashSet();
		// 取权限的合集
		for (Iterator iter = methodStrings.iterator(); iter.hasNext();) {
			String methodString = (String) iter.next();
			if (isMatch(clszz, mi, methodString)) {
				String[] authorities = resourceCacheManager.findResourceAuthorityByResString(methodString,Constants.METHOD);
				if (authorities == null || authorities.length == 0) {
					break;
				}
				auths.addAll(Arrays.asList(authorities));
			}
		}
		if (auths.size() == 0)
			return null;
		return ConfigAttributeDefinition.createFiltered(auths);
	}	
	public static boolean isMatch(Class clszz, Method mi, String methodString) {
		boolean isMatch = true;
		try {
			int lastDotIndex = methodString.lastIndexOf('.');
			String className = methodString.substring(0, lastDotIndex);
			String methodName = methodString.substring(lastDotIndex + 1);

			// 判断类是否相等
			if (!clszz.getName().equals(className))
				isMatch = false;

			// 判断接口是否相等
			Class[] interfaces = clszz.getInterfaces();
			for (int i = 0; i < interfaces.length; i++) {
				Class inf = interfaces[i];
				if (inf.getName().equals(className)) {
					isMatch = true;
				}
			}

			// 判断方法是否相等
			if (isMatch
					&& !(mi.getName().equals(methodName)
					|| (methodName.endsWith("*") && mi.getName().startsWith(
					methodName.substring(0, methodName.length() - 1))) || (methodName.startsWith("*") && mi
					.getName().endsWith(methodName.substring(1, methodName.length())))))
				isMatch = false;

		} catch (Exception e) {
			isMatch = false;
		}
		return isMatch;
	}

	public void setResourceCacheManager(ResourceCacheManager resourceCacheManager) {
		this.resourceCacheManager = resourceCacheManager;
	}
	
	

}
