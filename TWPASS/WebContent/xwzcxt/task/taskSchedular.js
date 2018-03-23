var request = fastDev.Browser.getRequest();
// 通过action参数进行信息读取，以确认操作的对象为子项(1)、子项类别(2)、基础项(3)
var item = request['item'];
// 刷新条件
var condition = null;
var currentGrid=null;

fastDev(function(){
	currentGrid=changeGrid();
	//alert("0000");
});

function doResetOrg() {
	fastDev.getInstance('orgform').cleanData();
	condition = null;
}

/**
 * 过滤信息
 */
function doSearch() {

	condition = null;
	condition = fastDev.getInstance('orgform').getItems();
	var dg = changeGrid();
	currentGrid=dg;
	dg.refreshData(condition);
}

function onBeforeReady() {
	this.setOptions({
		initSource : 'taskSchedular_getPersonSchedular.action'
	});
}

/*function onAfterInitRender() {

	var body = fastDev(window).height();
	this.setHeight(body - 120);
	//changeGrid();
	showResourceModes('JC_4');
}*/


function onAfterDataRender(){
	var body = fastDev(window).height();
	this.setHeight(body-80);
	//changeGrid();
	showResourceModes('JC_4');
}


function getTotalDays() {
	var year = fastDev.getInstance('year').getValue();
	var month = fastDev.getInstance('month').getValue();
	var totalDays = new Date(year, month, 0).getDate();
    return totalDays;
}

function changeGrid(){
	var days=getTotalDays();
	
	var dg=null;
	//var div=null;
	switch(days){
	case  31:dg=fastDev.getInstance('grid1');
			// div=document.getElementById('g1'); alert(31+"^^^");
			 break;
	case  30:dg=fastDev.getInstance('grid2');
		    // div=document.getElementById('g2');
		     break;
	case  29:dg=fastDev.getInstance('grid3');
			// div=document.getElementById('g3');
			 break;
	case  28:dg=fastDev.getInstance('grid4');
			// div=document.getElementById('g4');
			 break;
	}
	
	if(dg!=currentGrid&&currentGrid!=null){
		currentGrid.hide();
	}
	for(var i=1;i<=4;i++){
		fastDev.getInstance('grid'+i).hide();;
	}
	dg.show();
	return dg;
}

// function writeCurrWeekday() { // 按当前年份和月份生成DataGrid的表头
// var date = new Date();
// writeWeekday(date.getFullYear(), date.getMonth());
// }

function writeYear() {
	var curYear = new Date().getFullYear();
	var i = 3; // 年份可以选择去年、今年、明年
	var text = "";
	var startYear = curYear - 1;
	// alert(curYear+","+i);
	while (i > 0) {

		if (curYear != startYear) {
			text += '<option value="' + startYear + '">' + startYear
					+ '</option>';
		} else {
			text += '<option value="' + curYear + '" selected>' + curYear
					+ '</option>';
		}
		startYear++;
		i--;
	}
	document.write(text);
	document.close()
}

function writeMonth() { // 生成月份下拉框的选项，并把当前月份设为所选
	var curMonth = new Date().getMonth() + 1;
	var text = '';

	for ( var i = 1; i <= 12; i++) {
		if (i != curMonth) {
			text += '<option value="' + i + '">' + i + '</option>';

		} else {
			text += '<option value="' + curMonth + '" selected>' + curMonth
					+ '</option>';
		}
	}
	// alert(text);
	document.write(text);
	document.close();
}

function writeHead() {
	// alert(111);
	var text = "";
	for ( var i = 1; i <= 31; i++) {
		text += '<div width="150px" name="day'
				+ i
				+ '" text="'
				+ i
				+ '"'
				+ 'align="center" titleStyle="text-align:center"  renderer="toDiv()" ></div>';
	}
	text+='<div  hidden="true"  name="c_userid"></div>'+
	      '<div  hidden="true"   name="year_month"></div>'+
	      '<div  hidden="true"   name="c_org_id"></div>';
	document.write(text);
	document.close();
}

function toDiv(value) {
	return [ '<div  onclick="changeColor(this)">' + value + '</div>' ].join('');
}

var currentCell = null;
var currentRow = null;
function changeColor(obj) {
	if (currentCell != null) {
		currentCell.style.background = "";
		fastDev(currentCell).parents("tr").children("td").css(
				"background-color", "");
	}
	currentCell = obj;
	fastDev(obj).parents("tr").children("td")
			.css("background-color", "#CABBCC");
	obj.style.background = "#A4D9AD";

}

function showUsers(value) {
	var userList = fastDev.getInstance('member');
	userList.clean(true);
	userList.initRefresh({
		"urlParam" : {
			"department" : value
		}
	});
	// alert(value);
}

function importSchedular() {
	// alert("***");
	fastDev.create("Window", {
		title : "排班导入",
		width : "600",
		height : "400",
		src : "schedularImport.html",
		buttons : [ {
			text : "提交",
			width : "100px",
			align : "center",
			iconCls : 'icon-save',
			onclick : function(event, win, cwin, fast) {
				cwin.doSave(win);
			}
		}, {
			text : "重置",
			width : "100px",
			align : "center",
			iconCls : 'icon-reset',
			onclick : function(event, win, cwin, fast) {
				cwin.doReset();
			}
		},{
			text : "关闭",
			width : "100px",
			align : "center",
			iconCls : 'icon-close',
			onclick : function(event, win, cwin, fast) {
				   win.close();
			}
		} ]
	});
}

var c_userid = null;
var c_date = null;
function getColumnIndex(event, cellindex, content) {
    if(content!=null){    //过滤点击空白单元格的情况
    	return;
    }
	var grid = currentGrid;
	if(grid==null)return;
	c_userid = grid.getValue(currentCell)[0].c_userid;
	var day = '';
	cellindex-=1;
	if ((cellindex + "").length < 2) {
		day = '-0' + cellindex;
	} else {
		day = '-' + cellindex;
	}
	c_date = grid.getValue(currentCell)[0].year_month + day;
	//alert(c_userid+","+c_date);
}

function modify() {
	//alert(currentCell+","+currentRow+","+c_date+","+c_userid);
	if(currentCell==null
			||c_date==null
			||c_date==''
			||c_userid==null
			||c_userid==''
	){
		//alert("****###");
		fastDev.tips("请选择先要修改的排班数据");
		return;
	}
	//alert("****");
	var shiftInfo=currentGrid.getValue(currentCell)[0];
	var c_org_id=shiftInfo.c_org_id;
	var c_username=shiftInfo.name;
	var url = "schedularModify.html?c_date="+c_date+"&c_userid="+c_userid+"&c_org_id="+c_org_id+"&c_username="+c_username;
	//alert(url);
	fastDev.create("Window", {
		title : "排班修改",
		width : "600",
		height : "200",
		src : url,
		buttons : [ {
			text : "提交",
			width : "100px",
			align : "center",
			iconCls : 'icon-save',
			onclick : function(event, win, cwin, fast) {
				cwin.doSave(win,currentCell);
			}
		}, {
			text : "重置",
			width : "100px",
			align : "center",
			iconCls : 'icon-reset',
			onclick : function(event, win, cwin, fast) {
				cwin.doReset();
			}
		},{
			text : "关闭",
			width : "100px",
			align : "center",
			iconCls : 'icon-close',
			onclick : function(event, win, cwin, fast) {
				win.close();
			}
		} ]
	});
}

