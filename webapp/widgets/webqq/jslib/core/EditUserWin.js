/**
 * @update 
 * @version JPK System 2.0 beta
 * @author songyinghao
 * @sourceCode utf-8
 * @license LGPL,All Rights Reserved.
 */
/**
 * @class EditUserWin
 * 编辑用户信息的一个窗口
 */
EditUserWin = function(config){
    EditUserWin.superclass.constructor.call(this, Ext.apply({
        title: '更改信息',
        width: 280,
        height: 230,
        plain: true,
        border: false,
        bodyBorder: false,
        layout: 'fit',
        modal: true
    }, config ||
    {}));
}
Ext.extend(EditUserWin, Ext.Window, {
    /**
     *
     */
    afterRender: function(){
        EditUserWin.superclass.afterRender.call(this);
        this.formEl ||
        (this.formEl = this.add({
            xtype: 'form',
			baseCls : 'x-plain',
			bodyStyle:'padding:5px 0 0 0',
            labelWidth: 65,
            labelAlign: 'right',
			//standardSubmit: true,
            defaults: {
                width: 170
            },
            defaultType: 'JpkField',
            items: [{
                fieldLabel: '原密码',
                inputType: 'password',
                allowBlank: false,
                name: 'old_pass'
            }, {
                fieldLabel: '新密码',
                inputType: 'password',
                allowBlank: false,
                name: 'edit_pass',
                id: 'pass'
            }, {
                fieldLabel: '确认密码',
                inputType: 'password',
                name: 'edit_cfrm_pass',
                vtype: 'password',
                initialPassField: 'pass'
            }, {
                xtype: 'combo',
                fieldLabel: '安全提问',
                id: 'edit_combo',
                name: 'edit_question',
                displayField: 'question',
                valueField: 'question_id',
                value: '0',
                //用于和后台交互
                hiddenName: 'edit_question_id',
                typeAhead: true,
                mode: 'local',
                width: 170,
                ctCls: 'JpkFieldCSS',
                forceSelection: true,
                triggerAction: 'all',
                selectOnFocus: true,
                store: new Ext.data.SimpleStore({
                    fields: ['question_id', 'question'],
                    data: JpkFrame.data.reg_combo_data
                }),
                listeners: {
                    select: function(combo, record, index){
                        if (index > 0) {
                            Ext.getCmp('edit_answer').show();
                        }
                        if (index == 0) {
                            var field = Ext.getCmp('edit_answer');
                            field.hide();
                            field.reset();
                        }
                    }
                }
            }, {
                fieldLabel: '答案',
                hidden: true,
                name: 'edit_answer',
                id: 'edit_answer'
            }],
            buttons: [{
                text: '保存修改',
                handler: function(){
                    this.ownerCt.ownerCt.updateCon();
                }
            }]
        }));
        this.getCon(this.formEl);
    },
    /**
     * 更新用户信息
     */
    updateCon: function(){
        var ew = this;
        if (this.formEl.form.isValid()) {
            this.formEl.form.submit({
                waitTitle: '请稍后',
                waitMsg: '正在保存.....',
                url: 'edit.do?m=update',
                method: 'post',
                params: {
                    user: ew.user
                },
                success: function(form, action){
                    var result = action.result;
                    if (result && (!!result.success) && (result.success == '1')) {
                        ew.close();
                    }
                    else {
                        Ext.Msg.alert('信息提示', result.msg || '系统错误，请再试一次！');
                    }
                },
                failure: function(form, action){
                    Ext.Msg.alert('信息提示', action.result.msg || '系统错误，请再试一次！');
                }
            });
        }
    },
    /**
     * 获取信息
     */
    getCon: function(form){
        var ew = this;
        Ext.Ajax.request({
            url: 'edit.do?m=get',
            method: 'post',
            params: {
                user: ew.user
            },
            success: function(response){
                var result = eval('(' + response.responseText + ')');
                if (result && (!!result.success) && (result.success == '1') && result.msg) {
                    result = eval('(' + result.msg + ')');
                    if (result.question && (result.question > 0)) {
                        form.findById('edit_combo').setValue(result.question);
                        var answer = form.findById('edit_answer');
                        answer.show();
                        answer.setValue(result.answer);
                    }
                }
                else {
                    Ext.Msg.alert('信息提示', '系统错误，请再试一次！');
                }
            },
            failure: function(response, e){
                Ext.Msg.alert('信息提示', '系统错误，请再试一次！');
            }
        });
    },
    /**
     *
     */
    beforeDestroy: function(){
        if (this.rendered) {
			
			if(this.formEl){
				this.remove(this.formEl);
			}
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
            Ext.each(['formEl','header', 'body'], function(elName){
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
		EditUserWin.superclass.beforeDestroy.call(this);
    }
    
});
