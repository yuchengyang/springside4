/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.oadata.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;

/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * Oracle需要每个Entity独立定义id的SEQUCENCE时，不继承于本类而改为实现一个Idable的接口。
 * 
 * @author calvin
 */
// JPA 基类的标识
@XmlRootElement(name="projectInfo")
@Entity
@Table(name = "view_oa_service_projectrule")
public class ProjectRuleView {
	
	private String documentId;//文档id
	private String projectId;//project_id `项目id`,
	private String projectCode;//project_code `项目id`,
	private String isPack;
	private String sellPackType;//SELLING_TYPE文件售卖方式
    private BigDecimal filePrice;// BID_DOC_PRICE-- 招标文件价格
    private BigDecimal bailPrice; //BID_DEPOSIT-- `投标保证金`,
    private String purchaseDocUrl;//project_secret_level-- `招标方式编号`,
    
    private String packCode;
    private String packName;
    
    private List<ProjectRulePkgView> pack = Lists.newArrayList();
    
	@Id
	@Column(name="BID_DOCUMENT_ID")
    public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@XmlElement (name="projCode")
	@Column(name="project_code")
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@XmlElement(name="sellPackType")
	@Column(name="SELLING_TYPE")
	public String getSellPackType() {
		return sellPackType;
	}
	public void setSellPackType(String sellPackType) {
		this.sellPackType = sellPackType;
	}
	
	@XmlElement(name="filePrice")
	@Column(name="BID_DOC_PRICE")
	public BigDecimal getFilePrice() {
		return filePrice;
	}
	public void setFilePrice(BigDecimal filePrice) {
		this.filePrice = filePrice;
	}
	
	@XmlElement(name="bailPrice")
	@Column(name="BID_DEPOSIT")
	public BigDecimal getBailPrice() {
		return bailPrice;
	}
	public void setBailPrice(BigDecimal bailPrice) {
		this.bailPrice = bailPrice;
	}
	
	@Column(name="BID_SECTION_CODE")
	public String getPackCode() {
		return packCode;
	}
	public void setPackCode(String packCode) {
		this.packCode = packCode;
	}
	
	@Column(name="BID_SECTION_NAME")
	public String getPackName() {
		return packName;
	}
	public void setPackName(String packName) {
		this.packName = packName;
	}
	@Transient
	public String getPurchaseDocUrl() {
		return purchaseDocUrl;
	}
	public void setPurchaseDocUrl(String purchaseDocUrl) {
		this.purchaseDocUrl = purchaseDocUrl;
	}
	
	@Transient
	public String getIsPack() {
		return isPack;
	}
	public void setIsPack(String isPack) {
		this.isPack = isPack;
	}
	
	@XmlElementWrapper(name="packs")
	@XmlElement(name="pack")
	@Transient
	public List<ProjectRulePkgView> getPack() {
		return pack;
	}
	public void setPack(List<ProjectRulePkgView> pack) {
		this.pack = pack;
	}
}
