/* com.cutty.bravo.core.security.manager.cache.ButtonResourceCacheManager.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-3-24 上午09:01:33, Created by Yeon
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
import com.cutty.bravo.core.security.domain.ButtonResource;

/**
 *
 * <p>
 * <a href="ButtonResourceCacheManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:Wujx21cn@gmail.com">Jason Wu</a>
 */
public class ButtonResourceCacheManager extends BaseCacheManager<ButtonResource> {

	private String cacheName = Constants.BUTTONRESOURCE_CACHE_NAME;

	/**
	 * 该类用于记录按钮的详细信息，包括其授权信息
	 * <p>
	 * <a href="ButtonResourceCacheManager.java.html"><i>View Source</i></a>
	 * </p>
	 *
	 */
	/**
	 * 构造函数,初始化cache
	 */
	public ButtonResourceCacheManager(){
		
	}

	/**
	 * 根据按钮名称获取该按钮对应的权限.
	 * @param name
	 * @return String[]
	 * @throws BizException 
	 */
	public Long[] findButtonResourceAuthorityByName(String name) throws BizException{
		Long[] buttonResourceAuthority = null; 
		try {
			ButtonResourceDetail buttonResourceDetail = (ButtonResourceDetail)getCache().get(name);
			if ( null != buttonResourceDetail ){
					buttonResourceAuthority = buttonResourceDetail.getAuthorities();
			}
		} catch (CacheException e) {
			logger.error(e);
			throw new BizException("获取缓存失败"+e.getLocalizedMessage());
		} 
		return buttonResourceAuthority;
	}
	
	/**
	 * 根据按钮名称获取按钮详细信息对象.
	 * @param name
	 * @return buttonResourceDetail
	 * @throws BizException 
	 */
	public ButtonResourceDetail findButtonResourceByName(String name) throws BizException{
		ButtonResourceDetail buttonResourceDetail = null; 
		try {
			buttonResourceDetail = (ButtonResourceDetail)getCache().get(name);
		} catch (CacheException e) {
			logger.error(e);
			throw new BizException("获取缓存失败"+e.getLocalizedMessage());
		}
		return buttonResourceDetail;
	}	

	public void refreshButtonResourceDetailCache(ButtonResource buttonResource) throws BizException{
		try {
			//先根据按钮名称删去缓存中该按钮详细信息对象，然后再添加一个新的
			ButtonResourceDetail buttonResourceDetail = (ButtonResourceDetail)getCache().get(buttonResource.getName());
			if (null != buttonResourceDetail) getCache().remove(buttonResource.getName());
			buttonResourceDetail  = new ButtonResourceDetail();
			buttonResourceDetail.setName(buttonResource.getName());
			buttonResourceDetail.setOperCondition(buttonResource.getOperCondition());
			Long[] authorities = getAuthorityFromButtonResource(buttonResource);
			if (null != authorities){
				buttonResourceDetail.setAuthorities(authorities);
				getCache().put(buttonResource.getName(), buttonResourceDetail);
			}
		} catch (CacheException e) {
			logger.error(e);
			throw new BizException("获取缓存失败"+e.getLocalizedMessage());
		} 
	}
	

	
	/**
	 * 根据按钮实体的ID获取其对应的授权码
	 * @param resource
	 * @return
	 */
	private Long[] getAuthorityFromButtonResource(ButtonResource buttonResource){
		String hql = "select permission.id from Permission permission left outer join permission.buttonResources buttonResource where buttonResource.id = "+buttonResource.getId();
		List<Long> permissions = this.getBaseDao().find(null, hql,true);
		if (null == permissions || 0 == permissions.size()) return null;
		Long[] authorities = new Long[permissions.size()];
		for (int i = 0; i< permissions.size();i++){
			authorities[i] = permissions.get(i);
		}
		return authorities;
	}
	
	private final class ButtonResourceDetail{
		private String name;           //按钮对象的名称
		private String operCondition;  //按钮对象的执行情况
		private Long[] authorities;  //按钮对象的授权码
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getOperCondition() {
			return operCondition;
		}
		public void setOperCondition(String operCondition) {
			this.operCondition = operCondition;
		}
		public Long[] getAuthorities() {
			return authorities;
		}
		public void setAuthorities(Long[] authorities) {
			this.authorities = authorities;
		}
	}

	@Override
	protected String getCacheName() {
		return cacheName;
	}


	@Override
	public void initCacheData(String saasCode) throws BizException {
		List<ButtonResource> buttonResources = loadDataFromDb(saasCode);		
		Iterator<ButtonResource> buttonResourceIT = buttonResources.iterator();
		try {
			while (buttonResourceIT.hasNext()) {
				ButtonResource buttonResource = buttonResourceIT.next();
				
				//添加按钮授权代码到缓存中，以按钮名称做为主键
				ButtonResourceDetail buttonResourceDetail  = new ButtonResourceDetail();
				buttonResourceDetail.setName(buttonResource.getName());
				buttonResourceDetail.setOperCondition(buttonResource.getOperCondition());
				Long[] authorities = getAuthorityFromButtonResource(buttonResource);
				buttonResourceDetail.setAuthorities(authorities);
				if (null == authorities) continue;
				getCache(saasCode).put(buttonResourceDetail.getName(), buttonResourceDetail);
			}
		} catch (CacheException e) {
			logger.error(e);
			throw new BizException("获取缓存失败"+e.getLocalizedMessage());
		}
	}


	@Override
	public String getLoadDataQuery() {
		return  "select * from bravo_button";
	}


	@Override
	public List<ButtonResource> paserData(ResultSet rs) throws BizException {
		List<ButtonResource> dataList = new ArrayList<ButtonResource>();
		try {
			while (rs.next()) { 
				ButtonResource buttonResource = new ButtonResource();
				buttonResource.setId(rs.getLong("id"));
				buttonResource.setName(rs.getString("button_name"));
				buttonResource.setOperCondition(rs.getString("oper_condition"));
				buttonResource.setComments(rs.getString("comments"));
				dataList.add(buttonResource);
			}
		} catch (SQLException e) {
			logger.error("权限实现转换失败！！！",e);
			throw new BizException("权限实现转换失败！！！"+e.getLocalizedMessage());
		}
		return dataList;
	}
	
}
