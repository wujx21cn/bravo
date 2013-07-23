    var ${component.name} = new Ext.ux.Portlet({
		id:'${component.name}' 
		,name:'${component.name}'             
		<#if component.xtype?exists>,xtype:'${component.xtype}' 
		</#if><#if component.listeners?exists>,listeners:${component.listeners}
		</#if><#if component.plugins?exists>,plugins:${component.plugins}
		</#if><#if component.renderTo?exists>,renderTo:'${component.renderTo}'
		</#if><#if component.style?exists>,style:'${component.style}' 
		</#if><#if component.width?exists>,width:${component.width} 
		</#if><#if component.height?exists>,height:${component.height} 
		</#if><#if component.activeItem?exists>,activeItem:${component.activeItem} 
		</#if><#if component.cls?exists>,cls:'${component.cls}' 
		</#if><#if component.ctCls?exists>,ctCls:'${component.ctCls}' 
		</#if><#if component.defaults?exists>,defaults:${component.defaults} 
		</#if><#if component.disabledClass?exists>,disabledClass:'${component.disabledClass}' 
		</#if><#if component.hideBorders?exists>,hideBorders:${component.hideBorders} 
		</#if><#if component.layout?exists>,layout:'${component.layout}' 
		</#if><#if component.layoutConfig?exists>,layoutConfig:${component.layoutConfig} 
		</#if><#if component.animCollapse?exists>,animCollapse:${component.animCollapse} 
		</#if><#if component.applyTo?exists>,applyTo:'${component.applyTo}'
		</#if><#if component.autoLoad?exists>,autoLoad:{url:'${component.autoLoad}',nocache:true} 
		</#if><#if component.autoScroll?exists>,autoScroll:${component.autoScroll} 
		</#if><#if component.baseCls?exists>,baseCls:'${component.baseCls}' 
		</#if><#if component.bbar?exists>,bbar:${component.bbar} 
		</#if><#if component.bodyBorder?exists>,bodyBorder:${component.bodyBorder} 
		</#if><#if component.bodyStyle?exists>,bodyStyle:${component.bodyStyle} 
		</#if><#if component.region?exists>,region:'${component.region}'

		</#if><#if component.border?exists>,border:${component.border}
		</#if><#if component.buttonAlign?exists>,buttonAlign:'${component.buttonAlign}' 
		</#if><#if component.buttons?exists>,buttons:${component.buttons}
		</#if><#if component.collapseFirst?exists>,collapseFirst:${component.collapseFirst}  
		</#if><#if component.collapsed?exists>,collapsed:${component.collapsed}
		</#if><#if component.collapsedCls?exists>,collapsedCls:'${component.collapsedCls}'
		</#if><#if component.collapsible?exists>,collapsible:${component.collapsible}
		</#if><#if component.ctCls?exists>,ctCls:'${component.ctCls}'
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
		</#if><#if component.minButtonWidth?exists>,minButtonWidth:'${component.minButtonWidth}'
		</#if><#if component.shadow?exists>,shadow:${component.shadow}
		</#if><#if component.shadowOffset?exists>,shadowOffset:'${component.shadowOffset}'
		</#if><#if component.shim?exists>,shim:${component.shim}
		</#if><#if component.tbar?exists>,tbar:${component.tbar}
		</#if><#if component.title?exists>,title:'${component.title}'
		</#if><#if component.titleCollapse?exists>,titleCollapse:${component.titleCollapse}
		</#if><#if component.tools?exists>,tools:${component.tools}
                </#if><#if component.split?exists>,split:${component.split}
		</#if><#if childrenComponentNames?has_content> 
		,items:[<#list childrenComponentNames as childrenComponentNamesItem><#if childrenComponentNamesItem_index != 0>,</#if>${childrenComponentNamesItem}</#list>]
		</#if> 
});