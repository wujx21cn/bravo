<@bravo.Page name="ProcessInstanceManager" title="未完成流程管理">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="processInstance_find" tbar="Toolbar" region="north"  frame="true" autoScroll="true" height="150" width="750"  title="未完成流程查询列表" collapsible="true"  >  
			<@bravo.Toolbar name="Toolbar" valign="top">
				<@bravo.Button name="View" text="查询" iconCls="search" handler="searchForGrid(\\'processInstance_find\\',\\'processInstance_grid\\')"/>
				<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'processInstance_find\\')"/>
			</@bravo.Toolbar>
			<@bravo.FieldSet labelWidth="105" cols="1,1;1,1" rows="1,1;1,1" >		
				<@bravo.TextField fieldLabel="任务名称" fieldName="name" width="160"/>
				<@bravo.TextField fieldLabel="流程名称" fieldName="processInstance.processDefinition.name" width="160"/>
				<@bravo.DateTime fieldLabel="流程发起时间早于" name="processInstance.start_index_2" linkAgeField="processInstance.start_index_1"  width="160"/>
				<@bravo.DateTime fieldLabel="流程发起时间晚于" name="processInstance.start_index_1"   width="160"/>	
			</@bravo.FieldSet>

				<@bravo.Hidden name="processInstance.start_index_1_restriction" value="ge" />
				<@bravo.Hidden name="processInstance.start_index_2_restriction"  value="le" />
		</@bravo.FormPanel> 
		<@bravo.GridPanel  region="center"  stripeRows="true"  name="processInstance_grid"  dataProxy="./processInstance!findProcess.action?processInstance_restriction=isNotNull&processInstance.isSuspended=0&processInstance.end_restriction=isNull"  contextDataName="processInstances" collapsible="true" >  
			<@bravo.Column hidden="true" name="id" header="ID" width="176"  sortable="true"  resizable="true"/>
			<@bravo.Column name="processInstance.processDefinition.name" header="流程名称"  width="175"  sortable="true" resizable="true"/>
			<@bravo.Column name="processStarter.userName" header="流程发起人"  width="75"  sortable="true" resizable="true"/>
			<@bravo.Column name="processInstance.start" header="流程发起时间"  width="100"  sortable="true" resizable="true"/>
			<@bravo.Column name="view" header="查看"   sortable="true" >
				<a href=\'javascript:gridOpenNewWin(\\"../widgets/workflow/editors/processDefinition!previewDefinitionByWorkFlowDomainId.action?id=@{rowValue.id?c}\\",\\"查看任务[@{rowValue.name}]\\",\\"processInstance_grid\\")\' style=\\"TEXT-DECORATION:none\\"><span style=color:red;>查看</span></a>       
			</@bravo.Column>
		</@bravo.GridPanel>
	</@bravo.Viewport>
</@bravo.Page>