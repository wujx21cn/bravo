/* com.cutty.bravo.core.security.manager.cache.BaseCacheManager.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2011-9-12 下午02:31:46, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2011 bullshit Corporation. All Rights Reserved.
*/
package com.cutty.bravo.core.security.manager.cache;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;

import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.dao.extend.impl.BaseEntityDaoImpl;
import com.cutty.bravo.core.domain.SaasGroup;
import com.cutty.bravo.core.exception.BizException;
import com.cutty.bravo.core.exception.CacheException;
import com.cutty.bravo.core.saas.SaasGroupUtils;
import com.cutty.bravo.core.utils.cache.Cache;
import com.cutty.bravo.core.utils.cache.CacheFactory;
import com.cutty.bravo.core.web.handler.SaasCodeHandler;

/**
 *
 * <p>
 * <a href="BaseCacheManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public abstract class BaseCacheManager <T> extends BaseEntityDaoImpl <T>  {
	protected Map<String,Cache> cacheGroup = new HashMap<String,Cache>();
	protected boolean useSaas = Boolean.parseBoolean(ConfigurableConstants.getProperty("saas.group.database","false"));
	private SaasGroupUtils saasGroupUtils;
	public void initCache() throws BizException{
		Map<String, SaasGroup> saasGroups = saasGroupUtils.getSaasGroups();
		try {
			if (useSaas) {
				for (String saasKey:saasGroups.keySet()) {
					SaasGroup saasGroup = saasGroups.get(saasKey);
					String saasCacheName = getCacheName() +"---" + saasGroup.getCode();
					Cache cache = CacheFactory.getCacheManager().createCache(saasCacheName, Cache.DEFAULT_MAX_ELEMENTS_IN_MEMORY,Cache.DEFAULT_OVERFLOW_TO_DISK,
							Cache.DEFAULT_ETERNAL,Cache.DEFAULT_TIME_TO_LIVE_SECONDS,Cache.DEFAULT_TIME_TO_IDLE_SECONDS);
					cacheGroup.put(saasCacheName, cache);
				}
				//初始化按钮cache
				for (String saasKey:saasGroups.keySet()) {
					initCacheData(saasKey);
				}
			} else {
				//如果是单数据库模式，则直接用单cache
				Cache cache= CacheFactory.getCacheManager().getCache(getCacheName());
				cacheGroup.put(getCacheName(), cache);
				initCacheData(null);
			}

		} catch (CacheException e) {
			logger.error(e);
			throw new BizException("获取缓存失败"+e.getLocalizedMessage());
		} catch (BizException e) {
			logger.error(e);
			throw e;
		}
	}
	
	protected abstract String getCacheName();
	protected abstract void initCacheData(String saasCode) throws BizException;
	protected abstract String getLoadDataQuery();
	protected abstract List<T> paserData(ResultSet rs)  throws BizException ;
	
	protected List<T> loadDataFromDb(String saasCode) throws BizException{
		if (!useSaas) return getAll();
		String sql = getLoadDataQuery();
		List<T> dataList = null ;
		DataSource ds = saasGroupUtils.getSaasGroupDBObject(saasCode);
		//如果为空则直接返回，防止定义错误导致所有saas启动失败。
		if (null == ds) return dataList;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			dataList = paserData(rs);
		} catch (SQLException e) {
			logger.error(e);
			throw new BizException("权限实现转换失败！！！"+e.getLocalizedMessage());
		} catch (BizException e) {
			throw e;
		} finally {
				try {
					if (null == rs) rs.close();
					if (null == stmt) stmt.close();
					if (null == con) con.close();
				} catch (SQLException e) {
					logger.error(e);
				}
		}
		return dataList;
	}
	
	public void refreshCache() throws BizException{
		try {
			getCache().removeAll();
		    String saasCode=null;
		    if (StringUtils.isEmpty(SaasCodeHandler.getSaasCode())) {
		    	saasCode = SaasCodeHandler.DEFAULT_SAAS_KEY;
		    }else {
		    	saasCode = SaasCodeHandler.getSaasCode();
		    }
			initCacheData(saasCode);
		} catch (CacheException e) {
			logger.error(e);
			throw new BizException("获取缓存失败"+e.getLocalizedMessage());
		} 
		
	}
	
	public Cache getCache() throws BizException{
		if (useSaas) {
		    String saasCode=null;
		    if (StringUtils.isEmpty(SaasCodeHandler.getSaasCode())) {
		    	saasCode = SaasCodeHandler.DEFAULT_SAAS_KEY;
		    }else {
		    	saasCode = SaasCodeHandler.getSaasCode();
		    }
			String saasCacheName= getCacheName() +"---" + saasCode;
			return cacheGroup.get(saasCacheName);
		} else {
			return cacheGroup.get(getCacheName());
		}
	}
	
	public Cache getCache(String saasCode){
		if (useSaas) {
			String saasCacheName = getCacheName() +"---" + saasCode;
			return cacheGroup.get(saasCacheName);
		} else {
			return cacheGroup.get(getCacheName());
		}
	}

	public void setSaasGroupUtils(SaasGroupUtils saasGroupUtils) {
		this.saasGroupUtils = saasGroupUtils;
	}
	
	
}
