package com.talkweb.twdpe.webcomponent.component;

import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;

import com.talkweb.twdpe.webcomponent.common.action.ComponentActionSupport;

/**
 * 文件名称: RuleMngAction.java 内容摘要:
 * 
 * @author: 涂园园
 * @version: 1.0
 * @Date: 2011-7-26 上午10:26:02
 * 
 *        修改历史: 修改日期 修改人员 版本 修改内容 ----------------------------------------------
 *        2011-4-18 涂园园 1.0 1.0 XXXX
 * 
 *        版权: 版权所有(C)2011 公司: 拓维信息系统股份有限公司
 */

@SuppressWarnings("serial")
public class RuleMngAction extends ComponentActionSupport {
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(RuleMngAction.class);

    /**
     * 
     * @Title: addRuleInfo
     * @Description: 新增规则集
     */
    public void addRuleInfo() {
	// String data = request.getParameter("data");
	// try {
	// JsonObject jo = JsonObject.fromObject(data);
	// jo.put("CREATEUSER", loginUserID);
	// String result = databusconvert.execute("ruleMng", "addRuleInfo", jo);
	// String msgresult = setMessage(result, "RULEMNG", "ADD");
	// //addSystemLog(LogConfigParam.BUSI_MGR_ID,
	// LogConfigParam.BUSI_MGR_NAME, LogConfigParam.OPERATOR_TYPE_ADD,
	// jsonToString(jo
	// .toString()), this.getJSONPropertyValue(result, "msg"));
	// super.printToView(msgresult);
	// } catch (Exception ex) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(ex.getMessage(), ex);
	// }

    }

    /**
     * 
     * @Title: removeRuleInfo
     * @Description: 删除规则集
     */
    public void removeRuleInfo() {
	// String data = request.getParameter("id");
	// try {
	// //data="{ids:"+data+"}";
	// //JSONObject jo = JSONObject.fromObject(data);
	// //String idstr = jo.get("ids") == null ? "" : jo.getString("ids");
	// if (data.length() > 0) {
	// JsonArray jar=JsonArray.fromArray(Arrays.asList(data.split(",")));
	// String result = databusconvert.execute("ruleMng", "removeRuleInfo",
	// jar);
	// JsonObject jores=JsonObject.fromObject(result);
	// JsonArray jom = JsonArray.fromArray(jores.get("RESULT"));
	// int SuccessSum=0;
	// int fautSum=0;
	// for(int i=0;i<jom.size();i++){
	// String typevalue=(String) jom.get(i);
	// if ("1".equals(typevalue)){
	// SuccessSum+=1;
	// }else{
	// if ("0".equals(typevalue)){
	// fautSum+=1;
	// }
	// }
	// }
	// String
	// msgresult="您一共提交删除"+(SuccessSum+fautSum)+"条记录，有"+SuccessSum+"条成功，有"+fautSum+"条失败。";
	// //String msgresult = setMessage(result, "RULEMNG", "DELETE");
	// ////addSystemLog(LogConfigParam.BUSI_MGR_ID,
	// LogConfigParam.BUSI_MGR_NAME, LogConfigParam.OPERATOR_TYPE_DELETE,
	// jsonToString(jo
	// // .toString()), this.getJSONPropertyValue(result, "msg"));
	// JsonObject joRes = new JsonObject();
	// joRes.put("msgresult", msgresult);
	// super.printToView(joRes.toString());
	// }
	// } catch (Exception ex) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(ex.getMessage(), ex);
	// }
    }

    /**
     * 
     * @Title: modifyRuleInfo
     * @Description: 修改规则集
     */
    public void modifyRuleInfo() {
	// String data = request.getParameter("data");
	// try {
	// JsonObject jo = JsonObject.fromObject(data);
	// jo.put("CREATEUSER", loginUserID);
	// String result = databusconvert.execute("ruleMng", "modifyRuleInfo",
	// jo);
	// String msgresult = setMessage(result, "RULEMNG", "MODIFY");
	// //addSystemLog(LogConfigParam.BUSI_MGR_ID,
	// LogConfigParam.BUSI_MGR_NAME, LogConfigParam.OPERATOR_TYPE_MODIFY,
	// jsonToString(jo
	// .toString()), this.getJSONPropertyValue(result, "msg"));
	// super.printToView(msgresult);
	// } catch (Exception ex) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(ex.getMessage(), ex);
	// }
    }

    /**
     * 
     * @Title: queryRuleInfoByPage
     * @Description: 规则集分页查询
     */
    public void queryRuleInfoByPage() {
	// String data = request.getParameter("data");
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
	// JsonObject jso = new JsonObject();
	// try {
	// if (data != null) {
	// // data = java.net.URLDecoder.decode(data, "UTF-8"); // 对其解码
	// jso = JsonObject.fromObject(data); // 参数格式化成json格
	//
	// }
	// jso.put("PAGE", pagination);
	// bindDataGridSource("ruleMng", "queryRuleInfoByPage", jso, page,
	// rows);
	// } catch (Exception ex) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(ex.getMessage(), ex);
	// }
    }

    /**
     * 
     * @Title: getRuleInfoById
     * @Description: 获得规则集详情
     */
    public void getRuleInfoById() {
	// String data = request.getParameter("data");
	// try {
	// JsonObject jo = JsonObject.fromObject(data);//RULEID
	// String result = databusconvert.execute("ruleMng", "getRuleInfoById",
	// jo);
	// printToView(result);
	// } catch (Exception ex) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(ex.getMessage(), ex);
	// }
    }

    /**
     * 
     * @Title: initRuleInfoForm
     * @Description: 初始化表单下拉列表
     */
    public void initRuleInfoForm() {
	// StringBuilder result = new StringBuilder(); // 业务方法无参数的示例
	// String RDATATYPE = queryDictEntryByType_Integer("DIC_RULE_DATATPYE");
	// // 从字典表中取得元数据类型
	// String RULESTATE = queryDictEntryByType_Integer("DIC_RULE_STATUS");
	// String RULETYPE = queryDictEntryByType_Integer("DIC_RULE_RULETYPE");
	// String RULEID = "";
	//
	// try{
	// RULEID = databusconvert.execute("ruleMng", "queryAllRuleInfo",null);
	// }catch(Exception e){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// e.printStackTrace();
	// logger.error(e.getMessage(),e);
	// }
	//
	// StringBuilder applist = new StringBuilder();
	// applist.append("[{");
	// applist.append("\"text\":\"--所有--\",");
	// applist.append("\"value\":\"\"},{");
	// applist.append("\"text\":\"--公共--\",");
	// applist.append("\"value\":\"0\"},");
	// applist.append(StringUtils.stripStart(RULEID, "["));
	//
	// result.append("{");
	// result.append("RDATATYPE:");
	// result.append(RDATATYPE);
	// result.append(",");
	// result.append("RULESTATE:");
	// result.append(RULESTATE);
	// result.append(",");
	// result.append("RULETYPE:");
	// result.append(RULETYPE);
	// result.append(",");
	// result.append("RULEID:");
	// result.append(applist.toString());
	// result.append(",");
	// result.append("PRDATAID:");
	// result.append(RDATATYPE);
	// result.append("}");
	// printToView(result.toString());

    }

    /************************************ 历史规则处理 ****************************************************************/
    /**
     * 
     * @Title: queryVRuleInfoByPage
     * @Description: 历史规则集分页查询
     */
    public void queryVRuleInfoByPage() {
	// String data = request.getParameter("data");
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
	// JsonObject jso = new JsonObject();
	// try {
	// if (data != null) {
	// // data = java.net.URLDecoder.decode(data, "UTF-8"); // 对其解码
	// jso = JsonObject.fromObject(data); // 参数格式化成json格
	//
	// }
	// jso.put("PAGE", pagination);
	// bindDataGridSource("ruleMng", "queryVRuleInfoByPage", jso, page,
	// rows);
	// } catch (Exception ex) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(ex.getMessage(), ex);
	// }
    }

    /**
     * 
     * @Title: getVRuleInfoById
     * @Description: 根据ID查询历史规则
     */
    public void getVRuleInfoById() {
	// String rdataid=request.getParameter("RULEVESIONID");
	// JsonObject datas=new JsonObject();
	// datas.put("RULEVESIONID", rdataid);
	// try{
	// if(rdataid!=null){
	// String result=databusconvert.execute("ruleMng", "getVRuleInfoById",
	// datas);
	// String msgresult = setMessage(result, "RULEMNG", "GET");
	// //addSystemLog(LogConfigParam.BUSI_MGR_ID,
	// LogConfigParam.BUSI_MGR_NAME, LogConfigParam.OPERATOR_TYPE_OTHER,
	// jsonToString(datas
	// .toString()), this.getJSONPropertyValue(result, "msg"));
	// super.printToView(msgresult);
	// printToView(result);
	// }
	// }catch(Exception ex){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(ex.getMessage(),ex);
	// }
    }

    /**
     * 
     * @Title: modifyVRuleInfo
     * @Description: 修改历史规则集
     */
    public void modifyVRuleInfo() {
	// String data = request.getParameter("data");
	// try {
	// JsonObject jo = JsonObject.fromObject(data);
	// jo.put("CREATEUSER", loginUserID);
	// String result = databusconvert.execute("ruleMng", "modifyVRuleInfo",
	// jo);
	// String msgresult = setMessage(result, "RULEMNG", "MODIFY");
	// //addSystemLog(LogConfigParam.BUSI_MGR_ID,
	// LogConfigParam.BUSI_MGR_NAME, LogConfigParam.OPERATOR_TYPE_MODIFY,
	// jsonToString(jo
	// .toString()), this.getJSONPropertyValue(result, "msg"));
	// super.printToView(msgresult);
	// } catch (Exception ex) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(ex.getMessage(), ex);
	// }
    }

    /**
     * 
     * @Title: generateRuleFileInForm
     * @Description: 修改历史规则中生成规则文件
     */
    public void generateRuleFileInForm() {
	// try {
	// JsonObject jo = new JsonObject();
	// jo.put("RULEVESIONID", request.getParameter("RULEVESIONID"));
	// jo.put("RULENAME", request.getParameter("RULENAME"));
	// jo.put("RULETYPE", request.getParameter("RULETYPE"));
	// jo.put("RULEFILECONT", request.getParameter("RULEFILECONT"));
	// jo.put("RULEDESC", request.getParameter("RULEDESC"));
	// jo.put("CREATEUSER", loginUserID);
	// String result = databusconvert.execute("ruleMng",
	// "generateRuleFileInForm", jo);
	// String msgresult = setMessage(result, "RULEMNG", "GENERATE");
	// //addSystemLog(LogConfigParam.BUSI_MGR_ID,
	// LogConfigParam.BUSI_MGR_NAME, LogConfigParam.OPERATOR_TYPE_MODIFY,
	// jsonToString(jo
	// .toString()), this.getJSONPropertyValue(result, "msg"));
	// super.printToView(msgresult);
	// } catch (Exception ex) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(ex.getMessage(), ex);
	// }
    }

    /************************************ 元数据处理 ****************************************************************/
    /**
     * 
     * @Title: querymetadataInfoByPage
     * @Description: 规则集分页查询
     */
    public void querymetadataInfoByPage() {
	// String data = request.getParameter("data");
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
	// JsonObject jso = new JsonObject();
	// Pagination pagination = new Pagination(page, rows); //
	// 通过调用业务方法，获取分页数据
	// try {
	// if (data != null) {
	// // data = java.net.URLDecoder.decode(data, "UTF-8"); // 对其解码
	// jso = JsonObject.fromObject(data); // 参数格式化成json格
	// }
	// jso.put("PAGE", pagination);
	// bindDataGridSource("ruleMng", "queryRuleInfoByPage", jso.toString(),
	// page, rows);
	// } catch (Exception ex) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(ex.getMessage(), ex);
	// }
    }

    /**
     * 
     * @Title: addMeta
     * @Description: 新增元数据
     */
    public void addMeta() {
	// String data=request.getParameter("data");
	// JsonObject datas = JsonObject.fromObject(data);
	// try{
	// if(data!=null){
	// JsonObject parmjo =new JsonObject();
	// parmjo.put("rdataname",datas.getString("rdataname"));//添加的用户
	// parmjo.put("ruleid",datas.getString("RULEID"));
	// parmjo.put("rulename", datas.getString("rulename"));
	// parmjo.put("rdatatype", datas.getString("RDATATYPE"));
	// parmjo.put("rdatatypeName", datas.getString("rdatatypeName"));
	// parmjo.put("rdjavaname", datas.getString("rdjavaname"));
	// parmjo.put("rdjavatype", datas.getString("rdjavatype"));
	// parmjo.put("prdataid", datas.getString("PRDATAID"));
	// parmjo.put("createuser", this.loginUserID);
	// parmjo.put("createtime", DateUtil.getSysTime());
	//
	// RuleDataInfo ro=(RuleDataInfo)
	// JsonObject.toBean(parmjo,RuleDataInfo.class);
	// String result=databusconvert.execute("ruleMng", "addRuleOperInfo",
	// JsonObject.fromObject(ro));
	// String msgresult = setMessage(result, "RULEMNG", "ADD");
	// //addSystemLog(LogConfigParam.BUSI_MGR_ID,
	// LogConfigParam.BUSI_MGR_NAME, LogConfigParam.OPERATOR_TYPE_ADD,
	// jsonToString(data
	// .toString()), this.getJSONPropertyValue(result, "msg"));
	// super.printToView(msgresult);
	// printToView(result);
	// }
	// }catch(Exception ex){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(ex.getMessage(),ex);
	// }
    }

    /**
     * 
     * @Title: modifymeta
     * @Description: 修改元数据
     */
    public void modifymeta() {
	// String data=request.getParameter("data");
	// JsonObject datas = JsonObject.fromObject(data);
	// try{
	// if(data!=null){
	// JsonObject parmjo =new JsonObject();
	// parmjo.put("RDATAID",datas.getString("rdataid"));//添加的ID
	// parmjo.put("RDATANAME",datas.getString("rdataname"));
	// parmjo.put("RULEID",datas.getString("RULEID"));
	// parmjo.put("RDATATYPE", datas.getString("RDATATYPE"));
	// parmjo.put("RDJAVANAME", datas.getString("rdjavaname"));
	// parmjo.put("RDJAVATYPE", datas.getString("rdjavatype"));
	// parmjo.put("PRDADAID", datas.getString("PRDATAID"));
	//
	// RuleDataInfo ro=(RuleDataInfo)
	// JsonObject.toBean(parmjo,RuleDataInfo.class);
	// String result=databusconvert.execute("ruleMng", "modifyRuleDataInfo",
	// JsonObject.fromObject(ro));
	// String msgresult = setMessage(result, "RULEMNG", "MODIFY");
	// //addSystemLog(LogConfigParam.BUSI_MGR_ID,
	// LogConfigParam.BUSI_MGR_NAME, LogConfigParam.OPERATOR_TYPE_MODIFY,
	// jsonToString(data
	// .toString()), this.getJSONPropertyValue(result, "msg"));
	// super.printToView(msgresult);
	// printToView(result);
	// }
	// }catch(Exception ex){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(ex.getMessage(),ex);
	// }
    }

    /**
     * 
     * @Title: getmetadataInfo
     * @Description: 获取元数据详细
     */
    public void getmetadataInfo() {
	// String rdataid=request.getParameter("rdataid");
	// JsonObject datas=new JsonObject();
	// datas.put("RDATAID", rdataid);
	// try{
	// if(rdataid!=null){
	// String result=databusconvert.execute("ruleMng",
	// "getRuleDataInfoById", datas);
	// String msgresult = setMessage(result, "RULEMNG", "GET");
	// //addSystemLog(LogConfigParam.BUSI_MGR_ID,
	// LogConfigParam.BUSI_MGR_NAME, LogConfigParam.OPERATOR_TYPE_OTHER,
	// jsonToString(datas
	// .toString()), this.getJSONPropertyValue(result, "msg"));
	// super.printToView(msgresult);
	// printToView(result);
	// }
	// }catch(Exception ex){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(ex.getMessage(),ex);
	// }
    }

    /**
     * 
     * @Title: delmetadata
     * @Description: 批量删除元数据
     */
    public void delmetadata() {
	// String ids = request.getParameter("id");
	// String[] metaids = StringUtils.split(ids, ",");
	// List<String> metalist = new ArrayList<String>();
	// for(int i = 0; i <metaids.length; i++) {
	// metalist.add(metaids[i]);
	// }
	// IData param = new DataMap();
	// param.put("rDataIds", metalist);
	// String result = "";
	// try{
	// result = databusconvert.execute("ruleMng", "deleteRuleDataInfo",
	// JsonObject.fromObject(param));
	// String msgresult = setMessage(result, "RULEMNG", "DELETE");
	// //addSystemLog(LogConfigParam.BUSI_MGR_ID,
	// LogConfigParam.BUSI_MGR_NAME, LogConfigParam.OPERATOR_TYPE_DELETE,
	// jsonToString(param
	// .toString()), this.getJSONPropertyValue(result, "msg"));
	// printToView(msgresult);
	// printToView(result);
	// }catch (Exception e) {
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// addStateMessage("",e.getMessage());
	// logger.error(e.getMessage(),e);
	// }
    }

    /************************************ 规则处理 ****************************************************************/
    /**
     * 
     * @Title: initRuleTree
     * @Description: queryRuleOperInfo
     */
    public void initRuleTree() {
	// String data=request.getParameter("data");
	// data="{\"RULEVESIONID\":\"1\"}";
	// try{
	// JsonObject jo=JsonObject.fromObject(data);
	// String result=databusconvert.execute("ruleMng", "queryRuleOperInfo",
	// jo);
	// printToView(result);
	// }catch(Exception ex){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(ex.getMessage(),ex);
	// }
    }

    public void initMetaData() {
	// String opertype
	// ="[{\"text\":\"IF类型\",\"value\":\"1\"},{\"text\":\"FOR类型\",\"value\":\"2\"},{\"text\":\"普通类型\",\"value\":\"3\"}]";//只支持3种,写死
	// StringBuilder result = new StringBuilder();
	// result.append("{");
	// result.append("opertype:");
	// result.append(opertype)
	// .append("}");
	// super.printToView(result.toString());
    }

    /**
     * 
     * @Title: addMetaData
     * @Description: 新增规则元
     */
    public void addMetaData() {
	// String data=request.getParameter("data");
	// try{
	// if(data!=null){
	// JsonObject jo=JsonObject.fromObject(data);
	// RuleOperInfo ro=(RuleOperInfo)
	// JsonObject.toBean(jo,RuleOperInfo.class);
	// ro.setCreateuser(loginUserID);
	// String result=databusconvert.execute("ruleMng", "addRuleOperInfo",
	// JsonObject.fromObject(ro));
	// String msgresult = setMessage(result, "RULEBODY", "ADD");
	// //addSystemLog(LogConfigParam.BUSI_MGR_ID,
	// LogConfigParam.BUSI_MGR_NAME, LogConfigParam.OPERATOR_TYPE_ADD,
	// jsonToString(jo
	// .toString()), this.getJSONPropertyValue(result, "msg"));
	// super.printToView(msgresult);
	// //printToView(result);
	// }
	// }catch(Exception ex){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(ex.getMessage(),ex);
	// }
    }

    /**
     * 
     * @Title: deleteMetaData
     * @Description: 删除规则元
     */
    public void deleteMetaData() {
	// String data=request.getParameter("data");
	// try{
	// JsonObject jo=JsonObject.fromObject(data);
	// String result=databusconvert.execute("ruleMng", "deleteRuleOperInfo",
	// jo);
	// String msgresult = setMessage(result, "RULEBODY", "DELETE");
	// //addSystemLog(LogConfigParam.BUSI_MGR_ID,
	// LogConfigParam.BUSI_MGR_NAME, LogConfigParam.OPERATOR_TYPE_ADD,
	// jsonToString(jo
	// .toString()), this.getJSONPropertyValue(result, "msg"));
	// super.printToView(msgresult);
	// }catch(Exception ex){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(ex.getMessage(),ex);
	// }
    }

    /**
     * 
     * @Title: modifyMetaData
     * @Description: 修改规则元
     */
    public void modifyMetaData() {
	// String data=request.getParameter("data");
	// try{
	// // modifyRuleOperInfo
	// JsonObject jo=JsonObject.fromObject(data);
	// String result=databusconvert.execute("ruleMng", "modifyRuleOperInfo",
	// jo);
	// String msgresult = setMessage(result, "RULEBODY", "MODIFY");
	// //addSystemLog(LogConfigParam.BUSI_MGR_ID,
	// LogConfigParam.BUSI_MGR_NAME, LogConfigParam.OPERATOR_TYPE_ADD,
	// jsonToString(jo
	// .toString()), this.getJSONPropertyValue(result, "msg"));
	// super.printToView(msgresult);
	// }catch(Exception ex){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(ex.getMessage(),ex);
	// }
    }

    public void getMetaData() {
	// String data=request.getParameter("data");
	// try{
	// JsonObject jo=JsonObject.fromObject(data);//getRuleOperInfoById
	// String result=databusconvert.execute("ruleMng",
	// "getRuleOperInfoById", jo);
	// printToView(result);
	// }catch(Exception ex){
	    //ServletActionContext.getRequest().setAttribute("throw", e);
	// logger.error(ex.getMessage(),ex);
	// }
    }

    /**
     * 
     * @Title: getMetaDataBody
     * @Description: 获得规则体
     */
    public void getMetaDataBody() {
	// String data=request.getParameter("data");
    }

    /**
     * 
     * @Title: updateMetaDataBody
     * @Description: 修改规则体
     */
    public void updateMetaDataBody() {
	// String data=request.getParameter("data");
    }

    public void getPopMenu() {
	// String
	// str="[{id:\"1\",name:\"操作符\",pid:\"0\"},{id:\"2\",name:\"方法\",pid:\"0\"},{id:\"3\",name:\"变量\",pid:\"0\"},{id:\"4\",name:\"+\",pid:\"1\",link:\"javascript:setParm('4','+')\"},{id:\"5\",name:\"-\",pid:\"1\",link:\"javascript:setParm('5','-')\"},{id:\"8\",name:\"*\",pid:\"1\",link:\"javascript:setParm('8','*')\"},{id:\"6\",name:\"订购产品数量\",pid:\"3\",link:\"javascript:setParm('6','订购产品数量')\"},{id:\"7\",name:\"订购产品单价\",pid:\"3\",link:\"javascript:setParm('7','订购产品单价')\"}]";
	// printToView(str);

    }
}
