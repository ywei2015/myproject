var request = talkweb.ControlBus.getRequest();
var msgid = request['msgid'];
talkweb.Bus.ready({
	items : [
	//新增消息模板form表单
	{
		classPath : "Components.Form",
		options : {
			haveSubmitBtn: false, //是否有提交按钮
            haveResetBtn: false, //是否有重置按钮
            container: "addmsgmodle",
            id: "addmsg",
            saveInstance: true,
            resetToBlank: false,
            errorFile: "../../jsconfig/errorConfig.json",
            initSource : "msg_initMsgSerData.action",
            dataSource : "msg_getMsgTemplateInfo.action?TEMPLATEID="+msgid,
            action:"msg_modifyMsgTemplate.action",
            submitCallback: function(result){
                alert(result.msg);
            	//刷新父页面datagrid
            	window.parent.freshuserdata();
            	window.parent.dialog.close();
            },
            items : [
            {
            	classPath: "BaseControl.Hidden",
            	width:"0%",
            	options : {
            		name: "notifytype",
            		id : "notifytype"
            	}
            },
            {
            	classPath: "BaseControl.Hidden",
            	width:"0%",
            	options : {
            		name: "templateid",
            		id : "templateid"
            	}
            },
            {
            	classPath: "BaseControl.Hidden",
            	width:"0%",
            	options : {
            		name: "creator",
            		id : "creator"
            	}
            },
            {
            	classPath: "BaseControl.Hidden",
            	width:"0%",
            	options : {
            		name: "parentid",
            		id : "parentid"
            	}
            },{
            	classPath: "BaseControl.Hidden",
            	width:"0%",
            	options : {
            		name: "createtime",
            		id : "createtime"
            	}
            },
            {
            	classPath: "BaseControl.Text",
            	width:"100%",
            	options : {
            		name: "templatename",
            		id: "templatename",
            		notes: "模板名称：",
            		width:"400px",
            		validateItems: {
                        requires: true,
                        maxLength: 50
                    }
            	}
            },
            {
            	classPath: "BaseControl.Select",
            	width:"100%",
            	options: {
            		name: "msgtype",
            		id: "msgtype",
            		notes: "消息类型：",
            		validateItems: {
                        requires: true
                    }
            	}
            },
            {
            	classPath: "BaseControl.Select",
            	width:"100%",
            	options: {
            		name: "templatetype",
            		id: "templatetype",
            		notes: "模板类型：",
            		validateItems: {
                        requires: true
                    }
            	}
            },
            {
            	classPath: "Components.CheckBoxGroup",
            	width:"100%",
            	options: {
            		name:"sendType",
            		notes: "通知方式：",
            		validateItems: {
                        requires: true
                    }
            	}
            },
            {
            	classPath: "BaseControl.Select",
            	width:"100%",
            	options: {
            		name: "notifyto",
            		id: "notifyto",
            		notes: "通知对象：",
            		validateItems: {
                        requires: true
                    }
            	}
            },
            {
            	classPath: "BaseControl.TextArea",
            	width:"100%",
            	options: {
            		name: "content",
            		width:"100%",
            		notes:"模板内容：",
            		id: "content",
            		width:"550px",
            		height:"100px",
            		validateItems: {
                        requires: true,
                        maxLength: 512
                    }
            	}
            }]
		}
	},{
		classPath:"BaseControl.Button",
		options:{
			  value:"保存",
			  container:"buttons",
			  className:"btn_save",
			  click:function(){
			  		var a = 0;
					$("input[type='checkbox']:checked").each(function(i) {
						a += parseInt($(this).val());
					});
					$("#notifytype").val(a);
            		var msgaddform = talkweb.ControlBus.getInstance("addmsg");
					msgaddform.submit();
			  	}
			}
	},{
		classPath:"BaseControl.Button",
		options:{
			  value:"重置",
			  container:"buttons",
			  className:"btn_reset",
			  click:function(){
			  	var userformob = talkweb.ControlBus.getInstance("addmsg");//获取form表单对象
				userformob.resetClick();
			  }
		}
	},{
		classPath:"BaseControl.Button",
		options:{
			  value:"关闭",
			  container:"buttons",
			  className:"btn_close",
			  click:function(){
			  	window.parent.dialog.remove();
			  }
		}
	}
	]
});
