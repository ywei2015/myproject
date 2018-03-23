var type = getUrlParam('type');

var cBasedataId = getUrlParam('cBasedataId');
var cObjectTypeid = getUrlParam('cObjectTypeid');

var parentId = [];
var parentName = [];
var paddingTop = null;
var paddingLeft = null;
var currentWidth = null;
var currentHeight = null;
var iconWidth = 40;
var iconHeight = 40;
var iconPaddingLeft = 11;
var iconPaddingRight = 24;
var cAreaId = null;
var cErrId = null;
var cTaskId = null;
//联合工房区域
var partition0 = new Array();
partition0[1000100003161] = 1;
partition0[1000100003162] = 1;
partition0[1000100003163] = 1;
partition0[1000100003165] = 1;
partition0[1000100003164] = 1;
partition0[1000100003166] = 1;
partition0[1000100003167] = 1;
partition0[1000100003168] = 1;
partition0[1000100003185] = 1;

//只有一层的区域
var oneFloorArea=new Array();
oneFloorArea[1000100000033]=1;//选梗区
oneFloorArea[1000100000027]=1;//验级棚

var mainMap=null;
var mainMapId=null;
var mainMapName=null;
$(document).ready(function() {
//	ChooseMap();
	document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
});
$(document).on('pagebeforeshow', '#page', function() {
	newIScroll();
	setArea0();
});
//function ChooseMap() {
//	showLoading();
//
//	$.each($("#pageMap_ul").children().children(),function(){
//		this.href="#page";
//		this.onclick='onAClick_mainMap('+this+')';
//	});
//	hideLoading();
//}

function onAClick_mainMap(a){
	mainMap=a.id;
}

//获取用户双击图片后的坐标值
function getCoordinate(img) {
	if ('setPosition' == type) {
		if ((cObjectTypeid == '') || (cObjectTypeid == null) || (cObjectTypeid == undefined)) {
			fastDev.alert('请选择作业对象类型!');
			return;
		}
		var area2 = document.getElementById('area2');
		var naturalWidth = area2.naturalWidth;
		var naturalHeight = area2.naturalHeight;
		var startX = Math.abs(myScroll.startX);
		var startY = Math.abs(myScroll.startY);
		var leftP = paddingLeft - startX;
		var topP = paddingTop - startY;

		var x = (((myScroll.pointX - leftP - iconPaddingLeft) / myScroll.scale) / (currentWidth/naturalWidth));
		var y = (((myScroll.pointY - topP - iconPaddingRight) / myScroll.scale) / (currentHeight/naturalHeight)) - iconHeight;

		fastDev.confirm('当前选择的坐标为【X:' + x + ';Y:' + y + '】,是否保存改地理位置？', '信息提示', function(result) {
			if (result) {
				var url = 'jobObjects_addTaskPosition.action';
				fastDev.post(url, {
					success : function(data) {
						fastDev.alert(data);
						var areaImg2 = document.getElementById('areaImg2');
						areaImg2.innerHTML = '';
						var src = img.src;
						img.src = src.substring(0, src.indexOf('?') + 1) + Math.random();
					},
					data : {
						'cBasedataId' : cBasedataId,
						'cAreaId' : cAreaId,
						'x' : x,
						'y' : y
					}
				});
			}
		});
	}
}

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

var myScroll; //IScroll控件句柄
//IScroll5+在IOS、android平台上的点击事件（click）不兼容的解决方法
//navigator.userAgent查看浏览器类型
function iScrollClick() {
	if (/iPhone|iPad|iPod|Macintosh/i.test(navigator.userAgent)) {
		return true;
	}
	if (/Chrome/i.test(navigator.userAgent)) {
		return (/Android/i.test(navigator.userAgent));
	}
	if (/Silk/i.test(navigator.userAgent)) {
		return false;
	}
	if (/Android/i.test(navigator.userAgent)) {
		var s = navigator.userAgent.substr(navigator.userAgent.indexOf('Android') + 8, 3);
		return parseFloat(s[0] + s[3]) < 44 ? false : true;
	}
}
//初始化jquery.mobile（初始化后才能调用jquery.mobile的方法，新版本已不需要）
$(document).bind('mobileinit', function () {} );



function IsPC() {
	var userAgentInfo = navigator.userAgent;
	var Agents = ['Android', 'iPhone', 'SymbianOS', 'Windows Phone', 'iPad', 'iPod'];
	var flag = true;
	for (var v = 0; v < Agents.length; v++) {
		if (userAgentInfo.indexOf(Agents[v]) > 0) {
			flag = false;
			break;
		}
	}
	return flag;
}

function sizeCalculation(name, img, imgWidth, imgHeight) {
	var width = window.innerWidth, height = window.innerHeight;
	var time = width / height, imgTime = imgWidth / imgHeight;
	paddingTop = paddingLeft = 0;

	var wrapper = document.getElementById('wrapper');
	var text = document.getElementById('text');
	text.innerHTML = name;
	if (img) {
		if (time >= imgTime) {
			currentHeight = height - 35;
			currentWidth = imgWidth * (currentHeight / imgHeight);
			paddingLeft = (width - currentWidth) / 2;

			wrapper.style.width = currentWidth + 'px';
			wrapper.style.height = currentHeight + 'px';
			wrapper.style.position = 'absolute';
			wrapper.style.top = '30px';
			wrapper.style.left = paddingLeft + 'px';

			text.style.width = currentWidth - 30 + 'px';
		} else {
			currentHeight = (imgHeight * (width / imgWidth)) - 30;
			currentWidth = imgWidth * (currentHeight / imgHeight);
			paddingTop = (height - currentHeight) / 2;
			if (currentWidth < width) {
				paddingLeft = (width - currentWidth) / 2;
			}
			if (paddingTop < 30) {
				if ((2 * paddingTop) < 30) {
					wrapper.style.width = currentWidth + 'px';
					wrapper.style.height = (currentHeight - (30 - (2 * paddingTop))) + 'px';
					wrapper.style.position = 'absolute';
					wrapper.style.top = '30px';
					wrapper.style.left = paddingLeft + 'px';
				} else {
					wrapper.style.width = currentWidth + 'px';
					wrapper.style.height = currentHeight + 'px';
					wrapper.style.position = 'absolute';
					wrapper.style.top = '30px';
					wrapper.style.left = paddingLeft + 'px';
				}
			} else {
				wrapper.style.width = currentWidth + 'px';
				wrapper.style.height = currentHeight + 'px';
				wrapper.style.position = 'absolute';
				wrapper.style.top = paddingTop + 'px';
				wrapper.style.left = paddingLeft + 'px';
			}

			text.style.width = (currentWidth - 30) + 'px';
		}
		img.style.width = currentWidth + 'px';
		img.style.height = currentHeight + 'px';
	} else {
		wrapper.style.width = (width - 30) + 'px';
		wrapper.style.height = (height - 30) + 'px';
		wrapper.style.position = 'absolute';
		wrapper.style.top = '30px';
		wrapper.style.left = '30px';

		text.style.width = '270px';
	}
}

function showLoading() {
	document.getElementById('wrapper').style.display = 'none';
	document.getElementById('page0').style.display = 'none';
	document.getElementById('page1').style.display = 'none';
	document.getElementById('page2').style.display = 'none';
	document.getElementById('page3').style.display = 'none';
	document.getElementById('partitionPage0').style.display = 'none';
	$.mobile.loading('show', {
		text : '数据加载中，请稍等...',
		textVisible : true,
		theme : 'a'
	});
}

function hideLoading(index1, index2) {
	newIScroll();
	document.getElementById('wrapper').style.display = 'block';
	if (index1 != undefined) {
		document.getElementById('page' + index1).style.display = 'block';
	}
	if (index2 != undefined) {
		document.getElementById('partitionPage' + index2).style.display = 'block';
	}
	$.mobile.loading('hide');
}

var page0_scroll;
var page1_scroll;
var page2_scroll;
var page3_scroll;
var partitionPage0_scroll;
function newIScroll() {
	
	if (myScroll) {
		page0_scroll.destroy();
		myScroll.destroy();
		page1_scroll.destroy();
		page2_scroll.destroy();
		page3_scroll.destroy();
		partitionPage0_scroll.destroy();
	}
	page0_scroll=new IScroll('#page0');
	page1_scroll=new IScroll('#page1');
	page2_scroll=new IScroll('#page2');
	page3_scroll=new IScroll('#page3');
	partitionPage0_scroll=new IScroll('#partitionPage0');

	myScroll = new IScroll('#wrapper', {
		zoom: true,//设为true 就可以缩放
		scrollX: true,
		scrollY: true,
		zoomMax:40,
		freeScroll: true,
		mouseWheel: true,
		wheelAction: 'zoom',
		click: iScrollClick()
	});
}

//得到该区域位置并使之可触发点击事件
function getArea(imgWidth, imgHeight, data, callback) {
	var left1, top1, left2, top2;
	left1 = Number(data.x1) * (currentWidth / imgWidth);
	top1 = Number(data.y1) * (currentHeight / imgHeight);
	left2 = Number(data.x2) * (currentWidth / imgWidth);
	top2 = Number(data.y2) * (currentHeight / imgHeight);
	var a = document.createElement('area');
	a.href = 'javascript:void(0)';
	a.shape = 'rect';
	a.coords = '' + left1 + ',' + top1 + ',' + left2 + ',' + top2;
	a.setAttribute('onclick', callback + '(' + data.cAreaId + ",'" + data.cAreaFullname + "');");
	return a;
}
//得到图标（三个球）的位置并可点击
function getIcon(type, index, imgWidth, imgHeight, sum, data, callback) {
	var icon = ['../image/red.png', '../image/blue.png', '../image/green.png', '../image/bannerol.png'];
	var iconTop, iconLeft, _iconWidth, _iconHeight, id;
	switch (type) {
		case 0:
			switch (sum) {
				case 0:
					iconTop = (Number(data.y1) + ((Number(data.y2) - Number(data.y1)) / 2) - iconHeight) * (currentHeight / imgHeight);
					iconLeft = (Number(data.x1)  - iconPaddingLeft) * (currentWidth / imgWidth);
					break;
				case 1:
					iconTop = (Number(data.y1) + ((Number(data.y2) - Number(data.y1)) / 2) - iconHeight) * (currentHeight / imgHeight);
					iconLeft = (Number(data.x2) - iconWidth + iconPaddingRight) * (currentWidth / imgWidth);
					break;
				case 2:
					iconTop = (Number(data.y2) - iconHeight) * (currentHeight / imgHeight);
					iconLeft = (Number(data.x1) + ((Number(data.x2) - Number(data.x1)) / 2) - iconPaddingLeft) * (currentWidth / imgWidth);
					break;
				default:
					iconTop = iconLeft = 0;
					break;
			}
			_iconWidth = iconWidth * (currentWidth / imgWidth);
			_iconHeight = iconHeight * (currentHeight / imgHeight);
			id = data.cAreaId;
			break;
		case 1:
			iconTop = (Number(data.y1) + ((Number(data.y2) - Number(data.y1)) / 2) - iconHeight) * (currentHeight / imgHeight);
			iconLeft = (Number(data.x2) - 1) * (currentWidth / imgWidth);
			_iconWidth = iconWidth * (currentWidth / imgWidth);
			_iconHeight = iconHeight * (currentHeight / imgHeight);
			iconLeft -= ((sum + 1) * _iconWidth);
			id = data.cAreaId;
			break;
		case 2:
			var indexs = data.C_OBJECT_LOCATION.split(',');
			iconTop = Number(indexs[1]) * (currentHeight / imgHeight);
			iconLeft = Number(indexs[0]) * (currentWidth / imgWidth);
			_iconWidth = iconWidth * (currentWidth / imgWidth);
			_iconHeight = iconHeight * (currentHeight / imgHeight);
			id = data.C_ID;
			break;
		default:
			iconTop = iconLeft = _iconWidth = _iconHeight = 0;
			id = null;
			break;
	}
	var img = document.createElement('img');
	img.src = icon[index];
	img.border = 0;
	img.style.cursor = 'pointer';
	img.style.position = 'absolute';
	img.style.top = iconTop + 'px';
	img.style.left = iconLeft + 'px';
	img.style.width = _iconWidth + 'px';
	img.style.height = _iconHeight + 'px';
	img.setAttribute('onclick', callback + '(' + id + ",'" + data.cAreaFullname + "');");
	return img;
}
//判断主地图
function decideMap(){
	if('csjyc'==mainMap){
		mainMapId='1000100000001';
		mainMapName='长沙卷烟厂';
	}else  if('dtck'==mainMap){
		mainMapId='1000100000008';
		mainMapName='大托仓库';
	}else{
		
	}
	
}
//第一层
function setArea0() {
	showLoading();
	decideMap();
	parentId = [];
	parentName = [];
	
if(document.getElementById('back').parentNode.href==""){
	setTimeout(function(){
		document.getElementById('back').parentNode.setAttribute('href','#pageMap');
	}, 500);
}

	var areaMap0 = document.getElementById('areaMap0');
	var areaImg0 = document.getElementById('areaImg0');
	areaMap0.innerHTML = '';
	areaImg0.innerHTML = '';

	
	var area0 = document.getElementById('area0');
	area0.src = '../image/'+mainMapId+'.png?' + Math.random();
	area0.onload = function() {
		var imgWidth = this.naturalWidth, imgHeight = this.naturalHeight;
		
		sizeCalculation(mainMapName, area0, imgWidth, imgHeight);
		$.ajax({
			type : 'POST',
			url : 'map/getAreaInfoAction.action',
			data : {cAreaId : mainMapId, cObjectTypeid : cObjectTypeid},
			dataType : 'json',
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					
				//	var callback = 'setArea1';
//					if (partition0[data[i].cAreaId] == 1) {
//						callback = 'setPartition0';
//					} else {
//						callback = 'setArea1';
//					}
				
					areaMap0.appendChild(getArea(imgWidth, imgHeight, data[i], 'setArea1'));
					var sum = 0;
					if (0 < data[i].error) {
						areaImg0.appendChild(getIcon(0, 0, imgWidth, imgHeight, sum, data[i], 'setArea1'));
						sum++;
					}
					if (0 < data[i].uncheck) {
						areaImg0.appendChild(getIcon(0, 1, imgWidth, imgHeight, sum, data[i], 'setArea1'));
						sum++;
					}
					if (0 < data[i].check) {
						areaImg0.appendChild(getIcon(0, 2, imgWidth, imgHeight, sum, data[i], 'setArea1'));
						sum++;
					}
					if ((0 == sum) && (0 < data[i].sum)) {
						areaImg0.appendChild(getIcon(0, 3, imgWidth, imgHeight, sum, data[i], 'setArea1'));
					}
				}
				hideLoading(0);
			}
		});
	};
}

function setPartition0(id, name) {
	showLoading();
	if (!id) {
		parentId.pop();
		id = parentId[parentId.length - 1];
	} else {
		parentId.push(id);
	}
	if (!name) {
		parentName.pop();
		name = parentName[parentName.length - 1];
	} else {
		parentName.push(name);
	}
	document.getElementById('back').setAttribute('onclick', 'setArea1();');
	document.getElementById('back').parentNode.removeAttribute('href');
	var partitionAreaMap0 = document.getElementById('partitionAreaMap0');
	var partitionAreaImg0 = document.getElementById('partitionAreaImg0');
	partitionAreaMap0.innerHTML = '';
	partitionAreaImg0.innerHTML = '';
	var partitionArea0 = document.getElementById('partitionArea0');

	partitionArea0.src = '../image/' + id + '.png?' + Math.random();
	partitionArea0.onload = function() {
		var imgWidth = this.naturalWidth, imgHeight = this.naturalHeight;
		sizeCalculation(name, partitionArea0, imgWidth, imgHeight);
		$.ajax({
			type : 'POST',
			url : 'map/getAreaInfoAction.action',
			data : {cAreaId : id, cObjectTypeid : cObjectTypeid},
			dataType : 'json',
			success : function(data) {
				
				for (var i = 0; i < data.length; i++) {
					partitionAreaMap0.appendChild(getArea(imgWidth, imgHeight, data[i], 'setArea2'));
					var sum = 0;
					if (0 < data[i].check) {
						partitionAreaImg0.appendChild(getIcon(1, 2, imgWidth, imgHeight, sum, data[i], 'setArea2'));
						sum++;
					}
					if (0 < data[i].uncheck) {
						partitionAreaImg0.appendChild(getIcon(1, 1, imgWidth, imgHeight, sum, data[i], 'setArea2'));
						sum++;
					}
					if (0 < data[i].error) {
						partitionAreaImg0.appendChild(getIcon(1, 0, imgWidth, imgHeight, sum, data[i], 'setArea2'));
						sum++;
					}
				}
				hideLoading(null, 0);
			}
		});
	};
}
//设置第二层楼层图
function setArea1(id, name) {
	
//	oneFloorArea[1000100000033]=1;//选梗区
//	oneFloorArea[1000100000027]=1;//验级棚
	if(oneFloorArea[id]==1){
		setArea2(id,name);
	}else{
		showLoading();
		if (!id) {
			parentId.pop();
			id = parentId[parentId.length - 1];
		} else {
			parentId.push(id);
		}
		if (!name) {
			parentName.pop();
			name = parentName[parentName.length - 1];
		} else {
			parentName.push(name);
		}
		document.getElementById('back').parentNode.removeAttribute('href');
		document.getElementById('back').setAttribute('onclick', 'setArea0();');
	
	//	if (parentId.length == 1) {
	//		document.getElementById('back').setAttribute('onclick', 'setArea0();');
	//	} else if (parentId.length == 2) {
	//		document.getElementById('back').setAttribute('onclick', 'setPartition0();');
	//	}
	
		var areaMap1 = document.getElementById('areaMap1');
		var areaImg1 = document.getElementById('areaImg1');
		areaMap1.innerHTML = '';
		areaImg1.innerHTML = '';
		var area1 = document.getElementById('area1');
		area1.src = '../image/' + id + '.png?' + Math.random();
		area1.onload = function() {
			var imgWidth = this.naturalWidth, imgHeight = this.naturalHeight;
			sizeCalculation(name, area1, imgWidth, imgHeight);
		
			$.ajax({
				type : 'POST',
				url : 'map/getAreaInfoAction.action',
				data : {cAreaId : id, cObjectTypeid : cObjectTypeid},
				
				dataType : 'json',
				success : function(data) {
				
					for (var i = 0; i < data.length; i++) {
						var callback=null;
						if (partition0[data[i].cAreaId] == 1) {
							callback = 'setPartition0';
						} else {
							callback = 'setArea2';
						}
						//alert(data[i].cAreaId);
						areaMap1.appendChild(getArea(imgWidth, imgHeight, data[i], callback));
					
						var sum = 0;
						if (0 < data[i].check) {
							areaImg1.appendChild(getIcon(1, 2, imgWidth, imgHeight, sum, data[i], callback));
							sum++;
						}
						if (0 < data[i].uncheck) {
							areaImg1.appendChild(getIcon(1, 1, imgWidth, imgHeight, sum, data[i], callback));
							sum++;
						}
						if (0 < data[i].error) {
							areaImg1.appendChild(getIcon(1, 0, imgWidth, imgHeight, sum, data[i], callback));
							sum++;
						}
					}
					hideLoading(1);
				}
			});
		};
	}
}
//第三层
function setArea2(id, name) {
	showLoading(); 
	cAreaId = id;
	if (!id) {
		parentId.pop();
		id = parentId[parentId.length - 1];
	} else {
		parentId.push(id);
	}
	if (!name) {
		parentName.pop();
		name = parentName[parentName.length - 1];
	} else {
		parentName.push(name);
	}
	document.getElementById('back').parentNode.removeAttribute('href');
	//如果为只有1楼 没有楼层图，则setArea0
	if(oneFloorArea[id]==1){
		document.getElementById('back').setAttribute('onclick', 'setArea0();');
	}else{
		if (parentId.length == 2) {
			document.getElementById('back').setAttribute('onclick', 'setArea1();');
		} else if (parentId.length == 3) {
			document.getElementById('back').setAttribute('onclick', 'setPartition0();');
		} 
	}
	

	var areaImg2 = document.getElementById('areaImg2');
	areaImg2.innerHTML = '';

	var area2 = document.getElementById('area2');
	area2.src = '../image/' + id + '.png?' + Math.random();
	area2.onload = function() {
		var imgWidth = this.naturalWidth, imgHeight = this.naturalHeight;
		sizeCalculation(name, area2, imgWidth, imgHeight);
		$.ajax({
			type : 'POST',
			url : 'map/getAreaIconAction.action',
			data : {cAreaId : id, cObjectTypeid : cObjectTypeid},
			dataType : 'json',
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					var type = null;
					if (1 == data[i].C_ISERROR) {
						type = 0;
					} else if (0 == data[i].C_CHECKSTATUS) {
						type = 1;
					} else if (1 == data[i].C_CHECKSTATUS) {
						type = 2;
					} else {
						type = 3;
					}
					areaImg2.appendChild(getIcon(2, type, imgWidth, imgHeight, 0, data[i], 'setArea3'));
				}
				hideLoading(2);
			}
		});
	};
}
//第四层
function setArea3(id, name) {
	showLoading();
	if (!id) {
		parentId.pop();
		id = parentId[parentId.length - 1];
	} else {
		parentId.push(id);
	}
	if (!name) {
		parentName.pop();
		name = parentName[parentName.length - 1];
	} else {
		parentName.push(name);
	}
	document.getElementById('back').setAttribute('onclick', 'setArea2();');
	document.getElementById('back').parentNode.removeAttribute('href');
	var detail = document.getElementById('detail');
	detail.innerHTML = '';
	detail.style.display = 'block';
	document.getElementById('errorDetail').style.display = 'none';
	sizeCalculation('详情', null, null, null);

	$.ajax({
		type : 'POST',
		url : 'map/getAreaIconDetailByIdAction.action',
		data : {cId : id, cObjectTypeid : cObjectTypeid},
		dataType : 'json',
		success : function(data) {
			var checkTime = null, status1 = null, status2 = null, reason = null;
			if (1 == data.C_ISERROR) {
				checkTime = data.C_CHECKTIME;
				if (data.C_CHECKTIME > data.C_PLANCHECKTIME) {
					status1 = '逾期';
				} else {
					status1 = '及时';
				}
				status2 = '异常';
				reason = data.C_ERRORREASON;
			} else if (0 == data.C_CHECKSTATUS) {
				checkTime = '—';
				status1 = '—';
				status2 = '未检查';
				reason = '—';
			} else {
				checkTime = data.C_CHECKTIME;
				if (data.C_CHECKTIME > data.C_PLANCHECKTIME) {
					status1 = '逾期';
				} else {
					status1 = '及时';
				}
				status2 = '已检查';
				reason = '—';
			}

			var b = document.createElement('tbody');

			{
				var r = document.createElement('tr');
				{
					var c = document.createElement('td');
					var m = document.createTextNode('管理责任人：' + data.C_EXEC_USERNAME);
					c.appendChild(m);
					r.appendChild(c);
				}
				b.appendChild(r);
			}

			{
				var r = document.createElement('tr');
				{
					var c = document.createElement('td');
					var m = document.createTextNode('作业对象：' + data.C_OBJECT_TYPENAME);
					c.appendChild(m);
					r.appendChild(c);
				}
				b.appendChild(r);
			}

			{
				var r = document.createElement('tr');
				{
					var c = document.createElement('td');
					var m = document.createTextNode('作业区域：' + data.C_AREA_FULLNAME);
					c.appendChild(m);
					r.appendChild(c);
				}
				b.appendChild(r);
			}

			{
				var r = document.createElement('tr');
				{
					var c = document.createElement('td');
					var m = document.createTextNode('检查期限：' + data.C_PLANCHECKTIME);
					c.appendChild(m);
					r.appendChild(c);
				}
				b.appendChild(r);
			}

			{
				var r = document.createElement('tr');
				{
					var c = document.createElement('td');
					var m = document.createTextNode('检查时间：' + checkTime);
					c.appendChild(m);
					r.appendChild(c);
				}
				b.appendChild(r);
			}

			{
				var r = document.createElement('tr');
				{
					var c = document.createElement('td');
					var m = document.createTextNode('检查及时性：' + status1);
					c.appendChild(m);
					r.appendChild(c);
				}
				b.appendChild(r);
			}

			{
				var r = document.createElement('tr');
				{
					var c = document.createElement('td');
					var m = document.createTextNode('检查状态：' + status2);
					c.appendChild(m);
					r.appendChild(c);
				}
				b.appendChild(r);
			}

			{
				var r = document.createElement('tr');
				{
					var c = document.createElement('td');
					var m = document.createTextNode('异常原因：' + reason);
					c.appendChild(m);
					r.appendChild(c);
				}
				b.appendChild(r);
			}

			if (1 == data.C_ISERROR) {
				var r = document.createElement('tr');
				{
					var c = document.createElement('td');
					var m = document.createTextNode('整改情况：');
					c.appendChild(m);
					var a = document.createElement('a');
					a.innerHTML = '详情';
					a.href = 'javascript:void(0)';
					a.setAttribute('onclick', 'showErrorDetail()');
					cErrId = data.C_ERR_ID;
					cTaskId = data.C_TASK_ID;
					c.appendChild(a);
					r.appendChild(c);
				}
				b.appendChild(r);
			} else {
				var r = document.createElement('tr');
				{
					var c = document.createElement('td');
					var m = document.createTextNode('整改情况：—');
					c.appendChild(m);
					r.appendChild(c);
				}
				b.appendChild(r);
			}

			detail.appendChild(b);

			hideLoading(3);
		}
	});
}

function showErrorDetail() {
	if (myScroll) {
		myScroll.zoom(1);
		myScroll.destroy();
		page0_scroll.zoom(1);
		page0_scroll.destroy();
		page1_scroll.zoom(1);
		page1_scroll.destroy();
		page2_scroll.zoom(1);
		page2_scroll.destroy();
		page3_scroll.zoom(1);
		page3_scroll.destroy();
		partitionPage0_scroll.zoom(1);
		partitionPage0_scroll.destroy();
	}
	parentId.push('');
	parentName.push('');
	document.getElementById('back').setAttribute('onclick', 'setArea3();');

	var wrapper = document.getElementById('wrapper');
	var text = document.getElementById('text');
	wrapper.style.top = '0px';
	wrapper.style.left = '0px';
	wrapper.style.width = window.innerWidth + 'px';
	wrapper.style.height = window.innerHeight + 'px';
	text.style.width = (window.innerWidth - 30) + 'px';

	var errorDetail = document.getElementById('errorDetail');
	document.getElementById('detail').style.display = 'none';
	errorDetail.style.display = 'block';
	if (IsPC()) {
		errorDetail.src = '../../xwzcxt/taskerror/taskErrorDetail.html?cErrId=' + cErrId + '&cTaskId=' + cTaskId + '&dg_seq=1';
		errorDetail.width = window.innerWidth + 'px';
		errorDetail.height = window.innerHeight + 'px';
	} else {
		errorDetail.src = '../errinfo/errdetail.html?errId=' + cErrId;
		errorDetail.width = window.innerWidth + 'px';
		errorDetail.height = (window.innerHeight - 30) + 'px';
	}
}