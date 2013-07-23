/* com.cutty.bravo.core.web.struts2.interceptor.BaseInterceptor.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-7-29 上午09:18:24, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.web.struts2.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.web.handler.RequestHandler;
import com.cutty.bravo.core.web.struts2.EntityAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;


import freemarker.ext.jsp.TaglibFactory;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**h
 *
 * <p>
 * <a href="BaseInterceptor.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class BaseInterceptor  extends AbstractInterceptor {

	private static final long serialVersionUID = -3022866373552598430L;
	protected final Log logger = LogFactory.getLog(BaseInterceptor.class);
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//初始化所有页面参数.
		RequestHandler.getRequestHandler(ServletActionContext.getRequest());
		ServletActionContext.getRequest().setAttribute("bravoHome", ServletActionContext.getRequest().getContextPath());
		ServletActionContext.getRequest().setAttribute("extjsWidgetPath",ConfigurableConstants.getProperty("ui.extjs.widget.path", "widgets/ext-3.3.1"));
        Object action = invocation.getAction();
		if (null == ApplicationContextKeeper.getServletContext().getAttribute("bravo")){
	    	TaglibFactory JspTaglibs = new TaglibFactory(ApplicationContextKeeper.getServletContext());
	    	try {
	    		TemplateModel bravo = JspTaglibs.get(ConfigurableConstants.getProperty("ui.template.path","/WEB-INF/themes/")+"bravo.tld");
	    		ApplicationContextKeeper.getServletContext().setAttribute("bravo", bravo);
	    		ActionContext.getContext().put("bravo", bravo);
			} catch (TemplateModelException e) {
				logger.error(e);
			}
		} else {
			ActionContext.getContext().put("bravo", ApplicationContextKeeper.getServletContext().getAttribute("bravo"));
		} 
		if (action instanceof EntityAction){
			EntityAction entityAction = (EntityAction)action;
			Object model = entityAction.getModel();
			if (null == model)invocation.invoke();
			//**** liangg 2009-04-22  获得当前操作的实体全名，传到Button.java中接收，进行权限控制
	        	String modelForButton = entityAction.getEntityClass().getName();
	    		ServletActionContext.getRequest().setAttribute("entityNameForButton", modelForButton); 
	    	//****
		}
		
		/*获取nameSpaceName+actionName+methodName+resultCode用于按钮的按权显示 liangg 2009.04.22*/
		
        String buttonPrefix = invocation.getInvocationContext().getActionInvocation().getProxy().getNamespace()+"/"
        						+invocation.getInvocationContext().getActionInvocation().getProxy().getActionName()+"/"
        						+invocation.getInvocationContext().getActionInvocation().getProxy().getMethod()+"/"
        						+invocation.getInvocationContext().getActionInvocation().getResultCode();
        ServletActionContext.getRequest().setAttribute("buttonPrefix", buttonPrefix);
        
		
		String reslutCode =  invocation.invoke();
		return reslutCode;
    }
   
}
