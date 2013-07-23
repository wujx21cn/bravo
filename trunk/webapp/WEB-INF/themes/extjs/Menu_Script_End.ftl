<#if parentComponent?exists && parentComponent.xtype == "Menu">
   var ${component.name} = new Ext.menu.Item({   
      <#if childrenComponentNames?has_content>
          menu:{items:[<#list childrenComponentNames as childrenComponentNamesItem><#if childrenComponentNamesItem_index != 0>,</#if>${childrenComponentNamesItem}</#list>]},
      </#if>
<#elseif  childrenComponentNames?has_content>
   var ${component.name} = new Ext.Toolbar.Button({   
   menu:{items:[<#list childrenComponentNames as childrenComponentNamesItem><#if childrenComponentNamesItem_index != 0>,</#if>${childrenComponentNamesItem}</#list>]},  
<#else>
   var ${component.name} = new Ext.Toolbar.Button({
</#if>

		id:'${component.name}' 
		<#-------com.cutty.bravo.core.ui.component.Component------------------->
		<#if component.listeners?exists>,listeners:${component.listeners}
		</#if><#if component.allowDomMove?exists>,allowDomMove:${component.allowDomMove}
		</#if><#if component.applyTo?exists>,applyTo:'${component.applyTo}'
		</#if><#if component.autoShow?exists>,autoShow:${component.autoShow}
		</#if><#if component.cls?exists>,cls:'${component.cls}' 
		</#if><#if component.ctCls?exists>,ctCls:'${component.ctCls}' 
		</#if><#if component.disabledClass?exists>,disabledClass:'${component.disabledClass}' 
		</#if><#if component.hideMode?exists>,hideMode:'${component.hideMode}' 
		</#if><#if component.hideParent?exists>,hideParent:${component.hideParent}
		</#if><#if component.plugins?exists>,plugins:${component.plugins}
		</#if><#if component.renderTo?exists>,renderTo:'${component.renderTo}'
		</#if><#if component.stateEvents?exists>,stateEvents:${component.stateEvents}
		</#if><#if component.stateId?exists>,stateId:'${component.stateId}'
		</#if><#if component.style?exists>,style:'${component.style}'
		</#if><#if component.region?exists>,region:'${component.region}'

		<#-------com.cutty.bravo.core.ui.tags.menu.component.BaseItemBean------------------->
		</#if><#if component.activeClass?exists>,activeClass:'${component.activeClass}'
		</#if><#if component.canActivate?exists>,canActivate:${component.canActivate}
		</#if><#if component.handler?exists>,handler:menuHandlerEval
		</#if><#if component.handler?exists>,handlerExpress:'${component.handler}'
		</#if><#if component.hideDelay?exists>,hideDelay:${component.hideDelay}
		</#if><#if component.hideOnClick?exists>,hideOnClick:${component.hideOnClick}
		</#if><#if component.scope?exists>,scope:${component.scope}

		<#-------com.cutty.bravo.core.ui.tags.menu.component.MenuBean------------------->
		</#if><#if component.href?exists>,href:'${component.href}'
		</#if><#if component.hrefTarget?exists>,hrefTarget:'${component.hrefTarget}'
		</#if><#if component.icon?exists>,icon:'${component.icon}'
		</#if><#if component.iconCls?exists>,iconCls:'${component.iconCls}'
		</#if><#if component.itemCls?exists>,itemCls:'${component.itemCls}'
		</#if><#if component.showDelay?exists>,showDelay:${component.showDelay}
		</#if><#if component.text?exists>,text:'${component.text}'
		</#if><#if component.menuId?exists>,menu:{}
		</#if><#if component.hidden?exists>,hidden:${component.hidden}
		</#if>  
});

<#if component.addMenuStr?exists>
${component.addMenuStr}
</#if>
    