var request = fastDev.Browser.getRequest();//获取请求对象
var roleID = request['roleID'];
var userID = request['userID'];
var userName = request['userName'];
var currentId,oldPids,treeItems,pidMap={},idMap={},pidLeafMap={};
var datagridHasViewedItems;
var dialog = null;
var dataRight = {};

/**
 * 加载树数据
 */
function treeAfterConstruct(){
	this.addInitReqParam({
		"userId":userID,
		"roleId":roleID
	});
}

/**
 * 为datagrid表头选框绑定点击事件，切换全选和全不选
 */
function onAfterInitRender() {
	fastDev('#grid-height-checkbox').bind('click', onCheckboxSelectChange);
}


function clone(o) {
	if(typeof o != 'object') {
		return o;
	}
	if(o == null) {
		return o;
	}
	var no = {};
	for(var k in o) {
		no[k] = clone(o[k]);
	}
	return no;
}

/**
 * 树数据加载完成后，解析数据。
 */
function onAfterLoadInit() {
	var oldTreeItems = this.getItems();
	treeItems = [], oldPids = [];
	for(var i = 0; i < oldTreeItems.length; i++) {
		treeItems[i] = clone(oldTreeItems[i]);
		if(oldTreeItems[i].type == 'P' && oldTreeItems[i].chk == 'true') {
			oldPids.push(oldTreeItems[i].id);
		}
	}
	setTreeItems();
}

/**
 * 树节点点击事件，如果点击的为非上次点击的目录节点，则重建datagrid的数据。
 */
function onNodeClick(){
	var _currentId = this.getCurrentId();
	var node = this.getNodeByid(_currentId);
	fastDev.getInstance('dataRightBtn').disable();
	if(node.type=='R'){
		if(_currentId != currentId) {
			currentId = _currentId;
			setDatagrid();
		}
	} else {
		var n = idMap[node.id];
		if(n.chk == 'true') {
			fastDev.getInstance('dataRightBtn').enable();
		}
	}
}

/**
 * 树节点状态改变时，如果当前改变的节点所包含的叶子节点显示在datagrid中，刷新datagrid的状态。
 * @param e
 * @param id
 * @param checked
 */
function onCheckClick(e, id, checked) {
	var node = this.getNodeByid(id);
	var itemId = id;
	if(node.type == 'R') {
		var items = getLeafNodes(id);
		if(items) {
			for(var i = 0; i < items.length; i++) {
				idMap[items[i].id].chk = checked + '';
				itemId = items[i].id;
			}
		}
	} else {
		idMap[id].chk = checked + '';
		if(this.isChecked(id)) {
			fastDev('#dataRightBtn')[checked?'show':'hide']();
		}
	}
	if(datagridHasViewedItems) {
		for(var i = 0; i < datagridHasViewedItems.length; i++) {
			if(datagridHasViewedItems[i] == itemId) {
				updateDatagrid();
				break;
			}
		}
	}
}

/**
 * 解析树数据：为节点建立索引
 */
function setTreeItems() {
	for(var i = 0; i < treeItems.length; i++) {
		var item = treeItems[i];
		var pid = item.pid;
		if(!pidMap[pid]) {
			pidMap[pid] = [item];
		} else {
			pidMap[pid].push(item);
		}
		idMap[item.id] = item;
	}
}

/**
 * 采用递归方式，获取目录节点包含的所有叶子节点。
 * @param id
 * @param leafItems
 * @returns
 */
function getLeafNodes(id) {
	var items = pidMap[id];
	var cachedLeafItems = pidLeafMap[id];
	var leafItems = [];
	if(cachedLeafItems) {
		leafItems = cachedLeafItems;
	} else if(items){
		for(var i = 0; i < items.length; i++) {
			var item = items[i];
			if(item.type == 'R') {
				leafItems = leafItems.concat(getLeafNodes(item.id));
			} else if(item.disabled != 'true'){
				leafItems.push(item);
			}
		}
		pidLeafMap[id] = leafItems;
	}
	return leafItems;
}

/**
 * 设置datagrid的数据。
 * 1、删除datagrid原有数据
 * 2、用访问方式，合并显示的叶子节点数据
 */
function setDatagrid() {
	var datagrid = fastDev.getInstance('datagrid');
	datagrid.clean();
	datagridHasViewedItems = [];
	var dataItems = getLeafNodes(currentId);
	if(dataItems && dataItems.length > 0) {
		var dataMap = {};
		for(var i = 0; i < dataItems.length; i++) {
			var item = dataItems[i];
			if(dataMap[item.val]) {
				dataMap[item.val].push(item);
			} else {
				dataMap[item.val] = [item];
			}
		}
		for(var o in dataMap) {
			var items = dataMap[o];
			var rowData = {name:o,ids:[]};
			var index = 0;
			for(var i = 0; i < items.length; i ++) {
				var item = items[i];
				if(item.chk == 'true') {
					index ++;
				}
				rowData.ids.push(item.id);
				datagridHasViewedItems.push(item.id);
			}
			rowData.chk = (index == 0) ? 'false' : ((index == items.length) ? 'true' : 'part');
			rowData.ids = rowData.ids.join(',');
			delete dataMap[o];
			datagrid.addRow(rowData, false, null,fastDev.isEmptyObject(dataMap));
		}
	}
}

/**
 * 刷新datagrid的状态
 */
function updateDatagrid() {
	var datagrid = fastDev.getInstance('datagrid');
	var items = datagrid.getAllValue();
	for(var i = 0; i < items.length; i++) {
		var ids = items[i].ids.split(",");
		var index = 0;
		for(var j = 0; j < ids.length; j++) {
			var it = idMap[ids[j]];
			if(it.chk == 'true') {
				index ++;
			}
		}
		items[i].chk = (index == 0) ? 'false' : ((index == ids.length) ? 'true' : 'part');
		datagrid.updateRow(items[i], i==items.length - 1);
	}
}

/**
 * datagrid表头选框状态改变时，datagrid数据切换全选与全不选
 * @param event
 */
function onCheckboxSelectChange(event) {
	var target = fastDev(event.target);
	if(target.hasClass('ico-checkbox-checked')) {
		target.removeClass('ico-checkbox-checked');
		target.addClass('ico-checkbox');
		updateDatagridCheckbox(false);
	} else {
		target.removeClass('ico-checkbox');
		target.addClass('ico-checkbox-checked');
		updateDatagridCheckbox(true);
	}
}

/**
 * 更新datagrid数据状态
 * @param checked
 */
function updateDatagridCheckbox(checked) {
	var datagrid = fastDev.getInstance('datagrid');
	var items = datagrid.getAllValue();
	var value = checked ? 'true' : 'false';
	var len = items.length;
	for(var i = 0; i < len; i++) {
		if(items[i].chk != value) {
			items[i].chk = value;
			datagrid.updateRow(items[i], i==len-1);
			updateTree(items[i].ids, value);
		}
	}
}

/**
 * datagrid行数据状态改变时，刷新树数据状态
 * @param event
 * @param rowindex
 * @param data
 */
function onRowClick(event, rowindex, data) {
	var target = event.target;
	if(target.id == 'checkbox') {
		if(data.chk == 'true') {
			data.chk = 'false';
			updateTree(data.ids, 'false');
		} else {
			data.chk = 'true';
			updateTree(data.ids, 'true');
		}
		this.updateRow(data);
	} else if(target.id == 'dataright') {
		doSetDataRight(data.ids);
	}
}

/**
 * 刷新树数据状态
 * @param ids
 * @param checked
 */
function updateTree(ids, checked) {
	var tree = fastDev.getInstance('roleFuncTree');
	var methodName = checked=='true' ? 'checkedById' : 'unCheckedById';
	ids = ids.split(",");
	for(var i = 0; i < ids.length; i++) {
		tree[methodName](ids[i]);
		idMap[ids[i]].chk = checked;
		if(tree.isChecked(ids[i])) {
			fastDev('#dataRightBtn')[(checked=='true')?'show':'hide']();
		}
	}
}

function operRenderer(value) { 
	if(value == 'true') {
		return '<a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="dataright">数据权限</a>';
	}
	return '';
}

/**
 * 设置树的数据权限
 */
function doSetTreeDataRight() {
	var id = fastDev.getInstance('roleFuncTree').getCurrentId();
	if(!id) {
		fastDev.alert('请先选择访问方式节点，再操作', '信息提示', 'warning');
		return false;
	}
	var item = idMap[id];
	if(!item) {
		fastDev.alert('请选择有效的访问方式节点操作', '信息提示', 'warning');
		return false;
	}
	doSetDataRight(id);
}

/**
 * 设置数据权限
 * @param id
 */
function doSetDataRight(id) {
	dialog = fastDev.create('Window', {
		title : '分配数据权限',
		width : 500,
		height : 305,
		inside : false,
		showMaxBtn : false,
		allowResize : false,
		src : 'newsystem/campususer/roleRuleSet.html?userID=' + userID + '&roleID=' + roleID + '&permissionID=' + id + '&showType=user'
	});
}

function closeDialog() {
	dialog.close();
}

function setChildDataRight(permissionId, ruleIds) {
	var pids = (permissionId + '').split(',');
	for(var i = 0; i < pids.length; i++) {
		dataRight[pids[i]] = (ruleIds || "").split("_@@_");
	}
}

function setDataRight(permissionId, value) {
	var pids = (permissionId + '').split(',');
	for(var i = 0; i < pids.length; i++) {
		dataRight[pids[i]] = (value || []);
	}
}

function getDataRight() {
	return dataRight;
}

/**
 * 比较两个数组，删除数组中相同的元素
 * @param a
 * @param b
 * @returns
 */
function removeArray(a, b) {
	var len = a.length;
	var index = 0;
	for(var i = 0; i < len; i++) {
		var v = a[index++];
		for(var j = 0; j < b.length; j++) {
			if(v == b[j]) {
				b.splice(j, 1);
				a.splice(--index, 1);
				break;
			}
		}
	}
}

function getPermissions(arr) {
	if(arr && arr.length > 0) {
		var tmp = [];
		for(var i = 0; i < arr.length; i++) {
			var p = idMap[arr[i]];
			if(p && p.type == 'P') {
				tmp.push({
					id : arr[i],
					ptype : p.ptype || ''
				});
			}
		}
		return tmp;
	}
	return [];
}

function getDelParentPermissions(arr) {
	var delPermissions = [];
	for(var i = 0; i < arr.length; i++) {
		var p = idMap[arr[i]];
		if(p && (p.modeId == 'transfer' || p.modeId == 'grant' || p.modeId == 'view')) {
			var modeId = p.modeId;
			p = idMap[p.pid];
			p = idMap[p.pid];
			while(p) {
				if(p[modeId]) {
					var n = p[modeId].split(',');
					if(n[1] != '1') {
						var leafNodes = getLeafNodes(p.id);
						for(var j = 0; j < leafNodes.length; j++) {
							var leafNode = leafNodes[j];
							if(leafNode.modeId == modeId && leafNode.chk == 'true') {
								break;
							}
						}
						if(j < leafNodes.length - 1) {
							break;
						} else {
							delPermissions.push({
								id : n[0],
								ptype : n[2] || ''
							});
							p[modeId] = n[0]+',1,'+n[2];
						}
					} else {
						break;
					}
				}
				p = idMap[p.pid];
			}
		}
	}
	return delPermissions;
}

function getNewParentPermissions(arr) {
	var newPermissions = [];
	for(var i = 0; i < arr.length; i++) {
		var p = idMap[arr[i]];
		if(p && (p.modeId == 'transfer' || p.modeId == 'grant' || p.modeId == 'view')) {
			var modeId = p.modeId;
			p = idMap[p.pid];
			p = idMap[p.pid];
			while(p) {
				if(p[modeId]) {
					var n = p[modeId].split(',');
					if(n[1] == '1') {
						newPermissions.push({
							id : n[0],
							ptype : n[2] || ''
						});
						p[modeId] = n[0]+',2,'+n[2];
					} else {
						break;
					}
				}
				p = idMap[p.pid];
			}
		}
	}
	return newPermissions;
}

function hasNeedSave() {
	var permissionIds = fastDev.getInstance('roleFuncTree').getValues('chkedLeafNodes') || [];
	removeArray(permissionIds, oldPids);
	return permissionIds.length !=0 || oldPids.length !=0 || !fastDev.isEmptyObject(dataRight);
}

function doSave() {
	var permissionIds = fastDev.getInstance('roleFuncTree').getValues('chkedLeafNodes') || [];
	removeArray(permissionIds, oldPids);
	if(permissionIds.length ==0 && oldPids.length ==0 && fastDev.isEmptyObject(dataRight)) {
		fastDev.alert('没有做任何操作，不需要保存', '信息提示', 'warning');
		return false;
	} else {
		var newPids = getNewParentPermissions(permissionIds), delPids = getDelParentPermissions(oldPids);
		var tmpDatagrid = [];
		for(var pid in dataRight) {
			var p = idMap[pid];
			if(p.chk == 'true') {
				tmpDatagrid.push({
					pid : pid,
					ruleIds : dataRight[pid].join(',')
				});
			}
		}
		fastDev.create('Proxy', {
			action : 'role_privilege.action'
		}).save({
			newPids : fastDev.Util.JsonUtil.parseString(getPermissions(permissionIds).concat(newPids)),
			delPids : fastDev.Util.JsonUtil.parseString(getPermissions(oldPids).concat(delPids)),
			roleId : roleID,
			showType: 'user',
			roleRuleFuncValue : fastDev.Util.JsonUtil.parseString(tmpDatagrid)
		}, function(result) {
			fastDev.alert(result.msg, '信息提示', result.status, function() {
				if(result.status == 'ok') {
					fastDev.Ui.Window.parent.closeDialog();
				}
			});
		});
	}
}

function checkboxRenderer(value) {
	var cls;
	if(value == 'true') {
		cls = 'ico-checkbox-checked';
	} else if(value == 'part') {
		cls = 'ico-checkbox-half';
	} else {
		cls = 'ico-checkbox';
	}
	return '<div id="checkbox" class="ui-grid-checkbox ' + cls + '"></div>';
}

fastDev(function(){
	fastDev("#roleNameTextBox").setText(userName);
});

/**
 * 还原权限
 */
function doResetRights() {
	fastDev.confirm('是否删除用户私有权限以及限制权限？', '信息提示', function(result){
		if(result){
			fastDev.create('Proxy', {
				action: 'role_resetRights.action'
			}).save({
				roleId: roleID
			}, function(data){
				fastDev.alert(data.msg, '信息提示', data.status, function(){
					if(data.status == 'ok') {
						refreshData();
					}
				});
			});
		}
	});
}

function refreshData() {
	fastDev.getInstance('roleFuncTree').reLoad();
	fastDev.getInstance('datagrid').clean();
}

/**
 * 查看限制权限
 */
function viewLimitRights() {
	if(hasNeedSave()) {
		fastDev.alert('请先保存数据，再查看限制权限', '信息提示', 'warning');
	} else {
		dialog = fastDev.create('Window', {
			width : 480,
			height : 310,
			showMaxBtn : false,
			allowResize : false,
			title : '查看限制权限',
			inside: false,
			src : 'newsystem/campususer/viewLimitRights.html?roleId=' + roleID
		});
	}
}

