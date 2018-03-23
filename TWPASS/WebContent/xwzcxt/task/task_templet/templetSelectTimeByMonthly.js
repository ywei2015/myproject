var request = fastDev.Browser.getRequest();
var cOrgId = request['cOrgId'];
var cTid = request['cTid'];

var childWindow = null;
var loadingWindow = null;

function onBeforeReady() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "cTid",
		text : "加载中..."
	});

	this.setOptions({
		initSource: "getRuleTimeTreeAction.action?cDepartment=" + cOrgId
	});
}

function onAfterInitRender() {
	this.delNode("2");
	this.delNode("3");
	this.delNode("4");
	if (cTid) {
		fastDev.getInstance("cTid").setValue(cTid);
	}
	loadingWindow.close();
}

function doAdd() {
	childWindow = fastDev.create('Window', {
		id : "add",
		width : 700,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : '新建时间规则',
		allowResize : false,
		src : '../xwzcxt/rule/timeRuleAdd.html?cModifyType=2&cTimeruleId=null&cTimeruleType=5&cDepartment=' + '1-' + cOrgId,
		buttons : [ {
			text : '保存',
			align : 'center',
			iconCls : 'icon-add',
			onclick : function(event, win, childWin, fast) {
				fastDev.create("Dialog", {
					width : 450,
					inside : false,
					showMaxBtn : false,
					title : '请确认',
					content : "当前时间规则名称为：" + fast.getInstance("cTimeruleName").getValue() + "<br/>是否需要修改时间规则名称？",
					bodyStyle : 'text-align:center;padding:20px;font-size:15px;',
					allowResize : false,
					buttons : [ {
						text : "修改",
						align : "center",
						onclick : function(_event, _that, _win, _fast) {
							fast.getInstance("cTimeruleName").enable();
							_that.close();
						}
					}, {
						text : "不修改",
						align : "center",
						onclick : function(_event, _that, _win, _fast) {
							fast.getInstance("cDepartments").setValue('1-' + cOrgId);
							var form = fast.getInstance("addTimeRuleForm");
							form.submit();
							_that.close();
						}
					} ]
				});
			}
		}, {
			text : '关闭',
			align : 'center',
			iconCls : 'icon-close',
			onclick : function(event, win) {
				win.close();
			}
		} ]
	});
}

function refreshDatagrid() {
	fastDev.getInstance("cTid").reLoad();
}

function closeDialog() {
	if (null != childWindow) {
		childWindow.close();
		childWindow = null;
	}
}