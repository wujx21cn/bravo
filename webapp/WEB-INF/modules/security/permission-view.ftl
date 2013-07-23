<@bravo.Page name="permissionView" title="系统权限查看">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="permissionForm" region="center" border="false"  dataProxy="./permission!saveAndRendJsonData.action" tbar="toolBar">  
			<@bravo.Toolbar name="toolBar" valign="bottom">
                <@bravo.Button name="Save" tooltip="'保存'" text="保存" iconCls="add" handler="ajaxSubmitForm(\\'permissionForm\\')"/>
			</@bravo.Toolbar>
				<@bravo.Hidden  name="id" width="500" />
				<@bravo.TextField fieldLabel="名称" name="name" width="500" />
				<@bravo.TextField fieldLabel="备注" name="comments"  allowBlank="false"  width="500"/>
			
			<@bravo.TabPanel activeTab="0"  name="tab" height="390">
				

				<@bravo.TreePanel name="menuCheckTree" title="相关菜单列表"  tbar="permissionFunToolBar" text="相关菜单列表" region="center" rootVisible="true"  checkModel="cascade" onlyLeafCheckable="false" animate="false" margins="0 0 0 5" checkTreeDataProxy="../common/menuFunction!viewCheckedMenuTree.action?permisId=%{formValue.id?c}" contextData="treeData" nodeId="0" >
		                      	<@bravo.Toolbar name="permissionFunToolBar" valign="top">
						<@bravo.Button name="Save" text="保存" iconCls="save" handler="onItemClick(\\'menuCheckTree\\',\\'%{formValue.id?c}\\')"/>
					</@bravo.Toolbar>	
				</@bravo.TreePanel>

				<@bravo.GridPanel title="相关角色列表" tbar="permissionRoleToolBar" name="permissionRoleGrid" dataProxy="./role!findAndRendJsonData.action?permissions.id=%{formValue.id?c}"  contextDataName="Roles">
					<@bravo.Toolbar name="permissionRoleToolBar" valign="top">
						<@bravo.M2MSelectButton text="选择添加角色" iconCls="add" targetProxy="./role!query.action" targetGridName="Role_Grid" originGridName="permissionRoleGrid" entityName="Permission" fieldName="roles" entityId="%{formValue.id?c}"/>
						<@bravo.M2MRemoveButton text="删除角色" iconCls="delete" originGridName="permissionRoleGrid" entityName="Permission" fieldName="roles" entityId="%{formValue.id?c}"/>
					</@bravo.Toolbar>			  
					<@bravo.Column  hidden="true" name="id" header="ID" width="10"  sortable="true" />
					<@bravo.Column name="name" header="名称" width="100" sortable="true" />
					<@bravo.Column name="comments" header="备注" width="400"  sortable="true"/>
				</@bravo.GridPanel>

				<@bravo.GridPanel title="相关资源列表" tbar="permissionResourceToolBar" name="permissionResourceGrid" dataProxy="./resource!findAndRendJsonData.action?permission.id=%{formValue.id?c}"  contextDataName="Resources">
					<@bravo.Toolbar name="permissionResourceToolBar" valign="top">
						<@bravo.M2MSelectButton text="选择添加资源" iconCls="add" targetProxy="./resource!query.action" targetGridName="Resource_Grid" originGridName="permissionResourceGrid" entityName="Permission" fieldName="resources" entityId="%{formValue.id?c}"/>
						<@bravo.M2MRemoveButton text="删除资源" iconCls="delete" originGridName="permissionResourceGrid" entityName="Permission" fieldName="resources" entityId="%{formValue.id?c}"/>	
					</@bravo.Toolbar>			  
					<@bravo.Column  hidden="true" name="id" header="ID" width="10"  sortable="true" />
					<@bravo.Column name="name" header="名称" width="100" sortable="true" />
					<@bravo.Column name="module.name" header="模块名" width="100"  sortable="true"/>
					<@bravo.Column name="resType.name" header="资源类型" width="100"  sortable="true"/>
					<@bravo.Column name="resString" header="资源字段" width="100"  sortable="true"/>
					<@bravo.Column name="comments" header="备注" width="100"  sortable="true"/>
				</@bravo.GridPanel>

				<@bravo.GridPanel title="相关按钮列表" tbar="buttonPermisToolBar" name="buttonPermisGrid" dataProxy="./buttonResource!findAndRendJsonData.action?permissions.id=%{formValue.id?c}"  contextDataName="buttonResources">
					<@bravo.Toolbar name="buttonPermisToolBar" valign="top">
						<@bravo.M2MSelectButton text="选择添加按钮" iconCls="add" targetProxy="./buttonResource!query.action" targetGridName="Button_Grid" originGridName="buttonPermisGrid" entityName="Permission" fieldName="buttonResources" entityId="%{formValue.id?c}"/>
						<@bravo.M2MRemoveButton text="删除按钮" iconCls="delete" originGridName="buttonPermisGrid" entityName="Permission" fieldName="buttonResources" entityId="%{formValue.id?c}"/>	
					</@bravo.Toolbar>			  
					<@bravo.Column  hidden="true" name="id" header="ID" width="10"  sortable="true" />
					<@bravo.Column name="name" header="按钮名称" width="400" sortable="true" />
					<@bravo.Column name="operCondition" header="操作条件" width="100"  sortable="true"/>
					 
					<@bravo.Column name="comments" header="备注" width="100"  sortable="true"/>
				</@bravo.GridPanel>

			<@bravo.TreePanel name="buttonCheckTree" title="相关实体操作列表"  tbar="permissionFunToolBar1" text="实体列表" region="center" rootVisible="true"  checkModel="cascade" onlyLeafCheckable="false" animate="false" margins="0 0 0 5" checkTreeDataProxy="../security/entityOperatePermissionRelation!entityTree.action?permisId=%{formValue.id?c}" contextData="entityOpPermisTreeData" nodeId="0">
		                      	<@bravo.Toolbar name="permissionFunToolBar1" valign="top">
						<@bravo.Button name="Save" text="保存" iconCls="save" handler="onSaveEntityOpPermisClick(\\'buttonCheckTree\\',\\'%{formValue.id?c}\\')"/>
					</@bravo.Toolbar>	
				</@bravo.TreePanel>
				

			</@bravo.TabPanel>
		</@bravo.FormPanel>
	</@bravo.Viewport>
</@bravo.Page>

<script language="javaScript">
function onItemClick(treeName,permisID){
var treePanel = Ext.getCmp(treeName);
var checkedNodes = treePanel.getChecked();//tree必须事先创建好. 
var checkedIDArray = new Array(checkedNodes.length); 
 
var checkTreeRenderProxy = bravoHome+'/security/permission!checkedMenuTreeSave.action';
for(var i=0;i<checkedNodes.length;i++){ 
  checkedIDArray[i] =  checkedNodes[i].id;
  
  } 
  
	Ext.Ajax.request({            
	    method : 'POST'     
	   ,url: checkTreeRenderProxy
	   ,params: {checkedTreeIDs:checkedIDArray,permisID:permisID}
	   ,success: function(o) { 
	     if ("success" == o.responseText){
	        Ext.MessageBox.alert('消息', '权限保存成功!');
	     } else {
		Ext.MessageBox.alert('消息', '权限保存失败!<br/><font color=red>'+o.responseText+'</font>');
		}}
	  ,failure: function(form, action) { Ext.Msg.alert('消息', '权限保存成功!')}
	});
}

//保存按钮的权限Tree,传递到后台的是一个组数，格式为 "实体名.操作权限"
function onSaveEntityOpPermisClick(treeName,permisID){
var treePanel = Ext.getCmp(treeName);
var checkedNodes = treePanel.getChecked();//tree必须事先创建好. 

var checkedNameArray = new Array(); 

var entityName = "";
 var checkTreeRenderProxy = bravoHome+'/security/permission!entinyOpPermisTreeSave.action';
for(var i=0;i<checkedNodes.length;i++){ 
   
  if(checkedNodes[i].leaf){
	var index = checkedNameArray.length;
	checkedNameArray[index] = entityName + "." + checkedNodes[i].text;
  } else{
	entityName = checkedNodes[i].text;
	}
}
 	Ext.Ajax.request({            
	    method : 'POST'     
	   ,url: checkTreeRenderProxy
	   ,params: {checkedTreeNames:checkedNameArray,permisID:permisID}
	   ,success: function(o) { 
	     if ("success" == o.responseText){
	        Ext.MessageBox.alert('消息', '权限保存成功!');
	     } else {
		Ext.MessageBox.alert('消息', '权限保存失败!<br/><font color=red>'+o.responseText+'</font>');
		}}
	  ,failure: function(form, action) { Ext.Msg.alert('消息', '权限保存失败!')}
	});

}
</script>