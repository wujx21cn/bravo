/*
 * $Id: mxApplication.js,v 1.13 2008/07/14 14:58:14 gaudenz Exp $
 * Copyright (c) 2006, Gaudenz Alder
 *
 * Defines the startup sequence of the application.
 *
 */
{

	/**
	 * Constructs a new application
	 */
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
				
				// Updates the window title after opening new files
				var title = document.title;
				var funct = function(sender)
				{
					document.title = title + ' - ' + sender.getTitle();
				};
				
				editor.addListener('open', funct);
				
				// Prints the current root in the window title if the
				// current root of the graph changes (drilling).
				editor.addListener('root', funct);
				funct(editor);
				
				// Displays version in statusbar
				editor.setStatus('mxGraph '+mxClient.VERSION);

				//动态调整绘图区域的大小
				editor.graph.resizeContainer=true;
				editor.graph.border=600;
				
				// Adds a highlight on the cell under the mousepointer
				//new mxHighlight(editor.graph,'#FF0000');
				
				// Shows the application
				hideSplash();

				//创建属性框实例
				ExtOnReady();

				if( LoadWorkFlowDiagram != '' )
				{
				  var doc = mxUtils.parseXml(LoadWorkFlowDiagram);
				  
				  //获取Task_Node节点的个数
				  taskNodeList = doc.getElementsByTagName('Task_Node');
				  taskNodeCount = taskNodeList.length+1;
				  
				  //获取Fork节点的个数
				  forkNodeList = doc.getElementsByTagName('Fork');
				  forkCount = forkNodeList.length+1;
				  
				  //获取Join节点的个数
				  joinNodeList = doc.getElementsByTagName('Join');
				  joinCount = joinNodeList.length+1;
				  
				  //获取State节点的个数
				  stateNodeList  = doc.getElementsByTagName('State');
				  stateNodeCount = stateNodeList.length+1;
				  
				  var dec = new mxCodec(doc);
				  dec.decode(doc.documentElement, editor.graph.getModel());
				}
			}
		}
		catch (e)
		{
			hideSplash();
			window.location.reload();
			// Shows an error message if the editor cannot start
			// mxUtils.alert('Cannot start application: '+e.message);
			// throw e; // for debugging
		}
		
		editor.graph.setConnectable(true);
								
		return editor;
	}

}

//重写configure过程，增加一个异常抛出
mxEditor.prototype.configure = function(node)
{
  if(node != null)
  {
	 var dec = new mxCodec(node.ownerDocument);
	 //alert('omg');
	 try
	 {

		this.isConfiguring = true;
		dec.decode(node, this);
	 }
	 catch(e)
	 {
		//mxUtils.alert('Cannot configure the mxEditor: '+e.message);
		throw e;
	 }
	 finally
	 {
		this.isConfiguring = null;
	 }
	 //alert(node.xml);
	 this.resetHistory();
  }
};