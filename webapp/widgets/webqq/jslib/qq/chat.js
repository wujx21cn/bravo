//这是一个参考文件
//加载所需类库
dojo.require("dojox.cometd");
dojo.require("dojox.cometd.timestamp");
dojo.require("dojox.cometd.ack");

//定义一个room 对象
var room = {
	_last: "",//
	_username: null,//当前的用户名
	_connected: true,//当前的连接状态 true 以连接
	//*********************************************************************************************
	//登录操作
	join: function(name){
	
		//用户名不能为空
		if (name == null || name.length == 0) {
			alert('Please enter a username!');
			return;
		}
		
		dojox.cometd.ackEnabled = (dojo.query("#ackInit").attr("checked") == "true");
		
		
		var loc = (new String(document.location).replace(/http:\/\/[^\/]*/, '').replace(/\/examples\/.*$/, '')) +"/cometd";
		dojox.cometd.init(loc);
		// For x-domain test change line above to:
		// dojox.cometd.init("http://127.0.0.1:8080/cometd/cometd");
		this._connected = true;
		
		this._username = name;
		dojo.addClass("join", "hidden");
		dojo.removeClass("joined", "hidden");
		dojo.byId("phrase").focus();
		
		// subscribe and join
		dojox.cometd.startBatch();
		//定义频道
		//第一个参数：频道名
		//第二个参数：指定的对象
		//第三个参数：成功后的回调函数，它是第二个参数的一个方法
		dojox.cometd.subscribe("/chat/demo", room, "_chat");
		//发送信息,说明已加入
		dojox.cometd.publish("/chat/demo", {
			user: room._username,
			join: true,
			chat: room._username + " has joined"
		});
		dojox.cometd.endBatch();
		
		//对 comet failures 进行处理
		//如果之前已经订阅_meta，则取消订阅
		if (room._meta) {
			dojo.unsubscribe(room._meta, null, null);
		}
		//订阅
		room._meta = dojo.subscribe("/cometd/meta", this, function(e){
			// console.debug(e);
			if (e.action == "handshake") {
				if (e.reestablish) {
					if (e.successful) {
						//重新连接成功
						dojox.cometd.subscribe("/chat/demo", room, "_chat");
						dojox.cometd.publish("/chat/demo", {
							user: room._username,
							join: true,
							chat: room._username + " has re-joined"
						});
					}
					room._chat({
						data: {
							join: true,
							user: "SERVER",
							chat: "handshake " + e.successful ? "Handshake OK" : "Failed"
						}
					});
				}
			} else if (e.action == "connect") {
				if (e.successful && !this._connected) {
					room._chat({
						data: {
							join: true,
							user: "SERVER",
							chat: "reconnected!"
						}
					});
				}
				if (!e.successful && this._connected) {
					room._chat({
						data: {
							leave: true,
							user: "SERVER",
							chat: "disconnected!"
						}
					});
				}
				this._connected = e.successful;
			}
		});
	},
	//*********************************************************************************************
	//用户离开
	leave: function(){
		if (!room._username) {
			return;
		}
		
		if (room._meta) {
			dojo.unsubscribe(room._meta);
		}
		room._meta = null;
		
		dojox.cometd.startBatch();
		dojox.cometd.unsubscribe("/chat/demo", room, "_chat");
		dojox.cometd.publish("/chat/demo", {
			user: room._username,
			leave: true,
			chat: room._username + " has left"
		});
		dojox.cometd.endBatch();
		
		// switch the input form
		dojo.removeClass("join", "hidden");
		dojo.addClass("joined", "hidden");
		
		dojo.byId("username").focus();
		room._username = null;
		dojox.cometd.disconnect();
		dojo.byId('members').innerHTML = "";
	},
	//*********************************************************************************************
	//用户往频道上发送信息
	chat: function(text){
		//信息为空，返回
		if (!text || !text.length) {
			return false;
		}
		//判断是否为私聊
		var colons = text.indexOf("::");
		if (colons > 0) {
			//如果为私聊，则转到私聊频道上
			dojox.cometd.publish("/service/privatechat", {
				room: "/chat/demo", // This should be replaced by the room name
				user: room._username,
				chat: text.substring(colons + 2),
				//和当前用户要私聊的用户
				peer: text.substring(0, colons)
			});
		} else {
			//不是私聊，则转到公用频道上
			dojox.cometd.publish("/chat/demo", {
				user: room._username,
				chat: text
			});
		}
	},
	//*********************************************************************************************
	//用来处理  订阅后 频道上传来的消息
	_chat: function(message){
		//如果数据为空，返回
		if (!message.data) {
			// console.debug("bad message format " + message);
			return;
		}
		//如果数据是数组的形式，则说明得到信息 是 在线人员（这和后台有关）
		if (message.data instanceof Array) {
			//把在线人员添加到div中
			var members = dojo.byId('members');
			var list = "";
			for (var i in message.data) 
				list += message.data[i] + "<br/>";
			members.innerHTML = list;
		} else {
			//如果不是数组形式
			var chat = dojo.byId('chat');
			var from = message.data.user;
			//用户是加入还是退出
			var membership = message.data.join || message.data.leave;
			var text = message.data.chat;
			//如果信息为空，返回
			if (!text) 
				return;
			//如果不是加入也不是离开
			if (!membership && (from == room._last)) {
				from = "...";
			} else {
				room._last = from;
				from += ":";
			}
			
			if (membership) {
				chat.innerHTML += "<span class=\"membership\"><span class=\"from\">" + from + "&nbsp;</span><span class=\"text\">" + text + "</span></span><br/>";
				room._last = "";
			} else if (message.data.scope == "private") {
				chat.innerHTML += "<span class=\"private\"><span class=\"from\">" + from + "&nbsp;</span><span class=\"text\">[private]&nbsp;" + text + "</span></span><br/>";
			} else {
				chat.innerHTML += "<span class=\"from\">" + from + "&nbsp;</span><span class=\"text\">" + text + "</span><br/>";
			}
			chat.scrollTop = chat.scrollHeight - chat.clientHeight;
		}
	},
	//*********************************************************************************************
	//页面加载后，要调用的函数
	_init: function(){
		dojo.removeClass("join", "hidden");
		dojo.addClass("joined", "hidden");
		
		dojo.byId('username').focus();
		
		//用户加入
		dojo.query("#username").attr({
			"autocomplete": "OFF"
		}).onkeyup(function(e){
			if (e.keyCode == dojo.keys.ENTER) {
				room.join(dojo.byId('username').value);
				
				return false;
			}
			return true;
		});
		
		dojo.query("#joinB").onclick(function(e){
			room.join(dojo.byId('username').value);
			e.preventDefault();
		});
		
		//得到当前用户的信息，并发送
		dojo.query("#phrase").attr({
			"autocomplete": "OFF"
		}).onkeyup(function(e){
			if (e.keyCode == dojo.keys.ENTER) {
				room.chat(dojo.byId('phrase').value);
				dojo.byId('phrase').value = '';
				e.preventDefault();
			}
		});
		
		dojo.query("#sendB").onclick(function(e){
			room.chat(dojo.byId('phrase').value);
			dojo.byId('phrase').value = '';
		});
		//用户离开
		dojo.query("#leaveB").onclick(room, "leave");
		
	}
};

//页面加载的时候
dojo.addOnLoad(room, "_init");
//页面退出的时候
dojo.addOnUnload(room, "leave");
