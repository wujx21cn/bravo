<@bravo.Page name="Teacher_query" title="Teacher_query">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="Teacher_query" tbar="formToolBar" region="north" frame="true" autoScroll="true" height="150" width="780" autoScroll="true"  title="教师管理列表"  collapsible="true" > 
			<@bravo.Toolbar name="formToolBar" valign="top">
				<@bravo.Button name="View" text="查找" iconCls="search" handler="searchForGrid(\\'Teacher_query\\',\\'Teacher_grid\\')"/>
				<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'Teacher_query\\')"/>
			</@bravo.Toolbar>
			<@bravo.FieldSet labelWidth="60" cols="1,1;1,1" rows="1,1;1,1" >
				      <@bravo.TextField fieldLabel="姓名" name="name" width="160" />
				      <@bravo.TextField fieldLabel="性别" name="gender" width="160" />
				      <@bravo.NumberField fieldLabel="年龄" name="age" width="160" />
				      <@bravo.NumberField fieldLabel="工龄" name="workAge" width="160" />
			</@bravo.FieldSet>
		</@bravo.FormPanel>   

		<@bravo.GridPanel  region="center" tbar="gridToolBar" split="true"  name="Teacher_grid"  contextDataName="Teachers" dataProxy="./teacher!findAndRendJsonData.action" collapsible="true" stripeRows="true">
			<@bravo.Toolbar name="gridToolBar" valign="top" >
				<@bravo.Button name="Add" text="新增" iconCls="add" handler="gridOpenNewWin(\\'./teacher!add.action\\',\\'新增教师\\',\\'Teacher_grid\\')"/>
				<@bravo.Button name="Delete" text="删除" iconCls="delete" handler="grid_doDel(\\'./teacher!batchRemove.action\\',\\'Teacher_grid\\')"/>
			</@bravo.Toolbar>   
			     <@bravo.Column hidden="true" name="id" header="ID" width="0"  sortable="true"  resizable="true" />
			     <@bravo.Column dataIndex="name" name="name" header="姓名" sortable="true" resizable="true" />     
			     <@bravo.Column dataIndex="gender" name="gender" header="性别" sortable="true" resizable="true" />     
			     <@bravo.Column dataIndex="age" name="age" header="年龄" sortable="true" resizable="true" />     
			     <@bravo.Column dataIndex="workAge" name="workAge" header="工龄" sortable="true" resizable="true" />      
			     <@bravo.Column name="view" header="查看" sortable="true" >
			         <a href=\'javascript:gridOpenNewWin(\\"./teacher!view.action?id=@{rowValue.id?c}\\",\\"view_[@{rowValue.name}]\\",\\"Teacher_grid\\")\' style=\\"TEXT-DECORATION:none\\"><span style=color:red;>查看</span></a>
			     </@bravo.Column>			   
		</@bravo.GridPanel>
	</@bravo.Viewport>
</@bravo.Page>

