/* com.cutty.bravo.core.ui.dataRender.JasonComboBoxDataRenderComponent.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Sep 12, 2008 3:27:59 PM, Created by kukuxia.hw
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.dataRender;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p> 返回ComboBox所需数据格式的json渲染器 </p>
 * <p>
 * <a href="JasonComboBoxDataRenderComponent.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:huangw100@126.com">kukuxia.hw</a>
 */
public class JasonComboBoxDataRenderComponent implements DataRenderComponent {
	private static final Log logger = LogFactory.getLog(JasonComboBoxDataRenderComponent.class);

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
		String jsonDataSBString = "";
		String contextDataName = request.getParameter("contextDataName");
		String contextDataFieldName = request.getParameter("contextDataFieldName");
		String displayField = "";
		String valueField = "";
		//分解contextDataFieldName 其结构类似于“id_@_name”
		String[] Fields = contextDataFieldName.split("_@_");
		valueField = Fields[0];
		displayField = Fields[1];
		if (StringUtils.isNotEmpty(contextDataName)){
			jsonDataSB.append("{\"ComboBox_Data_Root\":[");
			//按照传来的contextDataName在request取出相应的数据组
			List contextData = (List)request.getAttribute(contextDataName);
			boolean hasNotPaserData = true;
			if (null != contextData){
				Iterator contextDataIT = contextData.iterator();
				while (contextDataIT.hasNext()){
					Object value = contextDataIT.next();
					String displayString = "";
					String valueString = "";
					try {
						//BeanUtils 用来在对象中取出对应的属性的值
						displayString = BeanUtils.getProperty(value, displayField);
						valueString = BeanUtils.getProperty(value, valueField);
					} catch (IllegalAccessException e) {
						
					} catch (InvocationTargetException e) {
						
					} catch (NoSuchMethodException e) {
						
					}
					jsonDataSB.append( "{" );
					jsonDataSB.append( "\"" + valueField +"\"" +":"+ "\"" + valueString + "\","); 
					jsonDataSB.append( "\"" + displayField +"\"" +":"+ "\"" + displayString + "\""); 
					jsonDataSB.append( "}," );
					                  
				}
			    jsonDataSBString = (jsonDataSB.toString().substring(0,jsonDataSB.length()-1));
			    jsonDataSBString += "]}";
    		}
		} else {
    		logger.error("you should define the contextDataName at the parameter!!!\n你必须在请求参数中定义contextDataName!!!");
    		throw new ServletException("you should define the contextDataName at the parameter!!!\n你必须在请求参数中定义contextDataName!!!");
    	}
		return jsonDataSBString;
	}

}
