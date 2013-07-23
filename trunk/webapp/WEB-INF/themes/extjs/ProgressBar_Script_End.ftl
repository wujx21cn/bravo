	var ${component.name} = new Ext.ProgressBar({
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
		<#-------com.cutty.bravo.core.ui.component.BoxComponent----------------->
		</#if><#if component.autoHeight?exists>,autoHeight:${component.autoHeight} 
		</#if><#if component.autoWidth?exists>,autoWidth:${component.autoWidth}
		</#if><#if component.width?exists>,width:${component.width} 
		</#if><#if component.height?exists>,height:${component.height} 

		<#-------com.cutty.bravo.core.ui.tags.progressbar.component.ProgressBarBean----------------->
		</#if><#if component.baseCls?exists>,baseCls:'${component.baseCls}' 
		</#if><#if component.text?exists>,text:'${component.text}' 
		</#if><#if component.textEl?exists>,textEl:'${component.textEl}' 
		</#if><#if component.value?exists>,value:'${component.value}'  </#if>

});

${component.name}_win = ${component.name};	
