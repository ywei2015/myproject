// 刷新条件
var condition = null;
var loadingWindow = null;
var ivalue = getUrlParam('ivalue');
var extype = getUrlParam('extype');
var userCode = getUrlParam('userCode');
var type = getUrlParam('type');
window.onload = function(){ 
	  if(extype!=null&&extype==1){
	 if(ivalue!=null&&ivalue=='1'){	//执行逾期  
	  fastDev.getInstance("cTimeStatus").setValue(1);
	  fastDev.getInstance("userCode").setValue(userCode);
	  doSearch();
	  }
	 else if(ivalue!=null&&ivalue=='2'){//任务异常
		  fastDev.getInstance("userCode").setValue(userCode);
		  fastDev.getInstance("errortask").setValue('yes');
		  doSearch();
	  }
	  else if(ivalue!=null&&ivalue=='3'){//验证逾期
		  fastDev.getInstance("userCode").setValue(userCode);
		  fastDev.getInstance("cTimeStatus").setValue(2);
		  doSearch();
	  }
	  else if(ivalue!=null&&ivalue=='45'){//验证异常
		  fastDev.getInstance("userCode").setValue(userCode);
		  fastDev.getInstance("checkerror").setValue('yes');
		  fastDev.getInstance("extype").setValue(1);
		  doSearch();
	  }
	  else if(ivalue!=null&&ivalue=='4'){//评价逾期
		  fastDev.getInstance("userCode").setValue(userCode);
		  fastDev.getInstance("cTimeStatus").setValue(3);
		  doSearch();
	  }
	  else if(ivalue!=null&&ivalue=='55'){//评价异常单点
		  fastDev.getInstance("userCode").setValue(userCode);
		  fastDev.getInstance("commenterror").setValue('yes');
		  doSearch();
	  }
	  }
	  else if(type!=null&&type==1){
			 fastDev.getInstance("cTimeStatus").setValue(1);
			  doSearch();
		 }
	  else if(type!=null&&type==4){//评价逾期
			 fastDev.getInstance("cTimeStatus").setValue(3);
			  doSearch();
		 }
	  else if(type!=null&&type==5){//评价异常推板
			 fastDev.getInstance("commenterror").setValue('yes');
			  doSearch();
		 }
	}

function exportXLS() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "checkform",
		text : "导出中..."
	});
	var formData = fastDev.getInstance("checkform").getItems();
	fastDev.create("Proxy", {
		action : "task_new/exportTaskExcelAction.action"
	}).save(formData, function(data) {
		fastDev("#dc").prop("src", data.url);
		loadingWindow.close();
	});	
}

function getUrlParam(name) {
	var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)'); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); //匹配目标参数
	if (null != r) {
		return unescape(r[2]);
	} else {
		return null; //返回参数值
	}
}

function onBeforeReady() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "checkform",
		text : "加载中..."
	});
	if(extype==1&&ivalue==45){
		this.setOptions({
			initSource : 'task_new/getAllTaskAction.action?extype=1&&checkerror=yes'
		});	
	}else{
	this.setOptions({
		initSource : 'task_new/getAllTaskAction.action'
	});
	}
}

function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body - 100);
	showResourceModes('RWCX');
	loadingWindow.close();
}

function doSearch() {
	var form = fastDev.getInstance("checkform");
	condition = form.getItems();
	fastDev.getInstance('grid_task').refreshData(condition);
}

function doReset() {
	fastDev.getInstance('checkform').cleanData();
	condition = null;
}

function operLinkRenderer(value) {
	return [ '<a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="openTaskDetails" name="openTaskDetails">' + value + '</a>' ].join('');
}

function operLinkStatusRenderer(value) {
	return [ '<a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="openTaskStatusDetails" name="openTaskStatusDetails">' + value + '</a>' ].join('');
}

function doOpenTaskStatusDetails(data) {
	var url= "../xwzcxt/task/taskStatus.html?dg_seq=" + data.dg_seq + "&cTaskId=" + data.C_TASK_ID;
	if(extype==1&&userCode!=null){
		url="../task/taskStatus.html?dg_seq=" + data.dg_seq + "&cTaskId=" + data.C_TASK_ID;
	}
	fastDev.create("Dialog", {
		height : 352,
		width : 1000,
		inside : false,
		showMaxBtn : false,
		allowResize : false,
		title : "任务状态",
		src : "../xwzcxt/task/taskStatus.html?dg_seq=" + data.dg_seq + "&cTaskId=" + data.C_TASK_ID,
		buttons : [ {
			text : "关闭",
			align : "center",
			iconCls : "icon-close",
			onclick : function(event, that, win, fast) {
				// 按钮点击事件回调的作用域(this)指向当前按钮控件实例
				// 参数that为当前对话框控件实例
				that.close();
			}
		} ]
	});
}

function doOpenTaskDetails(data) {
	var url = "../xwzcxt/task/taskDetails.html?edit=details&taskId=" + data.C_TASK_ID;
	//var url="../xwzcxt/standardlibrary/actionStandardAdd.html?taskId="+ data.C_TASK_ID;
	if(extype==1&&userCode!=null){
		var type2;
		if(ivalue=='1'){
			type2=21;
		}
		else if(ivalue=='2'){
			type2=23;
		}
		else if(ivalue=='3'){
			type2=31;
		}
		else if(ivalue=='4'){
			type2=41;
		}
		else if(ivalue=='45'){
			type2=33;
		}
		else if(ivalue=='55'){
			type2=43;
		}
		//url="../task/taskDetails.html?edit=details&taskId=" + data.C_TASK_ID
		fastDev.get("task_new/updateMatterdetail.action?userCode="+userCode+"&&taskId="+data.C_TASK_ID+"&&type="+type2, {
			success : function (data) {
//				if ("登录超时，请重新登录" == data) {
//					fastDev.alert(data, "信息提示", "warning", function() {
//						clearInterval(update);
//						top.location = "../component/login.html";
//					});
//				} else {
////	 				alert(data);
//					$("#informationchecklate").html("(" +data.rwyzyq  + ")");
//				
//				} 
			}
		});
	}
	fastDev.create("Dialog", {
		height : 500,
		width : 900,
		inside : false,
		showMaxBtn : false,
		allowResize : false,
		title : "任务详情",
		src : url,
		buttons : [ {
			text : "关闭",
			align : "center",
			iconCls : "icon-close",
			onclick : function(event, that, win, fast) {
				// 按钮点击事件回调的作用域(this)指向当前按钮控件实例
				// 参数that为当前对话框控件实例
				that.close();
			}
		} ]
	});
}

function onRowClick(event, rowindex, data) {
	var target = event.target;
	var name = target['name'];
	if (name) {
		name = fastDev.Util.StringUtil.capitalize(name);
		if (window['do' + name]) {
			window['do' + name].call(window, data);
		}
	}
}

function onchange(value) {
	var cTaskType = fastDev.getInstance("cTaskType");
	cTaskType.clean(true);
	cTaskType.initRefresh({
		"url" : "task_new/getTaskTypeListAction.action",
		"urlParam" : {"cTaskKind" : value}
	});
	if (value) {
		cTaskType.enable();
	} else {
		cTaskType.disable();
	}
}

function onchange1(value) {
	cTimeStatus = fastDev.getInstance("cTimeStatus");
	if (value) {
		cTimeStatus.clean();
		cTimeStatus.disable();
	} else {
		cTimeStatus.clean();
		cTimeStatus.enable();
	}
}

function onchange2(value) {
	cStatus = fastDev.getInstance("cStatus");
	if (value) {
		cStatus.clean();
		cStatus.disable();
	} else {
		cStatus.clean();
		cStatus.enable();
	}
}

function onSelectPosition(e) {
	var element = e.srcElement || e.target;
	fastDev.confirm("是否需要修改当前部门组织？", "信息提示", function(result) {
		if (result) {
			doSelectPosition(element.id);
		}
	});
}

function doSelectPosition(name) {
	var id = name.substring(0, name.indexOf("Name")) + "id";
	var cPid = fastDev.getInstance(id).getValue();
	var url ="../xwzcxt/task/task_templet/templetSelectPositionForSearch.html?orgtype=7&module=RWCX&cPid=" + cPid;
	if(extype==1&&userCode!=null){
		 url ="../task/task_templet/templetSelectPositionForSearch.html?orgtype=7&module=RWCX&cPid=" + cPid;
	}
	fastDev.create('Window', {
		width : 300,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : "选择部门组织",
		allowResize : false,
		src : url,
		buttons : [ {
			id : 'ok',
			text : '确定',
			align : 'center',
			iconCls : 'icon-save',
			onclick : function(event, win, childWin, fast) {
				var cPosition = fast.getInstance("cPosition");
				var pids = cPosition.getChkedIds("onlyLeafValue");
				if (pids) {
					setPosition(name, pids, cPosition.getValsByids(cPosition.getChkedIds("allchkValue")));
					win.close();
				} else {
					window.alert("请选择部门组织！");
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
	var id = name.substring(0, name.indexOf("Name")) + "id";
	fastDev.getInstance(id).setValue(cPid);
	fastDev.getInstance(name).setValue(cPidName);
}

function onSelectArea() {
	fastDev.confirm("是否需要修改当前区域？", "信息提示", function(result) {
		if (result) {
			doSelectArea();
		}
	});
}

function doSelectArea() {
	var cAreaid = fastDev.getInstance("cAreaId").getValue();
	var url ="../xwzcxt/task/task_templet/templetSelectArea.html?cAreaid=" + cAreaid;
	if(extype==1&&userCode!=null){
		url="../task/task_templet/templetSelectArea.html?cAreaid=" + cAreaid;
	}
	fastDev.create('Window', {
		width : 300,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : "选择区域",
		allowResize : false,
		src : url,
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
		}, {
			id : 'clear',
			text : '清空',
			align : 'center',
			iconCls : 'icon-reset',
			onclick : function(event, win) {
				setArea("", "");
				win.close();
			}
		} ]
	});
}

function setArea(cAreaid, cAreaName) {
	fastDev.getInstance("cAreaId").setValue(cAreaid);
	fastDev.getInstance("cAreaName").setValue(cAreaName);
}

function batchDeleteObject() {
	var datagrid = fastDev.getInstance('grid_task');
	var items = datagrid.getValue();
	if (items.length <= 0) {
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	} else {
		fastDev.confirm("确认是否删除选择的" + items.length + "条记录？", "信息提示", function(result) {
			if (result) {
				var ids = [];
				for (var i = 0; i < items.length; i++) {
					ids.push(items[i].C_TASK_ID);
				}
				fastDev.create("Proxy", {
					action : "task_new/deleteTaskByIdAction.action"
				}).save({
					id : ids.join(",")
				},function(data) {
					fastDev.alert(data.msg, "信息", data.status, function() {
						if (data.status == 'ok') {
							datagrid.refreshData(condition);
						}
					});
				});
			}
		});
	}
}