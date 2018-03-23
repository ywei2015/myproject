var request = fastDev.Browser.getRequest();
var cPid = request['cPid'];

var loadingWindow = null;

function onBeforeReady() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "body",
		text : "加载中..."
	});
}

function onAfterInitRender() {
	if (cPid) {
		fastDev.getInstance("cPosition").setValue(cPid);
	}
	loadingWindow.close();
}

function onchangeForCD(id, val) {
	if ("" != id) {
		id = "1-" + id;
		var url = "pbo/getPositionInOrgAction.action?orgtype=7&orgid=" + id;
		fastDev.getInstance("cPosition").reLoad( {url : url} );
	}
}

function onchangeForFQ() {
	if ("" != fastDev("#fuzzyQuery").prop("value")) {
		var url = "pbo/getPositionInOrgAction.action?orgtype=7&orgname=" + fastDev("#fuzzyQuery").prop("value");
		fastDev.getInstance("cPosition").reLoad( {url : url} );
	}
}

function onchangeForIA(event, item) {
	if (item.checked) {
		var url = "pbo/getPositionInOrgAction.action?orgtype=7";
		fastDev.getInstance("cPosition").reLoad( {url : url} );
	}
}