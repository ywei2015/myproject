// 刷新条件
var condition = null;
var loadingWindow = null;



function onBeforeReady() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "checkform",
		text : "加载中..."
	});

	this.setOptions({
		initSource : 'taskAndErrManager_getUnexecuteableTask.action'
	});
}

function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body - 95);
	showResourceModes('RWGLWH');
	loadingWindow.close();
}

/**
 * 过滤信息
 */
function doSearch() {
	var form = fastDev.getInstance("checkform");
	condition = form.getItems();
	condition['isFirstTime']=1;
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
	return [ '<div style="width:300px;"><a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="openTaskDetails" name="openTaskDetails">'
		+ value + '</a>' ].join('');
}



function doOpenTaskDetails(data) {
	 var url=window.location.href;
	    var index=-1;
	    for(var i=0;i<4;i++){
	    	index=url.indexOf("/",index+1);
	    }
	    url=url.substring(0,index);
	fastDev.create("Dialog", {
		height : 500,
		width : 900,
		inside : false,
		showMaxBtn : false,
		title : "任务详情",
		src : url+"/xwzcxt/task/taskDetails.html?edit=details&taskId=" + data.c_task_idStr,
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

function onRowClick(event, rowindex, data) {
	var target = event.target;
	var name = target['name'];
	console.info(name);
	if (name) {
		name = fastDev.Util.StringUtil.capitalize(name);
		console.info(name);
		if (window['do' + name]) {
			window['do' + name].call(window, data);
		}
	}
}

function onchange(value) {
	var cTaskType = fastDev.getInstance("typeid");
	cTaskType.disableItems("20");
}

function onSelectPosition(e) {
	var element = e.srcElement || e.target;
	fastDev.confirm("是否需要修改当前部门组织？", "信息提示", function(result) {
		if (result) {
			doSelectPosition(element.id);
		}
	});
}

function doSelectPosition(name) {
	var cPid = fastDev.getInstance('orgid').getValue();
    var url=window.location.href;
    var index=-1;
    for(var i=0;i<4;i++){
    	index=url.indexOf("/",index+1);
    }
    url=url.substring(0,index);
	fastDev.create('Window', {
		width : 300,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : "选择部门组织",
		allowResize : false,
		src : url+'/xwzcxt/taskerror/PositionTree/DynamicPositionTree.html',
		buttons : [ {
			id : 'ok',
			text : '确定',
			align : "center",
			iconCls : 'icon-save',
			onclick : function(event, win, childWin, fast) {
				var node=childWin.getSelectedNodes()[0];
				if(!node||node['id']=='1--1'){
					win.close();
					return;
				}				
				var id=node['id'];
				var prefix=id.substring(0,2);
				if(prefix=='1-'){
					fastDev.getInstance("orgid").setValue(id.substring(2));
					fastDev.getInstance("positionid").setValue(null);
					fastDev.getInstance("c_exec_userid").setValue(null);
				}else if(prefix=='3-'){
					fastDev.getInstance("orgid").setValue(null);
					fastDev.getInstance("positionid").setValue(null);
					fastDev.getInstance("c_exec_userid").setValue(id.substring(2));
				}else{
					fastDev.getInstance("orgid").setValue(null);
					fastDev.getInstance("positionid").setValue(id.substring(2));
					fastDev.getInstance("c_exec_userid").setValue(null);
				}
				
				fastDev.getInstance("name").setValue(node['name']);
				win.close();
				
			}
		}, {
			id : 'clear',
			text : '清空',
			align : "center",
			iconCls : 'icon-reset',
			onclick : function(event, win) {
				fasDev.getInstance("name").setValue(null);
				fastDev.getInstance("orgid").setValue(null);
				fastDev.getInstance("positionid").setValue(null);
				fastDev.getInstance("c_exec_userid").setValue(null);
				win.close();
			}
		} ]
	});
}


function onSelectArea() {
	fastDev.confirm("是否需要修改当前区域？", "信息提示", function(result) {
		if (result) {
			doSelectArea();
		}
	});
}

function doSelectArea() {
	var cAreaid = fastDev.getInstance("c_area_id").getValue();
	var index=-1;
	var url=window.location.href;
	for(var i=0;i<4;i++){
	    	index=url.indexOf("/",index+1);
	}
	url=url.substring(0,index);
	
	fastDev.create('Window', {
		width : 300,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : "选择区域",
		allowResize : false,
		src : url+'/xwzcxt/task/task_templet/templetSelectArea.html?cAreaid=' +
			cAreaid,
		buttons : [ {
			id : 'ok',
			text : '确定',
			align : "center",
			iconCls : 'icon-save',
			onclick : function(event, win, childWin, fast) {
				var cAreaid = fast.getInstance("cArea").getCurrentId();
				if (!cAreaid) {
					win.close();
				} else {
					var cAreaName = fast.getInstance("cArea").getCurrentTxt();
					if("1000100000001" != cAreaid) {
						var id = fast.getInstance("cArea").getParentid(cAreaid);
						while("1000100000001" != id) {
							cAreaName = fast.getInstance("cArea").getValByid(id) + cAreaName;
							id = fast.getInstance("cArea").getParentid(id);
						}
					}
					setArea(cAreaid, cAreaName);
					win.close();
				}
			}
		}, {
			id : 'clear',
			text : '清空',
			align : "center",
			iconCls : 'icon-reset',
			onclick : function(event, win) {
				setArea("", "");
				win.close();
			}
		} ]
	});
}

function setArea(cAreaid, cAreaName) {
	fastDev.getInstance("c_area_id").setValue(cAreaid);
	fastDev.getInstance("areaname").setValue(cAreaName);
}


function deleteMany(){
	var items=fastDev.getInstance("grid").getValue();
	 if(!items){
	    	fastDev.tips("请选择数据！");
	    	return;
	    }
		fastDev.confirm('是否确定删除选择行的数据？','数据删除提示',function(result){
			if(result){
				   id="";
				   for(var i=0;i<items.length;i++){
					   	id+=items[i].c_task_idStr+",";
					   
				   }
			 
			fastDev.post("taskAndErrManager_deleteTask.action",{
				success:function(data){
				   fastDev.tips(data);
				   fastDev.getInstance("grid").refreshData(false);
				},
			    data:{'ids':id}
			});
				  
				   }
		});
}

function toOperation(value){
	var status=fastDev.getInstance('grid').getValue(this)[0].c_status;
	if(status=='44'){
		return '';
	}
	return "<a href='javaScript:void(0);' id='delete' name='delete' onclick=deleteOne('"+value+"') >删除</a>";
}

function deleteOne(id){
	 if(!id){
	    	fastDev.tips("请选择数据！");
	    	return;
	    }
	 console.info(id);
		fastDev.confirm('是否确定删除选择行的数据？','数据删除提示',function(result){
			if(result){  
			fastDev.post("taskAndErrManager_deleteTask.action",{
				success:function(data){
				   fastDev.tips(data);
				   fastDev.getInstance("grid").refreshData(false);
				},
			    data:{'ids':id}
			});
				  
				   }
		});
}

function timeRenderer(value){
	value=(value+"").replace("T"," ");
	return value;
}