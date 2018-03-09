  var searchpanel = {
						name  : "search",   
					    type  : "searchpanel", 
					    title : "查询条件",
					    berth : "SearchPanel",
					    items : [
					    	{ 
					        	type:"combox",   
								title:"BOM类型",
								dataIndex: 'bomType',
								fieldText: 'name',
								fieldValue: 'pid',
								//data:[{'code':0,'name':'点检'},{'code':1,'name':'保养'},{'code':2,'name':'润滑'}],
								url: "../../../../dynamic/dicView/listDic?view=v_mat_bomtype",
								root: "dataset.data"
					        },
					        { 
					        	type:"textinput",
					            title:"BOM名称",
					            dataIndex:'bomName'
					        },{
						        dataIndex: 'status',
						        title: '状态',
								type:"combox",
								fieldText:"name",
								fieldValue:"code",
								data:[{'code':1,'name':'启用'},{'code':0,'name':'停用'}],
								//url:"../../../DataDict?type=Workshop",
								root: "dataset.item"
						    }
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
  var $table2;
  var dlg_callback1 =function(e){
		if(e=='ok')refreshTable() ;
	}
  
  var dlg_callback2 =function(e){
		if(e=='ok')refreshTable2() ;
	}
$(function() {
	
	 $table2 = $('#table2').bootstrapTable({ 
		 locale:'zh-CN',//中文支持 
	      pagination: true,//是否开启分页（*）
	      //striped:true,  //奇偶行渐色表
	      pageSize: 5,//每页的记录行数（*）
	      pageList: [5,10],//可供选择的每页的行数（*）
	      idField: "fPid", //标识哪个字段为id主键
	      uniqueId: "fPid",   //每一行的唯一标识，一般为主键列
	      sidePagination: "server", //分页方式：client 客户端分页，server 服务端分页（*）
	      showColumns: true, //是否显示所有的列
	      //showRefresh: true, //是否显示刷新按钮
	      minimumCountColumns: 2, //最少允许的列数
	      showToggle: true, //是否显示详细视图和列表视图的切换按钮
	      toolbar: '#toolbar2', //工具按钮用哪个容器
	      clickToSelect: true, 
	      smartDisplay:true,  
	      singleSelect :true,
	      cache : false,
		    columns: [
			    {field: 'checkStatus',checkbox: true, edit:false},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
			    //{field: 'checkStatus',radio: true},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
		        {field: 'fPid', visible:false, edit:false},   
		        {field: 'fBomPid', visible:false, edit:false},
		        {
			        field: 'fSubtype',
			        title: '物料子类', 
			    },{
			        field: 'fMatName',
			        title: '物料名称' 
			    },
			    {
			        field: 'fIoFlag',
			        title: '投入产出标记', 
			        formatter : function (value,row,index){
			        	if (value == 1) {
			        		return "投入";
			        	} else {
			        		return "产出";
			        	}
			        }
			    },{
			        field: 'fIndex',
			        title: '排序号'
			    }, 
			    {
			        field: 'fQuantity',
			        title: '数量'
			    }, {
			        field: 'fUnit',
			        title: '单位' 
			    } ,
			    {
			    	field:"action",title:"操作",align:"center", width : '80',
			    	formatter:function(value,row,index){
			    		var modifyHtml = '<a href="#" class="modify" ><li id="modify" class="glyphicon glyphicon-pencil"> </li></a>';
			    		var strHtml = modifyHtml ;
			    		return strHtml;
			    	},
			    	events: {
	                  'click .modify': function (e, value, row, index) { 
	                	  toModify2(e, value, row, index);//修改操作 
	                  }
	             },
			    	edit:false
			    } 
			    
		    ], 
		    onPageChange: function (number, size) {
		    	refreshTable2();
		        return false;
		    },
		    onSort: function (name, order) {
		    	refreshTable2();
		        return false;
		    }
		});
	  //删除数据
	    $("#btnDel2").click(function(){ 
	    	buttonDelete($table2,"../../../dynamic/materialbom/deletematerialbom_detail");
		}); 
	    $("#btnAdd2").click(function(){
	    	var a= $table.bootstrapTable('getSelections');
	    	if(a.length==1) {
		        var win = SF.showModalDlg("editmaterialbom_detail.html?fBomPid="+a[0].fPid,"add",null,800,400,dlg_callback2); 
	    	}else{
	    		alert("请选择一行BOM");
	    	}
	     });
	    
	 $table = $('#table').bootstrapTable({ 
	      locale:'zh-CN',//中文支持 
	      pagination: true,//是否开启分页（*）
	      //striped:true,  //奇偶行渐色表
	      pageSize: 5,//每页的记录行数（*）
	      pageList: [5,10],//可供选择的每页的行数（*）
	      idField: "fPid", //标识哪个字段为id主键
	      uniqueId: "fPid",   //每一行的唯一标识，一般为主键列
	      sidePagination: "server", //分页方式：client 客户端分页，server 服务端分页（*）
	      showColumns: true, //是否显示所有的列
	      //showRefresh: true, //是否显示刷新按钮
	      minimumCountColumns: 2, //最少允许的列数
	      showToggle: true, //是否显示详细视图和列表视图的切换按钮
	      toolbar: '#toolbar', //工具按钮用哪个容器
	      clickToSelect: true, 
	      singleSelect :true,
	      cache : false,
		    columns: [
		    	 {field: 'checkStatus',checkbox:true, edit:false,formatter : function stateFormatter(value, row, index) {
				    	if (index == 0){
				    		refreshTable2();
				            return {
				                disabled : false,//设置是否可用
				                checked : true//设置选中
				            };
				    	}    
				        return value;
				    }},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
			    //{field: 'checkStatus',radio: true},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
		        {field: 'fPid', visible:false, edit:false},   
		        {
			        field: 'fBomName',
			        title: 'BOM名称', 
			        sortable:true
			    }, {
			        field: 'fBomType',
			        title: 'BOM类型', 
			    }, 
			    {
			        field: 'fMatName',
			        title: '产出物料', 
			    },{
			        field: 'fQuantity',
			        title: '单批数量'
			    }, {
			        field: 'fUnit',
			        title: '单位' 
			    }, {
			        field: 'fVersion',
			        title: '版本' 
			    }, 
			    {
			        field: 'fEnableDt_vo',
			        title: '启用时间'
			    }, {
			        field: 'fDisableDt_vo',
			        title: '停用时间' 
			    }, 
			    {
			        field: 'fStatus',
			        title: '状态',
			        formatter : function (value,row,index){
			        	if (value == 1) {
			        		return "启用";
			        	} else {
			        		return "停用";
			        	}
			        }
			    },
			    {
			    	field:"action",title:"操作",align:"center", width : '80',
			    	formatter:function(value,row,index){
			    		var modifyHtml = '<a href="#" class="modify" ><li id="modify" class="glyphicon glyphicon-pencil"> </li></a>';
			    		var strHtml = modifyHtml ;
			    		return strHtml;
			    	},
			    	events: {
	                  'click .modify': function (e, value, row, index) { 
	                	  toModify(e, value, row, index);//修改操作 
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
		    },
		    onClickRow: function (item, $element) {
		    	fbompid=item.fPid;
		    	refreshTable2();
		    	
		    }
		}); 
			refreshTable();//首次调用  
			SF.SearchPanel.onload(searchpanel);
			//删除数据
		    $("#btnDel").click(function(){ 
		    	buttonDelete($table,"../../../dynamic/materialbom/deletematerialbom");
			}); 
		    $("#btnAdd").click(function(){  
		        var win = SF.showModalDlg("editmaterialbom.html","add",null,800,400,dlg_callback1); 
		     });
		   
	});  
var fbompid;
function toModify(e, value, row, index){
	$.post("../../../dynamic/materialbom/materialbom",{id:row.fPid},function(result){
		 var win = SF.showModalDlg("editmaterialbom.html?id="+row.fPid,"update",result,800,400,dlg_callback1); 
	});
}
function toModify2(e, value, row, index){
	$.post("../../../dynamic/materialbom/materialbom_detail",{id:row.fPid},function(result){
		 var win = SF.showModalDlg("editmaterialbom_detail.html?id="+row.fPid,"update",result,800,400,dlg_callback2); 
	});
}


 
//重新查询、重新排序、切换分页
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
	   url: "../../../dynamic/materialbom/list_materialbom",       
	   data: {
		    	'page':number,
		    	'limit':size,
		    	'bomType':objs.bomType,
		    	'bomName':objs.bomName,
		    	'status':objs.status
		    
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
	   		/*alert(XMLHttpRequest.status);
	   		alert(XMLHttpRequest.readyState);
	   		alert(textStatus);*/
	   }
	 }); 
} 

function refreshTable2() {
	var fpid;
	var options = $table2.bootstrapTable('getOptions'); 
	var a= $table.bootstrapTable('getSelections');
	var row = a[0];
    var number =  options.pageNumber;
    var size = options.pageSize;
    var sortName = options.sortName;
    var sortOrder = options.sortOrder;
    if( row == null || row.length == 0) {
    	fpid=fbompid;
    }
    else {
    	fpid=row.fPid;
    }
	$.ajax({
		   type: "POST",
	       async: true,
		   url: "../../../dynamic/materialbom/list_materialbom_detail",       
		   data: {
			    	'page':number,
			    	'limit':size,
			    	'fpid':fpid
			    
	       },
		   success: function(res){
		   		//console.info(res);
		     	var pagedata = {};
	        	pagedata.rows = res.rows;
	        	pagedata.total = res.total;
	        	//console.info(pagedata);
	        	$table2.bootstrapTable('load', pagedata);
	        	return false;
		   },
		   error: function(XMLHttpRequest, textStatus, errorThrown){
		   		alert("请求异常！");
		   		/*alert(XMLHttpRequest.status);
		   		alert(XMLHttpRequest.readyState);
		   		alert(textStatus);*/
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
				 		array.push(rowdata[i].fPid);
				 	}
				 	$.post(url,{ids : array.join(",")}, function(result) {
				 		BootstrapDialog.alert("操作成功!");
				 		if(table.attr("id") == "table2") {
				 			refreshTable2();
				 		}else {
				 			refreshTable();
				 		}
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