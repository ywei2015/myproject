var request = fastDev.Browser.getRequest();//获取请求对象
var id = request['id'];


var afterConstruct=function(){
	this.addInitReqParam({"id":id});
};

function operRenderer() {
	var width = fastDev(this).width();
	return '<div style="width:'
					+ width
					+ 'px;"><a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="delete">移除</a>';
}

function onRowClick(event, rowindex, data) {
	var target = event.target.id;
	if(!!target)
		delRuleSet(data.ruleId);
}

var dialog = null;

function addDataRule(){
	dialog = fastDev.create('Window', {
		title : '添加数据权限',
		src : "newsystem/resource/addDataRuleList.html?id="+id,
		width : 730,
		height : 350,
		showMaxBtn : false,
		allowResize : false,
		inside : false
	});
}

function batchDeleteDataRules(){
	var list = fastDev.getInstance("datagrid").getValue();
	if(!list||list.length==0)
	{
		fastDev.alert("请至少选择一条记录进行删除","信息提示","warning");
        return;	
	}
	var ruleIds = new Array();
    for (var i = 0; i < list.length;i++) {
        var obj = list[i];
        ruleIds.push(obj.ruleId);
    }
    delRuleSet(ruleIds.join(","));		
}

function delRuleSet(ruleIds){
	fastDev.confirm("确定删除记录？", "确认删除", function(response) {
		if (response) {
			fastDev.create("Proxy", {
				action : "dataright_unbindPermissions.action"
			}).save({
				pid : id,
				ruleIds:ruleIds
			}, function(data) {
				fastDev.alert(data.msg, "信息提示", data.status, function() {
					if(data.status=="ok")
						fastDev.getInstance("datagrid").refreshData(true);
				});
			});
		}
	});				
}

function closeDialog(){
	parent.dialog.close();
}

function refreshDatagrid() {
	fastDev.getInstance('datagrid').refreshData();
}