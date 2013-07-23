<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">       
    <title>流程日志</title>
    <script type="text/javascript" src="${bravoHome}/ui/dynamicJs!CreateDynamicJs.action"></script>   
    <LINK href="${bravoHome}/${extjsWidgetPath}/resources/css/ext-all.css" type="text/css" rel="stylesheet">    
    <LINK href="" type="text/css" rel="stylesheet">    
    <script type="text/javascript" src="${bravoHome}/${extjsWidgetPath}/adapter/ext/ext-base.js"></script> 
    <script type="text/javascript" src="${bravoHome}/${extjsWidgetPath}/ext-all.js"></script>      
    <script type="text/javascript" src="${bravoHome}/${extjsWidgetPath}/source/locale/ext-lang-zh_CN.js"></script>   

    <script type="text/javascript" src="${bravoHome}/${extjsWidgetPath}/ux/extjs-bravo-ux.js"></script>
    <link rel="stylesheet" type="text/css" href="${bravoHome}/${extjsWidgetPath}/extjs-bravo.css" /> 
    <script type="text/javascript" src="${bravoHome}/${extjsWidgetPath}/extjs-bravo.js"></script>  
    <link rel="stylesheet" type="text/css" href="${bravoHome}/${extjsWidgetPath}/ux/extjs-bravo-ux.css"/>
</head>    
    <body> 
<div id="workflowAddForm_div">
<div id="toolBar_div">
</div>
</div>
<SCRIPT LANGUAGE="JavaScript">
<!--
var workflowAddForm_win = null;
//-->
</SCRIPT>
    </body>
</html>
<script language="javascript">
Ext.onReady(
  function(){
    Ext.QuickTips.init();
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
var Button14451390 = new Ext.Button({
	id:'Button14451390' 
	,handler:menuHandlerEval
	,handlerExpress:'submitAndCreateProcess(\'workflowAddForm\')'
	,iconCls:'add' 
	,text:'完成任务' 
	,tooltip:'任务任务' 
	});
    

var toolBar = new Ext.Toolbar({
		id:'toolBar' 
		,items:[Button14451390]
});

<#if workflow_aviable_transition_name?has_content> 
	<#list workflow_aviable_transition_name as transitionItem>
    var transactionRadio_${transitionItem_index} = new Ext.form.Radio({
	name:'transitionName'
    ,fieldLabel:'${transitionItem.name?default("")}'
	,value:'${transitionItem.name?default("")}'
	,boxLabel:'${transitionItem.name?default("")}'
	,checked:<#if transitionItem_index == 0>true<#else>false</#if>
	,inputValue:'${transitionItem.name?default("")}'
	, hiddenName:'book' 
    });
	</#list>
</#if>

    var RadioGroup111= new Ext.form.RadioGroup({
	name:'RadioGroup111'
	,fieldLabel:'选择路径'
	,value:''
	,items:[
		<#if workflow_aviable_transition_name?has_content> 
			<#list workflow_aviable_transition_name as transitionItem>
				<#if transitionItem_index != 0>,</#if>transactionRadio_${transitionItem_index}
			</#list>
		</#if>]
    });

    var comments = new Ext.form.TextArea({
	name:'comments'
	,fieldLabel:'备注'
	,value:''
	,width:500
	,height:120

	

    });
	var workflowAddForm = new Ext.FormPanel({
		id:'workflowAddForm' 
		,region:'center'
		,width:600 
		,autoScroll:true 
		,border:false
		
		,contentEl:'workflowAddForm_div'
		,frame:true
		,tbar:toolBar
		,labelAlign:'left'
		,labelWidth:55
		,url:'../workflow/task!taskProcess.action'
		    ,items:[RadioGroup111,comments]

});

workflowAddForm_win = workflowAddForm;	
Viewport14191879 = new Ext.Viewport({
    id:'Viewport14191879'
		,items:[workflowAddForm]
	,layout:'border' 
	});


	
});
</script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function submitAndCreateProcess(formPanelName){
	var formPanel = Ext.getCmp(formPanelName);
    formPanel.form.doAction('submit', {
		url : formPanel.dataProxy,
		method : 'post',
		params : {jsonFormData:'Y',workflow_definition_name:'${workflow_definition_name}',workflow_operation:'workflow_operation_finish',workflow_entity_name:'${workflow_entity_name}',workflow_entity_id:'${workflow_entity_id}',workflow_task_id:'${workflow_task_id}'},
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