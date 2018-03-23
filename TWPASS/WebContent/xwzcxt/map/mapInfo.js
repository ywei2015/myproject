//刷新条件
var condition = null;
var loadingWindow = null;
var extype = getUrlParam('extype');
var cIserror = getUrlParam('cIserror');
var cFinalStatus = getUrlParam('cFinalStatus');
var cIslate = getUrlParam('cIslate');

window.onload = function(){ 
//	  alert(cFinalStatus);
	  if(extype!=null&&extype==1){
	 if(cIserror!=null&&cIserror!=''){	  
	  fastDev.getInstance("cIserror").setValue(cIserror);
	  doSearch();
	 }else if(cFinalStatus!=null&&cFinalStatus!=''){
		 fastDev.getInstance("cFinalStatus").setValue(cFinalStatus);
		  doSearch();
	 }else if(cIslate!=null&&cIslate!=''){
		 fastDev.getInstance("cIslate").setValue(cIslate);
		 fastDev.getInstance("extype").setValue(extype);
		  doSearch();
	 }
	  }else{
		  fastDev.Core.ControlBus.checkSession('portal_validateSessionExpire.action', function(msg) {
				fastDev.alert('登录超时，请重新登录', '信息提示', 'warning', function() {
					top.location = '../component/login.html';
				});
			});
	  }
	}

function onBeforeReady() {
	loadingWindow = fastDev.create('ProgressBar', {
		container : 'checkform',
		text : '加载中...'
	});
	

	this.setOptions({
		initSource : 'jobObjects_getAllTaskPointcheckInfo.action'
	});
}


function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body - 130);
	showResourceModes('DJXX');
	loadingWindow.close();
}

function doSearch() {
	var form = fastDev.getInstance('checkform');
	condition = form.getItems();
	fastDev.getInstance('grid_mapInfo').refreshData(condition);
}

function doReset() {
	fastDev.getInstance('checkform').cleanData();
	condition = null;
}

//获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)'); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); //匹配目标参数
	if (null != r) {
		return unescape(r[2]);
	} else {
		return null; //返回参数值
	}
}

function exportXLS() {
	loadingWindow = fastDev.create('ProgressBar', {
		container : 'checkform',
		text : '导出中...'
	});
	var formData = fastDev.getInstance('checkform').getItems();
	fastDev.create('Proxy', {
		action : 'jobObjects_exportTaskPointcheckInfoExcel.action'
	}).save(formData, function(data) {
		fastDev('#dc').prop('src', data.url);
		loadingWindow.close();
	});	
}


function exportCheckCard() {
	var grid = fastDev.getInstance("grid_mapInfo");
	if(grid==null || grid.getValue().length==0){
		fastDev.tips("请选择数据！");
    	return;
	}
	if(grid.getValue().length>1){
		fastDev.tips("请选择一条数据导出！");
    	return;
	}
	if(grid.getValue().length==1){
		
		var items = grid.getValue();
		var cBasedataId = items[0].cBasedataId;
		
		//alert("cBasedataId=="+cBasedataId);
		
		loadingWindow = fastDev.create('ProgressBar', {
			container : 'checkform',
			text : '导出中...'
		});
		var formData = fastDev.getInstance('checkform').getItems();
		fastDev.create('Proxy', {
			action : 'checkCard_cardExport.action?cBasedataId='+cBasedataId
		}).save(formData, function(data) {
			if(data.result){
				fastDev('#dc').prop('src', data.url);
				loadingWindow.close();
			}else{
				fastDev.tips(data.msg);
				loadingWindow.close();
			}
		});	
	}

}