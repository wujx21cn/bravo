/* com.cutty.bravo.core.ui.tags.container.component.Container.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-2 上午01:29:52, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.container.component;

import com.cutty.bravo.core.ui.component.BoxComponent;

/**
 * <p> 所有容器标签属性类的父类 </p>
 * <p>
 * <a href="Container.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class Container  extends BoxComponent{
	private static final long serialVersionUID = -2632986059379479564L;
	
	private String activeItem;
	private String autoDestroy;
	private String bufferResize;
	private String defaultType;
	private String defaults ;
	private String hideBorders ;
	private String items ;
	private String layout ;
	private String layoutConfig ;
	private String monitorResize  ;
	/**
	 * @return the activeItem
	 */
	public String getActiveItem() {
		return activeItem;
	}
	/**
	 * @param activeItem the activeItem to set
	 */
	public void setActiveItem(String activeItem) {
		this.activeItem = activeItem;
	}
	/**
	 * @return the autoDestroy
	 */
	public String getAutoDestroy() {
		return autoDestroy;
	}
	/**
	 * @param autoDestroy the autoDestroy to set
	 */
	public void setAutoDestroy(String autoDestroy) {
		this.autoDestroy = autoDestroy;
	}
	/**
	 * @return the bufferResize
	 */
	public String getBufferResize() {
		return bufferResize;
	}
	/**
	 * @param bufferResize the bufferResize to set
	 */
	public void setBufferResize(String bufferResize) {
		this.bufferResize = bufferResize;
	}
	/**
	 * @return the defaultType
	 */
	public String getDefaultType() {
		return defaultType;
	}
	/**
	 * @param defaultType the defaultType to set
	 */
	public void setDefaultType(String defaultType) {
		this.defaultType = defaultType;
	}
	/**
	 * @return the defaults
	 */
	public String getDefaults() {
		return defaults;
	}
	/**
	 * @param defaults the defaults to set
	 */
	public void setDefaults(String defaults) {
		this.defaults = defaults;
	}
	/**
	 * @return the hideBorders
	 */
	public String getHideBorders() {
		return hideBorders;
	}
	/**
	 * @param hideBorders the hideBorders to set
	 */
	public void setHideBorders(String hideBorders) {
		this.hideBorders = hideBorders;
	}
	/**
	 * @return the items
	 */
	public String getItems() {
		return items;
	}
	/**
	 * @param items the items to set
	 */
	public void setItems(String items) {
		this.items = items;
	}
	/**
	 * @return the layout
	 */
	public String getLayout() {
		return layout;
	}
	/**
	 * @param layout the layout to set
	 */
	public void setLayout(String layout) {
		this.layout = layout;
	}
	/**
	 * @return the layoutConfig
	 */
	public String getLayoutConfig() {
		return layoutConfig;
	}
	/**
	 * @param layoutConfig the layoutConfig to set
	 */
	public void setLayoutConfig(String layoutConfig) {
		this.layoutConfig = layoutConfig;
	}
	/**
	 * @return the monitorResize
	 */
	public String getMonitorResize() {
		return monitorResize;
	}
	/**
	 * @param monitorResize the monitorResize to set
	 */
	public void setMonitorResize(String monitorResize) {
		this.monitorResize = monitorResize;
	}
	

}
