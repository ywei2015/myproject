var win=this.window;
var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", //id
    title : "关联关系维护",
    berth : "detailForm",
    items : [
        { 
        	type:"textinput",
            title:"编号",
            dataIndex:'code'
        },
        { 
        	type:"textinput",
            title:"名称",
            dataIndex:'name'
        },
        { 
        	type:"choice",
            title:"父对象",
            dataIndex:'parentId', 
            modalurl:'choice_tree.html',
            modaltarget:'myModal1'
        },
        { 
        	type:"choice",
            title:"子对象",
            dataIndex:'childId', 
            modalurl:'choice_tree.html',
            modaltarget:'myModal2'
        },
        {
			type:"combox",   
			title:"是否有效",
			dataIndex: 'sysFlag',
			fieldText:"name",
			fieldValue:"code",
			data:[{'code':1,'name':'有效'},{'code':0,'name':'无效'}],
			//url:"../../../DataDict?type=Workshop",
			root: "dataset.item"
		},
		{ 
        	type:"textinput",
            title:"备注",
            dataIndex:'remark'
        },
    ],
    hidefields:["id","parentName","childName"],
    layoutConfig : {
        columns : 2
    },
    onsubmit: function(){ 
    	var objs =$("#Formbody"); //SF.getSearchParams();
    	var mode = window.dialogArguments.mode;
    	//开启验证,首先需要判断是否存在这个对象
    	console.log($('#Formbody').data('bootstrapValidator'));
    	if($.fn.isBlank($('#Formbody').data('bootstrapValidator'))){
    		validField();
    	}
	    $('#Formbody').data('bootstrapValidator').validate();  
		if(!$('#Formbody').data('bootstrapValidator').isValid()){  
    		return ;
    	} 
	 	//保存之前做校验
   	 var flag=false;
   	 $.ajax({
		        type: "POST",
		        dataType: "json",
		        url: "../../../dynamic/ObjEntityRef/validCodeAndName",
		        async: false,//同步  
		        data:objs.serialize(),
		        success: function (result) {
		           	if(result.status!=200){
		           		BootstrapDialog.show({
	        	            title: constant.TIP,
	        	            message:result.message,
	        	            buttons: [{
	        	                label: constant.CONFIRM,
	        	                action: function(dialog) {
	        	                	dialog.close();
	        	                	return false;
	        	                }
	        	            }]
	        	        });
		           		flag=true;
		           	}
		           
		        },
		    });
			if(flag){
				return;
			}
    	if(mode == 'add'){
    		var result=forceAjaxSynchro("../../../dynamic/ObjEntityRef/save",objs,function(){
        	});
    		if(result.responseJSON.status==200){
    	        win.returnValue = "ok"; 
    		}
    		BootstrapDialog.show({
	            title: constant.TIP,
	            message: result.responseJSON.message,
	            buttons: [{
	                label:constant.CONFIRM,
	                action: function(dialog) {
	                	dialog.close();
	                	win.close();
	                	return false;
	                }
	            }]
	        });
    	}else if(mode == 'update'){
    		var result=forceAjaxSynchro("../../../dynamic/ObjEntityRef/save",objs,function(){
        	});
    		if(result.responseJSON.status==200){
    		    win.returnValue = "ok"; 
    		}
    		BootstrapDialog.show({
	            title: constant.TIP,
	            message: result.responseJSON.message,
	            buttons: [{
	                label: constant.CONFIRM,
	                action: function(dialog) {
	                	dialog.close();
	                	win.close();
	                	return false;
	                }
	            }]
	        });
    		
    	}
    	
    	
    }
};


$(document).ready(function() {   

	SF.FormBody.onload(formbodyConfig);  
	var data = window.dialogArguments.data;
	var mode = window.dialogArguments.mode;
	if(mode == 'add'){
		
	}else if(mode == 'update'){
		//弹出选中无法实现绑定,自动设置绑定
		$("#nparentId").val(data.parentName);
		$("#nchildId").val(data.childName);
		SF.BindFormData(data);
	
	}	
	validField();
});
//校验字段
function validField(){

	/*************表单验证  stasrt**************/
	$('#Formbody').bootstrapValidator({
		      message: 'This value is not valid',
		      feedbackIcons: {/*输入框不同状态，显示图片的样式*/
		        valid: 'glyphicon glyphicon-ok',
		        invalid: 'glyphicon glyphicon-remove',
		        validating: 'glyphicon glyphicon-refresh'
		      },
		      fields: {/*验证*/
		        code: {/*键名username和input name值对应*/
		          message: 'The name is not valid',
				  verbose: false,//代表验证按顺序验证。验证成功才会下一个（验证成功才会发最后一个remote远程验证）
		          validators: {
		            notEmpty: {/*非空提示*/
		              message: '不能为空'
		            }
		          }
		        },
				name: {/*键名username和input name值对应*/
					          message: 'The name is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '不能为空'
					            }
					          }
					        },
				
				sysFlag:{/*键名username和input name值对应*/
					          message: 'The name is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '不能为空'
					            }
					          }
					        },
					nparentId:{/*键名username和input name值对应*/
						          message: 'The name is not valid',
						          validators: {
						            notEmpty: {/*非空提示*/
						              message: '不能为空'
						            }
						          }
						        },
						nchildId:{/*键名username和input name值对应*/
						          message: 'The name is not valid',
						          validators: {
						            notEmpty: {/*非空提示*/
						              message: '不能为空'
						            }
						          }
						        }
					},
			});
	/*************end*******************/
}