/* com.cutty.bravo.core.security.manager.cache.UserDetailsCacheManager.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-17 下午03:32:12, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.manager.cache;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.cutty.bravo.core.exception.BizException;
import com.cutty.bravo.core.exception.CacheException;
import com.cutty.bravo.core.security.Constants;
import com.cutty.bravo.core.security.domain.User;

/**
 * 该类实现了spring security认证管理时用户认证信息的获取来源UserDetailsService接口，为daoAuthenticationProvider提供
 * userDetailsService对象，该对象通过调用UserDetails loadUserByUsername(String username)方法获取用户名为username的
 * 用户详细信息，在这里BRAVO采用了用户信息缓存机制，即首先在用户信息缓存中查找有关的用户信息，如果缓存中没有再到用户数
 * 表中查找，找到后在返回的同时，将该用户详细信息保存到缓存中方便下次快速查找
 * <p>
 * <a href="UserDetailsCacheManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

public class UserDetailsCacheManager   extends BaseCacheManager<User> implements UserDetailsService{
	private String cacheName = Constants.USER_CACHE_NAME;	


	public UserDetailsCacheManager(){
		
	}


	/**
	 * 通过用户名信息获取用户详细信息，先从用户信息缓存中查找，如果没有再到数据库中查找，
	 * 找到后放一个副本到缓存中方便下次认证
	 * @param username 待查用户的用户名
	 * @return UserDetails 待查用户详细信息包括：用户名、密码、GrantedAuthority[]
	 */
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		BravoUserDetails bravoUserDetail = null;
		try {
			bravoUserDetail = (BravoUserDetails)getCache().get(username);
		} catch (CacheException e) {
			//不直接跑出异常，当做没有获取到
			logger.error(e);
		} catch (BizException e) {
			//不直接跑出异常，当做没有获取到
			logger.error(e);
		}
		//如果缓存中已有该用户数据，直接返回
		if (null != bravoUserDetail) return bravoUserDetail;
		//如果缓存中没有，则到数据库中去找
		User user = super.findUniqueBy("loginid", username,true);
		if (null != user){
			bravoUserDetail = new BravoUserDetails();
			List<GrantedAuthority> GrantedAuthoritys = new ArrayList<GrantedAuthority>();
			//该sql语句表示利用用户ID到角色表中找到用户角色，再到角色权限中找到授权码，于是最终得到用户授权码
			String hql = "select permission.id from Permission permission left outer join permission.roles role " +
						"left outer join role.users user where user.id = "+user.getId();
			List<Long> permissions = this.getBaseDao().find(null, hql,true);
			//将得到的授权代码放到bravoUserDetail成员变量GrantedAuthority[] GrantedAuthoritys中去
			//这里GrantedAuthorityImpl类是GrantedAuthority接口的一实现，是由spring提供的，它实际只包含一个
			//String对象用于存放一个授权码，同时实现了一个public String getAuthority()方法用于获取该授权码
			if (null != permissions && 0 < permissions.size()){
				for (int i=0;i<permissions.size();i++){
					GrantedAuthoritys.add(new GrantedAuthorityImpl(permissions.get(i).toString()));
				}	
			} else {
				GrantedAuthoritys.add(new GrantedAuthorityImpl("this guy has not permission give by jason!!!!,just don't let it error!!!"));
			}
			bravoUserDetail.setAuthorities(GrantedAuthoritys.toArray(new GrantedAuthority[0]));
			//这里的username和表里的loginid相对应
			bravoUserDetail.setUsername(username);
			bravoUserDetail.setPassword(user.getPasswd());
		} 
		try {
			//同时将结果放到缓存中，方便下次读取
			getCache().put(username, bravoUserDetail);
		} catch (CacheException e) {
			logger.error(e);
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bravoUserDetail;
	}

	public void refreshUserDetailsCache(String username) throws BizException{
		try {
			getCache().remove(username);
		} catch (CacheException e) {
			logger.error(e);
			throw new BizException("获取缓存失败"+e.getLocalizedMessage());
			
		}
	}
	/**
	 * 根据用户名获取用户授权码
	 * @param username 用户名
	 * @return Set<String> 用户授权码
	 * @throws DataAccessException
	 */
	public Set<String> loadUserAuthorityByUsername(String username) throws DataAccessException {
		GrantedAuthority[] grantedAuthoritys = loadUserByUsername(username).getAuthorities();
		if (null == grantedAuthoritys) return null;
		Set<String> authorities = new HashSet<String>();
		for (int i=0;i<grantedAuthoritys.length;i++){
			authorities.add(grantedAuthoritys[i].getAuthority());
		}
		return authorities;
	}
	
	/**
	 * 根据用户名获取用户授权码(Long类型)
	 * @param username 用户名
	 * @return Set<String> 用户授权码
	 * @throws DataAccessException
	 */
	public Long[] loadUserPermissIdByUsername(String username) throws DataAccessException {
		GrantedAuthority[] grantedAuthoritys = loadUserByUsername(username).getAuthorities();
		if (null == grantedAuthoritys) return null;
		Long[] authorities = new Long[grantedAuthoritys.length];
		for (int i=0;i<grantedAuthoritys.length;i++){
			try{
				authorities[i] = Long.parseLong(grantedAuthoritys[i].getAuthority().toString());
			}catch(NumberFormatException e){   
		        e.printStackTrace();
		        logger.error(e);
			}
		}
		return authorities;
	}

	
	
	//将com.cutty.bravo.core.security.domain.BravoUserDetails迁移至UserDetailsManager
	//作为内隐类,保持安全模块符合bravo平台规范，减少对spring security的依赖类
	/**
	 * 该类实现了spring security的UserDetails接口，记录一个用户详细信息，包括：用户名、密码、GrantedAuthority[]，
	 * 用于spring security的认证服务
	 */
	private final class BravoUserDetails implements UserDetails {
		private static final long serialVersionUID = 834921367135187790L;
		private GrantedAuthority[] Authorities;       //用户授权码
		private String password;                      //用户密码
		private String username;                      //用户名称
		private boolean accountNonExpired =true;      //用户是否过期
		private boolean accountNonLocked =true;       //用户是否被锁定
		private boolean credentialsNonExpired =true ; //用户是否信用过期
		private boolean enabled =true;                //用户是否有效
		
		public GrantedAuthority[] getAuthorities() {
			// TODO Auto-generated method stub
			return Authorities;
		}

		public String getPassword() {
			// TODO Auto-generated method stub
			return password;
		}

		public String getUsername() {
			// TODO Auto-generated method stub
			return username;
		}

		public boolean isAccountNonExpired() {
			return accountNonExpired;
		}

		public boolean isAccountNonLocked() {
			return accountNonLocked;
		}

		public boolean isCredentialsNonExpired() {
			return credentialsNonExpired;
		}

		public boolean isEnabled() {
			return enabled;
		}

		/**
		 * @param authorities the authorities to set
		 */
		public void setAuthorities(GrantedAuthority[] authorities) {
			Authorities = authorities;
		}

		/**
		 * @param password the password to set
		 */
		public void setPassword(String password) {
			this.password = password;
		}

		/**
		 * @param username the username to set
		 */
		public void setUsername(String username) {
			this.username = username;
		}

		/**
		 * @param accountNonExpired the accountNonExpired to set
		 */
		public void setAccountNonExpired(boolean accountNonExpired) {
			this.accountNonExpired = accountNonExpired;
		}

		/**
		 * @param accountNonLocked the accountNonLocked to set
		 */
		public void setAccountNonLocked(boolean accountNonLocked) {
			this.accountNonLocked = accountNonLocked;
		}

		/**
		 * @param credentialsNonExpired the credentialsNonExpired to set
		 */
		public void setCredentialsNonExpired(boolean credentialsNonExpired) {
			this.credentialsNonExpired = credentialsNonExpired;
		}

		/**
		 * @param enabled the enabled to set
		 */
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
	}



	@Override
	protected String getCacheName() {
		return cacheName;
	}


	@Override
	protected void initCacheData(String saasCode) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected String getLoadDataQuery() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected List<User> paserData(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}
}
