<html>

<head>
<script language='JavaScript' type='text/JavaScript'>

</script>
<style type="text/css">
	.topLogo{
		border:0 none;
		background:#213D78 ;
		padding-top:0px;
		padding-left:0px;
	}
	.bottomLogo{
		border:0 none;
		background:#213D78 ;
		padding-top:0px;
		padding-left:200px;
	}
	P { text-indent:15px }
	textarea { text-indent:15px }
 </style>


<title></title>

</head>

<body bgcolor=#213D78>
<div class='topLogo'><img alt='logo' src='${bravoHome}/${extjsWidgetPath}//resources/bravo-images/logo.gif'/></div>
<p align='left'><font color=#ffffff size=2 >系统名称：${name?default("BRAVO")} <font/><P>
<p align='left'><font color=#ffffff size=2 >版  本  号：${version?default("v 1.0")}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发布日期：${releaseDate?default("2009-3-1")}<font/><P>
<textarea rows="6" cols="52" readonly=true>
  SHIT Copyright © 2009 SHIT

${comments?default("备注信息：")}
</textarea>
</body>

</html>