<@bravo.Page name="newsView" title="新闻查看" cache="false">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="newsForm" region="center" border="false" height="180" width="790" collapsible="false" tbar="toolBar" dataProxy="./news!saveAndRendJsonData.action?id=%{formValue.id?c}">  
			<@bravo.Toolbar name="toolBar" valign="bottom">
                                <@bravo.Button name="Save" tooltip="'保存'" text="保存" iconCls="add" handler="ajaxSubmitForm(\\'newsForm\\')"/>
			</@bravo.Toolbar>
			 <@bravo.FieldSet border=true cols="3;1,1,1;3" rows="1;1,1,1;1" >
				<@bravo.TextField fieldLabel="标题" name="title" allowBlank="false" width="650"/>
				  <@bravo.TextField fieldLabel="发布时间" name="create_date" width="150" readOnly = "true" format="Y-m-d" 
				  value="%{formValue.createDt?string(\"yyyy-MM-dd\")}"/>
				 <@bravo.ComboBox name="status.id" fieldLabel="状态"  editable="false"  dataProxy="hql[from Enumeration where enumType.id=64]" displayField="name" valueField="id" value="%{formValue.status.id?c}" />

				<@bravo.TextField fieldLabel="发布人" name="last_publisher"  readOnly="true" width="150" value="%{formValue.publisher.userName}" />
				
				 <@bravo.HtmlEditor fieldLabel="内容" name="content" height="350" width = "650" /> 


			</@bravo.FieldSet>
				 <@bravo.Hidden fieldLabel="发布人" name="publisher.id"  readOnly="true" value="%{requestHandler.currentUser.id?c}" />
				<@bravo.Hidden fieldLabel="发布时间" name="createDt"  readOnly="true" value="%{requestHandler.currentDate?datetime}" />
		</@bravo.FormPanel>
	</@bravo.Viewport>
</@bravo.Page>