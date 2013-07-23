var ${component.name}_store = new Ext.data.SimpleStore({
        fields: ["${component.displayField}", "${component.valueField}"],
	data:  ${component.name}_array_data
    });
    
 var ${component.name} = new Ext.ux.Multiselect({
	store: ${component.name}_store,
	<#if component.fieldName?exists>name:'${component.fieldName}'
	</#if><#if component.autoCreate?exists>,autoCreate:${component.autoCreate}
	</#if><#if component.clearCls?exists>,clearCls:'${component.clearCls}'
	</#if><#if component.cls?exists>,cls:'${component.cls}'
	</#if><#if component.disabled?exists>,disabled:${component.disabled}
	</#if><#if component.fieldClass?exists>,fieldClass:${component.fieldClass}
	</#if><#if component.fieldLabel?exists>,fieldLabel:'${component.fieldLabel}'
	</#if><#if component.focusClass?exists>,focusClass:'${component.focusClass}'
	</#if><#if component.hideLabel?exists>,hideLabel:${component.hideLabel}
	</#if><#if component.inputType?exists>,inputType:'${component.inputType}'
	</#if><#if component.invalidClass?exists>,invalidClass:'${component.invalidClass}'
	</#if><#if component.invalidText?exists>,invalidText:'${component.invalidText}'
	</#if><#if component.itemCls?exists>,itemCls:'${component.itemCls}'
	</#if><#if component.labelSeparator?exists>,labelSeparator:'${component.labelSeparator}'
	</#if><#if component.labelStyle?exists>,labelStyle:'${component.labelStyle}'
	</#if><#if component.msgFx?exists>,msgFx:'${component.msgFx}'
	</#if><#if component.msgTarget?exists>,msgTarget:'${component.msgTarget}'
	</#if><#if component.readOnly?exists>,readOnly:${component.readOnly}
	</#if><#if component.tabIndex?exists>,tabIndex:${component.tabIndex}
	</#if><#if component.validateOnBlur?exists>,validateOnBlur:${component.validateOnBlur}
	</#if><#if component.validationDelay?exists>,validationDelay:${component.validationDelay}
	</#if><#if component.validationEvent?exists>,validationEvent:${component.validationEvent}
	</#if><#if component.value?exists>,value:'${component.value}'

	</#if><#if component.appendOnly?exists>,appendOnly:${component.appendOnly}
	</#if><#if component.width?exists>,width:${component.width}
	</#if><#if component.height?exists>,height:${component.height}
	</#if><#if component.displayField?exists>,displayField:'${component.displayField}'
	</#if><#if component.valueField?exists>,valueField:'${component.valueField}'
	</#if><#if component.allowBlank?exists>,allowBlank:${component.allowBlank}
	</#if><#if component.minLength?exists>,minLength:${component.minLength}
	</#if><#if component.maxLength?exists>,maxLength:${component.maxLength}
	</#if><#if component.toTBar?exists>,tbar:${component.toTBar}
	</#if><#if component.blankText?exists>,blankText:'${component.blankText}'
	</#if><#if component.minLengthText?exists>,minLengthText:'${component.minLengthText}'
	</#if><#if component.maxLengthText?exists>,maxLengthText:'${component.maxLengthText}'
	</#if><#if component.delimiter?exists>,delimiter:'${component.delimiter}'
	</#if>
<#----
	,tbar:[{
            text:"clear",
            handler:function(){
	    ${component.name}.reset();
	    }
         }]
---->
    });