var request = fastDev.Browser.getRequest();
var taskId = request['taskId'];
var edit = request['edit'];
var scanDetails = [];
var image_audio = '../../images/audio.png';
var image_image = '../../images/image.png';
var image_video = '../../images/video.png';

$(document).ready(function() {
	$("#divtaskresultflip").click(function() {
		$("#divtaskresult").slideToggle("slow");
		$("#divtaskresulticon").attr("iconCls", "icon-down");
	});
	$("#divtaskstepresultflip").click(function() {
		$("#divtaskstepresult").slideToggle("slow");
		$("#divtaskresulstepticon").attr("iconCls", "icon-down");
	});
	$("#divtaskverifyresultflip").click(function() {
		$("#divtaskverifyresult").slideToggle("slow");
		$("#divtaskverifyresulticon").attr("iconCls", "icon-down");
	});
	$("#divtaskcommentresultflip").click(function() {
		$("#divtaskcommentresult").slideToggle("slow");
		$("#divtaskcommentresulticon").attr("iconCls", "icon-down");
	});
});

fastDev.post("taskEntry_queryEntryList.action?action=1&taskMouldPojo.c_task_id=" + taskId, {
	success : function(data) {
		var innerHTML = "";
		for (var i = 0; i < data.length; i++) {
			if ((0 == i) || (data[i].c_groupindex != data[i - 1].c_groupindex)) {
				innerHTML += '<table class="ui-form-table">';
				innerHTML += '<tr><td class="ui-form-table-dt" style="border-right:none;background:none;text-align:left;">步骤【' + data[i].c_groupindex + '】—步骤名称：' + (data[i].c_step_prompt == undefined ? "收集结果---" + (i + 1) : data[i].c_step_prompt) + '</td>';
				innerHTML += '<td class="ui-form-table-dd" style="border-left:none;border-right:none;"></td><td class="ui-form-table-dt" style="border-left:none;border-right:none;background:none;"></td><td class="ui-form-table-dd" style="border-left:none;"></td></tr>';
			}
			innerHTML += '<tr>';
			innerHTML += '<td class="ui-form-table-dt">证据收集方式：</td><td class="ui-form-table-dd">' + (data[i].c_tracefun_name == undefined ? "无" : data[i].c_tracefun_name) + '</td>';
			innerHTML += '<td class="ui-form-table-dt">步骤执行结果：</td><td class="ui-form-table-dd">';
			var resultHTML = "";
			if (data[i].c_result == undefined) {
				resultHTML = '*(未收集)';
			} else {
				if (data[i].c_tracefunid == "1") {
					resultHTML = '<img src="' + image_image + '" onclick="viewImageDetail(' + "'" + data[i].c_file_path + "'" + ')" style="cursor:pointer;"/>';
				} else if (data[i].c_tracefunid == "2") {
					resultHTML = '<img src="' + image_audio + '" onclick="viewAudioDetail(' + "'" + data[i].c_file_path + "'" + ')" style="cursor:pointer;"/>';
				} else if (data[i].c_tracefunid == "3") {
					resultHTML = '<img src="' + image_video + '" onclick="viewVideoDetail(' + "'" + data[i].c_file_path + "'" + ')" style="cursor:pointer;"/>';
				} else if (data[i].c_tracefunid == "20") {
					var cBasedataId = (data[i].c_result).substring(0, 13);
					var IntcBasedataId = Number(cBasedataId);
					if (!isNaN(IntcBasedataId)) {
						resultHTML = '<a href="#" target="_self" class="scanCode" onclick="getScanDetails(' + cBasedataId + ')" style="text-decoration:underline;">查看二维码详情</a>';
					} else {
						resultHTML = data[i].c_result;
					}
				} else if (data[i].c_tracefunid == "11") {
					if ("1" == data[i].c_result) {
						resultHTML = "正常";
					} else if ("0" == data[i].c_result) {
						resultHTML = "异常";
					}
				} else {
					resultHTML = data[i].c_result;
				}
			}
			innerHTML += resultHTML;
			innerHTML += '</td></tr>';
			if (((data.length - 1) == i) || (data[i].c_groupindex != data[i + 1].c_groupindex)) {
				innerHTML += '</table>';
			}
		}
		document.getElementById('divtaskstepresult').innerHTML = innerHTML;
	}
});

fastDev.post("mediaStd_getStdMediaInfo.action?c_task_id=" + taskId, {
	success : function(data) {
		if (data) {
			var fileList = data.fileList, head = data.head;
			var sum1 = 0, sum2 = 0, sum3 = 0;
			for (var i = 0; i < fileList.length; i++) {
				var e = getElementByPath(head + fileList[i].c_file_path, fileList[i].c_file_type);
				switch (fileList[i].c_mediastd_type) {
					case '1':
						addMediaElement(e, sum1, "actnodeFileTable");
						sum1++;
						break;
					case '2':
						addMediaElement(e, sum2, "templetFileTable");
						sum2++;
						break;
					case '3':
						addMediaElement(e, sum3, "actnodeItemFileTable");
						sum3++;
						break;
					default:
						break;
				}
			}
			if (sum1 > 0) {
				document.getElementById("actnode").style.display = "table-row";
			}
			if (sum2 > 0) {
				document.getElementById("templet").style.display = "table-row";
			}
			if (sum3 > 0) {
				document.getElementById("actnodeItem").style.display = "table-row";
			}
		}
	}
});

function addMediaElement(e, fileSum, id) {
	var column = Math.ceil(fileSum / 20);
	if ((fileSum % 20) == 0) {
		var tr = document.createElement('tr');
		tr.id = id + 'Tr' + (column + 1);
		var td = document.createElement('td');
		td.appendChild(e);
		tr.appendChild(td);
		document.getElementById(id).appendChild(tr);
	} else {
		var td = document.createElement('td');
		td.appendChild(e);
		document.getElementById(id + 'Tr' + column).appendChild(td);
	}
}

//FORM加载完后回调
function onFormAfterDataRender() {
	getCheckDetails();
	systemFile();
	var form = fastDev.getInstance('checkForm');
	var items = form.getItems();
	var select_status = fastDev.getInstance("taskMouldPojo.c_status1");
	select_status.setValue(items["taskMouldPojo.c_status"]);
	ChkAndEvaluate(select_status.getValue()); //验证和评价、取消设置
    
	var c_iskeyctrld = fastDev.getInstance("taskMouldPojo.c_iskeyctrl");
	c_iskeyctrld.setValue(items["taskMouldPojo.c_iskeyctrl"]);
	console.info(items["taskMouldPojo.c_iskeyctrl"]);
	var taskkind = items["taskMouldPojo.c_task_kind"];
	if (taskkind == "1") {
		fastDev("#trtaskremark").hide();
		fastDev("#trtaskAttachment").hide();
	} else {
		fastDev.getInstance("taskMouldPojo.c_task_type").hide();
		fastDev.getInstance("taskMouldPojo.c_pdca").hide();
		fastDev.getInstance("taskMouldPojo.c_iskeyctrl").hide();
		fastDev.getInstance("taskMouldPojo.c_issequence").hide();
		setDivTaskAttachmentValue(); //DivTaskAttachment
	}

	document.getElementById('result_c_task_name').innerHTML = items["taskMouldPojo.c_task_name"];
	var c_handle_des = items["taskMouldPojo.c_remark"];
	if ((c_handle_des == "") || (c_handle_des == undefined)) {
		document.getElementById('result_handledes').innerHTML = "无";
	} else {
		document.getElementById('result_handledes').innerHTML = c_handle_des;
	}

	var taskiserr = items["taskMouldPojo.c_iserror"];
	if (taskiserr == "0") { //正常
		document.getElementById('task_result_type').innerHTML = "正常";
	} else {
		document.getElementById('task_result_type').innerHTML = "发现异常";
	}

	var isstdtask = items["taskMouldPojo.c_isstd"];
	if (isstdtask == "0") { //非标准任务
		fastDev("#divstdinfo").hide();
		try {
			fastDev.getInstance("tasktabs").removeTab("标准信息");
		} catch (e) {
			console.info(e);
			fastDev.tips(e);
		}
	} else { //标准任务
		fastDev("#divtaskrequireinfo").hide();
		getStdInfo(items["taskMouldPojo.c_actnode_id"], items["taskMouldPojo.c_std_verflag"]);
	}
	fastDev.getInstance("tasktabs").setActiveTabByTitle("任务基本信息");
}

function ChkAndEvaluate(status) { //验证和评价、取消设置
	if (status == '44') {
		fastDev("#trcanceldes").show();
	} else {
		fastDev("#trcanceldes").hide();
	}
	var items = fastDev.getInstance('checkForm').getItems();

	if (status == '34') {
		document.getElementById('c_chk_status').innerHTML = "已验证";

		var chk_rusult = items["taskMouldPojo.c_chk_result"];
		if ((chk_rusult == "") || (chk_rusult == null)) {
			document.getElementById("c_chk_result").innerHTML = "合格";
		} else {
			if (chk_rusult.toUpperCase() == "OK") {
				document.getElementById("c_chk_result").innerHTML = "合格";
			} else {
				document.getElementById("c_chk_result").innerHTML = "不合格" + chk_rusult.substring(2);
			}
		}

		var chk_username = items["taskMouldPojo.c_chk_username"];
		if ((!chk_username == '') || (chk_username != null)) {
			document.getElementById('c_chk_man').innerHTML = chk_username;
		} else {
			document.getElementById('c_chk_man').innerHTML = "";
		}

		var chk_endtime = items["taskMouldPojo.c_chk_endtime"];
		document.getElementById('c_chk_time').innerHTML = chk_endtime || '';
		document.getElementById('c_evaluate_status').innerHTML = "未评价";
		document.getElementById('c_evaluate_result').innerHTML = "";
		document.getElementById('c_evaluate_man').innerHTML = "";
		document.getElementById('c_evaluate_time').innerHTML = "";
	} else if (status == '35') {
		document.getElementById('c_chk_status').innerHTML = "已验证";

		var chk_rusult = items["taskMouldPojo.c_chk_result"];
		if ((chk_rusult == "") || (chk_rusult == null)) {
			document.getElementById("c_chk_result").innerHTML = "合格";
		} else {
			if (chk_rusult.toUpperCase() == "OK") {
				document.getElementById("c_chk_result").innerHTML = "合格";
			} else {
				document.getElementById("c_chk_result").innerHTML = "不合格" + chk_rusult.substring(2);
			}
		}

		var chk_username = items["taskMouldPojo.c_chk_username"];
		if ((!chk_username == '') || (chk_username != null)) {
			document.getElementById('c_chk_man').innerHTML = chk_username;
		} else {
			document.getElementById('c_chk_man').innerHTML = "";
		}

		var chk_endtime = items["taskMouldPojo.c_chk_endtime"];
		document.getElementById('c_chk_time').innerHTML = chk_endtime || '';

		document.getElementById('c_evaluate_status').innerHTML = "已评价";
		var evaluate_rusult = items["taskMouldPojo.c_evaluate_result"];
		if ((evaluate_rusult == "") || (evaluate_rusult == null)) {
			document.getElementById("c_evaluate_result").innerHTML = "合格";
		} else {
			if (evaluate_rusult.toUpperCase() == "OK") {
				document.getElementById("c_evaluate_result").innerHTML = "合格";
			} else {
				document.getElementById("c_evaluate_result").innerHTML = "不合格" + evaluate_rusult.substring(2);
			}
		}

		var evaluate_username = items["taskMouldPojo.c_evaluate_username"];
		if ((!evaluate_username == '') || (evaluate_username != null)) {
			document.getElementById('c_evaluate_man').innerHTML = evaluate_username;
		} else {
			document.getElementById('c_evaluate_man').innerHTML = "";
		}

		var evaluate_endtime = items["taskMouldPojo.c_evaluate_time"];
		document.getElementById('c_evaluate_time').innerHTML = evaluate_endtime || '';
	} else {
		document.getElementById('c_chk_status').innerHTML = "未验证";
		document.getElementById('c_chk_result').innerHTML = "";
		document.getElementById('c_chk_man').innerHTML = "";
		document.getElementById('c_chk_time').innerHTML = "";
		document.getElementById('c_evaluate_status').innerHTML = "未评价";
		document.getElementById('c_evaluate_result').innerHTML = "";
		document.getElementById('c_evaluate_man').innerHTML = "";
		document.getElementById('c_evaluate_time').innerHTML = "";
	}
}

function getStdInfo(stdid, version) {
	fastDev.post("actNode_getCommStdInfoById.action?actnodeid=" + stdid + "&version=" + version, {
		success : function(data) {
			if (data != undefined) {
				if ("0" == data["StdInfo.c_stdversion"]) {
					document.getElementById("title1").innerHTML = "做什么：";
					document.getElementById("title2").innerHTML = "执行标准：";
					document.getElementById("title3").innerHTML = "异常处置标准：";
					document.getElementById("title4").innerHTML = "验证标准：";
					document.getElementById("managestd").style.display = "none";
					document.getElementById("examstd").style.display = "none";
					document.getElementById("errdeal2").style.display = "none";
					document.getElementById("review").style.display = "table-row";
					var tmpstr = data["StdInfo.c_dowhat"] + "";
					tmpstr = tmpstr.split("\\n").join("<br/>") + "<br/><br/>";
					document.getElementById('std.what').innerHTML = tmpstr;
					document.getElementById('std.dorequire').innerHTML = (data["StdInfo.c_exec_std"] == null ? "" : data["StdInfo.c_exec_std"]).replace(/\\n/g, "<br/>") + "<br/><br/>";
					document.getElementById('std.errdeal').innerHTML = (data["StdInfo.c_err_std"] == null ? "" : data["StdInfo.c_err_std"]).replace(/\\n/g, "<br/>") + "<br/><br/>";
					document.getElementById('std.check').innerHTML = (data["StdInfo.c_check_std"] == null ? "" : data["StdInfo.c_check_std"]).replace(/\\n/g, "<br/>") + "<br/><br/>";
					document.getElementById('std.review').innerHTML = (data["StdInfo.c_review_std"] == null ? "" : data["StdInfo.c_review_std"]).replace(/\\n/g, "<br/>") + "<br/><br/>";
				} else {
					if ("1" == data["StdInfo.c_actnodetype"]) {
						document.getElementById("title1").innerHTML = "做什么：";
						document.getElementById("title2").innerHTML = "执行标准：";
						document.getElementById("title3").innerHTML = "异常怎么处置：<br/>（执行环节）";
						document.getElementById("title4").innerHTML = "验证标准：<br/>（如何核查）";
						document.getElementById("managestd").style.display = "none";
						document.getElementById("examstd").style.display = "none";
						document.getElementById("errdeal2").style.display = "table-row";
						document.getElementById("review").style.display = "table-row";
						var tmpstr = data["StdInfo.c_dowhat"] + "";
						tmpstr = tmpstr.split("\\n").join("<br/>") + "<br/><br/>";
						document.getElementById('std.what').innerHTML = tmpstr;
						document.getElementById('std.dorequire').innerHTML = (data["StdInfo.c_exec_std"] == null ? "" : data["StdInfo.c_exec_std"]).replace(/\\n/g, "<br/>") + "<br/><br/>";
						document.getElementById('std.errdeal').innerHTML = (data["StdInfo.c_err_std"] == null ? "" : data["StdInfo.c_err_std"]).replace(/\\n/g, "<br/>") + "<br/><br/>";
						document.getElementById('std.check').innerHTML = (data["StdInfo.c_check_std"] == null ? "" : data["StdInfo.c_check_std"]).replace(/\\n/g, "<br/>") + "<br/><br/>";
						document.getElementById('std.errdeal2').innerHTML = (data["StdInfo.c_err_std2"] == null ? "" : data["StdInfo.c_err_std2"]).replace(/\\n/g, "<br/>") + "<br/><br/>";
						document.getElementById('std.review').innerHTML = (data["StdInfo.c_review_std"] == null ? "" : data["StdInfo.c_review_std"]).replace(/\\n/g, "<br/>") + "<br/><br/>";
					} else {
						document.getElementById("title1").innerHTML = "管什么：";
						document.getElementById("title2").innerHTML = "管理要求：";
						document.getElementById("title3").innerHTML = "异常怎么处置：";
						document.getElementById("title4").innerHTML = "验证标准：<br/>（如何复核）";
						document.getElementById("managestd").style.display = "table-row";
						document.getElementById("examstd").style.display = "table-row";
						document.getElementById("errdeal2").style.display = "none";
						document.getElementById("review").style.display = "none";
						var tmpstr = data["StdInfo.c_dowhat"] + "";
						tmpstr = tmpstr.split("\\n").join("<br/>") + "<br/><br/>";
						document.getElementById('std.what').innerHTML = tmpstr;
						document.getElementById('std.dorequire').innerHTML = (data["StdInfo.c_exec_std"] == null ? "" : data["StdInfo.c_exec_std"]).replace(/\\n/g, "<br/>") + "<br/><br/>";
						document.getElementById('std.managestd').innerHTML = (data["StdInfo.c_managestd"] == null ? "" : data["StdInfo.c_managestd"]).replace(/\\n/g, "<br/>") + "<br/><br/>";
						document.getElementById('std.examstd').innerHTML = (data["StdInfo.c_examstd"] == null ? "" : data["StdInfo.c_examstd"]).replace(/\\n/g, "<br/>") + "<br/><br/>";
						document.getElementById('std.errdeal').innerHTML = (data["StdInfo.c_err_std"] == null ? "" : data["StdInfo.c_err_std"]).replace(/\\n/g, "<br/>") + "<br/><br/>";
						document.getElementById('std.check').innerHTML = (data["StdInfo.c_check_std"] == null ? "" : data["StdInfo.c_check_std"]).replace(/\\n/g, "<br/>") + "<br/><br/>";
					}
				}
			}
		}
	});
}

function onFormBeforeReady() {
	if (edit == "details") {
		this.setOptions({
			dataSource : 'taskEntry_queryTaskEntry.action?action=2&taskId=' + taskId
		});
	}
}

function doSubmit() {
	var form = fastDev.getInstance('checkForm');
	form.setOptions({
		action : "taskEdit_editData.action?action=1&edit=" + edit
	});	 
	form.submit();
}

function doReset() {
	fastDev.getInstance('checkForm').resetData();
}

//表单提交后回调
function onSubmitSuccess(result) {
	fastDev.alert(result.msg, '信息提示', result.status, function() {
		if (result.status == 'ok') {
			parent.refreshDatagrid();
			parent.closeDialog();
		}
	});
}

function setDivTaskAttachmentValue() {
	fastDev.post("actNode_getTaskAttachmentByMap.action?taskid=" + taskId, {
		success : function (data) {
			if ((data == undefined) || (data.length == 0)) {
				return;
			}
			var innerHTML = "";
			var divTaskAttachment = document.getElementById('DivTaskAttachment');
			for (var i = 0; i < data.length; i++) {
				var tmpImage = '<img src="' + image_image + '" onclick="viewImageDetail(' + "'" + data[i].c_file_path + "'" + ')" style="cursor:pointer;"/>';
				var tmpAudio = '<img src="' + image_audio + '" onclick="viewAudioDetail(' + "'" + data[i].c_file_path + "'" + ')" style="cursor:pointer;"/>';
				var tmpVideo = '<img src="' + image_video + '" onclick="viewVideoDetail(' + "'" + data[i].c_file_path + "'" + ')" style="cursor:pointer;"/>';
				if (innerHTML == "") {
					if (data[i].c_file_type == "3") {
						innerHTML = tmpVideo;
					} else if (data[i].c_file_type == "2") {
						innerHTML = tmpAudio;
					} else if (data[i].c_file_type == "1") {
						innerHTML = tmpImage;	
					} else {
						innerHTML == "无";
					}
				} else {
					if (data[i].c_file_type=="3") {
						innerHTML += tmpVideo;
					} else if (data[i].c_file_type == "2") {
						innerHTML += tmpAudio;
					} else if (data[i].c_file_type == "1") {
						innerHTML += tmpImage;
					} else {
						innerHTML += "";
					}
				}
			}
			divTaskAttachment.innerHTML = innerHTML;
		}
	});
}

function getCheckDetails() {
	fastDev.post("checkDetails_getCheckDetailsBillon.action", {
		success : function(data) {
			var billon = data.billon;
			var c_ex_type = data.c_ex_type;
			var billtype = data.billtype;
			if ((c_ex_type == 2) && (billon != null)) {
				var url;
				switch (billtype) {
					case 'A':
						url = 'http://10.75.97.65:7001/WEB_IMS/ims/stockinspect3/action/aDanViewAllAction.do?OMID=' + billon + '&auth=false';
						break;
					case 'B':
						url = 'http://10.75.97.65:7001/WEB_IMS/ims/stockinspect3/action/bDanViewAction.do?OMID=' + billon + '&auth=false';
						break;
					case 'C':
						url = 'http://10.75.97.65:7001/WEB_IMS/ims/stockinspect3/action/cDanViewAction.do?OMID=' + billon + '&auth=false';
						break;
					case 'D':
						url = 'http://10.75.97.65:7001/WEB_IMS/ims/stockinspect3/action/dDanViewAction.do?OMID=' + billon + '&auth=false';
						break;
					case 'F':
						url = 'http://10.75.97.65:7001/WEB_IMS/ims/stockinspect3/action/fDanViewAction.do?OMID=' + billon + '&auth=false';
						break;
					default:
						break;
				}
				if ((url == undefined) || (url == null)) {
					fastDev('#getCheckDetails').hide();
				}
				document.getElementById('getCheckDetails').href = url;
			} else if ((c_ex_type == 3) && (billon != null)){
				
			} else {
				fastDev('#getCheckDetails').hide();
			}
		},
		data : {
			"taskId" : taskId
		}
	});	
}

function systemFile() {
	fastDev.post("checkDetails_getUserCode.action", {
		success : function(data) {
			if ((data == undefined) || (data.length == 0)) {
				return;
			}
			var LoginUserCode = data;
			var url = "http://10.75.97.231/gonggao/bandindex.aspx?coding=0108&LoginUserCode=" + LoginUserCode;
			document.getElementById('systemFile').href = url;
		}
	});
}

function getScanDetails(cBasedataId) {
	fastDev.post("jobObjects_getObjectInfo.action", {
		success : function(data) {
			if (!data || !data.data[0] || !data.data[0].cScanDetail) {
				fastDev.alert('数据已过期！', '信息提示', function() {});
				return;
			}
			var scanDetails = data.data[0].cScanDetail;
			var obj = eval('(' + scanDetails + ')');
			var details = "";
			for (var key in obj) {
				details += key + ":" + (obj[key] || '') + "<br/>";
			}
			fastDev.create("Window", {
				title : "扫码详情",
				width : '400px',
				height : '400px',
				content : '<span style="font-size:18px;">' + details + '</span>'
			});
		},
		data : {
			"cBasedataId" : cBasedataId
		}
	});
}