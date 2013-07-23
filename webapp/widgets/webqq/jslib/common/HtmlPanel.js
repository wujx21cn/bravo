/**
 * @update 
 * @version JPK System 2.0 beta
 * @author songyinghao
 * @sourceCode utf-8
 * @license LGPL,All Rights Reserved.
 */
/**
 * @class JpkFrame.common.HtmlPanel
 */
/**
   使用方法：
   {
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
                closable: true
      }
 */
Ext.namespace('JpkFrame.common');
(function(){
JpkFrame.common.HtmlFrame = function(){
    var args = Array.prototype.slice.call(arguments, 0), el = Ext.get(args[0]), config = args[0];
    //console.log('判断el的类型是否为iframe');
    //判断el的类型是否为iframe
    if (el && el.dom && el.dom.tagName == 'IFRAME') {
        //console.log('el的类型是frame');
        config = args[1] ||{};
    }
    else {
    }//暂不作处理
    //确保el的dom有一个合法的名字
    el.dom.name || (el.dom.name = el.dom.id);
    
	JpkFrame.common.HtmlFrame.superclass.constructor.call(this);
    //
    Ext.apply(el, this);
    //增加样式
    el.addClass('html-iframe');
    if (config.style) {
        el.applyStyles(config.style);
    }
    //需要被遮盖的HtmlElement，根据css selector查找
    el._maskEl = el.parent('.html-ifame-mask') || el.parent().addClass('.html-ifame-mask');
    //el的一些属性
	
    Ext.apply(el, {
        //进度指示条
        loadMask: Ext.apply({
            msg: '内容加载中....',
            msgCls: 'html-loading',
            maskEl: el._maskEl,
			disabled:!config.loadMask
        },config.loadMask||{}),
        //判断加载完毕的事件
        _eventName: Ext.isIE ? 'onreadystatechange' : 'onload'
    });
    //为事件绑定处理函数loadHandler
    el.dom[el._eventName] = el.loadHandler.createDelegate(el);
    //设置iframe的src
    if (config.src) {
        el.setSrc(config.src);
    }
    return JpkFrame.common.HtmlFrame.Manager.register(el);
}
//JpkFrame.common.HtmlFrame管理器
JpkFrame.common.HtmlFrame.Manager = function(){
    var frames = {};
    return {
        register: function(frame){
            frame.manager = this;
            frames[frame.id] = frames[frame.dom.name] = {
                ref: frame,
                elCache: {}
            };
            return frame;
        },
        deRegister: function(frame){
            delete frames[frame.id];
            delete frames[frame.dom.name];
        }
    }
}();
//继承自Observable
Ext.extend(JpkFrame.common.HtmlFrame, Ext.util.Observable, {

    /**
     * @private
     * @param {Object} url iframe的src
     * 设置iframe的src
     */
    setSrc: function(url){
        var src = url;
        //_frameAction 标志，表示是否正在进行某些动作  _domReady  标志，检测frameEl的document的dom是否加载
        this._frameAction = this._domReady = false;
        //显示进度条
        this.showMask();
        //设置src
		//这里需要延时执行，否则浏览器抛出异常?
        (function(){
            var s = typeof src == 'function' ? src() || '' : src;
            try {
                this._frameAction = true;
                this.dom.src = s;
                this.checkDOM();
            } 
            catch (ex) {
                throw '设置src时，出现异常！ ' + ex;
            }
        }).defer(100, this);
        //返回该对象
        return this;
    },
    /**
     * @private
     * 自定义destroy方法
     */
    destroy: function(){
        this.removeAllListeners();
        if (this.dom) {
            this.dom[this._eventName] = null;
            JpkFrame.common.HtmlFrame.Manager.deRegister(this);
            if (Ext.isIE && this.dom.src) {
				this.dom.src = 'javascript:false';
            }
			//清除_maskEl
			if(this._maskEl){
				this._maskEl=null;
				delete this._maskEl;
			} 
            this.remove();
        }
        //再次确定是否删除这个dom
        if (this.dom) {
            this.dom = null; //消除引用
            delete this.dom;
        }
        //清除loadMask
        if (this.loadMask) {
            Ext.apply(this.loadMask, {
                maskEl: null,
                masker: null
            });
			this.loadMask=null;
			delete this.loadMask;
        }
    },
    /**
     * @private
     * @param {Object} showOpt
     * 显示进度条
     */
    showMask: function(){
        //console.log('1showMask');
        var slmask;
        if ((slmask = this.loadMask)&&!slmask.disabled) {
            //被遮蔽的Element对象
            slmask.masker ||(slmask.masker = Ext.get(slmask.maskEl || this.dom.parentNode ||this.wrap({ tag: 'div',style: {position: 'relative' }})));
            slmask.masker.mask.defer(5, slmask.masker, [slmask.msg, slmask.msgCls]);
        }
    },
    /**
     * @private
     * @param {Object} forced
     * 隐藏进度条
     */
    hideMask: function(){
        //console.log('hideMask');
        var hlmask;
        if ((hlmask = this.loadMask) && hlmask.masker&&!hlmask.disabled) {
            hlmask.masker.unmask.defer(5, hlmask.masker);
        }
    },
	print:function(){
		try{
			var win=this.getWindow();
			if(Ext.isIE){
				win.focus();
			}
			win.print();
		}catch(ex){
			throw '打印异常 : '+(ex.description||ex.message||ex);
		}
	},
	/**
	 * private
	 * 返回body
	 */
	getBody:function(){
		var dc;
		return (dc=this.getDocument())?dc.body:null;
	},
   /**
    * @private
    * 返回document对象
    */
    getDocument: function(){
        //console.log('getDocument');
        var win = this.getWindow(), doc = null;
        try {
            doc = (Ext.isIE && win ? win.document : null) ||this.dom.contentDocument ||window.frames[this.id].document ||null;
        } 
        catch (gdEx) {
            //可能由于某些限制，导致获取document失败
            return false;
        }
        return doc;
    },
    /**
     * @private
     * 返回window对象
     */
    getWindow: function(){
        //console.log('getWindow');
        var dom = this.dom, win = null;
        try {
            win = dom.contentWindow || window.frames[dom.name] || null;
        } 
        catch (gwEx) {
        }
        return win;
    },
	
    /**
     * @private
     * @param {Object} e
     * 根据iframe地readyState（IE）/onload事件，来确定iframeEl的加载状态
     * 自定义domready，domfail两种状态,来表示dom的加载状态
     * 注：dom的状态只能在同域(Same Domain)的时候判断
     */
    loadHandler: function(e){
        //console.log('loadHandler');
        if (!this._frameAction) {
            return;
        }
        var hstatus = (e && typeof e.type !== 'undefined' ? e.type : this.dom.readyState);
		//console.log('status : '+hstatus+new Date());
        switch (hstatus) {
            case 'loading': //IE
            case 'interactive': //IE
                break;
            case 'domready': //JpkFrame.common.HtmlPanel
                if (this._domReady) 
                    return;
                this._domReady = true;
            case 'domfail': //JpkFrame.common.HtmlPanel
                this._domReady = true;
                this.hideMask();
                
                break;
            case 'load': //Gecko(FireFox,Mozalia的内核), Opera
            case 'complete': //IE
                /**
             	整个页面加载完毕后，可能还有某些Dom未加载(至于到底有没有这种情况，我也不知道)
            	强制认为整个页面的dom已全部加载,checkDOM方法(如果还在执行)将不再调用该方法（loadHandler）
             	可以根据实际情况来调整，对于Same Domain（同域），如果
             	iframe中的page包含大量的图片，flash等，，可以等dom节点加载完毕即关闭进度条，而不是整个page加载完毕
                */
                if (!this._domReady) {
                    this.loadHandler({
                        type: 'domready'
                    });
                }
                //关闭进度条
                this.hideMask();
                //页面加载完毕后，复位
                this._frameAction = false;
                this._domReady = false;
                break;
            default:
        }
    },
    /**
     * @private
     * @param {Object} win
     * 检测DOM的加载情况
     */
    checkDOM: function(win){
        //console.log('checkDOM');
		//可根据需要设置
        if (!this._frameAction||Ext.isGecko) {
            return;
        }
        //初始化
        var n = 0, win = win || this.getWindow(), manager = this, domReady = false, max = 300;
        var poll = function(){ //轮番查询
            try {
                var doc = manager.getDocument(), body = null;
                if (doc === false) {
                    //主要是不同域引起的
                    throw "Document Access Denied";
                }
                if (!manager._domReady) {
                    domReady = !!(doc && doc.getElementsByTagName);
                    domReady = domReady && (body = doc.getElementsByTagName('body')[0]) && !!body.innerHTML.length;
                }
            } 
            catch (ex) {
                //可能由于浏览器到某些规则的影响，这里判断为已经加载完毕
                n = max;
            }
            //如果查询次数达到max（3秒后），停止查询，实际上，7轮查询就应该可以确定dom的加载情况
            if (!manager._frameAction || manager._domReady) 
                return;
            if ((++n < max) && !domReady) {
                //重新poll
                setTimeout(arguments.callee, 10);
                return;
            }
			//console.log('checkdom1 : '+!(doc && doc.getElementsByTagName));
			//console.log('checkdom2 : '+!!(doc && doc.getElementsByTagName));
            manager.loadHandler({
                type: domReady ? 'domready' : 'domfail'
            });
        };
        setTimeout(poll, 40);
    }
    
});
/**
 * @class JpkFrame.common.HtmlPanel
 * @description 这个JpkFrame.common.HtmlPanel很好的解决了
 * 内存释放的问题，并且加载的时候有进度条提示
 */
JpkFrame.common.HtmlPanel = Ext.extend(Ext.Panel, {
	//frame设置
    defaultSrc: null,
	frameConfig:null,
    frameStyle: {
        overflow: 'auto'
    },
    loadMask: false,
    /**
     * 初始化
     */
    initComponent: function(){
        //console.log('initComponent');
        this.bodyCfg ||
        (this.bodyCfg = {
            cls: 'html-iframe-mask',
            children: [Ext.apply({
                tag: 'iframe',
                frameborder: 0,
                cls: 'html-iframe',
                style:this.frameStyle || null
            	}, 
				this.frameConfig ? this.frameConfig.autoCreate ||{} : false, 
				Ext.isIE && Ext.isSecure ? { src: Ext.SSL_SECURE_URL } : false)
			]
        });
        JpkFrame.common.HtmlPanel.superclass.initComponent.call(this);
    },
    /**
     *
     */
    beforeDestroy: function(){
        //console.log('beforeDestroy');
        if (this.rendered) {
            if (this.tools) {
                for (var k in this.tools) {
                    Ext.destroy(this.tools[k]);
                }
            }
            if (this.header && this.headerAsText) {
                var s;
                if (s = this.header.child('span')) 
                    s.remove();
                this.header.update('');
            }

            Ext.each(['iframe', 'shim', 'header', 'topToolbar', 'bottomToolbar', 'footer', 'loadMask', 'body', 'bwrap'], function(elName){
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
        }
        JpkFrame.common.HtmlPanel.superclass.beforeDestroy.call(this);
    },
    /**
     *
     */
    onDestroy: function(){
        //console.log('onDestroy');
        JpkFrame.common.HtmlPanel.superclass.onDestroy.call(this);
    },
    /**
     *渲染
     */
    onRender: function(ct, position){
        //console.log('onRender');
        JpkFrame.common.HtmlPanel.superclass.onRender.call(this, ct, position);
        if (this.iframe = this.body.child('iframe.html-iframe')) {
            this.iframe.ownerCt = this;
            if (this.loadMask) {
                this.loadMask = Ext.apply({
                    maskEl: this.body
                }, this.loadMask);
            }
            if (this.iframe = new JpkFrame.common.HtmlFrame(this.iframe, {
                loadMask: this.loadMask,
                style: this.frameStyle
            })) {
                this.loadMask = this.iframe.loadMask;
            }
        }
    },
	/**
	 * @public
	 * @param {Object/String} url
	 */
    setSrc: function(url){
        //console.log('setSrc');
		if(!url){
			return this;
		}
        if (this.rendered && this.iframe) {
            this.iframe.setSrc(url);
        }
        return this;
    },
	/**
	 * 打印
	 */
	print:function(){
		try{
			if(this.rendered&&this.iframe){
				this.iframe.print();
			}
		}catch(ex){throw ex;}
	},
	/**
     * 这个是Ext 2.2新增的事件
     * 可用于初始化一些东西
     */
    afterRender: function(container){
        JpkFrame.common.HtmlPanel.superclass.afterRender.call(this);
        if (this.iframe) {
            if (this.defaultSrc) {
                this.setSrc(this.defaultSrc);
            }
        }
    }
});
Ext.reg('htmlpanel', JpkFrame.common.HtmlPanel);
//初始化css样式
var CSS = Ext.util.CSS, rules = [];
CSS.getRule('.html-loading div') || rules.push(".html-loading div{font-size:13px;padding:15px 10px 5px 10px;background: #fbfbfb url( 'jslib/images/menu/loading-balls.gif' ) no-repeat 5px 5px;background-position:top;line-height: 16px;}");
CSS.getRule('.html-iframe') || (rules.push('.html-iframe {height:100%;width:100%;overflow:auto;}'));
CSS.getRule('.html-iframe-mask') || (rules.push('.html-iframe-mask{width:100%;height:100%;position:relative;}'));
if (!!rules.length) {
    CSS.createStyleSheet(rules.join(''));
}	
})();
