var request = fastDev.Browser.getRequest();
var c_userid = request['c_userid'];
var c_tag_ip = request['c_tag_ip'];
var c_attend_date=request['c_attend_date'];

console.info(c_userid);
console.info(c_tag_ip);
console.info(c_attend_date);
function onBeforeReady() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "checkform",
		text : "加载中..."
	});

	this.setOptions({
		initSource : 'attendance_getAttendanceDetails.action?c_userid='+
		c_userid+'&c_tag_ip='+c_tag_ip+'&c_attend_date='+c_attend_date
	});
}

function timeRenderer(value){
	value=(value+"").replace("T"," ");
	return value;
}
function operLinkKao(value){
	return  [ '<div style="width:200px;"><span style="margin-left:5px; color:#ff7f24">'
	  		+ value + '</span></div>' ].join('');
}