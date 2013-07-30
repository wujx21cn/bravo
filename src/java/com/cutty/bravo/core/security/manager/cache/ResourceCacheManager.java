/* com.cutty.bravo.core.security.manager.ResourceCacheManager.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-15 上午02:27:45, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.manager.cache;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cutty.bravo.components.common.domain.Enumeration;
import com.cutty.bravo.core.exception.BizException;
import com.cutty.bravo.core.exception.CacheException;
import com.cutty.bravo.core.security.Constants;
import com.cutty.bravo.core.security.domain.Resource;
import com.cutty.bravo.core.web.handler.RequestHandler;

/**
 * 该类继承于BaseManager<Resource>，负责从数据库中获取所有类型资源(URL)的授权信息，可以起到缓存这些信息的功能，
 * 方便访问决策管理
 * <p>
 * <a href="ResourceCacheManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

public class ResourceCacheManager  extends BaseCacheManager<Resource>{
	
	private String cacheName = Constants.RESOURCE_CACHE_NAME;	
	/**
	 * 构造函数,初始化cache
	 */
	public ResourceCacheManager(){
	
	}
	/**
	 * 根据资源字符串、资源串类型获取该资源串对应的权限.
	 * @param resString
	 * @param resType
	 * @return
	 * @throws BizException 
	 */
	public String[] findResourceAuthorityByResString(String resString,String resType) throws BizException{
		String[] resourceAuthority = null; 
		try {
			ResourceDetail resourceDetail = (ResourceDetail)getCache().get(resString);
			if (null != resourceDetail && resourceDetail.getResType().equals(resType)){
					resourceAuthority = resourceDetail.getAuthorities();
			}
		} catch (CacheException e) {
			logger.error(e);
			throw new BizException("获取缓存失败"+e.getLocalizedMessage());
		} 
		return resourceAuthority;
	}
	
	/**
	 * 根据资源字符串、资源串类型获取资源详细信息对象.
	 * @param resString
	 * @param resType
	 * @return
	 * @throws BizException 
	 */
	public ResourceDetail findResourceByResString(String resString,String resType) throws BizException{
		ResourceDetail resourceDetail = null; 
		try {
			resourceDetail = (ResourceDetail)getCache().get(resString);
		} catch (CacheException e) {
			logger.error(e);
			throw new BizException("获取缓存失败"+e.getLocalizedMessage());
		} 
		return resourceDetail;
	}	

	/**
	 * 根据实体名获取数据分区数据命名列表，默认权限对应当前登陆人.
	 * @param entityName
	 * @return
	 */
	public List<String> getDataParationNameByEntityName(String entityName)throws BizException{
		RequestHandler requestHandler = RequestHandler.getContextRequestHandler();
		if (null == RequestHandler.getContextRequestHandler()) return null;
		if (null != requestHandler.getCurrentUserAutorities() && 0 < requestHandler.getCurrentUserAutorities().size()) {
			return getDataParationNameByEntityName(entityName,requestHandler.getCurrentUserAutorities());	
		}
		return null;
	}
	
	/**
	 * 根据实体名获取数据分区数据
	 * @param entityName
	 * @return
	 */
	public List<String> getDataParationNameByEntityName(String entityName,Set<String> autorities)throws BizException{
		List<String> dataPartionNameList = new ArrayList<String>();
		List<String> dataParations = getResourcesStrByTypeFromCache(Constants.PARTITION);
		if (null == dataParations || 0 == dataParations.size()) return null;
		Iterator<String> dataParationsIT = dataParations.iterator();
		while (dataParationsIT.hasNext()){
			String dataParationStr = dataParationsIT.next();
			if (dataParationStr.indexOf(entityName+":[") > -1){
				ResourceDetail resourceDetail  = findResourceByResString(dataParationStr,Constants.PARTITION);
				if (null == resourceDetail) break;
				String[] resourceAuthoritis = resourceDetail.getAuthorities();
				for (int i=0;i<resourceAuthoritis.length;i++){
					if (autorities.contains(resourceAuthoritis[i])){
						dataPartionNameList.add(resourceDetail.getName());
					}
				}
			}
		}
		return dataPartionNameList;
	}
	/**
	 * 从缓存中根据资源串类型获取资源串
	 * @param type
	 * @return
	 * @throws BizException 
	 */
	public List<String> getResourcesStrByTypeFromCache(String type) throws BizException {
		String reourceTypeCacheName = Constants.RESOURCE_CACHE_TYPE_NAME+":" + type;
		List<String> resources;
		try {
			resources = (List<String>)this.getCache().get(reourceTypeCacheName);
		}  catch (CacheException e) {
			logger.error(e);
			throw new BizException("获取缓存失败"+e.getLocalizedMessage());
		}
		return resources;
	}

	/**
	 * 
	 * @param resource
	 * @throws BizException 
	 */
	public void refreshResourceDetailCache(Resource resource) throws BizException{
		try {
			//先根据资源字符串删去缓存中该资源详细信息对象，然后再添加一个新的
			ResourceDetail resourceDetail = (ResourceDetail)getCache().get(resource.getResString());
			if (null != resourceDetail) getCache().remove(resource.getResString());
			resourceDetail  = new ResourceDetail();
			resourceDetail.setResString(resource.getResString());
			resourceDetail.setResType(resource.getResType().getCode());
			String[] authorities = getAuthorityFromResource(resource);
			if (null != authorities){
				resourceDetail.setAuthorities(authorities);
				getCache().put(resource.getResString(), resourceDetail);
			}
		} catch (CacheException e) {
			logger.error(e);
		}
	}

	/**
	 * 根据resource实体的ID获取其对应的授权码
	 * @param resource
	 * @return
	 */
	private String[] getAuthorityFromResource(Resource resource){
		String hql = "select permission.id from Permission permission left outer join permission.resources resource where resource.id = "+resource.getId();
		List<Long> permissions = this.getBaseDao().find(null, hql,true);
		if (null == permissions || 0 == permissions.size()) return null;
		String[] authorities = new String[permissions.size()];
		for (int i = 0; i< permissions.size();i++){
			authorities[i] = (permissions.get(i)).toString();
		}
		return authorities;
	}

	@Override
	protected String getCacheName() {
		return cacheName;
	}

	@Override
	protected void initCacheData(String saasCode) throws BizException {
		List<Resource> resources = loadDataFromDb(saasCode);		
		Iterator<Resource> resourceIT = resources.iterator();
		try {
			while (resourceIT.hasNext()) {
				Resource resource = resourceIT.next();
				
				//添加资源类型到缓存中，以类型代码为主键
				String reourceTypeCacheName = Constants.RESOURCE_CACHE_TYPE_NAME+":" + resource.getResType().getCode();
				List<String> resourceTypeStr = (List<String>)getCache(saasCode).get(reourceTypeCacheName);
				if (null == resourceTypeStr) resourceTypeStr = new ArrayList<String>();
				resourceTypeStr.add(resource.getResString());
				getCache(saasCode).put(reourceTypeCacheName, resourceTypeStr);
				
				//添加资源授权代码到缓存中，以资源url串做为主键
				ResourceDetail resourceDetail  = new ResourceDetail();
				resourceDetail.setName(resource.getName());
				resourceDetail.setResString(resource.getResString());
				resourceDetail.setResType(resource.getResType().getCode());
				String[] authorities =  getAuthorityFromResource(resource);
				resourceDetail.setAuthorities(authorities);
				if (null == authorities) continue;
				getCache(saasCode).put(resourceDetail.getResString(), resourceDetail);
			}
		} catch (CacheException e) {
			logger.error(e);
			throw new BizException("权限实现转换失败！！！"+e.getLocalizedMessage());
		}
		
	}
	@Override
	protected String getLoadDataQuery() {
		return "select res.*,enu.id as enum_id, enu.code as enum_code  from bravo_resource res left join bravo_enumeration enu on res.res_type=enu.id";
	}
	@Override
	protected List<Resource> paserData(ResultSet rs) throws BizException {
		List<Resource> dataList = new ArrayList<Resource>();
		try {
			while (rs.next()) { 
				Resource res = new Resource();
				res.setId(rs.getLong("id"));
				res.setName(rs.getString("name"));
				res.setResString(rs.getString("res_string"));
				Enumeration resType = new Enumeration();
				resType.setId(rs.getLong("enum_id"));
				resType.setCode(rs.getString("enum_code"));
				res.setResType(resType);
				dataList.add(res);
			}
		} catch (SQLException e) {
			logger.error("权限实现转换失败！！！",e);
			throw new BizException("权限实现转换失败！！！"+e.getLocalizedMessage());
		}
		return dataList;
	}
	
	/**
	 * 该类用于记录资源(URL)的详细信息，包括其授权信息
	 * <p>
	 * <a href="ResourceCacheManager.java.html"><i>View Source</i></a>
	 * </p>
	 *
	 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
	 */
	private final class ResourceDetail implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 680728855502868995L;
		private String name;           //资源对象的名称
		private String resString;      //资源对象的url串
		private String resType;        //资源对象的类型
		private String[] authorities;  //资源对象的授权码
		public String getResString() {
			return resString;
		}
		public void setResString(String resString) {
			this.resString = resString;
		}
		public String getResType() {
			return resType;
		}
		public void setResType(String resType) {
			this.resType = resType;
		}
		public String[] getAuthorities() {
			return authorities;
		}
		public void setAuthorities(String[] authorities) {
			this.authorities = authorities;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}

}
