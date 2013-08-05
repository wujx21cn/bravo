/* com.cutty.bravo.core.ui.tags.progressbar.component.progressbarBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2012-5-21 下午02:50:31, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 BullShit Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.progressbar.component;

import com.cutty.bravo.core.ui.component.BoxComponent;

/**
 *
 * <p>
 * <a href="progressbarBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class ProgressBarBean extends BoxComponent {
	private static final long serialVersionUID = 1608175161314212640L;
	
	private String baseCls ;
	private String text;
	private String textEl;
	private String value;
	public String getBaseCls() {
		return baseCls;
	}
	public void setBaseCls(String baseCls) {
		this.baseCls = baseCls;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTextEl() {
		return textEl;
	}
	public void setTextEl(String textEl) {
		this.textEl = textEl;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
