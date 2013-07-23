/* com.cutty.bravo.components.common.web.NoticeAction.java

{{IS_NOTE
 * <p>
 * <a href="NoticeAction.java.html"><i>View Source</i></a>
 * </p>
		
	History:
		2008-11-11 ����16:48:53, Created by Cathy.Lin
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.components.common.domain.Notice;
import com.cutty.bravo.components.common.manager.NoticeManager;
import com.cutty.bravo.core.web.handler.RequestHandler;
import com.cutty.bravo.core.web.struts2.EntityAction;

@Namespace("/common")   
@ParentPackage("bravo")
public class NoticeAction extends EntityAction<Notice>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5550934472339648832L;
	private NoticeManager noticeManager;
	
	public NoticeManager getNoticeManager(){
		return noticeManager;
		}
	public void setNoticeManager(NoticeManager noticeManager){
		this.noticeManager = noticeManager;
	}
	
	public String viewNotice() throws ServletException, IOException {
		List<Notice> noticeList = noticeManager.getNotice();
  		ServletActionContext.getRequest().setAttribute("bravoHome", RequestHandler.getContextRequestHandler().getRequest().getContextPath());
		ServletActionContext.getRequest().setAttribute("noticeList", noticeList);
		return "show";
	}
	
	public String viewDetailNotice() throws ServletException, IOException {
		int noticeID = Integer.parseInt(ServletActionContext.getRequest()
				.getParameter("id"));
		List<Notice> singleNoticeList = noticeManager.getSingleNotice(noticeID);
		Notice singleNotice = new Notice();
		singleNotice = singleNoticeList.get(0);
		ServletActionContext.getRequest().setAttribute("title", singleNotice.getTitle());
		ServletActionContext.getRequest().setAttribute("content", singleNotice.getContent());
		ServletActionContext.getRequest().setAttribute("publisher", singleNotice.getPublisher());
		ServletActionContext.getRequest().setAttribute("createDate", singleNotice.getCreateDate());
		return "detailShow";
	}
}


  