<@bravo.Page name="DeskTopConfigResourceManager" title="系统桌面资源管理">
	<@bravo.Viewport  layout="border">
		    <@bravo.FormPanel name="deskTopConfigResource_find" tbar="Toolbar" region="center"  frame="true" autoScroll="true" height="185" width="750"   title="系统桌面资源查询" collapsible="true" >  
			<@bravo.Toolbar name="Toolbar" valign="top">
				<@bravo.Button name="View" text="查询" iconCls="search" handler="searchForGrid(\\'deskTopConfigResource_find\\',\\'deskTopConfigResource_grid\\')"/>
				<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'deskTopConfigResource_find\\')"/>
			</@bravo.Toolbar>
				<@bravo.FieldSet  cols="1,1" rows="1,1" >		
					<@bravo.TextField fieldLabel="名称" fieldName="name" width="150"/>
					<@bravo.ComboBox fieldName="type.id" fieldLabel="类型"  editable="false"  dataProxy="hql[from Enumeration where enumType.id = 85]" displayField="name" valueField="id" />
				</@bravo.FieldSet>
			 </@bravo.FormPanel>    

            <@bravo.EditorGridPanel  region="south" tbar="Toodddlbar" height="400"  name="deskTopConfigResource_grid"  collapsible="true" split="true" dataProxy="./deskTopConfigResource!findAndRendJsonData.action" title="系统桌面资源" contextDataName="deskTopConfigResources">
			<@bravo.Toolbar name="Toodddlbar" valign="top" >
				<@bravo.Button name="Add" text="新增" iconCls="add" handler="grid_doAdd(\\'deskTopConfigResource_grid\\')"/>	
				<@bravo.Button name="Save" text="保存" iconCls="save" handler="grid_doSave(\\'./deskTopConfigResource!batchSave.action\\',\\'deskTopConfigResource_grid\\')"/>		<@bravo.Button text="删除" iconCls="delete" handler="grid_doDel(\\'./deskTopConfigResource!batchRemove.action\\',\\'deskTopConfigResource_grid\\')"/>	
				</@bravo.Toolbar>               
			   <@bravo.Column hidden="true" name="id" header="ID" width="176"  sortable="true"  resizable="true"/>
				<@bravo.Column name="name" header="名称"  width="75"  sortable="true" resizable="true">
					<@bravo.TextField  fieldName="name"/> 
                </@bravo.Column>
				<@bravo.Column name="url" header="url"  width="75"  sortable="true" resizable="true">
					<@bravo.TextField  fieldName="url" /> 
                </@bravo.Column>
				<@bravo.Column name="type.id" header="资源类型" width="100" sortable="true"  resizable="true" > 
					<@bravo.ComboBox fieldName="type.id" typeAhead="true" mode="local" forceSelection="true" triggerAction="all" editable="false"  dataProxy="hql[from Enumeration where enumType.id = 85]" displayField="name" valueField="id" emptyText="选择" selectOnFocus="true" readonly="false"  />
                </@bravo.Column>
				<@bravo.Column name="thumbnail" header="thumbnail"  width="50"  sortable="true" resizable="true">
					<@bravo.NumberField  fieldName="file" /> 
                </@bravo.Column>
               <@bravo.Column name="file" header="file" width="400"  sortable="true"  resizable="true"    >
					<@bravo.TextField fieldName="file" /> 
                </@bravo.Column>        
	    </@bravo.EditorGridPanel>
    </@bravo.Viewport>
</@bravo.Page>
