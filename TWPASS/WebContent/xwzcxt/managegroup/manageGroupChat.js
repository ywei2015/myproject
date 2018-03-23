
//定义查询工作圈聊天记录表单
var form ;
var condition;



//查询表单
function searchForm(){
	form= fastDev.getInstance("searchGroupChatForm");
	condition = form.getItems();
	refreshData(condition);
}
//刷新table
function refreshData(condition) {
	fastDev.getInstance('chatgrid').refreshData(condition);
}
//重置表单
function resetForm(){
	form== fastDev.getInstance("searchGroupChatForm");
	form.cleanData();
}

function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body-110);
	
}
//操作
function operRenderer() {
	var width = fastDev(this).width();
	return  [
				'<div titleCls="alignClass" style="width:'
				+ width
				+ 'px;">',
		'<a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="delete">删除</a>']
	.join('');
}

function onRowClick(event, rowindex, data) {
	var target = event.target.id;
	if (target=='delete') {
			fastDev.confirm(('是否删除“' + data.c_chat_text + '”？'), "信息提示", function(result){
				if(result){
					deleteGroupChat(data.c_id);
				}
			});
		
	}
}
//删除聊天记录
function deleteGroupChat(id) {
	fastDev.create("Proxy", {
		action : "ManageGroup_deleteGroupChat.action"
	}).save({
		c_id : id
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
	var datagrid = fastDev.getInstance("chatgrid");
	var objects = datagrid.getValue();
	var ids = new Array();
	if (!!objects) {
		for ( var i = 0; i < objects.length; i++)
			ids.push(objects[i].c_id);
	}
	if(ids.length==0)
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	else{
		fastDev.confirm('是否删除所选择的' + ids.length + '条记录？', '信息提示', function(result) {
			if(result) {
				deleteGroupChat(ids.join(","));
			}
		});
	}
	
}
function timeRenderer(value){
	value=(value+"").replace("T"," ");
	return value;
}
