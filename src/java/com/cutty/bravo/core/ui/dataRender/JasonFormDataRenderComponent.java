/* com.cutty.bravo.core.ui.dataRender.JasonFormDataRenderComponent.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Sep 23, 2008 1:49:31 PM, Created by Jason.Wu
		2008-10-09 modified by kukuxia.kevin
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.dataRender;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import com.cutty.bravo.core.ui.Constants;

/**
 * <p> 返回表单提交后数据信息的json渲染器 </p>
 * <p>
 * <a href="JasonFormDataRenderComponent.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:huangw100@gmail.com">kukuxia.hw</a>
 */
public class JasonFormDataRenderComponent implements DataRenderComponent{
	private static final Log logger = LogFactory.getLog(JasonFormDataRenderComponent.class);

    /**
     * 实现 DataRenderComponent 接口 rend(HttpServletRequest request, HttpServletResponse response)方法
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException
     * @throws ServletException
     * @return json格式返回信息 String
     */
	public String rend(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {

		String operationStatus = (String)request.getAttribute(Constants.FORM_AJAX_SUBMIT_STATUS);
		String operationMsg = (String)request.getAttribute(Constants.FORM_AJAX_SUBMIT_MSG);
		StringBuffer jsonStringSB = new StringBuffer("");
		
		jsonStringSB.append("{success:" + operationStatus + ", msg:'");
		if (StringUtils.isNotEmpty(operationMsg)) jsonStringSB.append(operationMsg);
		jsonStringSB.append("'");
		Map<String,Object> operationParam = (Map<String,Object>)request.getAttribute(Constants.FORM_AJAX_SUBMIT_PARAM);
		if (!CollectionUtils.isEmpty(operationParam)){
			for (String key:operationParam.keySet()){
				String retVal = operationParam.get(key).toString();
				jsonStringSB.append(",").append(key).append(":'")
					.append(retVal).append("'");
			}
		}
		jsonStringSB.append("}");
		return jsonStringSB.toString() ;
	}
}
