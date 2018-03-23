var request = talkweb.ControlBus.getRequest();//获取请求对象
var userid = request['userid'];
var usercode = request['userCode'];
talkweb.Bus.ready({
	items : [
	{
		classPath:"Components.Form",
		options:{
			id:"modifyPasswordform",
			container:"modifyPasswordform",
			saveInstance:true,
			haveSubmitBtn: false, //是否有提交按钮
            haveResetBtn: false, //是否有重置按钮
            resetToBlank: false,
            submitJson:false,
            //dataSource : "user_initModifyPassWordForm.action",
			action : "user_modifyPassWord.action",
            submitCallback: function(result){
                alert(result.msg);
            	window.parent.chpassword.close();
            },
            errorFile: "../jsconfig/errorConfig.json",
            items : [
            {
            	classPath:"BaseControl.Label",
            	width:"100%",
            	options:{
            		id:"userCode",
            		name:"userCode",
            		notes:"账号：",
            		value:usercode
            	}
            },{
            	classPath:"BaseControl.Password",
            	width:"100%",
            	options:{
            		id:"oldPassword",
            		name:"oldPassword",
            		notes:"旧密码：",
            		validateItems: {
                        requires: true
                    }
            	}
            },{
            	classPath:"BaseControl.Password",
            	width:"100%",
            	options:{
            		id:"newPassword",
            		name:"newPassword",
            		notes:"新密码：",
            		saveInstance:true,
            		validateItems: {
                        requires: true
                    }
            	}
            },{
            	classPath:"BaseControl.Password",
            	width:"100%",
            	options:{
            		id:"repeatPassword",
            		name:"repeatPassword",
            		notes:"确认新密码：",
            		saveInstance:true,
            		validateItems: {
                        requires: true
                    }
            	}
            },{
            	classPath:"BaseControl.Hidden",
            	width:"0%",
            	options:{
            		id:"userid",
            		name:"userid",
            		value:userid
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
			click:tijiao
		}
	},{
		classPath:"BaseControl.Button",
		options:{
			value:"重置",
			className:"btn_reset",
			container:"buttons",
			click:function(){
				talkweb.ControlBus.getInstance("modifyPasswordform").resetClick();
			}
		}
	},{
		classPath:"BaseControl.Button",
		options:{
			value:"关闭",
			className:"btn_close",
			container:"buttons",
			click:function(){
				window.parent.chpassword.close();
			}
		}
	}
	]
});

function tijiao(){
	var formobj = talkweb.ControlBus.getInstance("modifyPasswordform");
	var new1 = talkweb.ControlBus.getInstance("newPassword");
	var new2 = talkweb.ControlBus.getInstance("repeatPassword");
	if(new1.getValue() != new2.getValue()){
		alert("两次密码输入不一致，请重新输入");
		new1.setValue("");
		new2.setValue("");
		return;
	}
	
	
	formobj.submit();
}