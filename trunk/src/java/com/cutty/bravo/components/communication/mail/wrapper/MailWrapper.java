/* com.cutty.bravo.components.mail.wrapper.MailWrapper.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Mar 24, 2009 5:22:09 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.communication.mail.wrapper;

import java.util.List;

/**
 * 根据xml配置文件中的<mail>标签获取的邮件信息的单元存储器，一个id对应
 * 一个MailWrapper,一个mail中有内容，邮件地址及附件(可多个)
 * 属性说明：
 * id, <mail>标签的id属性值
 * subject, 主题
 * to, 地址
 * content, 内容
 * attachments , 附件列表
 * <p>
 * <a href="MailWrapper.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class MailWrapper {
	
	private String id;
	private String subject;
	private String to;
	private String content;
	private List<String> attachments;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}
	
	
}
