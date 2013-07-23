/* com.cutty.bravo.components.common.web.CodeGenAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2011-7-1 下午05:32:29, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.components.common.domain.CodeGenDomain;
import com.cutty.bravo.components.common.domain.EntityGenDomain;
import com.cutty.bravo.components.common.domain.PageGenDomain;
import com.cutty.bravo.components.common.manager.CodeGenManager;
import com.cutty.bravo.core.utils.DateUtil;
import com.cutty.bravo.core.web.handler.RequestHandler;
import com.cutty.bravo.core.web.struts2.BaseActionSupport;

/**
 *
 * <p>
 * <a href="CodeGenAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Namespace("/common")   
@ParentPackage("bravo")
public class CodeGenAction extends BaseActionSupport{

	private static final long serialVersionUID = -8193596861836056361L;
	protected CodeGenManager codeGenManager;

	public void setCodeGenManager(CodeGenManager codeGenManager) {
		this.codeGenManager = codeGenManager;
	}
	public String selectTable() throws Exception {
		String contextPath = ServletActionContext.getServletContext().getRealPath("/");
		String srcFolder = contextPath.substring(0,contextPath.lastIndexOf("\\")) +"\\src\\java";
		ServletActionContext.getRequest().setAttribute("srcFolder",srcFolder);
		List<String> tableNames = codeGenManager.getDataBaseTables();
		ServletActionContext.getRequest().setAttribute("tableNames",tableNames);
		return "selectTable";
    }
	public String genConfig(){
		String tableName = ServletActionContext.getRequest().getParameter("tableName");
		String entityName =tableName;
		if (tableName.indexOf("_")>0){
			String[] tableNameArr = tableName.split("_");
			entityName = tableNameArr[1].substring(0,1).toUpperCase()+tableNameArr[1].substring(1,tableNameArr[1].length());
			if (tableNameArr.length > 2) 
			for (int i=2;i<tableNameArr.length;i++){
				entityName = entityName +tableNameArr[i].substring(0,1).toUpperCase() +tableNameArr[i].substring(1,tableNameArr[i].length());
			}
		}
		String moduleName = ServletActionContext.getRequest().getParameter("moduleName");
		String srcFolder = ServletActionContext.getRequest().getParameter("srcFolder");
		String packageName = ServletActionContext.getRequest().getParameter("packageName");
		String modulePath = ServletActionContext.getServletContext().getRealPath("/") +"\\WEB-INF\\modules\\"+moduleName+"\\";
		String[] packageArr = packageName.split("[.]");

		ServletActionContext.getRequest().setAttribute("sourcePath",srcFolder);
		ServletActionContext.getRequest().setAttribute("entityPath",packageName+".domain");
		ServletActionContext.getRequest().setAttribute("managerName",entityName+"Manager");
		ServletActionContext.getRequest().setAttribute("managerPath",packageName+".manager");
		ServletActionContext.getRequest().setAttribute("actionnName",entityName+"Action");
		ServletActionContext.getRequest().setAttribute("actionnPath",packageName+".web");
		ServletActionContext.getRequest().setAttribute("moduleName",moduleName);
		
		ServletActionContext.getRequest().setAttribute("modulePath",modulePath);
		List<CodeGenDomain> tableColumns= codeGenManager.getTableStuct(tableName);
		ServletActionContext.getRequest().setAttribute("tableName",tableName);
		ServletActionContext.getRequest().setAttribute("tableColumns",tableColumns);
		ServletActionContext.getRequest().setAttribute("entityName",entityName);
		ServletActionContext.getRequest().setAttribute("entityNameSet",codeGenManager.getEntityNames());
		return "genConfig";
	}
	
	public String genSourceCode(){
		genJavaSourceCode();
		genPageSourceCode();
		return "genSourceCode";
	}
	private void genPageSourceCode(){
		String newAddNeed = ServletActionContext.getRequest().getParameter("new_add_need");
		String viewAddNeed = ServletActionContext.getRequest().getParameter("view_add_need");
		String listAddNeed = ServletActionContext.getRequest().getParameter("list_add_need");
		RequestHandler requestHandler = RequestHandler.getContextRequestHandler();
		String modulePath= ServletActionContext.getRequest().getParameter("modulePath");

		String entityName = ServletActionContext.getRequest().getParameter("entityName");
		
		String newPageDataproxy = ServletActionContext.getRequest().getParameter("new_page_dataproxy");
		String newPageTitle = ServletActionContext.getRequest().getParameter("new_page_title");
		String newFieldListTitle = ServletActionContext.getRequest().getParameter("new_field_list_title");
		String newFieldListDataproxy = ServletActionContext.getRequest().getParameter("new_field_list_dataproxy");
		
		Map<String,String> newFieldNeed = requestHandler.getParameterMapWithPrefix("new_field_need_", "");
		List<PageGenDomain> newPageGenDomains = new ArrayList<PageGenDomain>();
		Iterator<String> newFieldNeedKey = newFieldNeed.keySet().iterator();
		while(newFieldNeedKey.hasNext()){
			String keyIndex = newFieldNeedKey.next();
			PageGenDomain newPageGenDomain = new PageGenDomain();
			newPageGenDomain.setFieldName(ServletActionContext.getRequest().getParameter("new_field_name_"+keyIndex));
			newPageGenDomain.setLabelName(ServletActionContext.getRequest().getParameter("new_label_name_"+keyIndex));
			newPageGenDomain.setFieldType(ServletActionContext.getRequest().getParameter("new_field_type_"+keyIndex));
			newPageGenDomain.setFieldM2nRefField(ServletActionContext.getRequest().getParameter("new_field_m2n_ref_field_"+keyIndex));
			newPageGenDomain.setFieldM2nDataproxy(ServletActionContext.getRequest().getParameter("new_field_m2n_dataproxy_"+keyIndex));
			newPageGenDomain.setFieldM2nRefDisplay(ServletActionContext.getRequest().getParameter("new_field_m2n_ref_display_"+keyIndex));
			newPageGenDomain.setFieldM2nRefValue(ServletActionContext.getRequest().getParameter("new_field_m2n_ref_value_"+keyIndex));
			newPageGenDomains.add(newPageGenDomain);
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("newPageDataproxy",newPageDataproxy);
		param.put("entityName",entityName);
		param.put("newPageTitle",newPageTitle);
		param.put("newPageGenDomains",newPageGenDomains);
		
		Map<String,String> listQueryFieldNeed = requestHandler.getParameterMapWithPrefix("eneity_field_query_need_", "");
		Iterator<String> listQueryFieldNeedKey = listQueryFieldNeed.keySet().iterator();
		List<PageGenDomain> listQueryPageGenDomains = new ArrayList<PageGenDomain>();
		while(listQueryFieldNeedKey.hasNext()){
			String keyIndex = listQueryFieldNeedKey.next();
			PageGenDomain listQueryPageGen = new PageGenDomain();
			listQueryPageGen.setFieldName(ServletActionContext.getRequest().getParameter("eneity_field_list_name_"+keyIndex));
			listQueryPageGen.setLabelName(ServletActionContext.getRequest().getParameter("eneity_list_display_"+keyIndex));
			listQueryPageGen.setFieldType(ServletActionContext.getRequest().getParameter("new_field_type_"+keyIndex));
			listQueryPageGen.setFieldM2nRefField(ServletActionContext.getRequest().getParameter("new_field_m2n_ref_field_"+keyIndex));
			listQueryPageGen.setFieldM2nDataproxy(ServletActionContext.getRequest().getParameter("new_field_m2n_dataproxy_"+keyIndex));
			listQueryPageGen.setFieldM2nRefDisplay(ServletActionContext.getRequest().getParameter("new_field_m2n_ref_display_"+keyIndex));
			listQueryPageGen.setFieldM2nRefValue(ServletActionContext.getRequest().getParameter("new_field_m2n_ref_value_"+keyIndex));
			listQueryPageGenDomains.add(listQueryPageGen);
		}
		Map<String,String> eneityFieldListFieldNeed = requestHandler.getParameterMapWithPrefix("eneity_field_list_need_", "");
		Iterator<String> eneityFieldListFieldNeedKey = eneityFieldListFieldNeed.keySet().iterator();
		List<PageGenDomain> eneityFieldListGenDomains = new ArrayList<PageGenDomain>();
		while(eneityFieldListFieldNeedKey.hasNext()){
			String keyIndex = eneityFieldListFieldNeedKey.next();
			PageGenDomain listDisplayPageGen = new PageGenDomain();
			listDisplayPageGen.setFieldName(ServletActionContext.getRequest().getParameter("eneity_field_list_name_"+keyIndex));
			listDisplayPageGen.setLabelName(ServletActionContext.getRequest().getParameter("eneity_list_display_"+keyIndex));
			listDisplayPageGen.setFieldType(ServletActionContext.getRequest().getParameter("new_field_type_"+keyIndex));
			listDisplayPageGen.setFieldM2nRefField(ServletActionContext.getRequest().getParameter("new_field_m2n_ref_field_"+keyIndex));
			listDisplayPageGen.setFieldM2nDataproxy(ServletActionContext.getRequest().getParameter("new_field_m2n_dataproxy_"+keyIndex));
			listDisplayPageGen.setFieldM2nRefDisplay(ServletActionContext.getRequest().getParameter("new_field_m2n_ref_display_"+keyIndex));
			listDisplayPageGen.setFieldM2nRefValue(ServletActionContext.getRequest().getParameter("new_field_m2n_ref_value_"+keyIndex));
			eneityFieldListGenDomains.add(listDisplayPageGen);
		}

		param.put("listQueryPageGenDomains",listQueryPageGenDomains);
		param.put("eneityFieldListGenDomains",eneityFieldListGenDomains);
		param.put("newFieldListTitle",newFieldListTitle);
		param.put("newFieldListDataproxy",newFieldListDataproxy);
		
		if (StringUtils.isNotEmpty(newAddNeed)){
			codeGenManager.genSourceCode(modulePath,entityName.substring(0,1).toLowerCase() +entityName.substring(1,entityName.length()).toLowerCase()+"-add.ftl","add",param);
		}
		if (StringUtils.isNotEmpty(viewAddNeed)){
			codeGenManager.genSourceCode(modulePath,entityName.substring(0,1).toLowerCase() +entityName.substring(1,entityName.length()).toLowerCase()+"-view.ftl","view",param);
		}
		if (StringUtils.isNotEmpty(listAddNeed)){
			codeGenManager.genSourceCode(modulePath,entityName.substring(0,1).toLowerCase() +entityName.substring(1,entityName.length()).toLowerCase()+"-query.ftl","query",param);
		}			
		
	}
	private void genJavaSourceCode(){
		String tableName =  ServletActionContext.getRequest().getParameter("tableName");
		String sourcePath = ServletActionContext.getRequest().getParameter("sourcePath");
		String moduleName = ServletActionContext.getRequest().getParameter("moduleName");
		
		String entityName = ServletActionContext.getRequest().getParameter("entityName");
		String entityPath = ServletActionContext.getRequest().getParameter("entityPath");
		String actionnName = ServletActionContext.getRequest().getParameter("actionnName");
		String actionnPath = ServletActionContext.getRequest().getParameter("actionnPath");
		String managerName = ServletActionContext.getRequest().getParameter("managerName");
		String managerPath = ServletActionContext.getRequest().getParameter("managerPath");
		
		String entityFolderPath = codeGenManager.genSourceCodeFolder(sourcePath,entityPath);
		String actionnFolderPath = codeGenManager.genSourceCodeFolder(sourcePath,actionnPath);
		String managerFolderPath = codeGenManager.genSourceCodeFolder(sourcePath,managerPath);
		RequestHandler requestHandler = RequestHandler.getContextRequestHandler();
		Map<String,String> eneityFieldNeed = requestHandler.getParameterMapWithPrefix("eneity_field_need_", "");
		Iterator<String> eneityFieldNeedKey = eneityFieldNeed.keySet().iterator();
		List<EntityGenDomain> entityGenDomains = new ArrayList<EntityGenDomain>();
		while(eneityFieldNeedKey.hasNext()){
			String keyIndex = eneityFieldNeedKey.next();
			EntityGenDomain entityGenDomain = new EntityGenDomain();
			entityGenDomain.setEneityFieldName(ServletActionContext.getRequest().getParameter("eneity_field_name_"+keyIndex));
			entityGenDomain.setEneityColumnName(ServletActionContext.getRequest().getParameter("eneity_column_name_"+keyIndex));
			entityGenDomain.setEntityFieldType(ServletActionContext.getRequest().getParameter("entity_field_type_"+keyIndex));
			String entityFieldRefStr = ServletActionContext.getRequest().getParameter("entity_field_ref_"+keyIndex);
			entityGenDomain.setEntityFieldRef(entityFieldRefStr);
			if (StringUtils.isNotEmpty(entityFieldRefStr) && entityFieldRefStr.indexOf(".") > -1){
				entityGenDomain.setEntityFieldRefShortName(entityFieldRefStr.substring(entityFieldRefStr.lastIndexOf(".")+1,entityFieldRefStr.length()));
			} else {
				entityGenDomain.setEntityFieldRefShortName(entityFieldRefStr);
			}
			entityGenDomain.setEntityFieldRef(ServletActionContext.getRequest().getParameter("entity_field_ref_"+keyIndex));
			entityGenDomain.setEneityColumnJoinName(ServletActionContext.getRequest().getParameter("eneity_column_join_name_"+keyIndex));
			entityGenDomain.setEneityColumnRefName(ServletActionContext.getRequest().getParameter("eneity_column_ref_name_"+keyIndex));
			entityGenDomains.add(entityGenDomain);
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("entityName", entityName);
		param.put("entityGenDomains", entityGenDomains);
		param.put("entityPath", entityPath);
		param.put("tableName", tableName);
		param.put("createDate", DateUtil.parseToString(new Date(), DateUtil.yyyyMMddHHmmss));
		param.put("importPackages", codeGenManager.genImportPackages(entityGenDomains));
		param.put("moduleName", moduleName);
		param.put("actionnPath", actionnPath);
		param.put("actionnName", actionnName);
		param.put("managerPath", managerPath);
		param.put("managerName", managerName);
		codeGenManager.genSourceCode(entityFolderPath,entityName+".java","entity",param);
		codeGenManager.genSourceCode(actionnFolderPath,actionnName+".java","action",param);
		codeGenManager.genSourceCode(managerFolderPath,managerName+".java","manager",param);
	}

}
