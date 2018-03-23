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
            submitCallback: function(result){
                alert(result.msg);
            },
            items : [
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
			initSource:"msg_getGroupVerifys.action?data={status:0}",
			rows: 10, //指定展示的行数
            saveInstance: true,
             fields : [
             {
				text : "消息主题",
				name : "title",
				width :"17%",
				filter: [{
                    type: "control_db",
                    cond: "none",
                    controlList: [{
                        calssName: "BaseControl.Anchor",
                        options: {
                           click :shouinfo
                        }
                	}]
                }]
			},
			{
				text : "消息类型",
				name : "msgtypename",
				width : "10%"
			},
			{
				text : "发送方式",
				name : "notifytypename",
				width: "13%"
			},
			{
				text : "发送人",
				name : "sendername",
				width : "10%" 
			},
            {
                text : "创建时间",
                name : "createtime",
                width : "13%"
            },
			{
				text : "审核状态",
				name : "statusname",
				width : "12%"
			},
            {
                text : "审核时间",
                name : "verifytime",
                width : "12%"
            },
			{
				text : "操作",
				name : "status",
				width:"13%",
				filter: [{
                    type: "control",
                    cond: "0",
                    controlList: [{
                        calssName: "BaseControl.Anchor",
                        options: {
                        	value:"审核",
                           	click :shenhe
                        }
                	}]
                }]
			},{
				name : "verifyid",
				width :"0%",
				hide:true
			}
             ]
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
	
//跳转至审核页面
function shenhe(){
	var value=talkweb.ControlBus.getInstance("msgdata").getRowValue(this);
	var data = "{verifyid:\""+value.verifyid +"\",notifytypes:\"" + getNotifytypes($)+"\"}";
	dialog=talkweb.Components.Dialog({
		left : 150,
		top :  50,
		width: "700",
		title : "审核",
		height: "400",
		src : "groupmsgeav.html?data="+data
	});
}
//显示详情
function shouinfo(){
	var value=talkweb.ControlBus.getInstance("msgdata").getRowValue(this);
	var data = "{verifyid:\""+value.verifyid +"\",notifytypes:\"" + getNotifytypes($)+"\"}";
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

function getNotifytypes($){
	var notifytypes = $("#notifytype>option");
	var notifytypestr = "";
	if( notifytypes.length==1 ){
		return notifytypes[0].val();
	}else{
		for( var i = 0 ; i < notifytypes.length;i++ ){
			if(i==(notifytypes.length-1)||i==0){
				notifytypestr = notifytypestr+$(notifytypes[i]).val();
			}else{
				notifytypestr = notifytypestr+$(notifytypes[i]).val()+";";
			}
		}
		return notifytypestr;
	}	
}

