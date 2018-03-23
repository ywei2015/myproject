package com.talkweb.twdpe.webcomponent.component;

import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;

import com.talkweb.twdpe.webcomponent.common.action.ComponentActionSupport;

/**
 * 文件名称: MessageConfig.java 内容摘要:
 * 
 * @author: 涂园园
 * @version: 1.0
 * @Date: 2011-6-24 下午07:23:53
 * 
 *        修改历史: 修改日期 修改人员 版本 修改内容 ----------------------------------------------
 *        2011-6-24 涂园园 1.0 1.0 XXXX
 * 
 *        版权: 版权所有(C)2011 公司: 拓维信息系统股份有限公司
 */
public class MessageCfgAction extends ComponentActionSupport {
    private static final Logger logger = LoggerFactory.getLogger(MessageCfgAction.class);

    /**
     * 
     * @Title: queryMsgConfig
     * @Description: 查询用户配置信息
     */
    public void queryMsgConfig() {
	// try {
	// // 发送方式
	// String sendType = request.getParameter("sendType");
	// JsonObject jso = new JsonObject();
	// jso.put("USERID", super.loginUserID);
	// String data = databusconvert.execute("MsgManage", "queryAllSettings",
	// jso);
	// JsonObject reObj = JsonObject.fromObject(data);
	// JsonArray jArr = reObj.getJsonArray("LIST");
	// JsonArray reArr = new JsonArray();
	// for (Object obj : jArr.toArray()) {
	// JsonObject jobj = (JsonObject) obj;
	// String infovalue = jobj.getString("infovalue");
	// String checkid = super.bitand(infovalue, sendType, ",");
	// String[] str = checkid.split(",");
	// for (int i = 0; i < str.length; i++) {
	// jobj.put("type_" + str[i], "1");
	// }
	// reArr.add(jobj);
	// }
	// JsonObject jj = new JsonObject();
	// jj.put("rows", reArr);
	// super.printToView(jj.toString());
	// } catch (Exception ex) {
//	    ServletActionContext.getRequest().setAttribute("throw", ex);
	// logger.error(ex.getMessage(), ex);
	// }
    }

    /**
     * 
     * @Title: getMsgCfgHeader
     * @Description: 从数据字典获得发送方式动态生成表头
     * @throws Exception
     */
    public void getMsgCfgHeader() {
	// try {
	// // 查询出所有的发送方式
	// StringBuilder sb = new StringBuilder();// 发送方式字符串,用','分割
	// String types = "";
	// JsonObject parms = new JsonObject();
	// parms.put("DICTTYPEID", "MSGCENTER_NOTIFYTYPE");
	// types = databusconvert.execute("Dict",
	// "queryDictEntryByType_Integer", parms);
	// JsonArray header = new JsonArray();// 表头信息
	// // 固定列
	// header.add(JsonObject.fromObject("{header :'信息内容',sortable: false,name : 'infocontent'}"));
	// header.add(JsonObject.fromObject("{header :'主键',sortable: false,hidden : true,name : 'infoid'}"));
	// header.add(JsonObject.fromObject("{header :'分组列',sortable: false,hidden : true,name : 'msgtypename'}"));
	// // 可变列,从数据字典获得
	// JsonObject option = new JsonObject();
	// option.put("disabled", false);
	// if (types != null) {
	// JsonArray ops = JsonArray.fromArray(types);
	// int f = 0;
	// for (Object obj : ops.toArray()) {
	// JsonObject a = (JsonObject) obj;
	// if (f > 0)
	// sb.append(",");
	// sb.append(a.get("value"));
	// f = 1;
	// JsonObject arrt = new JsonObject();
	// arrt.put("header", a.getString("text"));
	// arrt.put("sortable", false);
	// arrt.put("formatter", "checkbox");
	// arrt.put("formatoptions", option);
	// arrt.put("name", "type_" + a.getString("value"));
	// header.add(arrt);
	//
	// }
	// }
	// printToView("{header:" + header.toString() + ",sendType:\"" +
	// sb.toString() + "\"}");
	// } catch (Exception e) {
//	    ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(), e);
	// }
    }

    /**
     * 
     * @Title: saveMsgConfig
     * @Description: 新增修改用户配置信息
     */
    public void saveMsgConfig() {
	// try {
	// String str = request.getParameter("data");
	// JsonArray jarry = JsonArray.fromArray(str);
	// for (Object obj : jarry.toArray()) {
	// JsonObject jo = (JsonObject) obj;
	// jo.put("userid", loginUserID);
	// databusconvert.execute("MsgManage", "modifyUserSettings", jo);
	// }
	// super.printToView("{status:\"ok\",msg:\"消息提醒设置成功\"}");
	// } catch (Exception e) {
//	    ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(), e);
	// super.printToView("{status:\"bad\",msg:\"消息提醒设置失败\"}");
	// }

    }

    public void getMsgAddr() {
	// String loginId = super.loginUserID;
	// JsonObject jo = new JsonObject();
	// jo.put("email", "test@talkweb.com.cn");
	// jo.put("mobile", "13787112425");
	// jo.put("fetion", "19098722");
	// super.printToView(jo.toString());
    }

    public void modifyAddr() {
	// String data = request.getParameter("data");
	// JsonObject jo = JsonObject.fromObject(data);
	// String type = jo.getString("type");
	// String newData = jo.getString("newData");
	// String randNum = "";
	// String pwd = "";
	// if ("mobile".equals("type")) {
	// randNum = jo.getString("randNum");
	// }
	// if (!"fetion".equals("type")) {
	// pwd = jo.getString("pwd");
	// }
	// logger.debug("输入参数:" + jo.toString());
	// super.printToView("{status:\"ok\",msg:\"修改成功\"}");
    }
}
