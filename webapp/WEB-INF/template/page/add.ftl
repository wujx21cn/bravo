<_bravo.Page name="${pageName}" title="${pageTitle}">
	<_bravo.Viewport  layout="border">
		<_bravo.FormPanel name="${formName}" tbar="formToolBar" region="center" height="500" dataProxy="${saveActionUrl}" frame="true" autoScroll="true" width="900"  autoScroll="true"  title="${formTitle}"  collapsible="true" > 
			<_bravo.Toolbar name="formToolBar" valign="top">
			  <_bravo.Button  name="Save" text="保存" iconCls="add" handler="submitForm(\\'${formName}\\')"/>
				</_bravo.Toolbar>
				<_bravo.FieldSet labelWidth="120" cols="${fieldSetCols}" rows="${fieldSetRows}" >
				<#list propertyList as propertyListItem>
				<#if propertyListItem.xtype="TextField">
				   <_bravo.TextField fieldLabel="${propertyListItem.fieldLabel}" name="${propertyListItem.name}" width="140" />
				</#if>
				<#if propertyListItem.xtype="NumberField">
				   <_bravo.NumberField fieldLabel="${propertyListItem.fieldLabel}" name="${propertyListItem.name}" width="140" />
				</#if>
				<#if propertyListItem.xtype="DateField">
				   <_bravo.DateField fieldLabel="${propertyListItem.fieldLabel}_from" name="${propertyListItem.fieldLabel}_index_1" width="120"  format="Y-m-d" /> 
				   <_bravo.DateField fieldLabel="${propertyListItem.fieldLabel}_to" name="${propertyListItem.fieldLabel}_index_2" width="120"  format="Y-m-d" />	
				</#if>
				<#if propertyListItem.xtype="ComboBox">
				   <_bravo.ComboBox name="${propertyListItem.name}" fieldLabel="${propertyListItem.fieldLabel}"  editable="false"  dataProxy="hql[from Enumeration]" displayField="name" valueField="id" width="130" />
				</#if>
				<#if propertyListItem.xtype="PopuSelect">
				      <_bravo.PopuSelect fieldLabel="${propertyListItem.fieldLabel}" name="${propertyListItem.name}" readOnly="true" text="${propertyListItem.text}"  width="130" valueField="id" displayField="name" targetProxy="${propertyListItem.targetProxy}" targetGridName="${propertyListItem.targetGridName}" hiddenName="${propertyListItem.hiddenName}" />
				</#if>
				</#list>
			</_bravo.FieldSet>
		         <#list propertyList as propertyListItem>
				  <#if propertyListItem.xtype="DateField">
				     <_bravo.Hidden name="${propertyListItem.fieldLabel}_index_1_restriction" value="ge" /> 
				     <_bravo.Hidden name="${propertyListItem.fieldLabel}_index_2_restriction" value="le" />	
				  </#if>
				</#list>
		</_bravo.FormPanel>  		
	</_bravo.Viewport>
</_bravo.Page>
