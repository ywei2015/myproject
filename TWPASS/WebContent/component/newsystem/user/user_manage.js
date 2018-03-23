var user_data_url = 'queryEmpInfos.action';
var order_data_url = 'queryEmpInfos.action';
var emp_order_action = "modifyEmpSortNo.action";
var tree_url = 'initOrgTree.action';
talkweb.Bus.ready({
	items :[
	{
		classPath : "Components.Tree",
		options: {
			initSource: "org_searchAllOrg.action",
			id: "orgtreedata",
			container: "orgtree",
			showIco: false,
			saveInstance: true,
			openFloor: 10,
            multiple: false,
            treeType: "normal",
            onclick : clicktree
		}	
	},
	{
		classPath: "Components.Form",
		options:{
			id: "uform",
			name: "userform",
			container: "userseach",
			initSource : "user_initUserData.action",
			saveInstance: true,
			haveSubmitBtn: false, //是否有提交按钮
			haveResetBtn: false, //是否有重置按钮
			submitCallback : function(result) {
				alert(result.msg);
			},
			errorFile : "errorConfig.json",
			items:[
			{
				classPath:"BaseControl.Text",
				width:"35%",
				options:{
					name:"empname",
					notes:"用户姓名：",
					id: "empname"
				}
			},
			{
				classPath :"BaseControl.Text",
				width:"35%",
				options: {
					name: "userid",
					id: "userid",
					notes: "用户账号："
				}
			},
			{
				classPath :"BaseControl.Select",
				width:"30%",
				options: {
					name: "usertype",
					id: "usertype",
					notes: "用户类型："
				}
			},{
				classPath :"BaseControl.Select",
				width:"35%",
				options: {
					name: "empstatus",
					id: "empstatus",
					notes: "状态："
				}
			},
			{
				classPath: "BaseControl.Button",
				width:"10%",
				options :{
					value: "  查询",
					className:"btn_search",
					click: search
				}
			},
			{
				classPath: "BaseControl.Button",
				width:"55%",
				options :{
					value: "  重置",
					className:"btn_reset",
					click: resetlog
				}
			},
			{
				classPath:"BaseControl.Hidden",
				width:"0%",
				options:{
					name:"orgid",
					id: "orgid"
				}
			}
			]
		}
	},
	{
		classPath: "BaseControl.Button",
		options:{
			value: "增加",
			container:"buttons",
			className:"btn_add",
			click: adduser
		}
	},
	{
		classPath: "BaseControl.Button",
		options:{
			value: "调动组织",
			width:"80px",
			container:"buttons",
			className:"btn_reset",
			click: changeorg
		}
	},
	{
		classPath: "BaseControl.Button",
		options:{
			value: "删除",
			container:"buttons",
			className:"btn_close",
			click: delmanyuser
		}
	},
	//用户数据grid
	{
		classPath: "Components.DataGrid",
		options:{
			initSource: user_data_url,
			name: "usergrid",
			id: "datagrid",
			container: "usergrid",
			rows: 10, //指定展示的行数
            multiple: true, //是否有复选框
            seqNum: true, //行序号
            saveInstance: true,
            fields : [
            {
            	text: "姓名",
            	name: "realname",
            	width:'10%',
            	filter: [{
                    type: "control_db",
                    cond: "none",
                    controlList: [{
                        calssName: "BaseControl.Anchor",
                        options: {
                           click :showuserinfo
                        }
                	}]
                }]
            },
            {
            	text: "用户账号",
            	name: "userid",
            	width:'10%'
            },
            {
            	text: "所属组织",
            	name: "orgname",
            	width:'10%'
            },{
            	text: "手机号码",
            	name: "mobileno",
            	width:'10%'
            },{
            	text: "邮箱地址",
            	name: "oemail",
            	width: '10%'
            },
            {
            	text: "用户类型",
            	name: "usertypename",
            	width:'10%'
            },
            {
            	text: "状态",
            	name: "empstatusname",
            	width:'10%'
            },
            {
            	text: "用户OID",
            	name: "operatorid",
            	hide:true
            },
            {
            	name: "empid",
            	hide : true
            },
            {
            	text: "常用操作",
            	name: "op",
            	width:'15%',
            	filter : [{
					type : "control",
					cond : "none",
					controlList : [
					{
						calssName : "BaseControl.Anchor",
						options : {
							value : " 关联角色  ",
							click : userrole
						}
					},
					{
						calssName : "BaseControl.Anchor",
						options : {
							value : "  修改  ",
							click : modifyuser
						}
					}, {
						calssName : "BaseControl.Anchor",
						options : {
							value : "  删除" ,
							click : deloneuser
						}
					}]
				}
				]
            }
            ]
		}
	}
	]
});
//组织树点击事件方法
function clicktree(){
    var orgtreeob = talkweb.ControlBus.getInstance("orgtreedata");//获取组织树对象
    var userformob = talkweb.ControlBus.getInstance("uform");//获取form表单对象
    var userdataob = talkweb.ControlBus.getInstance("datagrid");//获取datagrid对象
    var orfid = orgtreeob.getCurrentId();
    $("#orgid").val(orfid);//设置表单中的orgid
    var paramvalue = userformob.getValues();//获取表单参数值
    userdataob.setOptions({
		//reqParam : dataObj
		reqParam : {
			data : "{"+paramvalue+"}"
		}
	});         //重设datagrid的url
	userdataob.refreshData();
}
//查询
function search(){
    var userformob = talkweb.ControlBus.getInstance("uform");//获取form表单对象
    var userdataob = talkweb.ControlBus.getInstance("datagrid");//获取datagrid对象
    var paramvalue = userformob.getValues();//获取表单参数值
    userdataob.setOptions({
		//reqParam : dataObj
		reqParam : {
			data : "{"+paramvalue+"}"
		}
	});         //重设datagrid的url
	userdataob.refreshData();
}
//重置
function resetlog(){
	var userformob = talkweb.ControlBus.getInstance("uform");//获取form表单对象
	userformob.resetClick();
}

//添加用户
var adduserdia = "";
function adduser(){	
	adduserdia = talkweb.Components.Dialog({
		left : 0,
		top :  0,
		width: "700",
		title : "新增用户",
		height: "500",
		src : "adduser.html"
		});
}
//调动组织
var changeorgdia;
function changeorg(){
	var empids = talkweb.ControlBus.getInstance("datagrid").getValue();
	if(empids.length <= 0){
		alert("请至少选择一条记录进行操作");
        return;
	}
	var ids = "";
	for(var i=0; i < empids.length; i++){
		ids += empids[i].empid +",";
	}
	changeorgdia = talkweb.Components.Dialog({
		left : 150,
		top :  150,
		width: "500",
		title : "调动组织",
		height: "250",
		src : "changeuser_org.html?empids="+ids
	});
}
//导入用户
function inputuser(){
	talkweb.Components.Dialog({
		left : 50,
		top :  50,
		width: "700",
		title : "导入用户数据",
		height: "300",
		src : "../../system/upload/upload.jsp"
	});
}
//批量删除
function delmanyuser(){
	var userdataob = talkweb.ControlBus.getInstance("datagrid");//获取userdatagrid独享
	var orgtreeob = talkweb.ControlBus.getInstance("orgtreedata");//获取组织树对象
	var list = userdataob.getValue();
	if(list.lenth <= 0){
		alert('请至少选择一条记录进行操作');
		return;
	}
	var ids = "";
	for(var i=0; i < list.length; i++){
		ids += list[i].empid +",";
	}
	$.ajax({
        type: "POST",
        url: "user_delUser.action?id="+ids,
        dataType: "json",
        success: function(msgs){
            alert(msgs.msg);
             //刷新datagrid
        }
    });
    orgtreeob.reLoad();
    userdataob.refreshData();
}
//显示用户信息
var userinfo;
function showuserinfo(){
	var logformob = talkweb.ControlBus.getInstance("datagrid").getRowValue(this);
	
	var empid = logformob.empid;
	userinfo = talkweb.Components.Dialog({
		left : 50,
		top :  50,
		width: "700",
		title : "用户信息",
		height: "500",
		src : "userinfo.html?empid="+empid
	});
}
//修改用户信息
var modifyuserdia;
function modifyuser(){
	var logformob = talkweb.ControlBus.getInstance("datagrid").getRowValue(this);
	var empid = logformob.empid;
	modifyuserdia = talkweb.Components.Dialog({
		left : 50,
		top :  50,
		width: "700",
		title : "编辑用户信息",
		height: "400",
		src : "modifyuser.html?empid="+empid
	});
}

//单个删除
function deloneuser(){
	var userdataob = talkweb.ControlBus.getInstance("datagrid");//获取userdatagrid独享
	var logformob = userdataob.getRowValue(this);
	var empid = logformob.empid;
	var list = userdataob.getValue();
	
	var ids = empid + ",";
	
	$.ajax({
        type: "POST",
        url: "user_delUser.action?id="+ids,
        dataType: "json",
        success: function(msgs){
            alert(msgs.msg);
             
        }
    });
    //刷新datagrid
    userdataob.refreshData();
}
//关联角色
var userroledia;
function userrole(){
	var userdataob = talkweb.ControlBus.getInstance("datagrid");//获取userdatagrid独享
	var logformob = userdataob.getRowValue(this);
	userroledia = talkweb.Components.Dialog({
		left : 50,
		top :  50,
		width: "950",
		title : "用户已经关联的角色",
		height: "500",
		src : "addrole.html?operatorid="+logformob.operatorid
	});
}

//刷新用户信息列表
var freshuserdata = function(){
	var userdataob = talkweb.ControlBus.getInstance("datagrid");
	userdataob.refreshData();
};