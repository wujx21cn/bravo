/* com.cutty.bravo.components.mail.MailTemplate.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Mar 24, 2009 4:36:43 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.communication.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.cutty.bravo.components.communication.mail.wrapper.MailWrapper;
import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;

/**
 * 解析config.properties配置文件指定的xml文件，存放于mailWrapperMap中，
 * key为xml文件名.<mail>标签下的id属性值;value是自定义的mailWrapper;
 * 
 * <p>
 * <a href="MailTemplate.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class MailTemplate  implements Serializable{
	
	private static final Log logger = LogFactory.getLog(MailTemplate.class);
	
	private static final long serialVersionUID = -7513964303828933144L;

	private MailTemplate(){}
	
	private static Map<String,MailWrapper> mailWrapperMap = new HashMap<String,MailWrapper>();
	
	private static MailTemplate mailTemplate;
	
	
	/**
	 * 配置单例的MailTemplate,解析xml模板中的mail信息,存储到mailWrapperMap,供MailUtil使用.
	 * @return 单例MailTemplate
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private static MailTemplate configuration() throws SAXException, IOException, ParserConfigurationException{
		ServletContext servletContext = ApplicationContextKeeper.getServletContext();
		String mailTemplatePath = servletContext.getRealPath("/") +ConfigurableConstants.getProperty("mail.template.path","WEB-INF/template/mail/");
		File mailFiles = new File(mailTemplatePath);
		if (mailFiles.isDirectory()) {
			InputStream configStream = null;
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				File[] fileArray = mailFiles.listFiles();
				for (int i=0;i<fileArray.length;i++){
					if (! fileArray[i].getName().substring(fileArray[i].getName().length()-4, fileArray[i].getName().length()).equalsIgnoreCase(".xml")) continue;
					configStream = new FileInputStream(fileArray[i]);
					Document doc = db.parse(configStream);
					String fileName = fileArray[i].getName().substring(0, fileArray[i].getName().length()-4);
					if (null != doc){ 
						Element root = doc.getDocumentElement();
						paserMailMap(fileName,root);
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
		return mailTemplate;
	}
	public static MailTemplate getTemplate() throws SAXException, IOException, ParserConfigurationException{
		if (null != mailTemplate && Boolean.valueOf(ConfigurableConstants.getProperty("sql.use.cache","true"))) {
			return mailTemplate;
		}
		mailTemplate = new MailTemplate();
		MailTemplate.configuration();
		return mailTemplate;
	}
	
	/**
	 * 解析文件名为fileName的xml文件的邮件内容，当有多个xml文件时，该函数会被调用多次
	 * @param fileName xml模板文件的文件名
	 * @param root xml文件的根标签元素
	 * @throws ParserConfigurationException
	 */
	private static void paserMailMap(String fileName,Element root) throws ParserConfigurationException{
		NodeList mailElements = root.getElementsByTagName("mail");
		for (int i=0;i<mailElements.getLength();i++){
			MailWrapper mailWrapper = new MailWrapper();
			Element mailElement = (Element)mailElements.item(i);
			String id = fileName + "." + mailElement.getAttribute("id");
			mailWrapper.setId(id);
			if (!StringUtils.isEmpty(mailElement.getAttribute("subject"))){
				mailWrapper.setSubject(mailElement.getAttribute("subject"));
			}
			if (!StringUtils.isEmpty(mailElement.getAttribute("to"))){
				mailWrapper.setTo(mailElement.getAttribute("to"));
			} else {
				throw new ParserConfigurationException("mail模板必须定义目标地址");
			}
			/*构造附件list给mailWrapper*/
			NodeList attachments = mailElement.getElementsByTagName("attachment");
			List<String> attachMentPaths = new ArrayList<String>();
			for(int j = 0; j < attachments.getLength(); j ++){
				Element pathElement = (Element)attachments.item(j);
				String attachMentPath = pathElement.getAttribute("path");
				attachMentPaths.add(attachMentPath);
			}
			mailWrapper.setAttachments(attachMentPaths);
			/*获得mail 内容*/
			NodeList content = mailElement.getElementsByTagName("content");
			for(int j = 0; j < content.getLength(); j ++){
				Element contentElement = (Element)content.item(i);
				String contentText = contentElement.getTextContent();
				mailWrapper.setContent(contentText);
			}
			mailWrapperMap.put(id, mailWrapper);
		}
	}
	/**
	 * 概据id获取邮件信息与内容, id 规则为： xml文件名.<mail>标签的id属性值
	 * @param id xml文件名.<mail>标签的id属性值
	 * @return
	 */
	public MailWrapper getMailWrapperById(String id){
		return mailWrapperMap.get(id);
	}
}
