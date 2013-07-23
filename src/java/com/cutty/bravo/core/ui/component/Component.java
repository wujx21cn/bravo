/* com.cutty.bravo.core.ui.components.Component.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-21 上午09:20:33, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.component;

import java.io.Serializable;


/**
 * <p> 所有标签属性类的基类 </p>
 * <p>
 * <a href="Component.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public abstract class Component implements Serializable{

	private static final long serialVersionUID = -5810799161460561020L;

	private String name;
	private String listeners ;
	private String allowDomMove;
	private String applyTo;
	private String autoShow ;
	private String cls ;
	private String ctCls ;
	private String disabledClass ;
	private String hideMode ;
	private String hideParent ;
	private String id ; 
	private String plugins  ;
	private String renderTo  ;
	private String stateEvents ;
	private String stateId  ;
	private String style  ;
	private String xtype  ;
	private String region;
	private Component parentComponent;
	
	//扩展属性，如果该属性存在,则渲染HTML模板,直接输出该HTML
	private String innerHtml;

	public String getName() {
		if (null == name) {
			String beanName = this.getClass().getSimpleName();
			if (beanName.endsWith("Bean")){
				beanName = beanName.substring(0, beanName.length()-4);
			}
			this.name = beanName + this.hashCode();
		} else if (name.indexOf(".")>-1){
			return name.replace(".","___");
		}
	
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the listeners
	 */
	public String getListeners() {
		return listeners;
	}

	/**
	 * @param listeners the listeners to set
	 */
	public void setListeners(String listeners) {
		this.listeners = listeners;
	}

	/**
	 * @return the allowDomMove
	 */
	public String getAllowDomMove() {
		return allowDomMove;
	}

	/**
	 * @param allowDomMove the allowDomMove to set
	 */
	public void setAllowDomMove(String allowDomMove) {
		this.allowDomMove = allowDomMove;
	}

	/**
	 * @return the applyTo
	 */
	public String getApplyTo() {
		return applyTo;
	}

	/**
	 * @param applyTo the applyTo to set
	 */
	public void setApplyTo(String applyTo) {
		this.applyTo = applyTo;
	}

	/**
	 * @return the autoShow
	 */
	public String getAutoShow() {
		return autoShow;
	}

	/**
	 * @param autoShow the autoShow to set
	 */
	public void setAutoShow(String autoShow) {
		this.autoShow = autoShow;
	}

	/**
	 * @return the cls
	 */
	public String getCls() {
		return cls;
	}

	/**
	 * @param cls the cls to set
	 */
	public void setCls(String cls) {
		this.cls = cls;
	}

	/**
	 * @return the ctCls
	 */
	public String getCtCls() {
		return ctCls;
	}

	/**
	 * @param ctCls the ctCls to set
	 */
	public void setCtCls(String ctCls) {
		this.ctCls = ctCls;
	}

	/**
	 * @return the disabledClass
	 */
	public String getDisabledClass() {
		return disabledClass;
	}

	/**
	 * @param disabledClass the disabledClass to set
	 */
	public void setDisabledClass(String disabledClass) {
		this.disabledClass = disabledClass;
	}

	/**
	 * @return the hideMode
	 */
	public String getHideMode() {
		return hideMode;
	}

	/**
	 * @param hideMode the hideMode to set
	 */
	public void setHideMode(String hideMode) {
		this.hideMode = hideMode;
	}

	/**
	 * @return the hideParent
	 */
	public String getHideParent() {
		return hideParent;
	}

	/**
	 * @param hideParent the hideParent to set
	 */
	public void setHideParent(String hideParent) {
		this.hideParent = hideParent;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the plugins
	 */
	public String getPlugins() {
		return plugins;
	}

	/**
	 * @param plugins the plugins to set
	 */
	public void setPlugins(String plugins) {
		this.plugins = plugins;
	}

	/**
	 * @return the renderTo
	 */
	public String getRenderTo() {
		return renderTo;
	}

	/**
	 * @param renderTo the renderTo to set
	 */
	public void setRenderTo(String renderTo) {
		this.renderTo = renderTo;
	}

	/**
	 * @return the stateEvents
	 */
	public String getStateEvents() {
		return stateEvents;
	}

	/**
	 * @param stateEvents the stateEvents to set
	 */
	public void setStateEvents(String stateEvents) {
		this.stateEvents = stateEvents;
	}

	/**
	 * @return the stateId
	 */
	public String getStateId() {
		return stateId;
	}

	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	/**
	 * @return the style
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * @param style the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * @return the xtype
	 */
	public String getXtype() {
		if (null == xtype) {
			String beanName = this.getClass().getSimpleName();
			if (beanName.endsWith("Bean")){
				xtype = beanName.substring(0, beanName.length()-4);
			} else {
				xtype = beanName;
			}
		}
		return xtype;
	}

	/**
	 * @param xtype the xtype to set
	 */
	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

	/**
	 * @return the innerHtml
	 */
	public String getInnerHtml() {
		return innerHtml;
	}

	/**
	 * @param innerHtml the innerHtml to set
	 */
	public void setInnerHtml(String innerHtml) {
		this.innerHtml = innerHtml;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the parentComponent
	 */
	public Component getParentComponent() {
		return parentComponent;
	}

	/**
	 * @param parentComponent the parentComponent to set
	 */
	public void setParentComponent(Component parentComponent) {
		this.parentComponent = parentComponent;
	}

	
}
