/* com.cutty.bravo.core.dao.sql.SQLTemplate.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Mar 2, 2009 2:44:46 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.dao.sql;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.dao.sql.operationWrapper.DeleteSQLWrapper;
import com.cutty.bravo.core.dao.sql.operationWrapper.QuerySQLWrapper;
import com.cutty.bravo.core.dao.sql.operationWrapper.SaveSQLWrapper;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;

/**
 *
 * <p>
 * <a href="SQLTemplate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class SQLTemplate  implements Serializable{
	
	private static final Log logger = LogFactory.getLog(SQLTemplate.class);
	
	private static final long serialVersionUID = 5239028783998641082L;
	
	/*
	 * 结果集，key为”xml文件名.<resultMap>id”，value为ResultMap。
	 */
	private static Map<String,ResultMap> resultMap = new HashMap<String,ResultMap>();
	/*
	 * "xml文件名.<save>id",<save>标签即xml配置文件里的保存标签；value为SaveSQLWrapper;
	 * 存放所有的操作为"保存"的sql语句。
	 */	
	private static Map<String,SaveSQLWrapper> saveSQLMap = new HashMap<String,SaveSQLWrapper>();
	/*
	 * key为"xml文件名.<delete>id", <delete>为xml文件里的删除标签；value为DeleteSQLWrapper对象
	 * 保存所有删除操作的原生sql语句，供调用baseDao时使用。
	 */
	private static Map<String,DeleteSQLWrapper> deleteSQLMap = new HashMap<String,DeleteSQLWrapper>();
	/*
	 * 存放xml文件中所有的保存语句，key为”xml文件名.<query>id” ，value为SaveSQLWrapper
	 */
	private static Map<String,QuerySQLWrapper> querySQLMap = new HashMap<String,QuerySQLWrapper>();
	
	private SQLTemplate(){}
	
	private static SQLTemplate sqlTemplate;
	
	/**
	 * 配置SQLTemplate，读取xml配置文件，构造出resultMap,saveSQLMap, deleteSQLMap, querySQLMap
	 * 
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private static SQLTemplate configuration() throws SAXException, IOException, ParserConfigurationException{
		ServletContext servletContext = ApplicationContextKeeper.getServletContext();
		String sqlTemplatePath = servletContext.getRealPath("/") +ConfigurableConstants.getProperty("sql.template.path","WEB-INF/template/sql/");
		File sqlFiles = new File(sqlTemplatePath);
		if (sqlFiles.isDirectory()) {
			InputStream configStream = null;
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				File[] fileArray = sqlFiles.listFiles();
				for (int i=0;i<fileArray.length;i++){
					if (! fileArray[i].getName().substring(fileArray[i].getName().length()-4, fileArray[i].getName().length()).equalsIgnoreCase(".xml")) continue;
					configStream = new FileInputStream(fileArray[i]);
					Document doc = db.parse(configStream);
					String fileName = fileArray[i].getName().substring(0, fileArray[i].getName().length()-4);
					if (null != doc){ 
						Element root = doc.getDocumentElement();
						paserResultMap(fileName,root);
						paserSaveSQLMap(fileName,root);
						paserDeleteSQLMap(fileName,root);
						paserQuerySQLMapSQLMap(fileName,root);
					}
				} 
			}catch (FileNotFoundException e) {
				logger.error(e);
				throw e;
			}catch (SAXException e) {
				logger.error(e);
				throw e;
			} catch (IOException e) {
				logger.error(e);
				throw e;
			} catch (ParserConfigurationException e) {
				logger.error(e);
				throw e;
			}finally{
				if (null != configStream){
					try {
						configStream.close();
						configStream = null;
					} catch (IOException e) {
						logger.error(e);
						configStream = null;
					}
				}
			}
		}

		return sqlTemplate;
	}

	public static SQLTemplate getTemplate() throws SAXException, IOException, ParserConfigurationException{
		if (null != sqlTemplate && Boolean.valueOf(ConfigurableConstants.getProperty("sql.use.cache","true"))) {
			return sqlTemplate;
		}
		sqlTemplate = new SQLTemplate();
		SQLTemplate.configuration();
		return sqlTemplate;
	}
	/**
	 * 解析<resultmap>标签，映射成ResultMap对象
	 * @param fileName xml文件名,多个文件存在时该函数可能被多次调用.
	 * @param root xml文件中的根元素
	 */
	private static void paserResultMap(String fileName,Element root){
		NodeList resultMaps = root.getElementsByTagName("resultMap");
		for (int i=0;i<resultMaps.getLength();i++){
			ResultMap currentResultMap = new ResultMap();
			Element  resultMapElement = (Element)resultMaps.item(i);
			String id = fileName + "."+ resultMapElement.getAttribute("id");
			String entityClass = resultMapElement.getAttribute("entityClass");
			currentResultMap.setId(id);
			currentResultMap.setEntityClass(entityClass);
			NodeList results = resultMapElement.getElementsByTagName("result");
			for (int j=0;j<results.getLength();j++){
				Element  result = (Element)results.item(j);
				String property = result.getAttribute("property");
				String column = result.getAttribute("column").toUpperCase().trim();
				String loadEntity = result.getAttribute("loadEntity");
				Result currentResult = new Result();
				currentResult.setColumn(column);
				currentResult.setProperty(property);
				if (StringUtils.isNotEmpty(loadEntity) && "true".equalsIgnoreCase(loadEntity)){
					currentResult.setLoadEntity(true);
				} else {
					currentResult.setLoadEntity(false);
				}
				currentResultMap.getResultProperty().put(column, currentResult);
			}
			resultMap.put(id, currentResultMap);
		}
		System.out.println(resultMap.size());
	}

	/**
	 * 解析<delete>标签，映射成deleteSQLMap对象
	 * @param fileName xml文件名,多个文件存在时该函数可能被多次调用.
	 * @param root xml文件中的根元素
	 */
	private static void paserDeleteSQLMap(String fileName,Element root){
		NodeList deleteElements = root.getElementsByTagName("delete");
		for (int i=0;i<deleteElements.getLength();i++){
			DeleteSQLWrapper deleteSQLWrapper = new DeleteSQLWrapper();
			Element  deleteElement = (Element)deleteElements.item(i);
			String id = fileName + "."+ deleteElement.getAttribute("id");
			String deleteSQL = deleteElement.getTextContent();
			deleteSQLWrapper.setId(id);
			deleteSQLWrapper.setSqlStr(deleteSQL);
			deleteSQLMap.put(id, deleteSQLWrapper);
		}
	}
	/**
	 * 解析<save>标签，映射成saveSQLMap对象
	 * @param fileName xml文件名,多个文件存在时该函数可能被多次调用
	 * @param root xml文件中的根元素
	 */
	private static void paserSaveSQLMap(String fileName,Element root){
		NodeList savesElements = root.getElementsByTagName("save");
		for (int i=0;i<savesElements.getLength();i++){
			SaveSQLWrapper saveSQLWrapper = new SaveSQLWrapper();
			Element  savesElement = (Element)savesElements.item(i);
			String id = fileName + "."+ savesElement.getAttribute("id");
			String saveSQL = savesElement.getTextContent();
			saveSQLWrapper.setId(id);
			saveSQLWrapper.setSqlStr(saveSQL);
			saveSQLMap.put(id, saveSQLWrapper);
		}
	}
	/**
	 * 解析<query>标签，映射成querySQLMap对象
	 * @param fileName xml文件名,多个文件存在时该函数可能被多次调用
	 * @param root xml文件中的根元素
	 */
	private static void paserQuerySQLMapSQLMap(String fileName,Element root){
		NodeList queryElements = root.getElementsByTagName("query");
		for (int i=0;i<queryElements.getLength();i++){
			QuerySQLWrapper querySQLWrapper = new QuerySQLWrapper();
			Element queryElement = (Element)queryElements.item(i);
			String id = fileName + "." + queryElement.getAttribute("id");
			querySQLWrapper.setId(id);
			if (!StringUtils.isEmpty(queryElement.getAttribute("resultMap"))){
				String resultMapId = fileName + "." + queryElement.getAttribute("resultMap");
				querySQLWrapper.setResultMap(resultMap.get(resultMapId));
			}
			if (!StringUtils.isEmpty(queryElement.getAttribute("resultClass"))){
				String resultClass = queryElement.getAttribute("resultClass");
				querySQLWrapper.setResultClass(resultClass);
			}
			String queryStr =  queryElement.getTextContent();
			querySQLWrapper.setQueryStr(queryStr);
			querySQLMap.put(id, querySQLWrapper);
		}
	}
	
	public QuerySQLWrapper getQueryWrapperById(String queryId){
		return querySQLMap.get(queryId);
	}
	
	//liangg 09.03.06 
	public SaveSQLWrapper getSaveSQLWrapperById(String saveId){
		return saveSQLMap.get(saveId);
	}
	
	//liangg 09.03.06
	public DeleteSQLWrapper getDeleteSQLWrapperById(String deleteId){
		return deleteSQLMap.get(deleteId);
	}
}
