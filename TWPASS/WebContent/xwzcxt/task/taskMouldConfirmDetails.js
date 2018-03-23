//
//function onAfterLoadData() {
//	fastDev.getInstance('modifycheckForm').setValue({
//		'org.basicID':1001619
//	});
//}
var request = fastDev.Browser.getRequest();
var taskId = request['taskId'];
var edit = request['edit'];

var objname = "";
fastDev(function(){
	fastDev("[id='taskMouldPojo.c_task_id']").prop("value", taskId);
});

function onFormBeforeReady(){
	if (edit == "details") {
		this.setOptions({
			dataSource : 'taskEntry_queryTaskEntry.action?action=2&taskId='+taskId
		});
	}
}
function onBeforeGridReady(){	
	this.setOptions({
		initSource : "task_queryTaskByPage.action?action=4&taskId="+taskId
	});	 
};
function onSelectChange(){
	objname = this.getText();
	console.info(objname);
}
function doSubmit() {
	fastDev.confirm("是否确认下发？", "信息提示", function(result){
		if(result){
			var form = fastDev.getInstance('checkForm');
			form.setOptions({
				//action : "edit_editData.action?action=3"
				action : "task_sendTTask.action"
			});	 
			form.submit();
		}
	});
}
function doReset() {
	fastDev.getInstance('checkForm').resetData();
}
//表单提交后回调
function onSubmitSuccess(result) {
	fastDev.alert(result.msg, '信息提示', result.status, function() {
		if (result.status == 'ok') {
			parent.refreshDatagrid();
			parent.closeDialog();
		}
	});
}

function onRowClick(event,rowindex,data) {	
	var target = event.target;
	var name = target['name'];
	if(name) {
		name = fastDev.Util.StringUtil.capitalize(name);
		if(window['do' + name]) {
			this.writeBackCell();
			var newdata = this.getValueByIndex(rowindex);
			window['do' + name].call(window, newdata);
		}
	}
}

function operRenderer() { 
	return ['<a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="delete" name="delete">删除</a><a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="edit" name="edit">提交修改</a>']
			.join('');
}
function doDelete(data){
	deleteById(data.c_id);
}

function doSubmit(){
	fastDev.confirm("是否确认下发？", "信息提示", function(result){
		if(result){
			var form = fastDev.getInstance('checkForm');
			form.setOptions({
				//action : "edit_editData.action?action=3"
				action : "task_sendTTask.action"
			});	 
			form.submit();
		}
	});
}

function doEdit(data){
	console.info(data);
	editById(data);
}

function deleteById(id) {
	var proxy = fastDev.create('Proxy', {
		action : 'delete_deleteStepByCid.action'
	});
	proxy.save({deleteID:id}, function(result){
		fastDev.alert(result.msg, '信息提示', result.status, function(){
			if(result.status == 'ok') {
				refreshDatagrid();
			}
		});
	});
}

function editById(data){
	var proxy = fastDev.create('Proxy', {
		action : 'basicAdd_submitStepData.action'
	});
	proxy.save({cid:data.c_id,index:data.c_step_index,area:data.area,obj:data.c_obj_id}, function(result){
		fastDev.alert(result.msg, '信息提示', result.result);
	});
}

function refreshDatagrid() {
	fastDev.getInstance('grid1').refreshData();
}