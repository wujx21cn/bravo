<@bravo.Page name="Student_query" title="Student_query">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="Student_query" tbar="formToolBar" region="north" frame="true" autoScroll="true" height="150" width="780" autoScroll="true"  title="学生管理列表"  collapsible="true" > 
			<@bravo.Toolbar name="formToolBar" valign="top">
				<@bravo.Button name="View" text="查找" iconCls="search" handler="searchForGrid(\\'Student_query\\',\\'Student_grid\\')"/>
				<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'Student_query\\')"/>
			</@bravo.Toolbar>
			<@bravo.FieldSet labelWidth="60" cols="1,1;1,1" rows="1,1;1,1" >
				      <@bravo.TextField fieldLabel="姓名" name="name" width="160" />
				      <@bravo.PopuSelect fieldLabel="班主任" name="headTeacher.name" readOnly="true" text="选择班主任"  width="160" valueField="id" displayField="name" targetProxy="../common/teacher!query.action" targetGridName="Teacher_grid" hiddenName="headTeacher.id" />
				      <@bravo.TextField fieldLabel="性别" name="gender" width="160" />
				      <@bravo.NumberField fieldLabel="年龄" name="age" width="160" />
			</@bravo.FieldSet>
		</@bravo.FormPanel>   

        <@bravo.EditorGridPanel  region="center" tbar="gridToolBar"  split="true" stripeRows="true" collapsible="true" name="Student_grid"  contextDataName="students" dataProxy="./student!findAndRendJsonData.action"  >
			<@bravo.Toolbar name="gridToolBar" valign="top" >
				<@bravo.Button name="Add" text="新增" iconCls="add" handler="grid_doAdd(\\'Student_grid\\')"/>	
				<@bravo.Button name="Save" text="保存" iconCls="save" handler="grid_doSave(\\'./student!batchSave.action\\',\\'Student_grid\\')"/>			
				<@bravo.Button name="Delete" text="删除" iconCls="delete" handler="grid_doDel(\\'./student!batchRemove.action\\',\\'Student_grid\\')"/>
			</@bravo.Toolbar>   
			     <@bravo.Column hidden="true" name="id" header="ID" width="0"  sortable="true"  resizable="true" />
			     <@bravo.Column dataIndex="name" name="name" header="姓名" sortable="true" resizable="true" >
				   <@bravo.TextField fieldName="name" /> 
                 </@bravo.Column>
			     <@bravo.Column dataIndex="headTeacher.id" name="headTeacher.id" header="班主任" sortable="true" resizable="true" >
					<@bravo.ComboBox fieldName="headTeacher.id" typeAhead="true" mode="local" forceSelection="true" triggerAction="all" editable="false"  dataProxy="hql[from Teacher]" displayField="name" valueField="id" emptyText="选择" selectOnFocus="true" readonly="false"  />
                 </@bravo.Column>			     
				 <@bravo.Column dataIndex="gender" name="gender" header="性别" sortable="true" resizable="true" >     
				   <@bravo.TextField fieldName="gender" /> 
                 </@bravo.Column>			     
				 <@bravo.Column dataIndex="age" name="age" header="年龄" sortable="true" resizable="true" >     		   
				   <@bravo.NumberField  fieldName="age" /> 
                 </@bravo.Column>		
		</@bravo.EditorGridPanel>
	</@bravo.Viewport>
</@bravo.Page>
