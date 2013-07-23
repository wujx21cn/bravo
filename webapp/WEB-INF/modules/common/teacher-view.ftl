<@bravo.Page name="departmentView" title="教师查看" cache="false">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="Teacher_view" region="center" border="false"  tbar="toolBar" frame="true" autoScroll="true" height="200"  collapsible="false"   dataProxy="./teacher!saveAndRendJsonData.action?id=%{formValue.id?c}" width="780"> 
			<@bravo.Toolbar name="toolBar" valign="top">
				<@bravo.Button name="Save" tooltip="'保存'" text="保存" iconCls="add" handler="ajaxSubmitForm(\\'Teacher_view\\')"/>
			</@bravo.Toolbar>
			<@bravo.FieldSet labelWidth="60" cols="1,1,1;3;" rows="1,1,1;1;" >
				      <@bravo.TextField fieldLabel="姓名" name="name" width="165" />
				      <@bravo.TextField fieldLabel="性别" name="gender" width="165" />
				      <@bravo.TextField fieldLabel="年龄" name="age" width="165" />
				      <@bravo.TextField fieldLabel="工龄" name="workAge" width="165" />
			</@bravo.FieldSet>
		</@bravo.FormPanel>
		<@bravo.TabPanel activeTab="0"  tbar="gridToolBar" region="south" name="tab" height="400" split="true" collapsible="true">
			<@bravo.GridPanel title="学生列表" name="Teacher_grid" dataProxy="../common/student!findAndRendJsonData.action?teachers.id=%{formValue.id?c}"  contextDataName="students">	  
				 <@bravo.Toolbar name="gridToolBar" valign="top">
				 	<@bravo.M2MSelectButton text="选择添加学生色" iconCls="add" targetProxy="./student!query.action" targetGridName="Student_grid" originGridName="Teacher_grid" entityName="Teacher" fieldName="students" entityId="%{formValue.id?c}"/>
					<@bravo.M2MRemoveButton text="删除学生" iconCls="delete" originGridName="Teacher_grid" entityName="Teacher" fieldName="students" entityId="%{formValue.id?c}"/>
				 </@bravo.Toolbar>			  
			     <@bravo.Column hidden="true" name="id" header="ID" width="0"  sortable="true"  resizable="true" />
			     <@bravo.Column dataIndex="name" name="name" header="姓名" sortable="true" resizable="true" />
			     <@bravo.Column dataIndex="headTeacher.name" name="headTeacher.name" header="班主任" sortable="true" resizable="true" />     			     
				 <@bravo.Column dataIndex="gender" name="gender" header="性别" sortable="true" resizable="true" />     		     
				 <@bravo.Column dataIndex="age" name="age" header="年龄" sortable="true" resizable="true" />     		   
			</@bravo.GridPanel>
		</@bravo.TabPanel>
	</@bravo.Viewport>
</@bravo.Page>
