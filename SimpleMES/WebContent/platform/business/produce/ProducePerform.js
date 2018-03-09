//===================================================================
// 项目名称：Agile-MES
// 模块名称：生产工单执行列表
// 类    名：ProducePerform.js
//===================================================================
var searchpanel = {
	name : "search",
	type : "searchpanel",
	title : "查询条件",
	berth : "SearchPanel",
	items : [ {
		type : "datetime",
		title : "开始时间",
		dataIndex : 'startTime',
		config : {
			format : 'yyyy-mm-dd',
			minView : 'month',
			initialDate : new Date()
		}
	}, {
		type : "datetime",
		title : "结束时间",
		dataIndex : 'endTime',
		config : {
			format : 'yyyy-mm-dd',
			minView : 'month',
			initialDate : new Date()
		}
	} ],
	layoutConfig : {
		columns : 4
	},
	onsearch : function() {
		refreshTable();
	}
};
var $table;
var $table1;
var $table2;
$(function() {
	SF.SearchPanel.onload(searchpanel);
	$table = $('#table')
			.bootstrapTable(
					{
						locale : 'zh-CN',// 中文支持
						pagination : true,// 是否开启分页（*）
						// striped:true, //奇偶行渐色表
						pageSize : 10,// 每页的记录行数（*）
						pageList : [ 10, 25, 50, 100 ],// 可供选择的每页的行数（*）
						idField : "pid", // 标识哪个字段为id主键
						uniqueId : "pid", // 每一行的唯一标识，一般为主键列
						sidePagination : "server", // 分页方式：client 客户端分页，server
						// 服务端分页（*）
						showColumns : true, // 是否显示所有的列
						minimumCountColumns : 2, // 最少允许的列数
						// showToggle: true, //是否显示详细视图和列表视图的切换
						singleSelect : true, // 行单选按钮
						toolbar : '#toolbar', // 工具按钮用哪个容器
						columns : [
								{
									field : 'checkStatus',
									checkbox : true,
									edit : false,
									formatter : function stateFormatter(value,
											row, index) {
										if (index == 0) {
											refreshProduceInTable(row.pid);
											refreshProduceOutTable(row.pid);
											return {
												disabled : false,// 设置是否可用
												checked : true
											// 设置选中
											};
										}
										return value;
									}
								}, // 给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!
								{
									field : 'pid',
									visible : false,
									edit : false
								},
								{
									field : 'pwoSn',
									title : '生产工单号',
									align : "center"
								},
								{
									field : 'pwoTypeName',
									title : '工单类型',
									align : "center"
								},
								{
									field : 'workshopName',
									title : '生产车间名称',
									align : "center"
								},
								{
									field : 'matName',
									title : '产出物料名称',
									align : "center"
								},
								/*{
									field : 'productName',
									title : '成品名称',
									align : "center"
								},*/
								{
									field : 'unitName',
									title : '单位',
									align : "center"
								},
								{
									field : 'planQuantity',
									title : '计划生产数量',
									align : "center"
								},
								{
									field : 'planStVo',
									title : '计划开始时间',
									align : "center"
								},
								{
									field : 'planEtVo',
									title : '计划完成时间',
									align : "center"
								},
								{
									field : 'factStVo',
									title : '实际开始时间',
									align : "center"
								},
								{
									field : 'factEtVo',
									title : '实际完成时间',
									align : "center"
								},
								{
									field : 'issueUsername',
									title : '工单下达人姓名',
									align : "center"
								},
								{
									field : 'issueTimeVo',
									title : '工单下达时间',
									align : "center"
								},
								{
									field : 'recipeStd',
									title : '产品配方标准',
									align : "center"
								},
								{
									field : 'craftStd',
									title : '生产工艺标准',
									align : "center"
								},
								{
									field : 'status',
									title : '工单状态',
									align : "center",
									formatter : function(value, row, index) {
										if (row.status == '0') {
											return "草稿";
										} else if(row.status == '1'){
											return "下达";
										} else if(row.status == '10'){
											return "执行中";
										} else if(row.status == '20'){
											return "执行完毕";
										}
									}
								} ],
						clickToSelect : true,
						smartDisplay : true,
						cache : false,
						onPageChange : function(number, size) {
							// var objsSearch = SF.getSearchParams();
							refreshTable();
							return false;
						},
						onSort : function(name, order) {
							// var objsSearch = SF.getSearchParams();
							refreshTable();
							return false;
						},
						onCheck:function(row){
					    	$("#hiddenPwoId").val("");
					    	$("#hiddenPwoId").val(row.pid);
					    	refreshProduceInTable(row.pid);
					    	refreshProduceOutTable(row.pid);
					    },
					    onClickRow: function (item, $element,index) {
					    	$table1.bootstrapTable("removeAll");
					    	$table2.bootstrapTable("removeAll");
					    }
						/*onClickRow: function (item, $element,index) {
					    	$("#hiddenPwoId").val("");
					    	$("#hiddenPwoId").val(item.pid);
					    	refreshProduceInTable(item.pid);
					    	refreshProduceOutTable(item.pid);
					    }*/
					});

	refreshTable();// 首次调用
	
	//新增投入
	$("#BtnInAdd").click(function(){
		var rowdata = $('#table').bootstrapTable('getSelections');
	 	if(rowdata.length!=1){
	 		BootstrapDialog.show({
	            title: constant.TIP,
	            message: "请选择一条工单记录！",
	            buttons: [{
	                label: constant.CONFIRM,
	                action: function(dialog) {
	                	dialog.close();
	                }
	            }]
	        });
	 	}else{
	 		 var sdata = {"pwoId":rowdata[0].pid};
		     var addUser=SF.showModalDlg("ProduceInAdd.html","add",sdata,800,400,dlg_callback);
	 	}
	 });
	
	//删除投入
	$("#BtnInDel")
			.click(
					function() {
						var rowdata = $('#table1').bootstrapTable(
								'getSelections');
						if (rowdata.length == 0) {
							BootstrapDialog.show({
			    	            title: constant.TIP,
			    	            message: "请选择工单投入记录！",
			    	            buttons: [{
			    	                label: constant.CONFIRM,
			    	                action: function(dialog) {
			    	                	dialog.close();
			    	                }
			    	            }]
			    	        });
						} else {
							BootstrapDialog.show({
								title:constant.TIP,
					 			  message : constant.CONFIRM_DELETE,
					 			  buttons : [
					 			   {
					 			      label : constant.CONFIRM,
					 			      cssClass : "btn btn-primary",
					 			      //icon : "glyphicon glyphicon-ban-circle",//通过bootstrap的样式添加图标按钮
					 			      action : function(dialog){   //给当前按钮添加点击事件
					 			    	 var array = new Array();
					 			    	 for(var i=0;i<rowdata.length;i++){ 
					 				 		array.push(rowdata[i].pid);
					 				 	}
					 				 	$.post("../../../../dynamic/Produce/deleteProduceIn",{ids : array.join(",")}, function(result) {
					 				 		BootstrapDialog.alert(constant.SUCCESS_MSG);
					 				 		var rowdata = $('#table').bootstrapTable('getSelections');
											refreshProduceInTable(rowdata[0].pid);
											dialog.close();
										},"json");
						 			      }
					 			    },{
					 			      label : constant.CANCEL,
					 			      cssClass : "btn btn-Secondary",
					 			      //icon : "glyphicon glyphicon-ban-circle",
					 			      action : function(dialog){   //给当前按钮添加点击事件
					 			            dialog.close();
					 			      }
					 			    }
					 			  ]
					 		});
						}
					});
	
	//新增产出
	$("#BtnOutAdd").click(function(){
		var rowdata = $('#table').bootstrapTable('getSelections');
	 	if(rowdata.length!=1){
	 		BootstrapDialog.show({
	            title: constant.TIP,
	            message: "请选择一条工单记录！",
	            buttons: [{
	                label: constant.CONFIRM,
	                action: function(dialog) {
	                	dialog.close();
	                }
	            }]
	        });
	 	}else{
	 		 var sdata = {"pwoId":rowdata[0].pid};
		     var addUser=SF.showModalDlg("ProduceOutAdd.html","add",sdata,800,400,dlg_callback);
	 	}
	 });
	
	//删除产出
	$("#BtnOutDel")
			.click(
					function() {
						var rowdata = $('#table2').bootstrapTable(
								'getSelections');
						if (rowdata.length == 0) {
							BootstrapDialog.show({
					            title: constant.TIP,
					            message: "请选择工单产出数据！",
					            buttons: [{
					                label: constant.CONFIRM,
					                action: function(dialog) {
					                	dialog.close();
					                }
					            }]
					        });
						} else {
							BootstrapDialog.show({
								title:constant.TIP,
					 			  message : constant.CONFIRM_DELETE,
					 			  buttons : [
					 			   {
					 			      label : constant.CONFIRM,
					 			      cssClass : "btn btn-primary",
					 			      //icon : "glyphicon glyphicon-ban-circle",//通过bootstrap的样式添加图标按钮
					 			      action : function(dialog){   //给当前按钮添加点击事件
					 			    	 var array = new Array();
					 			    	 for(var i=0;i<rowdata.length;i++){ 
					 				 		array.push(rowdata[i].pid);
					 				 	}
					 				 	$.post("../../../../dynamic/Produce/deleteProduceOut",{ids : array.join(",")}, function(result) {
					 				 		BootstrapDialog.alert(constant.SUCCESS_MSG);
					 				 		var rowdata = $('#table').bootstrapTable('getSelections');
											refreshProduceOutTable(rowdata[0].pid);
											dialog.close();
										},"json");
						 			      }
					 			    },{
					 			      label : constant.CANCEL,
					 			      cssClass : "btn btn-Secondary",
					 			      //icon : "glyphicon glyphicon-ban-circle",
					 			      action : function(dialog){   //给当前按钮添加点击事件
					 			            dialog.close();
					 			      }
					 			    }
					 			  ]
					 		});
						}
					});
	
	//开始工单
	$("#BtnBegin")
	.click(
			function() {
				var rowdata = $('#table').bootstrapTable(
						'getSelections');
				if (rowdata.length == 0) {
					BootstrapDialog.show({
	    	            title: constant.TIP,
	    	            message: "请选择工单数据！",
	    	            buttons: [{
	    	                label: constant.CONFIRM,
	    	                action: function(dialog) {
	    	                	dialog.close();
	    	                }
	    	            }]
	    	        });
				}else if(rowdata.length >= 1){
					for (var i = 0; i < rowdata.length; i++){
						if(rowdata[i].factStVo !=null && rowdata[i].factStVo != ""){
							BootstrapDialog.show({
			    	            title: constant.TIP,
			    	            message: "请选择未开始工单！",
			    	            buttons: [{
			    	                label: constant.CONFIRM,
			    	                action: function(dialog) {
			    	                	dialog.close();
			    	                }
			    	            }]
			    	        });
						}else {
							BootstrapDialog.show({
								title:constant.TIP,
					 			  message : "是否开始工单？",
					 			  buttons : [
					 			   {
					 			      label : constant.CONFIRM,
					 			      cssClass : "btn btn-primary",
					 			      //icon : "glyphicon glyphicon-ban-circle",//通过bootstrap的样式添加图标按钮
					 			      action : function(dialog){   //给当前按钮添加点击事件
					 			    	 var array = new Array();
					 			    	 for(var i=0;i<rowdata.length;i++){ 
					 				 		array.push(rowdata[i].pid);
					 				 	}
					 				 	$.post("../../../../dynamic/Produce/saveBeginProduceWo",{ids : array.join(",")}, function(result) {
					 				 		BootstrapDialog.alert("操作成功!");
					 				 		refreshTable();
											dialog.close();
										},"json");
						 			      }
					 			    },{
					 			      label : constant.CANCEL,
					 			      cssClass : "btn btn-Secondary",
					 			      //icon : "glyphicon glyphicon-ban-circle",
					 			      action : function(dialog){   //给当前按钮添加点击事件
					 			            dialog.close();
					 			      }
					 			    }
					 			  ]
					 		});
						}
					}
				}
			});
	
	//结束工单
	$("#BtnEnd")
	.click(
			function() {
				var rowdata = $('#table').bootstrapTable(
						'getSelections');
				if (rowdata.length == 0) {
					BootstrapDialog.show({
	    	            title: constant.TIP,
	    	            message: "请选择工单数据！",
	    	            buttons: [{
	    	                label: constant.CONFIRM,
	    	                action: function(dialog) {
	    	                	dialog.close();
	    	                }
	    	            }]
	    	        });
				}else if(rowdata.length >= 1){
					for (var i = 0; i < rowdata.length; i++){
						if(rowdata[i].factEtVo !=null && rowdata[i].factEtVo != ""){
							BootstrapDialog.show({
			    	            title: constant.TIP,
			    	            message: "请选择未完成工单！",
			    	            buttons: [{
			    	                label: constant.CONFIRM,
			    	                action: function(dialog) {
			    	                	dialog.close();
			    	                }
			    	            }]
			    	        });
						}else {
							BootstrapDialog.show({
								title:constant.TIP,
					 			  message : "是否结束工单？",
					 			  buttons : [
					 			   {
					 			      label : constant.CONFIRM,
					 			      cssClass : "btn btn-primary",
					 			      //icon : "glyphicon glyphicon-ban-circle",//通过bootstrap的样式添加图标按钮
					 			      action : function(dialog){   //给当前按钮添加点击事件
					 			    	 var array = new Array();
					 			    	 for(var i=0;i<rowdata.length;i++){ 
					 				 		array.push(rowdata[i].pid);
					 				 	}
					 				 	$.post("../../../../dynamic/Produce/saveEndProduceWo",{ids : array.join(",")}, function(result) {
					 				 		BootstrapDialog.alert("操作成功!");
					 				 		refreshTable();
											dialog.close();
										},"json");
						 			      }
					 			    },{
					 			      label : constant.CANCEL,
					 			      cssClass : "btn btn-Secondary",
//					 			      icon : "glyphicon glyphicon-ban-circle",
					 			      action : function(dialog){   //给当前按钮添加点击事件
					 			            dialog.close();
					 			      }
					 			    }
					 			  ]
					 		});
						}
					}
				}
			});
///////////////////////////////////////////生产工单投入///////////////////////////////
	$table1 = $('#table1')
	.bootstrapTable(
			{
				locale : 'zh-CN',// 中文支持
				pagination : true,// 是否开启分页（*）
				// striped:true, //奇偶行渐色表
				pageSize : 10,// 每页的记录行数（*）
				pageList : [ 10, 25, 50, 100 ],// 可供选择的每页的行数（*）
				idField : "pid", // 标识哪个字段为id主键
				uniqueId : "pid", // 每一行的唯一标识，一般为主键列
				sidePagination : "server", // 分页方式：client 客户端分页，server
				// 服务端分页（*）
				showColumns : true, // 是否显示所有的列
				minimumCountColumns : 2, // 最少允许的列数
				// showToggle: true, //是否显示详细视图和列表视图的切换
				singleSelect : true, // 行单选按钮
				toolbar : '#toolbar1', // 工具按钮用哪个容器
				columns : [
						{
							field : 'checkStatus',
							checkbox : true,
							edit : false,
							formatter : function stateFormatter(value,
									row, index) {
								if (index == 0) {
									return {
										disabled : false,// 设置是否可用
										checked : true
									// 设置选中
									};
								}
								return value;
							}
						}, // 给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!
						{
							field : 'pid',
							visible : false,
							edit : false
						},
						{
							field : 'produceLineName',
							title : '生产线',
							align : "center"
						},
						{
							field : 'equCode',
							title : '生产设备编码',
							align : "center"
						},
						{
							field : 'equName',
							title : '生产设备名称',
							align : "center"
						},
						{
							field : 'matCode',
							title : '投入物料编码',
							align : "center"
						},
						{
							field : 'matName',
							title : '投入物料名称',
							align : "center"
						},
						{
							field : 'unitName',
							title : '单位',
							align : "center"
						},
						{
							field : 'quantity',
							title : '投入物料数量',
							align : "center"
						},
						{
							field : 'inputStVo',
							title : '投料开始时间',
							align : "center"
						},
						{
							field : 'inputEtVo',
							title : '投料完成时间',
							align : "center"
						},
						{
							field : 'matLotno',
							title : '投入物料批次号',
							align : "center"
						},
						{
					    	field:"action",title:"操作",align:"center", width : '80',
					    	formatter:function(value,row,index){
						    	var modifyHtml = '<a href="#" class="modify" title="编辑"><li id="modify" class="glyphicon glyphicon-pencil"> 编辑</li></a>';
					    		var strHtml = modifyHtml;
					    		return strHtml;
					    	},
					    	events : {
								'click .modify' : function(e, value,
										row, index) {
									modifyRow(e, value, row, index);// 修改操作
								}
							},
					    	edit:false
					    }],
				clickToSelect : true,
				smartDisplay : true,
				cache : false,
				onPageChange : function(number, size) {
					return false;
				},
				onSort : function(name, order) {
					return false;
				}
			});
		
	$table2 = $('#table2')
	.bootstrapTable(
			{
				locale : 'zh-CN',// 中文支持
				pagination : true,// 是否开启分页（*）
				// striped:true, //奇偶行渐色表
				pageSize : 10,// 每页的记录行数（*）
				pageList : [ 10, 25, 50, 100 ],// 可供选择的每页的行数（*）
				idField : "pid", // 标识哪个字段为id主键
				uniqueId : "pid", // 每一行的唯一标识，一般为主键列
				sidePagination : "server", // 分页方式：client 客户端分页，server
				// 服务端分页（*）
				showColumns : true, // 是否显示所有的列
				minimumCountColumns : 2, // 最少允许的列数
				// showToggle: true, //是否显示详细视图和列表视图的切换
				singleSelect : true, // 行单选按钮
				toolbar : '#toolbar2', // 工具按钮用哪个容器
				columns : [
						{
							field : 'checkStatus',
							checkbox : true,
							edit : false,
							formatter : function stateFormatter(value,
									row, index) {
								if (index == 0) {
									return {
										disabled : false,// 设置是否可用
										checked : true
									// 设置选中
									};
								}
								return value;
							}
						}, // 给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!
						{
							field : 'pid',
							visible : false,
							edit : false
						}/*,
						{
							field : 'collectType',
							title : '对象类别',
							align : "center",
							formatter : function(value, row, index) {
								if (row.collectType == '1') {
									return "小单元";
								} else if(row.status == '2'){
									return "工序";
								} else if(row.status == '3'){
									return "工段";
								} else if(row.status == '4'){
									return "生产线";
								} else if(row.status == '5'){
									return "车间";
								}
							}
						}*/,
						{
							field : 'objectName',
							title : '生产对象名称',
							align : "center"
						},
						{
							field : 'matName',
							title : '产出物料名称',
							align : "center"
						},
						{
							field : 'unitName',
							title : '单位',
							align : "center"
						},
						{
							field : 'quantity',
							title : '产出合格数量',
							align : "center"
						},
						{
							field : 'ngQuantity',
							title : '不良品数量',
							align : "center"
						},
						{
							field : 'lacation',
							title : '位置',
							align : "center"
						},
						{
							field : 'outStVo',
							title : '产出开始时间',
							align : "center"
						},
						{
							field : 'outEtVo',
							title : '产出完成时间',
							align : "center"
						},
						{
							field : 'warehouse',
							title : '仓库',
							align : "center"
						},{
					    	field:"action",title:"操作",align:"center", width : '80',
					    	formatter:function(value,row,index){
						    	var modifyHtml = '<a href="#" class="modify" title="编辑"><li id="modify" class="glyphicon glyphicon-pencil"> 编辑</li></a>';
					    		var strHtml = modifyHtml;
					    		return strHtml;
					    	},
					    	events : {
								'click .modify' : function(e, value,
										row, index) {
									modifyRow1(e, value, row, index);// 修改操作
								}
							},
					    	edit:false
					    } ],
				clickToSelect : true,
				smartDisplay : true,
				cache : false,
				onPageChange : function(number, size) {
					return false;
				},
				onSort : function(name, order) {
					return false;
				}
			});
});

var dlg_callback = function(e) {
	if (e == 'ok') {
		refreshTable();
		BootstrapDialog.alert(constant.SUCCESS_MSG);
	}
}

//工单投入修改
function modifyRow(e, value, row, index) {
	var sdata = {
		"f_pid" : row.pid
	};
	var win = SF.showModalDlg("ProduceInAdd.html", "update", sdata, 800, 400,
			dlg_callback);
}

//工单产出修改
function modifyRow1(e, value, row, index) {
	var sdata = {
		"f_pid" : row.pid
	};
	var win = SF.showModalDlg("ProduceOutAdd.html", "update", sdata, 800, 400,
			dlg_callback);
}

// 重新查询、重新排序、切换分页事件
function refreshTable() {
	var options = $table.bootstrapTable('getOptions');
	var number = options.pageNumber;
	var size = options.pageSize;
	var sortName = options.sortName;
	var sortOrder = options.sortOrder;
	var objsSearch = SF.getSearchParams();
	$.ajax({
		type : "POST",
		async : true,
		url : "../../../../dynamic/Produce/findProducePerformList",
		data : {
			'page' : number,
			'limit' : size,
			'startTime' : objsSearch.startTime,
       		'endTime' : objsSearch.endTime
		},
		success : function(res) {
			var pagedata = {};
			pagedata.rows = res.rows;
			pagedata.total = res.total;
			$table1.bootstrapTable("removeAll");
			$table2.bootstrapTable("removeAll");
			$table.bootstrapTable('load', pagedata);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}

function refreshProduceInTable(f_pid) {
	var options = $table1.bootstrapTable('getOptions');
    var number =  options.pageNumber;
    var size = options.pageSize;
    var sortName = options.sortName;
    var sortOrder = options.sortOrder;
    var objs = SF.getSearchParams();
	$.ajax({
	   type: "POST",
       async: true,
	   url: '../../../../dynamic/Produce/findProduceInList', 
	   data: {
		   'page':number,
		   'limit':size,
		   'pwoId':f_pid
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

function refreshProduceOutTable(f_pid) {
	var options = $table2.bootstrapTable('getOptions');
    var number =  options.pageNumber;
    var size = options.pageSize;
    var sortName = options.sortName;
    var sortOrder = options.sortOrder;
    var objs = SF.getSearchParams();
	$.ajax({
	   type: "POST",
       async: true,
	   url: '../../../../dynamic/Produce/findProduceOutList', 
	   data: {
		   'page':number,
		   'limit':size,
		   'pwoId':f_pid
       },
	   success: function(res){
	   		var pagedata = {};
        	pagedata.rows = res.rows;
			pagedata.total = res.total;
        	$table2.bootstrapTable('load', pagedata);
	   },
	   error: function(XMLHttpRequest, textStatus, errorThrown){
	   }
	 });
}
