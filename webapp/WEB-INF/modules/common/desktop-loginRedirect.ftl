<script type="text/javascript" src="${bravoHome}/${extjsWidgetPath}/adapter/ext/ext-base.js"></script> 
<script type="text/javascript" src="${bravoHome}/${extjsWidgetPath}/ext-all.js"></script>      
<SCRIPT LANGUAGE="JavaScript">
<!--
<#if _bravo_window_model?has_content>
	window.open("./desktop!view.action","","fullscreen=yes");
<#else>
	clearUserProfileCookie("JSESSIONID");
	
	<#if userCookies?has_content>
		var date=new Date();
		var expireDays=100;
		date.setTime(date.getTime()+expireDays*24*3600*1000);
		
		
		<#list userCookies as userCookieItem>
			document.cookie="${userCookieItem.name}=${userCookieItem.value?default("")}; path=/; expire="+date.toGMTString();
		</#list>
		
		refreshUserCookie();
	</#if>
	window.location="./desktop!index.action";

</#if>
	/**
	 * 把当前页面中除名为excptCookieName外的cookie删除掉
	 * Yeon   2008-12-4
	 */
	function clearUserProfileCookie(excptCookieName){
		var userProfileCookies = (document.cookie).split("; ");
		for(var i=0;i<userProfileCookies.length;i++)
		{	
			var Name = userProfileCookies[i].split("=")[0];
			if(excptCookieName != Name)
			{
				var date = new Date();
	  			date.setTime(date.getTime() - 1000*60*60*24*366);
				var washCookie = userProfileCookies[i]+"; path=/; expires="+date.toGMTString();
				document.cookie = washCookie;
			}
		}
	}
	
	/**
	 * 该函数去掉cookie值中两头的引号
	 * Yeon    2008-12-7
	 */
	function refreshUserCookie(){
		var cookies = (document.cookie).split("; ");
		if(null != cookies){
			for(i=0; i<cookies.length; i++){				
					var cookie = cookies[i].split("=");				
					if(null!=cookie[1] && cookie[1].substring(0,1) == "\""){
						cookie[1] = cookie[1].substr(1,cookie[1].length - 2);
						document.cookie = cookie[0]+"="+cookie[1]+"; path=/; expires="+(new Date(new Date().getTime()+(1000*60*60*24*7))).toGMTString();								
					}
				}
		}
	}
//-->
</SCRIPT>