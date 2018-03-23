var userId = getUrlParam("userId");
var type = 1;
var index = 1;
var total = null;

$(document).bind('mobileinit', function () {} );

//获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)'); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); //匹配目标参数
	if (null != r) {
		return unescape(r[2]);
	} else {
		return null; //返回参数值
	}
}

$(document).ready(function() {
	setContent();
});

function switchType(id) {
	type = id;
	index = 1;
	setContent();
}

function prePage() {
	if (index > 1) {
		index--;
		setContent();
	} else {
		alert("已经是第一页了！");
	}
}

function setPage() {
	setContent();
}

function nextPage() {
	if (index < total) {
		index++;
		setContent();
	} else {
		alert("已经是最后一页了！");
	}
}

function setContent() {
	showLoading();
	var url = null, text = null;
	var name = $('#search').val();
	var page = $('#pageSelect').val();
	switch (type) {
		case 1:
			url = 'task_new/getAllTaskForMobileAction.action';
			text = '没有工作任务';
			break;
		case 2:
			url = 'taskErrorInfo/getAllTaskErrorInfoForMobileAction.action';
			text = '没有异常信息';
			break;
		case 3:
			url = 'task_new/getAllTaskForMobileAction.action';
			text = '没有工作安排';
			break;
		default:
			break;
	}
	$.ajax({
		type : 'POST',
		url : url,
		data : {userId : userId, type : type, name : name, page : page, index : index},
		dataType : 'json',
		success : function(data) {
			var _data = data.data, _info = data.info;
			total = _info.total;
			var index = _info.page + '/' + _info.total + ' 共' + _info.records + '条';
			$('#index').html(index);
			if (_data.length > 0) {
				var html = '<ol id="ol" data-role="listview" data-inset="true" data-filter="true" data-filter-placeholder="搜索名称"></ol>';
				$('#content').html(html);
				if ((1 == type) || (3 == type)) {
					for (var i = 0; i < _data.length; i++) {
						var _html = '<li><a href="../taskinfo/taskdetail.html?taskid=' + _data[i].C_TASK_ID + '" target="_top">' + _data[i].C_TASK_NAME + '</a></li>';
						$('#ol').append(_html);
					}
				} else if (2 == type) {
					for (var i = 0; i < _data.length; i++) {
						var _html = '<li><a href="../errinfo/errdetail.html?errId=' + _data[i].C_ERR_ID + '" target="_top">' + _data[i].C_ERR_NAME + '</a></li>';
						$('#ol').append(_html);
					}
				}
			} else {
				$('#content').html(text);
			}
			$('#content').trigger('create');
			hideLoading();
		}
	});
}

function showLoading() {
	$('#page').hide();
	$.mobile.loading('show', {
		text : '数据加载中，请稍等...',
		textVisible : true,
		theme : 'a'
	});
}

function hideLoading() {
	$('#page').show();
	$.mobile.loading('hide');
}