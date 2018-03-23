var ruleDialog, condition;

function onBeforeCompile() {
	var height = fastDev(window).height();
	fastDev('#datagrid').attr('height', (height - 62) + 'px');
}

function submit(){
	var form = fastDev.getInstance("searchForm");
	condition = form.getItems();
	refreshData();
}

function reset(){
	var form = fastDev.getInstance("searchForm");
	form.cleanData();	
}

function submitSuccess(data){
	var datagrid = fastDev.getInstance("datagrid");
	datagrid.refreshData(data);
}

function addObject(){
	createWindow({
		src : "ruleAdd.html",
		title : "新增数据规则",
		width : "623",
		height : "228",
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(e,win,cwin, fd) {
				var form=fd.getInstance("ruleForm");
				form.submit();				
			}
		},{
			text : '重置',
			iconCls : 'icon-reset',
			onclick : function(e,win,cwin, fd) {
				var form=fd.getInstance("ruleForm");
				form.cleanData();
			}
		}]		
	});	
}

function createWindow(o) {
	var config = fastDev.apply({
		width : "500",
		height : "300",
		showMaxBtn : false,
		allowResize : false
	}, o || {});
	(config.buttons = config.buttons || []).push({
		text : "关闭",
		iconCls : "icon-close",
		onclick : function(e,win) {
			win.close();
		}
	});		
	dialog = fastDev.create('Window', config);
}

function operRenderer() {
	var width = fastDev(this).width();
	return [
			'<div style="width:'
					+ width
					+ 'px;"><a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="modify">修改</a>',
			'<a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="delete">删除</a>']
			.join('');
}

function onRowClick(event, rowindex, data) {
	var target = event.target.id;
	if (target) {
		switch (target) {
		case 'modify':
			createWindow({
				src : "ruleModify.html?id=" + data.ruleId,
				title : "修改数据规则",
				width : "623",
				height : "228",
				buttons : [{
					text : '保存',
					iconCls : 'icon-save',
					onclick : function(e,win,cwin, fd) {
						var form=fd.getInstance("ruleModifyForm");
						form.submit();				
					}
				},{
					text : '重置',
					iconCls : 'icon-reset',
					onclick : function(e,win,cwin, fd) {
						var form=fd.getInstance("ruleModifyForm");
						form.resetData();
					}
				}]					
			});
			break;
		case 'delete':
			fastDev.confirm(('是否删除“' + data.ruleName + '”？'), "信息提示", function(result){
				if(result){
					deleteObject(data.ruleId);
				}
			});
			break;
		}
	}
}

function batchDeleteObject() {
	var datagrid = fastDev.getInstance("datagrid");
	var objects = datagrid.getValue();
	var ids = new Array();
	if (!!objects) {
		for ( var i = 0; i < objects.length; i++)
			ids.push(objects[i].ruleId);
	}
	if(ids.length==0)
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	else{
		fastDev.confirm('是否删除所选择的' + ids.length + '条记录？', '信息提示', function(result) {
			if(result) {
				deleteObject(ids.join(","));
			}
		});
	}
		
		
}

function deleteObject(idStr) {
	fastDev.create("Proxy", {
		action : "dataright_deleteRule.action"
	}).save({
		id : idStr
	},function(data) {
		fastDev.alert(data.msg, "信息", data.status, function() {
			if(data.status == 'ok') {
				refreshData();
			}
		});
	});
}

function refreshData() {
	fastDev.getInstance('datagrid').refreshData(condition);
}

function closeDialog() {
	ruleDialog.close();
}

function onAfterInitRender() {
	showResourceModes('dataright');
}