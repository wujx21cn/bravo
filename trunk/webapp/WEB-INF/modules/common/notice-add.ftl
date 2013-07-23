<@bravo.Page name="notice_add" title="新增新闻">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="ccttvv" region="center" border="false" width="800" tbar="toolBar"  dataProxy="./notice!saveAndRendJsonData.action">  
			<@bravo.Toolbar name="toolBar" valign="bottom">
				<@bravo.Button name="Save" tooltip="'保存'" text="保存" iconCls="add" handler="ajaxSubmitForm(\\'ccttvv\\')"/>
			</@bravo.Toolbar>
                         <@bravo.FieldSet  cols="2;1,1;1,1;2" rows="1;1,1;1,1;1" >		
					<@bravo.TextField fieldLabel="标题" name="title" width="600"/>
					
					
					 
					<@bravo.DateTime fieldLabel="开始发布时间" name="publishStartDate" width="165"  value="%{requestHandler.currentDate?string(\"yyyy-MM-dd HH:mm\")}"/>
					 
					<@bravo.DateTime fieldLabel="结束发布时间" name="publishEndDate" width="165"  />
					 
					 <@bravo.ComboBox name="status.id" fieldLabel="状态"  editable="false"  dataProxy="hql[from Enumeration where enumType.id=63]" displayField="name" valueField="id" />
 
					<@bravo.TextField fieldLabel="发布人" name="publisher.userName"  readOnly="true" width="165" value="%{requestHandler.currentUser.userName}" />
					<@bravo.HtmlEditor fieldLabel="内容" name="content" width="600" height = "350" />
					
					
				</@bravo.FieldSet>
				
				<@bravo.Hidden fieldLabel="创建时间" name="createDate" value="%{requestHandler.currentDate?datetime}"  />
				<@bravo.Hidden name="publisher.id"  value="%{requestHandler.currentUser.id?trim}"  />
		         	 
		</@bravo.FormPanel>
    </@bravo.Viewport>
</@bravo.Page>