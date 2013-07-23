package com.cutty.bravo.core.ui.tags.grid.component;

import com.cutty.bravo.core.ui.component.Component;


/**
 *<p> EXT标签ColumnModel属性集合类 </p>
 * <p>
 * <a href="ColumnModelBean.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:linjuan0125@gmail.com">Cathy Lin</a>
 */

public class ColumnModelBean extends Component {


	private static final long serialVersionUID = 1L;
	private String align ;
    private String css ;
    private String editor ;
    private String fixed ;
    private String header ;
    private String hidden ;
    private String hideable ;
    private String id ;
    private String menuDisabled ;
    private String renderer ;
    private String resizeble ;
    private String sortable ;
    private String toopltip ;
    private String width = "175";//lian.gg2008-12-16，初始化一下，为了导出excel使用
    
    //该字段存放列的定义内容 根式为%{}....表示服务器渲染.... wujx2008-10-10
    private String value ;
//----------------------------------------------------------------   
    //以下定义列数据类型等。详细定义见Ext.data.Record.create函数。 wujx2008-10-10
    private String mapping;
    
    //该列的数据类型，默认为string 
	//auto (Default, implies no conversion),string,int,float,boolean,date
    private String type = "string";
    private String convert;
    private String dateFormat ;
    private String defaultValue;
//----------------------------------------------------------------      
	/**
	 * @return the align
	 */
    public String getAlign() {
		return align;
	}
    /**
	 * @param Align the Align to set
	 */
	public void setAlign(String align) {
		this.align = align;
	}
	/**
	 * @return the css
	 */
	public String getCss() {
		return css;
	}
	 /**
	 * @param css the css to set
	 */
	public void setCss(String css) {
		this.css = css;
	}
	/**
	 * @return the editor
	 */
	public String getEditor() {
		return editor;
		}
	/**
	 * @param Editor the Editor to set
	 */
	public void setEditor(String editor) {
		this.editor = editor;
	}
	/**
	 * @return the fixed
	 */
	public String getFixed() {
		return fixed;
	}
	/**
	 * @param fixed the fixed to set
	 */
	public void setFixed(String fixed) {
		this.fixed = fixed;
	}
	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}
	
	/**
	 * @param header the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}
	/**
	 * @return the hidden
	 */
	public String getHidden() {
		return hidden;
	}
	/**
	 * @param hidden the hidden to set
	 */
	public void setHidden(String hidden) {
		this.hidden = hidden;
	}
	/**
	 * @return the hideable
	 */
	public String getHideable() {
		return hideable;
	}
	/**
	 * @param hideable the hideable to set
	 */
	public void setHideable(String hideable) {
		this.hideable = hideable;
	}
	/**
	 * @return the id
	 */
	@Override
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the menuDisabled
	 */
	public String getMenuDisabled() {
		return menuDisabled;
	}
	/**
	 * @param menuDisabled the menuDisabled to set
	 */
	public void setMenuDisabled(String menuDisabled) {
		this.menuDisabled = menuDisabled;
	}
	/**
	 * @return the renderer
	 */
	public String getRenderer() {
		return renderer;
	}
	/**
	 * @param renderer the renderer to set
	 */
	public void setRenderer(String renderer) {
		this.renderer = renderer;
	}
	/**
	 * @return the resizeble
	 */
	public String getResizeble() {
		return resizeble;
	}
	/**
	 * @param resizeble the resizeble to set
	 */
	public void setResizeble(String resizeble) {
		this.resizeble = resizeble;
	}
	/**
	 * @return the sortable
	 */
	public String getSortable() {
		return sortable;
	}
	/**
	 * @param sortable the sortable to set
	 */
	public void setSortable(String sortable) {
		this.sortable = sortable;
	}
	/**
	 * @return the toopltip
	 */
	public String getToopltip() {
		return toopltip;
	}
	/**
	 * @param toopltip the toopltip to set
	 */
	public void setToopltip(String toopltip) {
		this.toopltip = toopltip;
	}
	/**
	 * @return the width
	 */
	public String getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the mapping
	 */
	public String getMapping() {
		return mapping;
	}
	/**
	 * @param mapping the mapping to set
	 */
	public void setMapping(String mapping) {
		this.mapping = mapping;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the convert
	 */
	public String getConvert() {
		return convert;
	}
	/**
	 * @param convert the convert to set
	 */
	public void setConvert(String convert) {
		this.convert = convert;
	}
	/**
	 * @return the dateFormat
	 */
	public String getDateFormat() {
		return dateFormat;
	}
	/**
	 * @param dateFormat the dateFormat to set
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}
	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
}