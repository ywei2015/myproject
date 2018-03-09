
var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", 
    title : "生产工单产出维护",
    berth : "detailForm",
    items : [
       /*{ 
        	type:"combox",
            title:"对象类别",
        	dataIndex: 'collectType',
			fieldText:"name",
			fieldValue:"code",
			data:[{'code':'1','name':'小单元'},{'code':'2','name':'工序'},{'code':'3','name':'工段'},{'code':'4','name':'生产线'},{'code':'5','name':'车间'}],
//			url:"../../../../dynamic/dicView/listDic?view=v_equ_tasktype",
			root: "dataset.item"
        },*/{ 
        	type:"combox",
            title:"生产对象名称",
        	dataIndex: 'objectId',
			fieldText:"name",
			fieldValue:"pid",
			url:"../../../../dynamic/dicView/listDic?view=v_equ_info",
			root: "dataset.data"
        },{ 
        	type:"combox",
            title:"产出物料名称",
        	dataIndex: 'matCode',
			dataText: 'matName',
			fieldText:"name",
			fieldValue:"code",
			url:"../../../../dynamic/dicView/listDic?view=v_mat_outputmat",
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
            title:"产出合格数量",
            dataIndex:'quantity'
        },{ 
			type:"numberinput",
            title:"不良品数量",
            dataIndex:'ngQuantity'
        },{ 
			type:"textinput",
            title:"位置",
            dataIndex:'lacation'
        },{
	        dataIndex: 'outStVo',
	        title: '产出开始时间',
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
	        dataIndex: 'outEtVo',
	        title: '产出结束时间',
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
            title:"仓库",
            dataIndex:'warehouse'
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
    	        url: "../../../dynamic/Produce/saveProduceOut",
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
		$.post("../../../dynamic/Produce/getProduceOutById?id="+f_pid,{},function(data){
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
		        /*collectType: {键名username和input name值对应
		          message: 'The name is not valid',
		          validators: {
		            notEmpty: {非空提示
		              message: '不能为空'
		            }
		          }
		        },*/
				objectId: {/*键名sysFlag和input name值对应*/
					          message: 'The objectId is not valid',
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
				lacation:{/*键名sysFlag和input name值对应*/
					          message: 'The lacation is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					               message: '不能为空'
					            }
					         }
					     },
				outStVo:{/*键名sysFlag和input name值对应*/
					          message: 'The outStVo is not valid',
							trigger:'change',
					          validators: {
					            notEmpty: {/*非空提示*/
					               message: '不能为空'
					            }
					         }
					     },
				
				outEtVo:{/*键名sysFlag和input name值对应*/
					          message: 'The outEtVo is not valid',
							trigger:'change',	
					          validators: {
					            notEmpty: {/*非空提示*/
					               message: '不能为空'
					            }
					         }
					     },
				
				warehouse:{/*键名sysFlag和input name值对应*/
					          message: 'The warehouse is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					               message: '不能为空'
					            }
					         }
					     }
					}
		    });
})