package com.talkweb.twdpe.webcomponent.common;

import java.util.Map;

import com.talkweb.twdpe.core.config.ConfigManager;

/**
 * 文件名称:    StaticUploadInfo.java
 * 内容摘要:   公用的一个类：用于存贮关于上传的所有静态信息，包括各模块的上传路径等
 * @author:   李锋 
 * @version:  1.0  
 * @Date:     2011-6-27 下午08:18:50  
 * 
 * 修改历史:  
 * 修改日期       修改人员   版本	   修改内容  
 * ----------------------------------------------  
 * 2011-6-27    李锋     1.0    1.0 XXXX
 *
 * 版权:   版权所有(C)2011
 * 公司:   拓维信息系统股份有限公司
 */
public class StaticUploadInfo
{	
	/**
	 * 上传路径定义：系统管理模块上传路径定义
	 */
	private static Map<String,String> system_uploadinfo = ConfigManager
	    .getInstance().getAppConfig().get("upload.system");
	
	//系统管理：上传文件URL前缀
	public static String upload_system_url = system_uploadinfo.get("url");
	
	//系统管理：默认上传路径
	public static String upload_system_default = system_uploadinfo.get("path");
	
	//系统管理：附件上传路径
	public static String upload_system_attach = system_uploadinfo.get("attach");
	
	//系统管理：图片上传路径
	public static String upload_system_image = system_uploadinfo.get("image");
	
	//系统管理：文件导出路径
	public static String upload_system_export = system_uploadinfo.get("export");
	
	//系统管理：文件导入路径
	public static String upload_system_import = system_uploadinfo.get("import");
	
	//系统管理：临时文件上传路径
	public static String upload_system_temp = system_uploadinfo.get("temp");
}

