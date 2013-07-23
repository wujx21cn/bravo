/* com.cutty.bravo.core.ui.bean.ComboBoxBean.java

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
 * <p> EXT标签ComboBox属性集合类 </p>
 * <p>
 * <a href="ComboBoxBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:linjuan0125@gmail.com">Cathy.Lin</a>
 */
public class ComboBoxTreeBean extends ComboBoxBean{

	private static final long serialVersionUID = 6040990250243334936L;
	
	private String dataTreeProxy;
	private String rootId;
	private String rootText;
	private String selectNodeModel;
	
	public String getDataTreeProxy() {
		return dataTreeProxy;
	}
	public void setDataTreeProxy(String dataTreeProxy) {
		this.dataTreeProxy = dataTreeProxy;
	}
	public String getRootId() {
		return rootId;
	}
	public void setRootId(String rootId) {
		this.rootId = rootId;
	}
	public String getRootText() {
		return rootText;
	}
	public void setRootText(String rootText) {
		this.rootText = rootText;
	}
	public String getSelectNodeModel() {
		return selectNodeModel;
	}
	public void setSelectNodeModel(String selectNodeModel) {
		this.selectNodeModel = selectNodeModel;
	}
}
