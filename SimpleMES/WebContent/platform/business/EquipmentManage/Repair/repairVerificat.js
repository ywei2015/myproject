
var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", 
    title : "设备维修验证",
    berth : "detailForm",
    items : [
//    	{ 
//        	type:"combox",   
//			title:"设备名称",
//			dataIndex: 'equId',
//			fieldText: 'name',
//			fieldValue: 'pid',
//			//data:[{'code':1,'name':'正常'},{'code':10,'name':'停用'}
//			    //  ,{'code':20,'name':'维修'},{'code':40,'name':'报废'}],
//			url: "../../../../dynamic/Repair/findEqu",
//			root: "dataset.data"
//        },{
//			type:"combox",   
//			title:"故障类型",
//			dataIndex: 'abnormalType',
//			fieldText:"name",
//			fieldValue:"pid",
//			//data:[{'code':'1','name':'机械'},{'code':'0','name':'电器'}],
//			url: "../../../../dynamic/dicView/listDic?view=v_equ_faulttype",
//			root: "dataset.data"
//		},{
//			type:"datetime",
//		    title:"维修开始时间",
//		    dataIndex:'repairSt',
//		    config:{
//		    	format: 'yyyy-mm-dd hh:ii:ss',
//		        //showSecond: true,
//		    	autoclose:true,
//		    	minView: 0 ,
//		    	minuteStep:1,
//				initialDate: new Date()
//			}
//		},{
//			type:"datetime",
//		    title:"维修结束时间",
//		    dataIndex:'repairEt',
//		    config:{
//		    	format: 'yyyy-mm-dd hh:ii:ss',
//		        //showSecond: true,
//		    	autoclose:true,
//		    	minView: 0 ,
//		    	minuteStep:1,
//				initialDate: new Date()
//			}
//		}
////		,{
////			type:"datetime",
////		    title:"维修日期",
////		    dataIndex:'endssTime',
////		    config:{
////		        format: 'yyyy-mm-dd hh:mm:ss',
////		    	minView:'month',
////				initialDate: new Date()
////			}
////		}
//		,{ 
//			type:"combox",
//            title:"维修人",
//            dataIndex: 'repairUserid',
//			fieldText: 'name',
//			fieldValue: 'pid',
//			url: "../../../../dynamic/dicView/listDic?view=v_sec_user",
//		    root: "dataset.data"
//        },{ 
//        	type:"textarea",
//            title:"维修情况描述",
//            dataIndex:'repairProdes',  
//            rowspan: 4
//        },
        {
			type:"combox",   
			title:"验证结果",
			dataIndex: 'chekcResult',
			fieldText:"name",
			fieldValue:"code",
			data:[{'code':'1','name':'已解决'},{'code':'0','name':'未解决'}],
			//url:"../../../DataDict?type=Workshop",
			root: "dataset"
		},{ 
        	type:"textinput",
            title:"结果描述",
            dataIndex:'invalidDes'
        },{ 
        	type:"combox",
            title:"验证人",
            dataIndex: 'checkUserid',
			fieldText: 'name',
			fieldValue: 'pid',
			url: "../../../../dynamic/dicView/listDic?view=v_sec_user",
		    root: "dataset.data"
        },{
			type:"datetime",
		    title:"验证时间",
		    dataIndex:'checkEt',
		    config:{
		    	format: 'yyyy-mm-dd hh:ii:ss',
		        //showSecond: true,
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
			       url: "../../../../dynamic/Record/saveVerificat",  
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
	console.log(SF.FormBody.onload(formbodyConfig));
	if(window.dialogArguments==undefined) return;
	var mode = window.dialogArguments.mode;
	var row = window.dialogArguments.data.row;
	console.log(mode);
	if(mode == 'add'){
		
	}else if(mode == 'update'){
		$.ajaxSettings.async = false; 
		$.post("../../../../dynamic/Record/findRepairById?id="+row.pid,{},function(data){
			SF.BindFormData(data);
			if(data.checkEt!=null){
				$("#checkEt").val(formatDate(data.checkEt));
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
		        chekcResult: {/*键名username和input name值对应*/
		          message: 'The chekcResult is not valid',
		          validators: {
		            notEmpty: {/*非空提示*/
		              message: '验证结果不能为空'
		            }
		          }
		        },
			invalidDes: {/*键名username和input name值对应*/
			          message: 'The invalidDes is not valid',
			          validators: {
			            notEmpty: {/*非空提示*/
			              message: '验证描述不能为空'
			            }
			         }
			     },
			checkUserid: {/*键名username和input name值对应*/
					          message: 'The checkUserid is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '验证人不能为空'
					            }
					         }
					     },
				checkEt: {/*键名username和input name值对应*/
				          message: 'The checkEt is not valid',
				          trigger:'change',
				          validators: {
				            notEmpty: {/*非空提示*/
				              message: '验证时间不能为空'
				            }
				         }
				     }
		      }
		    });
	
})