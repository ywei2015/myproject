talkweb.Bus.ready({
	items : [
	{
		classPath:"Components.Form",
		options:{
			id:"msggroupform",
			name:"msggroup",
			container:"groupmsgeav_form",
			initSource:"msg_initMsgSerData.action",
			saveInstance:true,
			haveSubmitBtn: false, //是否有提交按钮
            haveResetBtn: false, //是否有重置按钮
            items : [
            {
            	classPath:"BaseControl.Hidden",
            	width:"0%",
            	options:{
            		name:"searchID",
            		value:"EXDFQ1EUYIURLNXN32CKJX23CHG2YOUOSDFQ"
            	}
            },
            {
            	classPath:"BaseControl.Select",
            	width:"25%",
            	options:{
            		name:"status",
            		notes:"审核状态：",
            		id:"status"
            	}
            },{
            	classPath:"BaseControl.Select",
            	width:"25%",
            	options:{
            		name:"msgtype",
            		notes:"消息类型：",
            		id:"msgtype"
            	}
            },{
            	classPath:"BaseControl.Select",
            	width:"25%",
            	options:{
            		name:"notifytype",
            		notes:"发送方式：",
            		id:"notifytype"
            	}
            },{
            	classPath:"BaseControl.Text",
            	width:"25%",
            	options:{
            		name:"msgTitle",
            		notes:"消息主题：",
            		id:"msgTitle"
            	}
            },{
            	classPath:"Components.Datepicker",
            	width:"25%",
            	options:{
            		name:"msg_create_time1",
            		notes:"创建时间从：",
            		id:"msg_create_time1",
            		displayFormat : "yyyy-mm-dd",
					switchClock : "off"
            	}
            },{
            	classPath:"Components.Datepicker",
            	width:"25%",
            	options:{
            		name:"msg_create_time2",
            		notes:"到：",
            		id:"msg_create_time2",
            		displayFormat : "yyyy-mm-dd",
					switchClock : "off"
            	}
            },{
            	classPath:"BaseControl.Button",
            	width:"10%",
            	options:{
            		value:"查询",
            		className:"btn_search",
            		click:search
            	}
            },{
            	classPath:"BaseControl.Button",
            	width:"40%",
            	options:{
            		value:"重置",
            		className:"btn_reset",
            		click:resetlog
            	}
            }
            ]
		}
	},{
		classPath:"Components.DataGrid",
		options:{
			id:"msgdata",
			name:"maggroup",
			container:"msg_data",
			initSource:"msg_getGroupVerifys.action?data={searchID:\"loginUserID\"}",
			rows: 10, //指定展示的行数
            saveInstance: true,
            multiple: true,
            fields : [
            {
				text : "消息主题",
				name : "title",
				width :"18%",
				filter: [{
                    type: "control_db",
                    cond: "none",
                    controlList: [{
                        calssName: "BaseControl.Anchor",
                        options: {
                           click :function(){
                           		var value=talkweb.ControlBus.getInstance("msgdata").getRowValue(this);
								var verifyid = value.verifyid;
								shouinfo(verifyid);
                           }
                        }
                	}]
                }]
			},
			{
				text : "消息类型",
				name : "msgtypename",
				width :"10%"
			},
			{
				text : "发送方式",
				name : "notifytypename",
				width: "12%"
			},
			{
				text : "发送人",
				name : "sendername",
				width : "10%"
			},
            {
                text : "审核时间",
                name : "verifytime",
                width : "12%"
            },{
                text : "创建时间",
                name : "createtime",
                width : "13%"
            },{
				text : "审核状态",
				name : "statusname",
				width : "12%"
			},
			{
				text : "操作",
				name : "status",
				width: "13%",
				filter: [{
                    type: "control",
                    cond: "2",
                    controlList: [
                    {
                        calssName: "BaseControl.Anchor",
                        options: {
                        	value:"修改 ",
                           	click :modify
                        }
                	},{
                        calssName: "BaseControl.Anchor",
                        options: {
                        	value:" 删除 ",
                           	click :function(){
                           		var value=talkweb.ControlBus.getInstance("msgdata").getRowValue(this);
								if(confirm('确定删除！')){
                           		 	deleteone(value.verifyid);
                           		}
                          	}
                        }
                	}]
                },{
                    type: "control",
                    cond: "1",
                    controlList: []
                },{
                    type: "control",
                    cond: "0",
                    controlList: []
                },{
                    type: "control",
                    cond: "3",
                    controlList: []
                },{
                    type: "control",
                    cond: "4",
                    controlList: []
                },{
                    type: "control",
                    cond: "5",
                    controlList: []
                },{
                    type: "control",
                    cond: "6",
                    controlList: []
                },{
                    type: "control",
                    cond: "7",
                    controlList: []
                },{
                    type: "control",
                    cond: "8",
                    controlList: []
                }]
			},{
				name : "verifyid",
				width :"0%",
				hide:true
			}]
		}
	},{
		classPath:"BaseControl.Button",
		options:{
			container:"buttons",
			value:"群发消息",
			className:"btn_add",
			click:function(){
				window.location.href="../../msgmanage/sendmsg/sendmsg.html";
			}
		}
	},{
		classPath:"BaseControl.Button",
		options:{
			container:"buttons",
			value:"批量删除",
			className:"btn_delete",
			click:function(){
				if(confirm('确定删除！')){
					deletemany();
				}
			}
		}
	}
	]
});

var dialog="";
//查询
function search(){
	var msgformob = talkweb.ControlBus.getInstance("msggroupform");         //获取form对象
	var msggridob = talkweb.ControlBus.getInstance("msgdata");         //获取datagrid对象
	//var dataObj=eval("({"+msgformob.getValues()+"})");//转换为json对象  
	msggridob.setOptions({
		//reqParam : dataObj
		reqParam : {
			data : "{"+msgformob.getValues()+"}"
		}
	});         //重设datagrid的url
	msggridob.refreshData(true);
}
//重置表单reset
function resetlog(){
	var msgformob = talkweb.ControlBus.getInstance("msggroupform");   
	msgformob.resetClick();
}
	
//跳转至修改页面
function modify(){
	var value=talkweb.ControlBus.getInstance("msgdata").getRowValue(this);
	var data = "{verifyid:\""+value.verifyid +"\",notifytypes:\"" + getNotifytypes($)+"\"}";
	dialog=talkweb.Components.Dialog({
		left : 150,
		top :  50,
		width: "700",
		title : "修改",
		height: "400",
		src : "modifymsg.html?data="+data
	});
}
//显示详情
function shouinfo(obj){
	//var value=talkweb.ControlBus.getInstance("msgdata").getRowValue(this);
	var data = "{verifyid:\""+obj +"\",notifytypes:\"" + getNotifytypes($)+"\"}";
	dialog=talkweb.Components.Dialog({
		left : 150,
		top :  50,
		width: "700",
		title : "详情",
		height: "400",
		src : "groupmsgeavInfo.html?data="+data
	});
}

//刷新datagrid
function freshuserdata(){
	var msggridob = talkweb.ControlBus.getInstance("msgdata");
	msggridob.refreshData();
}

//单条删除
function deleteone(obj){
	var value = obj + ",";
	$.ajax({
        type: "POST",
        url: "msg_doDeleteMsgAndVerify.action?id="+value,
        dataType: "json",
        success: function(msgs){
            alert(msgs.msg);
            freshuserdata(); //刷新datagrid
        }
    });
}
//批量删除
function deletemany(){
	var msggridob = talkweb.ControlBus.getInstance("msgdata");
	var list = msggridob.getValue();
	var orgids = "";
    if (list.length <= 0) {
        alert("请至少选择一条记录进行操作");
        return;
    }
    for (var i = 0; i < list.length; i++) {
        var obj = list[i];
        orgids += obj.verifyid + ",";
    }
    $.ajax({
        type: "POST",
        url: "msg_doDeleteMsgAndVerify.action?id=" + orgids,
        dataType: "json",
        success: function(msgs){
            alert(msgs.msg);
            freshuserdata(); //刷新datagrid
        }
    });
}

function getNotifytypes($){
	var notifytypes = $("#notifytype>option");
	var notifytypestr = "";
	if( notifytypes.length==1 ){
		return notifytypes[0].val();
	}else{
		for( var i = 0 ; i < notifytypes.length;i++ ){
			if(i==(notifytypes.length-1)||i===0){
				notifytypestr = notifytypestr+$(notifytypes[i]).val();
			}else{
				notifytypestr = notifytypestr+$(notifytypes[i]).val()+";";
			}
		}
		return notifytypestr;
	}	
}