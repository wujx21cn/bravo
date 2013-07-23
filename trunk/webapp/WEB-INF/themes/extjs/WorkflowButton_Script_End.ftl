var ${component.name} = new Ext.Toolbar.MenuButton({
id:'${component.name}' 
		,menu:{},  
		id:'workflowButton' 
		,iconCls:'workflow'
		,text:'流程管理'
});

function generWorkflowButton(buttonID,entityName,entityId,workflowName){
	var taskProxy = bravoHome+'/workflow/task!generWorkflowButton.action';
	Ext.Ajax.request({            
	   method : 'POST',      
	   url: taskProxy,
	   success: function(o) {generWorkflowButton_onSaveSuccess(o)},
	   failure: function(form, action) {generWorkflowButton_onSaveFailure(form, action)},
	   params: { workflow_entity_name:entityName,workflow_entity_id:entityId,workflow_definition_name:workflowName,workflow_button_id:'${component.name}'}
	});
}

generWorkflowButton('${component.name}','${component.entityName}','${component.entityId}','${component.workflowName}');
function generWorkflowButton_onSaveSuccess(o){
	eval(o.responseText);
}
function generWorkflowButton_onSaveFailure(o){
}

${component.name}_win = ${component.name};
function openWorkflowLogWin(entityName,entityId,workflowName,workflowOperation,taskId) {
    var formPanel = Ext.getCmp('${component.formName}'); 
	if(!formPanel.form.isValid()){
		Ext.Msg.alert('提示', '红色波浪线标识为检验错误字段，请确认！');
		return ;
	}
	var url = bravoHome+'/workflow/task!admin.action';
	url = url + "?" +"workflow_entity_name="+entityName;
	url = url + "&workflow_definition_name=" + workflowName;//对中文进行编码，encodeURI()返回一个对URI字符串编码后的结果。
	url = url + "&workflow_entity_id=" + entityId;
	url = url + "&workflow_operation="+workflowOperation;
	if ("workflow_operation_create" == workflowOperation){
		winTitle="新建流程";
	} else if ("workflow_operation_accept" == workflowOperation){
		url = url + "&workflow_task_id="+taskId;
		winTitle="接受任务";
	} else if ("workflow_operation_finish" == workflowOperation){
		url = url + "&workflow_task_id="+taskId;
		winTitle="完成任务";
	}

	
	//将弹出页面的地址加上需要传递的参数.
	var html = "<iframe id='workflowLogWinFrame' name='workflowLogWinFrame' src='"+url+"' width='100%' height='100%'></iframe>";
	var tabs = new Ext.Panel({
			region: 'center',
			margins:'3 3 3 0', 
			defaults:{autoScroll:true},
			html: html
		});
	var workflowLogWin = new Ext.Window({id:'workflowLogWin',width:600,height:400,title:winTitle,layout: 'border',modal: true,items: [tabs]});
	
	workflowLogWin.on('close', function(){
		${component.name}.menu.removeAll();
		generWorkflowButton('${component.name}','${component.entityName}','${component.entityId}','${component.workflowName}');
	});	
	workflow_win_win = workflowLogWin;
	workflowTable_win = ${component.formName};
	workflowLogWin.show();
}