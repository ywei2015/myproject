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

/*function switchType(id) {
	type = id;
	index = 1;
	setContent();
}*/

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
	//var name = $('#search').val();
	var page = $('#pageSelect').val();
	var c_date=$("#meeting").val();
	url = 'attendance_getAttenForMobile.action';
	text = "暂无"+$("#meeting").val()+"考勤记录";
	$.ajax({
		type : 'POST',
		url : url,
		data : {userId : userId, page : page, index : index,c_date:c_date},
		dataType : 'json',
		success : function(data) {
			var _data = data.data, _info = data.info;
			total = _info.total;
			var index = '<font style="font-family:verdana;font-size:80%;">'+_info.page + '/' + _info.total + ' 共' + _info.records + '条'+'</font>';
			$('#index').html(index);
			if (_data.length > 0) {
				var html = '<ul id="ol" data-role="listview" data-inset="true"></ul>';
				$('#content').html(html);
				var username;
				var ip_yc;
				var data_time;
				var c_tag_ip;
					for (var i = 0; i < _data.length; i++) {
						var kao_color="green";
						 data_time=_data[i].c_attend_time.replace("T"," ");
						 ip_yc=_data[i].c_attend_good;
						 var ip_ycjj='<font style="font-family:verdana;font-size:80%;color:red">'+ip_yc+'</font>';
						var ip_ycj=ip_yc.trim().length;
						username=_data[i].c_username;
						c_tag_ip=_data[i].c_tag_ip;
						if(username==undefined) 
							username="行为系统";
						if(ip_ycj>0) 
							 kao_color="red";
						if(c_tag_ip==undefined) 
							c_tag_ip="";
						var page_index=i+(_info.page-1)*page+1;
						var _html = '<li onclick="showKQ(xiangQ_'+i+' )" style="width:100%"><font id="my_kaoqin" style="font-family:verdana;font-size:80%;color:'+kao_color+'">'+'第'+page_index+'次打卡时间：' + '&nbsp '+data_time +
						'</font><div id="xiangQ_'+i+'"style="display:none"><font style="font-family:verdana;font-size:80%;">'+username+'</br>考勤IP：'+c_tag_ip+
						'</br>考勤异常：'+ip_ycjj+'</font></div></li>';
						$('#ol').append(_html);
						
					}
			} else {
				$('#content').html(text);
			}
			$('#content').trigger('create');
			hideLoading();
		}
	});
}
function showKQ(xiangq_id){
	var xiangQ_div=xiangq_id;
	var xiangQ_divj=$(xiangQ_div);
	if(xiangQ_divj.css("display")=="none")
	xiangQ_divj.show();
	else xiangQ_divj.hide();
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
function showDateP(){
	var now = new Date();
	 var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate(); 
    if(day.toString().length<2) day="0"+day;
	var mytime=year+'-'+month+'-'+day;
	
	if($("#my_date").html()=="更多"){
		$("#meeting").val(mytime);
		$("#kaoqin_pick").show();
		$("#my_date").html("查询");
	}else if($("#my_date").html()=="查询"){
		$("#kaoqin_pick").hide();
		$("#my_date").html("更多");
		setContent();
	}
	
}