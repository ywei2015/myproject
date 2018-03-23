//刷新条件
var condition = null;
var loadingWindow = null;

function createNewErr() {
	var path = window.location.href;
	var url = path.substring(0, path.lastIndexOf("/"));
	url = url + "/" + "newErr.html?c_err_kind=2";
	var grid=fastDev.getInstance('grid_task');
	fastDev.create("Window", {
		title : "发起异常",
		src : url,
		width : 800,
		height : 400,
		buttons : [{
			text : "保存",
			align : "center",
			iconCls : "icon-save",
			onclick : function(event, that, win, fast) {
				fastDev.confirm("是否确认提交数据？","发起异常确认",function(res){					
					if(res){win.doSave(that,grid,this);}
				});
			}
		},{
			text : "关闭",
			align : "center",
			iconCls : "icon-close",
			onclick : function(event, that, win, fast) {
				that.close();
			}
		}]
	});
}



function onBeforeReady() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "checkform",
		text : "加载中..."
	});

	this.setOptions({
		initSource : 'errTrace_getNeedHandleErrInfo.action'
	});
}

function onAfterDataRender() {
	var body = fastDev(window).height();
	this.setHeight(body - 106);
	loadingWindow.close();

}

/**
 * 过滤信息
 */
function doSearch() {
	var form = fastDev.getInstance("checkform");
	condition = form.getItems();
	fastDev.getInstance('grid_task').refreshData(condition);
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

function doOpenErrTaskDetails(data) {
	var c_err_id=data.c_err_idStr;
	var c_task_id= data.c_task_idStr+"";
	var path=window.location.href;
	var index=path.lastIndexOf("/");
	path=path.substring(0,index);
	fastDev.create("Dialog", {
		height : 500,
		width : 900,
		inside : false,
		showMaxBtn : false,
		allowResize : false,
		title : "异常详情",
		src : path+"/taskErrorDetail.html?cErrId=" + c_err_id 
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
	var id = name;
	var cPid = fastDev.getInstance(id).getValue();
	var path = window.location.href;
	var url = path.substring(0, path.lastIndexOf("/"));
	url = url + '/PositionTree/DynamicPositionTree.html',
	fastDev.create('Window', {
		width : 300,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : "选择岗位",
		allowResize : false,
		
		src : url,
		buttons : [ {
			id : 'ok',
			text : '确定',
			onclick : function(event, win, cwin, fast) {
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
	fastDev.getInstance("taskErrTraceVo.c_writer").setValue(cPid.replace("U-",""));
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


function toOperation(value){
	var info=fastDev.getInstance('grid_task').getValue(this)[0];
	if(info.c_status==22){
		return '';
	}
	var c_feedback_id=info.c_feedback_idStr;
	//alert(fastDev.Util.JsonUtil.parseString(info));
	//alert(c_feedback_id);
	
	var link1='<a href="javaScript:void(0);" name="ct2"  onclick=continueHandle(this)>继续反馈</a>&nbsp;&nbsp;&nbsp;';
	var link2='<a href="javaScript:void(0);"  onclick=confirmReceive("'+c_feedback_id+'")>接收确认</a>&nbsp;&nbsp;&nbsp;';
	var link3='<a href="javaScript:void(0);" name="ct1" onclick=continueHandle(this)>安排整改</a>&nbsp;&nbsp;&nbsp;';
	var link4='<a href="javaScript:void(0);" name="fr1" onclick=finishError(this)>整改录入</a>&nbsp;&nbsp;&nbsp;';
	var link5='<a href="javaScript:void(0);" name="fr2" onclick=finishError(this)>本人处理</a>&nbsp;&nbsp;&nbsp;';
	
	if(value==0){
		return  link5+link3+link1;
	}
	if(value==1){
		return link2;
	}
	if(value==2){
		return link4;
	}
	
	
}

function getRowData(obj){
	var grid=fastDev.getInstance('grid_task');
	return grid.getValue(obj)[0];
}



function finishError(obj){  //实施整改或者自己处理
	var path=window.location.href;
	var index=path.lastIndexOf("/");
	path=path.substring(0,index);
	var data=getRowData(obj);
	//alert(fastDev.Util.JsonUtil.parseString(data)+"****");
	
	if(obj.name=='fr1'){
		type=1;
	}else if(obj.name='fr2'){
		type=2;
	}
	var title=['整改录入','本人处理'];
	var grid=fastDev.getInstance('grid_task');
	fastDev.create("Window",{
		title:title[type-1],
		src:path+'/finishError.html?c_feedback_id='+data.c_feedback_idStr+'&c_err_id='+data.c_err_idStr+'&type='+type,
		buttons:[{
			align:'center',
			id : 'ok',
			text : '提交',
			width:'100px',
			iconCls:'icon-save',
			onclick : function(event, win, childWin, fast) {
				var ti="";
				if(type==1){
					ti="整改录入确认";
				}else if(type==2){
					ti="本人处理确认";
				}
				fastDev.confirm("是否确定提交数据",ti,function(res){
					if(res){
						childWin.doSave(win,grid);
					}
				});
			}
		}, {
			align:'center',
			id : 'clear',
			text : '重置',
			width:'100px',
			iconCls:'icon-reset',
			onclick : function(event, win, childWin, fast) {
				childWin.doReset();
			}
		},{
			align:'center',
			id : 'close',
			text : '关闭',
			width:'100px',
			iconCls:'icon-close',
			onclick : function(event, win, childWin, fast) {
				win.close();
			}
		}]
	});
}

function continueHandle(obj){   //继续反馈或者安排整改
	var path=window.location.href;
	var index=path.lastIndexOf("/");
	path=path.substring(0,index);
	
	var type;
	if(obj.name=='ct1'){
		type=1;
	}else if(obj.name='ct2'){
		type=2;
	}
	var title=['安排整改','继续反馈'];
	var data=getRowData(obj);
	//alert(fastDev.Util.JsonUtil.parseString(data));
	var c_err_id=data.c_err_idStr;
	var c_feedback_id=data.c_feedback_idStr;
	var grid=fastDev.getInstance('grid_task');
	fastDev.create("Window",{
		title:title[type-1],
		src:path+'/continueFeedback.html?c_feedback_id='+c_feedback_id+'&type='+type+"&c_err_id="+c_err_id,
		buttons:[{
			align:'center',
			id : 'ok',
			text : '提交',
			width:'100px',
			iconCls:'icon-save',
			onclick : function(event, win, childWin, fast) {
				var ti="";
				if(type==1){
					ti="安排整改";
				}else if(type==2){
					ti="继续反馈";
				}
				fastDev.confirm("是否确定提交数据？",ti,function(res){					
					if(res){
						childWin.doSave(win,grid,type);
					}
				});
			}
		}, {
			align:'center',
			id : 'clear',
			text : '重置',
			width:'100px',
			iconCls:'icon-reset',
			onclick : function(event, win, childWin, fast) {
				childWin.doReset();
			}
		},{
			align:'center',
			id : 'close',
			text : '关闭',
			width:'100px',
			iconCls:'icon-close',
			onclick : function(event, win, childWin, fast) {
				win.close();
			}
		}]
	});
}

function confirmReceive(c_feedback_id){
	//alert(c_feedback_id);
	 fastDev.post('errTrace_receiveConfirmEror.action',{
		 success:function(data){
			 fastDev.tips(data);
			 fastDev.getInstance('grid_task').refreshData(false);
		 },
		 data:{'taskErrTraceVo.c_feedback_id':c_feedback_id}
	 });
}

function timeRender(value){
	if(value){
		return value.replace("T"," ");
	}
}
