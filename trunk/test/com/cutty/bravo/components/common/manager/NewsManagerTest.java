/* com.cutty.bravo.components.common.manager.NewsManagerTest.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2009-1-22 下午02:52:07, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.manager;

import java.util.List;

import com.cutty.bravo.core.manager.BaseManagerTest;
import com.cutty.bravo.components.common.domain.News;
import com.cutty.bravo.components.common.manager.NewsManager;

/**
 *
 * <p>
 * <a href="NewsManagerTest.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class NewsManagerTest extends BaseManagerTest<News>{
	private NewsManager newsManager;
	

	public void setNewsManager(NewsManager newsManager) {
		this.newsManager = newsManager;
	}


	public void testgetNews() {
		List<News>  news = newsManager.getNews();
		for (int i=0;i<news.size();i++){
			System.out.println(news.get(i).getTitle());
		}
		assertEquals(5, news.size());
	}
	
	public void testgetSingleNews() throws Exception {
		List<News>  news = newsManager.getNews();
		for (int i=0;i<news.size();i++){
			System.out.println(news.get(i).getTitle());
		}
		assertEquals(5, news.size());
	}
	
}
