<@bravo.Page name="sds" title="BRAVO open platform">
	<@bravo.Viewport  layout="border">
		<@bravo.FormPanel  labelAlign= "top" region="center" dataAction="./leave!saveAjax.action"  bodyStyle="'padding:5px 5px 0'" ajaxPass="true"  frame="true"  height="900" autoScroll="true">  


			  <@bravo.Multiselect fieldLabel="复选Select框" name="multiselect" valueField="id" displayField="name" width=250 height=200 dataProxy="hql[from MenuFunction]" />	
			  
			  <@bravo.RadioGroup  fieldLabel="Radio Auto Layout">
			    <@bravo.Radio name="aa" fieldName="sex" fieldLabel="Favorite Color" boxLabel="Red" inputValue="red" checked=true />
				  <@bravo.Script>
                        aa.on("check",function dd(obj,checked){testFunc(obj,checked)});
				  </@bravo.Script>
			    <@bravo.Radio fieldName="sex" fieldLabel="" boxLabel="Bule" inputValue="bule" labelSeparator="" />
			  </@bravo.RadioGroup>	
			  
		</@bravo.FormPanel>
    </@bravo.Viewport>
</@bravo.Page>
<SCRIPT LANGUAGE="JavaScript">
<!--
	function testFunc(obj,checked){
	    alert(obj.name);
        alert(checked);
	}
//-->
</SCRIPT>
