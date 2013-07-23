/* com.cutty.bravo.core.dao.impl.IbatisBaseDao.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-7-29 上午07:35:32, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;


import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.dao.support.Page;
import com.cutty.bravo.core.dao.support.QueryParameterWrapper;

/**
 *
 * <p>
 * <a href="IbatisBaseDao.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class IbatisBaseDao implements BaseDao{

	public <T> void batchSave(List<T> entitys) {
		// TODO Auto-generated method stub
		
	}

	public void clear() {
		// TODO Auto-generated method stub
		
	}

	public void evict(Object entity) {
		// TODO Auto-generated method stub
		
	}

	public <T> List<T> find(Page page, String sql, boolean useCache) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> List<T> find(Page page, String sql, boolean useCache,
			Object... values) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> List<T> findBy(Class<T> entityClass, Page page,
			String propertyName, Object value, boolean useCache) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> List<T> findBy(Class<T> entityClass, Page page,
			String propertyName, Object value, String orderBy, boolean isAsc,
			boolean useCache) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> Page findByPage(QueryParameterWrapper queryValueMap, Page page,
			boolean useCache) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Object findObjectBySqlId(String sqlId, Map parameter)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Page findPageBySqlId(String sqlId, Map parameter, Page page)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T findUniqueBy(Class<T> entityClass, String propertyName,
			Object value, boolean useCache) {
		// TODO Auto-generated method stub
		return null;
	}

	public void flush() {
		// TODO Auto-generated method stub
		
	}

	public <T> T get(Class<T> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> List<T> getAll(Class<T> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> List<T> getAll(Class<T> entityClass, String orderBy,
			boolean isAsc) {
		// TODO Auto-generated method stub
		return null;
	}

	public HibernateTemplate getHibernate() {
		// TODO Auto-generated method stub
		return null;
	}

	public Serializable getId(Class entityClass, Object entity)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getIdName(Class entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	public Class getIdType(Class entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> boolean isUnique(Class<T> entityClass, Object entity,
			String uniquePropertyNames) {
		// TODO Auto-generated method stub
		return false;
	}

	public void remove(Object o) {
		// TODO Auto-generated method stub
		
	}

	public <T> void removeById(Class<T> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		
	}

	public <T> void removeByIds(Class<T> entityClass, Serializable[] ids) {
		// TODO Auto-generated method stub
		
	}

	public int removeBySqlId(String sqlId, Map parameter) throws Exception {
		return 0;
		
	}

	public String removeOrders(String hql) {
		// TODO Auto-generated method stub
		return null;
	}

	public String removeSelect(String hql) {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(Object o) {
		// TODO Auto-generated method stub
		
	}

	public int saveBySqlId(String sqlId, Map parameter) throws Exception {
		return 0;
		
	}
	
	public void saveWithId(Object o, Serializable id) {
		// TODO Auto-generated method stub
		
	}

}
