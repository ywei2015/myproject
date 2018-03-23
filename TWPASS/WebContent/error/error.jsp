<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>title</title>
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<STYLE type=text/css>
INPUT {
	FONT-SIZE: 12px
}

TD {
	FONT-SIZE: 12px
}

.p2 {
	FONT-SIZE: 18px;
	line-height: 40px;
	color:#DA0A16;
	text-align:center;
	font-family:"黑体"
}

.p6 {
	FONT-SIZE: 12px;
	COLOR: #1b6ad8
}

A {
	COLOR: #333;
	TEXT-DECORATION: none
}

A:hover {
	COLOR: red
}
</STYLE>

	</head>
<body>
<div style="width:540px; margin:50px auto 0; padding:5px">
<TABLE cellSpacing=0 cellPadding=0 width=100% align=center border=0>
			<TBODY>
				<TR>
					<TD width="25%" vAlign=top >
		  <DIV align=center>
							<BR/>
							<IMG src="<%=path%>/themes/default/images/error.gif"/>						</DIV>
					</TD>
                  <td width="75%">
          <TABLE cellSpacing=0 cellPadding=0 width="100%" height="120px" border=0 style="border:1px solid #C3C3C3; padding:5px;border-radius: 5px;
  -moz-border-radius: 5px; -webkit-border-radius: 5px; margin-top:30px;">
<TBODY>
									<TR>
										<TD align="center">
											<FONT class=p2><IMG src="<%=path%>/themes/default/images/error_tip.gif" />
														很抱歉，您无法访问本页！
											</FONT>
										</TD>
									</TR>
									
								</TBODY>
							</TABLE>
							
							<table width="100%" border=0 align="center">
							<tr>
							<td style="line-height: 30px">无法访问本页的原因是：</td>
							</tr>
							<TR>
										<TD>
											<div style="line-height: 25px">
												<FONT color=#333>${exception.message}</FONT>!
											</div>
										</TD>
									</TR>
							</table>
                  </td>
			  </TR>
                </table>
                <TABLE width=100% border=0 align="center" cellPadding=0 cellSpacing=0
								style="margin-top: 20px; background:#F1F1F1">
  <TBODY>
									<TR>
										<TD height=25px align="center">
											<A href="#" onclick="javascript:try{window.close(0);}catch(e){};try{window.parent.close(0);}catch(e){};">关闭本页</A>&nbsp;|&nbsp;<A href="#" onclick="javascript:document.getElementById('exceptionStack').style.display='block';">详细信息</A>
										</TD>
									</TR>
								</TBODY>
							</TABLE>
<table cellSpacing=0 cellPadding=0 width=100% align=center border=0>
				<TR>
					<TD height=5></TD>
  <TR>
					<TD align=middle>
					    <div id="exceptionStack" style="display:none">
						<CENTER>
							<c:out value="${exceptionStack}"></c:out>
						</CENTER>
						</div>
					</TD>
  </TR>
</TABLE>
</div>
</body>
</html>

