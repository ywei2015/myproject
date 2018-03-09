var dId=geturl("rightIframe","id");
var roleId=geturl("rightIframe","roleId");
console.log(roleId);
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
//	        { 
//	        	type:"textinput",
//	            title:"工号",
//	            dataIndex:'name'
//	        }
	    ],
	    layoutConfig : {
	        columns : 4
	    },
	    onsearch: function(){
	    	//console.info(SF.SearchPanel.paramctrls);
	    	
	    	refreshTable();
	    }
	};
  var $table;
  var dlg_callback =function(e){
		if(e=='ok') alert("子窗口已经提交 并关闭! 您可以刷新父窗口数据了。"+e);
		if(e=='cancel') alert("子窗口已经关闭! 点了取消， 不需要刷新。"+e);
	}
$(function() {
	 $table = $('#table').bootstrapTable({ 
	      locale:'zh-CN',//中文支持 
	      pagination: true,//是否开启分页（*）
	      //striped:true,  //奇偶行渐色表
	      pageSize: 5,//每页的记录行数（*）
	      pageList: [5,10],//可供选择的每页的行数（*）
	      idField: "id", //标识哪个字段为id主键
	      uniqueId: "id",   //每一行的唯一标识，一般为主键列
	      sidePagination: "server", //分页方式：client 客户端分页，server 服务端分页（*）
	      showColumns: true, //是否显示所有的列
	      //showRefresh: true, //是否显示刷新按钮
	      minimumCountColumns: 2, //最少允许的列数
	      showToggle: true, //是否显示详细视图和列表视图的切换按钮
	      toolbar: '#toolbar', //工具按钮用哪个容器
	      //showRefresh:true,//刷新按钮 
	      //clickToSelect:true,     //是否选中  
	      //maintainSelected:true,  
	      //sortable:true,
	      //sortStable:true,
	      //search: true,//搜索 strictSearch精确搜索
		    //editable:true,//开启编辑模式  
	      clickToSelect: false, 
	      smartDisplay:true,  
	      cache : false,
		    columns: [
			    {field: 'checkStatus',checkbox: true, edit:false},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
			    //{field: 'checkStatus',radio: true},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
		        {field: 'id', visible:false, edit:false},   
			    {
			        field: 'name',
			        title: '姓名', 
			        sortable:true,
			        align:"center",
			        //editable: true,
			    	edit:false
			    }, {
			        field: 'code',
			        align:"center",
			        title: '用户名'
			    }, 
			    {
			        field: 'jobno',
			        align:"center",
			        title: '工号'
			    },
			    {
			        field: 'orgName',
			        align:"center",
			        title: '部门'
			    },
			    {
			        field: 'postsName',
			        align:"center",
			        title: '岗位'
			    },
			    {
			        field: 'rolesName',
			        align:"center",
			        title: '用户角色'
			    },
			    {
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
			    } 
		    ], 
		    onPageChange: function (number, size) {
		    	refreshTable();
		        return false;
		    },
		    onSort: function (name, order) {
		    	refreshTable();
		        return false;
		    },
		    onCheck:function(row){
		    	console.log(row);
		    	//refreshTable2(row);
		    }
		}); 
			refreshTable();//首次调用  
			SF.SearchPanel.onload(searchpanel); 
			
		    
		    //确定按钮
		    $("#userSure").click(function(){  
		    	var rowdata = $('#table').bootstrapTable('getSelections');
		    	var ids=[];
				for (var i = 0; i < rowdata.length; i++) {
					ids.push(rowdata[i].id);
				}
				if(rowdata.length>=1){
					var url  = "../../../dynamic/RoleMgr/saveUserForRole";
					$.post(url, {
						roleId:roleId , ids:ids.join(",") 
					}, function(result) {
						BootstrapDialog.alert(result.message);
						
					}, "json");
				}
		     });
		    
		   //取消按钮
		    $("#userDel").click(function(){
		    	top.document.location.reload();
		    }); 
		

	});  

 
//重新查询、重新排序、切换分页事件
function refreshTable() {
	var options = $table.bootstrapTable('getOptions'); 
    var number =  options.pageNumber;
    var size = options.pageSize;
    var sortName = options.sortName;
    var sortOrder = options.sortOrder;
    var objs = SF.getSearchParams();
	$.ajax({
	   type: "POST",
       async: true,
	   url: "../../../dynamic/User/findUserListByTrue",       
	   data: {
		    	'page':number,
		    	'limit':size,
		    	'name':objs.name,
		    	'code':objs.code,
		    	'orgId': dId
		    	
		    
       },
	   success: function(res){
	   		//console.info(res);
	     	var pagedata = {};
        	pagedata.rows = res.rows;
        	pagedata.total = res.total;
        	//console.info(pagedata);
        	$table.bootstrapTable('load', pagedata);
        	return false;
	   },
	   error: function(XMLHttpRequest, textStatus, errorThrown){
		   BootstrapDialog.alert("请求异常！");
	   		/*alert(XMLHttpRequest.status);
	   		alert(XMLHttpRequest.readyState);
	   		alert(textStatus);*/
	   }
	 }); 
} 

function geturl(id,name) {
    var reg = new RegExp("[^\?&]?" + encodeURI(name) + "=[^&]+");
    var arr = window.parent.document.getElementById(id).contentWindow.location.search.match(reg);
    if (arr != null) {
        return decodeURI(arr[0].substring(arr[0].search("=") + 1));
    }
    return "";
}