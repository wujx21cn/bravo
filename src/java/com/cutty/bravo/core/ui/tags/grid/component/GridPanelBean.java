
package com.cutty.bravo.core.ui.tags.grid.component;

import java.util.ArrayList;
import java.util.List;

import com.cutty.bravo.core.ConfigurableConstants;
import com.cutty.bravo.core.ui.tags.container.component.PanelBean;


/**
 *<p> EXT标签GridPanel属性集合类 </p>
 * <p>
 * <a href="GridPanelBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:linjuan0125@gmail.com">Cathy Lin</a>
 */

public class GridPanelBean extends PanelBean {


	private static final long serialVersionUID = 1L;
	private String enableDragDrop ;
	private String anchor;
    private String autoExpandColumn ;
    private String autoExpandMax ;
    private String autoExpandMin ;
    private String cm ;
    private String colModel ;
    private String columns ;
    private String deferRowRender ;
    private String disableSelection ;
    private String enableColumnHide ;
    private String enableColumnMove ;
    private String enableColumnResize ;
    private String enableHdMenu ;
    private String hideHeaders ;
    private String loadMask ;
    private String maxHeight ;
    private String minColumnWidth ;
    private String selModel ;
    private String sm ;
    private String store ;
    private String stripeRows ;
    private String trackMouseOver ;
    private String view ;
    private String viewConfig ;
    private String contextDataName ;
    private String contextDataFieldName ;
    
    //分页大小默认为20
    private String pageSize=ConfigurableConstants.getProperty("query.common.constant.default_page_size","20");
    
    private String dataProxy;
    
    //实体的ID,默认为"id"
    private String entityKey = "id";
    
    //初始值为dataProxy,为导出文件定义的字段
    private String urlForExprtFile;
	public String getUrlForExprtFile() {
		return urlForExprtFile;
	}
	public void setUrlForExprtFile(String urlForExprtFile) {
		this.urlForExprtFile = urlForExprtFile;
	}
	//扩展属性，所有columnModel组件均注册到该属性中。
	private List<ColumnModelBean> childColumns = new ArrayList<ColumnModelBean>();  
	
	/**
	 * @return the childColumns
	 */
	public List<ColumnModelBean> getChildColumns() {
		return childColumns;
	}
	/**
	 * @param childColumns the childColumns to set
	 */
	public void setChildColumns(List<ColumnModelBean> childColumns) {
		this.childColumns = childColumns;
	}
	/**
	 * @return the enableDragDrop
	 */
	public String getEnableDragDrop() {
		return enableDragDrop;
	}
	/**
	 * @param enableDragDrop the enableDragDrop to set
	 */
	public void setEnableDragDrop(String enableDragDrop) {
		this.enableDragDrop = enableDragDrop;
	}
	/**
	 * @return the autoExpandColumn
	 */
	public String getAutoExpandColumn() {
		return autoExpandColumn;
	}
	/**
	 * @param autoExpandColumn the autoExpandColumn to set
	 */
	public void setAutoExpandColumn(String autoExpandColumn) {
		this.autoExpandColumn = autoExpandColumn;
	}
	/**
	 * @return the autoExpandMax
	 */
	public String getAutoExpandMax() {
		return autoExpandMax;
	}
	/**
	 * @param autoExpandMax the autoExpandMax to set
	 */
	public void setAutoExpandMax(String autoExpandMax) {
		this.autoExpandMax = autoExpandMax;
	}
	/**
	 * @return the autoExpandMin
	 */
	public String getAutoExpandMin() {
		return autoExpandMin;
	}
	/**
	 * @param autoExpandMin the autoExpandMin to set
	 */
	public void setAutoExpandMin(String autoExpandMin) {
		this.autoExpandMin = autoExpandMin;
	}
	/**
	 * @return the cm
	 */
	public String getCm() {
		return cm;
	}
	/**
	 * @param cm the cm to set
	 */
	public void setCm(String cm) {
		this.cm = cm;
	}
	/**
	 * @return the colModel
	 */
	public String getColModel() {
		return colModel;
	}
	/**
	 * @param colModel the colModel to set
	 */
	public void setColModel(String colModel) {
		this.colModel = colModel;
	}
	/**
	 * @return the columns
	 */
	public String getColumns() {
		return columns;
	}
	/**
	 * @param columns the columns to set
	 */
	public void setColumns(String columns) {
		this.columns = columns;
	}
	/**
	 * @return the deferRowRender
	 */
	public String getDeferRowRender() {
		return deferRowRender;
	}
	/**
	 * @param deferRowRender the deferRowRender to set
	 */
	public void setDeferRowRender(String deferRowRender) {
		this.deferRowRender = deferRowRender;
	}
	/**
	 * @return the disableSelection
	 */
	public String getDisableSelection() {
		return disableSelection;
	}
	/**
	 * @param disableSelection the disableSelection to set
	 */
	public void setDisableSelection(String disableSelection) {
		this.disableSelection = disableSelection;
	}
	/**
	 * @return the enableColumnHide
	 */
	public String getEnableColumnHide() {
		return enableColumnHide;
	}
	/**
	 * @param enableColumnHide the enableColumnHide to set
	 */
	public void setEnableColumnHide(String enableColumnHide) {
		this.enableColumnHide = enableColumnHide;
	}
	/**
	 * @return the enableColumnMove
	 */
	public String getEnableColumnMove() {
		return enableColumnMove;
	}
	/**
	 * @param enableColumnMove the enableColumnMove to set
	 */
	public void setEnableColumnMove(String enableColumnMove) {
		this.enableColumnMove = enableColumnMove;
	}
	/**
	 * @return the enableColumnResize
	 */
	public String getEnableColumnResize() {
		return enableColumnResize;
	}
	/**
	 * @param enableColumnResize the enableColumnResize to set
	 */
	public void setEnableColumnResize(String enableColumnResize) {
		this.enableColumnResize = enableColumnResize;
	}
	/**
	 * @return the enableHdMenu
	 */
	public String getEnableHdMenu() {
		return enableHdMenu;
	}
	/**
	 * @param enableHdMenu the enableHdMenu to set
	 */
	public void setEnableHdMenu(String enableHdMenu) {
		this.enableHdMenu = enableHdMenu;
	}
	/**
	 * @return the hideHeaders
	 */
	public String getHideHeaders() {
		return hideHeaders;
	}
	/**
	 * @param hideHeaders the hideHeaders to set
	 */
	public void setHideHeaders(String hideHeaders) {
		this.hideHeaders = hideHeaders;
	}
	/**
	 * @return the loadMask
	 */
	public String getLoadMask() {
		return loadMask;
	}
	/**
	 * @param loadMask the loadMask to set
	 */
	public void setLoadMask(String loadMask) {
		this.loadMask = loadMask;
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
	 * @return the minColumnWidth
	 */
	public String getMinColumnWidth() {
		return minColumnWidth;
	}
	/**
	 * @param minColumnWidth the minColumnWidth to set
	 */
	public void setMinColumnWidth(String minColumnWidth) {
		this.minColumnWidth = minColumnWidth;
	}
	/**
	 * @return the selModel
	 */
	public String getSelModel() {
		return selModel;
	}
	/**
	 * @param selModel the selModel to set
	 */
	public void setSelModel(String selModel) {
		this.selModel = selModel;
	}
	/**
	 * @return the sm
	 */
	public String getSm() {
		return sm;
	}
	/**
	 * @param sm the sm to set
	 */
	public void setSm(String sm) {
		this.sm = sm;
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
	 * @return the stripeRows
	 */
	public String getStripeRows() {
		return stripeRows;
	}
	/**
	 * @param stripeRows the stripeRows to set
	 */
	public void setStripeRows(String stripeRows) {
		this.stripeRows = stripeRows;
	}
	/**
	 * @return the trackMouseOver
	 */
	public String getTrackMouseOver() {
		return trackMouseOver;
	}
	/**
	 * @param trackMouseOver the trackMouseOver to set
	 */
	public void setTrackMouseOver(String trackMouseOver) {
		this.trackMouseOver = trackMouseOver;
	}
	/**
	 * @return the view
	 */
	public String getView() {
		return view;
	}
	/**
	 * @param view the view to set
	 */
	public void setView(String view) {
		this.view = view;
	}
	/**
	 * @return the viewConfig
	 */
	public String getViewConfig() {
		return viewConfig;
	}
	/**
	 * @param viewConfig the viewConfig to set
	 */
	public void setViewConfig(String viewConfig) {
		this.viewConfig = viewConfig;
	}
	/**
	 * @return the contextDataName
	 */
	public String getContextDataName() {
		return contextDataName;
	}
	/**
	 * @param contextDataName the contextDataName to set
	 */
	public void setContextDataName(String contextDataName) {
		this.contextDataName = contextDataName;
	}
	/**
	 * @return the contextDataFieldName
	 */
	public String getContextDataFieldName() {
		return contextDataFieldName;
	}
	/**
	 * @param contextDataFieldName the contextDataFieldName to set
	 */
	public void setContextDataFieldName(String contextDataFieldName) {
		this.contextDataFieldName = contextDataFieldName;
	}

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
	 * @return the entityKey
	 */
	public String getEntityKey() {
		return entityKey;
	}

	/**
	 * @param entityKey the entityKey to set
	 */
	public void setEntityKey(String entityKey) {
		this.entityKey = entityKey;
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
	 * @param anchor the anchor to set
	 */
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}
	/**
	 * @return the anchor
	 */
	public String getAnchor() {
		return anchor;
	}

    
   
}
