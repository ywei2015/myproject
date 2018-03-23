var request = talkweb.ControlBus.getRequest();//获取请求对象
var roleId = request['roleId'];
var pid = request['pid'];
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
			initSource: 'role_getRoleScopes.action?roleId='+roleId+"&pid="+pid,
			name: "rolegrid",
			id: "dataright",
			container: "dataright",
            saveInstance: true,
            rows: 5, //指定展示的行数
            pager:false,//默认为true,为false不带分页工具栏
            fields : [{
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

