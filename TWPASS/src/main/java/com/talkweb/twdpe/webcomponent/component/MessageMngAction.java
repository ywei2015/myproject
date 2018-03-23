package com.talkweb.twdpe.webcomponent.component;

import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;

import com.talkweb.twdpe.base.contentfilter.KeywordFilterImpl;
import com.talkweb.twdpe.base.contentfilter.KeywordIterator;
import com.talkweb.twdpe.core.util.DateUtil;
import com.talkweb.twdpe.core.util.json.JsonObject;
import com.talkweb.twdpe.webcomponent.common.action.ComponentActionSupport;

/**
 * 文件名称: MessageMngAction.java 内容摘要:
 * 
 * @author: 丁涛、文追、涂园园、李腾杰
 * @version: 1.0
 * @Date: 2011-5-30 上午10:26:02
 * 
 * 修改历史: 修改日期 修改人员 版本 修改内容 ----------------------------------------------
 * 2011-5-30 丁涛 1.0 1.0 XXXX
 * 
 * 版权: 版权所有(C)2011 公司: 拓维信息系统股份有限公司
 */
@SuppressWarnings("serial")
public class MessageMngAction extends ComponentActionSupport {
    private static final Logger logger = LoggerFactory.getLogger(MessageMngAction.class);
    private final long contLen = 4000;

    // ********************************消息模板管理部分*************************************************
    /**
     * @title 查询消息模板列表
     * @return
     */
    public void getMsgTemplates() {
	// int page = 1; // 列表分页显示 - 设置时当前的页码
	// int rows = 10; // 列表分页显示 - 设置每页显示的记录数
	// try {
	// rows = Integer.valueOf(request.getParameter("rows"));
	// page = Integer.valueOf(request.getParameter("page"));
	// } catch (NumberFormatException e1) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// page = 1; // 列表分页显示 - 设置时当前的页码
	// rows = 10; // 列表分页显示 - 设置每页显示的记录数
	// }
	// String sidx = request.getParameter("sidx");
	// String sord = request.getParameter("sord");
	// Pagination pagination = new Pagination(page, rows); //
	// 通过调用业务方法，获取分页数据
	// IData param = new DataMap(); // 查询参数IDATA
	// String data = request.getParameter("data"); // 获取查询的条件参数
	// String cacheID = request.getParameter("cacheID");
	// try {
	// if (data != null) {
	// data = java.net.URLDecoder.decode(data, "UTF-8"); // 对其解码
	// JsonObject jso = JsonObject.fromObject(data); // 参数格式化成json格式
	// param.put("TEMPLATETYPE", jso.get("templatetype")); // 查询条件：模板类型
	// param.put("NOTIFYTYPE", jso.get("notifytype")); // 查询条件：发送类型
	// param.put("NOTIFYTO", jso.get("notifyto")); // 查询条件：发送对象
	// param.put("TEMPLATENAME", jso.get("templatename")); // 查询条件：模板名称
	// }
	// if (!"".equals(sidx) && sidx != null) {
	// param.put("ORDER", sidx + " " + sord); // 查询条件：排序
	// } else {
	// param.put("ORDER", " CREATETIME DESC");
	// }
	//
	// param.put("PAGE", pagination);
	// if (cacheID != null) {
	// this.formatResultData("MsgManage", "getMsgTemplates",
	// JsonObject.fromObject(param));
	// } else {
	// bindData("MsgManage", "getMsgTemplates", JsonObject
	// .fromObject(param), page, rows);
	// }
	//
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// e.printStackTrace();
	// logger.error(e.getMessage(), e);
	// }
    }

    /**
     * 
     * @Title: initMsgData
     * @Description: 初始化查询消息模板时的字典表等数据
     * @return
     */
    public void initMsgSerData() throws Exception {
	// StringBuilder result = new StringBuilder(); // 业务方法无参数的示例
	// String templatetype =
	// queryDictEntryByType_Integer("MSGCENTER_TEMPLATETYPE"); //
	// 从字典表中取得模板类型原始数据
	// String msgtype = queryDictEntryByType_Integer("MSGCENTER_MSGTYPE");
	// // 从字典表中取得消息类型原始数据
	// String notifytype =
	// queryDictEntryByType_Integer("MSGCENTER_NOTIFYTYPE"); //
	// 从字典表中取得通知方式原始数据
	// String notifyto = queryDictEntryByType_Integer("MSGCENTER_NOTIFYTO");
	// // 从字典表中取得通知对象原始数据
	// String status =
	// queryDictEntryByType_Integer("MSGCENTER_VERIFYSTATUS");//
	// 从字典表中取得审核状态原始数据
	// String parmes_st = queryDictEntryByType_Integer("MSGCENTER_VAR");
	// IData param = new DataMap();
	// param.put("DICTTYPEID", "MSGCENTER_NOTIFYTYPE");
	// JsonObject inputParamjson = JsonObject.fromObject(param);
	// // 取得应用类型列表中原始数据
	// String types = "";
	// types = databusconvert.execute("Dict",
	// "queryDictEntryByType_Integer", inputParamjson);
	//
	// String cacheID = request.getParameter("cacheID");
	// if (cacheID != null) {
	//
	// result.append("{");
	// result.append("templatetype:{data:");
	// result.append(templatetype);
	// result.append("},");
	// result.append("sendType:{data:");
	// result.append(types);
	// result.append("},");
	// result.append("templateid:{data:");
	// result.append(templatetype);
	// result.append("},");
	// result.append("msgtype:{data:");
	// result.append(msgtype);
	// result.append("},");
	// result.append("notifytype:{data:");
	// result.append(notifytype);
	// result.append("},");
	// result.append("notifyto:{data:");
	// result.append(notifyto);
	// result.append("},");
	// result.append("popparmes:{data:");
	// result.append(parmes_st);
	// result.append("},");
	// result.append("status:{data:");
	// result.append(status);
	// result.append("}}");
	// this.printFormatData(cacheID, result.toString());
	// } else {
	// result.append("{");
	// result.append("templatetype:");
	// result.append(templatetype);
	// result.append(",");
	// result.append("templateid:");
	// result.append(templatetype);
	// result.append(",");
	// result.append("msgtype:");
	// result.append(msgtype);
	// result.append(",");
	// result.append("notifytype:");
	// result.append(notifytype);
	// result.append(",");
	// result.append("notifyto:");
	// result.append(notifyto);
	// result.append(",");
	// result.append("popparmes:");
	// result.append(parmes_st);
	// result.append(",");
	// result.append("status:");
	// result.append(status);
	// result.append("}");
	// printToView(result.toString());
	// }

    }

    /**
     * @title 查看消息模板详情
     * @return
     */
    public void getMsgTemplateInfo() {
	// String id = request.getParameter("TEMPLATEID"); // 获取消息模板的ID
	// IData data = new DataMap();
	// data.put("TEMPLATEID", id);
	// // String datas = JSONUtil.iDataToJson(data);
	// String result = "";
	// String cacheID = request.getParameter("cacheID");
	// try {
	// result = databusconvert.execute("MsgManage", "getMsgTemplateInfo",
	// JsonObject.fromObject(data));
	// JsonObject jo = JsonObject.fromObject(result);
	// jo.put("notifytypename", getNotifytypename(jo
	// .getString("notifytype"), null));
	// jo.put("sendTypesHid", super.bitand(jo.getString("notifytype"),
	// getNotiyTypeStr(), ","));// 发送类型(短信,彩信,邮件)
	//
	// if(cacheID != null){
	// String[] sendType = jo.getString("sendTypesHid").split(",");
	// String ss = "";
	// List<String> typelist = new ArrayList<String>();
	// for(int i = 0; i < sendType.length; i++ ){
	// ss = sendType[i];
	// typelist.add(ss);
	// }
	//
	//
	// jo.put("sendType", typelist);
	// this.printFormatData(cacheID, jo.toString());
	// } else {
	// printToView(jo.toString());
	// }
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(), e);
	// }

    }

    /**
     * @title 新增消息模板
     * @return
     */
    public void addMsgTemplate() {
	// String data = request.getParameter("data");
	// JsonObject jo = JsonObject.fromObject(data);
	// jo.remove("sendType");
	// jo.put("creator", loginUserID);
	// jo.put("parentid", "0");
	// // jo.put("content", htmlConvert(request.getParameter("contentText"),
	// // false));
	// // // 后台长度验证??
	// // if (isOutLen(jo.getString("content"), contLen, "模版内容不能超过" +
	// contLen / 2
	// // + "个字符!")) {
	// // return;
	// // }
	// String result = "";
	// try {
	// result = databusconvert.execute("MsgManage", "addMsgTemplate", jo);
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("", e.getMessage());
	// logger.error(e.getMessage(), e);
	// }
	// String msgresult = setMessage(result, "MSGMANGE", "ADD"); //
	// 将新增是否成功的结果进行消息状态组合便于返回给页面展示
	// //addSystemLog(LogConfigParam.SYS_MGR_ID,
	// LogConfigParam.SYS_MGR_NAME,
	// LogConfigParam.OPERATOR_TYPE_ADD, jsonToString(jo.toString()),
	// this.getJSONPropertyValue(result, "msg"));
	// printToView(msgresult);

    }

    /**
     * @title 修改消息模板
     * @return
     */
    public void modifyMsgTemplate() {
	// String data = request.getParameter("data");
	// JsonObject jo = JsonObject.fromObject(data);
	// jo.remove("sendType");
	// jo.put("updater", loginUserID);
	// // jo.put("content", htmlConvert(request.getParameter("content"),
	// false));
	// // // 后台长度验证??
	// // if (isOutLen(jo.getString("content"), contLen, "模版内容不能超过" +
	// contLen / 2
	// // + "个字符!")) {
	// // return;
	// // }
	// String result = "";
	// try {
	// result = databusconvert.execute("MsgManage", "modifyMsgTemplate",
	// jo);
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("", e.getMessage());
	// logger.error(e.getMessage(), e);
	// }
	// String msgresult = setMessage(result, "MSGMANGE", "MODIFY"); //
	// 将新增是否成功的结果进行消息状态组合便于返回给页面展示
	// //addSystemLog(LogConfigParam.SYS_MGR_ID,
	// LogConfigParam.SYS_MGR_NAME,
	// LogConfigParam.OPERATOR_TYPE_MODIFY,
	// jsonToString(jo.toString()), this.getJSONPropertyValue(result,
	// "msg"));
	// printToView(msgresult);
    }

    /**
     * @title 删除消息模板
     * @return String
     */
    public void deleteMsgTemplates() {
	// String ids = request.getParameter("id");
	// String[] msgids = StringUtils.split(ids, ",");
	// List<String> msglist = new ArrayList<String>();
	// for (int i = 0; i < msgids.length; i++) {
	// msglist.add(msgids[i]);
	// }
	// // String jsondata = JSONUtil.listToJson(msglist, msglist);
	// String result = "";
	// try {
	// result = databusconvert.execute("MsgManage", "removeMsgTemplates",
	// JsonArray.fromArray(msglist));
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("", e.getMessage());
	// logger.error(e.getMessage(), e);
	// }
	// String msgresult = setMessage(result, "MSGMANGE", "DELETE"); //
	// 将新增是否成功的结果进行消息状态组合便于返回给页面展示
	// //addSystemLog(LogConfigParam.SYS_MGR_ID,
	// LogConfigParam.SYS_MGR_NAME,
	// LogConfigParam.OPERATOR_TYPE_DELETE, jsonToString(JsonArray
	// .toString(msglist)), this.getJSONPropertyValue(result,
	// "msg"));
	// printToView(msgresult);

    }

    // ********************************接受箱部分*************************************************
    /**
     * @title 查看接受消息信息
     * @return
     */
    public void getMsgReceiveTemplates() {
	// int page = 1; // 列表分页显示 - 设置时当前的页码
	// int rows = 10; // 列表分页显示 - 设置每页显示的记录数
	// try {
	// rows = Integer.valueOf(request.getParameter("rows"));
	// page = Integer.valueOf(request.getParameter("page"));
	// } catch (NumberFormatException e1) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// page = 1; // 列表分页显示 - 设置时当前的页码
	// rows = 10; // 列表分页显示 - 设置每页显示的记录数
	// }
	// Pagination pagination = new Pagination(page, rows); //
	// 通过调用业务方法，获取分页数据
	// IData param = new DataMap(); // 查询参数IDATA
	// String data = request.getParameter("data"); // 获取查询的条件参数
	// try {
	// if (data != null) {
	// data = java.net.URLDecoder.decode(data, "UTF-8"); // 对其解码
	// JsonObject jso = JsonObject.fromObject(data); // 参数格式化成json格式
	// param.put("SENDTYPEMSG", jso.get("sendtypemsg")); // 查询条件：发送方式
	// param.put("RECEIVESTARTDATE", jso.get("receivestart_date")); //
	// 查询条件：起始日期
	// param.put("RECEIVEENDDATE", jso.get("receiveend_date")); // 查询条件：结束日期
	// param.put("MSGNAME", jso.get("msgname")); // 查询条件：消息主题
	// }
	// param.put("PAGE", pagination);
	// bindDataGridSource("MsgManage", "getMsgReceiveTemplates",
	// JsonObject.fromObject(param), page, rows);
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(), e);
	// }
    }

    /**
     * 
     * @Title: initSendMsgData
     * @Description: 初始化接受查询消息模板时的字典表等数据
     * @return
     */
    public void initReceiveMsgTemplates() throws Exception {
	// StringBuilder result = new StringBuilder(); // 业务方法无参数的示例
	// String sendtypemsg = queryDictEntryByType_Integer("EMPSTATUS"); //
	// 从字典表中取得发送消息原始数据
	// result.append("{");
	// result.append("sendtypemsg:");
	// result.append(sendtypemsg);
	// result.append("}");
	// printToView(result.toString());

    }

    /**
     * @title 查看接受消息模板详情
     */
    public void getMsgReceiveInfo() {
	// String id = request.getParameter("RECEIVEMSGID"); // 获取消息模板的ID
	// IData data = new DataMap();
	// data.put("RECEIVEMSGID", id);
	// // String datas = JSONUtil.iDataToJson(data);
	// String result = "";
	// try {
	// result = databusconvert.execute("MsgManage", "getMsgReceiveInfo",
	// JsonObject.fromObject(data));
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(), e);
	// }
	// printToView(result.toString());
    }

    // ********************************发送箱部分*************************************************
    /**
     * @title 查看发送消息信息
     * @return
     */
    public void getMsgSendTemplates() {
	// int page = 1; // 列表分页显示 - 设置时当前的页码
	// int rows = 10; // 列表分页显示 - 设置每页显示的记录数
	// try {
	// rows = Integer.valueOf(request.getParameter("rows"));
	// page = Integer.valueOf(request.getParameter("page"));
	// } catch (NumberFormatException e1) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// page = 1; // 列表分页显示 - 设置时当前的页码
	// rows = 10; // 列表分页显示 - 设置每页显示的记录数
	// }
	// Pagination pagination = new Pagination(page, rows); //
	// 通过调用业务方法，获取分页数据
	// IData param = new DataMap(); // 查询参数IDATA
	// String data = request.getParameter("data"); // 获取查询的条件参数
	// try {
	// if (data != null) {
	// data = java.net.URLDecoder.decode(data, "UTF-8"); // 对其解码
	// JsonObject jso = JsonObject.fromObject(data); // 参数格式化成json格式
	// param.put("SENDTYPEMSG", jso.get("sendtypemsg")); // 查询条件：发送方式
	// param.put("MSGSTARTDATE", jso.get("msgstart_date")); // 查询条件：起始日期
	// param.put("MSGENDDATE", jso.get("msgend_date")); // 查询条件：结束日期
	// param.put("MSGNAME", jso.get("msgname")); // 查询条件：消息主题
	// }
	// param.put("PAGE", pagination);
	// bindDataGridSource("MsgManage", "getMsgSendTemplates", JsonObject
	// .fromObject(param), page, rows);
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(), e);
	// }
    }

    /**
     * 
     * @Title: initSendMsgData
     * @Description: 初始化发送查询消息模板时的字典表等数据
     * @return
     */
    public void initSendMsgTemplates() throws Exception {
	// StringBuilder result = new StringBuilder(); // 业务方法无参数的示例
	// String sendtypemsg = queryDictEntryByType_Integer("EMPSTATUS"); //
	// 从字典表中取得发送消息原始数据
	// result.append("{");
	// result.append("sendtypemsg:");
	// result.append(sendtypemsg);
	// result.append("}");
	// printToView(result.toString());
    }

    /**
     * @title 查看发送消息模板详情
     */
    public void getMsgSendInfo() {
	// String id = request.getParameter("SENDMSGID"); // 获取消息模板的ID
	// IData data = new DataMap();
	// data.put("SENDMSGID", id);
	// // String datas = JsonObject.fromObject(data);
	// String result = "";
	// String cacheID = request.getParameter("cacheID");
	// try {
	// result = databusconvert.execute("MsgManage", "getMsgSendInfo",
	// JsonObject.fromObject(data));
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(), e);
	// }
	// if(cacheID != null){
	// this.printFormatData(cacheID, result);
	//
	// } else {
	// printToView(result.toString());
	// }

    }

    // ********************************群发任务审核部分*************************************************

    /**
     * 
     * @Title: getGroupeavs
     * @Description: (获取群组消息列表)
     * @return
     */
    public String getGroupVerifys() {

	// int page = Integer.parseInt(request.getParameter("page") == null ?
	// "1"
	// : request.getParameter("page"));
	// int rows = Integer.parseInt(request.getParameter("rows") == null ?
	// "10"
	// : request.getParameter("rows"));
	// IData data = new DataMap();
	// // data.put("STATUS", "0");// 查询条件：审核状态默认查未审核的0
	// String param = request.getParameter("data");
	// try {
	// if (param != null) {
	// JsonObject obj = JsonObject.fromObject(param);
	// data.put("STATUS", obj.get("status"));// 查询条件：审核状态
	// data.put("MSGTYPE", obj.get("msgtype"));// 查询条件：消息类型
	// data.put("NOTIFYTYPE", obj.get("notifytype")); // 查询条件：发送方式
	// data.put("CREATETIME1", obj.get("msg_create_time1"));// 查询条件：起始时间
	// data.put("CREATETIME2", obj.get("msg_create_time2")); // 查询条件：结束时间
	// data.put("TITLE", obj.get("msgTitle")); // 查询条件：消息主题
	// data.put("SENDER", obj.get("searchID") == null ? null
	// : loginUserID);// 如果为空是审核,否则查看,只能看自己发送的
	//
	// }
	// Pagination pagenation = new Pagination(page, rows);
	// data.put("PAGE", pagenation);
	// // 排序
	// String orderStr = "CREATETIME DESC";
	// String sidx = request.getParameter("sidx");
	// String sord = request.getParameter("sord");
	// if (sidx != null && sidx.length() > 0) {
	// orderStr = sidx.toUpperCase() + " "
	// + (sord == null ? "ASC" : sord.toUpperCase());
	// }
	// data.put(CommonConfig.orderby, orderStr);
	// this.formatbindData("MsgManage", "queryVerifyByPage", JsonObject
	// .fromObject(data));
	// // bindData("MsgManage", "queryVerifyByPage", JsonObject
	// // .fromObject(data), page, rows);
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(), e);
	// }
	return null;
    }

    /**
     * 
     * @Title: getGroupMsgInfo
     * @Description: (获取消息详情)
     * @return
     */
    public String getGroupMsgInfo() {
	//
	// String result = null;
	// String datas = request.getParameter("data");
	// JsonObject datasObj = JsonObject.fromObject(datas);
	// String verifyid = datasObj.getString("verifyid");
	// // String notifytypes = datasObj.getString("notifytypes");
	// IData param = new DataMap();
	// param.put("VERIFYID", verifyid);
	// String cacheID = request.getParameter("cacheID");
	// try {
	// result = databusconvert.execute("MsgManage", "getVerify",
	// JsonObject.fromObject(param));
	// JsonObject jo = JsonObject.fromObject(result);
	// jo.put("viewObj", getReceiverName(jo.getString("receiver"), ","));//
	// 接收人
	// jo.put("sendTypesHid", super.bitand(jo.getString("notifytype"),
	// getNotiyTypeStr(), ","));// 发送类型(短信,彩信,邮件)
	// jo.put("templateidHid", jo.get("templateid"));
	// jo.put("wrongStr", getKeyWords(htmlConvert(jo.getString("content"),
	// false)));// 敏感词
	// jo.put("receivername", getReceiverName(jo.getString("receiver"),
	// ","));
	// jo.put("notifytypename", getNotifytypename(jo
	// .getString("notifytype"), null));
	// jo = this.parseSendTime(jo);
	// if(cacheID != null){
	// this.printFormatData(cacheID, jo.toString());
	// } else {
	// printToView(jo.toString());
	// }
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage());
	// e.printStackTrace();
	// }

	return null;
    }

    /**
     * 
     * @Title: modifyGroupmsgStatus
     * @Description: (群组消息审核)
     * @return
     */
    public String doVerify() {

	// String data = request.getParameter("data");
	// JsonObject dataObj = JsonObject.fromObject(data);
	// String verifyid = dataObj.getString("verifyid");
	// String status = dataObj.getString("status");
	// String verifyAdvice = dataObj.getString("verify_advice");
	// String result = null;
	// String methodName = "";
	// if (status != null && "1".equals(status)) {
	// methodName = "passVerify";
	// } else if (status != null && "2".equals(status)) {
	// methodName = "noPassVerify";
	// } else {
	// result = "{RESULT:\"1\"}";
	// logger.info(result);
	// String msgresult = setMessage(result, "MSGMANGE", "VERIFY"); //
	// 将新增是否成功的结果进行消息状态组合便于返回给页面展示
	// printToView(msgresult);
	// return null;
	// }
	//
	// if (verifyid != null) {
	// IData param = new DataMap();
	// param.put("VERIFYID", verifyid);
	// param.put("SATATUS", status);
	// param.put("VERIFYOPINION", verifyAdvice);
	// param.put("VERIFIER", loginUserID);
	// try {
	// result = databusconvert.execute("MsgManage", methodName,
	// JsonObject.fromObject(param));
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("", e.getMessage());
	// logger.error(e.getMessage(), e);
	// }
	// JsonObject jo = JsonObject.fromObject(result);
	// if ("1".equals(jo.get("RESULT"))) {// 审核操作成功
	// if ("2".equals(status)) {
	// jo.remove("RESULT");
	// jo.put("RESULT", "2");
	// }
	// }
	// logger.info(result);
	// String msgresult = setMessage(jo.toString(), "MSGMANGE", "VERIFY");
	// // 将新增是否成功的结果进行消息状态组合便于返回给页面展示
	// // //addSystemLog(LogConfigParam.SYS_MGR_ID,
	// // LogConfigParam.SYS_MGR_NAME, LogConfigParam.OPERATOR_TYPE_ADD,
	// // jsonToString(jo.toString()),
	// // this.getJSONPropertyValue(result, "msg"));
	// printToView(msgresult);
	// }
	return null;
    }

    /**
     * 
     * @Title: modifyMsgAndReVerify
     * @Description:(修改并提交审核)
     * @return
     */
    public String doModifyMsgAndReVerify() {

	// String data = request.getParameter("data");
	// JsonObject jsonb = JsonObject.fromObject(data); // 将data转换为json格式
	// MsgInfo minfo = (MsgInfo) JsonObject.toBean(jsonb, MsgInfo.class);
	// minfo.setContent(htmlConvert(request.getParameter("content"),
	// false));
	// // 后台长度验证??
	// if (isOutLen(minfo.getContent(), contLen, "模版内容不能超过" + contLen / 2
	// + "个字符!")) {
	// return null;
	// }
	// // 设置发送
	// String sendFun = jsonb.getString("sendFun");
	// if ("2".equals(sendFun)) {// 通过选择群组发送
	// if (!minfo.getReceiver().startsWith("$"))
	// minfo.setReceiver("$" + minfo.getReceiver());
	// }
	// String sendType = minfo.getSendtype();
	// if ("0".equals(sendType) || "1".equals(sendType)) {//
	// 立即发送和定时发送的发送频率设为0(一次)
	// minfo.setFrequency("0");
	// }
	//
	// String verifyid = jsonb.getString("verifyid");
	// IData param = new DataMap();
	// minfo.setSender(loginUserID);
	// param.put("MSGINFO", minfo);
	// param.put("VERIFYID", verifyid);
	// String result = "";
	// logger.info("JSONUtil.iDataToJson(param)： "
	// + JsonObject.fromObject(param));
	// try {
	// result = databusconvert.execute("MsgManage",
	// "modifyMsgAndReVerify", JsonObject.fromObject(param));
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("", e.getMessage());
	// logger.error(e.getMessage(), e);
	// }
	// String msgresult = setMessage(result, "MSGMANGE", "VERIFYMODIFY"); //
	// 将修改是否成功的结果进行消息状态组合便于返回给页面展示
	// //addSystemLog(
	// LogConfigParam.SYS_MGR_ID,
	// LogConfigParam.SYS_MGR_NAME,
	// LogConfigParam.OPERATOR_TYPE_MODIFY,
	// "修改并提交审核"
	// + jsonToString(JsonObject.fromObject(param).toString()),
	// this.getJSONPropertyValue(result, "msg"));
	// printToView(msgresult);
	return null;
    }

    /**
     * 
     * @Title: doDeleteMsgAndVerify
     * @Description: (删除未通过的群发任务)
     * @return
     */
    public String doDeleteMsgAndVerify() {
	// String ids = request.getParameter("id");
	// String[] verifyids = StringUtils.split(ids, ",");
	// List<String> verifylist = new ArrayList<String>();
	// for (int i = 0; i < verifyids.length; i++) {
	// verifylist.add(verifyids[i]);
	// }
	// // String jsondata = JSONUtil.listToJson(verifylist, verifylist);
	// String result = "";
	// try {
	// result = databusconvert.execute("MsgManage", "deleteMsgAndVerify",
	// JsonArray.fromArray(verifylist));
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("", e.getMessage());
	// logger.error(e.getMessage(), e);
	// }
	// String msgresult = setMessage(result, "MSGMANGE", "DELETEVERIFY"); //
	// 将新增是否成功的结果进行消息状态组合便于返回给页面展示
	// //addSystemLog(LogConfigParam.SYS_MGR_ID,
	// LogConfigParam.SYS_MGR_NAME,
	// LogConfigParam.OPERATOR_TYPE_DELETE, "删除未通过审核的群发任务"
	// + jsonToString(JsonArray.toString(verifylist)), this
	// .getJSONPropertyValue(result, "msg"));
	// printToView(msgresult);
	return null;
    }

    // ********************************发送消息部分*************************************************

    /**
     * @title消息发送
     * @return String
     */
    public String sendMsg() {
	// try {
	// String data = request.getParameter("data");
	// data = htmlConvert(data, false);// 替换特殊字符
	// JsonObject jsonb = JsonObject.fromObject(data); //
	// 将data转换为json格式content
	// jsonb.remove("hour");// 数据格式有问题，且已取此时间组合成新时间，故remove
	// jsonb.remove("minutes");// 数据格式有问题，且已取此时间组合成新时间，故remove
	// MsgInfo minfo = (MsgInfo) JsonObject.toBean(jsonb, MsgInfo.class);
	// //minfo.setContent(htmlConvert(request.getParameter("content"),
	// false));
	// // 后台长度验证??
	// if (isOutLen(minfo.getContent(), contLen, "消息内容不能超过" + contLen / 2
	// + "个字符!")) {
	// return null;
	// }
	// // 设置发送
	// String sendFun = jsonb.getString("sendFun");
	// if ("2".equals(sendFun)) {// 通过选择群组发送
	// if (!minfo.getReceiver().startsWith("$"))
	// minfo.setReceiver("$" + minfo.getReceiver());
	// }
	// String sendType = minfo.getSendtype();
	// if ("0".equals(sendType) || "1".equals(sendType)) {//
	// 立即发送和定时发送的发送频率设为0(一次)
	// minfo.setFrequency("0");
	// }
	// String result = "";
	// minfo.setSender(loginUserID);
	//
	// result = databusconvert.execute("MsgManage", "sendMsg", JsonObject
	// .fromObject(minfo));
	//
	// String msgresult = setMessage(result, "MSGMANGE", "SEND"); //
	// 将新增是否成功的结果进行消息状态组合便于返回给页面展示
	// printToView(msgresult);
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("", e.getMessage());
	// logger.error(e.getMessage(), e);
	// e.printStackTrace();
	// }
	return null;
    }

    /**
     * 通过消息类型获得模版信息
     * 
     * @return
     */
    public String getSendMsgTemplate() {
	// try {
	// String msgType = request.getParameter("MSGTYPE"); // 获取消息模板的ID
	// IData idata = new DataMap();
	// idata.put("MSGTYPE", msgType);
	// // String datas = JSONUtil.iDataToJson(idata);
	// String data;
	// data = databusconvert.execute("MsgManage", "getMsgTemplates",
	// JsonObject.fromObject(idata));
	// JsonObject json = JsonObject.fromObject(data);
	// JsonArray listObject = JsonArray.fromArray(json.get("LIST")
	// .toString());
	// StringBuilder typeBuffer = new StringBuilder();
	// typeBuffer.append("<option value=\"\">不使用模版</option>");
	// for (int i = 0; i < listObject.size(); i++) {
	// JsonObject jo = listObject.getJsonObject(i);
	// typeBuffer.append("<option value=\"").append(
	// jo.getString("templateid")).append("\">").append(
	// jo.getString("templatename")).append("</option>");
	// }
	// printToView(typeBuffer.toString());
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// e.printStackTrace();
	// }
	return null;
    }

    /**
     * @title 初始化消息发送表单
     * @return String
     */
    public String initMsgData() {
	// try {
	//
	// String msgtype = queryDictEntryByType_Integer("MSGCENTER_MSGTYPE");
	// // 消息类型//
	// // (重要提醒,官方动态)
	// String MSGCENTER_FREQUENCY =
	// queryDictEntryByType_Integer("MSGCENTER_FREQUENCY");
	// // JSONObject.fromObject(object)
	// // 去掉周期发送里的'一次'选项
	// JsonArray jarr = JsonArray.fromArray(MSGCENTER_FREQUENCY);
	// // jarr.remove(0);
	// Object[] ja = jarr.toArray();
	// JsonArray newarr = new JsonArray();
	// for (int i = 1; i < ja.length; i++) {
	// JsonObject jo = JsonObject.fromObject(ja[i]);
	// if (!"0".equals(jo.getString("value"))) {
	// newarr.add(jo);
	// // jarr.remove(jo);
	// // break;
	// }
	// }
	//
	// StringBuffer result = new StringBuffer();
	// result.append("{");
	// result.append("msgtype:");
	// result.append(msgtype);// 消息类型
	// result.append(",");
	// result.append("frequency:");
	// result.append(jarr.toString());// 发送周期
	// result.append("}");
	// printToView(result.toString());
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// e.printStackTrace();
	// }
	return null;
    }

    /**
     * @title 初始化消息类型
     * @return String
     */
    public void initMesType() {
	// String msgtype = queryDictEntryByType_Integer("MSGCENTER_MSGTYPE");
	// // 消息类型//
	// JsonArray ja = JsonArray.fromArray(msgtype);
	// StringBuffer result = new StringBuffer("");
	// for(int i = 0; i < ja.size(); i++){
	// JsonObject js = JsonObject.fromObject(ja.get(i));
	// result.append("<option value=\"")
	// .append(js.getString("value"))
	// .append("\">")
	// .append(js.getString("text"))
	// .append("</option>");
	// }
	// printToView(result.toString());
    }

    /**
     * @title 初始化消息发送周期
     * @return String
     */
    public void initMsgFre() {
	// String MSGCENTER_FREQUENCY =
	// queryDictEntryByType_Integer("MSGCENTER_FREQUENCY");
	// // JSONObject.fromObject(object)
	// // 去掉周期发送里的'一次'选项
	// JsonArray jarr = JsonArray.fromArray(MSGCENTER_FREQUENCY);
	// // jarr.remove(0);
	// Object[] ja = jarr.toArray();
	// StringBuffer result = new StringBuffer("");
	// for (int i = 1; i < ja.length; i++) {
	// JsonObject jo = JsonObject.fromObject(ja[i]);
	// if (!"0".equals(jo.getString("value"))) {
	// result.append("<option value=\"")
	// .append(jo.getString("value"))
	// .append("\">")
	// .append(jo.getString("text"))
	// .append("</option>");
	// }
	// }
	// printToView(result.toString());
    }

    public void getServerTime() {
	// printToView(DateUtil.getNowDate("yyyy-MM-dd-HH-mm"));
    }

    public String getNotifyType() {
	// IData param = new DataMap();
	// param.put("DICTTYPEID", "MSGCENTER_NOTIFYTYPE");
	// JsonObject inputParamjson = JsonObject.fromObject(param);
	// // 取得应用类型列表中原始数据
	// String types = "";
	// String cacheID = request.getParameter("cacheID");
	// try {
	// types = databusconvert.execute("Dict",
	// "queryDictEntryByType_Integer", inputParamjson);
	// if (types != null) {
	// if (cacheID != null) {
	// this.printFormatData(cacheID, types);
	// } else {
	// StringBuffer sb = new StringBuffer();
	// JsonArray ops = JsonArray.fromArray(types);
	// ListIterator<JsonObject> options = ops.listIterator();
	// logger.info("options------------------------:" + ops);
	// while (options.hasNext()) {
	// JsonObject obj = JsonObject.fromObject(options.next());
	//
	// sb.append("<span class=\"_m_checkbox\"><input type=\"checkbox\" name=\"sendTypes\" id=\"sendTypes\" value=\""
	// + obj.getString("value")
	// + "\" /><lable>");
	// sb.append(obj.getString("text")).append(
	// "</lable></span>");
	// }
	// printToView(sb.toString());
	// }
	// }
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(), e);
	// }

	return null;
    }

    /**
     * 
     * @Title: initOrgTree
     * @Description: 初始化组织结构树
     * @return
     * @throws IOException
     */
    public void initOrgTree() {
	// String result = "";
	// try {
	// result = convert.execute("User", "initOrgTree", null);
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("", e.getMessage());
	// logger.error(e.getMessage(), e);
	// }
	// printToView(result);
    }

    /**
     * 查询用户信息
     * 
     * @return
     */
    public String getUsers() {
	// String result = "";
	// try {
	// JsonObject obj = new JsonObject();
	// obj.put("ORGID", request.getParameter("ogid"));
	// result = databusconvert.execute("User", "queryEmpInfos", obj);
	// if (result != null) {
	// StringBuffer sb = new StringBuffer();
	// JsonObject json = JsonObject.fromObject(result);
	// JsonArray listObject = JsonArray.fromArray(json.get("LIST")
	// .toString());
	// ListIterator<JsonObject> options = listObject.listIterator();
	// while (options.hasNext()) {
	// JsonObject jo = options.next();
	// sb.append("<option value=\"").append(
	// jo.getString("operatorid")).append("\">").append(
	// jo.getString("realname")).append("</option>");
	// }
	// printToView(sb.toString());
	// }
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("", e.getMessage());
	// logger.error(e.getMessage(), e);
	// }
	return null;
    }

    /**
     * @title 初始化分组下拉树
     * @return String
     */
    public String initGroupTree() {
	// String cacheID = request.getParameter("cacheID");
	// String result = "{}";
	// try {
	// JsonObject obj = new JsonObject();
	// Pagination pagination = new Pagination(1, 1000);
	// obj.put("PAGE", pagination);
	// result = databusconvert.execute("MsgManage", "initGroupTree", obj);
	// if (result != null) {
	// JsonArray listObject = JsonArray.fromArray(JsonObject
	// .fromObject(result).get("LIST"));
	// Object[] options = listObject.toArray();
	// JsonArray restr = new JsonArray();
	// for(int i = 0; i < options.length;i++){
	// JsonObject jo = JsonObject.fromObject(options[i]);
	// JsonObject newJo = new JsonObject();
	// if(cacheID != null){
	// newJo.put("id", jo.get("queryId"));
	// newJo.put("val", jo.get("queryName"));
	// newJo.put("pid", "0");
	// } else {
	// newJo.put("id", jo.get("queryId"));
	// newJo.put("text", jo.get("queryName"));
	// newJo.put("parentid", "0");
	// }
	// restr.add(newJo);
	// }
	// //printToView(restr.toString());
	// result = restr.toString();
	// }
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("", e.getMessage());
	// logger.error(e.getMessage(), e);
	// }
	// if(cacheID != null){
	// this.printFormatData(cacheID, result);
	// } else {
	// printToView(result);
	// }

	return null;
    }

    // ********************************发送和接收消息日志查询部分*************************************************
    /**
     * 查询日志列表
     */
    public void queryMsgLogs() {
	// int page = 1; // 列表分页显示 - 设置时当前的页码
	// int rows = 10; // 列表分页显示 - 设置每页显示的记录数
	// try {
	// rows = Integer.valueOf(request.getParameter("rows"));
	// page = Integer.valueOf(request.getParameter("page"));
	// } catch (NumberFormatException e1) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// page = 1; // 列表分页显示 - 设置时当前的页码
	// rows = 10; // 列表分页显示 - 设置每页显示的记录数
	// }
	// Pagination pagination = new Pagination(page, rows); //
	// 通过调用业务方法，获取分页数据
	// IData param = new DataMap(); // 查询参数IDATA
	// String data = request.getParameter("data"); // 获取查询的条件参数
	// String cacheID = request.getParameter("cacheID");
	// try {
	// if (data != null) {
	// data = java.net.URLDecoder.decode(data, "UTF-8"); // 对其解码
	// JsonObject jso = JsonObject.fromObject(data); // 参数格式化成json格式
	// param.put("MSGTYPE", jso.get("msgType")); // 查询条件：消息类型(重要提醒,官方动态)
	// param.put("NOTIFYTYPE", jso.get("sendType")); // 查询条件：发送方式(飞信,邮箱)
	// param.put("STATUS", jso.get("state")); // 查询条件：状态
	// param.put("SENDTIME1", jso.get("bgTime")); // 查询条件：开始时间
	// param.put("SENDTIME2", jso.get("edTime")); // 查询条件：结束时间
	// }
	// // 排序
	// String orderStr = "CREATETIME DESC";
	// String sidx = request.getParameter("sidx");
	// String sord = request.getParameter("sord");
	// if (sidx != null && sidx.length() > 0) {
	// orderStr = sidx.toUpperCase() + " "
	// + (sord == null ? "ASC" : sord.toUpperCase());
	// }
	// // 只显示主日志
	// param.put("LOGTYPE", "0");
	// param.put(CommonConfig.orderby, orderStr);
	// param.put("PAGE", pagination);
	// // System.out.println(JsonObject.toString(param));
	// if(cacheID != null){
	// String result = databusconvert.execute("MsgManage", "queryMsgLogs",
	// JsonObject.fromObject(param));
	// JsonObject json = JsonObject.fromObject(result);
	// //调用前端总线执行接口方法并返回结果
	// StringBuilder datas = new StringBuilder();
	// datas.append("{");
	// //增加判断，统一datagrid数据格式和普通控件的数据源格式
	// if(json!=null && !json.toString().equals("null") &&
	// json.get("PAGE")!=null){
	// String types =
	// databusconvert.execute("Dict","queryDictEntryByType_Integer",JsonObject.fromObject("{\"DICTTYPEID\":\"MSGCENTER_NOTIFYTYPE\"}"));
	// Map<String, String> map = new HashMap<String, String>();
	// if (types != null) {
	// for (Object obj : JsonArray.fromArray(types).toArray()) {
	// JsonObject jo = JsonObject.fromObject(obj);
	// map.put(jo.getString("value"), jo.getString("text"));
	// }
	// }
	// JsonArray ja = JsonArray.fromArray(json.get("LIST"));
	// List newja = new ArrayList();
	// Object[] ob = ja.toArray();
	// for(int i = 0; i < ob.length; i++){
	//
	// JsonObject js = JsonObject.fromObject(ob[i]);
	// String status = js.get("status")==null?"":js.getString("status");
	// String sendtype =
	// js.get("sendtype")==null?"":js.getString("sendtype");
	// String statusinfo =
	// js.get("statusinfo")==null?"":js.getString("statusinfo");
	// String cz = "";
	// if((status.equals("1")||status.equals("7")||(status.equals("5")&&!statusinfo.equals(""))||(status.equals("8")&&!statusinfo.equals("")))&&
	// (sendtype.equals("1")&&!status.equals("1")&&!status.equals("7")&&!status.equals("8"))){
	// cz = "1";//重新发送，发送记录，撤销1
	// } else
	// if((status.equals("1")||status.equals("7")||(status.equals("5")&&!statusinfo.equals(""))||(status.equals("8")&&!statusinfo.equals("")))&&
	// (sendtype.equals("2")&&!status.equals("8"))){
	// cz = "2";//重新发送，发送记录，撤销2
	// } else
	// if(!(status.equals("1")||status.equals("7")||(status.equals("5")&&!statusinfo.equals(""))||(status.equals("8")&&!statusinfo.equals("")))&&
	// (sendtype.equals("1")&&!status.equals("1")&&!status.equals("7")&&!status.equals("8"))){
	// cz = "3";//重新发送，撤销1
	// } else
	// if(!(status.equals("1")||status.equals("7")||(status.equals("5")&&!statusinfo.equals(""))||(status.equals("8")&&!statusinfo.equals("")))
	// &&
	// (sendtype.equals("2")&&!status.equals("8"))){
	// cz = "4";//重新发送，撤销2
	// // } else
	// if(!(status.equals("1")||status.equals("7")||(status.equals("5")&&!statusinfo.equals(""))||(status.equals("8")&&!statusinfo.equals("")))
	// &&
	// //
	// !(sendtype.equals("1")&&!status.equals("1")&&!status.equals("7")&&!status.equals("8"))
	// &&
	// // !(sendtype.equals("2")&&!status.equals("8"))){
	// // cz = "5";//重新发送
	// } else
	// if((status.equals("1")||status.equals("7")||(status.equals("5")&&!statusinfo.equals(""))||(status.equals("8")&&!statusinfo.equals("")))&&
	// !(sendtype.equals("1")&&!status.equals("1")&&!status.equals("7")&&!status.equals("8"))
	// &&
	// !(sendtype.equals("2")&&!status.equals("8"))){
	// cz = "6";//重新发送，发送记录
	// } else {
	// cz = "5";//重新发送
	//
	// }
	// js.put("cz", cz);
	// // 解析发送方式
	// js.put("notifytypename",
	// getNotifytypename(js.getString("notifytype"), map));
	// // 转换接收人,接收组
	// if (js.get("receiver") != null)
	// js.put("receivername", getReceiverName(js.getString("receiver"),
	// ","));
	// if (js.get("realreceiver") != null)
	// js.put("realreceivername",
	// getReceiverName(js.getString("realreceiver"), ","));
	// if (js.get("statusinfo") != null &&
	// !js.getString("statusinfo").equals("") &&
	// !js.getString("status").equals("1"))
	// js.put("statusname", js.getString("statusname") + "(" +
	// js.getString("statusinfo") + ")");
	//
	// newja.add(js);
	// }
	// json.put("LIST", newja);
	//
	// JsonObject pageObject = JsonObject.fromObject(json.get("PAGE"));
	// //总页数
	// String totalpage = pageObject.get("allPage").toString();
	// //总记录数
	// String totalcount = pageObject.get("count").toString();
	// //当前页数
	// String currpage = pageObject.getString("currPage");
	// //页面显示的纪录数
	// String size = pageObject.getString("size");
	// datas.append("data:");
	// datas.append(json.get("LIST").toString());
	// datas.append(",info:{total:"+totalcount+",page:"+currpage+",records:"+totalpage+",rows:"+size+"}");
	// }else{
	// datas.append("data:");
	// datas.append(data==null||data.equals("null")?"":data);
	// }
	// datas.append("}");
	// this.printFormatData(cacheID, datas.toString());
	//
	// } else {
	//
	// bindData("MsgManage", "queryMsgLogs", JsonObject.fromObject(param),
	// page, rows);
	// }
	//
	//
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(), e);
	// }
    }

    /**
     * 初始化查询条件
     * 
     * @return
     */
    public String initMsgLogSel() {
	// StringBuilder result = new StringBuilder(); // 业务方法无参数的示例
	// String NOTIFYTYPE =
	// queryDictEntryByType_Integer("MSGCENTER_NOTIFYTYPE"); // 发送方式(飞信,邮箱)
	// String msgtype = queryDictEntryByType_Integer("MSGCENTER_MSGTYPE");
	// // 消息类型
	// // (重要提醒,官方动态)
	// String state = queryDictEntryByType_Integer("MSGCENTER_STATUS"); //
	// 审核标志??
	// String cacheID = request.getParameter("cacheID");
	// if (cacheID != null) {
	// result.append("{");
	// result.append("msgType:{data:");
	// result.append(msgtype);
	// result.append("},");
	// result.append("sendType:{data:");
	// result.append(NOTIFYTYPE);
	// result.append("},");
	// result.append("state:{data:");
	// result.append(state);
	// result.append("}}");
	// this.printFormatData(cacheID, result.toString());
	// } else {
	// result.append("{");
	// result.append("msgType:");
	// result.append(msgtype);
	// result.append(",");
	// result.append("sendType:");
	// result.append(NOTIFYTYPE);
	// result.append(",");
	// result.append("state:");
	// result.append(state);
	// result.append("}");
	// printToView(result.toString());
	// }
	return null;
    }

    /**
     * 
     * @Title: getMsgLogInfo
     * @Description: 日志详细信息查询
     */
    public void getMsgLogInfo() {
	// String logId = request.getParameter("logId");
	// IData param = new DataMap();
	// param.put("LOGID", logId);
	// String result = "{}";
	// String cacheID = request.getParameter("cacheID");
	// try {
	// result = databusconvert.execute("MsgManage", "getLogMsg",
	// JsonObject.fromObject(param));
	// JsonObject jo = JsonObject.fromObject(result);
	// jo = parseSendTime(jo);
	// jo.put("receivername", getReceiverName(jo.getString("receiver"),
	// ","));
	// jo.put("notifytypename", getNotifytypename(jo
	// .getString("notifytype"), null));
	// if(cacheID !=null){
	// this.printFormatData(cacheID, jo.toString());
	// } else {
	// printToView(jo.toString());
	// }
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(), e);
	// }
    }

    /**
     * 
     * @Title: resendMsg
     * @Description: 消息重新发送
     */
    public void reSendMsg() {
	// String logId = request.getParameter("logId");
	// IData param = new DataMap();
	// param.put("LOGID", logId);
	// String result = "{}";
	// try {
	// result = databusconvert.execute("MsgManage", "getLogMsg",
	// JsonObject.fromObject(param));
	// JsonObject jo = JsonObject.fromObject(result);
	// MsgInfo minfo = (MsgInfo) JsonObject.toBean(result, MsgInfo.class);
	// // 重新发送都视为立即发送且接收人也是当前这条日志的人
	// minfo.setSender(loginUserID);
	// minfo.setMsgid(null);
	// // logger.debug("fff");
	// minfo.setSender(loginUserID);
	// minfo.setFrequency("0");// 频率为1次
	// minfo.setReceiver(jo.getString("realreceiver"));// 实际发送人
	// minfo.setSendtype(MsgManagerService.SEND_TYPE_NOW);// 立即发送
	// result = databusconvert.execute("MsgManage", "sendMsg", JsonObject
	// .fromObject(minfo));
	// String msgresult = setMessage(result, "MSGMANGE", "SEND"); //
	// 将新增是否成功的结果进行消息状态组合便于返回给页面展示
	// printToView(msgresult);
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(), e);
	// }
    }

    /**
     * 
     * @Title: getMsgData
     * @Description: 通过发送日志生成发送信息用于修改后发送
     */
    public void getMsgData() {
	// String logId = request.getParameter("logId");
	// if (logId == null || logId.equals("")) {
	// printToView("{}");
	// return;
	// }
	// IData param = new DataMap();
	// param.put("LOGID", logId);
	// String result = "{}";
	// try {
	// result = databusconvert.execute("MsgManage", "getLogMsg",
	// JsonObject.fromObject(param));
	// // "receiver" viewObj msgtype sendTypes
	// //
	// {"content":"发给群组发给群组发给群组发给群组发给群组","count":"","createtime":"2011-06-30
	// //
	// 19:31:08.0","endtime":"","finishtime":"","frequency":"0","frequencyname":"一次","logid":"16167","logtype":"0","logtypename":"主日志","msgid":"16166","msgtype":"1","msgtypename":"重要提醒","notifytype":"4","notifytypename":"短信","pid":"0","realreceiver":"","receiver":"$15,69","sender":"1","sendtime":"","sendtype":"0","sendtypename":"","starttime":"2011-06-29
	// //
	// 20:50:00.0","status":"3","statusinfo":"","statusname":"审核开始","templateid":"","title":"发给群组"}
	// JsonObject jo = JsonObject.fromObject(result);
	// jo.put("viewObj", getReceiverName(jo.getString("receiver"), ","));//
	// 接收人
	// jo.put("sendTypesHid", super.bitand(jo.getString("notifytype"),
	// getNotiyTypeStr(), ","));// 发送类型(短信,彩信,邮件)
	// jo.put("templateidHid",
	// jo.get("templateid")==null?"":jo.get("templateid"));
	// jo = this.parseSendTime(jo);
	// printToView(jo.toString());
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(), e);
	// }
    }

    /**
     * 
     * @Title: pushJob
     * @Description: 撤销任务调度
     */
    public void pushJob() {
	// String logId = request.getParameter("msgId");
	// IData param = new DataMap();
	// param.put("MSGID", logId);
	// String result = "";
	// try {
	// result = databusconvert.execute("MsgManage", "pauseJobByMsgid",
	// JsonObject.fromObject(param));
	// String msgresult = setMessage(result, "MSGMANGE", "DELJOB"); //
	// 将新增是否成功的结果进行消息状态组合便于返回给页面展示
	// printToView(msgresult);
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(), e);
	// }
    }

    /**
     * 
     * @Title: queryMsgSubLogs
     * @Description: 根据主日志ID查询子日志信息
     */
    public void queryMsgSubLogs() {
	// int page = 1; // 列表分页显示 - 设置时当前的页码
	// int rows = 10; // 列表分页显示 - 设置每页显示的记录数
	// try {
	// rows = Integer.valueOf(request.getParameter("rows"));
	// page = Integer.valueOf(request.getParameter("page"));
	// } catch (NumberFormatException e1) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// page = 1; // 列表分页显示 - 设置时当前的页码
	// rows = 10; // 列表分页显示 - 设置每页显示的记录数
	// }
	// Pagination pagination = new Pagination(page, rows); //
	// 通过调用业务方法，获取分页数据
	// IData param = new DataMap(); // 查询参数IDATA
	// String pId = request.getParameter("pId"); // 获取查询的条件参数
	// String cacheID = request.getParameter("cacheID");
	// try {
	// if (pId != null) {
	// param.put("PID", pId); // 查询条件：消息类型(重要提醒,官方动态)
	// }
	// // 排序
	// String orderStr = "SENDTIME DESC";
	// String sidx = request.getParameter("sidx");
	// String sord = request.getParameter("sord");
	// if (sidx != null && sidx.length() > 0) {
	// orderStr = sidx.toUpperCase() + " "
	// + (sord == null ? "ASC" : sord.toUpperCase());
	// }
	// // 只显示子日志
	// param.put("LOGTYPE", "1");
	// param.put(CommonConfig.orderby, orderStr);
	// param.put("PAGE", pagination);
	// formatbindData("MsgManage", "queryMsgLogs",
	// JsonObject.fromObject(param));
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(), e);
	// }
    }

    public void initMsgFrom() {
	// try {
	//
	// String msgtype = queryDictEntryByType_Integer("MSGCENTER_MSGTYPE");
	// // 消息类型//
	// // (重要提醒,官方动态)
	// String MSGCENTER_FREQUENCY =
	// queryDictEntryByType_Integer("MSGCENTER_FREQUENCY");
	// // JSONObject.fromObject(object)
	// // 去掉周期发送里的'一次'选项
	// JsonArray jarr = JsonArray.fromArray(MSGCENTER_FREQUENCY);
	// // jarr.remove(0);
	// Object[] ja = jarr.toArray();
	// JsonArray newarr = new JsonArray();
	// for (int i = 1; i < ja.length; i++) {
	// JsonObject jo = JsonObject.fromObject(ja[i]);
	// if (!"0".equals(jo.getString("value"))) {
	// newarr.add(jo);
	// // jarr.remove(jo);
	// // break;
	// }
	// }
	//
	// StringBuffer result = new StringBuffer();
	// result.append("{");
	// result.append("msgtype:{data:");
	// result.append(msgtype);// 消息类型
	// result.append("},");
	// result.append("frequency:{data:");
	// result.append(jarr.toString());// 发送周期
	// result.append("}}");
	// this.printToViewMethod(result.toString());
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// e.printStackTrace();
	// }
    }

    /** ********************** 私有方法 ****************************************** */
    /**
     * 
     * @Title: getNotiyTypeStr
     * @Description: 获得所有发送类型(短信,彩信,邮件)字符串,用','分割
     * @return
     */
    private String getNotiyTypeStr() {
	// IData param = new DataMap();
	// param.put("DICTTYPEID", "MSGCENTER_NOTIFYTYPE");
	// // String inputParamjson = JSONUtil.iDataToJson(param);
	// String types;
	//
	// StringBuffer sb = new StringBuffer();
	// try {
	// types = databusconvert.execute("Dict",
	// "queryDictEntryByType_Integer", JsonObject
	// .fromObject(param));
	// if (types != null) {
	// JsonArray ops = JsonArray.fromArray(types);
	// ListIterator<JsonObject> options = ops.listIterator();
	// int i = 0;
	// while (options.hasNext()) {
	// JsonObject obj = JsonObject.fromObject(options.next());
	// if (i == 1)
	// sb.append(",");
	// sb.append(obj.getString("value"));
	// i = 1;
	// }
	// }
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// return sb.toString();
	// }
	// return sb.toString();
	return null;
    }

    /**
     * 
     * @Title: getUserName
     * @Description: 获得用户ID对应的用户名
     * @param codes 用户ID数组operatorids
     * @return 用户名,多个用','隔开
     * @throws Exception
     */
    private String getUserName(String[] codes) {
	// StringBuilder restr = new StringBuilder();
	// for (int i = 0; i < codes.length; i++) {
	// if (codes[i] == null || "".equals(codes[i].trim()))
	// continue;
	// if (i > 0)
	// restr.append(",");
	// try {// getOperatorByOperatorId
	// try {
	// new Long(codes[i]);
	// } catch (Exception ex) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// restr.append(codes[i]);// 非数字的,不查询
	// continue;
	// }
	// String result = databusconvert.execute("User",
	// "getOperatorByOperatorId", JsonObject
	// .fromObject("{\"OPERATORID\":\"" + codes[i]
	// + "\"}"));
	// if (result != null) {
	// JsonObject jo = JsonObject.fromObject(result);
	// restr.append(jo.getString("operatorname"));
	// } else {
	// restr.append(codes[i]);
	// }
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// restr.append(codes[i]);
	// }
	// }
	// return restr.toString();
	return null;
    }

    /**
     * 
     * @Title: getGroupName
     * @Description: 获得群组对应的名称
     * @param codesID ,多个用','隔开
     * @return名称,多个用','隔开
     * @throws Exception
     */
    private String getGroupName(String[] codes) {
	// StringBuilder restr = new StringBuilder();
	// JsonObject obj = new JsonObject();
	// Pagination pagination = new Pagination(1, 1000);
	// obj.put("PAGE", pagination);
	// for (int i = 0; i < codes.length; i++) {
	// if (i > 0)
	// restr.append(",");
	// try {
	// obj.remove("QueryId");
	// obj.put("QueryId", codes[i]);
	// String result = databusconvert.execute("MsgManage",
	// "initGroupTree", obj);
	// if (result != null) {
	// JsonObject jo = JsonObject.fromObject(result);
	// JsonArray ja = (JsonArray)jo.get("LIST");
	// restr.append(ja.size() > 0 ? ja.getJsonObject(0).getString(
	// "queryName") : codes[i]);
	// } else {
	// restr.append(codes[i]);
	// }
	// } catch (Exception ex) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// restr.append(codes[i]);
	// }
	// }
	// return restr.toString();
	return null;
    }

    /**
     * 
     * @Title: getReceiverName
     * @Description: 接收人代码转名称
     * @param code 接受人代码多个用分隔符隔开
     * @param spel 分隔符
     * @return
     * @throws Exception
     */
    private String getReceiverName(String code, String spel) throws Exception {
	// if (code == null || code.trim().equals(""))
	// return "";
	// int flag = code.indexOf("$");
	// String userNames = "";
	// if (flag == -1) {// 全部是个人发送
	// String[] s = code.split(spel);
	// userNames = getUserName(s);
	// } else {
	// if (flag == 0) {// 全部群组发送
	// String[] codes = code.substring(1).split(spel);
	// userNames = getGroupName(codes);
	// } else {
	// String[] codes = code.split("$");
	// // 个人发送
	// userNames = getUserName(codes[0].split(spel));
	// // 群组发送
	// userNames += " " + getGroupName(codes[1].split(spel));
	// ;
	// }
	// }
	// return userNames;
	return null;
    }

    /**
     * 
     * @Title: bindData
     * @Description: 自定义绑定表格控件,内部对发送方式,接收人进行解析
     * @param packageName
     * @param functionName
     * @param paramValue
     * @param page
     * @param rows
     * @throws Exception
     */
    private void bindData(String packageName, String functionName, JsonObject paramValue, int page, int rows)
	    throws Exception {
	// // 通过调用业务方法，获取分页数据
	// String data = databusconvert.execute(packageName, functionName,
	// paramValue);
	// try {
	// // 获取表格组件的实例
	// DataGrid dg = new DataGrid();
	// dg.setPage(page);
	// dg.setRows(rows);
	//
	// // 获取分页组件中各参数值
	// JsonObject json = JsonObject.fromObject(data);
	// JsonObject pageObject = JsonObject.fromObject(json.get("PAGE"));
	// // 获取数据库中所有记录数在此列表控件中应予以分配的页数
	// String totalpage = pageObject.get("allPage").toString();
	// String totalcount = pageObject.get("count").toString();
	// dg.setTotal(Integer.parseInt(totalpage));
	// dg.setRecords(Integer.parseInt(totalcount));
	//
	// if (dg.getPage() > dg.getTotal()) {
	// dg.setPage(dg.getTotal());
	// }
	//
	// // 获取所有列表值
	// if (json.get("LIST") == null || "".equals(json.get("LIST"))) {
	// // return;
	// } else {
	// Object o = json.get("LIST");
	// // JsonArray listObject =(JsonArray) json.get("LIST");
	// JsonArray listObject = JsonArray.fromArray(o.toString());
	// // 获得所有发送方式
	//
	// if (listObject.size() > 0) {
	// String types =
	// databusconvert.execute("Dict","queryDictEntryByType_Integer",JsonObject.fromObject("{\"DICTTYPEID\":\"MSGCENTER_NOTIFYTYPE\"}"));
	// Map<String, String> map = new HashMap<String, String>();
	// if (types != null) {
	// for (Object obj : JsonArray.fromArray(types).toArray()) {
	// JsonObject jo = JsonObject.fromObject(obj);
	// map.put(jo.getString("value"), jo.getString("text"));
	// }
	// }
	// JsonArray result = new JsonArray();
	// for (Object obj : listObject.toArray()) {
	// JsonObject jo = JsonObject.fromObject(obj);
	// // 解析发送方式
	// jo.put("notifytypename",
	// getNotifytypename(jo.getString("notifytype"), map));
	// // 转换接收人,接收组
	// if (jo.get("receiver") != null)
	// jo.put("receivername", getReceiverName(jo
	// .getString("receiver"), ","));
	// if (jo.get("realreceiver") != null)
	// jo.put("realreceivername", getReceiverName(jo
	// .getString("realreceiver"), ","));
	// if (jo.get("statusinfo") != null
	// && !jo.getString("statusinfo").equals("")
	// && !jo.getString("status").equals("1"))
	// jo.put("statusname", jo.getString("statusname")
	// + "(" + jo.getString("statusinfo") + ")");
	// result.add(jo);
	// }
	// dg.setData(result.toString());
	// } else {
	// dg.setData(listObject.toString());
	// }
	//
	// }
	// String returnData = dg.toJSON();
	//
	// printToView(returnData);
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// e.printStackTrace();
	// }

    }

    /**
     * 
     * @Title: bindData
     * @Description: 自定义绑定表格控件,内部对发送方式,接收人进行解析
     * @param packageName
     * @param functionName
     * @param paramValue
     * @param page
     * @param rows
     * @throws Exception
     */
    private void formatbindData(String packageName, String functionName, JsonObject paramValue) throws Exception {
	// String result = databusconvert.execute(packageName, functionName,
	// paramValue);
	// try {
	// JsonObject json = JsonObject.fromObject(result);
	// // 调用前端总线执行接口方法并返回结果
	// StringBuilder datas = new StringBuilder();
	// String cacheID = request.getParameter("cacheID");
	// datas.append("{");
	// // 增加判断，统一datagrid数据格式和普通控件的数据源格式
	// if (json != null && !json.toString().equals("null")
	// && json.get("PAGE") != null) {
	// String types =
	// databusconvert.execute("Dict","queryDictEntryByType_Integer",
	// JsonObject.fromObject("{\"DICTTYPEID\":\"MSGCENTER_NOTIFYTYPE\"}"));
	// Map<String, String> map = new HashMap<String, String>();
	// if (types != null) {
	// for (Object obj : JsonArray.fromArray(types).toArray()) {
	// JsonObject jo = JsonObject.fromObject(obj);
	// map.put(jo.getString("value"), jo.getString("text"));
	// }
	// }
	// JsonArray ja = JsonArray.fromArray(json.get("LIST"));
	// List newja = new ArrayList();
	// Object[] ob = ja.toArray();
	// for (int i = 0; i < ob.length; i++) {
	//
	// JsonObject js = JsonObject.fromObject(ob[i]);
	//
	// // 解析发送方式
	// js.put("notifytypename", getNotifytypename(js
	// .getString("notifytype"), map));
	// // 转换接收人,接收组
	// if (js.get("receiver") != null)
	// js.put("receivername", getReceiverName(js
	// .getString("receiver"), ","));
	// if (js.get("realreceiver") != null)
	// js.put("realreceivername", getReceiverName(js
	// .getString("realreceiver"), ","));
	// if (js.get("statusinfo") != null
	// && !js.getString("statusinfo").equals("")
	// && !js.getString("status").equals("1"))
	// js.put("statusname", js.getString("statusname") + "("
	// + js.getString("statusinfo") + ")");
	//
	// newja.add(js);
	// }
	// json.put("LIST", newja);
	//
	// JsonObject pageObject = JsonObject.fromObject(json.get("PAGE"));
	// // 总页数
	// String totalpage = pageObject.get("allPage").toString();
	// // 总记录数
	// String totalcount = pageObject.get("count").toString();
	// // 当前页数
	// String currpage = pageObject.getString("currPage");
	// // 页面显示的纪录数
	// String size = pageObject.getString("size");
	// datas.append("data:");
	// datas.append(json.get("LIST").toString());
	// datas.append(",info:{total:" + totalcount + ",page:" + currpage
	// + ",records:" + totalpage + ",rows:" + size + "}");
	// } else {
	// datas.append("data:");
	// datas.append(result == null || result.equals("null") ? ""
	// : result);
	// }
	// datas.append("}");
	// this.printFormatData(cacheID, datas.toString());
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// e.printStackTrace();
	// }
    }

    /**
     * 
     * @Title: getNotifytypename
     * @Description: 解析发送方式为中文表示
     * @param notifytype 发送方式相加所得值
     * @param notifyMaps 发送方式健值对应表
     * @return
     */
    private String getNotifytypename(String notifytype, Map<String, String> notifyMaps) {
	// if (notifytype == null)
	// return "";
	// try {
	// String str = "";
	// Map<String, String> map = new HashMap<String, String>();
	// if (notifyMaps == null || notifyMaps.equals("")) {
	// String types = databusconvert.execute("Dict",
	// "queryDictEntryByType_Integer",
	// JsonObject.fromObject("{\"DICTTYPEID\":\"MSGCENTER_NOTIFYTYPE\"}"));
	// int i = 0;
	// for (Object obj : JsonArray.fromArray(types).toArray()) {
	// JsonObject jo = JsonObject.fromObject(obj);
	// map.put(jo.getString("value"), jo.getString("text"));
	// if (i == 1)
	// str += ",";
	// str += jo.getString("value");
	// i = 1;
	// }
	// } else {// 先查出了发送方式,用于列表的时候转换
	// map = notifyMaps;
	// int i = 0;
	// for (String key : notifyMaps.keySet()) {
	// if (i == 1)
	// str += ",";
	// str += key;
	// i = 1;
	// }
	// }
	//
	// // 解析发送方式
	// String notifyname = "";
	// String restr = super.bitand(notifytype, str, ",");
	// String[] noti = restr.split(",");
	// for (int j = 0; j < noti.length; j++) {
	// if (j > 0) {
	// notifyname += ",";
	// }
	// notifyname += map.get(noti[j]);
	// }
	// return notifyname;
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// e.printStackTrace();
	// return notifytype;
	// }
	return null;
    }

    private String getKeyWords(String text) {
	if (text == null || text.trim().length() < 1)
	    return "";
	KeywordFilterImpl kfi = new KeywordFilterImpl();
	KeywordIterator iterator = kfi.getIterator(text);
	int i = 0;
	StringBuilder sb = new StringBuilder();
	while (iterator.moveNext()) {
	    if (i == 1)
		sb.append(",");
	    sb.append(text.substring(iterator.getStart(), iterator.getEnd()));
	    i = 1;
	}
	return sb.toString();
    }

    /**
     * 
     * @Title: parseMsgInfo
     * @Description: 解析发送时间
     * @param result
     * @return
     */
    private JsonObject parseSendTime(JsonObject jo) {
	try {
	    // 发送时间
	    String sendtype = jo.getString("sendtype") == null ? "0" : jo.getString("sendtype");// 发送方式
	    // 0立即,1定时,2周期
	    // 日期....
	    String timeStr = jo.getString("starttime");// 2011-11-11 12:25
	    StringBuilder sendtime = new StringBuilder();
	    if (sendtype.equals("2")) {// 周期发送
		int frequency = new Integer(jo.getString("frequency"));
		if (frequency == 2) {// 每周
		    Calendar temp = DateUtil.date2Calendar(DateUtil.formatStr2Date(timeStr.substring(0, 16),
			    "yyyy-MM-dd HH:mm"));
		    jo.put("ext_weekNum", temp.get(Calendar.DAY_OF_WEEK) - 1);
		} else {
		    jo.put("ext_weekNum", "");
		}
		jo.put("ext_month", timeStr.substring(5, 7));
		jo.put("ext_day", timeStr.substring(8, 10));
		jo.put("hour1", timeStr.substring(11, 13));
		jo.put("minutes1", timeStr.substring(14, 16));

		switch (frequency) {
		case 1:
		    sendtime.append("每天");
		    break;
		case 2:
		    sendtime.append("每周").append((jo.getInt("ext_weekNum") == 0 ? "日" : jo.getInt("ext_weekNum")))
			    .append(" ");
		    break;
		case 3:
		    sendtime.append("每月").append(jo.getString("ext_day")).append("日");
		    break;
		case 4:
		    sendtime.append("每年").append(jo.getString("ext_month")).append("月").append(jo.getString("ext_day"))
			    .append("日");
		    break;
		}
		sendtime.append(jo.getString("hour1")).append("时").append(jo.getString("minutes1")).append("分");
		jo.put("frequencyTime",
			"ext_weekNum:\"" + jo.getString("ext_weekNum") + "\",ext_month:\"" + jo.getString("ext_month")
				+ "\",ext_day:\"" + jo.getString("ext_day") + "\"");
	    } else if ("1".equals(sendtype)) {// 定时
		if (timeStr != null && timeStr.trim().length() >= 16) {
		    jo.put("year", timeStr.substring(0, 4));
		    jo.put("month", timeStr.substring(5, 7));
		    jo.put("day", timeStr.substring(8, 10));
		    jo.put("hour", timeStr.substring(11, 13));
		    jo.put("minutes", timeStr.substring(14, 16));
		}
		sendtime.append(timeStr);
	    } else {
		sendtime.append(timeStr);
	    }
	    if (sendtime.length() > 0)
		jo.put("sendtime", sendtime.toString());
	    return jo;
	} catch (Exception ex) {
	    ServletActionContext.getRequest().setAttribute("throw", ex);
	    ex.printStackTrace();
	    return jo;
	}
    }

    /**
     * 
     * @Title: isOutLen
     * @Description: 字段超长的后台验证
     * @param txt
     * @param len
     * @param msg
     * @return
     */
    private boolean isOutLen(String txt, long len, String msg) {
	// try {
	// if (txt != null && txt.getBytes("GBK").length > len) {
	// super.printToView("{status:\"bad\",msg:\"" + msg + "\"}");
	// return true;
	// }
	// } catch (UnsupportedEncodingException e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// return false;
	// }
	return false;
    }
}