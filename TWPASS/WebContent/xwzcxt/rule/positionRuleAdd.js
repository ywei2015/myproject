var cPrid = fastDev.Browser.getRequest()['cPrid'];

function FormReady() {
	// initControls();
	form = this;
	var action = "";
	if (cPrid != null && cPrid != "") {
		action = "ruleposition/updateTimeRuleAction.action?cTimeruleId="
				+ cTimeruleId;
		//window.alert(action);
	} else {
		action = "ruleposition/addRulePositionAction.action";
		//window.alert(action);
	}

	form.setOptions({
		action : action
	});
}


function submitSuccess(result) {
	fastDev.alert(result.msg, '信息提示', result.status, function() {
		if (result.status == 'ok') {
			fastDev.Ui.Window.parent.refreshDatagrid();
			fastDev.Ui.Window.parent.closeDialog();
		}
	});
};