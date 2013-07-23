<@bravo.Page name="permissionView" title="初始化数据">
	<@bravo.Viewport  layout="border">	
		<@bravo.TabPanel activeTab="0" region="center" name="tab" height="490">
		
			<@bravo.TreePanel name="initDataButtonCheckTree" title="实体列表"  tbar="permissionFunToolBar1" text="实体列表" rootVisible="true"  checkModel="cascade" onlyLeafCheckable="false" animate="false" margins="0 0 0 5" checkTreeDataProxy="../common/initData!entityTree.action" contextData="initDataTreeData" nodeId="0">
	            <@bravo.Toolbar name="permissionFunToolBar1" valign="top">
					<@bravo.Button name="Save" text="导出数据" iconCls="save" handler="onSaveEntityOpPermisClickdd(\\'permissionForm\\',\\'initDataButtonCheckTree\\')"/>
				</@bravo.Toolbar>	
			</@bravo.TreePanel>
		
			<@bravo.FormPanel name="importData_FormPanel" title="数据列表" tbar="Toolbar">
					  
				<@bravo.Toolbar name="Toolbar" valign="top">
					<@bravo.Button name="View" text="搜索" iconCls="search" handler="searchForGrid(\\'importData_FormPanel\\',\\'FileEntity_grid\\')"/>
					<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'importData_FormPanel\\')"/>
				</@bravo.Toolbar>
		
				<@bravo.TextField fieldLabel="文件名称" name="fileNameFilter" width="600"/>
				
				<@bravo.TextField fieldLabel="实体名称" name="entityNameFilter" width="600"/>
				 
				<@bravo.GridPanel name="FileEntity_grid" title="数据列表" tbar="gridToolBar" contextDataName="FileEntity" dataProxy="../common/initData!dataFileEntityView.action" height="414" anchor="100%">          	   
					<@bravo.Toolbar name="gridToolBar" valign="top" >
						<@bravo.Button name="Import" text="导入数据" iconCls="save" handler="importSelected(\\'./initData!importData.action\\',\\'FileEntity_grid\\')"/>
					</@bravo.Toolbar>					
					<@bravo.Column hidden="true" dataIndex="fullFileName" name="fullFileName" width="120" header="文件全名" sortable="true" resizable="true" />
					<@bravo.Column dataIndex="fileName" name="fileName" width="120" header="文件名" sortable="true" resizable="true" />     
					<@bravo.Column dataIndex="entityName" name="entityName" width="510" header="实体名" sortable="true" resizable="true" />
					<@bravo.Column hidden="true" dataIndex="sheetNo" name="sheetNo" width="120" header="工作薄编号" sortable="true" resizable="true" />
				</@bravo.GridPanel>
				
			</@bravo.FormPanel>
			
		</@bravo.TabPanel>
	</@bravo.Viewport>
</@bravo.Page>

<script language="javaScript">
 
//保存按钮的权限Tree,传递到后台的是一个组数，格式为 "实体名.操作权限"
function onSaveEntityOpPermisClickdd(form,treeName){
var treePanel = Ext.getCmp(treeName);
var checkedNodes = treePanel.getChecked();//tree必须事先创建好. 
 
var checkedNameArray = new Array(); 

 var entityName = "";
 var initDataCheckTreeRenderProxy = bravoHome+'/common/initData!exportData.action';
 var nowDate = new Date();
 var fileName = 'excel'+nowDate.getTime()+'.xls';
 var contentType = 'application/vnd.ms-excel';
 
 var leavesNumber = 0;
 if(checkedNodes.length <= 0){
	Ext.Msg.alert('消息', '你无选择任何节点！');
	return;
	}
for(var i=0;i<checkedNodes.length;i++){ 
   
  if(checkedNodes[i].leaf){
	leavesNumber = leavesNumber + 1;
	var index = checkedNameArray.length;
	checkedNameArray[index] = entityName + "." + checkedNodes[i].text;
  } else{
	entityName = checkedNodes[i].text;
	}
}
 
 if(leavesNumber > 10){
	Ext.Msg.alert('消息', '选择节点需小于 10 !!!');
	return;
 }
Ext.getBody().mask("数据导出中，请稍候...","x-mask-loading");
Ext.Ajax.request({        
			
   
    method : 'POST'  
   
   ,url: initDataCheckTreeRenderProxy
   ,params: {initDataCheckedTreeNames:checkedNameArray,fileName:fileName}
   ,success: function(o) { 
    
      if ("success" == o.responseText){
		Ext.getBody().unmask();   
	        downloadFile(fileName,contentType);
	} 
    }
  ,failure: function(form, action) { 
	Ext.getBody().unmask();   
	Ext.Msg.alert('消息', '数据导出失败!!!')
	}
});

}

function importSelected(url,grid){
	var gridBody = Ext.getCmp(grid);
	if (!gridBody.getSelectionModel().hasSelection()){
		Ext.MessageBox.alert('提示框', '对不起，您没有选择需要导入的记录！');
		return;
	}
	var selectRecords = gridBody.getSelectionModel().getSelections();
	var paramStr = '';
	for (var i = 0, len = selectRecords.length; i < len; i++) {
		var selectRecord = selectRecords[i].data;
		if (paramStr.length > 0) {
			paramStr = paramStr + '&';
		}
		paramStr = paramStr + 'select_' + i + '_fullFileName=' + selectRecord["fullFileName"] ;
		paramStr = paramStr + '&select_' + i + '_fileName=' + selectRecord["fileName"] ;
		paramStr = paramStr + '&select_' + i + '_entityName=' + selectRecord["entityName"] ;
		paramStr = paramStr + '&select_' + i + '_sheetNo=' + selectRecord["sheetNo"] ;
		paramStr = paramStr +  '&select_record_num_'+i+"=Y";
	}

	Ext.Ajax.request({            
	   method : 'POST',             
	   url: url,  
	   success:  function(o) {
							   	if ("success" == o.responseText){
							        Ext.MessageBox.alert('消息', '数据导入成功!');
									gridBody.store.reload();
							    } else {
							        Ext.MessageBox.alert('消息', '数据导入失败!<br/><font color=red>'+o.responseText+'</font>');
							    }
							 },
	   failure:  function(form, action) {Ext.MessageBox.alert('消息', '数据导入失败!');} ,
	   params:   paramStr
	});	
}

</script>
