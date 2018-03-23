var request = fastDev.Browser.getRequest();
var g_event=request['event'];//0-add或者1-edit

var operTpl = '&nbsp;<a name="del">删除</a>';
var fileuuid="";
/*
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
*/
/**
 * 流程节点树的操作
 */

var currentActionId, currentName;
var op = null;
var currentActionId, parentId, parentText;
var appDialog;

function getCurrentActionId() {
	var tree = fastDev.getInstance('actiontree');
	for ( var i = 0; i < tree.dataset.records.length; i++) {
		if (tree.dataset.records[i].id == currentActionId) {
			currentActionId = tree.dataset.records[i].id;
		}
	}
}

function onActionTreeNodeClick(event, id, value) {
	//alert('asdfas');
	importAction();
	/*
	currentActionId = id;
	getCurrentActionId();
	parentId = this.getParentid(id);
	parentText = this.getValByid(parentId);
	currentName = this.getValByid(id);
	// refreshUppositiontree();
	// fastDev.getInstance("position.posiName").getGlobal().box.prop("value",
	// parentText);
	op = 'view';
	//refreshDataGrid();
	//alert(currentName);
	//document.getElementById("treepath").innerText=currentName;

*/
	
}

function importAction(){
	//op = 'add';
	openImportPage();
}

function relate(){
	//alert('asdf');
	fastDev.alert('岗位活动[灭火器检查]与流程节点[质量]进行关联','信息提示','warning');
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
	var src = "actionStandardDetail2.html?edit=add&actid="+id ;
	//var src = "actionStandardDetail.html";
	var title = "增加活动标准";
	if(event == "modify"){
		title = "修改流程节点";
		src = "actionStandardDetail2.html?edit=modify&actnodeid="+id ;
	}
		
	createWindow({
		src : src,
		title : title,
		width : "1000",
		height: "800",
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
	var src = "actionmanagementprocess.html";
	var title = "查看流程节点信息";
	
		
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
	return '<div style="width:200px;"><a href="javascript:void(0);" style="margin-left:5px; " class="btn" id="modify" name="modify">查看详情</a>';
}
