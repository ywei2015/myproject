function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

/*var taskId = getQueryString("taskid"); //645 ; */
var userId= getQueryString("userId");
var pageId=0;
var task_handle_des = "";
var task_status_list = null;
var task_type_list = null;
var totalNum =null;
var totalPage=null;
var wind=$(window).width()-25;
var isHold=0;

$.mobile.page.prototype.options.domCache = true;
var kind=1; 

var pageIndex=1,pagesize=10;

var pageData=null;
var Ttype=0;
function switchPage(index) {
	switch(index) {
		case 1:
			showLoader();
			$("#page1").show();
			$("#page2").hide();			
			kind=1;
			Ttype=0;
	    	pageIndex=1;	    
		    pagesize=10;
			getTaskStatus();
			break;
		case 2:
			 showLoader();
			$("#page1").hide();
			$("#page2").show();
			 Ttype=1;
		     pageIndex=1;	    
		     pagesize=10;
			 OnShowTaskErrVerifyinfo();
			break;
		case 3:
			 showLoader();
			$("#page1").show();
			$("#page2").hide();
			kind=1;
			Ttype=0;
			pageIndex=1;	    
			pagesize=10;
			getTaskStatus();
			break;
		default:
			break;
	}
}

function showLoader() {
	            $.mobile.loading('show', {
	            	text : '数据加载中，请稍等...',
	        		textVisible : true,
	        		theme : 'a'
	             });
}

function hideLoader() {
	            $.mobile.loading('hide');
	         }

window.onload = function () {    
	showLoader();
};

function kset(key, value){  
    console.log("key"+key+"value"+value);  
    window.localStorage.setItem(key, value);  
}  



function getTaskStatus(){
		$.ajax({
			  url:'task_getTaskStatusList.action', 
			  dataType:'json',
			  success:function(data){ 
				  //console.info(data); 
				  if(data==undefined||data.length==0){ return; } 
				  task_status_list = data;
				  getTaskType();
			  }
			});  
}
function getTaskType(){
		$.ajax({
			  url:'task_getTaskTypeList.action', 
			  dataType:'json',
			  success:function(data){ 
				  //console.info(data); 
				  if(data==null||data==undefined||data.length==0){return;} 
				  task_type_list = data;
				  getCount();
			  }
			});  

}
function getCount(){
		$.ajax({
			  url:'taskVeriEva_queryTaskVerifyCount.action?userId='+userId+'&taskKind='+kind, 
			  dataType:'json',
			  success:function(data){ 
				  console.info(data); 
				  if(data==undefined||data.length==0){ return; } 
				  totalNum = data["totalNum"];
				  //alert(totalNum);
				  totalPage=Math.ceil(totalNum/pagesize);
				  //alert(totalPage);
				  OnShowTaskVerifyinfo();
			  }
			}); 
}

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
	 getTaskStatus();
	 var mydiv=$("#successDialog");
	 var widthj = (document.body.clientWidth -110) / 2;
	   mydiv.css("left",20);
	} 
); 


$(document).on("pageinit",function(){
	  $(".li").on("taphold",function(){
	    $(this).css('background-color','blue');
	  });                       
	});


function prePage(){
	 $("#nextPage").prop('disabled',false).removeClass("ui-disabled");
	console.info(pageIndex+"--"+totalPage+"----"+Ttype);
    if((pageIndex>1)&&(pageIndex <= totalPage)){
		pageIndex--;
		if("1"==Ttype){
			OnShowTaskErrVerifyinfo();
		}else {
			OnShowTaskVerifyinfo();
		}
		
	}
}

function nextPage(){
	console.info(pageIndex+"--"+totalPage+"----"+Ttype);
	if(pageIndex==totalPage){
		 $("#nextPage").prop('disabled',true).addClass("ui-disabled");
	}else if((pageIndex>=1)&&(pageIndex < totalPage)){
		pageIndex++;
		if("1"==Ttype){
			OnShowTaskErrVerifyinfo();
		}else {
			OnShowTaskVerifyinfo();
		}
		
	}
}

function firstPage(){
	 $("#nextPage").prop('disabled',false).removeClass("ui-disabled");
	console.info(pageIndex+"--"+totalPage);
	pageIndex=1;
	OnShowTaskVerifyinfo();
	
}
function lastPage(){
	// $("#nextPage").prop('disabled',false).removeClass("ui-disabled");
	console.info(pageIndex+"--"+totalPage);
	pageIndex=totalPage;
	OnShowTaskVerifyinfo();
}

function getTxt(text){
	showLoader();
	console.info(Ttype);
	var txt=text.options[text.selectedIndex].text;
	pageIndex=1;
	var txtNum=Number(txt);
	pagesize=txtNum;

	if("1"==Ttype){
		OnShowTaskErrVerifyinfo();
	}else {
		getCount();
	}
}
//工作任务
var OnShowTaskVerifyinfo= function(){
	showLoader();
	console.info(pageIndex+"--"+totalPage);
	pageData=pageIndex+'/'+totalPage+' 共'+totalNum+'条';
	$("#pageDisplay").html(pageData);
	console.info(kind);
	$.ajax({
		  url:'taskVeriEva_queryTaskVerify.action?userId='+userId+'&c_pageindex='+pageIndex+'&c_pagesize='+pagesize+'&taskKind='+kind,
		  dataType:'json',
		  success:function(data){ 
			  if(data==undefined||data.length==0){ 
				  innerHTml='<center><h3><font color="gray">没有要验证的任务哦~</h3></font></center>';
				  document.getElementById('DivTaskVerifyBase').innerHTML = innerHTml;	
				  return;
			  }
			  var currentWidth = window.innerWidth;
			  var innerHTml = "<table class='table' cellspacing='0' style='width:"+currentWidth+"px;margin:5px;'><tbody>";
			  innerHTml+="<tr  id='submit' style='height:40px;'><td style='width:40px;'><input  type='checkbox' name='choseAll'  onClick='checkAllBox(this)' style='width:22px;height:22px;'/></td><td style='text-align:right;'>" +
			  		"<a href='#popupDialog' data-rel='popup' data-position-to='window' data-role='button'  data-inline='true' data-transition='pop'  data-theme='b' style='text-decoration:none;'><input type='button' value='批量提交' style='height:30px;' /></a></td></tr>";
			  for(var i=0;i<data.length;i++){
					var taskid=data[i].c_task_id;
					console.info(data[i].c_task_id);
					innerHTml+='<tr class="tr"><td class="td"><input name="answer" id="'+taskid+'" type="checkbox" onclick="choseOne()" style="width:22px;height:22px;"/>&nbsp &nbsp &nbsp</td><td><a class="a" href="#" target="_self" onClick=changePage("'+taskid+'")><h3 style="margin-bottom:10px;font-weight:normal;"><div class="circle"><font size="3">'+data[i].rowindex+'</font></div>'+data[i].c_task_name+'</h3>';
					innerHTml+='<p class="p"><span><font size="2">'+(getTaskTypeName(data[i].c_task_type)==undefined?"无":getTaskTypeName(data[i].c_task_type))+'|'+(data[i].c_manage_section_name==undefined?"无":data[i].c_manage_section_name)+'</font></span><span style="padding-left:50px;"><font size="1">截止时间&nbsp;&nbsp;'+str2dt(data[i].c_chk_plantime==undefined?"无":data[i].c_chk_plantime)+'</font></span>';
					innerHTml+='<span style="text-align:right;float:right;margin-right:12px;"><font color="red" size="2">未验证</font></span></p>';
					innerHTml+='<br>';
					innerHTml+='</a></td></tr>';
				}
			  innerHTml+="</tbody></table>";
			  document.getElementById('DivTaskVerifyBase').innerHTML = innerHTml;
		/*	  $(".tr").on("taphold",function(){
				  if(0 == isHold) {
					  isHold = 2;
					  $("#submit").css('display','table-row');
					  $(".td").css('display','table-cell');
				  } else {
					  isHold = 0;
					  $("#submit").css('display','none');
					  $(".td").css('display','none');
				  }
			  });*/
		  }
	  });
	   hideLoader();
};

/*...
 * kind是用来判断当前是工作任务还是工作安排
 * Ttype是用来判断当前是异常信息还是工作任务或工作安排
*/
function verifySubmit(){

	  var verifyResult = $("[name='radio-choice-1']").filter(":checked").val(); 
	  var chkResult="";
	  var textDescribe=$("#textarea-1").val();
	  console.info('verifyResult='+verifyResult+'\ntextDescribe='+textDescribe);
	 
	  var actionUrl="";

	  if(verifyResult=='choice-1'){
		  chkResult='OK';
	
		  if("1"==Ttype){//正常-异常信息
			  actionUrl="errVerifyAndComment_updateVerifyStatus.action";
				console.info(errId);
				  $.post(actionUrl,{'ids':errId,'c_chk_userid':userId,'c_chk_result':chkResult,'flag':'mobile'},function(data,status){
					   if(status=='success'){
						   $("#a").text(data);
						   $("#ok").unbind("click").bind('click',function(){
							   $("#ok").attr("data-rel","");
							   changeStyle();
						   });				
					   }
				   });
		  }else{
			  console.info(kind);
			  actionUrl="taskVeriEva_updateChkResult.action";
			  if("3"==kind){  //正常-工作安排
				  $.post(actionUrl,{'c_task_id':taskId,'c_chk_result':chkResult},function(data,status){
					   if(status=='success'){
						   $("#a").text(data);  
						   $("#ok").unbind("click").bind('click',function(){
							   $("#ok").attr("data-rel","");
							   changeStyle();
						   });				
					   }
				   });
			  }else{  //正常-工作任务
				  $.post(actionUrl,{'c_task_id':taskId,'c_chk_result':chkResult},function(data,status){
					   if(status=='success'){
						   $("#a").text(data);  
						   $("#ok").unbind("click").bind('click',function(){
							   $("#ok").attr("data-rel","");
							   changeStyle();
						   });				
					   }
				   });
			  }
			
		  }
	  }else if(verifyResult=='choice-2'){ //异常
		  if(textDescribe==""){
			 
			  $("#a").text('请填写完异常备注再提交');
			   $("#ok").unbind("click").bind('click',function(){
				   $("#ok").attr("data-rel","back");
			   });
		  }else {
			  chkResult='NG'+':'+textDescribe;
		  if("1"==Ttype){//异常-异常信息
				actionUrl="errVerifyAndComment_updateVerifyStatus.action";
				console.info(errId);
				  $.post(actionUrl,{'ids':errId,'c_chk_userid':userId,'c_chk_result':chkResult,'flag':'mobile'},function(data,status){
					   if(status=='success'){
						   $("#a").text(data);
						   $("#ok").unbind("click").bind('click',function(){
							   $("#ok").attr("data-rel","");
							   changeStyle();
						   });				
					   }
				   });
				
		  }else{
			  actionUrl="taskVeriEva_updateChkResult.action";
			  if("3"==kind){//异常-工作安排
				  $.post(actionUrl,{'c_task_id':taskId,'c_chk_result':chkResult},function(data,status){
					   if(status=='success'){	
						   $("#a").text(data);
						   $("#ok").unbind("click").bind('click',function(){				 
							   $("#ok").attr("data-rel","");
							   changeStyle();
						   });
					   }
				  });
			  }else{//异常-工作任务
				  $.post(actionUrl,{'c_task_id':taskId,'c_chk_result':chkResult},function(data,status){
					   if(status=='success'){	
						   $("#a").text(data);
						   $("#ok").unbind("click").bind('click',function(){				 
							   $("#ok").attr("data-rel","");
							   changeStyle();
							  
						   });
					   }
				  });
			  }
		  }
		   $("#textarea-1").val("");
	  }
	  }else{
		   $("#a").text('请填写验证结果再提交');
		   $("#ok").unbind("click").bind('click',function(){
			   $("#ok").attr("data-rel","back");
		   });
	  }	  
		  
}

function tijiao(){
	var taskStr="";
	var taskList="";
	var answer= document.getElementsByName("answer");
	  for(var i=0;i<answer.length;i++){
		   if(answer[i].checked){
			   taskList=answer[i].id;
			   taskStr+=taskList+',';
		   }
	  }
	  console.info(taskStr);
	  var actionUrl="";
	  if("1"==Ttype){
		  actionUrl="errVerifyAndComment_updateVerifyStatus.action";
		  if(""!=taskStr){
			  $.post(actionUrl,{'ids':taskStr,'c_chk_userid':userId,'flag':'mobile'},function(data,status){
				   if(status=='success'){
					   $("#f").text(data);
					   $("#yes").unbind("click").bind('click',function(){
						   OnShowTaskErrVerifyinfo();
						   $.mobile.changePage("#taskVerifyinfoPage");
					   });				
				   }
			   });
		  }else{
			  $("#f").text('请至少选择一个任务提交');
			   $("#yes").unbind("click").bind('click',function(){
				   $("#yes").attr("data-rel","back"); 
			   });
		  } 
	  }else{
		  actionUrl="taskVeriEva_verifyStatusUpdate.action";
		  if(""!=taskStr){
			  $.post(actionUrl,{'cTaskId':taskStr,'userId':userId},function(data,status){
				   if(status=='success'){
					   $("#f").text(data);
					   $("#yes").unbind("click").bind('click',function(){
						   //OnShowTaskVerifyinfo();
						   getCount();
						   $.mobile.changePage("#taskVerifyinfoPage");
					   });				
				   }
			   });
		  }else{
			  $("#f").text('请至少选择一个任务提交');
			    $("#yes").unbind("click").bind('click',function(){
				   $("#yes").attr("data-rel","back"); 
			   });
		  } 
	  }

}

function checkAllBox(obj){
	 var answer= document.getElementsByName("answer");
	 if(obj.checked==true){
	  for(var i=0;i<answer.length;i++){
	   answer[i].checked = true;
	   $("#footer").attr("data-position","fixed");
	  }
	 }else{
	  for(var i=0;i<answer.length;i++){
	   answer[i].checked = false;
	   $("#footer").attr("data-position","fixed");
	  }
	 }
}

function choseOne(){
	var answer= document.getElementsByName("answer");
	var choseAll= document.getElementsByName("choseAll");
	  for(var i=0;i<answer.length;i++){
		  if(answer[i].checked){
			  choseAll[0].checked = true;
			  $("#footer").attr("data-position","fixed");
		  }else{
			  choseAll[0].checked = false;
			  $("#footer").attr("data-position","fixed");
			  break;
		  }
	  }
}

function setRadioDiv(){
	var htmlstr="";
	htmlstr='<fieldset data-role="controlgroup" data-type="horizontal">';
	htmlstr +='<legend style="float:left;">任务验证：</legend>'; 
	htmlstr+=' <input type="radio" name="radio-choice-1" id="radio-choice-1"  value="choice-1" />';
	htmlstr+='  <label for="radio-choice-1">正常</label>';
	htmlstr+=' <input type="radio" name="radio-choice-1" id="radio-choice-2" value="choice-2" />';
	htmlstr+=' <label for="radio-choice-2">异常</label>';
	htmlstr+='  <input type="hidden" id="result"/>';
	htmlstr+=' </fieldset>';
	
	$("#radioDiv").html("");
	$("#radioDiv").html(htmlstr).trigger('create');
	//$(htmlstr).appendTo('#radioDiv').trigger('create');
	
}
var mydiv_resize = function() {
	var mydiv = $("#yzsubmit");  
	var myqueding=$("#ok");
	 var widthj = (document.body.clientWidth -80) / 2;
	 mydiv.css("left",widthj);
	 myqueding.css("margin-left",10);
};
function changePage(vtaskid){
	mydiv_resize();
	if("1"==Ttype){ 
		setRadioDiv();
		$.mobile.changePage("#VerifyFromPage");	
		pageId=1;
		//showLoader();
		errId=vtaskid;
		$("#detail1").hide();
		$("#detail2").show();
		$("#detail2").html('<iframe  id="errFrame" onLoad="iFrameHeight()" frameborder="0" src="../errinfo/errdetail.html?errId='+errId+'"></iframe>');
		$("#errFrame").css("width",document.body.clientWidth-20 );
		//$("#errFrame").css("height", document.body.clienHeight);
		
		
	}else{
		setRadioDiv();
		$.mobile.changePage("#VerifyFromPage");	
		pageId=1;
		$("#detail1").show();
		$("#detail2").hide();
		showLoader();
		taskId=vtaskid;
		onShowTaskInfo();
	}
}

function iFrameHeight(){
	var ifm= document.getElementById("errFrame");
	var subWeb = document.frames ? document.frames["errFrame"].document : ifm.contentDocument;
	if(ifm != null && subWeb != null) {
	   ifm.height = subWeb.body.scrollHeight-20;
	   ifm.width = subWeb.body.scrollWidth;
	}
}
function refresh(){
	//window.location.href=window.location.href;
	//window.location.reload(true);
	   OnShowTaskVerifyinfo();
	   $.mobile.changePage("#taskVerifyinfoPage");
}


var task_std_version = 0;
var task_std_id = "0";
var task_kind = "0";
var task_status_list = null;
var task_type_list = null;


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
	return val.substring(0, len-3).replace("T"," "); 
}

function goTop(){
	$('html,body').animate({scrollTop:0},300);
} 
//任务验证
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
			  getTaskStatus();
		  } 
	  }); 
	hideLoader();
}; 

//步骤结果信息
var OnShowTaskResultInfo = function(){
	$.ajax({
		  url:'taskEntry_queryEntryList.action?action=1&taskMouldPojo.c_task_id='+taskId, 
		  dataType:'json',
		  success:function(data){ 
			  if(data==undefined||data.length==0){ return; }   
			  var innerHTml = ""; 
				for(var i=0;i<data.length;i++){
					innerHTml +='<div width="100%" height="100%" >';
					innerHTml +='<table class="ui-form-table" width="100%" height="100%"  ><tr valign="middle"><td  >步骤【'+(i+1)+'】</td></tr>';
					innerHTml +='<tr valign="middle">';
					innerHTml +='<td style="text-indent:2em;" >'+(data[i].c_step_prompt==undefined?"收集结果---"+(i+1):data[i].c_step_prompt);
					innerHTml += '【'+(data[i].c_tracefun_name==undefined?"无":data[i].c_tracefun_name)+'】</td>';
					innerHTml +='</tr>';
					innerHTml +='<tr valign="middle"><td >';
					var result_html = "";
					if(data[i].c_result==undefined){
						result_html ='*(未收集)';
					}
					else
					{ 
						//alert(data[i].c_file_path);
						if(data[i].c_tracefunid == "1"){ 
							result_html ='<a href="#" rel="'+data[i].c_file_path+'" target="_self"  id="imglinkpreview"><img height=80px width=140px onclick="big(this)"  src="'+data[i].c_file_path+'" title="点我放大" alt="点我放大"/></a>';					
						}
						else if(data[i].c_tracefunid == "2"){							
							result_html ='<br><audio src="'+data[i].c_file_path+'"  style="width:280;"  controls="controls"></audio>';				
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
								
						}else if(data[i].c_tracefunid=="11"){
							  if("1"==data[i].c_result){
								  result_html="正常";
							  }else if("0"==data[i].c_result){
								  result_html="异常";
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
//5W2H标准
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
				innerHTml +='<tr><td class="table_header2">一、要做什么</td></tr>';
				innerHTml +='<tr><td style="text-indent:2em;">'+tmpstr+'</td></tr>';
				var tmpstr = (data["StdInfo.c_exec_std"]==null?"无":data["StdInfo.c_exec_std"]).replace(/\\n/g, "<br/>");  
				innerHTml +='<tr><td class="table_header2">二、执行标准</td></tr>';
				innerHTml +='<tr><td style="text-indent:2em;">'+tmpstr+'</td></tr>'; 
				var tmpstr = (data["StdInfo.c_err_std"]==null?"无":data["StdInfo.c_err_std"]).replace(/\\n/g, "<br/>");  
				innerHTml +='<tr><td class="table_header2">三、异常怎么处置(执行环节)</td></tr>';   
				innerHTml +='<tr><td style="text-indent:2em;">'+tmpstr+'</td></tr>'; 
				var tmpstr = (data["StdInfo.c_check_std"]==null?"无":data["StdInfo.c_check_std"]).replace(/\\n/g, "<br/>"); 
				innerHTml +='<tr><td class="table_header2">四、验证标准</td></tr>';  
				innerHTml +='<tr><td style="text-indent:2em;">'+tmpstr+'</td></tr>';   
				var tmpstr = (data["StdInfo.c_err_std2"]==null?"无":data["StdInfo.c_check_std"]).replace(/\\n/g, "<br/>"); 
				innerHTml +='<tr><td class="table_header2">五、异常怎么处置(验证环节)</td></tr>';  
				innerHTml +='<tr><td style="text-indent:2em;">'+tmpstr+'</td></tr>'; 
				var tmpstr = (data["StdInfo.c_review_std"]==null?"无":data["StdInfo.c_review_std"]).replace(/\\n/g, "<br/>"); 
				innerHTml +='<tr><td class="table_header2">六、评价标准</td></tr>';  
				innerHTml +='<tr><td style="text-indent:2em;">'+tmpstr+'</td></tr>'; 
				var tmpstr = (data["StdInfo.c_review_std"]==null?"无":data["StdInfo.c_review_std"]).replace(/\\n/g, "<br/>"); 
				innerHTml +='<tr><td class="table_header2">七、制度文件</td></tr>';  
				innerHTml +='<tr><td style="text-indent:2em;"><a id="systemFile" href="#" target="_blank" ><font size="3">制度文件</font></a></td></tr>'; 
				innerHTml +='</table>';
				//console.info(innerHTml);
				document.getElementById('DivTaskStdInfo').innerHTML = innerHTml;
				OnShowTaskResultInfo();
				systemFile();
		  } 
	      
	  });  
};



function systemFile(){ 
		var actionUrl="taskVeriEva_getUserCode.action";
		$.post(actionUrl,{"userId":userId},function(data,status){
			   if(status=='success'){			   
				   if(data==undefined||data.length==0){
						return;
					}
			        var LoginUserCode=data;
		            console.info(LoginUserCode);
		            var url="http://10.75.97.231/gonggao/bandindex.aspx?coding=0108&LoginUserCode="+LoginUserCode;
			        document.getElementById('systemFile').href=url;
			   }
		  });
}

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


//异常信息
var OnShowTaskErrVerifyinfo= function(){
/*	console.info(pageIndex+"--"+totalPage);
	pageData=pageIndex+'/'+totalPage;
	$("#pageDisplay").html(pageData);*/
	console.info(kind);
	showLoader();
	$.ajax({
		  url:'errVerifyAndComment_getErrVerifyInfo.action?c_chk_userid='+userId+'&page='+pageIndex+'&pageSize='+pagesize+'&flag=mobile',
		  dataType:'json',
		  success:function(data){ 
			  totalNum=data.data.length;
			  totalPage=data.info.total;
			  console.info(pageIndex+"--"+totalPage);
			  pageData=pageIndex+'/'+totalPage+' 共'+totalNum+'条';
		      $("#pageDisplay").html(pageData);
		      var arr=data.data;
			  if(!arr){ 
				  innerHTml='<center><h3><font color="gray">没有要验证的异常任务哦~</h3></font></center>';
				  document.getElementById('DivTaskVerifyBase').innerHTML = innerHTml;	
				  return;
			  }
			  var currentWidth = window.innerWidth;
			  var innerHTml = "<table class='table' cellspacing='0' style='width:"+currentWidth+"px;margin:5px;'><tbody>";
			  innerHTml+="<tr  id='submit' style='height:40px;'><td style='width:40px;'><input  type='checkbox' name='choseAll' onClick='checkAllBox(this)' style='width:22px;height:22px;'></input></td><td style='text-align:right;'>" +
			  		"<a href='#popupDialog' data-rel='popup' data-position-to='window' data-role='button'  data-inline='true' data-transition='pop'  data-theme='b' style='text-decoration:none;'><input type='button' value='批量提交' style='height:30px;' /></a></td></tr>";
			  //console.info(arr.length);
			  for(var i=0;i<arr.length;i++){
					var errId=arr[i].c_err_idStr;
					console.info(arr[i].c_err_idStr);
					var s=(pageIndex-1)*pagesize+i+1;
					//alert(s);
					innerHTml+='<tr class="tr"><td class="td"><input name="answer" id="'+errId+'" type="checkbox" onclick="choseOne()" style="width:22px;height:22px;"/></td><td><a class="a" href="#" target="_self" onClick=changePage("'+errId+'")><h3 style="margin-bottom:10px;font-weight:normal;"><div class="circle"><font size="3">'+s+'</font></div>'+arr[i].c_err_name+'</h3>';
					innerHTml+='<p class="p"><span ><font size="1">验证期限&nbsp;&nbsp; '+str2dt(arr[i].c_chk_plantime==undefined?"无":arr[i].c_chk_plantime)+'</font></span>';
					innerHTml+='<span style="text-align:right;float:right;margin-right:12px;"><font color="red" size="2">未验证</font></span></p>';
					innerHTml+='<br>';
					innerHTml+='</a></td></tr>';
				}
			  innerHTml+="</tbody></table>";
			  document.getElementById('DivErrVerifyBase').innerHTML = innerHTml;
		  }
	  });
	   hideLoader();
};

function weiZhi(){
	var widthj = (document.body.clientWidth -80)/2;
	$("#popupDialog").css("left",widthj);
	$("#popupDialog").css("top",150);
}

function changeStyle(){
	var fflag=is_Ios();
	if(fflag=="android"){
		$.mobile.changePage("#taskVerifyinfoPage");
		OnShowTaskVerifyinfo();
		 
	}
	else
	   window.location.href='taskVerify.html?userId='+userId;
}

function is_Ios(){
	var flagm="";
	var u = navigator.userAgent;
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	if(isAndroid) {
		flagm="android";
	}
	else {
		flagm="ios";
	}
	//alert(flagm);
	return flagm;
};
