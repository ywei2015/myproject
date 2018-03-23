	var isUploading, uploader, fileList;
	var files=[];
	// document onload后，获取控件实例等
	fastDev(function() {
		uploader = fastDev.getInstance("upfileAccessory");
		// fileList为自定义的一个展现文件信息的列表
		//alert(111);
		fileList = fastDev("#fileList");
		//alert(flileList);
	});

	function onFileChoose(file) {
	    //alert("choose");
		var item, li = fastDev("<li style='font-size:17px'></li>").appendTo(
				fileList).prop("id", "li" + file.id);
		li.append(item = fastDev("<div></div>").prop("id", "item" + file.id)
				.css({
					height : 25
				}));

		item.elems[0].innerHTML = "<span style='margin-right:10px'>"
				+ file.name
				+ "</span><span style='margin-right:10px' id='status" 
		  + file.id + "'>等待上传</span><span id='loading" + file.id 
		  + "' style='margin-right:10px'><a id='upload" + file.id 
		  + "' href='###'>上传</a></span><span style='margin-right:10px'><a id='cancel" 
		  + file.id + "' href='###'>删除</a></span>";

		fastDev("#upload" + file.id).elems[0].onclick = function() {
			if (isUploading) {
				fastDev.tips("已经有文件正在上传，请耐心等待其上传完成后再尝试此操作！");
				return;
			}
			uploader.startUpload(file.id);
			fastDev("#status" + file.id).setText("正在上传");
			fastDev("#loading" + file.id).empty().addClass(
					"ui-loading-indicator");
			fastDev("#cancel" + file.id).setText("取消").elems[0].onclick = function() {
				// 取消当前文件上传
				uploader.cancelUpload();
			};
		};
		fastDev("#cancel" + file.id).elems[0].onclick = function() {
			// 从文件队列中移除指定ID的文件
			uploader.removeFile(file.id);
			li.remove();
		};
	}

	function onChooseError(file, code, msg) {
	    //alert("choose error");
		if (code === 2) {
			fastDev.tips("仅能上传小于100M的文件");
		}
	}

    
	function onUploadStart(file) {
	    //alert("start");
		isUploading = true;
		fastDev("#status" + file.id).setText("正在上传");
		fastDev("#loading" + file.id).empty().addClass("ui-loading-indicator");
		fastDev("#cancel" + file.id).setText("取消").elems[0].onclick = function() {
			// 取消当前文件上传
			uploader.cancelUpload();
		};

		//var uploader = fastDev.getInstance("upfileAccessory");
		//alert(fastDev.Util.JsonUtil.parseString(file));
		var type = 1; //kind默认为1表示图片，2表示音频，3表示视频
		var extens = file.type;
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

	function onUploadSuccess(file, response) {
	   // alert("success");
	    var fileInfo={"c_file_id":"","name":"","path":"","type":""};
	    var type = 1; //kind默认为1表示图片，2表示音频，3表示视频
		var extens = file.type;
		if (extens == 'mp3') {
			type = 2;
		} else if (extens == 'mp4') {
			type = 3;
		}
		fileInfo.type=type;
	    fileInfo.c_file_id=response.c_file_id;
	    fileInfo.name=file.name;
	    files.push(fileInfo);	    
		isUploading = false;
		//alert(fastDev.Util.JsonUtil.parseString(fileInfo));
		fastDev.tips("文件 " + file.name + " 上传成功");
		fastDev("#item" + file.id).css("color", "green");
		fastDev("#status" + file.id).setText("上传成功");
		fastDev("#loading" + file.id).remove();
		fastDev("#cancel" + file.id).remove();
	}

	function onUploadFail(file, response) {
	    //alert("fail");
		isUploading = false;
		fastDev.tips("文件 " + file.name + " 上传失败");
		fastDev("#item" + file.id).css("color", "red");
		fastDev("#status" + file.id).setText("上传失败");
		fastDev("#loading" + file.id).remove();
		fastDev("#cancel" + file.id).remove();
	}

	function onUploadCancel(file) {
	   // alert("cancel");
		isUploading = false;
		fastDev.tips("文件 " + file.name + " 已经取消上传");
		fastDev("#item" + file.id).remove();
	}

	// 综合示例"移除所有"按钮点击回调事件
	function removeAllFile() {
		if (isUploading) {
			fastDev.tips("当前正在上传文件中，请等待其上传完成后再尝试此操作！");
			return;
			
			
		}
		fileList.empty();
		// 清空文件队列
		uploader.cleanFileQueue();
	}
	
function getFiles(){
      return files;
}
