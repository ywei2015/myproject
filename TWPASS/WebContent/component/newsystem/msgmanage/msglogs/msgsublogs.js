var request = talkweb.ControlBus.getRequest();
var pid = request['pId'];
talkweb.Bus.ready({
	items : [
	{
		classPath:"Components.DataGrid",
		options:{
			id:"msggrid",
			name:"msggrid",
			container:"msggrid",
			initSource:"msg_queryMsgSubLogs.action?pId="+pid,
			rows: 10, //指定展示的行数
            saveInstance: true,
            fields : [
            {
            	name: 'msgid',
            	hide:true
        	},
        	{
            	text: '消息主题',
            	name: 'title',
            	width :100
        	},
        	{
            	text: '消息类型',
            	name: 'msgtypename',
            	width :100
        	},
        	{
            	text: '发送方式',
            	name: 'notifytypename',
            	width :100
        	}, {
            	text: '接收人',
            	name: 'realreceivername',
            	width :100
        	}, {
            	text: '发送时间',
            	name: 'sendtime',
            	width :100
        	},
        	{
            	text: '消息状态',
            	name: 'statusname',
            	width :100
        	},{
        		name:"logid",
        		hide:true
        	},{
             	text: '操作',
	            filter:[{
	            	type: "control",
                    cond: "none",
                    controlList: [{
                        calssName: "BaseControl.Anchor",
                        options: {
                            value: " 重新发送 ",
                            click: function(){
                            	if(confirm('您确定要重新发送消息吗?')){
                            		var value = talkweb.ControlBus.getInstance("msggrid").getRowValue(this);
									$.ajax({
        								type: "POST",
       	 								url: "msg_reSendMsg.action?logId="+value.logid,
        								dataType: "json",
        								success: function(msgs){
            								alert(msgs.msg);
            								talkweb.ControlBus.getInstance("msggrid").refreshData(true); //刷新datagrid
        								}
    								});
                            	}
                            }
                        }
                    }]
	            }]
        	}
            ]
		}
	},{
		classPath:"BaseControl.Button",
		options:{
			container:"buttons",
			value:"关闭",
			className:"btn_close",
			click:function(){
				window.parent.dialog.close();
			}
		}
	}
	]
});

function resend(){
	
}
