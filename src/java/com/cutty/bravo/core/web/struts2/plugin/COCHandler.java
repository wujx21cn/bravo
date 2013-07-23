/* com.cutty.bravo.core.web.struts2.plugin.COCHandler.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-12-3 下午02:59:45, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.web.struts2.plugin;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.util.ClassLoaderUtils;

import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.utils.generator.Generator;
import com.cutty.bravo.core.web.struts2.EntityAction;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.UnknownHandler;
import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.InterceptorLocator;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.config.entities.ResultTypeConfig;
import com.opensymphony.xwork2.config.providers.InterceptorBuilder;
import com.opensymphony.xwork2.inject.Inject;


/**
 *
 * <p>
 * <a href="COCHandler.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class COCHandler implements UnknownHandler {

    protected String defaultPackageName;
    protected ServletContext servletContext;
    protected Map<String,ResultTypeConfig> resultsByExtension;
    protected String templatePathPrefix;
    protected Configuration configuration;
    protected ObjectFactory objectFactory;
    
    protected static final Log LOG = LogFactory.getLog(COCHandler.class);
    
    /**
     * 构造函数,用于初始化COCHandler类
     * @param defaultPackage  读取struts.xml中的struts.codebehind.defaultPackage参数
     * @param configuration  配置类
     */
    @Inject
    public COCHandler(@Inject("struts.codebehind.defaultPackage") String defaultPackage, 
                                    @Inject Configuration configuration) {

        this.configuration = configuration;
        this.defaultPackageName = defaultPackage;
        resultsByExtension = new LinkedHashMap<String,ResultTypeConfig>();
        PackageConfig parentPackage = configuration.getPackageConfig(defaultPackageName);
        if (parentPackage == null) {
            throw new ConfigurationException("Unknown parent package: "+parentPackage);
        }    
        Map<String,ResultTypeConfig> results = parentPackage.getAllResultTypeConfigs();
 
        resultsByExtension.put("ftl", results.get("freemarker"));
        resultsByExtension.put("jsp", results.get("dispatcher"));
        resultsByExtension.put("vm", results.get("velocity"));

       
    }                                
    /**
     * 设置模板文件根路径
     * @param prefix
     */
    @Inject("struts.codebehind.pathPrefix")
    public void setPathPrefix(String prefix) {
        this.templatePathPrefix=prefix;
    }
    
    @Inject
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    
    @Inject
    public void setObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }
    
    public ActionConfig handleUnknownAction(String namespace, String actionName)
            throws XWorkException {
        String pathPrefix = determinePath(templatePathPrefix, namespace);
        ActionConfig actionConfig = null;
        for (String ext : resultsByExtension.keySet()) {
            String path = string(pathPrefix, actionName, "." , ext);
            try {
                if (locateTemplate(path) != null) {
                    actionConfig = buildActionConfig(path, namespace, actionName, resultsByExtension.get(ext));
                    break;
                }
            } catch (MalformedURLException e) {
                LOG.warn("Unable to parse template path: "+path+", skipping...");
            }
        	
             path = string(pathPrefix, actionName, "." , ext);
            try {
                if (locateTemplate(path) != null) {
                    actionConfig = buildActionConfig(path, namespace, actionName, resultsByExtension.get(ext));
                    break;
                }
            } catch (MalformedURLException e) {
                LOG.warn("Unable to parse template path: "+path+", skipping...");
            }
        }
        return actionConfig;
    }


    
    protected ActionConfig buildActionConfig(String path, String namespace, String actionName, ResultTypeConfig resultTypeConfig) {
        final PackageConfig pkg = configuration.getPackageConfig(defaultPackageName);
        return new ActionConfig.Builder(defaultPackageName, "execute", pkg.getDefaultClassRef())
                .addInterceptors(InterceptorBuilder.constructInterceptorReference(new InterceptorLocator() {
                    public Object getInterceptorConfig(String name) {
                        return pkg.getAllInterceptorConfigs().get(name); // recurse package hiearchy
                    }
                }, pkg.getFullDefaultInterceptorRef(),
                Collections.EMPTY_MAP, null, objectFactory))
                .addResultConfig(new ResultConfig.Builder(Action.SUCCESS, resultTypeConfig.getClassName())
                        .addParams(resultTypeConfig.getParams())
                        .addParam(resultTypeConfig.getDefaultResultParam(), path)
                        .build())
                .build();
    }


    public Result handleUnknownResult(ActionContext actionContext, String actionName, 
            ActionConfig actionConfig, String resultCode) throws XWorkException {
        
    	Result result = null;
        PackageConfig pkg = configuration.getPackageConfig(actionConfig.getPackageName());
        String ns = pkg.getNamespace();
        String xasisTemplatePathPrefix = ConfigurableConstants.getProperty("xasis.codebehind.pathPrefix",templatePathPrefix);
        String xasisPathPrefix = determinePath(xasisTemplatePathPrefix, ns);
        String pathPrefix = determinePath(templatePathPrefix, ns);
        //1.先查找项目页面的存放路径
        for (String ext : resultsByExtension.keySet()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Trying to locate result with extension ."+ext+" in directory "+xasisPathPrefix);
            }
            String path = string(xasisPathPrefix, actionName, "-", resultCode, "." , ext);
            try {
                if (locateTemplate(path) != null) {
                    result = buildResult(path, resultCode, resultsByExtension.get(ext), actionContext);
                    break;
                }
            } catch (MalformedURLException e) {
                LOG.warn("Unable to parse template path: "+path+", skipping...");
            }
            
            path = string(xasisPathPrefix, actionName, "." , ext);
            try {
                if (locateTemplate(path) != null) {
                    result = buildResult(path, resultCode, resultsByExtension.get(ext), actionContext);
                    break;
                }
            } catch (MalformedURLException e) {
                LOG.warn("Unable to parse template path: "+path+", skipping...");
            }
            path = string(resultCode, "." , ext);
            try {
                if (locateTemplate(path) != null) {
                    result = buildResult(path, resultCode, resultsByExtension.get(ext), actionContext);
                   break;
                }
            } catch (MalformedURLException e) {
                LOG.warn("Unable to parse template path: "+path+", skipping...");
            }
        }
      //2.再查找原bravo平台系统页面的存放路径
        if (null == result){
            for (String ext : resultsByExtension.keySet()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Trying to locate result with extension ."+ext+" in directory "+pathPrefix);
                }
                String path = string(pathPrefix, actionName, "-", resultCode, "." , ext);
                try {
                    if (locateTemplate(path) != null) {
                        result = buildResult(path, resultCode, resultsByExtension.get(ext), actionContext);
                        break;
                    }
                } catch (MalformedURLException e) {
                    LOG.warn("Unable to parse template path: "+path+", skipping...");
                }
                
                path = string(pathPrefix, actionName, "." , ext);
                try {
                    if (locateTemplate(path) != null) {
                        result = buildResult(path, resultCode, resultsByExtension.get(ext), actionContext);
                        break;
                    }
                } catch (MalformedURLException e) {
                    LOG.warn("Unable to parse template path: "+path+", skipping...");
                }
                path = string(resultCode, "." , ext);
                try {
                    if (locateTemplate(path) != null) {
                        result = buildResult(path, resultCode, resultsByExtension.get(ext), actionContext);
                       break;
                    }
                } catch (MalformedURLException e) {
                    LOG.warn("Unable to parse template path: "+path+", skipping...");
                }
            }
        }
        
     
        if (null != result || !(actionContext.getActionInvocation().getAction() instanceof EntityAction)) return result;
       //获取当前访问的action方法，如果不是query或者add或者view则返回.
        String proxyMethod = actionContext.getActionInvocation().getProxy().getMethod();
        if (!"query".equals(proxyMethod) && !"add".equals(proxyMethod) && !"view".equals(proxyMethod)) return result;
        //如果result为空,则表示当前请求没有对应页面,自动生成页面
    	String path = string(xasisPathPrefix, actionName, "-", resultCode, "." , "ftl");
		String fileName = path.substring(xasisPathPrefix.length(), path.length());
		EntityAction entityAction = (EntityAction)actionContext.getActionInvocation().getAction();
		Object model = entityAction.getModel();
		Generator.generatePage(xasisPathPrefix,fileName,proxyMethod,model.getClass(),configuration);
        try {
            if (locateTemplate(path) != null) {
                result = buildResult(path, resultCode, resultsByExtension.get("ftl"), actionContext);
            }
        } catch (MalformedURLException e) {
            LOG.warn("Unable to parse template path: "+path+", skipping...");
        }
        return result;

    }
    
    protected Result buildResult(String path, String resultCode, ResultTypeConfig config, ActionContext invocationContext) {
        ResultConfig resultConfig = new ResultConfig.Builder(resultCode, config.getClassName())
            .addParams(config.getParams())
            .addParam(config.getDefaultResultParam(), path)
            .build();
        try {
            return objectFactory.buildResult(resultConfig, invocationContext.getContextMap());
        } catch (Exception e) {
            throw new XWorkException("Unable to build codebehind result", e, resultConfig);
        }
    }

    protected String string(String... parts) {
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            sb.append(part);
        }
        return sb.toString();
    }
    
    protected String determinePath(String prefix, String ns) {
        if (ns == null || "/".equals(ns)) {
            ns = "";
        }
        if (ns.length() > 0) {
            if (ns.charAt(0) == '/') {
                ns = ns.substring(1);
            }
            if (ns.charAt(ns.length() - 1) != '/') {
                ns += "/";
            }
        }
        return prefix + ns;
    }
    
    URL locateTemplate(String path) throws MalformedURLException {
        URL template = servletContext.getResource(path);
        if (template != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Loaded template '" + path + "' from servlet context.");
            }
        } else {
            template = ClassLoaderUtils.getResource(path, getClass());
            if (template != null && LOG.isDebugEnabled()) {
                LOG.debug("Loaded template '" + path + "' from class path.");                
            }
        }
        return template;
    }


    /**
     * Not used
     */
	public Object handleUnknownActionMethod(Object action, String methodName) throws NoSuchMethodException {
		throw new NoSuchMethodException();
	}

}
