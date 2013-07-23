<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>bravo代码生成器</title>
<SCRIPT LANGUAGE="JavaScript">
<!--
	function onFieldTypeChange(obj,fieldIndex){
	var entityDivId = "mul_div_" + fieldIndex;
	   var divObj=document.getElementById(entityDivId);
	   if (obj.value=="ComboBox"){
		divObj.style.display="block";
	   } else {
		divObj.style.display="none";
	   }
	}
	function onEntityFieldChange(obj,fieldIndex){
          var entityDivId = "entity_div_" + fieldIndex;
	   var divObj=document.getElementById(entityDivId);
	   if (obj.value=="M2O"){
		divObj.style.display="block";
	   } else {
		divObj.style.display="none";
	   }
	}

	function onEntityFieldRefChange(obj,fieldIndex) {
	   var entityFieldId = "entity_field_ref_id_" + fieldIndex;
	   var fieldObj=document.getElementById(entityFieldId);
	   fieldObj.value=obj.value;
	}
//-->
</SCRIPT>
</head>

<body>
<p>---------------------------------------------代码生成设置----------------------------------------</p>
<p><form action="./codeGen!genSourceCode.action" method="post">
  <label>java代码路径
  <input type="text" name="sourcePath" value="${sourcePath}"/>
  </label>
  <label>页面代码路径
  <input type="text" name="modulePath" value="${modulePath}"/>
  </label>
  <label>模块名
  <input type="text" name="moduleName" value="${moduleName}"/>
  </label>
<p>---------------------------------------------对应java代码设置----------------------------------------</p>
  </label>实体名<label>
  <input type="text" name="entityName" value="${entityName}"/>
  </label>实体类路径<label>
  <input type="text" name="entityPath" value="${entityPath}" size="40"/>
   </label>数据库表名<label>
  <input type="text" name="tableName" value="${tableName}" size="40"/></br>
  </label>ACTION名<label>
  <input type="text" name="actionnName" value="${actionnName}"/>
  </label>ACTION类路径<label>
  <input type="text" name="actionnPath" value="${actionnPath}" size="40"/>  </br>
  </label>Manager名<label>
  <input type="text" name="managerName" value="${managerName}"/>
  </label>Manager类路径<label>
  <input type="text" name="managerPath" value="${managerPath}" size="40"/>  </br></br>
实体字段设置---------------------------------------</br>
 <#list tableColumns as tableColumn>
 <input type="checkbox" name="eneity_field_need_${tableColumn_index+1}" value="checkbox" checked/>
 <label> 实体字段名</label>
  <input type="text" name="eneity_field_name_${tableColumn_index+1}" value="${tableColumn.fieldName?default("")}"/>
  <label> 表字段名</label>
  <input type="text" name="eneity_column_name_${tableColumn_index+1}" value="${tableColumn.columnName?default("")}"/>
  <label> 字段类型</label>
  <select id="entity_field_type_id_${tableColumn_index+1}" name="entity_field_type_${tableColumn_index+1}" onchange="onEntityFieldChange(this,${tableColumn_index+1})">
		<option value="DATE">DATE</option>
		<option value="STRING">STRING</option>
		<option value="M2O">ManyToOne</option>
		<option value="INT">INT</option>
		<option value="DOUBLE">DOUBLE</option>
		<option value="FLOAT">FLOAT</option>

  </select>
  <div id="entity_div_${tableColumn_index+1}" style="display:none;width:800px;height:45px;border-style:solid;border-
color:red;border-width:1px">
  </label>对应实体名<label>
  <select name="entity_field_ref_sel_${tableColumn_index+1}"  onchange="onEntityFieldRefChange(this,${tableColumn_index+1})">
  		<option value="">请选择关联实体</option>
	<#list entityNameSet as entityNameSetItem>
		<option value="${entityNameSetItem}">${entityNameSetItem}</option>
	</#list>
  </select>
  <input type="text"  id="entity_field_ref_id_${tableColumn_index+1}" name="entity_field_ref_${tableColumn_index+1}" size="35"/>
  </br></label>关联字段名<label>
  <input type="text" name="eneity_column_join_name_${tableColumn_index+1}" value="${tableColumn.columnName?default("")}"/>
   </label>关联字段名<label>
  <input type="text" name="eneity_column_ref_name_${tableColumn_index+1}" value="id"/>
</div>
  <SCRIPT LANGUAGE="JavaScript">
	document.getElementById('entity_field_type_id_${tableColumn_index+1}').value="${tableColumn.fieldType?default("STRING")}";
         if ("${tableColumn.fieldType?default("STRING")}" == "M2O" ){
		var divObjccc=document.getElementById('entity_div_${tableColumn_index+1}');
		divObjccc.style.display="block";
	 }
  </SCRIPT>
</br>
</#list>
<p>---------------------------------------------新增表单设置|||是否生成<input type="checkbox" name="new_add_need" value="checkbox" checked/>----------------------------------------</p>
    </label>数据源<label>
  <input type="text" name="new_page_dataproxy" value="./${entityName?uncap_first}!saveAndRendJsonData.action"  size="50"/>
      </label>页面标题<label>
  <input type="text" name="new_page_title" value="新增${entityName}"  size="20"/></br>
 <#list tableColumns as tableColumn>
  <input type="checkbox" name="new_field_need_${tableColumn_index+1}" value="checkbox" checked/>
  <label> 实体字段名</label>
  <input type="text" name="new_field_name_${tableColumn_index+1}" value="${tableColumn.fieldName?default("")}"/>
  <label>标签名</label>
  <input type="text" name="new_label_name_${tableColumn_index+1}" value="${tableColumn.labelName?default("")}"/>
  <label>页面控件</label>
  <select id="new_field_type_id_${tableColumn_index+1}" name="new_field_type_${tableColumn_index+1}" onchange="onFieldTypeChange(this,${tableColumn_index+1})">
		<option value="TextField">TextField</option>
		<option value="ComboBox">ComboBox</option>
		<option value="DateTime">DateTime</option>
		<option value="NumberField">NumberField</option>
		<option value="TextArea">TextArea</option>
		<option value="Hidden">Hidden</option>
		<option value="HtmlEditor">HtmlEditor</option>
  </select>
  <div id="mul_div_${tableColumn_index+1}" style="display:none;width:800px;height:45px;border-style:solid;border-
color:red;border-width:1px">
    </label>关联字段名<label>
  <input type="text" name="new_field_m2n_ref_field_${tableColumn_index+1}" value="${tableColumn.fieldName?default("")}.id"/>
    </label>dataProxy<label>
  <input type="text" name="new_field_m2n_dataproxy_${tableColumn_index+1}" value="hql[from Enumeration]"/></br>
    </label>显示字段<label>
  <input type="text" name="new_field_m2n_ref_display_${tableColumn_index+1}" value="id"/>
    </label>值字段<label>
  <input type="text" name="new_field_m2n_ref_value_${tableColumn_index+1}" value="id"/>
</div>
  <SCRIPT LANGUAGE="JavaScript">
        if ("${tableColumn.fieldType?default("STRING")}" == "M2O") {
		document.getElementById('new_field_type_id_${tableColumn_index+1}').value="ComboBox";
		var divmulObj=document.getElementById('mul_div_${tableColumn_index+1}');
		divmulObj.style.display="block";
	}
	if ("${tableColumn.fieldType?default("STRING")}" == "DATE") {
		document.getElementById('new_field_type_id_${tableColumn_index+1}').value="DateTime";
	}
	
	if ("${tableColumn.fieldType?default("STRING")}" == "INT" || "${tableColumn.fieldType?default("STRING")}" == "DOUBLE"
			|| "${tableColumn.fieldType?default("STRING")}" == "FLOAT") {
		document.getElementById('new_field_type_id_${tableColumn_index+1}').value="NumberField";
	}
  </SCRIPT>
</br>
</#list>
<p>------------------------查看表单设置(与新增页面相同)|||是否生成<input type="checkbox" name="view_add_need" value="checkbox" checked/>------------------------------------</p>
<p>---------------------------------------------查询列表设置|||是否生成<input type="checkbox" name="list_add_need" value="checkbox" checked/>---------------------------------------------</p>
    </label>数据源<label>
  <input type="text" name="new_field_list_dataproxy" value="./${entityName}!findAndRendJsonData.action" size="50"/>
     </label>页面标题<label>
  <input type="text" name="new_field_list_title" value="${entityName}管理" size="39"/></br>
 <#list tableColumns as tableColumn>
 查询<input type="checkbox" name="eneity_field_query_need_${tableColumn_index+1}" value="checkbox"/>
 显示 <input type="checkbox" name="eneity_field_list_need_${tableColumn_index+1}" value="checkbox"/>
 <label> 实体字段名</label>
  <input type="text" name="eneity_field_list_name_${tableColumn_index+1}" value="${tableColumn.fieldName?default("")}"/>
    </label>显示字段<label>
  <input type="text" name="eneity_list_display_${tableColumn_index+1}" value="${tableColumn.labelName?default("")}"/></br>
</#list>
   <input name="submit" type="submit" value="提交"/>
</form> </p>
<p>&nbsp;</p>
</body>
</html>
