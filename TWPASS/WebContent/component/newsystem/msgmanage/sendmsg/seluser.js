talkweb.Bus.ready({
	items : [
	{
		classPath:"Components.Form",
		options:{
			id:"searchform",
			container:"userform",
			saveInstance:true,
			initSource:"user_initUserSearchData.action",
			haveSubmitBtn: false, //是否有提交按钮
            haveResetBtn: false, //是否有重置按钮
            submitCallback: function(result){
                alert(result.msg);
            },
            items : [
            {
            	classPath:"Components.Tree",
            	width:"40%",
            	options:{
            		//initSource: "initOrgTree.action",
					id: "orgids",
					name:"orgids",
					showIco: false,
					notes:"组织：",
					saveInstance: true,
					openFloor: 10,
           	 		multiple: false,
            		treeType: "selecttree",
            		onclick:function(){
            			var treeob = talkweb.ControlBus.getInstance("orgids");
           				var id = treeob.getValue("currentNode");
           				$("#orgidval").val(id);
            		}
            	}
            },{
            	classPath:"BaseControl.Text",
            	width:"40%",
            	options:{
 					name:"empname",
 					notes:"用户姓名：",
 					id:"empname"
            	}
            },{
            	classPath:"BaseControl.Button",
            	width:"20%",
            	options:{
            		value:"查询",
            		className:"btn_search",
            		click:search
            	}
            },{
            	classPath:"BaseControl.Text",
            	width:"40%",
            	options:{
            		name:"userid",
            		notes:"用户账号：",
            		id:"userid"
            	}
            },{
            	classPath:"BaseControl.Select",
            	width:"40%",
            	options:{
            		name:"usertype",
            		notes:"用户类型：",
            		id:"usertype"
            	}
            },{
            	classPath:"BaseControl.Button",
            	width:"20%",
            	options:{
            		value:"重置",
            		className:"btn_reset",
            		click:resetmsg
            	}
            },{
            	classPath:"BaseControl.Hidden",
            	width:"0%",
            	options:{
            		id:"orgidval",
            		name:"orgid"
            	}
            }
            ]
		}
	},{
		classPath:"Components.DataGrid",
		options:{
			id:"usergrid",
			container:"usergrid",
			initSource:"queryEmpInfos.action",
			saveInstance:true,
			rows:10,
			multiple: true, //是否有复选框
			fields : [
            {
            	name: 'userid',
                text:"用户账号",
                width: "50%"
            },{
                name: 'empname',
                text: '姓名',
                width: "50%"
            },{
                name: 'operatorid',
                text:"用户OID",
                width:"0%",
                hide: true
            },{
            	name:"empid",
            	hide:true,
            	width:"0%"
            }
            ]
		}
	},{
		classPath:"BaseControl.Button",
		options:{
			value:"确定",
			container:"buttons",
			className:"btn_save",
			click:sure
		}
	}
	]
});
//查询
function search(){
	var userformob = talkweb.ControlBus.getInstance("searchform");
	var usergridob = talkweb.ControlBus.getInstance("usergrid");
	usergridob.setOptions({//重设datagrid的url
		//reqParam : datavalue
		reqParam : {data : "{"+userformob.getValues()+"}"}//后台以idata方式获取参数
	});         
	usergridob.refreshData();
}
//重置表单reset
function resetmsg(){
	var userformob = talkweb.ControlBus.getInstance("searchform"); //获取form对象
	$("#orgidval").val(null);
	userformob.resetClick();
}
//确定
function sure(){
	var list = talkweb.ControlBus.getInstance("usergrid").getValue();
	var ids="";
    var txt="";
    for(var i =0; i < list.length;i++){
    	if(i > 0){
    		ids+=",";
			txt+=",";
    	}
    	ids+=list[i].operatorid;
		txt+=list[i].empname;
    	
    }
    window.parent.closeAndUptxt(ids,txt);
}