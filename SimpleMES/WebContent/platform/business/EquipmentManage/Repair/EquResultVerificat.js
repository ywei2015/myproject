//===================================================================
// 项目名称：mes系统
// 模块名称：设备结果验证
// 类    名：EquResultVerificat.js
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
	    columns: [
	        {field: 'pid', visible:false, edit:false},  
	        {
		        field: 'repairTypeName',
		        align:"center",
		        title: '报修类别', 
		    }
	        , 
		    {
		        field: 'abnormalTypeName',
		        align:"center",
		        title: '故障类型', 
		    }
	        , {
		        field: 'equName',
		        title: '设备名称',
		        align:"center"
		    }, 
//		    {
//		        field: 'remark',
//		        title: '维修日期',
//		        align:"center"
//		    }, 
		    {
		        field: 'repairUsername',
		        title: '维修人员',
		        align:"center"
		    }, 
//		    {
//		        field: 'remark',
//		        title: '维修用时',
//		        align:"center"
//		    },
		    {
		        field: 'repairProdes',
		        title: '维修情况描述',
		        align:"center"
		    }, {
		        field: 'chekcResult',
		        title: '验证结果',
		        align:"center",
		        formatter:function(value,row,index){
		        	if(value==1){
		        		return "已解决"
		        	}else{
		        		return "未解决"
		        	};
		        }
		    },
		    {
		        field: 'checkUsername',
		        title: '验证人',
		        align:"center"
		    },
		    {
		        field: 'checkEt',
		        title: '验证时间',
		        align:"center",
		        formatter:function(value,row,index){
		        	if(value==null){
		        		return "";
		        	}else{
		        		return formatDate(value);
		        	}
		        }
		    },{
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
		    	field:"action",title:"操作",align:"center", width : '120',
		    	formatter:function(value,row,index){
			    	var modifyHtml = '<a href="#" class="modify"><li id="modify" class="glyphicon glyphicon-saved">验证</li></a>';
			    	var delHtml = '<a href="#" class="details"><li class="glyphicon glyphicon-list-alt" >详情</li></a>';
			    	var strHtml =  modifyHtml+'&nbsp&nbsp&nbsp'+delHtml;
		    		return strHtml;
		    	},
		    	events: {
                    'click .modify': function (e, value, row, index) { 
                        modifyRow(e, value, row, index);//修改操作 
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

var dlg_callback =function(e){
	if(e=='ok'){
		refreshTable();
		BootstrapDialog.alert("操作成功！");
	}
}

//验证
function modifyRow(e, value, row, index){
	//Modal.load({ msg: "角色编辑",id:"addRoleIframe", url: "addRole.html?f_pid="+row.f_pid, title: "角色编辑", width: 800, height: 400 });
	if(row.status!=40){
		var sdata = {"row":row};
		var win = SF.showModalDlg("repairVerificat.html","update",sdata,800,400,dlg_callback);
	}else{
		BootstrapDialog.alert("该数据已经验证!");
	}
}
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
       	url: "../../../../dynamic/Record/findVerificat",
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
