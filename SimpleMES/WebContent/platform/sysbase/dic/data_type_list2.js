
 var searchpanel = {
			name  : "search",   
		    type  : "searchpanel", 
		    title : "查询条件",
		    berth : "SearchPanel",
		    items : [
		        { 
		        	type:"textinput",
		            title:"编号",
		            dataIndex:'code'
		        },
		        { 
		        	type:"textinput",
		            title:"名称",
		            dataIndex:'name'
		        }
		    ],
		    layoutConfig : {
		        columns : 4
		    },
		    onsearch: function(){
		    	refreshTable();
		    }
		};

 var formbodyConfig = {
 	name  : "Formbody",   
     type  : "FormBody", //id
     title : "基础数据维护",
     berth : "detailForm",
     items : [
         { 
         	type:"textinput",
             title:"编号",
             dataIndex:'code'
         },
         { 
         	type:"textinput",
             title:"名称",
             dataIndex:'name'
         },
         { 
         	type:"textinput",
             title:"全名称",
             dataIndex:'fullName'
         },
         { 
         	type:"numberinput",
             title:"排序值",
             dataIndex:'num1'
         },
         { 
          	type:"numberinput",
              title:"关系排序",
              dataIndex:'trdOrder'
          }, { 
           	type:"textinput",
            title:"网页地址",
            dataIndex:'value2'
        },
        { 
           	type:"textinput",
            title:"备注",
            dataIndex:'remark'
        },
        
        {
			type:"combox",   
			title:"是否有效",
			dataIndex: 'sysFlag',
			fieldText:"name",
			fieldValue:"code",
			data:[{'code':1,'name':'有效'},{'code':0,'name':'无效'}],
			//url:"../../../DataDict?type=Workshop",
			root: "dataset.item"
		}
 		
     ],
     layoutConfig : {
         columns : 2
     },
     hidefields:['typeRefDicId','objEntityRefId','parentId','type','id','typeChild'],
     onsubmit: function(){ 
    	 var objs =$("#Formbody"); //SF.getSearchParams();//var objs = SF.getFormValues(); //SF.getSearchParams();
     	forceAjax("../../../dynamic/ObjStructure/saveDic",objs,function(){
     		return false;
		});	
     }
 };
var $table;
var $table2;
var value2;
var dlg_callback =function(e){
	 BootstrapDialog.alert({
         type: BootstrapDialog.TYPE_SUCCESS,
         title: '提示',
         message: e,
         closeable: true,
         buttonLabel: "确定"
     });
	if(e=='ok')refreshTable() ;
	//if(e=='cancel') alert("子窗口已经关闭! 点了取消， 不需要刷新。"+e);
}
jQuery(function($) {
	SF.FormBody.onload(formbodyConfig); 
	//初始化当前节点，
	var parentId=$.fn.geturl("rightIframe","parentId");
  	var structId=$.fn.geturl("rightIframe","structId");
	var keyId=$.fn.geturl("rightIframe","keyId");
	var struct = window.parent.document.getElementById("structId").value;
		if(struct=="10001" || structId=="10001"){
			/*初始化表单数据*/
			value2="网页地址";
		
		}else{
			value2="结构序列号";
		
		}
	$.post("../../../dynamic/ObjStructure/dic_ref_detail",{id:keyId,structId:structId},function(result){
		//var obj=$("#form_dic_update");
		SF.BindFormData(result);
	});
	
    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        // 获取已激活的标签页的名称
        var activeTab = $(e.target).text();
        //alert(activeTab);
        // 获取前一个激活的标签页的名称
        var previousTab = $(e.relatedTarget).text();
        $(".active-tab span").html(activeTab);
        $(".previous-tab span").html(previousTab);
    });
    $('#myTab li a').click(function(){
		var id = $(this).attr('id');
		if(id == "menuDetail"){
			/*$("#tableData tbody").append('<tr id="tr1"><td>Tanmay</td>'+
					'<td>Bangalore</td></tr>');*/
		}
	});
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
    		        title: '名称', 
    		        editable: {  
                       type: 'text',  
                       validate: function (value) {  
                           if ($.trim(value) == '') {  
                               return '名称不能为空!';  
                           }  
                       }  
                   }  
    		    }, 
    		    {
    		        field: 'fullName',
    		        title: '全名称', 
    		        editable: {
                       type: 'text',  
                       validate: function (value) {  
                           if ($.trim(value) == '') {  
                               return 'Job NO不能为空!';  
                           }  
                       }  
                  }  
    		    }, 
    		    {
    		        field: 'num1',
    		        title: '排序值', 
    		        editable: {
                       type: 'text',  
                       validate: function (value) {  
                           if ($.trim(value) == '') {  
                               return 'Job NO不能为空!';  
                           }  
                       }  
                  }  
    		    }, 
    		    {
    		        field: 'value2',
    		        title: '结构序列号', 
    		        editable: {
                       type: 'text',  
                       validate: function (value) {  
                           if ($.trim(value) == '') {  
                               return 'Job NO不能为空!';  
                           }  
                       }  
                  }  
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
    	    	refreshTable2();
    	        return false;
    	    },
    	    onSort: function (name, order) {
    	    	refreshTable();
    	        return false;
    	    }
    	}); 
    $table = $('#table').bootstrapTable({ 
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
        toolbar: '#toolbar', //工具按钮用哪个容器
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
    		        title: '名称'},
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
    		        title: value2 
    		    },
    		    {
    		    	field:"action",title:"操作",align:"center", width : '80',
    		    	formatter:function(value,row,index){
    			    	var modifyHtml = '<a href="#" class="modify" ><li id="modify" class="glyphicon glyphicon-pencil"> </li></a>';
    			    	//var delHtml = '<a href="#" class="delete"><li class="glyphicon glyphicon-remove" ></li></a>';
    		    		var strHtml = modifyHtml;
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
    	    	refreshTable();
    	        return false;
    	    },
    	    onSort: function (name, order) {
    	    	refreshTable();
    	        return false;
    	    }
    	}); 
	//删除数据
    $("#btnDel2").click(function(){ 
    	var a= $table.bootstrapTable('getSelections');
    	if(confirm(constant.CONFIRM_DELETE)&&a.length>=1){
    		var array=new Array();
    		for(var i=0;i<a.length;i++){
    			array.push(a[i].id);
    		}
    		$.post("../../../dynamic/ObjStructure/deletesDicRef",{ids:array.join(",")},function(result){	
	    		refreshTable();
	    		
	    	},"json");
    	}else{ BootstrapDialog.alert({
            type: BootstrapDialog.TYPE_SUCCESS,
            title: '提示',
            message: "请至少选中一行！",
            closeable: true,
            buttonLabel: "确定"
        });}
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
        		refreshTable();
        	}
        }); 
     });
    
    
    var struct = window.parent.document.getElementById("structId").value;
	if(struct=="10001" || structId=="10001"){
		/*初始化表单数据*/
		refreshTable();
		refreshTable2();
	}else{
		$('#funcMenu').hide();
		//addValue.innerText="结构序列号";
		/*初始化表单数据*/
		refreshTable();
	}
    		SF.SearchPanel.onload(searchpanel); 
    		//删除数据
    	    $("#btnDel").click(function(){ 
    	    	var a= $table.bootstrapTable('getSelections');
    	    	if(confirm(constant.CONFIRM_DELETE)&&a.length>=1){
    	    		var array=new Array();
    	    		for(var i=0;i<a.length;i++){
    	    			array.push(a[i].id);
    	    		}
    	    		$.post("../../../dynamic/ObjStructure/deletesDicRef",{ids:array.join(",")},function(result){	
			    		refreshTable();
			    		
			    	},"json");
    	    	}else{ BootstrapDialog.alert({
		            type: BootstrapDialog.TYPE_SUCCESS,
		            title: '提示',
		            message: "请至少选中一行！",
		            closeable: true,
		            buttonLabel: "确定"
		        });}
    		}); 
    	    //打开子窗口，添加数据
    	    $("#btnAdd").click(function(){  
    	    	var update=$("#Formbody");
    	    	var typeRefDicId= update.find("input[name='typeRefDicId']").val();
    			var objEntityRefId= update.find("input[name='objEntityRefId']").val();
    			var parentId=update.find("input[name='id']").val();
    			var type= update.find("input[name='type']").val();
    			var typeChild= update.find("input[name='typeChild']").val();
    			
    	        var objs={"typeRefDicId":typeRefDicId,"objEntityRefId":objEntityRefId,"parentId":parentId,"type":type,"typeChild":typeChild};
    	        var win = SF.showModalDlg("data_type_edit.html","add",objs,800,400,function(e){
    	        	if(e)refreshTable();
    	        }); 
    	     });
    	    //打开已有项
    	    $("#btnAdd3").click(function(){  
    	    	var update=$("#Formbody");
    	    	var typeId= update.find("input[name='typeChild']").val();
    			var objEntityRefId= update.find("input[name='objEntityRefId']").val();
    			var parentId= update.find("input[name='parentId']").val();
    			var struId=objEntityRefId;
    	        var objs={"typeRefDicId":typeRefDicId,"objEntityRefId":objEntityRefId,"parentId":parentId,"type":type,"typeId":typeId,"struId":struId};
    	        var win = SF.showModalDlg("data_table_choice.html","add",objs,800,400,function(e){
    	        	 BootstrapDialog.alert({
    			            type: BootstrapDialog.TYPE_SUCCESS,
    			            title: '提示',
    			            message: e,
    			            closeable: true,
    			            buttonLabel: "确定"
    			        });
    	        }); //dlg_callback); 
    	     });
    	    
    	    
 
});
/*function removeRow(rowid){
	alert("你真的要删除这条记录吗？f_pid="+rowid);
}*/

function toModify(e, value, row, index){
	var structId=$.fn.geturl("rightIframe","structId");
	$.post("../../../dynamic/ObjStructure/dic_ref_detail",{id:row.id,structId:structId},function(result){
		
		 var win = SF.showModalDlg("data_type_edit.html?id="+row.id,"update",result,800,400,function(e){
			 if(e) {refreshTable();}
		 }); 
		 
	});
}
	  //重新查询、重新排序、切换分页事件
function refreshTable() {
	    	var options = $table.bootstrapTable('getOptions'); 
	        var number =  options.pageNumber;
	        var size = options.pageSize;
	        var sortName = options.sortName;
	        var sortOrder = options.sortOrder;
	        var parentId=$.fn.geturl("rightIframe","parentId");
	    	var structId=$.fn.geturl("rightIframe","structId");
	       /* var objs = SF.getSearchParams();*/
	    	$.ajax({
	    	   type: "POST",
	           async: true,
	    	   url: "../../../dynamic/ObjStructure/fetchDicListFormaintain",       
	    	   //{ name: name,typeId: typeId,struId: struId,parentId: parentId }
	    	   data: {
	    		    	'page':number,
	    		    	'limit':size,
	    		    	/*'code':objs.code,
	    		    	'name':objs.name,*/
	    		    	"structId":structId,
	    		    	"parentId":parentId
	    		    
	           },
	    	   success: function(res){
	    	     	var pagedata = {};
	            	pagedata.rows = res.rows;
	            	pagedata.total = res.total;
	            	$table.bootstrapTable('load', pagedata);
	            	return false;
	    	   },
	    	   error: function(XMLHttpRequest,textStatus,errorThrown){
	    	   		//alert("请求异常！");
	    	   }
	    	 }); 
	    } 

function refreshTable2() {
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
        	$table.bootstrapTable('load', pagedata);
        	return false;
	   },
	   error: function(XMLHttpRequest,textStatus,errorThrown){
	   		//alert("请求异常！");
	   }
	 }); 
} 