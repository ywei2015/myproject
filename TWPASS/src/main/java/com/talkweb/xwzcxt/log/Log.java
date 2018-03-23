package com.talkweb.xwzcxt.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * @actionName: action的名字，要包含命名空间，必填---------
 * @appName:应用程序名称,默认是 "长沙卷烟厂过程行为支持系统"
 * @appId:应用程序id,默认是"1",对应 "长沙卷烟厂过程行为支持系统"
 * @type:操作类型，add(新增),login(登录),logout(注销),visit(访问),修改(modify),删除(delete),默认为visit
 * @funcGroupId:模块编码,必填---------
 * @funcGroupName:模块名称,默认是空字符串
 * @accountType:操作人类型，默认是“1”
 * @actionDescription:描述action的描述信息,默认是空字符串
 * @resourceName:资源名称,默认是空字符串
 * @breakMessage:操作被中断的信息,默认为“操作被中断”
 * @failedMessage:操作失败的信息,默认为“操作失败”
 * @successMessage:操作成功的信息,默认为“操作成功”
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {
	public String actionName();
	public String appName() default "长沙卷烟厂过程行为支持系统";
	public String appId()  default "1";
	public String type()   default "visit";
	public String funcGroupId();
	public String funcGroupName() default "";
	public String accountType() default "1";
	public String actionDescription() default "";
	public String resourceName() default "";
	public String breakMessage() default "操作被中断";
	public String failedMessage() default "操作失败";
	public String successMessage() default "操作成功";
	
}
