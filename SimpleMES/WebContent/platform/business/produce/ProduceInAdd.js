
var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", 
    title : "生产工单投入维护",
    berth : "detailForm",
    items : [
       { 
        	type:"combox",
            title:"生产线",
        	dataIndex: 'produceLine',
			dataText: 'produceLine',
			fieldText:"name",
			fieldValue:"pid",
			url:"../../../../dynamic/dicView/listDic?view=v_produceline",
			root: "dataset.data"
        },{ 
        	type:"combox",
            title:"设备名称",
        	dataIndex: 'equCode',
			dataText: 'equName',
			fieldText:"name",
			fieldValue:"pid",
			url:"../../../../dynamic/dicView/listDic?view=v_equ_info",
			root: "dataset.data"
        },{ 
        	type:"combox",
            title:"投入物料名称",
        	dataIndex: 'matCode',
			dataText: 'matName',
			fieldText:"name",
			fieldValue:"code",
			url:"../../../../dynamic/dicView/listDic?view=v_mat_inputmat",
			root: "dataset.data"
        },{ 
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
            title:"投入物料数量",
            dataIndex:'quantity'
        },{
	        dataIndex: 'inputStVo',
	        title: '投料开始时间',
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
	        dataIndex: 'inputEtVo',
	        title: '投料结束时间',
			type:"datetime",
			config:{
				format: 'yyyy-mm-dd hh:ii:ss',
//		        showSecond: true,
		    	autoclose:true,
		    	minView: 0 ,
		    	minuteStep:1,
				initialDate: new Date()
			}
	    },{ 
        	type:"textinput",
            title:"投入物料批次号",
            dataIndex:'matLotno'
        }
    ],
    hidefields : ['pid','pwoId'] ,
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
    	        url: "../../../dynamic/Produce/saveProduceIn",
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
		var pwoId = window.dialogArguments.data.pwoId;
		$("#pwoId").val(pwoId);
	}else if(mode == 'update'){
		var f_pid = window.dialogArguments.data.f_pid;
		$.post("../../../dynamic/Produce/getProduceInById?id="+f_pid,{},function(data){
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
		        produceLine: {/*键名username和input name值对应*/
		          message: 'The name is not valid',
		          validators: {
		            notEmpty: {/*非空提示*/
		              message: '不能为空'
		            }
		          }
		        },
				equCode: {/*键名sysFlag和input name值对应*/
					          message: 'The equCode is not valid',
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
				quantity:{/*键名sysFlag和input name值对应*/
					          message: 'The quantity is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					               message: '不能为空'
					            }
					         }
					     },
				
				inputStVo:{/*键名sysFlag和input name值对应*/
					          message: 'The inputStVo is not valid',
							trigger:'change',
					          validators: {
					            notEmpty: {/*非空提示*/
					               message: '不能为空'
					            }
					         }
					     },
				
				inputEtVo:{/*键名sysFlag和input name值对应*/
					          message: 'The inputEtVo is not valid',
							trigger:'change',
					          validators: {
					            notEmpty: {/*非空提示*/
					               message: '不能为空'
					            }
					         }
					     },
				
				matLotno:{/*键名sysFlag和input name值对应*/
					          message: 'The matLotno is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					               message: '不能为空'
					            }
					         }
					     }
					}
		    });
})