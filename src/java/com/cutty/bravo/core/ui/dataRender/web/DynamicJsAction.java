/* com.cutty.bravo.core.ui.dataRender.web.DynamicJsAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-17 上午10:48:53, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.dataRender.web;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.views.freemarker.FreemarkerResult;

import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.web.struts2.BaseActionSupport;

/**
 * <p> 在所有页面初始化js全局bravoHome变量值 </p>
 * <p>
 * <a href="DynamicJsAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Namespace("/ui")   
@ParentPackage("bravo")
@Results( {   
	@Result(name = "dynamicJs" ,location = "/common/dynamicJs.ftl",type = "freemarker" )
    }) 
public class DynamicJsAction extends BaseActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -933276535120867610L;

	public String CreateDynamicJs(){
		ServletActionContext.getRequest().setAttribute("bravoHome", ServletActionContext.getRequest().getContextPath());
		ServletActionContext.getRequest().setAttribute("extjsWidgetPath",ConfigurableConstants.getProperty("ui.extjs.widget.path", "widgets/ext-3.3.1"));
		return "dynamicJs";
	}
}
