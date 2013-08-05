/* com.cutty.bravo.core.web.filter.SaasCodeFilter.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2011-9-13 下午09:07:37, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.web.handler.SaasCodeHandler;

/**
 *
 * <p>
 * <a href="SaasFilter.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class SaasCodeFilter   implements Filter {
	private static final boolean useSaas = Boolean.parseBoolean(ConfigurableConstants.getProperty("saas.group.database","false"));
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		if (!useSaas) {
			SaasCodeHandler.initSaasCodeHandler(SaasCodeHandler.DEFAULT_SAAS_KEY);
		} else {
			HttpServletRequest httpRequest = (HttpServletRequest)request;
			String saasCode = (String)httpRequest.getSession().getAttribute(SaasCodeHandler.CURRENT_LOGIN_USER_SAAS_KEY);
			if (StringUtils.isNotEmpty(saasCode)){
				SaasCodeHandler.initSaasCodeHandler(saasCode);
			} else {
				String requestSaasCode = httpRequest.getParameter(SaasCodeHandler.CURRENT_LOGIN_USER_SAAS_KEY);
				if (StringUtils.isEmpty(requestSaasCode)) requestSaasCode = SaasCodeHandler.DEFAULT_SAAS_KEY;
				httpRequest.getSession().setAttribute(SaasCodeHandler.CURRENT_LOGIN_USER_SAAS_KEY,requestSaasCode);
				SaasCodeHandler.initSaasCodeHandler(requestSaasCode);
			}
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}   

}
