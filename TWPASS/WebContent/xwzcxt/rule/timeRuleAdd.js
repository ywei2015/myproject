var request = fastDev.Browser.getRequest();
var cModifyType = request['cModifyType'];
var cTimeruleId = request['cTimeruleId'];
var cTimeruleType = request['cTimeruleType'];
var cDepartment = request['cDepartment'];
var s1 = "在";
var s2 = "执行计划";
var sonce = "";
var stype = "";// 每班，每天，每周，每月
var soffset = "";
var soffset1 = "";
var soffset2 = "";
var sday = "每天";
var sday1 = "每天";
var sday2 = "每天";
var sweek1 = "每周";
var sweek2 = "";
var smonth = "每月";
var smonth1 = "每月";
var smonth2 = "每月";
var shour = "00:00:00";
var shour1 = "00:00:00";
var shour2 = "00:00:00";
var sbegin = "";
var send = "";
var sremark = "";
var sname = "";
var smin = "";
var g_timerule_id = "";

var loadingWindow = null;

function onBeforeReady() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "addTimeRuleForm",
		text : "加载中..."
	});

	var action = "";
	var dataSource = "";
	if ("null" != cTimeruleId) {
		action = "ruletime/addTimeRuleAction.action";
		dataSource = "ruletime/getRuleTimeByIDAction.action?cTimeruleId=" + cTimeruleId;
	} else {
		action = "ruletime/addTimeRuleAction.action";
	}
	this.setOptions({
		action : action,
		dataSource: dataSource
	});
}

function onAfterReady() {
	$('#day').hide();
	$('#day1').hide();
	$('#week').hide();
	$('#month').hide();
}

function onBeforeInit() {
	showWorkgroup("1");
}

function onAfterDataRender() {
	if ("null" != cTimeruleId) {
		switch (fastDev.getInstance("cTimeruleType").getValue()) {
			case "1": // 执行一次
				fastDev.getInstance("cFirsttimeExecHour").disable();
				fastDev.getInstance("cFirsttimeExecMinute").disable();
				fastDev.getInstance("cFirsttimeExecSecond").disable();
				fastDev.getInstance("cOffsetWorkgroup").disable();
				fastDev.getInstance("cOffsetMinute").disable();
				break;
			case "2": // 每班
				$('#day').hide();
				$('#day1').hide();
				$('#week').hide();
				$('#month').hide();
				fastDev.getInstance("cFirsttimeExecHour").disable();
				fastDev.getInstance("cFirsttimeExecMinute").disable();
				fastDev.getInstance("cFirsttimeExecSecond").disable();
				fastDev.getInstance("cOffsetWorkgroup").enable();
				fastDev.getInstance("cOffsetMinute").enable();
				break;
			case "3": // 每天
				$('#day').hide();
				$('#day1').hide();
				$('#week').hide();
				$('#month').hide();
				fastDev.getInstance("cFirsttimeExecHour").enable();
				fastDev.getInstance("cFirsttimeExecMinute").enable();
				fastDev.getInstance("cFirsttimeExecSecond").enable();
				fastDev.getInstance("cOffsetWorkgroup").disable();
				fastDev.getInstance("cOffsetMinute").disable();
				break;
			case "4": // 每周
				$('#day').hide();
				$('#day1').hide();
				$('#week').show();
				$('#month').hide();
				fastDev.getInstance("cFirsttimeExecHour").enable();
				fastDev.getInstance("cFirsttimeExecMinute").enable();
				fastDev.getInstance("cFirsttimeExecSecond").enable();
				fastDev.getInstance("cOffsetWorkgroup").disable();
				fastDev.getInstance("cOffsetMinute").disable();
				break;
			case "5": // 每月
				$('#day').show();
				$('#day1').hide();
				$('#week').hide();
				$('#month').hide();
				fastDev.getInstance("cFirsttimeExecHour").enable();
				fastDev.getInstance("cFirsttimeExecMinute").enable();
				fastDev.getInstance("cFirsttimeExecSecond").enable();
				fastDev.getInstance("cOffsetWorkgroup").disable();
				fastDev.getInstance("cOffsetMinute").disable();
				break;
			case "51": // 每月
				$('#day').hide();
				$('#day1').show();
				$('#week').hide();
				$('#month').hide();
				fastDev.getInstance("cFirsttimeExecHour").disable();
				fastDev.getInstance("cFirsttimeExecMinute").disable();
				fastDev.getInstance("cFirsttimeExecSecond").disable();
				fastDev.getInstance("cOffsetWorkgroup").disable();
				fastDev.getInstance("cOffsetMinute").disable();
				break;
			default:
				break;
		}
		if (fastDev.getInstance("cTimeruleEnd").getValue()) {
			fastDev.getInstance("checkEndModel").setValue("1");
		} else {
			fastDev.getInstance("checkEndModel").setValue("2");
			fastDev.getInstance("cTimeruleEnd").disable();
		}
	}

	//编辑模式为查看时不可编辑数据
	if ("0" == cModifyType) {
		fastDev.getInstance("cTimeruleType").disable();
		fastDev.getInstance("cDepartment").disable();
		fastDev.getInstance("cTimeruleName").disable();
		fastDev.getInstance("execOnceTime").disable();
		fastDev.getInstance("cOffsetDay").disable();
		fastDev.getInstance("cOffsetW").disable();
		fastDev.getInstance("cOffsetWeek").disable();
		fastDev.getInstance("cOffsetMonth").disable();
		fastDev.getInstance("checkHourModel").disable();
		fastDev.getInstance("cFirsttimeExecHour").disable();
		fastDev.getInstance("cFirsttimeExecMinute").disable();
		fastDev.getInstance("cFirsttimeExecSecond").disable();
		fastDev.getInstance("cOffsetDay1").disable();
		fastDev.getInstance("cFirsttimeExecHour1").disable();
		fastDev.getInstance("cFirsttimeExecMinute1").disable();
		fastDev.getInstance("cFirsttimeExecSecond1").disable();
		fastDev.getInstance("cOffsetDay2").disable();
		fastDev.getInstance("cFirsttimeExecHour2").disable();
		fastDev.getInstance("cFirsttimeExecMinute2").disable();
		fastDev.getInstance("cFirsttimeExecSecond2").disable();
		fastDev.getInstance("cOffsetWorkgroup").disable();
		fastDev.getInstance("cOffsetMinute").disable();
		fastDev.getInstance("cOffsetHourUnit").disable();
		fastDev.getInstance("cOffsetHour").disable();
		fastDev.getInstance("cTimeruleBegin").disable();
		fastDev.getInstance("checkEndModel").disable();
		fastDev.getInstance("cTimeruleEnd").disable();
		fastDev.getInstance("cRemark").disable();
	} else if ("1" == cModifyType) {
		fastDev.getInstance("cTimeruleType").disable();
		fastDev.getInstance("cDepartment").disable();
	}
	fastDev.getInstance("cModifyType").setValue(cModifyType);

	loadingWindow.close();
}

function onAfterInitRender() {
	fastDev.getInstance("cTimeruleName").disable();
	if (cDepartment) {
		fastDev.getInstance("cTimeruleType").setValue(cTimeruleType);
		fastDev.getInstance("cDepartment").setValue(cDepartment);
		fastDev.getInstance("cDepartment").getTree().checkedById(cDepartment);
		if ("5" == cTimeruleType) {
			fastDev.getInstance("cTimeruleType").disableItems("2");
			fastDev.getInstance("cTimeruleType").disableItems("3");
			fastDev.getInstance("cTimeruleType").disableItems("4");
		} else {
			fastDev.getInstance("cTimeruleType").disable();
		}
		fastDev.getInstance("cDepartment").disable();
		onTypeChange(cTimeruleType);
	}
}

function onOffsetDayChange() {
	var a = fastDev.getInstance("cTimeruleType").getValue();
	var value = fastDev.getInstance("cOffsetDay").getValue();
	switch (a) {
		case "3":
			sday = value + "号";
			soffset = sday;
			showRemark();
			break;
		case "5":
			if (value != "0" && value != "") {
				sday = value + "号";
			} else {
				sday = "";
			}
			smonth = "每" + fastDev.getInstance("cOffsetMonth").getValue() + "个月";
			soffset = smonth + sday;
			showRemark();
		default:
			break;
	}
}

function onOffsetMonthChange() {
	var value = fastDev.getInstance("cOffsetDay").getValue();
	if (value != "0" && value != "") {
		sday = value + "号";
	} else {
		sday = "1号";
	}
	smonth = "每" + fastDev.getInstance("cOffsetMonth").getValue() + "个月";
	soffset = smonth + sday;
	showRemark();
}

function onOffsetWeek1Change() {
	var value = fastDev.getInstance("cOffsetW").getValue();
	if (value != "0" && value != "") {
		sweek1 = "每" + value + "周";
	} else {
		sweek1 = "每周";
	}
	soffset = sweek1 + sweek2;
	showRemark();
}

function onOffsetWeek2Change() {
	var value = fastDev.getInstance("cOffsetW").getValue();
	if (value != "0" && value != "") {
		sweek1 = "每" + value + "周";
	} else {
		sweek1 = "每周";
	}
	if (fastDev.getInstance("cOffsetWeek").getCheckedItems() == null) {
		sweek2 = "";
	} else {
		sweek2 = fastDev.getInstance("cOffsetWeek").getValue();
	}
	soffset = sweek1 + sweek2;
	showRemark();
}

function onTimeChange() {
	var hour = fastDev.getInstance("cFirsttimeExecHour").getValue();
	if (1 == hour.length) {
		hour = "0" + hour;
		fastDev.getInstance("cFirsttimeExecHour").setValue(hour);
	}
	var minute = fastDev.getInstance("cFirsttimeExecMinute").getValue();
	if (1 == minute.length) {
		minute = "0" + minute;
		fastDev.getInstance("cFirsttimeExecMinute").setValue(minute);
	}
	var second = fastDev.getInstance("cFirsttimeExecSecond").getValue();
	if (1 == second.length) {
		second = "0" + second;
		fastDev.getInstance("cFirsttimeExecSecond").setValue(second);
	}
	fastDev.getInstance("cFirsttimeExec").setValue(hour + ":" + minute + ":" + second);
	onOnceInDayChange();
}

function onOnceInDayChange() {
	shour = fastDev.getInstance("cFirsttimeExec").getValue();
	var type = fastDev.getInstance("cTimeruleType").getValue();
	switch (type) {
		case "2":
			soffset = "每班";
			break;
		case "3":
			soffset = sday;
			break;
		case "4":
			soffset = sweek1 + sweek2;
			break;
		case "5":
			if (sday == "") {
				sday = "1号";
			} else if (sday == "每天") {
				sday = "1号";
			}
			soffset = smonth + sday;
		default:
			break;
	}
	showRemark();
}

function onOffsetDayChange1() {
	var value = fastDev.getInstance("cOffsetDay1").getValue();
	if (value != "0" && value != "") {
		sday1 = value + "号";
	} else {
		sday1 = "";
	}
	smonth1 = "每" + fastDev.getInstance("cOffsetMonth").getValue() + "个月";
	soffset1 = smonth1 + sday1;
	showRemark();
}

function onOffsetDayChange2() {
	var value = fastDev.getInstance("cOffsetDay2").getValue();
	if (value != "0" && value != "") {
		sday2 = value + "号";
	} else {
		sday2 = "";
	}
	smonth2 = "每" + fastDev.getInstance("cOffsetMonth").getValue() + "个月";
	soffset2 = smonth2 + sday2;
	showRemark();
}

function onTimeChange1() {
	var hour = fastDev.getInstance("cFirsttimeExecHour1").getValue();
	if (1 == hour.length) {
		hour = "0" + hour;
		fastDev.getInstance("cFirsttimeExecHour1").setValue(hour);
	}
	var minute = fastDev.getInstance("cFirsttimeExecMinute1").getValue();
	if (1 == minute.length) {
		minute = "0" + minute;
		fastDev.getInstance("cFirsttimeExecMinute1").setValue(minute);
	}
	var second = fastDev.getInstance("cFirsttimeExecSecond1").getValue();
	if (1 == second.length) {
		second = "0" + second;
		fastDev.getInstance("cFirsttimeExecSecond1").setValue(second);
	}
	fastDev.getInstance("cFirsttimeExec1").setValue(hour + ":" + minute + ":" + second);
	onOnceInDayChange1(1);
}

function onTimeChange2() {
	var hour = fastDev.getInstance("cFirsttimeExecHour2").getValue();
	if (1 == hour.length) {
		hour = "0" + hour;
		fastDev.getInstance("cFirsttimeExecHour2").setValue(hour);
	}
	var minute = fastDev.getInstance("cFirsttimeExecMinute2").getValue();
	if (1 == minute.length) {
		minute = "0" + minute;
		fastDev.getInstance("cFirsttimeExecMinute2").setValue(minute);
	}
	var second = fastDev.getInstance("cFirsttimeExecSecond2").getValue();
	if (1 == second.length) {
		second = "0" + second;
		fastDev.getInstance("cFirsttimeExecSecond2").setValue(second);
	}
	fastDev.getInstance("cFirsttimeExec2").setValue(hour + ":" + minute + ":" + second);
	onOnceInDayChange1(2);
}

function onOnceInDayChange1(index) {
	if (1 == index) {
		shour1 = fastDev.getInstance("cFirsttimeExec1").getValue();
		if (sday1 == "") {
			sday1 = "1号";
		} else if (sday1 == "每天") {
			sday1 = "1号";
		}
		soffset1 = smonth1 + sday1;
	} else if (2 == index) {
		shour2 = fastDev.getInstance("cFirsttimeExec2").getValue();
		if (sday2 == "") {
			sday2 = "1号";
		} else if (sday2 == "每天") {
			sday2 = "1号";
		}
		soffset2 = smonth2 + sday2;
	}
	showRemark();
}

function oncOffsetMinChange() {
	var value = fastDev.getInstance("cOffsetWorkgroup").getValue();
	var minval = fastDev.getInstance("cOffsetMinute").getValue();
	var whour = minval / 60;
	var wmin = minval % 60;
	whour = Math.floor(whour);
	switch (value) {
		case "1":
			shour = " 开班前" + whour + "小时" + wmin + "分钟";
			break;
		case "2":
			shour = "开班后" + whour + "小时" + wmin + "分钟";
			break;
		case "3":
			shour = "结班后" + whour + "小时" + wmin + "分钟";
			break;
		default:
			shour = "";
			break;
	}
	var type = fastDev.getInstance("cTimeruleType").getValue();
	switch (type) {
		case "2":
			soffset = "每班";
			break;
		case "3":
			soffset = sday;
			break;
		case "4":
			soffset = sweek1 + sweek2;
			break;
		case "5":
			sday = "1号";
			soffset = smonth + sday;
		default:
			break;
	}
	showRemark();
}

function onOffsetHourChange() {
	var value = fastDev.getInstance("cOffsetHour").getValue();
	shour = " 每" + value + "小时 ";
	var type = fastDev.getInstance("cTimeruleType").getValue();
	switch (type) {
		case "2":
			soffset = "每班";
			break;
		case "3":
			soffset = sday;
			break;
		case "4":
			soffset = sweek1 + sweek2;
			break;
		case "5":
			soffset = smonth + sday;
		default:
			break;
	}
	showRemark();
}

function HourModelChange(e, item) {
	switch (item.value) {
		case "1":
			fastDev.getInstance("cFirsttimeExecHour").enable();
			fastDev.getInstance("cFirsttimeExecMinute").enable();
			fastDev.getInstance("cFirsttimeExecSecond").enable();
			fastDev.getInstance("cOffsetHour").disable();
			fastDev.getInstance("cOffsetHourUnit").disable();
			fastDev.getInstance("cFirsttimeExecHour").reset();
			fastDev.getInstance("cFirsttimeExecMinute").reset();
			fastDev.getInstance("cFirsttimeExecSecond").reset();
			fastDev.getInstance("cFirsttimeExec").reset();
			fastDev.getInstance("cOffsetHour").reset();
			fastDev.getInstance("cOffsetHour").initEmptyText();
			shour = "00:00:00";
			var type = fastDev.getInstance("cTimeruleType").getValue();
			showWorkgroup(type);
			break;
		case "2":
			fastDev.getInstance("cFirsttimeExecHour").disable();
			fastDev.getInstance("cFirsttimeExecMinute").disable();
			fastDev.getInstance("cFirsttimeExecSecond").disable();
			fastDev.getInstance("cOffsetHour").enable();
			fastDev.getInstance("cOffsetHourUnit").enable();
			fastDev.getInstance("cFirsttimeExecHour").reset();
			fastDev.getInstance("cFirsttimeExecMinute").reset();
			fastDev.getInstance("cFirsttimeExecSecond").reset();
			fastDev.getInstance("cFirsttimeExec").reset();
			fastDev.getInstance("cOffsetHour").reset();
			fastDev.getInstance("cOffsetHour").initEmptyText();
			shour = " 每1小时 ";
			showWorkgroup("1");
			break;
		default:
			break;
	}
	fastDev.getInstance("cFirsttimeExecHour").setValue("00");
	fastDev.getInstance("cFirsttimeExecMinute").setValue("00");
	fastDev.getInstance("cFirsttimeExecSecond").setValue("00");
	fastDev.getInstance("cFirsttimeExec").setValue("00:00:00");
	showRemark();
}

function onBeginChange() {
	var value = fastDev.getInstance("cTimeruleBegin").getValue();
	sbegin = value;
	var type = fastDev.getInstance("cTimeruleType").getValue();
	switch (type) {
		case "2":
			soffset = "每班";
			break;
		case "3":
			soffset = sday;
			break;
		case "4":
			soffset = sweek1 + sweek2;
			break;
		case "5":
			if (sday == "") {
				sday = "1号";
			} else if (sday == "每天") {
				sday = "1号";
			}
			soffset = smonth + sday;
		case "51":
			if (sday1 == "") {
				sday1 = "1号";
			} else if (sday1 == "每天") {
				sday1 = "1号";
			}
			soffset1 = smonth1 + sday1;
			if (sday2 == "") {
				sday2 = "1号";
			} else if (sday2 == "每天") {
				sday2 = "1号";
			}
			soffset2 = smonth2 + sday2;
		default:
			break;
	}
	showRemark();
}

function onEndChange() {
	var value = fastDev.getInstance("cTimeruleEnd").getValue();
	send = " 到 " + value;
	var type = fastDev.getInstance("cTimeruleType").getValue();
	switch (type) {
		case "2":
			soffset = "每班";
			break;
		case "3":
			soffset = sday;
			break;
		case "4":
			soffset = sweek1 + sweek2;
			break;
		case "5":
			if (sday == "") {
				sday = "1号";
			} else if (sday == "每天") {
				sday = "1号";
			}
			soffset = smonth + sday;
		case "51":
			if (sday1 == "") {
				sday1 = "1号";
			} else if (sday1 == "每天") {
				sday1 = "1号";
			}
			soffset1 = smonth1 + sday1;
			if (sday2 == "") {
				sday2 = "1号";
			} else if (sday2 == "每天") {
				sday2 = "1号";
			}
			soffset2 = smonth2 + sday2;
		default:
			break;
	}
	showRemark();
}

function onEndModelChange(e, item) {
	switch (item.value) {
		case "1":
			fastDev.getInstance("cTimeruleEnd").getOptions().rule = "Required";
			fastDev.getInstance("cTimeruleEnd").enable();
			break;
		case "2":
			fastDev.getInstance("cTimeruleEnd").getOptions().rule = "";
			fastDev.getInstance("cTimeruleEnd").disable();
			send = "";
			break;
		default:
			break;
	}
}

function allresSet() {
	fastDev.getInstance("execOnceTime").reset();
	fastDev.getInstance("cOffsetDay").reset();
	fastDev.getInstance("cOffsetDay1").reset();
	fastDev.getInstance("cFirsttimeExecHour1").reset();
	fastDev.getInstance("cFirsttimeExecMinute1").reset();
	fastDev.getInstance("cFirsttimeExecSecond1").reset();
	fastDev.getInstance("cFirsttimeExec1").reset();
	fastDev.getInstance("cOffsetDay2").reset();
	fastDev.getInstance("cFirsttimeExecHour2").reset();
	fastDev.getInstance("cFirsttimeExecMinute2").reset();
	fastDev.getInstance("cFirsttimeExecSecond2").reset();
	fastDev.getInstance("cFirsttimeExec2").reset();
	fastDev.getInstance("cOffsetW").reset();
	fastDev.getInstance("cOffsetWeek").reset();
	fastDev.getInstance("cOffsetMonth").reset();
	fastDev.getInstance("checkHourModel").reset();
	fastDev.getInstance("cFirsttimeExecHour").reset();
	fastDev.getInstance("cFirsttimeExecMinute").reset();
	fastDev.getInstance("cFirsttimeExecSecond").reset();
	fastDev.getInstance("cFirsttimeExec").reset();
	fastDev.getInstance("cOffsetWorkgroup").reset();
	fastDev.getInstance("cOffsetMinute").reset();
	fastDev.getInstance("cOffsetHourUnit").reset();
	fastDev.getInstance("cOffsetHour").reset();
	fastDev.getInstance("cTimeruleBegin").reset();
	fastDev.getInstance("cTimeruleEnd").reset();
	fastDev.getInstance("cRemark").reset();
}

function allstrclean() {
	s1 = "在";
	s2 = "执行计划";
	sonce = "";
	stype = "";// 每班，每天，每周，每月
	soffset = "";
	soffset1 = "";
	soffset2 = "";
	sday = "每天";
	sday1 = "每天";
	sday2 = "每天";
	sweek1 = "每周";
	sweek2 = "";
	smonth = "每月";
	smonth1 = "每月";
	smonth2 = "每月";
	sbegin = "";
	send = "";
	sremark = "";
	shour = "00:00:00";
	shour1 = "00:00:00";
	shour2 = "00:00:00";
	fastDev.getInstance("cFirsttimeExecHour").setValue("00");
	fastDev.getInstance("cFirsttimeExecMinute").setValue("00");
	fastDev.getInstance("cFirsttimeExecSecond").setValue("00");
	fastDev.getInstance("cFirsttimeExec").setValue("00:00:00");
	fastDev.getInstance("cFirsttimeExecHour1").setValue("00");
	fastDev.getInstance("cFirsttimeExecMinute1").setValue("00");
	fastDev.getInstance("cFirsttimeExecSecond1").setValue("00");
	fastDev.getInstance("cFirsttimeExec1").setValue("00:00:00");
	fastDev.getInstance("cFirsttimeExecHour2").setValue("00");
	fastDev.getInstance("cFirsttimeExecMinute2").setValue("00");
	fastDev.getInstance("cFirsttimeExecSecond2").setValue("00");
	fastDev.getInstance("cFirsttimeExec2").setValue("00:00:00");
}

function onTypeChange(value) {
	allresSet();
	allstrclean();
	switch (value) {
		case "1": // 执行一次
			fastDev.getInstance("checkHourModel").disable();
			fastDev.getInstance("cTimeruleBegin").disable();
			fastDev.getInstance("checkEndModel").disable();
			fastDev.getInstance("cTimeruleEnd").disable();
			break;
		case "2": // 每班
			$('#day').hide();
			$('#day1').hide();
			$('#week').hide();
			$('#month').hide();
			fastDev.getInstance("checkHourModel").enable();
			fastDev.getInstance("checkHourModel").setValue(1);
			fastDev.getInstance("cTimeruleBegin").enable();
			fastDev.getInstance("checkEndModel").enable();
			fastDev.getInstance("checkEndModel").setValue(1);
			fastDev.getInstance("cTimeruleEnd").enable();
			break;
		case "3": // 每天
			$('#day').hide();
			$('#day1').hide();
			$('#week').hide();
			$('#month').hide();
			fastDev.getInstance("checkHourModel").enable();
			fastDev.getInstance("checkHourModel").setValue(1);
			fastDev.getInstance("cTimeruleBegin").enable();
			fastDev.getInstance("checkEndModel").enable();
			fastDev.getInstance("checkEndModel").setValue(1);
			fastDev.getInstance("cTimeruleEnd").enable();
			break;
		case "4": // 每周
			$('#day').hide();
			$('#day1').hide();
			$('#week').show();
			$('#month').hide();
			fastDev.getInstance("checkHourModel").enable();
			fastDev.getInstance("checkHourModel").setValue(1);
			fastDev.getInstance("cTimeruleBegin").enable();
			fastDev.getInstance("checkEndModel").enable();
			fastDev.getInstance("checkEndModel").setValue(1);
			fastDev.getInstance("cTimeruleEnd").enable();
			break;
		case "5": // 每月一次
			$('#day').show();
			$('#day1').hide();
			$('#week').hide();
			$('#month').hide();
			fastDev.getInstance("checkHourModel").enable();
			fastDev.getInstance("checkHourModel").setValue(1);
			fastDev.getInstance("cTimeruleBegin").enable();
			fastDev.getInstance("checkEndModel").enable();
			fastDev.getInstance("checkEndModel").setValue(1);
			fastDev.getInstance("cTimeruleEnd").enable();
			break;
		case "51": // 每月多次
			$('#day').hide();
			$('#day1').show();
			$('#week').hide();
			$('#month').hide();
			fastDev.getInstance("checkHourModel").enable();
			fastDev.getInstance("checkHourModel").setValue(1);
			fastDev.getInstance("cTimeruleBegin").enable();
			fastDev.getInstance("checkEndModel").enable();
			fastDev.getInstance("checkEndModel").setValue(1);
			fastDev.getInstance("cTimeruleEnd").enable();
			break;
		default:
			value = 1;
			break;
	}
	showWorkgroup(value);
	showRemark();
}

function showWorkgroup(type) {
	if (type == "2") {
		fastDev.getInstance("cFirsttimeExecHour").disable();
		fastDev.getInstance("cFirsttimeExecMinute").disable();
		fastDev.getInstance("cFirsttimeExecSecond").disable();
		fastDev.getInstance("cOffsetWorkgroup").enable();
		fastDev.getInstance("cOffsetMinute").enable();
	} else if ((type == "1") || (type == "51")) {
		fastDev.getInstance("cFirsttimeExecHour").disable();
		fastDev.getInstance("cFirsttimeExecMinute").disable();
		fastDev.getInstance("cFirsttimeExecSecond").disable();
		fastDev.getInstance("cOffsetWorkgroup").disable();
		fastDev.getInstance("cOffsetMinute").disable();
	} else {
		fastDev.getInstance("cFirsttimeExecHour").enable();
		fastDev.getInstance("cFirsttimeExecMinute").enable();
		fastDev.getInstance("cFirsttimeExecSecond").enable();
		fastDev.getInstance("cOffsetWorkgroup").disable();
		fastDev.getInstance("cOffsetMinute").disable();
	}
	fastDev.getInstance("cOffsetMinute").reset();
}

function showRemark() {
	var type = fastDev.getInstance("cTimeruleType").getValue();
	if (type == "51") {
		sremark = s1 + "  " + soffset1 + "  的 " + shour1 + "  和  " + soffset2 + "  的 " + shour2 + "  执行， 将在 " + sbegin + " " + send + " 使用计划。";
		sname = s1 + "" + soffset1 + "的" + shour1 + "和" + soffset2 + "的" + shour2 + "执行";
	} else {
		sremark = s1 + "  " + soffset + "  的 " + shour + "  执行， 将在 " + sbegin + " " + send + " 使用计划。";
		sname = s1 + "" + soffset + "的" + shour + "执行";
	}
	fastDev.getInstance("cRemark").setValue(sremark);
	fastDev.getInstance("cTimeruleName").setValue(sname);
}

function onOnecChange(value) {
	var dateval = new Date(value);
	var datenow = new Date();
	if (dateval < datenow) {
		window.alert("执行一次的日期和时间必须晚于当前的日期与时间");
		return;
	}
	fastDev.getInstance("cTimeruleBegin").getOptions().rule = "";
	fastDev.getInstance("cTimeruleEnd").getOptions().rule = "";
	fastDev.getInstance("cRemark").setValue(s1 + "  " + value + "  " + s2);
}

function onSubmitSuccess(result) {
	fastDev.alert(result.msg, '信息提示', result.status, function() {
		if (result.status == 'ok') {
			fastDev.Ui.Window.parent.refreshDatagrid();
			fastDev.Ui.Window.parent.closeDialog();
		}
	});
}

function getQueryString(name) {
	// 获取浏览器URL中参数，此处用于判断是否需要验证码。
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}