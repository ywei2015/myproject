var request = fastDev.Browser.getRequest();
var cModifyType = request['cModifyType'];
var cPositionName = request['cPositionName'];
var cPositionID = request['cPositionID'];
var cDepartmentName = request['cDepartmentName'];
console.info(cDepartmentName);
var cDepartment = request['cDepartment'];
var cTempletId = request['cTempletId'];
var cOrgId = request['cOrgId'];

var isFirstInit = false;
var initStandardDetail = true;
var fileSum = 0;

var actnodewindow = null;
var submitButton = null;
var loadingWindow = null;

var image_audio = '../../../images/audio.png';
var image_image = '../../../images/image.png';
var image_video = '../../../images/video.png';

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
			var url = '../../standardlibrary/actionStandardAdd.html?cModifyType=0&cActnodeId=' + cActnodeId;
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

	var action = "";
	var dataSource = "";
	if ("null" != cTempletId) {
		action = "templet/addTaskTempletAction.action";
		dataSource = "templet/taskTempletDetailAction.action?cTempletId=" + cTempletId;
	} else {
		action = "templet/addTaskTempletAction.action";
	}
	this.setOptions({
		action : action,
		dataSource: dataSource
	});
}

function afterDataRender() {
	if ("null" != cTempletId) {
		isFirstInit = true;

		var checkTime = fastDev.getInstance('cTaskcheckTime').getValue();
		var reviewTime = fastDev.getInstance('cTaskreviewTime').getValue();
		if (('下月10日前' == checkTime) || ('下月15日前' == checkTime) || ('下月20日前' == checkTime)) {
			fastDev.getInstance('cCheckTime').setValue(checkTime);
		}
		if (('下月15日前' == reviewTime) || ('下月20日前' == reviewTime) || ('下月28日前' == reviewTime)) {
			fastDev.getInstance('cReviewTime').setValue(reviewTime);
		}

		onActnodeChange();
		onchange();

		if (fastDev("#cTempletMediaFileType").prop("value")) {
			var cTempletMediaFileType = fastDev("#cTempletMediaFileType").prop("value").split(",");
			var cTempletMediaFilePath = fastDev("#cTempletMediaFilePath").prop("value").split(",");
			for (var i = 0; i < cTempletMediaFileType.length; i++) {
				var e = getElementByPath(cTempletMediaFilePath[i], cTempletMediaFileType[i]);
				addMediaElement(e);
				fileSum++;
			}
		}
	} else {
		//设置所属部门和作业岗位
		fastDev.get("dpPosition/getUserNameByIDAction.action?pid=" + cPositionID, {
			success : function (data) {
				fastDev("#cDepartmentName").prop("value", cDepartmentName);
				fastDev("#cDepartment").prop("value", cDepartment);
				fastDev("#cPNameExec").prop("value", cPositionName + "-" + data);
				fastDev("#cPidExec").prop("value", cPositionID);
			}
		});
	}

	//编辑模式为查看时不可编辑数据
	if ("0" == cModifyType) {
		fastDev.getInstance("cTempletName").disable();
		fastDev.getInstance("cTempletCode").disable();
		fastDev.getInstance("addTemplet").disable();
		fastDev.getInstance("addPublicTemplet").disable();
		fastDev.getInstance("cIsscan").disable();
		fastDev.getInstance("cJobObjectsName").disable();
		fastDev.getInstance("cJobObjectsButton").disable();
		fastDev.getInstance("cPNameExec").disable();
		fastDev.getInstance("cDepartmentName").disable();
		fastDev.getInstance("cFrequency").disable();
		fastDev.getInstance("cExecAreaName").disable();
		fastDev.getInstance("cExecAreaButton").disable();
		fastDev.getInstance("cPNameCheck").disable();
		fastDev.getInstance("cPButtonCheck").disable();
		fastDev.getInstance("cPNameReview").disable();
		fastDev.getInstance("cPButtonReview").disable();
		fastDev.getInstance("cPNameFeedback1").disable();
		fastDev.getInstance("cPButtonFeedback1").disable();
		fastDev.getInstance("cPNameFeedback2").disable();
		fastDev.getInstance("cPButtonFeedback2").disable();
		fastDev.getInstance("cPNameErrFeedback1").disable();
		fastDev.getInstance("cPButtonErrFeedback1").disable();
		fastDev.getInstance("cPNameErrFeedback2").disable();
		fastDev.getInstance("cPButtonErrFeedback2").disable();
		fastDev.getInstance("cExecTime").disable();
		fastDev.getInstance("cTaskbeginTridButton").disable();
		fastDev.getInstance("cExecEndTime").disable();
		fastDev.getInstance("cTaskfinishTridButton").disable();
		fastDev.getInstance("cCheckTime").disable();
		fastDev.getInstance("cReviewTime").disable();
		fastDev.getInstance("upfileAccessory").disable();
	}
	fastDev.getInstance("cModifyType").setValue(cModifyType);
	
	loadingWindow.close();
}

function doReset() {
	fastDev.getInstance('checkForm').cleanData();
	condition = null;
}

function doAddTemplet(e) {
	var element = e.srcElement || e.target;
	var src = null;
	if ('5W2H标准' == element.innerHTML) {
		src = '../xwzcxt/task/task_templet/templetSetActNode.html?cPositionID=' + cPositionID;
	} else if ('通用5W2H标准' == element.innerHTML) {
		src = '../xwzcxt/standardlibrary/actionStandardPublic.html?type=search';
	}
	actnodewindow = fastDev.create('Window', {
		width : 1000,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : '模版添加岗位活动',
		allowResize : false,
		src : src,
		buttons : [ {
			text : '添加',
			align : 'center',
			iconCls : 'icon-add',
			onclick : function(event, win, childWin, fast) {
				childWin.getactnodeid();
			}
		}, {
			text : '关闭',
			align : 'center',
			iconCls : 'icon-close',
			onclick : function(event, win) {
				win.close();
			}
		} ]
	});
}

function closeDialog() {
	if (actnodewindow) {
		actnodewindow.close();
		actnodewindow = null;
	}
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

function getactnodeid(r) {
	fastDev.getInstance('cActnodeId').setValue(r.cActnodeId);
	fastDev.getInstance('cActnodeName').setValue(r.cActnodeName);
	fastDev.getInstance('cActnodetype').setValue(r.cActnodetype);
	fastDev.getInstance('cTempletName').setValue(r.cActnodeName);
	fastDev.getInstance('cIskeyctrl').setValue(r.cIskeyctrl);
	fastDev.getInstance('cFrequency').setValue(r.cFrequency);
	fastDev.getInstance('cIsscan').setValue(r.cIsscan);
	onActnodeChange();
	onchange();

	fastDev.getInstance('cExecTime').clean();
	fastDev.getInstance('cTaskbeginTrid').clean();
	fastDev.getInstance('cExecEndTime').clean();
	fastDev.getInstance('cTaskfinishTrid').clean();
	fastDev.getInstance('cCheckTime').clean();
	fastDev.getInstance('cTaskcheckTime').clean();
	fastDev.getInstance('cReviewTime').clean();
	fastDev.getInstance('cTaskreviewTime').clean();

	if ("1" == r.cIstimerule) {
		if (r.cAreaId) {
			fastDev.getInstance('cExecAreaid').setValue(r.cAreaId);
			fastDev.getInstance('cExecAreaName').setValue(r.cAreaName);
		}
		if (r.cStarttimeExec) {
			fastDev.getInstance('cExecTime').setValue(r.cStarttimeExecName);
			fastDev.getInstance('cTaskbeginTrid').setValue(r.cStarttimeExec);
		}
		if (r.cEndtimeExec) {
			fastDev.getInstance('cExecEndTime').setValue(r.cEndtimeExecName);
			fastDev.getInstance('cTaskfinishTrid').setValue(r.cEndtimeExec);
		}
		if (r.cPositionIdCheck) {
			fastDev.getInstance('cPidCheck').setValue(r.cPositionIdCheck);
			fastDev.getInstance('cPNameCheck').setValue(r.cPositionNameCheck);
		}
		if (r.cTimelimitCheck) {
			fastDev.getInstance('cCheckTime').setValue(r.cTimelimitCheckName);
			fastDev.getInstance('cTaskcheckTime').setValue(r.cTimelimitCheck);
		}
		if (r.cPositionIdReview) {
			fastDev.getInstance('cPidReview').setValue(r.cPositionIdReview);
			fastDev.getInstance('cPNameReview').setValue(r.cPositionNameReview);
		}
		if (r.cTimelimitReview) {
			fastDev.getInstance('cReviewTime').setValue(r.cTimelimitReviewName);
			fastDev.getInstance('cTaskreviewTime').setValue(r.cTimelimitReview);
		}
		if (r.cPositionIdFeedback1) {
			fastDev.getInstance('cPidFeedback1').setValue(r.cPositionIdFeedback1);
			fastDev.getInstance('cPNameFeedback1').setValue(r.cPositionNameFeedback1);
		}
		if (r.cPositionIdFeedback2) {
			fastDev.getInstance('cPidFeedback2').setValue(r.cPositionIdFeedback2);
			fastDev.getInstance('cPNameFeedback2').setValue(r.cPositionNameFeedback2);
		}
		if (r.cPositionIdErr1) {
			fastDev.getInstance('cPidErrFeedback1').setValue(r.cPositionIdErr1);
			fastDev.getInstance('cPNameErrFeedback1').setValue(r.cPositionNameErr1);
		}
		if (r.cPositionIdErr2) {
			fastDev.getInstance('cPidErrFeedback2').setValue(r.cPositionIdErr2);
			fastDev.getInstance('cPNameErrFeedback2').setValue(r.cPositionNameErr2);
		}
	} else {
		fastDev.getInstance('cExecAreaid').clean();
		fastDev.getInstance('cExecAreaName').clean();
		fastDev.getInstance('cExecTime').clean();
		fastDev.getInstance('cTaskbeginTrid').clean();
		fastDev.getInstance('cExecEndTime').clean();
		fastDev.getInstance('cTaskfinishTrid').clean();
		fastDev.getInstance('cPidCheck').clean();
		fastDev.getInstance('cPNameCheck').clean();
		fastDev.getInstance('cPidReview').clean();
		fastDev.getInstance('cPNameReview').clean();
		fastDev.getInstance('cPidFeedback1').clean();
		fastDev.getInstance('cPNameFeedback1').clean();
		fastDev.getInstance('cPidFeedback2').clean();
		fastDev.getInstance('cPNameFeedback2').clean();
		fastDev.getInstance('cPidErrFeedback1').clean();
		fastDev.getInstance('cPNameErrFeedback1').clean();
		fastDev.getInstance('cPidErrFeedback2').clean();
		fastDev.getInstance('cPNameErrFeedback2').clean();

		var cFrequency = r.cFrequency;
		var checkTime = null, reviewTime = null;
		if ("每月" == cFrequency) {
			checkTime = "下月20日前";
			reviewTime = "下月28日前";
		} else if ("每周" == cFrequency) {
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
	}
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
				if ("" == cAreaid) {
					window.alert("请选择区域！");
				} else {
					var cAreaName = cArea.getCurrentTxt();
					if ("1000100000001" != cAreaid) {
						var id = cArea.getParentid(cAreaid);
						while ("1000100000001" != id) {
							cAreaName = cArea.getValByid(id) + cAreaName;
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
		src : '../xwzcxt/task/task_templet/templetSelectPosition.html?cPid=' + cPid,
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
					cPid = cPosition.getParentid(cPid);
					setPosition(name, cPid, cPosition.getValByid(cPid) + "-" + cPidName);
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

function onSelectTime(e) {
	var element = e.srcElement || e.target;
	fastDev.confirm("是否需要修改当前时间？", "信息提示", function(result) {
		if (result) {
			if ("cCheckTime" == element.id) {
				var cPNameCheck = fastDev.getInstance('cPNameCheck').getValue();
				if ("" == cPNameCheck) {
					window.alert("请先选择验证岗位！");
					return;
				} else {
					doSelectTimeByMonthly(element.id);
					return;
				}
			}
			if ("cReviewTime" == element.id) {
				var cPNameReview = fastDev.getInstance('cPNameReview').getValue();
				if ("" == cPNameReview) {
					window.alert("请先选择评价岗位！");
					return;
				} else {
					doSelectTimeByMonthly(element.id);
					return;
				}
			}
			doSelectTime(element.id);
		}
	});
}

/**
 * 对应作业开始时间修改时的处理
 */
function doButton7() {
	doSelectTime("cExecTime");
}

/**
 * 对应作业完成时间修改时的处理
 */
function doButton8() {
	doSelectTime("cExecEndTime");
}

/**
 * 对应验证期限修改时的处理
 */
function doButton9() {
	var cPNameCheck = fastDev.getInstance('cPNameCheck').getValue();
	if ("" == cPNameCheck) {
		window.alert("请先选择验证岗位！");
	} else {
		doSelectTimeByMonthly("cCheckTime");
	}
}

/**
 * 对应评价期限修改时的处理
 */
function doButton10() {
	var cPNameReview = fastDev.getInstance('cPNameReview').getValue();
	if ("" == cPNameReview) {
		window.alert("请先选择评价岗位！");
	} else {
		doSelectTimeByMonthly("cReviewTime");
	}
}

function doSelectTime(name) {
	var cFrequency = fastDev.getInstance('cFrequency').getValue();

	if ("" == cFrequency) {
		window.alert("请先选择岗位活动！");
	} else {
		var id = null;
		if ("cExecTime" == name) {
			id = "cTaskbeginTrid";
		} else if ("cExecEndTime" == name) {
			id = "cTaskfinishTrid";
		}
		var cTid = fastDev.getInstance(id).getValue();
	
		fastDev.create('Window', {
			width : 300,
			height : 500,
			inside : false,
			showMaxBtn : false,
			title : "选择时间",
			allowResize : false,
			src : '../xwzcxt/task/task_templet/templetSelectTime.html?cOrgId=' + cOrgId + '&cFrequency=' + cFrequency + '&cTid=' + cTid,
			buttons : [ {
				id : 'add',
				text : '添加时间规则',
				align : 'left',
				iconCls : "icon-add",
				onclick : function(event, win, childWin, fast) {
					childWin.doAdd();
				}
			}, {
				id : 'ok',
				text : '确定',
				align : 'right',
				onclick : function(event, win, childWin, fast) {
					var cTidTree = fast.getInstance("cTid");
					var cTid = cTidTree.getCurrentId();
					var cTidName = cTidTree.getCurrentTxt();
					if (("" == cTid) || ("2" == cTid) || ("3" == cTid) || ("4" == cTid) || ("5" == cTid) || ("51" == cTid)) {
						window.alert("请选择时间！");
					} else {
						var cTimeruleType = null;
						if ("每日" == cFrequency) {
							cTimeruleType = 3;
						} else if ("每周" == cFrequency) {
							cTimeruleType = 4;
						} else if ("每月" == cFrequency) {
							cTimeruleType = 5;
						} else {
							cTimeruleType = 2;
						}
						var cPid = cTidTree.getParentid(cTid);
						if (cPid != cTimeruleType) {
							if ((51 == cPid) && (5 == cTimeruleType)) {
								setTime(name, cTid, cTidName);
								win.close();
							} else {
								if (5 == cTimeruleType) {
									window.alert("请选择“每月一次”或“每月两次”下的时间！");
								} else {
									window.alert("请选择“" + cFrequency + "”下的时间！");
								}
							}
						} else {
							setTime(name, cTid, cTidName);
							win.close();
						}
					}
				}
			} ]
		});
	}
}

function doSelectTimeByMonthly(name) {
	var cFrequency = fastDev.getInstance('cFrequency').getValue();

	if ("" == cFrequency) {
		window.alert("请先选择岗位活动！");
	} else {
		var id = null;
		if ("cCheckTime" == name) {
			id = "cTaskcheckTime";
		} else if ("cReviewTime" == name) {
			id = "cTaskreviewTime";
		}
		var cTid = fastDev.getInstance(id).getValue();
	
		fastDev.create('Window', {
			width : 300,
			height : 500,
			inside : false,
			showMaxBtn : false,
			title : "选择时间",
			allowResize : false,
			src : '../xwzcxt/task/task_templet/templetSelectTimeByMonthly.html?cOrgId=' + cOrgId + '&cTid=' + cTid,
			buttons : [ {
				id : 'add',
				text : '添加时间规则',
				align : 'left',
				iconCls : "icon-add",
				onclick : function(event, win, childWin, fast) {
					childWin.doAdd();
				}
			}, {
				id : 'ok',
				text : '确定',
				align : 'right',
				onclick : function(event, win, childWin, fast) {
					var cTidTree = fast.getInstance("cTid");
					var cTid = cTidTree.getCurrentId();
					var cTidName = cTidTree.getCurrentTxt();
					if (("" == cTid) || ("2" == cTid) || ("3" == cTid) || ("4" == cTid) || ("5" == cTid)) {
						window.alert("请选择时间！");
					} else {
						setTime(name, cTid, cTidName);
						win.close();
					}
				}
			} ]
		});
	}
}

function setTime(name, cTid, cTidName) {
	var id = null;
	if ("cExecTime" == name) {
		id = "cTaskbeginTrid";
	} else if ("cExecEndTime" == name) {
		id = "cTaskfinishTrid";
	} else if ("cCheckTime" == name) {
		id = "cTaskcheckTime";
	} else if ("cReviewTime" == name) {
		id = "cTaskreviewTime";
	}
	fastDev.getInstance(id).setValue(cTid);
	fastDev.getInstance(name).setValue(cTidName);
}

function chooseError(file, code, msg) {
	switch (code) {
		case 1:
			fastDev.tips("仅能导入bmp，jpg，jpeg，png，gif，mp3，mp4文件");
			break;
		default:
			fastDev.tips("文件上传因错误失败");
			break;
	}
}

function onUploadStart(file) {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "checkForm",
		text : "文件上传中..."
	});
	return true;
}

function onUploadSuccess(file, response) {
	if (!!response.C_FILE_PATH) {
		var e = getElementByPath(response.C_FILE_PATH, response.C_FILE_TYPE + '');
		addMediaElement(e);
		var cTempletMediaFileId = fastDev.getInstance('cTempletMediaFileId');
		if (fileSum == 0) {
			cTempletMediaFileId.setValue(response.C_FILE_ID);
		} else {
			cTempletMediaFileId.setValue(cTempletMediaFileId.getValue() + ',' + response.C_FILE_ID);
		}
		fileSum++;
	}
	if (loadingWindow) {
		loadingWindow.close();
	}
	fastDev.tips("文件 " + file.name + " 上传成功");
}

function onUploadFail(file, response) {
	if (loadingWindow) {
		loadingWindow.close();
	}
	fastDev.tips("文件 " + file.name + " 上传失败");
}

function onUploadComplete(file, response) {
	if (loadingWindow) {
		loadingWindow.close();
	}
	if (!response)
		return false;
}

function addMediaElement(e) {
	var column = Math.ceil(fileSum / 20);
	if ((fileSum % 20) == 0) {
		var tr = document.createElement('tr');
		tr.id = 'tr' + (column + 1);
		var td = document.createElement('td');
		td.appendChild(e);
		tr.appendChild(td);
		document.getElementById('fileTable').appendChild(tr);
	} else {
		var td = document.createElement('td');
		td.appendChild(e);
		document.getElementById('tr' + column).appendChild(td);
	}
}