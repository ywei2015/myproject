
var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", 
    title : "人员管理",
    berth : "detailForm",
    items : [
        { 
        	type:"textinput",
            title:"姓名",
            dataIndex:'name'
            
        },
        { 
        	type:"textinput",
            title:"用户名",
            dataIndex:'code'
            
        },
        { 
        	type:"textinput",
            title:"工号",
            dataIndex:'jobno'
            
        },
        { 
        	type:"textinput",
            title:"手机号",
            dataIndex:'mobile'
            
        },
//        { 
//        	type:"textinput",
//            title:"密码",
//            dataIndex:'password'
//            
//        },
//        { 
//        	type:"textinput",
//            title:"确认密码",
//            dataIndex:'conpassword'
//            
//        },
        {
			type:"combox",   
			title:"有效性",
			dataIndex: 'sysFlag',
			dataText: 'sysFlagName',
			fieldText:"name",
			fieldValue:"code",
			data:[{'code':'1','name':'有效'},{'code':'0','name':'无效'}],
			//url:"../../../DataDict?type=Workshop",
			root: "dataset"
		},
		{
			type:"combox",   
			title:"性别",
			dataIndex: 'sex',
			dataText: 'name',
			fieldText: "name",	
			fieldValue:"code",
			data:[{'code':'1','name':'男'},{'code':'0','name':'女'}],
			//url:"../../../DataDict?type=Workshop",
			root: "dataset"
		},
		 { 
        	type:"choice",
            title:"部门",
            dataIndex:'orgId', 
           // dataText: 'usersName',
            modalurl:'selectOrg.html?orgId=1',
            modaltarget:'myModal1'
        },
        { 
        	type:"choice",
            title:"岗位",
            dataIndex:'postsId', 
            modalurl:'selectPosts.html?orgId=1',
            modaltarget:'myModal2'
        },
       
        { 
        	type:"textarea",
            title:"备注",
            dataIndex:'remark',  
            rowspan:2
        }
    ],
    hidefields : ['id'] ,
    layoutConfig : {
        columns : 2
    },
    onsubmit: function(){ 
    	//是否通过校验
    	$('#Formbody').data('bootstrapValidator').validate();
    	// alert($('#Formbody').data('bootstrapValidator').isValid());
    	if(!$('#Formbody').data('bootstrapValidator').isValid()){  
    	    //没有通过校验
    		BootstrapDialog.alert('请填写完整！');
    	} else {
    	   //通过校验，可进行提交等操作
    		//alert("校验 ");
    		if(window.dialogArguments!=undefined){ 
//    			console.log("通过校验 ");
    			//$.ajaxSettings.async = false; 
    			//$.post("../../../dynamic/User/save",SF.getFormValues() );
    			$.ajaxSettings.async = false; 
    			$.post("../../../dynamic/User/findUserName",{id:SF.getFormValues().id,code:SF.getFormValues().code,jobno:SF.getFormValues().jobno},function(data){
	    			if(data){
    				var res = $.ajax({  
	    		       type: "post",  
	    		       url: "../../../dynamic/User/save",  
	    		       cache:false,  
	    		       async:false,  
	    		       data:SF.getFormValues() 
		    		});
	    			//console.info(res);
	    			//alert(res);
	    		    window.returnValue = "ok"; 
	    		    window.close();
	    			}else{
	    				BootstrapDialog.alert("该用户名或工号已存在!");
	    			}
    			})
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
	var user = window.dialogArguments.data.user;
	console.log(mode);
	if(mode == 'add'){
		//$("#password").val("******");
		//$("#conpassword").val("******");
	}else if(mode == 'update'){
		var f_pid = user.id;
		$.ajaxSettings.async = false; 
		$.post("../../../dynamic/User/findUserById?id="+f_pid,{},function(data){
			console.log(data);
			$("#norgId").val(data.orgName);
			$("#npostsId").val(data.postsName);
			SF.BindFormData(data);
			//$("#password").val("******");
			//$("#conpassword").val("******");
			console.log(SF.getFormValues());//获得表单的value
		});
	}
    //alert(window.dialogArguments.data.kname); 
    //console.info(window.dialogArguments.data); 
	
	$('#myModal1').on('hide.bs.modal', function () { 
         $('form').bootstrapValidator('resetForm');
	});
	$('#myModal2').on('hide.bs.modal', function () { 
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
		        name: {/*键名username和input name值对应*/
		          message: 'The name is not valid',
		          validators: {
		            notEmpty: {/*非空提示*/
		              message: '姓名不能为空'
		            }
		          }
		        },
		       code: {/*键名username和input name值对应*/
			          message: 'The code is not valid',
			          validators: {
			            notEmpty: {/*非空提示*/
			              message: '用户名不能为空'
			            }
			         }
			     },
		       jobno: {/*键名username和input name值对应*/
					          message: 'The jobno is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '工号不能为空jobno'
					            }
					         }
					     },
				mobile: {/*键名username和input name值对应*/
				          message: 'The mobile is not valid',
				          validators: {
				            notEmpty: {/*非空提示*/
				              message: '手机号不能为空'
				            }
				         }
				     },
//				password: {/*键名password和input name值对应*/
//					          message: 'The password is not valid',
//					          validators: {
//					            notEmpty: {/*非空提示*/
//					              message: '密码不能为空'
//					            }
//					         }
//					     },
				sysFlag: {/*键名sysFlag和input name值对应*/
				          message: 'The sysFlag is not valid',
				          validators: {
				            notEmpty: {/*非空提示*/
				              message: '请选择有效性！'
				            }
				         }
				     },
				sex: {/*键名sex和input name值对应*/
					          message: 'The sex is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '请选择性别！'
					            }
					         }
					     }
		      }
		    });
	//开启验证
    //$('#Formbody').data('bootstrapValidator').validate();  
	
})