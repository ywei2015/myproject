var request = fastDev.Browser.getRequest();
//通过action参数进行信息读取，以确认操作的对象为子项(1)、子项类别(2)、基础项(3)
var item = request['item'];
var currentTypeId = -1;
//刷新条件
var condition=null;
var checkDialog,tree;

function treeReady() {
	tree = this;

}
function onNodeClick(event, id, value) {
	currentTypeId = id;
	refreshDatagrid();
}
/**
 * 刷新datagrid
 * @param o
 */
function refreshDatagrid() {
	var o = fastDev.apply(o || {}, condition || {});
	o['tpostactnode.c_ACTION_ID'] = currentTypeId;
	fastDev.getInstance('activitydatagrid').refreshData(o);
}
function refreshTree() {
	tree.reLoad();
}

function actRenderer(value)
{
	switch (value) {
	case "0":
		return "否";
	case "1":
		return "是";
	}
}

 

/**
 * 关闭对话框
 */
function closeDialog() {
	if(checkDialog) {
		checkDialog.close();
		checkDialog = null;
	}
}	
function onBeforeReady(){	 
};
function doAddType() {

	var url = "addEndNode.html?Pid=" + currentTypeId;

	reqDialog = fastDev.create("Dialog", {
		width : "900px",
		height : "301px",
		top : "100px",
		showCollapseBtn : false,
		showMaxBtn : false,
		src : url
	});
}

//操作列渲染函数
function operRender() {
	var oper = [];

	oper.push('<a href="#" name="modify">修改</a>&nbsp;');
	oper.push('<a href="#" name="del">删除</a>');

	return oper.join('');
}
function modifyById(id) {

	var url = "modifyEndNode.html?id=" + id;

	modifyDialog = fastDev.create("Dialog", {
		width : "900px",
		height : "301px",
		top : "100px",
		showCollapseBtn : false,
		showMaxBtn : false,
		src : url
	});
}

function onRowClick(event,rowindex,rowData) {		
	var oper = event.target.name;
	switch (oper) {
	case "modify":
		modifyById(rowData.c_actnode_id);
		return;
	case "del":
		deleteById(rowData.c_actnode_id);
		return;

	default:
		return;
	}
}
 

/**
 * 批量删除信息
 */
function doBatchDelete() {
	var datagrid = fastDev.getInstance('activitydatagrid');
	var items = datagrid.getValue();
	if(items.length <=0 ) {
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	} else {
		fastDev.confirm("确认是否删除选择的" + items.length + "条记录？", "信息提示", function(result){
			if(result){
				var ids = [];
				for(var i = 0; i < items.length; i++) {
					ids.push(items[i].c_actnode_id);
				}
				doDeleteByIds(ids.join(","));
			}
		});
	}
}
var delectAction = "actNode_deleteNodesByIds.action";
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
					}
				}
			})
		}
	});

}
/**
 * 过滤信息
 */
function doSearch() {
	var fm = fastDev.getInstance('checkform');
	condition = fm.getItems();
	
	refreshDatagrid();
}
/**
 * 重置表单数据
 */
function doReset() {
	fastDev.getInstance('checkform').cleanData();
}
