<@bravo.Page name="workflow_add" title="流程日志" cache="false">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="workflowAddForm" region="center" border="false" width="800" tbar="toolBar"  dataProxy="./requestForLeaveList!save.action">  
			<@bravo.Toolbar name="toolBar" valign="bottom">
				<@bravo.Button tooltip="'保存并新建流程'" text="保存并新建流程" iconCls="add" handler="submitAndCreateProcess(\\'workflowAddForm\\')"/>
			</@bravo.Toolbar>
            <@bravo.FieldSet border=true cols="1;1" rows="1;1" >
				<@bravo.TextField fieldLabel="申请人" name="proposer.userName" readOnly="true" value="%{requestHandler.currentUser.userName}" width="165"/>
				 <@bravo.HtmlEditor fieldLabel="申请理由" name="reason" height="350" width = "650" /> 
			</@bravo.FieldSet>
		</@bravo.FormPanel>
    </@bravo.Viewport>
	<@bravo.Script>

	</@bravo.Script>
</@bravo.Page>
<SCRIPT LANGUAGE="JavaScript">
<!--
function submitAndCreateProcess(){
	var formPanel = parent.workflowTable_win;
	alert(formPanel);
    formPanel.form.doAction('submit', {
		url : formPanel.dataProxy,
		method : 'post',
		params : {jsonFormData:'Y'},
		success : function(form, action) {
			Ext.Msg.alert('成功', action.result.msg);
		},
		failure : function(form, action) {
			Ext.Msg.alert('错误', '操作错误，请重试！');
        }
    });
}
//-->
</SCRIPT>