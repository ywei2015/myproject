var request = fastDev.Browser.getRequest();
var module = request['module'];
var cPid = request['cPid'];

var loadingWindow = null;


function onBeforeReady() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "cPosition",
		text : "加载中..."
	});
}

function onAfterInitRender() {
	//var that = this;
	loadingWindow.close();
	//that.hide();
	//showDataModes(module, that, loadingWindow);
}