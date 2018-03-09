
var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", 
    title : "设备维保定义新增",
    berth : "detailForm",
    items : [
    	{ 
        	type:"numberinput",
            title:"步骤号:",
            dataIndex:'stepIndex'
        },
    	{ 
        	type:"textarea",
            title:"作业要求说明:",
            dataIndex:'execstdDes',  
            rowspan:8
        }
    ],
    hidefields : ['pid','taskdefinePid'] ,
    layoutConfig : {
        columns : 1
    },
    onsubmit: function(){ 
    	//校验
    	
    	if(window.dialogArguments!=undefined) 
    		 $('#Formbody').data('bootstrapValidator').validate();  
				if(!$('#Formbody').data('bootstrapValidator').isValid()){  
		    		return ;
		    	} 
			var objs = SF.getFormValues();
    		console.log(objs);
    		var result=$.ajax({
    	        type: "POST",
    	        dataType: "json",
    	        url: "../../../../dynamic/WbtaskDefine/saveEquWbtaskStepDefine",
    	        async: false,//同步  
    	        data:objs ,
    	        success: function (result) {
    	         
    	        },
    	        error: function(data) {
    	         }
    	    });
    		if(result.responseJSON.status==200){
    			BootstrapDialog.show({
    	            title: '提示',
    	            message: result.responseJSON.message,
    	            buttons: [{
    	                label: '确认',
    	                action: function(dialog) {
    	                	dialog.close();
    	                	window.returnValue = "ok"; 
    	                	window.close();
    	                	return false;
    	                }
    	            }]
    	        });
		}else{
			BootstrapDialog.show({
	            title: '提示',
	            message: result.responseJSON.message,
	            buttons: [{
	                label: '确认',
	                action: function(dialog) {
	                	dialog.close();
	                	return false;
	                }
	            }]
	        });
		}
    }
};


$(document).ready(function() {   
	//formbodyConfig["url"] = MesWebApiUrl + "/sec/user/getuserpage/"+f_pid;
	//formbodyConfig["dataroot"] = 'dataset';
	console.log(SF.FormBody.onload(formbodyConfig));
	var mode = window.dialogArguments.mode;
	if(mode == 'add'){
		var taskdefinePid = window.dialogArguments.data.taskdefinePid;
		$("#taskdefinePid").val(taskdefinePid);
	}else if(mode == 'update'){
		var f_pid = window.dialogArguments.data.row.pid;
		$.post("../../../../dynamic/WbtaskDefine/getEquWbtaskStepDefineById?id="+f_pid,{},function(data){
			SF.BindFormData(data);
		});
	}
	
	// 表单验证  ---------stasrt----------、
		$('#Formbody').bootstrapValidator({
			      message: 'This value is not valid',
			      feedbackIcons: {/*输入框不同状态，显示图片的样式*/
			        valid: 'glyphicon glyphicon-ok',
			        invalid: 'glyphicon glyphicon-remove',
			        validating: 'glyphicon glyphicon-refresh'
			      },
			      fields: {/*验证*/
			        execstdDes: {/*键名username和input name值对应*/
				          message: 'The name is not valid',
				          validators: {
				            notEmpty: {/*非空提示*/
				              message: '不能为空'
				            }
				          }
				        },
				stepIndex: {/*键名username和input name值对应*/
					          message: 'The name is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '不能为空'
					            }
					          }
					        }
			}
		});
	//-----------------end------------------
	
})