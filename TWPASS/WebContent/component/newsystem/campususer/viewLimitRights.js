var request = fastDev.Browser.getRequest();
var roleId = request['roleId'];

function onAfterConstruct() {
	this.addInitReqParam({
		roleId: roleId
	});
}

function operRenderer() {
	return '<a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="deleteLimitRights">恢复</a>';
}

function onRowClick(e, index, data) {
	if(e.target.id == 'deleteLimitRights') {
		doDeleteLimitRights(data);
	}
}

function doDeleteLimitRights(data) {
	fastDev.confirm('是否恢复限制权限“' + data.resourceName+'-'+data.modeName + '”?', '信息提示', function(result) {
		if(result) {
			deleteLimitRights(data.permissionId);
		}
	});
}

function deleteLimitRights(pids) {
	fastDev.create('Proxy', {
		action: 'role_deleteLimitRights.action'
	}).save({
		rid: roleId,
		pids: pids
	}, function(data) {
		fastDev.alert(data.msg, '信息提示', data.status, function() {
			if(data.status == 'ok') {
				fastDev.Ui.Window.parent.refreshData();
				doClose();
			}
		});
	});
}

function doBatchDeleteLimitRights() {
	var datagrid = fastDev.getInstance('datagrid');
	var pids = [];
	var values = datagrid.getValue();
	if(values.length < 0) {
		fastDev.alert('请至少选择一条记录进行操作', '信息提示', 'warning');
		return false;
	}
	fastDev.confirm('是否恢复选择的' + values.length + '记录的限制权限？','信息提示', function(result){
		if(result) {
			for(var i = 0; i < values.length; i++) {
				pids.push(values[i].permissionId);
			}
			deleteLimitRights(pids.join(','));
		}
	});
}

function doClose() {
	fastDev.Ui.Window.parent.closeDialog();
}