<@bravo.Page name="EnumerationTypeManager" title="基础数据管理">
	<@bravo.Viewport  layout="border">
		    <@bravo.FormPanel name="enumeration_find" tbar="Toolbar" region="north"  frame="true" autoScroll="true" height="130" width="750"   title="基础数据查询列表" >  
			<@bravo.Toolbar name="Toolbar" valign="top">
				<@bravo.Button name="View" text="查询" iconCls="search" handler="searchForGrid(\\'enumeration_find\\',\\'enumeration_grid\\')"/>
				<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'enumeration_find\\')"/>
			</@bravo.Toolbar>
				<@bravo.FieldSet  cols="1,1,1;3" rows="1,1,1;1" >		
					<@bravo.TextField fieldLabel="名称" fieldName="name" width="160"/>
					<@bravo.TextField fieldLabel="编码" fieldName="code" width="160"/>
					<@bravo.ComboBox fieldName="enumType.id" fieldLabel="类型"  editable="false"  dataProxy="hql[from EnumerationType]" displayField="name" valueField="id" width="160" />
					<@bravo.TextField fieldLabel="备注" fieldName="comments" width="600"/>
				</@bravo.FieldSet>
			 </@bravo.FormPanel>    

            <@bravo.EditorGridPanel  region="center" tbar="Toodddlbar"   name="enumeration_grid"  collapsible="true" split="true" dataProxy="./enumeration!findAndRendJsonData.action" contextDataName="enumerations" stripeRows="true" >
			<@bravo.Toolbar name="Toodddlbar" valign="top" >
				<@bravo.Button name="Add" text="新增" iconCls="add" handler="grid_doAdd(\\'enumeration_grid\\')"/>	
				<@bravo.Button name="Save" text="保存" iconCls="save" handler="grid_doSave(\\'./enumeration!batchSave.action\\',\\'enumeration_grid\\')"/>		<@bravo.Button text="删除" iconCls="delete" handler="grid_doDel(\\'./enumeration!batchRemove.action\\',\\'enumeration_grid\\')"/>	
				</@bravo.Toolbar>               
			   <@bravo.Column hidden="true" name="id" header="ID" width="176"  sortable="true"  resizable="true"/>
				<@bravo.Column name="code" header="编码"  width="75"  sortable="true" resizable="true">
					<@bravo.TextField  fieldName="code"/> 
                </@bravo.Column>
				<@bravo.Column name="name" header="名称"  width="75"  sortable="true" resizable="true">
					<@bravo.TextField  fieldName="name" /> 
                </@bravo.Column>
				<@bravo.Column name="enumType.id" header="数据类型" width="100" sortable="true"  resizable="true" > 
					<@bravo.ComboBox fieldName="enumType.id" typeAhead="true" mode="local" forceSelection="true" triggerAction="all" editable="false"  dataProxy="hql[from EnumerationType]" displayField="name" valueField="id" emptyText="选择" selectOnFocus="true" readonly="false"  />
                </@bravo.Column>
				<@bravo.Column name="sequences" header="顺序号"  width="50"  sortable="true" resizable="true">
					<@bravo.NumberField  fieldName="sequences" /> 
                </@bravo.Column>
               <@bravo.Column name="comments" header="描述" width="400"  sortable="true"  resizable="true"    >
					<@bravo.TextField fieldName="comments" /> 
                </@bravo.Column>        
	    </@bravo.EditorGridPanel>
    </@bravo.Viewport>
</@bravo.Page>
