var request = fastDev.Browser.getRequest();
var type = request['type'];
var isUnRead = request['isUnRead'];

function onBeforeReady() {
	this.setOptions({
		initSource : "tPanel/getPanelInfoListAction.action?cPanelType=" + type + "&isUnRead=" + isUnRead
	});
}

function operLinkRenderer(value) {
	return [ '<a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="openDetails" name="openDetails">' + value + '</a>' ].join('');
}

function onRowClick(event, rowindex, rowData) {
	var target = event.target;
	var name = target['name'];
	if (name) {
		name = fastDev.Util.StringUtil.capitalize(name);
		if (window['do' + name]) {
			window['do' + name].call(window, rowData);
		}
	}
}

function doOpenDetails(data) {
	if ("未查阅" == data.cIsreadName) {
		fastDev.get("tPanel/readInformationAction.action?cPanelId=" + data.cPanelId, {
			success : function (data) {
				if ("登录超时，请重新登录" == data) {
					fastDev.alert(data, "信息提示", "warning", function() {
						top.location = "../component/login.html";
					});
				} else {
					fastDev.getInstance('grid').refreshData();
				}
			}
		});
	}
	if ('1' == type) {
		fastDev.create("Dialog", {
			height : 500,
			width : 900,
			inside : false,
			showMaxBtn : false,
			allowResize : false,
			title : "任务详情",
			src : "../xwzcxt/task/taskDetails.html?edit=details&taskId=" + data.cExId,
			buttons : [ {
				text : "关闭",
				align : "center",
				iconCls : "icon-close",
				onclick : function(event, that, win, fast) {
					// 按钮点击事件回调的作用域(this)指向当前按钮控件实例
					// 参数that为当前对话框控件实例
					that.close();
				}
			} ]
		});
	} else {
		fastDev.create("Dialog", {
			height : 536,
			width : 1000,
			inside : false,
			showMaxBtn : false,
			allowResize : false,
			title : "异常详情",
			src : "../xwzcxt/taskerror/taskErrorDetail.html?cErrId=" + data.cExId + "&dg_seq=" + data.dg_seq,
			buttons : [ {
				text : "关闭",
				align : "center",
				iconCls : "icon-close",
				onclick : function(event, that, win, fast) {
					// 按钮点击事件回调的作用域(this)指向当前按钮控件实例
					// 参数that为当前对话框控件实例
					that.close();
				}
			} ]
		});
	}
}