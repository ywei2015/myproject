var request = talkweb.ControlBus.getRequest();//获取请求对象
var id = request['id'];
var positionid = request['positionid'];
talkweb.Bus.ready({
	items :[{
		classPath: "BaseControl.Button",
		options: {
			value: "返回",
			className:"btn_close",
			container:"buttons",
			click: function(){
				window.parent.datadialog.close();
			}
		}
	},
	{
		classPath: "Components.DataGrid",
		options:{
			initSource: 'position_showPosDataRight.action?id='+id+"&positionid="+positionid,
			name: "rolegrid",
			id: "dataright",
			container: "dataright",
			rows: 5, //指定展示的行数
            multiple: false, //是否有复选框
            //seqNum: true, //行序号
            pager:false,
            saveInstance: true,
            fields : [
            {
            	text: "规则编码",
            	name: "ruleId",
            	width: "15%"
            },
            {
            	text: "规则名称",
            	name: "ruleName",
            	width: "20%"
            },
            {
            	text: "应用名称",
            	name: "appName",
            	width: "20%"
            },
            {
            	text: "规则值",
            	name: "ruleValue",
            	width: "45%"
            }]
        }
	}]
	
});

