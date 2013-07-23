<@bravo.Page name="UserLoginLog_query" title="UserLoginLog_query">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="UserLoginLog_query" tbar="formToolBar" region="north" frame="true" autoScroll="true" height="100" width="780" autoScroll="true"  title="用户登陆日志查询列表"  collapsible="true" > 
			<@bravo.Toolbar name="formToolBar" valign="top">
				<@bravo.Button name="View" text="查找" iconCls="search" handler="searchForGrid(\\'UserLoginLog_query\\',\\'UserLoginLog_grid\\')"/>
				<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'UserLoginLog_query\\')"/>
			</@bravo.Toolbar>
			<@bravo.FieldSet labelWidth="60" cols="1,1;" rows="1,1;" >
					  <@bravo.TextField fieldLabel="登录人" name="chnName" width="160" />
				      <@bravo.TextField fieldLabel="工号" name="loginId" width="160" />
			</@bravo.FieldSet>
		</@bravo.FormPanel>   

		<@bravo.GridPanel  region="center" tbar="gridToolBar" split="true" stripeRows="true"  name="UserLoginLog_grid"  contextDataName="UserLoginLogs" dataProxy="./userLoginLog!findAndRendJsonData.action"  collapsible="true" >
			<@bravo.Toolbar name="gridToolBar" valign="top" >
			</@bravo.Toolbar>   
			     <@bravo.Column hidden="true" name="id" header="ID" width="0"  sortable="true"  resizable="true" />
			     <@bravo.Column dataIndex="chnName" name="chnName" width="80" header="登录人" sortable="true" resizable="true" />     
			     <@bravo.Column dataIndex="loginId" name="loginId" width="80" header="工号" sortable="true" resizable="true" />     
			     <@bravo.Column dataIndex="department" name="department" width="100" header="所在部门" sortable="true" resizable="true" />     
			     <@bravo.Column dataIndex="loginIp" name="loginIp" width="100" header="登录IP" sortable="true" resizable="true" />     
			     <@bravo.Column dataIndex="loginTime" name="loginTime" width="120" header="登录时间" sortable="true" resizable="true"/>     
			     <@bravo.Column dataIndex="logoutTime" name="logoutTime" width="120" header="退出时间" sortable="true" resizable="true"/>      	   
		</@bravo.GridPanel>
	</@bravo.Viewport>
</@bravo.Page>

