var request = fastDev.Browser.getRequest();

var g_event = request['event'];
var g_id = request['id'];
var form;
var submitSuccess=function(data){
	fastDev.alert(data.msg, "信息提示", data.result,function(){
		if (data.result >= 0){//成功
			parent.appDialog.close();
			parent.refreshTree();
			//modify模式下，需要刷新父窗口的grid
			if (g_event == "modify")
				parent.refreshData();
		}
	});
};

function FormReady(){
	if (g_event == "modify"){
		this.setOptions({
			"action":"sdaction_modifyActionDataById.action?id="+g_id,
			"dataSource":"sdaction_getActionInfoById.action?id="+g_id
		});
	}
}

function FormAfterInit(){
	if (g_event == "modify"){
		fastDev.getInstance("partgrid").setHeight(150);
	}
}

//若是修改模式，则要初始化获取原本的数据
function onGridBeforeReady(){
	if (g_event == "modify"){
		this.setOptions({
			"initSource":"sdaction_getActionPositionInfoById.action?id="+g_id,
		});
	}
}

function onGridRowClick(event,rowindex,data) {
	var target = event.target;
	var name = target['name'];
	if(name) {
		name = fastDev.Util.StringUtil.capitalize(name);
		if(fastDev.isFunction(window['do' + name])) {
			window['do' + name].call(window, data);
		}
	}
}

function doAddPart() {
	fastDev.getInstance("partgrid").addRow();
}

function operRenderer(value) {
	var width = fastDev(this).width();
	return ['<div style="width:'+width+'px;">',
	        	'<a href="javascript:void(0);" class="btn" style="margin-left:5px;" name="deletePosition">删除</a>',
	        '</div>'].join('');
}

function doDeletePosition(data) {
	fastDev.getInstance('partgrid').delRow(data.c_id);
}

//选择完部门后，获取部门下对应的岗位
function onPositionAfterConstruct() {
	var value = fastDev.getInstance("partgrid").getValue(this);
	this.addInitReqParam({
		orgid : value[0]['c_respons_orgid']
	});
}

//保存页面内容
function doSave(){
	fastDev.getInstance('partgrid').writeBackCell();
	var datagridValues = fastDev.getInstance('partgrid').getAllValue();
	for(var i = 0; i < datagridValues.length; i++) {
		var rowValue = datagridValues[i];
		if(!rowValue.c_respons_orgid || rowValue.c_respons_orgid == '-1') {
			fastDev.alert('第' + (i + 1) + '条职责信息，职责部门不能为空', '信息提示', 'warning');
			return;
		}
		if(!rowValue.c_respons_positionid || rowValue.c_respons_positionid == '-1') {
			fastDev.alert('第' + (i + 1) + '条职责信息，职责岗位不能为空', '信息提示', 'warning');
			return;
		}
		for(var j = 0; j < datagridValues.length; j++) {
			if(j != i) {
				if(rowValue.c_respons_positionid == datagridValues[j].c_respons_positionid) {
					fastDev.alert('第' + (i + 1) + '条职责信息与第' + (j + 1) + '条职责信息职责岗位重复', '信息提示', 'warning');
					return;
				}
			}
		}
	}
	fastDev.getInstance('positionParts').setValue(fastDev.Util.JsonUtil.parseString(datagridValues));
	fastDev.getInstance('addActionForm').submit();
}

