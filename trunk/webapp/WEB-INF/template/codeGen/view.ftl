<^bravo.Page name="${entityName?uncap_first}_add" title="${newPageTitle}">
	<^bravo.Viewport  layout="border">
		<^bravo.FormPanel name="${entityName?uncap_first}AddForm" region="center" border="false" width="800" tbar="toolBar"  dataProxy="${newPageDataproxy}">  
			<^bravo.Toolbar name="toolBar" valign="bottom">
				<^bravo.Button name="Save" tooltip="'保存'" text="保存" iconCls="add" handler="ajaxSubmitForm(\\'${entityName?uncap_first}AddForm\\')"/>
			</^bravo.Toolbar>
                         <^bravo.FieldSet cols="<#list newPageGenDomains as newPageGenDomain>1<#if newPageGenDomain_has_next>;</#if></#list>" rows="<#list newPageGenDomains as newPageGenDomain>1<#if newPageGenDomain_has_next>;</#if></#list>" >		
					<#list newPageGenDomains as newPageGenDomain>
					<#if newPageGenDomain.fieldType == "ComboBox">
					<^bravo.ComboBox fieldLabel="${newPageGenDomain.labelName}" name="${newPageGenDomain.fieldM2nRefField}" allowBlank="false" editable="false" dataProxy="${newPageGenDomain.fieldM2nDataproxy}" displayField="${newPageGenDomain.fieldM2nRefDisplay}" valueField="${newPageGenDomain.fieldM2nRefValue}" />
					<#else>
					<^bravo.${newPageGenDomain.fieldType} fieldLabel="${newPageGenDomain.labelName}" name="${newPageGenDomain.fieldName}" width="165"/>
					</#if>
					</#list>
			</^bravo.FieldSet>
		</^bravo.FormPanel>
    </^bravo.Viewport>
</^bravo.Page>