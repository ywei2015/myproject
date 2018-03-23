var request = fastDev.Browser.getRequest();
var cActnodeId = request['cActnodeId'];
var cPositionID = null;

var isFirstInit = false;
var initStandardDetail = true;

var submitButton = null;
var loadingWindow = null;

function onTabClick(e, title) {
	if ('标准详情' == title) {
		onAClick();
	}
}

function onAClick() {
	fastDev.getInstance("templetTab").setActiveTabByTitle('标准详情');
	if (initStandardDetail) {
		var cActnodeId = fastDev.getInstance('cActnodeId').getValue();
		if (cActnodeId) {
			var tActnodeDetail = document.getElementById('tActnodeDetail');
			tActnodeDetail.innerHTML = '';
			var iframe = document.createElement('iframe');
			var url = 'actionStandardAdd.html?cModifyType=0&cActnodeId=' + cActnodeId;
			iframe.style.border = '0';
			iframe.style.width = '800px';
			iframe.style.height = '430px';
			iframe.src = url;
			tActnodeDetail.appendChild(iframe);
			initStandardDetail = false;
		} else {
			window.alert("请设置“岗位活动”");
		}
	}
}

function beforeReady() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "detail",
		text : "加载中..."
	});

	this.setOptions({
		action : "templet/generateRandomTaskAction.action"
	});
	fastDev.post("tsdactnode/getActNodeByActNodeIDAction.action?cActnodeId=" +
		cActnodeId, {
		success : function(data) {
			fastDev("#cActnodeId").prop("value", data.cActnodeId);
			fastDev("#cActnodeName").prop("value", data.cActnodeName);
			fastDev("#cActnodetype").prop("value", data.cActnodetype);
			fastDev("#cIskeyctrl").prop("value", data.cIskeyctrl);
			fastDev("#cPdca").prop("value", data.cPdca);
			fastDev("#cIssequence").prop("value", data.cIssequence);
			fastDev.getInstance("cTaskName").setValue(data.cActnodeName);
			fastDev.getInstance("cIsscan").setValue(data.cIsscan);
			fastDev.getInstance("cFrequency").setValue(data.cFrequency);
			onActnodeChange();
			onchange();
			var checkTime = null, reviewTime = null;
			if ("每月" == data.cFrequency) {
				checkTime = "下月20日前";
				reviewTime = "下月28日前";
			} else if ("每周" == data.cFrequency) {
				checkTime = "下月15日前";
				reviewTime = "下月20日前";
			} else {
				checkTime = "下月10日前";
				reviewTime = "下月15日前";
			}
			fastDev.getInstance('cCheckTime').setValue(checkTime);
			fastDev.getInstance('cTaskcheckTime').setValue(checkTime);
			fastDev.getInstance('cReviewTime').setValue(reviewTime);
			fastDev.getInstance('cTaskreviewTime').setValue(reviewTime);
			loadingWindow.close();
		}
	});
}

function doReset() {
	fastDev.getInstance('checkForm').cleanData();
	condition = null;
}

function showWaitingDialog(button) {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "detail",
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

function onActnodeChange() {
	//根据岗位活动类型设置相关标题的文字信息
	initStandardDetail = true;
	document.getElementById('cActnodeNameLink').innerHTML = fastDev.getInstance('cActnodeName').getValue();;
	var cActnodetype = fastDev.getInstance('cActnodetype').getValue();
	if ("1" == cActnodetype) {
		document.getElementById("cPNameExecTitle").innerHTML = "<font color='red' >*</font>执行岗位：";
		document.getElementById("cExecAreaidTitle").innerHTML = "<font color='red' >*</font>作业区域：";
		document.getElementById("cPidReviewTitle").innerHTML = "评价岗位：";
		document.getElementById("cTaskbeginTridTitle").innerHTML = "<font color='red' >*</font>执行开始时间：";
		document.getElementById("cTaskfinishTridTitle").innerHTML = "<font color='red' >*</font>执行完成时间：";
		document.getElementById("cTaskreviewTimeTitle").innerHTML = "评价完成时间：";
	} else if ("2" == cActnodetype) {
		document.getElementById("cPNameExecTitle").innerHTML = "<font color='red' >*</font>管理岗位：";
		document.getElementById("cExecAreaidTitle").innerHTML = "<font color='red' >*</font>工作区域：";
		document.getElementById("cPidReviewTitle").innerHTML = "监督岗位：";
		document.getElementById("cTaskbeginTridTitle").innerHTML = "<font color='red' >*</font>完工开始时间：";
		document.getElementById("cTaskfinishTridTitle").innerHTML = "<font color='red' >*</font>完工完成时间：";
		document.getElementById("cTaskreviewTimeTitle").innerHTML = "监督完成时间：";
	}

	//根据是否关键管控显示或隐藏验证岗位和评价岗位
	var cIskeyctrl = fastDev.getInstance('cIskeyctrl').getValue();
	if ("1" == cIskeyctrl) {
		document.getElementById("cPidCheckTitle").style.display = "table-cell";
		document.getElementById("cPidCheckTd").style.display = "table-cell";
		document.getElementById("cPButtonCheckTd").style.display = "table-cell";
		document.getElementById("cTaskcheckTimeTitle").style.display = "table-cell";
		document.getElementById("cTaskcheckTimeTd").style.display = "table-cell";
	} else {
		document.getElementById("cPidCheckTitle").style.display = "none";
		document.getElementById("cPidCheckTd").style.display = "none";
		document.getElementById("cPButtonCheckTd").style.display = "none";
		document.getElementById("cTaskcheckTimeTitle").style.display = "none";
		document.getElementById("cTaskcheckTimeTd").style.display = "none";
	}
}

function onchange() {
	var cIsscan = fastDev.getInstance('cIsscan').getValue();
	if (!isFirstInit) {
		fastDev.getInstance("cJobObjectsName").clean();
		fastDev.getInstance("cJobObjects").clean();
	} else {
		isFirstInit = false;
	}
	if (0 == cIsscan) {
		document.getElementById("cJobObjectsTitle").style.display = "none";
		document.getElementById("cJobObjectsTd").style.display = "none";
		document.getElementById("cJobObjectsButtonTd").style.display = "none";
	} else if (1 == cIsscan) {
		document.getElementById("cJobObjectsTitle").style.display = "table-cell";
		document.getElementById("cJobObjectsTd").style.display = "table-cell";
		document.getElementById("cJobObjectsButtonTd").style.display = "table-cell";
	}
}

function onSelectJobObjects() {
	fastDev.confirm("是否需要修改当前扫码对象？", "信息提示", function(result) {
		if (result) {
			doSelectJobObjects();
		}
	});
}

function doSelectJobObjects() {
	if (cPositionID) {
		fastDev.create('Window', {
			width : 800,
			height : 500,
			inside : false,
			showMaxBtn : false,
			title : "选择扫码对象",
			allowResize : false,
			src : '../xwzcxt/task/jobObjects/jobObjects.html?type=set&cPositionID=' + cPositionID,
			buttons : [ {
				id : 'ok',
				text : '确定',
				align : 'center',
				iconCls : 'icon-save',
				onclick : function(event, win, childWin, fast) {
					var list = fast.getInstance("jobObjectsInfo").getValue();
					if (list.length <= 0) {
						fastDev.alert("请选择至少一条记录进行操作", "信息提示", "warning");
						return;
					}
					var cJobObjects = "", cJobObjectsName = "";
					for (var i = 0; i < list.length; i++) {
						cJobObjects += list[i].cBasedataId + ",";
						cJobObjectsName += list[i].c_basedata_name + ",";
					}
					cJobObjects = cJobObjects.substring(0, cJobObjects.length - 1);
					cJobObjectsName = cJobObjectsName.substring(0, cJobObjectsName.length - 1);
					setJobObjects(cJobObjects, cJobObjectsName);
					win.close();
				}
			} ]
		});
	} else {
		var cActnodetype = fastDev.getInstance('cActnodetype').getValue();
		if ('1' == cActnodetype) {
			fastDev.alert("请先选择“执行岗位”", "信息提示", "warning");
		} else if ('2' == cActnodetype) {
			fastDev.alert("请先选择“管理岗位”", "信息提示", "warning");
		}
	}
}

function setJobObjects(cJobObjects, cJobObjectsName) {
	fastDev.getInstance("cJobObjects").setValue(cJobObjects);
	fastDev.getInstance("cJobObjectsName").setValue(cJobObjectsName);
}

function onSelectArea() {
	fastDev.confirm("是否需要修改当前区域？", "信息提示", function(result) {
		if (result) {
			doSelectArea();
		}
	});
}

function doSelectArea() {
	var cAreaid = fastDev.getInstance("cExecAreaid").getValue();

	fastDev.create('Window', {
		width : 300,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : "选择区域",
		allowResize : false,
		src : '../xwzcxt/task/task_templet/templetSelectArea.html?cAreaid=' + cAreaid,
		buttons : [ {
			id : 'ok',
			text : '确定',
			align : 'center',
			iconCls : 'icon-save',
			onclick : function(event, win, childWin, fast) {
				var cArea = fast.getInstance("cArea");
				var cAreaid = cArea.getCurrentId();
				var pids = cArea.getChkedIds("onlyLeafValue");
				 console.info(pids);
				if ("" == pids) {
					window.alert("请选择区域！");
				} 
				else if (pids) {
					setArea(pids, cArea.getValsByids(cArea.getChkedIds("allchkValue")));
					//setArea(pids, cArea.getValsByids(cArea.getChkedIds("onlyLeafValue")));
					win.close();
				} else {
					var cAreaName = cArea.getCurrentTxt();
					console.info(cAreaName);
					if ("1000100000001" != cAreaid) {
						var id = cArea.getParentid(cAreaid);
						while ("1000100000001" != id) {
							cAreaName = cArea.getValByid(id) + cAreaName;
							console.info(cAreaName);
							id = cArea.getParentid(id);
						}
					}
					setArea(cAreaid, cAreaName);
					win.close();
				}
			}
		} ]
	});
}

function setArea(cAreaid, cAreaName) {
	fastDev.getInstance("cExecAreaid").setValue(cAreaid);
	fastDev.getInstance("cExecAreaName").setValue(cAreaName);
}

function onSelectPosition(e) {
	var element = e.srcElement || e.target;
	fastDev.confirm("是否需要修改当前岗位？", "信息提示", function(result) {
		if (result) {
			doSelectPosition(element.id);
		}
	});
}

/**
 * 对应执行岗位修改时的处理
 */
function doButton0() {
	doSelectPosition("cPNameExec");
}

/**
 * 对应验证岗位修改时的处理
 */
function doButton1() {
	doSelectPosition("cPNameCheck");
}

/**
 * 对应评价岗位修改时的处理
 */
function doButton2() {
	doSelectPosition("cPNameReview");
}

/**
 * 对应默认反馈岗位修改时的处理
 */
function doButton3() {
	doSelectPosition("cPNameFeedback1");
}

/**
 * 对应其他默认反馈岗位修改时的处理
 */
function doButton4() {
	doSelectPosition("cPNameFeedback2");
}

/**
 * 对应异常反馈岗位修改时的处理
 */
function doButton5() {
	doSelectPosition("cPNameErrFeedback1");
}

/**
 * 对应其他异常反馈岗位修改时的处理
 */
function doButton6() {
	doSelectPosition("cPNameErrFeedback2");
}

function doSelectPosition(name) {
	var id = name.substring(0, name.indexOf("Name")) + "id" + name.substring(name.indexOf("Name") + 4);
	var cPid = fastDev.getInstance(id).getValue();

	fastDev.create('Window', {
		width : 300,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : "选择岗位",
		allowResize : false,
		src : '../xwzcxt/task/task_templet/templetSelectPosition.html?cPid=U-' + cPid,
		buttons : [ {
			id : 'ok',
			text : '确定',
			align : 'center',
			iconCls : 'icon-save',
			onclick : function(event, win, childWin, fast) {
				var cPosition = fast.getInstance("cPosition");
				var cPid = cPosition.getCurrentId();
				var cPidName = cPosition.getCurrentTxt();
				if (("" == cPid) || (0 > cPid.indexOf("U-"))) {
					window.alert("请选择岗位！");
				} else {
					if ("cPNameExec" == name) {
						cPositionID = cPosition.getParentid(cPid);
					}
					setPosition(name, cPid.substring(cPid.indexOf("U-") + 2), cPosition.getValByid(cPosition.getParentid(cPid)) + "-" + cPidName);
					win.close();
				}
			}
		}, {
			id : 'clear',
			text : '清空',
			align : 'center',
			iconCls : 'icon-reset',
			onclick : function(event, win) {
				setPosition(name, "", "");
				win.close();
			}
		} ]
	});
}

function setPosition(name, cPid, cPidName) {
	var id = name.substring(0, name.indexOf("Name")) + "id" + name.substring(name.indexOf("Name") + 4);
	fastDev.getInstance(id).setValue(cPid);
	fastDev.getInstance(name).setValue(cPidName);
}