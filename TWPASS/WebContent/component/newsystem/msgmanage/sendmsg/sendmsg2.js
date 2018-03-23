talkweb.Bus.ready({
    items:[
        {
        	//加此属性是为了重新执行总线队列的程序，可以用作登录超时判断，a.html是一个不存在URL
        	configSource:"a.html",
            classPath:"Components.Form",
            options:{
	            id: "msgform",
	            saveInstance: true,			
                autoBuild : false,
                submitJson:false,
                name:"msgform",
                action:"msg_sendMsg.action",
                initSource:"msg_initMsgFrom.action",
                afterLoad:function(){
//        			this.setValue({
//        				"dicttypename":dicttypename,
//        				"dictEntryInfo.dicttypeid":dicttypeid,
//        				"dictEntryInfo.isshow":1,
//        				"dictEntryInfo.isnull":1,
//        				"dictEntryInfo.ismodify":1});
        		},
                submitCallback:function(result){
					if(result.status=='ok')
					{
						alert(result.msg);
						parent.talkweb.ControlBus.getInstance("dictEntries").refreshData();
						parent.addDictPopupObj.close();
					}else{
						alert(result.msg);
					}                                        
                },
                items:[
                       {
                        classPath:"BaseControl.Hidden",
                        options:{
                            name:"data"
                        }
                    },{
                        classPath:"BaseControl.Hidden",
                        options:{
                            name:"sendFun"
                        }
                    },{
                        classPath:"BaseControl.Hidden",
                        options:{
                            name:"notifytype"
                        }
                    },{
                        classPath:"BaseControl.Hidden",
                        options:{
                            name:"notifytypename"
                        }
                    },{
                        classPath:"BaseControl.Hidden",
                        options:{
                            name:"frequencyname"
                        }
                    },{
                        classPath:"BaseControl.Hidden",
                        options:{
                            name:"starttime"
                        }
                    },{
                        classPath:"BaseControl.Label",
                        options:{
                            name:"servertime"
                        }
                    },{
                        classPath:"BaseControl.Text",
                        options:{
                            name:"msgTitle",
                            validateItems:{
                                requires:true,
                                maxLength:100
                            }
                        }
                    },{
                        classPath:"BaseControl.Hidden",
                        options:{
                            name:"receiver"
                        }
                    },{
                        classPath:"BaseControl.Select",
                        options:{
                            name:"msgtype",
                            change:changeMSGTypeEvent
                        }
                    },{
                        classPath:"BaseControl.Select",
                        options:{
                            name:"templateid"//选择模板
                        }
                    }, {
						classPath : "BaseControl.TextArea",
						options : {
							name : "content"
						}
					}
                ]                        
            }
        },{
        	classPath:"Components.CheckBoxGroup",
        	container: "sendTypeTd",
			options:{	        	
				name : "sendTypes",
				items : [{
					value : "test1",
					text : "测试1"
				}, {
					value : "test2",
					text : "测试2"
				}],
				value: ["test1","test2"]
			}
        }
    ]
});

function changeMSGTypeEvent(){
	$("#template").empty();
	if ($(this).val() == ''){
		$("#template").html("<option value=\"\">不使用模版</option>");
		return;
	}
	$.post("msg_getSendMsgTemplate.action",{"MSGTYPE" : $(this).val()},
			function(result) {
				$("#template").html(result);
			}
		);	
}