<@bravo.Page name="departmentView" title="部门查看" cache="false">
	<@bravo.Viewport  layout="border">

		<@bravo.FormPanel name="departmentForm" region="center" border="false"  tbar="toolBar" frame="true" autoScroll="true" height="200"  collapsible="false"   dataProxy="./department!saveAndRendJsonData.action?id=%{formValue.id?c}" width="820"> 
			<@bravo.Toolbar name="toolBar" valign="top">
				<@bravo.Button name="Save" tooltip="'保存'" text="保存" iconCls="add" handler="ajaxSubmitForm(\\'departmentForm\\')"/>
			</@bravo.Toolbar>
			<@bravo.FieldSet  cols="1,1,1;1,1,1;1,2;3;3" rows="1,1,1;1,1,1;1,1;1;1" >		
				<@bravo.TextField fieldLabel="名称" fieldName="name" width="165"/>
				<@bravo.TextField fieldLabel="部门编码" fieldName="code" width="165"/>
				<@bravo.NumberField fieldLabel="顺序号" fieldName="sequences" width="165"/>
				<@bravo.PopuSelect fieldLabel="上级部门" fieldName="parentDepartment.name" readOnly="true" text="部门选择"  width="165" valueField="id" displayField="name" targetProxy="../common/department!query.action" targetGridName="department_grid" hiddenName="parentDepartment.id" />	
                <@bravo.PopuSelect fieldLabel="部门经理" fieldName="manager.userName" readOnly="true" text="人员选择"  width="166" valueField="id" displayField="userName" targetProxy="../security/user!query.action" targetGridName="user_Grid" hiddenName="manager.id" />
				<@bravo.NumberField fieldLabel="电话号码" fieldName="telephone" width="165"/>
				<@bravo.NumberField fieldLabel="邮政编码" fieldName="postalcode" width="165"/>  
				<@bravo.NumberField fieldLabel="传真号码" fieldName="fax" width="165"/>
				<@bravo.TextField fieldLabel="详细地址" fieldName="address" width="500"/>
				<@bravo.TextField fieldLabel="备注" fieldName="comments"   width="500"/>
			</@bravo.FieldSet>
		</@bravo.FormPanel>
		<@bravo.TabPanel activeTab="0" region="south" name="tab" height="250" collapsible="true">
			<@bravo.GridPanel title="部门成员列表" name="departmentUserGrid" dataProxy="../security/user!findAndRendJsonData.action?department.id=%{formValue.id?c}"  contextDataName="users">	  
				<@bravo.Column  hidden="true" name="id" header="ID" width="10"  sortable="true" />
					<@bravo.Column  name="loginid" header="登陆名"  width="175" sortable="true" resizable="true"     />
					<@bravo.Column  name="userName" header="用户名" width="175"  sortable="true" resizable="true" />
					<@bravo.Column  name="mobilephone" header="移动电话" width="175"  sortable="true" resizable="true" />     
			</@bravo.GridPanel>
		</@bravo.TabPanel>
	</@bravo.Viewport>
</@bravo.Page>
