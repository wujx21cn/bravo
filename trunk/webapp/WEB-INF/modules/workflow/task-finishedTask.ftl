<@bravo.Page name="TaskInstanceManager" title="已处理任务管理">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="taskInstance_find" tbar="Toolbar" region="north"  frame="true" autoScroll="true" height="130" width="750"  title="已处理任务查询列表" collapsible="true"  >  
			<@bravo.Toolbar name="Toolbar" valign="top">
				<@bravo.Button name="View" text="查询" iconCls="search" handler="searchForGrid(\\'taskInstance_find\\',\\'taskInstance_grid\\')"/>
				<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'taskInstance_find\\')"/>
			</@bravo.Toolbar>
			<@bravo.FieldSet labelWidth="105" cols="1,1,1;1,1,1" rows="1,1,1;1,1,1" >		
				<@bravo.TextField fieldLabel="任务名称" fieldName="name" width="120"/>
				<@bravo.TextField fieldLabel="流程名称" fieldName="processInstance.processDefinition.name" width="120"/>
				<@bravo.DateTime fieldLabel="创建时间早于" name="create_index_1" linkAgeField="create_index_2" width="120"/>
				<@bravo.DateTime fieldLabel="创建时间晚于" name="create_index_2" width="120"/>	
				<@bravo.DateTime fieldLabel="流程发起时间早于" name="processInstance.start_index_1" linkAgeField="processInstance.start_index_2" width="120"/>
				<@bravo.DateTime fieldLabel="流程发起时间晚于" name="processInstance.start_index_2" width="120"/>	
			</@bravo.FieldSet>
				<@bravo.Hidden name="create_index_1_restriction" value="ge" />
				<@bravo.Hidden name="create_index_2_restriction"  value="le" />
				<@bravo.Hidden name="processInstance.start_index_1_restriction" value="ge" />
				<@bravo.Hidden name="processInstance.start_index_2_restriction"  value="le" />
		</@bravo.FormPanel>
		<#---- 
		<@bravo.GridPanel  region="center"  height="370"  region="south"  name="taskInstance_grid"  dataProxy="./task!assignTasks.action?isSuspended=0&isOpen=1&pooledActors.id=%{requestHandler.currentUser.id?c}" title="已处理任务列表" contextDataName="taskInstances" collapsible="true" >  
			<@bravo.Column hidden="true" name="id" header="ID" width="176"  sortable="true"  resizable="true"/>
			<@bravo.Column name="name" header="任务名称"  width="75"  sortable="true" resizable="true"/>
			<@bravo.Column name="processInstance.processDefinition.name" header="流程名称"  width="175"  sortable="true" resizable="true"/>
			<@bravo.Column name="create" header="任务创建时间"  width="90"  sortable="true" resizable="true"/>
			<@bravo.Column name="processInstance.start" header="流程发起时间"  width="90"  sortable="true" resizable="true"/>
			<@bravo.Column name="view" header="查看"   sortable="true" >
				<a href=\'javascript:gridOpenNewWin(\\"./task!viewTask.action?id=@{rowValue.id?c}\\",\\"查看任务[@{rowValue.name}]\\",\\"taskInstance_grid\\")\' style=\\"TEXT-DECORATION:none\\"><span style=color:red;>查看</span></a>       
			</@bravo.Column>
		</@bravo.GridPanel>
		---->		
		<@bravo.GridPanel  region="center"  height="370"   name="taskInstance_grid"  stripeRows="true" dataProxy="./taskBaseDomain!findAndRendJsonData.action?taskInstance.isSuspended=0&taskInstance.isOpen=0&taskInstance.actorId=%{requestHandler.currentUser.id?c}&taskInstance.end_restriction=isNotNull&taskInstance.processInstance.end_restriction=isNull" contextDataName="taskInstances" collapsible="true" >  
			<@bravo.Column hidden="true" name="id" header="ID" width="176"  sortable="true"  resizable="true"/>
			<@bravo.Column name="workFlowBaseDomain.title" header="标题"  width="75"  sortable="true" resizable="true"/>
			<@bravo.Column name="workFlowBaseDomain.reason" header="申请原因"  width="75"  sortable="true" resizable="true"/>
			<@bravo.Column name="taskInstance.name" header="任务名称"  width="75"  sortable="true" resizable="true"/>
			<@bravo.Column name="taskInstance.processInstance.processDefinition.name" header="流程名称"  width="175"  sortable="true" resizable="true"/>
			<@bravo.Column name="taskInstance.create" header="任务创建时间"  width="90"  sortable="true" resizable="true"/>
			<@bravo.Column name="taskInstance.processInstance.start" header="流程发起时间"  width="90"  sortable="true" resizable="true"/>
			<@bravo.Column name="view" header="查看"   sortable="true" >
				<a href=\'javascript:gridOpenNewWin(\\"./task!viewTask.action?id=@{rowValue.taskInstance.id?c}\\",\\"查看任务[@{rowValue.name}]\\",\\"taskInstance_grid\\")\' style=\\"TEXT-DECORATION:none\\"><span style=color:red;>查看</span></a>       
			</@bravo.Column>
		</@bravo.GridPanel>		

	</@bravo.Viewport>
</@bravo.Page>