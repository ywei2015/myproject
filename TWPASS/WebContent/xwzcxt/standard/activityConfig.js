var activityDialog = null;
//刷新条件
var condition = null;

function onBeforeCompile() {
	var width = fastDev(window).width();
	var height = fastDev(window).height();
	fastDev('#centerPanel').css('width', (width - 211) + 'px');
	var datagrid = fastDev('#permissionDatagrid');
	datagrid.attr('height', (height - 20) + 'px');
	fastDev('#resourceTree').attr('height', (height - 63) + 'px');
}

var currentActivityOP=null;

function valueChange(id,val) {
	//fastDev.tips("你选中的id是"+id+",val是"+val);
	var positiontree = fastDev.getInstance("positiontree");
    var currentId = positiontree.getCurrentId();
    if(currentId != '') {
    	var grid1 = fastDev.getInstance("activityDatagrid1");
    	grid1.addInitReqParam({"tpostactnode.positionid": currentId,"tpostactnode.c_ACTION_ID": id});
    	grid1.refreshData(true);
    	
    	var grid2 = fastDev.getInstance("activityDatagrid2");
    	grid2.addInitReqParam({"tpostactnode.positionid": currentId,"tpostactnode.c_ACTION_ID": id});
    	grid2.refreshData(true);
    }
}

function actRenderer(value)
{
	switch (value) {
	case "0":
		return "否";
	case "1":
		return "是";
	}
}

/**
 * 过滤信息
 */
function doSearch() {
	var fm = fastDev.getInstance('checkform');
	condition = fm.getItems();
	
	refreshDatagrid();
}
/**
 * 重置表单数据
 */
function doReset() {
	fastDev.getInstance('checkform').cleanData();
}

function refreshDatagrid() {
	var positiontree = fastDev.getInstance("positiontree");
    var currentId = positiontree.getCurrentId();
	var o = fastDev.apply(o || {}, condition || {});
	o['tpostactnode.positionid'] = currentId;
	fastDev.getInstance('activityDatagrid1').refreshData(o);
	fastDev.getInstance('activityDatagrid2').refreshData(o);
}


var onNodeClick = function(){
	//只在没有做资源的增加，修改操作才做datagrid的刷新操作
	if(!currentActivityOP){
//	    var positiontree = fastDev.getInstance("positiontree");
//	    var currentId = positiontree.getCurrentId();
//    	var grid1 = fastDev.getInstance("activityDatagrid1");
//    	grid1.addInitReqParam({"tpostactnode.positionid": currentId});
//    	grid1.refreshData(true);
//    	
//    	var grid2 = fastDev.getInstance("activityDatagrid2");
//    	grid2.addInitReqParam({"tpostactnode.positionid": currentId});
//    	grid2.refreshData(true);
		doSearch();
	    
	}	
};

function onAfterDataRender(){
	var obj = fastDev(this.elems[0]);
	obj.height(obj.parent().height()-32);
}

//绑定
var doBinding = function() {
	var list = fastDev.getInstance("activityDatagrid1").getValue();
    if (list.length <= 0) {
        fastDev.alert("请至少选择一条记录进行操作","信息提示","warning");
        return;
    }                
    var ids = new Array();                
    for (var i = 0; i < list.length; i++) {
        var obj = list[i];
        ids.push(obj.c_actnode_id);
    }
    var positiontree = fastDev.getInstance("positiontree");
    var position = positiontree.getCurrentId();
    binding(position, ids.join(","));
}

function binding(position, ids){	
	fastDev.confirm("确定绑定数据？", "确认绑定", function(response) {
		if (response) {
			fastDev.create("Proxy", {
				action : "actNode_doBinding.action"
			}).save({
				position: position,
				ids : ids
			},function(data) {
				fastDev.alert(data.msg, "信息提示", data.status, function() {
					if(data.status=="ok")
						refreshDatagrid();
				});
			});
		}
	});		
}

//解绑
var doUnbind = function() {
	var list = fastDev.getInstance("activityDatagrid2").getValue();
    if (list.length <= 0) {
        fastDev.alert("请至少选择一条记录进行操作","信息提示","warning");
        return;
    }                
    var ids = new Array();                
    for (var i = 0; i < list.length; i++) {
        var obj = list[i];
        ids.push(obj.c_actnode_id);
    }
    var positiontree = fastDev.getInstance("positiontree");
    var position = positiontree.getCurrentId();
    unbind(position, ids.join(","));
}

function unbind(position, ids){	
	fastDev.confirm("确定解除绑定？", "确认取消绑定", function(response) {
		if (response) {
			fastDev.create("Proxy", {
				action : "actNode_doUnbind.action"
			}).save({
				position: position,
				ids : ids
			},function(data) {
				fastDev.alert(data.msg, "信息提示", data.status, function() {
					if(data.status=="ok")
						refreshDatagrid();
				});
			});
		}
	});		
}

