var dictDialog;
var condition = null, currentDictTypeId = null, currentDictTypeNode = null, currentItems;
var dialogHeight = 205, addUrl = 'addDict.html', modifyUrl = 'modifyDict.html';
var items = [];
var accessMode = {};

function onBeforeCompile() {
	var height = fastDev(window).height();
	fastDev('#centerPanel').css('width', (fastDev(window).width() - 211) + 'px');
	fastDev('#dicttypetree').attr('height',(height-35) + 'px');
	fastDev('#dictdatagrid').attr('height', (height-fastDev('#dictdatagrid').offset().top - 15) + 'px');
	fastDev('#dicttree').attr('height', (height-140) + 'px');
}

function dictNameRenderer(value) {
	return '<a href="javascript:void(0);" class="btn" name="viewDict">' + value + '</a>';
}

function onNodeClick(e,id) {
	currentDictTypeId = id;
	currentDictTypeNode = this.getNodeByid(id);
	refreshData();
	setButtonsEnable(currentDictTypeNode.allowmodify == '1');
}

function onRowDblClick(event, rowindex, data) {
	doViewDict(data);
}

/**
 * 渲染操作列
 * @param value
 * @returns
 */
function operRenderer(value) {
	var width = fastDev(this).width();
	var rowDatas = fastDev.getInstance('dictdatagrid').getValue(this);
	var ret = ['<div style="width:'+width+'px;">'];
	var rowData = rowDatas.length > 0 ? rowDatas[0] : {};
	if(rowData.allowmodify == '1') {
		ret.push('<a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="modifyDict" name="modifyDict">修改</a>');
	}
	if(rowData.allowdelete == '1') {
		ret.push('<a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="deleteDict" name="deleteDict">删除</a>');
	}
	ret.push('</div>');
	return ret.join('');
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

function doViewDict4Tree(id) {
	doViewDict({
		dicttypeid : currentDictTypeId,
		dictid : id
	});
}


/**
 * 过滤字典项数据
 */
function doSearch() {
	currentItems = fastDev.getInstance('dictform').getItems();
	refreshData();
}

/**
 * 重置字典项过滤表单
 */
function doReset() {
	fastDev.getInstance('dictform').cleanData();
}

/**
 * 查看字典项详细信息
 */
function doViewDict(data) {
	dictDialog = fastDev.create('Window',{
		src : 'dictInfo.html?dictid=' + data.dictid + '&dicttypeid=' + data.dicttypeid,
		title : '字典项详细信息',
		width : 557,
		height : 229,
		allowResize : false,
		showMaxBtn : false,
		buttons : [{
			text : '关闭',
			iconCls : 'icon-close',
			onclick : function(e,win) {
				win.close();
			}
		}]
	});
}

function refreshData() {
	if(currentDictTypeNode && currentDictTypeNode.type == "2") {
		fastDev('#datagridPanel').hide();
		fastDev('#treePanel').show();
		fastDev('#modifyDictBtn').show();
		fastDev('#batchDeleteDictBtn').hide();
		fastDev('#deleteDictBtn').show();
		fastDev('#toolbar').addClass('ui-border-bottom');
		dialogHeight = 212;
		addUrl = 'addDict4Tree.html';
		modifyUrl = 'modifyDict4Tree.html';
		refreshTreeData();
	} else {
		fastDev('#treePanel').hide();
		fastDev('#datagridPanel').show();
		fastDev('#modifyDictBtn').hide();
		fastDev('#deleteDictBtn').hide();
		fastDev('#batchDeleteDictBtn').show();
		fastDev('#toolbar').removeClass('ui-border-bottom');
		dialogHeight = 205;
		addUrl = 'addDict.html';
		modifyUrl = 'modifyDict.html';
		refreshDatagridData();
	}
	
	var allowModify = false;
	var allowDelete = false;
	for(var i = 0; i < accessMode.length; i++)
    {
		if (accessMode[i] == 'modify') {allowModify=true;}
		if (accessMode[i] == 'delete') {allowDelete=true;}
    }
	if (!allowModify) {fastDev('[id=modifyDictBtn]').hide();}
	if (!allowDelete) {fastDev('[id=deleteDictBtn]').hide(); fastDev('[id=batchDeleteDictBtn]').hide();}
}

function onDicAfterInitRender() {
	dictShowResourceModes('dic',null);
}

function onDicItemAfterInitRender() {
	var allowModify = false;
	var allowDelete = false;
	for(var i = 0; i < accessMode.length; i++)
    {
		if (accessMode[i] == 'modify') {allowModify=true;}
		if (accessMode[i] == 'delete') {allowDelete=true;}
    }
	
	if (!allowModify) {fastDev('[id=modifyDict]').hide();}
	if (!allowDelete) {fastDev('[id=deleteDict]').hide();}
}

function dictShowResourceModes(type, datagrid) {
	fastDev.create('Proxy', {
		action: 'accessmode_getPrivilegeResourceModes.action'
	}).save({
		module: type
	}, function(result) {
		if (result!=null){
			for(var i = 0; i < result.length; i++)
		    {
			if (result[i] == 'add')
		    {fastDev('[id=addDictTypeBtn]').show(); fastDev('[id=addDictBtn]').show();}
			if (result[i] == 'modify')
		    {fastDev('[id=modifyDictTypeBtn]').show(); fastDev('[id=modifyDictBtn]').show();}
			if (result[i] == 'delete')
		    {fastDev('[id=deleteDictTypeBtn]').show(); fastDev('[id=deleteDictBtn]').show(); fastDev('[id=batchDeleteDictBtn]').show();}
		    }
			accessMode = result;
		}
		if(datagrid) {
			var height = datagrid.getOptions().height;
			datagrid.setHeight(height);
		}
	});
}

function refreshDatagridData() {
	var o = fastDev.apply({}, currentItems || {});
	o['dictEntryInfo.dicttypeid'] = currentDictTypeId;
	fastDev.getInstance('dictdatagrid').refreshData(o);
}

function refreshTreeData() {
	var o = fastDev.apply({}, currentItems || {});
	o['dictEntryInfo.dicttypeid'] = currentDictTypeId;
	o['viewInfo'] = 'true';
	fastDev.create('Proxy', {
		action : 'dict_initDictEntryTree.action'
	}).save(o, function(data) {
		var dicttree = fastDev.getInstance('dicttree');
		dicttree.setOptions({
			items: data
		});
		dicttree.reLoad();
	});
}

function setButtonsEnable(enable) {
	var op = enable ? 'enable' : 'disable';
	fastDev.getInstance('addDictBtn')[op]();
	fastDev.getInstance('batchDeleteDictBtn')[op]();
}

/**
 * 添加字典类型数据
 */
function doAddDictType() {
	dictDialog = fastDev.create('Window',{
		src : 'addDictType.html?pid='+currentDictTypeId,
		title : '新增字典类型',
		width : 296,
		height : 241,
		allowResize : false,
		showMaxBtn : false,
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(e,win,cwin){
				cwin.doSave();
			}
		},{
			text : '重置',
			iconCls : 'icon-reset',
			onclick : function(e,win,cwin) {
				cwin.doReset();
			}
		},{
			text : '关闭',
			iconCls : 'icon-close',
			onclick : function(e,win) {
				win.close();
			}
		}]
	});
}

/**
 * 修改字典类型数据
 */
function doModifyDictType() {
	if(currentDictTypeId) {
		dictDialog = fastDev.create('Window',{
			src : 'modifyDictType.html?id='+currentDictTypeId+"&pid="+currentDictTypeNode.pid,
			title : '修改字典类型',
			width : 296,
			height : 241,
			allowResize : false,
			showMaxBtn : false,
			buttons : [{
				text : '保存',
				iconCls : 'icon-save',
				onclick : function(e,win,cwin) {
					cwin.doSave();
				}
			},{
				text : '重置',
				iconCls : 'icon-reset',
				onclick : function(e,win,cwin) {
					cwin.doReset();
				}
			},{
				text : '关闭',
				iconCls : 'icon-close',
				onclick : function(e,win) {
					win.close();
				}
			}]
		});
	} else {
		fastDev.alert('请选择要修改的字典类型', '信息提示', 'warning');
	}
}

/**
 * 删除字典类型数据
 */
function doDelDictType() {
	if(currentDictTypeNode) {
		fastDev.confirm('是否删除字典类型“' + currentDictTypeNode.val + '”？','信息提示',function(result){
			if(result) {
				fastDev.create('Proxy',{
					action : 'dict_deleteDictType.action'
				}).save({id : currentDictTypeId}, function(result) {
					fastDev.alert(result.msg, '信息提示', result.status, function(){
						if(result.status == 'ok') {
							delDictTypeTreeNode(currentDictTypeId);
							currentDictTypeId = null;
							currentDictTypeNode = null;
						}
					});
				});
			}
		});
	}
}

/**
 * 增加字典项数据
 */
function doAddDict() {
	dictDialog = fastDev.create('Window', {
		width: "685",
        height: dialogHeight,
        title: "新增字典项",
        src: addUrl + "?dicttypeid=" + currentDictTypeId + "&dicttypename=" + (currentDictTypeNode ? currentDictTypeNode.val : ''),
        allowResize : false,
        showMaxBtn : false,
        buttons : [{
        	text : '保存',
        	iconCls : 'icon-save',
        	onclick : function(e,win,cwin) {
        		cwin.doSave();
        	}
        },{
        	text : '重置',
        	iconCls : 'icon-reset',
        	onclick : function(e,win,cwin) {
        		cwin.doReset();
        	}
        },{
        	text : '关闭',
        	iconCls : 'icon-close',
        	onclick : function(e,win) {
        		win.close();
        	}
        }]
	});
}

/**
 * 修改字典项数据
 * @param data
 */
function doModifyDict(data) {
	if(currentDictTypeNode && currentDictTypeNode.type == "2") {
		var dictid = fastDev.getInstance('dicttree').getCurrentId();
		if(!dictid) {
			fastDev.alert('请先选择记录', '信息提示', 'warning');
			return;
		}
		var node = fastDev.getInstance('dicttree').getNodeByid(dictid);
		if(node.allowmodify != '1') {
			fastDev.alert('该记录不可以修改', '信息提示', 'warning');
			return;
		}
		data = {
			dictid : dictid,
			dicttypeid : currentDictTypeId,
			dicttypename : currentDictTypeNode.val,
			allowdelete : node.allowdelete
		};
	}
	dictDialog = fastDev.create('Window', {
		width: "685",
        height: dialogHeight,
        title: "修改字典",
        src: modifyUrl + "?dicttypeid=" + data.dicttypeid + "&dicttypename=" + data.dicttypename + '&dictid=' + data.dictid + '&allowdelete=' + data.allowdelete,
        allowResize : false,
        showMaxBtn : false,
        buttons : [{
        	text : '保存',
        	iconCls : 'icon-save',
        	onclick : function(e,win,cwin) {
        		cwin.doSave();
        	}
        },{
        	text : '重置',
        	iconCls : 'icon-reset',
        	onclick : function(e,win,cwin) {
        		cwin.doReset();
        	}
        },{
        	text : '关闭',
        	iconCls : 'icon-close',
        	onclick : function(e,win) {
        		win.close();
        	}
        }]
	});
}

/**
 * 删除字典项数据
 * @param data
 * @returns {Boolean}
 */
function doDeleteDict(data) {
	var typeid, dictid, name;
	if(currentDictTypeNode && currentDictTypeNode.type == "2") {
		dictid = fastDev.getInstance('dicttree').getCurrentId();
		if(!dictid) {
			fastDev.alert('请选择记录', '信息提示', 'warning');
			return false;
		}
		var node = fastDev.getInstance('dicttree').getNodeByid(dictid);
		if(node.allowdelete != '1') {
			fastDev.alert('该记录不可以删除', '信息提示', 'warning');
			return;
		}
		typeid = currentDictTypeId;
		name = node.val;
	} else {
		typeid = data.dicttypeid;
		dictid = data.dictid;
		name = data.name;
	}
	if(fastDev.confirm('是否删除“' + name + '”？', '信息提示', function(result){
		if(result) {
			deleteDictById(typeid, dictid);
		}
	}));
}

/**
 * 批量删除字典项
 */
function doBatchDeleteDict() {
	var datagrid = fastDev.getInstance('dictdatagrid');
	var items = datagrid.getValue();
	if(items.length <=0 ) {
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	} else {
		fastDev.confirm("确认是否删除选择的" + items.length + "条记录？", "信息提示", function(result){
			if(result){
				var dictids = [], typeids = [];
				for(var i = 0; i < items.length; i++) {
					typeids.push(items[i].dicttypeid);
					dictids.push(items[i].dictid);
				}
				deleteDictById(typeids.join(','), dictids.join(','));
			}
		});
	}
}

function deleteDictById(typeid,dictid) {
	fastDev.create('Proxy', {
		action : 'dict_moveDictValue.action'
	}).save({typeid:typeid,dictid:dictid},function(result){
		fastDev.alert(result.msg, '信息提示', result.status, function(){
			if(result.status == 'ok') {
				refreshData();
			}
		});
	});
}

/**
 * 关闭对话框
 */
function closeDialog() {
	if(dictDialog) {
		dictDialog.close();
		dictDialog = null;
	}
}

/**
 * 添加树节点
 * @param node
 */
function addDictTypeTreeNode(node) {
	var dicttypetree = fastDev.getInstance('dicttypetree');
	dicttypetree.addNode(node);
}

/**
 * 编辑树节点
 * @param node
 */
function editDictTypeTreeNode(node) {
	fastDev.getInstance('dicttypetree').editNode(node);
}

/**
 * 删除树节点
 * @param id
 */
function delDictTypeTreeNode(id) {
	fastDev.getInstance('dicttypetree').delNode(id);
}