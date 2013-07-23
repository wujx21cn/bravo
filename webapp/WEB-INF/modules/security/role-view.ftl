<@bravo.Page name="roleView" title="系统角色查看" cache="true">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="roleForm" region="center" border="false" tbar="toolBar" dataProxy="./role!saveAndRendJsonData.action?id=%{formValue.id?c}" contextDataName="menuData" height="490">  
			<@bravo.Toolbar name="toolBar" valign="bottom">
                <@bravo.Button name="Save" tooltip="'保存'" text="保存" iconCls="add" handler="ajaxSubmitForm(\\'roleForm\\')"/>
			</@bravo.Toolbar>
				<@bravo.TextField fieldLabel="名称" name="name" width="500" />
				<@bravo.TextField fieldLabel="备注" name="comments"  allowBlank="false"  width="500"/>
			<@bravo.TabPanel activeTab="0"  name="tab" height="390">
				<@bravo.GridPanel title="权限列表" tbar="rolePermissionToolBar" name="rolePermissionGrid" dataProxy="./permission!findAndRendJsonData.action?roles.id=%{formValue.id?c}"  contextDataName="Permissions">
					<@bravo.Toolbar name="rolePermissionToolBar" valign="top">
						<@bravo.M2MSelectButton text="选择添加权限" iconCls="add" targetProxy="./permission!query.action" targetGridName="Permission_Grid" originGridName="rolePermissionGrid" entityName="Role" fieldName="permissions" entityId="%{formValue.id?c}"/>
						<@bravo.M2MRemoveButton text="删除权限" iconCls="delete" originGridName="rolePermissionGrid" entityName="Role" fieldName="permissions" entityId="%{formValue.id?c}"/>
					</@bravo.Toolbar>			  
					<@bravo.Column  hidden="true" name="id" header="ID" width="10"  sortable="true" />
					<@bravo.Column name="name" header="权限名" width="100" sortable="true" />
					<@bravo.Column name="comments" header="备注" width="100"  sortable="true"/>
				</@bravo.GridPanel>
				<@bravo.GridPanel title="相关人员列表" tbar="roleUserToolBar" name="roleUserGrid" dataProxy="./user!findAndRendJsonData.action?roles.id=%{formValue.id?c}"  contextDataName="Users">
					<@bravo.Toolbar name="roleUserToolBar" valign="top">
						<@bravo.M2MSelectButton text="选择添加人员" iconCls="add" targetProxy="./user!query.action" targetGridName="user_Grid" originGridName="roleUserGrid" entityName="Role" fieldName="users" entityId="%{formValue.id?c}"/>
						<@bravo.M2MRemoveButton text="删除人员" iconCls="delete" originGridName="roleUserGrid" entityName="Role" fieldName="users" entityId="%{formValue.id?c}"/>
					</@bravo.Toolbar>			  
					<@bravo.Column hidden="true" name="id" header="ID" width="0"  sortable="true"  resizable="true"     />
					<@bravo.Column  name="loginid" header="登陆名"  width="175"  sortable="true" resizable="true"     />
					<@bravo.Column dataIndex="userName" name="userName" header="用户名" width="175"  sortable="true" resizable="true" />     
					<@bravo.Column name="department.name" header="部门名称" width="175"  sortable="true"  resizable="true"     />
					<@bravo.Column name="view" header="查看" sortable="true" >
						<a href=\'javascript:gridOpenNewWin(\\"./user!view.action?id=@{rowValue.id?c}\\",\\"查看人员[@{rowValue.userName}]\\",\\"roleUserGrid\\")\' style=\\"TEXT-DECORATION:none\\"><span style=color:red;>查看</span></a>
					</@bravo.Column>		
				</@bravo.GridPanel>
			</@bravo.TabPanel>
		</@bravo.FormPanel>
	</@bravo.Viewport>
</@bravo.Page>

