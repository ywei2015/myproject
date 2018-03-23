var request = fastDev.Browser.getRequest();
var empids = request['empids'];

function doSave() {
	var orgId = fastDev.getInstance('usertree').getCurrentId();
	if(orgId && orgId != '-1') {
		fastDev.create('Proxy',{
			action : 'user_moveEmpFromOrgToOrg.action'
		}).save({
			orgid : orgId,
			empids : empids
		},function(result){
			fastDev.alert(result.msg, '信息提示', result.status, function(){
				if(result.status == 'ok') {
					parent.refreshDatagrid();
					parent.closeDialog();
				}
			});
		});
	} else {
		fastDev.alert('请先选择组织记录', '信息提示', 'warning');
	}
}