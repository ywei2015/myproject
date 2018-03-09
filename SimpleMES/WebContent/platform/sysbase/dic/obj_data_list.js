var $table;
var myColumns = [];
var searchpanel = {
	name : "search",
	type : "searchpanel",
	title : "查询条件",
	berth : "SearchPanel",
	items : [ {
		type : "textinput",
		title : "编号",
		dataIndex : 'code'
	}, {
		type : "textinput",
		title : "名称",
		dataIndex : 'name'
	} ],
	layoutConfig : {
		columns : 4
	},
	onsearch : function() {
		refreshTable();
	}
};
var dlg_callback = function(e) {
	if (e == 'ok') {
		refreshTable();
	}
	if (e == 'cancel')
		;
	return false;
}
$(function() {
	$table = $('#table').bootstrapTable({
		locale : 'zh-CN',//中文支持 
		pagination : true,//是否开启分页（*）
		//striped:true,  //奇偶行渐色表
		pageSize : 10,//每页的记录行数（*）
		pageList : [ 10, 25, 50, 100 ],//可供选择的每页的行数（*）
		idField : "id", //标识哪个字段为id主键
		uniqueId : "id", //每一行的唯一标识，一般为主键列
		sidePagination : "server", //分页方式：client 客户端分页，server 服务端分页（*）
		showColumns : true, //是否显示所有的列
		//showRefresh: true, //是否显示刷新按钮
		singleSelect : true,
		minimumCountColumns : 2, //最少允许的列数
		showToggle : true, //是否显示详细视图和列表视图的切换按钮
		toolbar : '#toolbar', //工具按钮用哪个容器
		clickToSelect : true,
		smartDisplay : true,
		cache : false,
		columns : myColumns,
		onPageChange : function(number, size) {
			refreshTable();
			return false;
		},
		onSort : function(name, order) {
			refreshTable();
			return false;
		}
	});
	refreshTable();//首次调用  
	SF.SearchPanel.onload(searchpanel);
	//删除数据---变成单选后一次只能删除一个
	$("#btnDel").click(function() {
		var a = $table.bootstrapTable('getSelections');
		if (a.length == 0) {
			BootstrapDialog.show({
				title : constant.TIP,
				message : constant.LOWEST_CHOICE_ONE,
				buttons : [ {
					label : constant.CONFIRM,
					action : function(dialog) {
						dialog.close();
						window.close();
						return false;
					}
				} ]
			});
			//	BootstrapDialog.alert(constant.LOWEST_CHOICE_ONE);
		} else {
			BootstrapDialog.show({
				title : constant.TIP,
				message : constant.CONFIRM_DELETE,
				buttons : [ {
					label : constant.CONFIRM,
					cssClass : "btn-primary",
					icon : "glyphicon glyphicon-ban-circle",//通过bootstrap的样式添加图标按钮
					action : function(dialog) { //给当前按钮添加点击事件
						dialog.close();
						var array = new Array();
						for (var i = 0; i < a.length; i++) {
							array.push(a[i].id);
							//判断该节点是否存在子节点，如果存在，不删除
							var flag = false;
							$.ajax({
								type : "POST",
								dataType : "json",
								url : "../../../dynamic/objBase/isExistChild",
								async : false,//同步  
								data : {
									id : a[i].id
								},
								success : function(result) {
									if (result.status != 200) {
										BootstrapDialog.show({
											title : constant.TIP,
											message : result.message,
											buttons : [ {
												label : constant.CONFIRM,
												action : function(dialog) {
													dialog.close();
													window.close();
													return false;
												}
											} ]
										});
										flag = true;
									}

								},
							});
							/*	$.post("../../../dynamic/objBase/isExistChild",{id:a[i].id},function(result){	
									if(result.status!=200){
									}
								},"json");*/
							if (flag) {
								return;
							}
						}
						$.post("../../../dynamic/objBase/deletes", {
							ids : array.join(",")
						}, function(result) {
							BootstrapDialog.show({
								title : constant.TIP,
								message : result.message,
								buttons : [ {
									label : constant.CONFIRM,
									action : function(dialog) {
										dialog.close();
										window.close();
										return false;
									}
								} ]
							});
							//BootstrapDialog.alert(result.message);
							refreshTable();

						}, "json");
					}
				}, {
					label : constant.CANCEL,
					cssClass : "btn-primary",
					icon : "glyphicon glyphicon-ban-circle",
					action : function(dialog) { //给当前按钮添加点击事件
						dialog.close();
					}
				} ]
			});
		}
	});
	//打开子窗口，添加数据
	$("#btnAdd").click(
			function() {
				var id = $.fn.geturl("rightIframe", "id");
				var win = SF.showModalDlg(
						"obj_data_edit.html?superClass=" + id, "add", null,
						800, 400, dlg_callback);
			});

	$("#btnOpen").click(
			function() {
				var id = $.fn.geturl("rightIframe", "id");
				var sdata = {
					"id" : id,
					"flag" : '0'
				};
				var win = SF.showModalDlg("DerivedClasses.html", "add", sdata,
						1100, 500, dlg_callback);
			});

	$("#btnEdit").click(
			function() {
				//		    	var id=$.fn.geturl("rightIframe","id");
				var rowdata = $table.bootstrapTable('getSelections');
				if (rowdata.length == 0) {
					BootstrapDialog.show({
						title : constant.TIP,
						message : constant.LOWEST_CHOICE_ONE,
						buttons : [ {
							label : constant.CONFIRM,
							action : function(dialog) {
								dialog.close();
								window.close();
								return false;
							}
						} ]
					});
				} else {
					var id = rowdata[0].id;
					var sdata = {
						"id" : id,
						"flag" : '1'
					};
					var win = SF.showModalDlg("DerivedClasses.html", "update",
							sdata, 1100, 500, dlg_callback);
				}
			});
});

function toModify(e, value, row, index) {
	var id = $.fn.geturl("rightIframe", "id");
	$.post("../../../dynamic/objBase/get", {
		id : row.id
	}, function(result) {
		var win = SF
				.showModalDlg("obj_data_edit.html?id=" + row.id
						+ "&superClass=" + id, "update", result, 800, 400,
						dlg_callback);
	});
}
//重新查询、重新排序、切换分页事件
function refreshTable() {
	var options = $table.bootstrapTable('getOptions');
	var number = options.pageNumber;
	var size = options.pageSize;
	var sortName = options.sortName;
	var sortOrder = options.sortOrder;
	var objs = SF.getSearchParams();
	var id = $.fn.geturl("rightIframe", "id");
	$.ajax({
		type : "POST",
		async : true,
		url : "../../../dynamic/objBase/findListPagination",
		data : {
			'page' : number,
			'limit' : size,
			'code' : objs.code,
			'name' : objs.name,
			'id' : id

		},
		success : function(res) {
			var pagedata = {};
			pagedata.rows = res.rows;
			pagedata.total = res.total;
			$table.bootstrapTable('load', pagedata);
			return false;
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	var myColumn1 = {
		field : 'checkStatus',
		checkbox : true,
		edit : false
	};
	var myColumn2 = {
		field : 'id',
		visible : false,
		edit : false
	};
	var myColumn3 = {
		field : 'code',
		title : '编码',
		sortable : true,
		edit : false
	};
	var myColumn4 = {
		field : 'name',
		title : '名称'
	};
	var myColumn5 = {
		field : 'fullName',
		title : '全名称'
	};
	var myColumn6 = {
		field : 'num1',
		title : '排序值'
	};
	var myColumn7 = {
		field : 'sysFlag',
		title : '有效性',
		formatter : function(value, row, index) {
			if (row.sysFlag == 1) {
				return "有效";
			}
			return "无效";
		}
	}
	myColumns.push(myColumn1);
	myColumns.push(myColumn2);
	myColumns.push(myColumn3);
	myColumns.push(myColumn4);
	myColumns.push(myColumn5);
	myColumns.push(myColumn6);
	myColumns.push(myColumn7);
	var id = $.fn.geturl("rightIframe", "id");
	if(id != "1000"){
		$.ajax({
			url : '../../../dynamic/objBase/fetchObjAttributeList?objId=' + id,
			async : false,
			type : "get",
			data : {},
			success : function(data, status) {
				$.each(data.dataset.objAttribute, function(key, value) {
					myColumns.push({
						"field" : value.column,
						"title" : value.name
					});
				});
			}
		});
	}
	myColumns
			.push({
				field : "action",
				title : "操作",
				align : "center",
				width : '80',
				formatter : function(value, row, index) {
					var modifyHtml = '<a href="#" class="modify" ><li id="modify" class="glyphicon glyphicon-pencil"> </li></a>';
					var strHtml = modifyHtml;
					return strHtml;
				},
				events : {
					'click .modify' : function(e, value, row, index) {
						toModify(e, value, row, index);//修改操作 
					},
					'click .delete' : function(e, value, row, index) {
						removeRow(row.id);
					}
				},
				edit : false
			});
	$table.bootstrapTable("refreshOptions", {
		columns : myColumns
	});
	myColumns.length = 0;
}