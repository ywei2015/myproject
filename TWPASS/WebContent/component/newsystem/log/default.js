var positionDialog;

function onBeforeCompile() {
	var height = fastDev(window).height();
	fastDev('#logdatagrid').attr('height', (height-118)+'px');
}

function onRowClick(event,rowindex,data) {
	doShowInfo(data);
}

function statusRenderer(value) {
	if(value == "1") {
		return '<img src="../../fastdev/themes/default/images/ico/accept.gif" title="操作成功"/>';
	}
	return '<img src="../../fastdev/themes/default/images/ico/close.gif" title="操作失败"/>';
}

/**
 * 过滤日志信息
 */
function doSearchLog() {
	var condition = fastDev.getInstance('logform').getItems();
	var funcGroupId = condition['log.funcGroupId'];
	if(/^app/.test(funcGroupId)) {
		condition['log.appId'] = funcGroupId;
		condition['log.funcGroupId'] = '';
	} else {
		condition['log.appId'] = '';
	}
	if(condition['log.funcGroupId'] == '-1') {
		condition['log.funcGroupId'] = '';
	}
	fastDev.getInstance('logdatagrid').refreshData(condition);
}

/**
 * 重置过滤表单
 */
function doResetLog() {
	fastDev.getInstance('logform').resetData();
}

/**
 * 显示岗位详细信息
 * @param data
 */
function doShowInfo(data) {
	logDialog = fastDev.create('Window', {
		width : 629,
		height : 372,
		showMaxBtn : false,
		allowResize : false,
		title : '查看日志信息',
		src : 'logInfo.html?id=' + data.logId + '&actionType=' + (data.type || ''),
		buttons:[{
			text : '关闭',
			iconCls : "icon-close",
			onclick : function(e,win) {
				win.close();
			}
		}]
	});
}

/**
 * 导出日志信息
 */
function doExportLog() {
	logDialog = fastDev.create('Window', {
		width : 600,
		height : 140,
		showMaxBtn : false,
		allowResize : false,
		title : '选择日志属性',
		src : 'logAttribute.html',
		buttons:[{
			text : '关闭',
			iconCls : "icon-close",
			onclick : function(e,win) {
				win.close();
			}
		}]
	});
}
