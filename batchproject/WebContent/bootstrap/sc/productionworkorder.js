/**
 * 生产工单查询
 */

//表
function initTable() {
	var workorderCode = $('#workorderCode').val();
	var reg = /-/g;
	var startDate = $("#startDate").val().replace(reg,"");
	var endDate = $("#endDate").val().replace(reg,"");
	//console.info(matCode+startDate+endDate);
    //先销毁表格  
    $('#cusTable').bootstrapTable('destroy');  
    //初始化表格,动态从服务器加载数据  
    $("#cusTable").bootstrapTable({  
        method: "get",  //使用get请求到服务器获取数据  
        url: cqt_prefix+"MaterialUnaccept/productionWorkOrder", //获取数据的Servlet地址  
        striped: true,  //表格显示条纹  
        pagination: true, //启动分页  
        pageSize: 5,  //每页显示的记录数  
        pageNumber:1, //当前第几页  
        pageList: [5, 10, 15, 20, 25],  //记录数可选列表 
        cache: false,//是否使用缓存
        search: false,  //是否启用查询  
        showColumns: false,  //显示下拉框勾选要显示的列  
        showRefresh: false,  //显示刷新按钮  
        sidePagination: "server", //表示在服务端进行分页
        //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder  
        //设置为limit可以获取limit, offset, search, sort, order  
        queryParamsType : "undefined", 
        queryParams: function queryParams(params) {   //设置查询参数  
            var param = {    
                pageNumber: params.pageNumber,    
                pageSize: params.pageSize,  
                //orderNum : $("#orderNum").val() 
                workorderCode:workorderCode,
                startDate:startDate,
                endDate:endDate
            };    
            return param;                  
          },  
        onLoadSuccess: function(){  //加载成功时执行  
          //layer.msg("加载成功");  
        	console.info("成功");
        	$("#cusTable tbody tr").each(function(i,tr){
        		var td = $($(tr).children()).eq(0);
        		if(td.text() == "没有找到匹配的记录"){
        			return;
        		}
        		var index = i+1;
        		td.text(index);
    		});
        },  
        onLoadError: function(){  //加载失败时执行  
          //layer.msg("加载数据失败", {time : 1500, icon : 2});  
        	console.info("失败");
        }  
      });  
    
    
    
  }

$(document).ready(function () {          
	//调用函数，初始化表格  
	initTable();  
	
	//当点击查询按钮的时候执行  
	$("#search").bind("click", initTable);  
	
	//开始日期--结束日期  后者不能大于前者
	$("#startDate").datetimepicker({
		format: 'yyyy-mm-dd',//格式
		minView:'month',//最小显示
		initialDate: new Date(),//初始化当前日期
		todayBtn: true,//显示今天按钮
		language: 'zh-CN',//设置文字
		autoclose:true,//点击关闭
//    		        startDate:new Date()
	}).on("click",function(){
		$("#startDate").datetimepicker("setEndDate",$('#endDate').val())//设置可选的最后时间
	});
	$("#endDate").datetimepicker({
		format: 'yyyy-mm-dd',
		minView:'month',
		initialDate: new Date(),//初始化当前日期
		todayBtn: true,
		language: 'zh-CN',
		autoclose:true,
//    		        startDate:new Date()
	}).on("click",function(){
		$("#endDate").datetimepicker("setStartDate",$("#startDate").val())//设置可选的最早时间
	});
});  

