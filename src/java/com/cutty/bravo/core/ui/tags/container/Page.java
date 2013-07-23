/* com.cutty.bravo.core.ui.tags.Page.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-22 上午07:39:04, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.container;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang.StringUtils;

import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.exception.CacheException;
import com.cutty.bravo.core.ui.tags.BaseTag;
import com.cutty.bravo.core.ui.tags.container.component.PageBean;
import com.cutty.bravo.core.utils.cache.Cache;
import com.cutty.bravo.core.utils.cache.CacheFactory;
import com.cutty.bravo.core.utils.cache.ehcache.CacheImpl;
import com.cutty.bravo.core.utils.render.FreemarkerTemplateEngine;
import com.opensymphony.xwork2.DefaultActionInvocation;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * <p> 自定义EXT Page标签类 </p>
 * <p>
 * <a href="Page.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class Page extends BaseTag{
	private static final long serialVersionUID = -3302743282264981768L;
	private static final String  cacheName= "BRAVO_PAGE_CACHE";
	private Cache cache;
	private String pageId;

	/** 
	 * override BaseTag的initTag()方法，直接定于Page标签类的模板名
	 * @see com.cutty.bravo.core.ui.tags.BaseTag#initTag()
	 */
	@Override
	public void initTag() {
		logger.debug("Start:::::::====="+System.currentTimeMillis());
		this.setComponent(new PageBean());
		this.startHtmlTemplateName = "Page_Html_Start";
		this.endHtmlTemplateName = "Page_Html_End";
		this.startScriptTemplateName = "Page_Script_Start";
		this.endScriptTemplateName = "Page_Script_End";
	}

	/** 
	 * override BaseTag的doStartTag()方法，首先判断是否使用缓存读取页面，否则调用父类BaseTag的doStartTag()方法。
	 * @see com.cutty.bravo.core.ui.tags.BaseTag#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		boolean useCache =Boolean.parseBoolean( ConfigurableConstants.getProperty("ui.page.cache", "true"));
		PageBean component = (PageBean)this.getComponent();
		if (useCache && !"false".equalsIgnoreCase(component.getCache())){
			if (StringUtils.isEmpty(component.getName())) {
				logger.error("you have definded the cache attribute to be 'true',you have to define the name attribute for this page!!!/n您已经定义了该页面使用缓寸,你必须定义该页面的name属性!!!");
				throw new JspException("you have definded the cache attribute to be 'true',you have to define the name attribute for this page!!!/n您已经定义了该页面使用缓寸,你必须定义该页面的name属性!!!");
			}
			try {
				cache= CacheFactory.getCacheManager().getCache(cacheName);
				if (null == cache) {
					cache = new CacheImpl(cacheName, 10000, true, true, 7200, 3600);   
					CacheFactory.getCacheManager().addCache(cache);
					logger.warn("can't find the cache with the name:"+cacheName);
				}
				//如果cache为空,则按正常流程跑
				if (null == cache ) return super.doStartTag();
				//如果cache获取的缓存为空,则按正常流程跑.
				if (null == cache.get(getPageId())) return super.doStartTag();
			} catch (CacheException e) {
				logger.error(e);
			}
			return super.SKIP_BODY;//跳过了开始和结束标签之间的代码
		}
		return super.doStartTag();
	}

	/** 
	 * override BaseTag的doEndTag()方法，首先判断是否使用缓存读取页面，否则调用父类BaseTag的doEndTag()方法。
	 * @see com.cutty.bravo.core.ui.tags.BaseTag#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		boolean useCache =Boolean.parseBoolean( ConfigurableConstants.getProperty("ui.page.cache", "true"));
		PageBean component = (PageBean)this.getComponent();
		String outPutSB = null; 
		if (useCache && !"false".equalsIgnoreCase(component.getCache())){
			try {
				cache= CacheFactory.getCacheManager().getCache(cacheName);
				if (null == cache) {
					cache = new CacheImpl(cacheName, 10000, true, true, 7200, 3600);   
					CacheFactory.getCacheManager().addCache(cache);
					logger.warn("can't find the cache with the name:"+cacheName);
				}
				outPutSB = (String)cache.get(getPageId());
			} catch (CacheException e) {
				logger.error(e);
			}
		} 
		if (null == outPutSB){
			super.doEndTag();
			outPutSB = "";
			StringBuffer htmlSB = (StringBuffer)pageContext.getAttribute(OUTPUT_HTML_CONTEXT_KEY);
			if (null != htmlSB) outPutSB = outPutSB+ htmlSB.toString();
			StringBuffer scriptSB = (StringBuffer)pageContext.getAttribute(OUTPUT_SCRIPT_CONTEXT_KEY);
			if (null != scriptSB) outPutSB = outPutSB+ scriptSB.toString();
			if (useCache && !"false".equalsIgnoreCase(component.getCache())) {
				try {
					cache= CacheFactory.getCacheManager().getCache(cacheName);
					cache.put(getPageId(), outPutSB);
				} catch (CacheException e) {
					logger.error(e);
				}
				
			}
		}
		if (StringUtils.isNotEmpty(outPutSB)){
			FreemarkerTemplateEngine freemarkerTemplateEngine = FreemarkerTemplateEngine.getInstance();
			outPutSB = freemarkerTemplateEngine.changeText2MacroAndRenderFtl(pageContext.getServletContext(), "<#escape x as (x)!>"+outPutSB+"</#escape>",getPageContent());
		}
		JspWriter out = pageContext.getOut();   
		try {
			out.write(outPutSB);
		} catch (IOException e) {
			logger.error(e);
		}
		logger.debug("End:::::::::====="+System.currentTimeMillis());
		return super.EVAL_PAGE;//Page标签加载完毕，继续计算页面下面剩余的代码
	}
	
	/**
	 * 该函数为struts2专用,如果更换mvc框架,需要更改该函数.
	 * @return pageId 页面标识号
	 */
	private String getPageId(){
		if (StringUtils.isEmpty(pageId)) {
			HttpServletRequest httpRequest= (HttpServletRequest)pageContext.getRequest();
			//获取struts2的值笺
			ValueStack valueStack = (ValueStack)httpRequest.getAttribute("struts.valueStack");
			//获取当前返回的页面代码
			DefaultActionInvocation defaultActionInvocation = (DefaultActionInvocation)valueStack.findValue("com.opensymphony.xwork2.ActionContext.actionInvocation");
			String resultCode = defaultActionInvocation.getResultCode();
			pageId = httpRequest.getRequestURI()+"_"+resultCode;
		}
		return pageId;
	}
	
	/**
	 * 获取当前WEB的CONTEXT内容
     * @return pageMap Page标签的context信息
	 */
	private Map getPageContent(){
		Map pageMap = getWebContent();
		HttpServletRequest httpRequest= (HttpServletRequest)pageContext.getRequest();
		ValueStack valueStack = (ValueStack)httpRequest.getAttribute("struts.valueStack");
		DefaultActionInvocation defaultActionInvocation = (DefaultActionInvocation)valueStack.findValue("com.opensymphony.xwork2.ActionContext.actionInvocation");
		if (defaultActionInvocation.getAction() instanceof ModelDriven){
			ModelDriven modelDrivenAaction = (ModelDriven)defaultActionInvocation.getAction();
			Object model = modelDrivenAaction.getModel();
			pageMap.put("formValue",model);
		}
		return pageMap;
	}
}
