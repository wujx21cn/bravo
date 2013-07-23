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
public class ComboBoxBean extends TriggerFieldBean{
	
	private static final long serialVersionUID = 5629633963308808526L;
	private String allQuery;
	private String autoCreate ;
	private String displayField ;
	private String editable ;
	private String forceSelection ;
	private String handleHeight ;
	private String hiddenName ;
	private String lazyInit ;
	private String lazyRender ;
	private String listAlign ;
	private String listClass ;
	private String listWidth;
	private String loadingText;
	private String maxHeight;
	private String minChars;
	private String minListWidth;
	//删除mode属性，由dataProxy判断
	///private String mode;
	private String pageSize;
	private String queryDelay;
	private String queryParam;
	private String resizable;
	private String selectOnFocus;
	private String selectedClass;
	private String shadow;
	private String store;
	private String title;
	private String tpl;
	private String transform;
	private String triggerAction;
	private String triggerClass;
	private String typeAhead;
	private String tpeAheadDelay;
	private String valueField;
	private String valueNotFoundText;
	private String dataProxy;
	private String ContextDataName;
	//覆写TextFieldBean，默认为选择
	private String emptyText ="选择";
	

	//判断该combobox是否为grid中的combobox，默认为否，该判断条件在combobox的tag中处理。
	private String columnComBoBox ="false";
	/**
	 * @return the dataProxy
	 */
	public String getDataProxy() {
		return dataProxy;
	}
	/**
	 * @param dataProxy the dataProxy to set
	 */
	public void setDataProxy(String dataProxy) {
		this.dataProxy = dataProxy;
	}
	/**
	 * @return the allQuery
	 */
	public String getAllQuery() {
		return allQuery;
	}
	/**
	 * @param allQuery the allQuery to set
	 */
	public void setAllQuery(String allQuery) {
		this.allQuery = allQuery;
	}
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
	 * @return the displayField
	 */
	public String getDisplayField() {
		return displayField;
	}
	/**
	 * @param displayField the displayField to set
	 */
	public void setDisplayField(String displayField) {
		this.displayField = displayField;
	}
	/**
	 * @return the editable
	 */
	public String getEditable() {
		return editable;
	}
	/**
	 * @param editable the editable to set
	 */
	public void setEditable(String editable) {
		this.editable = editable;
	}
	/**
	 * @return the forceSelection
	 */
	public String getForceSelection() {
		return forceSelection;
	}
	/**
	 * @param forceSelection the forceSelection to set
	 */
	public void setForceSelection(String forceSelection) {
		this.forceSelection = forceSelection;
	}
	/**
	 * @return the handleHeight
	 */
	public String getHandleHeight() {
		return handleHeight;
	}
	/**
	 * @param handleHeight the handleHeight to set
	 */
	public void setHandleHeight(String handleHeight) {
		this.handleHeight = handleHeight;
	}
	/**
	 * @return the hiddenName
	 */
	public String getHiddenName() {
		return hiddenName;
	}
	/**
	 * @param hiddenName the hiddenName to set
	 */
	public void setHiddenName(String hiddenName) {
		this.hiddenName = hiddenName;
	}
	/**
	 * @return the lazyInit
	 */
	public String getLazyInit() {
		return lazyInit;
	}
	/**
	 * @param lazyInit the lazyInit to set
	 */
	public void setLazyInit(String lazyInit) {
		this.lazyInit = lazyInit;
	}
	/**
	 * @return the lazyRender
	 */
	public String getLazyRender() {
		return lazyRender;
	}
	/**
	 * @param lazyRender the lazyRender to set
	 */
	public void setLazyRender(String lazyRender) {
		this.lazyRender = lazyRender;
	}
	/**
	 * @return the listAlign
	 */
	public String getListAlign() {
		return listAlign;
	}
	/**
	 * @param listAlign the listAlign to set
	 */
	public void setListAlign(String listAlign) {
		this.listAlign = listAlign;
	}
	/**
	 * @return the listClass
	 */
	public String getListClass() {
		return listClass;
	}
	/**
	 * @param listClass the listClass to set
	 */
	public void setListClass(String listClass) {
		this.listClass = listClass;
	}
	/**
	 * @return the listWidth
	 */
	public String getListWidth() {
		return listWidth;
	}
	/**
	 * @param listWidth the listWidth to set
	 */
	public void setListWidth(String listWidth) {
		this.listWidth = listWidth;
	}
	/**
	 * @return the loadingText
	 */
	public String getLoadingText() {
		return loadingText;
	}
	/**
	 * @param loadingText the loadingText to set
	 */
	public void setLoadingText(String loadingText) {
		this.loadingText = loadingText;
	}
	/**
	 * @return the maxHeight
	 */
	public String getMaxHeight() {
		return maxHeight;
	}
	/**
	 * @param maxHeight the maxHeight to set
	 */
	public void setMaxHeight(String maxHeight) {
		this.maxHeight = maxHeight;
	}
	/**
	 * @return the minChars
	 */
	public String getMinChars() {
		return minChars;
	}
	/**
	 * @param minChars the minChars to set
	 */
	public void setMinChars(String minChars) {
		this.minChars = minChars;
	}
	/**
	 * @return the minListWidth
	 */
	public String getMinListWidth() {
		return minListWidth;
	}
	/**
	 * @param minListWidth the minListWidth to set
	 */
	public void setMinListWidth(String minListWidth) {
		this.minListWidth = minListWidth;
	}

	/**
	 * @return the pageSize
	 */
	public String getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return the queryDelay
	 */
	public String getQueryDelay() {
		return queryDelay;
	}
	/**
	 * @param queryDelay the queryDelay to set
	 */
	public void setQueryDelay(String queryDelay) {
		this.queryDelay = queryDelay;
	}
	/**
	 * @return the queryParam
	 */
	public String getQueryParam() {
		return queryParam;
	}
	/**
	 * @param queryParam the queryParam to set
	 */
	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}
	/**
	 * @return the resizable
	 */
	public String getResizable() {
		return resizable;
	}
	/**
	 * @param resizable the resizable to set
	 */
	public void setResizable(String resizable) {
		this.resizable = resizable;
	}
	/**
	 * @return the selectOnFocus
	 */
	@Override
	public String getSelectOnFocus() {
		return selectOnFocus;
	}
	/**
	 * @param selectOnFocus the selectOnFocus to set
	 */
	@Override
	public void setSelectOnFocus(String selectOnFocus) {
		this.selectOnFocus = selectOnFocus;
	}
	/**
	 * @return the selectedClass
	 */
	public String getSelectedClass() {
		return selectedClass;
	}
	/**
	 * @param selectedClass the selectedClass to set
	 */
	public void setSelectedClass(String selectedClass) {
		this.selectedClass = selectedClass;
	}
	/**
	 * @return the shadow
	 */
	public String getShadow() {
		return shadow;
	}
	/**
	 * @param shadow the shadow to set
	 */
	public void setShadow(String shadow) {
		this.shadow = shadow;
	}
	/**
	 * @return the store
	 */
	public String getStore() {
		return store;
	}
	/**
	 * @param store the store to set
	 */
	public void setStore(String store) {
		this.store = store;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the tpl
	 */
	public String getTpl() {
		return tpl;
	}
	/**
	 * @param tpl the tpl to set
	 */
	public void setTpl(String tpl) {
		this.tpl = tpl;
	}
	/**
	 * @return the transform
	 */
	public String getTransform() {
		return transform;
	}
	/**
	 * @param transform the transform to set
	 */
	public void setTransform(String transform) {
		this.transform = transform;
	}
	/**
	 * @return the triggerAction
	 */
	public String getTriggerAction() {
		return triggerAction;
	}
	/**
	 * @param triggerAction the triggerAction to set
	 */
	public void setTriggerAction(String triggerAction) {
		this.triggerAction = triggerAction;
	}
	/**
	 * @return the triggerClass
	 */
	@Override
	public String getTriggerClass() {
		return triggerClass;
	}
	/**
	 * @param triggerClass the triggerClass to set
	 */
	@Override
	public void setTriggerClass(String triggerClass) {
		this.triggerClass = triggerClass;
	}
	/**
	 * @return the typeAhead
	 */
	public String getTypeAhead() {
		return typeAhead;
	}
	/**
	 * @param typeAhead the typeAhead to set
	 */
	public void setTypeAhead(String typeAhead) {
		this.typeAhead = typeAhead;
	}
	/**
	 * @return the tpeAheadDelay
	 */
	public String getTpeAheadDelay() {
		return tpeAheadDelay;
	}
	/**
	 * @param tpeAheadDelay the tpeAheadDelay to set
	 */
	public void setTpeAheadDelay(String tpeAheadDelay) {
		this.tpeAheadDelay = tpeAheadDelay;
	}
	/**
	 * @return the valueField
	 */
	public String getValueField() {
		return valueField;
	}
	/**
	 * @param valueField the valueField to set
	 */
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}
	/**
	 * @return the valueNotFoundText
	 */
	public String getValueNotFoundText() {
		return valueNotFoundText;
	}
	/**
	 * @param valueNotFoundText the valueNotFoundText to set
	 */
	public void setValueNotFoundText(String valueNotFoundText) {
		this.valueNotFoundText = valueNotFoundText;
	}
	/**
	 * @return the contextDataName
	 */
	public String getContextDataName() {
		return ContextDataName;
	}
	/**
	 * @param contextDataName the contextDataName to set
	 */
	public void setContextDataName(String contextDataName) {
		ContextDataName = contextDataName;
	}
	/**
	 * @return the emptyText
	 */
	@Override
	public String getEmptyText() {
		return emptyText;
	}
	/**
	 * @param emptyText the emptyText to set
	 */
	@Override
	public void setEmptyText(String emptyText) {
		this.emptyText = emptyText;
	}
	/**
	 * @return the columnComBoBox
	 */
	public String getColumnComBoBox() {
		return columnComBoBox;
	}
	/**
	 * @param columnComBoBox the columnComBoBox to set
	 */
	public void setColumnComBoBox(String columnComBoBox) {
		this.columnComBoBox = columnComBoBox;
	}

}
