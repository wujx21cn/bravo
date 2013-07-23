/**
 * @class JpkFrame.common.JpkField
 * @自定义的一个field
 */
Ext.namespace('JpkFrame.common');

JpkFrame.common.JpkField = Ext.extend(Ext.form.TextField, {
    ctCls: 'JpkFieldCSS',
    initComponent: function() {
        JpkFrame.common.JpkField.superclass.initComponent.call(this);
    }
});

Ext.reg('JpkField', JpkFrame.common.JpkField);