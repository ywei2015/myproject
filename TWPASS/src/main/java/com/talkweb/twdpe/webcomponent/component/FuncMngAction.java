package com.talkweb.twdpe.webcomponent.component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.base.right.constant.RightConstants;
import com.talkweb.twdpe.base.right.pojo.Resource;
import com.talkweb.twdpe.base.right.service.ResourceService;
import com.talkweb.twdpe.core.util.json.JsonArray;
import com.talkweb.twdpe.dal.exception.DalException;
import com.talkweb.twdpe.web.action.BasicAction;
import com.talkweb.twdpe.web.jcontrols.RuleNavigation;

/**
 * 文件名称: FuncMngAction.java 内容摘要:
 * 
 * @author: 李锋、姜玲
 * @version: 1.0
 * @Date: 2011-4-13 下午03:52:26
 * 
 * 修改历史: 修改日期 修改人员 版本 修改内容 ----------------------------------------------
 * 2011-4-13 李锋 1.0 1.0 增加方法：queryAllApp
 * 
 * 版权: 版权所有(C)2011 公司: 拓维信息系统股份有限公司
 */
public class FuncMngAction extends BasicAction {
    private static final Logger logger = LoggerFactory.getLogger(FuncMngAction.class);

    /**
     * 显示功能组树
     * 
     * @Title: queryRootFuncGroupByAppID
     * @Description: TODO(显示功能组树)
     */
    public void queryAllFuncGroupByAppID() {
	// String id = request.getParameter("appid");
	//
	// JsonObject datas = new JsonObject();
	// datas.put("APPID", id);
	// String result = "{}";
	// try {
	// result = databusconvert.execute("Func", "queryAllFuncGroupByAppID",
	// datas);
	// } catch (Exception e) {
//	    ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// logger.error(e.getMessage(), e);
	// }
	// String cacheID = request.getParameter("cacheID");
	// if (cacheID != null) {
	//
	// // logger.debug(id);
	// // logger.debug(result.toString());
	// if (result.length() > 5) {
	// this.printFormatData(cacheID, result);
	// } else {
	// this.printFormatData(cacheID, "[]");
	// }
	//
	// } else {
	// JsonArray newjay = new JsonArray();
	// JsonArray jay = JsonArray.fromArray(result);
	// if(jay!=null && jay.size()>0)
	// for(Object o:jay.toArray())
	// {
	// JsonObject jo = JsonObject.fromObject(o);
	// jo.element("text", jo.get("val"));
	// jo.element("parentid", jo.get("pid"));
	// jo.remove("val");
	// jo.remove("pid");
	// newjay.add(jo);
	// }
	// if (result.length() > 5) {
	// printToViewMethod(newjay.toString());
	// } else {
	// printToView("");
	// }
	// }

    }

    public void queryAllFuncGroupByAppID2() {
	//
	// String id = request.getParameter("appid");
	//
	// IData datas = new DataMap();
	// datas.put("APPID", id);
	// String result = "{}";
	// try {
	// result = databusconvert.execute("Func", "queryAllFuncGroupByAppID",
	// JsonObject.fromObject(datas));
	// } catch (Exception e) {
//	    ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// logger.error(e.getMessage(), e);
	// }
	// // logger.debug(id);
	// // logger.debug(result.toString());
	// String cacheID = request.getParameter("cacheID");
	// if (cacheID != null) {
	//
	// if (result.length() > 5) {
	// StringBuilder datastr = new StringBuilder();
	// datastr.append("{parentgroup:{data:");
	// datastr.append(result);
	// datastr.append("}}");
	// this.printFormatData(cacheID, datastr.toString());
	// } else {
	// printToView("");
	// }
	// } else {
	// if (result.length() > 5) {
	// printToViewMethod(result.toString());
	// } else {
	// printToView("");
	// }
	// }
    }

    /**
     * 根据id得到初始化的添加表单初始值
     * 
     * @Title: queryRootFuncGroupByAppID
     * @Description: TODO(根据父id得到初始化的添加表单初始值)
     */
    public void initAddfuncGroup() {
	// String id = request.getParameter("id");
	// String target = request.getParameter("target");
	// String result = "{}";
	// if (id != null && Integer.parseInt(id) > 0) {
	//
	// JsonObject json = new JsonObject();
	// json.put("FUNCGROUPID", id);
	// try {
	// result = databusconvert.execute("Func",
	// "getAddFuncGroupByFuncGroupID", json);
	// } catch (Exception e) {
//	    ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// logger.error(e.getMessage(), e);
	// }
	// }
	// JsonObject jo = JsonObject.fromObject(result);
	// if (target == null || target.length() <= 0) {
	// if (id == null || id.equals("0"))
	// jo.element("target", "mainframe");
	// else
	// jo.element("target",
	// target == null || target.length() <= 0 ? "rightframe"
	// : target);
	// result = jo.toString();
	// }
	// printToView(result);

    }

    /**
     * 根据id得到初始化的编辑表单初始值
     * 
     * @Title: queryRootFuncGroupByAppID
     * @Description: TODO(根据id得到初始化的编辑表单初始值 )
     */
    public void initEditfuncGroup() {
	// String id = request.getParameter("id");
	// if (id != null && !id.equals("0")) {
	// IData idata = new DataMap();
	// idata.put("FUNCGROUPID", id);
	// String result = "{}";
	// try {
	// result = databusconvert.execute("Func",
	// "getEditFuncGroupByFuncGroupID", JsonObject
	// .fromObject(idata));
	// } catch (Exception e) {
//	    ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// logger.error(e.getMessage(), e);
	// result = "{}";
	// }
	// result = result == null || result.equals("null") ? "{}" : result;
	// JsonObject jsonObject = JsonObject.fromObject(result);
	// // printToView(result);
	//
	// logger.debug(result);
	//
	// idata.put("FUNCGROUPID", jsonObject.get("parentgroup"));
	// String result1 = "{}";
	// try {
	// result1 = databusconvert.execute("Func",
	// "getEditFuncGroupByFuncGroupID", JsonObject
	// .fromObject(idata));
	// } catch (Exception e) {
//	    ServletActionContext.getRequest().setAttribute("throw", e);
//	  TODO Auto-generated catch block
	// // e.printStackTrace();
	// logger.error(e.getMessage(), e);
	// result1 = "{}";
	// }
	// JsonObject jsonObject1 = JsonObject.fromObject(result1);
	// if (result1 == null || result1.equals("null")) {
	// jsonObject.put("upfuncgroupname", "无");
	// } else {
	// jsonObject.put("upfuncgroupname", jsonObject1
	// .get("funcgroupname"));
	// }
	// jsonObject.put("isonline", jsonObject.getString("isonline"));
	// printToViewMethod(jsonObject.toString());
	// } else {
	// printToView("{}");
	// }
    }

    // 暂时不用
    private String getChildNode(JsonArray jsonArray) {
	// StringBuilder str = new StringBuilder();
	// for (int i = 0; i < jsonArray.toArray().length; i++) {
	// JsonObject jsonObject = jsonArray.getJsonObject(i);
	// JsonObject json = new JsonObject();
	// json.put("funcgroupid", jsonObject.get("id"));
	// String rs = "{}";
	// try {
	// rs = databusconvert.execute("Func",
	// "querySubFuncGroupByFuncGroupID", json);
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// logger.error(e.getMessage(), e);
	// }
	// if (rs.length() > 2) {
	// str.append(",");
	// }
	// str.append(rs.substring(1, rs.length() - 1));
	// JsonArray tempArray = JsonArray.fromArray(rs);
	// if (tempArray.toArray().length >= 1) {
	// str.append(getChildNode(tempArray));
	// }
	// }
	// return str.toString();
	return null;
    }

    /**
     * 添加功能组
     * 
     * @Title: queryRootFuncGroupByAppID
     * @Description: TODO(添加功能组 )
     */
    public void addFuncGroup() {
	// String data = request.getParameter("data");
	// JsonObject jsonObject = JsonObject.fromObject(data);
	// FuncGroupInfo oinfo = (FuncGroupInfo) JsonObject.toBean(jsonObject,
	// FuncGroupInfo.class);
	// String result = "{}";
	// try {
	// result = databusconvert.execute("Func", "addFuncGroup", JsonObject
	// .fromObject(oinfo));
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// result = this.addStateMessage(result, e.getMessage());
	// logger.error(e.getMessage(), e);
	// }
	// result = this.setMessage(result, "FUNCGROUP", "ADD");
	// //addSystemLog(LogConfigParam.SYS_MGR_ID,
	// LogConfigParam.SYS_MGR_NAME,
	// LogConfigParam.OPERATOR_TYPE_ADD, jsonToString(jsonObject
	// .toString()), this.getJSONPropertyValue(result, "msg"));
	// printToView(result);
    }

    /**
     * 编辑功能组
     * 
     * @Title: queryRootFuncGroupByAppID
     * @Description: TODO(编辑功能组 )
     */
    public void modifyFuncGroup() {
	// String data = request.getParameter("data");
	// JsonObject jsonObject = JsonObject.fromObject(data);
	// String parentgroup = jsonObject.getString("parentgroup");
	// if(parentgroup == null || "".equals(parentgroup)){
	// jsonObject.put("parentgroup", 0);
	// }
	// jsonObject.put("isdelete", "0");
	// String result = "{}";
	// try {
	// result = databusconvert.execute("Func", "modifyFuncGroup",
	// jsonObject);
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// result = this.addStateMessage(result, e.getMessage());
	// logger.error(e.getMessage(), e);
	// }
	// result = this.setMessage(result, "FUNCGROUP", "MODIFY");
	// //addSystemLog(LogConfigParam.SYS_MGR_ID,
	// LogConfigParam.SYS_MGR_NAME,
	// LogConfigParam.OPERATOR_TYPE_MODIFY, jsonToString(jsonObject
	// .toString()), this.getJSONPropertyValue(result, "msg"));
	// printToView(result);
    }

    /**
     * 删除功能组
     * 
     * @Title: queryRootFuncGroupByAppID
     * @Description: TODO(删除功能组 )
     */
    public void removeFuncGroup() {

	// String id = request.getParameter("id");
	// List list = new ArrayList();
	// list.add(id);
	// String result = "{}";
	// JsonArray jsonStr = JsonArray.fromArray(list);
	// try {
	// result = databusconvert.execute("Func", "removeFuncGroup", jsonStr);
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// result = this.addStateMessage(result, e.getMessage());
	// logger.error(e.getMessage(), e);
	// }
	// result = this.setMessage(result, "FUNCGROUP", "DELETE");
	// //addSystemLog(LogConfigParam.SYS_MGR_ID,
	// LogConfigParam.SYS_MGR_NAME,
	// LogConfigParam.OPERATOR_TYPE_DELETE, jsonToString(jsonStr
	// .toString()), this.getJSONPropertyValue(result, "msg"));
	// printToView(result);

    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 分页查询所有应用
     * 
     * @Title: queryAllApp
     * @Description: 通过分页参数定义可以不同的查询结果数
     * @throws Exception
     */
    public void queryAllApp() {
	// int requestRows = Integer.valueOf(request.getParameter("rows"));
	// int requestpage = Integer.valueOf(request.getParameter("page"));
	// Pagination pagination = new Pagination(requestpage, requestRows);
	//
	// String APPNAME = request.getParameter("APPNAME");
	// String apptype = request.getParameter("apptype");
	//
	// JsonObject json = new JsonObject();
	// json.put("PAGE", pagination);
	// json.put("APPNAME", APPNAME);
	// json.put("APPTYPE", apptype);
	// try {
	// formatResultData("Func", "queryAllApp", json);
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// logger.error(e.getMessage(), e);
	// }
    }

    /**
     * 浏览、新增或修改应用基本信息，初始化表单控件默认值
     * 
     * @Title: initFromControl
     * @Description: 初始化表单控件默认值
     * @throws IOException
     * @throws DalException
     */
    public void initFromControl() {
	// // 业务方法无参数的示例
	// StringBuilder result = new StringBuilder();
	//
	// // 取得应用类型列表中原始数据
	// String apptype = queryDictEntryByType("APPTYPE");
	// // 取得所有角色信息列表
	// String roles = "{}";
	// try {
	// roles = databusconvert.execute("Func", "queryAllRoles", null);
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// logger.error(e.getMessage(), e);
	// }
	// StringBuilder applist = new StringBuilder();
	// StringBuilder result2 = new StringBuilder();
	// applist.append("[{");
	// applist.append("\"text\":\"--请选择--\",");
	// applist.append("\"value\":\"\"},");
	// applist.append(StringUtils.stripStart(roles, "["));
	//
	// result.append("{");
	// result.append("apptype:");
	// result.append(apptype);
	// result.append(",");
	// result.append("manarole:");
	// result.append(applist.toString());
	// result.append(",");
	// result.append("isopen:");
	// result.append("\"2\"");
	// result.append("}");
	// String cacheID = request.getParameter("cacheID");
	// if (cacheID != null) {
	// result2.append("{");
	// result2.append("apptype:{data:");
	// result2.append(apptype);
	// result2.append("},");
	// result2.append("manarole:{data:");
	// result2.append(applist.toString());
	// result2.append("}}");
	// this.printFormatData(cacheID, result2.toString());
	// } else
	// printToView(result.toString());

    }

    /**
     * 增加应用信息
     * 
     * @Title: addAppInfo
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @return
     * @throws Exception
     */
    public String addAppInfo() {
	// // 增加地区信息
	// String data = request.getParameter("data");
	// AppInfo jsonObject = (AppInfo) JsonObject.toBean(JsonObject
	// .fromObject(data), AppInfo.class);
	//
	// SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// String d = sf.format(new Date());
	// jsonObject.setOpendate(d);
	// String result = "{}";
	// try {
	// result = databusconvert.execute("Func", "addApp", JsonObject
	// .fromObject(jsonObject));
	// // 操作日志
	// //addSystemLog(LogConfigParam.SYS_MGR_ID,
	// LogConfigParam.SYS_MGR_NAME,
	// LogConfigParam.OPERATOR_TYPE_ADD, jsonToString(JsonObject
	// .toString(jsonObject)), "应用添加成功");
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// logger.error(e.getMessage(), e);
	// }
	// String msgresult = setMessage(result, "APP", "ADD"); //
	// 将新增是否成功的结果进行消息状态组合便于返回给页面展示
	// printToView(msgresult);
	return null;
    }

    /**
     * 获取指定的应用基本信息
     * 
     * @Title: getAppInfo
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @return
     * @throws Exception
     */
    public void getAppInfo() {
	// // String op_status = request.getParameter("opstatus");
	// String appid = request.getParameter("appid");
	//
	// IData data = new DataMap();
	// data.put("APPID", appid);
	//
	// String result = "{}";
	// try {
	// result = databusconvert.execute("Func", "getAppInfo", JsonObject
	// .fromObject(data));
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// logger.error(e.getMessage(), e);
	// }
	// // AppInfo app = (AppInfo) JSONUtil.jsonToPojo(result,
	// AppInfo.class);
	//
	// printToViewMethod(result);
    }

    /**
     * 修改指定的应用基本信息
     * 
     * @Title: modifyAppInfo
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @return
     * @throws Exception
     */
    public String modifyAppInfo() {
	// String data = request.getParameter("data");
	// JsonObject jsonObject = JsonObject.fromObject(data);
	// IData appdata = new DataMap();
	// appdata.put("APPID", jsonObject.get("appid"));
	// String result = "{}";
	// try {
	// String appinfo = databusconvert.execute("Func", "getAppInfo",
	// JsonObject.fromObject(appdata));
	// JsonObject infodata = JsonObject.fromObject(appinfo);
	// infodata.putAll(jsonObject);
	// result = databusconvert.execute("Func", "modifyApp", infodata);
	// // 写操作日志
	// String jsonData = JsonObject.toString(jsonObject);
	// //addSystemLog(LogConfigParam.SYS_MGR_ID,
	// LogConfigParam.SYS_MGR_NAME,
	// LogConfigParam.OPERATOR_TYPE_MODIFY,
	// jsonToString(jsonData), "应用修改成功");
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// result = this.addStateMessage(result, e.getMessage());
	// logger.error(e.getMessage(), e);
	// }
	// String msgresult = setMessage(result, "APP", "MODIFY"); //
	// 将新增是否成功的结果进行消息状态组合便于返回给页面展示
	// printToView(msgresult);
	return null;
    }

    /**
     * 批量删除应用信息
     * 
     * @Title: delAppInfo
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @return
     * @throws Exception
     */
    public String delAppInfo() {

	// Json ids;
	// String id = request.getParameter("appid");
	// String[] appids = StringUtils.split(id, ",");
	// List<String> applist = new ArrayList<String>();
	// for (int i = 0; i < appids.length; i++) {
	// applist.add(appids[i]);
	// }
	// ids = JsonArray.fromArray(applist);
	// String result = "{}";
	// try {
	// result = databusconvert.execute("Func", "removeApps", ids);
	// //addSystemLog(LogConfigParam.SYS_MGR_ID,
	// LogConfigParam.SYS_MGR_NAME,
	// LogConfigParam.OPERATOR_TYPE_DELETE, JsonArray.fromArray(
	// applist).toString(), "应用批量删除成功");
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(), e);
	// }
	// String msgresult = setMessage(result, "APP", "DELETE"); //
	// 将新增是否成功的结果进行消息状态组合便于返回给页面展示
	// printToView(msgresult);
	//
	return null;

    }

    /**
     * 添加功能信息
     * 
     * @Title: addFunc
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public void addFunc() {

	// String data;
	// String result = "";
	// try {
	// data = request.getParameter("data");
	// JsonObject ob = JsonObject.fromObject(data);
	// if (Integer.valueOf((String) ob.get("funcgroupid")) <= 0)
	// result = "{RESULT:'3'}";
	// else {
	// FuncInfo jsonObject = (FuncInfo) JsonObject.toBean(JsonObject
	// .fromObject(data), FuncInfo.class);
	// result = databusconvert.execute("Func", "addFunc", JsonObject
	// .fromObject(jsonObject));
	// result = this.setMessage(result, "FUNC", "ADD");
	// }
	// } catch (Exception e2) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// // result = this.addStateMessage(result,e2.getMessage());
	// logger.error(e2.getMessage(), e2);
	// }
	// printToView(result);

    }

    /**
     * 修改功能信息
     * 
     * @Title: modifyFunc
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public void modifyFunc() {
	// String data = request.getParameter("data");
	// JsonObject p = JsonObject.fromObject(data);
	// String funcCode = p.getString("funccode");
	// IData idata = new DataMap();
	// idata.put("FUNCCODE", funcCode);
	// String funcDataStr = "{}";
	// try {
	// funcDataStr = databusconvert.execute("Func", "getFuncInfo",
	// JsonObject.fromObject(idata));
	// } catch (Exception e2) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e2.printStackTrace();
	// logger.error(e2.getMessage(), e2);
	// }
	// FuncInfo fi = (FuncInfo) JsonObject.toBean(JsonObject
	// .fromObject(funcDataStr), FuncInfo.class);
	// fi
	// .setFuncname(p.get("funcname") == null ? "" : p
	// .getString("funcname"));
	// fi.setFuncgroupid(p.get("funcgroupid") == null
	// || p.get("funcgroupid").toString().length() == 0 ? 0 : p
	// .getInt("funcgroupid"));
	// fi
	// .setFunctype(p.get("functype") == null ? "" : p
	// .getString("functype"));
	// fi.setUrl(p.get("url") == null ? "" : p.getString("url"));
	// String result = "";
	// if (fi.getFuncgroupid() <= 0)
	// result = "{RESULT:'2'}";
	// else
	// try {
	// result = databusconvert.execute("Func", "modifyFunc",
	// JsonObject.fromObject(fi));
	// } catch (Exception e2) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e2.printStackTrace();
	// result = this.addStateMessage(result, e2.getMessage());
	// logger.error(e2.getMessage(), e2);
	// }
	// result = this.setMessage(result, "FUNC", "MODIFY");
	// //addSystemLog(LogConfigParam.SYS_MGR_ID,
	// LogConfigParam.SYS_MGR_NAME,
	// LogConfigParam.OPERATOR_TYPE_MODIFY, jsonToString(JsonObject
	// .toString(fi)), this.getJSONPropertyValue(JsonObject
	// .toString(fi), "msg"));
	// this.printToView(result);
    }

    /**
     * 批量删除功能信息
     * 
     * @Title: delFunc
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public void delFunc() {
	// Json ids;
	// String id = request.getParameter("funccode");
	// String[] funccodes = StringUtils.split(id, ",");
	// List<String> funclist = new ArrayList<String>();
	// for (int i = 0; i < funccodes.length; i++) {
	// funclist.add(funccodes[i]);
	// }
	// ids = JsonArray.fromArray(funclist);
	// String result = "{}";
	// try {
	// result = databusconvert.execute("Func", "delFunc", ids);
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(e.getMessage(), e);
	// }
	// result = this.setMessage(result, "FUNC", "DELETE");
	// printToView(result);

    }

    /**
     * 取功能信息
     */
    public void getFunc() {
	// String funcCode = request.getParameter("funcCode");
	// if (funcCode != null && funcCode.length() > 0) {
	//
	// try {
	// funcCode = new String(funcCode.getBytes(), "UTF-8");
	// } catch (UnsupportedEncodingException e2) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e2.printStackTrace();
	// logger.error(e2.getMessage());
	// }
	//
	// IData data = new DataMap();
	// data.put("FUNCCODE", funcCode);
	// String result = "{}";
	// try {
	// result = databusconvert.execute("Func", "getFuncInfo",
	// JsonObject.fromObject(data));
	// } catch (Exception e1) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e1.printStackTrace();
	// logger.error(e1.getMessage(), e1);
	// }
	// printToViewMethod(result);
	// }
    }

    /**
     * 根据条件查询功能信息
     * 
     * @Title: delFunc
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public void searchAppFunc() {
	//
	// int requestRows = Integer.valueOf(request.getParameter("rows"));
	// int requestpage = Integer.valueOf(request.getParameter("page"));
	// Pagination pagination = new Pagination(requestpage, requestRows);
	// // 查询参数IDATA
	// String appID = request.getParameter("appID");
	// String funcgroupID = request.getParameter("funcmangefuncgroupid");
	// String funcname = request.getParameter("funcmangefuncname");
	//
	// funcname = funcname == null ? "" : funcname;
	// JsonObject idata = new JsonObject();
	// idata.put("FUNCGROUPID", funcgroupID);
	// idata.put("FUNCNAME", funcname);
	// idata.put("PAGE", pagination);
	// idata.put("APPID", appID);
	//
	// try {
	// this.formatResultData("Func", "searchAppFunc", idata);
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// logger.error(e.getMessage());
	// }

    }

    /**
     * 初始化功能表单
     * 
     * @Title: delFunc
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public void initFuncFromControl() {
	// StringBuilder result = new StringBuilder();
	// // 取得组织类型列表中原始数据
	// String functype = queryDictEntryByType("FUNTYPE");
	// String cacheID = request.getParameter("cacheID");
	// if (cacheID != null) {
	// result.append("{");
	// result.append("functype:{data:");
	// result.append(functype);
	// result.append("}}");
	// this.printFormatData(cacheID, result.toString());
	// } else
	// printToView(result.toString());

    }

    /**
     * 初始化功能修改表单
     * 
     * @Title: delFunc
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public void initFuncUpdate() {
	// StringBuilder result = new StringBuilder();
	//
	// // 取得组织类型列表中原始数据
	// String functype = queryDictEntryByType("FUNTYPE");
	// //获取所有功能
	// String appid = request.getParameter("appid");
	// IData datas = new DataMap();
	// datas.put("APPID", appid);
	// String results = "{}";
	// try {
	// results = databusconvert.execute("Func", "queryAllFuncGroupByAppID",
	// JsonObject.fromObject(datas));
	// } catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// logger.error(e.getMessage(), e);
	// }
	//
	// String cacheID = request.getParameter("cacheID");
	// if (cacheID != null) {
	// result.append("{");
	// result.append("functype:{data:");
	// result.append(functype);
	// result.append("},funcgroupid:{data:");
	// result.append(results);
	// result.append("}}");
	// this.printFormatData(cacheID, result.toString());
	// } else
	// printToView(result.toString());

    }

    /**
     * 显示应用树
     * 
     * @Title: showAPPTree
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public void showAPPTree() {
	// String roleID = request.getParameter("roleID");
	// Pagination pagination = new Pagination(1, 1000);
	// JsonObject data = new JsonObject();
	// data.put("PAGE", pagination);
	// data.put("ROLEID", roleID);
	// String results = "{}";
	// try {
	// results = databusconvert.execute("Role", "queryAppForTree", data);
	// } catch (Exception e1) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e1.printStackTrace();
	// logger.error(e1.getMessage(), e1);
	// }
	// String cacheID = request.getParameter("cacheID");
	// JsonObject jo = JsonObject.fromObject(results);
	// JsonArray jsonArray = (JsonArray)jo.get("LIST");
	// JsonArray newJsonArray = new JsonArray();
	// if (jsonArray != null && jsonArray.toArray().length > 0)
	// for (Object obj : jsonArray.toArray()) {
	// JsonObject json = JsonObject.fromObject(obj);
	// int id = json.getInt("value");
	// String text = json.getString("text");
	// json.put("id", "app_" + id);
	// if (cacheID != null) {
	// json.put("pid", "0");
	// json.put("val", text);
	// json.remove("text");
	// } else {
	// json.put("parentid", "0");
	// json.put("isleaf", "1");
	// }
	// int checkState = json.getInt("chk");
	// String checkStateName = "";
	// if(checkState==1)
	// checkStateName = "true";
	// else if(checkState==2)
	// checkStateName = "part";
	// else
	// checkStateName = "false";
	// json.put("chk", checkStateName);
	// json.put("asyn", json.get("asyn")!=null &&
	// json.getString("asyn").equals("true")?1:0);
	// json.remove("value");
	// newJsonArray.add(json);
	// }
	// if (cacheID != null)
	// this.printFormatData(cacheID, newJsonArray.toString());
	// else
	// printToView(newJsonArray.toString());
    }

    /**
     * 显示应用与功能树
     * @Title: showAPPTree
     * @Description: TODO(显示应用与功能树)
     */
    public void showAppFunctionTree() {
	// String roleID = request.getParameter("roleID");
	// JsonObject data = new JsonObject();
	// data.put("ROLEID", roleID);
	// String results = "{}";
	// try {
	// results = databusconvert.execute("Func", "queryAppForTree", data);
	// JsonArray newjay = new JsonArray();
	// JsonArray jay = JsonArray.fromArray(results);
	// if(jay!=null && jay.size()>0){
	// for(Object o:jay.toArray())
	// {
	// JsonObject jo = JsonObject.fromObject(o);
	// int functype = jo.getInt("FUNCTYPE");
	// if(functype==2||functype==3){
	// String parentid = jo.get("PARENTORIGID") == null ? "" :
	// jo.getString("PARENTORIGID");
	// String funcid = jo.get("ORIGID") == null ? "" :
	// jo.getString("ORIGID");
	// String appID = jo.getString("APPID");
	// String funcValue = functype + "|" + parentid + "|" + funcid + "|" +
	// appID;
	// jo.put("funcValue", funcValue);
	// jo.remove("PARENTORIGID");
	// }
	// jo.put("id", jo.get("NODEID"));
	// jo.put("pid", jo.get("PARENTNODEID"));
	// jo.put("val", jo.get("FUNCNAME"));
	// jo.remove("NODEID");
	// jo.remove("PARENTNODEID");
	// jo.remove("FUNCNAME");
	// int checkState = jo.getInt("CHECKSTATE");
	// String checkStateName = "";
	// if(checkState==1)
	// checkStateName = "true";
	// else if(checkState==2)
	// checkStateName = "part";
	// else
	// checkStateName = "false";
	// jo.put("chk", checkStateName);
	// jo.remove("CHECKSTATE");
	// jo.remove("ORIGID");
	// jo.remove("APPID");
	// jo.remove("PASS");
	// jo.remove("ISRIGHT");
	// jo.remove("FUNCTYPE");
	// newjay.add(jo);
	// }
	// results = newjay.toString();
	// }
	// } catch (Exception e1) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e1.printStackTrace();
	// logger.error(e1.getMessage(), e1);
	// }
	// this.printToViewMethod(results);
    }

    /**
     * 根据应用ID查找用户能访问的菜单列表
     */
    @SuppressWarnings("unchecked")
    public String queryFuncGroupByUserID() {
	HttpServletRequest request = ServletActionContext.getRequest();
	String APPID = request.getParameter("appid");
	Resource r = new Resource();
	r.setAppId(Long.parseLong(APPID));
	r.setResourceType(RightConstants.RESOURCE_TYPE_MENU);
	List<Resource> list = resourceService.queryResources(r);

	RuleNavigation ruleNavigation = new RuleNavigation();
	ruleNavigation.setId("resourceCode");
	ruleNavigation.setGroup("parentCode");
	ruleNavigation.setText("resourceName");
	ruleNavigation.setUrl("url");

	List<Map> menu = null;
	try {
	    menu = this.initNavigationData(list, ruleNavigation);
	    for (Map m : menu) {
		if (m.get("url") == null) {
		    m.put("url", "");
		    continue;
		}
		// 给URL增加工程上下文路径
		String url = request.getContextPath() + m.get("url").toString();
		m.put("url", url);
		// 添加target属性
		m.put("target", "rightframe");
	    }
	} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	this.formatData(menu);

	return SUCCESS;

    }

    /**
     * 根据用户ID、功能组ID、链接地址及应用ID查找用户能访问的功能列表
     */
    public void queryFunctionByUserID() {
	// String FUNCGROUPID = request.getParameter("funcgroupid");
	// String OPERATORID = request.getParameter("operatorid");
	// if (OPERATORID == null)
	// OPERATORID = this.loginUserID;
	// String URL = request.getParameter("url");
	// IData idata = new DataMap();
	// idata.put("FUNCGROUPID", FUNCGROUPID);
	// idata.put("OPERATORID", OPERATORID);
	// idata.put("URL", URL);
	// String result = "{}";
	// try {
	// result = databusconvert.execute("Func", "queryFunctionByUserID",
	// JsonObject.fromObject(idata));
	// } catch (Exception e) {
//	    ServletActionContext.getRequest().setAttribute("throw", e);
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// logger.error(e.getMessage(), e);
	// }
	// this.printToView(result);
    }

    @Autowired
    private ResourceService resourceService;

}
