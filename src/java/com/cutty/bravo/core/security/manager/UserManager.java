/* com.cutty.bravo.core.security.manager.UserManager.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-11-10 上午11:37:52, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.manager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.cutty.bravo.core.manager.BaseManager;
import com.cutty.bravo.core.security.domain.User;

/**
 *
 * <p>
 * <a href="UserManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
@Service("userManager")
public class UserManager  extends BaseManager<User>{ 
	
	/**
	 * 根据角色ID获取该角色下的所有人员ID数组
	 * 
	 * @param roleId 角色ID
	 * @return
	 */
	public Set findUseridsByRoleId(String roleId){
		Set userIdSet = new HashSet();
		StringBuffer sb = new StringBuffer("select user.id from User user left outer join user.roles roles where roles.id=").append(roleId);
		List<Long> userIdList = super.getHibernate().find(sb.toString());
		if (null != userIdList && 0 < userIdList.size()){
			String[] userIds = new String[userIdList.size()];
			for (int i=0;i<userIdList.size();i++){
				userIdSet.add(userIdList.get(i).toString());
			}
			return userIdSet;
		}
		return null;
	}
	
}
