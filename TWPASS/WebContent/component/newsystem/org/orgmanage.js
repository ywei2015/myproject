var orgDialog = null;
var currentOrgId = -1;
//刷新条件
var currentCondition = null;

function onBeforeCompile() {
	var height = fastDev(window).height();
	fastDev('#centerPanel').css('width', (fastDev(window).width() - 211) + 'px');
	fastDev('#orgtree').attr('height',(height-35)+'px');
	fastDev('#orgdatagrid').attr('height', (height-66)+'px');
}

function operRenderer(value) {
	var width = fastDev(this).width();
	return ['<div style="width:'+width+'px;"><a href="javascript:void(0);" class="btn" id="modify" style="margin-left:5px;display:none;" name="editOrg">修改</a>',
	        '<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="delete" name="deleteOrg">删除</a>',
	        '<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="assrole" name="assrole">关联角色</a>',
	        '<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="authview" name="authview">权限视图</a></div>'].join('');
}

function onNodeClick(event, id, value) {
	currentOrgId = id;
	refreshDatagrid();
}

function orgNameRenderer(value) {
	return '<a href="javascript:void(0);" class="btn" name="viewOrg">' + value + '</a>';
}

function createWindow(o) {
	o.src = "newsystem/org/"+o.src;	
	var config = fastDev.apply({
		width : "640",
		height: "310",
		showMaxBtn : false,
		allowResize : false,
		inside:false
	}, o || {});
	(config.buttons = config.buttons || []).push({
		text : "关闭",
		iconCls : "icon-close",
		onclick : function(event, win) {
			win.close();
		}
	});
	orgDialog = fastDev.create('Window', config);
}


/**
 * 添加组织信息
 */
function doAddOrg() {
	createWindow({
		src : "addOrg.html?parentOrgId="+currentOrgId,
		title : "新增组织",
		width : "604",
		height : "271",
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(event, win, cwin, fast) {
				cwin.doSave();
			}
		},{
			text : '重置',
			iconCls : 'icon-reset',
			onclick : function(e, win, cwin) {
				cwin.doReset();
			}
		}]
	});
}

/**
 * 组织排序
 */
function doSortOrg() {
	if(currentOrgId == -1) {
		fastDev.alert('请从左边树结构选中要排序的组织！', '信息提示', 'warning');
	} else {
		createWindow({
			src : "orgOrder.html?orgid="+currentOrgId,
			width: "600",
			title : "组织排序",
			height: "350",
			buttons : [{
				text : '保存',
				iconCls : 'icon-save',
				onclick : function(event, win, cwin, fast) {
					cwin.doSave();
				}
			}]
		});
	}
}

/**
 * 批量删除组织信息
 */
function doBatchDeleteOrg() {
	var datagrid = fastDev.getInstance('orgdatagrid');
	var items = datagrid.getValue();
	if(items.length <=0 ) {
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	} else {
		fastDev.confirm("确认是否删除选择的" + items.length + "条记录？", "信息提示", function(result){
			if(result){
				var ids = [];
				for(var i = 0; i < items.length; i++) {
					ids.push(items[i].orgId);
				}
				deleteOrgById(ids.join(","));
			}
		});
	}
}

/**
 * 过滤组织信息
 */
function doSearchOrg() {
	var orgForm = fastDev.getInstance('orgform');
    currentCondition = orgForm.getItems();
    refreshDatagrid();
}

/**
 * 重置表单数据
 */
function doResetOrg() {
	fastDev.getInstance('orgform').cleanData();
}

function onRowClick(event,rowindex,data) {
	var target = event.target.name;
	if(target) {
		var name = fastDev.Util.StringUtil.capitalize(target);
		if(window['do' + name]) {
			window['do' + name].call(window, data);
		}
	}
}

function onRowDblClick(event, rowindex, data) {
	doViewOrg(data);
}

/**
 * 查看组织信息
 * @param data
 */
function doViewOrg(data) {
	createWindow({
		src : "orgInfo.html?orgid="+data.orgId + "&orgname=" + data.orgName,
		title : "查看组织",
		height : 283,
		width:559
	});
}

/**
 * 组织权限视图
 * @param data
 */
function doAuthview(data) {
	createWindow({
		src : "orgRightView.html?orgid="+data.orgId+'&orgname='+data.orgName,
		title : "组织权限视图",
		width : "783",
		height : "378"
	});
}

/**
 * 关联角色
 * @param data
 */
function doAssrole(data) {
	orgDialog = fastDev.create('Window', {
		src : "newsystem/org/addrole.html?orgid="+data.orgId,
		title : "组织已经关联的角色",
		width : "633",
		height : "340",
		showMaxBtn : false,
		allowResize : false,
		inside:false
	});		
}

/**
 * 修改组织信息
 * @param data
 */
function doEditOrg(data) {
	//console.info(data);
	createWindow({
		src : "modifyOrg.html?orgid="+data.orgId+"&parentOrgId="+data.parentOrgId+'&area='+data.area,
		title : "修改组织",
		width : "604",
		height : "271",
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(e,win,cwin, fast) {
				cwin.doSave();
			}
		},{
			text : '重置',
			iconCls : 'icon-reset',
			onclick : function(e, win, cwin) {
				cwin.doReset();
			}
		}]
	});
}

/**
 * 删除组织信息
 * @param data
 */
function doDeleteOrg(data) {
	fastDev.confirm('确认删除“'+ data.orgName +'”?', '信息提示', function(result){
		if(result) {
			deleteOrgById(data.orgId);
		}
	});
}

function deleteOrgById(id) {
	var proxy = fastDev.create('Proxy', {
		action : 'org_delOrg.action'
	});
	proxy.save({orgid:id}, function(result){
		fastDev.alert(result.msg, '信息提示', result.status, function(){
			if(result.status == 'ok') {
				refreshDatagrid();
				var ids = id.split(',');
				for(var i = 0; i < ids.length; i++) {
					delTreeNode(id);
				}
			}
		});
	});
}

/**
 * 刷新datagrid
 * @param o
 */
function refreshDatagrid(o) {
	o = fastDev.apply(o || {}, currentCondition || {});
	o['org.parentOrgId'] = currentOrgId;
	fastDev.getInstance('orgdatagrid').refreshData(o);
}

/**
 * 关闭窗口
 */
function closeDialog() {
	if(orgDialog) {
		orgDialog.close();
		orgDialog = null;
	}
}

/**
 * 刷新树
 */
function refreshTree() {
	fastDev.getInstance('orgtree').reLoad();
}

/**
 * 添加树节点
 * 格式如：{id:'id',val:'val',sync:'true',pid:'pid'}
 * @param node
 */
function addTreeNode(node) {
	if(node) {
		var orgTree = fastDev.getInstance('orgtree');
		if(orgTree.getNodeByid(node.pid)) {
			orgTree.addNode(node);
		}
	}
}

/**
 * 修改树节点
 * @param val
 */
function editTreeNode(nId, nVal, nPid) {
	if(nId) {
		var orgTree = fastDev.getInstance('orgtree');
		var node = orgTree.getNodeByid(nId);
		if(node) {
			node.val = nVal;
			orgTree.editNode(node);
			if (node.pid != nPid)
		    {
				var newNode = {id: nId,val: nVal,pid: nPid};
				if(orgTree.getNodeByid(nPid))
				{
					orgTree.delNode(nId);
					orgTree.addNode(newNode);
				}
		    }
		}
	}
}

/**
 * 删除节点
 * @param id
 */
function delTreeNode(id) {
	if(id && (id+'')!='-1') {
		var orgTree = fastDev.getInstance('orgtree');
		if(orgTree.getNodeByid(id)) {
			orgTree.delNode(id);
		}
	}
}

function onAfterInitRender() {
	showResourceModes('org', fastDev.getInstance('orgdatagrid'));
}