//===================================================================
// 项目名称：Agile-MES
// 模块名称：设备维保定义列表
// 类    名：EquTaskDefine.js
//===================================================================
var param = GetQueryString("param")
  var searchpanel = {
						name  : "search",   
					    type  : "searchpanel", 
					    title : "查询条件",
					    berth : "SearchPanel",
					    items : [
					        { 
					        	type:"textinput",
					            title:"名称",
					            dataIndex:'taskName'
					        }
					    ],
					    layoutConfig : {
					        columns : 4
					    },
					    onsearch: function(){
					    	refreshTable(getQueryString("param"));
					    }
					};
var $table;
var $table1;
$(function () {
	SF.SearchPanel.onload(searchpanel); 
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
//      showToggle: true, //是否显示详细视图和列表视图的切换
        singleSelect: true, //行单选按钮
        clickToSelect: false, 
        toolbar: '#toolbar', //工具按钮用哪个容器
	    columns: [
		    {field: 'checkStatus',checkbox: true, edit:false,formatter : function stateFormatter(value, row, index) {
		    	if (index == 0){
		    		refreshDefineTable(row.pid);
		            return {
		                disabled : false,//设置是否可用
		                checked : true//设置选中
		            };
		    	}    
		        return value;
		    }},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
	        {field: 'pid', visible:false, edit:false},   
		   /* {
		        field: 'wbTasktypeName',
		        align:"center",
		        title: '任务类别', 
		        sortable:true	
		    }
	        ,*/ {
		        field: 'taskName',
		        title: '任务名称',
		        align:"center"
		    }, {
		        field: 'taskDes',
		        title: '任务描述',
		        align:"center"
		    },  {
		        field: 'workObjName',
		        title: '作业对象',
		        align:"center"
		    }, {
		        field: 'executorName',
		        title: '任务接收对象',
		        align:"center"
		    }, /*{
		        field: 'createTime',
		        title: '任务触发时间',
		        align:"center" 
		    },*/ {
		        field: 'execTimeflag',
		        title: '是否工作日执行',
		        align:"center",
		        formatter:function(value,row,index){
		        	if(row.execTimeflag=='1'){
		        		return "是";
		        	}else{
		        		return "否";
		        	}
		        }
		    },{
		    	field:"action",title:"操作",align:"center", width : '80',
		    	formatter:function(value,row,index){
			    	var modifyHtml = '<a href="#" class="modify" title="编辑"><li id="modify" class="glyphicon glyphicon-pencil"> </li></a>';
			    	var generate = '<a href="#" class="generate" title="生成任务"><li id="modify" class="glyphicon glyphicon-tasks"> </li></a>';
		    		var strHtml = modifyHtml+"&nbsp;&nbsp;&nbsp;&nbsp;"+generate ;
		    		return strHtml;
		    	},
		    	events: {
                    'click .modify': function (e, value, row, index) { 
                        modifyRow(e, value, row, index);//修改操作 
                    },
			    	'click .generate': function (e, value, row, index) { 
			    		BootstrapDialog.show({
	    		            title: constant.TIP,
	    		            message: "确认生成任务吗？",
	    		            buttons: [{
	    		                label: constant.CONFIRM,
	    		                action: function(dialog) {
	    		                	dialog.close();
	    		                	$.post("../../../../dynamic/equwbtask/generatebyid",{"taskdefineid":row.pid},function(result){
	    			    				BootstrapDialog.show({
	    			    		            title: constant.TIP,
	    			    		            message: result.message,
	    			    		            buttons: [{
	    			    		                label: constant.CONFIRM,
	    			    		                action: function(dialog) {
	    			    		                	dialog.close();
	    			    		                	return false;
	    			    		                }
	    			    		            }]
	    			    		        });
	    				    		});
	    		                	return false;
	    		                }
	    		            },{
	    		                label: constant.CANCEL,
	    		                action: function(dialog) {
	    		                	dialog.close();
	    		                }
	    		            }]
	    		        });
			   
			    	}
               },
		    	edit:false
		    } 
	    ],
        smartDisplay:true,  
        cache : false,
	    onPageChange: function (number, size) {
	    	//var objsSearch = SF.getSearchParams();
	    	refreshTable(param);
	        return false;
	    },
	    onSort: function (name, order) {
	    	//var objsSearch = SF.getSearchParams();
	    	refreshTable(param);
	        return false;
	    },
	    onCheck: function (item, $element,index) {
	    	$("#hiddenTaskdefinePid").val("");
	    	$("#hiddenTaskdefinePid").val(item.pid);
	    	refreshDefineTable(item.pid);
	    }
	    ,
	    onClickRow: function (item, $element,index) {
	    	$table1.bootstrapTable("removeAll");
	    }
	    
	});
	
	refreshTable(param);//首次调用 
	 
	//新增任务列表
	 $("#BtnAdd").click(function(){
		 var sdata = {"f_pid":null,"wbTasktype":getQueryString("param")};
	      var win = SF.showModalDlg("addTaskDefine.html","add",sdata,800,400,dlg_callback);
	 });
	
	 //新增任务步骤列表
	 $("#stepsBtnAdd").click(function(){
		 var rowdata = $('#table').bootstrapTable('getSelections');
		 	if( rowdata.length!=1){
        			BootstrapDialog.show({
        	            title: constant.TIP,
        	            message: "请选择一条记录",
        	            buttons: [{
        	                label: constant.CONFIRM,
        	                action: function(dialog) {
        	                	dialog.close();
        	                }
        	            }]
        	        });
		 		//alert("请选择一条记录!");
		 		return ;
		 	}else{
		 		 var sdata = {"taskdefinePid":rowdata[0].pid};
			     var addUser=SF.showModalDlg("addTaskSteps.html","add",sdata,800,400,dlg_callback);
		 	}
		
	 });
	 
	 //删除任务列表
	 $("#BtnDel").click(function(){
	 	var rowdata = $('#table').bootstrapTable('getSelections');
	 	if(rowdata.length==0){
	 		
    			BootstrapDialog.show({
    	            title: constant.TIP,
    	            message: "请选择一条记录",
    	            buttons: [{
    	                label: constant.CONFIRM,
    	                action: function(dialog) {
    	                	dialog.close();
    	                }
    	            }]
    	        });

	 	}else{
	 		BootstrapDialog.show({
	            title: constant.TIP,
	            message: constant.CONFIRM_DELETE,
	            buttons: [{
	                label: constant.CONFIRM,
	                action: function(dialog) {
	                	dialog.close();
	                	if(rowdata.length>=1){
	        	    		var array=new Array();
	        	    		for(var i=0;i<rowdata.length;i++){
	        	    			array.push(rowdata[i].pid);
	        	    		}
	        		 
	        			 	$.post("../../../../dynamic/WbtaskDefine/deleteEquWbtaskDefine",{ids:array.join(",")},function(result){	
	        			 		refreshTable(param);
	        			 		BootstrapDialog.show({
	        			            title: constant.TIP,
	        			            message: result.message,
	        			            buttons: [{
	        			                label: constant.CONFIRM,
	        			                action: function(dialog) {
	        			                	dialog.close();
	        			                	return false;
	        			                }
	        			            }]
	        			        });
	        		    	},"json");
	        		 	}
	                	return false;
	                }
	            },{
	                label:constant.CANCEL,
	                action: function(dialog) {
	                	dialog.close();
	                	return false;
	                }
	            }]
	        });
	 		
	 	}
	 });
	 
	 //删除任务步骤列表
	 $("#stepsBtnDel").click(function(){
		 	var rowdata = $('#table1').bootstrapTable('getSelections');
		 	if(rowdata.length==0){
		 		BootstrapDialog.show({
		            title: constant.TIP,
		            message: "请选择一条记录",
		            buttons: [{
		                label:constant.CONFIRM,
		                action: function(dialog) {
		                	dialog.close();
		                	return false;
		                }
		            }]
		        });
		 		//alert("请选择一条记录!");
		 	}else{
			 	var ids = '';
			 	for(var i=0;i<rowdata.length;i++){ 
			 		ids+=rowdata[i].pid+",";
			 	}
			 	ids=ids.substring(0,ids.length-1);
			 	$.post("../../../../dynamic/WbtaskDefine/deleteEquWbtaskStepDefine",{ids:ids},function(result){	
			 		BootstrapDialog.show({
			            title: constant.TIP,
			            message: result.message,
			            buttons: [{
			                label: constant.CONFIRM,
			                action: function(dialog) {
			                	dialog.close();
			                	return false;
			                }
			            }]
			        });
			 		var rowdata = $('#table').bootstrapTable('getSelections');
			 		refreshDefineTable(rowdata[0].pid);
		    	},"json");
		 	}
		 });
	
	 $table1 = $('#table1').bootstrapTable({ 
        locale:'zh-CN',//中文支持 
        pagination: true,//是否开启分页（*）
		pageNumber:1,
        pageSize: 10,//每页的记录行数（*）
        pageList: [10,25,50,100],//可供选择的每页的行数（*）
        idField: "pid", //标识哪个字段为id主键
        uniqueId: "pid",   //每一行的唯一标识，一般为主键列
        sidePagination: "server", //分页方式：client 客户端分页，server 服务端分页（*）
        showColumns: true, //是否显示所有的列
        minimumCountColumns: 2, //最少允许的列数
        toolbar: '#toolbar1', //工具按钮用哪个容器
	    columns: [
		    {field: 'checkStatus',checkbox: true, edit:false},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
	        {field: 'pid', visible:false, edit:false},   
	        {
		        field: 'stepIndex',
		        align:"center",
		        title: '序号'
		    },{
		        field: 'execstdDes',
		        title: '作业要求说明',
		        align:"center",
		        sortable:true
		    },
		   {
		    	field:"action",title:"操作",align:"center", width : '80',
		    	formatter:function(value,row,index){
			    	var modifyHtml = '<a href="#" class="modify"><li id="modify" class="glyphicon glyphicon-pencil"> </li></a>';
		    		var strHtml = modifyHtml ;
		    		return strHtml;
		    	},
		    	events: {
                    'click .modify': function (e, value, row, index) { 
                        modifyRow1(e, value, row, index);//修改操作 
                    }
               },
		    	edit:false
		    } 
	    ],
        clickToSelect: true, 
        smartDisplay:true,  
        cache : false,
	    onPageChange: function (number, size) {
	        return false;
	    },
	    onSort: function (name, order) {
	        return false;
	    }
	   /* onCheck:function(row){
	    	
	    	refreshTable2(row);
	    }*/
	});
});


//function removeRow(rowid){
//	alert("你真的要删除这条记录吗？f_pid="+id);
//}

var dlg_callback =function(e){
	if(e=='ok'){
//		$("#table").bootstrapTable('refresh',{url : path );
//		$("#table").bootstrapTable('refreshOptions',{pageNumber : 1});
		
		refreshTable(getQueryString("param"));
	}
}

//任务列表修改
function modifyRow(e, value, row, index){
	var sdata = {"f_pid":row.pid};
    var win = SF.showModalDlg("addTaskDefine.html","update",sdata,800,400,dlg_callback);
}

//任务步骤列表修改
function modifyRow1(e, value, row, index){
	//Modal.load({ msg: "角色编辑",id:"addRoleIframe", url: "addRole.html?f_pid="+row.f_pid, title: "角色编辑", width: 800, height: 400 });
	var sdata = {"row":row};
    var win = SF.showModalDlg("addTaskSteps.html","update",sdata,800,400,dlg_callback);
}

//重新查询、重新排序、切换分页事件
function refreshTable(param) {
	var options = $table.bootstrapTable('getOptions');
    var number =  options.pageNumber;
    var size = options.pageSize;
    var sortName = options.sortName;
    var sortOrder = options.sortOrder;
    var objsSearch = SF.getSearchParams();
	$.ajax({
	   	type: "POST",
       	async: true,
	   	//url: MesWebApiUrl + "/sec/role/getpage",       
       	url: "../../../../dynamic/WbtaskDefine/findEquWbtaskDefineList",
       	data: {
       		'page' : number,
       		'limit' : size,
       		'taskName' : objsSearch.taskName,
       		'type' : param
       	},
	   	success: function(res){
	     	var pagedata = {};
        	pagedata.rows = res.rows;
        	pagedata.total = res.total;
        	$table1.bootstrapTable("removeAll");
        	$table.bootstrapTable('load', pagedata);
        	
	   },
	   error: function(XMLHttpRequest, textStatus, errorThrown){
	   }
	 }); 
}


function refreshDefineTable(f_pid) {
	
	var accessToken="meERw1HORJ6hFoEvthAx7pO-9BTQGyACI4rS6nKwwY6nwmLtOMZRNehughHZyI2n9KiF8DtSIdei-FNfe1qECQhV5jxOXCY90mN-uoGPdB9JDnp-rGceRNvIcEXkhiUdQQJxiWqeba9OpXONhw722AcuCHBB1CrissqqyqxuViRONkw_ZqDi19n7V2n4ClgE4ueZtdDeewHKuTbKXWGnqo6dyQqFSJpaFPvdknBv_s2CO_Rx6GBLCw820kGfAcoBfDK_7YpZMCxWECNwBu9KUNgPo_oqsSLMERsaXoA9lNWIWcvxntNqskwl5kB1QoJoMEqsa45IhKP6au7-q2dhpfbBWo4d9ZNRBgIG5nLysdI10T3QWzf2_gGxnfLmkxbntKF6OCZwZsF8t6jx_VA4oCPhxJ3gn93kZ_2e_cEBayE6QsYh0mjuh7pyVZ13SegZ";
	var options = $table1.bootstrapTable('getOptions');
    var number =  options.pageNumber;
    var size = options.pageSize;
    var sortName = options.sortName;
    var sortOrder = options.sortOrder;
    var objs = SF.getSearchParams();
	$.ajax({
	   type: "POST",
       async: true,
	   //url: MesWebApiUrl + "/sec/user/getsalvepage/"+f_pid,
	   url: '../../../../dynamic/WbtaskDefine/findEquWbtaskStepDefineList', 
	   headers: {"Authorization":"Bearer "+accessToken},
	   data: {
		   
		   'page':number,
		   'limit':size,
		   'taskdefinePid':f_pid
		  
       },
	   success: function(res){
	   		var pagedata = {};
        	pagedata.rows = res.rows;
			pagedata.total = res.total;
        	$table1.bootstrapTable('load', pagedata);
	   },
	   error: function(XMLHttpRequest, textStatus, errorThrown){
	   }
	 });
}


function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}