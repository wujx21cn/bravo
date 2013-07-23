/* com.cutty.bravo.core.utils.render.FreemarkerTemplateEngine.java

 {{IS_NOTE
 Purpose:
 
 Description:
 
 History:
 2008-8-22 上午05:39:07, Created by Jason.Wu
 }}IS_NOTE

 Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

 */
package com.cutty.bravo.core.utils.render;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.web.handler.RequestHandler;
import com.opensymphony.xwork2.util.FileManager;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * 
 * <p>
 * <a href="FreemarkerTemplateEngine.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class FreemarkerTemplateEngine {

	private String CONFIG_SERVLET_CONTEXT_KEY = "bravo.freemarker.config";

	private static final Log logger = LogFactory.getLog(FreemarkerTemplateEngine.class);

	private FreemarkerBeanWrapper wrapper;
	
	private static FreemarkerTemplateEngine templateEngine;
	
	private FreemarkerTemplateEngine(){}
	
	public static FreemarkerTemplateEngine getInstance(){
		if (null == templateEngine){
			templateEngine = new FreemarkerTemplateEngine();
		}
		return templateEngine;
	}


	public final synchronized freemarker.template.Configuration getConfiguration(
			ServletContext servletContext) throws TemplateException {
		this.wrapper = new FreemarkerBeanWrapper();
		freemarker.template.Configuration config = (freemarker.template.Configuration) servletContext
				.getAttribute(CONFIG_SERVLET_CONTEXT_KEY);
		if (config == null) {
			config = createConfiguration(servletContext);
			servletContext.setAttribute(CONFIG_SERVLET_CONTEXT_KEY, config);
		}
		config.setWhitespaceStripping(true);
		return config;
	}

	protected freemarker.template.Configuration createConfiguration(
			ServletContext servletContext) throws TemplateException {
		freemarker.template.Configuration configuration = new freemarker.template.Configuration();
		configuration.setTemplateLoader(new StringTemplateLoader());

		configuration
				.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

		configuration.setObjectWrapper(getObjectWrapper());
		
		//freemarker数字格式配置，使freemark不会自动添加一个千位分隔符，例如不会自动将1000000和1000，在页面格式化为1,000,000和1,000
		//configuration.setNumberFormat(ConfigurableConstants.getProperty("convert.format.number","#"));
		configuration.setDateFormat(ConfigurableConstants.getProperty("convert.format.date","yyyy-MM-dd"));
		configuration.setTimeFormat(ConfigurableConstants.getProperty("convert.format.time","HH:mm:Ss"));
		configuration.setDateTimeFormat(ConfigurableConstants.getProperty("convert.format.datetime","yyyy-MM-dd HH:mm:Ss"));
		configuration.setDefaultEncoding(ConfigurableConstants.getProperty("freemarker.config.default_encoding","utf-8"));	
		loadSettings(servletContext, configuration);
		return configuration;
	}

	protected BeansWrapper getObjectWrapper() {
		return new FreemarkerBeanWrapper();
	}

	protected void loadSettings(ServletContext servletContext,
			freemarker.template.Configuration configuration) {
		try {
			InputStream in = FileManager.loadFile("freemarker.properties",
					FreemarkerManager.class);

			if (in != null) {
				Properties p = new Properties();
				p.load(in);
				configuration.setSettings(p);
			}
		} catch (IOException e) {
			logger
					.error(
							"Error while loading freemarker settings from /freemarker.properties",
							e);
		} catch (TemplateException e) {
			logger
					.error(
							"Error while loading freemarker settings from /freemarker.properties",
							e);
		}
	}
	public String changeText2MacroAndRenderFtl(ServletContext servletContext, String ftlString,
			Map context) {
		if (ftlString == null)
			return null;
		ftlString = changeText2Macro(ftlString);
		return renderFtl(servletContext,ftlString,context);
	}
	
    /** 其中加入了encodeRegex（）和decodeRegex（）方法，目的是在模板转换时，将使用到正则表达
 	 *  且其中含有"*"（表示零次或者多次）这个符号可以正常的打印出来
	 */
	public String renderFtl(ServletContext servletContext, String ftlString,
			Map context) {
		if (ftlString == null)
			return null;
		//没有需要解析的变量（用${...}标识），语法定义（用#{...}标识），就直接返回字符串，无须freemarker解析
		if (ftlString.indexOf("$") < 0 && ftlString.indexOf("#") < 0)
			return ftlString;
		ftlString = encodeRegex(ftlString);//将"*"换成其他configuration.getTemplate()可识别的字符
		Template template = null;
		String ret;
		// process the template & flush the output
		StringWriter stringWriter=null;
		BufferedWriter writer=null;
		try {
			/*用freemarker编译解析ftl模板*/
			Configuration configuration = getConfiguration(servletContext);
			configuration.setLocalizedLookup(false);
			//取得标签ftl模板
			template = configuration.getTemplate(ftlString);
			stringWriter = new StringWriter();
			writer = new BufferedWriter(stringWriter);
			//合并数据模型和模版，并将结果输出到writer中
			template.process(this.getContextRoot(context), writer, BeansWrapper
					.getDefaultInstance());
			writer.flush();
			ret = stringWriter.toString();
		} catch (TemplateException te) {
			ret = ftlString;
		} catch (IOException ie) {
			ret = ftlString;
		} finally {
			try {
				if (null != stringWriter)stringWriter.close();
				if (null != writer) writer.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
		ret = decodeRegex(ret);//再将"*"转换回来
		return ret;
	}	
	/**
	 * 建立数据模型，调用后和ftl模板整合
	 */
	public SimpleHash getContextRoot(Map context) throws TemplateException {
		SimpleHash root = new SimpleHash(wrapper);
		//在ftl模板中可以调用static方法
		root.put("Static", wrapper.getStaticModels());
		// 在此写入ftl页面可以调用的静态类
		// root.put("UtilDateTime",wrapper.getStaticModels().get("com.cutty.bravo.util.UtilDateTime"));
		if (context != null) {
			root.putAll(context);
		}
		RequestHandler requestHandler = RequestHandler
				.getContextRequestHandler();
		if (requestHandler != null) {
			root.put("requestHandler", requestHandler);
		}
		return root;
	}

	public String upperFirstChar(String str) {
		if (str == null)
			return "";
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public String changeText2Macro(String str) {
		if (null == str || str.length() < 3)
			return str;
		if (str.indexOf("$") < 0 && str.indexOf("%") < 0)
			return str;
		if (str.indexOf("%{") >= 0)
			str = str.replaceAll("%\\{", "\\$\\{");
		if (str.indexOf("%^") >= 0)
			str = str.replaceAll("%\\^", "\\$\\{");
		if (str.indexOf("^%") >= 0)
			str = str.replaceAll("\\^%", "\\}");
		return str;
	}

	public String changeMacro2Text(String str) {

		if (str.indexOf("${") >= 0)
			str = str.replaceAll("\\$\\{", "@\\^");
		if (str.indexOf("}") >= 0)
			str = str.replaceAll("\\}", "\\^@");
		// if(str.indexOf("\"")>=0) str=str.replaceAll("\"","'");
		// if(str.indexOf("<")>=0) str=str.replaceAll("<","&lt;");
		// if(str.indexOf(">")>=0) str=str.replaceAll(">","&gt;");

		return str;
	}

    //2008-11-02 kukuxia.kevin.hw 将*号转换成一个没有特殊意义的"#-#"
	public String encodeRegex(String str) {
	       if(str.indexOf("regex") >= 0){
			if (str.indexOf("*") >= 0)
				str = str.replaceAll("\\*", "#-#");
	       }
			return str;
		}
	//2008-11-02 kukuxia.kevin.hw 将没有特殊意义的"#-#"转换成"*"号
	public String decodeRegex(String str) {
       if(str.indexOf("regex") >= 0){
		if (str.indexOf("#-#") >= 0)
			str = str.replaceAll("#-#", "*");
       }
		return str;
	}
}