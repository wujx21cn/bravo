<@bravo.Page name="requestForLeaveList_add" title="新增请假条" cache="false">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="requestForLeaveListAddForm" region="center" border="false" width="800" tbar="toolBar"  dataProxy="./requestForLeaveList!save.action">  
			<@bravo.Toolbar name="toolBar" valign="bottom">
				<@bravo.Button name="Save" tooltip="'保存'" text="保存" iconCls="add" handler="submitForm(\\'requestForLeaveListAddForm\\')"/>
			</@bravo.Toolbar>
            <@bravo.FieldSet border=true cols="1;1;1" rows="1;1;1" >
				<@bravo.TextField fieldLabel="标题" name="title" allowBlank="false" width="647"/>
				<@bravo.TextField fieldLabel="申请人" name="proposer_userName" readOnly="true" value="%{requestHandler.currentUser.userName}" width="165"/>
				 <@bravo.HtmlEditor fieldLabel="申请理由" name="reason" height="350" width = "650" /> 
			</@bravo.FieldSet>
		    <@bravo.Hidden name="proposer.id"  readOnly="true" width="165" value="%{requestHandler.currentUser.id?trim}"  />
		</@bravo.FormPanel>
    </@bravo.Viewport>
</@bravo.Page>

<SCRIPT LANGUAGE="JavaScript">
<!--
function ajaxWorkFlowSubmitForm(formName,processdefinitionName) {    
    var formPanel = Ext.getCmp(formName); 
	if(formPanel.form.isValid()){
              formPanel.form.doAction('submit', {
                   url : formPanel.dataProxy,
                   method : 'post',
		           params : {jsonFormData:'Y',
							workflow_operation:'workflow_operation_create',
							workflow_definitionName:processdefinitionName,
							workflow_operation:'workflow_operation_create'},
			  success : function(form, action) {
		              Ext.Msg.alert('成功', action.result.msg);
//                    this.disabled = false;
		      },
		      failure : function(form, action) {
		              Ext.Msg.alert('错误', '操作错误，请重试！');
//		              this.disabled = false;
                        }
                    });
//                 this.disabled = false;
      }else{
    	Ext.Msg.alert('提示', '红色波浪线标识为必填信息，请完成');
	}
}	

//-->
</SCRIPT>
<%if 1==2>sddsf</%if>