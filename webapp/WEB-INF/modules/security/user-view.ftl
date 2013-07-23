<@bravo.Page name="userView" title="人员查看" cache="false">
	<@bravo.Viewport  layout="border" innerHtml="<div><img title='双击关闭图片' id='customImage' style='position:absolute;left:0;top:0;cursor:move' src='./user!showPersonPic.action?userID=%{formValue.id?c}' /></div>">
		<@bravo.FormPanel name="userForm" region="center" border="false" height="180" width="770" collapsible="false" tbar="toolBar" dataProxy="./user!saveAndRendJsonData.action?id=%{formValue.id?c}">  
			<@bravo.Toolbar name="toolBar" valign="bottom">
                                <@bravo.Button name="Save" tooltip="'保存'" text="保存" iconCls="add" handler="ajaxSubmitForm(\\'userForm\\')"/>
			        <@bravo.UploadDialogButton tooltip="'图片上传'" text="图片上传" dataProxy="./user!uploadPersonPic.action?userID=%{formValue.id?c}" uploadType="pic"/>
			</@bravo.Toolbar>
                        <@bravo.FieldSet border="false" cols="1,1,1;1,1;1,1;1,1;1,1;1,2;1,1,1;3;1,1,1;1,1,1;3" rows="1,1,6;1,1;1,1;1,1;1,1;1,1;1,1,1;1;1,1,1;1,1,1;1" >
				<@bravo.TextField fieldLabel="登陆名" name="loginid" width="165"/>
				<@bravo.TextField fieldLabel="密码" name="passwd" allowBlank="false" width="165"/>
				<@bravo.Panel fieldLabel="照片" name="personalPhoto" html="<div align=\"center\"><iframe id=\"ImageIframe\" width=\"135\" height=\"170\" frameborder=\"yes\" border=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\"  src=\"./user!redirectPersonPic.action?userID=%{formValue.id?c}\"/></div>" />

				<@bravo.ComboBox  fieldLabel="职务" name="duty.id"  editable="false"  dataProxy="hql[from Enumeration where enumType.id=3]" displayField="name" valueField="id" />
				<@bravo.TextField fieldLabel="中文名" name="userName" width="165" />

				<@bravo.TextField fieldLabel="英文名" name="engName" width="165"/>
				<@bravo.TextField fieldLabel="工号" name="labour" width="165" />
				
				<@bravo.PopuSelect fieldLabel="部门" fieldName="department.name" readOnly="true" text="部门选择"  width="166" valueField="id" displayField="name" targetProxy="../common/department!query.action" targetGridName="department_grid" hiddenName="department.id" />
				<@bravo.DateField fieldLabel="入职时间" name="accession" width="165" format="Y-m-d" value="%{formValue.accession?string(\"yyyy-MM-dd\")}"/>
				
				<@bravo.ComboBox fieldLabel="性别" name="gender.id" editable="false" dataProxy="hql[from Enumeration where enumType.id=50]" displayField="name" valueField="id" />		
				<@bravo.ComboBox fieldLabel="教育程度" name="education.id" editable="false" dataProxy="hql[from Enumeration where enumType.id=54]" displayField="name" valueField="id" />
				
				<@bravo.TextField fieldLabel="状态" name="status"   readOnly="true" width="165"/>
				<@bravo.NumberField fieldLabel="邮编" name="postalcode" width="163"/>

				<@bravo.ComboBox fieldLabel="国家" name="country.id" editable="true" dataProxy="hql[from Enumeration where enumType.id=53]" displayField="name" valueField="id"/>
				<@bravo.ComboBox fieldLabel="省份" name="province.id" editable="false" dataProxy="hql[from Enumeration where enumType.id=52]" displayField="name" valueField="id" />
				<@bravo.ComboBox fieldLabel="城市" name="city.id" editable="false" dataProxy="hql[from Enumeration where enumType.id=51]" displayField="name" valueField="id" />
				
				<@bravo.TextField fieldLabel="住址" name="address" width="652"/>
				
				<@bravo.TextField fieldLabel="固定电话" name="telephone" width="165" regex="fixPhone" />
				<@bravo.TextField fieldLabel="移动电话" name="mobilephone" width="165" regex="cellPhone"/>
				<@bravo.TextField fieldLabel="传真" name="fax" width="165" />

				<@bravo.TextField fieldLabel="电子邮箱" name="email" width="165" regex="email"/>
				<@bravo.TextField fieldLabel="更新人" name="lastUpdater_userName"  readOnly="true" width="165" value="%{formValue.lastUpdater.userName}" />
				<@bravo.TextField fieldLabel="更新时间" name="lastUpdateDate_"  readOnly="true" width="165"  value="%{formValue.lastUpdateDate?datetime}" />
				
			        <@bravo.TextArea fieldLabel="备注" name="comments" width="652" height="120"/>
			</@bravo.FieldSet>
			        <@bravo.Hidden fieldLabel="当前更新人" name="lastUpdater.id"  readOnly="true" value="%{requestHandler.currentUser.id?c}" />
				<@bravo.Hidden fieldLabel="当前更时间" name="lastUpdateDate"  readOnly="true" value="%{requestHandler.currentDate?datetime}" />
		</@bravo.FormPanel>
		<@bravo.TabPanel activeTab="0" region="south" name="tab" height="250" split="true" collapsible="true">
			<@bravo.GridPanel title="相关角色列表" tbar="userRoleToolBar" name="userRoleGrid" dataProxy="./role!findAndRendJsonData.action?users.id=%{formValue.id?c}"  contextDataName="Roles">
				<@bravo.Toolbar name="userRoleToolBar" valign="top">	
					<@bravo.M2MSelectButton text="选择添加角色" iconCls="add" targetProxy="./role!query.action" targetGridName="Role_Grid" originGridName="userRoleGrid" entityName="User" fieldName="roles" entityId="%{formValue.id?c}"/>
					<@bravo.M2MRemoveButton text="删除角色" iconCls="delete" originGridName="userRoleGrid" entityName="User" fieldName="roles" entityId="%{formValue.id?c}"/>
				</@bravo.Toolbar>			  
				<@bravo.Column  hidden="true" name="id" header="ID" width="10"  sortable="true" />
				<@bravo.Column name="name" header="名称" width="100" sortable="true" />
				<@bravo.Column name="comments" header="备注" width="400"  sortable="true"/>
			</@bravo.GridPanel>
		</@bravo.TabPanel>
	</@bravo.Viewport>
</@bravo.Page>
<script language="javascript">
function preShowImage(filePath){
 ImageIframe.location.reload(true);
}
var customEl;
var ResizableExample = {
    init : function(){
        var custom = new Ext.Resizable('customImage', {
            wrap:true,
            pinned:true,
            preserveRatio: true,
            handles: 'all',
            draggable:true,
            dynamic:true
        });
         customEl = custom.getEl();
        // move to the body to prevent overlap on my blog
        //document.body.insertBefore(customEl.dom, document.body.firstChild);
        
        customEl.on('dblclick', function(){
            customEl.hide(true);
        });
        customEl.hide();
             
    }
};

Ext.EventManager.onDocumentReady(ResizableExample.init, ResizableExample, true);

function showOrginal(){
      customEl.center();
      customEl.show(true);
}
</script>