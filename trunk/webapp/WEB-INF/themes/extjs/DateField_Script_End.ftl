    var ${component.name} = new Ext.form.DateField({
	<#if component.fieldName?exists>name:'${component.fieldName}'
    ,id:'${component.fieldName}'
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

	</#if><#if component.allowBlank?exists>,allowBlank:${component.allowBlank}
	</#if><#if component.blankText?exists>,blankText:'${component.blankText}'
	</#if><#if component.disableKeyFilter?exists>,disableKeyFilter:${component.disableKeyFilter}
	</#if><#if component.emptyClass?exists>,emptyClass:'${component.emptyClass}'
	</#if><#if component.emptyText?exists>,emptyText:'${component.emptyText}'
	</#if><#if component.grow?exists>,grow:${component.grow}
	</#if><#if component.growMax?exists>,growMax:${component.growMax}
	</#if><#if component.growMin?exists>,growMin:${component.growMin}
	</#if><#if component.maskRe?exists>,maskRe:${component.maskRe}
	</#if><#if component.maxLength?exists>,maxLength:${component.maxLength}
	</#if><#if component.maxLengthText?exists>,maxLengthText:'${component.maxLengthText}'
	</#if><#if component.minLength?exists>,minLength:${component.minLength}
	</#if><#if component.minLengthText?exists>,minLengthText:'${component.minLengthText}'
	</#if><#if component.regex?exists>,regex:${component.regex}
	</#if><#if component.regexText?exists>,regexText:'${component.regexText}'
	</#if><#if component.selectOnFocus?exists>,selectOnFocus:${component.selectOnFocus}
	</#if><#if component.validator?exists>,validator:'${component.validator}'
 
	</#if><#if component.autoCreate?exists>,autoCreate:'${component.autoCreate}'
	</#if><#if component.hideTrigger?exists>,hideTrigger:${component.hideTrigger}
	</#if><#if component.triggerClass?exists>,triggerClass:'${component.triggerClass}'
	
	</#if><#if component.altFormats?exists>,altFormats:'${component.altFormats}'
	</#if><#if component.autoCreate?exists>,autoCreate:'${component.autoCreate}'
	</#if><#if component.disabledDates?exists>,disabledDates:${component.disabledDates}
	</#if><#if component.disabledDatesText?exists>,disabledDatesText:'${component.disabledDatesText}'
	</#if><#if component.disabledDays?exists>,disabledDays:${component.disabledDays}
	</#if><#if component.disabledDaysText?exists>,disabledDaysText:'${component.disabledDaysText}'
	</#if><#if component.format?exists>,format:'${component.format}'
	</#if><#if component.invalidText?exists>,invalidText:'${component.invalidText}'
	</#if><#if component.maxText?exists>,maxText:'${component.maxText}'
	</#if><#if component.maxValue?exists>,maxValue:'${component.maxValue}'
	</#if><#if component.minText?exists>,minText:'${component.minText}'
	</#if><#if component.minValue?exists>,minValue:'${component.minValue}'
	</#if><#if component.triggerClass?exists>,triggerClass:'${component.triggerClass}'
	</#if><#if component.width?exists>,width:${component.width}
	</#if><#if component.style?exists>,style:'${component.style}'
	</#if><#if component.startDateField?exists>,startDateField:'${component.startDateField}'
	</#if><#if component.endDateField?exists>,endDateField:'${component.endDateField}'
	</#if><#if component.linkAgeField?exists>,linkAgeField:'${component.linkAgeField}'
	</#if>
	,onTriggerClick:function(){ initCalderDateField(this); }
    });