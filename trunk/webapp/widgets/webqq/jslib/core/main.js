/**
 * @update 
 * @version JPK System 2.0 beta
 * @desc 增加了在线交流平台(WebQQ)，实现单聊，群聊，声音提示，采用Comet技术，无需数据库
 * @version JPK System 1.0 beta
 * @author songyinghao
 * @sourceCode utf-8
 * @license LGPL,All Rights Reserved.
 */
Ext.BLANK_IMAGE_URL = 'ext/s.gif';

/**
 * @class TreeMenuPanel
 * @private
 * @description 左侧菜单使用accordion布局，其中每一个分栏都是一个TreeMenuPanel
 */
TreeMenuPanel = Ext.extend(Ext.tree.TreePanel, {
    initComponent: function() {
        TreeMenuPanel.superclass.initComponent.call(this);
        //只能选中叶节点
        this.getSelectionModel().on('beforeselect',
        function(sm, node) {
            return node.isLeaf();
        });
    },
    /**
     * 当Tab切换时，TreeMenuPanel自动选中对应的叶节点
     * @param {Object} nodeId
     */
    selectItem: function(nodeId) {
        var node = this.getNodeById(nodeId);
        if (node) {
            this.getSelectionModel().select(node);
        }
        else {
            this.getSelectionModel().clearSelections();
        }
    }
});

/**
 * @class MenuPanel
 * @public
 * @description 左侧的导航菜单，采用accordion布局
 */
MenuPanel = function() {
    MenuPanel.superclass.constructor.call(this, {
        id: 'menu',
        region: 'west',
        //title: '网站导航',
        header: false,
        split: 'true',
        width: 200,
        minSize: 150,
        maxSize: 300,
        collapsible: true,
        margins: '0 0 0 0',
        cmargins: '0 0 0 0',
        layout: 'accordion',
        plugins: [new JpkFrame.plugins.ContainerMask({
            bodyMask: true
        })],
        layoutConfig: {
            titleCollapse: true,
            animate: true,
            activeOnTop: false
        },
        animCollapse: false,
        animate: false,
        collapseMode: 'mini',
        tbar: new Ext.Toolbar({
            border: false,
            items: ['<font size=2><b>导航</b></font>', '->', {
                text: '刷新菜单',
                cls: 'x-btn-text-icon',
                iconCls: 'menu-refresh',
                handler: function() {
                    var menu = Ext.getCmp('menu');
                    if (menu.items.length <= 0) {
                        loadMenu(menu, Ext.getCmp('main'));
                    }
                }
            }]
        })
    });
}

Ext.extend(MenuPanel, Ext.Panel, {});

/**
 * @method loadMenu
 * @public
 * @param {Object} menuCon  菜单
 * @param {Object} mainCon  中央显示区域
 * @description 通过该函数，实现所有菜单，子菜单均从数据库提取
 */
var loadMenu = function(menuCon, mainCon) {

    var menuAjax = new Ext.data.Connection({
        listeners: {
            beforerequest: function() {
                menuCon.showMask({
                    msg: '菜单加载中..',
                    msgClass: 'menu-loading'
                });
            },
            requestcomplete: function() {
                menuCon.hideMask();
            },
            requestexception: function() {
                menuCon.showMask({
                    msg: '加载失败!请点击<br>刷新按钮刷新一下',
                    msgClass: 'menu-loading-fail'
                });
            }
        }
    });
    menuAjax.request({
        url: 'getMenu.do',
        method: 'post',
        success: function(response) {
            var menu = Ext.util.JSON.decode(response.responseText);
            for (var i = 0; i < menu.items.length; i++) {
                /**
                 * @class TreeMenu
                 * @private
                 * @description 各分栏菜单都是一个TreeMenuPanel
                 */
                var treeMenu = new TreeMenuPanel({
                    id: 'tree-' + i,
                    title: menu.items[i].title,
                    iconCls: menu.items[i].iconCls,
                    rootVisible: false,
                    border: false,
                    margins: '0 0 0 0',
                    cmargins: '0 0 0 0',
                    lines: false,
                    loader: new Ext.tree.TreeLoader({
                        preloadChildren: true,
                        clearOnLoad: false
                    }),
                    root: new Ext.tree.AsyncTreeNode({
                        text: menu.items[i].title,
                        id: 'root-' + menu.items[i].id,
                        expanded: true,
                        children: menu.items[i].sub
                    })
                });
                /**
                 * 增加单击事件
                 * @param {Object} node
                 * @param {Object} e
                 */
                treeMenu.on('click',
                function(node, e) {
                    if (node.isLeaf()) {
                        e.stopEvent();
                        mainCon.loadCon(node, this);
                    }
                    else {
                        return false;
                    }
                });
                menuCon.add(treeMenu);
            }
            menuCon.doLayout();
        },
        failure: function(response, e) {}
    });
}
/**
 * @class HtmlPanel
 * @private
 * @description Center Region 使用tabpanel，它的tabItem就是HtmlPanel，HtmlPanel的内容是从数据库提取的
 * 实现了动态加载内容
 * @url jslib/common/HtmlPanel.js
 */
/**
 * @Class EditWin
 * 编辑对应的tab里内容
 * @url jslib/ui/EditWin.js
 */
/**
 * @class MainPanel
 * @public
 * @description Center Region
 */
MainPanel = function() {
    MainPanel.superclass.constructor.call(this, {
        id: 'main',
        region: 'center',
        margins: '0 2 0 0',
        resizeTabs: true,
        minTabWidth: 135,
        tabWidth: 135,
        enableTabScroll: true,
        activeTab: 0,
        plugins: [new Ext.ux.TabCloseMenu()],
        items: [{
            id: 'HomePage',
            xtype: 'htmlpanel',
            title: '首页',
            iconCls: 'hp-cls',
            loadMask: false,
            group: null,
            closable: false,
            layout: 'fit',
            defaultSrc: 'html/hp.html'
        }]
    });
}

Ext.extend(MainPanel, Ext.TabPanel, {
    /**
     * 编辑器
     */
    htmlEditWin: null,
    /**
     *
     */
    initEvents: function() {
        MainPanel.superclass.initEvents.call(this);
    },
    /**
     * 加载内容到HtmlPanel
     */
    loadCon: function(node, group) {

        var tabId = "tab-" + node.id;
        var tabTitle = node.text;
        var tabIconCls = node.attributes.iconCls;
        var tabLink = node.attributes.link;
        var tab = this.getComponent(tabId);
        if (tab) {
            this.setActiveTab(tab);
        }
        else {
            tab = this.add({
                xtype: 'htmlpanel',
                id: tabId,
                title: tabTitle,
                iconCls: tabIconCls,
                autoScroll: true,
                layout: 'fit',
                border: false,
                //loadMask:false,关闭进度条
                //loadMask:{} 启用默认的进度条,{}为设置
                //loadMask:{msg:'',msgCls:''} 启用自定义配置
                loadMask: {},
                defaultSrc: 'html.do?action=get&subMenuId=' + node.id,
                closable: true,
                group: group
            });
            this.setActiveTab(tab);
        }
    },
    /**
     * 打印HtmlPanel的html内容
     */
    printCon: function() {
        this.activeTab.print();
    },
    /**
     * 刷新
     */
    reloadCon: function() {
        var src = this.activeTab.defaultSrc;
        this.activeTab.setSrc(src ? src: null);
    },
    /**
     * 编辑HtmlPanel的内容
     */
    editCon: function() {
        var tab = this.activeTab,
        url = tab.defaultSrc,
        nodeId = tab.id.indexOf('tab') >= 0 ? tab.id.replace('tab-', '') : null;
        if (nodeId) {
            this.htmlEditWin || (this.htmlEditWin = new EditHtmlWin({
                id: 'eidt-win',
                title: '编辑内容',
                height: 424,
                width: 884,
                closeAction: 'hide',
                draggable: false,
                resizable: false,
                //constrain: true,
                modal: true,
                constrainHeader: true,
                buttonAlign: 'center',
                listeners: {
                    'afterupdate': function(updateSuccess) {
                        if (updateSuccess) {
                            tab.ownerCt.reloadCon();
                        }
                        else {
                            Ext.Msg.alert('信息提示', '抱歉，可能由于网络不畅，导致保存内容失败' + '<br>请重新编辑，如果还有问题<br>请与管理员联系！');
                        }
                    }
                },
                buttons: [{
                    text: '保存修改',
                    handler: function() {
                        this.ownerCt.updateCon();
                    }
                },
                {
                    text: '关闭',
                    handler: function() {
                        this.ownerCt.hide();
                    }
                }]
            }));
            this.htmlEditWin.setNodeId(nodeId);
            this.htmlEditWin.show();
            if (url != this.htmlEditWin.defaultUrl) {
                this.htmlEditWin.getCon(url);
            }

        }
    }
});
/**
 * Start the App
 */
Ext.onReady(function() {

    //初始化声音类soundManager 
    //为了把soundManager引入到ext中，竟然花了我将近一天时间，终于弄好了，兼容所有带flashplayer的浏览器
    soundManager = new SoundManager();
    //soundManager.waitForWindowLoad = true;
    soundManager.debugMode = false;
    soundManager.url = 'jslib/sound/swf';
    soundManager.beginDelayedInit();
    soundManager.onload = function() {
        //系统声音
        soundManager.createSound({
            id: 'systemSound',
            url: 'jslib/sound/mp3/system.mp3',
            //autoLoad: true,//自动加载
            //multiShot: false,//true 在同一时刻只能有一个频段的声音
            autoPlay: true //自动播放 这个是系统的背景音
            //volume: 100
        });
        //信息音
        soundManager.createSound({
            id: 'msgSound',
            url: 'jslib/sound/mp3/msg.mp3'
            //volume: 100
        });
        //加入音
        soundManager.createSound({
            id: 'joinSound',
            url: 'jslib/sound/mp3/join.mp3'
            //volume: 100
        });
    }

    Ext.QuickTips.init();
    Ext.QuickTips.getQuickTip().interceptTitles = true;
    Ext.QuickTips.enable();

    var menu = new MenuPanel();
    var main = new MainPanel();

    var currentUser = null;
    //QQ客户端
    var clientWin = null;

    main.on('tabchange',function(tp, tab) {
        if (tab.group) {
            var nodeId = tab.id.replace('tab-', '');
            tab.group.selectItem(nodeId);
        }
    });

    var header = new Ext.Panel({
        border: false,
        layout: 'anchor',
        region: 'north',
        cls: 'JpkSys-header',
        height: 60,
        items: [{
            xtype: 'box',
            el: 'header',
            border: false,
            anchor: 'none -25'
        },
        new Ext.Toolbar({
            border: false,
            id: 'headerBar',
            items: ['->', {
                text: 'QQ',
                cls: "x-btn-text-icon",
                iconCls: 'header-qq',
                id: 'header-qq',
                hidden: true,
                handler: function() {
                    if (clientWin) {
                        clientWin.show();
                        return;
                    }
                    clientWin = new JpkFrame.qq.ClientWin({
                        currentUser: currentUser
                    });
                    clientWin.show();
                    //页面退出的时候
                   dojo.addOnUnload(clientWin, "leave");
                }
            },
            {
                text: '登录',
                iconCls: 'header-login',
                id: 'header-login',
                handler: function() {
                    var logWin = new RegAndLogWin({
                        type: 'log'
                    });
                    logWin.show();
                    logWin.on('logsuccess',
                    function(tools, user) {
                        setUserLogin(tools, user);
                    });
                }
            },
            {
                text: '注册',
                iconCls: 'header-reg',
                id: 'header-reg',
                handler: function() {
                    var regWin = new RegAndLogWin({
                        type: 'reg'
                    });
                    regWin.show();
                    regWin.on('regsuccess',function(tools, user) {
                        setUserLogin(tools, user);
                    });
                }
            },
            {
                text: '更改信息',
                cls: 'x-btn-text-icon',
                iconCls: 'header-changepass',
                hidden: true,
                id: 'header-changepass',
                handler: function() {
                    var edit = new EditUserWin({
                        user: currentUser
                    });
                    edit.show();
                }
            },
            {
                text: '注销用户',
                cls: "x-btn-text-icon",
                iconCls: 'header-logout',
                hidden: true,
                id: 'header-logout',
                handler: function() {
                    setUserLogout();
                }
            },
            '更换皮肤:', {
                xtype: 'combo',
                width: 80,
                editable: false,
                displayField: 'theme-name',
                id: 'theme-combo',
                name: 'theme-combo',
                editable: false,
                value: '默认风格',
                mode: 'local',
                valueField: 'theme-value',
                triggerAction: 'all',
                selectOnFocus: true,
                store: new Ext.data.SimpleStore({
                    fields: ['id', 'theme-name', 'theme-value'],
                    data: JpkFrame.data.themes
                }),
                listeners: {
                    select: function(combo, record, index) {
                        changeTheme(combo.getValue());
                    }
                }
            }]
        })]
    });

    var bottom = new Ext.Toolbar({
        id: 'bottom',
        border: true,
        frame: true,
        region: 'south',
        height: 24,
        items: ['&nbsp&nbsp', {
            id: 'bottom-user',
            xtype: 'label',
            hidden: true,
            text: '当前用户是 ： '
        },
        '->', "©2008-2009 <a href='http://hi.baidu.com/ccutshyhao/blog/item/2c570f9436fe99007bf48084.html' target='_blank'><font color=blue>Shyhao Inc.</font></a>　版权所有　　　　"]
    });

    var viewport = new Ext.Viewport({
        layout: 'border',
        items: [header, menu, main, bottom]
    });

    viewport.doLayout();

    /**
     * 自动登录
     */
    var autoLogin = (function() {
        Ext.Ajax.request({
            url: 'autologin.do',
            method: 'post',
            success: function(response) {
                var result = eval('(' + response.responseText + ')');
                if (result && ( !! result.success) && (result.success == '1') && result.msg) {
                    result = eval('(' + result.msg + ')');
                    var user = result.user;
                    var tools = result.tools;
                    setUserLogin(tools, user);
                }
                else {
                    //Ext.Msg.alert('信息提示', result.msg || '系统错误，请再试一次！');
                }
            },
            failure: function(response, e) {}
        });
    }).defer(80);
    /**
     * 用户登录
     * @param {Object} tools
     */
    var setUserLogin = function(tools, user) {
        currentUser = user;
        Ext.getCmp('bottom-user').show();
        Ext.getCmp('bottom-user').setText('当前用户 ： ' + user);
        Ext.getCmp('header-login').hide();
        Ext.getCmp('header-reg').hide();
        Ext.getCmp('header-changepass').show();
        Ext.getCmp('header-logout').show();
        Ext.getCmp('header-qq').show();

        var headerBar = Ext.getCmp('headerBar');
        if (tools['refresh']) {
            headerBar.insertButton(2, {
                text: '刷新',
                iconCls: 'header-refresh',
                id: 'header-refresh',
                handler: function() {
                    main.reloadCon();
                }
            });
        }
        if (tools['print']) {
            headerBar.insertButton(2, {
                text: '打印',
                iconCls: 'header-print',
                id: 'header-print',
                handler: function() {
                    try {
                        main.printCon();
                    }
                    catch(ex) {
                        Ext.Msg.alert('信息提示', '由于某些原因导致打印失败！<br>' + ex);
                    }
                }
            });
        }
        if (tools['edit']) {
            headerBar.insertButton(2, {
                text: '编辑',
                iconCls: 'header-edit',
                id: 'header-edit',
                handler: function() {
                    main.editCon();
                }
            });
        }

    }
    /**
     * 用户注销
     */
    var setUserLogout = function() {
		//如果QQ客户端没有关闭的话，就destroy掉，不要使用close()
		if(clientWin){
			clientWin.destroy();
			clientWin=null;
		}
        Ext.getBody().mask("正在退出......");
        currentUser == null;
        Ext.getCmp('bottom-user').hide();
        Ext.getCmp('bottom-user').setText('');
        Ext.Ajax.request({
            url: 'logout.do',
            method: 'post',
            success: function(response) {
                if (response.responseText == '1') {
                    Ext.getCmp('header-login').show();
                    Ext.getCmp('header-reg').show();
                    Ext.getCmp('header-changepass').hide();
                    Ext.getCmp('header-logout').hide();
                    Ext.getCmp('header-qq').hide();
                    var edit, refresh, print;
                    if (edit = Ext.getCmp('header-edit')) {
                        edit.destroy();
                        edit = null;
                        delete edit;
                    }
                    if (refresh = Ext.getCmp('header-refresh')) {
                        refresh.destroy();
                        refresh = null;
                        delete refresh;
                    }
                    if (print = Ext.getCmp('header-print')) {
                        print.destroy();
                        print = null;
                        delete print;
                    }
                    Ext.getBody().unmask();
                }
                else {
                    Ext.getBody().unmask();
                    Ext.Msg.alert('信息提示', response.responseText);
                }
            },
            failure: function(response, e) {
                Ext.getBody().unmask();
                Ext.Msg.alert('信息提示', '退出失败,系统错误');
            }
        });
    }
    /**
     *更改主题
     * @param {Object} value
     */
    var changeTheme = function(value) {
        if (value) {
            Ext.util.CSS.swapStyleSheet('window', 'ext/resources/css/' + value + '.css');
            //恢复到默认样式时，别忘了把自定义样式加上
            if (value == 'ext-all') {
                Ext.util.CSS.swapStyleSheet('window', 'jslib/css/main.css');
            }
            //保存主题
            saveThemeToCookie(value);
        }
    }
    /**
     * 保存主题名到cookie中
     * 这里使用的是自定义的cookie工具（jslib/common/Cookie.js）
     * 你完全可以使用 ext提供的cookie工具
     * Ext.state.CookieProvider
     * @param {Object} value
     */
    var saveThemeToCookie = function(value) {
        //如果需要的话，请加上延时操作，防止主题更换过快
        JpkFrame.saveCookie('JpkFrame.theme', value);
    }
    /**
     * 从cookie中获取主题
     * 后面的小括号表示立即执行该函数
     */
    var readThemeFromCookie = (function() {
        var themeValue = JpkFrame.getCookie('JpkFrame.theme');
        if (themeValue) {
            Ext.getCmp('theme-combo').setValue(themeValue);
            changeTheme(themeValue);
        }
    }).defer(50);

    setTimeout(function() {
        Ext.get('loading').remove();
        Ext.get('loading-mask').fadeOut({
            remove: true
        });
        //加载菜单
        loadMenu(menu, main);
    },150);

    /**
     在“beforeunload”的时候销毁这些组件，比如 点击了浏览器的关闭/刷新/后退按钮,ext将destroy这些组件，释放内存(至于对释放内存有多大帮助，我没测试过)
     Gecko(FireFox,Mozalia),IE关闭/刷新/后退下都有效 ，WebKit(Chrome,Safari)支持关闭/刷新，Opera不支持该事件*/
    //由于前面需要用到'beforeunload'事件，和这个有些冲突，所以这里我改成了unload事件，不过这就起不到相应的作用了，没有特殊情况还是使用beforeunload
    //Ext.EventManager.on(window, "unload", Ext.destroy.createDelegate(Ext, [viewport, main, menu, header, bottom]), Ext, {
    //single: true
    //});
});