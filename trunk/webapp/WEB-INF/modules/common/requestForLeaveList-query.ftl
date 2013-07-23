<@bravo.Page name="requestForLeaveListManager" title="请假条管理">
	<@bravo.Viewport  layout="border">
		    <@bravo.FormPanel name="requestForLeaveList_Find" tbar="Toolbar" region="north" dataProxy="./requestForLeaveList!find.action"  ajaxPass="true"  frame="true" autoScroll="true" height="150"  width="800" title="请假条查询列表"  collapsible="true" >  
			<@bravo.Toolbar name="Toolbar" valign="top">
				<@bravo.Button name="View" text="查找" iconCls="search" handler="searchForGrid(\\'requestForLeaveList_Find\\',\\'requestForLeaveList_Grid\\')"/>
				<@bravo.Button text="重置" iconCls="reset" handler="resetForm(\\'requestForLeaveList_Find\\')"/>
			</@bravo.Toolbar>
				<@bravo.FieldSet  cols="1,1;2" rows="1,1;1" >		
					<@bravo.TextField fieldLabel="标题" name="title" width="160"/>
					<@bravo.PopuSelect fieldLabel="申请人" fieldName="proposer.userName" readOnly="true" text="人员选择"  width="160" valueField="id" displayField="userName" targetProxy="../security/user!query.action" targetGridName="user_Grid" hiddenName="proposer.id" />
					<@bravo.TextField fieldLabel="申请理由" name="reason" width="300"/>
					
				</@bravo.FieldSet>
			 </@bravo.FormPanel>    

			 <@bravo.GridPanel  region="center" tbar="Toodddlbar" name="requestForLeaveList_Grid"  contextDataName="requestForLeaveList"  stripeRows="true"  dataProxy="./requestForLeaveList!findAndRendJsonData.action"  collapsible="true" split="true">
			     <@bravo.Toolbar name="Toodddlbar" valign="top" >
				 <@bravo.Button name="Add" text="新增" iconCls="add" handler="gridOpenNewWin(\\'./requestForLeaveList!add.action\\',\\'新增请假条\\',\\'requestForLeaveList_Grid\\')"/>
				 <@bravo.Button name="Delete" text="删除" iconCls="delete" handler="grid_doDel(\\'./requestForLeaveList!batchRemove.action\\',\\'requestForLeaveList_Grid\\')"/>
			     </@bravo.Toolbar>               
			     <@bravo.Column hidden="true" name="id" header="ID" width="0"  sortable="true"  resizable="true"     />
			     <@bravo.Column  name="title" header="标题"  width="175"  sortable="true" resizable="true"     />
                             <@bravo.Column name="reason" header="申请理由" width="175"  sortable="true"  resizable="true"     />
			     <@bravo.Column name="proposer.userName" header="申请人" width="175"  sortable="true" resizable="true" />  
			     <@bravo.Column name="view" header="查看" sortable="true" >
			         <a href=\'javascript:gridOpenNewWin(\\"./requestForLeaveList!view.action?id=@{rowValue.id?c}\\",\\"查看请假条[@{rowValue.title}]\\",\\"requestForLeaveList_Grid\\")\' style=\\"TEXT-DECORATION:none\\"><span style=color:red;>查看</span></a>
			     </@bravo.Column>			   
	                </@bravo.GridPanel>
    </@bravo.Viewport>
</@bravo.Page>
