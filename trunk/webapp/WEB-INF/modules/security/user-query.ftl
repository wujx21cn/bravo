<@bravo.Page name="userManager" title="人员资料管理" >
	<@bravo.Viewport  layout="border">
		    <@bravo.FormPanel name="user_Find" tbar="Toolbar" region="north" dataProxy="./user!find.action"  ajaxPass="true"  frame="true" autoScroll="true" height="180" width="780" title="人员资料列表" collapsible="true" >  
			<@bravo.Toolbar name="Toolbar" valign="top">
				<@bravo.Button name="View" text="查找" iconCls="search" handler="searchForGrid(\\'user_Find\\',\\'user_Grid\\')"/>
				<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'user_Find\\')"/>
			</@bravo.Toolbar>
				<@bravo.FieldSet labelWidth="80" cols="1,1,1;1,1,1;1,2" rows="1,1,1;1,1,1;1,1" >		
					<@bravo.TextField fieldLabel="登陆名" name="loginid" width="160"/>
					<@bravo.TextField fieldLabel="中文" name="userName" width="160" />
					<@bravo.TextField fieldLabel="英文名" name="engName" width="160" />
					<@bravo.ComboBox name="gender.id" fieldLabel="性别" width="160" editable="false" dataProxy="hql[from Enumeration where enumType.id=50]" displayField="name" valueField="id" />
					<@bravo.ComboBox name="duty.id" fieldLabel="职务" width="160" editable="false" dataProxy="hql[from Enumeration where enumType.id=3]" displayField="name" valueField="id" />
					<@bravo.TextField fieldLabel="工号" name="labour" width="160" />
					<@bravo.DateField fieldLabel="入职时间早于" name="accession_index_1"  endDateField="accession_index_2" width="160" linkAgeField="accession_index_2"/>
					<@bravo.DateField fieldLabel="入职时间晚于" name="accession_index_2"  startDateField="accession_index_1" width="160"/>	
				</@bravo.FieldSet>
				<@bravo.Hidden  name="accession_index_1_restriction" value="ge" />
				<@bravo.Hidden name="accession_index_2_restriction"  value="le" />
				<@bravo.Hidden name="order_field_loginid"  value="desc" />
				<@bravo.Hidden name="order_field_userName"  value="asc" />
			 </@bravo.FormPanel>    

			 <@bravo.GridPanel name="user_Grid" region="center" tbar="Toodddlbar" contextDataName="users" stripeRows="true" dataProxy="./user!findAndRendJsonData.action"  collapsible="true" split="true" >
			     <@bravo.Toolbar name="Toodddlbar" valign="top" >
				 <@bravo.Button name="Add" text="新增" iconCls="add" handler="gridOpenNewWin(\\'./user!add.action\\',\\'新增人员\\',\\'user_Grid\\')"/>
				 <@bravo.Button name="Delete" text="删除" iconCls="delete" handler="grid_doDel(\\'./user!batchRemove.action\\',\\'user_Grid\\')"/>
			     </@bravo.Toolbar>               
			     <@bravo.Column hidden="true" name="id" header="ID" width="0"  sortable="true"  resizable="true"     />
			     <@bravo.Column  name="loginid" header="登陆名"  width="175"  sortable="true" resizable="true"     />
				 <@bravo.Column dataIndex="userName" name="userName" header="用户名" width="175"  sortable="true" resizable="true" />     
				 <@bravo.Column name="department.name" header="部门名称" width="175"  sortable="true"  resizable="true"     />
			     <@bravo.Column name="view" header="查看" sortable="true" >
			         <a href=\'javascript:gridOpenNewWin(\\"./user!view.action?id=@{rowValue.id?c}\\",\\"查看人员[@{rowValue.userName}]\\",\\"user_Grid\\")\' style=\\"TEXT-DECORATION:none\\"><span style=color:red;>查看</span></a>
			     </@bravo.Column>			   
	        </@bravo.GridPanel>
    </@bravo.Viewport>
</@bravo.Page>