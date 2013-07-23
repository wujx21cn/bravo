/* com.cutty.bravo.components.common.manager.NoticeManager.java

{{IS_NOTE
 * <p>
 * <a href="NoticeManager.java.html"><i>View Source</i></a>
 * </p>
		
	History:
		2008-11-11 ����16:48:53, Created by Cathy.Lin
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cutty.bravo.components.common.domain.Notice;
import com.cutty.bravo.core.dao.support.Page;
import com.cutty.bravo.core.manager.BaseManager;



@Service("noticeManager")
public class NoticeManager extends BaseManager<Notice>{
	
	

public List <Notice> getNotice(){
	Page page = new Page(0, 5, 5, null);
	String hql="from Notice order by create_Dt desc";
	List<Notice> notice = this.find(page, hql,true);
	return notice;
}

public List <Notice> getSingleNotice(int noticeID){
	String hql = "from Notice WHERE id="+noticeID;
	List<Notice> notice = this.find(null, hql,true);
	return notice;
}
}