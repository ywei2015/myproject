var condition;

function onBeforeCompile() {
	var height = fastDev(window).height();
	fastDev('#jobdatagrid').attr('height', (height-92)+'px');
}

function operRenderer() {
	return ['<a href="javascript:void(0);" class="btn" style="margin-left:5px;" name="pauseJob">暂停</a>',
	        '<a href="javascript:void(0);" class="btn" style="margin-left:5px;" name="resumeJob">恢复</a>'].join('');
}

function operChildRenderer(value) {
	if(value=='NORMAL') {
		return ['<a href="javascript:void(0);" class="btn" style="margin-left:5px;" name="pauseTrigger">暂停</a>',
		        '<a href="javascript:void(0);" class="btn" style="margin-left:5px;" name="immediateTrigger">立即执行</a>'].join('');
	}
	return '<a href="javascript:void(0);" class="btn" style="margin-left:5px;" name="resumeTrigger">恢复</a>';
}

function onRowClick(event,rowindex,data) {
	fastDev.Event.stopBubble(event);
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

/**
 * 暂停任务
 * @param data
 */
function doPauseJob(data) {
	fastDev.confirm('是否暂停任务？', '信息提示', function(result) {
		if(result) {
			fastDev.create('Proxy', {
				action : 'jobMng_pauseJob.action'
			}).save({
				jobName : data.jobName,
				jobGroup : data.jobGroup
			}, function(result) {
				fastDev.alert(result.msg, '信息提示', result.status, function(){
					if(result.status == 'ok') {
						refreshDatagrid();
					}
				});
			});
		}
	});
}

/**
 * 恢复任务
 * @param data
 */
function doResumeJob(data) {
	fastDev.confirm('是否恢复任务？', '信息提示', function(result) {
		if(result) {
			fastDev.create('Proxy', {
				action : 'jobMng_resumeJob.action'
			}).save({
				jobName : data.jobName,
				jobGroup : data.jobGroup
			}, function(result) {
				fastDev.alert(result.msg, '信息提示', result.status, function(){
					if(result.status == 'ok') {
						refreshDatagrid();
					}
				});
			});
		}
	});
}

/**
 * 暂停触发器
 * @param data
 */
function doPauseTrigger(data) {
	fastDev.confirm('是否暂停触发器？', '信息提示', function(result) {
		if(result) {
			fastDev.create('Proxy', {
				action : 'jobMng_pauseTrigger.action'
			}).save({
				triggerName : data.triggerName,
				triggerGroup : data.triggerGroup
			}, function(result) {
				fastDev.alert(result.msg, '信息提示', result.status, function(){
					if(result.status == 'ok') {
						refreshDatagrid();
					}
				});
			});
		}
	});
}

/**
 * 立即执行触发器
 * @param data
 */
function doImmediateTrigger(data) {
	fastDev.confirm('是否立即执行触发器？', '信息提示', function(result) {
		if(result) {
			fastDev.create('Proxy', {
				action : 'jobMng_triggerImmediate.action'
			}).save({
				jobName : data.jobName,
				jobGroup : data.jobGroup
			}, function(result) {
				fastDev.alert(result.msg, '信息提示', result.status, function(){
					if(result.status == 'ok') {
						refreshDatagrid();
					}
				});
			});
		}
	});
}

/**
 * 恢复触发器
 * @param data
 */
function doResumeTrigger(data) {
	fastDev.confirm('是否恢复触发器？', '信息提示', function(result) {
		if(result) {
			fastDev.create('Proxy', {
				action : 'jobMng_resumeTrigger.action'
			}).save({
				triggerName : data.triggerName,
				triggerGroup : data.triggerGroup
			}, function(result) {
				fastDev.alert(result.msg, '信息提示', result.status, function(){
					if(result.status == 'ok') {
						refreshDatagrid();
					}
				});
			});
		}
	});
}

function refreshDatagrid() {
	fastDev.getInstance('jobdatagrid').refreshData(condition);
}