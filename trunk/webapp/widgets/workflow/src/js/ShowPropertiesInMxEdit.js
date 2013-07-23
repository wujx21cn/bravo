/*-- Yeon transplant the showProperties method to the mxGraph object --*/   
        mxGraph.prototype.propertiesResource = (mxClient.language != 'none') ? 'properties' : '';
	    mxGraph.prototype.propertiesWidth = 200;
		mxGraph.prototype.propertiesHeight = null;
        
        var propertyIdValue = null;
        var propertyTopValue = null;
        var propertyLeftValue = null;
        var propertyWidthValue = null;
        var propertyHeightValue = null;
        var propertyAttrsValue = null;
        var propertyCell = null;
        var propertyGeo = null;
        var propertyGraph = null;
        var propertyModel = null;
		
		var taskNodeCount = 1;
		var stateNodeCount= 1;
		var forkCount = 1;
		var joinCount = 1;
		
		var taskVariables = null;
		var eventActions = null
		
		/*根据提供的节点类型和节点命名，判断是否重命名*/
		var isRenamed = function( nodeType, nodeName )
		{
			var cellArray = new Array();
			cellArray = propertyGraph.getChildCells(null, true, true);
			for (var i = 0; i < cellArray.length; i++)
			{
				if( nodeType==cellArray[i].getValue().nodeName && nodeName==cellArray[i].getAttribute('label') )
				{
					return true;
				}
			}
			return false;
		}
		
		/**
		 * 给节点名自动添加ID号
		 */
		var addIdWhitLabel = function(nodeName,intName,count)
		{
			var value = propertyModel.getValue(propertyCell);
			if( value.nodeName == nodeName && value.getAttribute('label')==intName)
          	{				
				/*自动增序命名时，还要确保是否有重名情况发生*/
				while( isRenamed( nodeName, nodeName+count ) )
				{
					if(nodeName=='Task_Node') {taskNodeCount++; count=taskNodeCount}
					if(nodeName=='Join')      {joinCount++; count=joinCount}
					if(nodeName=='Fork')      {forkCount++; count=forkCount}
					if(nodeName=='State')     {stateNodeCount++; count=stateNodeCount}
				}
				
				propertyModel.beginUpdate();
				var edit = new mxCellAttributeChange(propertyCell, 'label', nodeName+count);            	   
            	propertyModel.execute(edit);
				propertyModel.endUpdate();               
          	}			
		}

        /**
         * 将属性框中General标签里的信息保存到对应的图元的描述变量中去
         */
        var saveToGeneral = function()
        {
             // Supports undo for the changes on the underlying
             // XML structure / XML node attribute changes.
             propertyModel.beginUpdate();
             try
             {
            	if (propertyGeo != null)
            	{
            	   propertyGeo = propertyGeo.clone();    
            	   propertyGeo.y = parseFloat(propertyTopValue.get('value'));
            	   propertyGeo.x = parseFloat(propertyLeftValue.get('value'));
            	   propertyGeo.width = parseFloat(propertyWidthValue.get('value'));
            	   propertyGeo.height = parseFloat(propertyHeightValue.get('value'));   
            	   propertyModel.setGeometry(propertyCell, propertyGeo);
            	}    
                /**
            	 * Creates an undoable change for each attribute and executes it using the
            	 * model, which will also make the change part of the current transaction
            	 */
            	for (var i = 0; i < propertyAttrsValue.length; i ++ )
            	{
				   var nameCol = (propertyAttrsValue[i].get('name') == 'name')?'label':propertyAttrsValue[i].get('name');//把label属性名存储为name属性名
				   var valueCol = propertyAttrsValue[i].get('value');
				   
				   /*根据节点类型，判断是否与该类型图元有重命名的情况，有则恢复之前的命名*/
				   if( 'label'==nameCol && valueCol!=propertyCell.getAttribute('label') && isRenamed( propertyCell.getValue().nodeName, valueCol ) )
				   {
				   		Ext.MessageBox.alert('消息',valueCol+' 与现有'+propertyCell.getValue().nodeName+'节点命名重复,请用其他命名！');
				   		valueCol = propertyCell.getAttribute('label');
				   		propertyAttrsValue[i].set('value',valueCol);
				   }
				   
				   var edit = new mxCellAttributeChange(propertyCell, nameCol, valueCol);            	   
            	   propertyModel.execute(edit);
            	}
				
				//由于Task_Node节点的名字可能更改，为了确保一致，这里添加对task的刷新
				var value = propertyModel.getValue(propertyCell);
				if( value.nodeName == 'Task_Node' )
              	{
					saveToTasks();                
              	}
                
				/**    
            	 * Checks if the graph wants cells to
            	 * be automatically sized and updates
            	 * the size as an undoable step if
            	 * the feature is enabled
				 */
            	if (propertyGraph.isUpdateSize(propertyCell))
            	{
            	   propertyGraph.updateSize(propertyCell);
            	}
             }
             finally
             {
            	propertyModel.endUpdate();
             }  
        };
		
		/**
		 * 将属性框中Tasks标签里的信息保存到Task Node图元的task属性中去
		 */
        var saveToTasks = function()
        {             
			var taskXmlDocument = mxUtils.createXmlDocument();
			var taskElement = taskXmlDocument.createElement('task');
			var value = propertyModel.getValue(propertyCell);
			taskElement.setAttribute('name',value.getAttribute('label')); //task的名称与Task_Node的名称相同
			
			//通过读取variable标签里头的PropertyGrid内容来添加variable节点                 
			var variableElement = null;
			var variableText = null;
			var store = tasksVariablePropertyGrid.getStore();
			for (var i = 0; i < store.data.length; i++) 
			{ 
				var record = store.getAt(i);          //读一条记录
				var colName = record.get("name");     //读取该记录中的name的值，用作Variable的名称
				var colValue = record.get("value");   //读取该记录中的value的值，用作Variable的值
				
				//设置第i个variable节点的name属性 
				variableElement = taskXmlDocument.createElement('variable');
				variableElement.setAttribute('name',colName);
				//设置第i个variable节点的值
				variableText = taskXmlDocument.createTextNode(colValue);
				variableElement.appendChild(variableText);
				//将该variable节点加入到task节点上去
				taskElement.appendChild(variableElement); 
			}
			
			//通过读取assignment标签里头的信息来添加assignment节点
			if(tasksAssignmentExpreTextArea.getValue()!=''||
			   tasksAssignmentActorTextArea.getValue()!=''||
			   tasksAssignmentActorName.getValue()!=''||
			   tasksAssignmentPlActTextArea.getValue()!=''||
			   tasksAssignmentPlActName.getValue()!='')
			{
				var assignmentElement = taskXmlDocument.createElement('assignment');
				if(tasksAssignmentExpreTextArea.getValue()!='')
					assignmentElement.setAttribute('expression',tasksAssignmentExpreTextArea.getValue());
				if(tasksAssignmentActorTextArea.getValue()!='')
					assignmentElement.setAttribute('actor-id',tasksAssignmentActorTextArea.getValue());
				if(tasksAssignmentActorName.getValue()!='')
					assignmentElement.setAttribute('actor-name',tasksAssignmentActorName.getValue());
				if(tasksAssignmentPlActTextArea.getValue()!='')
					assignmentElement.setAttribute('pooled-actors',tasksAssignmentPlActTextArea.getValue());
				if(tasksAssignmentPlActName.getValue()!='')
					assignmentElement.setAttribute('pooled-actorsName',tasksAssignmentPlActName.getValue());
				//将该assignment节点加入到task节点上去              
				taskElement.appendChild(assignmentElement);
			}			
			
			//将该task节点的信息以字符串的形式添加到图元的task属性中去
			//alert(taskElement.xml);
			var edit = new mxCellAttributeChange( propertyCell, 'task', getXml(taskElement) );
			propertyModel.execute(edit);
			
			//以下将event的信息以字符串的形式添加到图元的event属性中去
			
			var eventXmlDocument = mxUtils.createXmlDocument();
			var eventElement = eventXmlDocument.createElement('event');
			var value = propertyModel.getValue(propertyCell);
			
			//填写event的type
			eventElement.setAttribute('type',tasksEventTypeComboBox.getValue());
			
			//通过读取variable标签里头的PropertyGrid内容来添加variable节点                 
			var variableElement = null;
			var variableText = null;
			var store = tasksEventActionPropertyGrid.getStore();
			for (var i = 0; i < store.data.length; i++) 
			{ 
				var record = store.getAt(i);          //读一条记录
				var colName = record.get("name");     //读取该记录中的name的值，用作Action的名称
				var colValue = record.get("value");   //读取该记录中的value的值，用作Action的值
				
				//设置第i个Action节点的name属性和class属性 
				actionElement = eventXmlDocument.createElement('action');
				actionElement.setAttribute('name',colName);
				actionElement.setAttribute('class',colValue);
				
				//将该action节点加入到event节点上去
				eventElement.appendChild(actionElement); 
			}
			
			//将该event节点的信息以字符串的形式添加到图元的event属性中去
			//alert(eventElement.xml);
			var edit = new mxCellAttributeChange( propertyCell, 'event', getXml(eventElement) );
			propertyModel.execute(edit);
           
        };	
        
		/**
		 * 把被选中的图元的相关信息导入到属性框中去
		 * @param {Object} cell
		 */
        mxGraph.prototype.reflashPropertyWin = function (cell)
        {           
		   var model = this.getModel();
		   
		   propertyModel = model;
		   propertyGraph = this;
		   
		   var value = model.getValue(cell);  
           
		   //alert(value.xml);

		   addIdWhitLabel('Task_Node','Task Node',taskNodeCount);    //给Task_Node节点换名
		   addIdWhitLabel('Fork','Fork',forkCount);                  //给Fork节点换名
		   addIdWhitLabel('Join','Join',joinCount);
		   addIdWhitLabel('State','State',stateNodeCount);
			
		   if (mxUtils.isNode(value))
		   {
		      //清空General标签里的属性列表内容，以备导入新的信息
			  generalTabPropertyGrid.store.removeAll();
			  
			  //导入图元的ID号
			  propertyIdValue = new Ext.grid.PropertyRecord({name:'ID', value:cell.getId()});
		      generalTabPropertyGrid.store.addSorted(propertyIdValue);
              	 
			  //导入图元的几何信息内容，ps.必须是含有几何信息的图元
			  if (model.isVertex(cell))
			  {
				 propertyGeo = model.getGeometry(cell);   
				 if (propertyGeo != null)
				 {					
					propertyTopValue = new Ext.grid.PropertyRecord({name:'top', value:propertyGeo.y});
					generalTabPropertyGrid.store.addSorted(propertyTopValue);
					
					propertyLeftValue = new Ext.grid.PropertyRecord({name:'left', value:propertyGeo.x});
					generalTabPropertyGrid.store.addSorted(propertyLeftValue);
					
					propertyWidthValue = new Ext.grid.PropertyRecord({name:'width', value:propertyGeo.width});
					generalTabPropertyGrid.store.addSorted(propertyWidthValue);
					
					propertyHeightValue = new Ext.grid.PropertyRecord({name:'height',value:propertyGeo.height});
					generalTabPropertyGrid.store.addSorted(propertyHeightValue);
				 }
			  }    
	   
			  //导入图元特有非几何信息内容
			  var attrs = value.attributes; 
			  //alert(value.nodeName);
              propertyAttrsValue = new Array();   
			  for (var i = 0; i < attrs.length; i ++ )
			  {	
                 if( attrs[i].nodeName != 'task' && attrs[i].nodeName != 'event' )
                 {	
					var nameCol = (attrs[i].nodeName == 'label')?'name':attrs[i].nodeName; //把label属性名显示为name属性名
					var valueCol = attrs[i].nodeValue;
				    propertyAttrsValue[i] = new Ext.grid.PropertyRecord({name:nameCol, value:valueCol});
				    generalTabPropertyGrid.store.addSorted(propertyAttrsValue[i]);
                 }
			  }              
              
              //如果当前被选中的图元不是Task_Node的话，需要隐藏Task相关的标签页
              if( value.nodeName != 'Task_Node' )
              {
                  tabPanelProperties.hideTabStripItem(tasksVariableFormPanel);
                  tabPanelProperties.hideTabStripItem(tasksAssignmentFormPanel);
                  tabPanelProperties.hideTabStripItem(tasksEventFormPanel);
                  tabPanelProperties.setActiveTab(generalTabPropertyGrid);                 
              }
              //如果当前被选中的图元是Task_Node的话，需要将其task信息导入到Tasks标签中去
              else if( value.nodeName == 'Task_Node' )
              {
                  tabPanelProperties.unhideTabStripItem(tasksVariableFormPanel);
                  tabPanelProperties.unhideTabStripItem(tasksAssignmentFormPanel);
                  tabPanelProperties.unhideTabStripItem(tasksEventFormPanel);
				  tabPanelProperties.setActiveTab(tasksVariableFormPanel);
				  
				  tasksVariableNameTextField.setValue('');
        	      tasksVariableValueTextField.setValue('');
        	      tasksVariablePropertyGrid.store.removeAll();
        	      
				  tasksAssignmentExpreTextArea.setValue('');
				  tasksAssignmentActorTextArea.setValue('');
				  tasksAssignmentActorName.setValue('');
				  tasksAssignmentPlActTextArea.setValue('');
				  tasksAssignmentPlActName.setValue('');
				  
				  tasksEventTypeComboBox.setValue('');
        	      tasksEventActionTextField.setValue('');
        	      tasksEventActionPropertyGrid.store.removeAll();

                  //如果当前Task_Node图元的task属性中的内容为空串的话，就让task标签内的各项内容置空
				  //alert(value.getAttribute('task'));            	  
            	  if(value.getAttribute('task')==''||null==value.getAttribute('task'))
            	  {

					  //给tasksVariablePropertyGrid初始化一条name为workflow_task_url的属性，必填字段
					  var newInitVariable = new Ext.grid.PropertyRecord({name:'workflow_task_url',value:''});
					  tasksVariablePropertyGrid.store.addSorted(newInitVariable);
					  //自定义PropertyGrid的Editors
					  tasksVariablePropertyGrid.customEditors = {           
						  "workflow_task_url" : new Ext.grid.GridEditor(new Ext.form.TextField({    
							                    name:'workflow_task_url',
							 			        allowBlank:false 
								      }))      
					  };
					  
					  saveToTasks();
                  }
                  else
                  {
					  //alert(value.getAttribute('task'));
  	                  var taskXmlDocument = mxUtils.parseXml(value.getAttribute('task'));
  	                  //alert(taskXmlDocument.xml);
  	                  var taskXmlElement = taskXmlDocument.getElementsByTagName('task')[0];
  	                  
  	                  //导入task的所有variable信息，ps.可以有很多个
			          tasksVariablePropertyGrid.store.removeAll();//清空列表内容，以备导入新的信息
  	                  var variableXmlElementList = taskXmlElement.getElementsByTagName('variable');
					  //判断是否存在workflow_task_url这个字段
					  var isTaskUrlExist = false;
                       
					  if( variableXmlElementList!=null )
					  {
					  	taskVariables = new Array();
	  	                for(i = 0; i < variableXmlElementList.length; i ++ )
	                    {  
	                        var colName  = variableXmlElementList[i].getAttribute('name');
							var colValue = getText( variableXmlElementList[i] );
	                        taskVariables[i] = new Ext.grid.PropertyRecord({name:colName, value:colValue});
	                        tasksVariablePropertyGrid.store.addSorted(taskVariables[i]);
							if(colName=="workflow_task_url"){
								isTaskUrlExist = true;
							}
	                    }
					  } 
					  if(!isTaskUrlExist){//如果不存在
					  //给tasksVariablePropertyGrid初始化一条name为workflow_task_url的属性，必填字段
					  		var newInitVariable = new Ext.grid.PropertyRecord({name:'workflow_task_url',value:''});
							tasksVariablePropertyGrid.store.addSorted(newInitVariable);
					  }
					  //自定义PropertyGrid的Editors
						tasksVariablePropertyGrid.customEditors = {           
						  "workflow_task_url" : new Ext.grid.GridEditor(new Ext.form.TextField({    
							                    name:'workflow_task_url',
							 			        allowBlank:false 
								      }))      
						};    
  	                  
  	                  //导入task的assignment信息，先判断属性名，再提取其值。
  	                  var assignmentXmlElement = taskXmlElement.getElementsByTagName('assignment')[0];
  	                  //alert(assignmentXmlElement.xml);
  	                  //alert(assignmentXmlElement.hasAttribute("pooled"));
					  //alert(assignmentXmlElement.getAttribute('handler'));
					  if(assignmentXmlElement != null )
					  {
					  	if( assignmentXmlElement.getAttribute('pooled-actors') != null )
					    {
					        tasksAssignmentPlActTextArea.setValue(assignmentXmlElement.getAttribute('pooled-actors'));
					    }else
						{
							tasksAssignmentPlActTextArea.setValue('');
						}

					  	if( assignmentXmlElement.getAttribute('pooled-actorsName') != null )
					    {
					        tasksAssignmentPlActName.setValue(assignmentXmlElement.getAttribute('pooled-actorsName'));
					    }else
						{
							tasksAssignmentPlActName.setValue('');
						}						

						if( assignmentXmlElement.getAttribute('expression') != null )
						{
					        tasksAssignmentExpreTextArea.setValue(assignmentXmlElement.getAttribute('expression'));
						}else
						{
							tasksAssignmentExpreTextArea.setValue('');
						} 
						
						if( assignmentXmlElement.getAttribute('actor-id') != null )
						{
					        tasksAssignmentActorTextArea.setValue(assignmentXmlElement.getAttribute('actor-id'));
						}else
						{
							tasksAssignmentActorTextArea.setValue('');
						}		

						if( assignmentXmlElement.getAttribute('actor-name') != null )
						{
					        tasksAssignmentActorName.setValue(assignmentXmlElement.getAttribute('actor-name'));
						}else
						{
							tasksAssignmentActorName.setValue('');
						}
					  }else
					  {
					  	tasksAssignmentExpreTextArea.setValue('');
					  	tasksAssignmentActorTextArea.setValue('');
						tasksAssignmentActorName.setValue('');
					  	tasksAssignmentPlActTextArea.setValue('');
						tasksAssignmentPlActName.setValue('');
					  }
                  }
                  
                  //如果当前Task_Node图元的event属性中的内容为空串的话，就让event标签内的各项内容置空
                  tabPanelProperties.setActiveTab(tasksEventFormPanel);
				  //alert(value.getAttribute('event'));            	  
            	  if(value.getAttribute('event')==''||null==value.getAttribute('event'))
            	  {
					  saveToTasks();
                  }
                  else
                  {                  	
					  //alert(value.getAttribute('event'));
  	                  var eventXmlDocument = mxUtils.parseXml(value.getAttribute('event'));
  	                  //alert(eventXmlDocument.xml);
  	                  var eventXmlElement = eventXmlDocument.getElementsByTagName('event')[0];
  	                  
  	                  //导入event的type信息
  	                  tasksEventTypeComboBox.setValue(eventXmlElement.getAttribute('type'));
  	                  
  	                  //导入event的所有action信息，ps.可以有很多个
  	                  var actionXmlElementList = eventXmlElement.getElementsByTagName('action');
                       
					  if( actionXmlElementList!=null )
					  {
					  	eventActions = new Array();
	  	                for(i = 0; i < actionXmlElementList.length; i ++ )
	                    {  
	                        var colName  = actionXmlElementList[i].getAttribute('name');
							var colValue = actionXmlElementList[i].getAttribute('class');
	                        eventActions[i] = new Ext.grid.PropertyRecord({name:colName, value:colValue});
	                        tasksEventActionPropertyGrid.store.addSorted(eventActions[i]);
	                    }
					  }
                  }
            	  tabPanelProperties.setActiveTab(generalTabPropertyGrid);
              }                                           
              return true;
		   }   
		   return false; 
        };
		
        /**
         * 属性框亮相
         * @param {Object} cell
         */		
		mxGraph.prototype.showProperties = function(cell)
		{
        	// cell = cell || this.getSelectionCell();
			
			// Uses the root node for the properties dialog
			// if not cell was passed in and no cell is
			// selected
			if (cell == null)
			{
			    //alert(cell.getId());
				cell = this.getCurrentRoot();
				if (cell == null)
				{
					cell = this.getModel().getRoot();
				}
			}
		
		   this.editor.stopEditing(true);
		   
		   propertyCell = cell;

		   if( propertiesWin != null )
		   {
		   	   propertiesWin.show();
			   propertiesWin.setPosition((document.body.clientWidth-this.propertiesWidth-60+document.body.scrollLeft),(document.body.scrollTop+20));
			   propertiesWin.setWidth(240);
			   propertiesWin.setHeight(500);
			   this.reflashPropertyWin(propertyCell);
		   }
		};

		/**
		 * 重写节点被点击后的响应事件
		 * @param {Object} evt
		 * @param {Object} cell
		 */
		mxGraph.prototype.click = function(evt, cell)
		{			
		  	//find this dispatchEvent's target!!!!!!
		  	this.dispatchEvent('click', this, evt, cell);

		  	if( mxEvent.isLeftMouseButton(evt) )
		  	{
			 	this.showProperties(cell);
		  	}
		  
		  	if(this.isEnabled() && ! mxEvent.isConsumed(evt))
		  	{
				 if(cell != null)
			 	{
					this.selectCellForEvent(cell, evt);
			 	}
			 	else
			 	{
					var swimlane = null;
					if(this.swimlaneSelectionEnabled)
					{
				  		var pt = mxUtils.convertPoint(this.container, evt.clientX, evt.clientY);
				   		swimlane = this.getSwimlaneAt(pt.x, pt.y);
					}
					if(swimlane != null)
					{
				   		this.selectCellForEvent(swimlane, evt);
					}
					else if( ! mxEvent.isToggleEvent(evt))
					{
				   		this.clearSelection();
					}
			 	}
		  	}
		}
 
