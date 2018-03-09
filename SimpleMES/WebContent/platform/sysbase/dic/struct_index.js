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
					    	//console.info(SF.SearchPanel.paramctrls);
					    	
					    	refreshTable();
					    }
			};
  var $table;
  var $table2;
  var dlg_callback =function(e){
		if(e=='ok'){
			refreshTable();
		}
		//if(e=='cancel') alert("子窗口已经关闭! 点了取消， 不需要刷新。"+e);
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
	      singleSelect  : true,    
	      //showRefresh: true, //是否显示刷新按钮
	      minimumCountColumns: 2, //最少允许的列数
	      showToggle: true, //是否显示详细视图和列表视图的切换按钮
	      toolbar: '#toolbar', //工具按钮用哪个容器
	      clickToSelect: false, 
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
			        field: 'accessControlName',
			        title: '是否权限控制',
			        formatter:function(value,row,index){
    		        	if(row.accessControlId=='4028a07b246a97c201246a9aea380002'){
    		        		return "是";
    		        	}
    		        	return "否";
    		        }
			    },
			    {
			    	field:"action",title:"操作",align:"center", width : '300',
			    	formatter:function(value,row,index){
			    		var modifyHtml = '<a href="#" class="modify"title="编辑"  ><li id="modify" class="glyphicon glyphicon-pencil"> </li></a>';
				    	var show = '<a href="#" class="showline" title="展示"><li class="glyphicon glyphicon-tree-conifer" ></li></a>';
				    	var relation = '<a href="#" class="relation" title="关系"><li class="glyphicon glyphicon-tree-deciduous" ></li></a>';
			    		var strHtml = modifyHtml + "&nbsp&nbsp&nbsp&nbsp" + show +"&nbsp&nbsp&nbsp&nbsp"+relation;
			    		return strHtml;
			    	},
			    	events: {
	                  'click .modify': function (e, value, row, index) { 
	                	  toModify(e, value, row, index);//修改操作 
	                  },
	                  'click .showline': function (e, value, row, index) { 
/*	                	  myModal
	                	  data_tree(row.id);*/
	                	  data_tree(row.id);
	                	  $(function () { $('#myModal').modal({
	                			keyboard: true
	                		})});
	                	    
	                  },
	                  'click .relation': function (e, value, row, index) { 
	                	  data_tree_connect(row.id);
	                	  $(function () { $('#myModal').modal({
	                			keyboard: true
	                		})});
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
		    onCheck:function(row){
		    	console.log(row);
		    	refreshTable2(row);
		    }
		}); 
			refreshTable();//首次调用  
			SF.SearchPanel.onload(searchpanel); 
			//删除数据
		    $("#btnDel").click(function(){ 
		    	var a= $table.bootstrapTable('getSelections');
		    	if (a.length == 0) {
		    		BootstrapDialog.show({
	        	            title: constant.TIP,
	        	            message:constant.LOWEST_CHOICE_ONE,
	        	            buttons: [{
	        	                label: constant.CONFIRM,
	        	                action: function(dialog) {
	        	                	dialog.close();
	        	                	window.close();
	        	                	return false;
	        	                }
	        	            }]
	        	        });
					//BootstrapDialog.alert(constant.LOWEST_CHOICE_ONE);
					return false;
				}else {
					BootstrapDialog.show({
						title: constant.TIP,
			 			  message : constant.CONFIRM_DELETE,
			 			  buttons : [
			 			   {
			 			      label : constant.CONFIRM,
			 			      cssClass : "btn-primary",
			 			      icon : "glyphicon glyphicon-ban-circle",//通过bootstrap的样式添加图标按钮
			 			      action : function(dialog){   //给当前按钮添加点击事件
			 			    	 var array = new Array();
			 			    	 for(var i=0;i<a.length;i++){ 
			 				 		array.push(a[i].id);
			 				 	}
			 				 	$.post("../../../dynamic/ObjStructure/deleteObjStructure",{ids : array.join(",")}, function(result) {
			 				 		//BootstrapDialog.alert(result.message);
			 				 		BootstrapDialog.show({
			 	        	            title: constant.TIP,
			 	        	            message: result.message,
			 	        	            buttons: [{
			 	        	                label: constant.CONFIRM,
			 	        	                action: function(dialog) {
			 	        	                	dialog.close();
			 	        	                	window.close();
			 	        	                	return false;
			 	        	                }
			 	        	            }]
			 	        	        });
			 				 		refreshTable();
									dialog.close();
								},"json");
				 			      }
			 			    },{
			 			      label : constant.CANCEL,
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
		    $("#btnAdd").click(function(){  
		        var win = SF.showModalDlg("struct_index_add.html","add",null,800,400,dlg_callback); 
		     });
		    $table2 = $('#table2').bootstrapTable({ 
			      locale:'zh-CN',//中文支持 
			      pagination: false,//是否开启分页（*）
			      //striped:true,  //奇偶行渐色表
			   /*   pageSize: 5,//每页的记录行数（*）
			      pageList: [5,10],//可供选择的每页的行数（*）
*/			      idField: "id", //标识哪个字段为id主键
			      uniqueId: "id",   //每一行的唯一标识，一般为主键列
			      sidePagination: "server", //分页方式：client 客户端分页，server 服务端分页（*）
			      showColumns: false, //右上角列选择
			       showToggle:false,  //右上角视图切换
			      //showRefresh: true, //是否显示刷新按钮
			      minimumCountColumns: 2, //最少允许的列数
			      clickToSelect: true, 
			      smartDisplay:true,  
			      cache : false,
				    columns: [
					    {field: 'checkStatus',checkbox: true, edit:false},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
					    //{field: 'checkStatus',radio: true},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
				        {field: 'id', visible:false, edit:false},   
					    {
					        field: 'code',
					        title: '关系编码', 
					        sortable:true,
					        //editable: true,
					    	edit:false
					    }, {
					        field: 'name',
					        title: '关系名称'  
					    }, 
					    {
					        field: 'parentName',
					        title: '父对象' 
					    },
					    {
					        field: 'childName',
					        title: '子对象' 
					    }
					    
				    ], 
				    onPageChange: function (number, size) {
				    	refreshTable();
				        return false;
				    },
				   
				    onCheck:function(row){
				    
				    }
				}); 
				/*	refreshTable();//首次调用  
					SF.SearchPanel.onload(searchpanel); */
					//删除数据
				
		    //打开子窗口，添加数据
		  
	
		

	});  

function toModify(e, value, row, index){
	$.post("../../../dynamic/ObjStructure/getObjStructure",{id:row.id},function(result){
		 var win = SF.showModalDlg("struct_index_add.html?id="+row.id,"update",result.dataset.objstructure,800,400,dlg_callback); 
	});
     //var win = SF.showModalDlg("struct_index_add.html","add",sdata,800,400,dlg_callback); 
}

function modifyRow(e, value, row, index){ 
	 var sdata = {"kname":"info","kname2":23423};
    var win = SF.showModalDlg("struct_index_add.html?id="+row.id,"add",sdata,800,400,dlg_callback); 
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
	   url: "../../../dynamic/ObjStructure/fetchObjStructureList",       
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
	   		//alert("请求异常！");
	   		/*alert(XMLHttpRequest.status);
	   		alert(XMLHttpRequest.readyState);
	   		alert(textStatus);*/
	   }
	 }); 
} 
/* 弹出一棵树 */
function data_tree(obj_id) {
	
	var setting = {
		/*
		 * check: { enable: true //是否可选中 },
		 */
		view : {
			dblClickExpand : true, // 双击节点时，是否自动展开父节点的标识
			showLine : true, // 设置 zTree 是否显示节点的图标。
			selectedMulti : false
		// 设置是否允许同时选中多个节点。
		},
		data : {
			simpleData : {
				// 如果设置为 true，请务必设置 setting.data.simpleData 内的其他参数: idKey /
				// pIdKey / rootPId，并且让数据满足父子关系。
				enable : true, // 使用简单数据模式
				idKey : "id", // 唯一标识名称
				pIdKey : "pid", // 父节点唯一标识名称
				rootPId : 0
			// 。[setting.data.simpleData.enable = true 时生效]
			}
		},
		callback : {
			beforeDblClick : function(treeId, treeNode) {
				layer.close(index);
			}
		}
	};
	/*var t = $("#ztree_show");ztree_connection
  	t = $.fn.zTree.init(t, setting, null);*/
	var t = $("#ztree_show");
	$.getJSON('../../../dynamic/ObjStructure/fetchDicTreeByComboboxSub?structId='+obj_id, function(result) {
		t = $.fn.zTree.init(t, setting, result.dataset.treenode);
		var treeObj = $.fn.zTree.getZTreeObj("ztree_show"); 
		treeObj.expandAll(true); 
	});
}
function data_tree_connect(obj_id) {
	var setting = {
		/*
		 * check: { enable: true //是否可选中 },
		 */
		view : {
			dblClickExpand : true, // 双击节点时，是否自动展开父节点的标识
			showLine : true, // 设置 zTree 是否显示节点的图标。
			selectedMulti : false
		// 设置是否允许同时选中多个节点。
		},
		data : {
			simpleData : {
				// 如果设置为 true，请务必设置 setting.data.simpleData 内的其他参数: idKey /
				// pIdKey / rootPId，并且让数据满足父子关系。
				enable : true, // 使用简单数据模式
				idKey : "id", // 唯一标识名称
				pIdKey : "pid", // 父节点唯一标识名称
				rootPId : 0
			// 。[setting.data.simpleData.enable = true 时生效]
			}
		},
		callback : {
			beforeDblClick : function(treeId, treeNode) {
				layer.close(index);
			}
		},
		edit:{
        	enable :true,
        	showRemoveBtn : false,
        	showRenameBtn : false
        }
	};
	/*var t = $("#ztree_show");ztree_connection
  	t = $.fn.zTree.init(t, setting, null);*/
	var t = $("#ztree_show");
	
	$.getJSON('../../../dynamic/ObjStructure/fetchObjStructureRelation?id='+obj_id, function(result) {
		t = $.fn.zTree.init(t, setting, result.dataset.rows);
		var treeObj = $.fn.zTree.getZTreeObj("ztree_show"); 
		treeObj.expandAll(true); 
	});
}
function refreshTable2(row) {
	var options = $table.bootstrapTable('getOptions'); 
    var number =  options.pageNumber;
    var size = options.pageSize;
    var sortName = options.sortName;
    var sortOrder = options.sortOrder;
 /*   var objs = SF.getSearchParams();*/
	$.post('../../../dynamic/ObjStructure/fetchObjStructureSub?id='+ row.id,{limit:10000},function(res){
		var pagedata = {};
    	pagedata.rows = res.dataset.rows;
    //	pagedata.total = res.total;
    	//console.info(pagedata);
    	$table2.bootstrapTable('load', pagedata);
    	return false;

	});
} 