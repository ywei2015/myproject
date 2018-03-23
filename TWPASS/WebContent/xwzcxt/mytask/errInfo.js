var condition = null;
var dg=fastDev.getInstance('errGrid');


function onBeforeReady(){
	var url;
	this.setOptions({
		initSource:url
	});
}

function onAfterInitRender(){
	var body=fastDev(window).height();
	this.setHeight(body);
}
/**
 * 过滤信息
 */
function doSearch() {
	var fm = fastDev.getInstance('checkform');
	condition = fm.getItems();
    dg.refreshData(condition);
}



/**	
 * 重置表单数据
 */
function doReset() {
	fastDev.getInstance('checkform').cleanData();
}


function showOperation(){
	var btn1='<div itype="Button" onclick="" text="处理"></div>';
	return [btn1].join('');
}

function showUsers(value){
	var userList = fastDev.getInstance('member');
	var position =fastDev.getInstance('position');
	position.setValue('1-'+value);
	userList.clean(true);
	userList.initRefresh({"urlParam" : {"department" : value}});
	// alert(value);
}


