Ext.ux.IFrameComponentForLayoutWindow = Ext.extend(Ext.BoxComponent, {
	onRender : function(ct, position,winWidth,winHeight) {
		this.el = ct.createChild({
			tag : 'iframe',
			name : this.id,
			id : 'iframe-' + this.id,
			frameBorder : 0,
			src : this.url,
			height : winHeight,
			width : winWidth
		});
	}
});

Ext.reg('iFrameComponentForLayoutWindow',Ext.ux.IFrameComponent);

function newTabForDeskPanelWithIcon(url,title,xx,targetObj){
	   var desktop = targetObj.app.getDesktop();
		var win = desktop.getWindow(targetObj.moduleId);
		if(!win){
			var winWidth = desktop.getWinWidth() / 1.1;
			var winHeight = desktop.getWinHeight() / 1.1;
				    	
		//	var html = "<iframe id='profileMenuWinFrame' name='profileMenuWinFrame' src='"+url+"' width='100%' height='100%'></iframe>";
			var html = new Ext.ux.IFrameComponent({ id: title, url: url,height:winWidth-230,width:winWidth-130 }) 
			win = desktop.createWindow({
				id: targetObj.moduleId,
				title:title,
				width:winWidth,
				height:winHeight,
				x:desktop.getWinX(winWidth),
				y:desktop.getWinY(winHeight),
				iconCls: 'layout-icon',
				shim:false,
				layout:'fit',
				animCollapse:false,
				constrainHeader:true,
				minimizable:true,
    			maximizable:true,
    			items: [ html ],
				taskbuttonTooltip: '<b>'+title+'</b>'
			});
		}
		win.show();
 }
Ext.namespace('QoDesk');
Ext.override(QoDesk.LayoutWindow, {
	createWindow : function(){
		if ( "" != this.action) {
			if (this.action.substring(this.action.length -1,this.action.length) == ")")
			{
				var scriptStr = this.action.trim().substring(0,this.action.length -1)+",this)";
				eval(scriptStr);
			} else {
				eval(this.action);
			}
		}
		
	}
});