<html>
<head>
	<title>Workflow Disigner</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style type="text/css" media="screen">
		div.base {
			position: absolute;
			overflow: hidden;
			white-space: nowrap;
			font-family: Arial;
			font-size: 8pt;
		}
		div.base#graph {
			border-style: solid;
			border-color: #F2F2F2;
			border-width: 1px;
			background: url('images/grid.gif');
		}
	</style>

	<link rel="stylesheet" type="text/css" href="${bravoHome}/${extjsWidgetPath}/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="${bravoHome}/${extjsWidgetPath}/resources/css/xtheme-default.css" />
	<link rel="stylesheet" type="text/css" href="${bravoHome}/${extjsWidgetPath}/extjs-bravo.css" />
	<script type="text/javascript" src="${bravoHome}/${extjsWidgetPath}/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${bravoHome}/${extjsWidgetPath}/ext-all.js"></script>
	<script type="text/javascript" src="${bravoHome}/${extjsWidgetPath}/ux/TreeCheckNodeUI.js"></script>

	<script type="text/javascript">
		mxBasePath = '../src/';
		
		//解析xml dom节点的xml字符串
		function getXml(oNode)
		{
			var sXml = "";
			if( 0 < navigator.userAgent.indexOf( 'Firefox' ) )
		    {
		   		sXml = (new XMLSerializer()).serializeToString(oNode);
		    }
		    else
		    {
		   		sXml = oNode.xml;
		    }
		    return sXml;
		}
		
		//解析xml dom节点的文本值
		function getText(oNode) 
		{
		    var sText = "";
		    for (var i = 0; i < oNode.childNodes.length; i++) 
			{
		       if (oNode.childNodes[i].hasChildNodes()) 
			   {
		           sText += getText(oNode.childNodes[i]);
		       } 
			   else 
			   {
		           sText += oNode.childNodes[i].nodeValue;
		       }
		    }
		    return sText;
		}
	</script>

	<script type="text/javascript">
	    LoadWorkFlowDiagram = '${workFlowDiagram?default("")}';
	</script>

    <script type="text/javascript" src="../src/js/ExtOnReady.js"></script>
	<script type="text/javascript" src="../src/js/mxClient.js"></script>
	<script type="text/javascript" src="../src/js/ShowPropertiesInMxEdit.js"></script>
	<script type="text/javascript" src="../src/js/JpdlConvert.js"></script>
	<script type="text/javascript" src="../src/js/mxApplication.js"></script>
	<script type="text/javascript">
		mxConstants.DEFAULT_HOTSPOT = 1;
	</script>
</head>
<body onload="new mxApplication('config/workfloweditor.xml');">
	<table id="splash" width="100%" height="100%"
		style="background:white;position:absolute;top:0px;left:0px;z-index:4;">
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
<script>
//每次加载的时候读取cookie里css的值
var cookiesArr = document.cookie.split("; ");
var css;
for(var i=0;i<cookiesArr.length;i++)
{
   var arr = cookiesArr[i].split("=");
   if(arr[0]=="css")
  {
      css = arr[1];
      break;
  }
}
document.getElementsByTagName("link")[1].href = "${bravoHome}/${extjsWidgetPath}/resources/css/"+css;
</script>