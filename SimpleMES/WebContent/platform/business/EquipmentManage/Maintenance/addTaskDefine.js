
var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", 
    title : "设备维保定义维护",
    berth : "detailForm",
    items : [
      /*  { 
        	type:"combox",
            title:"任务类型",
        	dataIndex: 'wbTasktype',
			dataText: 'wbTasktype',
			fieldText:"name",
			fieldValue:"id",
			url:"../../../../dynamic/dicView/listDic?view=v_equ_tasktype",
			root: "dataset.data"
        },*/{ 
        	type:"textinput",
            title:"任务名称",
            dataIndex:'taskName'
        },{
			type:"combox",   
			title:"是否仅工作日执行",
			dataIndex: 'execTimeflag',
			dataText: 'execTimeflag',
			fieldText:"name",
			fieldValue:"code",
			data:[{'code':'1','name':'是'},{'code':'0','name':'否'}],
			//url:"../../../DataDict?type=Workshop",
			root: "dataset"
		},{
			type:"combox",   
			title:"触发频率",
			dataIndex: 'frequency',
			dataText: 'frequency',
			fieldText:"name",
			fieldValue:"code",
			url:"../../../../dynamic/dicView/listDic?view=v_equ_wbtaskfrequency",
			root: "dataset.data"
		}, { 
			type:"numberinput",
            title:"有效时长（小时）",
            dataIndex:'validTimelong'
        },{
			type:"combox",   
			title:"对象类型",
			dataIndex: 'workObjtype',
			dataText: 'workObjtype',
			fieldText:"name",
			fieldValue:"code",
			url:"../../../../dynamic/dicView/listDic?view=v_equ_taskjobobjecttype",
			root: "dataset.data"
		}, { 
        	type:"combox",
            title:"作业对象",
            dataIndex:'workObjid', 
            dataText: 'workObjid',
			fieldText:"name",
			fieldValue:"pid",
			url:"../../../../dynamic/dicView/listDic?view=v_equ_info",
			root: "dataset.data"
        },
       {
			type:"combox",   
			title:"接收类型",
			dataIndex: 'sendeeType',
			dataText: 'sendeeType',
			fieldText:"name",
			fieldValue:"code",
			/*data:[{'code':'1','name':'人员'},{'code':'0','name':'岗位'}],*/
			url:"../../../../dynamic/dicView/listDic?view=v_equ_tasksendeetype",
			root: "dataset.data"
		},{ 
        	type:"combox",
            title:"接收对象",
            dataIndex:'executor', 
            dataText: 'executor',
			fieldText:"name",
			fieldValue:"pid",
			url:"../../../../dynamic/dicView/listDic?view=v_sec_user",
			root: "dataset.data"
        },{ 
        	type:"textarea",
            title:"任务描述",
            dataIndex:'taskDes',
            rowspan:4
        }
		,{ 
        	type:"textarea",
            title:"备注",
            dataIndex:'remark',  
            rowspan:4
        }
    ],
    hidefields : ['pid',"wbTasktype"] ,
    layoutConfig : {
        columns : 2
    },
    onsubmit: function(){
    	//$('#Formbody').data('bootstrapValidator').validate();  
	    //是否通过校验
    	//if(!$('#Formbody').data('bootstrapValidator').isValid()){  
    	//	alert(3);
    		// 没有通过校验 
    	//} else {
	    	if(window.dialogArguments!=undefined){
	    		//开启验证
	    	    $('#Formbody').data('bootstrapValidator').validate();  
	    		if(!$('#Formbody').data('bootstrapValidator').isValid()){  
	        		return ;
	        	} 
	    		var objs = SF.getFormValues();
	    		var result=$.ajax({
	    	        type: "POST",
	    	        dataType: "json",
	    	        url: "../../../../dynamic/WbtaskDefine/saveEquWbtaskDefine",
	    	        async: false,//同步  
	    	        data:objs ,
	    	        success: function (result) {
	    	           	
	    	        },
	    	        error: function(data) {
	    	        //	BootstrapDialog.alert(data.message);
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
    	//}
    }
};


$(document).ready(function() {   
	SF.FormBody.onload(formbodyConfig);
	var mode = window.dialogArguments.mode;
	var data = window.dialogArguments.data;
	SF.BindFormData(data);
	if(mode == 'add'){
		
	}else if(mode == 'update'){
		var f_pid = window.dialogArguments.data.f_pid;
		$.post("../../../../dynamic/WbtaskDefine/getEquWbtaskDefineById?id="+f_pid,{},function(data){
			SF.BindFormData(data);
		});
	}
	//只开发部分，额外代码 ，完整后删除  ----start
		$("#workObjtype").find("option[value='1']").attr("selected",true);
		$("#workObjtype").attr("disabled",true);
		$("#sendeeType").find("option[value='1']").attr("selected",true);
		$("#sendeeType").attr("disabled",true);
	//-------------------end--------------------------
		
	// 表单验证  ---------stasrt----------、
		$('#Formbody').bootstrapValidator({
			      message: 'This value is not valid',
			      feedbackIcons: {/*输入框不同状态，显示图片的样式*/
			        valid: 'glyphicon glyphicon-ok',
			        invalid: 'glyphicon glyphicon-remove',
			        validating: 'glyphicon glyphicon-refresh'
			      },
			      fields: {/*验证*/
			        taskName: {/*键名username和input name值对应*/
			          message: 'The name is not valid',
			          validators: {
			            notEmpty: {/*非空提示*/
			              message: '不能为空'
			            }
			          }
			        },
					workObjid: {/*键名sysFlag和input name值对应*/
						          message: 'The sysFlag is not valid',
						          validators: {
						            notEmpty: {/*非空提示*/
						               message: '不能为空'
						            }
						         }
						     },
					frequency:{/*键名sysFlag和input name值对应*/
						          message: 'The sysFlag is not valid',
						          validators: {
						            notEmpty: {/*非空提示*/
						               message: '不能为空'
						            }
						         }
						     },
					execTimeflag:{/*键名sysFlag和input name值对应*/
						          message: 'The sysFlag is not valid',
						          validators: {
						            notEmpty: {/*非空提示*/
						               message: '不能为空'
						            }
						         }
						     },
					validTimelong:{/*键名sysFlag和input name值对应*/
						          message: 'The sysFlag is not valid',
						          validators: {
						            notEmpty: {/*非空提示*/
						               message: '不能为空'
						            }
						         }
						     },
					
					workObjtype:{/*键名sysFlag和input name值对应*/
						          message: 'The sysFlag is not valid',
						          validators: {
						            notEmpty: {/*非空提示*/
						               message: '不能为空'
						            }
						         }
						     },
					
					sendeeType:{/*键名sysFlag和input name值对应*/
						          message: 'The sysFlag is not valid',
						          validators: {
						            notEmpty: {/*非空提示*/
						               message: '不能为空'
						            }
						         }
						     },
					
					executor:{/*键名sysFlag和input name值对应*/
						          message: 'The sysFlag is not valid',
						          validators: {
						            notEmpty: {/*非空提示*/
						               message: '不能为空'
						            }
						         }
						     }
						}
			    });
})