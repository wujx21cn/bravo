/**
 * @update 
 * @version JPK System 2.0 beta
 * @author songyinghao
 * @sourceCode utf-8
 * @license LGPL,All Rights Reserved.
 */
/**
 * @class EditHtmlWin
 * 编辑html的一个window
 */
EditHtmlWin = Ext.extend(Ext.Window, {
    /**
     * @cfg {Object/String} defaultUrl
     * 用于获取数据库中对应的内容
     */
    defaultUrl: null,
	/**
	 * @cfg {Object/String/Number} nodeId
	 * 用于更新数据库对应的内容，是对defaultUrl的一个补充
	 * 在全局上，它对应的是菜单的各个页节点的id
	 */
	nodeId:null,
    /**
     *
     */
    initComponent: function(){
        EditHtmlWin.superclass.initComponent.call(this);
		this.addEvents({
			'afterupdate':true,
			'afterget':true
		});
    },
    /**
     * 初始化事件
     */
    initEvents: function(){
        EditHtmlWin.superclass.initEvents.call(this);
        this.on('beforehide', this.disableCheck, this);
        this.on('beforeshow', this.enableCheck, this);
    },
    /**
     *
     */
    afterRender: function(){
        EditHtmlWin.superclass.afterRender.call(this);
        this.fckEl ||
        (this.fckEl = this.add({
            xtype: 'fckeditor',
            id: 'inner-fck',
            height: this.getInnerHeight(),
            width: this.getInnerWidth()
        }));
        if (this.defaultUrl) {
            this.getCon(this.defaultUrl);
        }
    },
    /**
     * 从数据库提取内容，并赋值给fckeidtor
     */
    getCon: function(url){
        var ew = this;
		ew.defaultUrl=url;
        ew.showMask();
        url = typeof url == 'function' ? url() || '' : url;
        Ext.Ajax.request({
            url: url,
            method: 'post',
            success: function(response){
                //此处 我用了延时执行，原因：在IE下（又是IE） 当把从后台提取的文本赋值给fck时
                //如果文本较大，在IE下等了将近2秒，才成功给fck赋值成功！在chrome，FF下，几乎刷的一下
                //就行了（不知道原因），所以如果能让客户选择浏览器到话，一定要选FireFox或者chrome
                (function(){
                    this.fckEl.setValue(response.responseText);
                    this.hideMask();
                }).defer(30, ew);
            },
            failure: function(response, e){
                ew.hideMask();
                Ext.Msg.alert('信息提示', '抱歉，可能由于网络不畅，导致获取内容失败<br>请与管理员联系！');
            }
        });
    },
    /**
     * 当点击保存按钮时，执行该函数
     */
    updateCon: function(){
        var url = this.defaultUrl, index;
        this.nodeId =this.nodeId?this.nodeId:((url && (index = url.indexOf('subMenuId'))>0) ? url.substring(index + 10) : null);
		if (!this.nodeId||isNaN(Number(this.nodeId))) {
            throw 'nodeId is not existed or nodeId is not the number type';
            return;
        }
        var ew = this;
		
		ew.showMask({
            msg: '正在保存..'
        });

        //标志位  表示更新成功与否
        var updateSuccess = false;
        
        Ext.Ajax.request({
            url: 'html.do',
            method: 'post',
            params: {
                action: 'update',
                subMenuId: this.nodeId,
                htmlCon: ew.fckEl.getValue()
            },
            success: function(response){
				ew.hideMask();
                if (ew.rendered) {
                    ew.hide();
                }
				//其实判断是否成功，完全没有必要在前后台“json来json去”，对于，一个ajax请求(request)，
				//除非服务器发生严重错误（failure），服务器都会回送(response)过来一个“信号”，即响应success，
				//和后台写的是success还是failure无关的 就像我下面写的判断就有些“罗嗦”
                var result = !!(response.responseText)?eval('(' + response.responseText + ')'):null;
                if (result&&!!result.success) {
                	ew.fireEvent.defer(50,ew,['afterupdate',(updateSuccess=true)]);
                }
                else {
                  	ew.fireEvent.defer(50,ew,['afterupdate',(updateSuccess=false)]);
                }
            },
            failure: function(response, e){
                ew.hideMask();
                if (ew.rendered) {
                    ew.hide();
                }
				ew.fireEvent.defer(50,ew,['afterupdate',(updateSuccess=false)]);
            }
        });
    },
	setNodeId:function(nodeId){
		if(nodeId){
			this.nodeId=nodeId;
		}
	},
    /**
     * 显示进度条
     */
    showMask: function(opt){
        opt = opt ||{};
        if (this.rendered) {
            this.body.mask.defer(5, this.body, [opt.msg || '正在加载..', opt.msgCls || 'edit-loading div']);
        }
    },
    /**
     * 关闭进度条
     */
    hideMask: function(){
        this.body.unmask.defer(5, this.body);
    },
    beforeDestroy: function(){
        this.un('beforehide', this.disableCheck, this);
        this.un('beforeshow', this.enableCheck, this);
        Ext.EventManager.un(window, 'beforeunload', this.checkSave, this);
        Ext.each(['fckEl'], function(elName){
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
        }, this);
        EditHtmlWin.superclass.beforeDestroy.call(this);
    },
    onDestroy: function(){
        EditHtmlWin.superclass.onDestroy.call(this);
    },
    /**
     * 检测浏览器到刷新/后退/关闭事件
     * @param {Object} e
     */
    disableCheck: function(e){
        Ext.EventManager.un(window, 'beforeunload', this.checkSave, this);
    },
    /**
     * 取消检测浏览器到刷新/后退/关闭事件
     * @param {Object} e
     */
    enableCheck: function(e){
        Ext.EventManager.on(window, 'beforeunload', this.checkSave, this);
    },
    /**
     * 如果检测 enableCheck
     * 提示用户保存
     * IE和FF下有效
     * @param {Object} e
     */
    checkSave: function(e){
        return e.browserEvent.returnValue = "编辑网页的窗口还没有关闭！";
    }
});
/**
 * 添加样式
 */
Ext.onReady(function(){
    var CSS = Ext.util.CSS, rules = [];
    CSS.getRule('.edit-loading div') || rules.push(".edit-loading div{font-size:13px;padding:15px 10px 5px 10px;background: #fbfbfb url( '/JingpinkeSystemApp/jslib/images/menu/loading-balls.gif' ) no-repeat 5px 5px;background-position:top;line-height: 16px;}");
    if (!!rules.length) {
        CSS.createStyleSheet(rules.join(''));
    }
});
