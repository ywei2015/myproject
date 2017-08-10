var workcode=getQueryString('workcode');
var batchj=getQueryString('batch');
var workname;
var matcode;
console.info(batchj);
function initform(){
	var dataj={
			'workcode':workcode,
			'batch':batchj
	};
	$.ajax({
		type : "post",
		url: cqt_prefix+'batchRetrospect/batchRetrospectForWorkOder',
		data:dataj,
		success : function(data) {
			var workorder= data; 
			console.info(workorder);
			$('#workordercode').val(workorder.workordercode);
			$('#process').val(workorder.process);
			$('#producedate').val(workorder.producedate);
			$('#matname').val(workorder.matname);
			$('#actualstarttime').val(workorder.actualstarttime);
			$('#actualendtime').val(workorder.actualendtime);
			$('#worktime').val(workorder.worktime);
			$('#workteam').val(workorder.workteam);
			workname=workorder.remark;
			$('#message').text(workname+"工单信息");
			$('#message2').text(workname+"投料汇总信息");
			$('#message3').text(workname+"投料批次信息");
		}
	});
}

function initTable() {  
    //先销毁表格  
    $('#cusTable').bootstrapTable('destroy');  
    //初始化表格,动态从服务器加载数据  
    $("#cusTable").bootstrapTable({  
        method: "get",  //使用get请求到服务器获取数据  
        url: cqt_prefix+"batchRetrospect/retrospectInputByGroup", //获取数据的Servlet地址  
        pagination: false, //启动分页  
        search: false,  //是否启用查询  
        showColumns: false,  //显示下拉框勾选要显示的列  
        showRefresh: false,  //显示刷新按钮  
        sidePagination: "server", //表示服务端请求  
        //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder  
        //设置为limit可以获取limit, offset, search, sort, order  
        queryParamsType : "undefined", 
        onClickRow: function (item, $element) {
        	matcode=item.matcode;
        	$('#input').show();
        	initTable1();
        	//window.location.href="workorderinput.html?lcode="+item.workordercode;
        },
        queryParams: function queryParams(params) {   //设置查询参数  
          var param = {    
        	  workcode:workcode,
        	  batch:batchj
          };    
          return param;                  
        }/*,  
        onLoadSuccess: function(){  //加载成功时执行  
          layer.msg("加载成功");  
        },  
        onLoadError: function(){  //加载失败时执行  
          layer.msg("加载数据失败", {time : 1500, icon : 2});  
        } */ 
      });  
  }  

function initTable1() {  
    //先销毁表格  
    $('#cusTable1').bootstrapTable('destroy');  
    //初始化表格,动态从服务器加载数据  
    $("#cusTable1").bootstrapTable({  
        method: "get",  //使用get请求到服务器获取数据  
        url: cqt_prefix+"batchRetrospect/retrospectInput", //获取数据的Servlet地址  
        pagination: true, //启动分页  
        pageSize: 6,  //每页显示的记录数  
        pageNumber:1, //当前第几页  
        pageList: [5, 10, 15, 20, 25],  //记录数可选列表  
        search: false,  //是否启用查询  
        showColumns: false,  //显示下拉框勾选要显示的列  
        showRefresh: false,  //显示刷新按钮  
        sidePagination: "server", //表示服务端请求  
        //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder  
        //设置为limit可以获取limit, offset, search, sort, order  
        queryParamsType : "undefined",  
        onClickRow: function (item, $element) {
        	if(item.remark>0){
        		self.location='workorderinput.html?batch='+item.matbatch;
        	}else{
        		window.location.href="batchretrospectout.html?batch="+item.matbatch;
        	}
        	  
        },
        queryParams: function queryParams(params) {   //设置查询参数  
          var param = {
        	  pageNumber: params.pageNumber,    
              pageSize: params.pageSize,  
        	  workcode:workcode,
        	  batch:batchj,
        	  matcode:matcode
          };    
          return param;                  
        }/*,  
        onLoadSuccess: function(){  //加载成功时执行  
          layer.msg("加载成功");  
        },  
        onLoadError: function(){  //加载失败时执行  
          layer.msg("加载数据失败", {time : 1500, icon : 2});  
        } */ 
      });  
  }  

  $(document).ready(function () { 
	  initform();
      //调用函数，初始化表格  
      initTable();  
      //当点击查询按钮的时候执行  
  });  
function backHistroy(){
 	 window.history.go(-1);
}  
  

function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return null; 
}