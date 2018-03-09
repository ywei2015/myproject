var $table2;
var dlg_callback =function(e){
	if(e=='ok')refreshTable() ;
	if(e=='cancel') ;
}
jQuery(function($) {
    //tab3表格
    $table2 = $('#table2').bootstrapTable({ 
        locale:'zh-CN',//中文支持 
        pagination: true,//是否开启分页（*）
        //striped:true,  //奇偶行渐色表
        pageSize: 10,//每页的记录行数（*）
        pageList: [10,25,50,100],//可供选择的每页的行数（*）
        idField: "id", //标识哪个字段为id主键
        uniqueId: "id",   //每一行的唯一标识，一般为主键列
        sidePagination: "server", //分页方式：client 客户端分页，server 服务端分页（*）
        showColumns: true, //是否显示所有的列
        //showRefresh: true, //是否显示刷新按钮
        minimumCountColumns: 2, //最少允许的列数
        showToggle: true, //是否显示详细视图和列表视图的切换按钮
        toolbar: '#toolbar2', //工具按钮用哪个容器
        //showRefresh:true,//刷新按钮 
        //clickToSelect:true,     //是否选中  
        //maintainSelected:true,  
        //sortable:true,
        //sortStable:true,
        //search: true,//搜索 strictSearch精确搜索
        //editable:true,//开启编辑模式  
        clickToSelect: true, 
        smartDisplay:true,  
        cache : false,
    	    columns: [
    		    {field: 'checkStatus',checkbox: true, edit:false},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
    		    //{field: 'checkStatus',radio: true},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
    	        {field: 'id', visible:false, edit:false},   
    		    {
    		        field: 'code',
    		        title: '编码', 
    		        sortable:true,
    		        //editable: true,
    		    	edit:false
    		    }, {
    		        field: 'name',
    		        title: '名称' 
    		    }, 
    		    {
    		        field: 'fullName',
    		        title: '全名称'
    		 
    		    }, 
    		    {
    		        field: 'num1',
    		        title: '排序值' 
    		    }, 
    		    {
    		        field: 'value2',
    		        title: '结构序列号' 
    		    },
    		    {
    		    	field:"action",title:"操作",align:"center", width : '80',
    		    	formatter:function(value,row,index){
    			    	var modifyHtml = '<a href="#" class="modify" ><li id="modify" class="glyphicon glyphicon-pencil"> </li></a>';
    			    //	var delHtml = '<a href="#" class="delete"><li class="glyphicon glyphicon-remove" ></li></a>';
    		    		var strHtml = modifyHtml ;
    		    		return strHtml;
    		    	},
    		    	events: {
                    'click .modify': function (e, value, row, index) { 
                    	toModify(e, value, row, index);//修改操作 
                    }
               },
    		    	edit:false
    		    } 
    	    ], 
    	    onPageChange: function (number, size) {
    	    	refreshTableFuncMenu();
    	        return false;
    	    },
    	    onSort: function (name, order) {
    	    	refreshTableFuncMenu();
    	        return false;
    	    }
    	}); 
  
	//删除数据
    $("#btnDel2").click(function(){ 
    	var rowdata = $table2.bootstrapTable('getSelections');
    	if(rowdata.length==0){
    		 BootstrapDialog.alert({
		            type: BootstrapDialog.TYPE_SUCCESS,
		            title: '提示',
		            message: "请至少选中一行！",
		            closeable: true,
		            buttonLabel: "确定"
		        });
	 	}else{
	 		if(confirm(constant.CONFIRM_DELETE)&&rowdata.length>=1){
	    		var array=new Array();
	    		for(var i=0;i<rowdata.length;i++){
	    			array.push(rowdata[i].id);
	    		}
	    		$.post("../../../dynamic/ObjStructure/deletesDicRef",{ids:array.join(",")},function(result){	
	    			refreshTableFuncMenu();
		    		
		    	},"json");
	    	}
	 	}
	}); 
    //打开子窗口，添加数据
    $("#btnAdd2").click(function(){  
    	var update=$("#Formbody");
    	var typeRefDicId= update.find("input[name='typeRefDicId']").val();
		var objEntityRefId= update.find("input[name='objEntityRefId']").val();
		var parentId=update.find("input[name='id']").val();
		var type= update.find("input[name='type']").val();
		var typeChild= update.find("input[name='typeChild']").val();
		
        var objs={"typeRefDicId":typeRefDicId,"objEntityRefId":"10001","parentId":parentId,"type":type,"typeChild":"10001"};
        var win = SF.showModalDlg("data_type_add.html","add",objs,800,400,function(e){
        	if(e){
        		refreshTableFuncMenu();
        	}
        }); 
     });
   
});


function refreshTableFuncMenu() {
	var options = $table2.bootstrapTable('getOptions'); 
    var number =  options.pageNumber;
    var size = options.pageSize;
    var sortName = options.sortName;
    var sortOrder = options.sortOrder;
    var parentId=$.fn.geturl("rightIframe","parentId");
	var structId=$.fn.geturl("rightIframe","structId");
    var objs = SF.getSearchParams();
	$.ajax({
	   type: "POST",
       async: true,
	   url: "../../../dynamic/ObjStructure/fetchFuncDicListFormaintain",       
	   //{ name: name,typeId: typeId,struId: struId,parentId: parentId }
	   data: {
		    	'page':number,
		    	'limit':size,
		    	'code':objs.code,
		    	'name':objs.name,
		    	"structId":structId,
		    	"parentId":parentId,
		    	"objEntityRefId":"10001"
		    
       },
	   success: function(res){
	     	var pagedata = {};
        	pagedata.rows = res.rows;
        	pagedata.total = res.total;
        	$table2.bootstrapTable('load', pagedata);
        	return false;
	   },
	   error: function(XMLHttpRequest,textStatus,errorThrown){
	   }
	 }); 
} 