var  request=fastDev.Browser.getRequest();


function doReset() {
	fastDev.getInstance('errRecord').cleanData();
}

function doSave(obj,grid) {
    if(check()==false){
    	return;
    }
	var form=fastDev.getInstance('errRecord');
	var items=form.getItems();
	//alert(fastDev.Util.JsonUtil.parseString(items));
	fastDev.post("errTrace_newError.action",{
		success:function(data){
				if(data=='数据提交成功!'){
					fastDev.tips('数据提交成功!');
					if(grid!=null&&grid!=undefined){
					grid.refreshData(false);
				}
				obj.close();
			}
			
		},
		data:items
	});
}

var isUploading, uploader, fileList;
var isUploading2, uploader2, fileList2;
fastDev(function() {
	fastDev("#verifyAndEvaluate").hide();
	uploader = fastDev.getInstance("upfileAccessory");
	fileList = fastDev("#fileList");// fileList为自定义的一个展现文件信息的列表
	uploader2 = fastDev.getInstance("upfileAccessory2");
	fileList2 = fastDev("#fileList2");
	fastDev.getInstance('taskErrTraceVo.c_err_kind').setValue(request['c_err_kind']);
	fastDev.getInstance('taskErrTraceVo.c_task_id').setValue(request['c_task_id']);
	var c_task_name=request['c_task_name'];
	fastDev.getInstance('taskErrTraceVo.c_err_name').setValue(c_task_name);
});


function uploadFile1(){
	showMutipleFileUploadWindow(1);
}
function uploadFile2() {
	showMutipleFileUploadWindow(2);
}

function showMutipleFileUploadWindow(type) {
	var list = fastDev("#fileList");
	var fileId = fastDev.getInstance('taskErrTraceVo.c_results');
	var fileType=fastDev.getInstance('taskErrTraceVo.c_tracefunids');
	if (type == 2) {
		list = fastDev("#fileList2");
		fileId = fastDev.getInstance('taskErrTraceVo.dealc_results');
		fileType=fastDev.getInstance('taskErrTraceVo.dealc_tracefunids');
	}
	//alert(fileId + "," + list + "," + type);
	var url = window.location.href;
	var index = url.lastIndexOf("/");
	url = url.substring(0, index);
	url += "/mutipleFileUpload.html";
	fastDev.create("Window", {
		//showCloseBtn : false,
		src : url,
		title : "取证文件上传",
		width : 500,
		height : 340,
		inside : false,
		modal : true,
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			onclick : function(event, that, win, fast) {
				var files = win.getFiles();   //获取上传的文件信息
				if(files.length==0||files==null||files==undefined){
					fastDev.tips('请先上传文件！');
					return ;
				}
				var fileStr = "",typeStr="";
				if (files != null && files.length > 0) {
					fileStr += files[0].c_file_id;
					typeStr+=files[0].type;
					for ( var i = 1; i < files.length; i++) {
						fileStr = fileStr + "," + files[i].c_file_id;
						typeStr=typeStr+","+files[i].type;
					}
				}
				var oldfile = fileId.getValue();
				var oldType=fileType.getValue();
				if (oldfile != null && oldfile.trim() != '') {
					fileStr = oldfile + "," + fileStr;
				}
				if (oldType != null && oldType.trim() != '') {
					typeStr = oldType + "," + typeStr;
				}
				fileId.setValue(fileStr);
				fileType.setValue(typeStr);
				//alert(fileId.getValue()+"*****"+fileType.getValue());
				listAppend(list,files);
				that.close();
			}
		} ,{
			text : "关闭",
			iconCls : "icon-close",
			onclick : function(event, that, win, fast){
				that.close();
			}
		}]
	});
}


function listAppend(list, file) {
	for ( var i = 0; file != null && file != undefined && i < file.length; i++) {
		var item, li = fastDev("<li style='font-size:17px'></li>").appendTo(
				list).prop("id", "li" + file[i].c_file_id);
		li.append(item = fastDev("<div></div>").prop("id",
				"item" + file[i].c_file_id).css({
			height : 25
		}));

		item.elems[0].innerHTML = "<span style='margin-right:10px'>"
				+ file[i].name + "</span><span id='loading" + file[i].c_file_id
				+ "' style='margin-right:10px'><a id='upload"
				+ file[i].c_file_id + "' href='###' onclick=viewSource('"+file[i].c_file_id+"') >查看</a>";
	}
}


function viewSource(c_file_id) {
	 
	 fastDev.post("errTrace_getFileInfoById.action", {
		success : function(data) {
			  
			  var path=data.c_file_path;
			  var type=data.c_file_type;
			  //alert("path="+path);
			  if(type==1||type=='1'){
				  viewImageDetail(path);
			  }else if(type==2||type=='2'){
				  viewAudioDetail(path);  
			  }else if(type==3||type=='3'){
				  viewVideoDetail(path);
			  }
			  
		},
		data : {
			"c_file_id" : c_file_id
		}
	});

};

function viewImageDetail(src) {

	fastDev.create('Window', {
		width : 500,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : "图片详情",
		allowResize : false,
		src : src
	});
}

function viewAudioDetail(src) {
	var url = window.location.href;
	var index = url.lastIndexOf("/");
	url = url.substring(0, index);
	url += '/detail.html?type=2&src=';
	fastDev.create('Window', {
		width : 500,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : "音频详情",
		allowResize : false,
		src : '../xwzcxt/taskerror/detail.html?type=2&src=' + src
	});
}

function viewVideoDetail(src) {
	var url = window.location.href;
	var index = url.lastIndexOf("/");
	url = url.substring(0, index);
	url += '/detail.html?type=3&src=';
	fastDev.create('Window', {
		width : 500,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : "视频详情",
		allowResize : false,
		src : url + src
	});
}

function getDatetimeStr(date){
	var year=date.getFullYear();
	var month=date.getMonth()+1;
	var day=date.getDate();
	var hour=date.getHours();
	var minutes=date.getMinutes();
	var seconds=date.getSeconds();
	if(month<10){
		month="0"+month;
	}
	if(day<10){
		day="0"+day;
	}
	if(hour<10){
		hour="0"+hour;
	}
	if(minutes<10){
		minutes="0"+minutes;
	}
	if(seconds<10){
		seconds="0"+seconds;
	}
	
	return year+"-"+month+"-"+day+" "+hour+":"+minutes+":"+seconds;
}

function showHandle(event,item){
	if(item.value=='1'){
		fastDev('#tb2').show();
		var now=new Date();
		
		now.setDate(now.getDate()+3);
		fastDev.getInstance('taskErrTraceVo.c_chk_plantime').setValue(getDatetimeStr(now));
		now.setDate(now.getDate()+2);
		fastDev.getInstance('taskErrTraceVo.c_evaluate_plantime').setValue(getDatetimeStr(now));
	}else{
		fastDev('#tb2').hide();
	}
	
}

function check(){
	var c_err_name= fastDev.getInstance('taskErrTraceVo.c_err_name').getValue();
	if(c_err_name==null||$.trim(c_err_name)==''){
		fastDev.alert('异常主题不能为空！');
		return false;
	}
	var c_manage_section=fastDev.getInstance('taskErrTraceVo.c_manage_section').getValue();
	if(c_manage_section==null||$.trim(c_manage_section)==''){
		fastDev.alert('板块不能为空！');
		return false;
	}
	
	var c_occur_time=fastDev.getInstance('taskErrTraceVo.c_occur_time').getValue();
	if(c_occur_time==null||$.trim(c_occur_time)==''){
		fastDev.alert('发生时间不能为空！');
		return false;
	}
	
	var c_to_userid= fastDev.getInstance('taskErrTraceVo.c_to_userid').getValue();
	if(c_to_userid==null||$.trim(c_to_userid)==''){
		fastDev.alert('接收人不能为空！');
		return false;
	}
	
	var c_suggestend_time=fastDev.getInstance('taskErrTraceVo.c_suggestend_time').getValue();
	if(c_suggestend_time==null || c_suggestend_time==''){
		fastDev.alert('异常的建议处理时间不能为空！');
		return false;
	}
	
	var now=new Date();
	var nowStr=getDatetimeStr(now);
	
	if(c_occur_time>c_suggestend_time){
		fastDev.alert("异常发生时间不能比建议处理时间迟!");
		return false;
	}
	
	if(c_suggestend_time<nowStr){
		fastDev.alert("异常的建议处理时间不能比当前时间早!");
		return false;
	}
	var c_errdes=fastDev.getInstance('taskErrTraceVo.c_errdes').getValue();
	if(c_errdes==null||$.trim(c_errdes)==''){
		fastDev.alert('异常描述不能为空！');
		return false;
	}
	var dealc_errdes=fastDev.getInstance('taskErrTraceVo.dealc_errdes').getValue();
	var c_isbyself=fastDev.getInstance('taskErrTraceVo.c_isbyself').getValue();
	if(c_isbyself==1&&(dealc_errdes==null||$.trim(dealc_errdes)=='')){
		fastDev.alert('因为您选择了自己处理异常，所以异常处理不能为空！');
		return false;
	}
	var c_chk_userid=fastDev.getInstance('taskErrTraceVo.c_chk_userid').getValue();
	if(!c_chk_userid&&c_isbyself=='1'){
		fastDev.alert('验证人不能为空！');
		return false;
	}
	var c_evaluate_userid=fastDev.getInstance('taskErrTraceVo.c_evaluate_userid').getValue();
	if(!c_evaluate_userid&&c_isbyself=='1'){
		fastDev.alert('评价人不能为空！');
		return false;
	}
	
	return true;
}

function getLocationHead(){
	var path=window.location.href;
	var index=-1;
	for(var i=1;i<5;i++){
		index=path.indexOf("/", index+1);
	}
	path=path.substring(0,index);
	return path;
}

function accept(){   //选择接收人
	var path=getLocationHead()+"/xwzcxt/taskerror/PositionTree/DynamicPositionTree.html";
	//alert(10);
	fastDev.create("Window", {
		height:600,
		width:350,
		inside : false,
		showMaxBtn:false,
	    title:"选择接收人",
	    src : path,
	 	buttons:[{
			text : "确定",
			width : "100px",
			align : "center",
			iconCls : 'icon-save',
			onclick : function(event, win, cwin, fast) {
				var userInfo=cwin.getLeafValue();
				if(!!userInfo){
					fastDev.getInstance('taskErrTraceVo.c_to_userid').setValue(userInfo.id);
					fastDev.getInstance('acceptName').setValue(userInfo.name);
					win.close();
				}else{
					fastDev.alert("请选择接收人!");
				}
			}
	}]
	});

	
}

function  copyto(e){  //选择抄送人
	var element = e.srcElement || e.target;
	var name=element.id;
	var id = name;
	var cPid = fastDev.getInstance(id).getValue();
	var path=getLocationHead();
	path+='/xwzcxt/taskerror/PositionTree/dynaimcMutiSelectPosTree.html';
	//alert(path);
	fastDev.create('Window', {
		width : 300,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : "选择抄送人",
		allowResize : false,
		src : path,
		buttons : [ {
			id : 'ok',
			text : '确定',
			onclick : function(event, win, cwin, fast) {
				var user = cwin.getLeafValue();
				if (!!user) {
					setPosition(name, user.id, user.name);
					win.close();
				} else {
					window.alert("请选择反馈人！");
				}
			}
		}, {
			id : 'clear',
			text : '清空',
			onclick : function(event, win) {
				setPosition(name, "", "");
				win.close();
			}
		} ]
	});
}


function verify(){   //选择验证人
	var path=getLocationHead()+"/xwzcxt/taskerror/PositionTree/DynamicPositionTree.html";
	//alert(10);
	fastDev.create("Window", {
		height:600,
		width:350,
		inside : false,
		showMaxBtn:false,
	    title:"选择验证人",
	    src : path,
	 	buttons:[{
			text : "确定",
			width : "100px",
			align : "center",
			iconCls : 'icon-save',
			onclick : function(event, win, cwin, fast) {
				var userInfo=cwin.getLeafValue();
				if(!!userInfo){
					fastDev.getInstance('taskErrTraceVo.c_chk_userid').setValue(userInfo.id);
					fastDev.getInstance('c_chk_username').setValue(userInfo.name);
					win.close();
				}else{
					fastDev.alert("请选择验证人!");
				}
			}
	}]
	});

	
}


function evaluate(){   //选择验证人
	var path=getLocationHead()+"/xwzcxt/taskerror/PositionTree/DynamicPositionTree.html";
	//alert(10);
	fastDev.create("Window", {
		height:600,
		width:350,
		inside : false,
		showMaxBtn:false,
	    title:"选择评价人",
	    src : path,
	 	buttons:[{
			text : "确定",
			width : "100px",
			align : "center",
			iconCls : 'icon-save',
			onclick : function(event, win, cwin, fast) {
				var userInfo=cwin.getLeafValue();
				if(!!userInfo){
					fastDev.getInstance('taskErrTraceVo.c_evaluate_userid').setValue(userInfo.id);
					fastDev.getInstance('c_evaluate_username').setValue(userInfo.name);
					win.close();
				}else{
					fastDev.alert("请选择评价人!");
				}
			}
	}]
	});

	
}

function setPosition(name, cPid, cPidName) {
	fastDev.getInstance("taskErrTraceVo.c_cc_userid_list").setValue(cPid);
	fastDev.getInstance(name).setValue(cPidName);
}