/* com.cutty.bravo.core.ui.dataRender.AjaxHandleResponseRenderComponent.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Nov 8, 2008 12:14:05 PM, Created kukuxia.kevin.hw
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.dataRender;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cutty.bravo.core.ui.Constants;

/**
 * <p> 统一通用的Ajax处理后返回json格式数据 </p>
 * <p>
 * <a href="AjaxHandleResponseRenderComponent.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:huangw100@126.com">kukuxia.kevin.hw</a>
 */
public class AjaxHandleResponseRenderComponent implements DataRenderComponent {
	private static final Log logger = LogFactory.getLog(AjaxHandleResponseRenderComponent.class);

    /**
     * 实现 DataRenderComponent 接口 rend(HttpServletRequest request, HttpServletResponse response)方法
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException
     * @throws ServletException
     * @return json格式返回信息 String
     */
	
	public String rend(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
	    String save_flag = (String)request.getAttribute(Constants.AJAX_HANDLE_STATUS);
	    if (Constants.AJAX_HANDLE_STATUS_SUCCESS.equalsIgnoreCase(save_flag)) {
	    	return Constants.AJAX_HANDLE_STATUS_SUCCESS;
	    } else {
	    	if (StringUtils.isNotEmpty((String)request.getAttribute(Constants.AJAX_HANDLE_STATUS))){
	    		return (String)request.getAttribute(Constants.AJAX_HANDLE_MSG);
	    	} 
	    }
		return save_flag;
	}
}