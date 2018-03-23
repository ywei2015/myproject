var request = fastDev.Browser.getRequest();
var orgtype = request['orgtype'];
var module = request['module'];
var cPid = request['cPid'];

var loadingWindow = null;

function onBeforeReady() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "cPosition",
		text : "加载中..."
	});

	this.setOptions({
		initSource : "pbo/getPositionInOrgAction.action?orgtype=" + orgtype
	});
}

function onAfterInitRender() {
//	var that = this;
//	that.hide();
//	showDataModes(module, that, loadingWindow);
	var ids = cPid.split(",");
	for (var i = 0; i < ids.length; i++) {
		this.checkedById(ids[i]);
	}
	loadingWindow.close();
}