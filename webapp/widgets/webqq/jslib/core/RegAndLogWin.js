/**
 * @update 
 * @version JPK System 2.0 beta
 * @author songyinghao
 * @sourceCode utf-8
 * @license LGPL,All Rights Reserved.
 */
/**
 * @class RegAndLogWin
 * 登录/注册/注册说明  窗口
 * @param {Object} config
 */
RegAndLogWin = function(config){
    //config中应包含type属性 比如 {type:'log'}
    //type log 登录 reg 注册 默认为登录
    Ext.applyIf(config, {
        type: 'log'
    });
    RegAndLogWin.superclass.constructor.call(this, Ext.apply({
        id: 'ralwin',
        title: '精品课系统',
        height: 400,
        width: 600,
        border: false,
        plain: true,
        deferredRender: true,
        bodyBorder: false,
        modal: true,
        resizable: false,
        draggable: false,
        //layout:'card',
        //如果不喜欢滑动效果，请使用上面的card
        layout: 'slide',
        activeItem: (config.type == 'log') ? 0 : 1,
        layoutConfig: {
            easing: 'easeout',
            duration: 0.4,
            opacity: 0.1
        }
    }, config ||{}));
}
Ext.extend(RegAndLogWin, Ext.Window, {
    /**
     *
     */
    initComponent: function(){
        RegAndLogWin.superclass.initComponent.call(this);
        this.addEvents({
            'logsuccess': true,
            'regsuccess': true
        });
    },
    /**
     * 初始化事件
     */
    initEvents: function(){
        RegAndLogWin.superclass.initEvents.call(this);
    },
    /**
     *
     */
    afterRender: function(){
        RegAndLogWin.superclass.afterRender.call(this);
        
        //登录模块
        this.add(this.createLoginPanel());
        //注册模块
        this.add(this.createRegPanel());
        //注册说明 模块
        this.add(this.createRegDescPanel());
        
        this.on('show', function(){
            this.showVerifyImg(this.type);
        }, this);
    },
    /**
     * @method createLoginPanel
     * @private
     * @desc create the login panel of the win
     */
    createLoginPanel: function(){
        var log_combo = new Ext.form.ComboBox({
            fieldLabel: '登录身份',
            id: 'log_combo',
            name: 'log_role',
            displayField: 'role_name',
            typeAhead: true,
            valueField: 'role_id',
            ctCls: 'JpkFieldCSS',
            mode: 'local',
            width: 170,
            editable: false,
            hiddenName: 'log_role_id',
            forceSelection: true,
            triggerAction: 'all',
            selectOnFocus: true,
            store: new Ext.data.SimpleStore({
                fields: ['role_id', 'role_name'],
                data: JpkFrame.data.log_combo_data
            }),
            listeners: {
                select: function(combo, record, index){
                }
            }
        });
        
        log_combo.setValue('1');
        //获取当前对象
        var ralWin = this;
        
        var log_form = new Ext.FormPanel({
            x: 50,
            y: 90,
            id: 'log_form',
            labelWidth: 60,
            width: 285,
            height: 240,
            labelAlign: 'left',
            defaults: {
                width: 170
            },
            defaultType: 'JpkField',
            items: [{
                fieldLabel: '用户名',
                allowBlank: false,
                name: 'log_email'
                //vtype: 'email'
            }, {
                fieldLabel: '密码',
                allowBlank: false,
                inputType: 'password',
                name: 'log_pass'
            }, log_combo, {
                xtype: 'JpkImgLabel',
                //src: 'getVerifyImg.do?' + Math.random(),
                height: 35,
                id: 'log_img',
                style: 'margin-bottom: 8px;margin-left:65px;',
                listeners: {
                    click: function(){
                        this.el.set({
                            src: 'getVerifyImg.do?' + Math.random()
                        });
                    }
                }
            }, {
                fieldLabel: '验证码',
                ctCls: 'JpkFieldCSS',
                allowBlank: false,
                name: 'log_randCode'
            }, {
                xtype: 'hidden',
                name: 'log_remember',
                id: 'log_remember',
                //'1' 记住 ‘0’ 不记住
                value: '1'
            }],
            keys: {
                key: 13,
                fn: function(){
                    ralWin.log_submit(log_form);
                }
            },
            buttons: [{
                text: '登陆',
                handler: function(){
                    ralWin.log_submit(log_form);
                }
            }, new Ext.form.Checkbox({
                id: 'log_form_checkbox',
                ctCls: 'log_form_checkbox',
                checked: true,
                boxLabel: '记住我的状态',
                listeners: {
                    check: function(checkbox, checked){
                        this.ownerCt.findById('log_remember').setValue(checked ? '1' : '0');
                    }
                }
            })]
        
        });
        
        var log_title_label = new Ext.form.Label({
            x: 50,
            y: 35,
            id: 'log_title_label',
            autoHeight: true,
            autoWidth: true,
			//cls: 'x-form-item',
            html: '<h1 style="font-size:130%">用户登陆</h1>'
        });
        var log_info_label = new Ext.form.Label({
            x: 385,
            y: 82,
            width: 195,
            height: 210,
            id: 'log_info_label',
            ctCls: 'log_info_label',
            header: false,
            html: "<br><br><br>没有账户？<a onclick=Ext.getCmp('ralwin').changeItem('reg'); href='javascript:;'>注册</a>" +
            "<br><br><font size='2'>忘记密码，<a href='javascript:;'>找回密码</a>" +
            "<br>无法登陆，<a href='javascript:;'>清除登陆状态</a></font>"
        });
        
        var log_panel = new Ext.Panel({
            id: 'log_panel',
            header: false,
            height: 371,
            width: 588,
			baseCls : 'x-plain',
            hideMode: 'offsets',
            layout: 'absolute',
            items: [log_title_label, log_form, log_info_label]
        });
        
        return log_panel;
    },
    /**
     * @method this.log_submit
     * @private
     * @description submit the login information
     */
    log_submit: function(form){
        //获取当前对象
        var ralWin = this;
        if (form.form.isValid()) {
            form.form.submit({
                waitTitle: '请稍后',
                waitMsg: '正在登陆.....',
                url: 'login.do',
                method: 'post',
                success: function(form, action){
                    var result = action.result;
                    if (result && (!!result.success) && (result.success == '1') && result.msg) {
                        result = eval('(' + result.msg + ')');
                        var user = result.user;
                        var tools = result.tools;
                        ralWin.fireEvent('logsuccess',tools,user);
                        ralWin.close();
                    }
                    else {
						var height=Ext.isIE?'4':'3';
						var info='<img src="jslib/images/error.gif" width="16" height="16" style="float:left;margin:4px 0 0 4px" />'+
						'<span style="float:left;margin:'+height+'px 0 0 8px">'+result.msg+'</span>'
						Ext.getCmp('log_title_label').setText(info,false);
                    }
                },
                failure: function(form, action){
                    Ext.Msg.alert('信息提示', action.result.msg||'系统错误，请再试一次！');
                }
            });
        }
    },
    /**
     * @method createRegPanel
     * @private
     * @desc create the reg panel of the win
     */
    createRegPanel: function(){
        var reg_combo = new Ext.form.ComboBox({
            fieldLabel: '安全提问',
            id: 'reg_combo',
            name: 'reg_question',
            displayField: 'question',
            valueField: 'q_id',
            //用于和后台交互
            hiddenName: 'reg_question_id',
            typeAhead: true,
            mode: 'local',
            width: 170,
            ctCls: 'JpkFieldCSS',
            forceSelection: true,
            triggerAction: 'all',
            selectOnFocus: true,
            store: new Ext.data.SimpleStore({
                fields: ['q_id', 'question'],
                data: JpkFrame.data.reg_combo_data
            }),
            listeners: {
                select: function(combo, record, index){
                    if (index > 0) {
                        Ext.getCmp('reg_answer').show();
                    }
                    if (index == 0) {
                        var field = Ext.getCmp('reg_answer');
                        field.hide();
                        field.reset();
                    }
                }
            }
        });
        reg_combo.setValue('0');
        //获取当前对象
        var ralWin = this;
        
        var reg_form = new Ext.FormPanel({
            x: 50,
            y: 90,
            id: 'reg_form',
            labelWidth: 60,
            width: 285,
            height: 240,
            labelAlign: 'left',
            defaults: {
                width: 170
            },
            defaultType: 'JpkField',
            items: [{
                fieldLabel: 'Email',
                allowBlank: false,
                name: 'reg_email',
                vtype: 'email'
            }, {
                fieldLabel: '密码',
                inputType: 'password',
                allowBlank: false,
                name: 'reg_pass',
                id: 'pass'
            }, {
                fieldLabel: '确认密码',
                inputType: 'password',
                name: 'reg_cfrm_pass',
                vtype: 'password',
                initialPassField: 'pass'
            }, reg_combo, {
                fieldLabel: '答案',
                hidden: true,
                name: 'reg_answer',
                id: 'reg_answer'
            }, {
                fieldLabel: '验证码',
                width: 82,
                ctCls: 'JpkFieldCSS',
                allowBlank: false,
                name: 'reg_randCode',
                id: 'reg_randCode'
            }],
            keys: {
                key: 13,
                fn: function(){
                    ralWin.reg_submit(reg_form);
                }
            },
            buttons: [{
                text: '提交',
                handler: function(){
                    ralWin.reg_submit(reg_form);
                }
            }, new Ext.form.Checkbox({
                id: 'reg_form_checkbox',
                ctCls: 'reg_form_checkbox',
                checked: true
            }), new Ext.form.Label({
                xtype: 'label',
                id: 'reg_form_checklabel',
                ctCls: 'reg_form_checklabel',
                html: "同意&nbsp<a onclick=Ext.getCmp('ralwin').changeItem('regDesc'); href='javascript:;'>网站服务条款</a>"
            })]
        });
        
        var reg_title_label = new Ext.form.Label({
            x: 50,
            y: 35,
            autoHeight: true,
            autoWidth: true,
            id: 'reg_title_label',
            html: '<h1 style="font-size:130%">用户注册</h1>'
        });
        
        var reg_info_label = new Ext.form.Label({
            x: 385,
            y: 82,
            width: 195,
            height: 210,
            id: 'reg_info_label',
            ctCls: 'reg_info_label',
            header: false,
            html: "<br><br><br><br>已有账户？<a onclick=Ext.getCmp('ralwin').changeItem('log'); href='javascript:;'>现在登陆</a>"
        });
        
        var reg_panel = new Ext.Panel({
            id: 'reg_panel',
            header: false,
            height: 371,
            width: 588,
			baseCls : 'x-plain',
            hideMode: 'offsets',
            layout: 'absolute',
            items: [reg_title_label, reg_form, reg_info_label]
        });
        
        return reg_panel;
    },
    /**
     * @method reg_submit
     * @private
     * @description submit the login information
     */
    reg_submit: function(form){
    
        var ralWin = this;
        
        if (form.form.isValid()) {
            form.form.submit({
                waitTitle: '请稍后',
                waitMsg: '正在提交.....',
                url: 'reg.do',
                method: 'post',
                success: function(form, action){
                    var result = action.result;
                    if (result && (!!result.success) && (result.success == '1') && result.msg) {
                        result = eval('(' + result.msg + ')');
                        var user = result.user;
                        var tools = result.tools;
                        ralWin.fireEvent('regsuccess',tools,user);
                        ralWin.close();
                    }
                    else {
						var height=Ext.isIE?'4':'3';
						var info='<img src="jslib/images/error.gif" width="16" height="16" style="float:left;margin:4px 0 0 4px" />'+
						'<span style="float:left;margin:'+height+'px 0 0 8px">'+result.msg+'</span>'
						Ext.getCmp('reg_title_label').setText(info,false);
                    }
                },
                failure: function(form, action){
                   Ext.Msg.alert('信息提示', action.result.msg||'系统错误，请再试一次！');
                } 
            });
        }
    },
    /**
     * @method createRegDescPanel
     * @private
     * @desc create regDesc panel
     */
    createRegDescPanel: function(){
    
        var ralWin = this;
        var regDesc_panel = new Ext.Panel({
            id: 'regDesc_panel',
            header: false,
            height: 371,
            width: 588,
            autoLoad: {
                url: 'jslib/html/regdesc.html',
                scripts: true
            },
            hideMode: 'offsets',
            autoScroll: true,
            buttonAlign: 'center',
            layout: 'fit',
            buttons: [{
                text: '同意',
                handler: function(){
                    ralWin.changeItem('reg');
                }
            }, {
                text: '不同意',
                handler: function(){
                    ralWin.changeItem('none');
                }
            }]
        });
        
        return regDesc_panel;
    },
    /**
     * @method showVerifyImg
     * @param {Object} type
     * @description show the rand code in the form ofimage
     */
    showVerifyImg: function(type){
        if (type == 'log') {
            Ext.getCmp('log_img').el.set({
                src: 'getVerifyImg.do?' + Math.random()
            });
        }
        if (type == 'reg') {
            var randPos = Ext.getDom('reg_randCode');
            var randParent = Ext.get(randPos.parentNode);
            this.createImg('reg_img', randParent);
        }
    },
    /**
     * @method createImg
     * @param {Object} type
     * @param {Object} par
     * @private
     * @description create the image in the panel
     */
    createImg: function(type, par){
        var randParent = par;
        if (document.getElementById(type) != null) {
            this.reloadcode(type);
        }
        else {
            randParent.createChild([{
                tag: 'span',
                html: "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href=javascript:Ext.getCmp('ralwin').reloadcode('" + type + "');>"
            }, {
                tag: 'img',
                src: 'getVerifyImg.do?' + Math.random(),
                align: 'absbottom',
                id: type
            }]);
        }
    },
    /**
     * @method reloadcode
     * @param {Object} type
     * @description change the randcode image
     */
    reloadcode: function(type){
        var verify = document.getElementById(type);
        verify.setAttribute('src', 'getVerifyImg.do?' + Math.random());
    },
    /**
     * @method changeItem
     * @param {Object} type
     * @description change the item of the RegAndLogWin
     */
    changeItem: function(type){
        if (type != null && type == 'reg') {
            this.showVerifyImg(type);
            this.getLayout().setActiveItem(Ext.getCmp('reg_panel'));
        }
        if (type != null && type == 'log') {
            this.showVerifyImg(type);
            this.getLayout().setActiveItem(Ext.getCmp('log_panel'));
        }
        if (type != null && type == 'regDesc') {
        
            this.getLayout().setActiveItem(Ext.getCmp('regDesc_panel'));
        }
        if (type != null && type == 'none') {
            this.close();
        }
    },
    /**
     *
     */
    beforeDestroy: function(){
    
        if (this.rendered) {
        
            Ext.each(['log_panel', 'reg_panel', 'regDesc_panel'], function(id){
                var el;
                if (el = Ext.getCmp(id)) {
                    this.remove(el);
                }
                el = null;
                delete el;
            }, this);
            
            
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
            
            Ext.each(['shim', 'header', 'topToolbar', 'bottomToolbar', 'footer', 'body', 'bwrap'], function(elName){
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
        RegAndLogWin.superclass.beforeDestroy.call(this);
    },
    onDestroy: function(){
        Ext.Window.superclass.onDestroy.call(this);
    }
});
/**
 * password's vtype,intend to verify the password
 */
Ext.apply(Ext.form.VTypes, {
    password: function(val, field){
        if (field.initialPassField) {
            var pwd = Ext.getCmp(field.initialPassField);
            return (val == pwd.getValue());
        }
        return true;
    },
    passwordText: '密码不匹配'
});
