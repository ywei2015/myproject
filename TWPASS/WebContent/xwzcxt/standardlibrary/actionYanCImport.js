var request = fastDev.Browser.getRequest();
var cAreaidj=request['cAreaidj'];


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
	//alert(data.msg);
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
//控制倒入模板类型
var checkISNu=false;
function onUploadStart() {
	checkISNu=true;
	console.info(checkISNu);
}
function checkISNull(){
	if(!checkISNu){
		fastDev.tips("请选择导入文件");
		return false;
	}else return true;
}
function onUploadSuccess(file, response) {
	if (!!response.filePath) {
		fastDev.getInstance("filePath").setValue(response.filePath);
		fastDev.getInstance('cAreaId').setValue(cAreaidj);
		console.info(response.filePath);
	}
}

function onUploadFail(file, response) {
	fastDev.tips("文件 " + file.name + " 上传失败");
}

//function onUploadComplete(file, response) {
//	if (!response)
//		return false;
//}