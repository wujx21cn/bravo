/* com.cutty.bravo.core.ui.tags.menu.component.BaseItemBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-10 下午01:26:43, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.menu.component;

import com.cutty.bravo.core.ui.component.Component;

/**
 *
 * <p> 自定义BaseItem标签属性集合类 </p>
 * <a href="BaseItemBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class BaseItemBean extends Component{

	private static final long serialVersionUID = 3822052836666901791L;
	
	private String activeClass;
	private String canActivate;
	private String handler;
	private String hideDelay;
	private String hideOnClick;
	private String scope;
	/**
	 * @return the activeClass
	 */
	public String getActiveClass() {
		return activeClass;
	}
	/**
	 * @param activeClass the activeClass to set
	 */
	public void setActiveClass(String activeClass) {
		this.activeClass = activeClass;
	}
	/**
	 * @return the canActivate
	 */
	public String getCanActivate() {
		return canActivate;
	}
	/**
	 * @param canActivate the canActivate to set
	 */
	public void setCanActivate(String canActivate) {
		this.canActivate = canActivate;
	}
	/**
	 * @return the handler
	 */
	public String getHandler() {
		return handler;
	}
	/**
	 * @param handler the handler to set
	 */
	public void setHandler(String handler) {
		this.handler = handler;
	}
	/**
	 * @return the hideDelay
	 */
	public String getHideDelay() {
		return hideDelay;
	}
	/**
	 * @param hideDelay the hideDelay to set
	 */
	public void setHideDelay(String hideDelay) {
		this.hideDelay = hideDelay;
	}
	/**
	 * @return the hideOnClick
	 */
	public String getHideOnClick() {
		return hideOnClick;
	}
	/**
	 * @param hideOnClick the hideOnClick to set
	 */
	public void setHideOnClick(String hideOnClick) {
		this.hideOnClick = hideOnClick;
	}
	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}
	/**
	 * @param scope the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	
}
