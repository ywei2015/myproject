var request = fastDev.Browser.getRequest();
var checkDialog;
var selectTree;
var edit = request['edit'];
var postId = request['postId'];
var taskId = request['taskId'];
var posiname = request['posiname'];

function handleTrigger(){
	var ok = fastDev.getInstance("taskMouldPojo.c_trigger_type");
	var okValue = ok.getValue();
	var btn=fastDev.getInstance("taskMouldPojo.c_timerule_id");
	if(okValue=="1"){
		btn.enable();
	}else{
		btn.setValue("");
		btn.disable();		
	}	
}
function handleOk(){
	var ok = fastDev.getInstance("taskMouldPojo.c_isokfeedback");
	var okValue = ok.getValue();
	var btn=fastDev.getInstance("taskMouldPojo.c_ok_templetid");
	if(okValue=="0"){
		btn.setValue("");
		btn.disable();		
	}else{
		btn.enable();		
	}	
}
function handleErr(){
	var ok = fastDev.getInstance("taskMouldPojo.c_iserrfeedback");
	var okValue = ok.getValue();
	var btn=fastDev.getInstance("taskMouldPojo.c_err_tasktemplet_id");
	if(okValue=="0"){
		btn.setValue("");
		btn.disable();		
	}else{
		btn.enable();		
	}
}
//grid加载前
function onGridBeforeReady(){
	if (edit == "update") {
		this.setOptions({
			initSource : "task_queryTaskByPage.action?action=6&taskMouldPojo.c_tasktemplet_id="+ taskId
		});
	}
}
//function onBeforeReady(){
//	if (edit == "update") {
//		this.setOptions({
//			initSource : "task_queryTaskByPage.action?action=6&taskMouldPojo.c_tasktemplet_id="+ taskId
//		});
//	}
//}

function  SelectTreeReady()
{
	if (edit == "update") {
		selectTree = this;
		this.setOptions({
			initSource : "select_querySelect.action?action=7&value=" + taskId
		});
	}
}
//form加载前
function onFormBeforeReady(){
	if (edit == "update") {
		this.setOptions({
			dataSource : "task_getTaskMouldById.action?cid="+ taskId
		});
		
	}
	
}


//活动末节点和标准项联动
function handleActionNode(value){
	/*fastDev.getInstance('taskMouldPojo.c_actitem_id').initRefresh({
		url : 'select_querySelect.action?action=6&value=' + value
	});*/
	
	//var ok = fastDev.getInstance("taskMouldPojo.c_iserrfeedback");
	var stree = fastDev.getInstance("taskMouldPojo.c_actitem_id");
	stree.clean();
	stree.initRefresh({url:'select_querySelect.action?action=6&value=' + value});
}
function operRenderer() {
	var width = $(this).width();
	return ['<div><a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="delete" name="delete">删除</a></div>'].join('');
}
function onRowClick(event,rowindex,data) {		
	var target = event.target;
	var name = target['name'];
	if(name) {
		name = fastDev.Util.StringUtil.capitalize(name);
		if(window['do' + name]) {
			window['do' + name].call(window, data);
		}
	}
}
function doSubmit(){
	fastDev("[id='taskMouldPojo.c_tasktemplet_id']").prop("value", taskId);
	var form = fastDev.getInstance('formInfo');
	form.setOptions({
		action : "edit_editData.action?action=1&edit=" + edit
	});	 
	form.submit();
}
//表单提交后回调
function onSubmitSuccess(result) {
	fastDev.alert(result.msg, '信息提示', result.status, function() {
		if (result.status == 'ok') {
			/*
			refreshDatagrid();
			if (edit == "update") {
				parent.refreshDatagrid();
			}
			*/
			parent.appDialog.close();
			parent.refreshDatagrid();
		}
	});
}
function doDelete(data){
	deleteById(data.c_id);
}

function deleteById(id) {
	var proxy = fastDev.create('Proxy', {
		action : 'delete_deleteInfo.action?action=1'
	});
	proxy.save({deleteID:id}, function(result){
		fastDev.alert(result.msg, '信息提示', result.status, function(){
			if(result.status == 'ok') {
				refreshDatagrid();
			}
		});
	});
}
/**
 * 刷新datagrid
 * @param o
 */
function refreshDatagrid() {
	fastDev.getInstance('grid1').refreshData();
}

function FormAfter() {
	if (edit == "add") {
//		fastDev.getInstance("taskMouldPojo.c_actnode_id").setValue(nodeId);
//		handleActionNode(nodeId);
		
		fastDev.getInstance("taskMouldPojo.c_positionid").setValue(postId);
		//fastDev.getInstance("taskMouldPojo.posiname").setValue(posiname);
	}
	else {
		handleTrigger();
		handleOk();
		handleErr();
	}
	
}
