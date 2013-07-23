/* com.cutty.bravo.components.common.domain.DeskTopConfig.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Apr 27, 2009 6:08:02 PM, Created by Jason.Wu
}}IS_NOTE

Copyright (C) 2008 Bravo Corporation. All Rights Reserved.

*/
package com.cutty.bravo.components.common.domain;

import javax.persistence.Column;
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
 * <a href="DeskTopConfig.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:wujx21cn@gmail.com">Jason Wu</a>
 */

@Entity
@Table(name = "bravo_user_desktop")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeskTopConfig  extends BaseDomain {

	private static final long serialVersionUID = -1206515314519012307L;

	private User user;
	private String shortcuts;
	private String autoRunApps;
	private String quickStartApps;
	private Integer taskbarTransparency;
	private DeskTopConfigResource theme;
	private DeskTopConfigResource wallpaper;
	private String wallpaperLayout;
	private String backgroundColor;
	private String fontColor;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "user_id",referencedColumnName="id")
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	public String getShortcuts() {
		return shortcuts;
	}
	public void setShortcuts(String shortcuts) {
		this.shortcuts = shortcuts;
	}
	
	@Column(name = "auto_run_apps")
	public String getAutoRunApps() {
		return autoRunApps;
	}
	public void setAutoRunApps(String autoRunApps) {
		this.autoRunApps = autoRunApps;
	}

	@Column(name = "quick_start_apps")
	public String getQuickStartApps() {
		return quickStartApps;
	}
	public void setQuickStartApps(String quickStartApps) {
		this.quickStartApps = quickStartApps;
	}
	

	@Column(name = "taskbar_transparency")
	public Integer getTaskbarTransparency() {
		return taskbarTransparency;
	}
	public void setTaskbarTransparency(Integer taskbarTransparency) {
		this.taskbarTransparency = taskbarTransparency;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "theme",referencedColumnName="id")
	public DeskTopConfigResource getTheme() {
		return theme;
	}

	public void setTheme(DeskTopConfigResource theme) {
		this.theme = theme;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "wallpaper",referencedColumnName="id")
	public DeskTopConfigResource getWallpaper() {
		return wallpaper;
	}

	public void setWallpaper(DeskTopConfigResource wallpaper) {
		this.wallpaper = wallpaper;
	}

	@Column(name = "wallpaper_layout")
	public String getWallpaperLayout() {
		return wallpaperLayout;
	}
	public void setWallpaperLayout(String wallpaperLayout) {
		this.wallpaperLayout = wallpaperLayout;
	}
	
	@Column(name = "background_color")
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	@Column(name = "font_color")
	public String getFontColor() {
		return fontColor;
	}
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
	
	
}
