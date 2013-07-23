package com.cutty.bravo.core.ui.tags.form;

public class ComboBoxTree extends ComboBox{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6994213352160845674L;

	/**
	 * Tell comboBox,I am a comboBoxTree
	 */   
//	@Override
//	public int doEndTag() throws JspException {
//		BaseTag parentTag = (BaseTag) TagSupport.findAncestorWithClass(this,BaseTag.class);
//		ComboBoxTreeBean comboBoxTreeBean = (ComboBoxTreeBean)this.getComponent();
//		if(parentTag instanceof ColumnModel){
//		    ColumnModelBean columnModelBean =(ColumnModelBean)parentTag.getComponent();
//		    comboBoxTreeBean.setColumnComBoBox("true");
//			columnModelBean.setRenderer("new Ext.ux.comboBoxTreeRenderer('"+comboBoxTreeBean.getName()+"')");
//		}
//		super.setNotComboBoxTree(false);//告诉comboBox,我是comboBoxTree
//		return super.doEndTag();
//	}

}
