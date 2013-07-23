/* com.cutty.bravo.core.ui.tags.button.Button.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-9-2 上午02:04:31, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.core.ui.tags.button;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts2.ServletActionContext;


import com.cutty.bravo.core.exception.BizException;
import com.cutty.bravo.core.exception.UIException;
import com.cutty.bravo.core.security.domain.User;
import com.cutty.bravo.core.security.manager.cache.ButtonResourceCacheManager;
import com.cutty.bravo.core.security.manager.cache.EntityOperationCacheManager;
import com.cutty.bravo.core.security.manager.cache.UserDetailsCacheManager;
import com.cutty.bravo.core.ui.tags.BaseTag;
import com.cutty.bravo.core.ui.tags.container.Panel;
import com.cutty.bravo.core.ui.tags.container.component.PanelBean;
import com.cutty.bravo.core.utils.ApplicationContextKeeper;
import com.cutty.bravo.core.web.handler.RequestHandler;

/**
 *
 * <p>定义EXT Button的标签类</p>
 * <p>
 * <a href="Button.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */
public class Button  extends BaseTag{

	private static final long serialVersionUID = 8417674499062783761L;
	
	/**
	 * Button重载regiestOnParent函数,当父标签为panel时候，直接注册到buttons属性中。
	 * @throws UIException 
	 * @see com.cutty.bravo.core.ui.tags.BaseTag#regiestOnParent()
	 * 
	 * 
	 */
	@Override
	protected void regiestOnParent() throws UIException{
		User   user       = RequestHandler.getContextRequestHandler().getCurrentUser();
		String entityName = (String)ServletActionContext.getRequest().getAttribute("entityNameForButton"); 
		String buttonName = (String)ServletActionContext.getRequest().getAttribute("buttonPrefix")+"/"+this.getComponent().getName();
		String operType   = this.getComponent().getName();
		
		/*获取不了用户时关闭按钮的显示*/
		if( null == user ) return;
		
		/*作用于实体的Action页面上的按钮*/
		if( null!=entityName )
		{
			if( buttonAuthenticationByEntityOperation(user,entityName,operType)/*按钮权限审核通过*/)
			{
				BaseTag parentTag = (BaseTag) TagSupport.findAncestorWithClass(this,BaseTag.class);
				if (parentTag instanceof Panel){
					PanelBean PanelComponent = (PanelBean)parentTag.getComponent();
					PanelComponent.getChildButtonNames().add(this.getComponent().getName());
					super.regiestOnParent();
				} else {
					super.regiestOnParent();
				}
			}
		}
		/*作用于非实体的Action页面上的按钮*/
		else
		{
			if( buttonAuthenticationByButtonName(user,buttonName)/*按钮权限审核通过*/)
			{
				BaseTag parentTag = (BaseTag) TagSupport.findAncestorWithClass(this,BaseTag.class);
				if (parentTag instanceof Panel){
					PanelBean PanelComponent = (PanelBean)parentTag.getComponent();
					PanelComponent.getChildButtonNames().add(this.getComponent().getName());
					super.regiestOnParent();
				} else {
					super.regiestOnParent();
				}
			}	
		}
		
	}
	
	protected boolean buttonAuthenticationByEntityOperation(User user, String entityName, String operType) throws UIException{
		/*获得用户的授权码*/
		UserDetailsCacheManager userDetailsCacheManager = (UserDetailsCacheManager)ApplicationContextKeeper.getAppCtx().getBean("userDetailsService");
		Long[] userAuthorities = userDetailsCacheManager.loadUserPermissIdByUsername(user.getLoginid());
		
		/*获得实体操作的授权码*/
		Long[] entityOperationAuthorities;
		EntityOperationCacheManager entityOperationCacheManager = (EntityOperationCacheManager) ApplicationContextKeeper.getAppCtx().getBean("entityOperationCacheManager");
		try {
			entityOperationAuthorities = entityOperationCacheManager.findEntityOperationAuthorityByEntityOperation(entityName, operType);
		} catch (BizException e) {
			throw new UIException(e.getMessage());
		}
		
		return (isIntersectanted(userAuthorities,entityOperationAuthorities)/*用户的授权码与实体操作的授权码有交集*/);

	}
	
	protected boolean buttonAuthenticationByButtonName(User user,String buttonName) throws UIException{
		/*获得用户的授权码*/
		UserDetailsCacheManager userDetailsCacheManager = (UserDetailsCacheManager)ApplicationContextKeeper.getAppCtx().getBean("userDetailsService");
		Long[] userAuthorities = userDetailsCacheManager.loadUserPermissIdByUsername(user.getLoginid());
		
		/*获得按钮的授权码*/
		Long[] buttonAuthorities;
		ButtonResourceCacheManager buttonResourceCacheManager = (ButtonResourceCacheManager) ApplicationContextKeeper.getAppCtx().getBean("buttonResourceCacheManager");
		try {
			buttonAuthorities = buttonResourceCacheManager.findButtonResourceAuthorityByName(buttonName);
		} catch (BizException e) {
			throw new UIException(e.getMessage());
		}
		
		return (isIntersectanted(userAuthorities,buttonAuthorities)/*用户的授权码与按钮的授权码有交集*/);

	}
	
	private boolean isIntersectanted(Long[] arrayA, Long[] arrayB){
		/*这里对未指派权限的按钮，或者未指派权限的用户（不太可能配出这样的用户），直接通过授权*/
		if( null!=arrayA && null!=arrayB )
		{
			for(int i=0; i<arrayA.length; i++){
				for(int j=0; j<arrayB.length; j++){
					if( arrayA[i].longValue()==arrayB[j].longValue() ) return true;
				}
			}
			return false;
		}
		else
		{
			return true;
		}
	} 
}
