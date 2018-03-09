
var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", 
    title : "设备维保任务执行",
    berth : "detailForm",
    items : [
		{
			type:"combox",   
			title:"执行结果",
			dataIndex: 'isabnormal',
			dataText: 'isabnormalName',
			fieldText:"name",
			fieldValue:"code",
			data:[{'code':'1','name':'正常'},{'code':'0','name':'异常'}],
			//url:"../../../DataDict?type=Workshop",
			root: "dataset"
		},/*{
			type:"datetime",
		    title:"执行时间",
		    dataIndex:'lastModifiedTime',
		    config:{
		        format: 'yyyy-mm-dd',
		    	minView:'month',
				initialDate: new Date()
			}
		},{ 
        	type:"textinput",
            title:"执行人",
            dataIndex:'lastModifierName'
        },{ 
        	type:"combox",
            title:"执行人",
            dataIndex:'lastModifier', 
            dataText: 'lastModifier',
			fieldText:"name",
			fieldValue:"pid",
			url:"../../../../dynamic/dicView/listDic?view=v_sec_user",
			root: "dataset.data"
        },*/{ 
        	type:"textarea",
            title:"异常描述",
            dataIndex:'abnormalDes',  
            rowspan: 6
        }
    ],
    hidefields : ['pid'] ,
    layoutConfig : {
        columns : 1
    },
    onsubmit: function(){ 
    	//alert($('#f_name').val());
    	//alert(JSON.stringify(SF.getSearchParams()));
    	//alert("Form Submit OnClick!"+window.dialogArguments.data.kname);
    	//console.log(window.dialogArguments);
    	if(window.dialogArguments!=undefined) 
    		
    		$('#Formbody').data('bootstrapValidator').validate();  
		if(!$('#Formbody').data('bootstrapValidator').isValid()){  
    		return ;
    	} 
    	var objs = SF.getFormValues();
    	var result=$.ajax({
	        type: "POST",
	        dataType: "json",
	        url: "../../../../dynamic/WbtaskDefine/updatEquWbtaskStep",
	        async: false,//同步  
	        data:objs ,
	        success: function (result) {
	           	if(result.status==200){
	           	}
	        },
	        error: function(data) {
	        	BootstrapDialog.show({
		            title: '提示',
		            message: result.message,
		            buttons: [{
		                label: '确认',
		                action: function(dialog) {
		                	dialog.close();
		                	return false;
		                }
		            }]
		        });
	        	//BootstrapDialog.alert(data.message);
	         }
	    });
    	
    	if(result.responseJSON.status==200){
    		window.returnValue = "ok"; 
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
    }
};


$(document).ready(function() {
	//formbodyConfig["url"] = MesWebApiUrl + "/sec/user/getuserpage/"+f_pid;
	//formbodyConfig["dataroot"] = 'dataset';
	SF.FormBody.onload(formbodyConfig);
	var mode = window.dialogArguments.mode;
	var data = window.dialogArguments.data;
	SF.BindFormData(data);
	if(mode == 'add'){
		
	}else if(mode == 'update'){
		var f_pid = window.dialogArguments.data.f_pid;
		/*$.post("../../../../dynamic/WbtaskDefine/getEquWbtaskStepById?id="+f_pid,{},function(data){
			SF.BindFormData(data);
		});*/
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
		        
				isabnormal: {/*键名sysFlag和input name值对应*/
					          message: 'The sysFlag is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					               message: '不能为空'
					            }
					         }
					     },
					
					abnormalDes:{/*键名sysFlag和input name值对应*/
					          message: 'The sysFlag is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					               message: '不能为空'
					            }
					         }
					     }
					}
		    });

	selectChange();
	$("#isabnormal").bind("change",function(){
		selectChange();
	}
	);
});
function selectChange(){
	var isabnormal= $("#isabnormal").val();
	if(isabnormal!='0'){
		$("#abnormalDes").parent().hide();
	}else if(isabnormal=='0'){
		$("#abnormalDes").parent().show();
	}
}