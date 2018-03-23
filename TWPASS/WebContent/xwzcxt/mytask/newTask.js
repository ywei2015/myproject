function doReset() {
	fastDev.getInstance('checkform').resetData();
}

//保存组织信息
function doSave(fn) {
	
	var c_start_time=fastDev.getInstance("startTaskPojo.c_start_time").getValue();
	var c_end_time=fastDev.getInstance("startTaskPojo.c_end_time").getValue();
	var now=new Date();
	if(c_end_time<c_start_time){
		fastDev.alert("任务开始时间必须在任务完成时间之后!");
		return;
	}
	var end=new Date(c_end_time);
	if(end<now){
		fastDev.alert("任务结束时间必须在当前时间之后!");
		return;
	}
	

	var c_chk_userid=fastDev.getInstance('startTaskPojo.c_chk_userid').getValue();
	var c_chk_plantime=fastDev.getInstance('startTaskPojo.c_chk_plantime').getValue();
	if(c_chk_userid){
		if(!c_chk_plantime){
			fastDev.alert("选择了验证人之后，验证时间不能为空！");
			return;
		}
		
		if(c_end_time>c_chk_plantime){
			fastDev.alert("任务验证期限必须在任务结束时间之后!");
			return;
		}
	}
	var c_evaluate_userid=fastDev.getInstance('startTaskPojo.c_evaluate_userid').getValue();
	var c_evaluate_plantime=fastDev.getInstance('startTaskPojo.c_evaluate_plantime').getValue();
	if(c_evaluate_userid){
		if(!c_evaluate_plantime){
			fastDev.alert("选择了评价人之后，评价时间不能为空！");
			return;
		}
		if(c_chk_plantime){
			if(c_chk_plantime>c_evaluate_plantime){
				
			}
			fastDev.alert("任评价证期限必须在任务验证期限之后!");
			return;
		}
		
		if(!c_chk_plantime&&c_end_time){
			if(c_end_time>c_evaluate_plantime){
				
			}
			fastDev.alert("任评价证期限必须在任务结束时间之后!");
			return;
		}
	}
	fastDev.getInstance('checkform').submit();
}

//表单提交后回调
function onSubmitSuccess(data) {
	if(data.result) {
		fastDev.Ui.Window.parent.refreshData(data.msg);
		fastDev.Ui.Window.parent.closeDialog();
	} else {
		fastDev.alert(data.msg, '信息提示', data.status);
	}
}

/*
	tableId:表格的Id值 
	dtName:表现形式为dt的td的name属性值 
	ddName:表现形式为dd的td的name属性值 
*/
function FormTable(tableId,dtName,ddName){
	//table属性 
	var borderSpacing=0;
	var borderCollapse="collapse";
	var border="1px solid #D8E2EB";
	var fontSize="12px";
	var margin="0px 0px";
	var width="500px";
	var color="#333";
	var fontFamily="宋体";
	//dt属性
	var dt_backgroundColor="#E7EEF7";
	var dt_padding="1px 4px";
	var dt_textAlign="right";
	var dt_whiteSpace="nowrap";
	var dt_width="100px";
	var dt_height="26px";
	//dd属性
	var dd_padding="1px 4px";
	//属性
	var td_border="1px solid #D8E2EB";
	
	this.getBorderSpacing=function(){
		return borderSpacing;
	};
	this.getBorderCollapse=function(){
		return borderCollapse;
	};
	this.getBorder=function(){
		return border;
	};
	this.getFontSize=function(isOnlyNumber){
		if(isOnlyNumber){
			return parseInt(fontSize);
		}
		return fontSize;
	};
	this.getMargin=function(){
		return margin;
	};
	this.getWidth=function(isOnlyNumber){
		if(isOnlyNumber){
			return parseInt(width);
		}
		return width;
	};
	this.getDt_height=function(isOnlyNumber){
		if(isOnlyNumber){
			return parseInt(dt_height);
		}
		return dt_height;
	};
	this.getColor=function(){
		return color;
	};
	this.getDt_backgroundColor=function(){
		return dt_backgroundColor;
	};
	this.getFontFamily=function(){
		return fontFamily;
	};
	this.getDt_padding=function(){
		return dt_padding;
	};
	this.getDt_textAlign=function(){
		return dt_textAlign;
	};
	this.getDt_whiteSpace=function(){
		return dt_whiteSpace;
	};
	this.getDt_width=function(isOnlyNumber){
		if(isOnlyNumber){
			return parseInt(dt_width);
		}
		return dt_width;
	};
	this.getDd_padding=function(){
		return dd_padding;
	};
	this.getTd_border=function(){
		return td_border;
	};
	//Set方法
	this.setWidth=function(wid){
		width=arguments[0];
	};
	this.setDt_width=function(width){
		dt_width=arguments[0];
	};
	this.setDt_height=function(height){
		dt_height=arguments[0];
	};
	this.setMargin=function(marginStyle){
		margin=arguments[0];
	};
	this.setDt_textAlign=function(alignType){
		dt_textAlign=arguments[0];
	};
	this.tableElement=document.getElementById(tableId);
	//获取所有td元素
	this.getAllTdElements=function(){
		return document.getElementsByTagName("td");
	};
	//获取所有dt,dd元素 
	this.getAllDlElements=function(tdElements,tdName){
		var dts=[];
		for(var i in tdElements){
			var currentTd=tdElements[i];
			if(currentTd.nodeName){
				var currentTdName=currentTd.getAttribute("name");
				if(currentTdName==tdName){
					dts.push(currentTd);
				}
			}
		}
		return dts;
	};
	//所有td元素 
	this.tdElements=this.getAllTdElements();
	//所有dt元素 
	this.dtElements=this.getAllDlElements(this.tdElements,dtName);
	//获取所有dd元素
	this.ddElements=this.getAllDlElements(this.tdElements,ddName);
	//初始化表格
	this.initTable=function(){
		//初始化table 
		this.tableElement.style.borderSpacing=this.getBorderSpacing();
		this.tableElement.style.borderCollapse=this.getBorderCollapse();
		//this.tableElement.style.border=this.getBorder();
		this.tableElement.style.fontSize=this.getFontSize();
		this.tableElement.style.margin=this.getMargin();
		this.tableElement.style.width=this.getWidth();
		this.tableElement.style.color=this.getColor();
		this.tableElement.style.fontFamily=this.getFontFamily();
		//初始化td
		for(var k in this.tdElements){
			var currentElement=this.tdElements[k];
			if(typeof currentElement=="object"){
				currentElement.style.border=this.getTd_border();
			}
		}
		//初始化dt
		for(var i in this.dtElements){
			var curElement=this.dtElements[i];
			if(typeof curElement=="object"){
				curElement.style.padding=this.getDt_padding();
				curElement.style.backgroundColor=this.getDt_backgroundColor();
				curElement.style.textAlign=this.getDt_textAlign();
				curElement.style.whiteSpace=this.getDt_whiteSpace();
				curElement.style.width=this.getDt_width();
				curElement.style.height=this.getDt_height();
			}
		}
		//初始化dd
		for(var j in this.ddElements){
			var curEle=this.ddElements[j];
			if(typeof curEle=="object"){
				curEle.style.padding=this.getDd_padding();
			}
		}
	};
}


var isUploading, uploader, fileList;
//document onload后，获取控件实例等
fastDev(function() {
	uploader = fastDev.getInstance("upfileAccessory");
	// fileList为自定义的一个展现文件信息的列表
	fileList = fastDev("#fileList");
});

function onFileChoose(file) {
	var item, li = fastDev("<li style='font-size:17px'></li>").appendTo(fileList).prop("id", "li" + file.id);
	li.append( item = fastDev("<div></div>").prop("id", "item" + file.id).css({
		height : 25
	}));
	item.elems[0].innerHTML = "<span style='margin-right:10px'>" + file.name + "</span><span style='margin-right:10px' id='status" + file.id + "'>等待上传</span>" +"<span id='loading" + file.id + "' style='margin-right:10px'><a id='upload" + file.id + "' href='###'>上传</a></span><span style='margin-right:10px'><a id='cancel" + file.id + "' href='###'>删除</a></span>";
	fastDev("#upload"+file.id).elems[0].onclick = function() {
		if (isUploading) {
			fastDev.tips("已经有文件正在上传，请耐心等待其上传完成后再尝试此操作！");
			return;
		}
		uploader.startUpload(file.id);
		fastDev("#status" + file.id).setText("正在上传");
		fastDev("#loading" + file.id).empty().addClass("ui-loading-indicator");
		fastDev("#cancel"+file.id).setText("取消").elems[0].onclick = function() {
			// 取消当前文件上传
			uploader.cancelUpload();
			//li.remove();
		};
	};
	fastDev("#cancel"+file.id).elems[0].onclick = function() {
		// 从文件队列中移除指定ID的文件
		uploader.removeFile(file.id);
		li.remove();
	};
}

function onChooseError(file, code, msg) {
	if (code === 2) {
		fastDev.tips("仅能上传小于100M的文件");
	}
}

function onUploadStart(file) {
	isUploading = true;
	fastDev("#status" + file.id).setText("正在上传");
	fastDev("#loading" + file.id).empty().addClass("ui-loading-indicator");
	fastDev("#cancel"+file.id).setText("取消").elems[0].onclick = function() {
		// 取消当前文件上传
		uploader.cancelUpload();
	};
	
	var type = 1; //kind默认为1表示图片，2表示音频，3表示视频
	var extens = file.type;//bmp,jpeg,gif,jpg,png,mp3,mp4,avi
	if (extens == 'mp3') {
		type = 2;
	} else if (extens == 'mp4') {
		type = 3;
	}
	uploader.addParams({
		c_Affix_type : 1,
		c_chaptercode : "",
		c_filetypes : type,
		c_filename : encodeURI(file.name),
		c_extens : extens,
		c_filesize : file.size,
		c_filekind : '2'
	});
}

var  fileInfo="";
function onUploadSuccess(file, response) {
	//alert(fastDev.Util.JsonUtil.parseString(file));
	fileInfo+=response.fileid+","; 
	console.info(response);
	
	fastDev.getInstance('filestr').setValue(fileInfo);
	isUploading = false;
	fastDev.tips("文件 " + file.name + " 上传成功");
	fastDev("#item" + file.id).css("color", "green");
	fastDev("#status" + file.id).setText("上传成功");
	fastDev("#loading" + file.id).remove();
	fastDev("#cancel" + file.id).remove();
	//fastDev("#cancel" + file.id).prop("href", response[0].url).prop("target", "_blank").setText("下载");
}


function onUploadFail(file, response) {
	isUploading = false;
	fastDev.tips("文件 " + file.name + " 上传失败");
	fastDev("#item" + file.id).css("color", "red");
	fastDev("#status" + file.id).setText("上传失败");
	fastDev("#loading" + file.id).remove();
	fastDev("#cancel" + file.id).remove();
}

function onUploadCancel(file) {
	isUploading = false;
	fastDev.tips("文件 " + file.name + " 已经取消上传");
	fastDev("#item" + file.id).css("color", "gray");
	fastDev("#status" + file.id).setText("已经取消");
	fastDev("#loading" + file.id).remove();
	fastDev("#cancel" + file.id).remove();
}

//综合示例"移除所有"按钮点击回调事件
function removeAllFile() {
	if (isUploading) {
		fastDev.tips("当前正在上传文件中，请等待其上传完成后再尝试此操作！");
		return;
	}
	fileList.empty();
	// 清空文件队列
	uploader.cleanFileQueue();
}

//获取统计信息
function getStats() {
	var stats = uploader.getStats(), html = "";
	fastDev.each(stats, function(name, value) {
		html += (name + " : " + value + "<br/>");
	});
	fastDev.create("Window", {
		content : html,
		title : "文件上传统计"
	});
}

//获取文件队列
function getFileQueue() {
	// 获取当前上传队列的一份快照
	var queue = uploader.getFileQueue();
	fastDev.tips(queue.toString());
}

//清空文件队列
function cleanFileQueue() {
	// 清空并返回当前上传文件队列
	var queue = uploader.cleanFileQueue();
	fastDev.tips("文件队列已清空");
}


