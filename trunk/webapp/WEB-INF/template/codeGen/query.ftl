<^bravo.Page name="${entityName?uncap_first}Manager" title="${newFieldListTitle}">
	<^bravo.Viewport  layout="border">
		    <^bravo.FormPanel name="${entityName?uncap_first}QueryForm" tbar="Toolbar" region="north" dataProxy="./${entityName?uncap_first}!find.action"  ajaxPass="true"  frame="true" autoScroll="true" height="160" width="800"  title="${newFieldListTitle}查询列表"  collapsible="true" >  
			<^bravo.Toolbar name="Toolbar" valign="top">
				<^bravo.Button name="View" text="查找" iconCls="search" handler="searchForGrid(\\'${entityName?uncap_first}QueryForm\\',\\'${entityName?uncap_first}Grid\\')"/>
				<^bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'${entityName?uncap_first}QueryForm\\')"/>
			</^bravo.Toolbar>
				<^bravo.FieldSet labelWidth="80"  cols="<#list listQueryPageGenDomains as listQueryPageGenDomain>1<#if listQueryPageGenDomain_has_next>;</#if></#list>" rows="<#list listQueryPageGenDomains as listQueryPageGenDomain>1<#if listQueryPageGenDomain_has_next>;</#if></#list>" >		
					<#list listQueryPageGenDomains as listQueryPageGenDomain>
					<#if listQueryPageGenDomain.fieldType == "ComboBox">
					<^bravo.ComboBox fieldLabel="${listQueryPageGenDomain.labelName}" name="${listQueryPageGenDomain.fieldM2nRefField}" allowBlank="false" editable="false" dataProxy="${listQueryPageGenDomain.fieldM2nDataproxy}" displayField="${listQueryPageGenDomain.fieldM2nRefDisplay}" valueField="${listQueryPageGenDomain.fieldM2nRefValue}" />
					<#else>
					<^bravo.${listQueryPageGenDomain.fieldType} fieldLabel="${listQueryPageGenDomain.labelName}" name="${listQueryPageGenDomain.fieldName}" width="165"/>
					</#if>
					</#list>
				</^bravo.FieldSet>
			 </^bravo.FormPanel>    

			 <^bravo.GridPanel  region="center" tbar="Toodddlbar" name="${entityName?uncap_first}Grid"  contextDataName="${entityName?uncap_first}s"   dataProxy="./${entityName?uncap_first}!findAndRendJsonData.action"  collapsible="true" split="true" stripeRows="true" >
			     <^bravo.Toolbar name="Toodddlbar" valign="top" >
				 <^bravo.Button name="Add" text="新增" iconCls="add" handler="gridOpenNewWin(\\'./${entityName?uncap_first}!add.action\\',\\'新增\\',\\'${entityName?uncap_first}Grid\\')"/>
				 <^bravo.Button name="Delete" text="删除" iconCls="delete" handler="grid_doDel(\\'./${entityName?uncap_first}!batchRemove.action\\',\\'${entityName?uncap_first}Grid\\')"/>
			     </^bravo.Toolbar>               
			     <^bravo.Column hidden="true" name="id" header="ID" width="0"  sortable="true"  resizable="true"     />
			    	<#list eneityFieldListGenDomains as eneityFieldListGenDomain>
				    <^bravo.Column  name="${eneityFieldListGenDomain.fieldName}" header="${eneityFieldListGenDomain.labelName}"  width="175"  sortable="true" resizable="true" />
				</#list>
				<^bravo.Column name="view" header="查看" sortable="true" >
			         <a href=\'javascript:gridOpenNewWin(\\"./${entityName?uncap_first}!view.action?id=@{rowValue.id?c}\\",\\"查看${entityName?uncap_first}[@{rowValue.id}]\\",\\"${entityName?uncap_first}Grid\\")\' style=\\"TEXT-DECORATION:none\\"><span style=color:red;>查看</span></a>
			     </^bravo.Column>			   
	                </^bravo.GridPanel>
    </^bravo.Viewport>
</^bravo.Page>
