/**
 * @update 
 * @version JPK System 2.0 beta
 * @author songyinghao
 * @sourceCode utf-8
 * @license LGPL,All Rights Reserved.
 */
/**
 * @class JpkFrame.common.JpkImgLabel
 * 图像label 点击时触发点击事件
 */
Ext.namespace('JpkFrame.common');

JpkFrame.common.JpkImgLabel = Ext.extend(Ext.BoxComponent, {
    initComponent: function() {
        JpkFrame.common.JpkImgLabel.superclass.initComponent.call(this);
        this.addEvents('click');
    },
    onRender: function(ct, position) {

        if (!this.el) {
            this.el = document.createElement('img');
            this.el.src = this.src;
            if (this.forId) {
                this.el.setAttribute('htmlFor', this.forId);
            }
        }

        JpkFrame.common.JpkImgLabel.superclass.onRender.call(this, ct, position);

        Ext.fly(this.el).on('click',function() {
            this.fireEvent('click', this);
        },
        this);
    }
});

Ext.reg('JpkImgLabel', JpkFrame.common.JpkImgLabel);