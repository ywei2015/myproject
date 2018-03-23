<%@page import="org.apache.poi.util.SystemOutLogger"%>
<%@ page language="java"
	import="java.util.Map"
	import="com.talkweb.twdpe.web.session.main.Session"
	import="com.talkweb.twdpe.web.session.util.SessionFactory"
	import="com.talkweb.xwzcxt.util.SSOUtils"
	import="smartbi.sdk.ClientConnector"
	import="smartbi.sdk.service.user.UserManagerService"
	pageEncoding="UTF-8"
%>
<%
	String displayName = "匿名";
	String displayCode = "";
	String smartbiCookie = "";
	Session ses = SessionFactory.getInstance(request, response);
	Map user = (Map) ses.getAttribute("USERSESSION");
	boolean ret = false;
	if (user != null) {
		displayName = user.get("name") + "";
		displayCode = user.get("code") + "";

		//System.out.println(displayName + "==========" + displayCode);
		String smartUser = displayCode;
		String smartPassword = "888888";
		String smartbiURL = "http://10.159.31.38:18080/smartbi/vision";
		ClientConnector conn = new ClientConnector("http://10.159.31.38:18080/smartbi"); 
		ret = conn.open(smartUser, smartPassword);
		if (ret) {
			smartbiCookie = conn.getCookie();
		} else {
			smartbiCookie = "";
		} 
	}/* else {
		String zyUid = null;
		zyUid = SSOUtils.getUIDFromWebSeal(request);
		//zyUid = "dengln0618";
		System.out.println(zyUid + "*****************");
		if (zyUid != null) {
			session.setAttribute("zyid", zyUid);
			response.sendRedirect("zyLogin.action");
		}
	}*/
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>长沙卷烟厂过程行为支持系统</title>
<link rel="shortcut icon" href="themes/default/images/favicon.ico" />
<script src="fastdev/fastDev-import.js"></script>
<script src="../js/jquery.min.js"></script>
<style>
.ui-nav-2thstage .ui-nav-selected .ui-nav-header {
	background: none;
}
.ui-nav-2thstage .ui-nav-header {
	background: none;
}
.ui-layout-head {
	overflow: hidden;
}
#rightIcon {
	z-index: 9999;
	position: fixed;
	right:0;
}
#rightIcon img {
	width: 36px;
	height: 191px;
	cursor: pointer;
	vertical-align: top;
}
#information {
	display: none;
	margin-left: -4px;
	margin-top:40px;
}
#detailTop {
	width: 258px;
	height: 9px;
	background-image: url(../images/backgroundTop.png);
}
#detailBottom {
	width: 258px;
	height: 9px;
	background-image: url(../images/backgroundBottom.png);
}
#detail {
	min-height: 300px;
	padding-left: 10px;
	background-image: url(../images/background.png);
}
#detail ul {
	color: rgb(0, 100, 255);
	padding-left: 25px;
	list-style-type: disc;
}
#detail ul li {
	padding-top: 5px;
}
#detail ul li a {
	color: rgb(0, 100, 255);
	font-weight: bold;
}
#detail ul li a:hover {
	text-decoration: underline;
}
#detail ul li span {
	color: red;
	cursor: pointer;
	font-weight: bold;
}
#detail ul li span:hover {
	text-decoration: underline;
}

.title_span{
      color: #075488;
      font-family: "微软雅黑";
     
}
</style>
<script type="text/javascript">
var currentEl;
var hasJianZhi = <%=ses.getAttribute("hasJianzhi")%>;
fastDev(function() {
	var winWidth = fastDev(window).width();
	var winHeight = fastDev(window).height();
	fastDev(".ui-layout").css({
		width : winWidth,
		height : winHeight - 96
	});
	fastDev(".ui-layout-center").css("width", winWidth - (winWidth * 0.15) - 3);

	fastDev(".ui-layout-arrow-left").bind("click", function(event) {
		if (/left/.test(event.target.className)) {
			fastDev(".ui-layout-left").animate({width:"0px"});
			fastDev(".ui-layout-handle-left").animate({left:"0px"});
			fastDev(".ui-layout-center").animate({left:winWidth*0.005,width:winWidth-(winWidth*0.005)-2});
			if (!tabs)
				tabs = fastDev.getInstance("tabs");
			tabs && tabs.resize(winWidth-(winWidth*0.005)-2+"px");
		} else {
			fastDev(".ui-layout-left").animate({width:winWidth*0.145});
			fastDev(".ui-layout-handle-left").animate({left:winWidth*0.145});
			fastDev(".ui-layout-center").animate({left:winWidth*0.15,width:winWidth-(winWidth*0.15)});
		}
		fastDev(event.target).toggleClass("ui-layout-arrow-left,ui-layout-arrow-right");
	});

	fastDev.create("Proxy", {
		url : "resource_showTopMenu.action"
	}).addJob({
		name : "exec",
		object : function(data) {
			if (data.length > 0) {
				var htmlList = [];
				for (var i = 0; i < data.length; i++) {
					var menu = data[i];
					htmlList.push('<li appid="' + menu.appId + '" resourceCode="' + menu.resourceCode  + '">');
					htmlList.push('<div class="ui-layout-nav2-left"></div>');
					htmlList.push('<a href="javascript:void(0);" class="ui-layout-nav2-inner"><span>' + menu.resourceName + '</span></a>');
					htmlList.push('<div class="ui-layout-nav2-right"></div>');
				}
				$("#menuPanel").elems[0].innerHTML = htmlList.join('');
				currentEl = $('#menuPanel > li:first');
				currentEl.addClass('ui-layout-nav2-frist');
				currentEl.addClass('ui-layout-nav2-active');
			}
		}
	}).load();
});

function showMenu(appId, menu) {
	fastDev.getInstance('nav').reLoad({
		url: 'resource_showMenu.action?parentCode=' + menu + '&appid=' + appId
	});
}

function loadMenu(event) {
	event = event || window.event;
	var target = event.target || event.srcElement;
	var targetEl = $(target);
	while (targetEl.elems[0].tagName != 'LI') {
		if (targetEl.elems[0].tagName == 'UL') {
			return;
		}
		targetEl = targetEl.parent();
	}
	if (currentEl) {
		if (currentEl == targetEl)
			return;
		currentEl.removeClass('ui-layout-nav2-active');
	}
	targetEl.addClass('ui-layout-nav2-active');
	currentEl = targetEl;
	showMenu(targetEl.attr('appid'), targetEl.attr('resourceCode'));
}

var tabs;
function openTabs(event, id) {
	var map = this.getItemById(id);
	var url = map["url"] || "";
	var text = map["text"] || "";

	if (!tabs) {
		tabs = fastDev.create("Tabs", {
			container : "tabBox",
			height : (fastDev(window).height()- 96) + "px",
			width : (parseInt(fastDev(window).width()) * 85.5 / 100 - 11) + "px"
		});
	}

	var smartCookie=$("#smartCookieId").html();

	if (url != "") {
		if (tabs.exists(text) == true) {
			tabs.setActiveTabByTitle(text);
		} else {
			if (smartCookie != "") {
				if (url.indexOf("?") > -1) {
					url += "&smartbiCookie=" + smartCookie;
				} else {
					url += "?smartbiCookie=" + smartCookie;
				}			    	
			}

			tabs.addTab({
				title : text,
				url : url,
				showCloseBtn : true
			});
		}
	}
	return false;
}

function onAfterInitRender() {
	var id = this.getSelected();
	var map = this.getItemById(id) || {};
	var url = map["url"] || "";
	var text = map["text"] || "";
	if (!tabs) {
		tabs = fastDev.create("Tabs", {
			container : "tabBox",
			height : (fastDev(window).height() - 96) + "px",
			width : (parseInt(fastDev(window).width()) * 85.5 / 100 - 11) + "px",
			items : [{
				title : text,
				url : url,
				showCloseBtn : true
			}]
		});
	}
}

function logout() {
	fastDev.confirm('是否退出系统？', '信息提示', function(result) {
		if (result) {
			location.href = "portal_loginout.action";
		}
	});
}

function selectUser() {
	if (hasJianZhi) {
		fastDev.confirm('是否切换身份？', '信息提示', function(result) {
			if (result) {
				fastDev.create('Window', {
					title : '切换身份',
					src :'userSelect.html',
					width : 518,
					height : 332,
					allowResize : false
				});
			}
		});
	} else {
		fastDev.alert('该用户没有兼职，不能切换身份', '信息提示', 'warning');
	}
}

function modifypsw() {
	fastDev.create('Window', {
		title : '修改密码',
		src : 'newsystem/user/passwordModify.html',
		width : 518,
		height : 332,
		allowResize : false,
		buttons : [ {
			id : 'ok',
			text : '修改',
			align : 'center',
			iconCls : 'icon-save',
			onclick : function(event, win, childWin, fast) {
				   childWin.tijiao();
				   setTimeout(function(){
					   var subfalg=childWin.getSubmitFalg();
					   if(subfalg==1) win.close();
				   },1000);
		          
			}
		}, {
			id : 'clear',
			text : '清空',
			align : 'center',
			iconCls : 'icon-reset',
			onclick : function(event, win,childWin,fast) {
				//fastDev.getInstance('modifyPasswordform').cleanData();
				childWin.cleanDataj();
			}
		}]
	});
}

function exit() {
	fastDev.confirm('是否退出系统', '信息提示', function(result) {
		if (result) {
			window.open('', '_self', '');
			window.close();
		}
	});
}

function feedback(code) {
	/*window.open('http://10.75.95.104:8087/pp/business/sec/CSLogin.html', '_blank', '');
	window.alert(code);
	if (code < 10) {
		code= "000" + code;
	} else if (code < 100) {
		code = "00" + code;
	} else if (code < 1000) {
		code = "0" + code;
	}
	window.alert(code);*/
	window.open('http://10.75.95.104:8087/pp/business/sec/mestz.jsp?dd-user=' + code, '_blank', '');
}

var update = null;

$(document).ready(function() {
	$("#rightIcon img").css("margin-top", ((fastDev(window).height() - 119 - 300) / 2) + "px");
	$("#detail").height(350);
});

function showInformation(type) {
// 	showErrDet();
// 	showInformationGrid('1','1');
	if ($("#information").css("display") == "none") {
		$("#information").css("display", "inline-block");
		if(type!=undefined){
		updateInformation(type);
		update = setInterval("updateInformation("+type+")", 1000);
		}
	} else {
		$("#information").css("display", "none");
		if (update) {
			clearInterval(update);
			update = null;
		}
	}
}

function updateInformation(type) {
// 	tPanel/getPanelInfoCountAction.action
if(type=="0"){
	fastDev.get("waithandTask_getWorkTask.action?type=1", {
		success : function (data) {
			if ("登录超时，请重新登录" == data) {
				fastDev.alert(data, "信息提示", "warning", function() {
					clearInterval(update);
					top.location = "../component/login.html";
				});
			} else {
				$("#informationtodo").html("(" +data.rwzx  + ")");
			} 
		}
	});
	fastDev.get("taskVerifyAndComment/taskVerifyAndComment_verify.action?type=1", {
		success : function (data) {
			if ("登录超时，请重新登录" == data) {
				fastDev.alert(data, "信息提示", "warning", function() {
					clearInterval(update);
					top.location = "../component/login.html";
				});
			} else {
			$("#informationcheck").html("(" +data.rwyz  + ")");
			} 
		}
	});
	
	fastDev.get("taskVerifyAndComment/taskVerifyAndComment_comment.action?type=1", {
		success : function (data) {
			if ("登录超时，请重新登录" == data) {
				fastDev.alert(data, "信息提示", "warning", function() {
					clearInterval(update);
					top.location = "../component/login.html";
				});
			} else {
			$("#informationcomment").html("(" +data.rwpj  + ")");
			} 
		}
	});
}
else if(type=='1'){
	fastDev.get("jobObjects_getAllTaskPointcheckInfo.action?type=1", {
		success : function (data) {
			if ("登录超时，请重新登录" == data) {
				fastDev.alert(data, "信息提示", "warning", function() {
					clearInterval(update);
					top.location = "../component/login.html";
				});
			} else {
// 				alert(data);
				$("#information1").html("(" +data.djyq  + ")");
				$("#information2").html("(" + data.djyc + ")");
				$("#information11").html("(" + data.zgyq + ")");
				$("#information12").html("(" + data.zgwwc + ")");
			} 
		}
	});
}
else if(type=='2'){
	fastDev.get("task_new/getAllTaskAction.action?type=1", {
		success : function (data) {
			if ("登录超时，请重新登录" == data) {
				fastDev.alert(data, "信息提示", "warning", function() {
					clearInterval(update);
					top.location = "../component/login.html";
				});
			} else {
// 				alert(data);
				$("#informationworklate").html("(" +data.rwzxyq  + ")");
			
			} 
		}
	});
	fastDev.get("task_new/getAllTaskAction.action?type=1&&errortask=yes", {
		success : function (data) {
			if ("登录超时，请重新登录" == data) {
				fastDev.alert(data, "信息提示", "warning", function() {
					clearInterval(update);
					top.location = "../component/login.html";
				});
			} else {
// 				alert(data);
				$("#informationworkerror").html("(" +data.rwyq  + ")");
			
			} 
		}
	});
	
}
else if(type=='3'){
	fastDev.get("task_new/getAllTaskAction.action?type=2", {
		success : function (data) {
			if ("登录超时，请重新登录" == data) {
				fastDev.alert(data, "信息提示", "warning", function() {
					clearInterval(update);
					top.location = "../component/login.html";
				});
			} else {
// 				alert(data);
				$("#informationchecklate").html("(" +data.rwyzyq  + ")");
			
			} 
		}
	});
	
	fastDev.get("task_new/getAllTaskAction.action?type=3", {
		success : function (data) {
			if ("登录超时，请重新登录" == data) {
				fastDev.alert(data, "信息提示", "warning", function() {
					clearInterval(update);
					top.location = "../component/login.html";
				});
			} else {
// 				alert(data);
				$("#informationcheckerror").html("(" +data.rwyzyc  + ")");
			
			} 
		}
	});
}

else if(type=='4'){
	fastDev.get("task_new/getAllTaskAction.action?type=4", {
		success : function (data) {
			if ("登录超时，请重新登录" == data) {
				fastDev.alert(data, "信息提示", "warning", function() {
					clearInterval(update);
					top.location = "../component/login.html";
				});
			} else {
// 				alert(data);
				$("#informationcommentlate").html("(" +data.rwpjyq  + ")");
			
			} 
		}
	});
	
	fastDev.get("task_new/getAllTaskAction.action?type=5", {
		success : function (data) {
			if ("登录超时，请重新登录" == data) {
				fastDev.alert(data, "信息提示", "warning", function() {
					clearInterval(update);
					top.location = "../component/login.html";
				});
			} else {
// 				alert(data);
				$("#informationcommenterror").html("(" +data.rwpjyc  + ")");
			
			} 
		}
	});
}
}

// function showAllInformation(type) {
// 	showInformationGrid(type, 0);

// 	showInformation();
// }

// function showUnreadInformation(type) {
// 	showInformationGrid(type, 1);
// 	showInformation();
// }

function showInformationGrid(type,sortType) {
	var title = [];
	title[1] = "点检逾期";
	title[2] = "点检异常";
	title[11] = "整改逾期";
	title[12] = "整改未完成";
	var url;
	if(type=='todo'){
		url = '../xwzcxt/mytask/myHandleTask.html?c_task_kind=1';
	}
	else if(type=='check'){
		url = '../xwzcxt/task/verify/taskVerify.html?c_task_kind=1';
	}
	else if(type=='comment'){
		url='../xwzcxt/task/comment/taskComment.html?c_task_kind=1';
	}
	else if(type=='worklate'){
		url='../xwzcxt/task/task.html?type=1';
	}
	else if(type=='workerror'){
		url = '../xwzcxt/task/task.html?ivalue=2&&extype=1';
	}
	else if(type=='checklate'){
		url = '../xwzcxt/task/task.html?ivalue=3&&extype=1';
	}
	else if(type=='checkerror'){
		url = '../xwzcxt/task/task.html?ivalue=45&&extype=1';
	}
	else if(type=='commentlate'){
		url = '../xwzcxt/task/task.html?type=4';
	}
	else if(type=='commenterror'){
		url = '../xwzcxt/task/task.html?type=5';
	}
	else if(type=='1'){
		url = '../xwzcxt/map/mapInfo.html?cIslate=0&&extype=1';
	}
	else if(type=='2'){
		url = '../xwzcxt/map/mapInfo.html?cIserror=1&&extype=1';
	}
	else if(type=='11'){
		url = '../xwzcxt/map/mapInfo.html?cFinalStatus=2&&extype=1';
	}
	else if(type=='12'){
		url = '../xwzcxt/map/mapInfo.html?cFinalStatus=0&&extype=1';
	}
	else if(type=='22'){
		url = '../xwzcxt/task/task.html?ivalue=22&&extype=1';
	}
	
	fastDev.create('Window', {
		title : title[type],
		src : url,
		width : '80%',
		height : '80%',
		allowResize : false
	});
	showInformation(sortType);
}
function showErrDet(){
	if($("#errDet").css("display") == "none"){
		$("#errDetzk").attr("size",5);
		$("#errDetzk").text("-");
	}
	else{ $("#errDetzk").text("+");
	     $("#errDetzk").attr("size",3.5);
	    }
		
	$("#errDet").slideToggle("slow");
	updateInformation('1');
	
}
function showTodo(){
	if($("#todoDet").css("display") == "none"){
		$("#todoDetzk").attr("size",5);
		$("#todoDetzk").text("-");
	}
	else{ $("#todoDetzk").text("+");
	     $("#todoDetzk").attr("size",3.5);
	    }
		
	$("#todoDet").slideToggle("slow");
	updateInformation('0');
	
}
function showTaskDet(){
	if($("#taskDet").css("display") == "none"){
		$("#taskDetzk").attr("size",5);
		$("#taskDetzk").text("-");
	}
	else{ $("#taskDetzk").text("+");
	     $("#taskDetzk").attr("size",3.5);
	    }
	$("#taskDet").slideToggle("slow");
	updateInformation('2');
}
function showVerifyDet(){
	if($("#verifyDet").css("display") == "none"){
		$("#verifyDetzk").attr("size",5);
		$("#verifyDetzk").text("-");
	}
	else{ $("#verifyDetzk").text("+");
	     $("#verifyDetzk").attr("size",3.5);
	    }
	$("#verifyDet").slideToggle("slow");
	updateInformation('3');
}
function showEvalDet(){
	if($("#evalDet").css("display") == "none"){
		$("#evalDetzk").attr("size",5);
		$("#evalDetzk").text("-");
	}
	else{ $("#evalDetzk").text("+");
	     $("#evalDetzk").attr("size",3.5);
	    }
	$("#evalDet").slideToggle("slow");
	updateInformation('4');
}
</script>
</head>

<body class="ui-layout-body ui-layout-body-bg">
<div class="ui-layout-head">
	<div class="ui-layout-logo"></div>
	<h1>长沙卷烟厂过程行为支持系统</h1>
	<div class="ui-layout-menu">
		<div class="ui-layout-welcome">
			<img src="fastdev/themes/default/images/ico/user.gif"/>
			<span>欢迎您：</span>
			<span class="ui-text-decoration ui-black"><%=displayName%></span>
			<span>[<a onclick="modifypsw()">修改密码</a>]</span>
			<img src="fastdev/themes/default/images/ico/exit.gif"/>
			<span><a onclick="logout()">退出系统</a></span>
			<span><a target="_blank" onclick="feedback('<%=displayCode%>')">意见反馈</a></span>
			<!-- <span><a href="download.action"><font color='black'>用户手册</font></a></span> -->
			<span><a href="helpUser.rar"><font color='black'>用户手册</font></a></span>
		</div>
		<div class="ui-layout-nav2">
			<div class="ui-layout-nav2-contain">
				<ul id="menuPanel" onclick="loadMenu(event)" class="ui-layout-nav2-tab"></ul>
			</div>
		</div>
	</div>
</div>

<div class="ui-layout">
	<div class="ui-layout-left" style="width:14.5%;height:99%;">
		<div id="example1">
			<div itype="Navigation" container="example1" id="nav" initSource="resource_showMenu.action" saveInstance="true" width="100%" onItemClick="openTabs()" onAfterInitRender="onAfterInitRender()"></div>
		</div>
	</div>
	<div class="ui-layout-center" style="width:85.5%;height:99%;top:0;left:15%;">
		<div id="tabBox"></div>
	</div>
	<div class="ui-layout-right" style="display:none"></div>
	<div class="ui-layout-handle-left ui-resizable-handle-c" style="display:block;left:14.5%;height:450px;top:0px;">
		<div class="ui-layout-arrow-left"></div>
	</div>
	<div id="rightIcon" style="margin-top: 150px;">
		<img src="../images/title.png" onmouseover="showInformation()" />
		<div id="information">
			<div id="detailTop"></div>
			<div id="detail">
			<a  onclick="showTodo()" class="ui-nav-header" style="margin-right:10px"><font id="todoDetzk" size="3.5">+</font> 
			  <font size="3"><span class="title_span" id="test11">待办提示</span></font></a>
			 <div id=todoDet style="display:none;margin-bottom: 5px;">
				<ul>
					<li><a href="javascript:void(0)">任务执行</a><span href="javascript:void(0)" id="informationtodo" onclick="showInformationGrid('todo','0');"></span></li>
					<li><a href="javascript:void(0)" >任务验证</a><span href="javascript:void(0)" id="informationcheck" onclick="showInformationGrid('check','0');"></span></li>
					<li><a href="javascript:void(0)" >任务评价</a><span href="javascript:void(0)" id="informationcomment" onclick="showInformationGrid('comment','0');"></span></li>
				</ul>
			</div>
			 <a  onclick="showErrDet()" class="ui-nav-header" style="margin-right:10px"><font id="errDetzk" size="3.5">+</font> 
			  <font size="3"><span class="title_span" id="test11">点检异常信息</span></font></a>
			 <div id=errDet style="display:none;margin-bottom: 5px;">
				<ul>
					<li><a href="javascript:void(0)">点检逾期</a><span href="javascript:void(0)" id="information1" onclick="showInformationGrid('1','1');"></span></li>
					<li><a href="javascript:void(0)" >点检异常</a><span href="javascript:void(0)" id="information2" onclick="showInformationGrid('2','1');"></span></li>
					<li><a href="javascript:void(0)" >整改逾期</a><span href="javascript:void(0)" id="information11" onclick="showInformationGrid('11','1');"></span></li>
					<li><a href="javascript:void(0)" >整改未完成</a><span href="javascript:void(0)" id="information12" onclick="showInformationGrid('12','1');"></span></li>
				</ul>
			</div>	
			 <a  onclick="showTaskDet()" class="ui-nav-header" style="margin-right:10px"><font size="3.5" id="taskDetzk">+</font><font size="3"> <span class="title_span" >任务异常信息</span></font></a>
			 <div id="taskDet" style="display:none;margin-bottom: 5px;">
				<ul>
					<li><a href="javascript:void(0)" >任务执行逾期</a><span href="javascript:void(0)" id="informationworklate" onclick="showInformationGrid('worklate','2');"></span></li>
<!-- 					<li><a href="javascript:void(0)" >任务未执行</a><span href="javascript:void(0)" id="information2" onclick="showInformationGrid('2','2');"></span></li> -->
					<li><a href="javascript:void(0)" >任务异常</a><span href="javascript:void(0)" id="informationworkerror" onclick="showInformationGrid('workerror','2');"></span></li>
				</ul>
			</div>
			 <a  onclick="showVerifyDet()" class="ui-nav-header" style="margin-right:10px"><font size="3.5" id="verifyDetzk">+</font><font size="3"> <span class="title_span" >验证异常信息</span></font></a>
			 <div id=verifyDet style="display:none;margin-bottom: 5px;">
				<ul>
					<li><a href="javascript:void(0)" >验证逾期</a><span href="javascript:void(0)" id="informationchecklate" onclick="showInformationGrid('checklate','3');"></span></li>
<!-- 					<li><a href="javascript:void(0)" >验证未执行</a><span href="javascript:void(0)" id="information2" onclick="showInformationGrid('2','3');"></span></li> -->
					<li><a href="javascript:void(0)" >验证异常</a><span href="javascript:void(0)" id="informationcheckerror" onclick="showInformationGrid('checkerror','3');"></span></li>
				</ul>
			</div>		
			 <a  onclick="showEvalDet()" class="ui-nav-header" style="margin-right:10px"><font size="3.5" id="evalDetzk">+</font><font size="3"> <span class="title_span" >评价异常信息</span></font></a>
			 <div id=evalDet style="display:none;margin-bottom: 5px;">
				<ul>
					<li><a href="javascript:void(0)" >评价逾期</a><span href="javascript:void(0)" id="informationcommentlate" onclick="showInformationGrid('commentlate','4');"></span></li>
<!-- 					<li><a href="javascript:void(0)" >评价未执行</a><span href="javascript:void(0)" id="information2" onclick="showInformationGrid('2','4');"></span></li> -->
					<li><a href="javascript:void(0)" >评价异常</a><span href="javascript:void(0)" id="informationcommenterror" onclick="showInformationGrid('commenterror','4');"></span></li>
				</ul>
			</div>	
			
			</div>
			<div id="detailBottom"></div>
		</div>
	</div>
</div>

<div class="ui-layout-footer">Copyright©www.talkweb.com.cn</div>
<iframe src="about:blank" style="display:none;" id="dc"></iframe>
<span id="smartCookieId" style="display:none;"><%=smartbiCookie%></span>
</body>
</html>