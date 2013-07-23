<@bravo.Page name="projectView" title="生成新项目">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel name="projectForm" tbar="Toodddlbar" region="center" border="false"  frame="true" autoScroll="true" height="185" width="800"  > 
			 <@bravo.Toolbar name="Toodddlbar" valign="top" >
				 <@bravo.Button name="Add" text="生成新项目" iconCls="add" handler="generateNewApp()"/>				  
			 </@bravo.Toolbar> 
			 <@bravo.FieldSet labelWidth="80"  cols="1;" rows="1;" >	  		
				 <@bravo.TextField fieldLabel="项目名称" name="projectName" width="165"/>
			 </@bravo.FieldSet>
		</@bravo.FormPanel>
	</@bravo.Viewport>
</@bravo.Page>
<script language="javaScript">
 

function generateNewApp(){

 
 var initProjectProxy = bravoHome+'/common/initProject!generate.action';
 var nowDate = new Date();
 var fileName = 'bravo'+nowDate.getTime()+'.zip';
 var contentType = 'application/x-zip-compressed';
 
Ext.getBody().mask("项目生成中，请稍候...","x-mask-loading");
Ext.Ajax.request({            
    method : 'POST'     
   ,url: initProjectProxy
   ,params: {fileName:fileName}
   ,success: function(o) { 
	Ext.getBody().unmask();   
       downloadFile(fileName,contentType);
      }
  ,failure: function(form, action) { Ext.getBody().unmask();   Ext.Msg.alert('消息', '项目未导出到IE客户端!!!')}
});

}


</script>