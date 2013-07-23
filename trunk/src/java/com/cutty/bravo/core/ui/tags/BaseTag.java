/* com.cutty.bravo.core.ui.tags.BaseTag.java

 {{IS_NOTE
 Purpose:
 
 Description:
 
 History:
 2008-8-22 上午02:32:24, Created by Jason.Wu
 }}IS_NOTE

 Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

 */
package com.cutty.bravo.core.ui.tags;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.exception.UIException;
import com.cutty.bravo.core.ui.component.Component;
import com.cutty.bravo.core.utils.render.UITemplateRender;
import com.cutty.bravo.core.web.handler.RequestHandler;
/**
 * <p> 所有自定义EXT标签类的基类 </p>
 * <p>
 * <a href="BaseTag.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public abstract class BaseTag extends BodyTagSupport implements DynamicAttributes {

	private static final long serialVersionUID = 5804729964141846124L;

	protected final Log logger = LogFactory.getLog(getClass());

	private Component component;

	private Component parentComponent;

	private List<String> childrenComponentNames = new ArrayList<String>();

	/**
	 * 标签开始HTML模板名
	 */
	protected String startHtmlTemplateName;

	/**
	 * 标签结束HTML模板名
	 */
	protected String endHtmlTemplateName;

	/**
	 * 标签开始脚本模板名
	 */
	protected String startScriptTemplateName;

	/**
	 * 标签结束脚本模板名
	 */
	protected String endScriptTemplateName;

	/**
	 * 输出脚本文本在pageContext中的key值
	 */
	protected String OUTPUT_SCRIPT_CONTEXT_KEY = "OUTPUT_SCRIPT_CONTEXT_KEY";

	/**
	 * 输出HTML文本在pageContext中的key值
	 */
	protected String OUTPUT_HTML_CONTEXT_KEY = "OUTPUT_HTML_CONTEXT_KEY";

	/**
	 * 定义该标签的名称,可选项;如果不定义将产生一随机码
	 */
	private String name;

	/**
	 * 定义该标签是否注册到父标签上
	 */
	private boolean isRegister = true;

	/**
	 * BaseTag的构造函数，每次都要执行iniTag()方法，Class.Page重写了该constructor
	 */
	public BaseTag(){
		initTag();
	}
	/**
	 * 实现DynamicAttributes接口使标签支持动态参数,借此分解UI BEAN与标签库藕荷.
	 * 参数BeanUtils动态设置,重新包装异常信息,将该异常信息转换为BEAN 没有property异常/
	 */
	public void setDynamicAttribute(String uri, String localName, Object value)
			throws JspException {
		try {
			BeanUtils.setProperty(component, localName, value);
		} catch (IllegalAccessException e) {
			logger.error(e);
			throw new JspException(
					"the ui bean has no such property!!!\n该UI没有不存在该属性!!!");
		} catch (InvocationTargetException e) {
			logger.error(e);
			throw new JspException(
					"the ui bean has no such property!!!\n该UI没有不存在该属性!!!");
		}

	}

	/**
	 * 初始化TAG,主要设置模板名称
	 */
	public void initTag(){
		//获得该类所在的包的名称 例如Class.ViewPort所在的包名：com.cutty.bravo.core.ui.tags.container
		String packageName = this.getClass().getPackage().getName();
		//获得该类的名称的简称,Class.getName获得的是带修饰符，返回类型，参数列表的完整的类名
		String classShotName = this.getClass().getSimpleName();
		//以下为UI EXT模板命名规范：封装的标签名+"_Script/Html_Strat/End"
		this.startScriptTemplateName = classShotName + "_Script_Start";
		this.startHtmlTemplateName = classShotName + "_Html_Start";
		this.endScriptTemplateName = classShotName + "_Script_End";
		this.endHtmlTemplateName = classShotName + "_Html_End";
		try {
			//利用反射机制，创建该标签类对应的Bean的实例，例如该标签类为ViewPort,就相当于new一个ViewPortBean的实例
			//Bean都放在该标签类所在包下的component包里，Bean的命名规范：标签类的类名+"Bean"
			Class componentBeanClass = Class.forName(packageName+".component."+classShotName+"Bean");
			this.component = (Component)componentBeanClass.newInstance();
		} catch (ClassNotFoundException e) {
			logger.error("cann't find the bean replay to this tag!!!\n无法获取该标签对应的BEAN!!!");
			logger.error(e);
		} catch (InstantiationException e) {
			logger.error("cann't init the bean replay to this tag!!!\n无法初始化该标签对应的BEAN!!!");
			logger.error(e);
		} catch (IllegalAccessException e) {
			logger.error("cann't init the bean replay to this tag!!!\n无法初始化该标签对应的BEAN!!!");
			logger.error(e);
		}
	}



	/**
	 * 设置当前TAG的父BEAN
     * @throws JspException 
	 */
	@Override
	public void doInitBody() throws JspException {

	}

   /**
    * 遇到标签的"<"符号时，开始执行 doStartTag()方法
    * @throws JspException 
    */
	@Override
	public int doStartTag() throws JspException {
		//返回该标签的父标签，也就是它的上一层标签.例如：编译到ViewPort标签的时候，它的父标签就是Page标签.
		BaseTag parentTag = (BaseTag) TagSupport.findAncestorWithClass(this,BaseTag.class);
		if (null != parentTag) {
			this.parentComponent = parentTag.getComponent();
			this.getComponent().setParentComponent(parentTag.getComponent());
		}
		//返回自定义标签start的script模板内容字符串
		String startScriptStr = UITemplateRender.getInstance().getOutPutStringByTemplateName(startScriptTemplateName,getWebContent());
	    //增量添加在pageContext全局变量OUTPUT_SCRIPT_CONTEXT_KEY里
		addOutPutScript(startScriptStr);
		if (StringUtils.isEmpty(component.getInnerHtml())) {
			//返回自定义标签start的html模板内容字符串
			String startHtmlStr = UITemplateRender.getInstance().getOutPutStringByTemplateName(startHtmlTemplateName,getWebContent());
			//增量添加在pageContext全局变量OUTPUT_HTML_CONTEXT_KEY里
			addOutPutHtml(startHtmlStr);
		}
		return super.doStartTag();
	}
	
	/**
	 * 遇到标签的"/>"符号时，开始执行 doEndTag()方法
     * @throws JspException 
	 */
	@Override
	public int doEndTag() throws JspException {
		//返回自定义标签end的script模板内容字符串
		String endScriptStr = UITemplateRender.getInstance().getOutPutStringByTemplateName(endScriptTemplateName,getWebContent());
		//增量添加在pageContext全局变量OUTPUT_SCRIPT_CONTEXT_KEY里
		addOutPutScript(endScriptStr);
		if (StringUtils.isEmpty(component.getInnerHtml())){
			//返回自定义标签end的html模板内容字符串
			String endHtmlStr = UITemplateRender.getInstance().getOutPutStringByTemplateName(endHtmlTemplateName,getWebContent());
			//增量添加在pageContext全局变量OUTPUT_HTML_CONTEXT_KEY里
			addOutPutHtml(endHtmlStr);
		} else {
			addOutPutHtml(component.getInnerHtml());
		}
		if(isRegister){
			try {
				regiestOnParent();
			} catch (UIException e) {
				throw new JspException(e);
			}
		}
		return super.doEndTag();
	}
	
	/**
	 * 把该标签的名称注册到其父标签的List<String> childrenComponentNames里
	 */	
	protected void regiestOnParent()throws UIException{
		BaseTag parentTag = (BaseTag) TagSupport.findAncestorWithClass(this,BaseTag.class);
		if (null != parentTag) parentTag.getChildrenComponentNames().add(component.getName());
	}

	/**
	 * 向request变量添加输出脚本变量
	 * @param addStr freemarker解析Script模板后内容
	 */
	protected void addOutPutScript(String addStr){
		if (StringUtils.isEmpty(addStr)) return;
		StringBuffer sb = (StringBuffer)pageContext.getAttribute(OUTPUT_SCRIPT_CONTEXT_KEY);
		if (null == sb ){
			sb = new StringBuffer();
		}
		sb.append(addStr);
		pageContext.setAttribute(OUTPUT_SCRIPT_CONTEXT_KEY, sb);
	}
	
	/**
	 * 向request变量添加输出HTML变量
	 * @param addStr freemarker解析Html模板后内容
	 */
	protected void addOutPutHtml(String addStr){
		if (StringUtils.isEmpty(addStr)) return;
		StringBuffer sb = (StringBuffer)pageContext.getAttribute(OUTPUT_HTML_CONTEXT_KEY);
		if (null == sb ){
			sb = new StringBuffer();
		}
		sb.append(addStr);
		pageContext.setAttribute(OUTPUT_HTML_CONTEXT_KEY, sb);
	}
	/**
	 * 预处理标签父子关系.将UI bean附加到父节点中
	 */
	@Override
	public int doAfterBody() throws JspException {
		return super.doAfterBody();
	}

	public Component getComponent() {
		return component;
	}
	
	protected void setComponent(Component component) {
		this.component = component;
	}

	public Component getParentComponent() {
		return parentComponent;
	}

	public void setParentComponent(Component parentComponent) {
		this.parentComponent = parentComponent;
	}


	public List<String> getChildrenComponentNames() {
		return childrenComponentNames;
	}
	public void setChildrenComponentNames(List<String> childrenComponentNames) {
		this.childrenComponentNames = childrenComponentNames;
	}
    /**
     * 获取当前自定义的ext控件名
     * @return String ext控件名
     */
	public String getName() {
		if (null == name && null != component) {
			name = component.getName();
		} else {
			if (null == name) this.name = this.getClass().getSimpleName() + this.hashCode();
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
		if (null != component) component.setName(name);
	}
	
	public boolean isRegister() {
		return isRegister;
	}
	public void setRegister(boolean isRegister) {
		this.isRegister = isRegister;
	}
	
    /**
     * 获取当前WEB的CONTEXT内容
     * @return Map
     */
    protected Map getWebContent(){
    	Map context = new HashMap();
    	Enumeration sessionEnum = pageContext.getAttributeNamesInScope(PageContext.SESSION_SCOPE);
    	Enumeration requestEnum = pageContext.getAttributeNamesInScope(PageContext.REQUEST_SCOPE);
    	Enumeration pageEnum = pageContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE);
    	while (sessionEnum.hasMoreElements()){
    		String key = (String)sessionEnum.nextElement();
    		context.put(key, pageContext.getSession().getAttribute(key));
    	}
    	while (requestEnum.hasMoreElements()){
    		String key = (String)requestEnum.nextElement();
    		context.put(key, pageContext.getRequest().getAttribute(key));
    	}
    	while (pageEnum.hasMoreElements()){
    		String key = (String)pageEnum.nextElement();
    		context.put(key, pageContext.getAttribute(key));
    	}

    	context.put("requestHandler", RequestHandler.getContextRequestHandler());
    	context.put("bravoHome", RequestHandler.getContextRequestHandler().getRequest().getContextPath());
    	context.put("extjsWidgetPath",ConfigurableConstants.getProperty("ui.extjs.widget.path", "widgets/ext-3.3.1"));
    	context.put("parentComponent",this.parentComponent);
    	context.put("childrenComponentNames",this.childrenComponentNames);
    	context.put("component",this.component);
    	return context;
    }

}
