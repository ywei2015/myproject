var struct = window.parent.document.getElementById("structId").value;
var $table;
var value2;
var radioValue;
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
	//基础模型维护查询还有问题
	onsearch : function() {
		if (struct == "10001" || structId == "10001") {
			//菜单项列表
			refreshTableMenu();
		}else{
			radioValue = $("input[name='optionsRadiosinline']:checked").val();
			refreshTable(radioValue);
		}
	}
};

var dlg_callback = function(e) {
	if (e == 'ok') {
		radioValue = $("input[name='optionsRadiosinline']:checked").val();
		refreshTable(radioValue);
	}
	if (e == 'cancel')
		;
}
jQuery(function($) {
	var attrDiv = document.getElementById('attrDiv');
	/*var addHtml = "<div class=\"row\"><div class=\"input-form form-group col-sm-6\">"
			+ "<label for=\"inputPassword\" class=\"col-sm-3 control-label\">网页地址</label>"
			+ "<div class=\"col-sm-9\"><input class=\"form-control\" type=\"text\"  name=\"value2\"></div></div></div>";
	var addHtml1 = "<div class=\"input-form form-group col-sm-6\">"
			+ "<label for=\"inputPassword\" class=\"col-sm-3 control-label\">网页地址</label>"
			+ "<div class=\"col-sm-9\"><input class=\"form-control\" type=\"text\"  name=\"value2\"></div></div>";
*///	$("#endRow").append(addHtml1);
	/*var dv_num = 0;
	$(".row").each(function() {
		dv_num += 1;
	});*/

	//查询按钮
	SF.SearchPanel.onload(searchpanel);

	//初始化当前节点，
	var parentId = $.fn.geturl("rightIframe", "parentId");
	var structId = $.fn.geturl("rightIframe", "structId");
	var keyId = $.fn.geturl("rightIframe", "keyId");

	//加载子节点
	if (struct != "10001" || structId != "10001") {
		$
				.ajax({
					url : '../../../dynamic/objBase/fetchObjBaseList?structId='
							+ structId + "&keyId=" + keyId,
					async : false,
					type : "get",
					data : {},
					success : function(data, status) {
						if (data.dataset.objBase.length > 0) {
							var radioDiv = document.getElementById('radioDiv');
							var lable = "<label>子节点类型：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>";
							var strHtml = "";
							$
									.each(
											data.dataset.objBase,
											function(key, value) {
												var inputHtml = "<label class=\"radio-inline\"><input type=\"radio\" name=\"optionsRadiosinline\" id=\""
														+ value.id
														+ "\" value=\""
														+ value.id
														+ "\" objEntityRefPid =\""+value.objEntityRefPid+"\"> "
														+ value.name
														+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>";
												strHtml += inputHtml;
											});
							strHtml = lable + strHtml;
							radioDiv.innerHTML = strHtml;
							$(".radio-inline input:first")
									.attr("checked", true);
							$("#radioDiv").show();
							radioValue = $(
									"input[name='optionsRadiosinline']:checked")
									.val();
							//根据选中的radio查询不同的列表
							refreshTable(radioValue);
						}
					}
				});
	}

	$(":radio").click(function() {
		refreshTable($(this).val());
	});

	//控制表格显示，字段不同情况下，显示不同值
	if(struct=="10001" || structId=="10001"){
			//初始化表单数据
			value2="网页地址";
			value3="图标";
		}else{
			value2="结构序列号";
			value3="other";
			//隐藏菜单栏
			$("#Formbody").find("input[name='value3']").parent().parent().hide();
			
		}
	$("#Formbody").find("input[name='value2']").parent().parent().find("label").html(value2);
	//	$('#table').bootstrapTable('destroy');
	$table = $('#table').bootstrapTable('destroy').bootstrapTable({
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
	if (struct == "10001" || structId == "10001") {
		//菜单项列表
		refreshTableMenu();
		//菜单功能项列表
		refreshTableFuncMenu();
	} else {
//		$table.bootstrapTable('hideColumn', 'value3');
		//其他情况隐藏tab3
		//隐藏图标
		$('#funcMenu').hide();
		//addValue.innerText="结构序列号";
		/*初始化表单数据*/
		radioValue = $("input[name='optionsRadiosinline']:checked").val();
		refreshTable(radioValue);
	}

	//删除数据
	$("#btnDel")
			.click(
					function() {
						var rowdata = $table.bootstrapTable('getSelections');
						if (rowdata.length == 0) {
							BootstrapDialog.alert(constant.LOWEST_CHOICE_ONE);
							return false;
						} else {
							BootstrapDialog
									.show({
										title : "提示",
										message : constant.CONFIRM_DELETE,
										buttons : [
												{
													label : "确定",
													cssClass : "btn-primary",
													icon : "glyphicon glyphicon-ban-circle",//通过bootstrap的样式添加图标按钮
													action : function(dialog) { //给当前按钮添加点击事件
														dialog.close();
														var array = new Array();
														for (var i = 0; i < rowdata.length; i++) {
															array
																	.push(rowdata[i].id);
															var flag = false;
															$
																	.ajax({
																		type : "POST",
																		dataType : "json",
																		url : "../../../dynamic/objBase/isExistChild",
																		async : false,//同步  
																		data : {
																			id : a[i].id
																		},
																		success : function(
																				result) {
																			if (result.status != 200) {
																				BootstrapDialog
																						.show({
																							title : '提示',
																							message : result.message,
																							buttons : [ {
																								label : '确认',
																								action : function(
																										dialog) {
																									dialog
																											.close();
																									window
																											.close();
																									return false;
																								}
																							} ]
																						});
																				flag = true;
																			}

																		},
																	});
															if (flag) {
																return;
															}
														}
														$
																.post(
																		"../../../dynamic/ObjStructure/deletesDicRef",
																		{
																			ids : array
																					.join(",")
																		},
																		function(
																				result) {
																			BootstrapDialog
																					.show({
																						title : '提示',
																						message : result.message,
																						buttons : [ {
																							label : '确认',
																							action : function(
																									dialog) {
																								dialog
																										.close();
																								window
																										.close();
																								return false;
																							}
																						} ]
																					});
																			radioValue = $(
																					"input[name='optionsRadiosinline']:checked")
																					.val();
																			refreshTable(radioValue);

																		},
																		"json");
													}
												},
												{
													label : "取消",
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
				var update = $("#Formbody");
				var typeRefDicId = update.find("input[name='typeRefDicId']")
						.val();
				var objEntityRefId = update
						.find("input[name='objEntityRefId']").val();
				var parentId = update.find("input[name='id']").val();
				var type = update.find("input[name='type']").val();
				var typeChild = update.find("input[name='typeChild']").val();
				var structId = $.fn.geturl("rightIframe", "structId");
				radioValue = $("input[name='optionsRadiosinline']:checked")
						.val();
				var objPid = $.fn.geturl("rightIframe", "objPid");;
				var objs = {
					"struct" : struct,
					"structId" : structId,
					"value22" : value2,
					"typeRefDicId" : typeRefDicId,
					"objEntityRefId" : objEntityRefId,
					"parentId" : parentId,
					"type" : type,
					"typeChild" : typeChild,
					"radioValue" : radioValue,
					"objPid" : objPid
				};
				var win = SF.showModalDlg("data_type_edit.html", "add", objs,
						800, 400, function(e) {
							if (e)
								refreshTable(radioValue);
						});
			});
	//打开已有项
	$("#btnAdd3").click(
			function() {
				var update = $("#Formbody");
				var typeId = update.find("input[name='typeChild']").val();
				var objEntityRefId = update
						.find("input[name='objEntityRefId']").val();
				var structId = $.fn.geturl("rightIframe", "structId");
				radioValue = $("input[name='optionsRadiosinline']:checked")
				.val();
				var objEntityRefPid = $("input[name='optionsRadiosinline']:checked").attr("objEntityRefPid")
				var parentId = $.fn.geturl("rightIframe", "objPid");
				var id = update.find("input[name='id']").val();
				var objs = {
					"typeId" : typeId,
					"objEntityRefId" : objEntityRefPid,
					"parentId" : parentId,
					"id" : id,
					"structId" : structId,
					"radioValue" : radioValue
				};
				var win = SF.showModalDlg("data_table_choice.html", "add",
						objs, 800, 400, function(e) {
					if (e) {
						radioValue = $("input[name='optionsRadiosinline']:checked")
								.val();
						refreshTable(radioValue);
					}
						});
			});
});

function toModify(e, value, row, index) {
	var structId = $.fn.geturl("rightIframe", "structId");
	$.post("../../../dynamic/ObjStructure/dic_ref_detail", {
		id : row.id,
		structId : structId
	}, function(result) {
		result.value22 = value2;
		var structId = $.fn.geturl("rightIframe", "structId");
		result.structId = structId;
		result.struct = struct;
		var win = SF.showModalDlg("data_type_edit.html?id=" + row.id + "&type="
				+ row.type, "update", result, 800, 400, function(e) {
			if (e) {
				radioValue = $("input[name='optionsRadiosinline']:checked")
						.val();
				refreshTable(radioValue);
			}
		});

	});
}
//重新查询、重新排序、切换分页事件
function refreshTable(radioValue) {
	var options = $table.bootstrapTable('getOptions');
	var number = options.pageNumber;
	var size = options.pageSize;
	var sortName = options.sortName;
	var sortOrder = options.sortOrder;
	var parentId = $.fn.geturl("rightIframe", "parentId");
	var structId = $.fn.geturl("rightIframe", "structId");
	var keyId = $.fn.geturl("rightIframe", "keyId");
	var code = $("#SearchPanel").find("input[name='code']").val();
	var name = $("#SearchPanel").find("input[name='name']").val();
	var objId = "";
	$.ajax({
		type : "POST",
		async : true,
		url : "../../../dynamic/ObjStructure/fetchDicListFormaintain",
		data : {
			'page' : number,
			'limit' : size,
			'code' : code,
			'name' : name,
			"structId" : structId,
			"parentId" : parentId,
			"type" : radioValue

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
	//	    	var myColumns = $table.bootstrapTable('getOptions').columns[0];
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
	myColumns.push(myColumn1);
	myColumns.push(myColumn2);
	myColumns.push(myColumn3);
	myColumns.push(myColumn4);
	myColumns.push(myColumn5);
	myColumns.push(myColumn6);
	$.ajax({
		url : '../../../dynamic/objBase/fetchObjAttributeList?objId='
				+ radioValue,
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
					}
				},
				edit : false
			});
	console.log(myColumns);
	$table.bootstrapTable("refreshOptions", {
		columns : myColumns
	});
	myColumns.length = 0;
}

function refreshTableMenu() {
	var options = $table.bootstrapTable('getOptions');
	var number = options.pageNumber;
	var size = options.pageSize;
	var sortName = options.sortName;
	var sortOrder = options.sortOrder;
	var parentId = $.fn.geturl("rightIframe", "parentId");
	var structId = $.fn.geturl("rightIframe", "structId");
	var objs = SF.getSearchParams();
	$.ajax({
		type : "POST",
		async : true,
		url : "../../../dynamic/ObjStructure/fetchFuncDicListFormaintain",
		data : {
			'page' : number,
			'limit' : size,
			'code' : objs.code,
			'name' : objs.name,
			"structId" : structId,
			"parentId" : parentId,
			"objEntityRefId" : "10000"

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
	//	var myColumns = $table.bootstrapTable('getOptions').columns[0];
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
	myColumns.push(myColumn1);
	myColumns.push(myColumn2);
	myColumns.push(myColumn3);
	myColumns.push(myColumn4);
	myColumns.push(myColumn5);
	myColumns.push(myColumn6);
	var id = $.fn.geturl("rightIframe", "id");
	$.ajax({
		url : '../../../dynamic/objBase/fetchObjAttributeList?objId=10000',
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
					}
				},
				edit : false
			});
	$table.bootstrapTable("refreshOptions", {
		columns : myColumns
	});
	myColumns.length = 0;
}