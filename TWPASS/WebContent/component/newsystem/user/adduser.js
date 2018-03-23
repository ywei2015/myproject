talkweb.Bus.ready({
	items :[{
		classPath: "Components.Form",
		options: {
			id: "adduser",
			name: "adduserform",
			container: "userform",
			initSource : "user_initUserData.action",
			saveInstance: true,
			haveSubmitBtn: false, //是否有提交按钮
			haveResetBtn: false, //是否有重置按钮
			submitCallback : function(result) {
				alert(msgs.msg);
             	//刷新datagrid
            	window.parent.freshuserdata();
    			window.parent.adduserdia.close();
			},
			action:"user_addUser.action",
			errorFile: "../jsconfig/errorConfig.json",
			items:[
			{
				classPath: "BaseControl.Text",
				width: '50%',
				options : {
					name: "empcode",
					notes: "用户编号"
				}
			},
			{
				classPath: "BaseControl.Text",
				width: "50%",
				options: {
					name: "userid",
					notes: "用户账号",
					validateItems: {
                        requires: true
                    }
				}
			},{
				classPath: "BaseControl.Text",
				width: "50%",
				options: {
					name: "empname",
					notes: "用户姓名",
					validateItems: {
                        requires: true
                    }
				}
			},{
				classPath: "BaseControl.Text",
				width: "50%",
				options: {
					name: "realname",
					notes: "真实姓名",
					validateItems: {
                        requires: true
                    }
				}
			},{
				classPath: "BaseControl.Password",
				width: "50%",
				options: {
					name: "passwordfirst",
					notes: "用户密码",
					validateItems: {
                        requires: true
                    }
				}
			},{
				classPath: "BaseControl.Password",
				width: "50%",
				options: {
					name: "password",
					notes: "确认密码",
					validateItems: {
                        requires: true
                    }
				}
			},{
				classPath: "Components.Datepicker",
				width: "50%",
				options: {
					name: "birthdate",
					notes: "出生日期",
					displayFormat : "yyyy-mm-dd",
					switchClock : "off"
				}
			},{
				classPath: "BaseControl.Text",
				width: "50%",
				options: {
					name: "mobileno",
					notes: "用户手机"
				}
			},{
				classPath: "BaseControl.Text",
				width: "50%",
				options: {
					name: "otel",
					notes: "用户座机"
				}
			},{
				classPath: "BaseControl.Text",
				width: "50%",
				options: {
					name: "cardno",
					notes: "身 份 证",
					validateItems: {
                        requires: true,
                        maxLength: 18
                    }
				}
			},{
				classPath: "BaseControl.Text",
				width: "50%",
				options: {
					name: "hzipcode",
					notes: "邮政编码"
				}
			},{
				classPath: "BaseControl.Text",
				width: "50%",
				options: {
					name: "oemail",
					notes: "用户邮箱"
				}
			},{
				classPath: "BaseControl.Select",
				width: "50%",
				options: {
					name: "usertype",
					id:"usertype",
					notes: "用户类型"
				}
			},{
				classPath: "Components.RadioGroup",
				width: "50%",
				options: {
					name: "gender",
					notes: "用户性别",
					items : [ {
						value : "1",
						text : "男"
					}, {
						value : "2",
						text : "女"
					}]
				}
			},{
				classPath:"Components.Tree",
				width:"50%",
				options: {
					initSource: "org_searchAllOrg.action",
					id: "orgid",
					name:"orgid",
					showIco: true,
					notes:"所属组织",
					saveInstance: true,
					openFloor: 10,
           	 		multiple: false,
            		treeType: "selecttree",
            		validateItems: {
                        requires: true
                    }
				}
			},{
				classPath:"BaseControl.Select",
				withd:"50%",
				options:{
					name:"empstatus",
					id:"empstatus",
					notes:"状态"
				}
			}]
		}
	},{
		classPath:"BaseControl.Button",
		options:{
			container:"buttons",
			value:"保存",
			className:"btn_save",
			click:submit
		}
	},{
		classPath:"BaseControl.Button",
		options:{
			value:"重置",
			className:"btn_reset",
			container:"buttons",
			click:function(){
				talkweb.ControlBus.getInstance("adduser").resetClick();
			}
		}
	},{
		classPath:"BaseControl.Button",
		options:{
			value:"关闭",
			className:"btn_close",
			container:"buttons",
			click:function(){
				window.parent.adduserdia.remove();
			}
		}
	}]
});

//提交
function submit(){
	var userfomrob = talkweb.ControlBus.getInstance("adduser");
	userfomrob.submit();
    
}