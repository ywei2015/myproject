<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="<%=request.getContextPath()%>/dynamic/user/getUser?f_user_code=654321&f_password=123456&f_client_type=pda&f_device_ip=127.0.0.1&f_device_uuid=343243&f_device_model=apk&f_device_os=android">
		<div>
			<span>用户：</span><input name="userCode" type="text" id="userCode"  tabindex="1"  value="" />
			<span>密码：</span><input type="password" id="userPwd" value="talkwebvip" maxlength="12" tabindex="2"  name = "userPwd"/>
			<input name="dj" type="submit" id="imgLogin" onClick="clickLogin()" value="登 陆" />
		</div>
	</form>
</body>
</html>