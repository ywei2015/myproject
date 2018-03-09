
var form = $('#updatePwd');

$(function () {
	$.ajaxSettings.async = false; 
	$.post("../../../dynamic/User/findUser",{},function(data){
		$("#name").val(data.name);
		$("#code").val(data.code);
		$("#jobno").val(data.jobno);
		$("#orgName").val(data.orgName);
		$("#postsName").val(data.postsName);
	});
	
	$("#delPwd").click(function(){
		var oldPwd=$("#oldPwd").val();
		var newPwd=$("#newPwd").val();
		var newPwd1=$("#newPwd1").val();
		if(oldPwd!="" && newPwd!="" && newPwd1!=""){
			if(newPwd==newPwd1){
				$.post("../../../dynamic/User/updatPwd",
				{oldPwd:oldPwd,newPwd:newPwd},function(data){
					console.log(data);
					if(data==true){
						BootstrapDialog.show({
							  title : "提示",
				 			  message : "保存成功!请重新登录！",
				 			  buttons : [
				 			   {
				 			      label : "确定",
				 			      cssClass : "btn-primary",
				 			      icon : "",//通过bootstrap的样式添加图标按钮
				 			     action : function(dialog){   //给当前按钮添加点击事件
				 			    	window.parent.location.href = "/platform/Admin/login.html";
					 			      }
				 			    }
				 			  ]
				 			});

					}else{
						BootstrapDialog.alert("原密码错误！");
					}
					
				});
				}else{
					BootstrapDialog.alert("两次密码必须一致！");
			}
		}else{
			BootstrapDialog.alert("密码不能为空！");
		}
	});
	 
});
var dlg_callback =function(e){
	console.log(e);
	if(e=='ok'){
		refreshTable();
		BootstrapDialog.alert("操作成功！");
	}
}
