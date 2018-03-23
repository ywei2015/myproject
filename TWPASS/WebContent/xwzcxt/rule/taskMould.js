var request = fastDev.Browser.getRequest();
//刷新条件
var condition=null;
var appDialog;
var currentposiid = null;
var currentposiname = "";
var currenttype = -1;


/**
 * 关闭对话框
 */
function closeDialog() {
	if(appDialog) {
		appDialog.close();
		appDialog = null;
	}
}	

function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body-60);
}
function operRenderer() {
	return ['<div style="width:200px;"><a href="javascript:void(0);" style="margin-left:5px; " class="btn" id="modify" name="modify">修改</a>',
	        '<div style="width:200px;"><a href="javascript:void(0);" style="margin-left:5px; " class="btn" id="delete" name="delete">删除</a>']
			.join('');
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

function doAdd(){
	/*var positiontree = fastDev.getInstance("orgpositiontree");
    var currentId = positiontree.getCurrentId();
    if(currentId=='') {*/
    if(currenttype!=1) {
    	fastDev.alert('请选择岗位！','信息提示','warning');
    	return false;
    }
    
    appDialog = fastDev.create("Dialog", {
		showMaxBtn:false,
		width :900,
		height:500,
	    title:"新增模板信息",
	    src : "addTaskMould.html?edit=add&postId="+currentposiid+"&posiname="+currentposiname,
		buttons : [{
			text : "保存",
			align : "center",
			iconCls : "icon-save",
			onclick : function(event, that, win, fast) {
				//fastDev.tips("你点击了保存按钮");
				// 按钮点击事件回调的作用域(this)指向当前按钮控件实例
				// 参数that为当前对话框控件实例
				win.doSubmit();
			}
		}, {
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
function doModify(data){
	
	appDialog = fastDev.create("Dialog", {
		showMaxBtn:false,
		width :900,
		height:500,
	    title:"调整模板信息",
	    src : "addTaskMould.html?edit=update&taskId="+data.c_tasktemplet_id+"&posiname="+currentposiname,
		buttons : [{
			text : "保存",
			align : "center",
			iconCls : "icon-save",
			onclick : function(event, that, win, fast) {
				//fastDev.tips("你点击了保存按钮");
				// 按钮点击事件回调的作用域(this)指向当前按钮控件实例
				// 参数that为当前对话框控件实例
				win.doSubmit();
			}
		}, {
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
function doBatchDelete(){
	var datagrid = fastDev.getInstance('grid1');
	var items = datagrid.getValue();
	if(items.length <=0 ) {
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	} else {
		fastDev.confirm("确认是否删除选择的" + items.length + "条记录？", "信息提示", function(result){
			if(result){
				var ids = [];
				for(var i = 0; i < items.length; i++) {
					ids.push(items[i].c_tasktemplet_id);
				}
				doSingleDelete(ids.join(","));
			}
		});
	}
}
function doSingleDelete(cId) {
	var proxy = fastDev.create('Proxy', {
		action : 'delete_deleteInfo.action?action=2'
	});
	proxy.save({deleteID:cId}, function(result){
		fastDev.alert(result.msg, '信息提示', result.status, function(){
			if(result.status == 'ok') {
				refreshDatagrid();
			}
		});
	});
}
function doDelete(data) {
	fastDev.confirm("确认是否删除？", "信息提示", function(result){
		if(result){
			doSingleDelete(data.c_tasktemplet_id);
		}
	});	
}
/**
 * 过滤信息
 */
function doSearch() {
	var fm = fastDev.getInstance('checkform');
	condition = fm.getItems();
	
	refreshDatagrid();
}
/**
 * 重置表单数据
 */
function doReset() {
	fastDev.getInstance('checkform').cleanData();
}
/**
 * 刷新datagrid
 * @param o
 */
function refreshDatagrid() {
	fastDev.getInstance('grid1').refreshData(condition);
}

var currentActivityOP=null;
var reloadActivity = function(){
	//只在没有做资源的增加，修改操作才做datagrid的刷新操作
	if(!currentActivityOP){
	    var activityTree = fastDev.getInstance("activityTree");
	    var currentId = activityTree.getCurrentId();
	    if(getActNodeId(currentId)=='') 
	    	return;
	    else {
	    	var grid1 = fastDev.getInstance("grid1");
	    	grid1.addInitReqParam({"taskMouldPojo.c_actnode_id": getActNodeId(currentId)});
	    	grid1.refreshData(true);
	    }
	    
	}	
};

function getActNodeId(value) {
	if (value.indexOf('_')<0)
	    return '';
	else 
		return value.split('_')[0];
	    
}


var onNodeClick = function(){
	//只在没有做资源的增加，修改操作才做datagrid的刷新操作
	if(!currentActivityOP){
	    var positiontree = fastDev.getInstance("orgpositiontree");
	    var currentId = positiontree.getCurrentId();
    	var grid1 = fastDev.getInstance("grid1");
    	grid1.addInitReqParam({"taskMouldPojo.c_positionid": currentId});
    	grid1.refreshData(true);
	}	
};

function onAfterDataRender(){
	var obj = fastDev(this.elems[0]);
	obj.height(obj.parent().height()-32);
}

var onTreeNodeClick = function(e,id,val){
	currentposiid = id;
	currentposiname = val;
	//只在没有做资源的增加，修改操作才做datagrid的刷新操作
	var element=e.srcElement||e.target;
	var fullClassName=element.className;
	//var dataType="";
	if(fullClassName.indexOf("font1")>=0){
		//dataType="组织"; 
		currenttype = 0;
		console.info("组织");
	}
	else if(fullClassName.indexOf("font2")>=0){
		//dataType="岗位";
		currenttype = 1;
		console.info("岗位");
		//fastDev.tips("id为"+id+",val为"+val+"的"+dataType+"...");
    	var grid1 = fastDev.getInstance("grid1");
    	grid1.addInitReqParam({"taskMouldPojo.c_positionid": currentposiid});
    	grid1.refreshData(true);
	}
};
