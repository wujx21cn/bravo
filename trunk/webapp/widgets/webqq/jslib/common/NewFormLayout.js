
/*
 * @description 由于官方未提供同时隐藏和显示field和label的方法，这里重写了官方的Form布局,
 * 使用方法: yourfield.destroy();yourfield.hide();yourfield.show();yourfield.disable();yourfield.enable()
 */
Ext.override(Ext.layout.FormLayout, {
    renderItem: function(c, position, target){
        if (c && !c.rendered && c.isFormField && c.inputType != 'hidden') {
            var args = [c.id, c.fieldLabel, c.labelStyle || this.labelStyle || '', this.elementStyle || '', typeof c.labelSeparator == 'undefined' ? this.labelSeparator : c.labelSeparator, (c.itemCls || this.container.itemCls || '') + (c.hideLabel ? ' x-hide-label' : ''), c.clearCls || 'x-form-clear-left'];
            if (typeof position == 'number') {
                position = target.dom.childNodes[position] || null;
            }
            if (position) {
                c.formItem = this.fieldTpl.insertBefore(position, args, true);
            }
            else {
                c.formItem = this.fieldTpl.append(target, args, true);
            }
            c.actionMode = 'formItem';
            c.render('x-form-el-' + c.id);
            c.container = c.formItem;
            c.actionMode = 'container';
        }
        else {
            Ext.layout.FormLayout.superclass.renderItem.apply(this, arguments);
        }
    }
});
/*
 * @description 对于combo box 需要重写它，以实现同时隐藏和显示field和label
 */
Ext.override(Ext.form.TriggerField, {
    actionMode: 'wrap',
    onShow: Ext.form.TriggerField.superclass.onShow,
    onHide: Ext.form.TriggerField.superclass.onHide
});
