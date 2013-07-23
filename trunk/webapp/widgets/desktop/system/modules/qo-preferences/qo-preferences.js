Ext.namespace('QoDesk');
QoDesk.QoPreferences = Ext.extend(Ext.app.Module, {
	moduleType : 'system/preferences',
	moduleId : 'qo-preferences',
	menuPath : 'ToolMenu',
	launcher : {
        iconCls: 'pref-icon',
        shortcutIconCls: 'pref-shortcut-icon',
        text: '个人设置',
        tooltip: '<b>个人设置</b><br />Allows you to modify your desktop'
    }
});