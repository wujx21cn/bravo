/* com.cutty.bravo.core.security.intercept.web.DataBaseFilterInvocationDefinitionSource.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-10 上午01:06:09, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.security.intercept.web;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.util.UrlMatcher;

import com.cutty.bravo.core.exception.BizException;
import com.cutty.bravo.core.security.Constants;
import com.cutty.bravo.core.security.manager.cache.ResourceCacheManager;
/**
 * 该类实现了spring security的FilterInvocationDefinitionSource接口，用于获取访问资源所需要的权限(authority)，
 * 以ConfigAttributeDefinition形式返回
 * <p>
 * <a href="DataBaseFilterInvocationDefinitionSource.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class DataBaseFilterInvocationDefinitionSource  implements FilterInvocationDefinitionSource {
	

	protected final Log logger = LogFactory.getLog(DataBaseFilterInvocationDefinitionSource.class);
	
	private ResourceCacheManager resourceCacheManager;
	
    private UrlMatcher urlMatcher;

    private boolean stripQueryStringFromUrls;



    public Collection getConfigAttributeDefinitions() {
        return null;
    }

    /**
     * 获取访问url的访问权限，以ConfigAttributeDefinition对象类型返回
     */
    public ConfigAttributeDefinition getAttributes(Object object) throws IllegalArgumentException {
        if ((object == null) || !this.supports(object.getClass())) {
            throw new IllegalArgumentException("Object must be a FilterInvocation");
        }

        String url = ((FilterInvocation) object).getRequestUrl();
        String method = ((FilterInvocation) object).getHttpRequest().getMethod();

        try {
			return lookupAttributes(url, method);
		} catch (BizException e) {
			 throw new IllegalArgumentException(e.getMessage());
		}
    }

    /**
     * 从资源授权缓存中找到资源名为url的资源所运行访问的授权码，并以ConfigAttributeDefinition返回，如果没有则返回null
     * 该授权码将用于与用户的授权码进行对比，决定是否让用户访问该资源对象
     * Performs the actual lookup of the relevant <code>ConfigAttributeDefinition</code> for the specified
     * <code>FilterInvocation</code>.
     * <p>
     * By default, iterates through the stored URL map and calls the
     * {@link UrlMatcher#pathMatchesUrl(Object path, String url)} method until a match is found.
     * <p>
     * Subclasses can override if required to perform any modifications to the URL.
     *
     * @param url the URI to retrieve configuration attributes for
     * @param method the HTTP method (GET, POST, DELETE...).
     *
     * @return the <code>ConfigAttributeDefinition</code> that applies to the specified <code>FilterInvocation</code>
     * or null if no match is foud
     * @throws BizException 
     */
    public ConfigAttributeDefinition lookupAttributes(String url, String method) throws BizException {
        if (stripQueryStringFromUrls) {
            // Strip anything after a question mark symbol, as per SEC-161. See also SEC-321
            int firstQuestionMarkIndex = url.indexOf("?");

            //吧带参数输入的action后的参数去掉，仅留下资源名
            if (firstQuestionMarkIndex != -1) {
                url = url.substring(0, firstQuestionMarkIndex);
            }            
        }
        
        /**去掉查询地址大小写问题。
        if (urlMatcher.requiresLowerCaseUrl()) {
            url = url.toLowerCase();

            if (logger.isDebugEnabled()) {
                logger.debug("Converted URL to lowercase, from: '" + url + "'; to: '" + url + "'");
            }
        }
         **/
        ConfigAttributeDefinition attributes = null;
       
        String[] authorities = resourceCacheManager.findResourceAuthorityByResString(method+"@"+url,Constants.URL);
        if (authorities != null) {
        	attributes = new ConfigAttributeDefinition(authorities);
        } else if (null != resourceCacheManager.findResourceAuthorityByResString(url,Constants.URL)){
        	authorities = resourceCacheManager.findResourceAuthorityByResString(url,Constants.URL);
        	attributes = new ConfigAttributeDefinition(authorities);
        }

        return attributes;
    }

    public boolean supports(Class clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

   

    protected UrlMatcher getUrlMatcher() {
        return urlMatcher;
    }

    
    /**
	 * @param urlMatcher the urlMatcher to set
	 */
	public void setUrlMatcher(UrlMatcher urlMatcher) {
		this.urlMatcher = urlMatcher;
	}

	public boolean isConvertUrlToLowercaseBeforeComparison() {
        return urlMatcher.requiresLowerCaseUrl();
    }

    public void setStripQueryStringFromUrls(boolean stripQueryStringFromUrls) {
        this.stripQueryStringFromUrls = stripQueryStringFromUrls;
    }

	/**
	 * @param resourceCacheManager the resourceCacheManager to set
	 */
	public void setResourceCacheManager(ResourceCacheManager resourceCacheManager) {
		this.resourceCacheManager = resourceCacheManager;
	}


}
