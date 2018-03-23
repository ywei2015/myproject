package com.talkweb.xwzcxt.pojo;

import java.util.Date;

public class NewsPojo {
	private String newsId;
	private String newsTitle;
	private String newsAuthor;
	private String cover;// 封面
	private String summary;// 摘要
	private String body;// 正文;
	private String creator;
	private Date createTime;
	private String modifier;
	private Date modifyTime;
	private String remark;
	private String isDelete;

	private Long manageSection;// 模块ID
	private String manageSectionName;
	private Long orgId;// 部门ID
	private String orgName;

	private String cNewsFileId;
	private String cNewsFileType;
	private String cNewsFilePath;

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsAuthor() {
		return newsAuthor;
	}

	public void setNewsAuthor(String newsAuthor) {
		this.newsAuthor = newsAuthor;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getcNewsFileId() {
		return cNewsFileId;
	}

	public void setcNewsFileId(String cNewsFileId) {
		this.cNewsFileId = cNewsFileId;
	}

	public String getcNewsFileType() {
		return cNewsFileType;
	}

	public void setcNewsFileType(String cNewsFileType) {
		this.cNewsFileType = cNewsFileType;
	}

	public String getcNewsFilePath() {
		return cNewsFilePath;
	}

	public void setcNewsFilePath(String cNewsFilePath) {
		this.cNewsFilePath = cNewsFilePath;
	}

	public String getManageSectionName() {
		return manageSectionName;
	}

	public void setManageSectionName(String manageSectionName) {
		this.manageSectionName = manageSectionName;
	}

	public Long getManageSection() {
		return manageSection;
	}

	public void setManageSection(Long manageSection) {
		this.manageSection = manageSection;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

}
