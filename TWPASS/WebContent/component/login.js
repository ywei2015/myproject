
$(function() {
	if(window != top) {
		top.location.href = 'login.html';
	}
});

//获取浏览器URL中参数，此处用于判断是否需要验证码。
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
    }

function changeValidateImg(){
	$("#imgcode").attr("src","validatecode.jsp?"+(new Date()).getTime());
}

function login() {
    var zhi=document.getElementById('a');
    zhi.innerHTML='';
    var userid=document.getElementById('USERID');
    if(userid.value.length=='0'){
          zhi.innerHTML='账号不能为空';
          
    }else{
		     zhi.innerHTML='';
			
  }
    var zhi=document.getElementById('p');
    var pwd=document.getElementById('PASSWORD');
    if(pwd.value.length=="0"){
      zhi.innerHTML='密码不能为空';
     
    }else{
      zhi.innerHTML='';
    
     }
    var form = fastDev.getInstance('loginForm');
    form.setOptions({
		action : 'portal_login.action?USERID=' + userid.value + '&PASSWORD=' + pwd.value
	});
    form.submit();
    
/*	var isAutoTest = getQueryString("isAutoTest");
	if(isAutoTest){ 
		fastDev.getInstance('loginForm').setOptions({'action':'portal_login.action'});
	fastDev.getInstance('loginForm').submit();
	}*/
}

function reset() {
	fastDev.getInstance('loginForm').resetData();
}

function onSubmitSuccess(result) {

	$("#pwd_error").hide();
	$("#validate_error").hide();
	$("#code_error").hide();
	if(result.msg=="0"){ //用户验证成功

		location.href = "index.jsp";
 
	} else if(result.msg=="1"){//存在多个帐号
		location.href = "userSelect.html";
	}
	else if(result.msg=="-1"){ //用户名不存在
		$("#pwd_error").show();
	}
	else if(result.msg=="-2"){ //密码错误
		$("#pwd_error").show();
	}
	else if(result.msg=="-5"){ //异常
		$("#code_error").show();
	}
	else if(result.msg=="-4"){ //验证码错误
		$("#validate_error").show();			
	}else if(result.msg=="-3"){
		$("#accountforbidden_error").show();
	}
}

function loginByKey(event) {
	var keycode = event.keyCode;
	if(keycode == 13) {
		login();
		return false;
	}
	return true;
}

