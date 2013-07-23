<@bravo.Page name="user_add" title="新增系统用户">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="ccxxvv" region="center" border="false" width="800" tbar="toolBar"  dataProxy="./user!save.action">  
			<@bravo.Toolbar name="toolBar" valign="bottom">
                <@bravo.Button name="Save" tooltip="'保存'" text="保存" iconCls="add" handler="submitForm(\\'ccxxvv\\')"/>
			</@bravo.Toolbar>
                           <@bravo.FieldSet cols="1,1,1;1,1,1;1,1,1;1,1,1;1,1,1;3;1,1,1;1,2;3" rows="1,1,1;1,1,1;1,1,1;1,1,1;1,1,1;1;1,1,1;1,1;1" >
				<@bravo.TextField fieldLabel="登陆名" name="loginid" allowBlank="false" width="165"/>
				<@bravo.TextField fieldLabel="密码" name="passwd" allowBlank="false" width="165"/>   
				<@bravo.ComboBox name="duty.id" fieldLabel="职务" editable="false"  dataProxy="hql[from Enumeration where enumType.id=3]" displayField="name" valueField="id" />

				<@bravo.TextField fieldLabel="中文名" name="userName" allowBlank="false" width="165" />
				<@bravo.TextField fieldLabel="英文名" name="engName"allowBlank="false" width="165"/>
				<@bravo.TextField fieldLabel="工号" name="labour" allowBlank="false" width="165" />
				
				<@bravo.PopuSelect fieldLabel="部门" fieldName="department.name" readOnly="true" text="部门选择"  width="165" valueField="id" displayField="name" targetProxy="../common/department!query.action" targetGridName="department_grid" hiddenName="department.id" />
				<@bravo.DateField fieldLabel="入职时间" name="accession" width="165" format="Y-m-d"/>
				<@bravo.ComboBox fieldLabel="性别" name="gender.id" allowBlank="false" editable="false" dataProxy="hql[from Enumeration where enumType.id=50]" displayField="name" valueField="id" />
				
				<@bravo.ComboBox fieldLabel="教育程度" name="education.id" editable="false" dataProxy="hql[from Enumeration where enumType.id=54]" displayField="name" valueField="id" />
				<@bravo.TextField fieldLabel="状态" name="status"   readOnly="true" width="165"/>
				<@bravo.NumberField fieldLabel="邮编" name="postalcode" width="163" />

				<@bravo.ComboBox fieldLabel="国家" name="country.id" editable="true" dataProxy="hql[from Enumeration where enumType.id=53]" displayField="name" valueField="id"  allowBlank="false"/>
				<@bravo.ComboBox fieldLabel="省份" name="province.id" editable="false" dataProxy="hql[from Enumeration where enumType.id=52]" displayField="name" valueField="id"  allowBlank="false"/>
				<@bravo.ComboBox fieldLabel="城市" name="city.id" editable="false" dataProxy="hql[from Enumeration where enumType.id=51]" displayField="name" valueField="id"  allowBlank="false" />
				
				<@bravo.TextField fieldLabel="住址" name="address" width="675"/>
				
				<@bravo.TextField fieldLabel="固定电话" name="telephone" width="165" regex="fixPhone" />
				<@bravo.TextField fieldLabel="移动电话" name="mobilephone" width="165" regex="cellPhone"/>
				<@bravo.TextField fieldLabel="传真" name="fax" width="165" />

				<@bravo.TextField fieldLabel="电子邮箱" name="email" width="165" regex="email" />							
				<@bravo.TextField fieldLabel="更新人" name="lastUpdater.userName"  readOnly="true" width="165" value="%{requestHandler.currentUser.userName}" />				
				
				<@bravo.TextArea fieldLabel="备注" name="comments" width="675" height="120"/>
			  </@bravo.FieldSet>
		         	<@bravo.Hidden fieldLabel="当前更新人" name="lastUpdater.id"  readOnly="true" value="%{requestHandler.currentUser.id?c}" />
				<@bravo.Hidden fieldLabel="更新时间" name="lastUpdateDate"  readOnly="true" width="165" value="%{requestHandler.currentDate?datetime}"  />
		</@bravo.FormPanel>

    </@bravo.Viewport>
</@bravo.Page>