var request = fastDev.Browser.getRequest();
var dg_seq = request['dg_seq'];
var cTaskId = request['cTaskId'];

var image_finished = '../../images/finished.png';
var image_unfinished = '../../images/unfinished.png';
var image_unused = '../../images/unused.png';
var image_enable = '../../images/enable.png';
var image_disable = '../../images/disable.png';

$(document).ready(function() {
	fastDev.post("task_new/getTaskByIDAction.action?cTaskId=" +
		cTaskId, {
		success : function(data) {
			setCStatusTitleTable(data);
			setCStatusDetailTable(data);
		}
	});
});

function setCStatusTitleTable(data) {
	var cStatusTitleTable = document.getElementById('cStatusTitleTable');
	var data1 = ['序\u00A0\u00A0\u00A0\u00A0\u00A0\u00A0号：', '任务名称：', '任务类型：', '任务类别：', '执\u00A0\u00A0行\u00A0人：'];
	var data2 = [dg_seq, data.cTaskName, data.cTaskKindName, data.cTaskTypename, data.cExecUsername];
	for (var i = 0; i < 5; i++) {
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
		cStatusTitleTable.appendChild(tr);
	}
}

function setCStatusDetailTable(data) {
	var cStatusDetailTable = document.getElementById('cStatusDetailTable');
	for (var i = 0; i < 2; i++) {
		var tr = document.createElement('tr');
		for (var j = 0; j < 11; j++) {
			var td = document.createElement('td');
			td.id = 'td' + i + j;
			td.width = '120px';
			td.height = '78px';
			tr.appendChild(td);
		}
		cStatusDetailTable.appendChild(tr);
	}

	{
		var text0 = null;
		var src0 = null;
		var text1 = null;
		if (data.cStatus >= 0) {
			text0 = '已生成';
			src0 = image_finished;
			text1 = data.cCreateTimeString;
		} else {
			text0 = '未生成';
			src0 = image_unfinished;
			text1 = '—';
		}
		document.getElementById('td10').appendChild(getTr(text0, src0, '系统生成', text1));
	}

	{
		var text0 = null;
		var src0 = null;
		var text1 = null;
		var src1 = null;
		if (data.cStatus >= 11) {
			text0 = '已下发';
			src0 = image_finished;
			text1 = data.cCreateTimeString;
			src1 = image_enable;
		} else {
			text0 = '未下发';
			src0 = image_unfinished;
			text1 = '—';
			src1 = image_disable;
		}
		var img0 = document.createElement('img');
		img0.src = src1;
		document.getElementById('td11').appendChild(img0);
		document.getElementById('td12').appendChild(getTr(text0, src0, '系统下发 ', text1));
	}

	{
		var text0 = null;
		var src0 = null;
		var text1 = null;
		var src1 = null;
		if (data.cStatus >= 22) {
			text0 = '已接收';
			src0 = image_finished;
			text1 = data.cDownTimeString;
			src1 = image_enable;
		} else {
			text0 = '未接收';
			src0 = image_unfinished;
			text1 = '—';
			src1 = image_disable;
		}
		var img0 = document.createElement('img');
		img0.src = src1;
		document.getElementById('td13').appendChild(img0);
		document.getElementById('td14').appendChild(getTr(text0, src0, data.cExecUsername, text1));
	}

	{
		var text0 = null;
		var src0 = null;
		var text1 = null;
		var src1 = null;
		if (data.cStatus >= 33) {
			text0 = '已执行';
			src0 = image_finished;
			text1 = data.cFactEndtimeString;
			src1 = image_enable;
		} else {
			text0 = '未执行';
			src0 = image_unfinished;
			text1 = '—';
			src1 = image_disable;
		}
		var img0 = document.createElement('img');
		img0.src = src1;
		document.getElementById('td15').appendChild(img0);
		document.getElementById('td16').appendChild(getTr(text0, src0, data.cExecUsername, text1));
	}

	{
		var text0 = null;
		var src0 = null;
		var name0 = null;
		var text1 = null;
		var src1 = null;
		if (1 == data.cIskeyctrl) {
			name0 = data.cChkUsername;
			if (data.cStatus >= 34) {
				text0 = '已验证';
				src0 = image_finished;
				text1 = data.cChkEndtimeString;
				src1 = image_enable;
			} else {
				text0 = '未验证';
				src0 = image_unfinished;
				text1 = '—';
				src1 = image_disable;
			}

			var img0 = document.createElement('img');
			img0.src = src1;
			document.getElementById('td17').appendChild(img0);
			document.getElementById('td18').appendChild(getTr(text0, src0, name0, text1));
		} else {
			text0 = '无验证';
			src0 = image_unused;
			name0 = '';
			text1 = '';
			document.getElementById('td08').appendChild(getTr(text0, src0, name0, text1));
		}
	}

	{
		var text0 = null;
		var src0 = null;
		var text1 = null;
		var src1 = null;
		if (data.cStatus >= 35) {
			text0 = '已评价';
			src0 = image_finished;
			text1 = data.cEvaluateTimeString;
			src1 = image_enable;
		} else {
			text0 = '未评价';
			src0 = image_unfinished;
			text1 = '—';
			src1 = image_disable;
		}
		var img0 = document.createElement('img');
		img0.src = src1;
		if (1 == data.cIskeyctrl) {
			document.getElementById('td19').appendChild(img0);
			document.getElementById('td110').appendChild(getTr(text0, src0, data.cEvaluateUsername, text1));
		} else {
			document.getElementById('td17').appendChild(img0);
			document.getElementById('td18').appendChild(getTr(text0, src0, data.cEvaluateUsername, text1));
		}
	}
}

function getTr(text0, src0, name0, text1) {
	var t = document.createElement('table');
	var b = document.createElement('tbody');
	var tr0 = document.createElement('tr');
	var td00 = document.createElement('td');
	td00.width = '120px';
	td00.height = '16px';
	var tn00 = document.createTextNode(text0);
	td00.appendChild(tn00);
	tr0.appendChild(td00);
	b.appendChild(tr0);
	var tr1 = document.createElement('tr');
	var td10 = document.createElement('td');
	td10.width = '120px';
	td10.height = '30px';
	var img0 = document.createElement('img');
	img0.src = src0;
	td10.appendChild(img0);
	tr1.appendChild(td10);
	b.appendChild(tr1);
	var tr2 = document.createElement('tr');
	var td20 = document.createElement('td');
	td20.width = '120px';
	td20.height = '16px';
	var tn20 = document.createTextNode(name0);
	td20.appendChild(tn20);
	tr2.appendChild(td20);
	b.appendChild(tr2);
	var tr3 = document.createElement('tr');
	var td30 = document.createElement('td');
	td30.width = '120px';
	td30.height = '16px';
	var tn30 = document.createTextNode(text1);
	td30.appendChild(tn30);
	tr3.appendChild(td30);
	b.appendChild(tr3);
	t.appendChild(b);
	return t;
}