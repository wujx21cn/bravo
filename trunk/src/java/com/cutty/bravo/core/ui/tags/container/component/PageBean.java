/* com.cutty.bravo.core.ui.bean.PageBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-22 上午07:44:07, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.container.component;

import com.cutty.bravo.core.ui.component.Component;

/**
 * <p> EXT容器标签Page 属性集合类 </p>
 * <p>
 * <a href="PageBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class PageBean extends Component{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7717616869625967762L;
	//定义页面标题
	private String title = "Bravo FrameWork";
	//定义该页面是否做cache,如果是则停止渲染所有内标签,直接从缓存获取.
	private String cache = "true";
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the cache
	 */
	public String getCache() {
		return cache;
	}

	/**
	 * @param cache the cache to set
	 */
	public void setCache(String cache) {
		this.cache = cache;
	}

}
