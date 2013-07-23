<@bravo.Page name="Teacher_add" title="Teacher_add">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="Teacher_add" tbar="formToolBar" region="center" height="500" dataProxy="./teacher!save.action" frame="true" autoScroll="true" width="780"  autoScroll="true"   collapsible="true" > 
			<@bravo.Toolbar name="formToolBar" valign="top">
			  <@bravo.Button  name="Save" text="保存" iconCls="add" handler="submitForm(\\'Teacher_add\\')"/>
				</@bravo.Toolbar>
				<@bravo.FieldSet labelWidth="60" cols="1,1,1;3;" rows="1,1,1;1;" >
				   <@bravo.TextField fieldLabel="姓名" name="name" width="165" />
				   <@bravo.TextField fieldLabel="性别" name="gender" width="165" />
				   <@bravo.NumberField fieldLabel="年龄" name="age" width="165" />
				   <@bravo.NumberField fieldLabel="工龄" name="workAge" width="165" />
			</@bravo.FieldSet>
		</@bravo.FormPanel>  		
	</@bravo.Viewport>
</@bravo.Page>

