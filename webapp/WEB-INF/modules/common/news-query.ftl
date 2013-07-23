<@bravo.Page name="newsManager" title="新闻资料管理">
	<@bravo.Viewport  layout="border">
		    <@bravo.FormPanel name="news_Find" tbar="Toolbar" region="north" dataProxy="./news!find.action"  ajaxPass="true"  frame="true" autoScroll="true" height="130" width="750"  title="新闻资料查询列表"  collapsible="true" >  
			<@bravo.Toolbar name="Toolbar" valign="top">
				<@bravo.Button name="View" text="查找" iconCls="search" handler="searchForGrid(\\'news_Find\\',\\'news_Grid\\')"/>
				<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'news_Find\\')"/>
			</@bravo.Toolbar>
				<@bravo.FieldSet  cols="1,1,1;1,2" rows="1,1,1;1,1" >		
					<@bravo.TextField fieldLabel="标题" name="title" width="160"/>
					<@bravo.TextField fieldLabel="内容" name="content" width="160" />
					<@bravo.DateTime fieldLabel="发布时间" name="createDt" width="160"/>
					 <@bravo.ComboBox name="status.id" fieldLabel="状态"  editable="false"  dataProxy="hql[from Enumeration where enumType.id=64]" displayField="name" valueField="id" width="160"/>

					<@bravo.PopuSelect fieldLabel="发布人" fieldName="publisher.userName" readOnly="true" text="人员选择"  width="160" valueField="id" displayField="userName" targetProxy="../security/user!query.action" targetGridName="user_Grid" hiddenName="publisher.id" />
					
					
				</@bravo.FieldSet>
			 </@bravo.FormPanel>    

			 <@bravo.GridPanel  region="center" tbar="Toodddlbar" name="news_Grid"  contextDataName="news" stripeRows="true"  dataProxy="./news!findAndRendJsonData.action"  collapsible="true" split="true" >
			     <@bravo.Toolbar name="Toodddlbar" valign="top" >
				 <@bravo.Button name="Add" text="新增" iconCls="add" handler="gridOpenNewWin(\\'./news!add.action\\',\\'新增新闻\\',\\'news_Grid\\')"/>
				 <@bravo.Button name="Delete" text="删除" iconCls="delete" handler="grid_doDel(\\'./news!batchRemove.action\\',\\'news_Grid\\')"/>
			     </@bravo.Toolbar>               
			     <@bravo.Column hidden="true" name="id" header="ID" width="0"  sortable="true"  resizable="true"     />
			     <@bravo.Column  name="title" header="标题"  width="175"  sortable="true" resizable="true"     />
                             <@bravo.Column name="publisher.userName" header="发布人" width="175"  sortable="true" resizable="true" /> 
                             <@bravo.Column name="createDt" header="发布时间" width="175"  sortable="true"  resizable="true"     />
                              <@bravo.Column name="status.name" header="状态" width="175"  sortable="true"  resizable="true"     />
			    
			     <@bravo.Column name="view" header="查看" sortable="true" >
			         <a href=\'javascript:gridOpenNewWin(\\"./news!view.action?id=@{rowValue.id?c}\\",\\"查看新闻[@{rowValue.title}]\\",\\"news_Grid\\")\' style=\\"TEXT-DECORATION:none\\"><span style=color:red;>查看</span></a>
			     </@bravo.Column>			   
	                </@bravo.GridPanel>
    </@bravo.Viewport>
</@bravo.Page>
