<@bravo.Page name="noticeView" title="消息查看" cache="false">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="noticeForm" region="center" border="false" height="180" width="790" collapsible="false" tbar="toolBar" dataProxy="./notice!saveAndRendJsonData.action?id=%{formValue.id?c}">  
			<@bravo.Toolbar name="toolBar" valign="bottom">
				<@bravo.Button name="Save" tooltip="'保存'" text="保存" iconCls="add" handler="ajaxSubmitForm(\\'noticeForm\\')"/>
			</@bravo.Toolbar>
			<@bravo.FieldSet labelWidth="79"  cols="3;1,1,1;1,2;3" rows="1;1,1,1;1,1;1" >		
				<@bravo.TextField fieldLabel="标题" name="title" width="645"/>
				<@bravo.DateField fieldLabel="开始发布时间"  name="publishStartDate" width="140" format="Y-m-d" value="%{formValue.publishStartDate?string(\"yyyy-MM-dd\")}"/>
				<@bravo.DateField fieldLabel="结束发布时间" name="publishEndDate" width="140" format="Y-m-d" value="%{formValue.publishEndDate?string(\"yyyy-MM-dd\")}"/>
				<@bravo.ComboBox name="status.id" fieldLabel="状态"  editable="false"  dataProxy="hql[from Enumeration where enumType.id=63]" displayField="name" valueField="id" width="140" value="%{formValue.status.id?c}"/>
				<@bravo.TextField fieldLabel="创建时间" name="createDate_display"  readOnly="true" width="140" value="%{formValue.createDate?string(\"yyyy-MM-dd HH:mm:ss\")}" />
				<@bravo.TextField fieldLabel="发布人" name="lastpublisher_display"  readOnly="true" width="140" value="%{formValue.publisher.userName}" />
				<@bravo.HtmlEditor fieldLabel="内容" name="content" width="645" height = "300" />
			</@bravo.FieldSet>
		</@bravo.FormPanel>
	</@bravo.Viewport>
</@bravo.Page>