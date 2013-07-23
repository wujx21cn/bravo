/**
 * kukuxia.hw
 */
package com.cutty.bravo.core.ui.tags.form.component;

/**
 * <p> EXT标签CheckboxGroup属性集合类 </p>
 * <p>
 * <a href="CheckboxGroupBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:huangw100@126.com">kukuxia.kevin.hw</a>
 */
public class CheckboxGroupBean extends FieldBean {

	private static final long serialVersionUID = -1711726428826908937L;
	private String columns;
	private String vertical;
	private String allowBlank;
	private String blankText;
	private String defaultType;
	private String groupCls;
	public String getColumns() {
		return columns;
	}
	public void setColumns(String columns) {
		this.columns = columns;
	}
	public String getVertical() {
		return vertical;
	}
	public void setVertical(String vertical) {
		this.vertical = vertical;
	}
	public String getAllowBlank() {
		return allowBlank;
	}
	public void setAllowBlank(String allowBlank) {
		this.allowBlank = allowBlank;
	}
	public String getBlankText() {
		return blankText;
	}
	public void setBlankText(String blankText) {
		this.blankText = blankText;
	}
	public String getDefaultType() {
		return defaultType;
	}
	public void setDefaultType(String defaultType) {
		this.defaultType = defaultType;
	}
	public String getGroupCls() {
		return groupCls;
	}
	public void setGroupCls(String groupCls) {
		this.groupCls = groupCls;
	}

}
