/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.quickstart.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

//JPA标识
@XmlRootElement(name="projectInfo")
@Entity
@Table(name = "OA_BULLETIN")
public class BulletinData extends IdEntity {
	
	
	private String projectId ;//项目id
	
	private String projectCode ;//项目code
	
	private String projectName ;//项目name
	
	private Date announcementDate ;//发出公告日期
	
	//报名结束日期
	private Date bidDocenddate  ;
	
	//招标文件发售时间
	private Date bidDocbeginselldate  ;
	
	//投标截止时间
	private Date bidDocendselldate  ;
	
	//开标时间
	private Date bidOpendate  ;
	
	//开标地址
	private String bidOpenaddr  ;
	
	private String bidAnnouncementid;
	
	private String synStatus;//同步状态 
	
	private String attachmentId;//, saa.ATTACHMENT_ID,
	private String attachmentName;  //saa.attachment_name          AS attachment_name,-- 文件名称,
	private String uploadDate;//  saa.upload_date              AS upload_date,-- 上传时间,
	//  '招标公告或采购邀请书' AS  filetype,-- 文件类型,
	private String attachmentPath;//  saa.attachment_path          AS attachment_path-- 文件保存服务器路径
	
	@Transient
	private String bulletinTitle;//公告标题
	
	@Transient
	private Date bidStartTime; //投标开始时间
	@Transient
	private String bulletinUrl;//招标公告地址
	
	@Column(name="project_id")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@XmlElement (name="bulletinContent")
	@Transient
	public String getBulletinContent(){
		return "";
	}
	
	@XmlElement (name="projCode")
	@Column(name="project_code")
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@XmlElement (name="bulletinTitle")
	@Transient
	public String getProjectNameForTitle() {
		return getProjectName()+"-公告";
	}
	
	@Column(name="project_name")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@XmlElement (name="signStartTime")
	@Column(name="announcement_date")
	public Date getAnnouncementDate() {
		return announcementDate;
	}

	public void setAnnouncementDate(Date announcementDate) {
		this.announcementDate = announcementDate;
	}

	@XmlElement (name="signEndTime")
	@Column(name="bid_doc_end_date")
	public Date getBidDocenddate() {
		return bidDocenddate;
	}

	public void setBidDocenddate(Date bidDocenddate) {
		this.bidDocenddate = bidDocenddate;
	}

	@Column(name="bid_doc_begin_sell_date")
	public Date getBidDocbeginselldate() {
		return bidDocbeginselldate;
	}

	public void setBidDocbeginselldate(Date bidDocbeginselldate) {
		this.bidDocbeginselldate = bidDocbeginselldate;
	}

	@Column(name="bid_doc_end_sell_date")
	public Date getBidDocendselldate() {
		return bidDocendselldate;
	}

	public void setBidDocendselldate(Date bidDocendselldate) {
		this.bidDocendselldate = bidDocendselldate;
	}

	@XmlElement (name="openBidTime")
	@Column(name="bid_open_date")
	public Date getBidOpendate() {
		return bidOpendate;
	}

	public void setBidOpendate(Date bidOpendate) {
		this.bidOpendate = bidOpendate;
	}

	@XmlElement (name="meetRoomAddress")
	@Column(name="bid_open_addr")
	public String getBidOpenaddr() {
		return bidOpenaddr;
	}

	public void setBidOpenaddr(String bidOpenaddr) {
		this.bidOpenaddr = bidOpenaddr;
	}
	
	@Column(name="bid_announcement_id")
	public String getBidAnnouncementid() {
		return bidAnnouncementid;
	}

	public void setBidAnnouncementid(String bidAnnouncementid) {
		this.bidAnnouncementid = bidAnnouncementid;
	}
	
	@Column(name="SYN_STATUS")
	public String getSynStatus() {
		return synStatus;
	}
	public void setSynStatus(String synStatus) {
		this.synStatus = synStatus;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	@XmlElement(name="bulletinUrl")
	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}
	
	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}
	
	@Transient
	public String getAttachmentPathShort() {
		return StringUtils.substring(attachmentPath, findFirstGBK(attachmentPath), attachmentPath.length());
	}

	public int findFirstGBK(String content) {
		int position = 0;
		// 用Unicode码实现
		String s = content;
		// 找第一个汉字
		for (int index = 0; index <= s.length() - 1; index++) {
			// 将字符串拆开成单个的字符
			String w = s.substring(index, index + 1);
			if (w.compareTo("\u4e00") > 0 && w.compareTo("\u9fa5") < 0) {// \u4e00-\u9fa5//
																			// 中文汉字的范围
				position = index;
				break;
			}
		}
		return position;
	}
	
}
