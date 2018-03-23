var request = talkweb.ControlBus.getRequest();//获取请求对象
var empid = request['empid'];
var data_source_url="user_getUser.action?EMPID="+empid;
talkweb.Bus.ready({
	items :[
	{
		classPath : "Components.Form",
		options : {
			container : "userinfo",
			dataSource : data_source_url,
			name : "userform",
			id : "userinfoform",
			columns : 2,
			submitCallback : function(result) {
				alert(result.msg);
			},
			haveSubmitBtn : false,
			haveResetBtn : false,
			items : [
			{
				classPath : "BaseControl.Label",
				width : "50%",
				options : {
					name : "userid",
					notes : "用户编号：",
					value:""
				}
			},{
				classPath : "BaseControl.Label",
				width : "50%",
				options : {
					name : "empcode",
					notes : "用户帐号：",
					value:""
				}
			},{
				classPath : "BaseControl.Label",
				width : "50%",
				options : {
					name : "empname",
					notes : "用户姓名：",
					value:""
				}
			},{
				classPath : "BaseControl.Label",
				width : "50%",
				options : {
					name : "realname",
					notes : "真实姓名：",
					value:""
				}
			},{
				classPath : "BaseControl.Label",
				width : "50%",
				options : {
					name : "mobileno",
					notes : "用户手机：",
					value:""
				}
			},{
				classPath : "BaseControl.Label",
				width : "50%",
				options : {
					name : "otel",
					notes : "用户座机：",
					value:""
				}
			},{
				classPath : "BaseControl.Label",
				width : "50%",
				options : {
					name : "cardno",
					notes : "身 份 证：",
					value:""
				}
			},{
				classPath : "BaseControl.Label",
				width : "50%",
				options : {
					name : "birthdate",
					notes : "出生日期：",
					value:""
				}
			},{
				classPath : "BaseControl.Label",
				width : "50%",
				options : {
					name : "oemail",
					notes : "用户邮箱：",
					value:""
				}
			},{
				classPath : "BaseControl.Label",
				width : "50%",
				options : {
					name : "gender",
					notes : "用户性别：",
					value:""
				}
			},{
				classPath : "BaseControl.Label",
				width : "50%",
				options : {
					name : "usertypename",
					notes : "用户类型：",
					value:""
				}
			},{
				classPath : "BaseControl.Label",
				width : "50%",
				options : {
					name : "empstatusname",
					notes : "状态：",
					value:""
				}
			},{
				classPath : "BaseControl.Label",
				width : "50%",
				options : {
					name : "orgname",
					notes : "所属组织：",
					value:""
				}
			},{
				classPath : "BaseControl.Label",
				width : "50%",
				options : {
					name : "hzipcode",
					notes : "邮政编码：",
					value:""
				}
			},{
				classPath: "BaseControl.Button",
				options: {
					value:"关闭",
					className:"btn_close",
					click:function(){
						window.parent.userinfo.remove();
					}
				}
			}
			]
		}
	}]
});