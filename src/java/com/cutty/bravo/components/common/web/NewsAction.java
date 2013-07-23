/* com.cutty.bravo.components.common.web.NewsAction.java

{{IS_NOTE
 * <p>
 * <a href="NewsAction.java.html"><i>View Source</i></a>
 * </p>
		
	History:
		2008-11-10 ����16:48:53, Created by Cathy.Lin
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

import com.cutty.bravo.components.common.domain.News;
import com.cutty.bravo.components.common.manager.NewsManager;
import com.cutty.bravo.core.web.handler.RequestHandler;
import com.cutty.bravo.core.web.struts2.EntityAction;

@Namespace("/common")
@ParentPackage("bravo")
public class NewsAction extends EntityAction<News> {
	private static final long serialVersionUID = 7084749492129233473L;

	private NewsManager newsManager;

	public NewsManager getNewsManager() {
		return newsManager;
	}

	public void setNewsManager(NewsManager newsManager) {
		this.newsManager = newsManager;
	}

	public String viewNews() throws ServletException, IOException {

		List<News> newsList = newsManager.getNews();
		ServletActionContext.getRequest().setAttribute("bravoHome", RequestHandler.getContextRequestHandler().getRequest().getContextPath());
		ServletActionContext.getRequest().setAttribute("newsList", newsList);
		return "show";
	}

	public String viewDetailNews() throws ServletException, IOException {
		int newsID = Integer.parseInt(ServletActionContext.getRequest()
				.getParameter("id"));
		List<News> singleNewsList = newsManager.getSingleNews(newsID);
		News singleNews = new News();
		singleNews = singleNewsList.get(0);
		ServletActionContext.getRequest().setAttribute("title", singleNews.getTitle());
		ServletActionContext.getRequest().setAttribute("content", singleNews.getContent());
		ServletActionContext.getRequest().setAttribute("publisher", singleNews.getPublisher());
		ServletActionContext.getRequest().setAttribute("createDt", singleNews.getCreateDt());
		return "detailShow";
	}
}
