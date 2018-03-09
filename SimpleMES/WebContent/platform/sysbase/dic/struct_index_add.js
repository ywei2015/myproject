
var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", //id
    title : "关联关系维护",
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
			type:"combox",   
			title:"是否权限控制",
			dataIndex: 'accessControlId',
			fieldText:"name",
			fieldValue:"code",
			data:[{'code':'4028a07b246a97c201246a9aea380002','name':'是'},{'code':'4028a07b246a97c201246a9b31c50003','name':'否'}],
			//url:"../../../DataDict?type=Workshop",
			root: "dataset.item"
		},
		{ 
        	type:"textinput",
            title:"备注",
            dataIndex:'remark'
        },
    ],
    hidefields:["id"],
    layoutConfig : {
        columns : 2
    },
    onsubmit: function(){ 
    	var objs =$("#Formbody"); //SF.getSearchParams();
    	var mode = window.dialogArguments.mode;
    	var a= $table2.bootstrapTable('getSelections');
    	var objs =$("#Formbody"); //SF.getSearchParams();
    	var ids;
    	//开启验证
	    $('#Formbody').data('bootstrapValidator').validate();  
		if(!$('#Formbody').data('bootstrapValidator').isValid()){  
    		return ;
    	} 
    	if(a.length>=1){
    		var array=new Array();
    		for(var i=0;i<a.length;i++){
    			array.push(a[i].id);
    		}
    	    ids=array.join(",");

    	}else{
    		BootstrapDialog.show({
 	            title: constant.TIP,
 	            message:'请至少选中一行',
 	            buttons: [{
 	                label: constant.CONFIRM,
 	                action: function(dialog) {
 	                	dialog.close();
 	                	return false;
 	                }
 	            }]
 	        });
    		//BootstrapDialog.alert("请至少选中一行");
    		return false;}
    //保存之前做校验
   	 var flag=false;
   	var result=forceAjaxSynchro("../../../dynamic/ObjStructure/validCodeAndName?ids="+ids,objs,function(){
	});
		if(result.responseJSON.status!=200){
			BootstrapDialog.show({
	            title:constant.TIP,
	            message:result.responseJSON.message,
	            buttons: [{
	                label: constant.CONFIRM,
	                action: function(dialog) {
	                	dialog.close();
	                	return false;
	                }
	            }]
	        });
			flag=true;
		}
   	
			if(flag){
				return;
			}
    	if(mode == 'add'){
    		var result=forceAjaxSynchro("../../../dynamic/ObjStructure/saveObjStructure?ids="+ids,objs,function(){
        	});
    		if(result.responseJSON.status==200){
    			window.returnValue = "ok"; 
        		
    		}
    		BootstrapDialog.show({
	            title: constant.TIP,
	            message: result.responseJSON.message,
	            buttons: [{
	                label: constant.CONFIRM,
	                action: function(dialog) {
	                	dialog.close();
	                	window.close();
	                	return false;
	                }
	            }]
	        });
    		
    		
    	}else if(mode == 'update'){
    		var result=forceAjaxSynchro("../../../dynamic/ObjStructure/saveObjStructure?ids="+ids,objs,function(){
        	});
    		if(result.responseJSON.status==200){
    			window.returnValue = "ok"; 
        		
    		}
    		BootstrapDialog.show({
	            title: constant.TIP,
	            message: result.responseJSON.message,
	            buttons: [{
	                label: constant.CONFIRM,
	                action: function(dialog) {
	                	dialog.close();
	                	window.close();
	                	return false;
	                }
	            }]
	        });
    	}
    	
    	
    }
};

var $table2;
var checkTable;
$(document).ready(function() {   

	SF.FormBody.onload(formbodyConfig);  
	var data = window.dialogArguments.data;
	var mode = window.dialogArguments.mode;
	var id=null;
	if(mode == 'add'){
		
	}else if(mode == 'update'){
		//绑定参数
		SF.BindFormData(data);
		id=data.id;
		  $.post('../../../dynamic/ObjStructure/getObjStructure?id='+ data.id,{limit:10000},function(result){
				checkTable=result.dataset.objstructure.children;;
				return false;
			});
	}	
	
	/*************表单验证  stasrt**************/
	$('#Formbody').bootstrapValidator({
		      message: 'This value is not valid',
		      feedbackIcons: {/*输入框不同状态，显示图片的样式*/
		        valid: 'glyphicon glyphicon-ok',
		        invalid: 'glyphicon glyphicon-remove',
		        validating: 'glyphicon glyphicon-refresh'
		      },
		      fields: {/*验证*/
		        code: {/*键名username和input name值对应*/
		          message: 'The name is not valid',
		          validators: {
		            notEmpty: {/*非空提示*/
		              message: '不能为空'
		            }
		          }
		        },
				name: {/*键名username和input name值对应*/
					          message: 'The name is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '不能为空'
					            }
					          }
					        },
				
					accessControlId:{/*键名username和input name值对应*/
					          message: 'The name is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '不能为空'
					            }
					          }
					        }
					}
			});
	/*************end*******************/
    $table2 = $('#table').bootstrapTable({ 
	      locale:'zh-CN',//中文支持 
	      pagination: false,//是否开启分页（*）
	      //striped:true,  //奇偶行渐色表
	   /*   pageSize: 5,//每页的记录行数（*）
	      pageList: [5,10],//可供选择的每页的行数（*）
*/		  idField: "id", //标识哪个字段为id主键
	      uniqueId: "id",   //每一行的唯一标识，一般为主键列
	      sidePagination: "server", //分页方式：client 客户端分页，server 服务端分页（*）
	      showColumns: true, //是否显示所有的列
	     // toolbar: '#toolbar', //工具按钮用哪个容器
	      minimumCountColumns: 2, //最少允许的列数
	      showToggle: true, //是否显示详细视图和列表视图的切换按钮
	      clickToSelect: true, 
	      smartDisplay:true,  
	      cache : false,
		    columns: [
			    {field: 'checkStatus',checkbox: true, edit:false, formatter : function(value, row, index) {
			    		var result;
				     $(checkTable).each(function(i,item){
					    	/* console.log(row);
					    	 console.log(item[i]\\);
					    	 console.log(checkTable);*/
					    	 if(item.id==row.id){
					    		 result = {
							          disabled : false,//设置是否可用
							          checked : true//设置选中
							        };
					    	 }
					    	
					     });
					      return result;
					    }},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
			    //{field: 'checkStatus',radio: true},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
		      //  {field: 'id', visible:false, edit:false},   
			    {
			        field: 'code',
			        title: '关系编码', 
			        sortable:true,
			        //editable: true,
			    	edit:false
			    }, {
			        field: 'name',
			        title: '关系名称' 
			    }, 
			    {
			        field: 'parentName',
			        title: '父对象'  
			    },
			    {
			        field: 'childName',
			        title: '子对象' 
			    }
		    ], 
		    onPageChange: function (number, size) {
		    	refreshTable();
		        return false;
		    },
		    onSort: function (name, order) {
		    	refreshTable();
		        return false;
		    },
		    onCheck:function(row){
		    
		    }
		}); 
	  //对应的函数进行判断；
	  
	    function stateFormatter(value, row, index) {
	     $(checkTable).each(function(i,item){
	    	 if(item.id==row.id){
	    		 console.log(row.id);
	    		 return {
			          disabled : false,//设置是否可用
			          checked : true//设置选中
			        };
	    	 }
	    		
	     });
	       
	      return value;
	    }
	/*    $("#btnDel").click(function(){ 
	    	var a= $table2.bootstrapTable('getSelections');
	    	if(a.length>=1){
	    		var array=new Array();
	    		for(var i=0;i<a.length;i++){
	    			array.push(a[i].id);
	    		}
	    	    // alert(array.join(","));
	
	    	}else{BootstrapDialog.alert("请至少选中一行")}
		}); */

		
		refreshTable2()
});

function refreshTable2() {
	var options = $table2.bootstrapTable('getOptions'); 
    var number =  options.pageNumber;
    var size = options.pageSize;
    var sortName = options.sortName;
    var sortOrder = options.sortOrder;
 /*   var objs = SF.getSearchParams();*/
    //首先获取所有，在获取需要选中的
  $.post('../../../dynamic/ObjEntityRef/findListPagination',{limit:10000,sysFlag:1},function(res){
	  	var pagedata = {};
	  	pagedata.rows = res.rows;
		$table2.bootstrapTable('load', pagedata);
		
    	return false;

	});
} 