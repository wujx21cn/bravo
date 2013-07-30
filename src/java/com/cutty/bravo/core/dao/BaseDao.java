/* com.cutty.bravo.core.dao.BaseDao.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-7-29 上午06:32:21, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.cutty.bravo.core.dao.support.Page;
import com.cutty.bravo.core.dao.support.QueryParameterWrapper;

/**
 * <p> 定义所有Dao的基类 </p>
 * <p>
 * <a href="BaseDao.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

public interface BaseDao extends Serializable {
 
	/**
	 * 根据ID获取对象. 实际调用Hibernate的session.load()方法返回实体或其proxy对象. 
	 * 如果对象不存在，抛出异常.
	 * 
	 * @param entityClass 对象类型
	 * @param id 主键
	 * @return 符合条件的对象
	 */
	public <T> T get(Class<T> entityClass, Serializable id);
	/**
	 * 获取全部对象.
	 * 
	 * @param entityClass 对象类型
	 * @return 符合条件的对象列表
	 */
	public <T> List<T> getAll(Class<T> entityClass) ;

	/**
	 * 获取全部对象,带排序字段与升降序参数.
	 * @param entityClass 对象类型
	 * @param orderBy 根据哪个字段排序
	 * @param isAsc 是不明是升序排序
	 * @return 符合条件的对象列表
	 */
	public <T> List<T> getAll(Class<T> entityClass, String orderBy, boolean isAsc);

	/**
	 * 保存对象.
	 * 
	 * @param o 被保存的对象
	 */
	public void save(Object o) ;
	
	/**
	 * 保存含ID的实体对象
	 * 
	 * @param o 被保存的对象
	 */
	public void saveWithId(Object o, Serializable id);
	
	/**
	 * 批量保存对象.
	 * 
	 * @param entitys 要保存的对象列表
	 */
	public <T> void batchSave(List<T> entitys) ;

	/**
	 * 删除对象.
	 * 
	 * @param o 要删除的对象
	 */
	public void remove(Object o) ;

	/**
	 * 根据ID删除对象.
	 * 
	 * @param entityClass 对象类型
	 * @param id 主键
	 */
	public <T> void removeById(Class<T> entityClass, Serializable id) ;
	
	/**
	 * 根据ID数组删除对象.
	 * 
	 * @param entityClass 对象类型
	 * @param ids 主键数组
	 */
	public <T> void removeByIds(Class<T> entityClass,Serializable[] ids) ;

	/**
	 * 刷新session缓存
	 */
	public void flush() ;
	
	/**
	 * 清除session缓存中的所有对象
	 */
	public void clear() ;

	/**
	 * 根据hql查询,可以传递参数
	 *
	 *@param page 分页对象
	 * @param sql 查询语句
	 * @param values 可变参数,见{@link #createQuery(String,Object...)}
	 * @return 返回符合条件的对象列表
	 */
	public <T> List<T>  find(Page page, String sql,boolean useCache) ;
	
	/**
	 * 根据hql查询
	 * @param page 分页对象
	 * @param sql 查询语句
	 * @param values 可变参数,见{@link #createQuery(String,Object...)}
	 * @return 返回符合条件的对象列表
	 */
	public <T> List<T>  find(Page page,String sql,boolean useCache, Object... values) ;

	/**
	 * 根据属性名和属性值查询对象.
	 *
	 * @param entityClass 对象类型
	 * @param page 分页对象
	 * @param propertyName 字段名
	 * @param value 字段的值
	 * @return 符合条件的对象列表
	 */
	public <T> List<T> findBy(Class<T> entityClass,Page page, String propertyName, Object value,boolean useCache) ;

	/**
	 * 根据属性名和属性值查询对象.
	 *
	 * @param entityClass 对象类型
	 * @param page 分页对象
	 * @param propertyName 字段名
	 * @param value 字段的值
	 * @param orderBy 根据哪个字段排序
	 * @param isAsc 是不明是升序排序
	 * @return 符合条件的对象列表
	 */
	public <T> List<T> findBy(Class<T> entityClass, Page page,String propertyName, Object value, String orderBy, boolean isAsc,boolean useCache) ;

	/**
	 * 根据属性名和属性值查询唯一对象.
	 *
	 * @param entityClass 对象类型
	 * @param propertyName 字段名
	 * @param value 字段的值
	 * @return 符合条件的唯一对象 or null if not found.
	 */
	public <T> T findUniqueBy(Class<T> entityClass, String propertyName, Object value,boolean useCache);


	/**
	 * 判断对象某些属性的值在数据库中是否唯一.
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param entity
	 * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 * @return
	 */
	public <T> boolean isUnique(Class<T> entityClass, Object entity, String uniquePropertyNames);


	/**
	 * 取得对象的主键值,辅助函数.
	 * @param entityClass 对象类型
	 * @param entity 操作的对象
	 * @return 该对象的主键
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Serializable getId(Class entityClass, Object entity) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException ;

	/**
	 * 取得对象的主键名,辅助函数.
	 * 
	 * @param entityClass 对象类型
	 * @return 主建名称
	 */
	public String getIdName(Class entityClass) ;
	
	/**
	 * 取得对象的主键类型,辅助函数.
	 * 
	 * @param entityClass 对象类型
	 * @return 主建类型
	 */
	public Class getIdType(Class entityClass);

	/**
	 * 去除hql的select 子句，未考虑union的情况.
	 * 
	 * @param hql 原始的hql语句
	 * @return 去除select子句后的hql语句
	 */
	public String removeSelect(String hql);

	/**
	 * 去除hql的orderby 子句.
	 * @param hql 原始的hql语句
	 * @return 去除了orderby后的语句
	 */
	public String removeOrders(String hql);

	/**
	 * 根据查询MAP产生查询语句
	 * @param entityClass
	 * @param queryValueMap 存放查询条件(where)中的字段名称及其约束条件(and, or , = , like等)的MAP
	 * @param page 对查询结果进行分页
	 * @return 返回把对象分好页的page
	 * @throws Exception
	 */
	public <T> Page findByPage(QueryParameterWrapper queryValueMap,Page page,boolean useCache) throws Exception ;
	/**
	 * 清除缓存中的entity对象
	 * 
	 * @param entity 要清除的对象
	 */
	public void evict(Object entity);
	
	@ Deprecated
	public HibernateTemplate getHibernate();
	
	/**
	 * 根据sql id 执行查询单对象的语句
	 * @param sqlId  该ID号为namespace+语句id 名
	 * @param parameter 渲染sql语句的参数
	 * @return
	 * @throws Exception
	 */
	public Object findObjectBySqlId(String sqlId,Map parameter) throws Exception;
	
	/**
	 * 根据sql id 执行保存操作的语句
	 * @param sqlId 该ID号为namespace+语句id 名
	 * @param parameter 渲染sql语句的参数
	 * @return
	 * @throws Exception
	 */
	public int saveBySqlId(String sqlId,Map parameter) throws Exception;
	
	/**
	 * 根据sql id 执行删除操作的语句
	 * @param sqlId 该ID号为namespace+语句id 名
	 * @param parameter 渲染sql语句的参数
	 * @return
	 * @throws Exception
	 */
	public int removeBySqlId(String sqlId,Map parameter) throws Exception;
	
	/**
	 * 通过读xml配置文件的"查询sql"语句的方法来执行数据库的查询操作
	 * @param sqlId 根据xml文件名+ "." + xml文件中的<query>标签获得
	 * @param parameter 原生sql语句中可能会有带"$"的参数，需要通过freeMarker解析,
	 * 			而参数的值的来源就是parameter，得到参数值的方法为：parameter的key.xml文件中该sql语句的<id>属性值
	 * @param page 将查询结果设置到该page中，然后将其返回
	 * @return Page 返回page 分页对象
	 */
	public Page findPageBySqlId(String sqlId,Map parameter,Page page) throws Exception;

	
}