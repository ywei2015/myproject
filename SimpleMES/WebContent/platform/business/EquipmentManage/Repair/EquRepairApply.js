//===================================================================
// 项目名称：mes系统
// 模块名称：设备报修申请
// 类    名：EquTaskDefine.js
//===================================================================

var searchpanel = {
	name  : "search",   
    type  : "searchpanel", 
    title : "查询条件",
    berth : "SearchPanel",
    items : [
    	{
			type:"datetime",
		    title:"开始时间",
		    dataIndex:'startTime',
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
            title:"设备名称",
            dataIndex:'equName'
        },
		{
			type:"combox",   
			title:"状态",
			dataIndex: 'status',
			fieldText: 'name',
			fieldValue: 'code',
			//data:[{'code':0,'name':'申请中'},{'code':1,'name':'维修中'},
				//  {'code':2,'name':'验证中'},{'code':2,'name':'完成关闭'}],
			url: "../../../../dynamic/dicView/listDic?view=v_equ_repairstatus",
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
	    	{field: 'checkStatus',checkbox: true, edit:false},
	        {field: 'pid', visible:false, edit:false},
	        {field: 'applyUserid', visible:false, edit:false}, 
	        {field: 'repairType', visible:false, edit:false},
		    {
		        field: 'repairTypeName',
		        align:"center",
		        title: '报修类别', 
		    }
	        , {
		        field: 'equName',
		        title: '设备名称',
		        align:"center"
		    }, {
		        field: 'equCode',
		        title: '设备编号',
		        align:"center"
		    },  {
		        field: 'abnormalDes',
		        title: '异常描述',
		        align:"center"
		    },{
		        field: 'occurTime',
		        title: '发生时间',
		        align:"center",
		        formatter:function(value,row,index){
		        	if(value==null){
		        		return "";
		        	}else{
		        		return formatDate(value);
		        	}
		        }
		    },{
		        field: 'applyUsername',
		        title: '报修申请人',
		        align:"center"
		    },{
		        field: 'createTime',
		        title: '申请报修时间',
		        align:"center",
		        formatter:function(value,row,index){
		        	if(value==null){
		        		return "";
		        	}else{
		        		return formatDate(value);
		        	}
		        }
		    },
		    {
		        field: 'repairUserid',
		        visible:false
		        
		    },
		    {
		        field: 'repairUsername',
		        title: '维修人',
		        align:"center"
		    },
		    {
		        field: 'status',
		        title: '处理状态',
		        align: "center",
		        formatter:function(value,row,index){
		        	return showStatus(row);
		        }
		    },{
		    	field:"action",title:"操作",align:"center", width : '150',
		    	formatter:function(value,row,index){
			    	var modifyHtml = '<a href="#" class="modify"><li id="modify" class="glyphicon glyphicon-pencil">修改</li></a>';
			    	var verifHtml = '<a href="#" class="verif"><li id="verif" class="glyphicon glyphicon-saved">提交</li></a>';
			    	var delHtml = '<a href="#" class="details"><li class="glyphicon glyphicon-list-alt" >详情</li></a>';
			    	var strHtml = modifyHtml+'&nbsp&nbsp&nbsp'+verifHtml+'&nbsp&nbsp&nbsp'+delHtml ;
		    		return strHtml;
		    	},
		    	events: {
                    'click .modify': function (e, value, row, index) { 
                        modifyRow(e, value, row, index);//修改操作 
                    },
                    'click .verif' : function(e, value, row, index) { 
                    	verifRow(e, value, row, index); 
                    },
                    'click .details' : function(e, value, row, index) { 
                    	delRow(e, value, row, index); 
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
	    },
	    onClickRow: function (item, $element,index) {}
	    
	});
	
	refreshTable();//首次调用 
	SF.SearchPanel.onload(searchpanel);//搜索框的加载
	 
	//新增
	 $("#BtnAdd").click(function(){
		 var sdata = {"row":null};
	      var win = SF.showModalDlg("addApply.html","add",sdata,800,400,dlg_callback);
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
	 				 	$.post("../../../../dynamic/Repair/deletes",{ids:ids},function(result){	
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
	if(e=='ok'){
		refreshTable();
		BootstrapDialog.alert("操作成功！");
	}
}

//修改
function modifyRow(e, value, row, index){
	//Modal.load({ msg: "角色编辑",id:"addRoleIframe", url: "addRole.html?f_pid="+row.f_pid, title: "角色编辑", width: 800, height: 400 });
	if(row.status<=10){   //只有已申报未维修的申请单能修改
		var sdata = {"row":row};
		var win = SF.showModalDlg("addApply.html","update",sdata,800,400,dlg_callback);
	}else{
		BootstrapDialog.alert("只有已申报未维修的申请单可以修改!");
	}
}

//详情
function delRow(e, value, row, index){
	//Modal.load({ msg: "角色编辑",id:"addRoleIframe", url: "addRole.html?f_pid="+row.f_pid, title: "角色编辑", width: 800, height: 400 });
	
		var sdata = {"row":row};
		var win = SF.showModalDlg("showDetail.html","",sdata,900,600,dlg_callback);
	
}

//提交
function verifRow(e, value, row, index){
	console.log(row);
	if(row.status>10){
		BootstrapDialog.alert("已提交草稿的不能再次提交!");
	}else{
		
		BootstrapDialog.show({
			  title : "提示",
			  message : "是否提交草稿！",
			  buttons : [
			   {
			      label : "确定",
			      cssClass : "btn-primary",
			      icon : "",//通过bootstrap的样式添加图标按钮
			     action : function(dialog){   //给当前按钮添加点击事件
				 	$.post("../../../../dynamic/Repair/isCheckUpdate",{id:row.pid},function(result){	
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
       	async: true,
	   	//url: MesWebApiUrl + "/sec/role/getpage",       
       	url: "../../../../dynamic/Repair/equRepairApplyList",
       	data: {
       		'page' : number,
       		'limit' : size,
       		'startTime' : objsSearch.startTime,
       		'endTime' : objsSearch.endTime,
       		'equName' : objsSearch.equName,
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
	if(row.status==0){
		return "草稿"
	}else if(row.status==10){
		return "已申报待修";
	}else if(row.status==20){
		return "维修中";
	}else if(row.status==29){
		return "修完待验证";
	}else if(row.status==30){
		return "验证中";
	}else if(row.status==40){
		return "维修单关闭";
	}
}