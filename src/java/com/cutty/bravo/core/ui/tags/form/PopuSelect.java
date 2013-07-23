/* com.cutty.bravo.core.ui.tags.form.PopuSelect.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Nov 10, 2008 11:07:47 AM, Created kukuxia.kevin.hw
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.form;

import javax.servlet.jsp.tagext.TagSupport;

import com.cutty.bravo.core.exception.UIException;
import com.cutty.bravo.core.ui.tags.BaseTag;




/**
 * <p> 自定义EXT PopuSelect标签类 </p>
 * <p>
 * <a href="PopuSelect.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:huangw100@126.com">kukuxia.kevin.hw</a>
 */
public class PopuSelect extends TriggerField {

	private static final long serialVersionUID = -2709161662689166988L;
	/**
	 * Button重载regiestOnParent函数,直到找到FormPanel容器的时候，把该标签的隐藏域直接注册到该FormPanel容器属性中。
	 * @throws UIException 
	 * @see com.cutty.bravo.core.ui.tags.BaseTag#regiestOnParent()
	 */
	@Override
	protected void regiestOnParent() throws UIException{
		String hiddenName = "Hidden_" + this.getComponent().getName();
		BaseTag parentTag = (BaseTag) TagSupport.findAncestorWithClass(this,BaseTag.class);
		while(!(parentTag instanceof FormPanel)){
			parentTag = findParnentTag(parentTag);
		}
		 parentTag.getChildrenComponentNames().add(hiddenName);
		 super.regiestOnParent();	
	}

	private BaseTag findParnentTag(BaseTag parentTag){
		parentTag = (BaseTag) TagSupport.findAncestorWithClass(parentTag,BaseTag.class);
		return parentTag;
	}
}
