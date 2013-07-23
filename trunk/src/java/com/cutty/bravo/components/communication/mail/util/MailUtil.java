/* com.cutty.bravo.components.mail.util.MailUtil.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Mar 26, 2009 2:36:47 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.communication.mail.util;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.cutty.bravo.components.communication.mail.MailTemplate;
import com.cutty.bravo.components.communication.mail.wrapper.MailWrapper;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.utils.render.FreemarkerTemplateEngine;

/**
 * 用freemarker解析xml文件中的参数，用springFramework带附件发送的JavaMailSender来发送邮件。
 * <p>
 * <a href="MailUtil.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
@Service("mailUtil")
public class MailUtil {
	String subject;
	String content;
	String [] mailTo;
 
	String [] attachMentPaths;
	/**
	 * 根据mailId及存放属性的map发送email
	 * @param mailId 格式为："xml文件名.<mail>标签的id属性值"
	 * @param parameterMap xml中读得该参数值的格式须为: "map的key.对应value的属性名"
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public void sendMail(String mailId,Map parameterMap) throws SAXException, IOException, ParserConfigurationException{
		MailTemplate mailTemplate = MailTemplate.getTemplate();
		MailWrapper mailWrapper = mailTemplate.getMailWrapperById(mailId);
		FreemarkerTemplateEngine freemarkerTemplateEngine = FreemarkerTemplateEngine.getInstance();
		/* 获得主题  并根据parameterMap render之*/
		  subject = freemarkerTemplateEngine.renderFtl(ApplicationContextKeeper.getServletContext(),"<#escape x as (x)!>"+mailWrapper.getSubject()+"</#escape>",parameterMap);
		/* 获得收件人string , 并经过处理*/
		String mailToAll = freemarkerTemplateEngine.renderFtl(ApplicationContextKeeper.getServletContext(), "<#escape x as (x)!>"+mailWrapper.getTo()+"</#escape>",parameterMap);
		mailTo = mailToAll.split(";");
		/* 获得content*/
		  content = freemarkerTemplateEngine.renderFtl(ApplicationContextKeeper.getServletContext(), "<#escape x as (x)!>"+mailWrapper.getContent() +"</#escape>",parameterMap);
		  
		/* 获得附件列表*/
		
		  List<String>  attachMents = mailWrapper.getAttachments();
		 attachMentPaths = new String[attachMents.size()];
		 
		for (int i = 0; i < attachMents.size(); i ++){
			 
			 String path = freemarkerTemplateEngine.renderFtl(ApplicationContextKeeper.getServletContext(), "<#escape x as (x)!>"+attachMents.get(i)+"</#escape>",parameterMap);
			 attachMentPaths[i] = path;
		}
		
		/* 发送邮件 */
		JavaMailSender mailSender = (JavaMailSender)ApplicationContextKeeper.getAppCtx().getBean("mailSender");
		
		MimeMessagePreparator preparator = new MimeMessagePreparator(){
            public void prepare(MimeMessage mimeMessage) throws MessagingException{
                //邮件信息
                //收件人
                InternetAddress[] addresses = new InternetAddress[mailTo.length];
                for(int i = 0; i < mailTo.length; i ++){
                	addresses[i] = new InternetAddress(mailTo[i]);
                }
                mimeMessage.setRecipients(Message.RecipientType.TO, addresses);
                mimeMessage.setFrom(new InternetAddress("ajax2007@126.com"));
                mimeMessage.setSubject(subject, "gb2312");
                //信息容器
                Multipart mp = new MimeMultipart();
                //信息主体
                MimeBodyPart mbpContent = new MimeBodyPart();
                mbpContent.setText(content);
                mp.addBodyPart(mbpContent);
                for(int i = 0; i < attachMentPaths.length; i ++){
                  //添加附件信息的主体
                    MimeBodyPart mbpFile = new MimeBodyPart();
                    String filename =attachMentPaths[i];
                    FileDataSource fds = new FileDataSource(filename);
                    mbpFile.setDataHandler(new DataHandler(fds));
                    mbpFile.setFileName(fds.getName());
                    mp.addBodyPart(mbpFile);
                }    
               
                mimeMessage.setContent(mp);
                mimeMessage.setSentDate(new Date());
            }
        };
        mailSender.send(preparator);//发送信息 
	}
}
