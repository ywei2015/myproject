/**
 * 未确认物资 
 */


//基础表
function initTable1() {
	var matCode = $('#matCode').val();
	var reg = /-/g;
	var startDate = $("#startDate").val().replace(reg,"");
	var endDate = $("#endDate").val().replace(reg,"");
	//console.info(matCode+startDate+endDate);
    //先销毁表格  
    $('#cusTable').bootstrapTable('destroy');  
    //初始化表格,动态从服务器加载数据  
    $("#cusTable").bootstrapTable({  
        method: "get",  //使用get请求到服务器获取数据  
        url: cqt_prefix+"MaterialUnaccept/materialUnacceptBase", //获取数据的Servlet地址  
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
                f_mat_code:matCode,
                startDate:startDate,
                endDate:endDate
            };    
            return param;                  
          },  
        onLoadSuccess: function(){  //加载成功时执行  
          //layer.msg("加载成功");  
        	console.info("成功");
        	//成功在表中添加查看明细链接
        	$("#cusTable tbody tr").each(function(i,tr){
        		var td = $($(tr).children()).eq(0);
        		var code = $($(tr).children()).eq(2).text();
        		var date = $($(tr).children()).eq(1).text();
        		var name = $($(tr).children()).eq(3).text();
//        		var link = "<a href="+href+" target=_blank>查看明细</a>";
        		var link = "<a href=javascript:;>查看明细</a>";
        		td.html(link);
        		$(td).children().eq(0).click(function(){
        			initTable2(code,date,name);
        		});
    		});
        },  
        onLoadError: function(){  //加载失败时执行  
          //layer.msg("加载数据失败", {time : 1500, icon : 2});  
        	console.info("失败");
        }  
      });  
    
    
    
  }  

//明细 表
function initTable2(code_p,date_p,name_p) {
	//设置明细标题
	$("#matDetail").text("“"+name_p+"”清单明细");
	//
	$("#tablebox2").show();
    //先销毁表格  
    $('#cusTable2').bootstrapTable('destroy');  
    //初始化表格,动态从服务器加载数据  
    $("#cusTable2").bootstrapTable({  
        method: "get",  //使用get请求到服务器获取数据  
        url: cqt_prefix+"MaterialUnaccept/materialUnacceptDetail", //获取数据的Servlet地址  
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
       //设置查询参数  
        queryParams: function queryParams(params) {   			
            var param = {    
                pageNumber: params.pageNumber,    
                pageSize: params.pageSize,  
                //orderNum : $("#orderNum").val() 
                code:code_p,
                date:date_p,
            };    
            return param;                  
          }/*,
        onLoadSuccess: function(){  //加载成功时执行  
          //layer.msg("加载成功");  
        	console.info("成功");
        },  
        onLoadError: function(){  //加载失败时执行  
          //layer.msg("加载数据失败", {time : 1500, icon : 2});  
        	console.info("失败");
        }  */
      });  
    
    
    
  }



  $(document).ready(function () {          
      //调用函数，初始化表格  
      initTable1();  
     
      //当点击查询按钮的时候执行  
      $("#search").bind("click", initTable1);  
      
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
    	    $("#startDate").datetimepicker("setEndDate",$('#endDate').val())//甚至可选的最后时间
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
    	    $("#endDate").datetimepicker("setStartDate",$("#startDate").val())
    	});
  });  

  
  




