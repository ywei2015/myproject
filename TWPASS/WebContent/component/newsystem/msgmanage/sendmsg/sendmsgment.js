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
            initSource : "msg_initSendMsgTemplates.action",
			//action : "../services/formsubmit.jsp",
            submitCallback: function(result){
                alert(result.msg);
            },
            //errorFile: "errorConfig.json",
            items : [
            {
            	classPath:"BaseControl.Select",
            	width:"33%",
            	options:{
            		name:"sendtypemsg",
            		notes:"发送方式：",
            		id:"sendtypemsg"
            	}
            },{
            	classPath:"Components.Datepicker",
            	width:"33%",
            	options:{
            		name:"msgstart_date",
            		notes:"时间段从：",
            		id:"msgstart_date",
            		displayFormat : "yyyy-mm-dd",
					switchClock : "off"
            	}
            },{
            	classPath:"Components.Datepicker",
            	width:"34%",
            	options:{
            		name:"msgend_date",
            		notes:"到：",
            		id:"msgend_date",
            		displayFormat : "yyyy-mm-dd",
					switchClock : "off"
            	}
            },{
            	classPath:"BaseControl.Text",
            	width:"33%",
            	options:{
            		name:"msgname",
            		notes:"消息主题：",
            		id:"msgname"
            	}
            },{
            	classPath:"BaseControl.Button",
            	width:"8%",
            	options:{
            		value:"查询",
            		className:"btn_search",
            		click:function(){
            			
            		}
            	}
            },{
            	classPath:"BaseControl.Button",
            	width:"59%",
            	options:{
            		value:"重置",
            		className:"btn_reset",
            		click:function(){
            			
            		}
            	}
            }
            ]
    	}
    },{
    	classPath:"Components.DataGrid",
    	optons:{
    		id:"msggrid",
    		name:"msggrid",
    		container:"msggrid",
    		initSource:"msg_getMsgSendTemplates.action",
    		rows: 10, //指定展示的行数
            multiple: true, //是否有复选框
            //seqNum: true, //行序号
            saveInstance: true,
	        fields: [
	        {
            	text: '消息主题',
            	name: 'msgname',
            	width: "15%"
        	},
       	 	{
            	text: '消息类型',
            	name: 'msgtype',
            	width: "10%"
        	},
        	{
            	text: '发送方式',
            	name: 'sendtypemsg',
            	width: "15%"
        	},
        	{
            	text: '接收人',
            	name: 'accepthuman',
            	width: "15%"
        	}, {
            	text: '发送时间',
            	name: 'senddate',
            	width: "15%"
        	}, {
            	text: '消息模板',
            	name: 'msgmodel',
            	width: "15%"
        	},
        	{
            	text: '操作',
            	width: "15%",
            	filter: [{
                    type: "control",
                    cond: "none",
                    controlList: [
                    {
                        calssName: "BaseControl.Anchor",
                        options: {
                            value: " 详情 ",
                            click: showinfo
                        }
                    }]
               	}]
        	},{
        		name:"sendmsgid",
        		hide:true
        	}
	        ]
    	}
    }
    ]
});

function search(){
	var msgformob = talkweb.ControlBus.getInstance("msgform");         //获取form对象
	var msggridob = talkweb.ControlBus.getInstance("msggrid");         //获取datagrid对象
	//var datavalue = eval("({"+msgformob.getValues()+"})");				//转换为json对象  
	msggridob.setOptions({//重设datagrid的url
		//reqParam : datavalue
		reqParam : {data : "{"+msgformob.getValues()+"}"}//后台以idata方式获取参数
	});         
	msggridob.refreshData();
}

//重置表单reset
function resetmsg(){
	var msgformob = talkweb.ControlBus.getInstance("msgdata"); //获取form对象
	msgformob.resetClick();
}

function showinfo(){
	var msgobj = talkweb.ControlBus.getInstance("msggrid").getRowValue(this);
	dialog = talkweb.Components.Dialog({
		left : "50",
		top :  "50",
		width: "800",
		height: "400",
		title: "消息模板信息",
		src : "sendmsgmentinfo.html?sendmsgid="+msgobj.templateid
	});
}
