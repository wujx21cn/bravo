/* com.cutty.bravo.core.ui.dataRender.JasonBatchRemoveRenderComponent.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-14 上午10:34:14, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.dataRender;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.cutty.bravo.core.ui.Constants;

/**
 * <p> 批量删除返回渲染后的json数据格式 </p>
 * <p>
 * <a href="JasonBatchRemoveRenderComponent.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class JasonBatchRemoveRenderComponent implements DataRenderComponent {
    /**
     * 实现 DataRenderComponent 接口 rend(HttpServletRequest request, HttpServletResponse response)方法
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException
     * @throws ServletException
     * @return json格式返回信息 String
     */
	public String rend(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
    	String del_flag = (String)request.getAttribute(Constants.GRID_BATCH_REMOVE_STATUS);
    	if (Constants.GRID_BATCH_REMOVE_STATUS_SUCCESS.equalsIgnoreCase(del_flag)) {
    		return Constants.GRID_BATCH_REMOVE_STATUS_SUCCESS;
    	} else {
    		if (StringUtils.isNotEmpty((String)request.getAttribute(Constants.GRID_BATCH_REMOVE_MSG))){
    			return (String)request.getAttribute(Constants.GRID_BATCH_REMOVE_MSG);
    		} 
    	}
		return del_flag;
	}

}
