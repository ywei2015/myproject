var request = fastDev.Browser.getRequest();
//通过action参数进行信息读取，以确认操作的对象为子项(1)、子项类别(2)、基础项(3)
var item = request['item'];
//刷新条件
var condition=null;
var checkDialog,tree,upTypeCode;
var delectAction = "task_deleteObjectByIds.action"; 
var datagrid;
var currentCondition = null;

var currentTypeId = 1;
var upTypeCode = 100000;
/**
 * 关闭对话框
 */
function closeDialog() {
	if(checkDialog) {
		checkDialog.close();
		checkDialog = null;
	}
}

var operTpl = '&nbsp;<a name="edit">修改</a>&nbsp;<a name="del">删除</a>';


function onNodeClick(event, id, value) {
	currentTypeId = id;
	
	for ( var i = 0; i < tree.dataset.records.length; i++) {
		if (tree.dataset.records[i].id == id) {
			upTypeCode = tree.dataset.records[i].c_objtype_code;
		}
	}
	//console.info(fastDev.getInstance('grid1'));
	refreshDatagrid();
	
}

function treeReady() {
	tree = this;

}
function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body-60);
}
function onBeforeReady(){	
	datagrid = this;
	this.setOptions({
		initSource : "basicXwgl_queryTaskByPage.action?action=6"
	});	 
};
 
function onRowClick(event,rowindex,data) {	
	switch(event.target.name){
	case "edit":
		editBasic(data.c_obj_id);
		break;
	case "del":
		deleteObjectById(data.c_obj_id);
		break;
	}
	
	/*var target = event.target;
	var name = target['name'];
	if(name) {
		name = fastDev.Util.StringUtil.capitalize(name);
		if(window['do' + name]) {
			window['do' + name].call(window, data);
		}
	}*/
}

function deleteObjectById(id){
	var proxy = fastDev.create('Proxy', {
		action : "basicAdd_deleteObjectById.action?cid="+id
	});
	proxy.save({deleteID:id}, function(result){
		fastDev.alert(result.msg, "信息提示", result.result, function() {
			if(result.result==0){
				refreshDatagrid();
			}
		});
	});
}

/*function doAddBasic(title){
	fastDev.create("Dialog", {
		showMaxBtn:false,
		width : "640",
		height : "200",
	    title : title,
	    src : "addBasic.html",
		buttons : [{
			text : "保存",
			align : "center",
			iconCls : "icon-save",
			onclick : function(event, that, win, fast) {
				var form=that.getInstance("addBasicForm");
				form.doSubmit();
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
}*/

function editBasic(id){
	//var datas = fastDev.getInstance("grid1").getValue();
	addBasic("edit",id);//datas[0].c_obj_id);
}

function addBasic(event,value) {
	var src = "addBasic.html?event="+event+"&objId="+value+"&objtypeId="+currentTypeId;
	var title = "新增作业对象";
	if(event == "edit"){
		//src = "addBasic.html?objId="+value;
		title = "修改作业对象";
	}
		
	createWindow({
		src : src,
		title : title,
		width : "625",
		height: "190",
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(e,win,cwin, fd) {
				var form=fd.getInstance("addBasicForm");
				form.submit();		
			}
		}]		
	});
}

function addObjType(){
	var url = "addType.html?Pid=" + currentTypeId + "&upTypeCode=" + upTypeCode;

	reqDialog = fastDev.create("Dialog", {
		width : "900px",
		height : "301px",
		top : "100px",
		showCollapseBtn : false,
		showMaxBtn : false,
		src : url
	});
}

function editObjType(){
	var url = "modifyType.html?id=" + currentTypeId;

	modifyDialog = fastDev.create("Dialog", {
		width : "900px",
		height : "301px",
		top : "100px",
		showCollapseBtn : false,
		showMaxBtn : false,
		src : url
	});
}

/*var delectAction = "ObjType_deleteNodesByIds.action";
function deleteObjType(){
	fastDev.confirm("是否确认删除当前选择记录", "确认", function(response) {
		if (response) {
			fastDev.get(delectAction, {
				data : {
					"ids" : currentTypeId
				},
				complete : function(rsp) {
					var type = rsp.status;
					type = type === "bad" ? "error" : type;
					fastDev.alert(rsp.msg, "提示信息", type);
					if (type !== "bad") {
						refreshTree();
					}
				}
			});
		}
	});
}*/

function createWindow(o) {
	var config = fastDev.apply({
		width : "640",
		height : "200",
		allowResize : false,
		showMaxBtn : false
	}, o || {});
	(config.buttons = config.buttons || []).push({
		text : "关闭",
		iconCls : "icon-close",
		onclick : function(e,win) {
			win.close();
		}
	});		
	appDialog = fastDev.create('Window', config);
}

/**
 * 批量删除信息
 */
function doBatchDeleteCheckModel() {
	var datagrid = fastDev.getInstance('grid1');
	var items = datagrid.getValue();
	if(items.length <=0 ) {
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	} else {
		fastDev.confirm("确认是否删除选择的" + items.length + "条记录？", "信息提示", function(result){
			if(result){
				var ids = [];
				for(var i = 0; i < items.length; i++) {
					ids.push(items[i].c_obj_id);
				}
				deleteCheckModelById(ids.join(","));
			}
		});
	}
}

function deleteCheckModelById(id) {
	var proxy = fastDev.create('Proxy', {
		action : delectAction
	});
	proxy.save({deleteID:id}, function(result){
		fastDev.alert(result.msg, '信息提示', result.result, function(){
			if(result.result == 0) {
				refreshDatagrid();
			}
		});
	});
}
/**
 * 过滤信息
 */
function doSearch() {
	var fm = fastDev.getInstance('checkform');
	currentCondition = fm.getItems();
	
	refreshDatagrid();
}
/**
 * 重置表单数据
 */
function doReset() {
	fastDev.getInstance('checkform').cleanData();
	currentCondition = null;
}
/**
 * 刷新datagrid
 * @param o
 */
function refreshDatagrid(o) {
	o = fastDev.apply(o || {}, currentCondition || {});
	o['basicMouldPojo.c_objtype_id'] = currentTypeId;
	o['action'] = 6;
	fastDev.getInstance('grid1').refreshData(o);
}


//Add By Rita.Zhou for addBasic.html to close addWindow
var clear = function() {
	var form = fastDev.getInstance("addBasicForm");
	form.cleanData();
};

var reset = function() {
	var form = fastDev.getInstance("addBasicForm");
	form.cleanData();
};

function refreshTree() {
	fastDev.getInstance('orgtree').reLoad();
}