/* com.cutty.bravo.core.security.manager.PermissionManager.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-10 上午06:03:57, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hibernate.Session;
import org.springframework.stereotype.Service;


import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.manager.BaseManager;
import com.cutty.bravo.core.security.domain.EntityOperatePermissionRelation;
import com.cutty.bravo.core.security.domain.Permission;
import com.cutty.bravo.core.security.manager.cache.EntityOperationCacheManager;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;

/**
 *
 * <p>
 * <a href="PermissionManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Service("permissionManager")
public class PermissionManager  extends BaseManager<Permission>{

	public BaseDao baseDao;

	/**
	 * @param baseDao
	 * the baseDao to set
	 */
	@Override
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * 人员菜单权限树保存
	 * 
	 * @return
	 * @throws Exception
	 */
	public void checkedMenuTreeSaveSql(String[] ids, String permisID) throws Exception {

		Connection conn = null;
		PreparedStatement prepareStatement = null;
		PreparedStatement prepareStatement2 = null;
		Session session = null;
		try {
			session = baseDao.getHibernate().getSessionFactory().getCurrentSession();
			conn = session.connection();
			//首先删除fun_permis表里的该权限对应的所有菜单ID
			prepareStatement = conn.prepareStatement("DELETE FROM bravo_fun_permis WHERE per_id = ?");
			//再把获得的新的该权限的菜单数组插入到数据库的fun_permis表里
			prepareStatement2 = conn.prepareStatement("INSERT INTO bravo_fun_permis (sys_id , per_id) VALUES (?,?)");
			
			prepareStatement.setString(1, permisID);
			prepareStatement.executeUpdate();
			
			for (int i = 0; i < ids.length; i++) {
				prepareStatement2.setString(1, ids[i]);
				prepareStatement2.setString(2, permisID);
				prepareStatement2.executeUpdate();
			}
		} catch (Exception e) {
			logger.error(e);
		} finally {
			prepareStatement.close();
			conn.close();
			session.clear();
		}
	}
	//liangg 090323 
	public void entityOpPermisTreeSave(String[] names, String permisID) throws Exception {
		
		Permission permission = baseDao.get(Permission.class,Long.valueOf(permisID));
		EntityOperationCacheManager entityOperationCacheManager = (EntityOperationCacheManager) ApplicationContextKeeper.getAppCtx().getBean("entityOperationCacheManager");
		try {
				deleteAllRef(permisID);
				if(names != null){
					for(String name : names){
						EntityOperatePermissionRelation entityOpPermis = new EntityOperatePermissionRelation();
						entityOpPermis.setEntityName(name.substring(0, name.lastIndexOf(".")));
						entityOpPermis.setOperType(name.substring(name.lastIndexOf(".")+1,name.length()));
						entityOpPermis.setPermission(permission);
						baseDao.save(entityOpPermis);
					}
				}
				entityOperationCacheManager.refreshCache();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}  

	}
	//根据permisID删除相应的数据，以免出现重复，以便保存新的checkedTree
	private void deleteAllRef(String permisID) throws SQLException {

		Connection conn = null;
		PreparedStatement prepareStatement = null;
	 
		Session session = null;
		try {
			session = baseDao.getHibernate().getSessionFactory().getCurrentSession();
			conn = session.connection();
			//首先删除bravo_entity_oper_permis表里permis_id为permisID的记录
			prepareStatement = conn.prepareStatement("DELETE FROM bravo_entity_oper_permis WHERE permis_id = ?");
			
			prepareStatement.setString(1, permisID);
			prepareStatement.executeUpdate();
	 
		} catch (Exception e) {
			logger.error(e);
		} finally {
			prepareStatement.close();
			conn.close();
			//清除二级查询缓存,但会影响其他cache的使用，需改为只清除该实体的查询缓存
			//session.getSessionFactory().evict(Class)方法只能清除二级缓存中的实体缓存，用evictQueries才可以清除所有的查询缓存
			session.getSessionFactory().evictQueries();
			session.clear();
		}
	}
	
	
}
