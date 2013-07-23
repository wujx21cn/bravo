/* com.cutty.bravo.core.dao.impl.HibernateBaseDao.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-7-29 上午07:28:03, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.type.CollectionType;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.dao.sql.SQLTemplate;
import com.cutty.bravo.core.dao.sql.operationWrapper.DeleteSQLWrapper;
import com.cutty.bravo.core.dao.sql.operationWrapper.QuerySQLWrapper;
import com.cutty.bravo.core.dao.sql.operationWrapper.SaveSQLWrapper;
import com.cutty.bravo.core.dao.sql.transformer.SqlResultClassTransformer;
import com.cutty.bravo.core.dao.sql.transformer.SqlResultMapTransformer;
import com.cutty.bravo.core.dao.support.Page;
import com.cutty.bravo.core.dao.support.QueryByMap;
import com.cutty.bravo.core.dao.support.QueryParameterValue;
import com.cutty.bravo.core.dao.support.QueryParameterWrapper;
import com.cutty.bravo.core.dao.support.VORestrictions;
import com.cutty.bravo.core.dao.support.listener.SaveOrUpdateListener;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.utils.render.FreemarkerTemplateEngine;
import com.cutty.bravo.core.web.handler.RequestHandler;
import com.cutty.bravo.core.web.handler.SaasCodeHandler;

/**
 * <p>BaseDao的实现类,定义了对实体的增删改查等操作的函数,用的是
 *    hibernate 的机制
 * <p>
 * <a href="HibernateBaseDao.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

public class HibernateBaseDao extends HibernateDaoSupport implements BaseDao{
	
	/**
	 * 清除session缓存中的所有对象
	 */
	public void clear() {
		getHibernateTemplate().clear();
	}

	/**
	 * 根据hql查询
	 *
	 * @param page 分页对象
	 * @param sql 查询语句
	 * @param values 可变参数,见{@link #createQuery(String,Object...)}
	 * @return 返回符合条件的对象列表
	 */
	public <T> List<T>  find(Page page,String sql,boolean useCache, Object... values) {
		Assert.hasText(sql);
		Query query =  super.getSession().createQuery(sql); 
		query.setCacheable(useCache);
		query.setCacheRegion(SaasCodeHandler.getSaasCode());
		//需要设置分区
		query.setCacheRegion(SaasCodeHandler.getSaasCode());
		if (null != values) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		if (null != page){
			query.setFirstResult(page.getStartIndex()); 
			query.setMaxResults(page.getPageSize()); 
			page.setResult(query.list());
			return (List<T> )page.getResult();
		}
		return query.list();
	}
	
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
	public <T> List<T> findBy(Class<T> entityClass, Page page,String propertyName, Object value, String orderBy, boolean isAsc,boolean useCache) {
		Assert.hasText(propertyName);
		Assert.hasText(orderBy);
		return createCriteria(entityClass, page,orderBy, isAsc,useCache, Restrictions.eq(propertyName, value)).list();
	}

	/**
	 * 根据属性名和属性值查询对象.
	 *
	 * @param entityClass 对象类型
	 * @param page 分页对象
	 * @param propertyName 字段名
	 * @param value 字段的值
	 * @return 符合条件的对象列表
	 */
	public <T> List<T> findBy(Class<T> entityClass, Page page,String propertyName, Object value,boolean useCache) {
		Assert.hasText(propertyName);
		return createCriteria(entityClass,  page,useCache,Restrictions.eq(propertyName, value)).list();
	}

	/**
	 * 根据属性名和属性值查询唯一对象.
	 *
	 * @param entityClass 对象类型
	 * @param propertyName 字段名
	 * @param value 字段的值
	 * @return 符合条件的唯一对象 or null if not found.
	 */
	public <T> T findUniqueBy(Class<T> entityClass, String propertyName, Object value,boolean useCache) {
		Assert.hasText(propertyName);
		return (T) createCriteria(entityClass,  null,useCache,Restrictions.eq(propertyName, value)).uniqueResult();
	}
	/**
	 * 刷新session缓存
	 */
	public void flush() {
		getHibernateTemplate().flush();
	}
	
	/**
	 * 根据ID获取对象. 实际调用Hibernate的session.load()方法返回实体或其proxy对象. 
	 * 如果对象不存在，抛出异常.
	 * 
	 * @param entityClass 对象类型
	 * @param id 主键
	 * @return 符合条件的对象
	 */
	public <T> T get(Class<T> entityClass, Serializable id) {
		return (T) getHibernateTemplate().load(entityClass, id);
	}

	/**
	 * 获取全部对象,带排序字段与升降序参数.
	 * @param entityClass 对象类型
	 * @param orderBy 根据哪个字段排序
	 * @param isAsc 是不明是升序排序
	 * @return 符合条件的对象列表
	 */
	public <T> List<T> getAll(Class<T> entityClass, String orderBy, boolean isAsc) {
		Assert.hasText(orderBy);
		
		if (isAsc)
			return getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(entityClass).addOrder(Order.asc(orderBy)));
		else
			return getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(entityClass).addOrder(Order.desc(orderBy)));
	}
	
	/**
	 * 获取全部对象.
	 * 
	 * @param entityClass 对象类型
	 * @return 符合条件的对象列表
	 */
	public <T> List<T> getAll(Class<T> entityClass) {
		return getHibernateTemplate().loadAll(entityClass);
	}
	
	/**
	 * 取得对象的主键值,辅助函数.
	 * @param entityClass 对象类型
	 * @param entity 操作的对象
	 * @return 该对象的主键
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Serializable getId(Class entityClass, Object entity) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Assert.notNull(entity);
		Assert.notNull(entityClass);
		return (Serializable) PropertyUtils.getProperty(entity, getIdName(entityClass));
	}
	
	/**
	 * 取得对象的主键名,辅助函数.
	 * 
	 * @param entityClass 对象类型
	 * @return 主建名称
	 */
	public String getIdName(Class entityClass) {
		Assert.notNull(entityClass);
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		Assert.notNull(meta, "Class " + entityClass + " not define in hibernate session factory.");
		String idName = meta.getIdentifierPropertyName();
		Assert.hasText(idName, entityClass.getSimpleName() + " has no identifier property define.");
		return idName;
	}
	
	/**
	 * 取得对象的主键类型,辅助函数.
	 * 
	 * @param entityClass 对象类型
	 * @return 主建类型
	 */
	public Class getIdType(Class entityClass) {
		Assert.notNull(entityClass);
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		Assert.notNull(meta, "Class " + entityClass + " not define in hibernate session factory.");
		Class idType = meta.getIdentifierType().getReturnedClass();
		return idType;
	}
	/**
	 * 判断对象某些属性的值在数据库中是否唯一.
	 *
	 * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割
	 *  如"name,loginid,password"
	 * @param entityClass 对象类型
	 * @param entity 操作的对象
	 * @return 
	 */
	public <T> boolean isUnique(Class<T> entityClass, Object entity, String uniquePropertyNames) {
		Assert.hasText(uniquePropertyNames);
		Criteria criteria = createCriteria(entityClass,null,false).setProjection(Projections.rowCount());
		String[] nameList = uniquePropertyNames.split(",");
		try {
			// 循环加入唯一列
			for (String name : nameList) {
				criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(entity, name)));
			}

			// 以下代码为了如果是update的情况,排除entity自身.

			String idName = getIdName(entityClass);

			// 取得entity的主键值
			Serializable id = getId(entityClass, entity);

			// 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
			if (id != null)
				criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		return (Integer) criteria.uniqueResult() == 0;
	}

	/**
	 * 删除对象.
	 * 
	 * @param o 要删除的对象
	 */
	public void remove(Object o) {
		getHibernateTemplate().delete(o);
	}

	/**
	 * 根据ID删除对象.
	 * 
	 * @param entityClass 对象类型
	 * @param id 主键
	 */
	public <T> void removeById(Class<T> entityClass, Serializable id) {
		remove(get(entityClass, id));
	}


	/**
	 * 根据ID数组删除对象.
	 * 
	 * @param entityClass 对象类型
	 * @param ids 主键数组
	 */
	public <T> void removeByIds(Class<T> entityClass,Serializable[] ids) {
		for (int i=0;i<ids.length;i++){
			if (null != ids[i] || (ids[i] instanceof String && StringUtils.isNotEmpty((String) ids[i]))){
				removeById(entityClass,ids[i]);
			}
		}
	}
	/**
	 * 去除hql的orderby 子句.
	 * @param hql 原始的hql语句
	 * @return 去除了orderby后的语句
	 */
	public String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 去除hql的select 子句，未考虑union的情况.
	 * 
	 * @param hql 原始的hql语句
	 * @return 去除select子句后的hql语句
	 */
	public String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
		return hql.substring(beginPos);
	}
	
	/**
	 * 保存对象.
	 * 
	 * @param o 被保存的对象
	 */
	public void save(Object o) {
		SaveOrUpdateListener.onSaveOrUpdate(o);
		getHibernateTemplate().saveOrUpdate(o);
	}
	
	/**
	 * 保存含ID的实体对象
	 * 
	 * @param o 被保存的对象
	 */
	public void saveWithId(Object o, Serializable id) {
		getHibernateTemplate().getSessionFactory().getCurrentSession().save(o, id);
		
	}

	/**
	 * 批量保存对象.
	 * 
	 * @param entitys 要保存的对象列表
	 */
	public <T> void batchSave(List<T> entitys) {
		if (null != entitys && 0 <entitys.size()){
			Iterator<T> entitysIT = entitys.iterator();
			while (entitysIT.hasNext()){
				Object entity = entitysIT.next();
				SaveOrUpdateListener.onSaveOrUpdate(entity);
				getHibernateTemplate().saveOrUpdate(entity);
			}
		}
	}
	
	/**
	 * 创建Criteria对象.
	 * 
	 * @param <T>
	 * @param yClasentityClasss 查询的实体
	 * @param page 分页对象
	 * @param criterions
	 * @return
	 */
	private <T> Criteria createCriteria(Class<T> yClasentityClasss, Page page,boolean useCache,Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(yClasentityClasss);
		criteria.setCacheable(useCache);
		criteria.setCacheRegion(SaasCodeHandler.getSaasCode());
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		if (null != page){
			criteria.setMaxResults(page.getPageSize());
			criteria.setFirstResult(page.getStartIndex());
		}
		return criteria;
	}

	/**
	 *  创建Criteria对象，带排序字段与升降序字段.
	 *  
	 * @param <T>
	 * @param entityClass 查询的实体
	 * @param page 分页对象
	 * @param orderBy 排序的字段名
	 * @param isAsc 是否倒序
	 * @param criterions
	 * @return
	 */
	private <T> Criteria createCriteria(Class<T> entityClass,Page page,String orderBy, boolean isAsc,boolean useCache, Criterion... criterions) {
		Assert.hasText(orderBy);
		Criteria criteria = createCriteria(entityClass,page,useCache,criterions);
		if (isAsc)
			criteria.addOrder(Order.asc(orderBy));
		else
			criteria.addOrder(Order.desc(orderBy));

		return criteria;
	}
	
	/**
	 * 根据sql查询得到对象列表
	 * 
	 * @param page 分页对象
	 * @param sql sql查询语句
	 * @return 查询结果的对象列表
	 */
	public <T> List<T> find(Page page,String sql,boolean useCache) {
		return find(page,sql,useCache,null) ;
	}
	/**
	 * 获取SessionFactory
	 * 
	 * @return SessionFactory
	 */
	public SessionFactory getSessionFactoryFormHibernateTemplate(){
		return super.getSessionFactory();
	}
	
	
	/**
	 * 根据MAP查询，并把结果分页
	 * @param entityClass
	 * @param queryValueMap 存放查询条件(where)中的字段名称及其约束条件(and, or , = , like等)的MAP
	 * @param page 对查询结果进行分页
	 * @return 返回把对象分好页的page
	 * @throws Exception
	 */
	public  <T> Page findByPage(QueryParameterWrapper queryParameterWrapper,Page page,boolean useCache) throws Exception {
		int totalCount = getCountByAttribute(queryParameterWrapper.getEntityClass(),queryParameterWrapper.getQueryParameterMap(),useCache);
		page.setTotalCount(totalCount);
		List items = getByAttribute(queryParameterWrapper.getEntityClass(),queryParameterWrapper.getQueryParameterMap(),page.getPageSize(), page.getStartIndex(),useCache);
		page.setResult(items);
		return page;
	}
	
	/**
	 * 根据查询MAP获取查询总条数
	 * @param <T>
	 * @param entityClass
	 * @param queryValueMap 存放查询条件(where)中的字段名称及其约束条件(and, or , = , like等)的MAP
	 * @return 查询总条数
	 */
	private <T> int getCountByAttribute(Class<T> entityClass,Map<String,List<QueryParameterValue>> queryValueMap,boolean useCache) {
		Criteria executableCriteria = getSession().createCriteria(entityClass);
		executableCriteria.setCacheable(useCache);
		executableCriteria.setCacheRegion(SaasCodeHandler.getSaasCode());
		executableCriteria.add(QueryByMap.create(entityClass,queryValueMap));
		appendAssociationCondition(null,executableCriteria,entityClass,queryValueMap);
		return ((Long)executableCriteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}
	/**
	 * 获取查询列表
	 * @param <T>
	 * @param entityClass
	 * @param queryValueMap 存放查询条件(where)中的字段名称及其约束条件(and, or , = , like等)的MAP
	 * @param maxResult
	 * @param startIndex
	 * @return
	 */
	private <T> List<T> getByAttribute(Class<T> entityClass,Map<String,List<QueryParameterValue>> queryValueMap, int maxResult, int startIndex,boolean useCache) {
		Criteria executableCriteria = getSession().createCriteria(entityClass);
		executableCriteria.setCacheable(useCache);
		executableCriteria.setCacheRegion(SaasCodeHandler.getSaasCode());
		executableCriteria.add(QueryByMap.create(entityClass,queryValueMap));
		appendAssociationCondition(null,executableCriteria,entityClass,queryValueMap);
		if (startIndex >= 0) {
			executableCriteria.setFirstResult(startIndex);
		}
		if (maxResult > 0) {
			executableCriteria.setMaxResults(maxResult);
		}
		//* liangg 09.03.04
		//* 从queryValueMap取出属性中的order属性为非空的字段，加到orderList中去，实现按该属性进行升降排序
		 	List<Order> orderList = new ArrayList<Order>();
			Set<String> propertys = queryValueMap.keySet();
			Iterator<String> it = propertys.iterator();
			while(it.hasNext()){
				
				 String propertyName = it.next();
			     QueryParameterValue queryParameterValue = queryValueMap.get(propertyName).get(0);
			     if(VORestrictions.ORDER_BY_ASC.equalsIgnoreCase(queryParameterValue.getOrder())){
			    	 //升序
			    	 orderList.add(Order.asc(propertyName));
			     }else if(VORestrictions.ORDER_BY_DESC.equalsIgnoreCase(queryParameterValue.getOrder())){
			    	 //降序
			    	 orderList.add(Order.desc(propertyName));
			     }
				
			}
		 //*
		
		
		if (orderList != null) {
			for (int i = 0; i < orderList.size(); i++) {
				executableCriteria.addOrder(orderList.get(i));
			}
		}
		
		return executableCriteria.list();
	}
	/**
	 * 根据一个map获得相关查询条件，通过的办法是递归
	 * @param prefix 前缀，递归时作为参数
	 * @param executableCriteria
	 * @param entityClass 相应的实体
	 * @param queryValueMap 存放查询条件(where)中的字段名称及其约束条件(and, or , = , like等)的MAP
	 */
	private void appendAssociationCondition(String prefix,Criteria executableCriteria,Class entityClass,Map<String,List<QueryParameterValue>> queryValueMap){
		String entityName = entityClass.getName();
		EntityPersister meta = ((SessionFactoryImplementor)getSession().getSessionFactory()).getEntityPersister(entityName);
		//获得实体的字段名称与字段类型
		String[] propertyNames = meta.getPropertyNames();
		Type[] propertyTypes = meta.getPropertyTypes();
		//循环对实体字段进行判断，当字段是关联类型时需要进行处理
		for (int i=0; i<propertyNames.length; i++) {
			if (propertyTypes[i].isAssociationType() ){
				String chilsPrefix;
				//未递归前prefix为空，从第一次递归开始则不为空
				if (StringUtils.isEmpty(prefix)){
					chilsPrefix = propertyNames[i];
				} else {
					chilsPrefix = prefix+"." + propertyNames[i];
				}
				Map<String,List<QueryParameterValue>> chilsQueryMap = RequestHandler.getMapWithPrefix(queryValueMap, propertyNames[i]+".", "");
				if (null !=  chilsQueryMap && 0 < chilsQueryMap.size()  ){
					if (propertyTypes[i].isCollectionType()){
						CollectionType collectionType = (CollectionType)propertyTypes[i];
						CollectionPersister collectionPersister = ((SessionFactoryImplementor)getSession().getSessionFactory()).getCollectionPersister(collectionType.getRole());
						executableCriteria.createCriteria(chilsPrefix).add(QueryByMap.create(chilsPrefix,collectionPersister.getElementType().getReturnedClass(),chilsQueryMap));
						appendAssociationCondition(chilsPrefix,executableCriteria,collectionPersister.getElementType().getReturnedClass(),chilsQueryMap);
					} else {
						executableCriteria.createCriteria(chilsPrefix).add(QueryByMap.create(chilsPrefix,propertyTypes[i].getReturnedClass(),chilsQueryMap));
						appendAssociationCondition(chilsPrefix,executableCriteria,propertyTypes[i].getReturnedClass(),chilsQueryMap);
					}
				}
			}
		}
		return ;
	}
	/**
	 * 清除缓存中的entity对象
	 * 
	 * @param entity 要清除的对象
	 */
	
	public void evict(Object entity) {
		getHibernateTemplate().evict(entity);
	}
	/**
	 * 
	 */
	public HibernateTemplate getHibernate() {
		return getHibernateTemplate();
	}
	
	public Object findObjectBySqlId(String sqlId, Map parameter)
			throws Exception {
		Page page = findPageBySqlId(sqlId, parameter,null);
		List<Object> resultList =(List<Object>) page.getResult();
		if (null != resultList && 0 < resultList.size()) {
			return resultList.get(0);
		}
		return null;
	}
	/**
	 * 通过读xml配置文件的"查询sql"语句的方法来执行数据库的查询操作
	 * @param sqlId 根据xml文件名+ "." + xml文件中的<query>标签获得
	 * @param parameter 原生sql语句中可能会有带"$"的参数，需要通过freeMarker解析,
	 * 			而参数的值的来源就是parameter，得到参数值的方法为：parameter的key.xml文件中该sql语句的<id>属性值
	 * @param page 将查询结果设置到该page中，然后将其返回
	 * @return Page 返回page 分页对象
	 * (non-Javadoc)
	 * @see com.cutty.bravo.core.dao.BaseDao#findPageBySqlId(java.lang.String, java.util.Map, com.cutty.bravo.core.dao.support.Page)
	 */
	
	public Page findPageBySqlId(String sqlId, Map parameter, Page page)
			throws Exception {
		
		QuerySQLWrapper queryWrapper = SQLTemplate.getTemplate().getQueryWrapperById(sqlId);
		FreemarkerTemplateEngine freemarkerTemplateEngine = FreemarkerTemplateEngine.getInstance();
		String queryStr = freemarkerTemplateEngine.renderFtl(ApplicationContextKeeper.getServletContext(), "<#escape x as (x)!>"+queryWrapper.getQueryStr()+"</#escape>",parameter);
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query sqlQuery = session.createSQLQuery(queryStr);
		if (null != page){
			sqlQuery.setFirstResult(page.getStartIndex());
			sqlQuery.setMaxResults(page.getPageSize());
		} else {
			sqlQuery.setMaxResults(10000);
		}
		List resultList = new ArrayList();
		int totalCount = -1;
		if (StringUtils.isNotEmpty(queryWrapper.getResultClass())){
			SqlResultClassTransformer sqlResultClassTransformer = new SqlResultClassTransformer(queryWrapper.getResultClass());
			resultList = sqlQuery.setResultTransformer(sqlResultClassTransformer).list();
			totalCount = sqlResultClassTransformer.getTotalCount();
		} else if (null != queryWrapper.getResultMap()){
			SqlResultMapTransformer sqlResultMapTransformer = new SqlResultMapTransformer(queryWrapper.getResultMap());
			resultList = sqlQuery.setResultTransformer(sqlResultMapTransformer).list();
			totalCount = sqlResultMapTransformer.getTotalCount();
		}
		if (null == page && null != RequestHandler.getContextRequestHandler()) {
			page = Page.getInstance(RequestHandler.getContextRequestHandler().getRequest());
		} else if (null == page){
			page = Page.getInstance(null);
		}
		page.setResult(resultList);
		if (-1 != totalCount){
			page.setTotalCount(totalCount);
		} else {
			page.setTotalCount(resultList.size());
		}
		return page;
	}
	//liangg 09.03.06 根据sqlId获取原生sql 执行删 除
	/**
	 * 根据配置文件的文件名及id执行delete操作
	 * @param sqlId 见 findPageBySqlId函数的说明
	 * @param parameter 见findPageBySqlId函数的说明
	 */
	public int removeBySqlId(String sqlId, Map parameter) throws Exception {
		DeleteSQLWrapper removeWrapper = SQLTemplate.getTemplate().getDeleteSQLWrapperById(sqlId);
		FreemarkerTemplateEngine freemarkerTemplateEngine = FreemarkerTemplateEngine.getInstance();
		String queryStr = freemarkerTemplateEngine.renderFtl(ApplicationContextKeeper.getServletContext(), "<#escape x as (x)!>"+removeWrapper.getSqlStr()+"</#escape>",parameter);
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query sqlQuery = session.createSQLQuery(queryStr);
		
		return sqlQuery.executeUpdate();
	}
	//liangg 09.03.06 根据sqlId获取原生sql 执行保存
	/**
	 * 根据配置文件的文件名及id执行save操作
	 * @param sqlId 见 findPageBySqlId函数的说明
	 * @param parameter 见findPageBySqlId函数的说明
	 */
	public int saveBySqlId(String sqlId, Map parameter) throws Exception {
		SaveSQLWrapper saveWrapper = SQLTemplate.getTemplate().getSaveSQLWrapperById(sqlId);
		FreemarkerTemplateEngine freemarkerTemplateEngine = FreemarkerTemplateEngine.getInstance();
		String queryStr = freemarkerTemplateEngine.renderFtl(ApplicationContextKeeper.getServletContext(), "<#escape x as (x)!>"+saveWrapper.getSqlStr()+"</#escape>",parameter);
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query sqlQuery = session.createSQLQuery(queryStr);
		    
		return sqlQuery.executeUpdate();
		 
	}	
	
}

