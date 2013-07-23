<@bravo.Page name="news_add" title="新增新闻">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="newsAddForm" region="center" border="false" width="800" tbar="toolBar"  dataProxy="./news!saveAndRendJsonData.action">  
			<@bravo.Toolbar name="toolBar" valign="bottom">
				<@bravo.Button name="Save" tooltip="'保存'" text="保存" iconCls="add" handler="ajaxSubmitForm(\\'newsAddForm\\')"/>
 
			</@bravo.Toolbar>
                        <@bravo.FieldSet border=true cols="2;1,1;2" rows="1;1,1;1" >
				<@bravo.TextField fieldLabel="标题" name="title" allowBlank="false" width="650"/>
				 
				<@bravo.ComboBox name="status.id" fieldLabel="状态"  editable="false"  dataProxy="hql[from Enumeration where enumType.id=64]" displayField="name" valueField="id" />
				 
				<@bravo.TextField fieldLabel="发布人" name="publisher.userName" readOnly="true" value="%{requestHandler.currentUser.userName}" width="165"/>
				 <@bravo.HtmlEditor fieldLabel="内容" name="content" height="350" width = "650" /> 
			</@bravo.FieldSet>
		         	 <@bravo.Hidden fieldLabel="发布时间" name="createDt"  readOnly="true" width="165" value="%{requestHandler.currentDate?datetime}"  />
		         	 <@bravo.Hidden name="publisher.id"  readOnly="true" width="165" value="%{requestHandler.currentUser.id?c}"  />
		</@bravo.FormPanel>
    </@bravo.Viewport>
</@bravo.Page>