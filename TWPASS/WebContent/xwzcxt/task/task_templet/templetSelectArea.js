var request = fastDev.Browser.getRequest();
var cAreaid = request['cAreaid'];

var loadingWindow = null;

function onBeforeReady() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "cArea",
		text : "加载中..."
	});
}

function onAfterInitRender() {
	if (cAreaid) {
		fastDev.getInstance("cArea").setValue(cAreaid);
	}
	loadingWindow.close();
}