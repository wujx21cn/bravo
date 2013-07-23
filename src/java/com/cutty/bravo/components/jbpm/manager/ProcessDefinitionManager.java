/* com.cutty.bravo.components.jbpm.manager.ProcessDefinitionManager.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-11-7 下午03:02:44, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jbpm.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.jbpm.graph.def.ProcessDefinition;
import org.springframework.stereotype.Service;


import com.cutty.bravo.core.dao.support.Page;
import com.cutty.bravo.core.manager.BaseManager;

/**
 * 该类用于管理流程定义的读取和写入，同时增加了一个得到当前最新流程定义版本的方法
 *
 * <p>
 * <a href="ProcessDefinitionManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Service("processDefinitionManager")
public class ProcessDefinitionManager extends BaseManager<ProcessDefinition>{
	
	/**
	 * 根据工作流名称name和工作流描述description得到符合条件的工作流程定义的最新版本
	 * @param name-工作流名称
	 * @param description-工作流描述
	 * @param page-控制分页的数量及页面的条目数量
	 * @return 符合条件的最新工作流定义文件列表
	 */
	public List<ProcessDefinition> findLatestProcessDefinition(String name,String description,Page page){

		StringBuffer sqlSB = new StringBuffer("select max(pp.id_) as id from ( ")
		.append("select processdefinition.* from jbpm_processdefinition processdefinition where 1=1  ");
		if (StringUtils.isNotEmpty(name)){
			sqlSB.append(" and processdefinition.name_ like  '%").append(name).append("%'");
		}
		if (StringUtils.isNotEmpty(description)){
			sqlSB.append(" and processdefinition.description_  like '%").append(description).append("%'");
		}
		sqlSB.append(" ) pp  group by pp.name_     ");
		
		Query query = super.getHibernate().getSessionFactory().getCurrentSession().createSQLQuery(sqlSB.toString());
		query.setMaxResults(page.getPageSize());
		query.setFirstResult(page.getStartIndex());
		List processDefinitionIDs = query.list();
		List<ProcessDefinition> ProcessDefinitions = new ArrayList<ProcessDefinition>();
		if (null != processDefinitionIDs){
			for (int i = 0;i<processDefinitionIDs.size();i++){
				BigDecimal processDefinitionID = (BigDecimal)processDefinitionIDs.get(i);
				ProcessDefinitions.add(this.get(Long.parseLong(processDefinitionID.toString())));
			}
		}
		page.setResult(ProcessDefinitions);
		return ProcessDefinitions;
	}
}
