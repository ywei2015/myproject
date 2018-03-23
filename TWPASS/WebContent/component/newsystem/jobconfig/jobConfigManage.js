var jobConfigDialog;
var condition = null;

function onBeforeCompile() {
	var height = fastDev(window).height();
	fastDev('#jobdatagrid').attr('height', (height-92)+'px');
}

/**
 * 渲染操作列
 * @param value
 * @returns
 */
function operRenderer(value) {
	var width = fastDev(this).width();
	return ['<div style="width:'+width+'px;">',
	        '<a href="javascript:void(0);" class="btn" style="margin-left:5px;" name="modifyJobConfig">修改</a>',
	        '<a href="javascript:void(0);" class="btn" style="margin-left:5px;" name="deleteJobConfig">删除</a>',
	        '</div>'].join('');
}

function onRowClick(event,rowindex,data) {
	var target = event.target;
	var name = target['name'];
	if(name) {
		name = fastDev.Util.StringUtil.capitalize(name);
		if(window['do' + name]) {
			window['do' + name].call(window, data);
		}
	}
}

/**
 * 新增任务调度信息
 */
function doAddJobConfig() {
	jobConfigDialog = fastDev.create('Window', {
		width : '771',
	    height : '506',
	    title : '新增调度任务',
	    src : 'jobConfigAdd.html',
		allowResize : false,
		showMaxBtn : false,
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(e,win,cwin) {
				cwin.doSave();
			}
		},{
			text : '重置',
			iconCls : 'icon-reset',
			onclick : function(e,win,cwin) {
				cwin.doReset();
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

/**
 * 修改任务调度信息
 * @param data
 */
function doModifyJobConfig(data) {
	jobConfigDialog = fastDev.create('Window', {
		width : '771',
	    height: '506',
	    title:"修改调度任务",
	    src : "jobConfigModify.html?jobName="+data.jobName+"&jobGroup="+data.jobGroup,
		allowResize : false,
		showMaxBtn : false,
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(e,win,cwin) {
				cwin.doSave();
			}
		},{
			text : '重置',
			iconCls : 'icon-reset',
			onclick : function(e,win,cwin) {
				cwin.doReset();
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

/**
 * 删除任务调度信息
 * @param data
 */
function doDeleteJobConfig(data) {
	fastDev.confirm('是否删除“' + data.jobName + '？”', '信息提示', function(result){
		if(result) {
			deleteJobConfig(data.jobName, data.jobGroup);
		}
	});
}

/**
 * 过滤任务调度信息
 */
function doSearchJobConfig() {
	condition = fastDev.getInstance('jobform').getItems();
	refreshDatagrid();
}

/**
 * 重置任务调度过滤表单
 */
function doResetJobConfig() {
	fastDev.getInstance('jobform').resetData();
}

function deleteJobConfig(jobName, jobGroup) {
	fastDev.create('Proxy',{
		action : 'jobMng_deleteJobs.action'
	}).save({
		jobName : jobName,
		jobGroup : jobGroup
	}, function(result){
		fastDev.alert(result.msg, '信息提示', result.status, function() {
			if(result.status == 'ok') {
				refreshDatagrid();
			}
		});
	});
}

function refreshDatagrid() {
	fastDev.getInstance('jobdatagrid').refreshData(condition);
}

function closeDialog() {
	if(jobConfigDialog) {
		jobConfigDialog.close();
		jobConfigDialog = null;
	}
}