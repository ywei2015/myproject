var request = fastDev.Browser.getRequest();
var cModifyType = request['cModifyType'];
var cFiledetailId = request['cFiledetailId'];

var submitButton = null;
var loadingWindow = null;

function beforeReady() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "checkForm",
		text : "加载中..."
	});

	var action = "";
	var dataSource = "";
	if ("null" != cFiledetailId) {
		action = "standardfile/addTSdStandardfileAction.action";
		dataSource = "standardfile/getTSdStandardfileDetailByIdAction.action?cFiledetailId=" + cFiledetailId;
	} else {
		action = "standardfile/addTSdStandardfileAction.action";
	}
	this.setOptions({
		action : action,
		dataSource: dataSource
	});
}

function afterDataRender() {
	//编辑模式为查看时不可编辑数据
	if ("0" == cModifyType) {
		fastDev.getInstance("cSortId").disable();
		fastDev.getInstance("cSfileVersion").disable();
		fastDev.getInstance("cSfileName").disable();
		fastDev.getInstance("cFwQcbmName").disable();
		fastDev.getInstance("cSystag").disable();
		fastDev.getInstance("cStatusTime").disable();
		fastDev.getInstance("cStatus").disable();
		fastDev.getInstance("cFileUrl").disable();
	}
	fastDev.getInstance("cModifyType").setValue(cModifyType);
	loadingWindow.close();
}

function showWaitingDialog(button) {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "checkForm",
		text : "保存中..."
	});
	submitButton = button;
}

function submitSuccess(result) {
	fastDev.alert(result.msg, '信息提示', result.status, function() {
		if (result.status == 'ok') {
			fastDev.Ui.Window.parent.refreshDatagrid();
			fastDev.Ui.Window.parent.closeDialog();
		}
	});
}

function onSelectDepartment(e) {
	var element = e.srcElement || e.target;
	fastDev.confirm("是否需要修改当前起草部门？", "信息提示", function(result) {
		if (result) {
			doSelectDepartment(element.id);
		}
	});
}

function doSelectDepartment(name) {
	var id = name.substring(0, name.indexOf("Name"));
	var cPid = fastDev.getInstance(id).getValue();

	fastDev.create('Window', {
		width : 300,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : "选择部门组织",
		allowResize : false,
		src : '../xwzcxt/task/task_templet/templetSelectPositionForAdd.html?orgtype=4&cPid=' + cPid,
		buttons : [ {
			id : 'ok',
			text : '确定',
			align : 'center',
			iconCls : 'icon-save',
			onclick : function(event, win, childWin, fast) {
				var cPosition = fast.getInstance("cPosition");
				var pid = cPosition.getValue();
				if (pid) {
					setDepartment(name, pid, cPosition.getValByid(pid));
					win.close();
				} else {
					window.alert("请选择部门！");
				}
			}
		}, {
			id : 'clear',
			text : '清空',
			align : 'center',
			iconCls : 'icon-reset',
			onclick : function(event, win) {
				setDepartment(name, "", "");
				win.close();
			}
		} ]
	});
}

function setDepartment(name, cPid, cPidName) {
	var id = name.substring(0, name.indexOf("Name"));
	fastDev.getInstance(id).setValue(cPid);
	fastDev.getInstance(name).setValue(cPidName);
}