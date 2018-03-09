
var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", 
    title : "生产工单维护",
    berth : "detailForm",
    items : [
        { 
        	type:"textinput",
            title:"生产工单号",
            dataIndex:'pwoSn'
        },{
			type:"combox",   
			title:"工单类型",
			dataIndex: 'pwoType',
			dataText: 'pwoTypeName',
			fieldText:"name",
			fieldValue:"pid",
			url:"../../../../dynamic/dicView/listDic?view=v_produce_wotype",
			root: "dataset.data"
		},{
			type:"combox",   
			title:"生产车间名称",
			dataIndex: 'workshopId',
			dataText: 'workshopName',
			fieldText:"name",
			fieldValue:"pid",
			url:"../../../../dynamic/dicView/listDic?view=v_workshop",
			root: "dataset.data"
		},{
			type:"combox",   
			title:"产出物料名称",
			dataIndex: 'matCode',
			dataText: 'matName',
			fieldText:"name",
			fieldValue:"code",
			url:"../../../../dynamic/dicView/listDic?view=v_mat_cpmat",
			root: "dataset.data"
		}/*,{
			type:"combox",   
			title:"成品名称",
			dataIndex: 'productCode',
			dataText: 'productName',
			fieldText:"name",
			fieldValue:"code",
			url:"../../../../dynamic/dicView/listDic?view=v_mat_cpmat",
			root: "dataset.data"
		}*/,{
			type:"combox",   
			title:"单位",
			dataIndex: 'unit',
			dataText: 'unitName',
			fieldText:"name",
			fieldValue:"pid",
			url:"../../../../dynamic/dicView/listDic?view=v_mat_units",
			root: "dataset.data"
		},{ 
			type:"numberinput",
            title:"计划生产数量",
            dataIndex:'planQuantity'
        },{
	        dataIndex: 'planStVo',
	        title: '计划开始时间',
			type:"datetime",
			config:{
				format: 'yyyy-mm-dd hh:ii:ss',
		        showSecond: true,
		    	autoclose:true,
		    	minView: 0 ,
		    	minuteStep:1,
				initialDate: new Date()
			}
	    },{
	        dataIndex: 'planEtVo',
	        title: '计划完成时间',
			type:"datetime",
			config:{
				format: 'yyyy-mm-dd hh:ii:ss',
//		        showSecond: true,
		    	autoclose:true,
		    	minView: 0 ,
		    	minuteStep:1,
				initialDate: new Date()
			}
	    }
    ],
    hidefields : ['pid'] ,
    layoutConfig : {
        columns : 2
    },
    onsubmit: function(){
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
    	        url: "../../../dynamic/Produce/saveProduceWo",
    	        async: false,//同步  
    	        data:objs ,
    	        success: function (result) {
    	           	if(result.status==200){
    	           	}
    	        },
    	        error: function(data) {
    	        	BootstrapDialog.alert(data.message);
    	         }
    	    });
    		if(result.responseJSON.status==200){
    			//BootstrapDialog.alert(result.responseJSON.message);
    			window.returnValue = "ok"; 
            	window.close();
            	//return false;
    		}
    	}
    }
};


$(document).ready(function() {   
	SF.FormBody.onload(formbodyConfig);
	var mode = window.dialogArguments.mode;
	var data = window.dialogArguments.data;
	SF.BindFormData(data);
	if(mode == 'add'){
		$("#pwoSn").val("系统自动生成");
		$("#pwoSn").attr("disabled",true);
	}else if(mode == 'update'){
		$("#pwoSn").attr("disabled",true);
		var f_pid = window.dialogArguments.data.f_pid;
		$.post("../../../dynamic/Produce/getProduceWoById?id="+f_pid,{},function(data){
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
			        pwoType: {/*键名username和input name值对应*/
			          message: 'The name is not valid',
			          validators: {
			            notEmpty: {/*非空提示*/
			              message: '不能为空'
			            }
			          }
			        },
					workshopId: {/*键名sysFlag和input name值对应*/
						          message: 'The workshopId is not valid',
						          validators: {
						            notEmpty: {/*非空提示*/
						               message: '不能为空'
						            }
						         }
						     },
					matCode:{/*键名sysFlag和input name值对应*/
						          message: 'The matCode is not valid',
						          validators: {
						            notEmpty: {/*非空提示*/
						               message: '不能为空'
						            }
						         }
						     },
					unit:{/*键名sysFlag和input name值对应*/
						          message: 'The unit is not valid',
						          validators: {
						            notEmpty: {/*非空提示*/
						               message: '不能为空'
						            }
						         }
						     },
					
					planQuantity:{/*键名sysFlag和input name值对应*/
						          message: 'The planQuantity is not valid',
						          validators: {
						            notEmpty: {/*非空提示*/
						               message: '不能为空'
						            }
						         }
						     },
					
					planStVo:{/*键名sysFlag和input name值对应*/
						          message: 'The planStVo is not valid',
								trigger:'change',
						          validators: {
						            notEmpty: {/*非空提示*/
						               message: '不能为空'
						            }
						         }
						     },
					
					planEtVo:{/*键名sysFlag和input name值对应*/
						          message: 'The planEtVo is not valid',
								trigger:'change',
						          validators: {
						            notEmpty: {/*非空提示*/
						               message: '不能为空'
						            }
						         }
						     }
						}
			    });
})