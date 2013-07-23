/* com.cutty.bravo.components.common.manager.NewsManager.java

{{IS_NOTE
 * <p>
 * <a href="NewsManager.java.html"><i>View Source</i></a>
 * </p>
		
	History:
		2008-11-10 ����16:48:53, Created by Cathy.Lin
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cutty.bravo.components.common.domain.News;
import com.cutty.bravo.core.dao.support.Page;
import com.cutty.bravo.core.manager.BaseManager;

@Service("newsManager")
public class NewsManager extends BaseManager<News>{
	
	

public List <News> getNews(){
	Page page = new Page(0, 5, 5, null);
	String hql="from News order by createDt desc";
	List<News> news = this.find(page, hql,true);
	return news;
		
}

public List <News> getSingleNews(int newsID){
	String hql="from News WHERE id="+newsID;
	List<News> news = this.find(null, hql,true);
	return news;
		
}
}