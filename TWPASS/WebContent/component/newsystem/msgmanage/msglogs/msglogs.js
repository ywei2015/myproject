talkweb.Bus.ready({
	items : [
	{
		classPath:"Components.Form",
		options:{
			id:"msglog",
			name:"msglogform",
			container:"msgform",
			initSource:"msg_initMsgLogSel.action",
			saveInstance:true,
			haveSubmitBtn: false, //是否有提交按钮
            haveResetBtn: false, //是否有重置按钮
            submitCallback: function(result){
                alert(result.msg);
            },
            items : [
            {
            	classPath:"BaseControl.Select",
            	width:"33%",
            	options:{
            		name:"msgType",
            		notes:"消息类型：",
            		id:"msgType"
            	}
            },{
            	classPath:"BaseControl.Select",
            	width:"33%",
            	options:{
            		name:"sendType",
            		notes:"发送方式：",
            		id:"sendType"
            	}
            },{
            	classPath:"BaseControl.Select",
            	width:"34%",
            	options:{
            		name:"state",
            		notes:"状态：",
            		id:"state"
            	}
            },{
            	classPath:"Components.Datepicker",
            	width:"33%",
            	options:{
            		name:"bgTime",
            		notes:"发送时间从：",
            		id:"bgTime",
            		displayFormat : "yyyy-mm-dd",
					switchClock : "off"
            	}
            },{
            	classPath:"Components.Datepicker",
            	width:"33%",
            	options:{
            		name:"edTime",
            		notes:"到：",
            		id:"edTime",
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
            	width:"24%",
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
			id:"msggrid",
			name:"msggrid",
			container:"msggrid",
			initSource:"msg_queryMsgLogs.action",
			rows: 10, //指定展示的行数
            saveInstance: true,
            fields : [
            {
            	text:"消息主题",
            	name:"title",
            	width:"15%",
            	filter : [{
					type : "control_db",
					cond : "none",
					controlList :[{
						calssName : "BaseControl.Anchor",
						options : {
						click :shouinfo
						}
					}]
				}]
            },
            {
            	text:"消息类型",
            	name:"msgtypename",
            	width:"10%"
            },
            {
            	text: '发送方式',
            	name: 'notifytypename',
            	width :"15%"
        	}, {
            	text: '接收人',
            	name: 'receivername',
            	width :"15%"
        	}, {
            	text: '发送时间',
            	name: 'sendtime',
            	width :"12%"
        	},
        	{
            	text: '消息状态',
            	name: 'statusname',
            	width :"13%"
        	},{
             	text: '操作',
	            width:"20%",
                filter: [{
                    type: "control",
                    cond: [
                    {
                    	field:"cz",
                    	match:"1",
                    	controlList:[0,1,2]
                    },
                    {
                    	field:"cz",
                    	match:"2",
                    	controlList:[0,1,2]
                    },{
                    	field:"cz",
                    	match:"3",
                    	controlList:[0,2]
                    },{
                    	field:"cz",
                    	match:"4",
                    	controlList:[0,2]
                    },{
                    	field:"cz",
                    	match:"5",
                    	controlList:[0]
                    },{
                    	field:"cz",
                    	match:"6",
                    	controlList:[0,1]
                    }
                    ],
                    controlList: [
                    {
                        calssName: "BaseControl.Button",
                        options: {
                        	value:"重新发送",
                           	click :resend
                        }
                	},{
                        calssName: "BaseControl.Button",
                        options: {
                        	value:"发送记录",
                           	click :sendhis
                        }
                	},{
                        calssName: "BaseControl.Button",
                        options: {
                        	value:"撤销",
                           	click :cancel
                        }
                	}]
                }]
        	},{
            	name:"logid",
            	hide:true,
            	width:"0%"
            },
            {
            	name: 'msgid',
            	hide:true,
            	width:"0%"
        	},
       		{
            	name: 'statusinfo',
            	hide:true,
            	width:"0%"
        	},{
            	name: 'cz',
            	width :"0%",
            	hide:true
        	}
            ]
		}
	}
	]
});

var dialog = "";
//查询
function search(){
	var formob = talkweb.ControlBus.getInstance("msglog");
	var datagridob = talkweb.ControlBus.getInstance("msggrid");
	datagridob.setOptions({
		//reqParam : dataObj
		reqParam : {
			data : "{"+formob.getValues()+"}"
		}
	});         //重设datagrid的url
	datagridob.refreshData(true);
}

//重置表单reset
function resetlog(){
	var msgformob = talkweb.ControlBus.getInstance("msglog");   
	msgformob.resetClick();
}

//显示详细信息
function shouinfo(){
	var value = talkweb.ControlBus.getInstance("msggrid").getRowValue(this);
	dialog=talkweb.Components.Dialog({
		left : 150,
		top :  50,
		width: "700",
		title : "信息详细",
		height: "400",
		src : "msgloginfo.html?logId="+value.logid
	});
}
//重新发送
function resend(){
	var value = talkweb.ControlBus.getInstance("msggrid").getRowValue(this);
	dialog=talkweb.Components.Dialog({
		left : 150,
		top :  50,
		width: "700",
		title : "修改并重新发送消息",
		height: "400",
		src : "sendmodifymsg.html?logId="+value.logid
	});
}
//消息发送历史记录
function sendhis(){
	var value = talkweb.ControlBus.getInstance("msggrid").getRowValue(this);
	dialog=talkweb.Components.Dialog({
		left : 150,
		top :  50,
		width: "700",
		title : "消息发送历史记录",
		height: "400",
		src : "msgsublogs.html?pId="+value.logid
	});
}

//撤销发送
function cancel(){
	if(confirm('您确定要撤销发送吗?'))
	{
		var value = talkweb.ControlBus.getInstance("msggrid").getRowValue(this);
		$.ajax({
        	type: "POST",
        	url: "msg_pushJob.action?msgId=" + value.msgid,
        	dataType: "json",
        	success: function(msgs){
            	alert(msgs.msg);
            	freshuserdata(); //刷新datagrid
        	}
    	});
	}
}

function freshuserdata(){
	talkweb.ControlBus.getInstance("msggrid").refreshData();
}
