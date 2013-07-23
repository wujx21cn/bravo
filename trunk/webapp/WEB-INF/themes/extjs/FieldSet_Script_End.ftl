 var ${component.name} = [
		<#list childrenComponentNames as childrenComponentNamesItem>
			<#if childrenComponentNamesItem_index != 0>,</#if>
			<#assign colspan = component.colDefs[childrenComponentNamesItem_index]> 
			<#assign rowspan = component.rowDefs[childrenComponentNamesItem_index]> 
			<#assign colWidth = component.colWidthDefs[childrenComponentNamesItem_index]> 

			{
				<#if component.labelWidth?exists>labelWidth:${component.labelWidth},</#if>
				layout:'${component.layout}',
				<#if component.layoutConfig?exists>,layoutConfig:${component.layoutConfig},</#if>
				colspan: ${colspan},
				rowspan: ${rowspan},
				width: ${colWidth}, 
				style:'margin-top: 5px;padding:0px 0px 0px 0px',
				items: [${childrenComponentNamesItem}]
			}
		</#list>
		];