var condition = null;
var usercode=null;
function loadCode(){
	fastDev.get("user_initModifyPassWordForm.action",{
		success : function(data){
			fastDev.getInstance("userid").setValue(data);
			usercode=data;
			fastDev.getInstance("userid").disable();
		}
	});
}
function tijiao(){
	var newpass=fastDev.getInstance("newPassword").getValue();
	var newpassj=fastDev.getInstance("repeatPassword").getValue();
	if(newpass==newpassj){
	fastDev.getInstance("modifyPasswordform").submit();
	}else 
		fastDev.alert("前后两次密码不一致！","信息提示","error");

}
var submitflag=0;
function onSubmitSuccess(data) {
		if(data.msg=="修改成功"){
		submitflag=1;
		fastDev.tips(data.msg);
	}else
		fastDev.alert(data.msg, '信息提示', data.status);
}

function cleanDataj(){
	doReset();
}
function getSubmitFalg(){
	if(submitflag==1){
	return 1;
	}else {
		return 0;
	}
		
}
function doReset(){
	
	fastDev.getInstance('modifyPasswordform').cleanData();
	condition = null;
	fastDev.getInstance("userid").setValue(usercode);
}