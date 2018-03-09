//===================================================================
// 项目名称：mes系统
// 模块名称：设备维保执行列表
// 类    名：EquTaskPerform.js
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
					        },{ 
					        	type:"combox",
					            title:"设备",
					            dataIndex:'equId', 
					            dataText: 'equId',
								fieldText:"name",
								fieldValue:"pid",
								url:"../../../../dynamic/dicView/listDic?view=v_equ_info",
								root: "dataset.data"
					        },{ 
					        	type:"combox",
					            title:"状态",
					            dataIndex:'status', 
					            dataText: 'status',
								fieldText:"name",
								fieldValue:"code",
								url:"../../../../dynamic/dicView/listDic?view=v_equ_taskstatus",
								root: "dataset.data"
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
        showColumns: false, //右上角列选择
        showToggle:false,  //右上角视图切换
        singleSelect: true, //行单选
        clickToSelect: false,
        minimumCountColumns: 2, //最少允许的列数
//      showToggle: true, //是否显示详细视图和列表视图的切换按钮
      //  toolbar: '#toolbar', //工具按钮用哪个容器
	    columns: [
		    {field: 'checkStatus',checkbox: true, edit:false,formatter : function stateFormatter(value, row, index) {
		    	
		    	if (index == 0){
		    		
		            return {
		                disabled : false,//设置是否可用
		                checked : true//设置选中
		            };
		    	}   
		    	
		        return value;
		    }},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
	        {field: 'pid', visible:false, edit:false,formatter: function stateFormatter(value, row, index){
	        	refreshTaskTable("");
	        }},   
		   /* {
		        field: 'wbTasktype',
		        align:"center",
		        title: '任务类别', 
		        sortable:true	
		    },*/ {
		        field: 'taskName',
		        title: '任务名称',
		        align:"center"
		    }, {
		        field: 'taskDes',
		        title: '任务描述',
		        align:"center"
		    },  {
		        field: 'equName',
		        title: '设备',
		        align:"center"
		    }, {
		        field: 'executorName',
		        title: '执行者',
		        align:"center"
		    },{
		        field: 'status',
		        title: '状态',
		        formatter:function(value,row,index){
		        	if(row.status=='20'){
		        		return "完成";
		        	}else if(row.status=='10'){
		        		return "执行中";
		        	}else if(row.status=='5'){
		        		return "下达";
		        	}else if(row.status=='0'){
		        		return "草稿";
		        	}else if(row.status=='44'){
		        		return "任务取消";
		        	}else{
		        		return "其它";
		        	}
		        }
		    },{
		    	field:"action",title:"操作",align:"center", width : '80',
		    	formatter:function(value,row,index){
			    	var modifyHtml = '<a href="#" class="modify" title="完成"><li id="modify" class="glyphicon glyphicon-ok"> </li></a>';
			    	var repair = '<a href="#" class="repair" title="报修"><li class="glyphicon glyphicon-wrench" ></li></a>';
			    	var strHtml = modifyHtml+"&nbsp;&nbsp;&nbsp;&nbsp;"+repair ;
		    		return strHtml;
		    	},
		    	events: {
                    'click .modify': function (e, value, row, index) { 
                    	BootstrapDialog.show({
    			            title: constant.TIP,
    			            message:"确认完成任务吗？",
    			            buttons: [{
    			                label: constant.CONFIRM,
    			                action: function(dialog) {
    			                	dialog.close();
    			                	modifyRow1(e, value, row, index);//修改操作 
    			                	return false;
    			                }
    			            },{
    			                label:constant.CANCEL ,
    			                action: function(dialog) {
    			                	dialog.close();
    			                	return false;
    			                }
    			            }]
    			        });
                    /*	if(confirm("确认完成任务吗？")){
                        	
                    	}*/
                    },
                  'click .repair':function(e, value, row, index){
                	  var sata={"equId":row.equId,"repairType":param,"equName":row.equName};
                	  var data={"row":sata};
                	  console.log(data);
                	  var win = SF.showModalDlg("../Repair/addApply.html","desTarget",data,800,400,dlg_callback);
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
	    	refreshTable(param);
	        return false;
	    },
	    onSort: function (name, order) {
	    	//var objsSearch = SF.getSearchParams();
	    	refreshTable(param);
	        return false;
	    },
	    onCheck:function(row){
	    	$("#hiddenTaskPid").val("");
	    	$("#hiddenTaskPid").val(row.pid);
	    	
	    	refreshTaskTable("");
	    },
	    onClickRow: function (item, $element,index) {
	    	$table1.bootstrapTable("removeAll");
	    }
	    
	});
	
	refreshTable(param);//首次调用 

	//新增任务列表
	 $("#BtnAdd").click(function(){
		 var sdata = {"f_pid":null};
	      var win = SF.showModalDlg("performDeal.html","add",sdata,800,400,dlg_callback);
	 });

	 //新增任务步骤列表
	 $("#stepsBtnAdd").click(function(){});
	 
	 //删除任务列表
	 $("#BtnDel").click(function(){});
	 
	 //删除任务步骤列表
	 $("#stepsBtnDel").click(function(){});
	
	 $table1 = $('#table1').bootstrapTable({ 
        locale:'zh-CN',//中文支持 
        pagination: true,//是否开启分页（*）
		pageNumber:1,
        pageSize: 10,//每页的记录行数（*）
        pageList: [10,25,50,100],//可供选择的每页的行数（*）
        idField: "pid", //标识哪个字段为id主键
        uniqueId: "pid",   //每一行的唯一标识，一般为主键列
        sidePagination: "server", //分页方式：client 客户端分页，server 服务端分页（*）
        showColumns: false, //右上角列选择
        showToggle:false,  //右上角视图切换
        minimumCountColumns: 2, //最少允许的列数
       // toolbar: '#toolbar1', //工具按钮用哪个容器
	    columns: [
		    {field: 'checkStatus',checkbox: true, edit:false},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
	        {field: 'pid', visible:false, edit:false},
	        {
		        field: 'stepIndex',
		        align:"center",
		        title: '序号'
		    },
		    {
		        field: 'execstdDes',
		        title: '作业要求说明',
		        align:"center"
		    },
		    {
		        field: 'state',
		        title: '执行状态',
		        align:"center",
		        formatter:function(value,row,index){
		        	if(row.state=='1'){
		        		return "已执行";
		        	}else{
		        		return "未执行";
		        	}
		        }
		    }, {
		        field: 'isabnormal',
		        title: '执行结果',
		        align:"center",
		        formatter:function(value,row,index){
		        	if(row.isabnormal=='1'){
		        		return "正常";
		        	}else if(row.isabnormal==0){
		        		return "异常";
		        	}else{
		        		return '-';
		        	}
		        }
		    },{
		        field: 'abnormalDes',
		        title: '异常描述',
		        align:"center"
		    },{
		        field: 'lastModifierName',
		        title: '执行人',
		        align:"center"
		    }/*,{
		        field: 'lastModifiedTime',
		        title: '执行时间',
		        align:"center"
		    }*/,
		   {
		    	field:"action",title:"操作",align:"center", width : '80',
		    	formatter:function(value,row,index){
			    	var modifyHtml = '<a href="#" class="modify" title="执行"><li id="modify" class="glyphicon glyphicon-pencil"> </li></a>';
		    		var strHtml = modifyHtml ;
		    		return strHtml;
		    	},
		    	events: {
                    'click .modify': function (e, value, row, index) { 
                        modifyRow(e, value, row, index);//执行操作 
                    }
               },
		    	edit:false
		    } 
	    ],
        clickToSelect: true, 
        smartDisplay:true,  
        cache : false,
	    onPageChange: function (number, size) {
	    	refreshTaskTable("");
	        return false;
	    },
	    onSort: function (name, order) {
	        return false;
	    }
	});
		refreshTaskTable("");
});


//function removeRow(rowid){
//	alert("你真的要删除这条记录吗？f_pid="+id);
//}

var dlg_callback =function(e){
	if(e=='ok'){
		refreshTaskTable("");
//		$("#table").bootstrapTable('refresh',{url : path );
//		$("#table").bootstrapTable('refreshOptions',{pageNumber : 1});
	/*	alert("子窗口已经提交 并关闭! 您可以刷新父窗口数据了。"+e);*/
	}
}
function  modifyRow1(e, value, row, index){
	 var rowdata = $('#table1').bootstrapTable('getData');
	 if(row.status==20){
			//alert("任务已完成");
			 BootstrapDialog.show({
		            title: constant.TIP,
		            message: "任务已完成",
		            buttons: [{
		                label: constant.CONFIRM,
		                action: function(dialog) {
		                	dialog.close();
		                }
		            }]
		        });
			return ;
		}
	for(var i=0;i<rowdata.length;i++){
		if(rowdata[i].state!=1){
			//alert("任务未完成，不能结束");
			BootstrapDialog.show({
	            title: constant.TIP,
	            message: "任务未完成，不能结束！",
	            buttons: [{
	                label: constant.CONFIRM,
	                action: function(dialog) {
	                	dialog.close();
	                }
	            }]
	        });
			return ;
		}
		
	}
	$.post("../../../../dynamic/WbtaskDefine/updateWbTask",{pid:row.pid},function(result){
			if(result.status==200){
				//alert(result.message);
				// refreshTable(param) 
				refreshTable(param);
			}
			BootstrapDialog.show({
	            title: '提示',
	            message: result.message,
	            buttons: [{
	                label: '确认',
	                action: function(dialog) {
	                	dialog.close();
	                }
	            }]
	        });

		
		
	});
}
//执行
function modifyRow(e, value, row, index){
	//Modal.load({ msg: "角色编辑",id:"addRoleIframe", url: "addRole.html?f_pid="+row.f_pid, title: "角色编辑", width: 800, height: 400 });
	var sdata = {"pid":row.pid};
	 var rowdata = $('#table').bootstrapTable('getSelections');
	if(rowdata.length !=1 || rowdata[0].status==20){
		//alert("该任务已经执行完成，不能修改");
			BootstrapDialog.show({
	            title: '提示',
	            message: "该任务已经执行完成，不能修改",
	            buttons: [{
	                label: '确认',
	                action: function(dialog) {
	                	dialog.close();
	                }
	            }]
	        });
		return ;
	}
    var win = SF.showModalDlg("performDeal.html","update",sdata,800,400,dlg_callback);
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
       	url: "../../../../dynamic/WbtaskDefine/findEquWbtaskList",
       	data: {
       		'page' : number,
       		'limit' : size,
       		'type' : param,
       		'taskName' : objsSearch.taskName,
       		'equId':objsSearch.equId,
       		'status':objsSearch.status
       	},
	   	success: function(res){
	     	var pagedata = {};
        	pagedata.rows = res.rows;
        	pagedata.total = res.total;
        	if(res.rows.length>=1){
        		refreshTaskTable(res.rows[0].pid);
        	}
        	$table1.bootstrapTable("removeAll");
        	$table.bootstrapTable('load', pagedata);
	   },
	   error: function(XMLHttpRequest, textStatus, errorThrown){
	   }
	 }); 
}


function refreshTaskTable(pid) {
	
	var accessToken="meERw1HORJ6hFoEvthAx7pO-9BTQGyACI4rS6nKwwY6nwmLtOMZRNehughHZyI2n9KiF8DtSIdei-FNfe1qECQhV5jxOXCY90mN-uoGPdB9JDnp-rGceRNvIcEXkhiUdQQJxiWqeba9OpXONhw722AcuCHBB1CrissqqyqxuViRONkw_ZqDi19n7V2n4ClgE4ueZtdDeewHKuTbKXWGnqo6dyQqFSJpaFPvdknBv_s2CO_Rx6GBLCw820kGfAcoBfDK_7YpZMCxWECNwBu9KUNgPo_oqsSLMERsaXoA9lNWIWcvxntNqskwl5kB1QoJoMEqsa45IhKP6au7-q2dhpfbBWo4d9ZNRBgIG5nLysdI10T3QWzf2_gGxnfLmkxbntKF6OCZwZsF8t6jx_VA4oCPhxJ3gn93kZ_2e_cEBayE6QsYh0mjuh7pyVZ13SegZ";
	var options = $table1.bootstrapTable('getOptions');
    var number =  options.pageNumber;
    var size = options.pageSize;
    var sortName = options.sortName;
    var sortOrder = options.sortOrder;
    var rowdata = $('#table').bootstrapTable('getSelections');
    
    if($.fn.isBlank(pid)){
    	if(rowdata.length!=1){
        	return ;
        }
    	pid=rowdata[0].pid;
    }
	$.ajax({
	   type: "POST",
       async: true,
	   //url: MesWebApiUrl + "/sec/user/getsalvepage/"+f_pid,
	   url: '../../../../dynamic/WbtaskDefine/findEquWbtaskStepList', 
	   headers: {"Authorization":"Bearer "+accessToken},
	   data: { 
		   'page':number,
		   'limit':size,
		   'taskPid':pid
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