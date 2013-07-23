/* com.cutty.bravo.core.ui.tags.form.Multiselect.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Sep 11, 2008 11:33:54 AM, Created by kukuxia.hw
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.form;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.BeanUtils;

import com.cutty.bravo.core.dao.BaseDao;
import com.cutty.bravo.core.ui.tags.form.component.MultiselectBean;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;

/**
 * <p> 自定义EXT Multiselect标签类 </p>
 * <p>
 * <a href="Multiselect.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:huangw100@126.com">kukuxia.hw</a>
 */
public class Multiselect extends Field {

	private static final long serialVersionUID = -4485575417114345385L;

	/** 
	 * override BaseTag的doEndTag()方法,如果该标签内属性dataProxy是以hql方式传回数据，调用此方法处理返回数据格式为json.
	 * @see com.cutty.bravo.core.ui.tags.BaseTag#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		StringBuffer dataArray = new StringBuffer("[");
		BaseDao baseDao= (BaseDao)ApplicationContextKeeper.getAppCtx().getBean("baseDao");
		MultiselectBean multiselectBean = (MultiselectBean)this.getComponent();
		String dataProxy = multiselectBean.getDataProxy();
		if ( dataProxy.indexOf("hql[") > -1){
			String sql = dataProxy.substring(4, dataProxy.length()-1);
			List dataList = baseDao.find(null, sql,true);
			java.util.Iterator dataIterator =  dataList.iterator();
			while (dataIterator.hasNext()){
				Object value = dataIterator.next();
				String displayString = "";
				String valueString = "";
				try {
					displayString = BeanUtils.getProperty(value, multiselectBean.getDisplayField());
					valueString = BeanUtils.getProperty(value, multiselectBean.getValueField());
				} catch (IllegalAccessException e) {
					
				} catch (InvocationTargetException e) {
					
				} catch (NoSuchMethodException e) {
					
				}
				dataArray.append("[" +"'"+ displayString +"'," + "'"+ valueString + "'" + "],");
			}
			
			String multiselectName = multiselectBean.getName();
			String dataArrayString = ("var "+ multiselectName +"_array_data = " + dataArray.toString().substring(0,dataArray.length()-1));
			dataArrayString += "];";
			
			super.addOutPutScript(dataArrayString);
		}
		return super.doEndTag();
	}

}
