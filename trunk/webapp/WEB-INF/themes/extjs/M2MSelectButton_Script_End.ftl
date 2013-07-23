var ${component.name} = new Ext.Button({
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

	<#-------com.cutty.bravo.core.ui.tags.button.component.ButtonBean---------->
	</#if><#if component.clickEvent?exists>,clickEvent:'${component.clickEvent}' 
	</#if><#if component.disabled?exists>,disabled:${component.disabled} 
	</#if><#if component.enableToggle?exists>,enableToggle:${component.enableToggle} 
	</#if><#if component.handleMouseEvents?exists>,handleMouseEvents:${component.handleMouseEvents}
	</#if><#if component.hidden?exists>,hidden:${component.hidden} 
	</#if><#if component.icon?exists>,icon:'${component.icon}' 
	</#if><#if component.iconCls?exists>,iconCls:'${component.iconCls}' 
	</#if><#if component.menu?exists>,menu:${component.menu} 
	</#if><#if component.menuAlign?exists>,menuAlign:'${component.menuAlign}'
	</#if><#if component.minWidth?exists>,minWidth:${component.minWidth} 
	</#if><#if component.pressed?exists>,pressed:${component.pressed} 
	</#if><#if component.repeat?exists>,repeat:${component.repeat} 
	</#if><#if component.scope?exists>,scope:${component.scope} 
	</#if><#if component.tabIndex?exists>,tabIndex:${component.tabIndex} 
	</#if><#if component.text?exists>,text:'${component.text}' 
	</#if><#if component.toggleGroup?exists>,toggleGroup:'${component.toggleGroup}' 
	</#if><#if component.tooltip?exists>,tooltip:${component.tooltip} 
	</#if><#if component.tooltipType?exists>,tooltipType:'${component.tooltipType}' 
	</#if><#if component.type?exists>,type:'${component.type}' 
	</#if>
	,handler:function(o) {openM2MSelectWin(o,'${component.text}','${component.targetProxy}','${component.targetGridName}','${component.originGridName}','${component.entityName}','${component.fieldName}','${component.entityId}')}
	
});
