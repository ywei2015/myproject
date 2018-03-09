
var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", 
    title : "设备维修记录",
    berth : "detailForm",
    items : [
    	{ 
        	type:"combox",   
			title:"设备名称",
			dataIndex: 'equId',
			fieldText: 'name',
			fieldValue: 'pid',
			//data:[{'code':1,'name':'正常'},{'code':10,'name':'停用'}
			    //  ,{'code':20,'name':'维修'},{'code':40,'name':'报废'}],
			url: "../../../../dynamic/Repair/findEqu",
			root: "dataset.data"
        },{
			type:"combox",   
			title:"故障类型",
			dataIndex: 'abnormalType',
			fieldText:"name",
			fieldValue:"pid",
			//data:[{'code':'1','name':'机械'},{'code':'0','name':'电器'}],
			url: "../../../../dynamic/dicView/listDic?view=v_equ_faulttype",
			root: "dataset.data"
		},{
			type:"datetime",
		    title:"维修开始时间",
		    dataIndex:'repairSt',
		    config:{
		    	format: 'yyyy-mm-dd hh:ii:ss',
		        //showSecond: true,
		    	autoclose:true,
		    	minView: 0 ,
		    	minuteStep:1,
				initialDate: new Date()
			}
		},{
			type:"datetime",
		    title:"维修结束时间",
		    dataIndex:'repairEt',
		    config:{
		    	format: 'yyyy-mm-dd hh:ii:ss',
		        //showSecond: true,
		    	autoclose:true,
		    	minView: 0 ,
		    	minuteStep:1,
				initialDate: new Date()
			}
		}
//		,{
//			type:"datetime",
//		    title:"维修日期",
//		    dataIndex:'endssTime',
//		    config:{
//		        format: 'yyyy-mm-dd',
//		    	minView:'month',
//				initialDate: new Date()
//			}
//		}
		,{ 
			type:"combox",
            title:"维修人",
            dataIndex: 'repairUserid',
			fieldText: 'name',
			fieldValue: 'pid',
			url: "../../../../dynamic/dicView/listDic?view=v_sec_user",
		    root: "dataset.data"
        },{ 
        	type:"textarea",
            title:"维修情况描述",
            dataIndex:'repairProdes',  
            rowspan: 4
        }
    ],
    hidefields : ['pid'] ,
    layoutConfig : {
        columns : 2
    },
    onsubmit: function(){ 
    	console.log(SF.getFormValues());
    	$('#Formbody').data('bootstrapValidator').validate();
    	if(!$('#Formbody').data('bootstrapValidator').isValid()){  
    	    //没有通过校验
    		BootstrapDialog.alert('请填写完整！');
    	} else {
	    	if(window.dialogArguments!=undefined){ 
				console.log(SF.getFormValues());
				//$.ajaxSettings.async = false; 
				var res = $.ajax({  
	 		       type: "post",  
	 		       url: "../../../../dynamic/Record/saveRecord",  
	 		       cache:false,  
	 		       async:false,  
	 		       data:SF.getFormValues() 
		    		});
				//$.post("../../../../dynamic/TzInfo/saveInfo",SF.getFormValues() );
			    window.returnValue = "ok"; 
			    window.close();
	    	}
	    }
    }
};


$(document).ready(function() {   
	//formbodyConfig["url"] = MesWebApiUrl + "/sec/user/getuserpage/"+f_pid;
	//formbodyConfig["dataroot"] = 'dataset';
	SF.FormBody.onload(formbodyConfig);
	if(window.dialogArguments==undefined) return;
	var mode = window.dialogArguments.mode;
	var f_pid = window.dialogArguments.data.f_pid;
	console.log(mode);
	if(mode == 'add'){
		
	}else if(mode == 'update'){
		$.ajaxSettings.async = false; 
		$.post("../../../../dynamic/Record/findRepairById?id="+f_pid,{},function(data){
			console.log(f_pid);
			SF.BindFormData(data);
			if(data.repairSt!=null){
				$("#repairSt").val(formatDate(data.repairSt));
			}
			if(data.repairEt!=null){
				$("#repairEt").val(formatDate(data.repairEt));
			}
			console.log(SF.getFormValues());//获得表单的value
		});
	}
	
	$('#Formbody').bootstrapValidator({
		      message: 'This value is not valid',
		      feedbackIcons: {/*输入框不同状态，显示图片的样式*/
		        valid: 'glyphicon glyphicon-ok',
		        invalid: 'glyphicon glyphicon-remove',
		        validating: 'glyphicon glyphicon-refresh'
		      },
		      fields: {/*验证*/
		        equId: {/*键名username和input name值对应*/
		          message: 'The equId is not valid',
		          validators: {
		            notEmpty: {/*非空提示*/
		              message: '设备名称不能为空'
		            }
		          }
		        },
				abnormalType: {/*键名username和input name值对应*/
			          message: 'The abnormalType is not valid',
			          validators: {
			            notEmpty: {/*非空提示*/
			              message: '故障类型不能为空'
			            }
			         }
			     },
			 repairSt: {/*键名username和input name值对应*/
					          message: 'The jobno is not valid',
							  trigger:'change',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '维修开始时间不能为空'
					            }
					         }
					     },
				repairEt: {/*键名username和input name值对应*/
				          message: 'The repairEt is not valid',
						  trigger:'change',
				          validators: {
				            notEmpty: {/*非空提示*/
				              message: '维修结束时间不能为空'
				            }
				         }
				     },
				repairUserid: {/*键名password和input name值对应*/
					          message: 'The repairUserid is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '维修人不能为空'
					            }
					         }
					     },
				repairProdes: {/*键名sysFlag和input name值对应*/
				          message: 'The repairProdes is not valid',
				          validators: {
				            notEmpty: {/*非空提示*/
				              message: '维修描述不能为空！'
				            }
				         }
				     }
		      }
		    });
    //alert(window.dialogArguments.data.kname); 
    //console.info(window.dialogArguments.data); 
	
})