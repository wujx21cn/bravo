<html>
<head>
	<title>Workflow Preview</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript">
		mxBasePath = '../src/';
	</script>
	<script type="text/javascript">
		//获取工作流定义图
	    var LoadWorkFlowDiagram = '${workFlowDiagram?default("")}';
	</script>

	<script type="text/javascript" src="../src/js/mxClient.js"></script>
	
	<script type="text/javascript">
		mxConstants.DEFAULT_HOTSPOT = 1;
	</script>
	
	<script type="text/javascript">
		/**
		 * Creates an overlay object using the given tooltip and text for the alert window
		 * which is being displayed on click.
		 */
		function createOverlay(image, tooltip)
		{
			var overlay = new mxOverlay(image, tooltip);

			// Installs a handler for clicks on the overlay
			overlay.addListener('click', function(sender, evt, cell)
			{
				mxUtils.alert(tooltip);
			});
			
			return overlay;
		};

		/**
		 * 用带color颜色的框标注graph上的节点图元cell，同时在图元右小角添加一个img图标
		 */
		function markCell(graph,cell,color,icon)
		{
			//默认颜色为红色
			color != null ? color=color : color='red';

			var model = graph.getModel();
			var propertyGeo = model.getGeometry(cell);
			
			if ( model.isVertex(cell) && propertyGeo != null )
			{
				var shape = new mxRectangleShape(new mxRectangle(), null, '#ff0000', 3);
				shape.dialect = (graph.dialect != mxConstants.DIALECT_SVG) ? mxConstants.DIALECT_VML : mxConstants.DIALECT_SVG;
				shape.init(graph.getView().getOverlayPane());
				shape.node.style.background = '';
				shape.node.style.display = 'none';shape.redraw();
				shape.bounds = new mxRectangle( 
												 propertyGeo.x-2,
												 propertyGeo.y-2,
												 propertyGeo.width+4,
												 propertyGeo.height+4 
											  );
											  
				shape.node.style.display = 'inline';
				shape.redraw();
											  
				if (shape.dialect == mxConstants.DIALECT_SVG)
				{
					shape.innerNode.setAttribute('stroke', color);
				}
				else
				{
					shape.node.setAttribute('strokecolor', color);
				}
				if(null!=icon)
				{
					graph.addOverlay(cell, icon);
				}
			}
		};
		
		/**
		 * 用带color颜色的线框标注graph上名称为label的节点图元，同时在其右下角添加一个img图标
		 */
		function markCellbyLabel(graph,label,color,icon)
		{
			var cellArray = new Array();
			cellArray = graph.getChildCells(null, true, false);

			for (var i = 0; i < cellArray.length; i++)
			{
				if( label == cellArray[i].getAttribute('label') )
				{
					markCell(graph,cellArray[i],color,icon);
				}
			}
		}
	</script>
	
	<script type="text/javascript">	
		function mxApplication(config)
		{
			var hideSplash = function()
			{
				// Fades-out the splash screen
				var splash = document.getElementById('splash');
				
				if (splash != null)
				{
					try
					{
						mxUtils.release(splash);
						mxUtils.fadeOut(splash, 100, true);
					}
					catch (e)
					{
						splash.parentNode.removeChild(splash);
					}
				}
			}
			
			try
			{
				if (!mxClient.isBrowserSupported())
				{
					mxUtils.error('Browser is not supported!', 200, false);
				}
				else
				{
					var node = mxUtils.load(config).getDocumentElement();
					
					var editor = new mxEditor(node);
					
					// Shows the application
					hideSplash();
	
					if( LoadWorkFlowDiagram != '' )
					{
					  var doc = mxUtils.parseXml(LoadWorkFlowDiagram);
					  var dec = new mxCodec(doc);
					  dec.decode(doc.documentElement, editor.graph.getModel());
					}
				}
			}
			catch (e)
			{
				hideSplash();
				window.location.reload();
			}
			
			editor.graph.setEnabled(false);

			/*			
			//如果是从流程实例查看进来
			if( null!=FinishedTasks || null!=UnFinishedTasks )
			{
				markCellbyLabel( editor.graph,'Start','lime',createOverlay(new mxImage('./images/overlays/check.png',16,16), 'Finished task') );
			}
			
			//标注已完成任务节点
			for( var i=0; null!=FinishedTasks && i<FinishedTasks.length; i++ )
			{
				markCellbyLabel(editor.graph,FinishedTasks[i],'lime',createOverlay(new mxImage('./images/overlays/check.png',16,16),'Finished task') );
			}
			
			//标注未完成任务节点
			for( var i=0; null!=UnFinishedTasks && i<UnFinishedTasks.length; i++ )
			{
				markCellbyLabel(editor.graph,UnFinishedTasks[i],'red',createOverlay(editor.graph.warningImage,'To be finished task'));
			}
			*/
			<#if processStartTime?exists>
				markCellbyLabel(editor.graph,'Start','lime',createOverlay(new mxImage('./images/overlays/check.png',16,16),'流程创建于: ${processStartTime}\n流程创建者: ${processStarterName}') );
			</#if>

			<#if finishedTasksInfo?has_content>
				<#list finishedTasksInfo as taskInstancesListItem>
					markCellbyLabel(editor.graph,'${taskInstancesListItem.name}','lime',createOverlay(new mxImage('./images/overlays/check.png',16,16),'任务创建于: ${taskInstancesListItem.createTime}\n任务完成于: ${taskInstancesListItem.endTime}\n任务执行者: ${taskInstancesListItem.actorName}') );
				</#list>
			</#if>

			<#if unfinishedTasksInfo?has_content> 
				<#list unfinishedTasksInfo as taskInstancesListItem>
					markCellbyLabel(editor.graph,'${taskInstancesListItem.name}','red',createOverlay(editor.graph.warningImage,'任务创建于: ${taskInstancesListItem.createTime}\n等待处理者: ${taskInstancesListItem.actorName}'));
				</#list>
			</#if>
			
			<#if processEndTime?exists>
				markCellbyLabel(editor.graph,'end','lime',createOverlay(new mxImage('./images/overlays/check.png',16,16),'流程创建于: ${processStartTime}\n流程完成于: ${processEndTime}\n流程创建者: ${processStarterName}') );
			</#if>
			
			return editor;
		};
		
		mxEditor.prototype.configure = function(node)
		{
		  if(node != null)
		  {
			 var dec = new mxCodec(node.ownerDocument);
			 try
			 {
				this.isConfiguring = true;
				dec.decode(node, this);
			 }
			 catch(e)
			 {
				throw e;
			 }
			 finally
			 {
				this.isConfiguring = null;
			 }
			 this.resetHistory();
		  }
		};
	</script>
	
</head>
<body onload="new mxApplication('config/workflowpreview.xml');">
	<table id="splash" width="100%" height="100%" style="background:white;position:absolute;top:0px;left:0px;z-index:4;">
		<tr>
			<td align="center" valign="middle">
				<img src="images/loading.gif">
			</td>
		</tr>
	</table>
	<div id="graph" class="base">
		<!-- Graph Here -->
	<b/div>
	<div id="status" class="base" align="right">
		<!-- Status Here -->
	</div>
</body>
</html>