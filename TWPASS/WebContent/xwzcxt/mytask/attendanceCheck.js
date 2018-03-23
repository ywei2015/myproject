// 刷新条件
var request = fastDev.Browser.getRequest();
var map = request['map'];
var condition = null;
var loadingWindow = null;

function exportXLS() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "checkform",
		text : "导出中..."
	});
	var formData = fastDev.getInstance("checkform").getItems();
	fastDev.create("Proxy", {
		action:'attendance_exportAttendance.action'
		//action : 'taskVerifyAndComment/taskVerifyAndComment_exportVerify.action?c_task_kind='+request['c_task_kind']
	}).save(formData, function(data) {
		fastDev("#dc").prop("src", data.url);
		loadingWindow.close();
	});	
}


function onBeforeReady() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "checkform",
		text : "加载中..."
	});
	this.setOptions({
		initSource : 'attendance_getAttendanceData.action?map='+map
	});
}

function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body - 95);
	showResourceModes('RWGLWH');
	loadingWindow.close();
}

/**
 * 过滤信息
 */
function doSearch() {
	var form = fastDev.getInstance("checkform");
	condition = form.getItems();
	condition['isFirstTime']=1;
	fastDev.getInstance('grid').refreshData(condition);
}

/**
 * 重置表单数据
 */
function doReset() {
	fastDev.getInstance('checkform').cleanData();
	condition = null;
}

function operLinkRenderer(value) {
	return [ '<div style="width:200px;"><a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="openTaskDetails" name="openTaskDetails">'
		+ value + '</a></div>' ].join('');
}
function operLinkKao(value){
	return  [ '<div style="width:200px;"><span style="margin-left:5px; color:#ff7f24">'
	  		+ value + '</span></div>' ].join('');
}

function doOpenTaskDetails(data) {
	 var url=window.location.href;
	    var index=-1;
	    for(var i=0;i<4;i++){
	    	index=url.indexOf("/",index+1);
	    }
	    url=url.substring(0,index);
	fastDev.create("Dialog", {
		height : 500,
		width : 900,
		inside : false,
		showMaxBtn : false,
		title : "考勤详情",
		src : url+"/xwzcxt/mytask/attendanceDetails.html?edit=details&c_userid=" + 
		data.c_userid+"&c_tag_ip="+data.c_tag_ip+"&c_attend_date="+data.c_attend_date,
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

//function onchange(value) {
//	var cTaskType = fastDev.getInstance("typeid");
//	cTaskType.disableItems("20");
//}

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

function timeRenderer(value){
	value=(value+"").replace("T"," ");
	return value;
}

function doKaoqin(){
	fastDev.confirm('是否确认考勤？', '信息提示', function(result){
		if (result) {
			fastDev.post("attendance_doKaoqin.action",{
				data:{
					"mac_addr":MacInfo()
				},
				success:function(dat){
					if(dat="success"){
					fastDev.tips("考勤成功");
					fastDev.getInstance('grid').refreshData();
					}
					else if(dat="登录超时，请重新登录"){
						window.fastDev.tips("登录超时，请重新登录！");  
					}
				}
			});
		}
	});
}
function MacInfo(){
    var locator =new ActiveXObject ("WbemScripting.SWbemLocator");
    var service = locator.ConnectServer(".");
    var properties = service.ExecQuery("Select * from Win32_NetworkAdapterConfiguration Where IPEnabled =True");
    var e =new Enumerator (properties);
    {
          var p = e.item();
         var mac = p.MACAddress;
    }
    return mac;
}
