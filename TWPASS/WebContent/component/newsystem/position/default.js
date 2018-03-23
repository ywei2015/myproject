var positionDialog;
var condition = null;

function onBeforeCompile() {
	var height = fastDev(window).height();
	fastDev('#positiondatagrid').attr('height', (height-90)+'px');
}

/**
 * 渲染用户姓名列
 * @param value
 * @returns {String}
 */
function displayNameRenderer(value) {
	return '<a href="javascript:void(0);" class="btn" name="showInfo">' + value + '</a>';
}

/**
 * 渲染操作列
 * @param value
 * @returns
 */
function operRenderer(value) {
	var width = fastDev(this).width();
	return ['<div style="width:'+width+'px;">',
	        '<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="modify" name="modifyPosition">修改</a>',
	        '<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="delete" name="deletePosition">删除</a>',
	        	'<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="assrole" name="assRole">关联角色</a>',
	        	'<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="authview" name="authView">权限视图</a>',
	        '</div>'].join('');
}

function onAfterInitRender() {
	showResourceModes('position', fastDev.getInstance('positiondatagrid'));
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

function onRowDblClick(event, rowindex, data) {
	doShowInfo(data);
}

/**
 * 过滤岗位信息
 */
function doSearchPosition() {
	condition = fastDev.getInstance('positionform').getItems();
	if(condition['position.orgId'] == '-1') {
		condition['position.orgId'] = '';
	}
	refreshDatagrid();
}

/**
 * 重置过滤表单
 */
function doResetPosition() {
	fastDev.getInstance('positionform').resetData();
}

function createWindow(o) {
	o.src = "newsystem/position/"+o.src;	
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
		onclick : function(e, win) {
			win.close();
		}
	});
	positionDialog = fastDev.create('Window', config);
	
}

/**
 * 显示岗位详细信息
 * @param data
 */
function doShowInfo(data) {
	createWindow({
		width : 506,
		height : 166,
		title : '查看岗位信息',
		src : 'positioninfo.html?positionid=' + data.positionId
	});	
}

/**
 * 关联角色
 * @param data
 */
function doAssRole(data) {
	positionDialog = fastDev.create('Window',{
		title: "岗位已经关联的角色",
        iscenter: true,
        width: "800",
        height: "339",
		inside : false,
		showMaxBtn : false,
		allowResize : false,
        src: "newsystem/position/addrole.html?positionid=" + data.positionId
	});	
}

/**
 * 权限视图
 * @param data
 */
function doAuthView(data) {
	createWindow({
		width: "783",
		title : "岗位权限视图",
		height: "378",
		src : "posRightView.html?positionid="+data.positionId +"&posname=" + data.posiName
	});		
}

/**
 * 修改岗位信息
 * @param data
 */
function doModifyPosition(data) {
	createWindow({
		title: "编辑岗位信息",
		width: "640",
        height: "160",
        src: "modifyposition.html?positionid=" + data.positionId + '&orgid=' + data.orgId,
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
        }]        
	});		
}

/**
 * 删除岗位信息
 * @param data
 */
function doDeletePosition(data) {
	fastDev.confirm('确认删除“'+ data.posiName +'”?', '信息提示', function(result){
		if(result) {
			deletePositionById(data.positionId);
		}
	});
}

function refreshDatagrid() {
	fastDev.getInstance('positiondatagrid').refreshData(condition);
}

function closeDialog() {
	if(positionDialog) {
		positionDialog.close();
		positionDialog = null;
	}
}

/**
 * 添加岗位信息
 */
function doAddPosition() {
	createWindow({
		title: "新增岗位",
        width: "640",
        height: "160",
        src: "addposition.html",
        buttons : [{
        	text : '保存',
        	iconCls : 'icon-save',
        	onclick : function(e,win,cwin, fast) {
        		cwin.doSave();
        	}
        },{
        	text : '重置',
        	iconCls : 'icon-reset',
        	onclick : function(e,win,cwin, fast) {
        		cwin.doReset();
        	}
        }]
	});		
}

/**
 * 批量删除岗位信息
 */
function doDelManyPositions() {
	var datagrid = fastDev.getInstance('positiondatagrid');
	var items = datagrid.getValue();
	if(items.length <=0 ) {
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	} else {
		fastDev.confirm("确认是否删除选择的" + items.length + "条记录？", "信息提示", function(result){
			if(result){
				var ids = [];
				for(var i = 0; i < items.length; i++) {
					ids.push(items[i].positionId);
				}
				deletePositionById(ids.join(","));
			}
		});
	}
}

function deletePositionById(id) {
	var proxy = fastDev.create('Proxy', {
		action : 'position_delPositions.action'
	});
	proxy.save({id:id}, function(result){
		fastDev.alert(result.msg, '信息提示', result.status, function(){
			if(result.status == 'ok') {
				refreshDatagrid();
			}
		});
	});
}