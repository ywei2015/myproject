var currentOrgId, currentCondition;
function onNodeClick(e, id, value) {
	currentOrgId = id;
	refreshDatagrid();
}

function doSearchUser() {
	refreshDatagrid(fastDev.getInstance('userform').getItems());
}

function refreshDatagrid(currentCondition) {
	var o = currentCondition || {};
	o['user.orgId'] = currentOrgId || "";
	fastDev.getInstance('userdatagrid').refreshData(o);
}

function getUpUser() {
	return fastDev.getInstance('userdatagrid').getValue();
}

function doResetUser() {
	fastDev.getInstance('userform').cleanData();
}