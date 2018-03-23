var request = fastDev.Browser.getRequest();

var g_event = request['event'];
var g_contentid = request['contentid'];
var g_sfileid = request['sfileid'];
var form;

/*
 stdfile_getStdFileContentPageByParams.action  stdfile_getStdFileContentInfoByID.action
http://localhost:8080/TWPASS/stdfile_getStdFileContentInfoByID.action?c_contentid=7014fed0-a079-418b-9034-88eccb868fbf
http://localhost:8080/TWPASS/xwzcxt/standardlibrary/standardcontentadd.html?event=modify&contentid=7014fed0-a079-418b-9034-88eccb868fbf
stdfile_addstdfileConetentInfo.action   9e0f236b-865a-4c50-a9ef-41609be63d29
*/

//fastDev.getInstance("StdFileContentForm").dataRefresh();
//根据页面传入event事件初始绑定form对应的action
function ContentFormReady() {
	if (g_event == "modify") {
		this.setOptions({
			"action" : "stdfile_getStdFileContentInfoByID.action?c_contentid=" + g_contentid,
			"dataSource" : "stdfile_getStdFileContentInfoByID.action?c_contentid=" + g_contentid,
			"initSource" : "stdfile_getStdFileContentInfoByID.action?c_contentid=" + g_contentid
		});
	}
	/*else{
		if(g_sfileid!=""&&g_sfileid!=undefined){ fastDev.getInstance("stdfilecontent.c_sfile_id").setValue(g_sfileid);}
	}*/
}

//onAfterLoadInit   //  onAfterLoadData  
function InitSelect(){
	if(g_sfileid!=""&&g_sfileid!=undefined){
		fastDev.getInstance("stdfilecontent.c_sfile_id").setValue(g_sfileid);
	}		
	var data = fastDev.getInstance("StdFileContentForm").getItems();
	//console.info(data);
	fastDev.getInstance("stdfilecontent.c_includeattA").setValue(data["stdfilecontent.c_includeatt"]);
	if(data["stdfilecontent.c_includeatt"]=="false")
		$('#affixtable').hide();
	else
		$('#affixtable').show();
}
function onSelectChange(value){
	fastDev.getInstance("stdfilecontent.c_includeatt").setValue(value);
	if(value=="false")
		$('#affixtable').hide();
	else
		$('#affixtable').show();
	//fastDev.tips(value+"---"+fastDev.getInstance("stdfilecontent.c_includeatt").getValue());
}

//提交章节点内容的数据保存
function OnSaveContentInfo(){
	//fastDev.tips("在此处添加保存实现！");
	var data = fastDev.getInstance("StdFileContentForm").getItems();
	//data["stdfilecontent.c_contentid"] =  fastDev.getInstance("stdfilecontent.c_contentid");
	//console.info(data);
	if (g_event == "modify"){
		var proxy = fastDev.create('Proxy', {
			action : "stdfile_UpdateStdFileContentInfo.action"
		});
		proxy.save(data, function(resp){
			console.info("SaveContentInfo response:\n"+resp);
			fastDev.alert(resp.msg, "信息提示", resp.result, function(){
				if (resp.result == true){//成功
					fastDev.tips("所修改数据保存成功！");
					//fastDev.getInstance('cur_control').refreshData(); 
				}
			});
		});
	}else{ //Add
		var proxy = fastDev.create('Proxy', {
			action : "stdfile_AddStdFileContentInfo.action"
		});
		proxy.save(data, function(resp){
			g_contentid = resp.newid;
			fastDev.getInstance("stdfilecontent.c_contentid").setValue(g_contentid);
			fastDev.alert(resp.msg, "信息提示", resp.result,function(){
				if (resp.result == true){//成功
					fastDev.tips("数据添加成功！contentid="+g_contentid);
					//fastDev.getInstance('cur_control').refreshData(); 
				}
			});
			g_event = "modify"
			console.info("-------------\n"+g_contentid);
			//var tmpdata = fastDev.getInstance("StdFileContentForm").getItems();
			//console.info(tmpdata);
			//fastDev.getInstance('affixgrid').onBeforeReady();
			fastDev.getInstance('affixgrid').setOptions({"initSource" :"stdfile_getStdFileAffixByParams.action?atttype=2&contentid=" + g_contentid});
			fastDev.getInstance('affixgrid').refreshData(); 
			console.info("-------------\n");
		});
	}
}

function onAffixBeforeReady(){
	initSource = "stdfile_getStdFileAffixByParams.action?atttype=2&contentid=" + g_contentid;
	this.setOptions({
		//"action" : initSource,
		"initSource" : initSource,
		"dataSource" : initSource
	});
}
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
		console.info("deleteAffixById:"+resp);
		fastDev.alert(resp.msg, "信息提示", resp.result,function(){
			if (resp.result == true){//成功
				fastDev.getInstance('affixgrid').refreshData(); 
			}
		});
	});
}

function onAffixFileChoose(file) {
	if(g_contentid==""||g_contentid==undefined){
		this.cleanFileQueue();
		//this.stopUpload();
	}
	//fastDev.tips("文件信息：ID:"+file.id + " \n Name:" + file.name + " \n Extension:" + file.type + " \n Size:" + file.size);
	console.info(file);
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

function onAutoUploadStartNew(file) {	
	var uploader = fastDev.getInstance("upfileAccessory");
	var data = fastDev.getInstance("StdFileContentForm").getItems();
	//console.info(data);
	uploader.addParams({
		c_contentid : g_contentid,
		c_sfile_id : data["stdfilecontent.c_sfile_id"],
		c_Affix_type : 2,
		c_filetypes : "4",
		c_filename : encodeURI(file.name),
		c_extens : file.type,
		c_filesize : file.size,
		c_filekind : "3"
	});
}

function onAutoUploadStart(file) {
	var uploader = fastDev.getInstance("upfileAccessory");
	var data = fastDev.getInstance("StdFileContentForm").getItems();
	//console.info(data);
	uploader.addParams({
		c_sfile_id : data["stdfilecontent.c_sfile_id"],
		c_Affix_type : 2,
		c_chaptercode : data["stdfilecontent.c_sectionid"],
		c_filetypes : "4",
		c_filename : encodeURI(file.name),
		c_extens : file.type,
		c_filesize : file.size,
		c_filekind : "3"
	});
	//console.info("-------------onAutoUploadStart Done!------------------");
}

//附件块 GuveXie
function flfilerender(value) {
	var width = fastDev(this).width();
		return [ '<div style="width:' + width
				+ 'px;"><a href="javascript:(0);" class="btn" id="showfile" style="margin-left:5px;">查看附录</a>' ]
				.join('');
}
//附件块 GuveXie
function affixoperRenderer() {
	var width = fastDev(this).width();
	return [
			'<div style="width:' + width + 'px;">',
			'<a href="javascript:void(0);" class="btn" id="delete" style="margin-left:5px;">删除</a>' ]
			.join('');
}

//附件块 GuveXie
function onAffixDatagridRowClick(event, rowindex, data) {
	//fastDev.tips("URL:"+data.c_file_url);
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

function createWindow(o) {
	var config = fastDev.apply({
		width : "640",
		height : "200",
		allowResize : false,
		showMaxBtn : false
	}, o || {});
	(config.buttons = config.buttons || []).push({
		text : "关闭",
		iconCls : "icon-close",
		onclick : function(e, win) {
			win.close();
		}
	});
	appDialog = fastDev.create('Window', config);
}
