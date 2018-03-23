package com.talkweb.xwzcxt.pojo;

import java.math.BigDecimal;

import com.talkweb.twdpe.core.data.BasePOJO;

public class TSdActnodeItemPojo extends BasePOJO {
    private String cActitemId;

    private BigDecimal cActitemIndex;

    private String cActitemCode;

    private String cActitemName;

    private String cActnodeId;

    private String cActitemStd;

    private String cActitemStdCheck;

    private String cExplain;

    private BigDecimal cGetdatatype;

    private BigDecimal cCheckdatatype;

    private String cGetdatatypename;

    private String cCheckdatatypename;

    private String cRemark;

    private BigDecimal cFlag;

    private BigDecimal cVersion;

    private String cGetdataPretext;

    private String cGetdataText;

    private String cGetdataUnit;

    private String cCheckdataPretext;

    private String cCheckdataText;

    private String cCheckdataUnit;

    private BigDecimal cGroupindex;

    private String cActitemMediaFileId;

    private String cActitemMediaFileType;

    private String cActitemMediaFilePath;

    public String getcActitemId() {
        return cActitemId;
    }

    public void setcActitemId(String cActitemId) {
        this.cActitemId = cActitemId == null ? null : cActitemId.trim();
    }

    public BigDecimal getcActitemIndex() {
        return cActitemIndex;
    }

    public void setcActitemIndex(BigDecimal cActitemIndex) {
        this.cActitemIndex = cActitemIndex;
    }

    public String getcActitemCode() {
        return cActitemCode;
    }

    public void setcActitemCode(String cActitemCode) {
        this.cActitemCode = cActitemCode == null ? null : cActitemCode.trim();
    }

    public String getcActitemName() {
        return cActitemName;
    }

    public void setcActitemName(String cActitemName) {
        this.cActitemName = cActitemName == null ? null : cActitemName.trim();
    }

    public String getcActnodeId() {
        return cActnodeId;
    }

    public void setcActnodeId(String cActnodeId) {
        this.cActnodeId = cActnodeId == null ? null : cActnodeId.trim();
    }

    public String getcActitemStd() {
        return cActitemStd;
    }

    public void setcActitemStd(String cActitemStd) {
        this.cActitemStd = cActitemStd == null ? null : cActitemStd.trim();
    }

    public String getcActitemStdCheck() {
        return cActitemStdCheck;
    }

    public void setcActitemStdCheck(String cActitemStdCheck) {
        this.cActitemStdCheck = cActitemStdCheck == null ? null : cActitemStdCheck.trim();
    }

    public String getcExplain() {
        return cExplain;
    }

    public void setcExplain(String cExplain) {
        this.cExplain = cExplain == null ? null : cExplain.trim();
    }

    public BigDecimal getcGetdatatype() {
        return cGetdatatype;
    }

    public void setcGetdatatype(BigDecimal cGetdatatype) {
        this.cGetdatatype = cGetdatatype;
    }

    public BigDecimal getcCheckdatatype() {
        return cCheckdatatype;
    }

    public void setcCheckdatatype(BigDecimal cCheckdatatype) {
        this.cCheckdatatype = cCheckdatatype;
    }

    public String getcGetdatatypename() {
        return cGetdatatypename;
    }

    public void setcGetdatatypename(String cGetdatatypename) {
        this.cGetdatatypename = cGetdatatypename;
    }

    public String getcCheckdatatypename() {
        return cCheckdatatypename;
    }

    public void setcCheckdatatypename(String cCheckdatatypename) {
        this.cCheckdatatypename = cCheckdatatypename;
    }

    public String getcRemark() {
        return cRemark;
    }

    public void setcRemark(String cRemark) {
        this.cRemark = cRemark == null ? null : cRemark.trim();
    }

    public BigDecimal getcFlag() {
        return cFlag;
    }

    public void setcFlag(BigDecimal cFlag) {
        this.cFlag = cFlag;
    }

    public BigDecimal getcVersion() {
        return cVersion;
    }

    public void setcVersion(BigDecimal cVersion) {
        this.cVersion = cVersion;
    }

    public String getcGetdataPretext() {
        return cGetdataPretext;
    }

    public void setcGetdataPretext(String cGetdataPretext) {
        this.cGetdataPretext = cGetdataPretext == null ? null : cGetdataPretext.trim();
    }

    public String getcGetdataText() {
        return cGetdataText;
    }

    public void setcGetdataText(String cGetdataText) {
        this.cGetdataText = cGetdataText == null ? null : cGetdataText.trim();
    }

    public String getcGetdataUnit() {
        return cGetdataUnit;
    }

    public void setcGetdataUnit(String cGetdataUnit) {
        this.cGetdataUnit = cGetdataUnit == null ? null : cGetdataUnit.trim();
    }

    public String getcCheckdataPretext() {
        return cCheckdataPretext;
    }

    public void setcCheckdataPretext(String cCheckdataPretext) {
        this.cCheckdataPretext = cCheckdataPretext == null ? null : cCheckdataPretext.trim();
    }

    public String getcCheckdataText() {
        return cCheckdataText;
    }

    public void setcCheckdataText(String cCheckdataText) {
        this.cCheckdataText = cCheckdataText == null ? null : cCheckdataText.trim();
    }

    public String getcCheckdataUnit() {
        return cCheckdataUnit;
    }

    public void setcCheckdataUnit(String cCheckdataUnit) {
        this.cCheckdataUnit = cCheckdataUnit == null ? null : cCheckdataUnit.trim();
    }

    public BigDecimal getcGroupindex() {
        return cGroupindex;
    }

    public void setcGroupindex(BigDecimal cGroupindex) {
        this.cGroupindex = cGroupindex;
    }

    public String getcActitemMediaFileId() {
        return cActitemMediaFileId;
    }

    public void setcActitemMediaFileId(String cActitemMediaFileId) {
        this.cActitemMediaFileId = cActitemMediaFileId;
    }

    public String getcActitemMediaFileType() {
        return cActitemMediaFileType;
    }

    public void setcActitemMediaFileType(String cActitemMediaFileType) {
        this.cActitemMediaFileType = cActitemMediaFileType;
    }

    public String getcActitemMediaFilePath() {
        return cActitemMediaFilePath;
    }

    public void setcActitemMediaFilePath(String cActitemMediaFilePath) {
        this.cActitemMediaFilePath = cActitemMediaFilePath;
    }

	@Override
	public String toString() {
		return "TSdActnodeItemPojo [cActitemId=" + cActitemId
				+ ", cActitemIndex=" + cActitemIndex + ", cActitemCode="
				+ cActitemCode + ", cActitemName=" + cActitemName
				+ ", cActnodeId=" + cActnodeId + ", cActitemStd=" + cActitemStd
				+ ", cActitemStdCheck=" + cActitemStdCheck + ", cExplain="
				+ cExplain + ", cGetdatatype=" + cGetdatatype
				+ ", cCheckdatatype=" + cCheckdatatype + ", cGetdatatypename="
				+ cGetdatatypename + ", cCheckdatatypename="
				+ cCheckdatatypename + ", cRemark=" + cRemark + ", cFlag="
				+ cFlag + ", cVersion=" + cVersion + ", cGetdataPretext="
				+ cGetdataPretext + ", cGetdataText=" + cGetdataText
				+ ", cGetdataUnit=" + cGetdataUnit + ", cCheckdataPretext="
				+ cCheckdataPretext + ", cCheckdataText=" + cCheckdataText
				+ ", cCheckdataUnit=" + cCheckdataUnit + ", cGroupindex="
				+ cGroupindex + ", cActitemMediaFileId=" + cActitemMediaFileId
				+ ", cActitemMediaFileType=" + cActitemMediaFileType
				+ ", cActitemMediaFilePath=" + cActitemMediaFilePath + "]";
	}
    
}