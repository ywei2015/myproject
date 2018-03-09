
var searchpanel = {
	name  : "search",   
    type  : "searchpanel", 
    title : "查询条件",
    berth : "SearchPanel",
    items : [
        { 
        	type:"textinput",
            title:"设备名称",
            dataIndex:'equName'
        },
        { 
        	type:"combox",   
			title:"设备种类",
			dataIndex: 'equKind',
			fieldText: 'name',
			fieldValue: 'pid',
			//data:[{'code':0,'name':'点检'},{'code':1,'name':'保养'},{'code':2,'name':'润滑'}],
			url: "../../../../dynamic/dicView/listDic?view=v_equ_kind",
			root: "dataset.data"
        },
        { 
        	type:"combox",   
			title:"设备状态",
			dataIndex: 'status',
			fieldText: 'name',
			fieldValue: 'code',
			//data:[{'code':1,'name':'正常'},{'code':10,'name':'停用'}
			     // ,{'code':20,'name':'维修'},{'code':40,'name':'报废'}],
			url: "../../../../dynamic/dicView/listDic?view=v_equ_status",
			root: "dataset.data"
        }
		
    ],
    layoutConfig : {
        columns : 4
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
        idField: "pid", //标识哪个字段为id主键
        uniqueId: "pid",   //每一行的唯一标识，一般为主键列
        sidePagination: "server", //分页方式：client 客户端分页，server 服务端分页（*）
        showColumns: true, //是否显示所有的列
        minimumCountColumns: 2, //最少允许的列数
//      showToggle: true, //是否显示详细视图和列表视图的切换按钮
        toolbar: '#toolbar', //工具按钮用哪个容器
	    columns: [
		    {field: 'checkStatus',checkbox: true, edit:false},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
	        {field: 'pid', visible:false, edit:false},   
	        {
		        field: 'equName',
		        align: "center",
		        title: '设备名称' 
		   },{
		        field: 'equCode',
		        title: '设备编号', 
		        align: 'center'
		    },{
		        field: 'kindName',
		        title: '设备种类', 
		        align: 'center'
		    },{
		        field: 'orgName',
		        title: '部门', 
		        align: 'center'
		    },{
		        field: 'manufacturer',
		        title: '生产厂家' ,
		        align: 'center',
		    },{
		        field: 'equModel',
		        title: '设备型号',
		        align: 'center'
		    },{
		        field: 'status',
		        title: '设备状态', 
		        align: 'center',
		        formatter:function(value,row,index){
		        	return showStatus(row);
		        }
		    },{
		        field: 'location',
		        title: '安装位置', 
		        align: 'center'
		    },{
		        field: 'purpose',
		        title: '设备用途', 
		        align: 'center'
		    },{
		        field: 'remark',
		        title: '备注', 
		        align: 'center'
		    }, {
		    	field:"action",title:"操作",align:"center", width : '80',
		    	formatter:function(value,row,index){
			    	var modifyHtml = '<a href="#" class="modify"><li id="modify" class="glyphicon glyphicon-pencil"> 修改  </li></a>';
		    		return modifyHtml;
		    	},
		    	events: {
                    'click .modify': function (e, value, row, index) { 
                        modifyRow(e, value, row, index);//修改操作 
                    }
               },
		    	edit:false
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
	    }
	});
	refreshTable();//首次调用 
	SF.SearchPanel.onload(searchpanel);//搜索框的加载
	 
	//新增
	 $("#BtnAdd").click(function(){
		 var sdata = {"info":null};
	      var win = SF.showModalDlg("addInfo.html","add",sdata,800,500,dlg_callback);
	 });
     
	 
	 
	 //删除
	 $("#BtnDel").click(function(){
	 	//console.info($('#table').bootstrapTable('getSelections'));
	 	var rowdata = $('#table').bootstrapTable('getSelections');
	 	console.log(rowdata);
	 	if(rowdata.length==0){
	 		BootstrapDialog.alert("请选择一条记录!");
	 	}else{
	 		BootstrapDialog.show({
	 			  title : "提示",
	 			  message : "是否删除数据！",
	 			  buttons : [
	 			   {
	 			      label : "确定",
	 			      cssClass : "btn-primary",
	 			      icon : "",//通过bootstrap的样式添加图标按钮
	 			     action : function(dialog){   //给当前按钮添加点击事件
	 			    	var ids = '';
	 				 	for(var i=0;i<rowdata.length;i++){ 
	 				 		ids+=rowdata[i].pid+",";
	 				 	}
	 				 	ids=ids.substring(0,ids.length-1);
	 				 	$.post("../../../../dynamic/TzInfo/deletesInfo",{ids:ids},function(result){	
	 				 		refreshTable();
	 				 		dialog.close();
	 			    	},"json");
		 			      }
	 			    },{
	 			      label : "取消",
	 			      cssClass : "btn-secondary",
	 			      icon : "",
	 			      action : function(dialog){   //给当前按钮添加点击事件
	 			            dialog.close();
	 			      }
	 			    }
	 			  ]
	 			});
		 	
	 	}
	 });
});

var dlg_callback =function(e){
	console.log(e);
	if(e=='ok'){
		refreshTable();
		BootstrapDialog.alert("操作成功！");
	}
}

//用户信息修改
function modifyRow(e, value, row, index){
	var sdata = {"info":row};
    var win = SF.showModalDlg("addInfo.html","update",sdata,800,500,dlg_callback);
}


//重新查询、重新排序、切换分页事件
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
       	async: false,
	   	//url: MesWebApiUrl + "/sec/role/getpage",       
       	url: "../../../../dynamic/TzInfo/findInfoList",
       	data: {
       		'page' : number,
       		'limit' : size,
       		'equName' : objsSearch.equName,
       		'equKind' : objsSearch.equKind,
       		'status'  : objsSearch.status
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


function showStatus(row){
	if(row.status==1){
		return "正常"
	}else if(row.status==10){
		return "停用";
	}else if(row.status==20){
		return "维修";
	}else if(row.status==40){
		return "报废";
	}
}