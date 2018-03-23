var request = fastDev.Browser.getRequest();
var cErrId = request['cErrId'];
var cTaskId = request['cTaskId'];
var dg_seq = request['dg_seq'];

var loadingWindow = null;
var initTaskDetail = false;
var isCheckEvaluate = false;

var image_finished = '../../images/finished.png';
var image_unfinished = '../../images/unfinished.png';
var image_unused = '../../images/unused.png';
var image_enable = '../../images/enable.png';
var image_disable = '../../images/disable.png';
var image_enableUp = '../../images/enableUp.png';
var image_enableDown = '../../images/enableDown.png';
var image_cornerUp = '../../images/cornerUp.png';
var image_cornerDown = '../../images/cornerDown.png';
var image_straight = '../../images/straight.png';
var image_straightDownEnable = '../../images/straightDownEnable.png';
var image_straightDownDisable = '../../images/straightDownDisable.png';
var image_downEnable = '../../images/downEnable.png';
var image_downDisable = '../../images/downDisable.png';
var image_audio = '../../images/audio.png';
var image_image = '../../images/image.png';
var image_video = '../../images/video.png';

function onTabClick(e, title) {
	if (!initTaskDetail && ('任务详情' == title)) {
		initTaskDetail = true;
		var tTaskDetail = document.getElementById('tTaskDetail');
		var iframe = document.createElement('iframe');
		var url = '../task/taskDetails.html?edit=details&taskId=' + cTaskId;
		iframe.style.border = '0';
		iframe.style.width = '1073px';
		iframe.style.height = '440px';
		iframe.src = url;
		tTaskDetail.appendChild(iframe);
	}
}

$(document).ready(function() {
	$('#tErrorInfoFlip').click(function() {
		$('#tErrorInfo').slideToggle('slow');
		$('#tErrorInfoIcon').attr('iconCls', 'icon-down');
	});
	$('#tErrorFeedbackFlip').click(function() {
		$('#tErrorFeedback').slideToggle('slow');
		$('#tErrorFeedbackIcon').attr('iconCls', 'icon-down');
	});
	$('#tErrorAffixFlip').click(function() {
		$('#tErrorAffix').slideToggle('slow');
		$('#tErrorAffixIcon').attr('iconCls', 'icon-down');
	});
});

function onBeforeReady() {
	loadingWindow = fastDev.create('ProgressBar', {
		container : 'detail',
		text : '加载中...'
	});

	this.setOptions({
		dataSource : 'taskErrorInfo/getTaskErrorInfoDetailByIDAction.action?cErrId=' + cErrId
	});
}

function onAfterDataRender() {
	var cErrKind = fastDev.getInstance('cErrKind').getValue();
	if ('2' == cErrKind) {
		fastDev.getInstance('tErrorTab').removeAllBut('异常详情');
	}

	var tErrorFeedbackTitleTable = document.getElementById('tErrorFeedbackTitleTable');
	var data1 = ['序\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0号：', '异常主题：', '异常类型：'];
	var data2 = [dg_seq, fastDev.getInstance('cErrName').getValue(), fastDev.getInstance('cErrKindName').getValue()];
	for (var i = 0; i < 3; i++) {
		var tr = document.createElement('tr');
		var td0 = document.createElement('td');
		td0.height = '20px';
		td0.style.cssFloat = 'left';
		td0.style.fontSize = '14px';
		td0.style.fontWeight = 'bold';
		var sp0 = document.createElement('span');
		sp0.innerHTML = data1[i];
		td0.appendChild(sp0);
		tr.appendChild(td0);
		var td1 = document.createElement('td');
		td1.height = '20px';
		td1.style.cssFloat = 'left';
		td1.style.lineHeight = '18px';
		var tn0 = document.createTextNode(data2[i]);
		td1.appendChild(tn0);
		tr.appendChild(td1);
		tErrorFeedbackTitleTable.appendChild(tr);
	}

	fastDev.post('taskErrorFeedback/getTaskErrorFeedbackDetailByIDAction.action?cErrId=' +
		cErrId, {
		success : function(data) {
			var num = data.num;
			if (num > 0) {
				var tErrorFeedbackDetailTable = document.getElementById('tErrorFeedbackDetailTable');
				for (var i = 0; i < num; i++) {
					for (var j = 0; j < 4; j++) {
						var tr = document.createElement('tr');
						for (var k = 0; k < (11 + ((num - 1) * 2)); k++) {
							var td = document.createElement('td');
							td.id = 'td' + i + j + k;
							td.width = '120px';
							td.height = '78px';
							tr.appendChild(td);
						}
						tErrorFeedbackDetailTable.appendChild(tr);
					}
				}
				setErrorFeedbackData(num, data.data);
				setErrorFeedbackDataDetail(num, data.data);
			}
		}
	});
}

function setErrorFeedbackData(num, data) {
	for (var index = 0; index < num; index++) {
		if (index == 0) {
			{
				var t = document.createElement('table');
				var b = document.createElement('tbody');
				var tr0 = document.createElement('tr');
				var td00 = document.createElement('td');
				td00.width = '120px';
				td00.height = '16px';
				var tn00 = document.createTextNode('异常反馈');
				td00.appendChild(tn00);
				tr0.appendChild(td00);
				b.appendChild(tr0);
				var tr1 = document.createElement('tr');
				var td10 = document.createElement('td');
				td10.width = '120px';
				td10.height = '30px';
				var img0 = document.createElement('img');
				img0.src = image_finished;
				td10.appendChild(img0);
				tr1.appendChild(td10);
				b.appendChild(tr1);
				var tr2 = document.createElement('tr');
				var td20 = document.createElement('td');
				td20.width = '120px';
				td20.height = '16px';
				var tn20 = document.createTextNode(data[index].data[0].cFeedbackerName);
				td20.appendChild(tn20);
				tr2.appendChild(td20);
				b.appendChild(tr2);
				var tr3 = document.createElement('tr');
				var td30 = document.createElement('td');
				td30.width = '120px';
				td30.height = '16px';
				var tn30 = document.createTextNode(data[index].data[0].cFeedbackTimeString);
				td30.appendChild(tn30);
				tr3.appendChild(td30);
				b.appendChild(tr3);
				t.appendChild(b);
				document.getElementById('td' + index + '20').appendChild(t);
			}

			{
				var img0 = document.createElement('img');
				if ('0' == data[index].data[0].cIsreceived) {
					img0.src = image_disable;
				} else {
					img0.src = image_enable;
				}
				document.getElementById('td' + index + '21').appendChild(img0);
			}
		} else {
			var img00 = document.createElement('img');
			document.getElementById('td' + index + '0' + (2 + (index * 2))).appendChild(img00);
			var img01 = document.createElement('img');
			document.getElementById('td' + index + '1' + (2 + (index * 2))).appendChild(img01);
			if ('0' == data[index].data[0].cIsreceived) {
				img00.src = image_straightDownDisable;
				img01.src = image_downDisable;
			} else {
				img00.src = image_straightDownEnable;
				img01.src = image_downEnable;
			}
		}

		{
			var t = document.createElement('table');
			var b = document.createElement('tbody');
			var tr0 = document.createElement('tr');
			var td00 = document.createElement('td');
			td00.width = '120px';
			td00.height = '16px';
			var tn00 = document.createTextNode('异常接收');
			td00.appendChild(tn00);
			tr0.appendChild(td00);
			b.appendChild(tr0);
			var tr1 = document.createElement('tr');
			var td10 = document.createElement('td');
			td10.width = '120px';
			td10.height = '30px';
			var img0 = document.createElement('img');
			td10.appendChild(img0);
			tr1.appendChild(td10);
			b.appendChild(tr1);
			var tr2 = document.createElement('tr');
			var td20 = document.createElement('td');
			td20.width = '120px';
			td20.height = '16px';
			var tn20 = document.createTextNode(data[index].data[0].cReceiverUsername);
			td20.appendChild(tn20);
			tr2.appendChild(td20);
			b.appendChild(tr2);
			var tr3 = document.createElement('tr');
			var td30 = document.createElement('td');
			td30.width = '120px';
			td30.height = '16px';
			tr3.appendChild(td30);
			b.appendChild(tr3);
			if ('0' == data[index].data[0].cIsreceived) {
				img0.src = image_unfinished;
				var tn30 = document.createTextNode('—');
				td30.appendChild(tn30);
			} else {
				img0.src = image_finished;
				var tn30 = document.createTextNode(data[index].data[0].cReceiverTimeString);
				td30.appendChild(tn30);
			}
			t.appendChild(b);
			document.getElementById('td' + index + '2' + (2 + (index * 2))).appendChild(t);
		}

		{
			var img0 = document.createElement('img');
			if ('0' == data[index].data[0].cIsreceived) {
				img0.src = image_disable;
			} else {
				if ('22' == data[index].data[0].cStatus) {
					if ('1' == data[index].data[0].cFactdealType) {
						var img00 = document.createElement('img');
						img00.src = image_enableUp;
						document.getElementById('td' + index + '0' + (3 + (index * 2))).appendChild(img00);
						var img01 = document.createElement('img');
						img01.src = image_straight;
						document.getElementById('td' + index + '1' + (3 + (index * 2))).appendChild(img01);
						img0.src = image_cornerUp;
					} else if ('2' == data[index].data[0].cFactdealType) {
						var img00 = document.createElement('img');
						img00.src = image_enableUp;
						document.getElementById('td' + index + '1' + (3 + (index * 2))).appendChild(img00);
						img0.src = image_cornerUp;
					} else if ('3' == data[index].data[0].cFactdealType) {
						img0.src = image_enable;
					} else if ('4' == data[index].data[0].cFactdealType) {
						var img00 = document.createElement('img');
						img00.src = image_enableDown;
						document.getElementById('td' + index + '3' + (3 + (index * 2))).appendChild(img00);
						img0.src = image_cornerDown;
					}
				} else {
					img0.src = image_disable;
				}
			}
			document.getElementById('td' + index + '2' + (3 + (index * 2))).appendChild(img0);
		}

		{
			{
				var t = document.createElement('table');
				var b = document.createElement('tbody');
				var tr0 = document.createElement('tr');
				var td00 = document.createElement('td');
				td00.width = '120px';
				td00.height = '16px';
				var tn00 = document.createTextNode('接收确认');
				td00.appendChild(tn00);
				tr0.appendChild(td00);
				b.appendChild(tr0);
				var tr1 = document.createElement('tr');
				var td10 = document.createElement('td');
				td10.width = '120px';
				td10.height = '30px';
				var img0 = document.createElement('img');
				td10.appendChild(img0);
				tr1.appendChild(td10);
				b.appendChild(tr1);
				var tr2 = document.createElement('tr');
				var td20 = document.createElement('td');
				td20.width = '120px';
				td20.height = '16px';
				tr2.appendChild(td20);
				var tr3 = document.createElement('tr');
				var td30 = document.createElement('td');
				td30.width = '120px';
				td30.height = '16px';
				tr3.appendChild(td30);
				if (('1' == data[index].data[0].cIsreceived) && ('22' == data[index].data[0].cStatus) && ('1' == data[index].data[0].cFactdealType)) {
					img0.src = image_finished;
					var tn20 = document.createTextNode(data[index].data[0].cReceiverUsername);
					td20.appendChild(tn20);
					b.appendChild(tr2);
					var tn30 = document.createTextNode(data[index].data[0].cFactdealTimeString);
					td30.appendChild(tn30);
					b.appendChild(tr3);
					setCheckEvaluateData(index, ['0', 5]);
				} else {
					img0.src = image_unused;
				}
				t.appendChild(b);
				document.getElementById('td' + index + '0' + (4 + (index * 2))).appendChild(t);
			}

			{
				var t = document.createElement('table');
				var b = document.createElement('tbody');
				var tr0 = document.createElement('tr');
				var td00 = document.createElement('td');
				td00.width = '120px';
				td00.height = '16px';
				var tn00 = document.createTextNode('本人处理');
				td00.appendChild(tn00);
				tr0.appendChild(td00);
				b.appendChild(tr0);
				var tr1 = document.createElement('tr');
				var td10 = document.createElement('td');
				td10.width = '120px';
				td10.height = '30px';
				var img0 = document.createElement('img');
				td10.appendChild(img0);
				tr1.appendChild(td10);
				b.appendChild(tr1);
				var tr2 = document.createElement('tr');
				var td20 = document.createElement('td');
				td20.width = '120px';
				td20.height = '16px';
				tr2.appendChild(td20);
				var tr3 = document.createElement('tr');
				var td30 = document.createElement('td');
				td30.width = '120px';
				td30.height = '16px';
				tr3.appendChild(td30);
				if (('1' == data[index].data[0].cIsreceived) && ('22' == data[index].data[0].cStatus) && ('2' == data[index].data[0].cFactdealType)) {
					img0.src = image_finished;
					var tn20 = document.createTextNode(data[index].data[0].cReceiverUsername);
					td20.appendChild(tn20);
					b.appendChild(tr2);
					var tn30 = document.createTextNode(data[index].data[0].cFactdealTimeString);
					td30.appendChild(tn30);
					b.appendChild(tr3);
					setCheckEvaluateData(index, ['1', 5]);
				} else {
					img0.src = image_unused;
				}
				t.appendChild(b);
				document.getElementById('td' + index + '1' +  (4 + (index * 2))).appendChild(t);
			}

			{
				var t = document.createElement('table');
				var b = document.createElement('tbody');
				var tr0 = document.createElement('tr');
				var td00 = document.createElement('td');
				td00.width = '120px';
				td00.height = '16px';
				var tn00 = document.createTextNode('安排整改');
				td00.appendChild(tn00);
				tr0.appendChild(td00);
				b.appendChild(tr0);
				var tr1 = document.createElement('tr');
				var td10 = document.createElement('td');
				td10.width = '120px';
				td10.height = '30px';
				var img0 = document.createElement('img');
				td10.appendChild(img0);
				tr1.appendChild(td10);
				b.appendChild(tr1);
				var tr2 = document.createElement('tr');
				var td20 = document.createElement('td');
				td20.width = '120px';
				td20.height = '16px';
				tr2.appendChild(td20);
				var tr3 = document.createElement('tr');
				var td30 = document.createElement('td');
				td30.width = '120px';
				td30.height = '16px';
				tr3.appendChild(td30);
				if (('1' == data[index].data[0].cIsreceived) && ('22' == data[index].data[0].cStatus) && ('3' == data[index].data[0].cFactdealType)) {
					img0.src = image_finished;
					var tn20 = document.createTextNode(data[index].data[0].cReceiverUsername);
					td20.appendChild(tn20);
					b.appendChild(tr2);
					var tn30 = document.createTextNode(data[index].data[0].cFactdealTimeString);
					td30.appendChild(tn30);
					b.appendChild(tr3);
					setCheckEvaluateData(index, ['2', 7]);
				} else {
					img0.src = image_unused;
				}
				t.appendChild(b);
				document.getElementById('td' + index + '2' + (4 + (index * 2))).appendChild(t);
			}

			{
				var t = document.createElement('table');
				var b = document.createElement('tbody');
				var tr0 = document.createElement('tr');
				var td00 = document.createElement('td');
				td00.width = '120px';
				td00.height = '16px';
				var tn00 = document.createTextNode('继续反馈');
				td00.appendChild(tn00);
				tr0.appendChild(td00);
				b.appendChild(tr0);
				var tr1 = document.createElement('tr');
				var td10 = document.createElement('td');
				td10.width = '120px';
				td10.height = '30px';
				var img0 = document.createElement('img');
				td10.appendChild(img0);
				tr1.appendChild(td10);
				b.appendChild(tr1);
				var tr2 = document.createElement('tr');
				var td20 = document.createElement('td');
				td20.width = '120px';
				td20.height = '16px';
				tr2.appendChild(td20);
				var tr3 = document.createElement('tr');
				var td30 = document.createElement('td');
				td30.width = '120px';
				td30.height = '16px';
				tr3.appendChild(td30);
				if (('1' == data[index].data[0].cIsreceived) && ('22' == data[index].data[0].cStatus) && ('4' == data[index].data[0].cFactdealType)) {
					img0.src = image_finished;
					var tn20 = document.createTextNode(data[index].data[0].cReceiverUsername);
					td20.appendChild(tn20);
					b.appendChild(tr2);
					var tn30 = document.createTextNode(data[index].data[0].cFactdealTimeString);
					td30.appendChild(tn30);
					b.appendChild(tr3);
				} else {
					img0.src = image_unused;
				}
				t.appendChild(b);
				document.getElementById('td' + index + '3' + (4 + (index * 2))).appendChild(t);
			}
		}

		if (('1' == data[index].data[0].cIsreceived) && ('22' == data[index].data[0].cStatus) && ('3' == data[index].data[0].cFactdealType)) {
			var img0 = document.createElement('img');
			document.getElementById('td' + index + '2' + (5 + (index * 2))).appendChild(img0);

			var t = document.createElement('table');
			var b = document.createElement('tbody');
			var tr0 = document.createElement('tr');
			var td00 = document.createElement('td');
			td00.width = '120px';
			td00.height = '16px';
			var tn00 = document.createTextNode('整改实施');
			td00.appendChild(tn00);
			tr0.appendChild(td00);
			b.appendChild(tr0);
			var tr1 = document.createElement('tr');
			var td10 = document.createElement('td');
			td10.width = '120px';
			td10.height = '30px';
			var img1 = document.createElement('img');
			td10.appendChild(img1);
			tr1.appendChild(td10);
			b.appendChild(tr1);
			var tr2 = document.createElement('tr');
			var td20 = document.createElement('td');
			td20.width = '120px';
			td20.height = '16px';
			tr2.appendChild(td20);
			b.appendChild(tr2);
			var tr3 = document.createElement('tr');
			var td30 = document.createElement('td');
			td30.width = '120px';
			td30.height = '16px';
			tr3.appendChild(td30);
			b.appendChild(tr3);
			t.appendChild(b);
			document.getElementById('td' + index + '2' +  (6 + (index * 2))).appendChild(t);
			if (('1' == data[index + 1].data[0].cIsreceived) && ('22' == data[index + 1].data[0].cStatus)) {
				img0.src = image_enable;
				img1.src = image_finished;
				var tn20 = document.createTextNode(data[index + 1].data[0].cReceiverUsername);
				td20.appendChild(tn20);
				var tn30 = document.createTextNode(data[index + 1].data[0].cFactdealTimeString);
				td30.appendChild(tn30);
			} else {
				img0.src = image_disable;
				img1.src = image_unfinished;
				var tn20 = document.createTextNode(data[index + 1].data[0].cReceiverUsername);
				td20.appendChild(tn20);
				var tn30 = document.createTextNode('—');
				td30.appendChild(tn30);
			}
		}
	}
}

function setCheckEvaluateData(index, column) {
	isCheckEvaluate = true;
	var _index = column[1];
	{
		var img0 = document.createElement('img');
		if ('2' == fastDev('#cChkStatus').prop('value')) {
			img0.src = image_enable;
		} else {
			img0.src = image_disable;
		}
		document.getElementById('td' + index + column[0] + (_index++ + (index * 2))).appendChild(img0);
	}

	{
		var t = document.createElement('table');
		var b = document.createElement('tbody');
		var tr0 = document.createElement('tr');
		var td00 = document.createElement('td');
		td00.width = '120px';
		td00.height = '16px';
		var tn00 = document.createTextNode('异常验证');
		td00.appendChild(tn00);
		tr0.appendChild(td00);
		b.appendChild(tr0);
		var tr1 = document.createElement('tr');
		var td10 = document.createElement('td');
		td10.width = '120px';
		td10.height = '30px';
		var img0 = document.createElement('img');
		td10.appendChild(img0);
		tr1.appendChild(td10);
		b.appendChild(tr1);
		var tr2 = document.createElement('tr');
		var td20 = document.createElement('td');
		td20.width = '120px';
		td20.height = '16px';
		var tn20 = document.createTextNode(fastDev('#cChkUsername').prop('value'));
		td20.appendChild(tn20);
		tr2.appendChild(td20);
		b.appendChild(tr2);
		var tr3 = document.createElement('tr');
		var td30 = document.createElement('td');
		td30.width = '120px';
		td30.height = '16px';
		tr3.appendChild(td30);
		b.appendChild(tr3);
		if ('2' == fastDev('#cChkStatus').prop('value')) {
			img0.src = image_finished;
			var tn30 = document.createTextNode(fastDev('#cChkTimeString').prop('value'));
			td30.appendChild(tn30);
		} else {
			img0.src = image_unfinished;
			var tn30 = document.createTextNode('—');
			td30.appendChild(tn30);
		}
		t.appendChild(b);
		document.getElementById('td' + index + column[0] + (_index++ + (index * 2))).appendChild(t);
	}

	{
		var img0 = document.createElement('img');
		if ('2' == fastDev('#cEvaluateStatus').prop('value')) {
			img0.src = image_enable;
		} else {
			img0.src = image_disable;
		}
		document.getElementById('td' + index + column[0] + (_index++ + (index * 2))).appendChild(img0);
	}

	{
		var t = document.createElement('table');
		var b = document.createElement('tbody');
		var tr0 = document.createElement('tr');
		var td00 = document.createElement('td');
		td00.width = '120px';
		td00.height = '16px';
		var tn00 = document.createTextNode('异常评价');
		td00.appendChild(tn00);
		tr0.appendChild(td00);
		b.appendChild(tr0);
		var tr1 = document.createElement('tr');
		var td10 = document.createElement('td');
		td10.width = '120px';
		td10.height = '30px';
		var img0 = document.createElement('img');
		td10.appendChild(img0);
		tr1.appendChild(td10);
		b.appendChild(tr1);
		var tr2 = document.createElement('tr');
		var td20 = document.createElement('td');
		td20.width = '120px';
		td20.height = '16px';
		var tn20 = document.createTextNode(fastDev('#cEvaluateUsername').prop('value'));
		td20.appendChild(tn20);
		tr2.appendChild(td20);
		b.appendChild(tr2);
		var tr3 = document.createElement('tr');
		var td30 = document.createElement('td');
		td30.width = '120px';
		td30.height = '16px';
		tr3.appendChild(td30);
		b.appendChild(tr3);
		if ('2' == fastDev('#cEvaluateStatus').prop('value')) {
			img0.src = image_finished;
			var tn30 = document.createTextNode(fastDev('#cEvaluateTimeString').prop('value'));
			td30.appendChild(tn30);
		} else {
			img0.src = image_unfinished;
			var tn30 = document.createTextNode('—');
			td30.appendChild(tn30);
		}
		t.appendChild(b);
		document.getElementById('td' + index + column[0] + (_index++ + (index * 2))).appendChild(t);
	}
}

function setErrorFeedbackDataDetail(num, data) {
	var tErrorAffix = document.getElementById('tErrorAffix');
	var title = ['处置节点', '处置状态', '处置人员', '要求处置时间', '实际处置时间', '处理信息', '附件', '处置及时性'];
	var width = ['100px', '100px', '100px', '150px', '150px', '300px', '100px', '100px'];
	for (var index = 0; index < num; index++) {
		var t = document.createElement('table');
		t.style.margin = '10px 0 0 0';
		var b = document.createElement('tbody');
		t.appendChild(b);
		tErrorAffix.appendChild(t);

		{
			var tr = document.createElement('tr');
			for (var i = 0; i < 8; i++) {
				var td = document.createElement('td');
				td.style.width = width[i];
				td.style.fontWeight = 'bold';
				var tn = document.createTextNode(title[i]);
				td.appendChild(tn);
				tr.appendChild(td);
			}
			b.appendChild(tr);
		}

		if (0 == index) {
			var tr = document.createElement('tr');
			{
				var td = document.createElement('td');
				var tn = document.createTextNode('异常反馈');
				td.appendChild(tn);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var img = document.createElement('img');
				img.src = image_finished;
				td.appendChild(img);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var tn = document.createTextNode(data[index].data[0].cFeedbackerName);
				td.appendChild(tn);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var tn = document.createTextNode('—');
				td.appendChild(tn);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var tn = document.createTextNode(data[index].data[0].cFeedbackTimeString);
				td.appendChild(tn);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var description = getDescription(data[index].data[0].cFeedbacker, data[index].description);
				td.appendChild(description);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var file = getFile(data[index].data[0].cFeedbacker, data[index].file);
				td.appendChild(file);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var tn = document.createTextNode('—');
				td.appendChild(tn);
				tr.appendChild(td);
			}
			b.appendChild(tr);
		}

		for (var i = 0; i < data[index].data.length; i++) {
			var tr = document.createElement('tr');
			{
				var td = document.createElement('td');
				var tn = null;
				if (0 == i) {
					tn = document.createTextNode('异常接收');
				} else {
					tn = document.createTextNode('异常抄送');
				}
				td.appendChild(tn);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var img = document.createElement('img');
				if ('0' == data[index].data[i].cIsreceived) {
					img.src = image_unfinished;
				} else {
					img.src = image_finished;
				}
				td.appendChild(img);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var tn = document.createTextNode(data[index].data[i].cReceiverUsername);
				td.appendChild(tn);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var tn = document.createTextNode(data[index].data[i].cEndTimeString);
				td.appendChild(tn);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var tn = null;
				if ('1' == data[index].data[i].cIsreceived) {
					if (data[index].data[i].cReceiverTimeString) {
						tn = document.createTextNode(data[index].data[i].cReceiverTimeString);
					} else {
						tn = document.createTextNode('—');
					}
				} else {
					tn = document.createTextNode('—');
				}
				td.appendChild(tn);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				td.style.textAlign = 'left';
				var tn = null;
				if ('0' == data[index].data[i].cIsreceived) {
					tn = document.createTextNode('—');
				} else {
					tn = document.createTextNode('已查阅');
				}
				td.appendChild(tn);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var tn = document.createTextNode('—');
				td.appendChild(tn);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var tn = null;
				if ('1' == data[index].data[i].cIsreceived) {
					if (data[index].data[i].cReceiverTimeString) {
						if (data[index].data[i].cReceiverTimeString > data[index].data[i].cEndTimeString) {
							td.style.color = 'red';
							tn = document.createTextNode('逾期');
						} else {
							tn = document.createTextNode('及时');
						}
					} else {
						tn = document.createTextNode('—');
					}
				} else {
					tn = document.createTextNode('—');
				}
				td.appendChild(tn);
				tr.appendChild(td);
			}
			b.appendChild(tr);
		}

		if (('1' == data[index].data[0].cIsreceived) && ('22' == data[index].data[0].cStatus)) {
			var tr = document.createElement('tr');
			{
				var td = document.createElement('td');
				var tn = null;
				if ('1' == data[index].data[0].cFactdealType) {
					tn = document.createTextNode('接收确认');
				} else if ('2' == data[index].data[0].cFactdealType) {
					tn = document.createTextNode('本人处理');
				} else if ('3' == data[index].data[0].cFactdealType) {
					tn = document.createTextNode('安排整改');
				} else if ('4' == data[index].data[0].cFactdealType) {
					tn = document.createTextNode('继续反馈');
				}
				td.appendChild(tn);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var img = document.createElement('img');
				img.src = image_finished;
				td.appendChild(img);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var tn = document.createTextNode(data[index].data[0].cReceiverUsername);
				td.appendChild(tn);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var tn = document.createTextNode(data[index].data[0].cEndTimeString);
				td.appendChild(tn);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var tn = null;
				if (data[index].data[0].cFactdealTimeString) {
					tn = document.createTextNode(data[index].data[0].cFactdealTimeString);
				} else {
					tn = document.createTextNode('—');
				}
				td.appendChild(tn);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var temp = '';
				if ('1' != data[index].data[0].cFactdealType) {
					temp = data[index + 1].description;
				}
				var description = getDescription(data[index].data[0].cReceiverUserid, temp);
				td.appendChild(description);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var temp = '';
				if ('1' != data[index].data[0].cFactdealType) {
					temp = data[index + 1].file;
				}
				var file = getFile(data[index].data[0].cReceiverUserid, temp);
				td.appendChild(file);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var tn = null;
				if (data[index].data[0].cFactdealTimeString) {
					if (data[index].data[0].cFactdealTimeString > data[index].data[0].cEndTimeString) {
						td.style.color = 'red';
						tn = document.createTextNode('逾期');
					} else {
						tn = document.createTextNode('及时');
					}
				} else {
					tn = document.createTextNode('—');
				}
				td.appendChild(tn);
				tr.appendChild(td);
			}
			b.appendChild(tr);
		}

		if (('1' == data[index].data[0].cIsreceived) && ('22' == data[index].data[0].cStatus) && ('3' == data[index].data[0].cFactdealType)) {
			var tr = document.createElement('tr');
			{
				var td = document.createElement('td');
				var tn = document.createTextNode('整改实施');
				td.appendChild(tn);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var img = document.createElement('img');
				if (('1' == data[index + 1].data[0].cIsreceived) && ('22' == data[index + 1].data[0].cStatus)) {
					img.src = image_finished;
				} else {
					img.src = image_unfinished;
				}
				td.appendChild(img);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var tn = document.createTextNode(data[index + 1].data[0].cReceiverUsername);
				td.appendChild(tn);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var tn = document.createTextNode(data[index + 1].data[0].cEndTimeString);
				td.appendChild(tn);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var tn = null;
				if (('1' == data[index + 1].data[0].cIsreceived) && ('22' == data[index + 1].data[0].cStatus)) {
					if (data[index + 1].data[0].cFactdealTimeString) {
						tn = document.createTextNode(data[index + 1].data[0].cFactdealTimeString);
					} else {
						tn = document.createTextNode('—');
					}
				} else {
					tn = document.createTextNode('—');
				}
				td.appendChild(tn);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var temp = '';
				if (data[index + 2]) {
					temp = data[index + 2].description;
				}
				var description = getDescription(data[index + 1].data[0].cReceiverUserid, temp);
				td.appendChild(description);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var temp = '';
				if (data[index + 2]) {
					temp = data[index + 2].file;
				}
				var file = getFile(data[index + 1].data[0].cReceiverUserid, temp);
				td.appendChild(file);
				tr.appendChild(td);
			}

			{
				var td = document.createElement('td');
				var tn = null;
				if (('1' == data[index + 1].data[0].cIsreceived) && ('22' == data[index + 1].data[0].cStatus)) {
					if (data[index + 1].data[0].cFactdealTimeString) {
						if (data[index + 1].data[0].cFactdealTimeString > data[index + 1].data[0].cEndTimeString) {
							td.style.color = 'red';
							tn = document.createTextNode('逾期');
						} else {
							tn = document.createTextNode('及时');
						}
					} else {
						tn = document.createTextNode('—');
					}
				} else {
					tn = document.createTextNode('—');
				}
				td.appendChild(tn);
				tr.appendChild(td);
			}
			b.appendChild(tr);
		}

		if (isCheckEvaluate && ('1' == data[index].data[0].cIsreceived) && ('22' == data[index].data[0].cStatus) && ('4' != data[index].data[0].cFactdealType)) {
			{
				var tr = document.createElement('tr');
				{
					var td = document.createElement('td');
					var tn = document.createTextNode('异常验证');
					td.appendChild(tn);
					tr.appendChild(td);
				}

				{
					var td = document.createElement('td');
					var img = document.createElement('img');
					if ('2' == fastDev('#cChkStatus').prop('value')) {
						img.src = image_finished;
					} else {
						img.src = image_unfinished;
					}
					td.appendChild(img);
					tr.appendChild(td);
				}

				{
					var td = document.createElement('td');
					var tn = document.createTextNode(fastDev('#cChkUsername').prop('value'));
					td.appendChild(tn);
					tr.appendChild(td);
				}

				{
					var td = document.createElement('td');
					var tn = document.createTextNode(fastDev('#cChkPlantimeString').prop('value'));
					td.appendChild(tn);
					tr.appendChild(td);
				}

				{
					var td = document.createElement('td');
					var tn = null;
					if (fastDev('#cChkTimeString').prop('value')) {
						tn = document.createTextNode(fastDev('#cChkTimeString').prop('value'));
					} else {
						tn = document.createTextNode('—');
					}
					td.appendChild(tn);
					tr.appendChild(td);
				}

				{
					var td = document.createElement('td');
					td.style.textAlign = 'left';
					var tn = null;
					if (fastDev('#cChkResult').prop('value')) {
						tn = document.createTextNode(fastDev('#cChkResult').prop('value'));
					} else {
						tn = document.createTextNode('—');
					}
					td.appendChild(tn);
					tr.appendChild(td);
				}

				{
					var td = document.createElement('td');
					var tn = document.createTextNode('—');
					td.appendChild(tn);
					tr.appendChild(td);
				}

				{
					var td = document.createElement('td');
					var tn = null;
					if (fastDev('#cChkTimeString').prop('value')) {
						if (fastDev('#cChkTimeString').prop('value') > fastDev('#cChkPlantimeString').prop('value')) {
							td.style.color = 'red';
							tn = document.createTextNode('逾期');
						} else {
							tn = document.createTextNode('及时');
						}
					} else {
						tn = document.createTextNode('—');
					}
					td.appendChild(tn);
					tr.appendChild(td);
				}
				b.appendChild(tr);
			}

			{
				var tr = document.createElement('tr');
				{
					var td = document.createElement('td');
					var tn = document.createTextNode('异常评价');
					td.appendChild(tn);
					tr.appendChild(td);
				}

				{
					var td = document.createElement('td');
					var img = document.createElement('img');
					if ('2' == fastDev('#cEvaluateStatus').prop('value')) {
						img.src = image_finished;
					} else {
						img.src = image_unfinished;
					}
					td.appendChild(img);
					tr.appendChild(td);
				}

				{
					var td = document.createElement('td');
					var tn = document.createTextNode(fastDev('#cEvaluateUsername').prop('value'));
					td.appendChild(tn);
					tr.appendChild(td);
				}

				{
					var td = document.createElement('td');
					var tn = document.createTextNode(fastDev('#cEvaluatePlantimeString').prop('value'));
					td.appendChild(tn);
					tr.appendChild(td);
				}

				{
					var td = document.createElement('td');
					var tn = null;
					if (fastDev('#cEvaluateTimeString').prop('value')) {
						tn = document.createTextNode(fastDev('#cEvaluateTimeString').prop('value'));
					} else {
						tn = document.createTextNode('—');
					}
					td.appendChild(tn);
					tr.appendChild(td);
				}

				{
					var td = document.createElement('td');
					td.style.textAlign = 'left';
					var tn = null;
					if (fastDev('#cEvaluateResult').prop('value')) {
						tn = document.createTextNode(fastDev('#cEvaluateResult').prop('value'));
					} else {
						tn = document.createTextNode('—');
					}
					td.appendChild(tn);
					tr.appendChild(td);
				}

				{
					var td = document.createElement('td');
					var tn = document.createTextNode('—');
					td.appendChild(tn);
					tr.appendChild(td);
				}

				{
					var td = document.createElement('td');
					var tn = null;
					if (fastDev('#cEvaluateTimeString').prop('value')) {
						if (fastDev('#cEvaluateTimeString').prop('value') > fastDev('#cEvaluatePlantimeString').prop('value')) {
							td.style.color = 'red';
							tn = document.createTextNode('逾期');
						} else {
							tn = document.createTextNode('及时');
						}
					} else {
						tn = document.createTextNode('—');
					}
					td.appendChild(tn);
					tr.appendChild(td);
				}
				b.appendChild(tr);
			}
		}
	}

	loadingWindow.close();
}

function getDescription(id, description) {
	var span = document.createElement('span');
	span.style.cssFloat = 'left';
	span.style.textAlign = 'left';
	var temp = '';
	for (var i = 0; i < description.length; i++) {
		if (description[i].id == id) {
			temp += (description[i].description + '</br>');
		}
	}
	if ('' != temp) {
		temp = temp.substr(0, temp.length - 5);
	}
	if ('' == temp) {
		span.innerHTML = '—';
	} else {
		span.innerHTML = temp;
	}
	return span;
}

function getFile(id, file) {
	var td = null;
	var div = document.createElement('div');
	div.style.width = '100px';
	var sum = 0;
	for (var i = 0; i < file.length; i++) {
		if (file[i].id == id) {
			if (null == td) {
				td = document.createElement('td');
				td.style.border = 'none';
				td.style.textAlign = 'center';
				td.appendChild(div);
			}
			div.appendChild(getElementByPath(file[i].path, file[i].type));
			sum++;
			if (0 == (sum % 3)) {
				div = document.createElement('div');
				div.style.width = '100px';
				td.appendChild(div);
			}
		}
	}
	if (null == td) {
		td = document.createTextNode('—');
	}
	return td;
}