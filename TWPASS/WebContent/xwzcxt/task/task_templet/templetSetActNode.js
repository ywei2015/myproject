var request = fastDev.Browser.getRequest();
var cPositionID = request['cPositionID'];
var cActnodeId = "";
var cActnodetype = "1";
var condition = null;

function onBeforeReady() {
	this.setOptions({
		initSource : "tsdactnode/getAllActNodesByConditionsAction.action?actnode.c_position_id=" + cPositionID
	});
}

function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body - 130);
}

function onBeforeDetailGridReady() {
	this.setOptions({
		initSource : "tsdactnode/getActNodeItemByActNodeIDAction.action?cActnodeId=" + cActnodeId
	});
}

function onRowClick(event, rowindex, rowData) {
	cActnodeId = rowData.cActnodeId;
}

function refreshDatagrid() {
	var o = condition || {};

	o['actnode.c_actnodetype'] = cActnodetype;

	fastDev.getInstance('grid_actnode' + cActnodetype).addInitReqParam(o);
	fastDev.getInstance('grid_actnode' + cActnodetype).refreshData();
}

function doSearch() {
	condition = fastDev.getInstance('checkForm').getItems();
	refreshDatagrid();
}

function doReset() {
	fastDev.getInstance('checkForm').cleanData();
	condition = null;
}

function getactnodeid() {
	var list = fastDev.getInstance("grid_actnode" + cActnodetype).getValue();
	if (list.length <= 0) {
		fastDev.alert("请选择一条记录进行操作", "信息提示", "warning");
		return;
	}
	window.alert("设置岗位活动 " + list[0].cActnodeName + " 成功");
	fastDev.Ui.Window.parent.getactnodeid(list[0]);
	fastDev.Ui.Window.parent.closeDialog();
}

function onTabClick(Object, name) {
	if ("作业类" == name) {
		cActnodetype = 1;
		document.getElementById("c_area_name_title").innerHTML = "作业区域：";
		document.getElementById("c_position_name_review_title").innerHTML = "评价岗位：";
		document.getElementById("c_manageattr_title").style.display = "none";
		document.getElementById("c_manageattr").style.display = "none";
		document.getElementById("actnodeSearchFormButton").colSpan = "4";
	} else {
		cActnodetype = 2;
		document.getElementById("c_area_name_title").innerHTML = "工作区域：";
		document.getElementById("c_position_name_review_title").innerHTML = "监督岗位：";
		document.getElementById("c_manageattr_title").style.display = "table-cell";
		document.getElementById("c_manageattr").style.display = "table-cell";
		document.getElementById("actnodeSearchFormButton").colSpan = "2";
	}
	refreshDatagrid();
}