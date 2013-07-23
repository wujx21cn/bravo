
package com.cutty.bravo.core.ui.tags.grid;



import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.cutty.bravo.core.exception.UIException;
import com.cutty.bravo.core.ui.tags.BaseTag;
import com.cutty.bravo.core.ui.tags.grid.component.ColumnModelBean;
import com.cutty.bravo.core.ui.tags.grid.component.GridPanelBean;


/**
 *
 * <p>定义所有ColumModel的类</p>
 * <p>
 * <a href="ColumnModel.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:linjuan0125@gmail.com">Cathy Lin</a>
 */

public class ColumnModel extends BaseTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ColumnModel重载regiestOnParent函数,当父标签为GridPanel时候，直接注册到cm属性中。
	 * @throws UIException 
	 */
	@Override
	protected void regiestOnParent() throws UIException{
		
		BaseTag parentTag = (BaseTag) TagSupport.findAncestorWithClass(this,BaseTag.class);
		if (parentTag instanceof GridPanel ){
			GridPanelBean gridPanelComponent = (GridPanelBean)parentTag.getComponent();
			gridPanelComponent.getChildColumns().add((ColumnModelBean)(this.getComponent()));
		} else {
			super.regiestOnParent();
		}
		
	}

	/** 
	 * override BaseTag的doEndTag()方法,如果该标签内属性dataProxy是以hql方式传回数据，调用此方法.
	 * @see com.cutty.bravo.core.ui.tags.BaseTag#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		if (StringUtils.isNotEmpty(this.getBodyContent().getString())){
			ColumnModelBean component = (ColumnModelBean)this.getComponent();
			//kukuxia.kevin.hw 2009-04-15 去掉column标签中bodyContent的回车换行符，否则页面会报"未结束字符串常量"错误。
			component.setValue((StringUtils.trim(this.getBodyContent().getString())).replaceAll("[\\n\\r]",""));
		}
		return super.doEndTag();
	}

}