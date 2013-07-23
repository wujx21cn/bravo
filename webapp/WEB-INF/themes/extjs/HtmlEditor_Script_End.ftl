 var ${component.name} = new Ext.form.HtmlEditor({
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
	
         
	 
	 </#if><#if component.createLinkText?exists>,createLinkText:'${component.createLinkText}'
	 </#if><#if component.defaultLinkValue?exists>,defaultLinkValue:'${component.defaultLinkValue}'
	 </#if><#if component.enableAlignments?exists>,enableAlignments:'${component.enableAlignments}
	 </#if><#if component.enableColors?exists>,enableColors:${component.enableColors}
	 </#if><#if component.enableFont?exists>,enableFont:${component.enableFont}
	 </#if><#if component.enableFontSize?exists>,enableFontSize:${component.enableFontSize}
	 </#if><#if component.enableFormat?exists>,enableFormat:${component.enableFormat}
	 </#if><#if component.enableLinks?exists>,enableLinks:${component.enableLinks}
	 </#if><#if component.enableLists?exists>,enableLists:${component.enableLists}
	 </#if><#if component.enableSourceEdit?exists>,enableSourceEdit:${component.enableSourceEdit}
	 </#if><#if component.fontFamilies?exists>,fontFamilies:${component.fontFamilies}
	 </#if><#if component.width?exists>,width:${component.width}
	 </#if><#if component.height?exists>,height:${component.height}
	</#if>

    });