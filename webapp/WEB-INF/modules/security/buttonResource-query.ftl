<@bravo.Page name="ButtonManager" title="按钮管理">
	<@bravo.Viewport  layout="border">
	<@bravo.FormPanel name="Button_Find" tbar="Toolbar" region="north" frame="true" autoScroll="true" height="130"  title="按钮查询列表"  collapsible="true"  >
		<@bravo.Toolbar name="Toolbar" valign="top">
			<@bravo.Button name="View" text="查询" iconCls="search" handler="searchForGrid(\\'Button_Find\\',\\'Button_Grid\\')"/>
			<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'Button_Find\\')"/>
		</@bravo.Toolbar>
		<@bravo.FieldSet  cols="1,1;2" rows="1,1;1" >
			<@bravo.TextField fieldLabel="按钮名称" fieldName="name" width="160"/>
			<@bravo.TextField fieldLabel="操作条件" fieldName="operCondition" width="160"/>
			 
			<@bravo.TextField fieldLabel="备注" fieldName="comments" width="560"/>
		</@bravo.FieldSet>
	</@bravo.FormPanel>
	<@bravo.EditorGridPanel  region="center" tbar="Toodddlbar"  name="Button_Grid"  contextDataName="buttons"  dataProxy="./buttonResource!findAndRendJsonData.action" stripeRows="true"  collapsible="true" split="true">
		<@bravo.Toolbar name="Toodddlbar" valign="top" defaults="{bodyStyle:'margin-top: 0px;padding:0px 0px 0px 0px'}" >
			<@bravo.Button name="Add" text="新增" iconCls="add" handler="grid_doAdd(\\'Button_Grid\\')"/>
			<@bravo.Button name="Save" text="保存" iconCls="save" handler="grid_doSave(\\'./buttonResource!batchSave.action\\',\\'Button_Grid\\')"/>      
			<@bravo.Button name="Delete" text="删除" iconCls="delete" handler="grid_doDel(\\'./buttonResource!batchRemove.action\\',\\'Button_Grid\\')"/>
		</@bravo.Toolbar>
		<@bravo.Column hidden="true" name="id" header="ID" width="50"  sortable="true"  resizable="true"/>
		<@bravo.Column name="name" header="按钮名称"  width="360"  sortable="true" resizable="true">
			<@bravo.TextField/>
		</@bravo.Column>
		 
		 <@bravo.Column name="operCondition" header="操作条件"  width="60"  sortable="true" resizable="true">
			<@bravo.TextField/>
		</@bravo.Column>
		 
		<@bravo.Column name="comments" header="备注" width="150"  sortable="true"  resizable="true"    >
			<@bravo.TextField/>
		</@bravo.Column>
	</@bravo.EditorGridPanel>
	</@bravo.Viewport>
</@bravo.Page>

