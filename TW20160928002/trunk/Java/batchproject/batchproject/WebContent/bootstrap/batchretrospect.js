var title= ["序号","编码","名称","小件批次号","原大件批次号","时间","操作"];
var theTable=document.getElementById("table");
var userId=getQueryString('userId');
var f_master_batch=getQueryString('f_master_batch'); //OldDEF
var data_p={
		'f_master_batch':f_master_batch
};
function initTable() {  
    //先销毁表格  
    $('#cusTable').bootstrapTable('destroy');  
    //初始化表格,动态从服务器加载数据  
    $("#cusTable").bootstrapTable({  
        method: "get",  //使用get请求到服务器获取数据  
        url: cqt_prefix+"batchRetrospect/batchRetrospectForJianYan", //获取数据的Servlet地址  
        pagination: true, //启动分页  
        pageSize: 5,  //每页显示的记录数  
        pageNumber:1, //当前第几页  
        pageList: [5, 10, 15, 20, 25],  //记录数可选列表  
        search: false,  //是否启用查询  
        showColumns: false,  //显示下拉框勾选要显示的列  
        showRefresh: false,  //显示刷新按钮  
        sidePagination: "server", //表示服务端请求  
        //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder  
        //设置为limit可以获取limit, offset, search, sort, order  
        queryParamsType : "undefined",  
       /* onClickRow: function (item, $element) {
        	console.info(item.lcode);
        	window.location.href="batchretrospectjb.html?lcode="+item.lcode;
        },*/
        queryParams: function queryParams(params) {   //设置查询参数  
          var param = {    
              pageNumber: params.pageNumber,    
              pageSize: params.pageSize,  
              orderNum : $("#orderNum").val() 
              //orderNum:'91118194126107021707042206741360'

          };    
          return param;                  
        }
      });  
    
  }  
  $(document).ready(function () {          
      //调用函数，初始化表格  
      //当点击查询按钮的时候执行  
     /* $("#search").bind("click", function(){
    	  $('#mytable').show();
    	  initTable();
      });  */
  });
function showMytable(){
	 $('#mytable').show();
	  initTable();
	  
}  

  
function operateFormatter(value,row,index){
	 var canshu=row;
	 console.info(row);
	  return [
              '<button type="button" onclick="openRoleOfA('+row.lcode+')" class=" btn btn-default  btn-sm" style="margin-right:5px;">向前追溯</button>',
              '<button type="button" onclick="openRoleOfB('+canshu+')" class="btn btn-default  btn-sm" style="margin-right:5px;">向后追溯</button>',
          ].join('');
}  
function  openRoleOfA(e){
	alert(e);
}
function  openRoleOfB(e){
	console.info(e.lcode);
	//window.location.href="batchretrospectjb.html?lcode="+e;
}
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return null; 
}