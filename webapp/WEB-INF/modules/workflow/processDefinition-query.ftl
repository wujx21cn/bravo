<@bravo.Page name="ProcessDefinitionManager" title="流程定义管理">
	<@bravo.Viewport  layout="border">
		    <@bravo.FormPanel name="processDefinition_find" tbar="Toolbar" region="north"  frame="true" autoScroll="true" height="110" width="750" title="流程定义查询列表" collapsible="true"  >  
			<@bravo.Toolbar name="Toolbar" valign="top">
				<@bravo.Button name="View" text="查询" iconCls="search" handler="searchForGrid(\\'processDefinition_find\\',\\'processDefinition_grid\\')"/>
				<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'processDefinition_find\\')"/>
			</@bravo.Toolbar>
				<@bravo.FieldSet  cols="1,1" rows="1,1" >		
					<@bravo.TextField fieldLabel="流程名称" name="name" width="160"/>
					<@bravo.TextField fieldLabel="描述" name="description" width="160"/>
				</@bravo.FieldSet>
			 </@bravo.FormPanel>  
            <@bravo.GridPanel  region="center" tbar="Toodddlbar" name="processDefinition_grid"  stripeRows="true"  contextDataName="processDefinitions" dataProxy="./processDefinition!findAndRendJsonData.action"  collapsible="true" split="true">			 
			<@bravo.Toolbar name="Toodddlbar" valign="top" >
				<@bravo.Button name="Add" text="新增流程定义" iconCls="add" handler="gridOpenNewWin(\\'./processDefinition!designer.action\\',\\'新增流程定义\\',\\'processDefinition_grid\\')"/>
				</@bravo.Toolbar>               
			    <@bravo.Column hidden="true" name="id" header="ID"  sortable="true"  resizable="true"/>
				<@bravo.Column name="name" header="流程名称"  width="200"  sortable="true" resizable="true"/>
				<@bravo.Column name="preview" header="查看"   sortable="true" >
					<a href=\'javascript:gridOpenNewWin(\\"./processDefinition!previewDefinition.action?id=@{rowValue.id?c}\\",\\"查看流程定义[@{rowValue.name}]\\",\\"processDefinition_grid\\")\' style=\\"TEXT-DECORATION:none\\"><span style=color:lime;>查看</span></a>       
				</@bravo.Column>
				<@bravo.Column name="view" header="修改"   sortable="true" >
					<a href=\'javascript:gridOpenNewWin(\\"./processDefinition!viewDefinition.action?id=@{rowValue.id?c}\\",\\"修改流程定义[@{rowValue.name}]\\",\\"processDefinition_grid\\")\' style=\\"TEXT-DECORATION:none\\"><span style=color:red;>修改</span></a>       
				</@bravo.Column>
				<@bravo.Column name="view2" header="发起流程"   sortable="true" >
					<a href=\'javascript:gridOpenNewWin(\\"@{rowValue.description}\\",\\"发起流程实例[@{rowValue.name}]\\",\\"processDefinition_grid\\")\' style=\\"TEXT-DECORATION:none\\"><span style=color:red;>发起流程</span></a>       
				</@bravo.Column>
			</@bravo.GridPanel>
    </@bravo.Viewport>
</@bravo.Page>
