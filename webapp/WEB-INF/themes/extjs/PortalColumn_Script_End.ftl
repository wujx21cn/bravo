    var ${component.name} = new Ext.ux.PortalColumn({
	 id:'${component.name}' 
     ,name:'${component.name}'  
    <#if component.xtype?exists>,xtype:'${component.xtype}' </#if>               
    <#if component.listeners?exists>, listeners:${component.listeners}</#if>      
    <#if component.plugins?exists>, plugins:${component.plugins}</#if>       
    <#if component.renderTo?exists>, renderTo:'${component.renderTo}'</#if>
    <#if component.style?exists>,style:'${component.style}' </#if>        
    <#if component.width?exists>,width:${component.width} </#if>                 
    <#if component.height?exists>,height:${component.height} </#if>               
    <#if component.activeItem?exists>,activeItem:${component.activeItem} </#if>  
    <#if component.cls?exists>,cls:'${component.cls}' </#if>  
    <#if component.ctCls?exists>,ctCls:'${component.ctCls}' </#if>  
    <#if component.defaults?exists>,defaults:${component.defaults} </#if>
    <#if component.disabledClass?exists>,disabledClass:'${component.disabledClass}' </#if>   
    <#if component.hideBorders?exists>,hideBorders:${component.hideBorders} </#if>  
    <#if component.layout?exists>,layout:'${component.layout}' </#if>  
    <#if component.layoutConfig?exists>,layoutConfig:${component.layoutConfig} </#if>  
    <#if component.columnWidth?exists>,columnWidth:${component.columnWidth} </#if>
    <#if childrenComponentNames?has_content> ,
    items:[<#list childrenComponentNames as childrenComponentNamesItem><#if childrenComponentNamesItem_index != 0>,</#if>${childrenComponentNamesItem}</#list>]
    </#if>
});