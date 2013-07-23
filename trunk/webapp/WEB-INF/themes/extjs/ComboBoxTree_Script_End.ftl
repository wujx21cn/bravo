 <#assign mode="local"/>
 <#if component.dataProxy?exists>
   <#if component.dataProxy? index_of ('url[' )=0 >
 <#assign mode="remote"/>
    var ${component.name}_store=new Ext.data.Store({
       proxy:new Ext.data.HttpProxy({
       method:'POST',
       url:'${component.dataProxy?substring(4,component.dataProxy?length-1)}'
	}),
       reader:new Ext.data.JsonReader({
       root:'ComboBox_Data_Root',
       id:'id',
       fields:[
            {name:'id'},
            {name:'name'}
	      ]
       }),
       remoteSort:false
       });
       ${component.name}_store.baseParams= {
       contextDataName : '${component.contextDataName}',
       contextDataFieldName :'${component.valueField}_@_${component.displayField}',
       jsonComboBoxData : 'Y'
    }
  <#else>
    var ${component.name}_store = new Ext.data.SimpleStore({
       fields: ["${component.displayField}", "${component.valueField}"],
       data:  ${component.name}_array_data
    });
  </#if>
</#if>


var ${component.name} = new Ext.ux.ComboBoxTree({  
    <#if component.dataProxy?exists>
    store: ${component.name}_store,
	</#if>
	<#if component.fieldName?exists>name:'${component.fieldName}'
	</#if><#if component.name?exists &&  component.columnComBoBox == "true">,id:'${component.name?replace('___', '.')}'
	</#if><#if component.name?exists &&  component.columnComBoBox == "false">,hiddenName:'${component.fieldName}'
	</#if><#if component.fieldLabel?exists>,fieldLabel:'${component.fieldLabel}'
	</#if><#if component.emptyText?exists>,emptyText:'${component.emptyText}'
	</#if><#if component.width?exists>,width:${component.width}
	</#if><#if component.value?exists>,value:'${component.value}'
	</#if><#if component.style?exists>,style:'${component.style}'
	</#if><#if component.displayField?exists>,displayField:'${component.displayField}'
	</#if><#if component.valueField?exists>,valueField:'${component.valueField}'
    </#if>
	,mode:'${mode}'
    ,renderto : 'comboBoxTree'
    ,tree : new Ext.tree.TreePanel({  
        loader: new Ext.tree.TreeLoader({dataUrl:'${component.dataTreeProxy}'
		,baseParams: {contextDataName:'treeData',jsonCheckedTreeData:'Y'} 
		}),     
        root : new Ext.tree.AsyncTreeNode({id:'${component.rootId}',text:'${component.rootText}'})     
    })

   <#if component.selectNodeModel?exists>,selectNodeModel:'${component.selectNodeModel}'</#if>
   <#if component.forceSelection?exists>
    ,forceSelection:${component.forceSelection}
   <#else>
    ,forceSelection:true
   </#if>
    ,triggerAction:'${component.triggerAction?default("all")}'
});    
