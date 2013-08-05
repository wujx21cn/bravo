/* com.cutty.bravo.components.jbpm.manager.TaskManager.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-11-24 上午10:07:10, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jbpm.manager;

import java.util.List;

import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.stereotype.Service;

import com.cutty.bravo.components.common.domain.Department;
import com.cutty.bravo.core.dao.support.Page;
import com.cutty.bravo.core.manager.BaseManager;
import com.cutty.bravo.core.security.domain.Role;
import com.cutty.bravo.core.security.domain.User;


/**
 *
 * <p>
 * <a href="TaskManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
@Service("taskManager")
public class TaskManager  extends BaseManager<TaskInstance>{
	
	public Page  findAassignTaskInstances(final String actorId,Page page){
		StringBuffer sb = new StringBuffer(" select ti from org.jbpm.taskmgmt.exe.TaskInstance as ti where ti.actorId = ?");
		sb.append(" and ti.isSuspended != true  and ti.isOpen = true");
		super.find(page, sb.toString(),true,actorId);
		return page;
	}
	
	public List<Department> getChildDeptTree(Long nodeId){
		//获取子菜单
		String hql = "from Department where parent_dep = "+nodeId+"  order by sequences asc";
		List<Department> departments = (List)super.baseDao.find(null, hql,true);
		return departments;
	}
	
	public List<User> getChildUser(Long nodeId){
		//获取子菜单
		String hql = "from User where department = "+nodeId+"  order by id asc";
		List<User> users = (List)super.baseDao.find(null, hql,true);
		return users;
	}
	
	public List<Role> getChildRoleTree(){
		String hql = "from Role order by id asc";
		List<Role> roles = (List)super.baseDao.find(null, hql,true);
		return roles;
	}
	
	public Role get(Long id) {
		return super.baseDao.get(Role.class,id);
	}
}
