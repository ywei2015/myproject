var editor=null;
var newsId=null;
var type=null;
var fileSum = 0;
var loadingWindow = null;
var fm=fastDev.getInstance('addNewsForm');
var request=fastDev.Browser.getRequest();

var image_audio = '../../images/audio.png';
var image_image = '../../images/image.png';
var image_video = '../../images/video.png';

 
$(document).ready(function() {
	//初始化xhEditor编辑器插件
	editor=$('#body').xheditor({
		tools:'Cut,Copy,Paste,Pastetext,|,Blocktag,Fontface,FontSize,Bold,Italic,Underline,Strikethrough,FontColor,BackColor,SelectAll,Removeformat,Align,List,Outdent,Indent,Img,Media,Hr,Table,Source,Preview,Fullscreen,About',
		skin:'default',
		upMultiple:true,
		upLinkUrl:"uploadaction_uploadEditorFileToFileServer.action",
		upLinkExt:"zip,rar,txt",
		upImgUrl: "uploadaction_uploadEditorFileToFileServer.action",
		upImgExt: "jpg,jpeg,gif,bmp,png",
		onUpload:insertUpload,
		upFlashUrl:"uploadaction_uploadEditorFileToFileServer.action",
		upFlashExt:"swf",
		upMediaUrl:"uploadaction_uploadEditorFileToFileServer.action",
		upMediaExt:"mp4",
		html5Upload:false
	}); 
 
	initForm();

 });
 

/*function beforeReady() {
	newsId=request['newsId'];
	type=request['type'];
	
	loadingWindow = fastDev.create("ProgressBar", {
		container : "addNewsForm",
		text : "加载中..."
	});

	var action = "";
	var dataSource = "";
	alert(newsId);
	if ("null" != newsId) {
		action = "struts-news/news_newsAdd.action";
		dataSource = "struts-news/news_getNewsInfo.action?newsId=" + newsId;
	} else {
		action = "struts-news/news_newsAdd.action";
	}
	this.setOptions({
		action : action,
		dataSource: dataSource
	});
}*/


function initForm(){
	newsId=request['newsId'];
	type=request['type'];
	var url="news_getNewsInfo.action";
	
	if(type!="add"){
		fastDev.post(url,{
			success:function(data){
					if(data==null ||data==undefined ||data==''){
						return;
					}
					var dat=data.data[0];
					for(var key in dat){
						if(key=="manageSection"){
							var sectionName=switchSection(dat[key]);
							document.getElementById(key).value=sectionName;	
						}
						if(key!="cover"&&key!="manageSection"){
							var el=document.getElementById(key);
							if(el!=null){
								el.value=dat[key];
							}	
						}
					}
					
					editor.setSource(dat['body']);
 
					afterDataRender();
				},
			data:{"newsId":newsId}	
		});		
	}	
}


	//选择所属板块
	function switchSection(sectionNum){
		var sectionName="";
		switch(sectionNum){
			case 0:
				sectionName="N/A";
	    	break;
		    case 1:
		    	sectionName="安全";
		    	break;
		    case 2:
		    	sectionName="质量";
		    	break;
		    case 3:
		    	sectionName="成本";
		    	break;
		    case 4:
		    	sectionName="效率";
		    	break;
		    case 5:
		    	sectionName="环境";
		    	break;
		    case 6:
		    	sectionName="团队";
		    	break;
		}
		return sectionName;
	}
 
function afterDataRender() {
	if ("null" != newsId) {
		if (fastDev("#cNewsFileType").prop("value")) {
			var cNewsFileType = fastDev("#cNewsFileType").prop("value").split(",");
			var cNewsFilePath = fastDev("#cNewsFilePath").prop("value").split(",");
			for (var i = 0; i < cNewsFileType.length; i++) {
				var e = getElementByPath(cNewsFilePath[i], cNewsFileType[i]);
				addMediaElement(e);
				//fileSum++;
			}
		}
		 
	}  
}
 
//xbhEditor编辑器图片上传回调函数
function insertUpload(msg) {  
	var _msg = msg.toString();
	var _picture_name = _msg.substring(_msg.lastIndexOf("/")+1);
	var _picture_path = Substring(_msg);
	var _str = "<input type='checkbox' name='_pictures' value='"+_picture_path+"' checked='checked' onclick='return false'/><label>"+_picture_name+"</label><br/>";
	//fastDev.alert("body==before="+$("#body").val()); 
	
	$("#body").append(_msg); 
	$(""+_msg).appendTo($("#clubDesc"));
	
	/*var _picture_name = _msg.substring(_msg.lastIndexOf("/")+1);
	var _picture_path = Substring(_msg);
	var _str = "<input type='checkbox' name='_pictures' value='"+_picture_path+"' checked='checked' onclick='return false'/><label>"+_picture_name+"</label><br/>";
	fastDev.alert("body==before="+$("#body").val()); 
	
	$("#body").append(_msg); 
	$(""+_msg).appendTo($("#clubDesc"));      */
	//fastDev.alert("body==end="+$("#body").val());   
}

//处理服务器返回到回调函数的字符串内容,格式是JSON的数据格式.
function Substring(s){
	return s.substring(s.substring(0,s.lastIndexOf("/")).lastIndexOf("/"),s.length);
}
 
 
function doReset() {
	fastDev.getInstance('addNewsForm').resetData();
	editor.setSource("");
	fastDev.getInstance("cover").setValue('');
	var ttrr=document.getElementById('tr0');
	if(ttrr!=undefined&&ttrr!=null){
		document.getElementById('fileTable').removeChild(ttrr);
	}
}

//保存新闻信息
function doSave(grid,win) {
	//var sss = document.getElementById("body").value;
	var editorResouce=editor.getSource();
	fastDev.getInstance("editorVal").setValue(editorResouce);
	//fastDev.getInstance('addNewsForm').submit();
	var items=new Object();
	items["newsId"]=newsId;
	items["newsTitle"]=fastDev.getInstance("newsTitle").getValue();
	items["newsAuthor"]=fastDev.getInstance("newsAuthor").getValue();
	items["summary"]=fastDev.getInstance("summary").getValue();
	items["editorVal"]=fastDev.getInstance("editorVal").getValue();
	items['cNewsFileId']=fastDev.getInstance("cNewsFileId").getValue();
	items['manageSection']=fastDev.getInstance("manageSection").getValue();
	//items['manageSectionName']=fastDev.getInstance("manageSection").getText();
	items['manageSectionName']=document.getElementById('manageSection').value;
	items['orgId']=fastDev.getInstance("orgId").getValue();
	items['orgName']=fastDev.getInstance("orgName").getValue();
 
	
	if(items["newsTitle"]==null||items["newsTitle"]==""){
		fastDev.alert("标题不能为空！");
		return;
	} 
	if(items["newsAuthor"]==null||items["newsAuthor"]==""){
		fastDev.alert("作者不能为空！");
		return;
	} 
	if(items["manageSectionName"]==null||items["manageSectionName"]==""){
		fastDev.alert("模块不能为空！");
		return;
	} 
	if(items["orgId"]==null||items["orgId"]==""){
		fastDev.alert("部门不能为空！");
		return;
	} 
	if(items['cNewsFileId']==null||items['cNewsFileId']==""){
		fastDev.alert("封面不能为空！");
		return;
	} 
	if(items["summary"]==null||items["summary"]==""){
		fastDev.alert("摘要不能为空！");
		return;
	} 
	if(items["editorVal"]==null||items["editorVal"]==""){
		fastDev.alert("正文不能为空！");
		return;
	} 
 
	var url="news_newsUpdate.action";
	if(type=='add'){
		url="news_newsAdd.action";
	}
	
	fastDev.post(url,{
		success:function(data){
			fastDev.tips(data);
			fastDev.Ui.Window.parent.refreshDatagrid();
			win.close();
		},
		data:items
	});
	
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
		container : "addNewsForm",
		text : "文件上传中..."
	});
	return true;
}

function onUploadSuccess(file, response) {
	//fastDev.tips(response.C_FILE_ID);
	if (!!response.C_FILE_PATH) {
		var e = getElementByPath(response.C_FILE_PATH, response.C_FILE_TYPE + '');
		addMediaElement(e);
		var cNewsFileId = fastDev.getInstance('cNewsFileId');
		if (fileSum == 0) {
			cNewsFileId.setValue(response.C_FILE_ID);
		} else {
			cNewsFileId.setValue(cNewsFileId.getValue() + ',' + response.C_FILE_ID);
		}
		//fileSum++;
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
	var ttrr=document.getElementById('tr0');
	if(ttrr!=undefined&&ttrr!=null){
		document.getElementById('fileTable').removeChild(ttrr);
	}
	var tr = document.createElement('tr');
	tr.id = 'tr0';
	var td = document.createElement('td');
	td.appendChild(e);
	tr.appendChild(td);
	document.getElementById('fileTable').appendChild(tr);
	/*
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
	*/
}

/*
//表单提交后回调
function onSubmitSuccess(data) {
	if(data.result) {
		fastDev.Ui.Window.parent.refreshDatagrid();
		fastDev.Ui.Window.parent.closeDialog();
		fastDev.tips(data.msg);
	} else {
		fastDev.alert("发起消息异常，请联系管理员！", '信息提示', data.status);
	}
}
*/


function editOrgid(){
	var orgid=fastDev.getInstance('orgId').getValue();
	var type=request['type'];

	if(orgid==null || orgid==''){
		if(request['type']!='add'){
			fastDev.tips("正在加载中，请稍等！");
			return;
		}
	}
	fastDev.getInstance('org').setValue(orgid);
	document.getElementById("orgnameDiv").style.display="none";
	document.getElementById("orgDiv").style.display="block";
	
}

function setOrgid(value){
	//alert(value);
	var orgid=fastDev.getInstance('orgId');
	orgid.setValue(value);
	
	document.getElementById("orgnameDiv").style.display="block";
	var orgname=fastDev.getInstance("org").getText();
	
	fastDev.getInstance('orgName').setValue(orgname);
	document.getElementById("orgDiv").style.display="none";
}

