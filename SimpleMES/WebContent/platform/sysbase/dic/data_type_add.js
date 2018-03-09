
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
         	type:"textinput",
             title:"全名称",
             dataIndex:'fullName'
         },
        { 
         	type:"numberinput",
             title:"排序值",
             dataIndex:'num1'
         },
         { 
          	type:"numberinput",
              title:"关系排序",
              dataIndex:'trdOrder'
          }, { 
           	type:"textinput",
            title:"网页地址",
            dataIndex:'value2'
        },
		{ 
        	type:"textinput",
            title:"备注",
            dataIndex:'remark'
        },
        
        {
			type:"combox",   
			title:"是否有效",
			dataIndex: 'sysFlag',
			fieldText:"name",
			fieldValue:"code",
			data:[{'code':1,'name':'有效'},{'code':0,'name':'无效'}],
			//url:"../../../DataDict?type=Workshop",
			root: "dataset.item"
		}
    ],
    hidefields:['typeRefDicId','objEntityRefId','parentId','type','id','typeChild'],
    layoutConfig : {
        columns : 2
    },
    onsubmit: function(){ 
    	var objs =$("#Formbody"); //SF.getSearchParams();
    	var mode = window.dialogArguments.mode;
    	
    	if(mode == 'add'){
    		forceAjax("../../../dynamic/ObjStructure/saveDicRef",objs,function(){
    			if(result.responseJSON.status==200){
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

        	});
    	}else if(mode == 'update'){
    		forceAjax("../../../dynamic/ObjStructure/saveDic",objs,function(){
    			if(result.responseJSON.status==200){
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
        	});
    	}
    	
    	
    }
};


$(document).ready(function() {   


	SF.FormBody.onload(formbodyConfig);  
	var data = window.dialogArguments.data;
	var mode = window.dialogArguments.mode;
	console.log(data);
	if(mode == 'add'){
		SF.BindFormData(data);
	}else if(mode == 'update'){
		//绑定参数
		SF.BindFormData(data);

	}	
	
	
});