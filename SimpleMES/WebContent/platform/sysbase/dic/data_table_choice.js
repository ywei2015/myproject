var $table;
var data;
var mode;
var type;

$(function() {
	//获取传递过来的参数
	data = window.dialogArguments.data;
	mode = window.dialogArguments.mode;
	if(data.radioValue == "" || data.radioValue == null || data.radioValue == undefined){
		type = data.typeId;
	}else{
		type = data.radioValue;
	}
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
		minimumCountColumns : 2, //最少允许的列数
		showToggle : true, //是否显示详细视图和列表视图的切换按钮
		toolbar : '#toolbar', //工具按钮用哪个容器
		clickToSelect : false,
		smartDisplay : true,
		cache : false,
		singleSelect: true,
		columns : [ {
			field : 'checkStatus',
			checkbox : true,
			edit : false
		}, //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
//		{field: 'checkStatus',checkbox: true,edit:false},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
		{
			field : 'id',
			visible : false,
			edit : false
		}, {
			field : 'code',
			title : '编码',
			sortable : true,
			//editable: true,
			edit : false
		}, {
			field : 'name',
			title : '名称',
			editable : {
				type : 'text',
				validate : function(value) {
					if ($.trim(value) == '') {
						return '名称不能为空!';
					}
				}
			}
		}, {
			field : 'fullName',
			title : '全名称'
		}

		],
		onPageChange : function(number, size) {
			refreshTable();
			return false;
		},
		onSort : function(name, order) {
			refreshTable();
			return false;
		}
	});

	refreshTable();
	$("#btnAdd").click(
			function() {
				var rowdata = $table.bootstrapTable('getSelections');
				if (rowdata.length == 0) {
					 BootstrapDialog.alert({
				            type: BootstrapDialog.TYPE_SUCCESS,
				            title: '提示',
				            message: "请至少选中一行！",
				            closeable: true,
				            buttonLabel: "确定"
				        });
				} else {
					if (confirm("确定保存！") && rowdata.length >= 1) {

						var prefJson = "[";
						var suffJson = "]";
						var objJson = "";
						var fullJson = "";
						if (rowdata.length > 0) {
							for (var i = 0; i < rowdata.length; i++) {
								objJson += "{\"childId\":\"" + rowdata[i].id
										+ "\",\"typeId\":\"" + type
										+ "\",\"objEntityRefId\":\""
										+ data.objEntityRefId + "\",\"parentId\":\""
										+ data.parentId + "\"},";
							}
							objJson = objJson.substring(0, objJson.length - 1);
							fullJson = prefJson + objJson + suffJson;
							var result = forceAjaxSynchro(
									"../../../dynamic/ObjStructure/saveExistTypeRefDic?childId="
											+ rowdata[0].id + "&typeId="
											+ type + "&objEntityRefId="
											+ data.objEntityRefId + "&parentId="
											+ data.id, null, function() {

									});
							if (result.responseJSON.status == 200) {
								//BootstrapDialog.alert(result.responseJSON.message);
								window.returnValue = 'ok';
								window.close();
							}

						}
					}
				}
			});

	//向上查询匹配的父节点，查找到删除 ，存在bug， 当不是点击保存时，第二次就会报错。

});
//重新查询、重新排序、切换分页事件
function refreshTable() {
	var options = $table.bootstrapTable('getOptions');
	var number = options.pageNumber;
	var size = options.pageSize;
	var sortName = options.sortName;
	var sortOrder = options.sortOrder;
	var objs = SF.getSearchParams();
	$.post("../../../dynamic/ObjStructure/fetchDicForAddMaintain", {
		'page' : number,
		'limit' : size,
		'typeId' : type,
		'struId' : data.structId,
		'parentId' : data.parentId
	}, function(res) {
		var pagedata = {};
		pagedata.rows = res.rows;
		pagedata.total = res.total;
		$table.bootstrapTable('load', pagedata);
		return false;
	});
}