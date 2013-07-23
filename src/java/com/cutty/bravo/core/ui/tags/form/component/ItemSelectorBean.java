/* com.cutty.bravo.core.ui.tags.form.component.ItemSelectorBean.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Sep 11, 2008 2:32:57 PM, Created by kukuxia.hw
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.form.component;

/**
 * <p> EXT标签ItemSelector属性集合类 </p>
 * <p>
 * <a href="ItemSelectorBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:huangw100@126.com">kukuxia.hw</a>
 */
public class ItemSelectorBean extends FieldBean {

	private static final long serialVersionUID = 670219080588335211L;
    private String msWidth;
    private String msHeight;
    private String imagePath;
    private String iconUp;
    private String iconDown;
    private String iconLeft;
    private String iconRight;
    private String iconTop;
    private String iconBottom;
    private String drawUpIcon;
    private String drawDownIcon;
    private String drawLeftIcon;
    private String drawRightIcon;
    private String drawTopIcon;
    private String drawBotIcon;
    private String fromStore;
    private String toStore;
    private String fromData;
    private String toData;
    private String displayField;
    private String valueField;
    private String switchToFrom;
    private String allowDup;
    private String focusClass;
    private String delimiter;
    private String readOnly;
    private String toLegend;
    private String fromLegend;
    private String toSortField;
    private String fromSortField;
    private String toSortDir;
    private String fromSortDir;
    private String toTBar;
    private String fromTBar;
    private String bodyStyle;
    private String border;
    private String defaultAutoCreate;
    private String dataProxy; 
    
	public String getDataProxy() {
		return dataProxy;
	}
	public void setDataProxy(String dataProxy) {
		this.dataProxy = dataProxy;
	}
	public String getMsWidth() {
		return msWidth;
	}
	public void setMsWidth(String msWidth) {
		this.msWidth = msWidth;
	}
	public String getMsHeight() {
		return msHeight;
	}
	public void setMsHeight(String msHeight) {
		this.msHeight = msHeight;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getIconUp() {
		return iconUp;
	}
	public void setIconUp(String iconUp) {
		this.iconUp = iconUp;
	}
	public String getIconDown() {
		return iconDown;
	}
	public void setIconDown(String iconDown) {
		this.iconDown = iconDown;
	}
	public String getIconLeft() {
		return iconLeft;
	}
	public void setIconLeft(String iconLeft) {
		this.iconLeft = iconLeft;
	}
	public String getIconRight() {
		return iconRight;
	}
	public void setIconRight(String iconRight) {
		this.iconRight = iconRight;
	}
	public String getIconTop() {
		return iconTop;
	}
	public void setIconTop(String iconTop) {
		this.iconTop = iconTop;
	}
	public String getIconBottom() {
		return iconBottom;
	}
	public void setIconBottom(String iconBottom) {
		this.iconBottom = iconBottom;
	}
	public String getDrawUpIcon() {
		return drawUpIcon;
	}
	public void setDrawUpIcon(String drawUpIcon) {
		this.drawUpIcon = drawUpIcon;
	}
	public String getDrawDownIcon() {
		return drawDownIcon;
	}
	public void setDrawDownIcon(String drawDownIcon) {
		this.drawDownIcon = drawDownIcon;
	}
	public String getDrawLeftIcon() {
		return drawLeftIcon;
	}
	public void setDrawLeftIcon(String drawLeftIcon) {
		this.drawLeftIcon = drawLeftIcon;
	}
	public String getDrawRightIcon() {
		return drawRightIcon;
	}
	public void setDrawRightIcon(String drawRightIcon) {
		this.drawRightIcon = drawRightIcon;
	}
	public String getDrawTopIcon() {
		return drawTopIcon;
	}
	public void setDrawTopIcon(String drawTopIcon) {
		this.drawTopIcon = drawTopIcon;
	}
	public String getDrawBotIcon() {
		return drawBotIcon;
	}
	public void setDrawBotIcon(String drawBotIcon) {
		this.drawBotIcon = drawBotIcon;
	}
	public String getFromStore() {
		return fromStore;
	}
	public void setFromStore(String fromStore) {
		this.fromStore = fromStore;
	}
	public String getToStore() {
		return toStore;
	}
	public void setToStore(String toStore) {
		this.toStore = toStore;
	}
	public String getFromData() {
		return fromData;
	}
	public void setFromData(String fromData) {
		this.fromData = fromData;
	}
	public String getToData() {
		return toData;
	}
	public void setToData(String toData) {
		this.toData = toData;
	}
	public String getDisplayField() {
		return displayField;
	}
	public void setDisplayField(String displayField) {
		this.displayField = displayField;
	}
	public String getValueField() {
		return valueField;
	}
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}
	public String getSwitchToFrom() {
		return switchToFrom;
	}
	public void setSwitchToFrom(String switchToFrom) {
		this.switchToFrom = switchToFrom;
	}
	public String getAllowDup() {
		return allowDup;
	}
	public void setAllowDup(String allowDup) {
		this.allowDup = allowDup;
	}
	@Override
	public String getFocusClass() {
		return focusClass;
	}
	@Override
	public void setFocusClass(String focusClass) {
		this.focusClass = focusClass;
	}
	public String getDelimiter() {
		return delimiter;
	}
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	@Override
	public String getReadOnly() {
		return readOnly;
	}
	@Override
	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}
	public String getToLegend() {
		return toLegend;
	}
	public void setToLegend(String toLegend) {
		this.toLegend = toLegend;
	}
	public String getFromLegend() {
		return fromLegend;
	}
	public void setFromLegend(String fromLegend) {
		this.fromLegend = fromLegend;
	}
	public String getToSortField() {
		return toSortField;
	}
	public void setToSortField(String toSortField) {
		this.toSortField = toSortField;
	}
	public String getFromSortField() {
		return fromSortField;
	}
	public void setFromSortField(String fromSortField) {
		this.fromSortField = fromSortField;
	}
	public String getToSortDir() {
		return toSortDir;
	}
	public void setToSortDir(String toSortDir) {
		this.toSortDir = toSortDir;
	}
	public String getFromSortDir() {
		return fromSortDir;
	}
	public void setFromSortDir(String fromSortDir) {
		this.fromSortDir = fromSortDir;
	}
	public String getToTBar() {
		return toTBar;
	}
	public void setToTBar(String toTBar) {
		this.toTBar = toTBar;
	}
	public String getFromTBar() {
		return fromTBar;
	}
	public void setFromTBar(String fromTBar) {
		this.fromTBar = fromTBar;
	}
	public String getBodyStyle() {
		return bodyStyle;
	}
	public void setBodyStyle(String bodyStyle) {
		this.bodyStyle = bodyStyle;
	}
	public String getBorder() {
		return border;
	}
	public void setBorder(String border) {
		this.border = border;
	}
	public String getDefaultAutoCreate() {
		return defaultAutoCreate;
	}
	public void setDefaultAutoCreate(String defaultAutoCreate) {
		this.defaultAutoCreate = defaultAutoCreate;
	}
    
}
