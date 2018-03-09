
var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", //id
    title : "基础数据维护",
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
			title:"是否有效",
			dataIndex: 'sysFlag',
			fieldText:"name",
			fieldValue:"code",
			data:[{'code':1,'name':'有效'},{'code':0,'name':'无效'}],
			//url:"../../../DataDict?type=Workshop",
			root: "dataset.item"
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
    hidefields:["superClass.id","id"],
    onsubmit: function(){ 
    	var objs =$("#Formbody"); //SF.getSearchParams();
    	var mode = window.dialogArguments.mode;
    	 $('#Formbody').data('bootstrapValidator').validate();  
    	 var flag = $('#Formbody').data('bootstrapValidator').isValid()
			if(!flag){  
	    		return ;
	    	}
    	 
    	//保存之前做校验
    	 var flag=false;
    	 $.ajax({
		        type: "POST",
		        dataType: "json",
		        url: "../../../dynamic/objBase/validCodeAndName",
		        async: false,//同步  
		        data:objs.serialize(),
		        success: function (result) {
		           	if(result.status!=200){
		           		BootstrapDialog.show({
	        	            title: constant.TIP,
	        	            message:result.message,
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
		           
		        },
		    });
			if(flag){
				return;
			}
    	if(mode == 'add'){
    		var result=forceAjaxSynchro("../../../dynamic/objBase/save",objs,function(){
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
	                	if(result.responseJSON.status==200){
	                		window.close();
	                	}
	                	return false;
	                }
	            }]
	        });
    	}else if(mode == 'update'){
    		var result=forceAjaxSynchro("../../../dynamic/objBase/save",objs,function(){
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
	                	if(result.responseJSON.status==200){
	                		window.close();
	                	}
	                	return false;
	                }
	            }]
	        });
    	}
    	
    	
    }
};


$(document).ready(function() {   
	SF.FormBody.onload(formbodyConfig);  
	var data = window.dialogArguments.data;
	var mode = window.dialogArguments.mode;
	var superClass=getQueryString("superClass");
	$("#Formbody input[name='superClass.id']").val(superClass);
	var id=null;
	if(mode == 'add'){
		
	}else if(mode == 'update'){
		//绑定参数
		SF.BindFormData(data);
		type=1;
		id=data.id
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
		            }/*,
					remote: {
				            type: 'POST',
				            url: '../../../dynamic/objBase/validField?id='+id,
				            delay: 1000
				        }*/
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
				
				sysFlag:{/*键名username和input name值对应*/
					          message: 'The name is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '不能为空'
					            }
					          }
					        }
					},
			});
	/*************end*******************/
});