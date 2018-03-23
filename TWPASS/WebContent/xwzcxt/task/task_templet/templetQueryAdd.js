var request = fastDev.Browser.getRequest();
var cModifyType = request['cModifyType'];
var cTsId = request['cTsId'];

var loadingWindow = null;

function beforeReady() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "checkForm",
		text : "加载中..."
	});

	var action = "";
	var dataSource = "";
	if ("null" != cTsId) {
		action = "templet/addTaskPlanAction.action";
		dataSource = "templet/planSeriesDetailAction.action?cTsId=" + cTsId;
	} else {
		action = "templet/addTaskPlanAction.action";
	}
	this.setOptions({
		action : action,
		dataSource: dataSource
	});
}

function afterDataRender() {
	fastDev.get("templet/taskTempletDetailAction.action?cTempletId=" + fastDev("#cTasktempletId").prop("value"), {
		success : function (data) {
			//根据是否关键管控显示或隐藏验证岗位和评价岗位
			if ("0" == data.cIskeyctrl) {
				fastDev("#cCheckTitle").hide();
				fastDev("#cCheckSelect").hide();
				fastDev("#cCheckTimeTitle").hide();
				fastDev("#cCheckTimeSelect").hide();
			} else {
				fastDev("#cCheckTitle").show();
				fastDev("#cCheckSelect").show();
				fastDev("#cCheckTimeTitle").show();
				fastDev("#cCheckTimeSelect").show();
			}
		}
	});

	//编辑模式为查看时不可编辑数据
	if ('0' == cModifyType) {
		fastDev.getInstance("cTasktempletName").disable();
		fastDev.getInstance("cTaskName").disable();
		fastDev.getInstance("cTaskTypeName").disable();
		fastDev.getInstance("cStateName").disable();
		fastDev.getInstance("cDecodeTime").disable();
		fastDev.getInstance("cPlandownTime").disable();
		fastDev.getInstance("cExecUserName").disable();
		fastDev.getInstance("cDoneTime").disable();
		fastDev.getInstance("cStartTime").disable();
		fastDev.getInstance("cEndTime").disable();
		fastDev.getInstance("cChkUserName").disable();
		fastDev.getInstance("cEvaluateUserName").disable();
		fastDev.getInstance("cChkHours").disable();
		fastDev.getInstance("cEvaluateTime").disable();
		fastDev.getInstance("cOkfbUserName").disable();
		fastDev.getInstance("cOkfbUlistName").disable();
		fastDev.getInstance("cNgfbUserName").disable();
		fastDev.getInstance("cNgfbUlistName").disable();
	}

	loadingWindow.close();
}