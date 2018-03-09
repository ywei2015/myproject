//===================================================================
// 项目名称：mes系统
// 模块名称：设备维修记录
// 类    名：EquRepairRecord.js
//===================================================================

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
			title:"状态",
			dataIndex: 'status',
			fieldText: 'name',
			fieldValue: 'code',
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
        singleSelect: true, //行单选
	    columns: [
		    	{field: 'checkStatus',checkbox: true, edit:false},
	    	    {field: 'pid', visible:false, edit:false},   
	    	    {field: 'issubmitCheck', visible:false, edit:false},   
	    	    {
			        field: 'repairTypeName',
			        align:"center",
			        title: '报修类别', 
			    }
		        , {
			        field: 'equName',
			        title: '设备名称',
			        align:"center"
			    },{
			        field: 'occurTime',
			        title: '发生时间',
			        align:"center",
			        formatter:function(value,row,index){
			        	return formatDate(value);
			        }
			    },{
			        field: 'applyUsername',
			        title: '报修申请人',
			        align:"center"
			    },{
			        field: 'createTime',
			        title: '申请报修',
			        align:"center",
			        formatter:function(value,row,index){
			        	return formatDate(value);
			        }
			    }
//		        , {
//			        field: 'remark',
//			        title: '维修日期',
//			        align:"center"
//			    }
		        ,  {
			        field: 'repairSt',
			        title: '维修开始',
			        align:"center",
			        formatter:function(value,row,index){
			        	if(value==null){
			        		return "";
			        	}else{
			        		return formatDate(value);
			        	}
			        }
			    }, {
			        field: 'repairEt',
			        title: '维修结束',
			        align:"center",
			        formatter:function(value,row,index){
			        	if(value==null){
			        		return "";
			        	}else{
			        		return formatDate(value);
			        	}
			        }
			    }, {
			        field: 'repairUsername',
			        title: '维修人员',
			        align:"center"
			    },
//			     {
//			        field: 'remark',
//			        title: '维修用时',
//			        align:"center"
//			    },
			    {
			        field: 'status',
			        title: '处理状态',
			        align: "center",
			        formatter:function(value,row,index){
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
			        	};
			        }
			    },{
		    	field:"action",title:"操作",align:"center", width : '200',
		    	formatter:function(value,row,index){
			    	var modifyHtml = '<a href="#" class="modify"><li id="modify" class="glyphicon glyphicon-pencil">维修</li></a>';
			    	var verifHtml = '<a href="#" class="verif"><li id="verif" class="glyphicon glyphicon-saved">提交验证</li></a>';
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
	 
	
});


//function removeRow(rowid){
//	alert("你真的要删除这条记录吗？f_pid="+id);
//}

var dlg_callback =function(e){
	if(e=='ok'){
		refreshTable();
		BootstrapDialog.alert("操作成功！");
	}
}

//维修
function modifyRow(e, value, row, index){
	//Modal.load({ msg: "角色编辑",id:"addRoleIframe", url: "addRole.html?f_pid="+row.f_pid, title: "角色编辑", width: 800, height: 400 });
	console.log(row);
	if(row.issubmitCheck==1){   //维修待验证以上的不能修改	
		BootstrapDialog.alert("已提交验证的不能修改!");
	}else{
		var sdata = {"f_pid":row.pid};
		var win = SF.showModalDlg("addRepairRecord.html","update",sdata,800,400,dlg_callback);
	}
}

//提交验证
function verifRow(e, value, row, index){
	console.log(row);
	if(row.issubmitCheck==1){
		BootstrapDialog.alert("已提交验证的不能再次验证!");
	}else{
		
		BootstrapDialog.show({
			  title : "提示",
			  message : "是否提交验证！",
			  buttons : [
			   {
			      label : "确定",
			      cssClass : "btn-primary",
			      icon : "",//通过bootstrap的样式添加图标按钮
			     action : function(dialog){   //给当前按钮添加点击事件
				 	$.post("../../../../dynamic/Record/isCheck",{id:row.pid},function(result){	
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

//详情
function delRow(e, value, row, index){
		var sdata = {"row":row};
		var win = SF.showModalDlg("showDetail.html","",sdata,900,600,dlg_callback);
	
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
       	url: "../../../../dynamic/Record/findRecordList",
       	data: {
       		'page' : number,
       		'limit' : size,
       		'equName' : objsSearch.equName,
       		'status' : objsSearch.status
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
