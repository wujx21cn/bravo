/* com.cutty.bravo.core.ui.filter.dataRender.DataRender.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-1 上午07:33:35, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.dataRender;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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


/**
 * <p> 根据DataRender.xml配置调用对应的json渲染器 </p>
 * <p>
 * <a href="DataRender.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class DataRender {
	
	private static final Log logger = LogFactory.getLog(DataRender.class);
	private static DataRender dataRenderFactory;
	private Map<String,String> matchParameterMap = new HashMap<String,String>();
	private Map<String,String> matchAttributeMap = new HashMap<String,String>();
	private Map<String,DataRenderComponent> renderObjectMap = new HashMap<String,DataRenderComponent>();
	/**
	 * Constructor
	 */
	private DataRender(){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		InputStream configStream = this.getClass().getResourceAsStream("DataRender.xml");
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			logger.error(e);
		}
		Document doc = null;
		try {
			doc = db.parse(configStream);
		} catch (SAXException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		if (null != doc){
			Element root = doc.getDocumentElement();
			NodeList jsonRenders = root.getElementsByTagName("jsonRender");
			for (int i=0;i<jsonRenders.getLength();i++){
				Element  jsonRender = (Element)jsonRenders.item(i);
				String classStr = jsonRender.getAttribute("class");
				NodeList matchParameterElements = jsonRender.getElementsByTagName("matchParameter");
				NodeList matchAttributes = jsonRender.getElementsByTagName("matchAttribute");
				if (0 == matchParameterElements.getLength() && 0 == matchAttributes.getLength()) continue;
				
				Element matchParameterElement = (Element)matchParameterElements.item(0);
				if (null != matchParameterElement) {
					String matchParameterName = matchParameterElement.getAttribute("name");
					String matchParameterValue = matchParameterElement.getAttribute("value");
					if (StringUtils.isNotEmpty(classStr)
								&& StringUtils.isNotEmpty(matchParameterName) && StringUtils.isNotEmpty(matchParameterValue)){
						try {
							Class jsonRenderClass = Class.forName(classStr);
							DataRenderComponent jsonDataRenderObject = (DataRenderComponent)jsonRenderClass.newInstance();
							renderObjectMap.put(matchParameterName, jsonDataRenderObject);
							matchParameterMap.put(matchParameterName, matchParameterValue);
						} catch (ClassNotFoundException e) {
							logger.error(e);
							continue;
						} catch (InstantiationException e) {
							logger.error(e);
							continue;
						} catch (IllegalAccessException e) {
							logger.error(e);
							continue;
						}
						
					}
				}
				
				Element matchAttributeElement = (Element)matchAttributes.item(0);
				if (null != matchAttributeElement){
					String matchAttributeName = matchAttributeElement.getAttribute("name");
					String matchAttributeValue = matchAttributeElement.getAttribute("value");
					if (StringUtils.isNotEmpty(classStr)
								&& StringUtils.isNotEmpty(matchAttributeName) && StringUtils.isNotEmpty(matchAttributeValue)){
						try {
							Class jsonRenderClass = Class.forName(classStr);
							DataRenderComponent jsonDataRenderObject = (DataRenderComponent)jsonRenderClass.newInstance();
							renderObjectMap.put(matchAttributeName, jsonDataRenderObject);
							matchAttributeMap.put(matchAttributeName, matchAttributeValue);
						} catch (ClassNotFoundException e) {
							logger.error(e);
							continue;
						} catch (InstantiationException e) {
							logger.error(e);
							continue;
						} catch (IllegalAccessException e) {
							logger.error(e);
							continue;
						}
						
					}
				}
			}
		}
		if (null != configStream){
			try {
				configStream.close();
			} catch (IOException e) {
				logger.error(e);
			} finally{
				configStream = null;
			}
		}

	}
	
    /**
     * 根据DataRender.xml配置调用对应的json渲染器类
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException
     * @throws ServletException
     * @return DataRender对象
     */
	public String rend(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

		Iterator<String> matchParameterIT = matchParameterMap.keySet().iterator();
		Iterator<String> matchAttributeIT = matchAttributeMap.keySet().iterator();
		StringBuffer sb = new StringBuffer("");
		while (matchParameterIT.hasNext()){
			String matchParameterName = matchParameterIT.next();
			if (StringUtils.isNotEmpty(request.getParameter(matchParameterName)) 
					    && matchParameterMap.get(matchParameterName).equalsIgnoreCase(request.getParameter(matchParameterName))){
				DataRenderComponent jsonDataRenderObject = renderObjectMap.get(matchParameterName);
				sb.append(jsonDataRenderObject.rend(request, response)) ;
			}
		}
		while (matchAttributeIT.hasNext()){
			String matchAttributeName = matchAttributeIT.next();
			if (StringUtils.isNotEmpty((String)request.getAttribute(matchAttributeName)) 
						&& matchAttributeMap.get(matchAttributeName).equalsIgnoreCase((String)request.getAttribute(matchAttributeName))){
				DataRenderComponent jsonDataRenderObject = renderObjectMap.get(matchAttributeName);
				sb.append(jsonDataRenderObject.rend(request, response)) ;
			}
		}
		return sb.toString();
	}
	
	public static DataRender getInstance(){
		if (null == dataRenderFactory){
			dataRenderFactory = new DataRender();
		}
		return DataRender.dataRenderFactory;
	}	
}
