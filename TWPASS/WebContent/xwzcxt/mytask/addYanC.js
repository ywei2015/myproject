// 刷新条件
var request = fastDev.Browser.getRequest();
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
		initSource :'acyanc_getYImportData.action'  
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

function operLinkKao(value){
	return  [ '<div style="width:200px;"><span style="margin-left:5px; color:#ff7f24">'
	  		+ value + '</span></div>' ].join('');
}
//删除
function deleteMany(){
	var items=fastDev.getInstance("grid").getValue();
	 if(!items){
	    	fastDev.tips("请选择数据！");
	    	return;
	    }
	 console.info(items[0]);
		fastDev.confirm('是否确定删除选择行的数据？','数据删除提示',function(result){
			if(result){
				   id="";
				   for(var i=0;i<items.length;i++){
					   	id+=items[i].c_id+",";
					   
				   }
			 
		   	fastDev.post("acyanc_deleteImportData.action",{
				success:function(data){
				   fastDev.tips(data.msg);
				   fastDev.getInstance("grid").refreshData(false);
				},
			    data:{'ids':id}
			});
				  
				   }
		});
}

//导入
function doImportAction() {
	/*fastDev.post("acyanc_importYanCNode.action",{
		success:function(dat){
			fastDev.getInstance('grid').refreshData(false);
		}
	});*/
	dialog = fastDev.create('Dialog', {
		width : 500,
		height : 230,
		inside : false,
		showMaxBtn : false,
		title : '烟虫ExceL导入',
		allowResize : false,
		src : '../xwzcxt/standardlibrary/actionYanCImport.html?cAreaidj='+cAreaidj,
		//src:'/XWGL/xwzcxt/standardlibrary/actionYanCImport.html?cAreaidj='+cAreaidj,
		buttons : [{
			text : '导入',
			align : 'center',
			iconCls : 'icon-save',
			onclick : function(event, win, cwin, fast) {
				var falg=cwin.checkISNull();
				console.info(falg);
				if(falg){
				this.disable();
				cwin.processBar(this);
				var form = fast.getInstance("checkForm");
				form.submit();
				}
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
	//var url= "/XWGL/xwzcxt/task/task_templet/templetSelectPositionForSearch.html?orgtype=7&module=RWCX&cPid=" + cPid;
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
	//var url= "/XWGL/xwzcxt/task/task_templet/templetSelectArea.html?cAreaid=" + cAreaid;
	console.info(url);
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
				var pids = cArea.getChkedIds("onlyLeafValue");
				 console.info(pids);
				if ("" == pids) {
					window.alert("请选择区域！");
				}else{
				if (pids) {
					setArea(pids, cArea.getValsByids(cArea.getChkedIds("allchkValue")));
					//setArea(pids, cArea.getValsByids(cArea.getChkedIds("onlyLeafValue")));
					win.close();
				} 
				else {
					var cAreaName = cArea.getCurrentTxt();
					if ("1000100000001" != cAreaid) {
						var id = cArea.getParentid(cAreaid);
						while ("1000100000001" != id) {
							cAreaName = cArea.getValByid(id) + cAreaName;
							id = cArea.getParentid(id);
						}
						setArea(cAreaid, cAreaName);
						win.close();
					}else window.alert("请选择区域！");
				};
		}}}, 
			{id : 'clear',
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
var cAreaNamej;
var cAreaidj;
function setArea(pids, cAreaName) {
	cAreaNamej=cAreaName;
	cAreaidj=pids;
	console.info(cAreaNamej);
	console.info(cAreaidj);
	fastDev.getInstance("cAreaId").setValue(pids);
	fastDev.getInstance("cAreaName").setValue(cAreaName);
}
function refreshDatagrid() {
	fastDev.getInstance('grid').refreshData();
}

function closeDialog() {
	if (dialog) {
		dialog.close();
		dialog = null;
	}
}
function exportZuoyeTemplet() {
	var quyname=fastDev.getInstance("cAreaName").getValue();
	console.info(quyname);
	if(quyname!=""){
	fastDev.post("acyanc_outYanCMb.action",{
		data:{
			'cAreaidj':cAreaidj,
			'cAreaNamej':cAreaNamej
		},
		success:function(dat){
			console.info(dat);
			if(dat=="success"){
					fastDev("#test").prop("src","ycdr.xls");
				
			}
			else {
				window.fastDev.tips("模板下载失败请稍候再试");  
			}
		}
	});
}else 
	window.fastDev.tips("请选择执行区域");

}
