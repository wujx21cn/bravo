<@bravo.Page name="EnumerationTypeManager" title="基础数据类型管理">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="enumerationType_Find" tbar="Toolbar" region="north" frame="true" autoScroll="true" height="100" width="750"  title="基础数据类型查询列表" collapsible="true"  >  
			<@bravo.Toolbar name="Toolbar" valign="top">
				<@bravo.Button name="View" text="查询" iconCls="search" handler="searchForGrid(\\'enumerationType_Find\\',\\'enumerationType_Grid\\')"/>
				<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'enumerationType_Find\\')"/>
			</@bravo.Toolbar>	
				<@bravo.FieldSet  cols="1,1" rows="1,1" >	
				<@bravo.TextField fieldLabel="名称" fieldName="name" width="200"/>
				<@bravo.TextField fieldLabel="备注" fieldName="comments" width="200"/>
				</@bravo.FieldSet>
		</@bravo.FormPanel>    
		<@bravo.EditorGridPanel  region="center" tbar="Toodddlbar"  name="enumerationType_Grid"  contextDataName="enumerationTypes" dataProxy="./enumerationType!findAndRendJsonData.action" stripeRows="true"   collapsible="true" split="true">
			<@bravo.Toolbar name="Toodddlbar" valign="top" defaults="{bodyStyle:'margin-top: 0px;padding:0px 0px 0px 0px'}" >
				<@bravo.Button name="Add" text="新增" iconCls="add" handler="grid_doAdd(\\'enumerationType_Grid\\')"/>	
				<@bravo.Button name="Save" text="保存" iconCls="save" handler="grid_doSave(\\'./enumerationType!batchSave.action\\',\\'enumerationType_Grid\\')"/>		<@bravo.Button text="删除" iconCls="delete" handler="grid_doDel(\\'./enumerationType!batchRemove.action\\',\\'enumerationType_Grid\\')"/>	
			</@bravo.Toolbar>               
				<@bravo.Column dataIndex="id" hidden="true" name="id" header="ID" width="176"  sortable="true"  resizable="true"/>
				<@bravo.Column dataIndex="name" name="name" header="名称"  width="175"  sortable="true" resizable="true">
					<@bravo.TextField/> 
				</@bravo.Column>
				<@bravo.Column dataIndex="comments" name="comments" header="描述" width="175"  sortable="true"  resizable="true"    >
					<@bravo.TextField/> 
				</@bravo.Column>        
		</@bravo.EditorGridPanel>
	</@bravo.Viewport>
</@bravo.Page>