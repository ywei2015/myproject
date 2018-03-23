var cObjectTypeid = getUrlParam('cObjectTypeid');

function onAfterInitTabs(){
	this.resize(window.innerWidth,window.innerHeight);//设置宽度跟高度
	this.setUrlByTitle("长沙卷烟厂","../../mobile/map/map.html?cObjectTypeid="+cObjectTypeid+"&mainMap=csjyc");
	this.setUrlByTitle("大托仓库","../../mobile/map/map.html?cObjectTypeid="+cObjectTypeid+"&mainMap=datuo");
	this.setUrlByTitle("地名2","../../mobile/map/map.html?cObjectTypeid="+cObjectTypeid+"&mainMap=csjyc");
}

function onAfterDataRenderTabs( ){
	alert(this.getId());
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