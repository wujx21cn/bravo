/* com.cutty.bravo.core.ui.tags.form.ComboBox.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-22 上午04:48:17, Created by Cathy.Lin
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
/*
 * History:
 *     XX 2008-9-19 rtr
 */
package com.cutty.bravo.core.ui.tags.form;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.BeanUtils;

import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.ui.tags.BaseTag;
import com.cutty.bravo.core.ui.tags.form.component.ComboBoxBean;
import com.cutty.bravo.core.ui.tags.grid.ColumnModel;
import com.cutty.bravo.core.ui.tags.grid.component.ColumnModelBean;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;

/**
 * <p> 自定义EXT ComboBox标签类 </p>
 * <p>
 * <a href="ComboBox.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:linjuan0125@gmail.com">Cathy Lin</a>
 */
public class ComboBox extends TriggerField{

	private static final long serialVersionUID = 268158281830352860L;
	private boolean isNotComboBoxTree = true;	

	public boolean isNotComboBoxTree() {
		return isNotComboBoxTree;
	
	}
	public void setNotComboBoxTree(boolean isNotComboBoxTree) {
		this.isNotComboBoxTree = isNotComboBoxTree;
	}
	/** 
	 * override BaseTag的doEndTag()方法,如果该标签内属性dataProxy是以hql方式传回数据，调用此方法.
	 * @see com.cutty.bravo.core.ui.tags.BaseTag#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		BaseTag parentTag = (BaseTag) TagSupport.findAncestorWithClass(this,BaseTag.class);
		ComboBoxBean comboBoxBean = (ComboBoxBean)this.getComponent();
		//判断父标签是否为Column，如果是则将ColumnComBoBox设置为true
		if(isNotComboBoxTree){//如果不是ComboBoxTree
		  if (parentTag instanceof ColumnModel){
		      ColumnModelBean columnModelBean =(ColumnModelBean)parentTag.getComponent();
			  comboBoxBean.setColumnComBoBox("true");
			  columnModelBean.setRenderer("new Ext.ux.comboBoxRenderer('"+comboBoxBean.getName()+"')");
		  }
		}
		StringBuffer dataArray = new StringBuffer("[");
		BaseDao baseDao= (BaseDao)ApplicationContextKeeper.getAppCtx().getBean("baseDao");
		
		String dataProxy = comboBoxBean.getDataProxy();
		List dataList = null; 
		if (dataProxy!=null && dataProxy.indexOf("hql[") > -1){
			String sql = dataProxy.substring(4, dataProxy.length()-1);
			 dataList = baseDao.find(null, sql,true);
			java.util.Iterator dataIterator =  dataList.iterator();
			while (dataIterator.hasNext()){
				Object value = dataIterator.next();
				String displayString = "";
				String valueString = "";
				try {
					displayString = BeanUtils.getProperty(value, comboBoxBean.getDisplayField());
					valueString = BeanUtils.getProperty(value, comboBoxBean.getValueField());
				} catch (IllegalAccessException e) {
					logger.error(e);
				} catch (InvocationTargetException e) {
					logger.error(e);
				} catch (NoSuchMethodException e) {
					logger.error(e);
				}
				dataArray.append("[" +"'"+ displayString +"'," + "'"+ valueString + "'" + "],");
			}
			
			String comboBoxName = comboBoxBean.getName();
			String dataArrayString = "";
			if(dataList.size()>0){
			  dataArrayString = ("var "+ comboBoxName +"_array_data = " + dataArray.toString().substring(0,dataArray.length()-1));
			}else{
			  dataArrayString = ("var "+ comboBoxName +"_array_data = " + dataArray.toString());	
			}
			dataArrayString += "];";
			
			super.addOutPutScript(dataArrayString);
		}
		return super.doEndTag();
	}

}
