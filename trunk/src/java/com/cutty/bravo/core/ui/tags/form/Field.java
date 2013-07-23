/* com.cutty.bravo.core.ui.tags.form.Field.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-22 上午04:48:17, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.form;

import javax.servlet.jsp.tagext.TagSupport;

import com.cutty.bravo.core.exception.UIException;
import com.cutty.bravo.core.ui.tags.BaseTag;
import com.cutty.bravo.core.ui.tags.grid.ColumnModel;
import com.cutty.bravo.core.ui.tags.grid.component.ColumnModelBean;

/**
 * <p> 自定义EXT Field标签类 </p>
 * <p>
 * <a href="Field.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class Field extends BaseTag{

	private static final long serialVersionUID = -3529960535030512093L;

	/** 
	 * override BaseTag的regiestOnParent()方法，判断其父标签是否为ColumnModel，是则调用其setEditor()方法
	 * @throws UIException 
	 * @see com.cutty.bravo.core.ui.tags.BaseTag#regiestOnParent()
	 */
	@Override
	protected void regiestOnParent() throws UIException{
		
		BaseTag parentTag = (BaseTag) TagSupport.findAncestorWithClass(this,BaseTag.class);
		if (parentTag instanceof ColumnModel){
			ColumnModelBean ColumnModelComponent = (ColumnModelBean)parentTag.getComponent();
			ColumnModelComponent.setEditor((this.getComponent().getName()));
		} else {
			super.regiestOnParent();
		}
		
	}



}
