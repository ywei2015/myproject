package com.talkweb.xwzcxt.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TbIntegrateInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3271639275517613672L;

	private BigDecimal cBasedataId;

	private Long cTabletypeId;

	private Long cOrgid;
	
	private String cOrgName;

	private String cScanCode;

	private String cScanDetail;

	private Long cPrintCount;

	private String cPrintUser;

	private Date cPrintTime;

	private Long cScanCount;

	private String cUpdateUser;

	private Date cUpdateTime;

	private String cIsdelete;

	private String cIsproperty;
	
	private String c_basedata_name;
	private String cBasedataName;

	private String c_basedata_code;
	private String cBasedataCode;

	private String c_basedata_usercode;
	private String cBasedataUserCode;
	
	private String col1;

	private String col2;

	private String col3;

	private String col4;

	private String col5;

	private String col6;

	private String col7;

	private String col8;

	private String col9;

	private String col10;

	private String col11;

	private String col12;

	private String col13;

	private String col14;

	private String col15;

	private String col16;

	private String col17;

	private String col18;

	private String col19;

	private String col20;

	private String col21;

	private String col22;

	private String col23;

	private String col24;

	private String col25;

	private String col26;

	private String col27;

	private String col28;

	private String col29;

	private String col30;

	private BigDecimal cDycolId;

	private String c_column_name;
	private String cColumnName;

	private BigDecimal cIndex;

	private String c_infocol_name;
	private String cInfocolName;

	private String cDataType;

	private String cIsscandata;

	private String cRemark;

	private String cTabletypeName;

	private BigDecimal cTabletypeUpid;

	private Long cColCount;

	private String cTabletypeDes;

	private String cCreator;

	private Date cCreateTime;

	private String cLastModifier;

	private Date cLastModifiedTime;
	
	private String cIsShowScan;
	
	private String objLocation;
	
	private String locStatus;
	private String locStatusName;
	
	private Long nodeId;
	private String nodeName;
	private Long nodePid;
	private String allowmodify;
	private String type;
	
	private String root1;
	private String root2;
	private String root3;
	private String root4;
	private String root5;
	private String root6;
	private String root7;
	private String root8;

	public BigDecimal getcDycolId() {
		return cDycolId;
	}

	public void setcDycolId(BigDecimal cDycolId) {
		this.cDycolId = cDycolId;
	}

	public BigDecimal getcIndex() {
		return cIndex;
	}

	public void setcIndex(BigDecimal cIndex) {
		this.cIndex = cIndex;
	}

	public String getcDataType() {
		return cDataType;
	}

	public void setcDataType(String cDataType) {
		this.cDataType = cDataType;
	}

	public String getcIsscandata() {
		return cIsscandata;
	}

	public void setcIsscandata(String cIsscandata) {
		this.cIsscandata = cIsscandata;
	}

	public String getcRemark() {
		return cRemark==null?"":cRemark;
	}

	public void setcRemark(String cRemark) {
		this.cRemark = cRemark;
	}

	public String getcTabletypeName() {
		return cTabletypeName==null?"":cTabletypeName;
	}

	public String getCTabletypeName() {
		return cTabletypeName==null?"":cTabletypeName;
	}

	public void setcTabletypeName(String cTabletypeName) {
		this.cTabletypeName = cTabletypeName;
	}

	public BigDecimal getcTabletypeUpid() {
		return cTabletypeUpid;
	}

	public BigDecimal getCTabletypeUpid() {
		return cTabletypeUpid;
	}

	public void setcTabletypeUpid(BigDecimal cTabletypeUpid) {
		this.cTabletypeUpid = cTabletypeUpid;
	}

	public Long getcColCount() {
		return cColCount;
	}

	public void setcColCount(Long cColCount) {
		this.cColCount = cColCount;
	}

	public String getcTabletypeDes() {
		return cTabletypeDes==null?"":cTabletypeDes;
	}

	public void setcTabletypeDes(String cTabletypeDes) {
		this.cTabletypeDes = cTabletypeDes;
	}

	public String getcCreator() {
		return cCreator;
	}

	public void setcCreator(String cCreator) {
		this.cCreator = cCreator;
	}

	public Date getcCreateTime() {
		return cCreateTime;
	}

	public void setcCreateTime(Date cCreateTime) {
		this.cCreateTime = cCreateTime;
	}

	public String getcLastModifier() {
		return cLastModifier;
	}

	public void setcLastModifier(String cLastModifier) {
		this.cLastModifier = cLastModifier;
	}

	public Date getcLastModifiedTime() {
		return cLastModifiedTime;
	}

	public void setcLastModifiedTime(Date cLastModifiedTime) {
		this.cLastModifiedTime = cLastModifiedTime;
	}

	public BigDecimal getcBasedataId() {
		return cBasedataId;
	}

	public void setcBasedataId(BigDecimal cBasedataId) {
		this.cBasedataId = cBasedataId;
	}

	public Long getcTabletypeId() {
		return cTabletypeId;
	}

	public Long getCTabletypeId() {
		return cTabletypeId;
	}

	public void setcTabletypeId(Long cTabletypeId) {
		this.cTabletypeId = cTabletypeId;
	}

	public Long getcOrgid() {
		return cOrgid;
	}

	public void setcOrgid(Long cOrgid) {
		this.cOrgid = cOrgid;
	}

	public String getcScanCode() {
		return cScanCode;
	}

	public void setcScanCode(String cScanCode) {
		this.cScanCode = cScanCode == null ? null : cScanCode.trim();
	}

	public String getcScanDetail() {
		return cScanDetail;
	}

	public void setcScanDetail(String cScanDetail) {
		this.cScanDetail = cScanDetail == null ? null : cScanDetail.trim();
	}

	public Long getcPrintCount() {
		return cPrintCount;
	}

	public void setcPrintCount(Long cPrintCount) {
		this.cPrintCount = cPrintCount;
	}

	public String getcPrintUser() {
		return cPrintUser;
	}

	public void setcPrintUser(String cPrintUser) {
		this.cPrintUser = cPrintUser == null ? null : cPrintUser.trim();
	}

	public Date getcPrintTime() {
		return cPrintTime;
	}

	public void setcPrintTime(Date cPrintTime) {
		this.cPrintTime = cPrintTime;
	}

	public Long getcScanCount() {
		return cScanCount;
	}

	public void setcScanCount(Long cScanCount) {
		this.cScanCount = cScanCount;
	}

	public String getcUpdateUser() {
		return cUpdateUser;
	}

	public void setcUpdateUser(String cUpdateUser) {
		this.cUpdateUser = cUpdateUser == null ? null : cUpdateUser.trim();
	}

	public Date getcUpdateTime() {
		return cUpdateTime;
	}

	public void setcUpdateTime(Date cUpdateTime) {
		this.cUpdateTime = cUpdateTime;
	}

	public String getcIsdelete() {
		return cIsdelete;
	}

	public void setcIsdelete(String cIsdelete) {
		this.cIsdelete = cIsdelete == null ? null : cIsdelete.trim();
	}



	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1 == null ? null : col1.trim();
	}

	public String getCol2() {
		return col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2 == null ? null : col2.trim();
	}

	public String getCol3() {
		return col3;
	}

	public void setCol3(String col3) {
		this.col3 = col3 == null ? null : col3.trim();
	}

	public String getCol4() {
		return col4;
	}

	public void setCol4(String col4) {
		this.col4 = col4 == null ? null : col4.trim();
	}

	public String getCol5() {
		return col5;
	}

	public void setCol5(String col5) {
		this.col5 = col5 == null ? null : col5.trim();
	}

	public String getCol6() {
		return col6;
	}

	public void setCol6(String col6) {
		this.col6 = col6 == null ? null : col6.trim();
	}

	public String getCol7() {
		return col7;
	}

	public void setCol7(String col7) {
		this.col7 = col7 == null ? null : col7.trim();
	}

	public String getCol8() {
		return col8;
	}

	public void setCol8(String col8) {
		this.col8 = col8 == null ? null : col8.trim();
	}

	public String getCol9() {
		return col9;
	}

	public void setCol9(String col9) {
		this.col9 = col9 == null ? null : col9.trim();
	}

	public String getCol10() {
		return col10;
	}

	public void setCol10(String col10) {
		this.col10 = col10 == null ? null : col10.trim();
	}

	public String getCol11() {
		return col11;
	}

	public void setCol11(String col11) {
		this.col11 = col11 == null ? null : col11.trim();
	}

	public String getCol12() {
		return col12;
	}

	public void setCol12(String col12) {
		this.col12 = col12 == null ? null : col12.trim();
	}

	public String getCol13() {
		return col13;
	}

	public void setCol13(String col13) {
		this.col13 = col13 == null ? null : col13.trim();
	}

	public String getCol14() {
		return col14;
	}

	public void setCol14(String col14) {
		this.col14 = col14 == null ? null : col14.trim();
	}

	public String getCol15() {
		return col15;
	}

	public void setCol15(String col15) {
		this.col15 = col15 == null ? null : col15.trim();
	}

	public String getCol16() {
		return col16;
	}

	public void setCol16(String col16) {
		this.col16 = col16 == null ? null : col16.trim();
	}

	public String getCol17() {
		return col17;
	}

	public void setCol17(String col17) {
		this.col17 = col17 == null ? null : col17.trim();
	}

	public String getCol18() {
		return col18;
	}

	public void setCol18(String col18) {
		this.col18 = col18 == null ? null : col18.trim();
	}

	public String getCol19() {
		return col19;
	}

	public void setCol19(String col19) {
		this.col19 = col19 == null ? null : col19.trim();
	}

	public String getCol20() {
		return col20;
	}

	public void setCol20(String col20) {
		this.col20 = col20 == null ? null : col20.trim();
	}

	public String getCol21() {
		return col21;
	}

	public void setCol21(String col21) {
		this.col21 = col21 == null ? null : col21.trim();
	}

	public String getCol22() {
		return col22;
	}

	public void setCol22(String col22) {
		this.col22 = col22 == null ? null : col22.trim();
	}

	public String getCol23() {
		return col23;
	}

	public void setCol23(String col23) {
		this.col23 = col23 == null ? null : col23.trim();
	}

	public String getCol24() {
		return col24;
	}

	public void setCol24(String col24) {
		this.col24 = col24 == null ? null : col24.trim();
	}

	public String getCol25() {
		return col25;
	}

	public void setCol25(String col25) {
		this.col25 = col25 == null ? null : col25.trim();
	}

	public String getCol26() {
		return col26;
	}

	public void setCol26(String col26) {
		this.col26 = col26 == null ? null : col26.trim();
	}

	public String getCol27() {
		return col27;
	}

	public void setCol27(String col27) {
		this.col27 = col27 == null ? null : col27.trim();
	}

	public String getCol28() {
		return col28;
	}

	public void setCol28(String col28) {
		this.col28 = col28 == null ? null : col28.trim();
	}

	public String getCol29() {
		return col29;
	}

	public void setCol29(String col29) {
		this.col29 = col29 == null ? null : col29.trim();
	}

	public String getCol30() {
		return col30;
	}

	public void setCol30(String col30) {
		this.col30 = col30 == null ? null : col30.trim();
	}

	public String getC_column_name() {
		return c_column_name==null?"":c_column_name;
	}

	public void setC_column_name(String c_column_name) {
		this.c_column_name = c_column_name;
		this.cColumnName=c_column_name;
	}

	public String getC_infocol_name() {
		return c_infocol_name;
	}

	public void setC_infocol_name(String c_infocol_name) {
		this.c_infocol_name = c_infocol_name;
		this.cInfocolName=c_infocol_name;
		
	}

	public String getC_basedata_name() {
		return c_basedata_name;
	}

	public void setC_basedata_name(String c_basedata_name) {
		this.c_basedata_name = c_basedata_name;
		this.cBasedataName = c_basedata_name;
	}

	public String getC_basedata_code() {
		return c_basedata_code==null?"":c_basedata_code;
	}

	public void setC_basedata_code(String c_basedata_code) {
		this.c_basedata_code = c_basedata_code;
		this.cBasedataCode=c_basedata_code;
	}

	public String getcBasedataName() {
		return cBasedataName==null?"":cBasedataName;
	}
	
	public String getCBasedataName() {
		return cBasedataName==null?"":cBasedataName;
	}
	
	public void setcBasedataName(String cBasedataName) {
		this.c_basedata_name = cBasedataName;
		this.cBasedataName = cBasedataName;
	}

	public String getcBasedataCode() {
		return cBasedataCode==null?"":cBasedataCode;
	}

	public String getCBasedataCode() {
		return cBasedataCode==null?"":cBasedataCode;
	}
	
	public void setcBasedataCode(String cBasedataCode) {
		this.c_basedata_code = cBasedataCode;
		this.cBasedataCode = cBasedataCode;
	}

	public String getcColumnName() {
		return cColumnName==null?"":cColumnName;
	}

	public String getCColumnName() {
		return cColumnName==null?"":cColumnName;
	}
	
	public void setcColumnName(String cColumnName) {
		this.c_column_name=cColumnName;
		this.cColumnName = cColumnName;
	}

	public String getcInfocolName() {
		return cInfocolName;
	}

	public String getCInfocolName() {
		return cInfocolName;
	}
	
	public void setcInfocolName(String cInfocolName) {
		this.c_infocol_name=cInfocolName;
		this.cInfocolName = cInfocolName;
	}

	public String getC_basedata_usercode() {
		return c_basedata_usercode==null?"":c_basedata_usercode;
	}

	public void setC_basedata_usercode(String c_basedata_usercode) {
		this.c_basedata_usercode = c_basedata_usercode;
		this.cBasedataUserCode = c_basedata_usercode;
	}

	public String getcBasedataUserCode() {
		return cBasedataUserCode==null?"":cBasedataUserCode;
	}

	public String getCBasedataUserCode() {
		return cBasedataUserCode==null?"":cBasedataUserCode;
	}
	
	public void setcBasedataUserCode(String cBasedataUserCode) {
		this.c_basedata_usercode = cBasedataUserCode;
		this.cBasedataUserCode = cBasedataUserCode;
	}

	public String getcOrgName() {
		return cOrgName;
	}

	public String getCOrgName() {
		return cOrgName;
	}
	
	public void setcOrgName(String cOrgName) {
		this.cOrgName = cOrgName;
	}

	public String getcIsproperty() {
		return cIsproperty;
	}

	public String getCIsproperty() {
		return cIsproperty;
	}
	
	public void setcIsproperty(String cIsproperty) {
		this.cIsproperty = cIsproperty;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Long getNodePid() {
		return nodePid;
	}

	public void setNodePid(Long nodePid) {
		this.nodePid = nodePid;
	}

	public String getAllowmodify() {
		this.allowmodify = this.cIsproperty;
		return this.allowmodify;
	}

	public void setAllowmodify(String allowmodify) {
		this.allowmodify = allowmodify;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getcIsShowScan() {
		return cIsShowScan;
	}

	public String getCIsShowScan() {
		return cIsShowScan;
	}
	
	public void setcIsShowScan(String cIsShowScan) {
		this.cIsShowScan = cIsShowScan;
	}

	public String getObjLocation() {
		return objLocation;
	}

	public void setObjLocation(String objLocation) {
		this.objLocation = objLocation;
	}

	public String getLocStatus() {
		return locStatus;
	}
	
	public String getRoot1() {
		return root1;
	}

	public void setRoot1(String root1) {
		this.root1 = root1;
	}

	public String getRoot2() {
		return root2;
	}

	public void setRoot2(String root2) {
		this.root2 = root2;
	}

	public String getRoot3() {
		return root3;
	}

	public void setRoot3(String root3) {
		this.root3 = root3;
	}

	public String getRoot4() {
		return root4;
	}

	public void setRoot4(String root4) {
		this.root4 = root4;
	}

	public String getRoot5() {
		return root5;
	}

	public void setRoot5(String root5) {
		this.root5 = root5;
	}

	public String getRoot6() {
		return root6;
	}

	public void setRoot6(String root6) {
		this.root6 = root6;
	}

	public String getRoot7() {
		return root7;
	}

	public void setRoot7(String root7) {
		this.root7 = root7;
	}

	public String getRoot8() {
		return root8;
	}

	public void setRoot8(String root8) {
		this.root8 = root8;
	}

	public void setLocStatus(String locStatus) {
		this.locStatus = locStatus;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public BigDecimal getCDycolId() {
		return cDycolId;
	}


	public BigDecimal getCIndex() {
		return cIndex;
	}

	public String getCDataType() {
		return cDataType;
	}

	public String getCIsscandata() {
		return cIsscandata;
	}

	public String getCRemark() {
		return cRemark==null?"":cRemark;
	}

	public Long getCColCount() {
		return cColCount;
	}

	public String getCTabletypeDes() {
		return cTabletypeDes==null?"":cTabletypeDes;
	}

	public String getCCreator() {
		return cCreator;
	}

	public Date getCCreateTime() {
		return cCreateTime;
	}
	
	public String getCLastModifier() {
		return cLastModifier;
	}
	
	public Date getCLastModifiedTime() {
		return cLastModifiedTime;
	}

	public BigDecimal getCBasedataId() {
		return cBasedataId;
	}

	public Long getCOrgid() {
		return cOrgid;
	}

	public String getCScanCode() {
		return cScanCode;
	}

	public String getCScanDetail() {
		return cScanDetail;
	}

	public Long getCPrintCount() {
		return cPrintCount;
	}

	public String getCPrintUser() {
		return cPrintUser;
	}

	public Date getCPrintTime() {
		return cPrintTime;
	}

	public Long getCScanCount() {
		return cScanCount;
	}

	public String getCUpdateUser() {
		return cUpdateUser;
	}

	public Date getCUpdateTime() {
		return cUpdateTime;
	}

	public String getCIsdelete() {
		return cIsdelete;
	}

	public void setLocStatusName(String locStatusName) {
		this.locStatusName = locStatus.equals("1")?"是":"否";
	}
	
	public String getLocStatusName() {
		return locStatus!=null&&locStatus.equals("1")?"是":"否";
	}
	
}