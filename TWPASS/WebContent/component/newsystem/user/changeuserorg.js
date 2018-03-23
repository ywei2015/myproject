var request = talkweb.ControlBus.getRequest();//获取请求对象
var epmid = request['empids'];

talkweb.Bus.ready({
	items :[{
		classPath: "Components.Form",
		options: {
			id: "changeorg",
			name: "changeorgform",
			container: "changeorgform",
			initSource: "role_initRoleData.action",
			saveInstance: true,
			haveSubmitBtn: false, //是否有提交按钮
			haveResetBtn: false, //是否有重置按钮
			submitCallback : function(result) {
				alert(result.msg);
			},
			errorFile : "errorConfig.json",
			items: [
			{
				classPath: "BaseControl.Hidden",
				width:"0%",
				options: {
					name: "empids",
					id: "empids"
				}
			},{
				classPath: "Components.Tree",
				width: '100%',
				options: {
					name: "orgid",
					notes: "调往机构：",
					id: "orgid",
					initSource: "org_searchAllOrg.action",
					showIco: true,
					saveInstance: true,
					topParentid: "-1",
					openFloor: 10,
            		multiple: false,
           			treeType: "selecttree"
				}
			}
			]
		}
	},{
		classPath: "BaseControl.Button",
		options: {
			container:"buttons",
			value: "确定",
			className:"btn_save",
			click: submitorg
		}
	},{
		classPath: "BaseControl.Button",
		options: {
			container:"buttons",
			className:"btn_close",
			value: "取消",
			click: close
		}
	}]
});

// 提交
function submitorg(){
	var changeformob = talkweb.ControlBus.getInstance("changeorg");
	var orgtreeob = talkweb.ControlBus.getInstance("orgid");
	$("#empids").val(epmid);
	var orgvalue = orgtreeob.getCurrentId();
	var values = changeformob.getValues();
	var orgid = orgvalue;
	if(orgid==null||orgid==""){
		alert('请选择一个调往机构！');
		return false;
	}
	var paramvalue = "{"+values+"}";
	$.ajax({
        type: "POST",
        url: "user_moveEmpFromOrgToOrg.action?data="+paramvalue,
        dataType: "json",
        success: function(msgs){
            alert(msgs.msg);
             //刷新datagrid
            window.parent.freshuserdata();
            window.parent.changeorgdia.close();
        }
    });
}
//ajax回调方法
var callback = function(data){
	var dataobj = eval("("+data+")");//转换为json对象 
	if(dataobj.status=="ok"){
		alert(dataobj.msg);
		//刷新父页面
	}
	if(dataobj.status=="bad"){
		alert(dataobj.msg);
	}
}
//关闭
function close(){
	window.parent.changeorgdia.close();
}