var request = fastDev.Browser.getRequest();
var jobName = request['jobName'];
var jobGroup = request['jobGroup'];


function operSTriggerRenderer() {
	return '<a href="javascript:void(0);" class="btn" style="margin-left:5px;" name="deleteSTrigger">删除</a>';
}

function operCTriggerRenderer() {
	return '<a href="javascript:void(0);" class="btn" style="margin-left:5px;" name="deleteCTrigger">删除</a>';
}

function onRowClick(event,rowindex,data) {
	var target = event.target;
	var name = target['name'];
	if(name) {
		name = fastDev.Util.StringUtil.capitalize(name);
		if(fastDev.isFunction(window['do' + name])) {
			window['do' + name].call(window, data);
		}
	}
}

function onFormBeforeReady() {
	this.setOptions({
		dataSource : 'jobMng_getJobInfo.action?jobName=' + encodeURIComponent(jobName) + '&jobGroup=' + encodeURIComponent(jobGroup)
	});
}

function onFormAfterDataRender() {
	var value = fastDev.getInstance('triggers').getValue();
	if(value) {
		var triggers = fastDev.Util.JsonUtil.parseJson(value);
		if(triggers.striggers) {
			for(var i = 0; i < triggers.striggers.length; i++) {
				fastDev.getInstance('striggerdatagrid').addRow(triggers.striggers[i], false);
			}
		}
		if(triggers.ctriggers) {
			for(var i = 0; i < triggers.ctriggers.length; i++) {
				fastDev.getInstance('ctriggerdatagrid').addRow(triggers.ctriggers[i], false);
			}
		}
	}
}

function onFormSubmitSuccess(result) {
	fastDev.alert(result.msg, '信息提示', result.status, function() {
		if(result.status == 'ok') {
			parent.refreshDatagrid();
			parent.closeDialog();
		}
	});
}

function doSave() {
	fastDev.getInstance('striggerdatagrid').writeBackCell();
	fastDev.getInstance('ctriggerdatagrid').writeBackCell();
	var striggers = fastDev.getInstance('striggerdatagrid').getAllValue();
	var ctriggers = fastDev.getInstance('ctriggerdatagrid').getAllValue();
	if(striggers.length == 0 && ctriggers.length == 0) {
		fastDev.alert('请增加至少一个触发器！');
		return false;
	} 
	if(validateTriggers(striggers, 'S') && validateTriggers(ctriggers, 'C')) {
		doCleanTriggerName(striggers);
		doCleanTriggerName(ctriggers);
		fastDev.getInstance('triggers').setValue(fastDev.Util.JsonUtil.parseString({
			striggers : striggers,
			ctriggers : ctriggers
		}));
		fastDev.getInstance('jobform').submit();
	}
}

function doCleanTriggerName(triggers) {
	if(triggers) {
		for(var i = 0; i < triggers.length; i++) {
			var trigger = triggers[i];
			if(/grid_keyword_/.exec(trigger.triggerName)) {
				trigger.triggerName = '';
			}
		}
	}
}

function doReset() {
	fastDev.getInstance('jobform').doReset();
	onFormAfterDataRender();
}

function validateTriggers(triggers, type) {
	var validateFn = window['validate' + type + 'Trigger'];
	for(var i = 0; i < triggers.length; i++) {
		if(!validateFn(triggers[i], i + 1)) {
			return false;
		}
	}
	return true;
}

function numberRenderer(value) {
	if(isNumber(value)) {
		return Number(value) + '';
	}
	return value;
}

/**
 * 判断字符串是否为数字
 * @param value
 */
function isNumber(value) {
	if(value) {
		return Number(value) == value;
	}
	if((value + '') === '0'){
		return true;
	}
	return false;
}

function validateSTrigger(trigger, i) {
	if(!trigger.startTime) {
		fastDev.alert('简单触发器中第' + i + '行的开始时间不能为空', '信息提示', 'warning');
		return false;
	}
	if(!trigger.repeatInterval || trigger.repeatInterval == '0') {
		fastDev.alert('简单触发器中第' + i + '行的重复间隔/响应时间不能为空或者为0', '信息提示', 'warning');
		return false;
	}
	if(!isNumber(trigger.repeatInterval) || Number(trigger.repeatInterval) < 1) {
		fastDev.alert('简单触发器中第' + i + '行的重复间隔/响应时间只能输入大于0的数字', '信息提示', 'warning');
		return false;
	}
	if(!trigger.repeatCount || trigger.repeatCount == '0') {
		fastDev.alert('简单触发器中第' + i + '行的重复次数不能为空或者为0', '信息提示', 'warning');
		return false;
	}
	if(!isNumber(trigger.repeatCount) || (Number(trigger.repeatCount) < 1 && Number(trigger.repeatCount) != -1)) {
		fastDev.alert('简单触发器中第' + i + '行的重复次数只能输入大于0或者-1的数字', '信息提示', 'warning');
		return false;
	}
	return true;
}

function validateCTrigger(trigger, i) {
	if(!trigger.startTime) {
		fastDev.alert('表达式触发器中第' + i + '行的开始时间不能为空');
		return false;
	}
	if(!trigger.cronExpression) {
		fastDev.alert('表达式触发器中第' + i + '行的表达式不能为空');
		return false;
	}
	return true;
}

/**
 * 删除简单触发器
 * @param data
 */
function doDeleteSTrigger(data) {
	fastDev.getInstance('striggerdatagrid').delRow(data.triggerName);
}

/**
 * 删除表达式触发器
 * @param data
 */
function doDeleteCTrigger(data) {
	fastDev.getInstance('ctriggerdatagrid').delRow(data.triggerName);
}

/**
 * 新增简单触发器
 */
function doAddSTrigger() {
	fastDev.getInstance('striggerdatagrid').addRow();
}

/**
 * 新增表达式触发器
 */
function doAddCTrigger() {
	fastDev.getInstance('ctriggerdatagrid').addRow();
}