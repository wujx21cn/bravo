/* com.cutty.bravo.core.ui.tags.container.component.ScriptBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-9 上午11:32:46, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.container.component;

import com.cutty.bravo.core.ui.component.Component;

/**
 * <p> EXT容器标签Script 属性集合类 </p>
 * <p>
 * <a href="ScriptBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class ScriptBean extends Component{
	private static final long serialVersionUID = -815853907842899695L;
	private String bodyContent;
	private String isRegister = "false";//是否注册到父标签上  默认 ：否
	public String getIsRegister() {
		return isRegister;
	}
	public void setIsRegister(String isRegister) {
		this.isRegister = isRegister;
	}
	/**
	 * @return the bodyContent
	 */
	public String getBodyContent() {
		return bodyContent;
	}
	/**
	 * @param bodyContent the bodyContent to set
	 */
	public void setBodyContent(String bodyContent) {
		this.bodyContent = bodyContent;
	}
	
}
