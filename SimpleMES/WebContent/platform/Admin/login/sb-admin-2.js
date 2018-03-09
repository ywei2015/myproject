/*!
 * Start Bootstrap - SB Admin 2 v3.3.7+1 (http://startbootstrap.com/template-overviews/sb-admin-2)
 * Copyright 2013-2016 Start Bootstrap
 * Licensed under MIT (https://github.com/BlackrockDigital/startbootstrap/blob/gh-pages/LICENSE)
 */
$(function() {
    $('#side-menu').metisMenu();
});

//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
$(function() {
    $(window).bind("load resize", function() {
        var topOffset = 50;
        var width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse');
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse');
        }

        var height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
    });
    //初始化，提示框影藏
    $("#tip").hide();
    $("#a_cli_login").bind("click",function(){
    			var id="form_login";
    			var userCode=$("#form_login").find("input[name='userCode']").val();
    			var userPwd=$("#form_login").find("input[name='userPwd']").val();
    			if(isBlack(userCode) || isBlack(userPwd)){
    				$("#tip").show();
    				$("#tip").text("账号或者密码不能为空");
    				return false;
    			}
		    	$.ajax({
		        type: "POST",
		        dataType: "json",
		        url: "../../dynamic/Security/loginUser",
		        data: $('#'+id).serialize(),
		        success: function (result) {
		           	if(result.status==200){
		           		setCookie("operator",result.operator,1,"/");
		           		setCookie("opername",result.opername,1,"/");
		           		self.location="main.html";
		           	}else{
		           		$("#tip").show();
    					$("#tip").text("账号或者密码错误");
		           	}
		        	return false;
		        },
		        error: function(data) {
		            alert(data.message);
		            
		         }
		    });
  });
    //监听整个页面
    $("body").on("keyup",function(event){
    	if(event.keyCode==13){
          		 var userCode=$("#form_login").find("input[name='userCode']").val();
    			var userPwd=$("#form_login").find("input[name='userPwd']").val();
    			if(isBlack(userCode)){
    				$("#form_login").find("input[name='userCode']").focus();
    			}else if(isBlack(userPwd)){
    				$("#form_login").find("input[name='userPwd']").focus();
    			}else{
    				document.getElementById("a_cli_login").click();
    			}
        	}
    	return false;
    });

    var url = window.location;
    // var element = $('ul.nav a').filter(function() {
    //     return this.href == url;
    // }).addClass('active').parent().parent().addClass('in').parent();
    var element = $('ul.nav a').filter(function() {
        return this.href == url;
    }).addClass('active').parent();

    while (true) {
        if (element.is('li')) {
            element = element.parent().addClass('in').parent();
        } else {
            break;
        }
    }
});

function isBlack(str){
		if (str == null || str == undefined  || str == '') { 
					return true;
			}else{
				return false;
			}
	}

function setCookie(name,value,days,path){
    var name = (name);
    var value = (value);
    var expires = new Date();
     expires.setTime(expires.getTime() + 60*120000);
     path = path == "" ? "" : ";path=" + path;
     _expires = (typeof days) == "string" ? "" : ";expires=" + expires.toUTCString();
     document.cookie = name + "=" + value + _expires + path;
     //alert(document.cookie);
}