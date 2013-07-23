var ${component.name}_record = Ext.data.Record.create([
       <#if component.childColumns?has_content> 
         <#list component.childColumns as childColumnsItem>
	 <#if childColumnsItem_index != 0>,</#if>
	 {name:'${childColumnsItem.name}'}
	 </#list>
      </#if>
  ]);


var ${component.name}_proxy = new Ext.data.HttpProxy({
        method: 'POST',
        url:'${component.dataProxy}'
    });

var ${component.name}_store=new Ext.data.Store({
      proxy:${component.name}_proxy ,
      reader:new Ext.data.JsonReader({
	  <#if component.contextDataName?exists>
		root:'${component.contextDataName}',
	  <#elseif contextDataName?exists >
		root:'${contextDataName}',
	  </#if>
      id:'${component.entityKey}',
	  totalProperty: 'totalCount',
	  fields:${component.name}_record
      }),
	  queryParam:{},
      remoteSort: false
     });

  ${component.name}_store.on('beforeload',function(){
                  ${component.name}_store.baseParams={
		       contextDataName:'${component.contextDataName}',
		       contextDataFieldName:
		       '<#if component.childColumns?has_content><#list component.childColumns as childColumnsItem>${childColumnsItem.name}<#if childColumnsItem.value?exists&&""!=childColumnsItem.value>_@@_${childColumnsItem.value}</#if><#if childColumnsItem_has_next >_@_</#if></#list></#if>',
		       jsonGridData : 'Y'
		       }
		       });

var ${component.name}_sm = new Ext.grid.CheckboxSelectionModel();

var ${component.name}_cm = new Ext.grid.ColumnModel([
      new Ext.grid.RowNumberer(),
      ${component.name}_sm
       <#if component.childColumns?has_content> 
         <#list component.childColumns as childColumnsItem>
			 ,{dataIndex:'${childColumnsItem.name}'
			 <#if childColumnsItem.header?exists>,header:'${childColumnsItem.header}'
			 </#if><#if childColumnsItem.width?exists>,width:${childColumnsItem.width}
			 </#if><#if childColumnsItem.sortable?exists>,sortable:${childColumnsItem.sortable}
			 </#if><#if childColumnsItem.hidden?exists>,hidden:${childColumnsItem.hidden}
			 </#if><#if childColumnsItem.renderer?exists>,renderer:${childColumnsItem.renderer}
			 </#if><#if childColumnsItem.resizable?exists>,resizable:${childColumnsItem.resizable}
			 </#if>
	 }
	 </#list>
      </#if>

    ]);
	

	var ${component.name} = new Ext.grid.GridPanel({
		id:'${component.name}' ,
		store: ${component.name}_store,
                cm: ${component.name}_cm,   
                sm: ${component.name}_sm,
		bbar : new Ext.GridPagingToolbar( {
			pageSize : ${component.pageSize},
			store : ${component.name}_store,
			grid: ${component.name},
			displayInfo : true,
			emptyMsg : "没有数据" ,
			plugins: [new Ext.ux.PageSizePlugin('${component.name}')]
		})
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
		</#if><#if childrenComponentNames?has_content> 
		,items:[<#list childrenComponentNames as childrenComponentNamesItem><#if childrenComponentNamesItem_index != 0>,</#if>${childrenComponentNamesItem}</#list>]
		</#if><#if component.layout?exists>,layout:'${component.layout}' 
		</#if><#if component.layoutConfig?exists>,layoutConfig:${component.layoutConfig}
		</#if><#if component.monitorResize?exists>,monitorResize:${component.monitorResize} 
		<#-------com.cutty.bravo.core.ui.tags.container.component.PanelBean---->
		</#if><#if component.animCollapse?exists>,animCollapse:${component.animCollapse}
		</#if><#if component.anchor?exists>,anchor:'${component.anchor}' 
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
		</#if><#if component.split?exists>,split:${component.split}
                <#-------com.cutty.bravo.core.ui.tags.grid.component.GridPanelBean---->
        </#if><#if component.enableDragDrop?exists>,enableDragDrop:${component.enableDragDro}
		</#if><#if component.autoExpandColumn?exists>,autoExpandColumn:'${component.autoExpandColumn}'
		</#if><#if component.autoExpandMax?exists>,autoExpandMax:${component.autoExpandMax}
		</#if><#if component.autoExpandMin?exists>,autoExpandMin:${component.autoExpandMax}
		</#if><#if component.cm?exists>,cm:${component.cm}
		</#if><#if component.colModel?exists>,colModel:${component.colModel}
		</#if><#if component.columns?exists>,columns:${component.columns}
		</#if><#if component.deferRowRender?exists>,deferRowRender:${component.deferRowRender}
		</#if><#if component.disableSelection?exists>,disableSelection:${component.disableSelection}
		</#if><#if component.enableColumnHide?exists>,enableColumnHide:${component.enableColumnHide}
		</#if><#if component.enableColumnMove?exists>,enableColumnMove:${component.enableColumnMove}
		</#if><#if component.enableColumnResize?exists>,enableColumnResize:${component.enableColumnResize}
		</#if><#if component.enableHdMenu?exists>,enableHdMenu:${component.enableHdMenu}
		</#if><#if component.hideHeaders?exists>,hideHeaders:${component.hideHeaders}
		</#if><#if component.loadMask?exists>,loadMask:${component.loadMask}
		</#if><#if component.maxHeight?exists>,maxHeight:${component.maxHeight}
		</#if><#if component.minColumnWidth?exists>,minColumnWidth:${component.minColumnWidth}
		</#if><#if component.selModel?exists>,selModel:${component.selModel}
		</#if><#if component.sm?exists>,sm:${component.sm}
		</#if><#if component.store?exists>,store:${component.store}
		</#if><#if component.stripeRows?exists>,stripeRows:${component.stripeRows}
		</#if><#if component.trackMouseOver?exists>,trackMouseOver:${component.trackMouseOver}
		</#if><#if component.viewConfig?exists>,viewConfig:${component.viewConfig}
        </#if><#if component.view?exists>,view:${component.view}
		</#if><#if component.contextDataName?exists>,contextDataName:'${component.contextDataName}'
		</#if><#if component.contextDataFieldName?exists>,contextDataFieldName:'${component.contextDataFieldName}'
		</#if><#if component.dataProxy?exists>,dataProxy:'${component.dataProxy}'
		</#if><#if component.dataProxy?exists>,urlForExprtFile:'${component.dataProxy}'
		</#if>
});

${component.name}_store.load( 
 
{
	 
    callback:function(){
 
   },
    params : {
        start : 0,
        limit : ${component.pageSize}        
    }
});