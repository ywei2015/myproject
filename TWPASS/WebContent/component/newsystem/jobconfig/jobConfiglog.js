function onBeforeCompile() {
	var height = fastDev(window).height();
	fastDev('#joblogdatagrid').attr('height', (height-89)+'px');
}

/**
 * 过滤历史日志信息
 */
function doSearchJobLog() {
	var condition = fastDev.getInstance('joblogform').getItems();
	fastDev.getInstance('joblogdatagrid').refreshData(condition);
}

/**
 * 重置历史日志过滤表单
 */
function doResetJobLog() {
	fastDev.getInstance('joblogform').resetData();
}