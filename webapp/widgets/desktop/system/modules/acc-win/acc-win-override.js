Ext.namespace('QoDesk');
Ext.namespace('Bravo.qq');
QoDesk.chatWindow = Ext.extend(Ext.app.Module, {
	moduleType : 'demo',
	moduleId : 'demo-chat',
	menuPath : 'StartMenu',
	launcher : {
		iconCls: 'layout-icon',
		shortcutIconCls: 'layout-shortcut',
		text: '聊天窗口',
		tooltip: '<b>Layout Window</b><br />A window with a layout'
	},
     /**
     * 添加聊天信息
     */
    addMsg: function(msg, from, color) {
        if (!color) {
            color = (from != this.user) ? 'red': 'blue';
        }
        //url:jslib/common/TimeFormat.js
        var time = (new Date()).format("yyyy-MM-dd EEE hh:mm:ss");
        var formatmsg = "<div class='_msgtitle' style='color:" + color + "'>" + from + "&nbsp;&nbsp;&nbsp;&nbsp;" + time + "</div><div class='_msg'>" + msg + "</div>";
        this.items.items[0].body.insertHtml("beforeEnd", formatmsg);
        this.items.items[0].body.scroll("bottom", 9999);
    }
});


Ext.override(QoDesk.chatWindow, {
	createWindow : function(){
		var desktop = QoDesk.App.getDesktop();
        var win = desktop.getWindow(this.userId);
		if(!win){
			var winWidth =550;
			var winHeight =450;
			win = desktop.createWindow({
				id: this.userId,
				title:'和' + this.userId + '聊天中',
				width:winWidth,
				height:winHeight,
				x:desktop.getWinX(winWidth),
				y:desktop.getWinY(winHeight),
				iconCls: 'layout-icon',
				shim:false,
				layout:'fit',
				animCollapse:false,
				constrainHeader:true,
				minimizable:true,
				maximizable:true,
				items: [{
						anchor: '100% 70%',
						bodyStyle: 'padding:10px;'
					},
					{
						xtype: 'form',
						anchor: '100% 30%',
						hideLabels: true,
						items: [{
							xtype: 'htmleditor',
							value: '',
							anchor: '100% 100%',
							name: 'editbody'
						}]
					}],
				 buttons: [{
					text: '发送',
					handler: function() {
						alert(this.ownerCt.title);
						var editor = this.ownerCt.items.items[1].items.items[0];
						var fontColor = this.ownerCt.client.fontColor ? this.ownerCt.client.fontColor: null;
						//本来我的打算是所有信息统一发往后台，再由后台传过来，但发现网路不好时（很有可能 后台传不过来数据），自己发送的信息也看不到了
						//所以我把当前客户发送的信息直接赋给当前客户端了
						this.ownerCt.addMsg(editor.getValue(), this.ownerCt.client.currentUser, fontColor);
						//this.ownerCt.client.sendMsg(editor.getValue(), this.ownerCt.user, fontColor);
						//editor.reset();
					}
				},
				{
					text: '关闭',
					handler: function() {
						this.ownerCt.close();
					}
				}],
						taskbuttonTooltip: '<b>abc</b>'
					});
		}
		win.show();		
	}
});


Bravo.qq.ChatWin = function(config) {
    //config共有两项属性配置 user client
    if (!config.user) {
        throw 'user can not be null';
        return;
    }
    Bravo.qq.ChatWin.superclass.constructor.call(this, Ext.apply({
        title: '和' + config.user + '聊天中',
        iconCls: 'header-qq',
        width: 550,
        height: 450,
        id: config.user,
        collapsible: true,
        maximizable: true,
        border: false,
        bodyBorder: false,
        plain: true,
        layout: 'anchor',
        defaultType: 'panel',
        defaults: {
            border: false,
            bodyBorder: false
        },
        items: [{
            anchor: '100% 70%',
            bodyStyle: 'padding:10px;'
        },
        {
            xtype: 'form',
            anchor: '100% 30%',
            hideLabels: true,
            items: [{
                xtype: 'htmleditor',
                value: '',
                anchor: '100% 100%',
                name: 'editbody'
            }]
        }],
        buttons: [{
            text: '发送',
            handler: function() {
                var editor = this.ownerCt.items.items[1].items.items[0];
                var fontColor = this.ownerCt.client.fontColor ? this.ownerCt.client.fontColor: null;
                //本来我的打算是所有信息统一发往后台，再由后台传过来，但发现网路不好时（很有可能 后台传不过来数据），自己发送的信息也看不到了
                //所以我把当前客户发送的信息直接赋给当前客户端了
                this.ownerCt.addMsg(editor.getValue(), this.ownerCt.client.currentUser, fontColor);
                this.ownerCt.client.sendMsg(editor.getValue(), this.ownerCt.user, fontColor);
                editor.reset();
            }
        },
        {
            text: '关闭',
            handler: function() {
                this.ownerCt.close();
            }
        }]
    },config || {}));
}
Ext.extend(Bravo.qq.ChatWin, Ext.Window, {
    /**
     * 添加聊天信息
     */
    addMsg: function(msg, from, color) {
        if (!color) {
            color = (from != this.user) ? 'red': 'blue';
        }
        //url:jslib/common/TimeFormat.js
        var time = (new Date()).format("yyyy-MM-dd EEE hh:mm:ss");
        var formatmsg = "<div class='_msgtitle' style='color:" + color + "'>" + from + "&nbsp;&nbsp;&nbsp;&nbsp;" + time + "</div><div class='_msg'>" + msg + "</div>";
        this.items.items[0].body.insertHtml("beforeEnd", formatmsg);
        this.items.items[0].body.scroll("bottom", 9999);
    },
    /**
     * 清除输入框的信息
     */
    clearInput: function() {},
    /**
     * 销毁组件
     */
    beforeDestroy: function() {
        if (this.rendered) {

            if (this.tools) {
                for (var k in this.tools) {
                    Ext.destroy(this.tools[k]);
                }
            }
            if (this.header && this.headerAsText) {
                var s;
                if (s = this.header.child('span')) s.remove();
                this.header.update('');
            }
            Ext.each(['header', 'body', 'footer'],
            function(elName) {
                if (this[elName]) {
                    if (typeof this[elName].destroy == 'function') {
                        this[elName].destroy();
                    }
                    else {
                        Ext.destroy(this[elName]);
                    }

                    this[elName] = null;
                    delete this[elName];
                }
            },
            this);
        }
        Bravo.qq.ChatWin.superclass.beforeDestroy.call(this);
    }
});



Ext.override(QoDesk.AccordionWindow, {
    createWindow : function(){
    	var desktop = this.app.getDesktop();
        var win = desktop.getWindow('acc-win');
		var qqPanel = new Ext.tree.TreePanel({
			id:'im-tree',
			title: 'Online Users',
			loader: new Ext.tree.TreeLoader(),
			rootVisible:false,
			lines:false,
			enableDD: true,
			autoScroll:true,
			useArrows: true,
			tools:[{
				id:'refresh',
				on:{
					click: function(){
						var tree = Ext.getCmp('im-tree');
						tree.body.mask('Loading', 'x-mask-loading');
						tree.root.reload();
						tree.root.collapse(true, false);
						setTimeout(function(){ // mimic a server call
							tree.body.unmask();
							tree.root.expand(true, true);
						}, 1000);
					}
				}
			}],
			root: new Ext.tree.AsyncTreeNode({
				text:'Online',
				children:[{
					text:'Friends',
					expanded:true,
					children:[{
						text:'Jack',
						iconCls:'user',
						leaf:true
					},{
						text:'Brian',
						iconCls:'user',
						leaf:true
					},{
						text:'Jon',
						iconCls:'user',
						leaf:true
					},{
						text:'Tim',
						iconCls:'user',
						leaf:true
					},{
						text:'Nige',
						iconCls:'user',
						leaf:true
					},{
						text:'Fred',
						iconCls:'user',
						leaf:true
					},{
						text:'Bob',
						iconCls:'user',
						leaf:true
					}]
				},{
					text:'Family',
					expanded:true,
					children:[{
						text:'Kelly',
						iconCls:'user-girl',
						leaf:true
					},{
						text:'Sara',
						iconCls:'user-girl',
						leaf:true
					},{
						text:'Zack',
						iconCls:'user-kid',
						leaf:true
					},{
						text:'John',
						iconCls:'user-kid',
						leaf:true
					}]
				}]
			})
		});
		
		qqPanel.on('click',function(node, e){   
			if(node.isLeaf()){ 
				var ccbcc = new QoDesk.chatWindow({ userId : 'wujx',  launcher : { iconCls: 'layout-icon', shortcutIconCls: 'layout-shortcut', text: 'Call项目', tooltip: '快捷方式Call项目' }});
				ccbcc.createWindow();
				e.stopEvent();   
			}
		});   

        if(!win){
            win = desktop.createWindow({
                id: 'acc-win',
                title: '通信王子',
                width:250,
                height:400,
                iconCls: 'acc-icon',
                shim:false,
                animCollapse:false,
                constrainHeader:true,
                maximizable: false,
                taskbuttonTooltip: '<b>Accordion Window</b><br />A window with an accordion layout',

                tbar:[{
                    tooltip:'<b>Rich Tooltips</b><br />Let your users know what they can do!',
                    iconCls:'demo-acc-connect'
                },'-',{
                    tooltip:'Add a new user',
                    iconCls:'demo-acc-user-add'
                },' ',{
                    tooltip:'Remove the selected user',
                    iconCls:'demo-acc-user-delete'
                }],

                layout: 'accordion',
                layoutConfig: {
                    animate:false
                },
                items: [qqPanel
                    , {
                        title: 'Settings',
                        html:'<p>Something useful would be in here.</p>',
                        autoScroll:true
                    },{
                        title: 'Even More Stuff',
                        html : '<p>Something useful would be in here.</p>'
                    },{
                        title: 'My Stuff',
                        html : '<p>Something useful would be in here.</p>'
                    }
                ]
            });
        }
        win.show();
    }
});