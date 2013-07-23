    var ${component.name} = new Ext.ux.ItemSelector({

	    dataFields:["${component.displayField}","${component.valueField}"],
            toData:[],
<#----
if (!this.toStore) {
        this.toStore = new Ext.data.SimpleStore({
        fields: this.dataFields,
        data : this.toData
 });
}
---->
            imagePath:"${bravoHome}/${extjsWidgetPath}/resources/images/default/multiselect-images/",
	    fromData:${component.name}_array_fromData,

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

	</#if><#if component.msWidth?exists>,msWidth:${component.msWidth}
	</#if><#if component.msHeight?exists>,msHeight:${component.msHeight}
	</#if><#if component.imagePath?exists>,imagePath:${component.imagePath}
	</#if><#if component.iconUp?exists>,iconUp:'${component.iconUp}'
	</#if><#if component.iconDown?exists>,iconDown:'${component.iconDown}'
	</#if><#if component.iconLeft?exists>,iconLeft:'${component.iconLeft}'
	</#if><#if component.iconRight?exists>,iconRight:'${component.iconRight}'
	</#if><#if component.iconTop?exists>,iconTop:'${component.iconTop}'
	</#if><#if component.iconBottom?exists>,iconBottom:'${component.iconBottom}'
	</#if><#if component.drawUpIcon?exists>,drawUpIcon:${component.drawUpIcon}
	</#if><#if component.drawDownIcon?exists>,drawDownIcon:${component.drawDownIcon}
	</#if><#if component.drawLeftIcon?exists>,drawLeftIcon:${component.drawLeftIcon}
	</#if><#if component.drawRightIcon?exists>,drawRightIcon:${component.drawRightIcon}
	</#if><#if component.drawTopIcon?exists>,drawTopIcon:${component.drawTopIcon}
	</#if><#if component.drawBotIcon?exists>,drawBotIcon:${component.drawBotIcon}
	</#if><#if component.displayField?exists>,displayField:'${component.displayField}'
	</#if><#if component.valueField?exists>,valueField:'${component.valueField}'
	</#if><#if component.switchToFrom?exists>,switchToFrom:${component.switchToFrom}
	</#if><#if component.allowDup?exists>,allowDup:${component.allowDup}
	</#if><#if component.focusClass?exists>,focusClass:${component.focusClass}
	</#if><#if component.delimiter?exists>,delimiter:'${component.delimiter}'
	</#if><#if component.toLegend?exists>,toLegend:'${component.toLegend}'
	</#if><#if component.fromLegend?exists>,fromLegend:'${component.fromLegend}'
	</#if><#if component.toSortField?exists>,toSortField:${component.toSortField}
	</#if><#if component.fromSortField?exists>,fromSortField:${component.fromSortField}
	</#if><#if component.toSortDir?exists>,toSortDir:'${component.toSortDir}'
	</#if><#if component.fromSortDir?exists>,fromSortDir:'${component.fromSortDir}'
	</#if><#if component.toTBar?exists>,toTBar:${component.toTBar}
	</#if><#if component.fromTBar?exists>,fromTBar:${component.fromTBar}
	</#if><#if component.bodyStyle?exists>,bodyStyle:${component.bodyStyle}
	</#if><#if component.border?exists>,border:${component.border}
	</#if><#if component.defaultAutoCreate?exists>,defaultAutoCreate:${component.defaultAutoCreate}
	</#if>
<#----
	, toTBar:[{
            text:"ToClear",
            handler:function(){
            var i=${component.name};
             i.reset.call(i);
             }
          }]
	 , fromTBar:[{
            text:"FromClear",
            handler:function(){
            ${component.name}.reset();
             }
          }]
---->
    });