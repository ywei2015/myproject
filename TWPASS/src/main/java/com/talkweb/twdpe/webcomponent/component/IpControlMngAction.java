package com.talkweb.twdpe.webcomponent.component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import com.talkweb.twdpe.core.log.Logger;
import com.talkweb.twdpe.core.log.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.talkweb.twdpe.base.ipcontrol.pojo.IpControl;
import com.talkweb.twdpe.base.ipcontrol.service.IpControlService;
import com.talkweb.twdpe.base.rule.service.RuleManagerService;
import com.talkweb.twdpe.core.data.DataMap;
import com.talkweb.twdpe.core.data.IData;
import com.talkweb.twdpe.dal.jdbc.info.Pagination;
import com.talkweb.twdpe.web.session.main.Session;
import com.talkweb.twdpe.web.session.util.SessionFactory;
import com.talkweb.twdpe.webcomponent.common.action.ComponentActionSupport;

/**
 * 文件名称:    IpControlMngAction.java
 * 内容摘要: 
 * @author:   zhangwen 
 * @version:  1.0  
 * @Date:     2011-5-24 下午07:04:43  
 * 
 * 修改历史:  
 * 修改日期       修改人员   版本	   修改内容  
 * ----------------------------------------------  
 * 2011-5-24    zhangwen     1.0    1.0 XXXX
 *
 * 版权:   版权所有(C)2011
 * 公司:   拓维信息系统股份有限公司
 */
public class IpControlMngAction extends ComponentActionSupport {
    private static final Logger logger = LoggerFactory.getLogger(IpControlMngAction.class);

    private IpControl ipControl = null;

    public IpControl getIpControl() {
	return ipControl;
    }

    public void setIpControl(IpControl ipControl) {
	this.ipControl = ipControl;
    }

    @Autowired
    private IpControlService ipControlService;

    private RuleManagerService ruleManagerService;

    public RuleManagerService getRuleManagerService() {
	return ruleManagerService;
    }

    public void setRuleManagerService(RuleManagerService ruleManagerService) {
	this.ruleManagerService = ruleManagerService;
    }

    public IpControlService getIpControlService() {
	return ipControlService;
    }

    public void setIpControlService(IpControlService ipControlService) {
	this.ipControlService = ipControlService;
    }

    /**
     * 
     * @Title: addIpControl
     * @Description: 新增访问控制信息
     * @return
     */
    public String addIpControl() {

	try {
	    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    ipControl.setCreatetime(sf.format(new Date()));
	    HttpServletRequest request = ServletActionContext.getRequest();
	    HttpServletResponse response = ServletActionContext.getResponse();
	    Session session = SessionFactory.getInstance(request, response);
	    Map<String, Object> userMap = ((Map<String, Object>) session.getAttribute("USERSESSION"));
	    // 当前登录用户的登录ID编号
	    String loginUserID = userMap.get("id").toString();
	    ipControl.setCreateuser(loginUserID);// 创建人暂时是固定一个ID值，后面需要改成调用当前登录用户的ID值
	    IData res = ipControlService.addIpControl(ipControl);
	    this.setMessage(Integer.parseInt(res.getString("RESULT")), "IPCONTROL", "ADD");
	} catch (Exception e2) {
	    ServletActionContext.getRequest().setAttribute("throw", e2);
	    this.setMessage(0, "IPCONTROL", "ADD");
	    logger.error(e2.getMessage(), e2);
	}
	if ("2".equals(ipControl.getControltype())) {
	    // addSystemLog(LogConfigParam.MODULE_IP_ID,LogConfigParam.MODULE_IP_NAME,LogConfigParam.OPERATOR_TYPE_ADD,
	    // jsonToString(JsonObject.toString(ipControl)),
	    // this.pageData.get("msg").toString());
	} else {
	    // addSystemLog(LogConfigParam.MODULE_REJECT_IP_ID,LogConfigParam.MODULE_REJECT_IP_NAME,LogConfigParam.OPERATOR_TYPE_ADD,
	    // jsonToString(JsonObject.toString(ipControl)),
	    // this.pageData.get("msg").toString());
	}
	ipControl = null;

	return SUCCESS;
    }

    /**
     * 
     * @Title: modifyIpControl
     * @Description: 修改访问控制信息
     * @return
     */
    public String modifyIpControl() {
	try {
	    IData res = ipControlService.modifyIpControl(ipControl);
	    this.setMessage(Integer.parseInt(res.getString("RESULT")), "IPCONTROL", "MODIFY");
	} catch (Exception e2) {
	    ServletActionContext.getRequest().setAttribute("throw", e2);
	    this.setMessage(0, "IPCONTROL", "MODIFY");
	    logger.error(e2.getMessage(), e2);
	}
	if ("2".equals(ipControl.getControltype())) {
	    // addSystemLog(LogConfigParam.MODULE_IP_ID,LogConfigParam.MODULE_IP_NAME,LogConfigParam.OPERATOR_TYPE_MODIFY,
	    // jsonToString(JsonObject.toString(ipControl)),
	    // this.pageData.get("msg").toString());
	} else {
	    // addSystemLog(LogConfigParam.MODULE_REJECT_IP_ID,LogConfigParam.MODULE_REJECT_IP_NAME,LogConfigParam.OPERATOR_TYPE_MODIFY,
	    // jsonToString(JsonObject.toString(ipControl)),
	    // this.pageData.get("msg").toString());
	}
	ipControl = null;
	return SUCCESS;
    }

    /**
     * 
     * @Title: getIpControlByIpControlId
     * @Description: 根据id查询访问控制信息
     * @return
     */
    public String getIpControlByIpControlId() {
	HttpServletRequest request = ServletActionContext.getRequest();
	String id = request.getParameter("id");
	IData data = new DataMap();
	data.put("CONTROLID", id);
	try {
	    IpControl ip = ipControlService.getIpControlByIpControlId(data);
	    if (ip != null) {
		this.formatData(this.addPrefix(ip, "ipControl."));
	    }
	} catch (Exception e1) {
	    ServletActionContext.getRequest().setAttribute("throw", e1);
	    logger.error(e1.getMessage(), e1);
	}
	return SUCCESS;
    }

    /**
     * 
     * @Title: queryIpControlByPage
     * @Description: 分页查询访问控制
     * @return
     */
    public String queryIpControlByPage() {
	// 列表分页显示 - 设置每页显示的记录数
	HttpServletRequest request = ServletActionContext.getRequest();
	String type = request.getParameter("type");
	if (pagination == null) {
	    pagination = new Pagination(1, 10);
	}
	IData data = new DataMap();
	data.put("PAGE", pagination);
	data.put("CONTROLTYPE", type);
	try {
	    // this.bindDataGridSource("Ipcontrol", "queryIpControlByPage",
	    // data, page, rows);
	    IData res = ipControlService.queryIpControlByPage(data);
	    List list = null;
	    if (res.get("LIST") != null) {
		list = (List) res.get("LIST");
	    }
	    this.formatDatagirdData(list, pagination);
	    // this.formatResultData("Ipcontrol", "queryIpControlByPage", data);
	} catch (Exception e) {
	    ServletActionContext.getRequest().setAttribute("throw", e);
	    // TODO Auto-generated catch block
	    // e.printStackTrace();
	    logger.error(e.getMessage(), e);
	}
	return SUCCESS;
    }

    /**
     * 
     * @Title: deleteIpControl
     * @Description: 删除查询访问控制
     * @return
     */
    public String deleteIpControl() {
	HttpServletRequest request = ServletActionContext.getRequest();
	String idStr = request.getParameter("id");
	String[] ids = StringUtils.split(idStr, ",");
	String type = request.getParameter("type");
	List<String> list = new ArrayList<String>();
	for (int i = 0; i < ids.length; i++) {
	    if (ids[i].length() == 0)
		continue;
	    list.add(ids[i]);
	}

	try {
	    IData res = ipControlService.deleteIpControl(list);
	    this.setMessage(Integer.parseInt(res.getString("RESULT")), "IPCONTROL", "DELETE");
	} catch (Exception e2) {
	    ServletActionContext.getRequest().setAttribute("throw", e2);
	    this.setMessage(0, "IPCONTROL", "DELETE");
	    logger.error(e2.getMessage(), e2);
	}
	if ("2".equals(type)) {
	    // addSystemLog(LogConfigParam.MODULE_IP_ID,LogConfigParam.MODULE_IP_NAME,LogConfigParam.OPERATOR_TYPE_DELETE,
	    // jsonToString(JsonObject.toString(idStr)),
	    // this.pageData.get("msg").toString());
	} else {
	    // addSystemLog(LogConfigParam.MODULE_REJECT_IP_ID,LogConfigParam.MODULE_REJECT_IP_NAME,LogConfigParam.OPERATOR_TYPE_DELETE,
	    // jsonToString(JsonObject.toString(idStr)),
	    // this.pageData.get("msg").toString());
	}
	return SUCCESS;
    }

    // public String queryRulesByRuleType(){
    // HttpServletRequest request = ServletActionContext.getRequest();
    // String ruleType = request.getParameter("ruleType");
    //
    // ruleType = ruleType == null?"LOGINRULE":ruleType;
    // if(pagination == null){
    // pagination = new Pagination(1, 10);
    // }
    // IData data = new DataMap();
    // data.put("RULETYPE", ruleType);
    // data.put("PAGE", pagination);
    // String result = "{}";
    // try {
    // IData res = ruleManagerService.queryRuleInfoByPage(data);
    // List list = null;
    // if(res.get("LIST") != null){
    // list = (List)res.get("LIST");
    // }
    // this.formatDatagirdData(list, pagination);
    // //result = databusconvert.execute("Ipcontrol", "queryRulesByRuleType",
    // JsonObject.fromObject(data));
    // } catch (Exception e) {
//	    ServletActionContext.getRequest().setAttribute("throw", e);
    // // TODO Auto-generated catch block
    // //e.printStackTrace();
    // logger.error(e.getMessage(),e);
    // }
    //
    // return SUCCESS;
    // }
    //
    // public String modifyRuleValue(){
    // HttpServletRequest request = ServletActionContext.getRequest();
    // String data = request.getParameter("data");
    // List<IData> list = new ArrayList<IData>();
    // String[] ruleName =
    // {"ONSCHOOL","SCHOOLSTATUS","REGISTER","ONJOB","CERTIFICATE"};
    // JsonObject jo = JsonObject.fromObject(data);
    // for(String s:ruleName)
    // {
    // IData idata = new DataMap();
    // idata.put("RULECODE", s);
    // if(jo.get(s)==null)
    // {
    // idata.put("RULEVALUE", 1);
    // }else{
    // idata.put("RULEVALUE", jo.get(s));
    // }
    // list.add(idata);
    // }
    //
    // String result = "{}";
    // try {
    // ruleManagerService.modifyVRuleInfo(param);
    // result = databusconvert.execute("Ipcontrol", "modifyRuleValue",
    // JsonArray.fromArray(list));
    // } catch (Exception e1) {
//	    ServletActionContext.getRequest().setAttribute("throw", e1);
    // // TODO Auto-generated catch block
    // //e1.printStackTrace();
    // result = this.addStateMessage(result,e1.getMessage());
    // logger.error(e1.getMessage(),e1);
    // }
    // this.printToView(result);
    // }
    //
    // public void initRuleForm(){
    // String ruleType = request.getParameter("ruleType");
    //
    // ruleType = ruleType == null?"LOGINRULE":ruleType;
    //
    // IData data = new DataMap();
    // data.put("RULETYPE", ruleType);
    // String result = "{}";
    // try {
    // result = databusconvert.execute("Ipcontrol", "queryRulesByRuleType",
    // JsonObject.fromObject(data));
    // } catch (Exception e1) {
//	    ServletActionContext.getRequest().setAttribute("throw", e1);
    // // TODO Auto-generated catch block
    // //e1.printStackTrace();
    // logger.error(e1.getMessage(),e1);
    // }
    // JsonArray jarray = JsonArray.fromArray(result);
    // JsonObject njo = new JsonObject();
    // if(jarray!=null && jarray.toArray().length>0)
    // for(Object o:jarray.toArray())
    // {
    // JsonObject jo = JsonObject.fromObject(o);
    // int ruleValue = jo.getInt("rulevalue");
    // if(ruleValue==0)
    // {
    // njo.put(jo.get("rulecode").toString(), "0");
    // }
    // }
    // //this.printToView(njo.toString());
    // this.printToViewMethod(njo.toString());
    // }

}
