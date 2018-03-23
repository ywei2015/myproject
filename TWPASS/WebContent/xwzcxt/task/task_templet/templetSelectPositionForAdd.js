var request = fastDev.Browser.getRequest();
var orgtype = request['orgtype'];
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
	this.setValue(cPid);
	loadingWindow.close();
}