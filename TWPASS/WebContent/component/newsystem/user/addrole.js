var request = talkweb.ControlBus.getRequest();//获取请求对象
var operatorid = request['operatorid'];
talkweb.Bus.ready({
	items :[{
		classPath: "BaseControl.Button",
		options: {
			value: "添加角色",
			width:"80px",
			className:"btn_add",
			container:"button1",
			click: showaddrolelocation
		}
	},
	{
		classPath: "BaseControl.Button",
		options: {
			value: "删除角色",
			width:"80px",
			className:"btn_close",
			container:"button2",
			click: deleteuserrole
		}
	},
	{
		classPath: "Components.DataGrid",
		options:{
			initSource: 'user_queryHasRelRoles.action?opid='+operatorid,
			name: "rolegrid",
			id: "userrole",
			container: "userrolegrid",
			rows: 10, //指定展示的行数
            multiple: true, //是否有复选框
            seqNum: true, //行序号
            saveInstance: true,
            fields : [
            {
            	text: "角色编号",
            	name: "roleid",
            	width: "15%"
            },
            {
            	text: "角色名称",
            	name: "rolename",
            	width: "20%"
            },
            {
            	text: "所属应用",
            	name: "appname",
            	width: "15%"
            },
            {
            	text: "所属组织",
            	name: "orgname",
            	width: "50%"
            }]
        }
	},{
		classPath:"BaseControl.Button",
		options:{
			value:"关闭",
			container:"btn",
			className:"btn_close",
			click:function(){
				window.parent.userroledia.close();
			}
		}
	}]
	
});

//新增
var addrole;
function showaddrole(){
	addrole = talkweb.Components.Dialog({
		left : "0",
		top :  "0",
		width: "850",
		title : "用户关联角色",
		height: "450",
		src : "addUserRole.html?opid="+operatorid
		});
}

//新增
function showaddrolelocation(){
	var link = "addUserRole.html?opid="+operatorid;
	window.location.href=link;
}
//删除
function deleteuserrole(){
	//获取datagrid对象
	var userroleob = talkweb.ControlBus.getInstance("userrole");
	var roleids = userroleob.getValue();
	if(roleids <= 0){
		alert("请至少选择一条记录进行操作");
        return;
	}
	var ids = "";
	for (var i = 0; i < roleids.length; i++) {
        var obj = roleids[i];
        ids += obj.roleid + ",";
    }
	$.ajax({
        type: "POST",
        url: "user_delHasRelRoles.action?opid="+operatorid +"&id="+ids,
        dataType: "json",
        success: function(msgs){
            alert('删除成功！');
            userroleob.refreshData(); //刷新datagrid
        }
    });
}

//刷新datagrid
var freshuserdata = function (){
    var userroleob = talkweb.ControlBus.getInstance("userrole");
    userroleob.refreshData();
};
