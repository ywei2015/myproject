function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

$(document).bind('mobileinit', function () {} );

var errId = getQueryString("errId");
var cTaskId = 0;
var feedbacklist = [];
var image_audio = '../../images/audio.png';
var image_image = '../../images/image.png';
var image_video = '../../images/video.png';

function goTop() {
	$('html,body').animate({scrollTop : 0}, 300);
}

function DTStr(val) {
	if (val == undefined)
		return "";
	var len = 19;
	if (val.length < 19)
		len = val.length;
	return val.substring(0, len).replace("T", " ");
}

var taskid = 0;
function showErrInfoInPage(d) {
	cTaskId = d.cTaskId == undefined ? 0 : d.cTaskId;
	$("#errkindname").text(d.cErrKindName); //cErrKind
	if (cTaskId == 0) {
		$("#errtaskname").text("无");
	} else {
		$("#errtaskname").text(d.cTaskName);
	}
	$("#errareaname").text(d.cAreaName == null ? "无" : data.cAreaName);
	$("#errname").text(d.cErrName);
	$("#erroccurTime").text(DTStr(d.cOccurTime));
	$("#errwriterName").text(d.cWriterName);
	$("#errmanagesectionname").text(d.cManageSectionName);
	$("#errcloseTime").text(DTStr(d.cCloseTime));
	$("#errsuggestendTime").text(DTStr(d.cSuggestendTime));
	$("#errisclosename").text(d.cIscloseName); //cIsclose

	var isdealdone = d.cIsclose;
	$("#dealusername").text(d.cHandleUsername);
	$("#dealtime").text(DTStr(d.cCloseTime));
	$("#dealstate").text(d.cIscloseName);

	var lotno = d.cFeedbackPath.split(";")[0];
	//获取异常现附件及描述
	$.ajax({
		url : 'taskErrorAffixM/getErrorAffixByIdAndLotNoM.action?cErrId=' + errId + "&cLotNo=" + lotno,
		dataType : 'json',
		success : function(d) {
			showErrInfoAffixInPage(d);
		}
	});

	//获取异常处理附件及描述
	if (isdealdone > 0) {
		$.ajax({
			url : 'taskErrorAffixM/getTaskErrorAffixDetailByIDMAction.action?cErrId=' + errId,
			dataType : 'json',
			success : function(d) {
				showErrDealAffixInPage(d);
			}
		});
	}
}

function showErrDealAffixInPage(d) {
	var errdes = "无";
	var tablehtml = "无附件";
	if (d != null) {
		errdes = d.description;
		var pathlist = d.path.split(';');
		tablehtml = "<table style='width: 100%;border-spacing: 10px;'>";
		var trhtml = "";
		for (var i = 0; i < pathlist.length; i++) {
			var path = pathlist[i].split(',');
			if (path[0] != '') {
				if (path[0] == '2') {
					trhtml = '<tr><td><img src="' + image_audio + '" onclick="viewAudioDetail(' + "'" + path[1] + "'" + ')" style="cursor:pointer;"/></td></tr>';
					tablehtml = tablehtml + trhtml;
				} else if (path[0] == '3') {
					trhtml = '<tr><td><img src="' + image_video + '" onclick="viewVideoDetail(' + "'" + path[1] + "'" + ')" style="cursor:pointer;"/></td></tr>';
					tablehtml = tablehtml + trhtml;
				} else {
					//trhtml = '<tr><td><img src="' + image_image + '" onclick="viewImageDetail(' + "'" + path[1] + "'" + ')" style="cursor:pointer;"/></td></tr>';
					trhtml = '<tr><td><img src="' + image_image + '" onclick="viewImageDetail(' + "'" + path[1] + "'" + ')" style="cursor:pointer;"/></td></tr>';
					tablehtml = tablehtml + trhtml;
				}
			}
		}
//		<div data-role="popup" id="popupPhotoLandscape" class="photopopup" data-overlay-theme="a" data-corners="false" data-tolerance="30,15"> 
//		<a href="#" data-rel="back" data-role="button" data-theme="a" data-icon="delete" data-iconpos="notext" class="ui-btn-right"> Close</a> <img src="image/1.png" alt="Photo landscape"> 
//	</div>  
		tablehtml = tablehtml + "</table>";
	}
	$("#dealdes").text(errdes);
	document.getElementById('dealtableDiv').innerHTML = tablehtml;
}

function showErrInfoAffixInPage(d) {
	var errdes = "无";
	var tablehtml = "无附件";
	if (d != null) {
		errdes = d.description;
		var pathlist = d.path.split(',');
		tablehtml = "<table style='width: 100%;border-spacing: 10px;'";
		var trhtml = "";
		for (var i = 0; i < pathlist.length; i++) {
			var a = pathlist[i].lastIndexOf(".");
			if (a > -1) {
				if (pathlist[i].substring(a + 1) == 'mp3') {
					trhtml = '<tr><td><img src="' + image_audio + '" onclick="viewAudioDetail(' + "'" + pathlist[i] + "'" + ')" style="cursor:pointer;"/></td></tr>';
					tablehtml = tablehtml + trhtml;
				} else if (pathlist[i].substring(a + 1) == 'mp4') {
					trhtml = '<tr><td><img src="' + image_video + '" onclick="viewVideoDetail(' + "'" + pathlist[i] + "'" + ')" style="cursor:pointer;"/></td></tr>';
					tablehtml = tablehtml + trhtml;
				} else {
					//trhtml = '<tr><td><img src="' + image_image + '" onclick="viewImageDetail(' + "'" + pathlist[i] + "'" + ')" style="cursor:pointer;"/></td></tr>';
					trhtml = '<tr><td><img src="' + image_image + '"  onclick="viewImageDetail(' + "'" + pathlist[i] + "'" + ')" style="cursor:pointer;"/></td></tr>';	
					tablehtml = tablehtml + trhtml;
				}
			}
		}
		tablehtml = tablehtml + "</table>";
	}
	$("#errdes").text(errdes);
	document.getElementById('imagetableDiv').innerHTML = tablehtml;
}

var flowcount = 0;
function showErrFlowInPage(d) {
	var nodename = "序号" + ++flowcount;
	var dealflowpanel_html = document.getElementById('dealflowpanel').innerHTML;
	var new_html = "<li data-role='list-divider' role='heading' class='ui-li ui-li-divider ui-bar-b ui-li-has-count'>" + nodename;
	new_html += "<span class='ui-li-count ui-btn-up-c ui-btn-corner-all' onclick='goTop()'>Top</span>";
	new_html += "</li>";
	new_html += "<li class='ui-li ui-li-static ui-btn-up-c ui-last-child'>";
	new_html += "<div>";
	new_html += setFlowTable(d); //"...Flow 3..."; //创建流程图方法
	new_html += "</div>";
	new_html += "</li>";
	document.getElementById('dealflowpanel').innerHTML = dealflowpanel_html + new_html;
}

var zhenggaistatehtml = "<div>---</div>";
var zhenggaistatedone = 0;
var zhenggaistatelotno = 0;
function setFlowTable(d) {
	var newtablehtml = "";
	var feedbackstatehtml = "";
	var Receivestatehtml = "";
	var copytostatehtml = "";
	var factdealtype = 0;
	for (var i = 0; i < d.length; i++) {
		if (d[i].cFeedbackType == 0) { //接收信息
			factdealtype = d[i].cFactdealType;
			feedbackstatehtml = "<div>" + d[i].cFeedbackerName + " 已完成</div>"; 
			Receivestatehtml = "<div>" + d[i].cReceiverUsername + " " + (d[i].cStatus == 22 ? "已完成" : "未完成") + "</div>";
		} else { //抄送信息
			copytostatehtml += "<div>" + d[i].cReceiverUsername + " " + (d[i].cStatus >0 ? "已完成" : "未完成") + "</div>";
		}
	}
	newtablehtml += "<table id='flowitemtable' style='width: 100%;'>";
	newtablehtml += "<tr>";
	newtablehtml += "<td class='td_vheader'>异常反馈</td>";
	newtablehtml += "<td class='td_vflowitem'>";
	newtablehtml += "<div class='flownodepass'>异常反馈</div>";
	newtablehtml += "</td>";
	newtablehtml += "<td>";
	newtablehtml += "<div>反馈人：</div>";
	newtablehtml += feedbackstatehtml;
	newtablehtml += "</td>";
	newtablehtml += "</tr>";
	newtablehtml += "<tr>";
	newtablehtml += "<td class='td_vheader'>异常接收</td>";
	newtablehtml += "<td class='td_vflowitem'>";
	newtablehtml += "<div class='flownodepass'>异常接收</div>";
	newtablehtml += "</td>";
	newtablehtml += "<td>";
	newtablehtml += "<div>接收人：</div>";
	newtablehtml += Receivestatehtml;
	newtablehtml += "</td>";
	newtablehtml += "</tr>";
	newtablehtml += "<tr>";
	newtablehtml += "<td class='td_vheader'>异常处置</td>";
	newtablehtml += "<td class='td_vflowitem'>";
	newtablehtml += "<div class='" + (factdealtype == 1 ? "flownodepass" : "flownodewait") + "'>接收确认</div>";
	newtablehtml += "<div class='" + (factdealtype == 2 ? "flownodepass" : "flownodewait") + "'>本人处置</div>";
	newtablehtml += "<div class='" + (factdealtype == 3 ? "flownodepass" : "flownodewait") + "'>安排整改</div>";
	newtablehtml += "<div class='" + (factdealtype == 4 ? "flownodepass" : "flownodewait") + "'>继续反馈</div>";
	newtablehtml += "</td>";
	newtablehtml += "<td>";
	newtablehtml += "<div>处置人：</div>";
	newtablehtml += Receivestatehtml;
	newtablehtml += "</td>";
	newtablehtml += "</tr>";
	newtablehtml += "<tr>";
	newtablehtml += "<td class='td_vheader'>异常整改</td>";
	newtablehtml += "<td class='td_vflowitem'> ";
	newtablehtml += "<div class='" + (zhenggaistatedone == 1 ? "flownodepass" : "flownodewait") + "'>整改实施</div>";
	newtablehtml += "</td>";
	newtablehtml += "<td>";
	newtablehtml += "<div>整改人：</div>";
	newtablehtml += zhenggaistatehtml;
	newtablehtml += "</td>";
	newtablehtml += "</tr>";
	newtablehtml += "</table>";
	return newtablehtml;
}

var flowrowobjs = [];
function drawflow() {
	//获取第一次反馈描述及附件信息
	for (var i = 0; i < feedbacklist.length; i++) {
		$.ajax({
			url : 'taskErrorFeedbackM/getTaskErrorFeedbackDetailByLotnoMAction.action?cErrId=' + errId + "&cFeedbackLotno=" + feedbacklist[i],
			dataType : 'json',
			success : function(d) {
				if (d.length > 0) {
					flowrowobjs[flowrowobjs.length] = d;
				}
				if (flowrowobjs.length == feedbacklist.length) {
					AsynResultSortCall();
				}
			}
		});
	}
}

function AsynResultSortCall() {
	var count = feedbacklist.length;
	for (var i = 0; i < count; i++) { //获取整改信息
		if (flowrowobjs[i][0].cDealType > 0) {
			zhenggaistatelotno = flowrowobjs[i][0].cFeedbackLotno;
			if (flowrowobjs[i][0].cFactdealType > 0) {
				zhenggaistatedone = 1;
				zhenggaistatehtml = "<div>" + flowrowobjs[i][0].cReceiverUsername + " 已完成</div>";
			} else {
				zhenggaistatedone = 0;
				zhenggaistatehtml = "<div>" + flowrowobjs[i][0].cReceiverUsername + " 未完成</div>";
			}
		}
	}
	for (var k = 0; k < count; k++) {
		for (var j = 0; j < count; j++) {
			if (flowrowobjs[j][0].cFeedbackLotno == feedbacklist[k])
				if (flowrowobjs[j][0].cDealType != 2) {
					showErrFlowInPage(flowrowobjs[j]);
				}
		}
	}
}

$(document).ready(function() {
	if (errId == null) {
		document.getElementsByTagName("body")[0].innerHTML = "对不起，没有找到相关记录！";
		return;
	}
	//获取验证评价结果
	$.ajax({
		url : 'taskErrorInfoM/getVerifyEvalInfoAction.action?errId=' + errId,
		dataType : 'json',
		success : function(data) { 
			showVerEvaInfo(data);
		}
	});

	//获取异常主要内容
	$.ajax({
		url : 'taskErrorInfoM/getTaskErrorInfoDetailByIDMAction.action?cErrId=' + errId,
		dataType : 'json',
		success : function(d) {
			if (d.cErrName != undefined) {
				showErrInfoInPage(d);
				$.ajax({
					url : 'taskErrorFeedbackM/getTaskErrorFeedbackDetailByIDMAction.action?cErrId=' + errId,
					dataType : 'json',
					success : function(d) {
						feedbacklist = d.cFeedbackLotno.split(',');
						drawflow(); //画流程图
					}
				});
			} else {
				document.getElementsByTagName("body")[0].innerHTML = "对不起，没有找到相关记录！";
			}
		}
	});
});

function showVerEvaInfo(data) {
	if (data) {
		if ("2" == data.c_chk_status) {
			$("#cChkStatus").text('已验证');
			if ("" == data.c_chk_result || null == data.c_chk_result || "OK" == data.c_chk_result.toUpperCase()) {
				$("#cChkResult").text('正常');
			} else {
				$("#cChkResult").text("异常" + data.c_chk_result.substring(2));
			}
		} else {
			$("#cChkStatus").text('未验证');
			$("#cChkResult").text('');
		}
		$("#cChkUserName").text(data.c_chk_username == null ? "" : data.c_chk_username);
		$("#cChkTime").text(DTStr(data.c_chk_time == null ? "" : data.c_chk_time));
		if ("2" == data.c_evaluate_status) {
			$("#cEvalStatus").text('已评价');
			if ("" == data.c_evaluate_result || null == data.c_evaluate_result || "OK" == data.c_evaluate_result.toUpperCase()){
				$("#cEvalResult").text('合格');
			} else {
				$("#cEvalResult").text('不合格' + data.c_evaluate_result.substring(2));
			}
		} else {
			$("#cEvalStatus").text('未评价');
			$("#cEvalResult").text('');
		}
		$("#cEvalUserName").text(data.c_evaluate_username == null ? "" : data.c_evaluate_username);
		$("#cEvalTime").text(DTStr(data.c_evaluate_time == null ? "" : data.c_evaluate_time));
	} else {
		$("#cChkStatus").text('');
		$("#cChkResult").text('');
		$("#cChkUserName").text('');
		$("#cChkTime").text('');
		$("#cEvalStatus").text('');
		$("#cEvalResult").text('');
		$("#cEvalUserName").text('');
		$("#cEvalTime").text('');
	}
}
/**
 * 图片详情查看
 * @param src 多媒体文件路径
 */

 function viewImageDetail(src) { 
	 var img=document.getElementById("popupPhotoimg");	
	 var img2 = new Image(); //创建一个Image对象，实现图片的预下载 
	 img2.src = src; 
     img.src=src;
     if (img2.complete) { // 如果图片已经存在于浏览器缓存，直接调用回调函数 
    	 $("#popupPhotoLandscape").popup('open');
    	 return; // 直接返回，不用再处理onload事件 
    	 } 
    	 img2.onload = function () { //图片下载完毕时异步调用callback函数。 
    	 $("#popupPhotoLandscape").popup('open');//将回调函数的this替换为Image对象 
    	 }; 
    	 hideLoader();
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

function fastLocation(index) {
	if ("ui-collapsible ui-collapsible-inset ui-corner-all ui-collapsible-themed-content ui-collapsible-collapsed" == $("#collapsible" + index).attr("class")) {
		$("#collapsible" + index).attr("data-collapsed", "false");
		$("#collapsible" + index + "> h3 > a").click();
	}
	$("html,body").animate({scrollTop : $("#collapsible" + index).offset().top}, 1000);
}


function showLoader() {
	            $.mobile.loading('show', {
	            	text : '数据加载中，请稍等...',
	        		textVisible : true,
	        		theme : 'a'
	             });
}

function hideLoader() {
	            $.mobile.loading('hide');
	         }

function goBack(){
	window.location.href='../taskAndErr/taskAndErr.html';
}

