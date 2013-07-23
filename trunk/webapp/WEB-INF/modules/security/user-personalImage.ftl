<html>
<script language="javascript">
parent.customImage.src = "./user!showPersonPic.action?" + Math.random() +"&userID="+${userID.toString()};
function showParentOrginal(){
      parent.showOrginal();
}
</script>
 <body>
   <div align="center" style="background-color:blue"><img title="单击显示原始图片" onclick="showParentOrginal()" id="personalImage"  src="./user!showPersonPic.action?userID=${userID.toString()}" width="135" height="170"/><div>
 </body>
</html>