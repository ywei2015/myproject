var request = fastDev.Browser.getRequest();
//var typeid = request['typeid'];  //表单里面的隐藏域中的类型对象

var griddiv = null;
var condition = null;

var column=null;  //DataGrid的列数组
var tmpColumn=null; //临时DataGrid的列数组
var grid=null;   //DataGrid对象
var form=null;  //表单对象
var cTabletypeId= null;

fastDev(function (){
	//grid=fastDev.getInstance('jobObjectsInfo').getInstance();
//	var data=[{"name":"mzl","sex":"M"},{"name":"xqf","sex":"M"},
//	          {"name":"zz","sex":"M"},{"name":"dd","sex":"W"}];
//	var data2=[{"name":"mzl","sex":"M"},{"name":"xqf","sex":"M"},
//	          {"name":"zz","sex":"M"},{"name":"dd","sex":"W"}];
//	var val=fastDev.Util.JsonUtil.parseJson(data);
//	var val2=fastDev.Util.JsonUtil.parseJson(data2);
	doSearchEx(null);
	//alert("--HOHO--"+typeid);
});

function onBeforeReady(){
	url="jobObjects_getObjectInfo.action"; //?cTabletypeId=11001
	this.setOptions({
		initSource:url
	});
	//alert("onBeforeReady!");
}

function onAfterInitRender(){
	var body=fastDev(window).height();
	this.setHeight(body-80);
	//alert("onAfterInitRender!"); 
}

/*
function writeColumn(condition){
	var url="jobObjects_getColumnsConfig.action";
	if(condition==null||condition==undefined){
		return;
	}
	
	fastDev.post(url,{
		success:function(data){
			column=data;
			//test(data);
		
			if(grid==null){
				grid=fastDev.getInstance('jobObjectsInfo');
			}
			grid.showColumn('operation');
			for(var j=0;tmpColumn!=null&&j<tmpColumn.length;j++){   //移除之前存在的所有列
				grid.removeColumn(tmpColumn[j].name);
			}
			if(column.length==undefined){
				return;
			}
			
			for(var i=0;i<column.length;i++){  //重新设定新得到的列
				//alert(column[i]);
				grid.addColumn(column[i],i+3);
				//alert("增加第"+i+"列："+fastDev.Util.JsonUtil.parseString(column[i]));
			}
			//alert(fastDev.Util.JsonUtil.parseString(column));
			
			tmpColumn=column;
		},
		data:condition
	});
}
*/

function doResetjobObjects() {
	fastDev.getInstance('jobObjects').cleanData();
	
	grid=null;
	if(grid==null){
		grid=fastDev.getInstance('jobObjectsInfo');
	} 
	grid.setOptions({ initSource:"jobObjects_getObjectInfo.action?cTabletypeId=11001" }); //?cTabletypeId=11001
	grid.refreshData(condition); 
}


function doSearch() {
	//var condition = null;
	if(form==null){
		form = fastDev.getInstance("jobObjects");
	}
	condition = form.getItems();
	console.info(condition);
	if(grid==null){
		grid=fastDev.getInstance('jobObjectsInfo');
	}
	//alert(fastDev.Util.JsonUtil.parseString(condition));
	//writeColumn(condition);
	grid.refreshData(condition);
	console.info(condition);
}

function getTypeValue(event,value){
	if(cTabletypeId==null){
		cTabletypeId=fastDev.getInstance('cTabletypeId');
	}
	cTabletypeId.setValue(value);
	//doSearch(); 
	doSearchEx();
	DyCreateDataGrid(condition); 
}

function multiDelete(){
    if(grid==null || grid.getValue().length==0){
    	fastDev.tips("请选择数据！");
    	return;
    }
	fastDev.confirm('是否确定删除选择行的数据？','数据删除提示',function(result){
		if(result){
			   id="";
			   var items=grid.getValue();
			   for(var i=0;i<items.length;i++){
				   	id+=items[i].cBasedataId+",";
				   
			   }
			   deleteData(id);
			  
			   }
	});
}


function deleteData(cBasedataId){
	var url="jobObjects_deleteObjects.action";
	fastDev.post(url,{
		success:function(data){
			grid.refreshData(false);
			fastDev.tips(data);
		},
	    data:{"cBasedataId":cBasedataId}
	});
}

function addObject(cBasedataId){
	var tabletypeId=cTabletypeId.getValue();
	var url="objectDetails2.html?cBasedataId="+cBasedataId+'&cTabletypeId='+tabletypeId;
	fastDev.create("Window",{
		title:"新增作业对象",
		width:"800px",
		height:"300px",
		src:url,
		buttons:[{
			text:"提交",
			width:"100px",
			align:"center",
			iconCls : 'icon-save', 
			onclick : function(e,win,cwin, fd){
				 cwin.doSave();
				 cwin.close();
			}
		},{
			text:"重置",
			width:"100px",
			iconCls : "icon-reset",
			align:"center",
			onclick:function(event,win,cwin,fast){
				//alert(111);
				cwin.resetForm();
			}
		}
		         ]
	});
}

function toOperationLink(){
	var value=grid.getValue(this)[0].cBasedataId;
	var printInfo="";
	return '<a href="javaScript:void(0);" onclick=addObject('+value+') >修改</a>&nbsp;&nbsp;'+
	       '<a href="javaScript:void(0);" onclick=deleteData('+value+') >删除</a>&nbsp;&nbsp;'+
	       '<a href="javaScript:alert('+value+');" onlcick= >打印二维码</a>';
}

function initForm(jobObject){
	var el=null;
	for(var item in jobObject){
	    el=	document.getElementById(item);
	   if(el!=null && el!=undefined){
		   el.value=jobObject[item];
	   }
	}
}



/**
 * 过滤信息
 */
function doSearchEx() {
	condition = {};
	if(form==null){
		form = fastDev.getInstance("jobObjects");
	}
	condition = form.getItems(); 
	//condition["cTabletypeId"] = typeid;
	onRefreshDatagrid();
	//WriteGrid(condition);
	//console.info(condition);
}

function DyCreateDataGrid(condition){
	if(condition==null||condition==undefined){
		return;
	} 
	var url="jobObjects_getColumnsConfig.action";
	fastDev.post(url,{
		success:function(data){
		    var htmlstr = condition["cTabletypeId"]+"<br>----Start....--<br>";
		    //var htmlstr = typeid+"<br>----Start....--<br>";
		    htmlstr += '<div itype="DataGrid" id="jobObjectsInfo" saveInstance="true"';
		    //htmlstr += ' initSource="jobObjects_getObjectInfo.action?cTabletypeId="'+condition["cTabletypeId"];
		    htmlstr += ' onAfterDataRender="onAfterInitRender()" OnBeforeReady="onBeforeReady()"';
		    htmlstr += ' allowResizeColumn=true pageSize=20 showSeqColumn=true showCheckColumn=true>';
		    htmlstr += '<div name="top-toolbar">';
		    htmlstr += '<div plain=true text="新增" iconCls="icon-add" id="add"  onclick="addOne()"></div>';
		    htmlstr += '<div itype="separator"></div><div plain=true text="批量删除" iconCls="icon-delete" id="d" onclick="multiDelete()"></div>';
		    htmlstr += '</div>';
		    //htmlstr += '<div name="CTabletypeId" text="唯一编号A" width="150px"></div>';
		    				 
			column=data; 
			for(var i=0;i<column.length;i++){  //重新设定新得到的列 
				var tmpColumn= '<div width="15%" name="'+column[i].name+'" text="'+column[i].text+'" ></div>';
				//alert(tmpColumn);
				htmlstr += tmpColumn; 
			}  
			//htmlstr += '<div name="cBasedataCode" text="DType" width="150px"></div>';
			htmlstr += '<div name="operation" text="操作" width="150px" renderer="toOperationLink()"></div>';
			htmlstr += '</div>';
		    document.getElementById('gridDiv').innerHTML = htmlstr; //griddiv.innerHTML = innerHTml;
		    //console.info(htmlstr); 
		    
			/**/
		    if(fastDev.isFunction(window.onBeforeCompile)) {
				onBeforeCompile();
			}
			fastDev.Core.ControlBus.compile();
			if(fastDev.isFunction(window.onAfterCompile)) {
				onAfterCompile();
			}   
			
			onRefreshDatagrid(); 
			fastDev.Core.ControlBus.compile();
		},
		data:condition
	}); 
}

function onRefreshDatagrid(){ 
	grid=null;
	if(grid==null){
		grid=fastDev.getInstance('jobObjectsInfo');
	}
	if(grid==null) return;
	grid.setOptions({ initSource:"jobObjects_getObjectInfo.action" }); //?cTabletypeId=11001
	grid.refreshData(condition);  
}


function onRefreshDatagridEx(){	
	grid=null;
	if(grid==null){
		grid=fastDev.getInstance('jobObjectsInfo');
	}
	if(grid==null) return;
	grid.setOptions({ dataSource:"jobObjects_getObjectInfo.action" });
	grid.refreshData(condition);  
}
