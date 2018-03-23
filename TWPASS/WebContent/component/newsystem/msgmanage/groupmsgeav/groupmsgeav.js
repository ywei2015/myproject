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
            action:"msg_doVerify.action",
            submitCallback: function(result){
                alert(msgs.msg);
            	window.parent.freshuserdata(); //刷新datagrid
            	window.parent.dialog.close();
            },
            items : [
           	{
           		classPath:"BaseControl.Label",
           		width:"100%",
           		options:{
           			name:"title",
           			notes:"消息主题：",
           			id:"title",
           			value:""
           		}
           	},
           	{
           		classPath:"BaseControl.Label",
           		width:"100%",
           		options:{
           			name:"receivername",
           			notes:"消息接收人：",
           			id:"receivername",
           			value:""
           		}
           	},
           	{
           		classPath:"BaseControl.Label",
           		width:"100%",
           		options:{
           			name:"msgtypename",
           			notes:"消息类型：",
           			id:"msgtypename",
           			value:""
           		}
           	},
           	{
           		classPath:"BaseControl.Label",
           		width:"100%",
           		options:{
           			name:"notifytypename",
           			notes:"通知方式：",
           			id:"notifytypename",
           			value:""
           		}
           	},
           	{
           		classPath:"BaseControl.Label",
           		width:"100%",
           		options:{
           			name:"content",
           			notes:"消息内容：",
           			id:"content",
           			value:""
           		}
           	},
           	{
           		classPath:"Components.RadioGroup",
           		width:"100%",
           		options:{
           			name:"status",
           			notes:"审核结果：",
           			id:"status",
           			value:"1",
           			items : [ {
						value : "1",
						text : "通过"
					}, {
						value : "2",
						text : "不通过"
					}]
           		}
           	},
           	{
           		classPath:"BaseControl.Text",
           		width:"100%",
           		options:{
           			name:"verify_advice",
           			notes:"审核意见：",
           			id:"verify_advice",
           			height:'50px',
           			width:"400px",
           			value:"",
           			validateItems: {
                        requires: true,
                        maxLength: 200
                    }
           		}
           	},{
           		classPath:"BaseControl.Hidden",
           		width:"0%",
           		options:{
           			name:"verifyid",
           			id:"verifyid"
           		}
           	},{
           		classPath:"BaseControl.Hidden",
           		width:"0%",
           		options:{
           			name:"notifytype",
           			id:"notifytype"
           		}
           	},{
           		classPath:"BaseControl.Hidden",
           		width:"0%",
           		options:{
           			name:"wrongStr",
           			id:"wrongStr"
           		}
           	}
            ]
		}
	},{
		classPath:"BaseControl.Button",
		options:{
			value:"保存",
			container:"buttons",
			className:"btn_save",
			click:function(){
				var formob = talkweb.ControlBus.getInstance("addmsg");
				formob.submit();
			}
		}
	},{
		classPath:"BaseControl.Button",
		options:{
			value:"重置",
			container:"buttons",
			className:"btn_reset",
			click:function(){
				talkweb.ControlBus.getInstance("addmsg").resetClick();
			}
		}
	},{
		classPath:"BaseControl.Button",
		options:{
			value:"取消",
			container:"buttons",
			className:"btn_close",
			click:function(){
				window.parent.dialog.remove();
			}
		}
	}
	]
});
