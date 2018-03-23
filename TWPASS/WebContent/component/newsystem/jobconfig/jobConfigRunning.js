function onBeforeCompile() {
	var height = fastDev(window).height();
	fastDev('#jobrunetimedatagrid').attr('height', (height-72)+'px');
}

/**
 * 过滤运行日志信息
 */
function doSearchJobRuntime() {
	var condition = fastDev.getInstance('jobruntime').getItems();
	fastDev.getInstance('jobrunetimedatagrid').refreshData(condition);
}

/**
 * 重置运行日志过滤表单
 */
function doResetJobRuntime() {
	fastDev.getInstance('jobruntime').resetData();
}