/* com.cutty.bravo.core.ui.tags.container.Script.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-10-9 上午11:31:30, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.container;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;

import com.cutty.bravo.core.ui.tags.BaseTag;
import com.cutty.bravo.core.ui.tags.container.component.ScriptBean;

/**
 * <p> 自定义EXT Script标签类 </p>
 * <p>
 * <a href="Script.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class Script extends BaseTag{
	private static final long serialVersionUID = 6473251247250231087L;

	/* (non-Javadoc)
	 * @see com.cutty.bravo.core.ui.tags.BaseTag#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		if (StringUtils.isNotEmpty(this.getBodyContent().getString())){
			ScriptBean component = (ScriptBean)this.getComponent();
			component.setBodyContent(this.getBodyContent().getString());
			super.setRegister(Boolean.parseBoolean(component.getIsRegister()));
		}
		return super.doEndTag();
	}
	
}
