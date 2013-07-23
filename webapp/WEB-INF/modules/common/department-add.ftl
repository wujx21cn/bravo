<@bravo.Page name="department_add" title="新增部门">
	<@bravo.Viewport  layout="border">

		<@bravo.FormPanel name="department_add_form" region="center" border="false"  tbar="toolBar" dataProxy="./department!save.action" >  

			<@bravo.Toolbar name="toolBar" valign="bottom">
     <@bravo.Button name="Save" tooltip="'保存'" text="保存" iconCls="add" handler="submitForm(\\'department_add_form\\')"/>

			</@bravo.Toolbar>
			<@bravo.FieldSet  cols="1,1,1;1,1,1;1,2;3;3" rows="1,1,1;1,1,1;1,1;1;1" >		
				<@bravo.TextField fieldLabel="名称" fieldName="name" width="165" allowBlank="false" />
				<@bravo.TextField fieldLabel="部门编码" fieldName="code" width="165" allowBlank="false" />
				<@bravo.NumberField fieldLabel="顺序号" fieldName="sequences" width="165"/>
				<@bravo.PopuSelect fieldLabel="上级部门" fieldName="parentDepartment.name" readOnly="true" text="部门选择"  width="166" valueField="id" displayField="name" targetProxy="../common/department!query.action" targetGridName="department_grid" hiddenName="parentDepartment.id" />
				<@bravo.PopuSelect fieldLabel="部门经理" fieldName="manager.userName" readOnly="true" text="人员选择"  width="166" valueField="id" displayField="userName" targetProxy="../security/user!query.action" targetGridName="user_Grid" hiddenName="manager.id" />	
				<@bravo.NumberField fieldLabel="电话号码" fieldName="telephone" width="165"/>
				<@bravo.NumberField fieldLabel="邮政编码" fieldName="postalcode" width="165"/>  
				<@bravo.NumberField fieldLabel="传真号码" fieldName="fax" width="165"/>
				<@bravo.TextField fieldLabel="详细地址" fieldName="address" width="500"/>
				<@bravo.TextField fieldLabel="备注" fieldName="comments"   width="500"/>
			</@bravo.FieldSet>
		</@bravo.FormPanel>
    </@bravo.Viewport>
</@bravo.Page>
