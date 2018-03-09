
var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", 
    title : "设备台账",
    berth : "detailForm",
    items : [
        { 
        	type:"textinput",
            title:"设备名称",
            dataIndex:'equName'
            
        },
        { 
        	type:"textinput",
            title:"设备编号",
            dataIndex:'equCode'
            
        },
        {
			type:"combox",   
			title:"设备种类",
			dataIndex: 'equKind',
			fieldText: "name",	
			fieldValue:"pid",
			//data:[{'code':'1','name':'男'},{'code':'0','name':'女'}],
			url: "../../../../dynamic/dicView/listDic?view=v_equ_kind",
			root: "dataset.data"
		},
		{ 
        	type:"choice",
            title:"部门",
            dataIndex:'orgId', 
           // dataText: 'usersName',
            modalurl:'selectOrg.html',
            modaltarget:'myModal1'
        },
        { 
        	type:"textinput",
            title:"固定资产号",
            dataIndex:'fixassetCode'
            
        },
        { 
        	type:"textinput",
            title:"生产厂家",
            dataIndex:'manufacturer'
            
        },
        { 
        	type:"textinput",
            title:"设备型号",
            dataIndex:'equModel'
            
        },
        { 
        	type:"textinput",
            title:"出厂编号",
            dataIndex:'factoryNum'
            
        },
        { 
        	type:"datetime",
            title:"购置日期",
            dataIndex:'purchaseDate',
            config:{
	            format: 'yyyy-mm-dd',
		    	minView:'month',
				initialDate: new Date()
			}
        },
        { 
        	type:"combox",   
			title:"设备状态",
			dataIndex: 'status',
			fieldText: 'name',
			fieldValue: 'code',
			//data:[{'code':1,'name':'正常'},{'code':10,'name':'停用'}
			     // ,{'code':20,'name':'维修'},{'code':40,'name':'报废'}],
			url: "../../../../dynamic/dicView/listDic?view=v_equ_status",
			root: "dataset.data"
            
        },
        { 
        	type:"textinput",
            title:"安装位置",
            dataIndex:'location'
            
        },
        { 
        	type:"textinput",
            title:"安装用途",
            dataIndex:'purpose'
            
        },
        { 
        	type:"textarea",
            title:"备注",
            dataIndex:'remark',  
            rowspan:4
        }
    ],
    hidefields : ['pid'] ,
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
     		       url: "../../../../dynamic/TzInfo/saveInfo",  
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
	SF.FormBody.onload(formbodyConfig);
	if(window.dialogArguments==undefined) return;
	var mode = window.dialogArguments.mode;
	var info = window.dialogArguments.data.info;
	
	console.log(info);
	if(mode == 'add'){
		
	}else if(mode == 'update'){
		var f_pid = info.pid;
		$.ajaxSettings.async = false; 
		$.post("../../../../dynamic/TzInfo/findInfoById?id="+f_pid,{},function(data){
			console.log(data);
			$("#norgId").val(info.orgName);
			SF.BindFormData(data);
		});
	}
    
	$('#myModal1').on('hide.bs.modal', function () { 
        $('form').bootstrapValidator('resetForm');
	});
	
	$('#Formbody').bootstrapValidator({
		      message: 'This value is not valid',
		      feedbackIcons: {/*输入框不同状态，显示图片的样式*/
		        valid: 'glyphicon glyphicon-ok',
		        invalid: 'glyphicon glyphicon-remove',
		        validating: 'glyphicon glyphicon-refresh'
		      },
		      fields: {/*验证*/
		        equName: {/*键名username和input name值对应*/
		          message: 'The equName is not valid',
		          validators: {
		            notEmpty: {/*非空提示*/
		              message: '设备名称不能为空'
		            }
		          }
		        },
		       equCode: {/*键名username和input name值对应*/
			          message: 'The equCode is not valid',
			          validators: {
			            notEmpty: {/*非空提示*/
			              message: '设备编号不能为空'
			            }
			         }
			     },
			   equKind: {/*键名username和input name值对应*/
					          message: 'The jobno is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '设备种类不能为空'
					            }
					         }
					     },
			   norgId: {/*键名username和input name值对应*/
				          message: 'The orgId is not valid',
				          validators: {
				            notEmpty: {/*非空提示*/
				              message: '部门不能为空'
				            }
				         }
				     },
				fixassetCode: {/*键名password和input name值对应*/
					          message: 'The fixassetCode is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '固定资产不能为空'
					            }
					         }
					     },
			   manufacturer: {/*键名sysFlag和input name值对应*/
				          message: 'The manufacturer is not valid',
				          validators: {
				            notEmpty: {/*非空提示*/
				              message: '生产厂家不能为空！'
				            }
				         }
				     },
				equModel: {/*键名sex和input name值对应*/
					          message: 'The equModel is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '设备型号不能为空'
					            }
					         }
					     },
				factoryNum: {/*键名sex和input name值对应*/
					          message: 'The equModel is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '出厂编号不能为空'
					            }
					         }
					     },
				purchaseDate: {/*键名sex和input name值对应*/
					          message: 'The purchaseDate is not valid',
					          trigger:'change',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '购置日期不能为空'
					            }
					         }
					     },
				status: {/*键名sex和input name值对应*/
					          message: 'The status is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '设备状态不能为空'
					            }
					         }
					     },
					purpose: {/*键名sex和input name值对应*/
					          message: 'The status is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '设备用途不能为空'
					            }
					         }
					     }
		      }
		    });
	//开启验证
   // $('#Formbody').data('bootstrapValidator').validate();
})