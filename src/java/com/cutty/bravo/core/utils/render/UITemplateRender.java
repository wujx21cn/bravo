/* com.cutty.bravo.core.utils.UITemplateRender.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-22 上午02:10:03, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.utils.render;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.web.handler.RequestHandler;



/**
 *
 * <p>
 * <a href="UITemplateRender.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class UITemplateRender {
	
	private static final Log logger = LogFactory.getLog(UITemplateRender.class);
	
	private CacheManager cacheManager ;
	
	private static UITemplateRender templateUtils;
	
	private UITemplateRender(){
		cacheManager = CacheManager.getInstance();
	}
	
    public static UITemplateRender getInstance(){
		if (null == templateUtils){
			templateUtils = new UITemplateRender();
		}
		return templateUtils;
	}
    
    /**
     * 获取当前登陆人的UI 主题,如果当前SESSION没有登陆，或者没有设置界面主题,则采用系统默认配置,
     * @return
     */
    private String getUITheme(){
    	String uiTheme = ConfigurableConstants.getProperty("ui.theme","extjs");
    	RequestHandler requestHandler = RequestHandler.getContextRequestHandler();
    	if (null != requestHandler
    				&& null != requestHandler.getSessionAttribute("uiTheme"))
    		uiTheme = (String)requestHandler.getSessionAttribute("uiTheme");
    	return uiTheme;
    }
	/**
	 * 根据当前session获取模板缓存,如果session中不存在该key,则由配置文件获取.
	 * @return
	 */
    private Cache getCache(){
    	String cacheName = getUITheme() + "TemplateFileCache" ;
    	Cache cache =  cacheManager.getCache(cacheName);
    	if (null == cache) {
    		Cache templateCache = new Cache(cacheName, 10000, true, true, 7200, 3600);   
    		cacheManager.addCache(templateCache);
    		cache =  cacheManager.getCache(cacheName);
    	}
    	return cache;
    }
    
	/**
	 * 根据模板文件名获取模板文件内容
	 * @param templateName
	 */
    private String getExtTemplateString(String templateName) {
    	RequestHandler requestHandler = RequestHandler.getContextRequestHandler();
    	//获得是否使用UI模板缓存的配置
    	String useCache = ConfigurableConstants.getProperty("ui.template.cache","false");
    	//获得ext标签模板的绝对路径
    	  //requestHandler.getRequest().getRealPath("/"): 返回虚拟路径"/"在服务器文件系统中的绝对路径,即web应用根目录的绝对路径
    	  //ConfigurableConstants.getProperty("extjs.template.path","/WEB-INF/themes/"):返回UI模板存放的路径
    	  //getUITheme():返回当前UI使用主题所在的文件夹名称
    	  //templateName+".ftl"：返回标签模板完整的文件名
    	String templatePath = requestHandler.getRequest().getRealPath("/")+ConfigurableConstants.getProperty("extjs.template.path","/WEB-INF/themes/")+getUITheme()+"/"+templateName+".ftl";
    	//标签模板的缓存设置
    	if (null != useCache && Boolean.valueOf(useCache)){
    		Cache cache = getCache();
    		Element element = cache.get(templateName);
        	if ( null != element){
        		return (String)element.getValue();
        	} else {
        		String templateString = getExtTemplateStringByPath(templatePath);
        		Element newElement = new Element(templateName, templateString);
        		cache.put(newElement);
        		return templateString;
        	}
    	} else {
    		return getExtTemplateStringByPath(templatePath);
    	}
    }
    
    
    /**
     * 根据模板路径读取模板内容
     * @param templatePath
     * @return
     */
    public String getExtTemplateStringByPath(String templatePath) {
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
    
    /**
     * 根据模板名称返回freemarker处理过的类.
     * @param templateName
     * @return
     */
    public String getOutPutStringByTemplateName(String templateName,Map context){
    	//获得对应的ftl标签模板的内容
    	String ftlString = getExtTemplateString(templateName);
    	if(ftlString.equals("")) return ftlString;
    	//创建freemarker解析引擎的实例
    	FreemarkerTemplateEngine freemarkerTemplateEngine = FreemarkerTemplateEngine.getInstance();
    	//根据继承于BaseTag的标签类定义的ftl模板 & Map context(包含目标ftl页面上使用自定义Bravo标签attributes的信息)的内容,
    	//解析出要打印在页面上的Html或则extjs的字符串
    	//<#escape x as x!""></#escape>可以对所有的变量进行空值处理
    	return freemarkerTemplateEngine.renderFtl(ApplicationContextKeeper.getServletContext(), "<#escape x as (x)!>"+ftlString+"</#escape>",context);
    }
}
