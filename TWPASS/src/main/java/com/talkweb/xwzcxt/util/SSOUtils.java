/*
 * 北京联信永益科技股份有限公司　　版权所有
 * $version: 1.0 　2008-6-24  
*  $id$
 */
package com.talkweb.xwzcxt.util;

import javax.servlet.http.HttpServletRequest;

public class SSOUtils {
	/**
	 * TAM SSO ,从 http 头中取得用户名
	 * @param request
	 * @return tam 认证的用户名 ， 如果未通过 webseal 或未认证，返回null
	 */
	public static final   String getUIDFromWebSeal(HttpServletRequest request){
		String userId = null;
		if (request.getHeader("iv_server_name") != null){
			userId = request.getHeader("iv-user");
			if (userId != null && userId.equals("Unauthenticated")) {
				userId = null;
			}
		}
		return userId;
	}
	
	/**
	 * TAM SSO ,从 http 中读取IP地址
	 * @param request
	 * @return
	 */
	public static final  String getRemoteIpFromWebSeal(HttpServletRequest request){
		String remoteAdd = request.getHeader("iv-remote-address");
		if (remoteAdd == null )
			remoteAdd = request.getRemoteAddr();
		return remoteAdd;
	}
}
