<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">       
    <link rel="stylesheet" type="text/css" href="${bravoHome}/${extjsWidgetPath}/extjs-bravo.css" /> 
</head>
	<body topmargin="5" leftmargin="0" rightmargin="0">
		<div>
			<ol class="topList_01">
				<#if newsList?has_content> 
				  <#list newsList as newsListItem>
					<#if newsListItem.title?length gt 15>
					  <#assign shortTitle = newsListItem.title?substring(0,15) + '...'>
					<#else>
					  <#assign shortTitle = newsListItem.title>
					</#if>
					<li>
					   <label class="No${newsListItem_index+1}"></label>
					   <a href="javascript:newsOpenNewWin('${newsListItem.id?c}','../common/news!viewDetailNews.action?id=${newsListItem.id?c}','${newsListItem.title}')" title="${newsListItem.title}">
						 ${shortTitle}
					   </a>
					</li>
				  </#list>
				</#if>
			</ol>
		</div>
	</body>
</html>