/* com.cutty.bravo.core.web.struts2.BaseActionSupport.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-7-28 上午02:03:40, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.web.struts2;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * <p>
 * <a href="BaseActionSupport.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public abstract class BaseActionSupport extends ActionSupport{
	
	private static final long serialVersionUID = -4933168937970633362L;

	protected final Log logger = LogFactory.getLog(getClass());
	
    private ActionContext context; //Struts2的ActionContext
    
    protected static final String JSON_DATA_RENDER_CHAIN = "jsonDataRenderChain";


    /**
     * 根据指定的页面参数名称，获取页面传递来的参数值
     *
     * @param parameter
     * @return 单个对象
     */
    protected Object getParameterValue(String parameter) {
        Object[] parameterArray = getParamenterArray(parameter);
        if (parameterArray != null && parameterArray.length == 1) {
            return parameterArray[0];
        } else {
            return null;
        }
    }

    /**
     * 根据指定的页面参数名称，获取页面传递来的参数值
     *
     * @param parameter
     * @return 数组对象
     */
    protected Object[] getParamenterArray(String parameter) {
        return (Object[]) (getActionContext().getParameters().get(parameter));
    }

    /**
     * 获得Webwork的ActionContext
     */
    public ActionContext getActionContext() {
        return context == null ? context = ActionContext.getContext() : context;
    }

    /**
     * 获得当前Action的名称
     */
    public String getActionName() {
        return getActionContext().getName();
    }

    /**
     * 获得当前Http Servlet Request
     */
    public HttpServletRequest getHttpServletRequest() {
        return (HttpServletRequest) getActionContext().get(StrutsStatics.HTTP_REQUEST);
    }

    /**
     * 默认的输入页面
     */
    @Override
	public String input() {
        return INPUT;
    }


	/**
	 * 获取当前struts2 context.
	 */
	protected Map getContextMap(){
		 return  ActionContext.getContext().getContextMap(); 
	}
}
