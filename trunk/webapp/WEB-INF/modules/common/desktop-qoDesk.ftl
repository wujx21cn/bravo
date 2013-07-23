Ext.namespace('QoDesk');
<#assign menuIDS = ""/>
<#macro displayMenu menu title> 
    <#if menu.childMenuFunction?has_content> 
        <#list menu.childMenuFunction  as childMenuItem> 
			<#if title=="StartMenu" && childMenuItem.childMenuFunction?has_content>
			<#else>
				<#assign menuIDS = menuIDS + ", menu" + childMenuItem.id?c/>
				var menu${childMenuItem.id?c} = new QoDesk.LayoutWindow({
					action : "${childMenuItem.action?default("")}",
					moduleType : 'demo',
					moduleId : 'moduleId${childMenuItem.id?c}',
					menuPath : '${title}',
					launcher : {
						iconCls: '${childMenuItem.iconSrc?default("layout")}-icon',
						shortcutIconCls: '${childMenuItem.iconSrc?default("layout")}-shortcut',
						text: '${childMenuItem.name?default("abc")}',
						tooltip: '<b>快捷方式</b><br />${childMenuItem.name?default("abc")}'
				}});
			</#if>
			<#if title?length == 0>
				<#assign childMenuTitle = childMenuItem.name/>
			<#else>
				<#assign childMenuTitle = title+"/"+childMenuItem.name/>
			</#if>
			
            <@displayMenu menu=childMenuItem title=childMenuTitle/> 
        </#list> 
    </#if> 
</#macro> 

<@displayMenu menu=menuFunction title="StartMenu"/> 


QoDesk.App = new Ext.app.App({
	
	init : function(){
		Ext.QuickTips.init();
	},
	
	getPrivileges : function(){
	    return {};
	},

    getModules : function(){
	 
    	return [ new QoDesk.AccordionWindow(), new QoDesk.QoPreferences()${menuIDS}  ];
    },
    
    getLaunchers : function(){
    	return {"autorun":${deskTopConfig.autoRunApps?default("[]")},"contextmenu":["qo-preferences"],"quickstart":${deskTopConfig.quickStartApps?default("[]")},"shortcut":${deskTopConfig.shortcuts?default("[]")}};
    },

    getStyles : function(){
		/**
		该段函数需要根据当前登陆人读取配置，并写入。
		**/
    	return {"backgroundcolor":"${deskTopConfig.backgroundColor}","fontcolor":"${deskTopConfig.fontColor}","transparency":"${deskTopConfig.taskbarTransparency}","theme":{"id":"${deskTopConfig.theme.id?c}","name":"${deskTopConfig.theme.name}","pathtofile":"${bravoHome}${deskTopConfig.theme.file}"},"wallpaper":{"id":"${deskTopConfig.wallpaper.id?c}","name":"${deskTopConfig.wallpaper.name}","pathtofile":"${bravoHome}${deskTopConfig.wallpaper.file}"},"wallpaperposition":"${deskTopConfig.wallpaperLayout}"};
    },
	

	getLogoutConfig : function(){
		return {
			text: 'Logout',
			iconCls: 'logout',
			handler: function(){ window.location = bravoHome+'/j_spring_security_logout'; },
			scope: this
		};
	},
	

    getStartConfig : function(){
    	return {
        	// iconCls: 'user',
            // title: get_cookie('memberName'),
			toolPanelWidth: 125
        };
    },

	startMenuSortFn : function(a, b){

		if( ( ( b.menu && a.menu ) && ( b.text < a.text ) ) || ( b.menu && !a.menu ) || ( (b.text < a.text) && !a.menu ) ){
			return true;
		}
		
		return false;
	}
});