//===================================================================
// 项目名称：澄城mes系统
// 模块名称：角色管理列表
// 类    名：RoleMgr.js
//===================================================================

var searchpanel = {
	name  : "search",   
    type  : "searchpanel", 
    title : "查询条件",
    berth : "SearchPanel",
    items : [
        { 
        	type:"textinput",
            title:"角色名称",
            dataIndex:'name'
        },
        
		{
			type:"combox",   
			title:"有效性",
			dataIndex: 'sysFlagName',
			fieldText: 'name',
			fieldValue: 'code',
			data:[{'code':1,'name':'有效'},{'code':0,'name':'无效'}],
			//url: "http://192.168.12.215/MesWebApi/api/test/test/getname",
			root: "dataset"
		}
//		{
//			type:"combox",   
//			title:"有效性11111",
//			dataIndex: 'f_sys_flag',
//			fieldText:"name",
//			fieldValue:"code",
//			//data:[{'code':1,'name':'是'},{'code':0,'name':'否'}],
//			url:"http://192.168.12.215/MesWebApi/api/test/test/getname",
//			root: "dataset"
//		}
    ],
    layoutConfig : {
        columns : 4
    },
  onsearch: function(){
	  console.log("1111");
	  refreshTable();
  }
};
var $table;
var $table1;

$(function () {
	
	 $table = $('#table').bootstrapTable({ 
        locale:'zh-CN',//中文支持 
        pagination: true,//是否开启分页（*）
//      striped:true,  //奇偶行渐色表
        pageSize: 10,//每页的记录行数（*）
        pageList: [10,25,50,100],//可供选择的每页的行数（*）
        idField: "id", //标识哪个字段为id主键
        uniqueId: "id",   //每一行的唯一标识，一般为主键列
        sidePagination: "server", //分页方式：client 客户端分页，server 服务端分页（*）
        showColumns: true, //是否显示所有的列
        minimumCountColumns: 2, //最少允许的列数
//      showToggle: true, //是否显示详细视图和列表视图的切换按钮
        toolbar: '#toolbar', //工具按钮用哪个容器
        singleSelect: true, 
	    columns: [
		    {field: 'checkStatus',checkbox: true, edit:false,formatter : function stateFormatter(value, row, index) {
		    	if (index == 0){
		    		refreshUserTable(row.id);
		    		$("#hiddenRoleId").val("");
			    	$("#hiddenRoleId").val(row.id);
		            return {
		                disabled : false,//设置是否可用
		                checked : true//设置选中
		            };
		    	}    
		        return value;
		    }},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
	        {field: 'id', visible:false, edit:false},   
		    {
		        field: 'name',
		        align:"center",
		        title: '角色名称', 
		        sortable:true	
		    }
//	        , {
//		        field: 'f_type',
//		        title: '角色类型',
//		        align:"center",
//		        formatter:function(value,row,index){
//		        	if(row.f_type==0){
//		        		return "普通角色";
//		        	}
//		        }
//		    }
	        , {
		        field: 'remark',
		        title: '角色描述',
		        align:"center",
		        formatter:function(value,row,index){
		        	if(row.f_remark==null){
		        		return "";
		        	}
		        }
		    },{
		        field: 'sysFlagName',
		        align:"center",
		        title: '有效性', 
		        formatter:function(value,row,index){
		        	if(row.sysFlag=='1'){
		        		return "有效";
		        	}else{
		        		return "无效";
		        	}
		        }
		    }, {
		    	field:"action",title:"操作",align:"center", width : '150',
		    	formatter:function(value,row,index){
			    	var modifyHtml = '<a href="#" class="modify"><li id="modify" class="glyphicon glyphicon-pencil">修改</li></a>';
			    	var delHtml = '<a href="#" class="delete"><li class="glyphicon glyphicon-inbox" >功能授权</li></a>';
		    		var strHtml = modifyHtml + "&nbsp&nbsp&nbsp&nbsp" + delHtml;
		    		return strHtml;
		    	},
		    	events: {
                    'click .modify': function (e, value, row, index) { 
                        modifyRow(e, value, row, index);//修改操作 
                    },
                    'click .delete': function (e, value, row, index) { 
                    	authorizationRow(e, value, row, index);//功能授权操作
                    }
               },
		    	edit:false
		    } 
	    ],
        clickToSelect: true, 
        smartDisplay:true,  
        cache : false,
	    onPageChange: function (number, size) {
	    	//var objsSearch = SF.getSearchParams();
	    	refreshTable();
	        return false;
	    },
	    onSort: function (name, order) {
	    	//var objsSearch = SF.getSearchParams();
	    	refreshTable();
	        return false;
	    },
	    onClickRow: function (item, $element,index) {
	    	$("#hiddenRoleId").val("");
	    	$("#hiddenRoleId").val(item.id);
			refreshUserTable(item.id);
	        return false;
	    },
	    onCheck:function(row){
            $("#hiddenRoleId").val("");
	    	$("#hiddenRoleId").val(row.id);
			refreshUserTable(row.id);
	        return false;
	    },
	    onUncheck:function(row){
	    	$("#hiddenRoleId").val("");      
	    }
	    
	});
	
	refreshTable();//首次调用 
	SF.SearchPanel.onload(searchpanel);//搜索框的加载
	 
	//新增角色
	 $("#roleBtnAdd").click(function(){
		 var sdata = {"f_pid":null};
	      var win = SF.showModalDlg("addRole.html","add",sdata,800,400,dlg_callback);
	 });

	 //新增用户
	 $("#userBtnAdd").click(function(){
		  var roleId=$("#hiddenRoleId").val();
		  if(roleId=="" || roleId==null){
			  BootstrapDialog.alert("请先选择角色！");
		  }else{
			  var addUser=SF.showModalDlg("selectUser.html?roleId="+roleId,"add",null,1000,500,dlg_callback);
		  }
	 });
	 
	 //删除角色
	 $("#roleBtnDel").click(function(){
	 	//console.info($('#table').bootstrapTable('getSelections'));
	 	var rowdata = $('#table').bootstrapTable('getSelections');
	 	if(rowdata.length==0){
	 		BootstrapDialog.alert("请选择一条记录!");
	 	}else{
	 		BootstrapDialog.show({
	 			  title : "提示",
	 			  message : "删除角色会清空该角色已分配人员，确认要删除所选择的数据？",
	 			  buttons : [
	 			   {
	 			      label : "确定",
	 			      cssClass : "btn-primary",
	 			      icon : "",//通过bootstrap的样式添加图标按钮
	 			     action : function(dialog){   //给当前按钮添加点击事件
	 			    	var ids = '';
	 				 	for(var i=0;i<rowdata.length;i++){ 
	 				 		ids+=rowdata[i].id+",";
	 				 	}
	 				 	ids=ids.substring(0,ids.length-1);
	 				 	$.post("../../../dynamic/RoleMgr/deletes",{ids:ids},function(result){	
	 				 		refreshTable();
	 				 		dialog.close();
	 			    	},"json");
		 			      }
	 			    },{
	 			      label : "取消",
	 			      cssClass : "btn-secondary",
	 			      icon : "",
	 			      action : function(dialog){   //给当前按钮添加点击事件
	 			            dialog.close();
	 			      }
	 			    }
	 			  ]
	 			});
		 	
	 	}
	 });
	 
	 //删除已分配用户
	 $("#userBtnDel").click(function(){
		 	//console.info($('#table').bootstrapTable('getSelections'));
		 	var rowdata = $('#table1').bootstrapTable('getSelections');
		 	if(rowdata.length==0){
		 		BootstrapDialog.alert("请选择一条记录!");
		 	}else{
			 	var ids = '';
			 	for(var i=0;i<rowdata.length;i++){ 
			 		ids+=rowdata[i].id+",";
			 	}
			 	ids=ids.substring(0,ids.length-1);
			 	$.post("../../../dynamic/RoleMgr/deleteUserForRole",{roleId: $("#hiddenRoleId").val(), ids:ids},function(result){	
			 		console.log($("#hiddenRoleId").val());
			 		refreshUserTable($("#hiddenRoleId").val());
			 		BootstrapDialog.alert(result.message);
		    	},"json");
		 	}
		 });
	
	 $table1 = $('#table1').bootstrapTable({ 
        locale:'zh-CN',//中文支持 
        pagination: true,//是否开启分页（*）
		pageNumber:1,
        pageSize: 10,//每页的记录行数（*）
        pageList: [10,25,50,100],//可供选择的每页的行数（*）
        idField: "id", //标识哪个字段为id主键
        uniqueId: "id",   //每一行的唯一标识，一般为主键列
        sidePagination: "server", //分页方式：client 客户端分页，server 服务端分页（*）
        showColumns: true, //是否显示所有的列
        minimumCountColumns: 2, //最少允许的列数
        toolbar: '#toolbar1', //工具按钮用哪个容器
	    columns: [
		    {field: 'checkStatus',checkbox: true, edit:false},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
	        {field: 'id', visible:false, edit:false},   
		    {
		        field: 'name',
		        title: '用户名',
		        sortable:true
		    },
		    {
		        field: 'code',
		        title: '工号'
		    },
		    {
		        field: 'orgName',
		        title: '部门'
		    },{
		        field: 'postsName',
		        title: '岗位'
		    }
	    ],
        clickToSelect: true, 
        smartDisplay:true,  
        cache : false,
	    onPageChange: function (number, size) {
	    	refreshUserTable();
	        return false;
	    },
	    onSort: function (name, order) {
	    	refreshUserTable();
	        return false;
	    }
	});
});


//function removeRow(rowid){
//	alert("你真的要删除这条记录吗？f_pid="+id);
//}

var dlg_callback =function(e){
	if(e=='ok'){
		refreshTable();
		BootstrapDialog.alert("操作成功！");
	}
}

//角色信息修改
function modifyRow(e, value, row, index){
	//Modal.load({ msg: "角色编辑",id:"addRoleIframe", url: "addRole.html?f_pid="+row.f_pid, title: "角色编辑", width: 800, height: 400 });
	var sdata = {"f_pid":row.id};
    var win = SF.showModalDlg("addRole.html","update",sdata,800,400,dlg_callback);
}

//功能授权
function authorizationRow(e, value, row, index){
	//Modal.load({ msg: "角色编辑",id:"addRoleIframe", url: "addRole.html?f_pid="+row.f_pid, title: "角色编辑", width: 800, height: 400 });
	
    var win = SF.showModalDlg("purview.html?roleId="+row.id,"purview",null,900,550,dlg_callback);
}

//重新查询、重新排序、切换分页事件
function refreshTable() {
	var options = $table.bootstrapTable('getOptions');
    var number =  options.pageNumber;
    var size = options.pageSize;
    var sortName = options.sortName;
    var sortOrder = options.sortOrder;
    var objsSearch = SF.getSearchParams();
    console.log(objsSearch);
	$.ajax({
	   	type: "POST",
       	async: true,
	   	//url: MesWebApiUrl + "/sec/role/getpage",       
       	url: "../../../dynamic/RoleMgr/findRoleListPagination",
       	data: {
       		'page' : number,
       		'limit' : size,
       		'name' : objsSearch.name,
       		'sysFlag' : objsSearch.sysFlagName
//            "conditions": [
//            {
//                "fieldname": "name",
//                "opration": ConditionOperator.Like,
//                "values": [name]
//            }
//            ],
//            "pagination": {
//                "pageindex": number,
//                "pagesize": size
//                
//            }
       	},
	   	success: function(res){
	     	var pagedata = {};
        	pagedata.rows = res.rows;
        	pagedata.total = res.total;
        	$table.bootstrapTable('load', pagedata);
	   },
	   error: function(XMLHttpRequest, textStatus, errorThrown){
	   }
	 }); 
}


function refreshUserTable(f_pid) {
	var accessToken="meERw1HORJ6hFoEvthAx7pO-9BTQGyACI4rS6nKwwY6nwmLtOMZRNehughHZyI2n9KiF8DtSIdei-FNfe1qECQhV5jxOXCY90mN-uoGPdB9JDnp-rGceRNvIcEXkhiUdQQJxiWqeba9OpXONhw722AcuCHBB1CrissqqyqxuViRONkw_ZqDi19n7V2n4ClgE4ueZtdDeewHKuTbKXWGnqo6dyQqFSJpaFPvdknBv_s2CO_Rx6GBLCw820kGfAcoBfDK_7YpZMCxWECNwBu9KUNgPo_oqsSLMERsaXoA9lNWIWcvxntNqskwl5kB1QoJoMEqsa45IhKP6au7-q2dhpfbBWo4d9ZNRBgIG5nLysdI10T3QWzf2_gGxnfLmkxbntKF6OCZwZsF8t6jx_VA4oCPhxJ3gn93kZ_2e_cEBayE6QsYh0mjuh7pyVZ13SegZ";
	var options = $table1.bootstrapTable('getOptions');
    var number =  options.pageNumber;
    var size = options.pageSize;
    var sortName = options.sortName;
    var sortOrder = options.sortOrder;
	$.ajax({
	   type: "POST",
       async: true,
	   //url: MesWebApiUrl + "/sec/user/getsalvepage/"+f_pid,
	   url: '../../../dynamic/RoleMgr/queryUsersByRoleId?roleId='+f_pid, 
	   headers: {"Authorization":"Bearer "+accessToken},
	   data: { 
                "pageindex": number,
                "pagesize": size 
       },
	   success: function(res){
	   		var pagedata = {};
        	pagedata.rows = res.rows;
			pagedata.total = res.total;
        	$table1.bootstrapTable('load', pagedata);
	   },
	   error: function(XMLHttpRequest, textStatus, errorThrown){
	   }
	 });
}