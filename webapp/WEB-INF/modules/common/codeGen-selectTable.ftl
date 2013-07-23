<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>bravo代码生成器</title>
</head>

<body>
<p>---------------------------------------------选择数据库表----------------------------------------</p>
<p><form action="./codeGen!genConfig.action" method="post">
  <label>包名
  <input type="text" name="packageName" value="com.cutty"/>
  </label>
  <label>模块名
  <input type="text" name="moduleName" value="XXXX"/>
  </label>
  <label>源代码位置
  <input type="text" name="srcFolder" value="${srcFolder?default("")}" size="35"/>
  </label>
  <label>数据库表  </label>
  <select name="tableName">
	<#list tableNames as tableName>
		<option value="${tableName}">${tableName}</option>
	</#list>
  </select>
   <input name="submit" type="submit" value="提交"/>
</form> </p>
<p>&nbsp;</p>
</body>
</html>
