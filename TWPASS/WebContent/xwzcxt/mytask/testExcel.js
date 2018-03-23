var request = fastDev.Browser.getRequest();

var operTpl = '&nbsp;<a name="del">删除</a>';
var fileuuid="";
var g_event=request['event'];//0-add或者1-edit
var g_type=3;//初始化为工厂文件

$(function(){
	init();
});

function init(){
	//fastDev.getInstance("savebasicbtn").enable();
	fastDev.getInstance("saveforwordbtn").disable();
}
//保存基础信息
function onBasicFormBeforeReady(){
	//g_event 
	var form = this;
	//var action = "upload_saveBasicInformationOfFile.action?uuid="+fileuuid;
	form.setOptions({
		"action":"upload_saveBasicInformationOfFile.action"
		//,"dataSource":dataSource
		});
}
function onBasicFormAfterInit(){
	$('#company').hide();
	$('#factory').show();
	$('#displayerrmsg').hide();
	
	$('#forworddiv').hide();
}
function onBasicSubmitSuccess(resp){
	//fileuuid
	//提交文件基础信息完成后返回文件的uuid
	//g_event
	//新增模式下，要
	fastDev.alert(resp.msg, "信息提示", resp.result,function(){
		if (resp.result >= 0){//成功
			//parent.appDialog.close();
			//parent.refreshDatagrid();
			fileuuid = resp.data;
			
			fastDev.getInstance("savebasicbtn").disable();
			fastDev.getInstance("saveforwordbtn").enable();
			fastDev.getInstance("uploadaccessorybtn").enable();
			
			$('#forworddiv').show();
			fastDev.getInstance("filesgrid").setWidth("600");
		}
	});
}

//保存前言
function onPrefaceFormBeforeReady(){
	//修改模式下需要设置获取数据action
	/*form.setOptions({
		"dataSource":""
		});*/
}
function onPrefaceSubmitSuccess(resp){
	fastDev.alert(resp.msg, "信息提示");
}

function onSelectChange(){
	var v = fastDev.getInstance("standardLibraryPojo.c_sort_id").getValue();
	if (v==null || v=="") return;
	
	g_type = v/1000;
	if (g_type<3){//公司文件
		$('#company').show();
		$('#factory').hide();
	}else if(g_type>=3){//工厂文件
		$('#company').hide();
		$('#factory').show();
	}
}

function onBeforeExcelFileUploadReady(){
	
}

function onAccessorySelectChange(){
	var v = this.getValue();
	if (v == 2)
		$('#accessoryselect').show();
	else if (v == 1)
		$('#accessoryselect').hide();
}

function onGridRowClick(event,rowindex,data){
	if (event.target.name == "del"){
		var proxy = fastDev.create('Proxy', {
			action : "upload_deleteAccessory.action"
		});
		proxy.save({filename:data.filenamegrid}, function(result){
			fastDev.getInstance("filesgrid").delRow(data.fileid);
		});
	}
}

function onFileChooseChange(file){
	var filetype = fastDev.getInstance("accessorytypeselect").getValue();
	var chapter = "";
	if (filetype == 2)
		chapter = fastDev.getInstance("accessorychapter").getText();
	var data = {
			"filetype" : filetype,
			"fileid" : file.id,//不是唯一的
			"filechapter" : chapter,
			"filenamegrid" : file.name
	};
	fastDev.getInstance("filesgrid").addRow(data);
}

function chooseExcelError(file, code, msg){
	if (code == 1) {
		fastDev.tips("仅能上传xls和xlsx类型文件");
	}
}

function onExcelFileChooseChange(){
	fastDev.getInstance("accessorychapter").clean(true);
}

function onUploadExcelSuccess(file, response){
	$('#displayerrmsg').hide();
	if (response.result == true){
		var msgs = [];
		msgs = response.msg.split(",");
		for (var i=0; i<msgs.length; i++){
			if (msgs[i] != "")
				fastDev.getInstance("accessorychapter").addItems({value:i,text:msgs[i]});
		}
		fastDev.tips("文件上传成功！");
	}else{
		//有alert出现则弹出alert
		if (response.alert != ""){
			fastDev.alert(response.alert);
		}else{//没有alert，则显示所有msg
			$('#displayerrmsg').show();
			fastDev.getInstance("errmsgarea").setValue(response.msg);
		}
	}
}
		
function chooseAccessorError(file, code, msg){
	if (code == 1) {
		fastDev.tips("仅能上传xls、xlsx、doc、docx、jpg、png或者bmp类型文件");
	}
}

function onUploadAccessorySuccess(file, response){
	
}

function chooseOriginalError(file, code, msg){
	if (code == 1) {
		fastDev.tips("仅能上传doc、docx、xls活xlsx类型文件");
	}
}

//保存基础信息
function saveBasicInfo(){
	var form=fastDev.getInstance("uploadbasicform");
	form.submit();
}

//保存前言
function savePreface(){
	if (fileuuid == null || fileuuid == ""){
		fastDev.alert("请先保存文件基本信息！");
		return;
	}
	
	var form = fastDev.getInstance("uploadprefaceform");
	form.setOptions({
		"action":"upload_savePrefaceOfFile.action?type="+g_type+"&uuid="+fileuuid
	});
	form.submit();
}

//上传附件
function saveAccessory(){
	var data = fastDev.getInstance("filesgrid").getAllValue();
	var proxy = fastDev.create('Proxy', {
		action : "upload_submitAccessoryInformation.action"
	});
	proxy.save({data:data}, function(resp){
		console.info(resp);
		fastDev.alert(resp.msg, "信息提示", resp.result,function(){
			if (resp.result == true){//成功
				fastDev.getInstance("filesgrid").clean();
			}
		});
	});
	
}

function refreshDatagrid(o) {
	o = fastDev.apply(o || {});//, currentCondition || {});
	fastDev.getInstance('filesgrid').refreshData(o);
}

/*function onBtnAfterDataRender(){
	init();
	//this.disable();
}*/

