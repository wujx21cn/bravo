<@bravo.Page name="workflow_add" title="流程日志" cache="false">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="workflowAddForm" region="center" border="false" width="600" tbar="toolBar" dataProxy="../workflow/task!taskProcess.action">  
			<@bravo.Toolbar name="toolBar" valign="bottom">
				<@bravo.Button tooltip="'保存并新建流程'" text="保存并新建流程" iconCls="add" handler="submitAndCreateProcess(\\'workflowAddForm\\')"/>
			</@bravo.Toolbar>
				 <@bravo.TextArea fieldLabel="备注" name="comments" width="500" height="120"/>
		</@bravo.FormPanel>
    </@bravo.Viewport>
	<@bravo.Script>
	</@bravo.Script>
</@bravo.Page>
<SCRIPT LANGUAGE="JavaScript">
<!--
function submitAndCreateProcess(formPanelName){
	var formPanel = Ext.getCmp(formPanelName);
    formPanel.form.doAction('submit', {
		url : formPanel.dataProxy,
		method : 'post',
		params : {jsonFormData:'Y',workflow_definition_name:'${workflow_definition_name}',workflow_operation:'workflow_operation_create',workflow_entity_name:'${workflow_entity_name}',workflow_entity_id:'${workflow_entity_id}'},
		success : function(form, action) {
			//Ext.Msg.alert('成功','提交成功！');
			parent.workflow_win_win.close();
		},
		failure : function(form, action) {
			Ext.Msg.alert('错误', '操作错误，请重试！');
        }
    });
}
//-->
</SCRIPT>