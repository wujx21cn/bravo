<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>bravo 登陆界面</title>
</head>
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/jquery.drag.js"></script>
<script>
var initBg = 'default-bg.jpg';
var initFace = 'demoface.jpg';
$(function(){
	document.body.style.backgroundImage  = 'url(../images/'+ initBg +')'
	document.getElementById('loginface').style.backgroundImage  = 'url(../images/'+ initFace +')'
	//
	var winW = document.body.offsetWidth;
	var winH = document.all ? document.body.offsetHeight : window.innerHeight;
	//alert(winW+'|'+winH)
	$('#loginbox')
		.css('top',((winH-311)/2-50)+'px')
		.css('left',(winW-457)/2+'px')
        .bind('drag',function( event ){
                $( this ).css({
                        top: event.offsetY,
                        left: event.offsetX
                        });
                }); 
})
</script>
<style>
body{
	margin:0px;
}

.login-box{
	background-image:url(../images/login_bg.png) !important;
	background-repeat:no-repeat;
	width:457px;
	height:311px;
	position:absolute;
	/*filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src="../images/login_bg.png");
	background:none;*/		
}

.login-box td{
	font-size:12px;
}

.login-box .login-table{
	width:380px;
	margin-top:105px;
}

.login-box .login-face{
	width:131px;
	height:140px;
	background:url(../images/login-headbg.png) 0 6px no-repeat;
	*filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src="../images/login-headbg.png");
	*background:none;		
}

	.login-box .login-face .login-faceimg{
		width:92px;
		height:92px;
		background-repeat:no-repeat;
		margin:0 19px;
	}

.login-box .login-form{
	width:252px;
}

	.login-box .login-form table{
		width:95%;
		margin-top:15px;
	}
	
	.login-box .login-form input{
		width:200px;
	}

.login-box button{
	width:94px;
	height:27px;
	line-height:27px;
	*line-height:25px;
	text-decoration:none;
	color:#000000;
}
.login-button input{
	width:94px;
	height:27px;
	line-height:27px;
	*line-height:25px;
	text-decoration:none;
	color:#000000;
}
</style>
<body onload='document.f.j_username.focus();'>
<div>
<div class="login-box" id="loginbox">
<form name='f' action='../j_spring_security_check' method='POST'>
<table border="0" align="center" cellpadding="0" cellspacing="0" class="login-table">
  <tr>
    <td class="login-face">
    	<div class="login-faceimg" id="loginface"></div>
    </td>

    <td valign="top" class="login-form"><table border="0" align="right" cellpadding="0" cellspacing="5">
      <tr>
        <td>用户名</td>
      </tr>
      <tr>
        <td><!--input  type="text" name='LOGIN_USER_SAAS_GROUP' size="1"/--><input  type="text" name='j_username' /></td>
      </tr>
      <tr>
        <td>密码</td>
      </tr>
      <tr>
        <td><input  type="password" name='j_password' /></td>
      </tr>
	   <tr>
        <td valign="middle"><input type='checkbox' style="width:25px"  name='_bravo_window_model'/><label for="_spring_security_remember_me" accesskey="r">WINDOWS全屏模式</label>
		</td>
      </tr>
    </table></td>
  </tr>
  <tr>
   <td align="right"></td>
    <td class="login-button" align="left">
	  <input name="submit" value="登陆" type="submit" style="margin-left:15px"/>
	  <input name="reset" value="重置" type="reset"  style="margin-left:10px"/>
	</td>
    </tr>
</table></form></div>
</div>
</body>
</html>
