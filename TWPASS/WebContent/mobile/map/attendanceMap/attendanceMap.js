var paddingTop = null;
var paddingLeft = null;
var currentWidth = null;
var currentHeight = null;
var iconWidth = 40;
var iconHeight = 40;
var iconPaddingLeft = 11;
var iconPaddingRight = 24;
var myScroll;
var page0_scroll;
var position=[
		{areaName:'长沙',x1:429,y1:231},
		{areaName:'株洲',x1:449,y1:272},
		{areaName:'湘潭',x1:420,y1:268},
		{areaName:'张家界',x1:184,y1:126},
		{areaName:'常德',x1:303,y1:136},
		{areaName:'岳阳',x1:450,y1:98},
		{areaName:'怀化',x1:133,y1:302},
		{areaName:'益阳',x1:371,y1:180},
		{areaName:'娄底',x1:329,y1:284},
		{areaName:'邵阳',x1:282,y1:337},
		{areaName:'衡阳',x1:396,y1:376},
		{areaName:'永州',x1:296,y1:433},
		{areaName:'郴州',x1:431,y1:502},
];

$(document).ready(function() {
	document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
});

$(document).on('pagebeforeshow', '#page', function() {
	newIScroll();
	setArea0();
});


function newIScroll() {
	
	if (myScroll) {
		page0_scroll.destroy();
		myScroll.destroy();
	}
	page0_scroll=new IScroll('#page0');
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

//通过这个方法来判断是否为pc端，才能给相应的链接
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


function sizeCalculation( img, imgWidth, imgHeight) {
	var width = window.innerWidth, height = window.innerHeight;
	var time = width / height, imgTime = imgWidth / imgHeight;
	paddingTop = paddingLeft = 0;

	var wrapper = document.getElementById('wrapper');

	
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

		}
		img.style.width = currentWidth + 'px';
		img.style.height = currentHeight + 'px';
	} else {
		wrapper.style.width = (width - 30) + 'px';
		wrapper.style.height = (height - 30) + 'px';
		wrapper.style.position = 'absolute';
		wrapper.style.top = '30px';
		wrapper.style.left = '30px';

	}
}


function showLoading() {
	document.getElementById('wrapper').style.display = 'none';
	document.getElementById('page0').style.display = 'none';
	$.mobile.loading('show', {
		text : '数据加载中，请稍等...',
		textVisible : true,
		theme : 'a'
	});
}

function hideLoading(index1) {
	newIScroll();
	document.getElementById('wrapper').style.display = 'block';
	if (index1 != undefined) {
		document.getElementById('page' + index1).style.display = 'block';
	}
	$.mobile.loading('show', {
		text : '',
		textVisible : false,
		theme : 'a'
	});
	$.mobile.loading('hide');
}


function setArea0() {
	showLoading();
	var areaMap0 = document.getElementById('areaMap0');
	var areaImg0 = document.getElementById('areaImg0');
	areaMap0.innerHTML = '';
	areaImg0.innerHTML = '';

	
	var area0 = document.getElementById('area0');
	area0.src = '../../image/HuNan.png?' + Math.random();
	area0.onload = function() {
		var imgWidth = this.naturalWidth, imgHeight = this.naturalHeight;
		
		sizeCalculation( area0, imgWidth, imgHeight);
		/*var data=[
		          {
		        	  cAreaFullname: "长沙",
		        		  cAreaId: "1",
		        		  check: 0,
		        		  error: 1,
		        		  x1: "430",
		        		  x2: "",
		        		  y1: "230",
		        		  y2: "",
		          },{
		        	  cAreaFullname: "湘潭",
	        		  cAreaId: "2",
	        		  check: 1,
	        		  error: 0,
	        		  x1: "420",
	        		  x2: "",
	        		  y1: "268",
	        		  y2: "",
		          }
		          ];
		for (var i = 0; i < data.length; i++) {
			if (0 < data[i].error) {
				areaImg0.appendChild(getIcon( 0, imgWidth, imgHeight,  data[i]));
			
			}
			if (0 < data[i].uncheck) {
				areaImg0.appendChild(getIcon( 1, imgWidth, imgHeight,  data[i]));
			
			}
			if (0 < data[i].check) {
				areaImg0.appendChild(getIcon( 2, imgWidth, imgHeight,  data[i]));
			
			}
			
		}
		hideLoading(0);
		*/
		
		$.ajax({
			type : 'POST',
			url : 'attendance_getAttendanceForMap.action',
			//data : {cAreaId : mainMapId, cObjectTypeid : cObjectTypeid},
			dataType : 'json',
			success : function(data) {
				var mapArea="";
				for (var i = 0; i < data.length; i++) {
					for(var j=0;j<position.length;j++){
						if(data[i].areaName.trim()==position[j].areaName){
							data[i].x1=position[j].x1;
							data[i].y1=position[j].y1;
							mapArea=position[j].areaName;
							break;
						}
					}
					if (0 < data[i].error) {
						areaImg0.appendChild(getIcon( 0, imgWidth, imgHeight,  data[i],mapArea));
					
					}else if (0 < data[i].normal) {
						areaImg0.appendChild(getIcon( 2, imgWidth, imgHeight,  data[i],mapArea));
					
					}
					
				}
				hideLoading(0);
			}
		});
	};
}

//得到图标（三个球）的位置并可点击
function getIcon( index, imgWidth, imgHeight,  data ,mapArea) {
	var icon = ['../../image/red.png', '../../image/blue.png', '../../image/green.png', '../../image/bannerol.png'];
	var iconTop, iconLeft, _iconWidth, _iconHeight, id;
	
			//小图标定位
			iconTop = (Number(data.y1)  - iconHeight) * (currentHeight / imgHeight);
			iconLeft = (Number(data.x1)  - iconPaddingLeft) * (currentWidth / imgWidth);
			//小图标的长高
			_iconWidth = iconWidth * (currentWidth / imgWidth);
			_iconHeight = iconHeight * (currentHeight / imgHeight);
		
		
		
	var img = document.createElement('img');
	img.src = icon[index];
	img.border = 0;
	img.style.cursor = 'pointer';
	img.style.position = 'absolute';
	img.style.top = iconTop + 'px';
	img.style.left = iconLeft + 'px';
	img.style.width = _iconWidth + 'px';
	img.style.height = _iconHeight + 'px';
	img.setAttribute('onclick','showErrorDetail("'+mapArea+'")');
	return img;
}

function showErrorDetail(Area) {
	if (myScroll) {
		myScroll.zoom(1);
		myScroll.destroy();
		page0_scroll.zoom(1);
		page0_scroll.destroy();
	}
	

	document.getElementById('page0').style.display = 'none';
	var wrapper = document.getElementById('wrapper');

	wrapper.style.top = '30px';
	wrapper.style.left = '0px';
	wrapper.style.width = window.innerWidth + 'px';
	wrapper.style.height = window.innerHeight + 'px';
	document.getElementById('attendanceDetail').style.display = 'block';
	document.getElementById('back').setAttribute('onclick', 'backMap()');
	var errorDetail = document.getElementById('errorDetail');
	
	if (IsPC()) {
		errorDetail.src = '../../../xwzcxt/mytask/attendanceCheck.html?map='+Area;
		errorDetail.width = window.innerWidth + 'px';
		errorDetail.height = window.innerHeight + 'px';
	} else {
		errorDetail.src = '../../attendance/attendanceCheck.html?map='+Area;
		errorDetail.width = window.innerWidth + 'px';
		errorDetail.height = (window.innerHeight - 30) + 'px';
	}
}
function backMap(){
	document.getElementById('attendanceDetail').style.display = 'none';
	setArea0();
}
//忽略字符串前后空格
String.prototype.trim=function() {
    return this.replace(/(^\s*)|(\s*$)/g,'');
}