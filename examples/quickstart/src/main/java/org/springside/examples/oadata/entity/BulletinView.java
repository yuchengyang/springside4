/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.oadata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * Oracle需要每个Entity独立定义id的SEQUCENCE时，不继承于本类而改为实现一个Idable的接口。
 * 
 * @author calvin
 */
// JPA 基类的标识
@Entity
@Table(name = "view_oa_service_announcement")
public class BulletinView {

	private String attachmentId;//, saa.ATTACHMENT_ID,
	
	protected String projectId;
	private String projectCode ;
	private String projectName ;
	private Date announcementDate ;
	private Date bidDocenddate  ;
	private Date bidDocbeginselldate  ;
	private Date bidDocendselldate  ;
	private Date bidOpendate  ;
	private String bidOpenaddr  ;
	private String bidAnnouncementid;
	
	private String attachmentName;  //saa.attachment_name          AS attachment_name,-- 文件名称,
	private String uploadDate;//  saa.upload_date              AS upload_date,-- 上传时间,
	//  '招标公告或采购邀请书' AS  filetype,-- 文件类型,
	private String attachmentPath;//  saa.attachment_path          AS attachment_path-- 文件保存服务器路径
	
	
	@Id
	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Column(name="project_code")
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@Column(name="project_name")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name="announcement_date")
	public Date getAnnouncementDate() {
		return announcementDate;
	}

	public void setAnnouncementDate(Date announcementDate) {
		this.announcementDate = announcementDate;
	}

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

	@Column(name="bid_open_date")
	public Date getBidOpendate() {
		return bidOpendate;
	}

	public void setBidOpendate(Date bidOpendate) {
		this.bidOpendate = bidOpendate;
	}

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

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}
}
