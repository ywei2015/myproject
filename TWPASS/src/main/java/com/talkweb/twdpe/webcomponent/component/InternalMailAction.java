package com.talkweb.twdpe.webcomponent.component;

import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;

import com.talkweb.twdpe.webcomponent.common.action.ComponentActionSupport;

/**
 * 文件名称:    InternalMailAction.java
 * 内容摘要: 
 * @author:   TalkWeb 
 * @version:  1.0  
 * @Date:     2011-5-30 上午09:50:13  
 * 
 * 修改历史:  
 * 修改日期       修改人员 张誉   版本1.0	   修改内容  
 * ----------------------------------------------  
 * 2011-5-30    李锋     1.0    1.0 XXXX
 *
 * 版权:   版权所有(C)2011
 * 公司:   拓维信息系统股份有限公司
 */
public class InternalMailAction extends ComponentActionSupport {
    private static final Logger logger = LoggerFactory.getLogger(InternalMailAction.class);

    /**
     * queryOtherMsgByPage 分页查询收件夹，通过分页参数定义可以不同的查询结果数 现在是为默认的收件箱列表，不包含私人信件和重要提醒
     */
    public void queryOtherMsgByPage() throws Exception {
	// HttpServletRequest request = ServletActionContext.getRequest();
	// int page = 1; // 列表分页显示 - 设置时当前的页码
	// int rows = 10; // 列表分页显示 - 设置每页显示的记录数
	// try{
	// rows = Integer.valueOf(request.getParameter("rows"));
	// page = Integer.valueOf(request.getParameter("page"));
	// }catch(Exception e){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// page = 1; // 列表分页显示 - 设置时当前的页码
	// rows = 10;
	// }
	// Pagination pagination = new Pagination(page, rows); //通过调用业务方法，获取分页数据
	// JsonObject param = new JsonObject(); //查询参数IDATA
	//
	// param.put("RECEIVER",loginUserID); //获得收件人，通过收件人（登录人员）获得本登录人员收件箱
	// param.put("MSGTYPE1",5);//初始化加载默认的收件箱，不包含私人信件
	// param.put("MSGTYPE2",1);//初始化加载默认的收件箱，不包含重要信件
	// try{
	// param.put("PAGE", pagination);
	// //bindDataGridSource("Info", "queryOtherMsgByPage",
	// JsonObject.fromObject(param), page, rows);
	// this.formatResultData("Info", "queryOtherMsgByPage",
	// JsonObject.fromObject(param));
	// }catch(Exception e){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(),e);
	// }
    }

    /**
     * queryownerMsgByPage 分页查询收件夹，通过分页参数定义可以不同的查询结果数 现在是为私人信件列表
     */
    public void queryownerMsgByPage() throws Exception {
	//
	// int page = 1; // 列表分页显示 - 设置时当前的页码
	// int rows = 10; // 列表分页显示 - 设置每页显示的记录数
	// try{
	// rows = Integer.valueOf(request.getParameter("rows"));
	// page = Integer.valueOf(request.getParameter("page"));
	// }catch(Exception e){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// page = 1; // 列表分页显示 - 设置时当前的页码
	// rows = 10;
	// }
	// Pagination pagination = new Pagination(page, rows); //通过调用业务方法，获取分页数据
	// JsonObject param = new JsonObject(); //查询参数IDATA
	//
	// param.put("RECEIVER",loginUserID); //获得收件人，通过收件人（登录人员）获得本登录人员收件箱
	// param.put("MSGTYPE",5);//初始化加载默认的收件箱，邮件类型为私人信件
	// try{
	// param.put("PAGE", pagination);
	// //bindDataGridSource("Info", "queryownerMsgByPage", param, page,
	// rows);
	// this.formatResultData("Info", "queryownerMsgByPage", param);
	// }catch(Exception e){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(),e);
	// }
    }

    /**
     * querywarnMsgByPage 分页查询收件夹，通过分页参数定义可以不同的查询结果数 现在是为重要提醒
     */
    public void querywarnMsgByPage() throws Exception {
	//
	// int page = 1; // 列表分页显示 - 设置时当前的页码
	// int rows = 10; // 列表分页显示 - 设置每页显示的记录数
	// try{
	// rows = Integer.valueOf(request.getParameter("rows"));
	// page = Integer.valueOf(request.getParameter("page"));
	// }catch(Exception e){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// page = 1; // 列表分页显示 - 设置时当前的页码
	// rows = 10;
	// }
	// Pagination pagination = new Pagination(page, rows); //通过调用业务方法，获取分页数据
	// JsonObject param = new JsonObject(); //查询参数IDATA
	//
	// param.put("RECEIVER",loginUserID); //获得收件人，通过收件人（登录人员）获得本登录人员收件箱
	// param.put("MSGTYPE",1);//初始化加载默认的收件箱，邮件类型为重要提醒
	// try{
	// param.put("PAGE", pagination);
	// //bindDataGridSource("Info", "querywarnMsgByPage", param, page,
	// rows);
	// this.formatResultData("Info", "querywarnMsgByPage", param);
	// }catch(Exception e){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(),e);
	// }
    }

    /**
     * querySendMsgByPage 分页查询发件箱内容，通过分页参数定义可以不同的查询结果数
     */

    public void querySendMsgByPage() throws Exception {
	// int page = 1; // 列表分页显示 - 设置时当前的页码
	// int rows = 10; // 列表分页显示 - 设置每页显示的记录数
	// try{
	// rows = Integer.valueOf(request.getParameter("rows"));
	// page = Integer.valueOf(request.getParameter("page"));
	// }catch(Exception e){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// page = 1; // 列表分页显示 - 设置时当前的页码
	// rows = 10;
	// }
	// Pagination pagination = new Pagination(page, rows); //通过调用业务方法，获取分页数据
	// JsonObject param = new JsonObject(); //查询参数IDATA
	// param.put("SENDER",loginUserID);//获得当前登录人员，发送邮件列表
	// try{
	// param.put("PAGE", pagination);
	// //bindDataGridSource("Info", "querySendMsgByPage", param, page,
	// rows);
	// this.formatResultData("Info", "querySendMsgByPage", param);
	// }catch(Exception e){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(),e);
	// }
    }

    /**
     * // 删除发件信息
     * @return
     */

    public String deleteSendMsg() {
	//
	// String ids = request.getParameter("id");
	// String[] msgids = StringUtils.split(ids, ",");
	// List<String> msglist = new ArrayList<String>();
	// for(int i = 0; i < msgids.length; i++) {
	// msglist.add(msgids[i]);
	// }
	// JsonObject param = new JsonObject();
	// param.put("LIST", msglist);
	// //param.put("RECEIVER",this.loginUserID); // 收件人 目前写死 登录写好后改正
	// String result = "";
	// try{
	// result = databusconvert.execute("Info", "deleteSendMsg", param);
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// }
	// String msgresult = setMessage(result, "INFOMANGE", "DELETE"); //
	// 将新增是否成功的结果进行消息状态组合便于返回给页面展示
	// printToView(msgresult);
	return null;
    }

    /**
     * // 删除收件信息
     * @return
     */
    public String deleteMsg() {
	// // 删除收件信息
	// String ids = request.getParameter("id");
	// String[] msgids = StringUtils.split(ids, ",");
	// List<String> msglist = new ArrayList<String>();
	// for(int i = 0; i < msgids.length; i++) {
	// msglist.add(msgids[i]);
	// }
	// JsonObject param = new JsonObject();
	// param.put("LIST", msglist);
	// param.put("RECEIVER",this.loginUserID); // 收件人 目前写死 登录写好后改正
	// String result = "";
	// try{
	// result = databusconvert.execute("Info", "deleteMsg", param);
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// }
	// String msgresult = setMessage(result, "INFOMANGE", "DELETE"); //
	// 将新增是否成功的结果进行消息状态组合便于返回给页面展示
	// printToView(msgresult);
	return null;
    }

    /**
     * 
     * @Title: getMail
     * @Description: 查找收件详细信息
     * @return
     */
    public void getMail() {
	// // 查找收件信息
	// String id = request.getParameter("receiveid");
	// IData data = new DataMap();
	// data.put("RECEIVEID", id);
	//
	// String result = "";
	// try{
	// result = databusconvert.execute("Info", "getRcvMsg",
	// JsonObject.fromObject(data));
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// result = "{RESULT:\"-1\",info:\"" + e.getMessage() + "\"}";
	// logger.error(e.getMessage(),e);
	// }
	// JsonObject jo = JsonObject.fromObject(result);
	// printToView(jo.toString());
    }

    /**
     * 
     * @Title: getHisMail
     * @Description: 查找用户历史收件详细信息
     * @return
     */
    public void getHisMail() {
	// // 查找历史收件信息
	// String id = request.getParameter("receiveid");
	// IData data = new DataMap();
	// data.put("RECEIVEID", id);
	// String result = "";
	// String cacheID = request.getParameter("cacheID");
	// try{
	// result = databusconvert.execute("Info", "getHisRcvMsg",
	// JsonObject.fromObject(data));
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// result = "{RESULT:\"-1\",info:\"" + e.getMessage() + "\"}";
	// logger.error(e.getMessage(),e);
	// }
	// JsonObject jo = JsonObject.fromObject(result);
	// if(cacheID != null){
	// this.printFormatData(cacheID, jo.toString());
	// } else {
	// printToView(jo.toString());
	// }
    }

    /**
     * 
     * @Title: getSendMsg
     * @Description: 获取发件详细信息
     * @return
     */
    public void getSendMsg() {
	// // 查找发件详细信息
	// String id = request.getParameter("sendid");
	// IData data = new DataMap();
	// data.put("SENDID", id);
	// String result = "";
	// try{
	// result = databusconvert.execute("Info", "getSendMsg",
	// JsonObject.fromObject(data));
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// result = "{RESULT:\"-1\",info:\"" + e.getMessage() + "\"}";
	// logger.error(e.getMessage(),e);
	// }
	// JsonObject jo = JsonObject.fromObject(result);
	// printToView(jo.toString());
    }

    /**
     * 主页面发送邮件ACTION
     * @title 新增写件箱信息
     * @return
     */
    public void addSendMsg() {
	// // SUBJECT :String 消息标题
	// // * CONTENT :String 消息内容
	// // * SENDER :String 消息发送人
	// // * ISSAVE :String 是否保存发送消息：1保存，0不保存
	// // * MSGTYPE :String 消息类型
	// // * SENDTIME :String 发送时间
	// // * LIST :List<String> 收件人id
	// //
	// {"msgtype":"5","subject":"123123","receiver":"123","content":"123","isSave":"1","sender":"1"}
	//
	// //String data = super.htmlConvert(request.getParameter("data"),
	// true);
	// String data = request.getParameter("data");
	// data = htmlConvert(data,false);
	// JsonObject jo = JsonObject.fromObject(data);
	//
	// String result = "-1";
	// // 获取页面填写验证码
	// //String validateCode = jo.getString("validateCode");
	// // 验证码不正确
	// // if
	// (!session.getAttribute("loginValidateCode").equals(validateCode)) {
	// // result = "-4";
	// // } else {
	// JsonObject parmjo = new JsonObject();
	// parmjo.put("SENDER", this.loginUserID);// 添加的用户
	// parmjo.put("SENDNAME", this.loginUserName);
	// parmjo.put("SUBJECT", jo.getString("subject"));
	// parmjo.put("CONTENT", jo.getString("content"));
	// try {
	// parmjo.put("ISSAVE", jo.getString("isSave") == null ? "0" : jo
	// .getString("isSave"));
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// parmjo.put("ISSAVE", "0");
	// }
	// parmjo.put("MSGTYPE", jo.getString("msgtype"));
	// parmjo.put("SENDTIME", DateUtil.getSysTime());
	// parmjo.put("RECEIVERNAME", jo.getString("receiverName"));
	// parmjo.put("LIST", jo.getString("receiver") == null ? new ArrayList()
	// : Arrays.asList(jo.getString("receiver").split(",")));
	// try {
	// result = databusconvert.execute("Info", "sendMsg", parmjo);
	// JsonObject resultjo = JsonObject.fromObject(result);
	// result = resultjo.getString("RESULT");
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("", e.getMessage());
	// logger.error(e.getMessage(), e);
	// e.printStackTrace();
	// }
	// //}
	// //String msgresult = setMessage(result, "INFOMANGE", "ADD"); //
	// // 将新增是否成功的结果进行消息状态组合便于返回给页面展示
	// JsonObject msg = new JsonObject();
	// msg.put("msg", result);
	//
	// printToView(msg.toString());

    }

    /**
     * 预览页面发送邮件ACTION
     * @title 新增写件箱信息
     * @return
     */
    public void addSendViewMsg() {
	// // SUBJECT :String 消息标题
	// // * CONTENT :String 消息内容
	// // * SENDER :String 消息发送人
	// // * ISSAVE :String 是否保存发送消息：1保存，0不保存
	// // * MSGTYPE :String 消息类型
	// // * SENDTIME :String 发送时间
	// // * LIST :List<String> 收件人id
	// //
	// {"msgtype":"5","subject":"123123","receiver":"123","content":"123","isSave":"1","sender":"1"}
	// String data = super.htmlConvert(request.getParameter("data"),true);
	// JsonObject jo = JsonObject.fromObject(data);
	// String result = "-1";
	//
	// JsonObject parmjo =new JsonObject();
	// parmjo.put("SENDER",this.loginUserID);//添加的用户
	// parmjo.put("SENDNAME",this.loginUserName);
	// parmjo.put("SUBJECT", jo.getString("subject"));
	// parmjo.put("CONTENT", jo.getString("content"));
	// parmjo.put("ISSAVE",
	// jo.getString("isSave")==null?"0":jo.getString("isSave"));
	// parmjo.put("MSGTYPE", jo.getString("msgtype"));
	// parmjo.put("SENDTIME", DateUtil.getSysTime());
	// parmjo.put("RECEIVERNAME", jo.getString("receiverName"));
	// parmjo.put("LIST", jo.getString("receiver")==null?new
	// ArrayList():Arrays.asList(jo.getString("receiver").split(",")));
	// try{
	// result = databusconvert.execute("Info", "sendMsg", parmjo);
	// JsonObject resultjo = JsonObject.fromObject(result);
	// result=resultjo.getString("RESULT");
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// }
	// //String msgresult = setMessage(result, "INFOMANGE", "ADD"); //
	// 将新增是否成功的结果进行消息状态组合便于返回给页面展示
	// JsonObject msg = new JsonObject();
	// msg.put("msg", result);
	//
	// printToView(msg.toString());

    }

    /**
     * markReaded
     * @title 更新已读标志信息
     * @return
     */
    public void markReaded() {
	// String ids = request.getParameter("receiveid");
	// String[] msgids = StringUtils.split(ids, ",");
	// List<String> msglist = new ArrayList<String>();
	// for(int i = 0; i < msgids.length; i++) {
	// msglist.add(msgids[i]);
	// }
	// JsonObject param = new JsonObject();
	// param.put("LIST", msglist);
	// param.put("RECEIVER",this.loginUserID);
	// String result = "";
	// try{
	// result = databusconvert.execute("Info", "markReaded", param);
	//
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// result = "{RESULT:\"-1\",info:\"" + e.getMessage() + "\"}";
	// logger.error(e.getMessage(),e);
	// }
	// JsonObject jo = JsonObject.fromObject(result);
	// printToView(jo.toString());
    }

    /**
     * markHisReaded
     * @title 更新历史消息已读标志信息
     * @return
     */
    public void markHisReaded() {
	// String ids = request.getParameter("receiveid");
	// String[] msgids = StringUtils.split(ids, ",");
	// List<String> msglist = new ArrayList<String>();
	// for(int i = 0; i < msgids.length; i++) {
	// msglist.add(msgids[i]);
	// }
	// JsonObject param = new JsonObject();
	// param.put("LIST", msglist);
	// param.put("RECEIVER",this.loginUserID);
	// String result = "";
	// try{
	// result = databusconvert.execute("Info", "markHisReaded", param);
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// result = "{RESULT:\"-1\",info:\"" + e.getMessage() + "\"}";
	// logger.error(e.getMessage(),e);
	// }
	// JsonObject jo = JsonObject.fromObject(result);
	// printToView(jo.toString());
    }

    /**
     * queryhistoryMsgByPage 分页查询历史收件夹，通过分页参数定义可以不同的查询结果数 现在是为历史信件列表
     */
    public void queryhistoryMsgByPage() throws Exception {
	//
	// int page = 1; // 列表分页显示 - 设置时当前的页码
	// int rows = 10; // 列表分页显示 - 设置每页显示的记录数
	// try{
	// rows = Integer.valueOf(request.getParameter("rows"));
	// page = Integer.valueOf(request.getParameter("page"));
	// }catch(Exception e){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// page = 1; // 列表分页显示 - 设置时当前的页码
	// rows = 10;
	// }
	// Pagination pagination = new Pagination(page, rows); //通过调用业务方法，获取分页数据
	// JsonObject param = new JsonObject(); //查询参数IDATA
	//
	// param.put("RECEIVER",loginUserID); //获得收件人，通过收件人（登录人员）获得本登录人员收件箱
	// //param.put("MSGTYPE",5);//初始化加载默认的收件箱，邮件类型为私人信件
	// try{
	// param.put("PAGE", pagination);
	// //bindDataGridSource("Info", "queryHisRcvMsgByPage", param, page,
	// rows);
	// this.formatResultData("Info", "queryHisRcvMsgByPage", param);
	// }catch(Exception e){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(),e);
	// }
    }

    /**
     * deleteHisMsg 根据ID删除历史收件箱
     * 
     */

    public String deleteHisMsg() {
	// // 根据ID删除历史收件信息
	// String ids = request.getParameter("id");
	// String[] msgids = StringUtils.split(ids, ",");
	// List<String> msglist = new ArrayList<String>();
	// for(int i = 0; i < msgids.length; i++) {
	// msglist.add(msgids[i]);
	// }
	// JsonObject param = new JsonObject();
	// param.put("LIST", msglist);
	// param.put("RECEIVER",this.loginUserID); // 收件人 目前写死 登录写好后改正
	// String result = "";
	// try{
	// result = databusconvert.execute("Info", "deleteHisMsg", param);
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// }
	// String msgresult = setMessage(result, "INFOMANGE", "DELETE"); //
	// 将新增是否成功的结果进行消息状态组合便于返回给页面展示
	// printToView(msgresult);
	return null;
    }

    /**
     * queryHisSendMsgByPage 分页查询历史发件箱内容，通过分页参数定义可以不同的查询结果数
     */

    public void queryHisSendMsgByPage() throws Exception {
	// int page = 1; // 列表分页显示 - 设置时当前的页码
	// int rows = 10; // 列表分页显示 - 设置每页显示的记录数
	// try{
	// rows = Integer.valueOf(request.getParameter("rows"));
	// page = Integer.valueOf(request.getParameter("page"));
	// }catch(Exception e){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// page = 1; // 列表分页显示 - 设置时当前的页码
	// rows = 10;
	// }
	// Pagination pagination = new Pagination(page, rows); //通过调用业务方法，获取分页数据
	// JsonObject param = new JsonObject(); //查询参数IDATA
	// param.put("SENDER",loginUserID);//获得当前登录人员，发送邮件列表
	// try{
	// param.put("PAGE",pagination);
	// //bindDataGridSource("Info", "queryHisSendMsgByPage", param, page,
	// rows);
	// this.formatResultData("Info", "queryHisSendMsgByPage", param);
	// }catch(Exception e){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(),e);
	// }
    }

    /**
     * // 删除历史发件信息
     * @return
     */

    public String deleteHisSendMsg() {
	//
	// String ids = request.getParameter("id");
	// String[] msgids = StringUtils.split(ids, ",");
	// List<String> msglist = new ArrayList<String>();
	// for(int i = 0; i < msgids.length; i++) {
	// msglist.add(msgids[i]);
	// }
	// JsonObject param = new JsonObject();
	// param.put("LIST", msglist);
	// param.put("sender",this.loginUserID); // 收件人 目前写死 登录写好后改正
	// String result = "";
	// try{
	// result = databusconvert.execute("Info", "deleteHisSendMsg", param);
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// }
	// String msgresult = setMessage(result, "INFOMANGE", "DELETE"); //
	// 将新增是否成功的结果进行消息状态组合便于返回给页面展示
	// printToView(msgresult);
	return null;
    }

    /**
     * 
     * @Title: getHisSendMail
     * @Description: 查找历史发送邮件详细信息
     * @return
     */
    public void getHisSendMsg() {
	// // 查找发件详细信息
	// String id = request.getParameter("sendid");
	// IData data = new DataMap();
	// data.put("SENDID", id);
	// String result = "";
	// String cacheID = request.getParameter("cacheID");
	// try{
	// result = databusconvert.execute("Info", "getHisSendMsg",
	// JsonObject.fromObject(data));
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// result = "{RESULT:\"-1\",info:\"" + e.getMessage() + "\"}";
	// logger.error(e.getMessage(),e);
	// }
	// JsonObject jo = JsonObject.fromObject(result);
	// if(cacheID != null){
	// this.printFormatData(cacheID, jo.toString());
	// } else {
	// printToView(jo.toString());
	// }
    }

    /**
     * delAllHisSendMsg 根据发件人清空历史发件箱内容 并返回清空是否成功标志
     */

    public String delAllHisSendMsg() {
	// // 清空历史发件信息
	// IData data = new DataMap();
	// data.put("SENDER", this.loginUserID);
	// //String datas = JSONUtil.iDataToJson(data);
	// String result = "";
	// try{
	// result = databusconvert.execute("Info", "delAllHisSendMsg",
	// JsonObject.fromObject(data));
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// }
	// String msgresult = setMessage(result, "INFOMANGE", "DELETE"); //
	// 将清空是否成功的结果进行消息状态组合便于返回给页面展示
	// printToView(msgresult);
	return null;
    }

    /**
     * delAllSendMsg 根据发件人清空发件箱内容 并返回清空是否成功标志
     */

    public String delAllSendMsg() {
	// // 根据发件人清空发件箱内容
	// IData data = new DataMap();
	// data.put("SENDER", this.loginUserID);
	// //String datas = JSONUtil.iDataToJson(data);
	// String result = "";
	// try{
	// result = databusconvert.execute("Info", "delAllSendMsg",
	// JsonObject.fromObject(data));
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// }
	// String msgresult = setMessage(result, "INFOMANGE", "DELETE"); //
	// 将清空是否成功的结果进行消息状态组合便于返回给页面展示
	// printToView(msgresult);
	return null;
    }

    /**
     * delAllHisMsg 删除所有历史收件箱 并返回是否成功标志
     */

    public String delAllHisMsg() {
	// // 清空历史收件信息
	// IData data = new DataMap();
	// data.put("RECEIVER", this.loginUserID);
	// //String datas = JSONUtil.iDataToJson(data);
	// String result = "";
	// try{
	// result = databusconvert.execute("Info", "delAllHisMsg",
	// JsonObject.fromObject(data));
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// }
	// String msgresult = setMessage(result, "INFOMANGE", "DELETE"); //
	// 将清空是否成功的结果进行消息状态组合便于返回给页面展示
	// printToView(msgresult);
	return null;
    }

    /**
     * delAllInternalMsg 根据收件人和收件类型清空收件箱内容 此处清空默认收件箱 并返回清空是否成功标志
     */

    public String delAllInternalMsg() {
	// // 清空发件信息
	// IData param = new DataMap(); //查询参数IDATA
	// param.put("RECEIVER",loginUserID); //获得收件人，通过收件人（登录人员）获得本登录人员收件箱
	// param.put("MSGTYPE1",5);//初始化加载默认的收件箱，不包含私人信件
	// param.put("MSGTYPE2",1);//初始化加载默认的收件箱，不包含重要信件
	//
	// //String datas = JSONUtil.iDataToJson(param);
	// String result = "";
	// try{
	// result = databusconvert.execute("Info", "delAllInternalMsg",
	// JsonObject.fromObject(param));
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// }
	// String msgresult = setMessage(result, "INFOMANGE", "DELETE"); //
	// 将清空是否成功的结果进行消息状态组合便于返回给页面展示
	// printToView(msgresult);
	return null;
    }

    /**
     * delAllownerMsg 根据收件人和收件类型清空收件箱内容 此处清空私人收件箱 并返回清空是否成功标志
     */

    public String delAllownerMsg() {
	// // 清空发件信息
	// IData param = new DataMap(); //查询参数IDATA
	// param.put("RECEIVER",loginUserID); //获得收件人，通过收件人（登录人员）获得本登录人员收件箱
	// param.put("MSGTYPE",5);//私人信件类型
	//
	// //String datas = JSONUtil.iDataToJson(param);
	// String result = "";
	// try{
	// result = databusconvert.execute("Info", "delAllownerMsg",
	// JsonObject.fromObject(param));
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// }
	// String msgresult = setMessage(result, "INFOMANGE", "DELETE"); //
	// 将清空是否成功的结果进行消息状态组合便于返回给页面展示
	// printToView(msgresult);
	return null;
    }

    /**
     * delAllwarnMsg 根据收件人和收件类型清空收件箱内容 此处清空重要收件箱 并返回清空是否成功标志
     */

    public String delAllwarnMsg() {
	// // 清空发件信息
	// IData param = new DataMap(); //查询参数IDATA
	// param.put("RECEIVER",loginUserID); //获得收件人，通过收件人（登录人员）获得本登录人员收件箱
	// param.put("MSGTYPE",1);//重要信件类型
	//
	// //String datas = JSONUtil.iDataToJson(param);
	// String result = "";
	// try{
	// result = databusconvert.execute("Info", "delAllwarnMsg",
	// JsonObject.fromObject(param));
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// }
	// String msgresult = setMessage(result, "INFOMANGE", "DELETE"); //
	// 将清空是否成功的结果进行消息状态组合便于返回给页面展示
	// printToView(msgresult);
	return null;
    }

    /**
     * @title 以下为2011-7-11修改原型后修改
     * @return
     */

    /**
     * @title 检查写件箱信息是否正确
     * @return
     */
    public void checkMsg() {
	// // subject :String 消息标题
	// // * content :String 消息内容
	// // * receiverName :String 收件发送人
	// // * validateCode：String 验证码
	// // * 检验上述填写内容是否正确
	// //
	// {"msgtype":"5","subject":"123123","receiver":"123","content":"123","isSave":"1","sender":"1"}
	// String subject = request.getParameter("subject");
	// String content = request.getParameter("content");
	// String receiverName = request.getParameter("receiverName");
	// String validateCode = request.getParameter("validateCode");
	//
	// String result = "0";
	//
	// //验证码不正确
	// if(!session.getAttribute("loginValidateCode").equals(validateCode)){
	// result = "-4";
	// }else{
	// //标题
	// if(subject.length()>=50){result = "-1";
	// }else{
	// //
	// if(content.length()>=512){result = "-2";
	// }else{
	// //
	// if(subject.length()<=0){result = "-3";
	// }else{
	// //
	// if(content.length()<=0){result = "-5";
	// }else{
	// if(receiverName.length()<=0){result = "-6";
	// }else{result = "1";
	// }
	// }
	// }
	// }
	// }
	// }
	//
	// JsonObject msg = new JsonObject();
	// msg.put("msg", result);
	//
	// printToView(msg.toString());
    }

    /**
     * @title 获取数据字典内的消息类型
     * @return
     */
    public void gettype() throws Exception {
	// StringBuilder result = new StringBuilder(); // 业务方法无参数的示例
	// String mailstatus=request.getParameter("mailstatus");
	// String msgtype = queryDictEntryByType_Integer("MSGCENTER_MSGTYPE");
	// // 从字典表中取得消息类型原始数据
	// // result.append("{");
	// // result.append("msgtype:");
	// // result.append(msgtype);
	// // result.append("}");
	// //JsonArray jsonObject = JsonArray.fromArray(msgtype);
	// // Object[] o = jsonObject.toArray();
	// // List<String> msgtypelist = new ArrayList<String>();
	// // for (int i = 0; i < o.length; i++){
	// // JsonObject jo = JsonObject.fromObject(o[i]);
	// // String typevalue=(String) jo.get("value");
	// // if (!"".equals(typevalue)){
	// // msgtypelist.add(typevalue);
	// // }
	// // };
	// //JsonObject js = JsonObject.fromObject(result);
	// JsonArray jaa = JsonArray.fromArray(msgtype);
	// List<String> list = new ArrayList<String>();
	// for(Object ob : jaa.toArray()){
	// JsonObject jss = JsonObject.fromObject(ob);
	// if(jss.get("value") !=null && !"".equals(jss.get("value"))){
	// list.add(jss.getString("value"));
	// }
	// }
	// JsonObject param = new JsonObject();
	// param.put("LIST", list);
	// param.put("RECEIVER",this.loginUserID);
	// String msgtypeStr="";
	// try{
	// if(mailstatus.trim().equals("1")){
	// msgtypeStr = databusconvert.execute("Info", "queryNewCount", param);
	// }else{
	// msgtypeStr = databusconvert.execute("Info",
	// "queryhistoryNewCount",param);
	// }
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// }
	// JsonObject job = JsonObject.fromObject(msgtypeStr);
	//
	// StringBuilder Result = new StringBuilder();
	// Result.append("{");
	// Result.append("msg:[");
	// Object[] o = JsonArray.fromArray(msgtype).toArray();
	// for (int i = 0; i < o.length; i++){
	// JsonObject jo = JsonObject.fromObject(o[i]);
	// String typevalue=(String) jo.get("value");
	// String typetxt=(String) jo.get("text");
	// if (!"".equals(typevalue)){
	// Result.append("{text:\"");
	// Result.append(typetxt);
	// Result.append("\",value:\"");
	// Result.append(typevalue);
	// Result.append("\",count:\"");
	// Result.append(job.get(typevalue)==null?0:job.get(typevalue));
	// if(i<o.length-1){
	// Result.append("\"},");
	// }
	// else
	// {
	// Result.append("\"}");
	// }
	// }
	// }
	// Result.append("]}");
	// printToView(Result.toString());
    }

    /**
     * getMailList 分页查询收件夹，通过分页参数定义可以不同的查询结果数
     * 根据maildatatype(收件1或发件2)，mailtype(数据字典里的邮件类型
     * )，mailstatus(邮件是最近邮件或历史邮件)查询消息列表
     * 
     */
    public void getMailList() throws Exception {
	// int page = 1; // 列表分页显示 - 设置时当前的页码
	// int rows = 10; // 列表分页显示 - 设置每页显示的记录数
	// try{
	// rows = Integer.valueOf(request.getParameter("rows"));
	// page = Integer.valueOf(request.getParameter("page"));
	// }catch(Exception e){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// rows = 1;
	// page = 10;
	// }
	// Pagination pagination = new Pagination(page, rows);
	// JsonObject param = new JsonObject();
	// String maildatatype=request.getParameter("maildatatype");
	// String mailstatus=request.getParameter("mailstatus");
	// if (maildatatype.trim().equals("1")){
	// if(mailstatus.trim().equals("1")){
	// param.put("RECEIVER",loginUserID); //获得收件人，通过收件人（登录人员）获得本登录人员收件箱
	// param.put("MSGTYPE",request.getParameter("mailtype"));//获取消息类型
	// try{
	// param.put("PAGE", pagination);
	// //bindDataGridSource("Info", "queryownerMsgByPage", param, page,
	// rows);
	// this.formatResultData("Info", "queryownerMsgByPage", param);
	// }catch(Exception e){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(),e);
	// }
	// }else{
	// param.put("RECEIVER",loginUserID); //获得收件人，通过收件人（登录人员）获得本登录人员收件箱
	// param.put("MSGTYPE",request.getParameter("mailtype"));//获取消息类型
	// try{
	// param.put("PAGE", pagination);
	// // bindDataGridSource("Info", "queryHisRcvMsgByPage", param, page,
	// rows);
	// this.formatResultData("Info", "queryHisRcvMsgByPage", param);
	// }catch(Exception e){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(),e);
	// }
	// }
	// }else{
	// if(mailstatus.trim().equals("1")){
	// param.put("SENDER",loginUserID);//获得当前登录人员，发送邮件列表
	// param.put("MSGTYPE",request.getParameter("mailtype"));//获取消息类型
	// try{
	// param.put("PAGE", pagination);
	// //bindDataGridSource("Info", "querySendMsgByPage", param, page,
	// rows);
	// this.formatResultData("Info", "querySendMsgByPage", param);
	// }catch(Exception e){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(),e);
	// }
	// }else{
	// param.put("SENDER",loginUserID);//获得当前登录人员，发送邮件列表
	// param.put("MSGTYPE",request.getParameter("mailtype"));//获取消息类型
	// try{
	// param.put("PAGE", pagination);
	// //bindDataGridSource("Info", "queryHisSendMsgByPage", param, page,
	// rows);
	// this.formatResultData("Info", "queryHisSendMsgByPage", param);
	// }catch(Exception e){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(),e);
	// }
	// }
	// }
    }

    /**
     * newdeleteMsg
     * 
     * 根据maildatatype(收件1或发件2)，mailtype(数据字典里的邮件类型)，mailstatus(邮件是最近邮件或历史邮件)
     * 分类删除列表 根据ID主键删除
     */
    public String newdeleteMsg() {
	// // 删除收件信息
	// String result = "";
	// String maildatatype=request.getParameter("maildatatype");
	// String mailstatus=request.getParameter("mailstatus");
	// if (maildatatype.trim().equals("1")){
	// String ids = request.getParameter("id");
	// String[] msgids = StringUtils.split(ids, ",");
	// List<String> msglist = new ArrayList<String>();
	// for(int i = 0; i < msgids.length; i++) {
	// msglist.add(msgids[i]);
	// }
	// JsonObject param = new JsonObject();
	// param.put("LIST", msglist);
	// param.put("RECEIVER",this.loginUserID); // 收件人 目前写死 登录写好后改正
	// param.put("MSGTYPE",request.getParameter("mailtype")); // 收件人 目前写死
	// 登录写好后改正
	// if(mailstatus.trim().equals("1")){
	// try{
	// result = databusconvert.execute("Info", "deleteMsg", param);
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// return null;
	// }
	// }else{
	// try{
	// result = databusconvert.execute("Info", "deleteHisMsg", param);
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// return null;
	// }
	// }
	// }else{
	// String ids = request.getParameter("id");
	// String[] msgids = StringUtils.split(ids, ",");
	// List<String> msglist = new ArrayList<String>();
	// for(int i = 0; i < msgids.length; i++) {
	// msglist.add(msgids[i]);
	// }
	// JsonObject param = new JsonObject();
	// param.put("LIST", msglist);
	// //param.put("RECEIVER",this.loginUserID); // 收件人 目前写死 登录写好后改正
	//
	// if(mailstatus.trim().equals("1")){
	// try{
	// result = databusconvert.execute("Info", "deleteSendMsg", param);
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// return null;
	// }
	// }else{
	// try{
	// result = databusconvert.execute("Info", "deleteHisSendMsg", param);
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// return null;
	// }
	// }
	// }
	// String msgresult = setMessage(result, "INFOMANGE", "DELETE"); //
	// 将新增是否成功的结果进行消息状态组合便于返回给页面展示
	// printToView(msgresult);
	return null;
    }

    /**
     * clearMsg
     * 
     * 根据maildatatype(收件1或发件2)，mailtype(数据字典里的邮件类型)，mailstatus(邮件是最近邮件或历史邮件)
     * 分类删除列表 根据msgtype清空
     */
    public String clearMsg() {
	// String result = "";
	// String maildatatype=request.getParameter("maildatatype");
	// String mailstatus=request.getParameter("mailstatus");
	// JsonObject param = new JsonObject();//参数IDATA
	// if (maildatatype.trim().equals("1")){
	// // 清空发件信息
	// param.put("RECEIVER",loginUserID); //获得收件人，通过收件人（登录人员）获得本登录人员收件箱
	// param.put("MSGTYPE",request.getParameter("mailtype"));//信件类型
	// //String datas = JSONUtil.iDataToJson(param);
	// if(mailstatus.trim().equals("1")){
	// try{
	// result = databusconvert.execute("Info", "delAllwarnMsg", param);
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// return null;
	// }
	// }else{
	// // 清空历史收件信息
	// try{
	// result = databusconvert.execute("Info", "delAllHisMsg", param);
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// return null;
	// }
	// }
	// }else{
	// JsonObject data = new JsonObject();
	// data.put("SENDER", this.loginUserID);
	// //String datas = JSONUtil.iDataToJson(data);
	// if(mailstatus.trim().equals("1")){
	// try{
	// result = databusconvert.execute("Info", "delAllSendMsg", data);
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// return null;
	// }
	// }else{
	// try{
	// result = databusconvert.execute("Info", "delAllHisSendMsg", data);
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// return null;
	// }
	// }
	// }
	// String msgresult = setMessage(result, "INFOMANGE", "DELETE"); //
	// 将清空是否成功的结果进行消息状态组合便于返回给页面展示
	// printToView(msgresult);
	return null;
    }

    /**
     * 
     * @Title: getUser
     * @Description: 查找用户信息
     * @return
     */
    public void getUser() {
	// 查找用户信息
	// JsonObject data = new JsonObject();
	// data.put("EMPID", this.loginUserID);
	// //String datas = JSONUtil.iDataToJson(data);
	// String result = "";
	// try{
	// result = databusconvert.execute("User", "getEmpByEmpId", data);
	// if(result!=null)
	// {
	// JsonObject jo = JsonObject.fromObject(result);
	// jo.put("oldorgid", jo.get("orgid").toString());
	// jo.put("oldposition", jo.get("position").toString());
	// String operatorID =
	// jo.get("operatorid")==null?"":jo.get("operatorid").toString();
	// IData idata = new DataMap();
	// idata.put("OPERATORID", operatorID);
	// String operatorData = databusconvert.execute("User",
	// "getOperatorByOperatorId", JsonObject.fromObject(idata));
	// JsonObject jo2 = JsonObject.fromObject(operatorData);
	// jo.put("status", jo2.get("status"));
	// result = jo.toString();
	// }
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// result = "{RESULT:\"-1\",info:\"" + e.getMessage() + "\"}";
	// logger.error(e.getMessage(),e);
	// }
	// printToView(result);
    }

    /**
     * newgetMail
     * 
     * 根据maildatatype(收件1或发件2)，mailtype(数据字典里的邮件类型)，mailstatus(邮件是最近邮件或历史邮件)
     * 分类获取单一详细记录并返回上一个ID和下个ID 根据ID查找详细资料及返回上一个ID和下个ID
     */
    public void newgetMail() {
	// String maildatatype=request.getParameter("maildatatype");
	// String mailstatus=request.getParameter("mailstatus");
	// String result = "";
	// JsonObject job ;
	// String cacheID = request.getParameter("cacheID");
	// if (maildatatype.trim().equals("1")){
	// // 查找收件信息
	// String id = request.getParameter("receiveid");
	// JsonObject data = new JsonObject();
	// data.put("RECEIVEID", id);
	// data.put("MSGTYPE",request.getParameter("msgtype"));//信件类型
	// data.put("RECEIVER",this.loginUserID);
	// //String datas = JSONUtil.iDataToJson(data);
	// try{
	// if(mailstatus.trim().equals("1")){
	// result = databusconvert.execute("Info", "getNextMsgInfo", data);
	// }else{
	// result = databusconvert.execute("Info", "getNextHisMsgInfo", data);
	// }
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// result = "{RESULT:\"-1\",info:\"" + e.getMessage() + "\"}";
	// logger.error(e.getMessage(),e);
	// }
	// job = JsonObject.fromObject(result);
	// String NEXTID=job.get("NEXTID")==null?"":job.getString("NEXTID");
	// String LASTID=job.get("LASTID")==null?"":job.getString("LASTID");
	// job=job.getJsonObject("MSGINFO");
	// job.put("NEXTID", NEXTID);
	// job.put("LASTID", LASTID);
	// if(cacheID != null) {
	// this.printFormatData(cacheID, job.toString());
	// } else {
	// printToView(job.toString());
	// }
	// }else{
	// // 查找发件信息
	// String id = request.getParameter("sendid");
	// JsonObject data = new JsonObject();
	// data.put("SENDID", id);
	// //String datas = JSONUtil.iDataToJson(data);
	// try{
	// if(mailstatus.trim().equals("1")){
	// result = databusconvert.execute("Info", "getNextSendMsg", data);
	// }else{
	// result = databusconvert.execute("Info", "getNextHisSendMsg", data);
	// }
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// result = "{RESULT:\"-1\",info:\"" + e.getMessage() + "\"}";
	// logger.error(e.getMessage(),e);
	// }
	// job = JsonObject.fromObject(result);
	// String NEXTID=job.get("NEXTID")==null?"":job.getString("NEXTID");
	// String LASTID=job.get("LASTID")==null?"":job.getString("LASTID");
	// job=job.getJsonObject("SENDMSGINFO");
	// job.put("NEXTID", NEXTID);
	// job.put("LASTID", LASTID);
	// if(cacheID != null) {
	// this.printFormatData(cacheID, job.toString());
	// } else {
	// printToView(job.toString());
	// }
	// }
    }

    /**
     * mail_delAndgetMail
     * 
     * 根据maildatatype(收件1或发件2)，mailtype(数据字典里的邮件类型)，mailstatus(邮件是最近邮件或历史邮件)
     * 分类删除单一消息 根据ID删除详细资料并返回下一条（如果下条不存在则上一条）消息详细资料
     */
    public void delAndgetMail() {
	// String maildatatype=request.getParameter("maildatatype");
	// String mailstatus=request.getParameter("mailstatus");
	// String result = "";
	// JsonObject job ;
	// String cacheID = request.getParameter("cacheID");
	// if (maildatatype.trim().equals("1")){
	// String ids = request.getParameter("receiveid");
	// String[] msgids = StringUtils.split(ids, ",");
	// List<String> msglist = new ArrayList<String>();
	// for(int i = 0; i < msgids.length; i++) {
	// msglist.add(msgids[i]);
	// }
	// JsonObject param = new JsonObject();
	// param.put("LIST", msglist);
	// param.put("RECEIVER",this.loginUserID); // 收件人 目前写死 登录写好后改正
	// param.put("MSGTYPE",request.getParameter("mailtype"));
	//
	// if(request.getParameter("nextreceiveid")==null||"".equals(request.getParameter("nextreceiveid").trim())){
	// try{
	// if(mailstatus.trim().equals("1")){
	// result = databusconvert.execute("Info", "deleteMsg",param);
	// }else{
	// result = databusconvert.execute("Info", "deleteHisMsg", param);
	// }
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// }
	// job = JsonObject.fromObject(result);
	// job.put("RESULT", "");
	// printToView(job.toString());
	// }else
	// {
	// JsonObject newdata = new JsonObject();
	// newdata.put("RECEIVEID", request.getParameter("nextreceiveid"));
	// newdata.put("MSGTYPE",request.getParameter("msgtype"));//信件类型
	// newdata.put("RECEIVER",this.loginUserID);
	// //String datas = JSONUtil.iDataToJson(newdata);
	// try{
	// if(mailstatus.trim().equals("1")){
	// result = databusconvert.execute("Info", "deleteMsg", param);
	// result = databusconvert.execute("Info", "getNextMsgInfo", newdata);
	// }else{
	// result = databusconvert.execute("Info", "deleteHisMsg", param);
	// result = databusconvert.execute("Info", "getNextHisMsgInfo",
	// newdata);
	// }
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// }
	// job = JsonObject.fromObject(result);
	// String NEXTID=job.get("NEXTID")==null?"":job.getString("NEXTID");
	// String LASTID=job.get("LASTID")==null?"":job.getString("LASTID");
	// job=job.getJsonObject("MSGINFO");
	// job.put("NEXTID", NEXTID);
	// job.put("LASTID", LASTID);
	// if(cacheID != null){
	// this.printFormatData(cacheID, job.toString());
	// } else {
	// printToView(job.toString());
	// }
	// }
	//
	// }else{
	// String ids = request.getParameter("sendid");
	// String[] msgids = StringUtils.split(ids, ",");
	// List<String> msglist = new ArrayList<String>();
	// for(int i = 0; i < msgids.length; i++) {
	// msglist.add(msgids[i]);
	// }
	// JsonObject param = new JsonObject();
	// param.put("LIST", msglist);
	// //param.put("RECEIVER",this.loginUserID); // 收件人 目前写死 登录写好后改正
	//
	//
	// if(request.getParameter("nextsendid")==null||"".equals(request.getParameter("nextsendid").trim())){
	// try{
	// if(mailstatus.trim().equals("1")){
	// result = databusconvert.execute("Info", "deleteSendMsg", param); ;
	// }else{
	// result = databusconvert.execute("Info", "deleteHisSendMsg", param);
	// }
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// }
	// }else{
	// String id = request.getParameter("nextsendid");
	// JsonObject data = new JsonObject();
	// data.put("SENDID", id);
	// //String datas = JSONUtil.iDataToJson(data);
	// try{
	// if(mailstatus.trim().equals("1")){
	// result = databusconvert.execute("Info", "deleteSendMsg", param);
	// result = databusconvert.execute("Info", "getNextSendMsg", data);
	// }else{
	// result = databusconvert.execute("Info", "deleteHisSendMsg", param);
	// result = databusconvert.execute("Info", "getNextHisSendMsg", data);
	// }
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// }
	// job = JsonObject.fromObject(result);
	// String NEXTID=job.get("NEXTID")==null?"":job.getString("NEXTID");
	// String LASTID=job.get("LASTID")==null?"":job.getString("LASTID");
	// job=job.getJsonObject("SENDMSGINFO");
	// job.put("NEXTID", NEXTID);
	// job.put("LASTID", LASTID);
	// if(cacheID != null){
	// this.printFormatData(cacheID, job.toString());
	// } else {
	// printToView(job.toString());
	// }
	// }
	// }
    }

    /**
     * mail_delAndgetMail
     * 
     * 根据maildatatype(收件1或发件2)，mailtype(数据字典里的邮件类型)，mailstatus(邮件是最近邮件或历史邮件)
     * 分类删除单一消息 根据ID删除详细资料并返回下一条（如果下条不存在则上一条）消息详细资料
     */
    public void delMail() {
	// String maildatatype=request.getParameter("maildatatype");
	// String mailstatus=request.getParameter("mailstatus");
	// String result = "";
	// JsonObject job ;
	// String cacheID = request.getParameter("cacheID");
	// if (maildatatype.trim().equals("1")){
	// String ids = request.getParameter("receiveid");
	// String[] msgids = StringUtils.split(ids, ",");
	// List<String> msglist = new ArrayList<String>();
	// for(int i = 0; i < msgids.length; i++) {
	// msglist.add(msgids[i]);
	// }
	// JsonObject param = new JsonObject();
	// param.put("LIST", msglist);
	// param.put("RECEIVER",this.loginUserID); // 收件人 目前写死 登录写好后改正
	// param.put("MSGTYPE",request.getParameter("mailtype"));
	//
	// //if(request.getParameter("nextreceiveid")==null||"".equals(request.getParameter("nextreceiveid").trim())){
	// try{
	// if(mailstatus.trim().equals("1")){
	// result = databusconvert.execute("Info", "deleteMsg",param);
	// }else{
	// result = databusconvert.execute("Info", "deleteHisMsg", param);
	// }
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// }
	// job = JsonObject.fromObject(result);
	// if("1".equals(job.getString("RESULT"))) {
	// job.put("msg", "消息删除成功");
	// } else {
	// job.put("msg", "消息删除失败");
	// }
	// result = job.toString();
	// // }else
	// // {
	// // JsonObject newdata = new JsonObject();
	// // newdata.put("RECEIVEID", request.getParameter("nextreceiveid"));
	// // newdata.put("MSGTYPE",request.getParameter("msgtype"));//信件类型
	// // newdata.put("RECEIVER",this.loginUserID);
	// // //String datas = JSONUtil.iDataToJson(newdata);
	// // try{
	// // if(mailstatus.trim().equals("1")){
	// // result = databusconvert.execute("Info", "deleteMsg", param);
	// // result = databusconvert.execute("Info", "getNextMsgInfo",
	// newdata);
	// // }else{
	// // result = databusconvert.execute("Info", "deleteHisMsg", param);
	// // result = databusconvert.execute("Info", "getNextHisMsgInfo",
	// newdata);
	// // }
	// // }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// // addStateMessage("",e.getMessage());
	// // logger.error(e.getMessage(),e);
	// // }
	// // job = JsonObject.fromObject(result);
	// // String NEXTID=job.get("NEXTID")==null?"":job.getString("NEXTID");
	// // String LASTID=job.get("LASTID")==null?"":job.getString("LASTID");
	// // job=job.getJsonObject("MSGINFO");
	// // job.put("NEXTID", NEXTID);
	// // job.put("LASTID", LASTID);
	// // if(cacheID != null){
	// // this.printFormatData(cacheID, job.toString());
	// // } else {
	// // printToView(job.toString());
	// // }
	// // }
	//
	// }else{
	// String ids = request.getParameter("sendid");
	// String[] msgids = StringUtils.split(ids, ",");
	// List<String> msglist = new ArrayList<String>();
	// for(int i = 0; i < msgids.length; i++) {
	// msglist.add(msgids[i]);
	// }
	// JsonObject param = new JsonObject();
	// param.put("LIST", msglist);
	// param.put("RECEIVER",this.loginUserID); // 收件人 目前写死 登录写好后改正
	//
	//
	// //if(request.getParameter("nextsendid")==null||"".equals(request.getParameter("nextsendid").trim())){
	// try{
	// if(mailstatus.trim().equals("1")){
	// result = databusconvert.execute("Info", "deleteSendMsg", param); ;
	// }else{
	// result = databusconvert.execute("Info", "deleteHisSendMsg", param);
	// }
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// }
	// job = JsonObject.fromObject(result);
	// if("1".equals(job.getString("RESULT"))) {
	// job.put("msg", "消息删除成功");
	// } else {
	// job.put("msg", "消息删除失败");
	// }
	// result = job.toString();
	// // }else{
	// // String id = request.getParameter("nextsendid");
	// // JsonObject data = new JsonObject();
	// // data.put("SENDID", id);
	// // //String datas = JSONUtil.iDataToJson(data);
	// // try{
	// // if(mailstatus.trim().equals("1")){
	// // result = databusconvert.execute("Info", "deleteSendMsg", param);
	// // result = databusconvert.execute("Info", "getNextSendMsg", data);
	// // }else{
	// // result = databusconvert.execute("Info", "deleteHisSendMsg",
	// param);
	// // result = databusconvert.execute("Info", "getNextHisSendMsg",
	// data);
	// // }
	// // }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// // addStateMessage("",e.getMessage());
	// // logger.error(e.getMessage(),e);
	// // }
	// // job = JsonObject.fromObject(result);
	// // String NEXTID=job.get("NEXTID")==null?"":job.getString("NEXTID");
	// // String LASTID=job.get("LASTID")==null?"":job.getString("LASTID");
	// // job=job.getJsonObject("SENDMSGINFO");
	// // job.put("NEXTID", NEXTID);
	// // job.put("LASTID", LASTID);
	// // if(cacheID != null){
	// // this.printFormatData(cacheID, job.toString());
	// // } else {
	// // printToView(job.toString());
	// // }
	// // }
	// }
	// this.printToView(result);
    }

    /**
     * getCountAll 当前用户站内未读信息数
     * @return the getCountAll
     * @since
     */

    public String getCountAll() {
	// IData data = new DataMap();
	// data.put("RECEIVER", this.loginUserID);
	// //String datas = JSONUtil.iDataToJson(data);
	// String result = "";
	// try{
	// result = databusconvert.execute("Info", "getCountAll",
	// JsonObject.fromObject(data));
	// JsonObject jo = JsonObject.fromObject(result);
	// result = jo.getString("NEWCOUNT");
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// }
	// IData msg = new DataMap();
	// msg.put("result", result);
	// printToView(msg.toString());
	// return msg.toString();
	return null;
    }
}
