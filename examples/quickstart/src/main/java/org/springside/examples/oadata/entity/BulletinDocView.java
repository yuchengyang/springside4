/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.oadata.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

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
@Table(name = "view_oa_service_announcementdoc")
public class BulletinDocView {
	
	protected Long attachmentId;
	protected String projectId;
	private String attachmentName ;
	private Date uploadDate ;
	private String attachmentPath  ;
	
	@Id
	public Long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getAttachmentPath() {
		return attachmentPath;
	}
	


	@Transient
	public String getAttachmentPathShort() {
		return StringUtils.substring(attachmentPath, findFirstGBK(attachmentPath), attachmentPath.length());
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
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
