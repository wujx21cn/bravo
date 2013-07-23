<@bravo.Page name="AuditHistoryDetailView" title="实体更新明细查询">
	<@bravo.Viewport  layout="border">
	    <@bravo.FormPanel name="auditHistoryDetailForm"  region="north" frame="true" autoScroll="true" height="185" width="700"  collapsible="true" dataProxy="./auditHistory!findAndRendJsonData.action?id=%{formValue.id?c}" >  
				<@bravo.FieldSet  cols="1,1;1,1" rows="1,1;1,1" >		
					<@bravo.TextField fieldLabel="实体名" name="entityName" width="200" />
					<@bravo.TextField fieldLabel="实体ID" name="entityId" width="200"/>
					<@bravo.TextField fieldLabel="更新人" name="updater.userName" width="200"/>
					<@bravo.TextField fieldLabel="更新时间" name="updateDt" width="200"/>
				</@bravo.FieldSet>
			 </@bravo.FormPanel>    
            <@bravo.GridPanel  region="center"   name="AuditHistoryDetail_Grid"  contextDataName="AuditHistoryDetails" dataProxy="./auditHistoryDetail!findAndRendJsonData.action?auditHistory.id=%{formValue.id?c}" title="修改记录列表" >
	        <@bravo.Column dataIndex="id" hidden="true" name="id" header="ID" width="176"  sortable="true"  resizable="true"  />
				<@bravo.Column dataIndex="fieldName" name="fieldName" header="字段名"  width="175"  sortable="true" resizable="true" >
			   <@bravo.TextField/> 
                </@bravo.Column>
			<@bravo.Column dataIndex="fieldType" name="fieldType" header="字段类型" width="175"  sortable="true"  resizable="true"   >
			   <@bravo.NumberField/> 
                </@bravo.Column>   
               <@bravo.Column dataIndex="orignalValue" name="orignalValue" header="初始值" width="175"  sortable="true"  resizable="true"  >
			   <@bravo.TextField/> 
                </@bravo.Column>        
		 <@bravo.Column dataIndex="finalValue" name="finalValue" header="改变值" width="175"  sortable="true"  resizable="true"  >
			   <@bravo.TextField/> 
                </@bravo.Column> 		
	    </@bravo.GridPanel>
    </@bravo.Viewport>
</@bravo.Page>
