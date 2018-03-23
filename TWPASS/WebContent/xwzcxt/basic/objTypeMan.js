var orgDialog = null;
var currentTypeId = 1;
var upTypeCode;
// 刷新条件
var currentCondition = null;
var reqDialog, tree, modifyDialog;
function onBeforeCompile() {
	var height = fastDev(window).height();
	fastDev('#centerPanel')
			.css('width', (fastDev(window).width() - 211) + 'px');
	fastDev('#orgtree').attr('height', (height - 35) + 'px');
	fastDev('#typedatagrid').attr('height', (height - 66) + 'px');
}
function treeReady() {
	tree = this;

}

function onNodeClick(event, id, value) {
	currentTypeId = id;
	for ( var i = 0; i < tree.dataset.records.length; i++) {
		if (tree.dataset.records[i].id == id) {
			upTypeCode = tree.dataset.records[i].c_objtype_code;
		}

	}
	refreshDatagrid();
}
function refreshTree() {
	tree.reLoad();
}

function doAddType() {

	var url = "addType.html?Pid=" + currentTypeId + "&upTypeCode=" + upTypeCode;

	reqDialog = fastDev.create("Dialog", {
		width : "900px",
		height : "240px",
		top : "100px",
		left : 50,
		title : "新增对象类别",
		src : url
	});
}

function closeDialog() {

}
// 操作列渲染函数
function operRender() {
	var oper = [];

	oper.push('<a href="#" name="modify">修改</a>&nbsp;');
	oper.push('<a href="#" name="del">删除</a>');

	return oper.join('');
}
function modifyById(id) {

	var url = "modifyType.html?id=" + id;

	modifyDialog = fastDev.create("Dialog", {
		width : "900px",
		height : "240px",
		top : "100px",
		left : 50,
		title : "修改对象类别信息",
		src : url
	});
}

/**
 * 批量删除信息
 */
function doBatchDelete() {
	var datagrid = fastDev.getInstance('typedatagrid');
	var items = datagrid.getValue();
	if(items.length <=0 ) {
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	} else {
		fastDev.confirm("确认是否删除选择的" + items.length + "条记录？", "信息提示", function(result){
			if(result){
				var ids = [];
				for(var i = 0; i < items.length; i++) {
					ids.push(items[i].c_objtype_id);
				}
				doDeleteByIds(ids.join(","));
			}
		});
	}
}
var delectAction = "ObjType_deleteNodesByIds.action";
function doDeleteByIds(id) {
	var proxy = fastDev.create('Proxy', {
		action : delectAction
	});
	proxy.save({ids:id}, function(result){
		fastDev.alert(result.msg, '信息提示', result.status, function(){
			if(result.status == 'ok') {
				refreshDatagrid();
			}
		});
	});
}
function deleteById(nodeid) {
	fastDev.confirm("是否确认删除当前选择记录", "确认", function(response) {
		if (response) {
			fastDev.get(delectAction, {
				data : {
					"ids" : nodeid
				},
				complete : function(rsp) {
					var type = rsp.status;
					type = type === "bad" ? "error" : type;
					fastDev.alert(rsp.msg, "提示信息", type);
					if (type !== "bad") {
						refreshDatagrid();
						refreshTree();
					}
				}
			});
		}
	});

}
/**
 * 过滤组织信息
 */
function doSearchOrg() {
	var orgForm = fastDev.getInstance('orgform');
	currentCondition = orgForm.getItems();
	refreshDatagrid();
}


/**
 * 重置表单数据
 */
function doResetOrg() {
	fastDev.getInstance('orgform').cleanData();
	currentCondition = null;
}

// 操作列单击事件响应函数
function onRowClick(event, rowIndex, rowData) {
	var oper = event.target.name, flag;
	switch (oper) {
	case "modify":
		modifyById(rowData.c_objtype_id);
		return;
	case "del":
		deleteById(rowData.c_objtype_id);
		return;

	default:
		return;
	}
}

function onRowDblClick(event, rowindex, data) {
	doViewOrg(data);
}

/**
 * 查看组织信息
 * 
 * @param data
 */
function doViewOrg(data) {
	createWindow({
		src : "orgInfo.html?orgid=" + data.orgId + "&orgname=" + data.orgName,
		title : "查看组织",
		height : 283,
		width : 559
	});
}

 
 

/**
 * 刷新datagrid
 * 
 * @param o
 */
function refreshDatagrid(o) {
	o = fastDev.apply(o || {}, currentCondition || {});
	o['c_up_objtype_id'] = currentTypeId;
	fastDev.getInstance('typedatagrid').refreshData(o);
}

/**
 * 刷新树
 */
function refreshTree() {
	fastDev.getInstance('orgtree').reLoad();
}

function onAfterInitRender() {
	showResourceModes('org', fastDev.getInstance('typedatagrid'));
}