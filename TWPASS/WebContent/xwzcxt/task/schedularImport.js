var processbar = null;

function exportAction() {
	var url=window.location.href;
	var index=-1;
	for(var i=0;i<4;i++){
		index=url.indexOf("/", index+1);
	}
	url=url.substring(0, index)+"/xwzcxt/task/pbmb.xls";
	//alert(url);
	fastDev("#test").prop("src",url);
}

function chooseError(file, code, msg) {
	switch (code) {
	case 1:
		fastDev.tips("仅能导入xls,xlsx文件");
		break;
	case 2:
		fastDev.tips("文件过大，请分成较小文件再进行导入！");
		break;
	}
}


function onUploadStart() {

}

function onUploadComplete(file, response) {
	if(!response)
		return false;		
}

function onUploadSuccess(file, response) {
	if(!!response.filePath){
		fastDev.getInstance("filePath").setValue(response.filePath);
	}
}

function onUploadFail(file, response) {
	fastDev.tips("文件 " + file.name + " 上传失败");
}

function processBarClose() {
	processbar.close();
}

function processBar(){
	processbar = fastDev.create("ProgressBar", {
		container : "checkForm",
		text : "数据导入中，请稍后..."
	});
}

//清空表单数据
function doReset() {
	fastDev.getInstance('upfileExcel').cleanTextInput(true);
	fastDev.getInstance('checkForm').resetData();
}

//保存

var thiswin;
function doSave(win) {
	fastDev.getInstance('checkForm').submit();
	thiswin=win;
	processBar();
}

function onBeforeReady() {
	
	
};

function onSubmitSuccess(data){
/*	fastDev.create("Window", {
		
		title : "导入结果",
		width:"400px",
		height:"600px",
		// 字符串中可以使用HTML标签
		// 无需显式为文本内容指定窗体的宽高值，弹窗默认将根据当前内容以及可视窗口区域的大小自适应弹窗的尺寸
		content :"<font color=red>"+( data["msg"]||"")+"</font>",
		buttons:[{
				text:"关闭",
				width:"100px",
				align:"center",
				iconCls : 'icon-close', 
				onclick : function(e,win,cwin, fd){
					 win.close();
				}		 
		}]
	});*/
	document.getElementById("text").innerHTML="<font color=red>"+data.msg+"</font>";
	processbar.close();
}



