  var searchpanel = {
						name  : "search",   
					    type  : "searchpanel", 
					    title : "查询条件",
					    berth : "SearchPanel",
					    items : [
					        { 
					        	type:"textinput",
					            title:"编号",
					            dataIndex:'matCode'
					        },
					        { 
					        	type:"textinput",
					            title:"名称",
					            dataIndex:'matName'
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
  var dlg_callback =function(e){
		if(e=='ok') refreshTable();
		if(e=='cancel') ;
		return false;
	}
$(function() {
	 $table = $('#table').bootstrapTable({ 
	      locale:'zh-CN',//中文支持 
	      pagination: true,//是否开启分页（*）
	      //striped:true,  //奇偶行渐色表
	      pageSize: 10,//每页的记录行数（*）
	      pageList: [10,25,50,100],//可供选择的每页的行数（*）
	      idField: "pid", //标识哪个字段为id主键
	      uniqueId: "pid",   //每一行的唯一标识，一般为主键列
	      sidePagination: "server", //分页方式：client 客户端分页，server 服务端分页（*）
	      showColumns: true, //是否显示所有的列
	      //showRefresh: true, //是否显示刷新按钮
	      minimumCountColumns: 2, //最少允许的列数
	      showToggle: true, //是否显示详细视图和列表视图的切换按钮
	      toolbar: '#toolbar', //工具按钮用哪个容器
	      clickToSelect: true, 
	      smartDisplay:true,  
	      cache : false,
		    columns: [
			    {field: 'checkStatus',checkbox: true, edit:false},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
			    //{field: 'checkStatus',radio: true},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
		        {field: 'pid', visible:false, edit:false},   
			    {
			        field: 'matCode',
			        title: '编码', 
			        sortable:true,
			    }, {
			        field: 'matName',
			        title: '名称' 
			    }, {
			        field: 'spec',
			        title: '规格描述' 
			    }, 
			    {
			        field: 'type',
			        title: '物料大类'
			    },{
			        field: 'sub_type',
			        title: '物料子类'
			    }, {
			        field: 'unit',
			        title: '单位' 
			    }, 
			    {
			        field: 'remark',
			        title: '描述'
			    },
			    {
			        field: 'sysFlag',
			        title: '有效性',
					visible:true
			    },
			    {
			    	field:"action",title:"操作",align:"center", width : '80',
			    	formatter:function(value,row,index){
				    	var modifyHtml = '<a href="#" class="modify" ><li id="modify" class="glyphicon glyphicon-pencil"> </li></a>';
				    //	var delHtml = '<a href="#" class="delete"><li class="glyphicon glyphicon-remove" ></li></a>';
			    		var strHtml = modifyHtml ;
			    		return strHtml;
			    	},
			    	events: {
	                  'click .modify': function (e, value, row, index) { 
	                	  toModify(e, value, row, index);//修改操作 
	                  },
	                  'click .delete': function (e, value, row, index) { 
	                	  removeRow(row.id);
	                  }
	             },
			    	edit:false
			    } 
		    ], 
		    onPageChange: function (number, size) {
		    	refreshTable();
		        return false;
		    },
		    onSort: function (name, order) {
		    	refreshTable();
		        return false;
		    }
		}); 
			refreshTable();//首次调用  
			SF.SearchPanel.onload(searchpanel); 
			//删除数据
		    $("#btnDel").click(function(){/* 
		    	var a= $table.bootstrapTable('getSelections');
		    	 if(a.length<1){
			    		alert("请至少选中一行")
			    	}else if(confirm(constant.CONFIRM_DELETE)&&a.length>=1){
		    		var array=new Array();
		    		for(var i=0;i<a.length;i++){
		    			array.push(a[i].pid);
		    		}
		    		$.post("../../../dynamic/material/deletematerial",{ids:array.join(",")},function(result){	
			    		refreshTable();
			    	},"json");
		    		console.info(a);
		    	}
			*/
		    	buttonDelete($table,"../../../dynamic/material/deletematerial");	
		    }); 
		    //打开子窗口，添加数据
		    $("#btnAdd").click(function(){  
		    	//var id=$.fn.geturl("rightIframe","id");
		        var win = SF.showModalDlg("editmaterial.html","add",null,800,400,dlg_callback); 
		     });
	});  

function toModify(e, value, row, index){
	//var id=$.fn.geturl("rightIframe","id");
	$.post("../../../dynamic/material/material",{id:row.pid},function(result){
		 var win = SF.showModalDlg("editmaterial.html?pid="+row.id,"update",result,800,400,dlg_callback); 
	});
}
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
	   url: "../../../dynamic/material/materials",       
	   data: {
		   'page':number,
	    	'limit':size,
	    	'matCode':objs.matCode,
	    	'matName':objs.matName
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
	   		alert("请求异常！");
	   }
	 }); 

	
} 

function buttonDelete(table,url){
	var rowdata = table.bootstrapTable(
	'getSelections');
if (rowdata.length == 0) {
BootstrapDialog.alert("请选择一条记录!");
return false;
}else if(rowdata.length >= 1){

	BootstrapDialog.show({
			  message : "是否删除工单数据！",
			  buttons : [
			   {
			      label : "确定",
			      cssClass : "btn-primary",
			      icon : "glyphicon glyphicon-ban-circle",//通过bootstrap的样式添加图标按钮
			      action : function(dialog){   //给当前按钮添加点击事件
			    	 var array = new Array();
			    	 for(var i=0;i<rowdata.length;i++){ 
				 		array.push(rowdata[i].pid);
				 	}
				 	$.post(url,{ids : array.join(",")}, function(result) {
				 		BootstrapDialog.alert("操作成功!");
				 		refreshTable();
					dialog.close();
				},"json");
 			      }
			    },{
			      label : "取消",
			      cssClass : "btn-primary",
			      icon : "glyphicon glyphicon-ban-circle",
			      action : function(dialog){   //给当前按钮添加点击事件
			            dialog.close();
			      }
			    }
			  ]
			});

}
}
