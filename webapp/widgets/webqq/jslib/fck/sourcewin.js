//测试文件
Ext.onReady(function(){
	
    var win = new Ext.Window({
        title: 'Ext和Fckeditor结合示例',
        width: 900,//844
        height: 400,//300
        closable: false,
        layout: 'fit',
        border: false,
        bodyBorder: false,
        items: [new Ext.ux.form.FCKeditor({
            id: 'te',
            height: 333
        })],
        buttonAlign: 'left',
        buttons: [{
            text: '创建',
            handler: function(){
                var f = Ext.getCmp('te').getValue();
                Ext.Ajax.request({
                    url: 'html.do',
                    params: {
                        action: 'save',
                        htmlCon: f,
						subMenuId:'17'
                    },
                    success: function(response){
                        var result = eval('(' + response.responseText + ')');
                        if (result.success == 'true') {
                            Ext.Msg.alert('保存成功！');
                        }
                        else {
                            Ext.Msg.alert('保存失败！');
                        }
                    },
                    failure: function(response, e){
                    
                    }
                });
            }
        }, {
            text: '获取',
            handler: function(){
                Ext.Ajax.request({
                    url: 'html.do',
                    params: {
                        action: 'get',
                        subMenuId: '6'
                    },
                    success: function(response){
						//检测数据类型
                        //alert(typeof(response.responseText));
                        // var result = eval('(' + response.responseText + ')');
                        Ext.getCmp('te').setValue(response.responseText);  
                    },
                    failure: function(response, e){
                    
                    }
                });
            }
        }]
    
    });
    win.show();
});
