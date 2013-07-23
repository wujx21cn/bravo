var ${component.name} = new Ext.ux.Portal({
     name:'${component.name}'
     ,id:'${component.name}'
    <#if component.title?exists>,title:'${component.title}' </#if> 
    <#if component.region?exists>,region:'${component.region}' </#if>           
    <#if component.width?exists>,width:${component.width} </#if>                 
    <#if component.height?exists>,height:${component.height} </#if>                      
    <#if component.defaults?exists>, defaults:${component.defaults}</#if>
    <#if component.iconCls?exists>,iconCls:'${component.iconCls}' </#if>  
    <#if component.html?exists>,html:${component.html} </#if>         
    <#if component.autoLoad?exists>,autoLoad:'${component.autoLoad}' </#if>      
    <#if component.plugins?exists>,plugins:${component.plugins} </#if>
    <#if component.margins?exists>,margins:'${component.margins}' </#if>
    <#if childrenComponentNames?has_content> ,
    items:[<#list childrenComponentNames as childrenComponentNamesItem><#if childrenComponentNamesItem_index != 0>,</#if>${childrenComponentNamesItem}</#list>]
    </#if>
    <#if component.tbar?exists>,tbar:${component.tbar} </#if>
    <#if component.bbar?exists>,bbar:${component.bbar} </#if>   
});
