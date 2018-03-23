var request = fastDev.Browser.getRequest();
var cModifyType = request['cModifyType'];
var cActnodetype = request['cActnodetype'];
var cPositionName = request['cPositionName'];
var cPositionId = request['cPositionId'];
var cDepartment = request['cDepartment'];
var cActnodeId = request['cActnodeId'];
var cOrgId = request['cOrgId'];
var cPublicId = request['cPublicId'];
var cPublicName = request['cPublicName'];
var cIspublic = request['cIspublic'];

var init = 0;
var isAdd = false;
var size = 0;
var index = 0;
var groupIndex = 0;
var fileSum = 0;

var submitButton = null;
var loadingWindow = null;

var image_audio = '../../images/audio.png';
var image_image = '../../images/image.png';
var image_video = '../../images/video.png';

function beforeReady() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "checkForm",
		text : "加载中..."
	});

	var action = "";
	var dataSource = "";
	if ("" != cActnodeId) {
		dataSource = "tsdactnode/getActNodeByActNodeIDAction.action?cActnodeId=" + cActnodeId;
	}
	this.setOptions({
		dataSource : dataSource
	});
}

function afterDataRender() {
	var cIsrandomj = fastDev.getInstance("cManageattr").getValue();
	cIsrandomjj=cIsrandomj.replace("\\n","");
	fastDev.getInstance("cManageattrj").setValue(cIsrandomjj);
	
	if ("" != cActnodeId) {
		cActnodetype = fastDev("#cActnodetype").prop("value");
		cIspublic = fastDev("#cIspublic").prop("value");
		var cIskeyctrl = fastDev.getInstance("cIskeyctrl").getValue();
		var cIssequence = fastDev.getInstance("cIssequence").getValue();
		var cIsrandom = fastDev.getInstance("cIsrandom").getValue();
		var cIsscan = fastDev.getInstance("cIsscan").getValue();
		if (!cIskeyctrl) {
			cIskeyctrl = "0";
			fastDev.getInstance("cIskeyctrl").setValue(cIskeyctrl);
		}
		if (!cIssequence) {
			cIssequence = "0";
			fastDev.getInstance("cIssequence").setValue(cIssequence);
		}
		if (!cIsrandom) {
			cIsrandom = "0";
			fastDev.getInstance("cIsrandom").setValue(cIsrandom);
		}
		if (!cIsscan) {
			cIsscan = "0";
			fastDev.getInstance("cIsscan").setValue(cIsscan);
		}
		onKeyChange();

		if (fastDev("#cMediaFileType").prop("value")) {
			var cMediaFileType = fastDev("#cMediaFileType").prop("value").split(",");
			var cMediaFilePath = fastDev("#cMediaFilePath").prop("value").split(",");
			for (var i = 0; i < cMediaFileType.length; i++) {
				var e = getElementByPath(cMediaFilePath[i], cMediaFileType[i]);
				addMediaElement(e);
				fileSum++;
			}
		}
	} else {
		fastDev("#cActnodetype").prop("value", cActnodetype);
		fastDev("#cIspublic").prop("value", cIspublic);
		if (0 == cIspublic) {
			fastDev("#cDepartment").prop("value", cDepartment);
			fastDev("#cPositionName").prop("value", cPositionName);
			fastDev("#cPositionId").prop("value", cPositionId);
		} else if (1 == cIspublic) {
			fastDev("#cPublicId").prop("value", cPublicId);
			fastDev("#cPublicName").prop("value", cPublicName);
			fastDev.getInstance("cPositionName").setValue("在行为系统中勾选");
			fastDev.getInstance("cAreaName").setValue("在行为系统中勾选");
			fastDev.getInstance("cStarttimeExecName").setValue("在行为系统中勾选");
			fastDev.getInstance("cStarttimeExec").setValue("在行为系统中勾选");
			fastDev.getInstance("cEndtimeExecName").setValue("在行为系统中勾选");
			fastDev.getInstance("cEndtimeExec").setValue("在行为系统中勾选");
			fastDev.getInstance("cPositionNameCheck").setValue("在行为系统中勾选");
			fastDev.getInstance("cPositionNameReview").setValue("在行为系统中勾选");
			fastDev.getInstance("cPositionNameFeedback1").setValue("在行为系统中勾选");
			fastDev.getInstance("cPositionNameFeedback2").setValue("在行为系统中勾选");
			fastDev.getInstance("cPositionNameErr1").setValue("在行为系统中勾选");
			fastDev.getInstance("cPositionNameErr2").setValue("在行为系统中勾选");
		}
	}

	onTypeChange();

	//编辑模式为查看时不可编辑数据
	if ("0" == cModifyType) {
		fastDev.getInstance("cPublicName").disable();
		fastDev.getInstance("cActnodetype").disable();
		fastDev.getInstance("cActionName").disable();
		fastDev.getInstance("cActnodeName").disable();
		fastDev.getInstance("cActnodeCode").disable();
		fastDev.getInstance("cManageattr").disable();
		fastDev.getInstance("cPdca").disable();
		fastDev.getInstance("cIskeyctrl").disable();
		fastDev.getInstance("cFrequency").disable();
		fastDev.getInstance("cPositionName").disable();
		fastDev.getInstance("cAreaName").disable();
		fastDev.getInstance("cAreaButton").disable();
		fastDev.getInstance("cIssequence").disable();
		fastDev.getInstance("cIsrandom").disable();
		fastDev.getInstance("cIsscan").disable();
		fastDev.getInstance("cStarttimeExecName").disable();
		fastDev.getInstance("cStarttimeExecButton").disable();
		fastDev.getInstance("cEndtimeExecName").disable();
		fastDev.getInstance("cEndtimeExecButton").disable();
		fastDev.getInstance("cRecordsExec").disable();
		fastDev.getInstance("cStdExec").disable();
		fastDev.getInstance("cManagestd").disable();
		fastDev.getInstance("cExamstd").disable();
		fastDev.getInstance("cErrExec").disable();
		document.getElementById("add1").style.display = "none";
		document.getElementById("add2").style.display = "none";
		document.getElementById("delete1").style.display = "none";
		document.getElementById("delete2").style.display = "none";
		fastDev.getInstance("cPositionNameCheck").disable();
		fastDev.getInstance("cPositionButtonCheck").disable();
		fastDev.getInstance("cTimelimitCheckName").disable();
		fastDev.getInstance("cRecordsCheck").disable();
		fastDev.getInstance("cStdCheck").disable();
		fastDev.getInstance("cErrCheck").disable();
		fastDev.getInstance("cPositionNameReview").disable();
		fastDev.getInstance("cPositionButtonReview").disable();
		fastDev.getInstance("cTimelimitReviewName").disable();
		fastDev.getInstance("cStdReview").disable();
		fastDev.getInstance("cPositionNameFeedback1").disable();
		fastDev.getInstance("cPositionButtonFeedback1").disable();
		fastDev.getInstance("cPositionNameFeedback2").disable();
		fastDev.getInstance("cPositionButtonFeedback2").disable();
		fastDev.getInstance("cPositionNameErr1").disable();
		fastDev.getInstance("cPositionButtonErr1").disable();
		fastDev.getInstance("cPositionNameErr2").disable();
		fastDev.getInstance("cPositionButtonErr2").disable();
		fastDev.getInstance("upfileAccessory").disable();
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
	if (result.status == 'ng') {
		alert(result.msg);
		submitButton.enable();
		loadingWindow.close();
	} else {
		var value = fastDev.getInstance("detailGrid" + cActnodetype).getAllValue();
		if (!result.index) {
			index = 0;
			groupIndex = 0;
			result.index = true;
		}
		if (0 == index) {
			groupIndex = 1;
		} else {
			if (value[index].cActitemName != value[index - 1].cActitemName) {
				groupIndex++;
			}
		}
		fastDev.post("struts-actnode/actnode_addActNodeItemByEdit.action?index=" + 
			(index + 1) + "&groupIndex=" + groupIndex + "&length=" + value.length + "&cActnodeId=" + result.id, {
			data : value[index],
			success : function(data) {
				index++;
				if (index == value.length) {
					fastDev.alert(result.msg, '信息提示', result.status, function() {
						if (result.status == 'ok') {
							fastDev.Ui.Window.parent.refreshDatagrid();
							fastDev.Ui.Window.parent.closeDialog();
						}
					});
				} else {
					submitSuccess(result);
				}
			}
		});
	}
}

function onTypeChange() {
	var cActnodetype = fastDev.getInstance("cActnodetype").getValue();
	var font = "";
	if (0 == cIspublic) {
		font = "<font color='red' >*</font>";
		document.getElementById("cPublicNameTr").style.display = "none";
		document.getElementById("cAreaButtonTd").style.display = "table-cell";
		document.getElementById("cStarttimeExecButtonTd").style.display = "table-cell";
		document.getElementById("cEndtimeExecButtonTd").style.display = "table-cell";
		document.getElementById("cPositionButtonCheckTd").style.display = "table-cell";
		document.getElementById("cPositionButtonReviewTd").style.display = "table-cell";
		document.getElementById("cPositionButtonFeedbackTd1").style.display = "table-cell";
		document.getElementById("cPositionButtonFeedbackTd2").style.display = "table-cell";
		document.getElementById("cPositionButtonErrTd1").style.display = "table-cell";
		document.getElementById("cPositionButtonErrTd2").style.display = "table-cell";
		document.getElementById("cAreaIdTd").colSpan = "3";
		document.getElementById("cStarttimeExecTd").colSpan = "3";
		document.getElementById("cEndtimeExecTd").colSpan = "3";
		document.getElementById("cPositionIdCheckTd").colSpan = "3";
		document.getElementById("cPositionIdReviewTd").colSpan = "3";
		document.getElementById("cPositionIdFeedbackTd1").colSpan = "3";
		document.getElementById("cPositionIdFeedbackTd2").colSpan = "3";
		document.getElementById("cPositionIdErrTd1").colSpan = "3";
		document.getElementById("cPositionIdErrTd2").colSpan = "3";
	} else if (1 == cIspublic) {
		document.getElementById("cPublicNameTr").style.display = "table-row";
		document.getElementById("cAreaButtonTd").style.display = "none";
		document.getElementById("cStarttimeExecButtonTd").style.display = "none";
		document.getElementById("cEndtimeExecButtonTd").style.display = "none";
		document.getElementById("cPositionButtonCheckTd").style.display = "none";
		document.getElementById("cPositionButtonReviewTd").style.display = "none";
		document.getElementById("cPositionButtonFeedbackTd1").style.display = "none";
		document.getElementById("cPositionButtonFeedbackTd2").style.display = "none";
		document.getElementById("cPositionButtonErrTd1").style.display = "none";
		document.getElementById("cPositionButtonErrTd2").style.display = "none";
		document.getElementById("cAreaIdTd").colSpan = "4";
		document.getElementById("cStarttimeExecTd").colSpan = "4";
		document.getElementById("cEndtimeExecTd").colSpan = "4";
		document.getElementById("cPositionIdCheckTd").colSpan = "4";
		document.getElementById("cPositionIdReviewTd").colSpan = "4";
		document.getElementById("cPositionIdFeedbackTd1").colSpan = "4";
		document.getElementById("cPositionIdFeedbackTd2").colSpan = "4";
		document.getElementById("cPositionIdErrTd1").colSpan = "4";
		document.getElementById("cPositionIdErrTd2").colSpan = "4";
	}
	if ("1" == cActnodetype) {
		document.getElementById("cPositionIdTitle").innerHTML = font + "执行岗位：";
		document.getElementById("cAreaIdTitle").innerHTML = font + "作业区域：";
		document.getElementById("cStarttimeExecTitle").innerHTML = font + "执行开始时间：";
		document.getElementById("cEndtimeExecTitle").innerHTML = font + "执行完成时间：";
		document.getElementById("cRecordsExecTitle").innerHTML = "规范性记录：<br/>（执行环节）";
		document.getElementById("cStdExecTitle").innerHTML = "执行标准：";
		document.getElementById("cErrExecTitle").innerHTML = "异常怎么处置：<br/>（执行环节）";
		document.getElementById("cStdCheckTitle").innerHTML = "验证标准：<br/>（如何核查）";
		document.getElementById("cPositionIdReviewTitle").innerHTML = "评价岗位：";
		document.getElementById("cTimelimitReviewTitle").innerHTML = "评价完成时间：";
		document.getElementById("cManageattrTitle").style.display = "none";
		document.getElementById("cManageattrTd").style.display = "none";
		document.getElementById("cManagestdTr").style.display = "none";
		document.getElementById("cExamstdTr").style.display = "none";
		document.getElementById("cRecordsCheckTr").style.display = "table-row";
		document.getElementById("cErrCheckTr").style.display = "table-row";
		document.getElementById("cStdReviewTr").style.display = "table-row";
		document.getElementById("grid2").style.display = "none";
	} else if ("2" == cActnodetype) {
		document.getElementById("cPositionIdTitle").innerHTML = font + "管理岗位：";
		document.getElementById("cAreaIdTitle").innerHTML = font + "工作区域：";
		document.getElementById("cStarttimeExecTitle").innerHTML = font + "完工开始时间：";
		document.getElementById("cEndtimeExecTitle").innerHTML = font + "完工完成时间：";
		document.getElementById("cRecordsExecTitle").innerHTML = "规范性记录：";
		document.getElementById("cStdExecTitle").innerHTML = "管理要求：";
		document.getElementById("cErrExecTitle").innerHTML = "异常怎么处置：";
		document.getElementById("cStdCheckTitle").innerHTML = "验证标准：<br/>（如何复核）";
		document.getElementById("cPositionIdReviewTitle").innerHTML = "监督岗位：";
		document.getElementById("cTimelimitReviewTitle").innerHTML = "监督完成时间：";
		document.getElementById("cManageattrTitle").style.display = "table-cell";
		document.getElementById("cManageattrTd").style.display = "table-cell";
		document.getElementById("cManagestdTr").style.display = "table-row";
		document.getElementById("cExamstdTr").style.display = "table-row";
		document.getElementById("cRecordsCheckTr").style.display = "none";
		document.getElementById("cErrCheckTr").style.display = "none";
		document.getElementById("cStdReviewTr").style.display = "none";
		document.getElementById("grid1").style.display = "none";
	}
}

function onKeyChange() {
	var cIskeyctrl = fastDev.getInstance("cIskeyctrl").getValue();
	var cActnodetype = fastDev.getInstance("cActnodetype").getValue();
	if ("1" == cIskeyctrl) {
		document.getElementById("checkTr").style.display = "table-row";
		document.getElementById("cRecordsCheckTr").style.display = "table-row";
		document.getElementById("cStdCheckTr").style.display = "table-row";
		if ("1" == cActnodetype) {
			document.getElementById("cErrCheckTr").style.display = "table-row";
		}
	} else {
		document.getElementById("checkTr").style.display = "none";
		document.getElementById("cRecordsCheckTr").style.display = "none";
		document.getElementById("cStdCheckTr").style.display = "none";
		document.getElementById("cErrCheckTr").style.display = "none";
	}
}

function onBeforeDetailGridReady() {
	this.setOptions({
		initSource : "tsdactnode/getActNodeItemByActNodeIDAction.action?cActnodeId=" + cActnodeId
	});
}

function onAfterDetailGridInitRender() {
	if (init < 2) {
		init++;
		size = this.getSize();
		var values = this.getAllValue();
		this.clean();
		for (var i = 0; i < size; i++) {
			values[i].id = i + "";
			this.addLastRow(values[i], false);
		}
	}
}

function operRenderer() {
	//编辑模式为查看时不可编辑数据
	if ("0" == cModifyType) {
		return;
	} else {
		return [ '<a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="modify" name="modify">修改</a>',
			'<a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="delete" name="delete">删除</a>' ]
			.join('');
	}
}

function operLinkRenderer(value) {
	return [ '<a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="openItemDetails" name="openItemDetails">' + value + '</a>' ].join('');
}

function onRowClick(event, rowindex, rowData) {
	current_actitemId = rowData.cActitemId;
	var target = event.target;
	var name = target['name'];
	if (name) {
		name = fastDev.Util.StringUtil.capitalize(name);
		if (window['do' + name]) {
			window['do' + name].call(window, rowData);
		}
	}
}

function doReset() {
	fastDev.getInstance('checkForm').resetData();
}

function doAddItem() {
	isAdd = true;
	itemAdd('添加活动项', '');
}

function doOpenItemDetails(data) {
	itemAdd('标准活动项明细', data);
}

function doModify(data) {
	isAdd = false;
	itemAdd('修改活动项', data);
}

function itemAdd(title, data) {
	var cActnodetype = fastDev.getInstance("cActnodetype").getValue();
	var cModifyType = null;
	var button = null;
	var save = {
		id : 'save',
		text : '保存',
		align : 'center',
		iconCls : 'icon-save',
		onclick : function(event, win, childWin, fast) {
			var name = fast.getInstance("cActitemName").getValue();
			var get = fast.getInstance("cGetdatatype").getValue();
			var check = fast.getInstance("cCheckdatatype").getValue();
			if (name == "") {
				if ("1" == cActnodetype) {
					window.alert("请设置“做什么”");
				} else if ("2" == cActnodetype) {
					window.alert("请设置“管什么”");
				}
				
			} else if (get == "") {
				if ("1" == cActnodetype) {
					window.alert("请设置“执行结果上传要求”");
				} else if ("2" == cActnodetype) {
					window.alert("请设置“完工上传要求”");
				}
			} else if (check == "") {
				if ("1" == cActnodetype) {
					window.alert("请设置“验证结果上传要求”");
				} else if ("2" == cActnodetype) {
					window.alert("请设置“验证结果上传要求”");
				}
			} else {
				this.disable();
				childWin.showWaitingDialog(this);
				var detailData = fast.getInstance("checkForm").getItems();
				detailData.cGetdatatypename = fast.getInstance("cGetdatatype").getText();
				detailData.cCheckdatatypename = fast.getInstance("cCheckdatatype").getText();
				if (isAdd) {
					detailData.id = size + "";
					size++;
					fastDev.getInstance("detailGrid" + cActnodetype).addLastRow(detailData, false);
				} else {
					fastDev.getInstance("detailGrid" + cActnodetype).updateRow(detailData, false, true);
				}
				refreshStd();
				win.close();
			}
		}
	};
	var reset = {
		id : 'reset',
		text : '重置',
		align : 'center',
		iconCls : 'icon-reset',
		onclick : function(e, win, cwin) {
			cwin.doReset();
		}
	};
	var close = {
		id : 'close',
		text : '关闭',
		align : 'center',
		iconCls : 'icon-close',
		onclick : function(event, win) {
			win.close();
		}
	};

	if ('标准活动项明细' == title) {
		cModifyType = 0;
		button = new Array(close);
	} else if ('修改活动项' == title) {
		cModifyType = 1;
		button = new Array(save, close);
	} else if ('添加活动项' == title) {
		cModifyType = 2;
		button = new Array(save, reset, close);
	} else {
		cModifyType = 0;
		button = new Array(close);
	}

	fastDev.create('Window', {
		width : 800,
		height : 430,
		inside : false,
		showMaxBtn : false,
		title : title,
		allowResize : false,
		src : '../xwzcxt/standardlibrary/actionItemStandardAdd.html?cModifyType=' + cModifyType + '&cActnodetype=' + cActnodetype,
		buttons : button
	}).setData("data", data).setData("cStdExec", fastDev.getInstance("cStdExec").getValue()).setData("cStdCheck", fastDev.getInstance("cStdCheck").getValue());
}

function doDelete(data) {
	fastDev.getInstance("detailGrid" + cActnodetype).delRow(data.id);
	refreshStd();
}

function batchDeleteObject() {
	var objects = fastDev.getInstance("detailGrid" + cActnodetype).getValue();
	if (objects.length == 0) {
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	} else {
		fastDev.confirm('是否删除所选择的' + objects.length + '条记录？', '信息提示', function(result) {
			if (result) {
				for (var i = 0; i < objects.length; i++)
					doDelete(objects[i]);
			}
		});
	}
}

function refreshStd() {
	var value = fastDev.getInstance("detailGrid" + cActnodetype).getAllValue();
	var cStdExec = "", cStdCheck = "";
	for (var i = 0; i < value.length; i++) {
		if (cStdExec.indexOf(value[i].cActitemStd) < 0) {
			if (cStdExec == "") {
				cStdExec = value[i].cActitemStd;
			} else {
				cStdExec += ("\n" + value[i].cActitemStd);
			}
		}
		if (cStdCheck.indexOf(value[i].cActitemStdCheck) < 0) {
			if (cStdCheck == "") {
				cStdCheck = value[i].cActitemStdCheck;
			} else {
				cStdCheck += ("\n" + value[i].cActitemStdCheck);
			}
		}
	}
	fastDev.getInstance("cStdExec").setValue(cStdExec);
	fastDev.getInstance("cStdCheck").setValue(cStdCheck);
}

function onchange() {
	if (0 == cIspublic) {
		fastDev.getInstance("cStarttimeExec").clean();
		fastDev.getInstance("cStarttimeExecName").clean();
		fastDev.getInstance("cEndtimeExec").clean();
		fastDev.getInstance("cEndtimeExecName").clean();
	}

	var cFrequency = fastDev.getInstance("cFrequency").getValue();
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
	fastDev.getInstance("cTimelimitCheck").setValue(checkTime);
	fastDev.getInstance("cTimelimitCheckName").setValue(checkTime);
	fastDev.getInstance("cTimelimitReview").setValue(reviewTime);
	fastDev.getInstance("cTimelimitReviewName").setValue(reviewTime);
}

function onSelectArea() {
	if (0 == cIspublic) {
		fastDev.confirm("是否需要修改当前区域？", "信息提示", function(result) {
			if (result) {
				doSelectArea();
			}
		});
	}
}

function doSelectArea() {
	var cAreaid = fastDev.getInstance("cAreaId").getValue();

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
	fastDev.getInstance("cAreaId").setValue(cAreaid);
	fastDev.getInstance("cAreaName").setValue(cAreaName);
}

function onSelectPosition(e) {
	if (0 == cIspublic) {
		var element = e.srcElement || e.target;
		fastDev.confirm("是否需要修改当前岗位？", "信息提示", function(result) {
			if (result) {
				doSelectPosition(element.id);
			}
		});
	}
}

// 对应验证岗位修改时的处理

function doButton1() {
	doSelectPosition("cPositionNameCheck");
}

// 对应评价岗位修改时的处理

function doButton2() {
	doSelectPosition("cPositionNameReview");
}

//对应默认反馈岗位修改时的处理
 
function doButton3() {
	doSelectPosition("cPositionNameFeedback1");
}

// 对应其他默认反馈岗位修改时的处理

function doButton4() {
	doSelectPosition("cPositionNameFeedback2");
}

// 对应异常反馈岗位修改时的处理

function doButton5() {
	doSelectPosition("cPositionNameErr1");
}

// 对应其他异常反馈岗位修改时的处理

function doButton6() {
	doSelectPosition("cPositionNameErr2");
}

function doSelectPosition(name) {
	var id = name.substring(0, name.indexOf("Name")) + "Id" + name.substring(name.indexOf("Name") + 4);
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
	var id = name.substring(0, name.indexOf("Name")) + "Id" + name.substring(name.indexOf("Name") + 4);
	fastDev.getInstance(id).setValue(cPid);
	fastDev.getInstance(name).setValue(cPidName);
}

function onSelectTime(e) {
	if (0 == cIspublic) {
		var element = e.srcElement || e.target;
		fastDev.confirm("是否需要修改当前时间？", "信息提示", function(result) {
			if (result) {
				if ("cTimelimitCheckName" == element.id) {
					var cPositionNameCheck = fastDev.getInstance('cPositionNameCheck').getValue();
					if ("" == cPositionNameCheck) {
						window.alert("请先选择验证岗位！");
						return;
					} else {
						doSelectTimeByMonthly(element.id);
						return;
					}
				}
				if ("cTimelimitReviewName" == element.id) {
					var cPositionNameReview = fastDev.getInstance('cPositionNameReview').getValue();
					if ("" == cPositionNameReview) {
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
}

// 对应执行开始时间修改时的处理

function doButton7() {
	doSelectTime("cStarttimeExecName");
}


//对应执行完成时间修改时的处理

function doButton8() {
	doSelectTime("cEndtimeExecName");
}

// 对应验证完成时间修改时的处理
 
function doButton9() {
	var cPositionNameCheck = fastDev.getInstance('cPositionNameCheck').getValue();
	if ("" == cPositionNameCheck) {
		window.alert("请先选择验证岗位！");
	} else {
		doSelectTimeByMonthly("cTimelimitCheckName");
	}
}

//对应评价完成时间修改时的处理

function doButton10() {
	var cPositionNameReview = fastDev.getInstance('cPositionNameReview').getValue();
	if ("" == cPositionNameReview) {
		window.alert("请先选择评价岗位！");
	} else {
		doSelectTimeByMonthly("cTimelimitReviewName");
	}
}

function doSelectTime(name) {
	var cFrequency = fastDev.getInstance('cFrequency').getValue();

	if ("" == cFrequency) {
		window.alert("请先选择时态！");
	} else {
		var id = name.substring(0, name.length - 4);
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
	var id = name.substring(0, name.length - 4);
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

function setTime(name, cTid, cTidName) {
	var id = name.substring(0, name.length - 4);
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
		var cMediaFileId = fastDev.getInstance('cMediaFileId');
		if (fileSum == 0) {
			cMediaFileId.setValue(response.C_FILE_ID);
		} else {
			cMediaFileId.setValue(cMediaFileId.getValue() + ',' + response.C_FILE_ID);
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