	var ${component.name} = new Ext.FormPanel({
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
		<#-------com.cutty.bravo.core.ui.tags.container.component.Container---->
		</#if><#if component.activeItem?exists>,activeItem:${component.activeItem} 
		</#if><#if component.autoDestroy?exists>,autoDestroy:${component.autoDestroy} 
		</#if><#if component.bufferResize?exists>,bufferResize:${component.bufferResize} 
		</#if><#if component.defaultType?exists>,defaultType:'${component.defaultType}'
		</#if><#if component.defaults?exists>,defaults:${component.defaults} 
		</#if><#if component.hideBorders?exists>,hideBorders:${component.hideBorders} 
		
		</#if><#if component.layout?exists>,layout:'${component.layout}' 
		</#if><#if component.layoutConfig?exists>,layoutConfig:${component.layoutConfig}
		</#if><#if component.monitorResize?exists>,monitorResize:${component.monitorResize} 
		<#-------com.cutty.bravo.core.ui.tags.container.component.PanelBean---->
		</#if><#if component.animCollapse?exists>,animCollapse:${component.animCollapse} 
		</#if><#if component.autoLoad?exists>,autoLoad:'${component.autoLoad}' 
		</#if><#if component.autoScroll?exists>,autoScroll:${component.autoScroll} 
		</#if><#if component.baseCls?exists>,baseCls:'${component.baseCls}' 
		</#if><#if component.bbar?exists>,bbar:${component.bbar} 
		</#if><#if component.bodyBorder?exists>,bodyBorder:${component.bodyBorder} 
		</#if><#if component.bodyStyle?exists>,bodyStyle:${component.bodyStyle} 
		</#if><#if component.border?exists>,border:${component.border}
		</#if><#if component.buttonAlign?exists>,buttonAlign:'${component.buttonAlign}' 
		</#if><#if component.childButtonNames?has_content>,buttons:
		[<#list childButtonNames as childButtonNamesItem><#if childButtonNamesItem_index != 0>,</#if>${childButtonNamesItem}</#list>]
		</#if><#if component.collapseFirst?exists>,collapseFirst:${component.collapseFirst}  
		</#if><#if component.collapsed?exists>,collapsed:${component.collapsed}
		</#if><#if component.collapsedCls?exists>,collapsedCls:'${component.collapsedCls}'
		</#if><#if component.collapsible?exists>,collapsible:${component.collapsible}</#if>
		,contentEl:'${component.name}_div'
		<#if component.ctCls?exists>,ctCls:'${component.ctCls}'
		</#if><#if component.draggable?exists>,draggable:${component.draggable}
		</#if><#if component.elements?exists>,elements:${component.elements}
		</#if><#if component.floating?exists>,floating:${component.floating}
		</#if><#if component.footer?exists>,footer:${component.footer}
		</#if><#if component.frame?exists>,frame:${component.frame}
		</#if><#if component.header?exists>,header:${component.header}
		</#if><#if component.headerAsText?exists>,headerAsText:${component.headerAsText}
		</#if><#if component.hideCollapseTool?exists>,hideCollapseTool:${component.hideCollapseTool}
		</#if><#if component.html?exists>,html:'${component.html}'
		</#if><#if component.iconCls?exists>,iconCls:'${component.iconCls}'
		</#if><#if component.keys?exists>,keys:${component.keys}
		</#if><#if component.maskDisabled?exists>,maskDisabled:'${component.maskDisabled}'
		</#if><#if component.minButtonWidth?exists>,minButtonWidth:${component.minButtonWidth}
		</#if><#if component.shadow?exists>,shadow:${component.shadow}
		</#if><#if component.shadowOffset?exists>,shadowOffset:'${component.shadowOffset}'
		</#if><#if component.shim?exists>,shim:${component.shim}
		</#if><#if component.tbar?exists>,tbar:${component.tbar}
		</#if><#if component.title?exists>,title:'${component.title}'
		</#if><#if component.titleCollapse?exists>,titleCollapse:${component.titleCollapse}
		</#if><#if component.tools?exists>,tools:${component.tools}
		<#-------com.cutty.bravo.core.ui.tags.form.component.FormPanelBean---->
		</#if><#if component.itemCls?exists>,itemCls:'${component.itemCls}'
		</#if><#if component.labelAlign?exists>,labelAlign:'${component.labelAlign}'
		</#if><#if component.labelWidth?exists>,labelWidth:${component.labelWidth}
		</#if><#if component.monitorPoll?exists>,monitorPoll:${component.monitorPoll}
		</#if><#if component.monitorValid?exists>,monitorValid:${component.monitorValid}
		</#if><#if component.dataProxy?exists>,url:'${component.dataProxy}'
                </#if>
		<#if childrenComponentNames?has_content> 
 		    <#if component.childFormSetNames?has_content> 
		    ,items:[<#list component.childFormSetNames as childFormSetNamesItem><#if childFormSetNamesItem_index != 0>,</#if>${childFormSetNamesItem}</#list> <#list childrenComponentNames as childrenComponentNamesItem>,${childrenComponentNamesItem}</#list>]
                    <#else>
		    ,items:[<#list childrenComponentNames as childrenComponentNamesItem><#if childrenComponentNamesItem_index != 0>,</#if>${childrenComponentNamesItem}</#list>]
		    </#if>
		<#else>
 		    <#if component.childFormSetNames?has_content> 
		    ,items:[<#list component.childFormSetNames as childFormSetNamesItem><#if childFormSetNamesItem_index != 0>,</#if>${childFormSetNamesItem}</#list>] 
		    </#if>
		</#if>

});

${component.name}_win = ${component.name};	
