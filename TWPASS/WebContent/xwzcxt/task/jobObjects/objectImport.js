var request=fastDev.Browser.getRequest();

fastDev(function(){
	var cTabletypeId= request['cTabletypeId'];
	var obj=fastDev.getInstance("cTabletypeId");
	obj.setValue(cTabletypeId);
});




function exportTemplet(){
	var formData = fastDev.getInstance("checkform").getItems();
	fastDev.create("Proxy", {
		action : "jobObjects_getImportTemplete.action"
	}).save(formData, function(data) {
		fastDev("#dc").prop("src", data.url);
	});	
}

var isSuccess=true;
function getIsSuccess(){
	return isSuccess;
}

function doSave(win,grid){
	isSuccess=false;
	var orgid=fastDev.getInstance('cOrgid').getValue();
	if(!orgid){
		isSuccess=true;
		fastDev.alert("请先选择部门!");
		return;
	}
	var filePath=fastDev.getInstance('filePath').getValue();
	if(!filePath){
		isSuccess=true;
		fastDev.alert("请先选择文件!");
		return;
	}
	var cTabletypeId=fastDev.getInstance('cTabletypeId').getValue();
	if(!cTabletypeId){
		isSuccess=true;
		fastDev.alert("请先选择作业对象类型!");
		return;
	}
	processBar();
	var data=fastDev.getInstance('checkform').getItems();
	fastDev.post("jobObjects_addObjectsByImport.action",{
		success:function(data){
			document.getElementById("text").innerHTML="<font color=red>"+data+"</font>";
			processbar.close();
			grid.refreshData(false);
			doReset();
			isSuccess=true;
		},
		data:data

	});
}


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
		container : "page",
		text : "数据导入中，请稍后..."
	});
}

//清空表单数据
function doReset() {
	fastDev.getInstance('upfileExcel').cleanTextInput(true);
	var  cTabletypeId=fastDev.getInstance("cTabletypeId").getValue();
	fastDev.getInstance('checkform').resetData();
	fastDev.getInstance("cTabletypeId").setValue(cTabletypeId);
}

//保存



function onSubmitSuccess(data){

}


