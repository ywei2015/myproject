
//===================================================================
// 项目名称：澄城mes系统
// 模块名称：人员管理列表
// 类    名：UserMgr.js
//===================================================================

var searchpanel = {
	name  : "search",   
    type  : "searchpanel", 
    title : "查询条件",
    berth : "SearchPanel",
    items : [
        { 
        	type:"textinput",
            title:"姓名",
            dataIndex:'name'
        },
        { 
        	type:"textinput",
            title:"用户名",
            dataIndex:'code'
        }
		
    ],
    layoutConfig : {
        columns : 4
    },
  onsearch: function(){
	  refreshTable();
  }
};
var $table;

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
	    columns: [
		    {field: 'checkStatus',checkbox: true, edit:false},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
	        {field: 'id', visible:false, edit:false},   
	        {
		        field: 'name',
		        align: "center",
		        title: '姓名' 
		   },{
		        field: 'code',
		        title: '用户名', 
		        align: 'center'
		    }, {
		        field: 'jobno',
		        title: '工号' ,
		        align: 'center',
		    },/*{
		        field: 'cardNO',
		        title: '中烟编码',
		        align: 'center'
		    },*/{
		        field: 'orgName',
		        title: '部门', 
		        align: 'center'
		    },{
		        field: 'postsName',
		        title: '岗位', 
		        align: 'center'
		    },{
		        field: 'rolesName',
		        title: '用户角色', 
		        align: 'center'
		    },{
		        field: 'sysFlagName',
		        title: '有效性', 
		        align: 'center'
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
                    	authorizationRow(e, value, row, index);//功能授权 
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
	    }
	});
	refreshTable();//首次调用 
	SF.SearchPanel.onload(searchpanel);//搜索框的加载
	 
	//新增
	 $("#userBtnAdd").click(function(){
		 var sdata = {"user":null};
	      var win = SF.showModalDlg("addUser.html","add",sdata,800,400,dlg_callback);
	 });
     
	 
	 
	 //删除
	 $("#userBtnDel").click(function(){
	 	//console.info($('#table').bootstrapTable('getSelections'));
	 	var rowdata = $('#table').bootstrapTable('getSelections');
	 	if(rowdata.length==0){
	 		BootstrapDialog.alert("请选择一条记录!");
	 	}else{
	 		BootstrapDialog.show({
	 			  title : "提示",
	 			  message : "是否删除数据！",
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
	 				 	$.post("../../../dynamic/User/deletes",{ids:ids},function(result){	
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
});

var dlg_callback =function(e){
	console.log(e);
	if(e=='ok'){
		refreshTable();
		BootstrapDialog.alert("操作成功！");
	}
}

//用户信息修改
function modifyRow(e, value, row, index){
	var sdata = {"user":row};
    var win = SF.showModalDlg("addUser.html","update",sdata,800,400,dlg_callback);
}

//功能授权
function authorizationRow(e, value, row, index){
	//Modal.load({ msg: "角色编辑",id:"addRoleIframe", url: "addRole.html?f_pid="+row.f_pid, title: "角色编辑", width: 800, height: 400 });
	
    var win = SF.showModalDlg("purviewForUser.html?userId="+row.id,"purview",null,700,550,dlg_callback);
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
       	async: false,
	   	//url: MesWebApiUrl + "/sec/role/getpage",       
       	url: "../../../dynamic/User/findUserList",
       	data: {
       		'page' : number,
       		'limit' : size,
       		'name' : objsSearch.name,
       		'code' : objsSearch.code
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
