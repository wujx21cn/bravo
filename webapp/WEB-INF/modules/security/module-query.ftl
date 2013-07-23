<@bravo.Page name="ModuleManager" title="模块管理">
	<@bravo.Viewport  layout="border">
		    <@bravo.FormPanel name="Module_Find" tbar="Toolbar" region="north" frame="true" autoScroll="true" height="130" width="750" title="模块查询列表"  collapsible="true"  >  
			<@bravo.Toolbar name="Toolbar" valign="top">
				<@bravo.Button name="View" text="查询" iconCls="search" handler="searchForGrid(\\'Module_Find\\',\\'Module_Grid\\')"/>
				<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'Module_Find\\')"/>
			</@bravo.Toolbar>
				<@bravo.FieldSet  cols="1,1;2" rows="1,1;2" >		
					<@bravo.TextField fieldLabel="模块名称" name="title" width="160"/>
					<@bravo.PopuSelect fieldLabel="父模块" fieldName="parent.title" readOnly="true" text="模块选择"  width="160" valueField="id" displayField="title" targetProxy="../security/module!query.action" targetGridName="Module_Grid" hiddenName="parent.id" />
					<@bravo.TextField fieldLabel="备注" name="comments" width="520"/>
				
				</@bravo.FieldSet>
				
			 </@bravo.FormPanel>    
            <@bravo.EditorGridPanel  region="center" tbar="Toodddlbar"  name="Module_Grid"  contextDataName="modules" stripeRows="true"  dataProxy="./module!findAndRendJsonData.action"  collapsible="true" split="true" >
			<@bravo.Toolbar name="Toodddlbar" valign="top" defaults="{bodyStyle:'margin-top: 0px;padding:0px 0px 0px 0px'}" >
				<@bravo.Button name="Add" text="新增" iconCls="add" handler="grid_doAdd(\\'Module_Grid\\')"/>	
				<@bravo.Button name="Save" text="保存" iconCls="save" handler="grid_doSave(\\'./module!batchSave.action\\',\\'Module_Grid\\')"/>		<@bravo.Button text="删除" iconCls="delete" handler="grid_doDel(\\'./module!batchRemove.action\\',\\'Module_Grid\\')"/>	
			</@bravo.Toolbar>               
			   <@bravo.Column dataIndex="id" hidden="true" name="id" header="ID" width="176"  sortable="true"  resizable="true"/>
				<@bravo.Column dataIndex="title" name="title" header="名称"  width="175"  sortable="true" resizable="true">
			   <@bravo.TextField/> 
                </@bravo.Column>
               <@bravo.Column dataIndex="comments" name="comments" header="描述" width="300"  sortable="true"  resizable="true"    >
			   <@bravo.TextField/> 
                </@bravo.Column>        
	    </@bravo.EditorGridPanel>
    </@bravo.Viewport>
</@bravo.Page>

