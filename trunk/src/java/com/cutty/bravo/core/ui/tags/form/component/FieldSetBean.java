/* com.cutty.bravo.core.ui.tags.form.component.FieldSetBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
          Created by kukuxia.hw
  
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.form.component;



import com.cutty.bravo.core.ui.tags.container.component.PanelBean;

/**
 * <p> EXT标签FieldSet属性集合类 </p>
 * <p>
 * <a href="FieldSetBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:huangw100@126.com">kukuxia.kevin.hw</a>
 */
public class FieldSetBean extends PanelBean{

	private static final long serialVersionUID = -7664816920817061310L;
    private String baseCls;
    private String checkboxName;
    private String checkboxToggle;
    private String itemCls;
    private String labelWidth;
    private String border="false";
    private String layout="form";
    private String layoutConfig;
    //默认每个FieldSet的colspan为1
    private String colspan = "1";
    
    //扩展字段,表明该FieldSet列的排序关系,
    //如"1,1,3|1,1,1,2"表示分为两行,第一行的cols分别为1,1,3第二行为1,1,1,2
    private String cols;
    private String rows;
    
    //扩展字段,表示每个子字段所占的列树与行数,由FieldSet分析得到
    private int[] colDefs;
    private int[] colWidthDefs;
    private int[] rowDefs;    
    


	@Override
	public String getBaseCls() {
		return baseCls;
	}
	@Override
	public void setBaseCls(String baseCls) {
		this.baseCls = baseCls;
	}
	public String getCheckboxName() {
		return checkboxName;
	}
	public void setCheckboxName(String checkboxName) {
		this.checkboxName = checkboxName;
	}
	public String getCheckboxToggle() {
		return checkboxToggle;
	}
	public void setCheckboxToggle(String checkboxToggle) {
		this.checkboxToggle = checkboxToggle;
	}
	public String getItemCls() {
		return itemCls;
	}
	public void setItemCls(String itemCls) {
		this.itemCls = itemCls;
	}
	public String getLabelWidth() {
		return labelWidth;
	}
	public void setLabelWidth(String labelWidth) {
		this.labelWidth = labelWidth;
	}
	@Override
	public String getLayout() {
		return layout;
	}
	/**
	 * @return the layoutConfig
	 */
	@Override
	public String getLayoutConfig() {
		return layoutConfig;
	}
	/**
	 * @param layoutConfig the layoutConfig to set
	 */
	@Override
	public void setLayoutConfig(String layoutConfig) {
		this.layoutConfig = layoutConfig;
	}
	/**
	 * @return the colspan
	 */
	public String getColspan() {
		return colspan;
	}
	/**
	 * @param colspan the colspan to set
	 */
	public void setColspan(String colspan) {
		this.colspan = colspan;
	}
	/**
	 * @return the cols
	 */
	public String getCols() {
		return cols;
	}
	/**
	 * @param cols the cols to set
	 */
	public void setCols(String cols) {
		this.cols = cols;
	}
	/**
	 * @return the rows
	 */
	public String getRows() {
		return rows;
	}
	/**
	 * @param rows the rows to set
	 */
	public void setRows(String rows) {
		this.rows = rows;
	}
	/**
	 * @return the colDefs
	 */
	public int[] getColDefs() {
		return colDefs;
	}
	/**
	 * @param colDefs the colDefs to set
	 */
	public void setColDefs(int[] colDefs) {
		this.colDefs = colDefs;
	}
	/**
	 * @return the colWidthDefs
	 */
	public int[] getColWidthDefs() {
		return colWidthDefs;
	}
	/**
	 * @param colWidthDefs the colWidthDefs to set
	 */
	public void setColWidthDefs(int[] colWidthDefs) {
		this.colWidthDefs = colWidthDefs;
	}
	/**
	 * @return the rowDefs
	 */
	public int[] getRowDefs() {
		return rowDefs;
	}
	/**
	 * @param rowDefs the rowDefs to set
	 */
	public void setRowDefs(int[] rowDefs) {
		this.rowDefs = rowDefs;
	}
	/**
	 * @param layout the layout to set
	 */
	@Override
	public void setLayout(String layout) {
		this.layout = layout;
	}
	/**
	 * @return the border
	 */
	@Override
	public String getBorder() {
		return border;
	}
	/**
	 * @param border the border to set
	 */
	@Override
	public void setBorder(String border) {
		this.border = border;
	}
	
}