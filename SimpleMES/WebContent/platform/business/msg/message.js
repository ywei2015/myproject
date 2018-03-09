  var searchpanel = {
						name  : "search",   
					    type  : "searchpanel", 
					    title : "查询条件",
					    berth : "SearchPanel",
					    items : [
					        { 
					        	type:"textinput",
					            title:"标题",
					            dataIndex:'title'
					        },
					        { 
					        	type:"textinput",
					            title:"内容",
					            dataIndex:'content'
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
  var dlg_callback =function(e){
		if(e=='ok'){
			refreshTable();
			//BootstrapDialog.alert(constant.SUCCESS_MSG);
		}
		if(e=='cancel') ;
	}
$(function() {
	 $table = $('#table').bootstrapTable({ 
	      locale:'zh-CN',//中文支持 
	      pagination: true,//是否开启分页（*）
	      //striped:true,  //奇偶行渐色表
	      pageSize: 10,//每页的记录行数（*）
	      pageList: [10,25,50,100],//可供选择的每页的行数（*）
	      idField: "id", //标识哪个字段为id主键
	      uniqueId: "id",   //每一行的唯一标识，一般为主键列
	      sidePagination: "server", //分页方式：client 客户端分页，server 服务端分页（*）
	      showColumns: false, //是否显示所有的列
	      //showRefresh: true, //是否显示刷新按钮
	      minimumCountColumns: 2, //最少允许的列数
	      showToggle: false, //是否显示详细视图和列表视图的切换按钮
	      toolbar: '#toolbar', //工具按钮用哪个容器
	      clickToSelect: true, 
	      smartDisplay:false,  
	      cache : false,
		    columns: [
			    {field: 'checkStatus',checkbox: true, edit:false},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
			    //{field: 'checkStatus',radio: true},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
		        {field: 'id', visible:false, edit:false},   
			    {
			        field: 'title',
			        title: '标题', 
			        sortable:true,
			        //editable: true,
			    	edit:false
			    }, {
			        field: 'content',
			        title: '内容'
			    }, 
			 /*   {
			        field: 'from',
			        title: '来源'  
			    }, */
			    {
			        field: 'sender',
			        title: '发送者'
			    }, 
			    {
			    	field:'planTime',
			    	title:'计划下达时间'
			    },
			    {
			        field: 'notifyType',
			        title: '通知类型',
			        formatter:function(value,row,index){
    		        	if(row.sysFlag==1){
    		        		return "文本短消息";
    		        	}
    		        	return "切到app应用并触发";
    		        }
			    },
			  /*  {
			        field: 'remark',
			        title: '备注'  
			    },*/
			    {
			        field: 'status',
			        title: '是否已读',
			        formatter:function(value,row,index){
    		        	if(row.status==10){
    		        		return "送达";
    		        	}
    		        	else if (value==20){
    		        		return "已读";
    		        	}
    		        	return "";
    		        
    		        }
			    },
			  /*  {
			        field: 'sysFlag',
			        title: '有效性',
			        formatter:function(value,row,index){
    		        	if(row.sysFlag==1){
    		        		return "有效";
    		        	}
    		        	return "无效";
    		        }
			    },*/
			    {
			    	field:"action",title:"操作",align:"center", width : '80',
			    	formatter:function(value,row,index){
				    	var modifyHtml = '<a href="#" class="modify" ><li id="modify" class="glyphicon glyphicon-pencil"> </li></a>';
			    		var strHtml = modifyHtml;
			    		return strHtml;
			    	},
			    	events: {
	                  'click .modify': function (e, value, row, index) { 
	                      modifyRow(e, value, row, index);//修改操作 
	                  }/*,
	                  'click .delete': function (e, value, row, index) { 
	                	  removeRow(row.id);//删除操作
	                  }*/
	             },
			    	edit:false
			    } 
		    ], 
		    onPageChange: function (number, size) {
		    	refreshTable();
		        return false;
		    },
		    onSort: function (name, order) {
		    	refreshTable();
		        return false;
		    }
		}); 
			refreshTable();//首次调用  
			SF.SearchPanel.onload(searchpanel); 

	});  


function modifyRow(e, value, row, index){ 
	$.post("../../../dynamic/message/updateStatus",{pid:row.pid},function(result){
		 var win = SF.showModalDlg("detailMsg.html?id="+row.pid,"update",result,800,400,dlg_callback); 
	});
   
}
 
//重新查询、重新排序、切换分页事件
function refreshTable() {
	var options = $table.bootstrapTable('getOptions'); 
    var number =  options.pageNumber;
    var size = options.pageSize;
    var sortName = options.sortName;
    var sortOrder = options.sortOrder;
    var objs = SF.getSearchParams();
	$.ajax({
	   type: "POST",
       async: true,
	   url: "../../../dynamic/message/list",       
	   data: {
		    	'page':number,
		    	'limit':size,
		    	'code':objs.title,
		    	'name':objs.content
		    
       },
	   success: function(res){
	   		//console.info(res);
	     	var pagedata = {};
        	pagedata.rows = res.rows;
        	pagedata.total = res.total;
        	//console.info(pagedata);
        	$table.bootstrapTable('load', pagedata);
        	return false;
	   },
	   error: function(XMLHttpRequest, textStatus, errorThrown){
	   }
	 }); 
} 