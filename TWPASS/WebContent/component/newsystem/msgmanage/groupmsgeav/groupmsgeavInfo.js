var request = talkweb.ControlBus.getRequest();
var msgid = request['data'];
talkweb.Bus.ready({
	items : [
	{
		classPath:"Components.Form",
		options:{
			haveSubmitBtn: false, //是否有提交按钮
            haveResetBtn: false, //是否有重置按钮
            container: "groupinfo",
            id: "addmsg",
            saveInstance: true,
            //initSource : "msg_initMsgSerData.action",
            dataSource : "msg_getGroupMsgInfo.action?data="+msgid,
            submitCallback: function(result){
                alert(result.msg);
            },
            items : [
           	{
           		classPath:"BaseControl.Label",
           		width:"100%",
           		options:{
           			name:"title",
           			notes:"消息主题：",
           			value:""
           		}
           	},
           	{
           		classPath:"BaseControl.Label",
           		width:"100%",
           		options:{
           			name:"receivername",
           			notes:"消息接收人：",
           			value:""
           		}
           	},
           	{
           		classPath:"BaseControl.Label",
           		width:"100%",
           		options:{
           			name:"msgtypename",
           			notes:"消息类型：",
           			value:""
           		}
           	},
           	{
           		classPath:"BaseControl.Label",
           		width:"100%",
           		options:{
           			name:"notifytypename",
           			notes:"通知方式：",
           			value:""
           		}
           	},
           	{
           		classPath:"BaseControl.Label",
           		width:"100%",
           		options:{
           			name:"content",
           			notes:"消息内容：",
           			value:""
           		}
           	},
           	{
           		classPath:"BaseControl.Label",
           		width:"100%",
           		options:{
           			name:"verifytime",
           			notes:"审核时间：",
           			value:""
           		}
           	},
           	{
           		classPath:"BaseControl.Label",
           		width:"100%",
           		options:{
           			name:"verifiername",
           			notes:"审核人：",
           			value:""
           		}
           	},{
           		classPath:"BaseControl.Label",
           		width:"100%",
           		options:{
           			name:"verifyopinion",
           			notes:"审核意见：",
           			value:""
           		}
           	}
            ]
		}
	},{
		classPath:"BaseControl.Button",
		options:{
			value:"关闭",
			container:"buttons",
			className:"btn_close",
			click:function(){
				window.parent.dialog.close();
			}
		}
	}
	]
});