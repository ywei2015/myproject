var id = null;

$(document).bind('mobileinit', function () {} );

$(document).ready(function() {
	setTableTypeTree();
});

$(document).on('pagebeforeshow', '#page2', function() {
	showLoading();
	var map = document.getElementById('map');
	map.style.width = (window.innerWidth) * 0.99 + 'px';
	map.style.height = (window.innerHeight) * 0.99 + 'px';
	map.src = 'map.html?cObjectTypeid=' + id;
	hideLoading();
});

function showLoading() {
	$.mobile.loading('show', {
		text : '数据加载中，请稍等...',
		textVisible : true,
		theme : 'a'
	});
}

function hideLoading() {
	$.mobile.loading('hide');
}

function setTableTypeTree() {
	showLoading();
	$.ajax({
		type : 'POST',
		url : 'jobObjects/jobObjects_getTableTypeTreeForMobile.action',
		dataType : 'json',
		success : function(data) {
			var html =
				'<div id="-1" data-role="collapsible" data-collapsed="false">' +
				'<h1>所有作业对象</h1>' +
				'<ul id="ul-1" data-role="listview" data-inset="true"></ul>' +
				'</div>';
			$('#content1').html(html);
			for (var i = 0; i < data.length; i++) {
				if ('1' == data[i].cIsproperty) {
					var _html = '<li><a id="a' + data[i].cTabletypeId + '" href="#page2" onclick="onAClick(this);">' + data[i].cTabletypeName + '</a></li>';
					$('#ul' + data[i].cTabletypeUpid).append(_html);
				} else if ('2' == data[i].cIsproperty) {
					var _html =
						'<div id="' + data[i].cTabletypeId + '" data-role="collapsible">' +
						'<h1>' + data[i].cTabletypeName + '</h1>' +
						'<ul id="ul' + data[i].cTabletypeId + '" data-role="listview" data-inset="true"></ul>' +
						'</div>';
					$('#ul' + data[i].cTabletypeUpid).before(_html);
				}
			}
			$('#content1').trigger('create');
			hideLoading();
		}
	});
}

function onAClick(a) {
	id = a.id.substring(1);
}