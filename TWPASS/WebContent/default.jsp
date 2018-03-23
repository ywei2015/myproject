<%@page import="org.apache.poi.util.SystemOutLogger"%>
<%@ page language="java" import="com.talkweb.xwzcxt.util.SSOUtils"
	pageEncoding="UTF-8"%>

<%
	    String zyUid=null;
 		zyUid=SSOUtils.getUIDFromWebSeal(request);
 		//zyUid="dengln0618";
	   // System.out.println(zyUid+"*****************");
		if(zyUid!=null){	
			session.setAttribute("zyid", zyUid);
	    	response.sendRedirect("zyLogin.action");
		}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	window.location.href = "component/login.html";
</script>
</head>
<body>

</body>
</html>