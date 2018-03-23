
/**
 * 取得资源所拥有的数据级权限进行数据级权限控制
 * @param type
 * @param tree
 */
function showDataModes(type, tree, window) {
	fastDev.create('Proxy', {
		action: 'authorityManage/getPrivilegeDataModesAction.action'
	}).save({
		module: type
	}, function(result) {
		var id = result.positionid;
		var scope = result.scope;
		if (scope.indexOf('All') > -1) {
			tree.show();
		} else if (scope.indexOf('ITMinister') > -1) {
			var pid = tree.getParentid(id);
			while ("1-1000509" != tree.getParentid(pid)) {
				pid = tree.getParentid(pid);
			}
			delNode(tree, pid);
		} else if (scope.indexOf('VFMinister') > -1) {
			
		} else if (scope.indexOf('Leader') > -1) {
			delNode(tree, tree.getParentid(id));
		} else if (scope.indexOf('Crew') > -1) {
			delNode(tree, id);
		} else {
			tree.hide();
		}
		if (window) {
			window.close();
		}
	});
}

function delNode(tree, id) {
	var pid = "";
	var cid = id;
	while ("1-1000509" != pid) {
		pid = tree.getParentid(cid);
		var nodes = tree.getNodesByPid(pid);
		for (var i = (nodes.length - 1); i > -1; i--) {
			var tmpid = nodes[i].id;
			if (tmpid != cid) {
				tree.delNode(tmpid);
			}
		}
		cid = pid;
	}
	tree.show();
}

/**
 * 取得资源所拥有的按钮级权限进行按钮级权限控制，同时支持对DataGrid设定高度
 * @param type
 * @param datagrid
 */
function showResourceModes(type, datagrid) {
	fastDev.create('Proxy', {
		action: 'authorityManage/getPrivilegeResourceModesAction.action'
	}).save({
		module: type
	}, function(result) {
		//alert(JSON.stringify(result));
		if (result != null) {
			for (var i = 0; i < result.length; i++) {
				fastDev('[id=' + result[i] + ']').show();
			}
		}
		if (datagrid) {
			var height = datagrid.getOptions().height;
			datagrid.setHeight(height);
		}
	});
}

//fastDev(function() {
//	validateSessionExpire();
//});

/**
 * 获取多媒体节点
 * @param path 多媒体文件路径
 * @param type 多媒体文件类型（1：图片，2：音频，3：视频，4：其他）
 * @returns e 多媒体节点
 */
function getElementByPath(path, type) {
	var e;
	switch (type) {
		case '1':
			e = document.createElement('img');
			e.src = image_image;
			e.style.cursor = 'pointer';
			e.onclick = function(){viewImageDetail(path);};
			break;
		case '2':
			e = document.createElement('img');
			e.src = image_audio;
			e.style.cursor = 'pointer';
			e.onclick = function(){viewAudioDetail(path);};
			break;
		case '3':
			e = document.createElement('img');
			e.src = image_video;
			e.style.cursor = 'pointer';
			e.onclick = function(){viewVideoDetail(path);};
			break;
		case '4':
			e = document.createTextNode('—');
			break;
		default:
			e = document.createTextNode('—');
			break;
	}
	return e;
}

/**
 * 图片详情查看
 * @param src 多媒体文件路径
 */
function viewImageDetail(src) {
	fastDev.create('Window', {
		width : $(window).width(),
		height : $(window).height(),
		inside : false,
		showMaxBtn : false,
		title : '图片详情',
		allowResize : false,
		src : src
	});
}

/**
 * 音频详情查看
 * @param src 多媒体文件路径
 */
function viewAudioDetail(src) {
	fastDev.create('Window', {
		width : $(window).width(),
		height : $(window).height(),
		inside : false,
		showMaxBtn : false,
		title : '音频详情',
		allowResize : false,
		src : '../fastdev/detail.html?type=2&src=' + src
	});
}

/**
 * 视频详情查看
 * @param src 多媒体文件路径
 */
function viewVideoDetail(src) {
	fastDev.create('Window', {
		width : $(window).width(),
		height : $(window).height(),
		inside : false,
		showMaxBtn : false,
		title : '视频详情',
		allowResize : false,
		src : '../fastdev/detail.html?type=3&src=' + src
	});
}