<@bravo.Page name="requestForLeaveListView" title="查看请假条" cache="false">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="requestForLeaveListForm" region="center" border="false" height="180" width="790" collapsible="false" tbar="toolBar" dataProxy="./requestForLeaveList!saveAndRendJsonData.action?id=%{formValue.id?c}">  
			<@bravo.Toolbar name="toolBar" valign="bottom">
            <@bravo.WorkflowButton name="workflowButton" entityName="RequestForLeave" entityId="%{formValue.id?c}" workflowName="请假示例流程" formName="requestForLeaveListForm"/>
			<@bravo.Button name="Save" tooltip="'保存'" text="保存" iconCls="add" handler="ajaxSubmitForm(\\'requestForLeaveListForm\\')"/>
			</@bravo.Toolbar>
			  <@bravo.FieldSet border=true cols="1;1;1" rows="1;1;1" >
				<@bravo.TextField fieldLabel="标题" name="title" allowBlank="false" width="650"/>
				<@bravo.TextField fieldLabel="申请人" name="proposer.userName" readOnly="true" value="%{formValue.proposer.userName}" width="165"/>
				 <@bravo.HtmlEditor fieldLabel="申请理由" name="reason" height="350" width = "650" /> 


			</@bravo.FieldSet>
				 <@bravo.Hidden fieldLabel="发布人" name="publisher.id"  readOnly="true" value="%{requestHandler.currentUser.id?c}" />
		</@bravo.FormPanel>
	</@bravo.Viewport>
</@bravo.Page>