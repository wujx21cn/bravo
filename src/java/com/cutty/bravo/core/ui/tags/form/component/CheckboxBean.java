/**
 * kukuxia.hw
 */
package com.cutty.bravo.core.ui.tags.form.component;

/**
 * <p> EXT标签Checkbox属性集合类 </p>
 * <p>
 * <a href="CheckboxBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:huangw100@126.com">kukuxia.kevin.hw</a>
 */
public class CheckboxBean extends FieldBean {

	private static final long serialVersionUID = -1864382856857170071L;
    private String autoCreate ;
    private String boxLabel ;
    private String checked ;
    private String fieldClass ;
    private String focusClass ;
    private String inputValue ;
	@Override
	public String getAutoCreate() {
		return autoCreate;
	}
	@Override
	public void setAutoCreate(String autoCreate) {
		this.autoCreate = autoCreate;
	}
	public String getBoxLabel() {
		return boxLabel;
	}
	public void setBoxLabel(String boxLabel) {
		this.boxLabel = boxLabel;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	@Override
	public String getFieldClass() {
		return fieldClass;
	}
	@Override
	public void setFieldClass(String fieldClass) {
		this.fieldClass = fieldClass;
	}
	@Override
	public String getFocusClass() {
		return focusClass;
	}
	@Override
	public void setFocusClass(String focusClass) {
		this.focusClass = focusClass;
	}
	public String getInputValue() {
		return inputValue;
	}
	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}
	
}
