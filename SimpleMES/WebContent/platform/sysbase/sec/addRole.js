
var formbodyConfig = {
	name  : "Formbody",   
    type  : "FormBody", 
    title : "角色信息维护",
    berth : "detailForm",
    items : [
        { 
        	type:"textinput",
            title:"角色名称",
            dataIndex:'name'
            
        },
//        {
//			type:"combox",   
//			title:"角色类型",
//			dataIndex: 'state',
//			dataText: 'name',
//			fieldText:"name",
//			fieldValue:"code",
//			data:[{'code':0,'name':'普通角色'},{'code':1,'name':'管理员角色'}],
//			//url:"../../../DataDict?type=Workshop",
//			root: "dataset"
//		},
//        { 
////        	type:"choice",
////            title:"用户",
////            dataIndex:'usersId', 
////            dataText: 'usersName',
////            modalurl:'selectUser.html?roleId='+window.dialogArguments.data.f_pid,
////            modaltarget:'myModal'
//        	type:"textinput",
//            title:"用户",
//            dataIndex:'usersName'
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
    	//alert($('#f_name').val());
    	//alert(JSON.stringify(SF.getSearchParams()));
    	//alert("Form Submit OnClick!"+window.dialogArguments.data.kname);
    	//console.log(window.dialogArguments);
    	 $('#Formbody').data('bootstrapValidator').validate();  
    	    //是否通过校验
    	 if(!$('#Formbody').data('bootstrapValidator').isValid()){  
    	   // 没有通过校验 
    	} else {	
    	   //通过校验，可进行提交等操作
    		if(window.dialogArguments!=undefined){
    			SF.getFormValues().name;
    			$.ajax({  
 			       type: "post",  
 			       url: "../../../dynamic/RoleMgr/findRoleName",  
 			       cache:false,  
 			       async:false,  
 			       data:SF.getFormValues(),
 			       success:function(data){
 			    	   if(data){
	 			    	   $.ajaxSettings.async = false; 
	 			    	   $.post("../../../dynamic/RoleMgr/saveRole",SF.getFormValues() );
	 			    	   window.returnValue = "ok"; 
	 			    	   window.close();
 			    	   }else{
 			    		  BootstrapDialog.alert("该用户名已存在!");
 			    	   }
 			       }
 		    	});
    	   }
    	}	
    }
};

//$(function () {
//	
//});

var dlg_callback =function(e){
	if(e=='ok'){
//		$("#table").bootstrapTable('refresh',{url : path );
//		$("#table").bootstrapTable('refreshOptions',{pageNumber : 1});
		alert("子窗口已经提交 并关闭! 您可以刷新父窗口数据了。"+e);
	}
}

$(document).ready(function() {   
	//formbodyConfig["url"] = MesWebApiUrl + "/sec/user/getuserpage/"+f_pid;
	//formbodyConfig["dataroot"] = 'dataset';
	console.log(SF.FormBody.onload(formbodyConfig));
	var mode = window.dialogArguments.mode;
	console.log(mode);
	if(mode == 'add'){
		
	}else if(mode == 'update'){
		var f_pid = window.dialogArguments.data.f_pid;
		$.ajaxSettings.async = false; 
		$.post("../../../dynamic/RoleMgr/findRole?roleId="+f_pid,{},function(data){
			console.log(data);
			SF.BindFormData(data);
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
		        name: {/*键名username和input name值对应*/
		          message: 'The name is not valid',
		          validators: {
		            notEmpty: {/*非空提示*/
		              message: '用户名不能为空'
		            }
		          }
		        },
		       nusersId: {/*键名username和input name值对应*/
			          message: 'The name is not valid',
			          validators: {
			            notEmpty: {/*非空提示*/
			              message: '用户名不能为空'
			            }
			         }
			     },
		       sysFlag: {/*键名username和input name值对应*/
					          message: 'The name is not valid',
					          validators: {
					            notEmpty: {/*非空提示*/
					              message: '请选择有效性'
					            }
					         }
					     }
		      }
		    });
    //alert(window.dialogArguments.data.kname); 
    //console.info(window.dialogArguments.data); 
	
	
//	$("#usersName").click(function(){
//		var roleId=$("#usersName").val();
//		console.log(roleId);
//	    var win = SF.showModalDlg("selectUser.html?roleId=","update",null,1000,500,dlg_callback);
//	});
})