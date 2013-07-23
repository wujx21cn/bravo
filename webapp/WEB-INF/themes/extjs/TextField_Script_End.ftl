    var ${component.name} = new Ext.form.TextField({
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
	</#if><#if component.style?exists>,style:'${component.style}'
	</#if><#if component.msgFx?exists>,msgFx:'${component.msgFx}'
	</#if><#if component.msgTarget?exists>,msgTarget:'${component.msgTarget}'
	</#if><#if component.readOnly?exists>,readOnly:${component.readOnly}
	</#if><#if component.tabIndex?exists>,tabIndex:${component.tabIndex}
	</#if><#if component.validateOnBlur?exists>,validateOnBlur:${component.validateOnBlur}
	</#if><#if component.validationDelay?exists>,validationDelay:${component.validationDelay}
	</#if><#if component.validationEvent?exists>,validationEvent:${component.validationEvent}
	</#if><#if component.value?exists>,value:'${component.value}'
	</#if><#if component.width?exists>,width:${component.width}
	</#if><#if component.height?exists>,height:${component.height}

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
	</#if><#if component.selectOnFocus?exists>,selectOnFocus:${component.selectOnFocus}
	</#if><#if component.validator?exists>,validator:'${component.validator}'
	</#if><#if component.vtype?exists>,vtype:'${component.vtype}'
	</#if><#if component.vtypeText?exists>,vtypeText:'${component.vtypeText}'
	</#if>
	<#if component.regex?exists &&  component.regex == "email">
          ,regex:/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/
	  ,regexText:'请输入正确的邮箱格式'
	<#elseif component.regex?exists &&  component.regex == "fixPhone">
          ,regex:/^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$/
	  ,regexText:'请输入正确的固定电话号码格式'
	<#elseif component.regex?exists &&  component.regex == "cellPhone">
          ,regex:/^\d+$/
	  ,regexText:'请输入正确的手机号码格式'
        <#else> 
	  <#if component.regex?exists>,regex:${component.regex}</#if>
	  <#if component.regexText?exists>,regexText:'${component.regexText}'</#if>
	  <#if component.enableKeyEvents?exists>,enableKeyEvents:${component.enableKeyEvents}</#if>
	</#if>

    });