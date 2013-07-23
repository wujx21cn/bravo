/* com.cutty.bravo.core.ui.component.BoxComponent.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-10 下午12:13:43, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.component;

/**
 * <p> 定义标签基础结构属性类 </p>
 * <p>
 * <a href="BoxComponent.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class BoxComponent extends Component{

	private static final long serialVersionUID = -7887400970529901637L;
	
	private String autoHeight ;
	private String autoWidth  ;
	private String height  ;
	private String width  ;
	/**
	 * @return the autoHeight
	 */
	public String getAutoHeight() {
		return autoHeight;
	}
	/**
	 * @param autoHeight the autoHeight to set
	 */
	public void setAutoHeight(String autoHeight) {
		this.autoHeight = autoHeight;
	}
	/**
	 * @return the autoWidth
	 */
	public String getAutoWidth() {
		return autoWidth;
	}
	/**
	 * @param autoWidth the autoWidth to set
	 */
	public void setAutoWidth(String autoWidth) {
		this.autoWidth = autoWidth;
	}
	/**
	 * @return the height
	 */
	public String getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}
	/**
	 * @return the width
	 */
	public String getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}
	
}
