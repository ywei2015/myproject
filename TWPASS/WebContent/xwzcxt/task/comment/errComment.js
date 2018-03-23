//刷新条件
var condition = null;
var loadingWindow = null;


function exportXLS() {
	var formData = fastDev.getInstance("checkform").getItems();
	fastDev.create("Proxy", {
		action : "errVerifyAndComment_exportComment.action"
	}).save(formData, function(data) {
		fastDev("#dc").prop("src", data.url);
	});	
}

function onBeforeReady() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "checkform",
		text : "加载中..."
	});

	this.setOptions({
		initSource : 'errVerifyAndComment_getErrCommentInfo.action'
	});
}

function timeRender(value){
	if(value){
		return value.replace("T"," ");
	}
}

function onAfterDataRender() {
	chkcount=0;
	var body = fastDev(window).height();
	this.setHeight(body - 90);
	showResourceModes('ycczcx');
	loadingWindow.close();
}

var chkcount=0;
function toChkRadio(value){
	if(value=='OK'||!value){		
		return  '<input type="radio" name="chk'+(++chkcount)+'" value="OK" checked  onchange="updateCommentResult(this)"><label>合格</label>'
		       +'<input type="radio" name="chk'+chkcount+'" value="NG" onchange="updateCommentResult(this)"><label>不合格</label>';
	}else if(value=='NG'){
		return  '<input type="radio" name="chk'+(++chkcount)+'" value="OK" onchange="updateCommentResult(this)"><label>合格</label>'
		+'<input type="radio" name="chk'+chkcount+'" value="NG" checked onchange="updateCommentResult(this)"><label>不合格</label>';
	}
}

function updateCommentResult(obj){
	fastDev.confirm('是否确定更新评价结果？','信息提示',function(res){
		if(!res){
			var rad=document.getElementsByName(obj.name);
			var val=obj.value;
			for(var i=0;i<rad.length;i++){
				if(val!=rad[i].value){
					rad[i].checked=true;
				}
			}
			return;
		}
		var data=fastDev.getInstance('grid').getValue(obj)[0];
		var c_err_id=data.c_err_idStr;
		if(obj.value=='OK'){
			fastDev.post("errVerifyAndComment_updateEvaluateResult.action",{
				success:function(data){
					//fastDev.tips(data);
					fastDev.getInstance("grid").refreshData(false);
				},
				failed:function(data){
					obj.value='';
				},
				data:{"c_err_id":c_err_id,"c_evaluate_result":"OK"},
			});
		}
		else if(obj.value=='NG'){
			fastDev.post("errVerifyAndComment_updateEvaluateResult.action",{
				success:function(data){
					//fastDev.tips(data);
					fastDev.getInstance("grid").refreshData(false);
				},
				failed:function(data){
					obj.value='';
				},
				data:{"c_err_id":c_err_id,"c_evaluate_result":"NG"},
			});
			 getUserCode(c_err_id);
		}
	});
	
}

/**
 * 过滤信息
 */
function doSearch() {
	chkcount=0;
	var form = fastDev.getInstance("checkform");
	condition = form.getItems();
	condition['isFirst']=0;
	fastDev.getInstance('grid').refreshData(condition);
}
/**
 * 重置表单数据
 */
function doReset() {
	fastDev.getInstance('checkform').cleanData();
	condition = null;
}

function operLinkRenderer(value) {
	return [ '<div style="width:300px;"><a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="openErrTaskDetails" name="openErrTaskDetails">'
 		+ value + '</a>' ].join('');
}

function isCommented(){
	   var status= fastDev.getInstance('c_evaluate_status').getValue();
	   var obj=  fastDev.getInstance('c_evaluate_result');
	   if(status=='2'){
	     	obj.enable();
	   }else{
		    //obj.setValue("");
	        obj.disable();
	   }
	}


function doOpenErrTaskDetails(data) {
	var c_err_id=data.c_err_idStr;
	var c_task_id= data.c_task_idStr+"";
	var url=getLocationHead();
	fastDev.create("Dialog", {
		height : 500,
		width : 900,
		inside : false,
		showMaxBtn : false,
		allowResize : false,
		title : "异常详情",
		src : url+"/xwzcxt/taskerror/taskErrorDetail.html?cErrId=" + c_err_id 
				+ "&cTaskId=" + c_task_id+"&dg_seq="+ data.dg_seq,
		buttons : [ {
			text : "关闭",
			align : "center",
			iconCls : "icon-close",
			onclick : function(event, that, win, fast) {
				// 按钮点击事件回调的作用域(this)指向当前按钮控件实例
				// 参数that为当前对话框控件实例
				that.close();
			}
		} ]
	});
}

var currentTarget=null;
function changeColor(target, rowindex) {
    var obj=target;
	if(currentTarget!=null){
		fastDev(currentTarget).parents("tr").children("td").css(
				"background-color", "");
	}

	fastDev(obj).parents("tr").children("td").css("background-color", "#CABBCC");
	currentTarget=obj;
}


function onRowClick(event, rowindex, data) {
	var target = event.target;
	 changeColor(target, rowindex);
	var name = target['name'];
	if (name) {
		name = fastDev.Util.StringUtil.capitalize(name);
		if (window['do' + name]) {
			window['do' + name].call(window, data);
		}
	}
}

function onSelectPosition(e) {
	var element = e.srcElement || e.target;
	fastDev.confirm("是否需要修改当前反馈人？", "信息提示", function(result) {
		if (result) {
			doSelectPosition(element.id);
		}
	});
}



function doSelectPosition(name) {
	var url=getLocationHead();
	var id = name;
	var cPid = fastDev.getInstance(id).getValue();

	fastDev.create('Window', {
		width : 300,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : "反馈人",
		allowResize : false,
		
		src :url+ '/xwzcxt/taskerror/PositionTree/DynamicPositionTree.html',
		buttons : [ {
			id : 'ok',
			text : '确定',
			onclick : function(event, win, cwin, fast) {
				//var cPosition = fast.getInstance("cPosition");
				var user = cwin.getLeafValue();
				if (!!user) {
					setPosition(name, user.id, user.name);
					win.close();
				} else {
					window.alert("请选择反馈人！");
				}
			}
		}, {
			id : 'clear',
			text : '清空',
			onclick : function(event, win) {
				setPosition(name, "", "");
				win.close();
			}
		} ]
	});
}

function setPosition(name, cPid, cPidName) {
	//var id = name.substring(0, name.indexOf("Name"));
	fastDev.getInstance("c_writer").setValue(cPid.replace("U-",""));
	fastDev.getInstance("c_feedbacker_name").setValue(cPidName);
}


function toResult(value){
	var a=['未完成','已完成'];
	return a[value];
}

function toErrKindName(value){
	var a=['工作执行异常','人工发起异常'];
	return a[value-1];
}



function getLocationHead(){
	var path=window.location.href;
	var index=-1;
	for(var i=1;i<5;i++){
		index=path.indexOf("/", index+1);
	}
	path=path.substring(0,index);
	return path;
}


function toOperation(value){
	if(value=='1'){
		return "<a href='javaScript:void(0);' onclick='updateStatus(this)' >提交</a>";
	}else{
		return '';
	}
}

function updateStatus(obj){
	fastDev.confirm("是否提交评价？","信息提示",function(res){
		if(!res)return;
		var c_err_id=fastDev.getInstance('grid').getValue(obj)[0].c_err_idStr;
		fastDev.post("errVerifyAndComment_updateEvaluateStatus.action",{
			success:function(data){
				fastDev.alert(data);
				fastDev.getInstance("grid").refreshData(false);
			},
			data:{'ids':c_err_id}
		});
	});
	
}

function saveMany(){
	fastDev.confirm("是否批量提交评价？","信息提示",function(res){
		if(!res)return;
		var data=fastDev.getInstance('grid').getValue();
		var ids='';
		for(var i=0;i<data.length;i++){
			if(data[i].c_evaluate_status=='1'){				
				ids+=data[i].c_err_idStr+",";
			}
		}
		if(ids==''){
			fastDev.alert("请至少选择一行未评价的数据!");
			return;
		}
		fastDev.post("errVerifyAndComment_updateEvaluateStatus.action",{
			success:function(data){
				fastDev.alert(data);
				fastDev.getInstance("grid").refreshData(false);
			},
			data:{'ids':ids}
		});
	});
}


function toEventLink(value){
	var c_err_id=fastDev.getInstance('grid').getValue(this)[0].c_err_idStr;
	if(value=='已评价未发起事件'){
			return '<a href="javaScript:void(0); onclick=getUserCode('+c_err_id+')">已评价未发起事件</a>';
	}
	return value;

}

var  dlWin=null;
function getUserCode(c_err_id){
	url="taskVerifyAndComment_getUserCode.action";
	   fastDev.post(url,{
		  success:function(data){
			var emis_url= data.emis_url+"?rnd=1--c_err_id="+c_err_id+"--userId="+data.userId+"%22&SourcePage=Mes&LoginUserCode="+data.userCode;
			//var emis_url="http://www.baidu.com";
//			dlWin=fastDev.create("Window",{
//				title:"发起度量系统事件",
//				src:emis_url,
//				height:"90%",
//				width:"90%",
//				inside:false,
//				onBeforeClose:function(){
//					fastDev.getInstance('grid').refreshData(false);
//				}
//			});  
			var returnValue=window.showModalDialog("emis.html",emis_url,"dialogWidth=850px;dialogHeight=700px");
			if(returnValue=="success")
				{
				fastDev.getInstance('grid').refreshData(false);
				}
			
		  }
	  });
}
