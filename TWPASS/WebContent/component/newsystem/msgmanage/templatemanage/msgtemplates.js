talkweb.Bus.ready({
	items : [
	{
		classPath : "Components.Form",
		options : {
			haveSubmitBtn: false, //是否有提交按钮
            haveResetBtn: false, //是否有重置按钮
            container: "MsgForm",
            id: "msgform",
            saveInstance: true,
            initSource : "msg_initMsgSerData.action",
			//action : "../services/formsubmit.jsp",
            submitCallback: function(result){
                alert(result.msg);
            },
            errorFile: "../../jsconfig/errorConfig.json",
            items : [
            {
            	classPath : "BaseControl.Select",
            	width:"30%",
            	options : {
            		name: "templatetype",
            		notes: "模板类型：",
            		id: "templatetype"
            	}
            },
            {
            	classPath : "BaseControl.Select",
            	width:"30%",
            	options : {
            		name: "notifytype",
            		notes: "通知方式：",
            		id: "notifytype"
            	}
            },
            {
            	classPath : "BaseControl.Select",
            	width:"40%",
            	options : {
            		name: "notifyto",
            		notes: "通知对象：",
            		id: "notifyto"
            	}
            },
            {
            	classPath : "BaseControl.Text",
            	width:"30%",
            	options : {
            		name: "templatename",
            		notes: "模板名称：",
            		id: "templatename"
            	}
            },
            {
                classPath: "BaseControl.Button",
                width:"10%",
                options: {
                    value: "查询",
                    click: refreshmsg,
                    className : "btn_search"
                }
            },
			{
                classPath: "BaseControl.Button",
                width:"60%",
                options: {
                    value: "重置",
                    click: resetmsg,
                    className : "btn_reset"
                }
            }
            ]
		}
	},
	{
		classPath : "BaseControl.Button",
		options : {
			type: "button",
            container: "buttons",
			value: "新增",
			className:"btn_add",
			click :addmsg
		}
	},
	{
		classPath : "BaseControl.Button",
		options : {
			type: "button",
            container: "buttons",
			value: "删除",
			className:"btn_delete",
			click : function(){
				if(confirm('确认删除')){
					deletemsgbymanay();
				}
			}
		}
	},
	{
		classPath : "Components.DataGrid",
		options : {
			initSource : "msg_getMsgTemplates.action",
			container: "msggrid",
            id: "msgdata",
            rows: 10, //指定展示的行数
            multiple: true, //是否有复选框
            //seqNum: true, //行序号
            saveInstance: true,
	        fields: [
	        {
            	text: '编号',
            	name: 'templateid',
            	width: "7%",
            	filter: [{
                    type: "control_db",
                    cond: "none",
                    controlList: [{
                        calssName: "BaseControl.Anchor",
                        options: {
                           click: showmsg
                        }
                	}]
                }]
        	},
        	{
          		text: '模板名称',
            	name: 'templatename',
            	width: "18%",
            	filter: [
            	{
                    type: "control_db",
                    cond: "none",
                    controlList: [{
                        calssName: "BaseControl.Anchor",
                        options: {
                           click: showmsg
                        }
                	}]
                }]
        	},
        	{
           		text: '模板类型',
            	name: 'templatetypename',
            	width: "10%"
        	},
        	{
            	text: '通知方式',
            	name: 'notifytypename',
            	width: "18%"
        	}, 
        	{
            	text: '通知对象',
            	name: 'notifytoname',
            	width: "17%"
        	}, 
        	{
            	text: '创建时间',
            	name: 'createtime',
            	width: "15%"
        	},
        	{
            	text: "操作",
            	width:"15%",
                filter: [{
                    type: "control",
                    cond: "none",
                    controlList: [
                    {
                        calssName: "BaseControl.Anchor",
                        options: {
                            value: "修改 ",
                            click: modifymsg
                        }
                    }, 
                    {
                        calssName: "BaseControl.Anchor",
                        options: {
                            value: " 删除",
                            click: function(){
                            	if(confirm('确认删除')){
                            		//获取datagrid对象
									var msggridob = talkweb.ControlBus.getInstance("msgdata");
									var msgobj = talkweb.ControlBus.getInstance("msgdata").getRowValue(this);
									//消息模板id
									var magid = msgobj.templateid;
									$.ajax({
       	 								type: "POST",
        								url: "msg_deleteMsgTemplates.action",
        								data: "id=" + magid+',',
        								dataType: "json",
        								success: function(msgs){
            								alert(msgs.msg);
            								msggridob.refreshData(true); //刷新datagrid
        								}
   	 								});
                            	}
                            }
                        }
                    }]
                }]
        	}]
		}
	}
	]
});

var dialog = "";

//刷新logdatagrid
function refreshmsg(){
	var msgformob = talkweb.ControlBus.getInstance("msgform");         //获取form对象
	var msggridob = talkweb.ControlBus.getInstance("msgdata");         //获取datagrid对象
	var datavalue = eval("({"+msgformob.getValues()+"})");				//转换为json对象  
	msggridob.setOptions({//重设datagrid的url
		//reqParam : datavalue
		reqParam : {data : "{"+msgformob.getValues()+"}"}//后台以idata方式获取参数
	});         
	msggridob.refreshData(true);
}

//重置表单reset
function resetmsg(){
	var msgformob = talkweb.ControlBus.getInstance("msgform"); //获取form对象
	msgformob.resetClick();
}
// 显示消息模板详细信息
function showmsg(){
	var msgobj = talkweb.ControlBus.getInstance("msgdata").getRowValue(this);
	dialog = talkweb.Components.Dialog({
		left : "50",
		top :  "50",
		width: "800",
		height: "400",
		title: "消息模板详情",
		src : "msgtemplateinfo.html?msgid="+msgobj.templateid
	});
}
// 修改消息模板
function modifymsg(){
	var msgobj = talkweb.ControlBus.getInstance("msgdata").getRowValue(this);
	dialog = talkweb.Components.Dialog({
		left : "50",
		top :  "50",
		width: "800",
		height: "400",
		title: "修改消息模板",
		src : "modifymsgtemplate.html?msgid="+msgobj.templateid
	});
}
//新增消息模板
function addmsg(){
	dialog = talkweb.Components.Dialog({
		left : "40",
		top :  "50",
		width: "800",
		height: "400",
		title: "新增消息模板",
		src : "addmsgtemplate.html"
	});
}
//删除多条消息模板
function deletemsgbymanay(){
	//获取datagrid对象
	var msggridob = talkweb.ControlBus.getInstance("msgdata");
	var msglist = talkweb.ControlBus.getInstance("msgdata").getValue();
	//消息模板id
	var magid = "";
    if (msglist.length <= 0) {
        alert("请至少选择一条记录进行操作");
        return;
    }
    for (var i = 0; i < msglist.length; i++) {
        var obj = msglist[i];
        magid += obj.templateid + ",";
    }
	$.ajax({
        type: "POST",
        url: "msg_deleteMsgTemplates.action",
        data: "id=" + magid+',',
        dataType: "json",
        success: function(msgs){
            alert(msgs.msg);
            msggridob.refreshData(true); //刷新datagrid
        }
    });
}
//刷新datagrid
function freshuserdata(){
	var msggridob = talkweb.ControlBus.getInstance("msgdata");
	 msggridob.refreshData(true);
}
