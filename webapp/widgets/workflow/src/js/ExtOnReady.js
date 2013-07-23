/*
 *      属性窗口的框架结构
 */
document.write('<script type="text/javascript" src="../../../ext-2.2/source/locale/ext-lang-zh_CN.js"></script>');		

var generalTabPropertyGrid       = null; //图元基本属性列表
		
var tasksVariableNameTextField   = null; //建立task子节点variable的name属性
var tasksVariableValueTextField  = null; //建立task子节点variable的值
var tasksVariablePropertyGrid    = null; //记录所有的task节点信息
var tasksVariableFormPanel       = null; //放置task的variable相关属性输入框
var tabPanelProperties           = null; //放置General和Tasks标签

var tasksAssignmentExpreTextArea = null; //建立task子节点assignment中Expression的值
var tasksAssignmentActorTextArea = null; //建立task子节点assignment中Actor ID的值
var tasksAssignmentActorName     = null; //Actor ID对应的Actor Name的值
var tasksAssignmentPlActTextArea = null; //建立task子节点assignment中Pooled Actors的值
var tasksAssignmentPlActName     = null; //Pooled Actors对应的Pooled Actors Name的值
var tasksAssignmentFormPanel     = null; //放置tasks的assignment相关属性输入框

var typeComboBox_array_data      = null;
var typeComboBox_store           = null;
var tasksEventTypeComboBox       = null;
var tasksEventActionTextField    = null;
var tasksEventActionPropertyGrid = null;
var tasksEventFormPanel          = null;


var treeWin;//人员部门树弹出框         
var treeRoleWin;//人员角色

ExtOnReady = function()
{
	Ext.QuickTips.init();
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	//图元基本属性列表                                       
	generalTabPropertyGrid = new Ext.grid.PropertyGrid({
														title      : 'General',
														autoHeight : true,
														autoWidth  : true,
														source     : {},
														viewConfig : {
																		forceFit:true,
																		scrollOffset:2 // 右边拉动条的宽度
																	 }
													
													   });
	//图元基本属性更改事件响应
	generalTabPropertyGrid.on("propertychange",function( source, recordId, value, oldValue  )
											   {
												  //alert(generalTabPropertyGrid.store.getById(recordId).get('name'));
												  //alert(value);
												  //alert(e.record.get('name'));
												  //alert(e.value);
												  saveToGeneral();  
												  //alert(attrsValue[0].get('name')+':'+attrsValue[0].get('value'));
												  //self.reflashPropertyWin(propertyCell);                                             
												
												return false;
											   }
							 );
													 
//-------------------------------------------------------------------------------------------------------------------               
	//增加variable信息
	var addVariable = function()
	{
		var varName = tasksVariableNameTextField.getValue();
		var varValue = tasksVariableValueTextField.getValue();
		if( varName !== '' && varValue !== '' )
		{
			//如果该变量已经存在，就只改变其值而不必添加
			var found = false;
			var store = tasksVariablePropertyGrid.getStore();
			for(var i=0; i<store.data.length; i++)//搜索整个variable表看是否已存在该变量
			{
				var record = store.getAt(i);
				var colName = record.get("name");   
				var colValue = record.get("value"); 
				if(colName==varName)
				{
					record.set("value",varValue);
					found = true;
				}
			}
			//如果该变量不存在，就添加该变量和它的值
			if(!found)
			{
				var newVariable = new Ext.grid.PropertyRecord({name:varName,value:varValue});
				tasksVariablePropertyGrid.store.addSorted(newVariable);
			}
		}
	};
	
	//建立task子节点variable的name属性
	tasksVariableNameTextField = new Ext.form.TextField({
														  fieldLabel : 'Name',
														  value      : '',
														  hideLabels : false,
														  anchor     : '100%',
														  listeners  : {
																		"change":function(textField,newValue,oldValue )
																				 {
																					//alert(newValue);
																					addVariable();
																					saveToTasks();
																					return false;
																				 }
																		}
														 });
													   
	//建立task子节点variable的值
	tasksVariableValueTextField = new Ext.form.TextField({
														  fieldLabel : 'Value',
														  value      : '',
														  hideLabels : false,
														  anchor     : '100%',
														  listeners  : {
																		 "change":function(textField,newValue,oldValue )
																				  {
																					 //alert(newValue);
																					 addVariable();
																					 saveToTasks();
																					 return false;
																				  }
																		}
														});
														
	//记录所有的task节点信息                                       
	tasksVariablePropertyGrid = new Ext.grid.PropertyGrid({
															height     : 360,
															anchor     : '100%',
															source     : {},
															viewConfig : {
																			forceFit:true,
																			scrollOffset:2 // the grid will never have scrollbars
																		 }
														 });
	//双击删除一条variable信息                                                
	tasksVariablePropertyGrid.on("rowdblclick",function(grid,rowIndex,e)
											   {
												  Ext.MessageBox.confirm('变量删除提示', '是否确信删除变量：'+grid.store.getAt(rowIndex).get('name'), 
																		 function(btn)
																		 {
																			if(btn == 'yes') 
																			{
																				var r = grid.store.getAt(rowIndex);
																				grid.store.remove(r);
																				saveToTasks();
																			}
																		 });
												  return false;
											   }
								);
								
	//variable信息改变事件响应                                                
	tasksVariablePropertyGrid.on("propertychange",function( source, recordId, value, oldValue )
												  {
													 saveToTasks(); 
													 return false;
												  }
								);
//----------------------------------------------------
	//建立task子节点assignment中Expression的值                                                                                                   
	tasksAssignmentExpreTextArea = new Ext.form.TextArea({
														  fieldLabel : 'Expression',
														  value      : '',
														  height     : 60,
														  hideLabels : false,
														  anchor     : '100%',
														  listeners  :{
																		 "change":function(textField,newValue,oldValue )
																				  {
																					 //alert(newValue);
																					 saveToTasks();
																					 return false;
																				  }
																	  }
														});
	//建立task子节点assignment中Actor ID的值
    tasksAssignmentActorTextArea = new Ext.form.Hidden({
														 name		:'tasksAssignmentActorTextAreaID'
														,fieldLabel :'hiddenActorIDs'
														,readOnly   :true
														,value      : ''
														,anchor     : '100%'
														,hideLabels : false
														,height     : 40
														
											});
	//建立task子节点assignment中Actor Name的值
	tasksAssignmentActorName = new Ext.form.TextArea({
														  fieldLabel : 'Actor Name',
														  value      : '',
														  height     : 70,
														  hideLabels : false,
														  anchor     : '100%',
													      readOnly   : true,
													      style      : 'cursor:hand',
														  listeners  :{
																		 "change":function(textField,newValue,oldValue )
																				  {
																					 saveToTasks();
																					 return false;
																				  }
																			,
																		  "focus":function(textField)
																				  {
																				      userIDForName();
																			          humanDeptTree();
																				  }
																	  }
														});
	//建立task子节点assignment中Pooled Actors ID的值
    tasksAssignmentPlActTextArea = new Ext.form.Hidden({
														 name		:'tasksAssignmentPlActTextAreaID'
														,fieldLabel :'hiddenActorIDs2'
														,readOnly   :true
														,value      : ''
														,anchor     : '100%'
														,hideLabels : false
														,height     : 40
														
											});
	//建立task子节点assignment中Pooled Actors的值													
	tasksAssignmentPlActName = new Ext.form.TextArea({
														  fieldLabel : 'Pooled Actors',
														  value      : '',
														  height     : 70,
														  hideLabels : false,
														  anchor     : '100%',
													      readOnly   : true,
													      style      : 'cursor:hand',
														  listeners  :{
																		 "change":function(textField,newValue,oldValue )
																	             {
																					 //alert(newValue);
																					 saveToTasks();
																					 return false;
																				  }
																			,
																		  "focus":function(textField)
																				  {
																				      roleIDForName();
																			          humanRoleTree();
																				  }
																	  }
														});	
														
//----------------------------------------------------
	//增加Action信息
	var addAction = function()
	{
		var varName = tasksEventActionTextField.getValue();
		if( varName !== '' )
		{
			var found = false;
			var store = tasksEventActionPropertyGrid.getStore();
			//搜索整个Action表看是否已存在该Action											
			for(var i=0; i<store.data.length; i++)
			{
				var record = store.getAt(i);
				var colName = record.get("name");    
				if(colName==varName)
				{
					found = true;
					break;									
				}
			}
			//如果该Action不存在，就添加该Action
			if(!found)
			{
				var newAction = new Ext.grid.PropertyRecord({name:varName,value:''});
				tasksEventActionPropertyGrid.store.addSorted(newAction);
			}
		}
	};
														
	typeComboBox_array_data = [[''],['before-signal'],['after-signal'],['node-enter'],['node-leave']];
	
	
	typeComboBox_store = new Ext.data.SimpleStore({
												       fields: ["type"],
												       data:  typeComboBox_array_data
											     });
														
	
	//建立task子节点Event的type属性
	tasksEventTypeComboBox = new Ext.form.ComboBox({
														  store: typeComboBox_store,
														  emptyText:'选择',
														  displayField:'type',
														  editable:false,
														  mode:'local',
														  valueField:'type',
														  forceSelection:true,
														  triggerAction:'all',
														  fieldLabel : 'Type',
														  value      : '',
														  hideLabels : false,
														  anchor     : '100%',
														  listeners  : {
																		"change":function(textField,newValue,oldValue )
																				 {
																					//alert(newValue);
																					addAction();
																					saveToTasks();
																					return false;
																				 }
																		}
													});
													   
	//建立task子节点Action的Name值
	tasksEventActionTextField = new Ext.form.TextField({
														  fieldLabel : 'Action',
														  value      : '',
														  hideLabels : false,
														  anchor     : '100%',
														  listeners  : {
																		 "change":function(textField,newValue,oldValue )
																				  {
																					 //alert(newValue);
																					 addAction();
																					 saveToTasks();
																					 return false;
																				  }
																		}
														});
														
	//记录所有的Action节点信息                                       
	tasksEventActionPropertyGrid = new Ext.grid.PropertyGrid({
															height     : 360,
															anchor     : '100%',
															source     : {},
															viewConfig : {
																			forceFit:true,
																			scrollOffset:2 // the grid will never have scrollbars
																		 }
														 });
	//双击删除一条Action信息                                                
	tasksEventActionPropertyGrid.on("rowdblclick",function(grid,rowIndex,e)
											   {
												  Ext.MessageBox.confirm('变量删除提示', '是否确信删除变量：'+grid.store.getAt(rowIndex).get('name'), 
																		 function(btn)
																		 {
																			if(btn == 'yes') 
																			{
																				var r = grid.store.getAt(rowIndex);
																				grid.store.remove(r);
																				saveToTasks();
																			}
																		 });
												  return false;
											   }
								);
								
	//Event信息改变事件响应                                                
	tasksEventActionPropertyGrid.on("propertychange",function( source, recordId, value, oldValue )
												  {
													 saveToTasks(); 
													 return false;
												  }
								);		
								
//----------------------------------------------------								
								
														
	////输入Task节点Variable属性的标签页
	tasksVariableFormPanel = new Ext.form.FormPanel({
														labelWidth : 55,
												        frame:true,
												        title: 'variable',
												        bodyStyle:'padding:6px 2px 0',
												        width: 350,
												        defaults: {width: 230},
												        defaultType: 'textfield',
												        items: [
																	tasksVariableNameTextField,
																	tasksVariableValueTextField,
																	tasksVariablePropertyGrid
															   ]

												   });
												   
	//输入Task节点Assignment属性的标签页											   
	tasksAssignmentFormPanel = new Ext.form.FormPanel({
														labelWidth : 100,
												        frame:true,
												        title: 'assignment',
												        bodyStyle:'padding:6px 2px 0',
												        width: 350,
												        defaults: {width: 230},
												        defaultType: 'textfield',
												        labelAlign: 'top',
												        items: [
																	tasksAssignmentActorName,
																	tasksAssignmentActorTextArea,
															     	tasksAssignmentPlActName,
																	tasksAssignmentPlActTextArea,
																	tasksAssignmentExpreTextArea
															   ]

												     });
	/*输入Task节点Event属性的标签页*/											     
	tasksEventFormPanel = new Ext.form.FormPanel({
														labelWidth : 55,
												        frame:true,
												        title: 'Event',
												        bodyStyle:'padding:6px 2px 0',
												        width: 350,
												        defaults: {width: 230},
												        defaultType: 'textfield',
												        items: [
																	tasksEventTypeComboBox,
																	tasksEventActionTextField,
																	tasksEventActionPropertyGrid
															   ]

												   });
                                                
//-------------------------------------------------------------------------------------------------------------------  													    
	//放置General和Tasks标签                                        
	tabPanelProperties = new Ext.TabPanel({
										   activeTab  : 0,
										   anchor     : '100%',
										   border     : false,
										   enableTabScroll:true,
										   tabPosition: 'top',
										   items      : [
														 generalTabPropertyGrid,
														 tasksVariableFormPanel,
														 tasksAssignmentFormPanel,
														 tasksEventFormPanel
														]
										 });
	//属性框的窗体
	propertiesWin = new Ext.Window({ 
									title       : 'Properties',
									maximizable : true,
									autoScroll  : true,
									expandOnShow: false,
									layout      : 'fit',
									closeAction : 'hide',
									plain       : true,
									resizable   : true,
									width       : 240,
							        height      : 500,
									items       : tabPanelProperties,
									listeners  :{

												   "move":function(component,x,y)//当主窗口移动的时候，弹出树的窗口也跟着移动定位
												   {
													 if(treeWin!=undefined){
                                                        treeWin.setPosition(tasksAssignmentActorName.getPosition()[0],
															                tasksAssignmentActorName.getPosition()[1]+70);
														treeWin.toFront();//前端显示
													 }
													 if(treeRoleWin!=undefined){
                                                        treeRoleWin.setPosition(tasksAssignmentPlActName.getPosition()[0],
															                    tasksAssignmentPlActName.getPosition()[1]+70);
														treeRoleWin.toFront();//前端显示
													 }
												   },
													"hide":function(p)//当主窗口关闭的时候，弹出树窗口也关闭
												   {
													 if(treeWin!=undefined){
														treeWin.close();
													 }
													 if(treeRoleWin!=undefined){
														treeRoleWin.close();
													 }
												   }
									}
								  });
								  
};

//------------------------------------------------------------人员部门树
function humanDeptTree(){
//------------------------------------------------------------humanDeptTree_Start
  var humanDeptTree_root = new Ext.tree.AsyncTreeNode({    
    text:'部门人员',   
    expanded:true,
    id:'dept0' 
    });

	var humanDeptTree = new Ext.tree.TreePanel({
		id:'humanDeptTree' 
		,autoScroll:true 
		,xtype:'TreePanel' 
		,region:'center'
		,animate:false
		,rootVisible:true
	    ,root:humanDeptTree_root
		,name:'humanDeptTree'
		,checkModel:'cascade'
		,onlyLeafCheckable:true
        ,loader: new Ext.tree.TreeLoader({
			 dataUrl:'../../../workflow/task!userDeptCheckedTree.action'
			,baseParams: {contextDataName:'treeData',jsonCheckedTreeData:'Y',IDString:tasksAssignmentActorTextArea.getValue()} 
			,baseAttrs: {uiProvider: Ext.tree.TreeCheckNodeUI}
			})
    });
//------------------------------------------------------------humanDeptTree_End

/**
 * humanDeptTree注册响应事件
 */
	humanDeptTree.on('click', function(node,option){//响应树节点click事件
	//   humanDeptTree.fireEvent('check',node,'true');
	});

	humanDeptTree.on("check",function(node,checked)//相应树节点check事件
	{
		var oldIDString = tasksAssignmentActorTextArea.getValue();//获得修改前的actorID串
		var IDArray = oldIDString.split(",");
		var newIDString = "";
		var userID = (node.id).substring(4);//为了使部门和人员的ID不重复，在初始树的时候在节点id前添加标识字符串，例如id=1的部门节点:dept1 id=1的人员节点:user1

		var oldNameString = tasksAssignmentActorName.getValue();
		var nameArray = oldNameString.split(",");
		var newNameString = "";


		if(checked){//要添加actor
			var isExist = false;
			for(var i=0;i<IDArray.length;i++){
			  if(IDArray[i]==userID){
				isExist = true;
				break;
			  }
			}
			if(isExist==false){//要添加的actor不存在
			   if(IDArray[0]!=""){//不是添加的第一个，要以逗号隔开
				  newIDString = oldIDString + ',' + userID;
			      newNameString = oldNameString + ',' + node.text;
		       }else{
			      newIDString = userID;
			      newNameString = node.text;
		       }
			}else{
			   newIDString = oldIDString;
			   newNameString = oldNameString;
			}
		}else{//取消勾选的actor
			for(var i=0;i<IDArray.length;i++){
			  if(IDArray[i]==userID){
				continue;
			  }
			  if(newIDString==""){
				newIDString = IDArray[i];
				newNameString = nameArray[i];
			  }else{
				newIDString += (','+IDArray[i]);
				newNameString += (','+nameArray[i]);
			  }
			}
		}

		tasksAssignmentActorTextArea.setValue(newIDString);
		tasksAssignmentActorName.setValue(newNameString);
		saveToTasks();
   });

  if(treeWin!=undefined){
		treeWin.close();
	}
  if(treeRoleWin!=undefined){
		treeRoleWin.close();
	}
	 treeWin = new Ext.Window({ 
	                              //id          :'onlyYou',
									title       : 'ActorTree',
									x			:tasksAssignmentActorName.getPosition()[0],
									y			:tasksAssignmentActorName.getPosition()[1]+70,
		 							width       :190,
									height		:200,
									maximizable : false,
									autoScroll  : true,
									closeAction : 'hide',
									collapsible : false,
									plain       : true,
									resizable	:true,
								    items       :[humanDeptTree]
								  });        

      treeWin.show();

}

/**
 * 处理在完成该弹出树功能前，只有ID，而没有对应的name的情况
 */
function userIDForName(){

	var oldInitIDString = tasksAssignmentActorTextArea.getValue();//获得修改前的actorID串
	var oldInitNameString = tasksAssignmentActorName.getValue();
	var oldInitIDArray = oldInitIDString.split(",");
	var oldInitNameArray = oldInitNameString.split(",");
 if((oldInitIDString!="" && oldInitNameString=="")||
	(oldInitIDArray[0]!="" && oldInitNameArray[0]=="")||
	(oldInitIDArray[0]=="" && oldInitIDArray[1]!= undefined && oldInitIDArray[1]!="")){//只有ID，而没有对应的name的情况
	Ext.Ajax.request
	 ({
		method: 'POST',
		url: '../../../workflow/task!userIDForName.action',
        params:{oldIDs:oldInitIDString},
		success: function(response,options) {
			var obj= Ext.decode(response.responseText);
			oldInitNameString = obj.oldNames;
			oldInitIDString = obj.oldIDs;
			tasksAssignmentActorName.setValue(oldInitNameString);
			tasksAssignmentActorTextArea.setValue(oldInitIDString);
                }
	 });
  }
}

//------------------------------------------------------------角色树
function humanRoleTree(){
//------------------------------------------------------------humanRoleTree_Start
  var humanRoleTree_root = new Ext.tree.AsyncTreeNode({    
    text:'人员角色',   
    expanded:true,
    id:'0' 
    });

	var humanRoleTree = new Ext.tree.TreePanel({
		id:'humanRoleTree' 
		,autoScroll:true 
		,xtype:'TreePanel' 
		,region:'center'
		,animate:false
		,rootVisible:true
	    ,root:humanRoleTree_root
		,name:'humanRoleTree'
		,checkModel:'cascade'
		,onlyLeafCheckable:true
        ,loader: new Ext.tree.TreeLoader({
			 dataUrl:'../../../workflow/task!userRoleCheckedTree.action'
			,baseParams: {contextDataName:'treeData',jsonCheckedTreeData:'Y',IDString:tasksAssignmentPlActTextArea.getValue()} 
			,baseAttrs: {uiProvider: Ext.tree.TreeCheckNodeUI}
			})
    });
//------------------------------------------------------------humanRoleTree_End

/**
 * humanDeptTree注册响应事件
 */
	humanRoleTree.on('click', function(node,option){//响应树节点click事件
	//   humanRoleTree.fireEvent('check',node,'true');
	});

	humanRoleTree.on("check",function(node,checked)//相应树节点check事件
	{
		var oldIDString = tasksAssignmentPlActTextArea.getValue();//获得修改前的pooled actor ID串
		var IDArray = oldIDString.split(",");
		var newIDString = "";

		var oldNameString = tasksAssignmentPlActName.getValue();
		var nameArray = oldNameString.split(",");
		var newNameString = "";


		if(checked){//要添加pooled actor
			var isExist = false;
			for(var i=0;i<IDArray.length;i++){
			  if(IDArray[i]==node.id){
				isExist = true;
				break;
			  }
			}
			if(isExist==false){//要添加的pooled actor不存在
			   if(IDArray[0]!=""){//不是添加的第一个，要以逗号隔开
				  newIDString = oldIDString + ',' + node.id;
			      newNameString = oldNameString + ',' + node.text;
		       }else{
			      newIDString = node.id;
			      newNameString = node.text;
		       }
			}else{
			   newIDString = oldIDString;
			   newNameString = oldNameString;
			}
		}else{//取消勾选的pooled actor
			for(var i=0;i<IDArray.length;i++){
			  if(IDArray[i]==node.id){
				continue;
			  }
			  if(newIDString==""){
				newIDString = IDArray[i];
				newNameString = nameArray[i];
			  }else{
				newIDString += (','+IDArray[i]);
				newNameString += (','+nameArray[i]);
			  }
			}
		}

		tasksAssignmentPlActTextArea.setValue(newIDString);
		tasksAssignmentPlActName.setValue(newNameString);
		saveToTasks();
   });

  if(treeWin!=undefined){
		treeWin.close();
	}
  if(treeRoleWin!=undefined){
		treeRoleWin.close();
	}
	 treeRoleWin = new Ext.Window({ 
	                              //id          :'onlyYou2',
									title       :'RoleTree',
									x			:tasksAssignmentPlActName.getPosition()[0],
									y			:tasksAssignmentPlActName.getPosition()[1]+70,
		 							width       :190,
									height		:200,
									maximizable : false,
									autoScroll  : true,
									closeAction : 'hide',
									collapsible : false,
									plain       : true,
									resizable	: true,
								    items       :[humanRoleTree]
								  });        

      treeRoleWin.show();

}

/**
 * 处理在完成该弹出树功能前，只有ID，而没有对应的name的情况
 */
function roleIDForName(){

	var oldInitIDString = tasksAssignmentPlActTextArea.getValue();//获得修改前的pooledID串
	var oldInitNameString = tasksAssignmentPlActName.getValue();
	var oldInitIDArray = oldInitIDString.split(",");
	var oldInitNameArray = oldInitNameString.split(",");
 if((oldInitIDString!="" && oldInitNameString=="")||
	(oldInitIDArray[0]!="" && oldInitNameArray[0]=="")||
	(oldInitIDArray[0]=="" && oldInitIDArray[1]!= undefined && oldInitIDArray[1]!="")){//只有ID，而没有对应的name的情况
	Ext.Ajax.request
	 ({
		method: 'POST',
		url: '../../../workflow/task!roleIDForName.action',
        params:{oldIDs:oldInitIDString},
		success: function(response,options) {
			var obj= Ext.decode(response.responseText);
			oldInitNameString = obj.oldNames;
			oldInitIDString = obj.oldIDs;
			tasksAssignmentPlActName.setValue(oldInitNameString);
			tasksAssignmentPlActTextArea.setValue(oldInitIDString);
                }
	 });
  }
}