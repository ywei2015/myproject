var request = talkweb.ControlBus.getRequest();//获取请求对象
var operatorid = request['opid'];
talkweb.Bus.ready({
	items :[{
		classPath: "Components.Form",
		options: {
			id: "roleform",
			name: "searchform",
			container: "rolesearchform",
			initSource: "role_initUserRoleData.action",
			saveInstance: true,
			haveSubmitBtn: false, //是否有提交按钮
			haveResetBtn: false, //是否有重置按钮
			submitCallback : function(result) {
				alert(result.msg);
			},
			errorFile : "errorConfig.json",
			items: [
			{
				classPath: "Components.Tree",
				width:"38%",
				options: {
					width:"150px;",
					name: "orgids",
					notes: "所属组织：",
					id: "orgids",
					//initSource: "org_searchAllOrg.action",
					showIco: true,
					saveInstance: true,
					openFloor: 3,
            		multiple: false,
           			treeType: "selecttree",
           			onclick:function(){
           				var treeob = talkweb.ControlBus.getInstance("orgids");
           				var id = treeob.getValue("currentNode");
           				$("#orgidval").val(id);
           			}
				}
			},{
				classPath: "BaseControl.Select",
				width:"31%",
				options: {
					name: "appid",
					notes: "所属应用：",
					width:"100px",
					id: "appid"
				}
			},{
				classPath: "BaseControl.Text",
				width:"31%",
				options: {
					name: "rolename",
					notes: "角色名称：",
					width:"100px",
					id: "rolename"
				}
			},{
				classPath: "BaseControl.Button",
				width:"10%",
				options: {
					value: "查询",
					className:"btn_search",
					click:serchrole
				}
			},{
				classPath: "BaseControl.Button",
				width:"90%",
				options: {
					value: "重置",
					className:"btn_reset",
					click: reset
				}
			},{
				classPath: "BaseControl.Hidden",
				width:"0%",
				options: {
					name: "opid",
					id: "opid"
				}
			},{
				classPath: "BaseControl.Hidden",
				width:"0%",
				options: {
					name: "orgid",
					id: "orgidval"
				}
			}]
		}
	},{
		classPath: "BaseControl.Button",
		options:{
			container: "buttons",
			value: "关联",
			className:"btn_add",
			click: addrole
		}
	},{
		classPath: "BaseControl.Button",
		options:{
			container: "buttons",
			value: "返回",
			className:"btn_reset",
			click: function(){
				var link = "addrole.html?operatorid="+operatorid;
            	window.location.href=link;
			}
		}
	},{
		classPath: "Components.DataGrid",
		options: {
			name: "roledatagrid",
			id: "searchrole",
			container: "rolegrid",
			initSource: "role_queryNoRelRoles.action?opid=" + operatorid,
			rows: 10, //指定展示的行数
            multiple: true, //是否有复选框
            seqNum: true, //行序号
            saveInstance: true,
             fields : [
            {
            	text: "角色编码",
            	name: "roleid",
            	width: "15%"
            },
            {
            	text: "角色名称",
            	name: "rolename",
            	width: "20%"
            },{
            	text: "所属应用",
            	name: "appname",
            	width: "15%"
            },
            {
            	text: "所属组织",
            	name: "orgname",
            	width: "50%"
            }
            ]
		}
	}]
});
//查询
function serchrole(){
	$("#opid").val(operatorid);
	var rolformob = talkweb.ControlBus.getInstance("roleform");
	var rolegridob = talkweb.ControlBus.getInstance("searchrole");
	var parmobj = rolformob.getValues();
	rolegridob.setOptions({
		//reqParam : dataObj
		reqParam : {
			data : "{"+parmobj+"}"
		}
	});         //重设datagrid的url
	rolegridob.refreshData();
}
//重置
function reset(){
	var rolformob = talkweb.ControlBus.getInstance("roleform");
	$("#orgidval").val(null);
	rolformob.resetClick();
}
//关联
function addrole(){
	var rolegridob = talkweb.ControlBus.getInstance("searchrole");
	var objvalue = rolegridob.getValue();
	if(objvalue.length <= 0){
		alert("没有选择添加的角色!");
	    return false;
	}
	var ids = "";
	for (var i = 0; i < objvalue.length; i++) {
        var obj = objvalue[i];
        ids += obj.roleid + ",";
    }
	$.ajax({
        type: "POST",
        url: "user_addRoleWithuser.action?operid="+operatorid +"&roleids="+ids,
        dataType: "json",
        success: function(msgs){
            alert(msgs.msg);
            //刷新父页面datagrid
            //window.parent.freshuserdata();
            //window.parent.addrole.close();
            var link = "addrole.html?operatorid="+operatorid;
            window.location.href=link;
        },
         error:function(){
			alert("关联角色失败！");
		}
    });
 }
 //