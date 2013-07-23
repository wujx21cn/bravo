/* com.cutty.bravo.core.ui.dataRender.JsonWorkFlowButtonDataRenderComponent.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-11-18 上午10:02:30, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.dataRender;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;


import com.cutty.bravo.core.ui.tags.menu.component.MenuBean;

/**
 *
 * <p>
 * <a href="JsonWorkFlowButtonDataRenderComponent.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class JsonWorkFlowButtonDataRenderComponent implements DataRenderComponent{


	public String rend(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		List<MenuBean> menuBeans = (List<MenuBean>)request.getAttribute(com.cutty.bravo.core.ui.Constants.WORKFLOW_BUTTON_CONTEXT_NAME);
		StringBuffer sb = new StringBuffer();
		String buttonId = null;
		if (StringUtils.isEmpty(buttonId)) return sb.toString();
		if (null != menuBeans && 0 < menuBeans.size()){
			Iterator<MenuBean> menuBeansIT = menuBeans.iterator();
			while (menuBeansIT.hasNext()){
				MenuBean menuBean = menuBeansIT.next();
				String MenuBeanID = menuBean.getName();
				sb.append("var ").append(MenuBeanID).append(" = new Ext.menu.Item({ id:'").append(MenuBeanID).append("'");
				sb.append(",handler:").append(menuBean.getHandler()).append(",text:'").append(menuBean.getText()).append("'});");
				sb.append(buttonId).append(".menu.add(").append(MenuBeanID).append(");");
			}
		} 
		return sb.toString();
	}

}
