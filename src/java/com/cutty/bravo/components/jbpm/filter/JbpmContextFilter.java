/* com.cutty.bravo.components.jbpm.filter.JbpmContextFilter.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-3 下午01:21:35, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Travelsky Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.jbpm.filter;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * 该类为一个过滤器，用于获取jbpm的配置文件，对jbpm进行预处理
 *
 * <p>
 * <a href="JbpmContextFilter.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class JbpmContextFilter implements Filter, Serializable {
	
	private static final long serialVersionUID = 628732010835256558L;
	String jbpmContextName = null; 
	private FilterConfig filterConfig = null;

	/**
	 * 初始化过滤器
	 * @param filterConfig-过滤器的配置文件
	 */
	public void init(FilterConfig filterConfig) throws ServletException { 
		this.filterConfig = filterConfig;
		this.jbpmContextName = filterConfig.getInitParameter("jbpm.context.name"); 
		if (jbpmContextName == null) { 
			jbpmContextName = JbpmContext.DEFAULT_JBPM_CONTEXT_NAME; 
		} 
	} 

	/**
	 * 执行过滤器
	 * @param 
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException { 
	JbpmContext jbpmContext = getJbpmConfiguration().createJbpmContext(jbpmContextName); 
	try { 
			filterChain.doFilter(servletRequest, servletResponse); 
		} finally { 
			jbpmContext.close(); 
		} 
	} 

	/**
	 * 获取jbpm的配置文件
	 * @return-jbpm的配置文件
	 */
	protected JbpmConfiguration getJbpmConfiguration() { 
		ServletContext context = filterConfig.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		return (JbpmConfiguration)ctx.getBean("jbpmConfiguration");
	} 

	public void destroy() { 
		
	} 

}
