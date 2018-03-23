talkweb.Bus.ready({
	items : [
	{
		classPath:"Components.Form",
		options:{
			id:"usermodify",
			container:"usermodify",
			dataSource : "user_getLoginUserInfo.action",
			saveInstance: true,
			haveSubmitBtn: false, //是否有提交按钮
			haveResetBtn: false, //是否有重置按钮
			submitCallback : function(result) {
				alert(result.msg);
			},
			resetToBlank: false,
			action:"user_modifyUserBaseInfo.action",
			errorFile: "../jsconfig/errorConfig.json",
			items:[
			{
				classPath:"BaseControl.Label",
				width:"100%",
				options:{
					id:"realname",
					name:"realname",
					notes:"姓名：",
					value:""
				}
			},{
				classPath:"BaseControl.Text",
				width:"100%",
				options:{
					id:"userid",
					name:"userid",
					width:"200px",
					notes:"账号：",
					readOnly:true
				}
			},{
				classPath:"BaseControl.Text",
				width:"100%",
				options:{
					id:"mobileno",
					width:"200px",
					name:"mobileno",
					notes:"手机号：",
					validateItems: {
                        requires: true,
                        maxLength: 20
                    }
				}
			},{
				classPath:"BaseControl.Text",
				width:"100%",
				options:{
					id:"msn",
					name:"msn",
					width:"200px",
					notes:"飞信号：",
					validateItems: {
                        maxLength: 20
                    }
				}
			},{
				classPath:"BaseControl.Text",
				width:"100%",
				options:{
					id:"oemail",
					name:"oemail",
					width:"200px",
					notes:"邮件地址：",
					validateItems: {
                        requires: true,
                        maxLength: 100
                    }
				}
			}
			]
		}
	},{
		classPath:"BaseControl.Button",
		options:{
			container:"buttons",
			value:"保存",
			className:"btn_save",
			click:function(){
				talkweb.ControlBus.getInstance("usermodify").submit();
			}
		}
	},{
		classPath:"BaseControl.Button",
		options:{
			value:"重置",
			className:"btn_reset",
			container:"buttons",
			click:function(){
				talkweb.ControlBus.getInstance("usermodify").resetClick();
			}
		}
	}
	]
});