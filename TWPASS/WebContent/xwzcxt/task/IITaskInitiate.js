var request = fastDev.Browser.getRequest();
var taskId = request['taskId'];
var taskName = request['taskName'];
var postId  = request['postId'];
var areaId  = request['areaId'];

fastDev(function(){
	fastDev("[id='taskMouldPojo.c_tasktemplet_id']").prop("value", taskId);
	fastDev("[id='taskMouldPojo.c_tasktemplet_name']").prop("value", taskName);
});
function onAfterInitRender1() {
	fastDev.getInstance("taskMouldPojo.c_positionid").setValue(postId);
}
function onAfterInitRender2() {
	fastDev.getInstance("taskMouldPojo.c_arer").setValue(areaId);
}

function doSubmit(){
	fastDev.confirm("确认是否下发？", "信息提示", function(result){
		if(result){
			var form = fastDev.getInstance('formInfo');
			form.setOptions({
				action : "edit_editData.action?action=2"
			});	 
			form.submit();
		}
	});	
}
//表单提交后回调
function onSubmitSuccess(result) {
	fastDev.alert(result.msg, '信息提示', result.status, function() {
		if (result.status == 'ok') {
			parent.closeDialog();
		}
	});
}

function FormAfter() {
	onChange(postId);
	
	var now = new Date().Format("yyyy-MM-dd");
	//console.log(now);
	fastDev.getInstance("taskMouldPojo.c_end_time").setValue(now+" 17:00:00");
	fastDev.getInstance("taskMouldPojo.c_urge_time").setValue(now+" 16:30:00");
	fastDev.getInstance("taskMouldPojo.c_exceed_time").setValue(now+" 17:30:00");
}

function onChange(value) {
	var userSelect = fastDev.getInstance("taskMouldPojo.c_exec_userid");
	userSelect.clean(true);
	userSelect.initRefresh({"urlParam" : {"postid" : value}});
}


//对Date的扩展，将 Date 转化为指定格式的String   
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
//例子：   
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
Date.prototype.Format = function(fmt)   
{ //author: meizz   
var o = {   
 "M+" : this.getMonth()+1,                 //月份   
 "d+" : this.getDate(),                    //日   
 "h+" : this.getHours(),                   //小时   
 "m+" : this.getMinutes(),                 //分   
 "s+" : this.getSeconds(),                 //秒   
 "q+" : Math.floor((this.getMonth()+3)/3), //季度   
 "S"  : this.getMilliseconds()             //毫秒   
};   
if(/(y+)/.test(fmt))   
 fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
for(var k in o)   
 if(new RegExp("("+ k +")").test(fmt))   
fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
return fmt;   
} 