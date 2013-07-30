/* com.cutty.bravo.core.dao.sql.transformer.SqlResultMapTransformer.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Mar 2, 2009 4:37:42 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.dao.sql.transformer;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;
import org.springframework.beans.BeanUtils;

import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.dao.sql.Result;
import com.cutty.bravo.core.dao.sql.ResultMap;
import com.cutty.bravo.core.exception.CacheException;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.utils.AutoLoadSessionFactoryBean;
import com.cutty.bravo.core.utils.cache.Cache;
import com.cutty.bravo.core.utils.cache.CacheManager;
import com.cutty.bravo.core.utils.cache.ehcache.CacheImpl;

/**
 * 该类作为Query的参数，根据ResultMap构造出propertyColumnsMap每一个column对应该
 * map的一条映射.
 * 属性说明：
 * ResultMap：从xml文件读<resultMap>参数得到的map.于SQLTemplate类中形成,在这里的作用
 * 是得到其entityClass及<result>标签的内容
 * cache: 在第二次构造ResultTransformer时，读取缓存，提交效率
 * propertyColumnsMap：依据该entityClass的所有属性形成该map
 * 
 * <p>
 * <a href="SqlResultMapTransformer.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class SqlResultMapTransformer  implements ResultTransformer {
	
	protected final Log logger = LogFactory.getLog(SqlResultMapTransformer.class);
	private static final long serialVersionUID = -3499443316304821160L;
	private static final String  cacheName= "BRAVO_SQL_RESULT_CACHE";
	private ResultMap resultMap;
	private Cache cache;
	private Map<String,Result> propertyColumnsMap ;
	
	private int totalCount = -1;
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public SqlResultMapTransformer(ResultMap resultMap) throws Exception{
		this.resultMap = resultMap;
		if (Boolean.valueOf(ConfigurableConstants.getProperty("sql.use.cache","true"))){
			try {

				CacheManager cacheManager= (CacheManager)ApplicationContextKeeper.getAppCtx().getBean("cacheManager");
				cache=  cacheManager.getCache(cacheName);
				if (null == cache || null == cache.getCache()) {
					cache =cacheManager.createCache(cacheName, 10000, true, true, 7200, 3600);   
					cacheManager.addCache(cache);
					logger.warn("can't find the cache with the name:"+cacheName);
				}
				//先查缓存，缓存找到则直接返回
				propertyColumnsMap  = (Map<String,Result>)cache.get(resultMap.getId());
				if (null != propertyColumnsMap){
					return ;
				} else {
					propertyColumnsMap = new HashMap<String,Result>();
				}
			} catch (CacheException e) {
				logger.error(e);
			}
		}

		//先根据resultMap的entityClass去获得persistentClass,若autoLoadSessionFactoryBean中存在该持续化类，则根据其
		//属性，一个个存入propertyColumnsMap, key为Result中的column，再将来自xml文件中的resultMap中的Result加入propertyColumnsMap
		//即覆盖掉其中的相同id的result,完成loadEntity属性的替换(在xml中可能设置成为true); 
		AutoLoadSessionFactoryBean autoLoadSessionFactoryBean = (AutoLoadSessionFactoryBean) ApplicationContextKeeper.getAppCtx().getBean("&sessionFactory");
		PersistentClass persistentClass = autoLoadSessionFactoryBean.getConfiguration().getClassMapping(resultMap.getEntityClass());
		if (null != persistentClass){
			Iterator<Property> propertys = persistentClass.getPropertyIterator();
			Property identifierProperty = persistentClass.getIdentifierProperty();
			Result identifierPropertyResult = new Result();
			Iterator<Column> identifierPropertyColumnIterator = identifierProperty.getColumnIterator();
			Column identifierPropertyColumn = identifierPropertyColumnIterator.next();
			identifierPropertyResult.setProperty(identifierProperty.getName());
			identifierPropertyResult.setColumn(identifierPropertyColumn.getName().toUpperCase());
			identifierPropertyResult.setLoadEntity(false);
			propertyColumnsMap.put(identifierPropertyResult.getColumn(),identifierPropertyResult);
			
			while (propertys.hasNext()){
				Property property = propertys.next();
				Type propertyType = property.getType();
				if (!propertyType.isComponentType() && !propertyType.isCollectionType()){//
					Iterator propertyColumnIterator = property.getColumnIterator();
					Column propertyColumn = (Column)propertyColumnIterator.next();
					Result result = new Result();
					result.setProperty(property.getName());
					result.setColumn(propertyColumn.getName().toUpperCase());
					result.setLoadEntity(false);
					if (propertyType.isAssociationType() && ! propertyType.isCollectionType()){//
						result.setEntityClass(propertyType.getReturnedClass().getName());
					} 
					propertyColumnsMap.put(result.getColumn(),result);
				}
			}
			//若autoLoadSessionFactoryBean中拿不到该实体类型，则用反射机制去形成propertyColumnsMap;
			if (0 < resultMap.getResultProperty().size()){
				Map<String, Result> resultPropertyMap = resultMap.getResultProperty();
				Iterator<String> resultPropertyKeyIterator = resultPropertyMap.keySet().iterator();
				while (resultPropertyKeyIterator.hasNext()){
					String resultColumnKey = resultPropertyKeyIterator.next().toUpperCase();
					Result orignPropertyResult = propertyColumnsMap.get(resultColumnKey);
					if (null != orignPropertyResult){
						Result replaceResult = resultPropertyMap.get(resultColumnKey);
						if (replaceResult.isLoadEntity() ){
							if (StringUtils.isNotEmpty(replaceResult.getColumn()))orignPropertyResult.setColumn(replaceResult.getColumn());
							orignPropertyResult.setLoadEntity(true);
							propertyColumnsMap.put(orignPropertyResult.getColumn(), orignPropertyResult);
						}
					} else {
						propertyColumnsMap.put(resultColumnKey, resultPropertyMap.get(resultColumnKey));
					}
				}
				
			}

		} else {
			Class pojoClass = Class.forName(resultMap.getEntityClass());
			PropertyDescriptor[] PropertyDescriptors = BeanUtils.getPropertyDescriptors(pojoClass);
			for (int i=0;i<PropertyDescriptors.length;i++){
				Result result = new Result();
				result.setProperty(PropertyDescriptors[i].getName());
				result.setColumn(PropertyDescriptors[i].getName().toUpperCase());
				result.setLoadEntity(false);
				propertyColumnsMap.put(result.getColumn(),result);
			}
			if (0 < resultMap.getResultProperty().size()){
				Map<String, Result> resultPropertyMap = resultMap.getResultProperty();
				Iterator<String> resultColumnKeyIterator = resultPropertyMap.keySet().iterator();
				while (resultColumnKeyIterator.hasNext()){
					String resultColumnKey = resultColumnKeyIterator.next();
					propertyColumnsMap.put(resultColumnKey, resultPropertyMap.get(resultColumnKey));
				}
				
			}
		}
		//增加缓存数据，第二次查询则可提高效率
		if (null != cache && null != cache.getCache() && Boolean.valueOf(ConfigurableConstants.getProperty("sql.use.cache","true"))){
			cache.put(resultMap.getId(), propertyColumnsMap);
		}
	}

 
	/**
	 * 根据属性数组aliases及一一对应的tuple数组，转化为 一个Object
	 * @param tuple 属性的值数组
	 * @param aliases 属性名数组
	 * @return Object 查询的结果对象
	 */
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object returnValue = null;
		try {
			returnValue = Class.forName(resultMap.getEntityClass()).newInstance();
			for ( int i=0; i<tuple.length; i++ ) {
				String alias = aliases[i];
				Result result = propertyColumnsMap.get(alias);
				if (!result.isLoadEntity() && StringUtils.isEmpty(result.getEntityClass()) ){
					org.apache.commons.beanutils.BeanUtils.setProperty(returnValue, result.getProperty(), tuple[i]);
				} else if (result.isLoadEntity() && StringUtils.isNotEmpty(result.getEntityClass())){
					 Class refEntityClass = Class.forName(result.getEntityClass());
					 AutoLoadSessionFactoryBean autoLoadSessionFactoryBean = (AutoLoadSessionFactoryBean) ApplicationContextKeeper.getAppCtx().getBean("&sessionFactory");
					 PersistentClass persistentClass = autoLoadSessionFactoryBean.getConfiguration().getClassMapping(result.getEntityClass());
					 Class refEntityIdClass =persistentClass.getIdentifierProperty().getType().getReturnedClass();
					 Serializable refEntityId = (Serializable) ConvertUtils.convert(tuple[i], refEntityIdClass);
					 BaseDao baseDao = (BaseDao) ApplicationContextKeeper.getAppCtx().getBean("baseDao");
					 Object refEntityValue =baseDao.get(refEntityClass, refEntityId);				
					 org.apache.commons.beanutils.BeanUtils.setProperty(returnValue, result.getProperty(), refEntityValue);
				}
				if ("rowCount".equalsIgnoreCase(alias)){
					try {
						totalCount = Integer.parseInt(tuple[i].toString());
					} catch (Exception e){
						logger.error("paser rowCount error,the row count should be numeric");
					}
					
				}
			}
		} catch (InstantiationException e) {
			logger.error(e);
		} catch (IllegalAccessException e) {
			logger.error(e);
		} catch (ClassNotFoundException e) {
			logger.error(e);
		} catch (InvocationTargetException e) {
			logger.error(e);
		} 
		return returnValue;
	}

	public List transformList(List collection) {
		return collection;
	}
}
