<@bravo.Page name="DepartmentManager" title="部门管理">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="department_find" tbar="Toolbar" region="north"  frame="true" autoScroll="true" height="150" width="750"  title="部门查询列表" collapsible="true"  >  
			<@bravo.Toolbar name="Toolbar" valign="top">
				<@bravo.Button name="View" text="查询" iconCls="search" handler="searchForGrid(\\'department_find\\',\\'department_grid\\')"/>
				<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'department_find\\')"/>
			</@bravo.Toolbar>
			<@bravo.FieldSet  cols="1,1,1;1,2" rows="1,1,1;1,1" >		
				<@bravo.TextField fieldLabel="部门编码" fieldName="code" width="160"/>
				<@bravo.TextField fieldLabel="名称" fieldName="name" width="160"/>
				<@bravo.PopuSelect fieldLabel="上级部门" fieldName="parentDepartment.name" readOnly="true" text="部门选择"  width="160" valueField="id" displayField="name" targetProxy="../common/department!query.action" targetGridName="department_grid" hiddenName="parentDepartment.id" />
				<@bravo.PopuSelect fieldLabel="部门经理" fieldName="manager.userName" readOnly="true" text="人员选择"  width="160" valueField="id" displayField="userName" targetProxy="../security/user!query.action" targetGridName="user_Grid" hiddenName="manager.id" />
				<@bravo.NumberField fieldLabel="电话号码" fieldName="telephone" width="160"/>
			</@bravo.FieldSet>
		</@bravo.FormPanel> 
		<@bravo.GridPanel  region="center"    name="department_grid"  dataProxy="./department!findAndRendJsonData.action" contextDataName="departments"  tbar="Toodddlbar" collapsible="true" split="true"  stripeRows="true" >
			<@bravo.Toolbar name="Toodddlbar" valign="top" >
				<@bravo.Button name="Add" text="新增" iconCls="add"  handler="gridOpenNewWin(\\'./department!add.action\\',\\'新增部门\\',\\'department_grid\\')"/>
				<@bravo.Button name="Delete" text="删除" iconCls="delete" handler="grid_doDel(\\'./department!batchRemove.action\\',\\'department_grid\\')"/>
			</@bravo.Toolbar>   
			<@bravo.Column hidden="true" name="id" header="ID" width="176"  sortable="true"  resizable="true"/>
			<@bravo.Column name="code" header="部门编码"  width="75"  sortable="true" resizable="true"/>
			<@bravo.Column name="name" header="名称"  width="75"  sortable="true" resizable="true"/>
			<@bravo.Column name="manager.userName" header="部门经理" width="100" sortable="true"  resizable="true" /> 
			<@bravo.Column name="parentDepartment.name" header="上级部门" width="100" sortable="true"  resizable="true"/> 
			<@bravo.Column name="telephone" header="电话"  width="50"  sortable="true" resizable="true"/>
			<@bravo.Column name="view" header="查看"   sortable="true" >
				<a href=\'javascript:gridOpenNewWin(\\"./department!view.action?id=@{rowValue.id?c}\\",\\"查看部门[@{rowValue.name}]\\",\\"department_grid\\")\' style=\\"TEXT-DECORATION:none\\"><span style=color:red;>查看</span></a>       
			</@bravo.Column>
		</@bravo.GridPanel>
	</@bravo.Viewport>
</@bravo.Page>