

var form ;
var condition;
//定义动态id
var c_msg_id;
var value;

//查询动态表单
function searchForm(){
	form= fastDev.getInstance("searchGroupMessageForm");
	condition = form.getItems();
	refreshData(condition);
}
//刷新动态
function refreshData(condition) {
	
	fastDev.getInstance('messagegrid').refreshData(condition);
}
//重置表单
function resetForm(){
	form== fastDev.getInstance("searchGroupMessageForm");
	form.cleanData();
}

function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body-110);
	
}
//动态操作
function operRenderer() {
	var width = fastDev(this).width();
	return  [
				'<div style="width:'
				+ width
				+ 'px;">',
		'<a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="delete">删除</a>']
	.join('');
}
//评论操作
function operRendererDetail(){
	var width = fastDev(this).width();
	return  [
				'<div style="width:'
				+ width
				+ 'px;">',
		'<a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="deleteComment">删除</a>']
	.join('');
}
//评论具体操作
function onRowClickDetail(event, rowindex, data){

	var target = event.target.id;
	if (target=='deleteComment') {
			fastDev.confirm(('是否删除“' + data.c_comm_content + '”？'), "信息提示", function(result){
				if(result){
					deleteGroupMessageComment(data.c_id,data.c_msg_id);
				}
			});
		
	}
}
//删除评论
function  deleteGroupMessageComment(id,msg_id){
	
	fastDev.create("Proxy", {
		action : "ManageGroup_deleteGroupMessageComment.action"
	}).save({
		c_id : id,
		c_msg_id:msg_id
	},function(data) {
		fastDev.alert(data.msg, "信息", data.status, function() {
			if(data.status == 'ok') {
				//刷新评论
				fastDev.getInstance("commentDetailGrid").refreshData(JSON.parse('[{"c_msg_id":"'+msg_id+'"}]'));
			}
		});
	});

}



function onRowClick(event, rowindex, data) {
	c_msg_id=data.c_msg_id;
	var target = event.target.id;
	if (target=='delete') {
			fastDev.confirm(('是否删除“' + data.c_msg_content + '”？'), "信息提示", function(result){
				if(result){
					deleteGroupMessage(data.c_msg_id);
				}
			});
		
	}
}
//删除动态
function deleteGroupMessage(id) {
	fastDev.create("Proxy", {
		action : "ManageGroup_deleteGroupMessage.action"
	}).save({
		c_msg_id : id
	},function(data) {
		fastDev.alert(data.msg, "信息", data.status, function() {
			if(data.status == 'ok') {
				refreshData();
			}
		});
	});
}

//批量删除
function batchDeleteObject() {
	var datagrid = fastDev.getInstance("messagegrid");
	var objects = datagrid.getValue();
	var ids = new Array();
	if (!!objects) {
		for ( var i = 0; i < objects.length; i++)
			ids.push(objects[i].c_msg_id);
	}
	if(ids.length==0)
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	else{
		fastDev.confirm('是否删除所选择的' + ids.length + '条记录？', '信息提示', function(result) {
			if(result) {
				deleteGroupMessage(ids.join(","));
			}
		});
	}
	
}
//加载评论
function onBeforeDetailGridReady(){
	this.setOptions({
		initSource : "ManageGroup_getCommentByMessageId.action?c_msg_id=" + c_msg_id
	});
	
}


function timeRenderer(value){
	value=(value+"").replace("T"," ");
	return value;
}
