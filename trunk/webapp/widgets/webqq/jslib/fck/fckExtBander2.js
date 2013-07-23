//参考文件
//change the config with the correct dirs
var oFCKeditorOptions = {
    BasePath : '/JingpinkeSystemApp/fckeditor/'
    ,Config : {
    	BaseHref 	: window.location.href
        ,SkinPath	: '/JingpinkeSystemApp/fckeditor/editor/skins/office2003/'
        ,CustomConfigurationsPath	: '/JingpinkeSystemApp/jslib/fck/fckCustomCfg.js'
        ,ProcessHTMLEntities 		: true 
        ,ProcessNumericEntities 	: false
    }
    ,AllowQueryStringDebug 			: false
    ,ToolbarSet 					: 'Custom'
};

Ext.form.FCKeditor = function(config){
    this.config 	= config;
    Ext.form.FCKeditor.superclass.constructor.call(this, config);
    this.MyisLoaded = false;
    this.MyValue	= '';
    this.fckInstance= undefined; // to avoid using FCKAPI, this is a reference to instance created on FCKeditor_OnComplete
};

Ext.extend(Ext.form.FCKeditor, Ext.form.TextArea,  {
    onRender : function(ct, position){
        if(!this.el){
            this.defaultAutoCreate = {
                tag: "textarea",
                style:"width:100px;height:60px;",
                autocomplete: "off"
            };
        }
        Ext.form.TextArea.superclass.onRender.call(this, ct, position);
        if(this.grow){
            this.textSizeEl = Ext.DomHelper.append(document.body, {
                tag: "pre", cls: "x-form-grow-sizer"
            });
            if(this.preventScrollbars){
                this.el.setStyle("overflow", "hidden");
            }
            this.el.setHeight(this.growMin);
        }
        setTimeout("loadFCKeditor('"+this.name+"',"+ this.config.height +");",100);
    },
    setValue : function(value){
        this.MyValue = value;
        if (this.MyisLoaded){
          FCKeditorSetValue(this.name,value);
        }
    },
    getValue : function(){
        if (this.MyisLoaded){
            value = FCKeditorGetValue(this.name);
            Ext.form.TextArea.superclass.setValue.apply(this,[value]);
            return Ext.form.TextArea.superclass.getValue(this);
        } else {
            return this.MyValue;
        }
    },
    getRawValue : function(){
        if (this.MyisLoaded){
            value=FCKeditorGetValue(this.name);
            Ext.form.TextArea.superclass.setRawValue.apply(this,[value]);
            return Ext.form.TextArea.superclass.getRawValue(this);
        } else {
            return this.MyValue;
        }
    }
});
Ext.reg('fckeditor', Ext.form.FCKeditor);

function loadFCKeditor(element, height){
    oFCKeditor 						= new FCKeditor(element);
    oFCKeditor.Height 				= height;
    Ext.apply(oFCKeditor,oFCKeditorOptions)
	oFCKeditor.ReplaceTextarea();
}

function FCKeditor_OnComplete(editorInstance){
	Ext.getCmp(editorInstance.Name).MyisLoaded  = true;
	Ext.getCmp(editorInstance.Name).fckInstance = editorInstance;
}
function FCKeditorSetValue(name,value){
    if (name != undefined){
        var extCmp = Ext.getCmp(name);
        if (extCmp != undefined) {
        	extCmp.fckInstance.SetData(value);
        }
    }
}
function FCKeditorGetValue(name){
    if (name != undefined){
        var data = '';
        var extCmp = Ext.getCmp(name);
        if (extCmp != undefined) {
        	data = extCmp.fckInstance.GetData();
        }
        return data;
    }
}