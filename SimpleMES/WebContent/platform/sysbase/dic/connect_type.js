  var searchpanel = {
						name  : "search",   
					    type  : "searchpanel", 
					    title : "查询条件",
					    berth : "SearchPanel",
					    items : [
					        { 
					        	type:"textinput",
					            title:"编号",
					            dataIndex:'code'
					        },
					        { 
					        	type:"textinput",
					            title:"名称",
					            dataIndex:'name'
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
		if(e=='ok'){
			refreshTable();
			//BootstrapDialog.alert(constant.SUCCESS_MSG);
		}
		if(e=='cancel') ;
	}
$(function() {
	 $table = $('#table').bootstrapTable({ 
	      locale:'zh-CN',//中文支持 
	      pagination: true,//是否开启分页（*）
	      //striped:true,  //奇偶行渐色表
	      pageSize: 10,//每页的记录行数（*）
	      pageList: [10,25,50,100],//可供选择的每页的行数（*）
	      idField: "id", //标识哪个字段为id主键
	      uniqueId: "id",   //每一行的唯一标识，一般为主键列
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
		        {field: 'id', visible:false, edit:false},   
			    {
			        field: 'code',
			        title: '编码', 
			        sortable:true,
			        //editable: true,
			    	edit:false
			    }, {
			        field: 'name',
			        title: '名称'
			    }, 
			    {
			        field: 'parentName',
			        title: '父对象'  
			    }, 
			    {
			        field: 'childName',
			        title: '子对象'
			    }, 
			    {
			        field: 'remark',
			        title: '备注'  
			    },
			    {
			        field: 'sysFlag',
			        title: '有效性',
			        formatter:function(value,row,index){
    		        	if(row.sysFlag==1){
    		        		return "有效";
    		        	}
    		        	return "无效";
    		        }
			    },
			    {
			    	field:"action",title:"操作",align:"center", width : '80',
			    	formatter:function(value,row,index){
				    	var modifyHtml = '<a href="#" class="modify" ><li id="modify" class="glyphicon glyphicon-pencil"> </li></a>';
				    	//var delHtml = '<a href="#" class="delete"><li class="glyphicon glyphicon-remove" ></li></a>';
			    		var strHtml = modifyHtml;
			    		return strHtml;
			    	},
			    	events: {
	                  'click .modify': function (e, value, row, index) { 
	                      modifyRow(e, value, row, index);//修改操作 
	                  }/*,
	                  'click .delete': function (e, value, row, index) { 
	                	  removeRow(row.id);//删除操作
	                  }*/
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
		    $("#btnDel").click(function(){ 
		    	var a= $table.bootstrapTable('getSelections');
		    	if (a.length == 0) {
					BootstrapDialog.alert(constant.LOWEST_CHOICE_ONE);
					return false;
				}else {
					BootstrapDialog.show({
						title: constant.TIP,
			 			  message : constant.CONFIRM_DELETE,
			 			  buttons : [
			 			   {
			 			      label :constant.CONFIRM,
			 			      cssClass : "btn-primary",
			 			      icon : "glyphicon glyphicon-ban-circle",//通过bootstrap的样式添加图标按钮
			 			      action : function(dialog){   //给当前按钮添加点击事件
			 			    	 var array = new Array();
			 			    	 for(var i=0;i<a.length;i++){ 
			 				 		array.push(a[i].id);
			 				 	}
			 				 	$.post("../../../dynamic/ObjEntityRef/deletes",{ids : array.join(",")}, function(result) {
			 				 		BootstrapDialog.alert(result.message);
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
			}); 
		    //打开子窗口，添加数据
		    $("#btnAdd").click(function(){  
		        var win = SF.showModalDlg("connect_edit.html","add",null,800,400,dlg_callback); 
		     });
		
	});  

function toModify(){
	$.post("../../../dynamic/ObjEntityRef/get",{id:row.id},function(result){
		 var win = SF.showModalDlg("connect_edit.html?id="+row.id,"update",result,800,400,dlg_callback); 
	});
}
function removeRow(rowid){
	alert("你真的要删除这条记录吗？f_pid="+rowid);
}

function modifyRow(e, value, row, index){ 
	$.post("../../../dynamic/ObjEntityRef/get",{id:row.id},function(result){
		 var win = SF.showModalDlg("connect_edit.html?id="+row.id,"update",result,800,400,dlg_callback); 
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
	   url: "../../../dynamic/ObjEntityRef/findListPagination",       
	   data: {
		    	'page':number,
		    	'limit':size,
		    	'code':objs.code,
		    	'name':objs.name
		    
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
	   }
	 }); 
} 