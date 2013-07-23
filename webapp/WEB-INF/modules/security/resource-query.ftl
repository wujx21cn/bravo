<@bravo.Page name="ResourceManager" title="资源管理">
	<@bravo.Viewport  layout="border">
	<@bravo.FormPanel name="Resource_Find" tbar="Toolbar" region="north" frame="true" autoScroll="true" height="130"  title="资源查询列表"  collapsible="true"  >
		<@bravo.Toolbar name="Toolbar" valign="top">
			<@bravo.Button name="View" text="查询" iconCls="search" handler="searchForGrid(\\'Resource_Find\\',\\'Resource_Grid\\')"/>
			<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'Resource_Find\\')"/>
		</@bravo.Toolbar>
		<@bravo.FieldSet  cols="1,1,1;1,2;3;3;3;3;3" rows="1,1,1;1,1;1;1;1;1;1" >
			<@bravo.TextField fieldLabel="名称" fieldName="name" width="160"/>
			<@bravo.PopuSelect fieldLabel="所属模块" fieldName="module.title" readOnly="true" text="模块选择"  width="160" valueField="id" displayField="title" targetProxy="../security/module!query.action" targetGridName="Module_Grid" hiddenName="module.id"  width="160" />
			<@bravo.ComboBox fieldName="resType.id" fieldLabel="资源类型"  editable="false"  dataProxy="hql[from Enumeration where enumType.id=1]" displayField="name" valueField="id"  width="160"/>
			<@bravo.TextField fieldLabel="资源字段" fieldName="resString" width="160"/>
			<@bravo.TextField fieldLabel="备注" fieldName="comments" width="400"/>
			<@bravo.ProgressBar text="dskdsadasdsad" width="600"></@bravo.ProgressBar>
			<@bravo.ProgressBar text="撒旦撒旦" width="300"></@bravo.ProgressBar>
			<@bravo.ProgressBar text="3243得唔得" width="800"></@bravo.ProgressBar>
			<@bravo.ProgressBar text="34的撒旦撒旦" width="900"></@bravo.ProgressBar>
			<@bravo.ProgressBar text="dsdasfasd" width="700"></@bravo.ProgressBar>
		</@bravo.FieldSet>
		
		        
	</@bravo.FormPanel>
			
	<@bravo.EditorGridPanel  region="center" tbar="Toodddlbar"  name="Resource_Grid"  contextDataName="resources"  dataProxy="./resource!findAndRendJsonData.action"stripeRows="true"  collapsible="true" split="true">
		<@bravo.Toolbar name="Toodddlbar" valign="top" defaults="{bodyStyle:'margin-top: 0px;padding:0px 0px 0px 0px'}" >
			<@bravo.Button name="Add" text="新增" iconCls="add" handler="grid_doAdd(\\'Resource_Grid\\')"/>
			<@bravo.Button name="Save" text="保存" iconCls="save" handler="grid_doSave(\\'./resource!batchSave.action\\',\\'Resource_Grid\\')"/>      
			<@bravo.Button name="Delete" text="删除" iconCls="delete" handler="grid_doDel(\\'./resource!batchRemove.action\\',\\'Resource_Grid\\')"/>
		</@bravo.Toolbar>
		<@bravo.Column hidden="true" name="id" header="ID" width="50"  sortable="true"  resizable="true"/>
		<@bravo.Column name="name" header="名称"  width="100"  sortable="true" resizable="true">
			<@bravo.TextField/>
		</@bravo.Column>
		<@bravo.Column name="module.id" header="所属模块" width="100" sortable="true"  resizable="true">
			<@bravo.ComboBox fieldName="module.id" typeAhead="true"  forceSelection="true" triggerAction="all" editable="false"  dataProxy="hql[from Module]" displayField="title" valueField="id" emptyText="选择" selectOnFocus="true" readonly="false"  />
		</@bravo.Column>
		<@bravo.Column name="resType.id" header="资源类型" width="100" sortable="true"  resizable="true" >
			<@bravo.ComboBox fieldName="resType.id" typeAhead="true"  forceSelection="true" triggerAction="all" editable="false"  dataProxy="hql[from Enumeration where enumType.id=1]" displayField="name" valueField="id" emptyText="选择" selectOnFocus="true" readonly="false"  />
		</@bravo.Column>
		<@bravo.Column name="resString" header="资源字段"  width="200"  sortable="true" resizable="true">
			<@bravo.TextField/>
		</@bravo.Column>
		<@bravo.Column name="comments" header="备注" width="300"  sortable="true"  resizable="true"    >
			<@bravo.TextField/>
		</@bravo.Column>
	</@bravo.EditorGridPanel>
	</@bravo.Viewport>
</@bravo.Page>
