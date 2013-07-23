<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">       
    <link rel="stylesheet" type="text/css" href="${bravoHome}/${extjsWidgetPath}/extjs-bravo.css" /> 
</head>
	<body topmargin="5" leftmargin="5" rightmargin="0">
		<div>
			<ol class="topList_01">
				<#if noticeList?has_content> 
				  <#list noticeList as noticeListItem>
					<#if noticeListItem.title?length gt 15>
					  <#assign shortTitle = noticeListItem.title?substring(0,15) + '...'>
					<#else>
					  <#assign shortTitle = noticeListItem.title>
					</#if>
					<li>
					   <label class="No${noticeListItem_index+1}"></label>
					   <a href="javascript:newsOpenNewWin('${noticeListItem.id?c}','../common/notice!viewDetailNotice.action?id=${noticeListItem.id?c}','${noticeListItem.title}')" title="${noticeListItem.title}">
						 ${shortTitle}
					   </a>
					</li>
				  </#list>
				</#if>
			</ol>
		</div>
	</body>
</html>