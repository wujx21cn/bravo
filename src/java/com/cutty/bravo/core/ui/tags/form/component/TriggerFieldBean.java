/* com.cutty.bravo.core.ui.bean.TriggerFieldBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-8-22 上午02:29:19, Created by Cathy.Lin
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.form.component;


/**
 * <p> EXT标签TriggerField属性集合类 </p>
 * <p>
 * <a href="TriggerFieldBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:linjuan0125@gmail.com">Cathy Lin</a>
 */
public class TriggerFieldBean extends TextFieldBean{
	private static final long serialVersionUID = 8901107681028295760L;
	private String autoCreate;
	private String  hideTrigger;
	private String triggerClass ;
	/**
	 * @return the autoCreate
	 */
	@Override
	public String getAutoCreate() {
		return autoCreate;
	}
	/**
	 * @param autoCreate the autoCreate to set
	 */
	@Override
	public void setAutoCreate(String autoCreate) {
		this.autoCreate = autoCreate;
	}
	/**
	 * @return the hideTrigger
	 */
	public String getHideTrigger() {
		return hideTrigger;
	}
	/**
	 * @param hideTrigger the hideTrigger to set
	 */
	public void setHideTrigger(String hideTrigger) {
		this.hideTrigger = hideTrigger;
	}
	/**
	 * @return the triggerClass
	 */
	public String getTriggerClass() {
		return triggerClass;
	}
	/**
	 * @param triggerClass the triggerClass to set
	 */
	public void setTriggerClass(String triggerClass) {
		this.triggerClass = triggerClass;
	}

	
}
