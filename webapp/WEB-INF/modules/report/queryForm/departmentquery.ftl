<@bravo.Page name="DepartmentManager" title="部门报表">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="department_find" tbar="Toolbar" region="center"  frame="true" autoScroll="true" height="185" width="750" dataProxy="../ReportServer?reportlet=department.cpt"  title="部门查询" collapsible="true"  >  
			<@bravo.Toolbar name="Toolbar" valign="top">
				<@bravo.Button name="View" text="查询" iconCls="search" handler="submit4FineReport(\\'department_find\\')"/>
				<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'department_find\\')"/>
			</@bravo.Toolbar>
			<@bravo.FieldSet  cols="1,1;1,1;" rows="1,1;1,1;" >		
				<@bravo.TextField fieldLabel="部门编码" fieldName="code" width="165"/>
				<@bravo.TextField fieldLabel="部门名称" fieldName="name" width="165"/>
				<@bravo.ComboBox fieldName="parentDepartment.id" fieldLabel="上级部门"  editable="true"  dataProxy="hql[from Department]" displayField="name" valueField="id" width="165"/>
				<@bravo.PopuSelect fieldLabel="部门经理" fieldName="_manager.id" readOnly="true" text="人员选择"  width="166" valueField="id" displayField="userName" targetProxy="../security/user!query.action" targetGridName="user_Grid" hiddenName="manager.id" />
			</@bravo.FieldSet>
			   <@bravo.Hidden fieldLabel="部门经理ID" fieldName="manager.id" readOnly="true"  width="166"  />
		</@bravo.FormPanel> 
	</@bravo.Viewport>
</@bravo.Page>
<script>
function submit4FineReport(formName){
    var formPanel = Ext.getCmp(formName); 
	if(formPanel.form.isValid()){
        var paramObj=formPanel.getForm().getValues(false);
		var formUrl = formPanel.url;
		var params = formUrl.split("?");
		if(params.length<2){
			url =  formUrl+"?";
		} else {
			url =  formUrl+"&";
		}	  
		if(!Ext.isArray(paramObj)){              
			var field, id;
			var hasNotBeenRender = true;
			var paramStr = '';
			for(id in paramObj){
				if(typeof paramObj[id] != 'function' && (field = formPanel.getForm().findField(id)) && paramObj[id] != 'undefined' && paramObj[id] != ''){
					if (hasNotBeenRender) {
						hasNotBeenRender = false;
					} else {
						paramStr = paramStr+ "&";
					}
					if(id=='parentDepartment.id'){
						paramStr = paramStr + id + "=and bravo_department.parent_dep \= " + paramObj[id];
					}else{
						paramStr = paramStr + id + "="+paramObj[id];
					}
				}
			}
		}
    url = url + cjkEncode(paramStr);
    formPanel.form.submit = Ext.emptyFn;
    formPanel.form.submit = function(){this.getEl().dom.action = url; this.getEl().dom.submit();}
    formPanel.form.submit();
	}else{
	    Ext.Msg.alert('提示', '红色波浪线标识为必填信息，请完成');
	}
}
</script>