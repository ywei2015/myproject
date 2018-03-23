var request = fastDev.Browser.getRequest();
var cModifyType = request['cModifyType'];
var cActnodetype = request['cActnodetype'];

var fileSum = 0;

var submitButton = null;
var loadingWindow = null;
var init = 0;

var image_audio = '../../images/audio.png';
var image_image = '../../images/image.png';
var image_video = '../../images/video.png';

function onAfterInitRender() {
	if (0 == init) {
		loadingWindow = fastDev.create("ProgressBar", {
			container : "checkForm",
			text : "加载中..."
		});
	}

	init++;

	if (2 == init) {
		if ("2" != cModifyType) {
			var data = fastDev.Ui.Window.getData("data");
			if (data.id) {
				fastDev.getInstance("id").setValue(data.id);
			} else {
				fastDev.getInstance("id").setValue(data.dg_seq);
			}
			fastDev.getInstance("cActitemMediaFileId").setValue(data.cActitemMediaFileId);
			fastDev.getInstance("cActitemMediaFileType").setValue(data.cActitemMediaFileType);
			fastDev.getInstance("cActitemMediaFilePath").setValue(data.cActitemMediaFilePath);
			fastDev.getInstance("cActitemName").setValue(data.cActitemName);
			fastDev.getInstance("cActitemCode").setValue(data.cActitemCode);
			fastDev.getInstance("cGetdataPretext").setValue(data.cGetdataPretext);
			fastDev.getInstance("cGetdatatype").setValue(data.cGetdatatype);
			fastDev.getInstance("cGetdataUnit").setValue(data.cGetdataUnit);
			fastDev.getInstance("cGetdataText").setValue(data.cGetdataText);
			fastDev.getInstance("cCheckdatatype").setValue(data.cCheckdatatype);
			fastDev.getInstance("cActitemStd").setValue(data.cActitemStd);
			fastDev.getInstance("cActitemStdCheck").setValue(data.cActitemStdCheck);
			fastDev.getInstance("cRemark").setValue(data.cRemark);

			if (fastDev("#cActitemMediaFileType").prop("value")) {
				var cActitemMediaFileType = fastDev("#cActitemMediaFileType").prop("value").split(",");
				var cActitemMediaFilePath = fastDev("#cActitemMediaFilePath").prop("value").split(",");
				for (var i = 0; i < cActitemMediaFileType.length; i++) {
					var e = getElementByPath(cActitemMediaFilePath[i], cActitemMediaFileType[i]);
					addMediaElement(e);
					fileSum++;
				}
			}
		}

		//编辑模式为查看时不可编辑数据
		if ("0" == cModifyType) {
			fastDev.getInstance("cActitemName").disable();
			fastDev.getInstance("cActitemCode").disable();
			fastDev.getInstance("cGetdataPretext").disable();
			fastDev.getInstance("cGetdatatype").disable();
			fastDev.getInstance("cGetdataUnit").disable();
			fastDev.getInstance("cGetdataText").disable();
			fastDev.getInstance("cCheckdatatype").disable();
			fastDev.getInstance("cActitemStd").disable();
			fastDev.getInstance("cActitemStdCheck").disable();
			fastDev.getInstance("cRemark").disable();
			fastDev.getInstance("upfileAccessory").disable();
		}

		if ("1" == cActnodetype) {
			document.getElementById("title1").innerHTML = "<font color='red' >*</font>做什么：";
			document.getElementById("title2-1").innerHTML = "数据采集名称：";
			document.getElementById("title2-2").innerHTML = "数据采集单位：";
			document.getElementById("title2-3").innerHTML = "<font color='red' >*</font>执行结果上传要求：";
			document.getElementById("title2-4").innerHTML = "执行结果后置文本：";
			document.getElementById("title3").innerHTML = "<font color='red' >*</font>验证结果上传要求：";
			document.getElementById("title4").innerHTML = "执行标准：";
			document.getElementById("title5").innerHTML = "验证标准：<br/>（如何核查）";
			fastDev.getInstance("cGetdatatype").removeItems("99");
			fastDev.getInstance("cCheckdatatype").removeItems("99");
		} else if ("2" == cActnodetype) {
			document.getElementById("title1").innerHTML = "<font color='red' >*</font>管什么：";
			document.getElementById("title2-1").innerHTML = "数据采集名称：";
			document.getElementById("title2-2").innerHTML = "数据采集单位：";
			document.getElementById("title2-3").innerHTML = "<font color='red' >*</font>完工上传要求：";
			document.getElementById("title2-4").innerHTML = "完工后置文本：";
			document.getElementById("title3").innerHTML = "<font color='red' >*</font>验证结果上传要求：";
			document.getElementById("title4").innerHTML = "管理要求：";
			document.getElementById("title5").innerHTML = "验证标准：<br/>（如何复核）";
			fastDev.getInstance("cGetdatatype").removeItems("98");
			fastDev.getInstance("cCheckdatatype").removeItems("98");
		}

		loadingWindow.close();
	}
}

function doReset() {
	fastDev.getInstance('checkForm').resetData();
}

function showWaitingDialog(button) {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "checkForm",
		text : "保存中..."
	});
	submitButton = button;
}

function chooseError(file, code, msg) {
	switch (code) {
		case 1:
			fastDev.tips("仅能导入bmp，jpg，jpeg，png，gif，mp3，mp4文件");
			break;
		default:
			fastDev.tips("文件上传因错误失败");
			break;
	}
}

function onUploadStart(file) {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "checkForm",
		text : "文件上传中..."
	});
	return true;
}

function onUploadSuccess(file, response) {
	if (!!response.C_FILE_PATH) {
		var e = getElementByPath(response.C_FILE_PATH, response.C_FILE_TYPE + '');
		addMediaElement(e);
		var cActitemMediaFileId = fastDev.getInstance('cActitemMediaFileId');
		if (fileSum == 0) {
			cActitemMediaFileId.setValue(response.C_FILE_ID);
		} else {
			cActitemMediaFileId.setValue(cActitemMediaFileId.getValue() + ',' + response.C_FILE_ID);
		}
		fileSum++;
	}
	if (loadingWindow) {
		loadingWindow.close();
	}
	fastDev.tips("文件 " + file.name + " 上传成功");
}

function onUploadFail(file, response) {
	if (loadingWindow) {
		loadingWindow.close();
	}
	fastDev.tips("文件 " + file.name + " 上传失败");
}

function onUploadComplete(file, response) {
	if (loadingWindow) {
		loadingWindow.close();
	}
	if (!response)
		return false;
}

function addMediaElement(e) {
	var column = Math.ceil(fileSum / 20);
	if ((fileSum % 20) == 0) {
		var tr = document.createElement('tr');
		tr.id = 'tr' + (column + 1);
		var td = document.createElement('td');
		td.appendChild(e);
		tr.appendChild(td);
		document.getElementById('fileTable').appendChild(tr);
	} else {
		var td = document.createElement('td');
		td.appendChild(e);
		document.getElementById('tr' + column).appendChild(td);
	}
}