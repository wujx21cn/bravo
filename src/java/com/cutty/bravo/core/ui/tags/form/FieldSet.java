/* com.cutty.bravo.core.ui.tags.form.FieldSet.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-9 上午 Created by kukuxia.hw
		2008-10-13 去掉FieldSet的默认高度，因为formPanel的autoScroll属性已经默认为true
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.cutty.bravo.core.ui.tags.BaseTag;
import com.cutty.bravo.core.ui.tags.container.Panel;
import com.cutty.bravo.core.ui.tags.form.component.FieldSetBean;
import com.cutty.bravo.core.ui.tags.form.component.FormPanelBean;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.utils.render.FreemarkerTemplateEngine;

/**
 * <p> 自定义EXT FieldSet标签类 </p>
 * <p>
 * <a href="Checkbox.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:huangw100@126.com">kukuxia.kevin.hw</a>
 */
public class FieldSet extends Panel{


	private static final long serialVersionUID = 3597391407723229177L;

	/** 
	 * override BaseTag的doEndTag()方法，当父标签为FormPanel时，自定义构造fieldSet的样式
	 * @see com.cutty.bravo.core.ui.tags.BaseTag#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		BaseTag parentTag = (BaseTag) TagSupport.findAncestorWithClass(this,BaseTag.class);
		if (! (parentTag instanceof FormPanel))return super.doEndTag();
		FormPanelBean formPanelComponent = (FormPanelBean)parentTag.getComponent();
		FieldSetBean fieldSetBean = (FieldSetBean)this.getComponent();
		
		//定义fieldSet的长度
		int fieldSetWidth = Integer.parseInt(formPanelComponent.getWidth()) -35;
		if (StringUtils.isNotEmpty(fieldSetBean.getWidth())){
			fieldSetWidth = Integer.parseInt(fieldSetBean.getWidth());
		} 
		
		//默认每行高度为55
		int  rowHeigth = 55;
		
		
		//该fieldSet的大列数
		int maxCols = 0;
		int maxRows = 0;

		int childFieldSize = this.getChildrenComponentNames().size() ;
		int[] coldefs = new int[childFieldSize];
		int[] rowdefs = new int[childFieldSize];
		String cols = fieldSetBean.getCols();
		String rows = fieldSetBean.getRows();
		
		//先预处理，支持语法
    	FreemarkerTemplateEngine freemarkerTemplateEngine = FreemarkerTemplateEngine.getInstance();
    	cols =  freemarkerTemplateEngine.renderFtl(ApplicationContextKeeper.getServletContext(), "<#escape x as (x)!>"+cols+"</#escape>",getWebContent());
    	rows =  freemarkerTemplateEngine.renderFtl(ApplicationContextKeeper.getServletContext(), "<#escape x as (x)!>"+rows+"</#escape>",getWebContent());
		if (StringUtils.isEmpty(cols)){
			maxCols = childFieldSize;
			Arrays.fill(coldefs, 1);
		} else {
			if (cols.indexOf(";")>-1){
				String[] colsRowsDefinitions = cols.split(";");
				int colIndex = 0;
				for (int i=0;i<colsRowsDefinitions.length;i++){
					String[] colsDefinitions = colsRowsDefinitions[i].split(",");
					int rowColsNum = 0;
					for (int j=0;j<colsDefinitions.length;j++){
						int colsDefinitionsValue = Integer.parseInt(colsDefinitions[j]);
						coldefs[colIndex++] = colsDefinitionsValue;
						rowColsNum+=colsDefinitionsValue;
					}
					if (i == 0){
						maxCols = rowColsNum;
					} 
//					else if (maxCols != rowColsNum){
//						logger.error("the total num of cols for each row should be equal!!!\\每行列的总数必须相等!!!");
//						throw new JspException("the total num of cols for each row should be equal!!!\\每行列的总数必须相等!!!");
//					}
				}
				
			} else {
				if (cols.indexOf(",") > -1){
					String[] colsDefinitions = cols.split(",");
					if (colsDefinitions.length != childFieldSize){
						logger.error("you should define all the cols of the childFild!!!\\你必须定义所有子标签的cols!!!");
						throw new JspException("you should define all the cols of the childFild!!!\\你必须定义所有子标签的cols!!!");
					}
					for (int i=0;i<colsDefinitions.length;i++){
						int colsDefinitionsValue = Integer.parseInt(colsDefinitions[i]);
						maxCols += colsDefinitionsValue;
						coldefs[i] = colsDefinitionsValue;
					}
				}
			}
		}
		
		if (StringUtils.isEmpty(rows)){
			Arrays.fill(rowdefs, 1);
			maxRows = childFieldSize;
		} else {
			if (rows.indexOf(";")>-1){
				String[] RowscolsDefinitions = rows.split(";");
				int rowIndex = 0;
				for (int i=0;i<RowscolsDefinitions.length;i++){
					String[] rowsDefinitions = RowscolsDefinitions[i].split(",");
					int colRowsNum = 0;
					for (int j=0;j<rowsDefinitions.length;j++){
						int rowsDefinitionsValue = Integer.parseInt(rowsDefinitions[j]);
						rowdefs[rowIndex++] = rowsDefinitionsValue;
						if (colRowsNum < rowsDefinitionsValue)colRowsNum = rowsDefinitionsValue;
					}
					maxRows += colRowsNum;
				}
				
			} else {
				if (rows.indexOf(",") > -1){
					String[] rowsDefinitions = rows.split(",");
					if (rowsDefinitions.length != childFieldSize){
						logger.error("you should define all the rows of the childFild!!!\\你必须定义所有子标签的rows!!!");
						throw new JspException("you should define all the rows of the childFild!!!\\你必须定义所有子标签的rows!!!");
					}
					for (int i=0;i<rowsDefinitions.length;i++){
						int rowsDefinitionsValue = Integer.parseInt(rowsDefinitions[i]);
						if (maxRows < rowsDefinitionsValue) maxRows = rowsDefinitionsValue;
						rowdefs[i] = rowsDefinitionsValue;
					}
				}
			}
		}
		fieldSetBean.setRowDefs(rowdefs);
		fieldSetBean.setColDefs(coldefs);
		if (maxCols == 0) maxCols =1;
		if (maxRows == 0) maxRows =1;
		int colWidth = fieldSetWidth/maxCols;
		int[] colWidthDefs = new int[coldefs.length];
		for (int i=0;i<coldefs.length;i++){
			colWidthDefs[i] = coldefs[i] * colWidth;
		}
		fieldSetBean.setColWidthDefs(colWidthDefs);
//		fieldSetBean.setLayoutConfig("{ columns: "+maxCols+" }");
		
		//由formSet设置formPanel的默认属性.
		
		//设置自动高度为true
		if (StringUtils.isEmpty(formPanelComponent.getAutoHeight())){
			formPanelComponent.setAutoHeight("false");
		}
		//设置layout默认值为table
		if (StringUtils.isEmpty(formPanelComponent.getLayout())){
			formPanelComponent.setLayout("table");
		}
		
		//设置layout默认值为table
		if (StringUtils.isEmpty(formPanelComponent.getLayoutConfig())){
			formPanelComponent.setLayoutConfig("{ columns: "+maxCols+" }");
		}
		
		//设置layout默认值为table
		if (StringUtils.isEmpty(formPanelComponent.getDefaults())){
			formPanelComponent.setDefaults("{bodyStyle:'margin-top: 0px;padding:0px 0px 0px 0px'}");
		}

		//设置layout默认值为table
		if (StringUtils.isEmpty(formPanelComponent.getDefaults())){
			formPanelComponent.setDefaults("{bodyStyle:'margin-top: 0px;padding:0px 0px 0px 0px'}");
		}
		
		//设置Form下formSet里的控件名称
		  List<String> childFormSetNames = new ArrayList<String>();
		  String formSetLableName = this.getName();
		  for(int i=0;i<childFieldSize;i++){
			  String childName = formSetLableName + "[" + i + "]";
			  childFormSetNames.add(childName);
		  }
		  formPanelComponent.setChildFormSetNames(childFormSetNames);
			
	
		// TODO Auto-generated method stub
		super.setRegister(false);
		return super.doEndTag();
	}
	
	
}
