
var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", 
    title : "维修单新增",
    berth : "detailForm",
    items : [
        { 
        	type:"combox",   
			title:"报修类型",
			dataIndex: 'repairType',
			fieldText: 'name',
			fieldValue: 'pid',
			//data:[{'code':1,'name':'正常'},{'code':10,'name':'停用'}
			      //,{'code':20,'name':'维修'},{'code':40,'name':'报废'}],
			    url: "../../../../dynamic/dicView/listDic?view=v_equ_repairapplytype",
				root: "dataset.data"
        },
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
            
        }
//        ,{ 
//        	type:"textinput",
//            title:"报修申请人",
//            dataIndex:'applyUsername'
//        }
//        ,{
//			type:"datetime",
//		    title:"报修申请时间",
//		    dataIndex:'receiveTime',
//		    config:{
//		        format: 'yyyy-mm-dd',
//		    	minView:'month',
//				initialDate: new Date()
//			}
//		}
        ,{
			type:"datetime",
		    title:"发生时间",
		    dataIndex:'occurTime',
		    config:{
		    	format: 'yyyy-mm-dd hh:ii:ss',
		        //showSecond: true,
		    	autoclose:true,
		    	minView: 0 ,
		    	minuteStep:1,
				initialDate: new Date()
			}
		}
		,{ 
        	type:"combox",
            title:"维修人",
            dataIndex: 'repairUserid',
			fieldText: 'name',
			fieldValue: 'pid',
			//data:[{'code':1,'name':'正常'},{'code':10,'name':'停用'}
			      //,{'code':20,'name':'维修'},{'code':40,'name':'报废'}],
			    url: "../../../../dynamic/dicView/listDic?view=v_sec_user",
				root: "dataset.data"
        },{ 
        	type:"textarea",
            title:"异常描述",
            dataIndex:'abnormalDes',
            rowspan: 5,
            colspan: 13
        }
//        ,{ 
//        	type:"textarea",
//            title:"备注",
//            dataIndex:'remark',  
//            rowspan: 5
//        }
    ],
    hidefields : ['pid','applyUserid'] ,
    layoutConfig : {
        columns : 2
    },
    onsubmit: function(){ 
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
	 		       url: "../../../../dynamic/Repair/save",  
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
	var row = window.dialogArguments.data.row;
	console.log(mode);
	if(mode == 'add'){
		
	}else if(mode == 'update'){
//		var f_pid = window.dialogArguments.data.f_pid;
		$.ajaxSettings.async = false; 
		$.post("../../../../dynamic/Repair/findRepairById?id="+row.pid,{},function(data){
			console.log(row);
			SF.BindFormData(data);
			if(data.occurTime!=null){
				$("#occurTime").val(formatDate(data.occurTime));
			}
			$("#equName").val(row.equName);
			console.log(SF.getFormValues());//获得表单的value
		});
	}else if("desTarget"){
		console.log(row);
		SF.BindFormData(row);
	}
	
	$('#Formbody').bootstrapValidator({
		      message: 'This value is not valid',
		      feedbackIcons: {/*输入框不同状态，显示图片的样式*/
		        valid: 'glyphicon glyphicon-ok',
		        invalid: 'glyphicon glyphicon-remove',
		        validating: 'glyphicon glyphicon-refresh'
		      },
		      fields: {/*验证*/
		        repairType: {/*键名username和input name值对应*/
		          message: 'The repairType is not valid',
		          validators: {
		            notEmpty: {/*非空提示*/
		              message: '报修类型不能为空'
		            }
		          }
		        },
				equId: {/*键名username和input name值对应*/
			          message: 'The equId is not valid',
			          validators: {
			            notEmpty: {/*非空提示*/
			              message: '设备名称不能为空'
			            }
			         }
			     },
			occurTime: {/*键名username和input name值对应*/
					          message: 'The occurTime is not valid',
					          trigger:'change',
								validators: {
					            notEmpty: {/*非空提示*/
					              message: '发生时间不能为空'
					            }
					         }
					     },
					repairUserid: {/*键名username和input name值对应*/
				          message: 'The repairUserid is not valid',
				          validators: {
				            notEmpty: {/*非空提示*/
				              message: '维修时间不能为空'
				            }
				         }
				     },
				abnormalDes: {/*键名password和input name值对应*/
					          message: 'The abnormalDes is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '异常描述不能为空'
					            }
					         }
					     }
		     	  }
		    });
    //alert(window.dialogArguments.data.kname); 
    //console.info(window.dialogArguments.data); 
	
})