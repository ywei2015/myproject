var request = fastDev.Browser.getRequest();
var cPositionId = request['cPositionId'];
var cPositionName = request['cPositionName'];
var cDepartment = request['cDepartment'];
var cDepartmentName = request['cDepartmentName'];
var cPublicId = request['cPublicId'];
var cPublicName = request['cPublicName'];
var cIspublic = request['cIspublic'];
var submitButton = null;
var processbar = null;

function exportZuoyeTemplet() {
	if (0 == cIspublic) {
		fastDev("#test").prop("src", "zymb.xls");
	} else if (1 == cIspublic) {
		fastDev("#test").prop("src", "tyzymb.xls");
	}
}

function exportGuanliTemplet() {
	if (0 == cIspublic) {
		fastDev("#test").prop("src", "glmb.xls");
	} else if (1 == cIspublic) {
		fastDev("#test").prop("src", "tyglmb.xls");
	}
}

function cActnodetypeChange(e, item) {
	switch (item.value) {
		case "1":
			$('#zuoye').show();
			$('#guanli').hide();
			fastDev.getInstance('actnode.c_actnodetype').setValue(1);
			break;
		case "2":
			$('#zuoye').hide();
			$('#guanli').show();
			fastDev.getInstance('actnode.c_actnodetype').setValue(2);
			break;
		default:
			break;
	}
}

function getPosition() {
	var stree = fastDev.getInstance("actnode.c_position_id");
	var value = stree.getValue();
	if (value != "") {
		var tree = stree.getGlobal().tree;
		var pid = tree.getParentid(value);
		fastDev.getInstance('actnode.c_department').setValue(pid);
	}
}

function FormReady() {
	$('#zuoye').show();
	$('#guanli').hide();
}

function BeforeInit() {
	fastDev.getInstance('actnode.c_department').setValue(cDepartment);
	fastDev.getInstance('actnode.c_position_id').setValue(cPositionId);
	fastDev.getInstance('actnode.c_public_id').setValue(cPublicId);
	fastDev.getInstance('actnode.c_ispublic').setValue(cIspublic);
	if (0 == cIspublic) {
		document.getElementById("department_title").style.display = "table-row";
		document.getElementById("position_title").style.display = "table-row";
		document.getElementById("public_title").style.display = "none";
		fastDev.getInstance('department').setValue(cDepartmentName);
		fastDev.getInstance('position').setValue(cPositionName);
		document.getElementById("zuoyedownload0").style.display = "table-row";
		document.getElementById("zuoyedownload1").style.display = "none";
		document.getElementById("guanlidownload0").style.display = "table-row";
		document.getElementById("guanlidownload1").style.display = "none";
	} else if (1 == cIspublic) {
		document.getElementById("department_title").style.display = "none";
		document.getElementById("position_title").style.display = "none";
		document.getElementById("public_title").style.display = "table-row";
		fastDev.getInstance('public').setValue(cPublicName);
		document.getElementById("zuoyedownload0").style.display = "none";
		document.getElementById("zuoyedownload1").style.display = "table-row";
		document.getElementById("guanlidownload0").style.display = "none";
		document.getElementById("guanlidownload1").style.display = "table-row";
	}
}

function onBeforeReady() {
	this.setOptions({
		initSource : "tsdactnode/getAllActNodesByConditionsAction.action"
	});
}

function processBar(button) {
	processbar = fastDev.create("ProgressBar", {
		container : "checkForm",
		text : "数据导入中，请稍后..."
	});
	submitButton = button;
}

function processBarClose() {
	processbar.close();
}

function doReset() {
	fastDev.getInstance('checkForm').resetData();
}

//保存
function doSave(fn) {
	fastDev.getInstance('checkForm').submit();
}

//表单提交后回调
function onSubmitSuccess(data) {
	processBarClose();
	if (data.msg.indexOf("导入完成") != -1) {
		fastDev.alert(data.msg, '信息提示', data.status, function() {
			if (data.status == 'ok') {
				fastDev.Ui.Window.parent.refreshDatagrid();
				fastDev.Ui.Window.parent.closeDialog();
			}
		});
	} else {
		submitButton.enable();
		document.getElementById("text").innerHTML = "<font color=red>" + data.msg + "</font>";
	}
}

function chooseError(file, code, msg) {
	switch (code) {
		case 1:
			fastDev.tips("仅能导入xls,xlsx文件");
			break;
	}
}

function onUploadStart() {
	if (!fastDev.getInstance("actnode.c_actnodetype").getValue()) {
		fastDev.alert("请选择导入的模板类型！", "信息提示", "warning");
		this.cleanTextInput();
		return false;
	}
}

function onUploadSuccess(file, response) {
	if (!!response.filePath) {
		fastDev.getInstance("actnode.filePath").setValue(response.filePath);
	}
}

function onUploadFail(file, response) {
	fastDev.tips("文件 " + file.name + " 上传失败");
}

function onUploadComplete(file, response) {
	if (!response)
		return false;
}