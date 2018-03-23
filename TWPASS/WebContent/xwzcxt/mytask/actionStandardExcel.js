var request = fastDev.Browser.getRequest();
var g_event=request['event'];//0-add或者1-edit

var operTpl = '&nbsp;<a name="del">删除</a>';
var fileuuid="";

function chooseExcelError(file, code, msg){
	if (code ==1){
		fastDev.tips("仅能选择xls或xlsx文件");
	}
}
function onUploadExcelSuccess(file, response){
	fastDev.alert(response.msg,"提示信息");
}
function onUploadExcelFail(file, response){
	fastDev.tips("文件上传失败，请检查网络！");
}

function importAction(){
	//op = 'add';
	openImportPage();
}

function addPoint(){
	op = 'addpoint';
	var positiontree = fastDev.getInstance("actiontree");
    var currentId = positiontree.getCurrentId();
    if(currentId=='') {
    	fastDev.alert('请选择流程节点！','信息提示','warning');
    	return false;
    }
    
	openDetailsPage(op,currentId);
}

function openDetailsPage(event,id){
	var src = "actionStandardDetail.html?edit=add&actid="+id ;
	//var src = "actionStandardDetail.html";
	var title = "增加活动标准";
	if(event == "modify"){
		title = "修改流程节点";
		src = "actionStandardDetail.html?edit=modify&actnodeid="+id ;
	}
		
	createWindow({
		src : src,
		title : title,
		width : "800",
		height: "600",
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(event, win, childWin, fast) {
				childWin.doSave();
				/*var form=fd.getInstance("addActionForm");
				var data = appDialog.getAllGridValue();
				console.info(data);
				form.setOptions({"otherSubmitData" : fastDev.Util.JsonUtil.parseJson(data)});
				form.submit();	*/	
			}
		}]		
	});
}

function openImportPage(){
	var src = "actionStandardImport.html";
	var title = "导入活动标准";
	
		
	createWindow({
		src : src,
		title : title,
		width : "625",
		height: "390",
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(event, win, childWin, fast) {
				childWin.doSave();
				/*var form=fd.getInstance("addActionForm");
				var data = appDialog.getAllGridValue();
				console.info(data);
				form.setOptions({"otherSubmitData" : fastDev.Util.JsonUtil.parseJson(data)});
				form.submit();	*/	
			}
		}]		
	});
}

function doModify(data){
	
	openDetailsPage('modify',data.c_flow_id);
}


function createWindow(o) {
	var config = fastDev.apply({
		width : "500",
		height : "300",
		showMaxBtn : false,
		allowResize : false
	}, o || {});
	(config.buttons = config.buttons || []).push({
		text : "关闭",
		iconCls : "icon-close",
		onclick : function(e,win) {
			win.close();
		}
	});		
	dialog = fastDev.create('Window', config);
}

function onRowClick(event,rowindex,data) {		
	
	var target = event.target;
	var name = target['name'];
	if(name) {
		name = fastDev.Util.StringUtil.capitalize(name);
		//console.log(name);
		if(window['do' + name]) {
			window['do' + name].call(window, data);
		}
	}
}


function operRenderer() {
	return ['<div style="width:200px;"><a href="javascript:void(0);" style="margin-left:5px; " class="btn" id="modify" name="modify">修改</a>',
	        '<div style="width:200px;"><a href="javascript:void(0);" style="margin-left:5px; " class="btn" id="delete" name="delete">删除</a>']
			.join('');
}
