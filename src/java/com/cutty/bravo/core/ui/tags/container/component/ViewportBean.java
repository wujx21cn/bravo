/* com.cutty.bravo.core.ui.tags.view.component.ViewportBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-25 下午10:23:33, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.container.component;

/**
 * <p> EXT容器标签Viewport 属性集合类 </p>
 * <p>
 * <a href="ViewportBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class ViewportBean  extends Container{

	private static final long serialVersionUID = 8256805305735520212L;

	private String layout;

	@Override
	public String getLayout() {
		return layout;
	}

	@Override
	public void setLayout(String layout) {
		this.layout = layout;
	}
	
}
