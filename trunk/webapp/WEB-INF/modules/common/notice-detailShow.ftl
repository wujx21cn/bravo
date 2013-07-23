<HTML>
 <HEAD>
  <TITLE> 浏览公告</TITLE>
<style type="text/css">
/* 主容器 */
.blkContainer{float:left; width:body.width; overflow:hidden;margin:0;padding:0;border:0;}
/* 正文块 */
.blkContainerPblk{padding:20px 0 0; overflow:hidden; zoom:1; margin:0 1px;background:#f5f8fd;}
.blkContainerSblk{padding:0 34px 20px; overflow:hidden; zoom:1; width:580px;}

/* 正文标题 */
.blkContainerSblk h1{height:35px; line-height:35px; overflow:hidden; text-align:center; font-family:"黑体"; font-size:20px; font-weight:normal; color:#03005C;border-bottom:1px #c8d8f2 solid;margin:5;}

.artInfo{padding-top:2px; overflow:hidden; line-height:14px; text-align:center; font-size:12px; font-family:Arial, Helvetica, sans-serif;margin:5;}
.artInfo a,.artInfo a:visited{text-decoration:none;color:#c00;}
.artInfo a:hover,.artInfo a:active{text-decoration:underline;color:#f00;}

/* 正文内容 */
.blkContainerSblkCon{margin-top:15px; line-height:164.28%; font-size:14px;}
</style>
</HEAD>

 <BODY style="background:#f5f8fd">
 	<div align="center" class="blkContainer">
		<!-- 正文块 begin -->
		<div class="blkContainerPblk">
			<div class="blkContainerSblk">
				<h1 id="artibodyTitle">${title?default("")}</h1>
				<div class="artInfo"><span>${createDate?default("")}</span>&nbsp;&nbsp;<span>   
				<#if publisher?exists>${publisher.userName}</#if></span></div>
				<div align="left" class="blkContainerSblkCon">
					<p>&nbsp;&nbsp${content?default("")}</p>
				</div>
				<br>
			</div>
		</div>
	</div>
</div>
</BODY>
</HTML>
