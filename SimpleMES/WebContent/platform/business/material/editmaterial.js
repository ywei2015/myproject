
var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", //id
    title : "基础物料维护",
    berth : "detailForm",
    items : [
        { 
        	type:"textinput",
            title:"编号",
            dataIndex:'matCode'
        },
        { 
        	type:"textinput",
            title:"名称",
            dataIndex:'matName' 
        },{ 
        	type:"textinput",
            title:"全名称",
            dataIndex:'matFullname'
        },{ 
        	type:"textinput",
            title:"规格描述",
            dataIndex:'spec'
        },{ 
        	type:"combox",   
			title:"物料大类",
			dataIndex: 'type',
			fieldText: 'name',
			fieldValue: 'pid',
			//data:[{'code':0,'name':'点检'},{'code':1,'name':'保养'},{'code':2,'name':'润滑'}],
			url: "../../../../dynamic/dicView/listDic?view=v_mat_maintype",
			root: "dataset.data"
        },{ 
        	type:"combox",   
			title:"物料子类",
			dataIndex: 'sub_type',
			fieldText: 'name',
			fieldValue: 'pid',
			//data:[{'code':0,'name':'点检'},{'code':1,'name':'保养'},{'code':2,'name':'润滑'}],
			url: "../../../../dynamic/dicView/listDic?view=v_mat_subtype",
			root: "dataset.data"
        },{ 
        	type:"combox",   
			title:"单位",
			dataIndex: 'unit',
			fieldText: 'name',
			fieldValue: 'pid',
			url: "../../../../dynamic/dicView/listDic?view=v_mat_units",
			root: "dataset.data"
        },
		{ 
        	type:"textinput",
            title:"备注",
            dataIndex:'remark'
        }
    ],
    layoutConfig : {
        columns : 2
    },
    hidefields:["pid"],
    onsubmit: function(){ 
    	var objs =$("#Formbody"); //SF.getSearchParams();
    	var mode = window.dialogArguments.mode;
    	if(mode == 'add'){
    		var result=forceAjaxSynchro("../../../dynamic/material/savematerial",objs,function(){
    			BootstrapDialog.alert(result.responseJSON.message);
        	});
    		if(result.responseJSON.code==200){
    			BootstrapDialog.show({
    				message:"保存成功"
    			});
    			window.returnValue = "ok"; 
    			setTimeout(function(){
                	window.close();
                	return false;
    			},850)
    		}else {
    			SF.validForm(result);
    		}
    		
    	}else if(mode == 'update'){
    		var result=forceAjaxSynchro("../../../dynamic/material/updatematerial",objs,function(result){
        	});
			if(result.responseJSON.code==200){
    			BootstrapDialog.show({
    				message:"修改成功"
    			});
    			window.returnValue = "ok"; 
    			setTimeout(function(){
                	window.close();
                	return false;
    			},850)
    		}else {
    			SF.validForm(result);
    		}
    	}
    }
};



$(document).ready(function() {   
	SF.FormBody.onload(formbodyConfig);  
	var data = window.dialogArguments.data;
	var mode = window.dialogArguments.mode;
	var superClass=getQueryString("superClass");
	$("#Formbody input[name='superClass.id']").val(superClass);
	if(mode == 'add'){
		
	}else if(mode == 'update'){
		//绑定参数
		SF.BindFormData(data);
	}	
	


});