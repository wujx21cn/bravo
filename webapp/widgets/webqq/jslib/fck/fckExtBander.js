//如果修改fck配置的话，有时候会发现配置没有生效，
//此时，把已经部署的工程删除！（不是覆盖），重新部署
Ext.namespace('Ext.ux.form');

/**
 * FCKeditor 初始配置信息
 *
 * @type {Object}
 */
var oFCKeditorOptions = {
    BasePath: '/JingpinkeSystemApp/fckeditor/',
    Config: {
        BaseHref: window.location,
        SkinPath: '/JingpinkeSystemApp/fckeditor/editor/skins/office2003/',
        ProcessHTMLEntities: true,
        ProcessNumericEntities: false,
        plain: true,
        ToolbarStartExpanded: true
        //建议把这些属性属性写到fckCustomCfg.js中
        //毕竟这个js只是简单的wrapper，
    },
    CustomConfigurationsPath: '/JingpinkeSystemApp/jslib/fck/fckCustomCfg.js',//自定义配置
    ToolbarSet: 'Custom'//自定义
};

/**
 * Ext FCKeditor
 *
 * @param {Object}
 *  config 配置信息
 */
Ext.ux.form.FCKeditor = function(config){
    this.config = config;
    Ext.ux.form.FCKeditor.superclass.constructor.call(this, config);
    /**
     * 通知FCKeditor是否实例化
     * notice the component's FCKeditor instance inited
     * @type Boolean
     */
    this.instanceLoaded = false;
    /**
     * 实例值
     * the component's FCKeditor instance value
     * @type String
     */
    this.instanceValue = '';
    /**
     * 组件的FCKeditor实例
     * @type {FCKeditor}
     */
    this.editorInstance = undefined;
};
/**
 * Ext FCKeditor
 *
 * @class Ext.ux.form.FCKeditor
 * @extends Ext.form.TextArea
 */
Ext.extend(Ext.ux.form.FCKeditor, Ext.form.TextArea, {
    /**
     * 初始化事件
     */
    initEvents: function(){
        this.on('destroy', function(){
            if (typeof this.editorInstance != 'undefined') {
                delete this.editorInstance;
            }
        });
    },
    onRender: function(ct, position){
        if (!this.el) {
            this.defaultAutoCreate = {
                tag: "textarea",
                style: "width:100px;height:60px;",//自定
                autocomplete: "off"
            };
        }
        Ext.form.TextArea.superclass.onRender.call(this, ct, position);
        this.hideMode = "visibility";
        this.hidden = true;
        if (this.grow) {
            this.textSizeEl = Ext.DomHelper.append(document.body, {
                tag: "pre",
                cls: "x-form-grow-sizer"
            });
            if (this.preventScrollbars) {
                this.el.setStyle("overflow", "hidden");
            }
            this.el.setHeight(this.growMin);
        }
        setTimeout("loadFCKeditor('" + this.id + "'," + this.config.height + ");", 100); // Change
    },
    /**
     * 设置是否已经加载过此控件
     * set FCKeditor instance is inited
     * @param {Boolean} v
     */
    setIsLoaded: function(v){
        this.instanceLoaded = v;
    },
    /**
     * 获取是否已实例化过此控件
     * get FCKeditor instance is inited
     * @return {Boolean}
     */
    getIsLoaded: function(){
        return this.instanceLoaded;
    },
	/**
	 * 
	 */
	focus:function(){
		if (this.instanceLoaded) {
            this.editorInstance.Focus();
        }
	},
    /**
     *
     * @param {String} value
     */
    setValue: function(value){
        this.instanceValue = value;
        if (this.instanceLoaded) {
            this.FCKeditorSetValue(value);// Change this.name
        }
        Ext.form.TextArea.superclass.setValue.apply(this, [value]);
    },
    /**
     *
     * @return {String}
     */
    getValue: function(){
        if (this.instanceLoaded) {
            value = this.FCKeditorGetValue(); // Change this.name to this.id
            Ext.form.TextArea.superclass.setValue.apply(this, [value]);
            return Ext.form.TextArea.superclass.getValue.call(this); // Change getValue(this) to
        }
        else {
            return this.instanceValue;
        }
    },
    /**
     *
     * @return {String}
     */
    getRawValue: function(){
        if (this.instanceLoaded) {
            value = this.FCKeditorGetValue(); // Change this.name to this.id
            Ext.form.TextArea.superclass.setRawValue.apply(this, [value]);
            return Ext.form.TextArea.superclass.getRawValue.call(this); // Change getValue(this) to
        }
        else {
            return this.instanceValue;
        }
    },
    /**
     * 设置FCKeditor实例的值
     * @param {String} value
     */
    FCKeditorSetValue: function(value){
        if (this.instanceLoaded == false) {
            return;
        }
        // 这里主要避免ie提示没有权限
        //原因：由于fck实例化未完成，便进行赋值，(程序设计的问题？)
        //我现在找到的最好办法便是延时执行，使用defer或者setTimeout,延时时间20~80毫秒即可
        //如果你不放心，可以用循环方法对其赋值，但总时间最好不要超过1秒，也就是循环判断十几次就够了,
		//但我认为这根本没有必要，就像我下面写的程序，实际上里面的循环根本起不到“避免ie提示没有权限”的作用，起作用的还是延时
		//毕竟fckeditor不是ext的原生组件，ext中使用fckeditor只是权宜之计，有兴趣的话参考参考fckBander2.js
        //我发现ie下出现权限问题时，使用延时执行，效果特好
        var n = 0;
        var editor = this.editorInstance; // update var editor
        (function(){
            try {
                if (editor.EditorDocument.body) {
                        editor.SetData(value);
                }
            } 
            catch (ex) {
                if (n < 5) {
                    setTimeout(arguments.callee, 100);
                    n++;
                }
                else 
                    throw '给fckeditor赋值失败!';
            }
        }).defer(100);//可根据实际情况确定
    },
    /**
     * 获取FCKeditor实例的值
     * @return {String}
     */
    FCKeditorGetValue: function(){
        var data = '';
        if (this.instanceLoaded == false) {
            return data;
        }
        data = this.editorInstance.GetData();
        return data;
    }
});
Ext.reg('fckeditor', Ext.ux.form.FCKeditor);

/**
 * 实例化FCKeditor
 *
 * @param {String}
 *            element el id
 * @param {Number}
 *            height
 */
function loadFCKeditor(element, height){
    var oFCKeditor = new FCKeditor(element);
    oFCKeditor.BasePath = oFCKeditorOptions.BasePath;
    oFCKeditor.Height = height;
    oFCKeditor.Config = oFCKeditorOptions.Config;
    if (oFCKeditorOptions.CustomConfigurationsPath) {
        oFCKeditor.Config.CustomConfigurationsPath = oFCKeditorOptions.CustomConfigurationsPath;
    }
    oFCKeditor.ToolbarSet = oFCKeditorOptions.ToolbarSet;
    oFCKeditor.ReplaceTextarea();
}

/**
 * FCKeditor API: 当FCKeditor实例化完成时执行
 *
 * @param {FCKeditor} editorInstance
 */
function FCKeditor_OnComplete(editorInstance){
    /**
     * @type {Ext.ux.form.FCKeditor}
     */
    var activeEditor = Ext.getCmp(editorInstance.Name);
    activeEditor.editorInstance = editorInstance;
    activeEditor.instanceLoaded = true;
    activeEditor.FCKeditorSetValue(activeEditor.instanceValue);
    //可根据需要是否使用以下语句
    //editorInstance.Events.AttachEvent('OnBlur', FCKeditor_OnBlur);
    //editorInstance.Events.AttachEvent('OnFocus', FCKeditor_OnFocus);
}

function FCKeditor_OnBlur(editorInstance){
    editorInstance.ToolbarSet.Collapse();
}

function FCKeditor_OnFocus(editorInstance){
    editorInstance.ToolbarSet.Expand();
}

/**

 * 使用方法：

 * Ext.onReady(function(){

 var win = new Ext.Window({

 title: 'Ext和Fckeditor结合示例',

 width: 800,

 height: 400,

 closable: false,

 layout: 'fit',

 border: false,

 bodyBorder: false,

 items: [new Ext.ux.form.FCKeditor({

 id: 'te',

 height: 333//高度请根据实际情况自定

 })],

 buttonAlign: 'left',

 buttons: [{

 text: '创建',

 handler: function(){

 

 var f = Ext.getCmp('te').getValue();

 Ext.Ajax.request({

 url: 'html.do',

 params: {

 action: 'save',

 content: f

 },

 success: function(response){

 var result = Ext.util.JSON.decode(response.responseText);

 if (result.success == 'true') {

 Ext.Msg.alert('保存成功！');

 }

 else {

 Ext.Msg.alert('保存失败！');

 }

 },

 failure: function(response, e){

 

 }

 });

 }

 }, {

 text: '获取',

 handler: function(){

 Ext.Ajax.request({

 url: 'html.do',

 params: {

 action: 'get',

 parentId: '1'

 },

 success: function(response){

 var result = Ext.util.JSON.decode(response.responseText);

 if (result.success == 'true') {

 //alert(typeof(result.msg));

 //alert(result.msg);

 Ext.getCmp('te').setValue(result.msg);

 }

 else {

 }

 },

 failure: function(response, e){


 }

 });

 }
 }]

 });

 win.show();

 });

 */

