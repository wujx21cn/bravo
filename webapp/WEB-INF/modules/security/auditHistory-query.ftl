<@bravo.Page name="AuditHistoryManager" title="实体修改记录管理">
	<@bravo.Viewport  layout="border">
		    <@bravo.FormPanel name="AuditHistory_Find" tbar="Toolbar" region="north"  frame="true" autoScroll="true" height="100"  title="修改记录列表"  collapsible="true"  >  
			<@bravo.Toolbar name="Toolbar" valign="top">
				<@bravo.Button name="View" text="查询" iconCls="search" handler="searchForGrid(\\'AuditHistory_Find\\',\\'AuditHistory_Grid\\')"/>
				<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'AuditHistory_Find\\')"/>
			</@bravo.Toolbar>
				<@bravo.FieldSet  cols="1,1" rows="1,1" >		
					<@bravo.TextField fieldLabel="实体名" name="entityName" width="160"/>
					<@bravo.PopuSelect fieldLabel="更新人" fieldName="updater.userName" readOnly="true" text="人员选择"  width="160" valueField="id" displayField="userName" targetProxy="../security/user!query.action" targetGridName="user_Grid" hiddenName="updater.id" />
					
				</@bravo.FieldSet>
			 </@bravo.FormPanel>    
            <@bravo.GridPanel  region="center"   name="AuditHistory_Grid"  contextDataName="AuditHistories" stripeRows="true"  dataProxy="./auditHistory!findAndRendJsonData.action"  collapsible="true" split="true">
	        <@bravo.Column  hidden="true" name="id" header="ID" width="176"  sortable="true"  resizable="true"  />
				<@bravo.Column   name="entityName" header="实体名"  width="300"  sortable="true" resizable="true" />
			<@bravo.Column dataIndex="entityId" name="entityId" header="实体ID" width="80"  sortable="true"  resizable="true"   >
			   <@bravo.NumberField/> 
                </@bravo.Column>   

               <@bravo.Column name="updater.userName" header="更新人" width="80"  sortable="true"  resizable="true"  />       
		 <@bravo.Column  name="updateDt" header="更新时间" width="175"  sortable="true"  resizable="true" />        
		 <@bravo.Column name="view" header="查看" sortable="true" >
			         <a href=\'javascript:gridOpenNewWin(\\"./auditHistory!view.action?id=@{rowValue.id?c}\\",\\"修改记录[@{rowValue.entityName}]\\",\\"AuditHistory_Grid\\")\' style=\\"TEXT-DECORATION:none\\"><span style=color:red;>查看</span></a>
			     </@bravo.Column>			   

	    </@bravo.GridPanel>
    </@bravo.Viewport>
</@bravo.Page>
