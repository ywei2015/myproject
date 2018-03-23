var request = fastDev.Browser.getRequest();

var g_event = request['event'];
var g_objId = request['objId'];
var g_typeId = request['objtypeId'];
var form;
var submitSuccess=function(data){
	fastDev.alert(data.msg, "信息提示", data.result,function(){
		if (data.result >= 0){//成功
			parent.appDialog.close();
			parent.refreshDatagrid();
		}
	});
};

function doSearchArea() {
	SearchArea(function(area){
		var areas = new Array();
		areas = area.split(",");
		fastDev.getInstance('basicMouldPojo.c_area').setValue(areas[0]);
		fastDev.getInstance('basicMouldPojo.areaname').setValue(areas[1]);
	});
}

function SearchArea(fn){
	fastDev.create('Window', {
		title : '选择区域',
		width : 445,
		height : 498,
		inside : false,
		showMaxBtn : false,
		allowResize : false,
		src : '../xwzcxt/basic/addBasicArea.html',
		buttons : [{
			text : '确定',
			iconCls : 'icon-save',
			onclick : function(e,win,cwin) {
				var area = cwin.getArea();
				if(area.length == 0) {
					fastDev.alert('请选择一条记录进行操作', '信息提示', 'warning');
				} else {
					if(fn(area) !== false) {
						win.close();
					}
				}
			}
		},{
			text : '关闭',
			iconCls : 'icon-close',
			onclick : function(e,win) {
				win.close();
			}
		}]
	});
}

function doSearchOrg(){
	SearchOrg(function(org){
		var orgs = new Array();
		orgs = org.split(",");
		fastDev.getInstance('basicMouldPojo.c_orgid').setValue(orgs[0]);
		fastDev.getInstance('basicMouldPojo.orgname').setValue(orgs[1]);
		
		//在组织选择完成后，搜索对应的用户ID
		var actionUser="user_queryUsersByOrgForSel?orgid="+orgs[0];
		var proxy = fastDev.create('Proxy', {
			action : actionUser
		});
		/*proxy.save({orgid:orgs[0]}, function(result){
			console.info(result);
			fastDev.alert(result.msg, '信息提示', result.status, function(){
				if(result.status == 'ok') {
					refreshDatagrid();
				}
			});
		});*/
	});
}

function SearchOrg(fn){
	fastDev.create('Window', {
		title : '选择区域',
		width : 445,
		height : 498,
		inside : false,
		showMaxBtn : false,
		allowResize : false,
		src : '../xwzcxt/basic/addBasicOrg.html',
		buttons : [{
			text : '确定',
			iconCls : 'icon-save',
			onclick : function(e,win,cwin) {
				var org = cwin.getOrg();
				if(org.length == 0) {
					fastDev.alert('请选择一条记录进行操作', '信息提示', 'warning');
				} else {
					if(fn(org) !== false) {
						win.close();
					}
				}
			}
		},{
			text : '关闭',
			iconCls : 'icon-close',
			onclick : function(e,win) {
				win.close();
			}
		}]
	});
}

function doSearchObjtype(){
	SearchObjtype(function(area){
		var areas = new Array();
		areas = area.split(",");
		fastDev.getInstance('basicMouldPojo.c_objtype_id').setValue(areas[0]);
		fastDev.getInstance('basicMouldPojo.c_objtype_name').setValue(areas[1]);
	});
}

function SearchObjtype(fn){
	fastDev.create('Window', {
		title : '选择区域',
		width : 445,
		height : 498,
		inside : false,
		showMaxBtn : false,
		allowResize : false,
		src : '../xwzcxt/basic/addBasicObjtype.html',
		buttons : [{
			text : '确定',
			iconCls : 'icon-save',
			onclick : function(e,win,cwin) {
				var objtype = cwin.getObjtype();
				if(objtype.length == 0) {
					fastDev.alert('请选择一条记录进行操作', '信息提示', 'warning');
				} else {
					if(fn(objtype) !== false) {
						win.close();
					}
				}
			}
		},{
			text : '关闭',
			iconCls : 'icon-close',
			onclick : function(e,win) {
				win.close();
			}
		}]
	});
}

function FormReady(){
	form = this;
	var action = "";
	var dataSource = "";
	if (g_event=="edit"){
		action = "basicAdd_modifyBasicData.action?objId="+g_objId;
		dataSource = "basicAdd_getBasicDataByObjId.action?objId="+g_objId;
		form.setOptions({
			"action":action,
			"dataSource":dataSource
			});
	}else{
		action = "basicAdd_addBasic.action";
		form.setOptions({
			"action":action,
			});
	}
}


