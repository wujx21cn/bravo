/* com.cutty.bravo.components.common.domain.ProfileMenu.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2008-12-9 下午01:31:50, Created by Yeon
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cutty.bravo.core.domain.BaseDomain;
import com.cutty.bravo.core.security.domain.User;

/**
 *
 * <p>
 * <a href="ProfileMenu.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:YeonOrchid@gmail.com">Yeon</a>
 */

@Entity
@Table(name = "bravo_profile_menu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProfileMenu extends BaseDomain {

	private static final long serialVersionUID = -3109561262798207384L;
	private User user;
	private MenuFunction menuFunction;
	private String icon;
	private String sequence;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id",referencedColumnName="id")
	public MenuFunction getMenuFunction() {
		return menuFunction;
	}
	public void setMenuFunction(MenuFunction menuFunction) {
		this.menuFunction = menuFunction;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id",referencedColumnName="id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIcon() {
		return icon;
	}
	
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getSequence() {
		return sequence;
	}
	
}
