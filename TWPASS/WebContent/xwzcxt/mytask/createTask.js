var url = window.location.pathname;
var path = url.substring(0, url.lastIndexOf("/") + 1);
var condition = null;
var dialog =null;
function taskStart() {
	createWindow({
		src : path + "newTask.html",
		//src:'/TWPASS/xwzcxt/mytask/newTask.html',
		title : "发起任务",
		height : "500",
		width : "650",
		buttons : [ {
			text : '提交',
			iconCls : 'icon-save',
			onclick : function(event, win, cwin, fast) {
				
				fastDev.confirm("是否确定提交？","发起任务提示",function(result){
					if(result){
						cwin.doSave();
						window.fastDev.tips("任务发起成功！");
						win.close();
						searchForm();
					}
				});
			}
		} ]
	});
	
}

function createWindow(o) {
	var config = fastDev.apply({
		width : "900",
		height : "500",
		allowResize : false,
		inside : false,
		showMaxBtn : false
	}, o || {});

	(config.buttons = config.buttons || []).push({
		text : "关闭",
		iconCls : "icon-close",
		onclick : function(e, win) {
			win.close();
		}
	});
	dialog = fastDev.create('Window', config);
}


function onBeforeReady(){
	this.setOptions({
		initSource : "waithandTask_queryNewTask.action"
	});	
}

function searchForm() {
	var fm = fastDev.getInstance('checkform');
	condition = fm.getItems();
	
	refreshDatagrid();
}

function refreshDatagrid() {
	fastDev.getInstance('grid1').refreshData(condition);
}

function closeDialog(){
	if(dialog) {
		dialog.close();
		dialog = null;
	}
}

function refreshData(msg){
	fastDev.alert(msg);
	refreshDatagrid();
}

function submitHiddenForm(value){
	var url = window.location.href;
	var p_url = url.substring(0, url.lastIndexOf("/"));
	var p_path = p_url.substring(0, p_url.lastIndexOf("/")+1);
	fastDev.create("Dialog", {
		height:500,
		width:900,
		inside:false,
		showMaxBtn:false,
	    title:"任务详情",
	    src : p_path+"task/taskDetails.html?edit=details&taskId="+ value,
		buttons : [{
			text : "关闭",
			align : "center",
			iconCls : "icon-close",
			onclick : function(event, that, win, fast) {
				// 按钮点击事件回调的作用域(this)指向当前按钮控件实例
				// 参数that为当前对话框控件实例
				that.close();
			}
		}]
	});
}

function toTaskKindName(value){
	switch(value){
	case '1':return "工作任务";
	case '2':return "异常信息";
	case '3':return "工作安排";
	}
}

function onAfterInitRender(){
	var body = fastDev(window).height();
	this.setHeight(body - 70);
	showResourceModes("fqrw");
}
