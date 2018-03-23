var request = talkweb.ControlBus.getRequest();//获取请求对象
var id = request['id'];
var userid = request['userid'];
talkweb.Bus.ready({
	items :[{
		classPath: "BaseControl.Button",
		options: {
			value: "返回",
			className:"btn_return",
			container:"buttons",
			click: function(){
				window.parent.datadialog.close();
			}
		}
	},
	{
		classPath: "Components.DataGrid",
		options:{
			initSource: 'user_showUserDataRight.action?id='+id+"&userid="+userid,
			name: "rolegrid",
			id: "dataright",
			container: "dataright",
			rows: 5, //指定展示的行数
            pager:false,
            saveInstance: true,
            fields : [
            {
            	text: "规则组名称",
            	name: "ruleSetName",
            	width: "50%"
            },
            {
            	text: "规则关系",
            	name: "relation",
            	width: "50%"
            }]
        }
	}]
	
});

