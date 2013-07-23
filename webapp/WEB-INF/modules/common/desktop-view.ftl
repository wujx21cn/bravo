<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">       
    <title>BRAVO open platform</title>
    <script type="text/javascript" src="${bravoHome}/ui/dynamicJs!CreateDynamicJs.action"></script>   
    <LINK href="${bravoHome}/${extjsWidgetPath}/resources/css/ext-all.css" type="text/css" rel="stylesheet">    
    <script type="text/javascript" src="${bravoHome}/${extjsWidgetPath}/adapter/ext/ext-base.js"></script> 
    <script type="text/javascript" src="${bravoHome}/${extjsWidgetPath}/ext-all.js"></script>      
    <script type="text/javascript" src="${bravoHome}/${extjsWidgetPath}/source/locale/ext-lang-zh_CN.js"></script>   
 <script type="text/javascript" src="${bravoHome}/${extjsWidgetPath}/ux/extjs-bravo-ux.js"></script>
<!-- DESKTOP CSS -->
<link rel="stylesheet" type="text/css" href="${bravoHome}/widgets/desktop/resources/css/desktop.css" />
<link rel="stylesheet" type="text/css" href="${bravoHome}/widgets/desktop/system/dialogs/colorpicker/colorpicker.css" />

<link id='theme' rel='stylesheet' type='text/css' href='${bravoHome}/widgets/desktop/resources/themes/xtheme-vistaglass/css/xtheme-vistaglass.css' />
<link rel='stylesheet' type='text/css' href='${bravoHome}/widgets/desktop/system/modules/qwiki/preferences/preferences.css' />
<link rel='stylesheet' type='text/css' href='${bravoHome}/widgets/desktop/system/modules/grid-win/grid-win.css' />
<link rel='stylesheet' type='text/css' href='${bravoHome}/widgets/desktop/system/modules/tab-win/tab-win.css' />
<link rel='stylesheet' type='text/css' href='${bravoHome}/widgets/desktop/system/modules/acc-win/acc-win.css' />
<link rel='stylesheet' type='text/css' href='${bravoHome}/widgets/desktop/system/modules/layout-win/layout-win.css' />
<link rel='stylesheet' type='text/css' href='${bravoHome}/widgets/desktop/system/modules/bogus/bogus-win/bogus-win.css' />
<link rel='stylesheet' type='text/css' href='${bravoHome}/widgets/desktop/system/modules/qo-preferences/qo-preferences.css' />
<!-- SYSTEM DIALOGS AND CORE -->
<!-- In a production environment these should be minified into one file -->
<script src="${bravoHome}/widgets/desktop/system/dialogs/colorpicker/ColorPicker.js"></script>
<script src="${bravoHome}/widgets/desktop/system/core/App.js"></script>
<script src="${bravoHome}/widgets/desktop/system/core/Desktop.js"></script>
<script src="${bravoHome}/widgets/desktop/system/core/HexField.js"></script>
<script src="${bravoHome}/widgets/desktop/system/core/Module.js"></script>
<script src="${bravoHome}/widgets/desktop/system/core/Notification.js"></script>
<script src="${bravoHome}/widgets/desktop/system/core/Shortcut.js"></script>
<script src="${bravoHome}/widgets/desktop/system/core/StartMenu.js"></script>
<script src="${bravoHome}/widgets/desktop/system/core/TaskBar.js"></script>

<script src="${bravoHome}/widgets/desktop/system/modules/qo-preferences/qo-preferences.js"></script>
<script src="${bravoHome}/widgets/desktop/system/modules/qo-preferences/qo-preferences-override.js"></script>

<script src="${bravoHome}/widgets/desktop/system/modules/acc-win/acc-win.js"></script>
<script src="${bravoHome}/widgets/desktop/system/modules/acc-win/acc-win-override.js"></script>

<script src="${bravoHome}/widgets/desktop/system/modules/layout-win/layout-win.js"></script>
<script src="${bravoHome}/widgets/desktop/system/modules/layout-win/layout-win-override.js"></script>

<script type="text/javascript" src="${bravoHome}/widgets/webqq/jslib/common/TimeFormat.js"></script>
<script type="text/javascript" src="${bravoHome}/widgets/webqq/jslib/common/ColorUtil.js"></script>
<script type="text/javascript" src="${bravoHome}/widgets/webqq/dojo/dojo.js"></script>
<script type="text/javascript" src="${bravoHome}/widgets/webqq/jslib/sound/soundmanager2-min.js"></script>
<script type="text/javascript" src="${bravoHome}/widgets/webqq/jslib/qq/ClientWin.js"></script>

<script src="${bravoHome}/common/desktop!qoDesk.action"></script>
<link rel='stylesheet' type='text/css' href='${bravoHome}/common/desktop!qoDeskCCS.action' />
</head>

<body scroll="no">

<div id="x-desktop"></div>

<div id="ux-taskbar">
	<div id="ux-taskbar-start"></div>
	<div id="ux-taskbar-panel-wrap">
		<div id="ux-quickstart-panel"></div>
		<div id="ux-taskbuttons-panel"></div>
		<div id="ux-systemtray-panel"></div>
	</div>
	<div class="x-clear"></div>
</div>

</body>
</html>