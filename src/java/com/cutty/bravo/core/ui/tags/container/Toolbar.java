/* com.cutty.bravo.core.ui.tags.container.Toolbar.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-2 上午01:37:44, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.container;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.cutty.bravo.core.ui.Constants;
import com.cutty.bravo.core.ui.tags.BaseTag;
import com.cutty.bravo.core.ui.tags.container.component.PanelBean;
import com.cutty.bravo.core.ui.tags.grid.component.GridPanelBean;

/**
 * <p> 自定义EXT Toolbar标签类 </p>
 * <p>
 * <a href="Toolbar.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class Toolbar extends BaseTag{
	private static final long serialVersionUID = 3018167212486244440L;
	
	/**
	 * Toolbar重载regiestOnParent函数,不需要注册到父类childrenComponent
	 */
	@Override
	protected void regiestOnParent(){
		
		BaseTag parentTag = (BaseTag) TagSupport.findAncestorWithClass(this,BaseTag.class);
		if (parentTag.getComponent() instanceof PanelBean){
			PanelBean PanelComponent = (PanelBean)parentTag.getComponent();
			if (StringUtils.isEmpty(PanelComponent.getBbar()) && StringUtils.isEmpty(PanelComponent.getTbar())){
				PanelComponent.setBbar(this.getComponent().getName());
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see com.cutty.bravo.core.ui.tags.BaseTag#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		//根据传递的参数处理M2M按钮
		renderM2MSelectButton();
		//根据传递的参数处理popu按钮
		renderPopuSelectButton();

		// TODO Auto-generated method stub
		return super.doEndTag();
	}
	/**
	 * 该函数需要迁移到M2MSelectButton.
	 * 如果传递参数中带有M2M_TARGET_GRID等,则自动为toolBar新增一选择按钮,该按钮调用m2mSelect,具体见extjs-bravo.js
	 */
	private void renderM2MSelectButton(){
		BaseTag parentTag = (BaseTag) TagSupport.findAncestorWithClass(this,BaseTag.class);
		if (parentTag.getComponent() instanceof GridPanelBean){
			if (StringUtils.isNotEmpty(pageContext.getRequest().getParameter(Constants.M2M_TARGET_GRID)  )){
				String _M2MSelect_targetGridName = pageContext.getRequest().getParameter(Constants.M2M_TARGET_GRID);
				if (_M2MSelect_targetGridName.equals(parentTag.getComponent().getName())) {
					String _M2MSelect_originGridName = pageContext.getRequest().getParameter(Constants.M2M_ORIGIN_GRID);
					String _M2MSelect_entityName = pageContext.getRequest().getParameter(Constants.M2M_ENTITY_NAME);
					String _M2MSelect_fieldName = pageContext.getRequest().getParameter(Constants.M2M_FIELD_NAME);
					String _M2MSelect_entityId = pageContext.getRequest().getParameter(Constants.M2M_ENTITY_ID);
					StringBuffer m2mSelectButtonStr = new StringBuffer();
					String m2mSelectButtonName = "m2mSelectButton_" +parentTag.getComponent().getName();
					String dataProxy = ServletActionContext.getRequest().getContextPath()+"/ui/dataManageMent!m2mDataManageMent.action";
					m2mSelectButtonStr.append("var ").append(m2mSelectButtonName)
							.append("= new Ext.Button({ id:'").append(m2mSelectButtonName).append("',handler:")
							.append("function(menuObj){m2mSelect(menuObj,'"+dataProxy+"','"+_M2MSelect_originGridName+"','"+_M2MSelect_targetGridName+"','"+_M2MSelect_entityName+"','"+_M2MSelect_fieldName+"','"+_M2MSelect_entityId+"')},iconCls:'m2mSelect',text:'选择所选项'});");
					addOutPutScript(m2mSelectButtonStr.toString());
					List items = new ArrayList();
					items.add(m2mSelectButtonName);
//					items.addAll(this.getChildrenComponentNames());
					this.setChildrenComponentNames(items);
				}

			}
		}
	}
	

	/**
	 * 如果传递参数中带有POPU_TARGET_GRID等,则自动为toolBar新增一选择按钮,该按钮调用PopuSelect,具体见extjs-bravo.js
	 * kukuxia.kevin.hw 2008-11-10
	 */
	private void renderPopuSelectButton(){
		BaseTag parentTag = (BaseTag) TagSupport.findAncestorWithClass(this,BaseTag.class);
		if (parentTag.getComponent() instanceof GridPanelBean){
			if (StringUtils.isNotEmpty(pageContext.getRequest().getParameter(Constants.POPU_TARGET_GRID)  )){
				String _PopuSelect_targetGridName = pageContext.getRequest().getParameter(Constants.POPU_TARGET_GRID);
				if (_PopuSelect_targetGridName.equals(parentTag.getComponent().getName())) {
					String _PopuSelect_name = pageContext.getRequest().getParameter(Constants.POPU_SELECT_NAME);
					String _PopuSelect_hiddenName = pageContext.getRequest().getParameter(Constants.POPU_HIDDEN_NAME);
					String _PopuSelect_valueField = pageContext.getRequest().getParameter(Constants.POPU_VALUE_FIELD);
					String _PopuSelect_displayField = pageContext.getRequest().getParameter(Constants.POPU_DISPLAY_FIELD);
					StringBuffer popuSelectButtonStr = new StringBuffer();
					String popuSelectButtonName = "popuSelectButton_" +parentTag.getComponent().getName();
					popuSelectButtonStr.append("var ").append(popuSelectButtonName)
							.append("= new Ext.Button({ id:'").append(popuSelectButtonName).append("',handler:")
							.append("function(menuObj){popuSelect(menuObj,'"+_PopuSelect_name+"','"+_PopuSelect_targetGridName+"','"+_PopuSelect_valueField+"','"+_PopuSelect_displayField+"','"+_PopuSelect_hiddenName+"')},iconCls:'popuSelect',text:'选择所选项'});");
					addOutPutScript(popuSelectButtonStr.toString());
					List<String> items = new ArrayList<String>();
					items.add(popuSelectButtonName);
//					items.addAll(this.getChildrenComponentNames());
					this.setChildrenComponentNames(items);
				}

			}
		}
	}
}
