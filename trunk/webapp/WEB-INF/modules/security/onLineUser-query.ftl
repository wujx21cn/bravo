<@bravo.Page name="OnLineUser_query" title="OnLineUser_query">
	<@bravo.Viewport  layout="border">
		<@bravo.GridPanel  region="center" tbar="gridToolBar" split="true" height="565" name="OnLineUser_grid"  contextDataName="OnLineUsers" dataProxy="./onLineUser!findAndRendJsonData.action" title="当前在线用户" collapsible="true" >
			<@bravo.Toolbar name="gridToolBar" valign="top" ></@bravo.Toolbar>   
			<@bravo.Column hidden="true" name="id" header="ID" width="0"  sortable="true"  resizable="true" />
			<@bravo.Column dataIndex="chnName" name="chnName" width="80" header="登录人" sortable="true" resizable="true" />     
			<@bravo.Column dataIndex="loginId" name="loginId" width="80" header="工号" sortable="true" resizable="true" />     
			<@bravo.Column dataIndex="department" name="department" width="100" header="所在部门" sortable="true" resizable="true" />     
			<@bravo.Column dataIndex="loginIp" name="loginIp" width="100" header="登录IP" sortable="true" resizable="true" />     
			<@bravo.Column dataIndex="loginTime" name="loginTime" width="120" header="登录时间" sortable="true" resizable="true"/>          	   
		</@bravo.GridPanel>
	</@bravo.Viewport>
</@bravo.Page>