function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
 
//var taskId = getQueryString("taskid"); //645 ;  
var task_handle_des = "";
var task_std_version = 0;
var task_std_id = "0";
var task_kind = "0";
var task_status_list = null;
var task_type_list = null;

function kget(key){  
    console.log(key);  
    return window.localStorage.getItem(key);  
}  
  
function kremove(key){  
    window.localStorage.removeItem(key);  
}  
  
function kclear(){  
    window.localStorage.clear();  
} 

var taskId=kget("taskid");
var userId=kget("userId");
kclear();

var isopen = false; 
var newImg; 
var w = 130; //将图片宽度+200 
var h = 100; // 将图片高度 +200 
function big(obj){
	  //$(this).bind("click", function(){ 
		  newImg = obj; 
		  if (!isopen) 
		  { 
			  isopen = true; 
			  var w2=$(obj).width() + w;
			  var h2=$(obj).height() + h;
			  $(obj).width(w2); 
			  $(obj).height(h2);
			  moveImg(0, -8); 
		  } 
		  else 
		  { 
			  isopen = false;
			  var w2=$(obj).width() - w;
			  var h2=$(obj).height() - h;
			  console.info(w2+"|"+h2);
			  $(obj).width(w2); 
			  $(obj).height(h2); 
			  moveImg(0, 8); 
		  } 

		  //}); 
}
i = 0; 
function moveImg(left,top) 
{ 
		var offset = $(newImg).offset(); 
		$(newImg).offset({ top: offset.top + top, left: offset.left + left}); 
		if (i == 10) 
		{ 
		i =0; 
		return; 
		} 
		setTimeout("moveImg("+left+","+top+")", 10); 
		i++; 
} 


$.mobile.page.prototype.options.domCache = false;

//alert(window.innerWidth+' | '+window.innerHeight);
$.ajax({
	  url:'task_getTaskStatusList.action', 
	  dataType:'json',
	  success:function(data){ 
		  //console.info(data); 
		  if(data==undefined||data.length==0){ return; } 
		  task_status_list = data;
	  }
	});  
$.ajax({
	  url:'task_getTaskTypeList.action', 
	  dataType:'json',
	  success:function(data){ 
		  //console.info(data); 
		  if(data==undefined||data.length==0){ return; } 
		  task_type_list = data;
	  }
	});   
var getTaskStatusName = function(s){
	for(var i=0;i<task_status_list.length;i++){
		if(task_status_list[i].value==s)
			return task_status_list[i].text;
	}
	return "N/A";
}; 
var getTaskTypeName = function(s){
	for(var i=0;i<task_type_list.length;i++){
		if(task_type_list[i].value==s)
			return task_type_list[i].text;
	}
	return "N/A";
}; 


//$.mobile.changePage("#FeedBackRecordPage"); 
function str2dt(val){
	if(val==undefined) return "";
	var len = 19;
	if(val.length<19) len = val.length;
	return val.substring(0, len).replace("T"," "); 
}

function goTop(){
	$('html,body').animate({scrollTop:0},300);
} 
$(document).ready(function(){ 
//$(document).on("pageinit", function(){
	  onShowTaskInfo();
	  OnShowTaskResultInfo();
	  OnShowTaskStdInfo();
	} 
); 

 var onShowTaskInfo = function(){
	$.ajax({
		  url:'taskEntry_queryTaskEntry.action?action=2&taskId='+taskId, 
		  dataType:'json',
		  success:function(data){ 
			  console.info(data);
			  //alert(str2dt(data["taskMouldPojo.c_start_time"]));
			  if(data==undefined||data.length==0){ return; }
			  $("#task_name").text(data["taskMouldPojo.c_task_name"]);
			  $("#task_id").text(data["taskMouldPojo.c_task_id"]);
			  task_kind = data["taskMouldPojo.c_task_kind"];
			  $("#task_type").text( getTaskTypeName(data["taskMouldPojo.c_task_type"]) );
			  $("#task_section").text(data["taskMouldPojo.c_manage_section_name"]);
			  $("#task_area").text(data["taskMouldPojo.areaname"]);
			  $("#task_executor").text(data["taskMouldPojo.c_exec_username"]);
			  $("#task_chkMan").text(data["taskMouldPojo.c_chk_username"]);
			  $("#task_starttime").text( str2dt( data["taskMouldPojo.c_start_time"]) );
			  $("#task_endtime").text( str2dt( data["taskMouldPojo.c_end_time"]) );
			  task_std_id = (data["taskMouldPojo.c_actnode_id"]==undefined?"0":data["taskMouldPojo.c_actnode_id"]);
			  task_std_version = (data["taskMouldPojo.c_std_verflag"]==undefined?"0":data["taskMouldPojo.c_std_verflag"]);
			  OnShowTaskStdInfo();
		  } 
	  }); 
}; 

var OnShowTaskResultInfo = function(){
	$.ajax({
		  url:'taskEntry_queryEntryList.action?action=1&taskMouldPojo.c_task_id='+taskId, 
		  dataType:'json',
		  success:function(data){ 
			  if(data==undefined||data.length==0){ return; }   
			  var innerHTml = ""; 
				for(var i=0;i<data.length;i++){
					innerHTml +='<div width="100%" height="100%">';
					innerHTml +='<table class="ui-form-table" width="100%" height="100%" ><tr valign="middle"><td >步骤【'+(i+1)+'】</td></tr>';
					innerHTml +='<tr valign="middle">';
					innerHTml +='<td style="text-indent:2em;" >'+(data[i].c_step_prompt==undefined?"收集结果---"+(i+1):data[i].c_step_prompt);
					innerHTml += '【'+(data[i].c_tracefun_name==undefined?"无":data[i].c_tracefun_name)+'】</td>';
					innerHTml +='</tr>';
					innerHTml +='<tr valign="middle"><td>';
					var result_html = "";
					if(data[i].c_result==undefined){
						result_html ='*(未收集)';
					}
					else
					{ 
						if(data[i].c_tracefunid == "1"){ 
							result_html ='<a href="#" rel="'+data[i].c_file_path+'" target="_self" class="imglinkpreview"><img onclick="big(this)" height=80px width=140px src="'+data[i].c_file_path+'" title="点我放大" alt="点我放大"/></a>';					
						}
						else if(data[i].c_tracefunid == "2"){					
							result_html ='<br><audio src="'+data[i].c_file_path+'" style="width:280;"  controls="controls"></audio>';
						}else if(data[i].c_tracefunid == "3"){
							result_html='<video src="'+data[i].c_file_path+'" width="280" height="240" controls="controls"></video>';
						}else if(data[i].c_tracefunid=="20"){
							var cBasedataId=(data[i].c_result).substring(0,13);
							//alert(cBasedataId);
							var IntcBasedataId = Number(cBasedataId);
							if (!isNaN(IntcBasedataId))
							{
								result_html='<a href="#popupCloseRight" class="scanCode" onclick="getScanDetails('+cBasedataId+')"  data-rel="popup" data-role="button" data-inline="true" style="text-decoration:underline;color:blue;">查看二维码详情</a>';	
							}else{
								result_html=data[i].c_result;
							}
								
						}else{
							result_html = data[i].c_result;		
						}
					}
					innerHTml += result_html;
					innerHTml +='</td></tr></table></div><hr>';
				}
			  document.getElementById('DivTaskResultStep').innerHTML = innerHTml;	
		  }
	  });  
};
function verifySubmit(){
	  var eResult = $("[name='radio-choice-1']").filter(":checked").val(); 
	  var evalResult="";
	  var actionUrl="taskVeriEva_updateEvalResult.action";
	  var htmlUrl="taskEvaluate.html?userId="+userId;
	  //alert(taskId);
	  //alert(evalResult);
	  if(eResult=='choice-1'){
		  evalResult='OK';
		  $.post(actionUrl,{'c_task_id':taskId,'c_evaluate_result':evalResult},function(data,status){
			   if(status=='success'){			   
				   $("#a").text(data);
				   $("#ok").unbind("click").bind('click',function(){
					    window.location.href=htmlUrl;
				   });
			   }
		  });
	  }else if((eResult=='choice-2')){
		  evalResult='NG';
		  $.post(actionUrl,{'c_task_id':taskId,'c_evaluate_result':evalResult},function(data,status){
			   if(status=='success'){			   
				   $("#a").text("请到行为系统发起事件,手机端评价结束");
				   $("#ok").unbind("click").bind('click',function(){
					    window.location.href=htmlUrl;
				   });
			   }
		  });
	  }else{
		  $("#a").text('请填写验证结果再提交');
		   $("#ok").unbind("click").bind('click',function(){
			   $("#ok").attr("data-rel","back"); 
		   });
	  }
	  		  
}


var OnShowTaskStdInfo = function(){
	$.ajax({
		  url:'actNode_getCommStdInfoById.action?actnodeid='+task_std_id+'&version='+task_std_version, 
		  dataType:'json',
		  success:function(data){ 
			  //console.info(data);
			  if(data==undefined||data.length==0){ return; }  
				var tmpstr = data["StdInfo.c_dowhat"]+"";  
				var innerHTml = "";				
				innerHTml +='<table class="ui-form-table">';
				innerHTml +='<tr><td class="table_header">做什么</td></tr>';
				innerHTml +='<tr><td style="text-indent:2em;">'+tmpstr+'</td></tr>';
				var tmpstr = (data["StdInfo.c_exec_std"]==null?"无":data["StdInfo.c_exec_std"]).replace(/\\n/g, "<br/>");  
				innerHTml +='<tr><td class="table_header">执行标准</td></tr>';
				innerHTml +='<tr><td style="text-indent:2em;">'+tmpstr+'</td></tr>'; 
				var tmpstr = (data["StdInfo.c_err_std"]==null?"无":data["StdInfo.c_err_std"]).replace(/\\n/g, "<br/>");  
				innerHTml +='<tr><td class="table_header">异常怎么处置(执行环节)</td></tr>';   
				innerHTml +='<tr><td style="text-indent:2em;">'+tmpstr+'</td></tr>'; 
				var tmpstr = (data["StdInfo.c_check_std"]==null?"无":data["StdInfo.c_check_std"]).replace(/\\n/g, "<br/>"); 
				innerHTml +='<tr><td class="table_header">验证标准(如何核查)</td></tr>';  
				innerHTml +='<tr><td style="text-indent:2em;">'+tmpstr+'</td></tr>';   
				var tmpstr = (data["StdInfo.c_review_std"]==null?"无":data["StdInfo.c_review_std"]).replace(/\\n/g, "<br/>"); 
				innerHTml +='<tr><td class="table_header">评价标准</td></tr>';  
				innerHTml +='<tr><td style="text-indent:2em;">'+tmpstr+'</td></tr>';   
				innerHTml +='</table>';
				//console.info(innerHTml);
				document.getElementById('DivTaskStdInfo').innerHTML = innerHTml;		  
		  } 
	  });  
};
 



function getScanDetails(cBasedataId){
	var actionUrl="taskVeriEva_getObjectInfo.action";
	$.post(actionUrl,{"cBasedataId":cBasedataId},function(data,status){
		   if(status=='success'){			   
			   var scanDetails=data.data[0].cScanDetail;
				//alert(scanDetails);
				var obj=eval('('+scanDetails+')');
				var details="";
				for(var key in obj){
					details+=key+":"+obj[key]+'<br>';
				}
				$("#details").html(details);
		   }
	  });

}
