var loadingWindow = null;

function onBeforeReady() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "cPosition",
		text : "加载中..."
	});
}

function onAfterInitRender() {
	var cPosition = fastDev.getInstance("cPosition");
	var idArray = fastDev.Ui.Window.getData("idArray");
	for (var i = (idArray.length - 1); i > 1; i--) {
		var array = cPosition.getNodesByPid(idArray[i]);
		var j = 0;
		while (array.length > 1) {
			if (idArray[i - 1] != array[j].id) {
				cPosition.delNode(array[j].id);
			} else {
				j++;
			}
		}
	}
	cPosition.disableChk(idArray[0]);
	loadingWindow.close();
}

function showWaitingDialog() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "cPosition",
		text : "复制中..."
	});
}