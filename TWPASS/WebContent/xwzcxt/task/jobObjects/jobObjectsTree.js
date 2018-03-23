var request = fastDev.Browser.getRequest();
var nodeId = request['nodeId'];
var positionId = request['positionId'];

function onAfterInitRender() {
	var cjobobjectTree = fastDev.getInstance("cjobobjectTree");
	if (nodeId) {
		cjobobjectTree.setValue(nodeId);
	}
}

function onAfterInit(){
	if(positionId==undefined){
		positionId = "";
	}
	var cjobobjectTree = fastDev.getInstance("cjobobjectTree");
	cjobobjectTree.clearRadio();
	cjobobjectTree.initRefresh({
		"url" : "jobObjects_selectJobObjectTree.action",
		"urlParam" : {"positionId" : positionId}
	});
}
