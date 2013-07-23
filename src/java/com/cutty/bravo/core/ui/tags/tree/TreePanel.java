/* com.cutty.bravo.core.ui.tags.container.TreePanel.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-31 下午08:18:03, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.tree;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cutty.bravo.components.common.domain.MenuFunction;
import com.cutty.bravo.components.common.manager.MenuFunctionManager;
import com.cutty.bravo.core.exception.UIException;
import com.cutty.bravo.core.ui.tags.container.Panel;
import com.cutty.bravo.core.ui.tags.tree.component.TreePanelBean;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;

/**
 * <p> 自定义EXT TreePanel标签类 </p>
 * <p>
 * <a href="TreePanel.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class TreePanel  extends Panel{

	private static final long serialVersionUID = 7115443398246518672L;


	@Override
	protected void regiestOnParent() throws UIException {
		TreePanelBean treePanelBean = (TreePanelBean)this.getComponent();
		String nodeId = treePanelBean.getNodeId();
		if (StringUtils.isEmpty(nodeId)){
			return;
		}
		MenuFunctionManager menuFunctionManager = (MenuFunctionManager)ApplicationContextKeeper.getAppCtx().getBean("menuFunctionManager");
		List<MenuFunction> childList = menuFunctionManager.getChildMenu(Long.valueOf(nodeId));
		boolean hasChild = false;
		if (null != childList && 0 < childList.size()){
			Iterator<MenuFunction> childIT = childList.iterator();
			while (childIT.hasNext()){
				MenuFunction menuFunction = childIT.next();
				if("true".equals(menuFunction.getChecked())) hasChild=true;
			}
		}
		if (hasChild) super.regiestOnParent();
	}
	
}
