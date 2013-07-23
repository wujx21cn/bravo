
package com.cutty.bravo.core.ui.tags.grid.component;

import java.util.ArrayList;
import java.util.List;


/**
 *<p> EXT标签EditorGridPanel属性集合类 </p>
 * <p>
 * <a href="EditorGridPanelBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:linjuan0125@gmail.com">Cathy Lin</a>
 */

public class EditorGridPanelBean extends GridPanelBean {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3511760389141083668L;
	private String autoEncode ;
    private String clicksToEdit ;
    private String selModel ;
     private String editor;
    
   //实体的ID,默认为"id"
     private String entityKey="id";
    
	//扩展属性，所有columnModel组件均注册到该属性中。
	private List<ColumnModelBean> childColumns = new ArrayList<ColumnModelBean>();  
	
	/**
	 * @return the childColumns
	 */
	@Override
	public List<ColumnModelBean> getChildColumns() {
		return childColumns;
	}
	/**
	 * @param childColumns the childColumns to set
	 */
	@Override
	public void setChildColumns(List<ColumnModelBean> childColumns) {
		this.childColumns = childColumns;
	}
	/**
	 * @return the autoEncode
	 */
	public String getAutoEncode() {
		return autoEncode;
	}
	/**
	 * @param autoEncode the autoEncode to set
	 */
	public void setAutoEncode(String autoEncode) {
		this.autoEncode = autoEncode;
	}
	/**
	 * @return the clicksToEdit
	 */
	public String getClicksToEdit() {
		return clicksToEdit;
	}
	/**
	 * @param clicksToEdit the clicksToEdit to set
	 */
	public void setClicksToEdit(String clicksToEdit) {
		this.clicksToEdit = clicksToEdit;
	}
	/**
	 * @return the selModel
	 */
	@Override
	public String getSelModel() {
		return selModel;
	}
	/**
	 * @param selModel the selModel to set
	 */
	@Override
	public void setSelModel(String selModel) {
		this.selModel = selModel;
	}

	/**
	 * @return the editor
	 */
	public String getEditor() {
		return editor;
	}
	/**
	 * @param editor the editor to set
	 */
	public void setEditor(String editor) {
		this.editor = editor;
	}
	/**
	 * @return the entityKey
	 */
	@Override
	public String getEntityKey() {
		return entityKey;
	}
	/**
	 * @param entityKey the entityKey to set
	 */
	@Override
	public void setEntityKey(String entityKey) {
		this.entityKey = entityKey;
	}

	
}
