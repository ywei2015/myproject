var currentPositionId, currentName;
var op = null;
var form;
var tree, positionDialog;
var currentOrgId, parentId, parentText;
function fromReady() {
	form = this;
}
function treeReady() {
	tree = this;
}
function formRender() {
	fastDev.getInstance('position.orgId').setValue(currentOrgId);
	fastDev.getInstance('position.manaPosi').setValue(parentId);

}

function showManaPosition(id, value) {
	 fastDev.getInstance('position.manaPosi').clean(true);
	 fastDev.getInstance('position.manaPosi').initRefresh({
	 url : 'user_initUserPos.action?orgid=' + currentOrgId
	 });
}

function getCurrentOrgId() {
	for ( var i = 0; i < tree.dataset.records.length; i++) {
		if (tree.dataset.records[i].id == currentPositionId) {
			currentOrgId = tree.dataset.records[i].orgId;
		}

	}

}

function onParentOrgBeforeReady() {
	this
			.setOptions({
				value : orgId,
				initSource : 'org_initFirstGradeOrgTree.action?moduleId=M34&modeId=modify&orgID='
						+ orgId
			});
}

function onBeforeCompile() {
	var height = fastDev(window).height();
	fastDev('#positiontree').attr('height', (height - 35) + 'px');
	fastDev('#centerPanel')
			.css('width', (fastDev(window).width() - 220) + 'px');
}

function onNodeClick(event, id, value) {
	currentPositionId = id;
	getCurrentOrgId();
	parentId = this.getParentid(id);
	parentText = this.getValByid(parentId);
	currentName = this.getValByid(id);
	refreshUppositiontree();
	// fastDev.getInstance("position.posiName").getGlobal().box.prop("value",
	// parentText);
	op = 'view';
	setReadOnly(true);
	refreshForm(id);
}

function refreshUppositiontree() {
	var uppositiontree = fastDev.getInstance('position.manaPosi');
	uppositiontree.setOptions({
		"initSource" : 'user_initUserPos.action?orgid=' + currentOrgId
	});
	fastDev.getInstance('position.manaPosi').initRefresh({
		url : 'user_initUserPos.action?orgid=' + currentOrgId
	});
}

function refreshForm(id) {
	fastDev.getInstance('positionForm').cleanData();
	fastDev.getInstance('positionForm').dataRefresh({
		url : 'position_getPositionInfo.action?positionid=' + id
	});
}

/**
 * 设置只读
 * 
 * @param readOnly
 */
function setReadOnly(readOnly) {
	fastDev.getInstance('position.posiCode').setReadonly(readOnly);
	fastDev.getInstance('position.posiName').setReadonly(readOnly);
	fastDev.getInstance('position.posiType').setReadonly(readOnly);
	fastDev.getInstance('position.posiLevel').setReadonly(readOnly);
	fastDev.getInstance('position.orgId').setReadonly(readOnly);

	if (readOnly) {
		fastDev.getInstance('position.manaPosi').disable();

	} else {
		fastDev.getInstance('position.manaPosi').enable();
	}
	fastDev('#buttonBar')[readOnly ? 'hide' : 'show']();
}

/**
 * 修改区域信息
 */
function doModifyPosition() {
	if (!currentPositionId || currentPositionId == -1) {
		fastDev.alert('请选择需要修改的区域节点', '信息提示', 'warning');
	} else {
		// refreshUppositiontree();
		// refreshForm(currentPositionId);
		op = 'modify';
		setReadOnly(false);
		// fastDev.getInstance('area.area').setReadonly(true);
	}
}

/**
 * 删除区域信息
 */
function doDeletePosition() {
	if (!currentPositionId || currentPositionId == -1) {
		fastDev.alert('请选择需要删除的区域节点', '信息提示', 'warning');
	} else {
		fastDev.confirm('是否删除所选记录？', '信息提示', function(result) {
			if (result) {
				setReadOnly(true);
				op = 'delete';
				var proxy = fastDev.create('Proxy', {
					action : 'position_delPositions.action'
				});
				proxy.save({
					id : currentPositionId
				}, function(result) {
					fastDev.alert(result.msg, '信息提示', result.status, function() {
						if (result.status == 'ok') {
							var positiontree = fastDev.getInstance('positiontree');
							if (positiontree.getNodeByid(currentPositionId)) {
								positiontree.delNode(currentPositionId);
							}
							fastDev.getInstance('positionForm').cleanData();
							currentPositionId = null;
						}
					});
				});
			}
		});
	}
}

function doAddPosition() {
	op = 'add';
	fastDev.getInstance('positionForm').cleanData();
	if (currentOrgId && currentOrgId != "-1") {
		fastDev.getInstance('positionForm').setValue({
			'position.orgId' : currentOrgId
		});
	}
	if (currentPositionId && currentPositionId != "-1") {
		fastDev.getInstance('positionForm').setValue({
			'position.manaPosi' : currentPositionId
		});
	}
	setReadOnly(false);
}

/**
 * 保存区域信息
 */
function doSave() {
	fastDev.getInstance('positionForm').setOptions(
			{
				action : op == 'modify' ? 'position_modifyPosition.action'
						: 'position_addPosition.action'
			});
	fastDev.getInstance('positionForm').submit();
}
/**
 * 关联角色
 * 
 * @param data
 */
function doAssRole() {

	positionDialog = fastDev.create('Window', {
		title : "岗位已经关联的角色",
		iscenter : true,
		width : "800",
		height : "339",
		inside : false,
		showMaxBtn : false,
		allowResize : false,
		src : "newsystem/position/addrole.html?positionid=" + currentPositionId
	});
}

/**
 * 权限视图
 * 
 * @param data
 */
function doAuthView() {
	positionDialog = fastDev.create('Window', {
		width : "783",
		title : "岗位权限视图",
		iscenter : true,
		height : "378",
		inside : false,
		showMaxBtn : false,
		allowResize : false,
		src : "newsystem/position/posRightView.html?positionid="
				+ currentPositionId + "&posname=" + currentName
	});
}
function onFormSubmitSuccess(result) {
	fastDev.alert(result.msg, '信息提示', result.status, function() {
		if (result.status == 'ok') {
			var items = fastDev.getInstance('positionForm').getItems();
			var positiontree = fastDev.getInstance('positiontree');
			if (op == 'add') {
				op = 'modify';
				positiontree.reLoad();
				fastDev.getInstance('positionForm').cleanData();
			} else {
				if (positiontree.getNodeByid(items['position.positionId'])) {
					positiontree.editNode({
						id : items['position.positionId'],
						val : items['position.posiName'],
						pid : items['position.manaPosi'] || '-1'
					});
				}
			}
			setReadOnly(true);
		}
	});
}

/**
 * 重置表单
 */
function doReset() {
	fastDev.getInstance('positionForm').resetData();
}

function onAfterDataRender(){
	var obj = fastDev(this.elems[0]);
	obj.height(obj.parent().height()-62);
}

