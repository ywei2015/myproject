function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

var taskId = getQueryString("taskid");
var task_std_version = 0;
var task_std_id = "0";
var task_kind = "0";
var task_status_list = null;
var task_type_list = null;

var image_audio = '../../images/audio.png';
var image_image = '../../images/image.png';
var image_video = '../../images/video.png';
var height = $(window).height();
var width = $(window).width();

$.mobile.page.prototype.options.domCache = true;

$.ajax({
	url : 'task_getTaskStatusList.action',
	async : false,
	dataType : 'json',
	success : function(data) {
		if (data == undefined || data.length == 0) {
			return;
		}
		task_status_list = data;
	}
});

$.ajax({
	url : 'task_getTaskTypeList.action',
	async : false,
	dataType : 'json',
	success : function(data) {
		if (data == undefined || data.length == 0) {
			return;
		}
		task_type_list = data;
	}
});

var getTaskStatusName = function(s) {
	for (var i = 0; i < task_status_list.length; i++) {
		if (task_status_list[i].value == s)
			return task_status_list[i].text;
	}
	return "N/A";
};

var getTaskTypeName = function(s) {
	for (var i = 0; i < task_type_list.length; i++) {
		if (task_type_list[i].value == s)
			return task_type_list[i].text;
	}
	return "N/A";
};

function str2dt(val) {
	if (val == undefined)
		return "";
	var len = 19;
	if (val.length < 19)
		len = val.length;
	return val.substring(0, len).replace("T"," ");
}

function goTop() {
	$('html,body').animate({scrollTop : 0}, 300);
}

$(document).ready(function() {
	OnShowTaskbaseinfo();
});

var OnShowTaskbaseinfo = function() {
	$.ajax({
		url : 'taskEntry_queryTaskEntry.action?action=2&taskId=' + taskId,
		dataType : 'json',
		success : function(data) {
			if (data == undefined || data.length == 0) {
				return;
			}
			task_std_version = (data["taskMouldPojo.c_std_verflag"] == undefined ? "0" : data["taskMouldPojo.c_std_verflag"]);
			task_std_id = (data["taskMouldPojo.c_actnode_id"] == undefined ? "0" : data["taskMouldPojo.c_actnode_id"]);
			$("#task_name").text(data["taskMouldPojo.c_task_name"]);
			$("#task_id").text(data["taskMouldPojo.c_task_id"]);
			task_kind = data["taskMouldPojo.c_task_kind"];
			$("#task_kind").text(task_kind == "3" ? "工作安排" : "工作任务");
			$("#task_type").text(getTaskTypeName(data["taskMouldPojo.c_task_type"]));
			$("#task_section").text(data["taskMouldPojo.c_manage_section_name"]);
			$("#task_pdca").text(data["taskMouldPojo.c_pdca"]);
			$("#task_area").text(data["taskMouldPojo.areaname"]);
			switch (data["taskMouldPojo.c_trigger_type"]) {
				case "1":
					$("#task_tiggertype").text("按5W2H标准自动触发");
					break;
				case "3":
					$("#task_tiggertype").text("发起任务手动触发");
					break;
				case "4":
					$("#task_tiggertype").text("其它系统触发");
					break;
				case "2":
					$("#task_tiggertype").text("其它");
					break;
			}
			$("#task_iskeyctrl").text(data["taskMouldPojo.c_iskeyctrl"] == "0" ? "否" : "是");
			$("#task_issequence").text(data["taskMouldPojo.c_issequence"] == "0" ? "否" : "是");
			$("#task_creater").text(data["taskMouldPojo.c_create_username"]);
			$("#task_confirmer").text(data["taskMouldPojo.c_confirm_username"]);
			$("#task_status").text(getTaskStatusName(data["taskMouldPojo.c_status"]));
			$("#task_exec_username").text(data["taskMouldPojo.c_exec_username"]);
			$("#task_down_time").text(str2dt(data["taskMouldPojo.c_down_time"]));
			$("#task_fact_endtime").text(str2dt(data["taskMouldPojo.c_fact_endtime"]));
			if (task_kind == "1") {
				$("#task_destitle").hide();
				$("#task_desval").hide();
				$("#task_atttitle").hide();
				$("#DivTaskAttachment").hide();
				OnShowTaskStdInfo();
			} else {
				$("#task_type_title").hide();
				$("#task_pdca_title").hide();
				$("#task_iskeyctrl_title").hide();
				$("#task_issequence_title").hide();
				$("#task_desval").text(data["taskMouldPojo.c_remark"] == undefined ? "无" : data["taskMouldPojo.c_remark"].trim());
				$("#DivTaskStdInfo").html('<span style="padding-left: 2em;">此任务没有关联标准信息。</span>');
				OnShowSendTimeDes(); //DivTaskAttachment
			}
			var c_handle_des = data["taskMouldPojo.c_handle_des"];
			if ((c_handle_des == "") || (c_handle_des == undefined)) {
				document.getElementById('result_handledes').innerHTML = "无";
			} else {
				document.getElementById('result_handledes').innerHTML = c_handle_des;
			}

			var taskiserr = data["taskMouldPojo.c_iserror"];
			if (taskiserr == "0") { //正常
				document.getElementById('task_result_type').innerHTML = "正常";
			} else {
				document.getElementById('task_result_type').innerHTML = "发现异常";
			}
			//验证、评价
			SetCheckAndEvaluateDiv(data);
			OnShowTaskResultInfo();
		}
	});
};

var SetCheckAndEvaluateDiv = function(data) {
	var chkstate = "未验证";
	var evaluatestate = "未评价";
	if (data["taskMouldPojo.c_status"] == "34" || data["taskMouldPojo.c_status"] == "35") {
		chkstate = "已验证";
		if (data["taskMouldPojo.c_status"] == "35") {
			evaluatestate = "已评价";
		}
	}
	var chkhtml = '<div width="100%" height="100%"><table style="width: 100%;">';
	chkhtml += '<tr><td width="30%" class="table_header">验证状态</td><td>' + chkstate + '</td></tr>';
	var chk_rusult = (data["taskMouldPojo.c_chk_result"] == undefined ? '' : data["taskMouldPojo.c_chk_result"]);
	if (chkstate == "已验证") {
		if ((chk_rusult == "") || (chk_rusult == null)) {
			chk_rusult = "合格";
		} else {
			if (chk_rusult.toUpperCase() == "OK") {
				chk_rusult = "合格";
			} else {
				chk_rusult = "不合格" + chk_rusult.substring(2);
			}
		}
	}
	chkhtml += '<tr><td width="30%" class="table_header">验证结果</td><td>' + chk_rusult + '</td></tr>';
	chkhtml += '<tr><td width="30%" class="table_header">验证人</td><td>' + (data["taskMouldPojo.c_chk_username"] == undefined ? '' : data["taskMouldPojo.c_chk_username"]) + '</td></tr>';
	chkhtml += '<tr><td width="30%" class="table_header">验证时间</td><td>' + (data["taskMouldPojo.c_chk_endtime"] == undefined ? '' : data["taskMouldPojo.c_chk_endtime"].substring(0, 19)) + '</td></tr>';
	chkhtml += '</table></div>';
	document.getElementById('DivTaskCheckInfo').innerHTML = chkhtml;

	var evaluatehtml = '<div width="100%" height="100%"><table style="width: 100%;">';
	evaluatehtml += '<tr><td width="30%" class="table_header">评价状态</td><td>' + evaluatestate + '</td></tr>';
	var evaluate_rusult = (data["taskMouldPojo.c_evaluate_result"] == undefined ? '' : data["taskMouldPojo.c_evaluate_result"]);
	if (evaluatestate == "已评价") {
		if ((evaluate_rusult == "") || (evaluate_rusult == null)) {
			evaluate_rusult = "合格";
		} else {
			if (evaluate_rusult.toUpperCase() == "OK") {
				evaluate_rusult = "合格";
			} else {
				evaluate_rusult = "不合格" + evaluate_rusult.substring(2);
			}
		}
	}
	evaluatehtml += '<tr><td width="30%" class="table_header">评价结果</td><td>' + evaluate_rusult + '</td></tr>';
	evaluatehtml += '<tr><td width="30%" class="table_header">评价人</td><td>' + (data["taskMouldPojo.c_evaluate_username"] == undefined ? '' : data["taskMouldPojo.c_evaluate_username"]) + '</td></tr>';
	evaluatehtml += '<tr><td width="30%" class="table_header">评价时间</td><td>' + (data["taskMouldPojo.c_evaluate_time"] == undefined ? '' : data["taskMouldPojo.c_evaluate_time"].substring(0, 19)) + '</td></tr>';
	evaluatehtml += '</table></div>';
	document.getElementById('DivTaskEvaluateInfo').innerHTML = evaluatehtml;
};

var OnShowSendTimeDes = function() {
	$.ajax({
		url : 'actNode_getTaskAttachmentByMap.action?taskid=' + taskId,
		dataType : 'json',
		success : function(data) {
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
};

var OnShowTaskResultInfo = function() {
	$.ajax({
		url : 'taskEntry_queryEntryList.action?action=1&taskMouldPojo.c_task_id=' + taskId,
		dataType : 'json',
		success : function(data) {
			var innerHTML = "";
			var index = 1;
			for (var i = 0; i < data.length; i++) {
				if ((0 == i) || (data[i].c_groupindex != data[i - 1].c_groupindex)) {
					index = 1;
					innerHTML += '<table style="width: 100%;margin: 10px 0 10px 0;">';
					innerHTML += '<tr><td width="100%" class="table_header">步骤【' + data[i].c_groupindex + '】—步骤名称：' + (data[i].c_step_prompt == undefined ? "收集结果---" + (i + 1) : data[i].c_step_prompt) + '</td></tr>';
				}
				innerHTML += '<tr><td width="30%" class="table_header" style="padding-left: 2em;">证据收集方式' + index + '：</td></tr><tr><td style="padding-left: 4em;">' + (data[i].c_tracefun_name == undefined ? "无" : data[i].c_tracefun_name) + '</td></tr>';
				innerHTML += '<tr><td width="30%" class="table_header" style="padding-left: 2em;">步骤执行结果' + index + '：</td></tr><tr><td style="padding-left: 4em;">';
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
				index++;
			}
			document.getElementById('DivTaskResultStep').innerHTML = innerHTML;
		}
	});
};

var OnShowTaskStdInfo = function() {
	$.ajax({
		url : 'actNode_getCommStdInfoById.action?actnodeid=' + task_std_id + '&version=' + task_std_version,
		dataType : 'json',
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
						document.getElementById("title3").innerHTML = "异常怎么处置：（执行环节）";
						document.getElementById("title4").innerHTML = "验证标准：（如何核查）";
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
						document.getElementById("title4").innerHTML = "验证标准：（如何复核）";
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
};

/**
 * 图片详情查看
 * @param src 多媒体文件路径
 */
function viewImageDetail(src) {
	fastDev.create('Window', {
		width : $(window).width(),
		height : $(window).height(),
		inside : false,
		showMaxBtn : false,
		title : '图片详情',
		allowResize : false,
		src : src
	});
}

/**
 * 音频详情查看
 * @param src 多媒体文件路径
 */
function viewAudioDetail(src) {
	fastDev.create('Window', {
		width : $(window).width(),
		height : $(window).height(),
		inside : false,
		showMaxBtn : false,
		title : '音频详情',
		allowResize : false,
		src : '../../fastdev/detail.html?type=2&src=' + src
	});
}

/**
 * 视频详情查看
 * @param src 多媒体文件路径
 */
function viewVideoDetail(src) {
	fastDev.create('Window', {
		width : $(window).width(),
		height : $(window).height(),
		inside : false,
		showMaxBtn : false,
		title : '视频详情',
		allowResize : false,
		src : '../../fastdev/detail.html?type=3&src=' + src
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
				width : $(window).width(),
				height : $(window).height(),
				content : '<span style="font-size:18px;">' + details + '</span>'
			});
		},
		data : {
			"cBasedataId" : cBasedataId
		}
	});
}

function fastLocation(index) {
	if ("ui-collapsible ui-collapsible-inset ui-corner-all ui-collapsible-themed-content ui-collapsible-collapsed" == $("#collapsible" + index).attr("class")) {
		$("#collapsible" + index).attr("data-collapsed", "false");
		$("#collapsible" + index + "> h3 > a").click();
	}
	$("html,body").animate({scrollTop : $("#collapsible" + index).offset().top}, 1000);
}
function goBack(){
	window.location.href='../taskAndErr/taskAndErr.html';
}