var condition=null;
var dialog =null;

var url = window.location.pathname;
var path = url.substring(0, url.lastIndexOf("/")+1);

//为每个datagrid的单元格绑定样式 
function renderer(value) {
	var data = fastDev.getInstance("grid1").getValue(this)[0];
	fastDev(this).parents("tr").children("td").css("text-align", "center");
}
	
function onBeforeReady(){
	this.setOptions({
		initSource : "taskSearch_getMessages.action"
	});	
}

function onAfterInitRender(){
	var body = fastDev(window).height();
	this.setHeight(body-76);
	showResourceModes("XXTZ");
}

function onRowClick(event,rowindex,data) {
	
	if(data.c_msg_id!=null && data.c_msg_id!=""){
		var url=window.location.href+"";
		var index=-1;
		for(var i=0;i<4;i++){
			index++;
			index=url.indexOf("/",index);
		}
		var head=url.substring(0, index);
		url=head+"/xwzcxt/message/msgDetails.html";
		fastDev.create("Window",{
			id:"msgDetails",
			saveInstance:true,
			inside:false,
			width:"400px",
			height:"400px",
			src:url,
		}).setData("data", data);
		
		
		//修改状态
		fastDev.post("taskSearch_updateMessageState.action?message.c_msg_id="+data.c_msg_id, {
			
			success: function(data) {
				refreshDatagrid();
			}
		});
	}
}

function closeDialog(){
	if(dialog) {
		dialog.close();
		dialog = null;
	}
}

function newMsg() {
	createWindow({
		src : path+"newMessage.html",
		//src:'/TWPASS/xwzcxt/message/newMessage.html',
		title:"发起消息",
		height:300,
		width:346,
		buttons : [{
			text : '提交',
			iconCls : 'icon-save',
			onclick : function(event, win, cwin, fast) {
				cwin.doSave();
			}
		}]
	});
	console.info(url);
	console.info(path);
}

function createWindow(o) {
	var config = fastDev.apply({
		width : "900",
		height : "500",
		allowResize : false,
		inside : false,
		showMaxBtn : false
	}, o || {});
	
	(config.buttons = config.buttons || []).push({
		text : "关闭",
		iconCls : "icon-close",
		onclick : function(e,win) {
			win.close();
		}
	});		
	dialog = fastDev.create('Window', config);
}

/**
 * 过滤信息
 */
function searchForm() {
	var fm = fastDev.getInstance('checkform');
	condition = fm.getItems();
	
	refreshDatagrid();
}
/**
 * 重置表单数据
 */
function resetForm() {
	fastDev.getInstance('checkform').cleanData();
}
/**
 * 刷新datagrid
 * @param o
 */
function refreshDatagrid() {
	fastDev.getInstance('grid1').addInitReqParam(condition);
	fastDev.getInstance('grid1').refreshData(false);
}

function toFormat (value){
	renderer(value);
	var val=value+"";
	val=val.substring(0, 6)+"<font color='red'>......</font>";
	return val;
}