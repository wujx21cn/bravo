/* com.cutty.bravo.core.dao.extend.BaseEntityDao.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-7-29 上午06:36:54, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.dao.extend;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;


import com.cutty.bravo.core.dao.support.Page;
import com.cutty.bravo.core.dao.support.QueryParameterWrapper;

/**
 * <p>该接口定义所有数据库操作的基础行为,针对特定实体类的DAO必须实现该接口.</p>
 * <p>
 * <a href="BaseEntityDao.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public interface BaseEntityDao<T> {

	/**
	 * 根据ID获取对象. 实际调用Hibernate的session.load()方法返回实体或其proxy对象. 如果对象不存在，抛出异常.
	 * 
	 * @param id 对象的主键
	 * @return 唯一返回一个对象或者抛出异常
	 */
	public T get( Serializable id);
	
	/**
	 * 获取全部对象.
	 * 
	 * @return
	 */
	public List<T> getAll() ;

	/**
	 * 获取全部对象,带排序字段与升降序参数.
	 * 
	 * @param orderBy 按某个字段排序
	 * @param isAsc 是否升序排序
	 * @return 全部对象
	 */
	public List<T> getAll(String orderBy, boolean isAsc) ;

	/**
	 * 保存对象.
	 * 
	 * @param o
	 */
	public void save(Object o) ;
	
	/**
	 * 根据sql id 执行保存操作的语句
	 * @param sqlId 该ID号为namespace+语句id 名
	 * @param parameter 渲染sql语句的参数
	 * @return
	 * @throws Exception
	 */
	public int saveBySqlId(String sqlId,Map parameter) throws Exception;
	
	
	/**
	 * 保存含ID的实体对象
	 * 
	 * @param o 被保存的对象
	 */
	public void saveWithId(Object o, Serializable id);
	
	
	/**
	 * 批量保存对象.
	 * 
	 * @param entitys
	 */
	public void batchSave(List<T> entitys) ;
	
	/**
	 * 删除对象.
	 * @param o 要删除的对象
	 */
	public void remove(Object o) ;

	/**
	 * 根据ID删除对象.
	 * @param id 对象的主键
	 */
	public void removeById(Serializable id) ;
	
	/**
	 * 
	 * 根据ID数组删除对象.
	 * 
	 * @param ids
	 */
	public void removeByIds(Serializable[] ids) ;

	/**
	 * 根据hql查询,直接使用HibernateTemplate的find函数.
	 *
	 * @param page 分页对象
	 * @param values 可变参数,见{@link #createQuery(String,Object...)}
	 */
	public List<T> find(Page page,String hql,boolean useCache) ;
	
	/**
	 * 根据hql查询,直接使用HibernateTemplate的find函数.
	 *
	 * @param page 分页对象
	 * @param hql 传入的hql查询语句
	 * @param values 可变参数,见{@link #createQuery(String,Object...)}
	 * @return 符合条件的对象列表
	 */
	public List<T> find(Page page,String hql,boolean useCache, Object... values) ;

	/**
	 * 根据属性名和属性值查询对象.
	 *
	 * @param page 分页对象
	 * @param propertyName 对象的属性名
	 * @param value 对象的属性值
	 * @return 符合条件的对象列表
	 */
	public List<T> findBy(Page page,String propertyName, Object value,boolean useCache) ;

	/**
	 * 根据属性名和属性值查询对象.\
	 * 
	 * @param page 分页对象
	 * @param propertyName 对象的属性名
	 * @param value 属性值
	 * @param orderBy 按哪个属性排序
	 * @param 是否升序
	 * @return 符合条件的对象列表
	 */
	public List<T> findBy(Page page,String propertyName, Object value, String orderBy, boolean isAsc,boolean useCache) ;
	

	/**
	 * 根据属性名和属性值查询唯一对象.
	 *
	 * @param propertyName 对象的属性名
	 * @param value 对象的属性值
	 * @return 符合条件的唯一对象 or null if not found.
	 */
	public T findUniqueBy(String propertyName, Object value,boolean useCache) ;
	
	/**
	 * 根据sql id 执行查询单对象的语句
	 * @param sqlId  该ID号为namespace+语句id 名
	 * @param parameter 渲染sql语句的参数
	 * @return
	 * @throws Exception
	 */
	public Object findObjectBySqlId(String sqlId,Map parameter) throws Exception;
	
	/**
	 * 通过读xml配置文件的"查询sql"语句的方法来执行数据库的查询操作
	 * @param sqlId 根据xml文件名+ "." + xml文件中的<query>标签获得
	 * @param parameter 原生sql语句中可能会有带"$"的参数，需要通过freeMarker解析,
	 * 			而参数的值的来源就是parameter，得到参数值的方法为：parameter的key.xml文件中该sql语句的<id>属性值
	 * @param page 将查询结果设置到该page中，然后将其返回
	 * @return Page 返回page 分页对象
	 */
	public Page findPageBySqlId(String sqlId,Map parameter,Page page) throws Exception;

	
	/**
	 * 判断对象某些属性的值在数据库中是否唯一.
	 *
	 * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 * @param entity 目标对象
	 * @return 唯一则为true,否则为false
	 */
	public boolean isUnique(Object entity, String uniquePropertyNames) ;

	/**
	 * 取得对象的主键值,辅助函数.
	 * 
	 * @param entity 被获取的对象
	 * @return 该对象的唯一的ID
	 * @throws NoSuchMethodException 
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Serializable getId(Object entity) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException ;
	/**
	 * 取得对象的主键名,辅助函数.
	 * 
	 * @return 对象的主键的名称
	 */
	public String getIdName() ;
	
	/**
	 * 取得对象的主键类型,辅助函数.
	 * 
	 * @return
	 */
	public Class getIdType() ;

	/**
	 * 去除hql的select 子句，未考虑union的情况.
	 * 
	 * @param hql
	 * @return
	 */
	public String removeSelect(String hql) ;

	/**
	 * 去除hql的orderby 子句.
	 * @param hql hql语句
	 * @return 去除后的hql
	 */
	public String removeOrders(String hql) ;
	
	/**
	 * 根据sql id 执行删除操作的语句
	 * @param sqlId 该ID号为namespace+语句id 名
	 * @param parameter 渲染sql语句的参数
	 * @return
	 * @throws Exception
	 */
	public int removeBySqlId(String sqlId,Map parameter) throws Exception;
	
	/**
	 * 根据查询MAP查询数据
	 * @param queryValueMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page findByPage(QueryParameterWrapper queryParameterWrapper,Page page,boolean useCache) throws Exception ;
	
	/**
	 * 清除session缓存中的所有对象
	 */
	public void clear() ;
	
	/**
	 * 清空该对象缓存
	 * @param entity
	 */
	public void evict(Object entity);
	

	/**
	 * 刷新session缓存
	 */
	public void flush() ;
	
	/**
	 * 向dao暴露hibernate接口,该方法慎用，将导致dao层与hibernate耦合.
	 * @return
	 */
	@ Deprecated
	public HibernateTemplate getHibernate() ;
}

