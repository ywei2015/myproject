var condition = null;
var childwindow = null;

function onBeforeReady() {
	this.setOptions({
		initSource : "ruleposition/getAllRulePositionsAction.action"
	});
}

function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body - 130);
}

/**
 * 过滤信息
 */
function doSearch() {
	var form = fastDev.getInstance("checkform");
	condition = form.getItems();
	fastDev.getInstance('grid_ruleposition').refreshData(condition);
	console.info(condition);
}
/**
 * 重置表单数据
 */
function doReset() {
	fastDev.getInstance('checkform').cleanData();
	condition = null;
}

function closeDialog() {
	if (childwindow) {
		childwindow.close();
		childwindow = null;
	}
}

function refreshDatagrid() {
	var o = condition || {};
	fastDev.getInstance('grid_ruleposition').refreshData(o);
}

function addpositionrule() {
	childwindow = fastDev.create('Window', {
		id : "window_add",
		width : 700,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : '新建岗位规则',
		allowResize : false,
		src : '../xwzcxt/rule/positionRuleAdd.html',
		buttons : [ {
			text : '保存',
			iconCls : 'icon-add',
			onclick : function(event, win, childWin, fast) {
				var form = fast.getInstance("addPositionRuleForm");
				form.submit();
			}
		}, {
			text : '关闭',
			iconCls : 'icon-close',
			onclick : function(event, win) {
				win.close();
			}
		} ]
	});
}


