/* com.cutty.bravo.core.utils.generator.Generator.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-12-4 上午10:32:40, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.utils.generator;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.type.Type;

import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.dao.impl.HibernateBaseDao;
import com.cutty.bravo.core.domain.DataDictionary;
import com.cutty.bravo.core.domain.DataDictionaryDetail;
import com.cutty.bravo.core.ui.tags.form.component.FieldBean;
import com.cutty.bravo.core.ui.tags.form.component.PopuSelectBean;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.utils.render.FreemarkerTemplateEngine;
import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.util.ResolverUtil;

/**
 *
 * <p>
 * <a href="Generator.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class Generator {
	
	protected static final Log logger = LogFactory.getLog(Generator.class);
	
	/**
	 * 该函数用于自动生成页面
	 * @param path -要产生页面的路径
	 * @param templateName -模板名称
	 * @param entityName -相关的实体名
	 */
	public static void generatePage(String pathPrefix,String fileName,String templateName,Class entityClass,Configuration configuration){
		Map pageContextMap = new HashMap();
		String entitySimpleName = entityClass.getSimpleName();
		String pageName = entitySimpleName+"_"+templateName;
		String entityName = entityClass.getName();
		String allLowerCaseSimpleName = entitySimpleName.substring(0,1).toLowerCase()+entitySimpleName.substring(1);
		
		/**
		 * 获取所有设置了Namespace的Action类，用于在后面直接用字段的名字来匹配获得其对应类的相关信息
		 */
	     ResolverUtil<Object> resolver = new ResolverUtil<Object>();
	     Map<String,String> actionMap = new HashMap<String,String>();
	     resolver.findAnnotated(Namespace.class,ConfigurableConstants.getProperty("entity.package.name", "com"));
	     Set<Class<? extends Object>> valueObjectSet = resolver.getClasses();
	     for(Iterator<Class<? extends Object>> iterator = valueObjectSet.iterator();iterator.hasNext();){
	    	 Class<? extends Object> action = iterator.next();
	 	     PackageConfig pkg = configuration.getPackageConfig(action.getName());
	 	     actionMap.put(action.getSimpleName(), pkg.getNamespace());
	     }		
		pageContextMap.put("pageName", pageName);
		pageContextMap.put("pageTitle", pageName);
		pageContextMap.put("formName", entitySimpleName+"_"+templateName);
		pageContextMap.put("gridName", entitySimpleName+"_grid");
		pageContextMap.put("contextDataName",entityClass.getSimpleName()+"s");
		  String gridDataProxy = "./"+allLowerCaseSimpleName+"!findAndRendJsonData.action";
		pageContextMap.put("gridDataProxy", gridDataProxy);
		pageContextMap.put("formTitle",entitySimpleName+"_title");
		pageContextMap.put("gridTitle",entitySimpleName+"_title");
		  String viewPageRedirect = "./"+allLowerCaseSimpleName+"!view.action?id=@{rowValue.id?c}";
		pageContextMap.put("viewPageRedirect",viewPageRedirect);
		  String addActionUrl = "./"+allLowerCaseSimpleName+"!add.action";
		pageContextMap.put("addActionUrl",addActionUrl);
		  String removeActionUrl = "./"+allLowerCaseSimpleName+"!batchRemove.action";
		pageContextMap.put("removeActionUrl",removeActionUrl);
			String saveActionUrl = "./"+allLowerCaseSimpleName+"!save.action";
		pageContextMap.put("saveActionUrl", saveActionUrl);
		
		
		HibernateBaseDao baseDao = (HibernateBaseDao)ApplicationContextKeeper.getAppCtx().getBean("baseDao");
		SessionFactoryImplementor sessionFactoryImplementor = (SessionFactoryImplementor)baseDao.getSessionFactoryFormHibernateTemplate();
		if (null == sessionFactoryImplementor) throw new XWorkException("sessionFactory of hibernate not found,!\n找不到sessionFactory!!!");
		//获取数据字典,根据数据字典生成页面
		DataDictionary dataDictionary = baseDao.findUniqueBy(DataDictionary.class, "entityName", entityClass.getName(),true);
		if (null != dataDictionary){
			Set<DataDictionaryDetail> dataDictionaryDetails =dataDictionary.getDataDictionaryDetails();
		} else {
			
		}

		EntityPersister meta = sessionFactoryImplementor.getEntityPersister(entityClass.getName());
		String[] propertyNames = meta.getPropertyNames();
		Type[] propertyTypes = meta.getPropertyTypes();
		propertyNames = (String[])ArrayUtils.add(propertyNames, meta.getIdentifierPropertyName());
		propertyTypes = (Type[])ArrayUtils.add(propertyTypes, meta.getIdentifierType());
		List<String> propertyNamesList = new ArrayList<String>();
		List<String> propertyTypesList = new ArrayList<String>();
		FieldBean fieldBean = null;
		PopuSelectBean popuSelectBean = null;
		List<FieldBean> propertyList = new ArrayList<FieldBean>();
		int formComponentNum = 0;
		for (int i=0;i<propertyNames.length;i++){
			if (! propertyTypes[i].isAssociationType()){
				fieldBean = new FieldBean(); 
				fieldBean.setName(propertyNames[i]);
				fieldBean.setFieldLabel(propertyNames[i]);
				String returnType = propertyTypes[i].getReturnedClass().getSimpleName();
				if ("Date".equals(returnType)){
					fieldBean.setXtype("DateField");
					formComponentNum++;
				} else if ("Number".equals(returnType)) {
					fieldBean.setXtype("NumberField");
				}else {
					fieldBean.setXtype("TextField");
				}
				formComponentNum++;
				propertyList.add(fieldBean);
			} else if (!propertyTypes[i].isCollectionType()){
				String returnType = propertyTypes[i].getReturnedClass().getSimpleName();
				if ("Enumeration".equals(returnType)){
					fieldBean = new FieldBean(); 
					fieldBean.setName(propertyNames[i]+".id");
					fieldBean.setFieldLabel(propertyNames[i]+".id");
					fieldBean.setXtype("ComboBox");
					propertyList.add(fieldBean);
				} else {
					popuSelectBean = new PopuSelectBean();
					popuSelectBean.setName(propertyNames[i]+".name");
					popuSelectBean.setFieldLabel(propertyNames[i]+".name");
					popuSelectBean.setXtype("PopuSelect");
					popuSelectBean.setHiddenName(propertyNames[i]+".id");
					popuSelectBean.setTargetGridName(returnType+"_grid");
					popuSelectBean.setText(propertyNames[i]+"_select");
					//获得该字段类型（一般是bean）对应的Action类
					String actionName = returnType+"Action";
					//获得其对应的表空间
					String actionNamespace = actionMap.get(actionName);
					String targetProxy = ".."+actionNamespace+"/"+returnType.substring(0,1).toLowerCase()+returnType.substring(1)+"!query.action";
					popuSelectBean.setTargetProxy(targetProxy);
					propertyList.add(popuSelectBean);
				}
				formComponentNum++;
			}
		}
		pageContextMap.put("propertyList",propertyList);
		String fieldSetCols = "";
		String fieldSetRows = "";
		int propertyNamesMod = formComponentNum % 3;
		for (int i=0;i<formComponentNum-propertyNamesMod;i+=3){
			fieldSetCols += "1,1,1;";
			fieldSetRows += "1,1,1;";
		}
		if(propertyNamesMod==1){
			fieldSetCols += "3;";
			fieldSetRows += "1;";		
		}else if((propertyNamesMod==2)){
			fieldSetCols += "1,2;";
			fieldSetRows += "1,1;";				
		}
		pageContextMap.put("fieldSetCols",fieldSetCols);
		pageContextMap.put("fieldSetRows",fieldSetRows);
		ServletContext servletContext = ApplicationContextKeeper.getServletContext();
		String templatePath = servletContext.getRealPath("/") +ConfigurableConstants.getProperty("sql.template.path","WEB-INF/template/page/")+templateName+".ftl";
    	StringBuffer sb = new StringBuffer("");
        String str;
        try {
        	BufferedReader in = new BufferedReader(new FileReader(templatePath));
			while ((str = in.readLine()) != null) {
				str = str+"\n";
				sb.append(str);
			}
	        in.close();
		} catch (IOException e) {
			logger.warn(e);
		}
    	
    	
    	FreemarkerTemplateEngine freemarkerTemplateEngine = FreemarkerTemplateEngine.getInstance();
    	
    	String outPutSB = freemarkerTemplateEngine.renderFtl(ApplicationContextKeeper.getServletContext(), "<#escape x as (x)!>"+sb.toString()+"</#escape>",pageContextMap);
    	outPutSB = outPutSB.replace("_bravo", "@bravo");
    	outPutSB = outPutSB.replace("___", ".");
    	try {
    		String fileDirPath = ServletActionContext.getRequest().getRealPath("/")+pathPrefix;
            File fileDir = new File(fileDirPath);
            if(!fileDir.exists()){
            	fileDir.mkdir();
            }
            String filePath = fileDirPath + fileName;
			PrintWriter out = new PrintWriter(new File(filePath), "utf-8");
			out.println(outPutSB.toString());
			out.close();
			out = null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(outPutSB);
	}
}
