var request = talkweb.ControlBus.getRequest();//获取请求对象
var msgid = request['msgid'];//获取请求中的消息id
talkweb.Bus.ready({
	items : [
	{
		classPath : "Components.Form",
		options : {
			container : "msginfo",
			dataSource : "msg_getMsgTemplateInfo.action?TEMPLATEID="+msgid,
			name : "msginfoform",
			id : "msgdetail",
			columns : 2,
			submitCallback : function(result) {
				alert(result.msg);
			},
			haveSubmitBtn : false,
			haveResetBtn : false,
			items : [
			{
				classPath : "BaseControl.Label",
				width : "100%",
				options : {
					name : "templatename",
					notes : "模板名称:",
					value:""
				}
			},
			{
				classPath : "BaseControl.Label",
				width : "100%",
				options : {
					name : "msgtypename",
					notes : "消息类型:",
					value:""
				}
			},
			{
				classPath : "BaseControl.Label",
				width : "100%",
				options : {
					name : "templatetypename",
					notes : "模板类型:",
					value:""
				}
			},
			{
				classPath : "BaseControl.Label",
				width : "100%",
				options : {
					name : "notifytypename",
					notes : "通知方式:",
					value:""
				}
			},
			{
				classPath : "BaseControl.Label",
				width : "100%",
				options : {
					name : "notifytoname",
					notes : "通知对象:",
					value:""
				}
			},
			{
				classPath : "BaseControl.Label",
				width : "100%",
				options : {
					name : "createtime",
					notes : "创建时间:",
					value:""
				}
			},
			{
				classPath : "BaseControl.Label",
				width : "100%",
				options : {
					name : "content",
					notes : "模板内容:",
					width:"450px",
					height:"100px",
					value:""
				}
			}
			]
		}
	},{
		classPath : "BaseControl.Button",
		options : {
			value:"关闭",
			container:"buttons",
			className:"btn_close",
			click : function(){
				window.parent.dialog.remove();
			}
		}
	}
	]
});