<@bravo.Page name="SystemInformation_query" title="系统信息管理">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="SystemInformation_query" tbar="formToolBar"  region="north" frame="true" autoScroll="true" height="130" width="780" autoScroll="true"  title="系统信息管理列表"  collapsible="true" > 
			<@bravo.Toolbar name="formToolBar" valign="top">
				<@bravo.Button name="View" text="查找" iconCls="search" handler="searchForGrid(\\'SystemInformation_query\\',\\'SystemInformation_grid\\')"/>
				<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'SystemInformation_query\\')"/>
			</@bravo.Toolbar>
			<@bravo.FieldSet labelWidth="80" cols="1,1,1;1;1" rows="1,1,1;1;1" >					
				      <@bravo.TextField fieldLabel="系统名称" name="systemName" width="160" />
				      <@bravo.TextField fieldLabel="版本号" name="version" width="160" />
				      <@bravo.DateTime fieldLabel="发布时间早于" name="releaseDate_index_1" linkAgeField="releaseDate_index_2" width="160"    /> 
				      <@bravo.DateTime fieldLabel="发布时间晚于" name="releaseDate_index_2" width="160"    />
				      <@bravo.TextField fieldLabel="备注信息" name="comments" width="160" />	
			</@bravo.FieldSet>
				     <@bravo.Hidden name="releaseDate_index_1_restriction" value="ge" /> 
				     <@bravo.Hidden name="releaseDate_index_2_restriction" value="le" />	
		</@bravo.FormPanel>   

		<@bravo.EditorGridPanel  region="center" tbar="gridToolBar" split="true" name="SystemInformation_grid"  contextDataName="SystemInformations" dataProxy="./systemInformation!findAndRendJsonData.action" stripeRows="true"  collapsible="true" >
			<@bravo.Toolbar name="gridToolBar" valign="top" >
				<@bravo.Button name="Add" text="新增" iconCls="add" handler="grid_doAdd(\\'SystemInformation_grid\\')"/>
				<@bravo.Button name="Save" text="保存" iconCls="save" handler="grid_doSave(\\'./systemInformation!batchSave.action\\',\\'SystemInformation_grid\\')"/>
				<@bravo.Button name="Delete" text="删除" iconCls="delete" handler="grid_doDel(\\'./systemInformation!batchRemove.action\\',\\'SystemInformation_grid\\')"/>
			</@bravo.Toolbar>   
			     <@bravo.Column hidden="true" name="id" header="ID" width="0"  sortable="true"  resizable="true" />
			     <@bravo.Column dataIndex="systemName" name="systemName" width="100" header="系统名称" sortable="true" resizable="true" >
			     	<@bravo.TextField fieldName="systemName" />
			     </@bravo.Column>  
			     <@bravo.Column dataIndex="version" name="version" width="100" header="版本号" sortable="true" resizable="true" >
			     	<@bravo.TextField fieldName="version" />
			     </@bravo.Column>
			     <@bravo.Column dataIndex="releaseDate" name="releaseDate" width="100" header="发布日期" sortable="true" resizable="true" renderer="gridDateFormat" >
			     	<@bravo.DateTime fieldName="releaseDate"/>
			     </@bravo.Column>
			     <@bravo.Column dataIndex="comments" name="comments" width="440" header="备注信息" sortable="true" resizable="true" >
			     	<@bravo.TextField fieldName="comments" />
			     </@bravo.Column> 			   
		</@bravo.EditorGridPanel>
	</@bravo.Viewport>
</@bravo.Page>

