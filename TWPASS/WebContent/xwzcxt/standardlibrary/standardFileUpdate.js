var request = fastDev.Browser.getRequest();

var operTpl = '&nbsp;<a name="del">删除</a>';
var fileuuid=request['sfileid'];  //"";
var condition;

var g_event=request['event'];//0-add或者1-edit
var g_type=3;//初始化为工厂文件

function fromReady() {
	var action = "", dataSource;
	dataSource = "stdfile_getStdFileInfoByID.action?sfileid=" + fileuuid;
	action = "stdfile_modifyStdFileInfo.action";
	this.setOptions({
		"action" : action,
		"dataSource" : dataSource
	});
	form = this;
}

function onContentBeforeReady(){
	var action = "", dataSource;
	initSource = "stdfile_getStdFileContentPageByFileId.action?sfileid=" + fileuuid;
	this.setOptions({
		"initSource" : initSource
	});
	datagrid = this;	
}

function onAffixBeforeReady(){
	var action = "", dataSource;
	initSource = "stdfile_getStdFileAffixByParams.action?atttype=1&sfileid=" + fileuuid;
	this.setOptions({
		"initSource" : initSource
	});
	datagrid = this;	
}


function onAfterReady(){
	/*fastDev.getInstance('stdfilecontentgrid').refreshData(condition);
	console.info(condition); */
}

function refreshData() {
	fastDev.getInstance('stdfilecontentgrid').refreshData(condition);
}

function onAfterInitRender(){}

$(function(){
	init();
});

function init(){
	//fastDev.getInstance("savebasicbtn").enable();
	//fastDev.getInstance("saveforwordbtn").disable();
	var o = condition || {};
	o["sfileid"] = fileuuid;
	condition = o;
	//fastDev.getInstance('stdfilecontentgrid').attr("initSource","stdfile_getStdFileContentPageByFileId.action?sfileid="+fileuuid);
}


function OnUpdateBasicInfo(){
	OnUpdateInfo("basic");
}

function OnUpdateForewordInfo(){
	OnUpdateInfo("foreword");
}

//GuveXie
function OnUpdateInfo(part){
	var stdfileinfo = {};

	stdfileinfo["stdfile.c_sfile_id"] = fileuuid;
	if(part=="basic"){
		var data = fastDev.getInstance("uploadbasicform").getItems();
		console.info(data);
		//基础信息
		stdfileinfo["stdfile.c_sfile_name"] = data["stdfile.c_sfile_name"];
		stdfileinfo["stdfile.c_sfile_version"] = data["stdfile.c_sfile_version"];
		stdfileinfo["stdfile.c_sort_id"] = data["stdfile.c_sort_id"];
		stdfileinfo["stdfile.c_releaseunit"] = data["stdfile.c_releaseunit"];
		stdfileinfo["stdfile.c_releasetime"] = data["stdfile.c_releasetime"];
		stdfileinfo["stdfile.c_impletime"] = data["stdfile.c_impletime"];
	}else{
	    //var fwtype = ''; //console.info('Here A');
		var data = fastDev.getInstance("uploadprefaceform").getItems();
		console.info(data);
	    if($("#company").is(":hidden")==true){
			//"factory"
				//factory-前言信息1
				stdfileinfo["stdfile.c_fw_zdyj"] = data["stdfile.c_fw_zdyj"];
				stdfileinfo["stdfile.c_fw_xdnr1"] = data["stdfile.c_fw_xdnr1"];
				stdfileinfo["stdfile.c_fw_tcdw"] = data["stdfile.c_fw_tcdw"];
				stdfileinfo["stdfile.c_fw_qcbm"] = data["stdfile.c_fw_qcbm"];
				stdfileinfo["stdfile.c_fw_gkbm"] = data["stdfile.c_fw_gkbm"];
				stdfileinfo["stdfile.c_fw_pzr"] = data["stdfile.c_fw_pzr"];
				stdfileinfo["stdfile.c_fw_qcr"] = data["stdfile.c_fw_qcr"];
				stdfileinfo["stdfile.c_fw_fbsj"] = data["stdfile.c_fw_fbsj"];
				stdfileinfo["stdfile.c_fw_xdcs"] = data["stdfile.c_fw_xdcs"];
			
	    }else{			
			//"company"
			//company-前言信息2
			stdfileinfo["stdfile.c_foreword1"] = data["stdfile.c_foreword1"];
	    }
	}

	console.info("---------------------------------------------------------");
	console.info(stdfileinfo);
	var proxy = fastDev.create('Proxy', {
		action : 'stdfile_UpdateStdFileInfo.action'
	});
	proxy.save(
					stdfileinfo,
					function(result) {
						fastDev
								.alert(
										result.msg,
										'信息提示',
										result.status,
										function() {
											if (result.status == 'ok') {
												// fastDev.getInstance('positionForm').cleanData();
												//currentActionId = null;
												//refreshDataGrid();
											}
										});
					});
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

function onFWFormAfterDataRender(){
	var formdata = this.getItems(); //fastDev.getInstance("stdfile.c_foreword1").value;
	var fw = formdata["stdfile.c_foreword1"];
	//console.info(fw);  //g_type
	if(fw==null||fw==''){
		$('#company').hide();
		$('#factory').show();		
	}else{
		$('#company').show();
		$('#factory').hide();	
	}
	/*var v =  fastDev.getInstance("stdfile.c_sort_id").getValue(); //formdata["stdfile.c_sort_id"]; //uploadbasicform
	if (v==null || v=="") return;
	g_type = v/1000;
	if (g_type<3){//公司文件
		$('#company').show();
		$('#factory').hide();
	}else if(g_type>=3){//工厂文件
		$('#company').hide();
		$('#factory').show();
	}*/
}

//基础信息Form初始化完后回调函数
function onBasicFormAfterInit(){
//	$('#company').hide();
//	$('#factory').show();
//	$('#displayerrmsg').hide();
//	$('#forworddiv').hide();
}

function onBasicSubmitSuccess(resp){
	fastDev.alert(resp.msg, "信息提示", resp.result,function(){
		/*if (resp.result >= 0){//成功;}*/
	});
}

function onPrefaceSubmitSuccess(resp){
	fastDev.alert(resp.msg, "信息提示");
}

//附件选择异常时回调
function chooseAccessorError(file, code, msg){
	if (code == 1) {
		fastDev.tips("仅能上传xls、xlsx、doc、docx、jpg、png或者bmp类型文件");
	}
}

//GuveXie
function queryaffixrender(value) {
	//return [value].join(''); false
	var width = fastDev(this).width();
	if(value=="true"){ //||value==1||value=='1'
		return [ '<div style="width:' + width
				+ 'px;"><a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="showfile">查看附件</a>' ]
				.join('');
	}else{
		return  [ '<div style="width:' + width
					+ 'px;">无附件' ]
					.join('');
	}
}

//附件块 GuveXie
/*function showfile(fileurl){
	createWindow({
		src : fileurl,
		title : "查看文件",
		width : "500",
		height : "300"
	});
	fastDev.create("Dialog", {
		height:400,
		width:500,
		showMaxBtn:false,
	    title:"查看文件",
	    src : fileurl
	});	
}*/

//附件块 GuveXie
function flfilerender(value) {
	var width = fastDev(this).width();
	//+ 'px;"><a href="javascript:showfile(\''+value+'\');" class="btn" id="showfile" style="margin-left:5px;">查看附录</a>' ]
		return [ '<div style="width:' + width
				+ 'px;"><a href="javascript:(0);" class="btn" id="showfile" style="margin-left:5px;">查看附录</a>' ]
				.join('');
}
//附件块 GuveXie
function affixoperRenderer() {
	var width = fastDev(this).width();
	return [
			'<div style="width:' + width + 'px;">',
			'<a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="delete">删除</a>' ]
			.join('');
}

//附件块 GuveXie
function onAffixDatagridRowClick(event, rowindex, data) {
	var target = event.target.id;
	if (target) {
		switch (target) {
		case 'delete':
			fastDev.confirm(('是否删除“' + data.c_file_name + '”？'), "信息提示",
					function(result) {
						if (result) {
							deleteAffixById(data.c_att_id);
						}
					});
			break;
		case 'showfile':
			createWindow({
				src : data.c_file_url,
				title : "附属文件",
				width : "500",
				height : "250"
			});
			break;
		}
	}
}

//根据附件ID删除附件 --stdfile_DeleteStdFileAffixById.action
function deleteAffixById(attid){
	//fastDev.tips("在此处添加附件ID为"+attid+"的删除实现！");
	var ditems = {};
	ditems["c_att_id"] = attid;
	var proxy = fastDev.create('Proxy', {
		action : "stdfile_deleteStdFileAffixById.action"
	});
	proxy.save(ditems, function(resp){
		console.info(resp);
		fastDev.alert(resp.msg, "信息提示", resp.result,function(){
			if (resp.result == true){//成功
				fastDev.getInstance('affixgrid').refreshData(); 
			}
		});
	});
}

//删除标准文件内容 依据内容id号
function  deleteStdFileContentById(contentid){
	//fastDev.tips("在此处添加附件ID为"+attid+"的删除实现！");
	var ditems = {};
	ditems["c_contentid"] = contentid;
	var proxy = fastDev.create('Proxy', {
		action : "stdfile_deleteStdFileContentById.action"
	});
	proxy.save(ditems, function(resp){
		console.info(resp);
		fastDev.alert(resp.msg, "信息提示", resp.result,function(){
			if (resp.result == true){//成功
				fastDev.getInstance('stdfilecontentgrid').refreshData(); 
			}
		});
	});
}

//附件块 GuveXie
function OnAddAffixFile(){
	fastDev.tips("实现附件上传功能！");
}

function operRenderer() {
	var width = fastDev(this).width();
	return [
			'<div style="width:'
					+ width
					+ 'px;"><a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="modify">修改</a>',
			'<a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="delete">删除</a>' ]
			.join('');
}


function onRowClick(event, rowindex, data) {
	console.info(data);
	var target = event.target.id;
	if (target) {
		switch (target) {
		case 'modify':
			createWindow({
				src : "standardcontentadd.html?event=modify&contentid="+data.c_contentid,
				title : "修改章节内容",
				width : "600",
				height : "500",
				buttons : [{
					text : '保存',
					iconCls : 'icon-save',
					onclick : function(e,win,cwin, fd) {
						var form=fd.getInstance("pointAddForm");
						form.submit();		
					}
				},{
					text : '重置',
					iconCls : 'icon-reset',
					onclick : function(e,win,cwin, fd) {
						var form=fd.getInstance("pointAddForm");
						form.cleanData();
					}
				}]		
			});
			break;
		case 'cknr':
			createWindow({
				src : "getStandardcontent.html?c_sfile_id=" + data.c_sfile_id,
				title : "文件内容",
				width : "800",
				height : "400"
			});
			break;
		case 'delete':
			fastDev.confirm(('是否删除“' + data.c_sectionid + '”？'), "信息提示",
					function(result) {
						if (result) {
							deleteStdFileContentById(data.c_contentid);
						}
					});
			break;
		case 'showfile':
			createWindow({
				src : data.c_file_url,
				title : "附属文件",
				width : "500",
				height : "250"
			});
			break;
		case 'viewInfo':
			doShow(data);
		}
	}
}

function onActionTreeNodeClick(event, id, value) {
	currentActionId = id;
	getCurrentActionId();
	parentId = this.getParentid(id);
	parentText = this.getValByid(parentId);
	currentName = this.getValByid(id);
	// refreshUppositiontree();
	// fastDev.getInstance("position.posiName").getGlobal().box.prop("value",
	// parentText);
	op = 'view';
	refreshDataGrid();
}

/*function refreshDataGrid() {
	var o = condition || {};
	o['standardfile.c_action_id'] = currentActionId;
	fastDev.getInstance('actiongrid').refreshData(o);
	console.info(o);
}*/

function createWindow(o) {
	var config = fastDev.apply({
		width : "640",
		height : "300",
		allowResize : false,
		showMaxBtn : false,
		onAfterClose : function() {
			//fastDev.tips("-----------onAfterClose");
			fastDev.getInstance('stdfilecontentgrid').refreshData();
			win = null;
		}
	}, o || {});
	(config.buttons = config.buttons || []).push({
		text : "关闭",
		iconCls : "icon-close",
		onclick : function(e, win) {
			//fastDev.getInstance('stdfilecontentgrid').refreshData();
			win.close();
		}
	});
	appDialog = fastDev.create('Window', config);
}

function addPoint() {
	createWindow({
		src : "standardcontentadd.html?sfileid="+fileuuid,
		title : "新增章节内容",
		width : "600",
		height : "500" //,
		/*buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(e,win,cwin, fd) {
				var form=fd.getInstance("pointAddForm");
				form.submit();		
			}
		},{
			text : '重置',
			iconCls : 'icon-reset',
			onclick : function(e,win,cwin, fd) {
				var form=fd.getInstance("pointAddForm");
				form.cleanData();
			}
		}]		*/
	});
}

function onAffixFileChoose(file) {
	//fastDev.tips("文件信息：ID:"+file.id + " \n Name:" + file.name + " \n Extension:" + file.type + " \n Size:" + file.size);
	console.info(file);
}

function onAutoUploadStart(file) {	
	var uploader = fastDev.getInstance("upfileAccessory");
	uploader.addParams({
		c_sfile_id : fileuuid,
		c_Affix_type : 1,
		c_chaptercode : "",
		c_filetypes : "4",
		c_filename : encodeURI(file.name),
		c_extens : file.type,
		c_filesize : file.size,
		c_filekind : "3"
	});
}

function onUploadAffixFileSuccess(file, response){
	if (response.result == true){ 
		fastDev.tips("文件上传成功！"+response.msg); 
		fastDev.getInstance('affixgrid').refreshData(); 
	}
}

function onUploadAccessoryFail(file, response){
	fastDev.tips("文件上传失败！");
}

