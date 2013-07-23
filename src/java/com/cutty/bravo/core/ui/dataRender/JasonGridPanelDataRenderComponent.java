/* com.cutty.bravo.core.ui.dataRender.JasonGridPanelDataRenderComponent.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Sep 18, 2008 3:32:33 PM, Created by kukuxia.hw
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.dataRender;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.utils.render.FreemarkerTemplateEngine;
import com.cutty.bravo.core.web.handler.RequestHandler;

/**
* <p> 返回Grid所需数据格式的json渲染器 </p>
 * <p>
 * <a href="JasonGridPanelDataRenderComponent.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:huangw100@126.com">kukuxia.hw</a>
 * History 
 *   2008-09-22 kukuxia.hw 实现页面上的分页显示。
 */
public class JasonGridPanelDataRenderComponent implements DataRenderComponent{
	private static final Log logger = LogFactory.getLog(JasonGridPanelDataRenderComponent.class);

    /**
     * 实现 DataRenderComponent 接口 rend(HttpServletRequest request, HttpServletResponse response)方法
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException
     * @throws ServletException
     * @return json格式返回信息 String
     */
	public String rend(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		StringBuffer jsonDataSB = new StringBuffer("");
		String contextDataName = request.getParameter("contextDataName");
		String contextDataFieldName = request.getParameter("contextDataFieldName");
		//分解contextDataFieldName 其结构类似于“id_@_name”
		String[] fields = contextDataFieldName.split("_@_");
		//判断contextDataName是否存在查询参数中，如果不存在,则直接从request attribute中获取.
		if (StringUtils.isEmpty(contextDataName)){
			contextDataName = (String) request.getAttribute("contextDataName");
		}
		if (StringUtils.isEmpty(contextDataName)) throw new ServletException("you shold defined the contextDataName!!!\n你必须定义contextDataName");

		ArrayList contextData = (ArrayList)request.getAttribute(contextDataName);
		String totalCount = (String)request.getAttribute("totalCount");
		if (StringUtils.isEmpty(totalCount)){
			totalCount = String.valueOf(contextData.size());
		}
		//totalCount需要设置成查询总条数jason 2008-10-10
		jsonDataSB.append("{\"totalCount\":\"" + totalCount + "\"," +"\""+contextDataName+"\"" + ":[");
		if (null != contextData){
			Iterator contextDataIT = contextData.iterator();
			boolean hasNotBeenRenderDate = true;
			while (contextDataIT.hasNext()){
				if (hasNotBeenRenderDate){
					hasNotBeenRenderDate = false;
				} else {
					jsonDataSB.append(",");
				}
				Object value = contextDataIT.next();
				jsonDataSB = dateConvToJson(fields, value, jsonDataSB);			                  
			}
			jsonDataSB.append("]}");
	} else {
		logger.error("you should define the contextDataName at the parameter!!!\n你必须在请求参数中定义contextDataName!!!");
		throw new ServletException("you should define the contextDataName at the parameter!!!\n你必须在请求参数中定义contextDataName!!!");
	}

		return jsonDataSB.toString();
  }
	
	
 /**以下方法是将数据组合成GridPanle需要的Json的格式中循环的部分
  * Json格式类似于：{"totalCount":"2","root":
  * [
  * {"id":"1","name":"name1","resString":"s1","comments":""},
  * {"id":"2","name":"name2","resString":"s2","comments":""}
  * ]}
  * @param fields String[]
  * @param value Object
  * @param jsonDataSB StringBuffer
  * @return StringBuffer
  * 
  */
	public StringBuffer dateConvToJson(String[] fields, Object value, StringBuffer jsonDataSB){
		String displayString = "";
		jsonDataSB.append( "{" );
		for(int i=0;i<fields.length;i++){
			//将所有---置换为.符合javaBean格式.jason 2008-10-8
			String dataIndexName = fields[i];
			//String filedName = fields[i].replace("___", ".");
			String filedName = fields[i];
			//如果参数中带"_@@_"则表明该列存在自定义值需要做特殊处理.jason 2008-10-8
			if (filedName.indexOf("_@@_") > -1){
				String[] filedNameAarray = filedName.split("_@@_");
				dataIndexName = filedNameAarray[0];
				String currentContextDataFieldValueKey = filedNameAarray[1];
				Map<String,Object> context = new HashMap<String,Object>();
				context.put("rowValue", value);
				context.put("requestHandler", RequestHandler.getContextRequestHandler());
				FreemarkerTemplateEngine freemarkerTemplateEngine = FreemarkerTemplateEngine.getInstance();
				currentContextDataFieldValueKey = changeText2FreemarkerSyntax(currentContextDataFieldValueKey);
				displayString = freemarkerTemplateEngine.renderFtl(ApplicationContextKeeper.getServletContext(), "<#escape x as (x)!>"+currentContextDataFieldValueKey.replace("@{", "${")+"</#escape>",context);
			} else {
				filedName = fields[i].replace("___", ".");
				try {
					Object propertyValue = PropertyUtils.getProperty(value, filedName);
					if (propertyValue instanceof Date){
						displayString = (new SimpleDateFormat(ConfigurableConstants.getProperty("convert.format.date","yyyy-MM-dd"))).format((Date)propertyValue);
					} else {
						displayString = org.springframework.web.util.JavaScriptUtils.javaScriptEscape(propertyValue.toString());
					}
				} catch(Exception e) {
					displayString = null;//kukuxia.kevin 2008-10-31
					logger.warn(e);
				}
				if (null == displayString) displayString = "";
			}
			
			if(i<fields.length-1){
				jsonDataSB.append( "\"" + dataIndexName +"\"" +":"+ "\"" + displayString + "\","); 	
			}else{
				jsonDataSB.append( "\"" + dataIndexName +"\"" +":"+ "\"" + displayString + "\"");	
			}
		}
		jsonDataSB.append( "}" );
		return jsonDataSB;
	} 	
	
	//使GridPanel列的value值可以使用<#if></#if>这样的Freemarker表达式。在页面使用时的格式是<$if></$if>
	private String changeText2FreemarkerSyntax(String str) {
		if (str.indexOf("<$") >= 0)
			str = str.replaceAll("<\\$", "<\\#");
		if (str.indexOf("</$") >= 0)
			str = str.replaceAll("</\\$", "</\\#");
		return str;
	}
}
