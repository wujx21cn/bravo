/* com.cutty.bravo.core.ui.bean.view.TabPanelBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-24 上午12:25:25, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.container.component;

/**
 * <p> EXT容器标签TabPanel 属性集合类 </p>
 * <p>
 * <a href="TabPanelBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class TabPanelBean extends PanelBean{
	private static final long serialVersionUID = -8728750284316892428L;

	private String activeTab ;
	private String animScroll ;
	private String autoTabSelector ;
	private String autoTabs ;
	private String baseCls ;
	private String deferredRender ;
	private String enableTabScroll ="true";
	private String layoutOnTabChange ;
	private String minTabWidth ;
	private String monitorResize ;
	private String plain ;
	private String resizeTabs ;
	private String scrollDuration ;
	private String scrollIncrement ;
	private String scrollRepeatInterval ;
	private String tabMargin ;
	private String tabPosition ;
	private String tabWidth ;
	private String wheelIncrement ;
	
	//重载plugins,tabPanel默认可以关闭其他的tab
	private String plugins = "new Ext.ux.TabCloseMenu()";
	/**
	 * @return the activeTab
	 */
	public String getActiveTab() {
		return activeTab;
	}
	/**
	 * @param activeTab the activeTab to set
	 */
	public void setActiveTab(String activeTab) {
		this.activeTab = activeTab;
	}
	/**
	 * @return the animScroll
	 */
	public String getAnimScroll() {
		return animScroll;
	}
	/**
	 * @param animScroll the animScroll to set
	 */
	public void setAnimScroll(String animScroll) {
		this.animScroll = animScroll;
	}
	/**
	 * @return the autoTabs
	 */
	public String getAutoTabs() {
		return autoTabs;
	}
	/**
	 * @param autoTabs the autoTabs to set
	 */
	public void setAutoTabs(String autoTabs) {
		this.autoTabs = autoTabs;
	}
	/**
	 * @return the autoTabSelector
	 */
	public String getAutoTabSelector() {
		return autoTabSelector;
	}
	/**
	 * @param autoTabSelector the autoTabSelector to set
	 */
	public void setAutoTabSelector(String autoTabSelector) {
		this.autoTabSelector = autoTabSelector;
	}
	/**
	 * @return the baseCls
	 */
	@Override
	public String getBaseCls() {
		return baseCls;
	}
	/**
	 * @param baseCls the baseCls to set
	 */
	@Override
	public void setBaseCls(String baseCls) {
		this.baseCls = baseCls;
	}
	/**
	 * @return the deferredRender
	 */
	public String getDeferredRender() {
		return deferredRender;
	}
	/**
	 * @param deferredRender the deferredRender to set
	 */
	public void setDeferredRender(String deferredRender) {
		this.deferredRender = deferredRender;
	}
	/**
	 * @return the enableTabScroll
	 */
	public String getEnableTabScroll() {
		return enableTabScroll;
	}
	/**
	 * @param enableTabScroll the enableTabScroll to set
	 */
	public void setEnableTabScroll(String enableTabScroll) {
		this.enableTabScroll = enableTabScroll;
	}
	/**
	 * @return the layoutOnTabChange
	 */
	public String getLayoutOnTabChange() {
		return layoutOnTabChange;
	}
	/**
	 * @param layoutOnTabChange the layoutOnTabChange to set
	 */
	public void setLayoutOnTabChange(String layoutOnTabChange) {
		this.layoutOnTabChange = layoutOnTabChange;
	}
	/**
	 * @return the minTabWidth
	 */
	public String getMinTabWidth() {
		return minTabWidth;
	}
	/**
	 * @param minTabWidth the minTabWidth to set
	 */
	public void setMinTabWidth(String minTabWidth) {
		this.minTabWidth = minTabWidth;
	}
	/**
	 * @return the monitorResize
	 */
	@Override
	public String getMonitorResize() {
		return monitorResize;
	}
	/**
	 * @param monitorResize the monitorResize to set
	 */
	@Override
	public void setMonitorResize(String monitorResize) {
		this.monitorResize = monitorResize;
	}
	/**
	 * @return the plain
	 */
	public String getPlain() {
		return plain;
	}
	/**
	 * @param plain the plain to set
	 */
	public void setPlain(String plain) {
		this.plain = plain;
	}
	/**
	 * @return the resizeTabs
	 */
	public String getResizeTabs() {
		return resizeTabs;
	}
	/**
	 * @param resizeTabs the resizeTabs to set
	 */
	public void setResizeTabs(String resizeTabs) {
		this.resizeTabs = resizeTabs;
	}
	/**
	 * @return the scrollDuration
	 */
	public String getScrollDuration() {
		return scrollDuration;
	}
	/**
	 * @param scrollDuration the scrollDuration to set
	 */
	public void setScrollDuration(String scrollDuration) {
		this.scrollDuration = scrollDuration;
	}
	/**
	 * @return the scrollIncrement
	 */
	public String getScrollIncrement() {
		return scrollIncrement;
	}
	/**
	 * @param scrollIncrement the scrollIncrement to set
	 */
	public void setScrollIncrement(String scrollIncrement) {
		this.scrollIncrement = scrollIncrement;
	}
	/**
	 * @return the scrollRepeatInterval
	 */
	public String getScrollRepeatInterval() {
		return scrollRepeatInterval;
	}
	/**
	 * @param scrollRepeatInterval the scrollRepeatInterval to set
	 */
	public void setScrollRepeatInterval(String scrollRepeatInterval) {
		this.scrollRepeatInterval = scrollRepeatInterval;
	}
	/**
	 * @return the tabMargin
	 */
	public String getTabMargin() {
		return tabMargin;
	}
	/**
	 * @param tabMargin the tabMargin to set
	 */
	public void setTabMargin(String tabMargin) {
		this.tabMargin = tabMargin;
	}
	/**
	 * @return the tabPosition
	 */
	public String getTabPosition() {
		return tabPosition;
	}
	/**
	 * @param tabPosition the tabPosition to set
	 */
	public void setTabPosition(String tabPosition) {
		this.tabPosition = tabPosition;
	}
	/**
	 * @return the tabWidth
	 */
	public String getTabWidth() {
		return tabWidth;
	}
	/**
	 * @param tabWidth the tabWidth to set
	 */
	public void setTabWidth(String tabWidth) {
		this.tabWidth = tabWidth;
	}
	/**
	 * @return the wheelIncrement
	 */
	public String getWheelIncrement() {
		return wheelIncrement;
	}
	/**
	 * @param wheelIncrement the wheelIncrement to set
	 */
	public void setWheelIncrement(String wheelIncrement) {
		this.wheelIncrement = wheelIncrement;
	}
	/**
	 * @return the plugins
	 */
	@Override
	public String getPlugins() {
		return plugins;
	}
	/**
	 * @param plugins the plugins to set
	 */
	@Override
	public void setPlugins(String plugins) {
		this.plugins = plugins;
	}
	
	
}
