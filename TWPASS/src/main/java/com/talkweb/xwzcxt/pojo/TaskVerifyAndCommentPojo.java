package com.talkweb.xwzcxt.pojo;


public class TaskVerifyAndCommentPojo extends TTask{

	private String cTaskName; // 任务名称
	private String typename;
	/*
	 * 任务状态： 11 已确认 22 已推送 33 已执行 34 已验证 35 已评价 44 被取消
	 */
	private int cStatus; // 任务状态
	private String cStatusName;
	private String cExecUsername; // 任务执行人姓名
	private String cExecUserInfo;
	private String cEndTime; // 任务完成时间
	private String cActnodeId; // 岗位活动ID
	private String cChkResult; // 任务验证结果 OK表示正常，NG开头表示异常
	private String cEvaluateResult; // 任务评价结果 OK表示正常，NG开头表示异常
	private String cTaskId;  //任务编号
    private String cChkUsername;
    private String cChkUserInfo;
    private String cFactEndtime;
    private String cTaskTypename;
    private String isExpired;
    private String cManageSectionName;
    private String cEvaluateUserInfo;
    private String c_std_verflag;
    private String c_ex_iemisevent;
    private String c_check_std;
    private String c_review_std;
    private String c_evaluate_desc;
    private String c_chk_desc;
    private String c_chk_result_name;
    private String c_evaluate_result_name;
    
    
	public String getC_chk_result_name() {
		return c_chk_result_name;
	}

	public void setC_chk_result_name(String c_chk_result_name) {
		this.c_chk_result_name = c_chk_result_name;
	}

	public String getC_evaluate_result_name() {
		return c_evaluate_result_name;
	}

	public void setC_evaluate_result_name(String c_evaluate_result_name) {
		this.c_evaluate_result_name = c_evaluate_result_name;
	}

	public String getC_evaluate_desc() {
		return c_evaluate_desc;
	}

	public void setC_evaluate_desc(String c_evaluate_desc) {
		this.c_evaluate_desc = c_evaluate_desc;
	}

	public String getC_chk_desc() {
		return c_chk_desc;
	}

	public void setC_chk_desc(String c_chk_desc) {
		this.c_chk_desc = c_chk_desc;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getC_check_std() {
		return c_check_std;
	}

	public void setC_check_std(String c_check_std) {
		this.c_check_std = c_check_std;
	}

	public String getC_review_std() {
		return c_review_std;
	}

	public void setC_review_std(String c_review_std) {
		this.c_review_std = c_review_std;
	}

	public String getcStatusName() {
		return cStatusName;
	}

	public void setcStatusName(String cStatusName) {
		this.cStatusName = cStatusName;
	}

	public String getcTaskName() {
		return cTaskName;
	}

	public void setcTaskName(String cTaskName) {
		this.cTaskName = cTaskName;
	}

	
	public int getcStatus() {
		
		return cStatus;
	}
	public void setcStatus(int cStatus) {
		this.cStatus = cStatus;
	}
 

	
	public String getcExecUsername() {
		return cExecUsername;
	}

	public void setcExecUsername(String cExecUsername) {
		this.cExecUsername = cExecUsername;
	}

	public String getcEndTime() {
		return cEndTime;
	}

	public void setcEndTime(String cEndTime) {
		this.cEndTime = cEndTime;
	}

	public String getcActnodeId() {
		return cActnodeId;
	}

	public void setcActnodeId(String cActnodeId) {
		this.cActnodeId = cActnodeId;
	}

	public String getcChkResult() {
		// if(cIserror.equals("0")){
		// return "正常";
		// }else if(cIserror.equals("1")){
		// return "异常";
		// }
		return cChkResult;
	}


	public void setcChkResult(String cChkResult) {
		this.cChkResult = cChkResult;
	}

	public String getcEvaluateResult() {
		return cEvaluateResult;
	}

	public void setcEvaluateResult(String cEvaluateResult) {
		this.cEvaluateResult = cEvaluateResult;
	}

	public String getcTaskId() {
		return cTaskId;
	}

	public void setcTaskId(String cTaskId) {
		this.cTaskId = cTaskId;
	}

	public String getcChkUsername() {
		return cChkUsername;
	}

	public void setcChkUsername(String cChkUsername) {
		this.cChkUsername = cChkUsername;
	}

	public String getcFactEndtime() {
		return cFactEndtime;
	}

	public void setcFactEndtime(String cFactEndtime) {
		this.cFactEndtime = cFactEndtime;
	}

	public String getcTaskTypename() {
		return cTaskTypename;
	}

	public void setcTaskTypename(String cTaskTypename) {
		this.cTaskTypename = cTaskTypename;
	}

	public String getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(String isExpired) {
		this.isExpired = isExpired;
	}

	public String getcManageSectionName() {
		return cManageSectionName;
	}

	public void setcManageSectionName(String cManageSectionName) {
		this.cManageSectionName = cManageSectionName;
	}

	public String getcExecUserInfo() {
		return cExecUserInfo;
	}

	public void setcExecUserInfo(String cExecUserInfo) {
		this.cExecUserInfo = cExecUserInfo;
	}

	public String getcChkUserInfo() {
		return cChkUserInfo;
	}

	public void setcChkUserInfo(String cChkUserInfo) {
		this.cChkUserInfo = cChkUserInfo;
	}

	public String getcEvaluateUserInfo() {
		return cEvaluateUserInfo;
	}

	public void setcEvaluateUserInfo(String cEvaluateUserInfo) {
		this.cEvaluateUserInfo = cEvaluateUserInfo;
	}

	public String getC_std_verflag() {
		return c_std_verflag;
	}

	public void setC_std_verflag(String c_std_verflag) {
		this.c_std_verflag = c_std_verflag;
	}

	public String getC_ex_iemisevent() {
		return c_ex_iemisevent;
	}

	public void setC_ex_iemisevent(String c_ex_iemisevent) {
		this.c_ex_iemisevent = c_ex_iemisevent;
	}

//***********************************************************

	public String getCStatusName() {
		return cStatusName;
	}

	public void setCStatusName(String cStatusName) {
		this.cStatusName = cStatusName;
	}

	public String getCTaskName() {
		return cTaskName;
	}

	public void setCTaskName(String cTaskName) {
		this.cTaskName = cTaskName;
	}

	
	public int getCStatus() {
		
		return cStatus;
	}
	public void setCStatus(int cStatus) {
		this.cStatus = cStatus;
	}
 

	
	public String getCExecUsername() {
		return cExecUsername;
	}

	public void setCExecUsername(String cExecUsername) {
		this.cExecUsername = cExecUsername;
	}

	public String getCEndTime() {
		return cEndTime;
	}

	public void setCEndTime(String cEndTime) {
		this.cEndTime = cEndTime;
	}

	public String getCActnodeId() {
		return cActnodeId;
	}

	public void setCActnodeId(String cActnodeId) {
		this.cActnodeId = cActnodeId;
	}

	public String getCChkResult() {
		// if(cIserror.equals("0")){
		// return "正常";
		// }else if(cIserror.equals("1")){
		// return "异常";
		// }
		return cChkResult;
	}


	public void setCChkResult(String cChkResult) {
		this.cChkResult = cChkResult;
	}

	public String getCEvaluateResult() {
		return cEvaluateResult;
	}

	public void setCEvaluateResult(String cEvaluateResult) {
		this.cEvaluateResult = cEvaluateResult;
	}

	public String getCTaskId() {
		return cTaskId;
	}

	public void setCTaskId(String cTaskId) {
		this.cTaskId = cTaskId;
	}

	public String getCChkUsername() {
		return cChkUsername;
	}

	public void setCChkUsername(String cChkUsername) {
		this.cChkUsername = cChkUsername;
	}

	public String getCFactEndtime() {
		return cFactEndtime;
	}

	public void setCFactEndtime(String cFactEndtime) {
		this.cFactEndtime = cFactEndtime;
	}

	public String getCTaskTypename() {
		return cTaskTypename;
	}

	public void setCTaskTypename(String cTaskTypename) {
		this.cTaskTypename = cTaskTypename;
	}


	public String getCManageSectionName() {
		return cManageSectionName;
	}

	public void setCManageSectionName(String cManageSectionName) {
		this.cManageSectionName = cManageSectionName;
	}

	public String getCExecUserInfo() {
		return cExecUserInfo;
	}

	public void setCExecUserInfo(String cExecUserInfo) {
		this.cExecUserInfo = cExecUserInfo;
	}

	public String getCChkUserInfo() {
		return cChkUserInfo;
	}

	public void setCChkUserInfo(String cChkUserInfo) {
		this.cChkUserInfo = cChkUserInfo;
	}

	public String getCEvaluateUserInfo() {
		return cEvaluateUserInfo;
	}

	public void setCEvaluateUserInfo(String cEvaluateUserInfo) {
		this.cEvaluateUserInfo = cEvaluateUserInfo;
	}

	

	

	
	
	

//	public String getsTdCheck() {
//		return sTdCheck;
//	}
//
//	public void setsTdCheck(String sTdCheck) {
//		this.sTdCheck = sTdCheck;
//	}
//
//	public String getsTdReview() {
//		return sTdReview;
//	}
//
//	public void setsTdReview(String sTdReview) {
//		this.sTdReview = sTdReview;
//	}

}
