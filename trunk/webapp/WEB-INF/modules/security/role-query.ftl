<@bravo.Page name="RoleManager" title="系统角色管理">
	<@bravo.Viewport  layout="border">
		    <@bravo.FormPanel name="Role_Find" tbar="Toolbar" region="north" dataProxy="./role!list.action"  ajaxPass="true"  frame="true" autoScroll="true" height="100"  title="角色查询列表"  collapsible="true"  >  
			<@bravo.Toolbar name="Toolbar" valign="top">
				<@bravo.Button name="View" text="查询" iconCls="search" handler="searchForGrid(\\'Role_Find\\',\\'Role_Grid\\')"/>
				<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'Role_Find\\')"/>
			</@bravo.Toolbar>
				<@bravo.FieldSet  cols="1,1" rows="1,1" >		
					<@bravo.TextField fieldLabel="名称" name="name" width="180"/>
					<@bravo.TextField fieldLabel="备注" name="comments" width="180"/>
				</@bravo.FieldSet>
			 </@bravo.FormPanel>    
            <@bravo.EditorGridPanel  region="center" tbar="Toodddlbar"  name="Role_Grid"  contextDataName="Roles"  dataProxy="./role!findAndRendJsonData.action"  stripeRows="true"  collapsible="true" split="true">
			<@bravo.Toolbar name="Toodddlbar" valign="top" defaults="{bodyStyle:'margin-top: 0px;padding:0px 0px 0px 0px'}" >
				<@bravo.Button name="Add" text="新增" iconCls="add" handler="grid_doAdd(\\'Role_Grid\\')"/>	
				<@bravo.Button name="Save" text="保存" iconCls="save" handler="grid_doSave(\\'./role!batchSave.action\\',\\'Role_Grid\\')"/>		
				<@bravo.Button name="Delete" text="删除" iconCls="delete" handler="grid_doDel(\\'./role!batchRemove.action\\',\\'Role_Grid\\')"/>	
			</@bravo.Toolbar>               
			   <@bravo.Column dataIndex="id" hidden="true" name="id" header="ID" width="176"  sortable="true"  resizable="true"/>
				<@bravo.Column dataIndex="name" name="name" header="名称"  width="175"  sortable="true" resizable="true">
			   <@bravo.TextField/> 
                </@bravo.Column>
               <@bravo.Column dataIndex="comments" name="comments" header="备注" width="300"  sortable="true"  resizable="true"    >
			   <@bravo.TextField/> 
                </@bravo.Column>  
			   <@bravo.Column name="view" header="查看"   sortable="true" >
					<a href=\'javascript:gridOpenNewWin(\\"./role!view.action?id=@{rowValue.id?c}\\",\\"@{rowValue.name}\\",\\"Role_Grid\\")\' style=\\"TEXT-DECORATION:none\\"><span style=color:red;>查看</span></a>
			   </@bravo.Column>					
	    </@bravo.EditorGridPanel>
    </@bravo.Viewport>
</@bravo.Page>

