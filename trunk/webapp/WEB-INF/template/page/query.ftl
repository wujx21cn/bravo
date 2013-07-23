<_bravo.Page name="${pageName}" title="${pageTitle}">
	<_bravo.Viewport  layout="border">
		<_bravo.FormPanel name="${formName}" tbar="formToolBar" region="center" frame="true" autoScroll="true" height="200" width="930" autoScroll="true"  title="${formTitle}"  collapsible="true" > 
			<_bravo.Toolbar name="formToolBar" valign="top">
				<_bravo.Button name="View" text="查找" iconCls="search" handler="searchForGrid(\\'${formName}\\',\\'${gridName}\\')"/>
				<_bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'${formName}\\')"/>
			</_bravo.Toolbar>
			<_bravo.FieldSet labelWidth="120" cols="${fieldSetCols}" rows="${fieldSetRows}" >
				<#list propertyList as propertyListItem>
				  <#if propertyListItem.xtype="TextField">
				      <_bravo.TextField fieldLabel="${propertyListItem.fieldLabel}" name="${propertyListItem.name}" width="165" />
				  </#if>
				  <#if propertyListItem.xtype="NumberField">
			              <_bravo.NumberField fieldLabel="${propertyListItem.fieldLabel}" name="${propertyListItem.name}" width="165" />
				  </#if>
				  <#if propertyListItem.xtype="DateField">
				      <_bravo.DateField fieldLabel="${propertyListItem.fieldLabel}_from" name="${propertyListItem.fieldLabel}_index_1" width="165"  format="Y-m-d" /> 
				      <_bravo.DateField fieldLabel="${propertyListItem.fieldLabel}_to" name="${propertyListItem.fieldLabel}_index_2" width="165"  format="Y-m-d" />	
				  </#if>
				  <#if propertyListItem.xtype="ComboBox">
				      <_bravo.ComboBox name="${propertyListItem.name}" fieldLabel="${propertyListItem.fieldLabel}"  editable="false"  dataProxy="hql[from Enumeration]" displayField="name" valueField="id" />
				  </#if>
				  <#if propertyListItem.xtype="PopuSelect">
				      <_bravo.PopuSelect fieldLabel="${propertyListItem.fieldLabel}" name="${propertyListItem.name}" readOnly="true" text="${propertyListItem.text}"  width="166" valueField="id" displayField="name" targetProxy="${propertyListItem.targetProxy}" targetGridName="${propertyListItem.targetGridName}" hiddenName="${propertyListItem.hiddenName}" />
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

		<_bravo.GridPanel  region="south" tbar="gridToolBar" split="true" height="265" name="${gridName}"  contextDataName="${contextDataName}" dataProxy="${gridDataProxy}" title="${gridTitle}" collapsible="true" >
			<_bravo.Toolbar name="gridToolBar" valign="top" >
				<_bravo.Button name="Add" text="新增" iconCls="add" handler="gridOpenNewWin(\\'${addActionUrl}\\',\\'newAddTitle\\',\\'${gridName}\\')"/>
				<_bravo.Button name="Delete" text="删除" iconCls="delete" handler="grid_doDel(\\'${removeActionUrl}\\',\\'${gridName}\\')"/>
			</_bravo.Toolbar>   
			     <_bravo.Column hidden="true" name="id" header="ID" width="0"  sortable="true"  resizable="true" />
			  <#list propertyList as propertyListItem>
			     <_bravo.Column dataIndex="${propertyListItem.fieldLabel}" name="${propertyListItem.name}" header="${propertyListItem.name}" sortable="true" resizable="true" />     
			  </#list>
			     <_bravo.Column name="view" header="查看" sortable="true" >
			         <a href=\'javascript:gridOpenNewWin(\\"${viewPageRedirect}\\",\\"view_[@{rowValue.name}]\\",\\"${gridName}\\")\' style=\\"TEXT-DECORATION:none\\"><span style=color:red;>查看</span></a>
			     </_bravo.Column>			   
		</_bravo.GridPanel>
	</_bravo.Viewport>
</_bravo.Page>
