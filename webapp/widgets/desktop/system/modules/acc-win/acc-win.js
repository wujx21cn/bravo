/* This code defines the module and will be loaded at start up.
 * 
 * When the user selects to open this module, the override code will
 * be loaded to provide the functionality.
 * 
 * Allows for 'Module on Demand'.
 */

QoDesk.AccordionWindow = Ext.extend(Ext.app.Module, {
	moduleType : 'demo',
	moduleId : 'demo-acc',
	menuPath : 'ToolMenu',
	createChatWin : Ext.emptyFn,
	launcher : {
		iconCls: 'acc-icon',
		shortcutIconCls: 'demo-acc-shortcut',
		text: '通信王子',
		tooltip: '<b>通信王子</b><br />A window with an accordion layout'
	}
});