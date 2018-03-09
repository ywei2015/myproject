//===================================================================
// 项目名称：Agile-MES
// 模块名称：生产工单(计划、实际)列表
// 类    名：ProduceWo.js
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
	},{
		type:"combox",   
		title:"工单类型",
		dataIndex: 'pwoType',
		dataText: 'pwoTypeName',
		fieldText:"name",
		fieldValue:"pid",
		url:"../../../../dynamic/dicView/listDic?view=v_produce_wotype",
		root: "dataset.data"
	} ],
	layoutConfig : {
		columns : 4
	},
	onsearch : function() {
		refreshTable();
	}
};
var $table;
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
								},
								{
									field : "action",
									title : "操作",
									align : "center",
									width : '80',
									formatter : function(value, row, index) {
										var status = row.status;
										if(status == 0){
											var modifyHtml = '<a href="#" class="modify"><li id="modify" class="glyphicon glyphicon-pencil"> </li></a>';
											var strHtml = modifyHtml;
											return strHtml;
										}else{
											return "";
										}
									},
									events : {
										'click .modify' : function(e, value,
												row, index) {
											modifyRow(e, value, row, index);// 修改操作
										}
									},
									edit : false
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
						}
					});

	refreshTable();// 首次调用
	
	$("#BtnAdd").click(function(){
		var sdata = {"f_pid":null};
	    var win = SF.showModalDlg("ProduceWoAdd.html","add",sdata,800,400,dlg_callback);
	 });

	// 删除工单数据
	$("#BtnDel")
			.click(
					function() {
						var rowdata = $('#table').bootstrapTable(
								'getSelections');
						if (rowdata.length == 0) {
							BootstrapDialog.show({
					            title: constant.TIP,
					            message: constant.LOWEST_CHOICE_ONE,
					            buttons: [{
					                label: constant.CONFIRM,
					                action: function(dialog) {
					                	dialog.close();
					                }
					            }]
					        });
						}else if(rowdata.length >= 1){
							for (var i = 0; i < rowdata.length; i++){
								if(rowdata[i].status != '0'){
									BootstrapDialog.show({
							            title: constant.TIP,
							            message: "已选数据中正在使用，请重新选择！",
							            buttons: [{
							                label: constant.CONFIRM,
							                action: function(dialog) {
							                	dialog.close();
							                }
							            }]
							        });
								}else{
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
							 				 	$.post("../../../../dynamic/Produce/deleteProduceWo",{ids : array.join(",")}, function(result) {
							 				 		BootstrapDialog.alert(constant.SUCCESS.MSG);
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
	//工单下达
	$("#BtnIssue")
	.click(
			function() {
				var rowdata = $('#table').bootstrapTable(
						'getSelections');
				if (rowdata.length == 0) {
					BootstrapDialog.show({
			            title: constant.TIP,
			            message: constant.LOWEST_CHOICE_ONE,
			            buttons: [{
			                label: constant.CONFIRM,
			                action: function(dialog) {
			                	dialog.close();
			                }
			            }]
			        });
				}else if(rowdata.length >= 1){
					for (var i = 0; i < rowdata.length; i++) {
						if(rowdata[i].status != '0'){
							BootstrapDialog.show({
					            title: constant.TIP,
					            message: "已选数据中包含已下发记录，请重新选择！",
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
					 			  message : "是否下达工单数据！",
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
					 				 	$.post("../../../../dynamic/Produce/saveIssueProduceWo",{ids : array.join(",")}, function(result) {
					 				 		BootstrapDialog.alert(constant.SUCCESS_MSG);
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
});

var dlg_callback = function(e) {
	if (e == 'ok') {
		refreshTable();
		BootstrapDialog.alert(constant.SUCCESS_MSG);
	}
}

// 任务列表修改
function modifyRow(e, value, row, index) {
	var sdata = {
		"f_pid" : row.pid
	};
	var win = SF.showModalDlg("ProduceWoAdd.html", "update", sdata, 800, 400,
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
		url : "../../../../dynamic/Produce/findProduceWoList",
		data : {
			'page' : number,
			'limit' : size,
			'startTime' : objsSearch.startTime,
       		'endTime' : objsSearch.endTime,
       		'pwoType' : objsSearch.pwoType
		},
		success : function(res) {
			var pagedata = {};
			pagedata.rows = res.rows;
			pagedata.total = res.total;
			$table.bootstrapTable('load', pagedata);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}
