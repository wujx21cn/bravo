/* com.cutty.bravo.core.ui.tags.portal.component.PortalColumnBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-2 下午09:54:51, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.portal.component;

import com.cutty.bravo.core.ui.tags.container.component.Container;

/**
 * <p> EXT标签PortalColumn属性集合类 </p>
 * <p>
 * <a href="PortalColumnBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class PortalColumnBean  extends Container{

	private static final long serialVersionUID = -7697995886000753716L;
	
	private String columnWidth;
	
	/**
	 * @return the columnWidth
	 */
	public String getColumnWidth() {
		return columnWidth;
	}
	/**
	 * @param columnWidth the columnWidth to set
	 */
	public void setColumnWidth(String columnWidth) {
		this.columnWidth = columnWidth;
	}

	
}
