//===================================================================
// 项目名称：Agile-MES
// 模块名称：基础数据类派生类列表
// 类    名：DerivedClasses.js
//===================================================================
var $table;
var $table1;
var id;
var flag;
var data = window.dialogArguments.data;
var mode = window.dialogArguments.mode;
id = data.id;
flag = data.flag;

var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", //id
    title : "类型维护",
    berth : "detailForm",
    items : [
        { 
        	type:"textinput",
            title:"编码",
            dataIndex:'code'
        },
        { 
        	type:"textinput",
            title:"名称",
            dataIndex:'name'
        }
    ],
    hidefields:['id'],
    layoutConfig : {
        columns : 3
    },
    onsubmit: function(){ 
    	var formData = SF.getFormValues();
    	var listData = $table1.bootstrapTable('getData');
    	var cachename = [];
    	var cachecolumn = [];
    	if (listData.length > 0) {
    		for(var i = 0; i < listData.length; i++){
    			if(isEmpty(listData[i].name)){
    				BootstrapDialog.alert("字段名称不能为空！");
    				return;
    			}
    			if(isEmpty(listData[i].cateId)){
    				BootstrapDialog.alert("字段类型不能为空！");
    				return;
    			}
    			if(isEmpty(listData[i].column)){
    				BootstrapDialog.alert("字段不能为空！");
    				return;
    			}
    			if(isEmpty(listData[i].order)){
    				BootstrapDialog.alert("排序不能为空！");
    				return;
    			}
    			if(isEmpty(listData[i].pkFlag.toString())){
    				BootstrapDialog.alert("是否主键不能为空！");
    				return;
    			}
    			if(isEmpty(listData[i].allowBlank)){
    				BootstrapDialog.alert("是否为空不能为空！");
    				return;
    			}
    			var _indexname = cachename.indexOf(listData[i].name);
    			var _indexcolumn = cachecolumn.indexOf(listData[i].column);
    			if( _indexname > -1){
    				BootstrapDialog.alert("第"+(i+1)+"行字段名称:与"+(_indexname+1)+"行重复定义");
    				return;
    			}else
                {
                    cachename.push(listData[i].name);
                }
    			if( _indexcolumn > -1){
    				BootstrapDialog.alert("第"+(i+1)+"行字段:与"+(_indexcolumn+1)+"行重复定义");
    				return;
    			}else
                {
                    cachecolumn.push(listData[i].column);
                }
        	}
		}
    	
    	var url = "";
    	if(mode == 'add'){
	    	url = "../../../dynamic/objBase/saveOBTypeVo";
    	}else if(mode == 'update'){
    		url = "../../../dynamic/objBase/updateOBTypeVo";
    	}
    	var result=$.ajax({
            type: "POST",
//            dataType: "json",
            url: url,
            async: false,//同步  
            data:{"listData":JSON.stringify(listData),"formData":JSON.stringify(formData)} ,
            success: function (result) {
               	
            },
            error: function(data) {
             }
        });
    	if(result.status==200){
       		window.returnValue = "ok";
       	}
    	BootstrapDialog.show({
            title: '提示',
            message: result.responseJSON.message,
            buttons: [{
                label: '确认',
                action: function(dialog) {
                	dialog.close();
                	
                	window.close();
                	return false;
                }
            }]
        });
    }
};

$(document).ready(function() {
	SF.FormBody.onload(formbodyConfig);
});
$(function () {
	
	 $table = $('#table').bootstrapTable({ 
        locale:'zh-CN',//中文支持 
        pagination: false,//是否开启分页（*）
        pageSize: 10,//每页的记录行数（*）
        pageList: [10,25,50,100],//可供选择的每页的行数（*）
        idField: "id", //标识哪个字段为id主键
        uniqueId: "id",   //每一行的唯一标识，一般为主键列
        sidePagination: "server", //分页方式：client 客户端分页，server 服务端分页（*）
        showColumns: true, //是否显示所有的列
        minimumCountColumns: 2, //最少允许的列数
        toolbar: '#toolbar', //工具按钮用哪个容器
        singleSelect: true, 
	    columns: [
	        {field: 'id', visible:false, edit:false},   
		    {
		        field: 'name',
		        align:"center",
		        title: '字段名称', 
		        sortable:true	
		    },{
		        field: 'cateName',
		        align:"center",
		        title: '字段类型' 
		    },{
		        field: 'column',
		        align:"center",
		        title: '字段' 
		    },{
		        field: 'order',
		        align:"center",
		        title: '排序' 
		    }
	    ],
        clickToSelect: true, 
        smartDisplay:true,  
        cache : false
	});
	 
	 $table1 = $('#table1').bootstrapTable({ 
	        locale:'zh-CN',//中文支持 
	        pagination: false,//是否开启分页（*）
			pageNumber:1,
	        pageSize: 10,//每页的记录行数（*）
	        pageList: [10,25,50,100],//可供选择的每页的行数（*）
	        idField: "id", //标识哪个字段为id主键
	        uniqueId: "id",   //每一行的唯一标识，一般为主键列
	        sidePagination: "server", //分页方式：client 客户端分页，server 服务端分页（*）
	        showColumns: true, //是否显示所有的列
	        minimumCountColumns: 2, //最少允许的列数
	        toolbar: '#toolbar1', //工具按钮用哪个容器
	        singleSelect : true, // 行单选按钮
	        editable:true,
		    columns: [
			    {field: 'checkStatus',checkbox: true, edit:false},  //给多选框赋一个field值为“checkStatus”用于更改选择状态!!!!!  
		        {field: 'id', visible:false, edit:false},   
			    {
			        field: 'name',
			        title: '字段名称',
			        editable: {
			        	type: 'text',
			        	validate: function (value) {  
			        		if ($.trim(value) == '') {  
			        			return '字段名称不能为空!';  
			        		}  
			        	}  
			        }
			    },
			    {
			        field: 'cateId',
			        title: '字段类型',
			        editable:{
			        	type: "select",
			        	source: function () {
	                        var result = [];
	                        $.ajax({
	                            url: '../../../../dynamic/objBase/fetchObjTypeCombox',
	                            async: false,
	                            type: "get",
	                            data: {},
	                            success: function (data, status) {
	                                $.each(data.dataset.data, function (key, value) {
	                                    result.push({ value: value.pid, text: value.name });
	                                });
	                            }
	                        });
	                        return result;
	                    },
			            title: "选择类型",
			            disabled: false,
			            mode: "popup",
			            validate: function (value) { //字段验证
			                if (!$.trim(value)) {
			                    return '不能为空';
			                }
			            } 
					},
					visible:true
			    },
			    {
			        field: 'column',
			        title: '字段',
			        editable:{
			        	type: "select",
			        	source: function () {
	                        var result = [];
	                        $.ajax({
	                            url: '../../../../dynamic/objBase/fetchObjattributeCombox',
	                            async: false,
	                            type: "get",
	                            data: {},
	                            success: function (data, status) {
	                                $.each(data.dataset.data, function (key, value) {
	                                    result.push({ value: value.code, text: value.name });
	                                });
	                            }
	                        });
	                        return result;
	                    },
			            title: "选择字段",
			            disabled: false,
			            mode: "popup",
			            validate: function (value) { //字段验证
			                if (!$.trim(value)) {
			                    return '不能为空';
			                }
			            } 
					},
					visible:true
			    },{
			        field: 'order',
			        title: '排序',
			        editable: {
			        	type: 'number',
			        	validate: function (value) {  
			        		if (!$.trim(value)) {  
			        			return '排序不能为空!';  
			        		}
			        		if (value < 0) {  
			        			return '排序不能为负数!';  
			        		}
			        	}  
			        }
			    },{
			        field: 'pkFlag',
			        title: '是否为主键',
			        editable:{
			        	type: "select",
			            source: [{ value: '1', text: "是" }, { value: '0', text: "否" }],
			            title: "选择状态",
			            disabled: false,
			            mode: "popup",
			            validate: function (value) { //字段验证
			                if (!$.trim(value)) {
			                    return '不能为空';
			                }
			            } 
					},
					visible:true
			    },{
			        field: 'verifyRule',
			        title: '扩展属性'
			    },{
			        field: 'allowBlank',
			        title: '是否为空',
			        editable:{
			        	type: "select",              //编辑框的类型。支持text|textarea|select|date|checklist等
			            source: [{ value: '1', text: "允许" }, { value: '0', text: "不允许" }],
			            title: "选择状态",           //编辑框的标题
			            disabled: false,           //是否禁用编辑
//			            emptytext: "空文本",       //空值的默认文本
			            mode: "popup",            //编辑框的模式：支持popup和inline两种模式，默认是popup
			            validate: function (value) { //字段验证
			                if (!$.trim(value)) {
			                    return '不能为空';
			                }
			            } 
					},
					visible:true
			    }
		    ],
	        clickToSelect: true, 
	        smartDisplay:true,  
	        cache : false
		});
	
	refreshInherTable();
	refreshAttrTable();
	
	$("#btnAdd").click(function(){
		var index = $table1.bootstrapTable('getData').length;
	    $table1.bootstrapTable('insertRow', {
	    	index: index,
	        row: {
	        	id: index,
	            name: '',
	            cateId: '',
//	            cateName: '',
	            column: '',
	            order: '',
	            pkFlag: '',
	            verifyRule: '',
	            allowBlank: ''
	        }
	    });
	});
	$("#btnDel").click(function(){
		var ids = $.map($table1.bootstrapTable('getSelections'), function (row) {
            return row.id;
        });
        if (ids.length == 0) {
        	BootstrapDialog.show({
	            title: constant.TIP,
	            message: "请选择一行删除！",
	            buttons: [{
	                label: constant.CONFIRM,
	                action: function(dialog) {
	                	dialog.close();
	                }
	            }]
	        });
        }
        $table1.bootstrapTable('remove', {
            field: 'id',
            values: ids
        });
	});
});

var dlg_callback =function(e){
	if(e=='ok'){
		refreshTable();
		BootstrapDialog.alert("操作成功！");
	}
}

function refreshInherTable() {
	var options = $table.bootstrapTable('getOptions'); 
    var number =  options.pageNumber;
    var size = options.pageSize;
	$.post('../../../dynamic/objBase/fetchOBTypeVoDetail?id='+ id+"&flag="+flag,{limit:1000},function(res){
		var pagedata = {};
    	pagedata.rows = res.dataset.rows.inherPros;
    	if(mode == 'add'){
    		$("#id").val(id);
    	}
    	if(mode == 'update'){
    		SF.BindFormData(res.dataset.rows);
    	}
    	$table.bootstrapTable('load', pagedata);
    	return false;
	});
}

function refreshAttrTable() {
	var options = $table1.bootstrapTable('getOptions'); 
    var number =  options.pageNumber;
    var size = options.pageSize;
	$.post('../../../dynamic/objBase/fetchOBTypeVoDetail?id='+ id+"&flag="+flag,{limit:1000},function(res){
		var pagedata = {};
    	pagedata.rows = res.dataset.rows.pros;
    	$table1.bootstrapTable('load', pagedata);
    	return false;
	});
}

function isEmpty(str){
	if (str == null || str == undefined  || str == '') { 
		return true;
	}else{
		return false;
	}
}
