<%@ page language="java"
	import="java.util.*,com.talkweb.twdpe.web.util.ValidateCode"
	pageEncoding="UTF-8"%>
<%
	ValidateCode validate = new ValidateCode();
	validate.generateValidate_num(request, response);
	out.clear();
	out = pageContext.pushBody(); 	
%>
