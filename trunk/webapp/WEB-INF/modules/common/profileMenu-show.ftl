<html>
	<head>
	</head>
	<body>
		<span></span>
		<ul style="margin-left:13px" class="profile-panel-body">
			<#if profileMenus?has_content>
				<#list profileMenus as profileMenuItem>
					<li>
						<img src="${bravoHome}/${extjsWidgetPath}/resources/bravo-images/shortcut-images/${profileMenuItem.icon}"/>
						<font size=2>
							<a href="${profileMenuItem.menuFunction.action}">${profileMenuItem.menuFunction.name}</a>
						</font>
					</li>
				</#list>
			</#if>
		</ul>
	</body>
</html>
