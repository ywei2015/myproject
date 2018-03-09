var searchpanel = {
	name  : "search",   
    type  : "searchpanel", 
    title : "查询条件",
    berth : "SearchPanel",
    items : [
    	{ 
        	type:"datetime",
            title:"开始时间",
            dataIndex:'stateTime',
            config:{
	            format: 'yyyy-mm-dd',
		    	minView:'month',
				initialDate: new Date()
			}
        },
        { 
        	type:"datetime",
            title:"结束时间",
            dataIndex:'endTime',
            config:{
	            format: 'yyyy-mm-dd',
		    	minView:'month',
				initialDate: new Date()
			}
        },
        { 
        	type:"textinput",
            title:"用户名称",
            dataIndex:'name'
        },
        { 
        	type:"textinput",
            title:"编码",
            dataIndex:'code'
        }
    ],
    layoutConfig : {
        columns : 3
    },
  onsearch: function(){
	  refreshTable();
  }
};

var $table;

$(function () {
	
	 $table = $('#table').bootstrapTable({ 
        locale:'zh-CN',//中文支持 
        pagination: true,//是否开启分页（*）
//      striped:true,  //奇偶行渐色表
        pageSize: 10,//每页的记录行数（*）
        pageList: [10,25,50,100],//可供选择的每页的行数（*）
        idField: "id", //标识哪个字段为id主键
        uniqueId: "id",   //每一行的唯一标识，一般为主键列
        sidePagination: "server", //分页方式：client 客户端分页，server 服务端分页（*）
        showColumns: true, //是否显示所有的列
        minimumCountColumns: 2, //最少允许的列数
//      showToggle: true, //是否显示详细视图和列表视图的切换按钮
        toolbar: '#toolbar', //工具按钮用哪个容器
	    columns: [
	        {
				field : 'userName',
				align:"center",
				title : '用户名称'
			},{
				field : 'userCode',
				align:"center",
				title : '用户编码'
			}, {
				field : 'loginTime',
				align:"center",
				title : '登录时间',
				formatter:function(value,row,index){
				return	stringToDate(value);
		        }
				//templet:'#loginTime_format'
			},{
				field : 'clientIp',
				align:"center",
				title : 'IP地址'
			}
	    ],
        clickToSelect: true, 
        smartDisplay:true,  
        cache : false,
	    onPageChange: function (number, size) {
	    	//var objsSearch = SF.getSearchParams();
	    	refreshTable();
	        return false;
	    },
	    onSort: function (name, order) {
	    	//var objsSearch = SF.getSearchParams();
	    	refreshTable();
	        return false;
	    },
	    onClickRow: function (item, $element,index) {}
	    
	});
	
	refreshTable();//首次调用 
	SF.SearchPanel.onload(searchpanel);//搜索框的加载
	 
});


function refreshTable() {
	var options = $table.bootstrapTable('getOptions');
    var number =  options.pageNumber;
    var size = options.pageSize;
    var sortName = options.sortName;
    var sortOrder = options.sortOrder;
    var objsSearch = SF.getSearchParams();
    console.log(objsSearch);
	$.ajax({
	   	type: "POST",
       	async: true,
	   	//url: MesWebApiUrl + "/sec/role/getpage",       
       	url: "../../../dynamic/Security/getLogList",
       	data: {
       		'page' : number,
       		'limit' : size,
       		'userCode' : objsSearch.code,
			'userName' : objsSearch.name,
			'startTime' : objsSearch.stateTime,
			'endTime' : objsSearch.endTime
       	},
	   	success: function(res){
	     	var pagedata = {};
        	pagedata.rows = res.rows;
        	pagedata.total = res.total;
        	$table.bootstrapTable('load', pagedata);
	   },
	   error: function(XMLHttpRequest, textStatus, errorThrown){
	   }
	 }); 
}

function stringToDate(value){
	var YYR=value.substring(0,4)+"-"+value.substring(4,6)+"-"+value.substring(6,8)+" ";
	var SFM=value.substring(8,10)+":"+value.substring(10,12)+":"+value.substring(12);
	var date=YYR+SFM;
	return date;
}
