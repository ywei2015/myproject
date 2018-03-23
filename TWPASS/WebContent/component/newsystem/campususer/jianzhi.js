var request = talkweb.ControlBus.getRequest();// 获取请求对象
var staffId = request['staffId'];
var usercode = request['usercode'];
talkweb.Bus.ready({
	items :[
	{
		classPath: "BaseControl.Label",
		width : "50%",
		options: {
			value: "当前员工：",
			container:"usercode"
		}
	},{
		classPath: "BaseControl.Label",
		width : "50%",
		options: {
			value: usercode,
			container:"usercode"
		}
	},
	{
		classPath: "BaseControl.Button",
		options: {
			value: "添加兼职",
			width:"80px",
			className:"btn_add",
			container:"buttons",
			click: showadduserpart
		}
	},
	{
		classPath: "BaseControl.Button",
		options: {
			value: "删除兼职",
			width:"80px",
			className:"btn_close",
			container:"buttons",
			click: deleteuserrole
		}
	},
	{
		classPath: "Components.DataGrid",
		options:{
			initSource: 'user_getPartUser.action?staffId='+staffId,
			name: "rolegrid",
			id: "userrole",
			container: "userrolegrid",
			rows: 10, // 指定展示的行数
            multiple: true, // 是否有复选框
            // seqNum: true, //行序号
            saveInstance: true,
            pager:"none",
            fields : [
            {
            	text: "用户编号",
            	name: "userid",
            	width: "15%"
            },
            {
            	text: "用户帐号",
            	name: "usercode",
            	width: "20%"
            },
            {
            	text: "兼职组织",
            	name: "orgname",
            	width: "25%"
            },
            {
            	text: "兼职岗位",
            	name: "posname",
            	width: "20%"
            },
            {
            	text: "上级领导",
            	name: "upusername",
            	width: "20%"
            }]
        }
	},{
		classPath:"BaseControl.Button",
		options:{
			value:"关闭",
			container:"buttons",
			className:"btn_close",
			click:function(){
				window.parent.jianzhidialog.close();
			}
		}
	}]
	
});

// 显示新增兼职
function showadduserpart(){
	var link = "addjianzhi.html?staffId="+staffId+"&usercode="+usercode;
	window.location.href=link;
}
// 删除
function deleteuserrole(){
	// 获取datagrid对象
	var userroleob = talkweb.ControlBus.getInstance("userrole");
	var roleids = userroleob.getValue();
	if(roleids <= 0){
		alert("请至少选择一条记录进行操作");
        return;
	}
	if(confirm("确认删除？"))
	{
		var ids = "";
		for (var i = 0; i < roleids.length; i++) {
	        var obj = roleids[i];
	        ids += obj.userid + ",";
	    }
		$.ajax({
	        type: "POST",
	        url: "user_delPartUser.action?id="+ids,
	        dataType: "json",
	        success: function(msgs){
	            alert(msgs.msg);
	            if(msgs.status=="ok"){
	            	userroleob.refreshData(); // 刷新datagrid
	            	parent.talkweb.ControlBus.getInstance("datagrid").refreshData();
	            }
	            
	        }
	    });		
	}
}
