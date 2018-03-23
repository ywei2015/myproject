var request = talkweb.ControlBus.getRequest();//获取请求对象
var empid = request['empid'];
talkweb.Bus.ready({
	items : [
	{
		classPath: "Components.Form",
		options: {
			id: "usermodify",
			name: "usermodifyname",
			container: "modifyuserform",
			dataSource : "user_getModifyUser.action?opstatus=modify&EMPID="+empid,
			initSource : "user_initUserData.action",
			action: "user_modifyUser.action",
			saveInstance: true,
			haveSubmitBtn: false, //是否有提交按钮
			haveResetBtn: false, //是否有重置按钮
			resetToBlank: false,
			submitCallback : function(result) {
				alert(msgs.msg);
             	//刷新datagrid
            	window.parent.freshuserdata();
    			window.parent.modifyuserdia.close();
			},
			errorFile: "../jsconfig/errorConfig.json",
			items: [
			{
				classPath: "BaseControl.Text",
				width: "50%",
				options: {
					name: "userid",
					notes: "用户编号：",
					validateItems: {
                        requires: true
                    }
				}
			},{
				classPath: "BaseControl.Text",
				width: "50%",
				options:{
					name: "empcode",
					notes: "用户帐号：",
					validateItems: {
                        requires: true
                    }
				}
			},{
				classPath: "BaseControl.Text",
				width: "50%",
				options: {
					name: "empname",
					notes: "用户姓名：",
					validateItems: {
                        requires: true
                    }
				}
			},{
				classPath: "BaseControl.Text",
				width: "50%",
				options: {
					name: "realname",
					notes: "真实姓名：",
					validateItems: {
                        requires: true
                    }
				}
			}
			,{
				classPath: "BaseControl.Text",
				width: "50%",
				options: {
					name: "mobileno",
					notes: "用户手机："
				}
			},{
				classPath: "BaseControl.Text",
				width: "50%",
				options: {
					name: "otel",
					notes: "用户座机："
				}
			},{
				classPath: "BaseControl.Text",
				width: "50%",
				options: {
					name: "cardno",
					notes: "身 份 证："
				}
			},{
				classPath: "Components.Datepicker",
				width: "50%",
				options: {
					name: "birthdate",
					notes: "出生日期：",
					displayFormat : "yyyy-mm-dd",
					switchClock : "off"
				}
			},{
				classPath: "BaseControl.Text",
				width: "50%",
				options: {
					name: "oemail",
					notes: "用户邮箱："
				}
			},{
				classPath: "Components.RadioGroup",
				width: "50%",
				options: {
					name: "gender",
					notes: "用户性别：",
					items : [ {
						value : "1",
						text : "男"
					}, {
						value : "2",
						text : "女"
					}]
				}
			},
			{
				classPath: "BaseControl.Label",
				width: "50%",
				options: {
					name: "usertypename",
					notes: "用户类型：",
					value:""
				}
			},{
				classPath: "BaseControl.Text",
				width: "50%",
				options: {
					name: "hzipcode",
					notes: "邮政编码："
				}
			},{
				classPath: "Components.Tree",
				width: "50%",
				options: {
					name: "orgid",
					notes: "所属组织：",
					id: "orgid",
					//initSource: "initOrgTree.action",
					showIco: true,
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
					notes:"状态：",
					validateItems: {
                        requires: true
                    }
				}
			},{
				classPath: "BaseControl.Hidden",
				width: "0%",
				options: {
					name: "usertype",
					id: "usertype"
				}
			},{
				classPath: "BaseControl.Hidden",
				width:'0%',
				options: {
					name: "empid",
					id: "empid"
				}
			},
			{
				classPath: "BaseControl.Hidden",
				width:'0%',
				options: {
					name: "operatorid",
					id: "operatorid"
				}
			},
			{
				classPath: "BaseControl.Hidden",
				width:'0%',
				options: {
					name: "isdelete",
					id: "isdelete",
					value: "0"
				}
			},
			{
				classPath: "BaseControl.Hidden",
				width:'0%',
				options: {
					name: "status",
					id: "status",
					value: "0"
				}
			}]
		}
	},{
		classPath: "BaseControl.Button",
		width:"50%",
		options: {
			container:"buttons",
			value: "保存",
			className:"btn_save",
			click : subbtn
		}
	},{
		classPath:"BaseControl.Button",
		width:"50%",
		options: {
			container:"buttons",
			value:"重置",
			className:"btn_reset",
			click: function(){
				talkweb.ControlBus.getInstance("usermodify").resetClick();
			}
		}
	},{
		classPath:"BaseControl.Button",
		width:"50%",
		options: {
			container:"buttons",
			value:"关闭",
			className:"btn_close",
			click: close
		}
	}
	]
});
//提交
function subbtn(){
	var userformobj = talkweb.ControlBus.getInstance("usermodify");
	userformobj.submit();
}
//关闭
function close(){
    window.parent.modifyuserdia.remove();
}