<@bravo.Page name="ProfileMenuManager" title="个人设置管理">
	<@bravo.Viewport  layout="border">
	<@bravo.FormPanel name="formPanel" region="center" layout="fit" border="false" >
		<@bravo.EditorGridPanel tbar="Toodddlbar"  name="ProfileMenu_Grid"  contextDataName="ProfileMenus" dataProxy="./profileMenu!findAndRendJsonData.action?user.id=%{requestHandler.currentUser.id?c}" collapsible="false">
			<@bravo.Toolbar name="Toodddlbar" valign="top" defaults="{bodyStyle:'margin-top: 0px;padding:0px 0px 0px 0px'}" >
				<@bravo.Button name="Add" text="新增" iconCls="add" handler="grid_doAdd(\\'ProfileMenu_Grid\\')"/>
				<@bravo.Button name="Save" text="保存" iconCls="save" handler="grid_doSave(\\'./profileMenu!batchSave.action\\',\\'ProfileMenu_Grid\\')"/>      
				<@bravo.Button name="Delete" text="删除" iconCls="delete" handler="grid_doDel(\\'./profileMenu!batchRemove.action\\',\\'ProfileMenu_Grid\\')"/>
			</@bravo.Toolbar>
			<@bravo.Column hidden="true" name="id" header="ID" width="50"  sortable="true"  resizable="true"/>
			<@bravo.Column name="menuFunction___id" header="菜单选项" width="232" sortable="true"  resizable="true" >
			<@bravo.ComboBoxTree fieldName="menuFunction.id" width="200" dataTreeProxy="./menuFunction!viewTree.action?setNodeHref=no" rootId="0" rootText="选择菜单" selectNodeModel="leaf"  displayField="name" valueField="id" dataProxy="hql[from MenuFunction]"/>	
			</@bravo.Column>
			<@bravo.Column name="icon" header="图标"  width="99"  sortable="true" resizable="true">
				<@bravo.ComboBox fieldName="iconid" typeAhead="true" forceSelection="true" triggerAction="all" editable="false"  dataProxy="hql[from Enumeration where enumType.id=${iconTypeId}]" displayField="code" valueField="name" emptyText="选择" selectOnFocus="true" readonly="false"  tpl="<tpl for=\".\"><div class=\"x-combo-list-item\"><p><img src=\"%{bravoHome}/%{extjsWidgetPath}/resources/bravo-images/shortcut-images/{name}\" class=\"x-icon-combo-img\"/>{code}</p></div></tpl>"/>
			</@bravo.Column>
			<@bravo.Column name="sequence" header="顺序"  width="80"  sortable="true" resizable="true">
				<@bravo.TextField/>
			</@bravo.Column>
		</@bravo.EditorGridPanel>
      </@bravo.FormPanel>
	</@bravo.Viewport>
</@bravo.Page>