var request = fastDev.Browser.getRequest();
//通过action参数进行信息读取，以确认操作的对象为子项(1)、子项类别(2)、基础项(3)
var item = request['item'];
//刷新条件
var condition=null;
var checkDialog;
var delectAction = "task_delete.action?action=1"; 

var seqId = -1;

var operTpl = '&nbsp;<a name="update" onclick="clickRow()">修改</a>';
var editCalDialog;

/**
 * 关闭对话框
 */

function closeDialog() {
	if(checkDialog) {
		checkDialog.close();
		checkDialog = null;
	}
}

function writeYear() {
	var curYear = new Date().getFullYear();
	var i = 3; // 年份可以选择去年、今年、明年
	var text = "";
	var Year = curYear - 1;
	// alert(curYear+","+i);
	while (i > 0) {

		if (curYear != Year) {
			text += '<option value="' + Year + '">' + Year
					+ '</option>';
		} else {
			text += '<option value="' + curYear + '" selected>' + curYear
					+ '</option>';
		}
		Year++;
		i--;
	}
	document.write(text);
	document.close();
}

function writeMonth() { // 生成月份下拉框的选项，并把当前月份设为所选
	var curMonth = new Date().getMonth() + 1;
	var text = '';

	for ( var i = 1; i <= 12; i++) {
		if (i != curMonth) {
			text += '<option value="' + i + '">' + i + '</option>';

		} else {
			text += '<option value="' + curMonth + '" >' + curMonth
					+ '</option>';
		}
	}
	// alert(text);
	document.write(text);
	document.close();
}



var year;
var month;
var endYearNum;
var endMonthNum;
var endYear;
var endMonth;
var startTime;
var endTime;

function onchangeYear(){
    year=fastDev.getInstance("year").getValue();
	month=fastDev.getInstance("month").getValue();
	if(month=="12"){
		 endYearNum=Number(year)+1;
		 endYear=String(endYearNum);
		 endMonth="1";
		 startTime=year+"-"+month+"-"+"1";
		 endTime=endYear+"-"+endMonth+"-"+"1";
		 //alert(startTime+"-----"+endTime);
	}else if(month=="-1"){
		 
		 endYearNum=Number(year)+1;
		 endYear=String(endYearNum);
		 month="1";		 
		 startTime=year+"-"+month+"-"+"1";
		 endTime=endYear+"-"+month+"-"+"1";
		 //alert(startTime+"-----"+endTime);
	}else{
		endMonthNum=Number(month)+1;
	    endMonth=String(endMonthNum);
		startTime=year+"-"+month+"-"+"1";
	    endTime=year+"-"+endMonth+"-"+"1";
		//alert(startTime+"-----"+endTime);
	}	
	fastDev.getInstance("startTime").setValue(startTime);
	fastDev.getInstance("endTime").setValue(endTime);
}

function onchangeMonth(){
    year=fastDev.getInstance("year").getValue();
	month=fastDev.getInstance("month").getValue();
	if(month=="12"){
		 endYearNum=Number(year)+1;
		 endYear=String(endYearNum);
		 endMonth="1";
		 startTime=year+"-"+month+"-"+"1";
		 endTime=endYear+"-"+endMonth+"-"+"1";
		 //alert(startTime+"-----"+endTime);
	}else if(month=="-1"){		 
		 endYearNum=Number(year)+1;
		 endYear=String(endYearNum);
		 month="1";		 
		 startTime=year+"-"+month+"-"+"1";
		 endTime=endYear+"-"+month+"-"+"1";
		 //alert(startTime+"-----"+endTime);
	}else{
		endMonthNum=Number(month)+1;
	    endMonth=String(endMonthNum);
		startTime=year+"-"+month+"-"+"1";
	    endTime=year+"-"+endMonth+"-"+"1";
		//alert(startTime+"-----"+endTime);
	}
	fastDev.getInstance("startTime").setValue(startTime);
	fastDev.getInstance("endTime").setValue(endTime);
}


function onBeforeReady(){
	this.setOptions({
		initSource : "calendar_queryCalendarByPage.action"
	});	 
};


/*function onRowClick(event,rowindex,data) {		
	var target = event.target;
	var name = target['name'];
	console.info(event.target.name);
	if(event.target.name) {
		name = fastDev.Util.StringUtil.capitalize(name);
		if(window['do' + name]) {
			window['do' + name].call(window, data);
		}
	}
}*/


function operRenderer(value) {
	var width = fastDev(this).width();
	return ['<div style="width:'+width+'px;"><a href="javascript:void(0);" class="btn" id="modify" style="margin-left:5px;display:none;" name="editCal" onclick="clickRow()">修改</a>'].join('');
}


function onRowClick(event, id, value) {
	//alert(value.c_id);
	editCalendar(value.c_id);
}

function clickRow(){
}

function editCalendar(seq){
	var src = "editCalendar.html?seq="+seq;
	createWindow({
		src : src,
		title : "编辑日历信息",
		width : "625",
		height: "190",
		buttons : [{
			text : '保存',
			iconCls : 'icon-save', 
			onclick : function(e,win,cwin, fd) {
				var form=fd.getInstance("editCalendarForm");
				form.submit();		
			}
		}]		
	});
}
	
function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body-100);
}


function onAfterDataRender(){
	 year=fastDev.getInstance("year").getValue();
	 var startTime=year+'-'+'1'+'-'+'1';  
	 fastDev.getInstance('startTime').setValue(startTime);
	 var endTime=year+'-'+'12'+'-'+'31';
	 fastDev.getInstance('endTime').setValue(endTime);
	 doSearch();
	
}

function createWindow(o) {
	var config = fastDev.apply({
		width : "640",
		height : "200",
		allowResize : false,
		showMaxBtn : false
	}, o || {});
	
	(config.buttons = config.buttons || []).push({
		text : "关闭",
		iconCls : "icon-close",
		onclick : function(e,win) {
			win.close();
		}
	});		
	appDialog = fastDev.create('Window', config);
}
/**
 * 批量删除信息
 */
function doBatchDeleteCheckModel() {
	var datagrid = fastDev.getInstance('grid1');
	var items = datagrid.getValue();
	if(items.length <=0 ) {
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	} else {
		fastDev.confirm("确认是否删除选择的" + items.length + "条记录？", "信息提示", function(result){
			if(result){
				var ids = [];
				for(var i = 0; i < items.length; i++) {
					ids.push(items[i].c_basic_id);
				}
				deleteCheckModelById(ids.join(","));
			}
		});
	}
}

function deleteCheckModelById(id) {
	var proxy = fastDev.create('Proxy', {
		action : delectAction
	});
	proxy.save({deleteID:id}, function(result){
		fastDev.alert(result.msg, '信息提示', result.status, function(){
			if(result.status == 'ok') {
				refreshDatagrid();
			}
		});
	});
}
/**
 * 过滤信息
 */
function doSearch() {
	var fm = fastDev.getInstance('checkform');
	condition = fm.getItems();
	console.info(condition);
	refreshDatagrid();
}
/**
 * 重置表单数据
 */
function doReset() {
	fastDev.getInstance('checkform').cleanData();
}
/**
 * 刷新datagrid
 * @param o
 */
function refreshDatagrid() {
	//alert(fastDev.Util.JsonUtil.parseString(condition));
	fastDev.getInstance('grid1').refreshData(condition);
}


function refreshDatagrid2(){
	fastDev.getInstance('grid1').refreshData(false);
}


