
//加载所需dojo类库
dojo.require("dojox.cometd");
dojo.require("dojox.cometd.timestamp");
dojo.require("dojox.cometd.ack");

Ext.namespace('JpkFrame.qq');
/**
 * 聊天窗口
 * @param {Object} config
 */
JpkFrame.qq.ChatWin = function(config) {
    //config共有两项属性配置 user client
    if (!config.user) {
        throw 'user can not be null';
        return;
    }
    JpkFrame.qq.ChatWin.superclass.constructor.call(this, Ext.apply({
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
Ext.extend(JpkFrame.qq.ChatWin, Ext.Window, {
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
        JpkFrame.qq.ChatWin.superclass.beforeDestroy.call(this);
    }
});

/**
 * 客户端窗口
 * @param {Object} config
 */
JpkFrame.qq.ClientWin = function(config) {
    if (!config.currentUser) {
        throw 'currentUser can not be null';
        return;
    }
    JpkFrame.qq.ClientWin.superclass.constructor.call(this, Ext.apply({
        title: '当前用户<br>' + config.currentUser,
        iconCls: 'header-qq',
        collapsible: true,
        id: config.currentUser,
        x: (document.body.clientWidth > 270) ? (document.body.clientWidth - 270) : undefined,
        width: 220,
        height: 400,
        closeAction: 'hide',
        constrain: true,
        plain: true,
        border: false,
        bodyBorder: false,
        layout: 'fit',
        tbar: [{
            text: '刷新',
            cls: 'x-btn-text-icon',
            iconCls: 'menu-refresh',
            handler: function() {
				var hasJoined=false;
                Ext.getCmp(config.currentUser).userListPanel.root.eachChild(function(node) {
                    if (node.text == config.currentUser) {
						hasJoined=true;
                        return;
                    }
                });
				if(hasJoined){
					hasJoined=false;
					return;
				}
                dojox.cometd.publish("/public/chat", {
                    user: config.currentUser,
                    join: true,
                    action: 'rejoin',
                    chat: config.currentUser + '重新加入'
                });
            }
        }],
        bbar: [{
            text: '进入聊天室',
            cls: 'x-btn-text-icon',
            iconCls: 'menu-teacher',
            handler: function() {
                Ext.getCmp(config.currentUser).joinPublicRoom();
            }
        }]
    },
    config || {}));
}
Ext.extend(JpkFrame.qq.ClientWin, Ext.Window, {

    /**
     * 初始化事件
     */
    initEvents: function() {
        JpkFrame.qq.ClientWin.superclass.initEvents.call(this);
        //this.on('beforeclose', this.leave, this);
    },
    /**
     *
     */
    onRender: function(ct, position) {
        JpkFrame.qq.ClientWin.superclass.onRender.call(this, ct, position);
        this.userListPanel || (this.userListPanel = this.add(new Ext.tree.TreePanel({
            header: false,
            title: '在线用户',
            border: true,
            //rootVisible: false,
            lines: false,
            height: 300,
            width: 210,
            autoScroll: true,
            animate: false,
            root: new Ext.tree.TreeNode({
                expandable: true,
                text: '登录用户',
                expanded: true
            })
        })));
        this.userListPanel.on('dblclick',
        function(node, e) {
            if ((node.text == this.ownerCt.currentUser) || (node.text == '登录用户')) {
                return;
            }
            this.ownerCt.createChatWin(node.text).show();
        });
    },
    /**
     * afterRender常用来初始化
     */
    afterRender: function() {
        JpkFrame.qq.ClientWin.superclass.afterRender.call(this);
        this.initConnect();
    },
    /**
     *初始化连接
     */
    initConnect: function() {
        //指向后台的servlet
        //127.0.0.1/JingpinkeSystemApp/cometd
        var loc = "cometd";
        dojox.cometd.init(loc);
        this._connected = true;
        dojox.cometd.startBatch();
        //订阅频道
        dojox.cometd.subscribe("/public/chat", this, "getMsg");
        //向频道上发布消息,说明当前用户的加入
        dojox.cometd.publish("/public/chat", {
            user: this.currentUser,
            join: true,
            chat: this.currentUser + " 已加入"
        });
        dojox.cometd.endBatch();
        //对 comet failures 进行处理
        this.cometFail();

    },
    /**
     * 连接失败处理
     */
    cometFail: function() {
        //如果之前已经订阅_meta，则取消订阅
        if (this._meta) {
            dojo.unsubscribe(this._meta, null, null);
        }
        //订阅
        this._meta = dojo.subscribe("/cometd/meta", this,
        function(e) {
            if (e.action == "handshake") {
                if (e.reestablish) {
                    if (e.successful) {
                        //重新连接成功
                        dojox.cometd.subscribe("/public/chat", this, "getMsg");
                        dojox.cometd.publish("/public/chat", {
                            user: this.currentUser,
                            join: true,
                            chat: this.currentUser + " 重新加入"
                        });
                    }
                    this.getMsg({
                        data: {
                            join: true,
                            user: "SERVER",
                            chat: "handshake " + e.successful ? "Handshake OK": "Failed"
                        }
                    });
                }
            }
            else if (e.action == "connect") {
                if (e.successful && !this._connected) {
                    this.getMsg({
                        data: {
                            join: true,
                            user: "SERVER",
                            chat: "reconnected!"
                        }
                    });
                }
                if (!e.successful && this._connected) {
                    this.getMsg({
                        data: {
                            leave: true,
                            user: "SERVER",
                            chat: "disconnected!"
                        }
                    });
                }
                this._connected = e.successful;
            }
        });
    },
    /**
     * 加入聊天室
     */
    joinPublicRoom: function() {
        if (this.publicRoom) {
            this.publicRoom.show();
            return;
        }
        var fontColor = this.fontColor ? this.fontColor: ColorUtil.rand();
        Ext.apply(this, {
            fontColor: fontColor
        });
        this.publicRoom = new JpkFrame.qq.ChatWin({
            user: 'publicRoom',
            title: '大家一块交流吧<br>当前用户是 ' + this.currentUser,
            iconCls: 'menu-teacher',
            client: this
        });
        this.publicRoom.show();

        //向频道上发布消息,说明当前用户已加入聊天室
        dojox.cometd.publish("/public/chat", {
            user: this.currentUser,
            chat: this.currentUser + " 已加入聊天室",
            fontColor: fontColor
        });
        this.publicRoom.on('beforeclose',
        function() {
            //向频道上发布消息,说明当前用户已离开聊天室
            dojox.cometd.publish("/public/chat", {
                user: this.client.currentUser,
                chat: this.client.currentUser + " 已离开聊天室",
                fontColor: fontColor
            });
            this.client.publicRoom.destroy();
            this.client.publicRoom = null;
            delete this.client.publicRoom;
        });
    },
    /**
     * 向频道上发送消息
     */
    sendMsg: function(text, toUser, fontColor) {
        //信息为空（null或‘’），返回 这个根据实际情况情况确定，但null一定要返回的
        if (!text || !text.length) {
            return false;
        }
        if (toUser && (toUser != 'publicRoom')) {
            //如果为私聊，则转到私聊频道上
            dojox.cometd.publish("/private/chat", {
                room: "/public/chat",
                // This should be replaced by the room name
                user: this.currentUser,
                chat: text,
                //和当前用户要私聊的用户
                peer: toUser
            });
        }
        else {
            //不是私聊，则转到公用频道上
            dojox.cometd.publish("/public/chat", {
                user: this.currentUser,
                chat: text,
                fontColor: fontColor
            });
        }
    },
    /**
     * 获取消息
     */
    getMsg: function(message) {
        //alert('getMsg');
        if (!message.data) {
            return;
        }
        //如果数据是数组的形式，则说明得到信息 是 在线人员（这和后台有关）
        if (message.data instanceof Array) {
            //alert(message.data);
            //把当前树中中的人员全部删除
            var root = this.userListPanel.root;
            root.collapse(false, false);
            while (root.firstChild) {
                root.removeChild(root.firstChild).destroy();
            }
            //root.childrenRendered = false;
            //root.loaded = false;
            //if (root.isHiddenRoot()) {
            //  root.expanded = false;
            //}
            //把在线人员添加到树中    
            var i = 0;
            for (i = 0; i < message.data.length; i++) {
                root.appendChild({
                    text: message.data[i],
                    leaf: true,
                    iconCls: (this.currentUser != message.data[i]) ? 'qq-guest': 'qq-host'
                });
            }
            root.expand(false, false);
            root = null;
            delete root;
            //播放声音
            soundManager.play('joinSound');
        }
        else {
            //是加入还是离开
            var state = message.data.join || message.data.leave;
            //当用户刚加入/退出时，会发出已经加入/退出信息，规定为不接受加入/退出信息
            if (state) {
                return;
            }
            //发送信息者 
            var from = message.data.user;
            //如果接到的信息的发送者是当前客户(自己发给自己的)，则返回
            if (from == this.currentUser) {
                return;
            }
            //聊天内容
            // 这一点比较让人郁闷，没想到dojo这么体贴人心，标签都转换好了，可我还得转过来
            var text = message.data.chat;
            text = text.replace(/&lt;/g, '<');
            text = text.replace(/&gt;/g, '>');
            //范围 \private 私聊 \ error 错误 \ 空  公聊
            var scope;
            //私聊
            if ((scope = message.data.scope) && (scope == 'private')) {
                //当用户刚加入/退出时，会发出已经加入/退出信息，在私聊情况下规定为不接受加入/退出信息
                //if (state) {
                //  return;
                //}
                //私聊情况下，如果接到的信息的发送者是当前客户(自己发给自己的)，则返回
                // if (from == this.currentUser) {
                //   return;
                // }
                var win = this.createChatWin(from);
                win.show();
                win.addMsg(text, from);
                //播放声音
                soundManager.play('msgSound');
                win = null;
                delete win;
                return;
            }
            //公聊
            if (this.publicRoom) {
                //alert(this.fontColor);
                //alert(message.data.fontColor);
                this.publicRoom.addMsg(text, from, message.data.fontColor);

                //播放声音
                soundManager.play('msgSound');
            }
        }
    },
    /**
     * 用户离开
     */
    leave: function() {
        //alert('leave');
        if (!this.currentUser) {
            return;
        }
        if (this._meta) {
            dojo.unsubscribe(this._meta);
        }
        this._meta = null;
        dojox.cometd.startBatch();
        dojox.cometd.unsubscribe("/public/chat", this, "getMsg");
        dojox.cometd.publish("/public/chat", {
            user: this.currentUser,
            leave: true,
            chat: this.currentUser + "已离开"
        });
        dojox.cometd.endBatch();
        this.currentUser = null;
        dojox.cometd.disconnect();
    },
    /**
     *弹出聊天窗口
     */
    createChatWin: function(user) {
        var win;
        if (win = Ext.getCmp(user)) {
            return win;
        }
        win = new JpkFrame.qq.ChatWin({
            user: user,
            client: this
        });
        return win;
    },
    /**
     *
     */
    beforeDestroy: function() {
        //离开
        this.leave();
        //关闭所有聊天窗口
        this.userListPanel.root.eachChild(function(node) {
            var win;
            if (win = Ext.getCmp(node.text)) {
                win.close();
            }
            win = null;
            delete win;
        });

        if (this.rendered) {

            if (this.userListPanel) {
                this.remove(this.userListPanel);
            }
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
            Ext.each(['userListPanel', 'publicRoom', 'header', 'body', 'bottomToolbar'],
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
        JpkFrame.qq.ClientWin.superclass.beforeDestroy.call(this);
    }
});