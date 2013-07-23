/* com.cutty.bravo.core.dao.sql.transformer.SqlResultClassTransformer.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Mar 4, 2009 10:03:46 AM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.dao.sql.transformer;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.BeanUtils;

/**
 * 根据xml文件的<query>标签的resultClass属性值，通过反射得到该class的属性，形成属性数组
 * <p>
 * <a href="SqlResultClassTransformer.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class SqlResultClassTransformer implements ResultTransformer {
	
	private static final long serialVersionUID = -3684824474956594042L;

	protected final Log logger = LogFactory.getLog(SqlResultClassTransformer.class);
	
	private Class resultEntityClass;
	
	private PropertyDescriptor[] propertyDescriptors ;
	
	private int totalCount = -1;
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public SqlResultClassTransformer(String resultClass){
		try {
			resultEntityClass = Class.forName(resultClass);
			propertyDescriptors = BeanUtils.getPropertyDescriptors(resultEntityClass);
		} catch (ClassNotFoundException e) {
			logger.error(e);
		}
	}
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object returnResult = null;
		if (null == resultEntityClass) return tuple;
		try {
			if (null != aliases && 1 <aliases.length && null != propertyDescriptors && 1 < propertyDescriptors.length){
				returnResult = resultEntityClass.newInstance();
				for (int i=0 ; i< aliases.length; i++){
					for (int j=0;j<propertyDescriptors.length;j++){
						if (propertyDescriptors[j].getName().equalsIgnoreCase(aliases[i])){
							 org.apache.commons.beanutils.BeanUtils.setProperty(returnResult,propertyDescriptors[j].getName(), tuple[i]);
						}
					}
					if ("rowCount".equalsIgnoreCase(aliases[i])){
						try {
							totalCount = Integer.parseInt(tuple[i].toString());
						} catch (Exception e){
							logger.error("paser rowCount error,the row count should be numeric");
						}
						
					}
				}
			} else if (1 == aliases.length){
				returnResult = ConvertUtils.convert(tuple[0], resultEntityClass);
				if ("rowCount".equalsIgnoreCase(aliases[0])){
					try {
						totalCount = Integer.parseInt(tuple[0].toString());
					} catch (Exception e){
						logger.error("paser rowCount error,the row count should be numeric");
					}
				}
			}
		} catch (InstantiationException e) {
			logger.error(e);
		} catch (IllegalAccessException e) {
			logger.error(e);
		} catch (InvocationTargetException e) {
			logger.error(e);
		}

		return returnResult;
	}
	
	public List transformList(List collection) {
		return collection;
	}



}
