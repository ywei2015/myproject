var request=fastDev.Browser.getRequest();

fastDev(function(){
	fastDev("#verifyAndEvaluate").hide();
	var type =request['type'];
	if(type=='1'){    //安排整改
		$('#title').html('<font color="red">*</font>安排类容');
	}else if(type=='2'){  //继续反馈
		$('#title').html('<font color="red">*</font>反馈内容');
		$("#tb2").remove();
	}
});

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


function evaluate(){   //选评价人
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

function wirteEndtime(){
	var type=request['type'];
	if(type!='1'){
		return;
	}
	var endtimeString='<tr id="endtime" valign="middle" style="display:false;">'+
	'<td class="ui-form-table-dt"><font color="red">*</font></font>整改期限</td>'+
	'<td class="ui-form-table-dd" colspan="3">'+
	'<input itype="DatePicker" onblur="setChkAndEvalateTime()" format="yyyy-MM-dd HH:mm:ss" id="taskErrTraceVo.c_suggestend_time" width="100%" saveInstance="true" />'+
	'</td></tr>';
	document.write(endtimeString);
	document.close();
}

function setChkAndEvalateTime(){
	var c_suggestend_time=fastDev.getInstance('taskErrTraceVo.c_suggestend_time').getValue();
	var date=new Date(c_suggestend_time);
	date.setDate(date.getDate()+3);
	var text=getDatetimeStr(date);
	fastDev.getInstance('taskErrTraceVo.c_chk_plantime').setValue(text);
	date.setDate(date.getDate()+2);
	text=getDatetimeStr(date);
	fastDev.getInstance('taskErrTraceVo.c_evaluate_plantime').setValue(text);
	
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



function uploadFile() {
	var list = fastDev("#fileList");
	var fileId = fastDev.getInstance('taskErrTraceVo.c_results');
	var fileType=fastDev.getInstance('taskErrTraceVo.c_tracefunids');

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
				if (oldfile != null && oldfile != '') {
					fileStr = oldfile + "," + fileStr;
				}
				if (oldType != null && oldType != '') {
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


function accept(){   //选择接收人
	var path=getLocationHead()+"/xwzcxt/mytask/personSelect.html";
	fastDev.create("Window", {
		height:300,
		width:350,
		showMaxBtn:false,
	    title:"选择接收人",
	    src : path,
	 	buttons:[{
			text : "确定",
			width : "100px",
			align : "center",
			iconCls : 'icon-save',
			onclick : function(event, win, cwin, fast) {
				var userInfo=cwin.getUserInfo();
				fastDev.getInstance('taskErrTraceVo.c_to_userid').setValue(userInfo.userid);
				fastDev.getInstance('acceptName').setValue(userInfo.username);
				win.close();
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
	path+='/xwzcxt/taskerror/templetSelectPositionForSearch.html?module=ycczcx&cPid='+cPid;
	//alert(path);
	fastDev.create('Window', {
		width : 300,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : "选择岗位",
		allowResize : false,
		src : path,
		buttons : [ {
			id : 'ok',
			text : '确定',
			onclick : function(event, win, childWin, fast) {
				var cPosition = fast.getInstance("cPosition");
				var pids = cPosition.getChkedIds("onlyLeafValue");
				if (pids) {
					setPosition(name, pids, cPosition.getValsByids(pids));
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

function setPosition(name, cPid, cPidName) {
	fastDev.getInstance("taskErrTraceVo.c_cc_userid_list").setValue(cPid);
	fastDev.getInstance(name).setValue(cPidName);
}

function check(){
	    var type=request['type'];
		var c_to_userid=fastDev.getInstance('taskErrTraceVo.c_to_userid').getValue();
		if(!c_to_userid){
			fastDev.alert('请选择接收人！');
			return false;
		}
		
		var c_errdes=  fastDev.getInstance('taskErrTraceVo.c_errdes').getValue();
		if(!c_errdes){
			if(type==1){
				fastDev.alert('安排内容不能为空！');
				return false;
			}else if(type==2){
				fastDev.alert('反馈内容不能为空！');
				return false;
			}
		}
		if(type=='1'){
			var c_chk_userid=fastDev.getInstance('taskErrTraceVo.c_chk_userid').getValue();
			if(!c_chk_userid){
				fastDev.alert('请选择验证人！');
				return false;
			}
			
			var c_evaluate_userid=fastDev.getInstance('taskErrTraceVo.c_evaluate_userid').getValue();
			if(!c_evaluate_userid){
				fastDev.alert('请选择评价人！');
				return false;
			}
			
			var now=getDatetimeStr(new Date());
			var c_suggestend_time=fastDev.getInstance('taskErrTraceVo.c_suggestend_time');
			if(now>c_suggestend_time){
				fastDev.alert("整改期限不能比当前时间早!");
				return false;
			}
			
		}
	return true;
	
}

function doSave(win,grid,ct){
	if(!check()){
		return;
	}
	var items=fastDev.getInstance('checkform').getItems();
	var type=request['type'];
	if(type=='1'){
		items['taskErrTraceVo.c_deal_type']=2;
	}else if(type=='2'){
		items['taskErrTraceVo.c_deal_type']='0';
	}
    items['taskErrTraceVo.c_feedback_id']=request['c_feedback_id'];
    items['taskErrTraceVo.c_err_id']=request['c_err_id'];
    items['taskErrTraceVo.type']=request['type'];
   // alert(fastDev.Util.JsonUtil.parseString(items));
   
	fastDev.post('errTrace_continueFeedback.action',{
		success:function(data){
			if(data=='数据提交成功!'){
				fastDev.tips('数据提交成功!');
				if(grid!=null&&grid!=undefined){
					grid.refreshData(false);
				}
				win.close();
			}
			
		},
		data:items
	});
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

function doReset(){
	fastDev.getInstance('checkform').cleanData();
}
