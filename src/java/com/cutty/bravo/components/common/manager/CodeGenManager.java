/* com.cutty.bravo.components.common.manager.CodeGenManager.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2011-7-1 下午05:33:24, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.manager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.cutty.bravo.components.common.domain.CodeGenDomain;
import com.cutty.bravo.components.common.domain.EntityGenDomain;
import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.utils.FileUtils;
import com.cutty.bravo.core.utils.render.FreemarkerTemplateEngine;
import com.cutty.bravo.core.web.handler.RequestHandler;

/**
 *
 * <p>
 * <a href="CodeGenManager.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Service("codeGenManager")
public class CodeGenManager {
	protected final Log logger = LogFactory.getLog(getClass());
	protected BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	public List<String> getDataBaseTables(){
		ResultSet rs = null;
		List<String> tableNames = new ArrayList<String>();
		try {
			Connection conn = baseDao.getHibernate().getSessionFactory().getCurrentSession().connection();
			DatabaseMetaData meta = conn.getMetaData();
			rs = meta.getTables(null, null, null, new String[]{"TABLE"});
		   while (rs.next()) {
			      String tableName = rs.getString("TABLE_NAME");
			      tableNames.add(tableName);
			    }
		} catch (SQLException e) {
			logger.error("获取表结构异常！！！",e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("获取表结构异常！！！",e);
			}
			rs = null;
		}
		return tableNames;
	}
	public List<CodeGenDomain> getTableStuct(String tableName){
		List<CodeGenDomain> tableStuct = new ArrayList<CodeGenDomain>();
		Connection conn = baseDao.getHibernate().getSessionFactory().getCurrentSession().connection();
		try {
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet columnSet = meta.getColumns(null, "%", tableName, "%");
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + tableName);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData(); 
			int fieldNum=1;
			while(columnSet.next()){
				CodeGenDomain codeGenDomain = new CodeGenDomain();
				String columnName  = columnSet.getString("COLUMN_NAME");
				if ("id".equals(columnName)){
					//忽略
					fieldNum++;
					continue;
				}
				String fieldType =rsmd.getColumnClassName(fieldNum);
				System.out.println(fieldType);
				if ("java.math.BigDecimal".equals(fieldType)) {
					codeGenDomain.setFieldType(CodeGenDomain.FIELD_TYPE_MANY_2_ONE);
				} else if ("java.lang.Double".equals(fieldType)){
					codeGenDomain.setFieldType(CodeGenDomain.FIELD_TYPE_DOUBLE);
				} else if ("java.lang.Float".equals(fieldType)){
					codeGenDomain.setFieldType(CodeGenDomain.FIELD_TYPE_FLOAT);
				}  else if ("java.sql.Date".equals(fieldType)){
					codeGenDomain.setFieldType(CodeGenDomain.FIELD_TYPE_DATE);
				} else if ("java.lang.Integer".equals(fieldType)){
					codeGenDomain.setFieldType(CodeGenDomain.FIELD_TYPE_INT);
				} else {
					codeGenDomain.setFieldType(CodeGenDomain.FIELD_TYPE_STRING);
				}
				codeGenDomain.setColumnName(columnName);
				String columnComment = columnSet.getString("REMARKS");
				codeGenDomain.setLabelName(columnComment);
				String fieldName = columnName;
				if (columnName.indexOf("_")>0){
					String[] columnNameArr = columnName.split("_");
					fieldName = columnNameArr[0];
					for (int i=1;i<columnNameArr.length;i++){
						fieldName = fieldName +columnNameArr[i].substring(0,1).toUpperCase() +columnNameArr[i].substring(1,columnNameArr[i].length());
					}
				}
				codeGenDomain.setFieldName(fieldName);
				tableStuct.add(codeGenDomain);
				fieldNum++;
			}
		} catch (SQLException e) {
			logger.error("获取表结构异常！！！",e);
		}
		return tableStuct;

	}
	public List<String> getEntityNames(){
		Map meta = baseDao.getHibernate().getSessionFactory().getAllClassMetadata();
		List<String> entityNameList = new ArrayList<String>();
		entityNameList.addAll(meta.keySet());
		Collections.sort(entityNameList);
		return entityNameList;
	}
	
	public String genSourceCodeFolder(String sourcePath,String packagePath){
		String sourceFileFolder = sourcePath + "\\" + packagePath.replaceAll("\\.","\\\\");
		FileUtils.createFolders(sourceFileFolder);
		return sourceFileFolder;

	}
	public Set<String> genImportPackages(List<EntityGenDomain> entityGenDomains){
		Set<String> importPackages = new HashSet<String>();
		for (EntityGenDomain entityGenDomain:entityGenDomains){
			if (CodeGenDomain.FIELD_TYPE_DATE.equals(entityGenDomain.getEntityFieldType())){
				importPackages.add("java.util.Date");
			} else if (CodeGenDomain.FIELD_TYPE_MANY_2_ONE.equals(entityGenDomain.getEntityFieldType())){
				importPackages.add(entityGenDomain.getEntityFieldRef());
				importPackages.add("javax.persistence.JoinColumn");
				importPackages.add("javax.persistence.ManyToOne");
			}
		}
		return importPackages;
	}
	public void genSourceCode(String path,String fileName,String templateName,Map context){
		String templatePath = RequestHandler.getContextRequestHandler().getRequest().getRealPath("/")+"/WEB-INF/template/codeGen/"+templateName+".ftl";
		String ftlString = getTemplateStringByPath(templatePath);
		FreemarkerTemplateEngine freemarkerTemplateEngine = FreemarkerTemplateEngine.getInstance();
		String entityStr = decodeRegex(freemarkerTemplateEngine.renderFtl(ApplicationContextKeeper.getServletContext(), "<#escape x as (x)!>"+encodeRegex(ftlString)+"</#escape>",context));
		entityStr = entityStr.replaceAll("\\<\\^bravo","\\<@bravo");
		entityStr = entityStr.replaceAll("\\</\\^bravo","\\</@bravo");
		try {
			FileUtils.writeStr2File(path+"\\"+fileName, entityStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

    private String getTemplateStringByPath(String templatePath) {
    	StringBuffer sb = new StringBuffer("");
        String str;
        FileInputStream fi = null;
        BufferedReader in = null;
        try {
        	fi = new FileInputStream(templatePath);
        	in = new BufferedReader(new InputStreamReader(fi,"UTF-8"));
			while ((str = in.readLine()) != null) {
				str = str+"\n";
				sb.append(str);
			}
		} catch (IOException e) {
			logger.warn(e);
		} finally {
			if (null != fi){
				try {
					fi.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
			if (null != in){
				try {
					in.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
    	return sb.toString();
    }
    
    private String encodeRegex(String str) {
			if (str.indexOf("*") >= 0)
				str = str.replaceAll("\\*", "#-#");
			return str;
		}
    private String decodeRegex(String str) {
		if (str.indexOf("#-#") >= 0)
			str = str.replaceAll("#-#", "*");
		return str;
	}
}
