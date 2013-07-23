<@bravo.Page name="noticeManager" title="公告资料管理">
	<@bravo.Viewport  layout="border">
		    <@bravo.FormPanel name="notice_Find" tbar="Toolbar" region="north" dataProxy="./notice!find.action"  ajaxPass="true"  frame="true" autoScroll="true" height="160" width="800"  title="公告资料查询列表"  collapsible="true" >  
			<@bravo.Toolbar name="Toolbar" valign="top">
				<@bravo.Button name="View" text="查找" iconCls="search" handler="searchForGrid(\\'notice_Find\\',\\'notice_Grid\\')"/>
				<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'notice_Find\\')"/>
			</@bravo.Toolbar>
				<@bravo.FieldSet labelWidth="80"  cols="1,1,1;1,1,1;3" rows="1,1,1;1,1,1;1" >		
					<@bravo.TextField fieldLabel="标题" name="title" width="160"/>
					<@bravo.TextField fieldLabel="内容" name="content" width="160" />
					<@bravo.DateTime fieldLabel="创建时间" name="createDt" width="160" />
					 
					<@bravo.DateTime fieldLabel="开始发布时间" name="publishStartDate" endDateField="publishEndDate"  linkAgeField="publishEndDate" width="160" />
					 
					<@bravo.DateTime fieldLabel="结束发布时间" name="publishEndDate" startDateField="publishStartDate" width="160" />
					 
					 <@bravo.ComboBox name="status.id" fieldLabel="状态"  editable="false"  dataProxy="hql[from Enumeration where enumType.id=63]" displayField="name" valueField="id"  width="160" />

					<@bravo.PopuSelect fieldLabel="发布人" fieldName="publisher.userName" readOnly="true" text="人员选择"  width="160" valueField="id" displayField="userName" targetProxy="../security/user!query.action" targetGridName="user_Grid" hiddenName="publisher.id" />
					
					
				</@bravo.FieldSet>
			 </@bravo.FormPanel>    

			 <@bravo.GridPanel  region="center" tbar="Toodddlbar" name="notice_Grid"  contextDataName="notice"   dataProxy="./notice!findAndRendJsonData.action"  collapsible="true" split="true" stripeRows="true" >
			     <@bravo.Toolbar name="Toodddlbar" valign="top" >
				 <@bravo.Button name="Add" text="新增" iconCls="add" handler="gridOpenNewWin(\\'./notice!add.action\\',\\'新增公告\\',\\'notice_Grid\\')"/>
				 <@bravo.Button name="Delete" text="删除" iconCls="delete" handler="grid_doDel(\\'./notice!batchRemove.action\\',\\'notice_Grid\\')"/>
			     </@bravo.Toolbar>               
			     <@bravo.Column hidden="true" name="id" header="ID" width="0"  sortable="true"  resizable="true"     />
			     <@bravo.Column  name="title" header="标题"  width="175"  sortable="true" resizable="true"     />
                             <@bravo.Column name="publisher.userName" header="发布人" width="175"  sortable="true" resizable="true" />
                              <@bravo.Column name="status.name" header="状态" width="175"  sortable="true"  resizable="true"     />
			    
			     <@bravo.Column name="view" header="查看" sortable="true" >
			         <a href=\'javascript:gridOpenNewWin(\\"./notice!view.action?id=@{rowValue.id?c}\\",\\"查看公告[@{rowValue.title}]\\",\\"notice_Grid\\")\' style=\\"TEXT-DECORATION:none\\"><span style=color:red;>查看</span></a>
			     </@bravo.Column>			   
	                </@bravo.GridPanel>
    </@bravo.Viewport>
</@bravo.Page>
