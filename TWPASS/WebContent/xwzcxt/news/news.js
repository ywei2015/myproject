var grid=null;   //DataGrid对象
var form=null;  //表单对象
var condition=null;
var dialog =null;

var url = window.location.href;
var path = url.substring(0, url.lastIndexOf("/")+1);

function onBeforeReady(){
	url1="news_getNewsInfo.action";
	this.setOptions({
		initSource:url1
	});
}


function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body-80);
	//showResourceModes("WDRW001");
}



function toOperationLink() {
	grid=fastDev.getInstance('newsdatagrid');
	var value=grid.getValue(this)[0].newsId;
	//fastDev.alert("value="+value);
	return '<a href="javaScript:void(0);" id="modify" onclick="modifyObject(\''+value+'\')","")>修改</a>&nbsp;&nbsp;'+
	       '<a href="javaScript:void(0);" id="delete" onclick="deleteOneNews(\''+value+'\')">删除</a>&nbsp;&nbsp;';
}



var currentTarget=null;
function changeColor(event, rowindex,data) {
    var obj=event.target;
	if(currentTarget!=null){
		fastDev(currentTarget).parents("tr").children("td").css(
				"background-color", "");
	}

	fastDev(obj).parents("tr").children("td").css("background-color", "#CABBCC");
	currentTarget=obj;
}



/*	新增
function add(){
	var src="newsInfoAdd.html";
	var title="新增新闻";
	fastDev.create("Window",{
		src : src,
		title : title,
		width : "1000",
		height : "600",
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(e,win,cwin, fd) {
				var form=fd.getInstance("addNewsForm");
				form.submit();		
			}
		},{
			text : '重置',
			iconCls : 'icon-reset',
			onclick : function(e,win,cwin, fd) {
				cwin.fastDev.getInstance("addNewsForm").cleanData();
			}
		}]
	});
}
*/


function closeDialog(){
	if(dialog) {
		dialog.close();
		dialog = null;
	}
}



function modify(){
	modifyObject(null,"add");
}

/*
function add() {
	createWindow({
		src : path+"newsInfoAdd.html",
		title:"新增新闻",
		height:600,
		width:1000,
		buttons : [{
			text : '提交',
			iconCls : 'icon-save',
			onclick : function(event, win, cwin, fast) {
				cwin.doSave();
			}
		}]
	});
}
*/



function modifyObject(newsId,type){
	
	var url=window.location.href;
	
	var index=-1;
	for(var i=0;i<4;i++){
		index=url.indexOf("/", index+1);
	}
	url=url.substring(0,index)+"/xwzcxt/news/";
    url+="newsInfoAdd.html?newsId="+newsId+"&type="+type;
    
	var title="修改新闻";
	if(type=="add"){
		title="新增新闻";
	}
	createWindow({
		src : url,
		title:title,
		height:500,
		width:1000,
		buttons : [{
			modal : true,
			text : '提交',
			iconCls : 'icon-save',
			onclick : function(event, win, cwin, fast) {
				cwin.doSave(grid,win);
			}
		}]
	});	
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
	
	(config.buttons = config.buttons || []).push({
		text : "重置",
		iconCls : "icon-reset",
		onclick : function(event,win,cwin,fast) {
			cwin.doReset();
		}
	});	
	dialog = fastDev.create('Window', config);
}



/**
 * 过滤信息
 */
function doSearch() {
	var fm = fastDev.getInstance('newsform');
	condition = fm.getItems();
	refreshDatagrid();
}




/*	多条数据删除	*/
function multiDelete(){
	//grid=fastDev.getInstance('newsdatagrid');
    if(grid==null || grid.getValue().length==0){
    	fastDev.tips("请选择数据！");
    	return;
    }
	fastDev.confirm('是否确定删除选择行的数据？','数据删除提示',function(result){
		if(result){
			   id="";
			   var items=grid.getValue();
			   for(var i=0;i<items.length;i++){
				   	id+=items[i].newsId+",";
			   }
			   deleteData(id);
			   }
	});
}



function deleteData(newsId){
	var url="news_deleteNews.action";
	fastDev.post(url,{
		success:function(data){
			grid.refreshData(false);
			fastDev.tips(data);
		},
	    data:{"newsId":newsId}
	});	
}


/*	单条数据删除	*/
function deleteOneNews(newsId){
	fastDev.confirm("是否确定删除当前行的数据？","单条数据删除提示",function(result){
		if(result){
			deleteData(newsId);
		}
	});
}


/**
 * 重置表单数据
 */
function resetForm() {
	fastDev.getInstance('newsform').resetData();
}
/**
 * 刷新datagrid
 * @param o
 */
function refreshDatagrid() {
	//var grid=fastDev.getInstance('newsdatagrid');
	grid.addInitReqParam(condition);
	grid.refreshData(false);
}


 
