/* com.cutty.bravo.core.ui.dataRender.DataRenderAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-1 下午11:00:40, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.dataRender.web;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.cutty.bravo.core.ui.dataRender.DataRender;
import com.cutty.bravo.core.web.struts2.BaseActionSupport;

/**
 * <p>  </p>
 * <p>
 * <a href="DataRenderAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Namespace("/ui")   
@ParentPackage("bravo")
public class DataRenderAction  extends BaseActionSupport{

	private static final long serialVersionUID = 5351249310356512614L;

	public String renderJsonData() throws Exception {
		long start = System.currentTimeMillis();
		DataRender dataRender =  DataRender.getInstance();
		String jsonStr = dataRender.rend(ServletActionContext.getRequest(), ServletActionContext.getResponse());
		ServletActionContext.getRequest().setAttribute("jsonStr",jsonStr);
		long totalWaste = System.currentTimeMillis() - start;
		logger.debug("total costed when finished render data:::" + totalWaste);
		return "blank";
    }
}
