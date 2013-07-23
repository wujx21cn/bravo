/* com.cutty.bravo.core.security.manager.cache.EtityOperationCacheManager.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-3-23 下午02:47:08, Created by Yeon
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.manager.cache;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cutty.bravo.core.exception.BizException;
import com.cutty.bravo.core.exception.CacheException;
import com.cutty.bravo.core.security.Constants;
import com.cutty.bravo.core.security.domain.EntityOperatePermissionRelation;
import com.cutty.bravo.core.security.domain.Permission;
/**
 *
 * <p>
 * <a href="EtityOperationCacheManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class EntityOperationCacheManager extends BaseCacheManager<EntityOperatePermissionRelation> {
	
	private String cacheName = Constants.ENTITY_OPERATION_CACHE_NAME;
	
	/**
	 * 构造函数,初始化cache
	 * @return 
	 */
	public EntityOperationCacheManager(){
	}
	/**
	 * 根据实体名称、实体操作类型获取实体操作对应的权限.
	 * @param entityName 实体名称
	 * @param operType   实体操作类型
	 * @return
	 * @throws BizException 
	 */
	public Long[] findEntityOperationAuthorityByEntityOperation(String entityName,String operType) throws BizException{
		Long[] entityOperationAuthority = null; 
		try {
			String keyString = entityName+"."+operType;
			EntityOperationDetail entityOperationDetail = (EntityOperationDetail)getCache().get(keyString);
			if (null != entityOperationDetail){
				entityOperationAuthority = entityOperationDetail.getAuthorities();
			}
		} catch (CacheException e) {
			logger.error(e);
			throw new BizException("获取缓存失败"+e.getLocalizedMessage());
		}
		return entityOperationAuthority;
	}
	
	/**
	 * 根据实体名称、实体操作类型获取实体操作详细信息对象.
	 * @param entityName 实体名称
	 * @param operType   实体操作类型
	 * @return
	 * @throws BizException 
	 */
	public EntityOperationDetail findEntityOperationDetailByEntityOperation(String entityName,String operType) throws BizException{
		EntityOperationDetail entityOperationDetail = null; 
		try {
			String keyString = entityName+"."+operType;
			entityOperationDetail = (EntityOperationDetail)getCache().get(keyString);
		} catch (CacheException e) {
			logger.error(e);
			throw new BizException("获取缓存失败"+e.getLocalizedMessage());
		} 
		return entityOperationDetail;
	}
	
	/**
	 * 
	 * @param resource
	 * @throws BizException 
	 */
	public void refreshEntityOperationDetailCache(EntityOperatePermissionRelation entityOperatePermissionRelation) throws BizException{
		try {
			//拼接entityOperationPermission的标识符
			String keyString = entityOperatePermissionRelation.getEntityName()+"."+entityOperatePermissionRelation.getOperType();
			EntityOperationDetail entityOperationDetail = (EntityOperationDetail)getCache().get(keyString);
			if (null != entityOperationDetail) getCache().remove(keyString);
			entityOperationDetail  = new EntityOperationDetail();
			entityOperationDetail.setName(entityOperatePermissionRelation.getEntityName());
			entityOperationDetail.setOperType(entityOperatePermissionRelation.getOperType());
			Long[] authorities = getAuthorityFromEntityOperatePermissionRelation(entityOperatePermissionRelation);
			if (null != authorities){
				entityOperationDetail.setAuthorities(authorities);
				getCache().put(keyString, entityOperationDetail);
			}
		} catch (CacheException e) {
			logger.error(e);
			throw new BizException("获取缓存失败"+e.getLocalizedMessage());
		}
	}


	private Long[] getAuthorityFromEntityOperatePermissionRelation(EntityOperatePermissionRelation entityOperationPermissionRelation) {
		String hql = "select permission.id from EntityOperatePermissionRelation entityOperationPermissionRelation where entityOperationPermissionRelation.entityName = '"+entityOperationPermissionRelation.getEntityName()+"' and entityOperationPermissionRelation.operType = '"+entityOperationPermissionRelation.getOperType()+"'";
		List<Long> permissions = this.getBaseDao().find(null, hql,true);
		if (null == permissions || 0 == permissions.size()) return null;
		Long[] authorities = new Long[permissions.size()];
		for (int i = 0; i< permissions.size();i++){
			authorities[i] = permissions.get(i);
		}
		return authorities;
	}
	@Override
	public String getCacheName() {
		return cacheName;
	}

	@Override
	public void initCacheData(String saasCode) throws BizException {
		List<EntityOperatePermissionRelation> entityOperationPermissionRelations =  loadDataFromDb(saasCode);		
		Iterator<EntityOperatePermissionRelation> entityOperationPermissionRelationIT = entityOperationPermissionRelations.iterator();
		try {
			while (entityOperationPermissionRelationIT.hasNext()) {
				EntityOperatePermissionRelation entityOperationPermissionRelation = entityOperationPermissionRelationIT.next();
				
				EntityOperationDetail entityOperationDetail  = new EntityOperationDetail();
				entityOperationDetail.setName(entityOperationPermissionRelation.getEntityName());
				entityOperationDetail.setOperType(entityOperationPermissionRelation.getOperType());
				//以entityName和operType为主键
				String keyString = entityOperationDetail.getName()+"."+entityOperationDetail.getOperType();
				
				//对于权限数组的赋值，要先根据已遍历的情况，更新该值
				if( null != getCache(saasCode).get(keyString) && null != ((EntityOperationDetail)getCache(saasCode).get(keyString)).getAuthorities() ){
					Long[] oldAuthorities = ((EntityOperationDetail)getCache(saasCode).get(keyString)).getAuthorities();
					Long[] newAuthorities = new Long[oldAuthorities.length+1];
					for( int i=0; i<oldAuthorities.length; i++ ){
						newAuthorities[i] = oldAuthorities[i];
					}
					newAuthorities[oldAuthorities.length] = entityOperationPermissionRelation.getPermission().getId();
					entityOperationDetail.setAuthorities(newAuthorities);
				}else{
					Long[] newAuthorities = new Long[1];
					newAuthorities[0] = entityOperationPermissionRelation.getPermission().getId();
					entityOperationDetail.setAuthorities(newAuthorities);
				}
				
				getCache(saasCode).put(keyString, entityOperationDetail );
			}
		} catch (CacheException e) {
			logger.error(e);
			throw new BizException("获取缓存失败"+e.getLocalizedMessage());
		} 
		
	}

	@Override
	public String getLoadDataQuery() {
		return "select * from bravo_entity_oper_permis";
	}

	@Override
	public List<EntityOperatePermissionRelation> paserData(ResultSet rs) throws BizException {
		List<EntityOperatePermissionRelation> dataList = new ArrayList<EntityOperatePermissionRelation>();
		try {
			while (rs.next()) { 
				EntityOperatePermissionRelation entityOperatePermissionRelation = new EntityOperatePermissionRelation();
				entityOperatePermissionRelation.setId(rs.getLong("id"));
				Permission permission = this.getBaseDao().get(Permission.class, rs.getLong("PERMIS_ID"));
				entityOperatePermissionRelation.setPermission(permission);
				entityOperatePermissionRelation.setOperType(rs.getString("OPER_TYPE"));
				entityOperatePermissionRelation.setEntityName(rs.getString("ENTITY_NAME"));
				dataList.add(entityOperatePermissionRelation);
			}
		} catch (SQLException e) {
			logger.error("权限实现转换失败！！！",e);
			throw new BizException("权限实现转换失败！！！"+e.getLocalizedMessage());
		}
		return dataList;
	}
	
	private final class EntityOperationDetail{
		private String   name;
		private String   operType;
		private Long[] authorities;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getOperType() {
			return operType;
		}
		public void setOperType(String operType) {
			this.operType = operType;
		}
		public Long[] getAuthorities() {
			return authorities;
		}
		public void setAuthorities(Long[] authorities) {
			this.authorities = authorities;
		}
	}	
}
