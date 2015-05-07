/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.oadata.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="PROJ_TYPE", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("m")
@Table(name = "view_oa_service_projectinfo")
public class ProjectView {
	
	private String projectId;//project_id `项目id`,
    private String projectCode;// project_code-- `项目编号`,
    private String projectName; //project_name-- `项目名称`,
    private String projectSecretlevel;//project_secret_level-- `招标方式编号`,
	private String projectSecretlevelName;// project_secret_levelName-- `招标方式名称`,
    private String projectTypeid;//project_type_id-- `招标类型编号`,
    private String projectTypename; //,project_type_name-- `招标类型名称`,
    private String bidType;//BID_TYPE招标类型
    private String bidTypename;//BID_TYPE_NAME招标类型名称
    private String bidContent;//bid_content-- `招标内容`,
    private String investmentScaleforeign ;//investment_scale_foreign-- `项目投资规模（万元）`,
    private String organizationName;//organization_name-- `招标部门`,
    private String departmentId;// DEPARTMENT_ID-- 缺少 招标部门代码
    private String delegateCompany;//DELEGATE_COMPANY -- 缺少 委托单位
	private String customerCorporate;//rc.CUSTOMER_CORPORATE,	--  预算单位联系人
	private String customerTelephone;//   rc.CUSTOMER_TELEPHONE,预算单位联系电话
    private Date delegateDate;//DELEGATE_DATE-- 缺少 立项日期
    private BigDecimal delegateAmount;//DELEGATE_AMOUNT-- 缺少 项目投资规模
    private String creatorName;//         creator_name    AS `项目经理`,
    private String creator;//         creator_name    AS `项目经理id`,
    
//    private Date ANNOUNCEMENT_DATE;  //公告发布日期
//    private String BID_OPEN_ADDR;//   -- 开标地点
//    private Date BID_OPEN_DATE;//开标日期
    
    private String projType;//项目类型（项目 ：m ， 包   ：s）
	private List<ProjectPkgView> projectPkgViews = Lists.newArrayList();

    @Id
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
	@Column(name="project_secret_level")
	public String getProjectSecretlevel() {
		return projectSecretlevel;
	}
	public void setProjectSecretlevel(String projectSecretlevel) {
		this.projectSecretlevel = projectSecretlevel;
	}
	@Column(name="project_secret_level_Name")
	public String getProjectSecretlevelName() {
		return projectSecretlevelName;
	}
	public void setProjectSecretlevelName(String projectSecretlevelName) {
		this.projectSecretlevelName = projectSecretlevelName;
	}
	@Column(name="project_type_id")
	public String getProjectTypeid() {
		return projectTypeid;
	}
	public void setProjectTypeid(String projectTypeid) {
		this.projectTypeid = projectTypeid;
	}
	@Column(name="project_type_name")
	public String getProjectTypename() {
		return projectTypename;
	}
	public void setProjectTypename(String projectTypename) {
		this.projectTypename = projectTypename;
	}
	@Column(name="BID_TYPE")
	public String getBidType() {
		return bidType;
	}
	public void setBidType(String bidType) {
		this.bidType = bidType;
	}
	@Column(name="BID_TYPE_NAME")
	public String getBidTypename() {
		return bidTypename;
	}
	public void setBidTypename(String bidTypename) {
		this.bidTypename = bidTypename;
	}
	@Column(name="bid_content")
	public String getBidContent() {
		return bidContent;
	}
	public void setBidContent(String bidContent) {
		this.bidContent = bidContent;
	}
	@Column(name="investment_scale_foreign")
	public String getInvestmentScaleforeign() {
		return investmentScaleforeign;
	}
	public void setInvestmentScaleforeign(String investmentScaleforeign) {
		this.investmentScaleforeign = investmentScaleforeign;
	}
	@Column(name="organization_name")
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	@Column(name="DEPARTMENT_ID")
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	@Column(name="DELEGATE_COMPANY")
	public String getDelegateCompany() {
		return delegateCompany;
	}
	public void setDelegateCompany(String delegateCompany) {
		this.delegateCompany = delegateCompany;
	}
	@Column(name="DELEGATE_DATE")
	public Date getDelegateDate() {
		return delegateDate;
	}
	public void setDelegateDate(Date delegateDate) {
		this.delegateDate = delegateDate;
	}
	@Column(name="DELEGATE_AMOUNT")
	public BigDecimal getDelegateAmount() {
		return delegateAmount;
	}
	public void setDelegateAmount(BigDecimal delegateAmount) {
		this.delegateAmount = delegateAmount;
	}
	
	@Column(name="creator_name")
	public String getCreatorName() {
		return creatorName;
	}
	
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	
	@Column(name="creator")
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Column(name="CUSTOMER_CORPORATE")
	public String getCustomerCorporate() {
		return customerCorporate;
	}
	public void setCustomerCorporate(String customerCorporate) {
		this.customerCorporate = customerCorporate;
	}
	@Column(name="CUSTOMER_TELEPHONE")
	public String getCustomerTelephone() {
		return customerTelephone;
	}
	public void setCustomerTelephone(String customerTelephone) {
		this.customerTelephone = customerTelephone;
	}
	
	@Column(name="PROJ_TYPE" , insertable =false, updatable= false )
	public String getProjType() {
		return projType;
	}
	public void setProjType(String projType) {
		this.projType = projType;
	}
	
	@OneToMany(mappedBy = "parentProject")
	public List<ProjectPkgView> getProjectPkgViews() {
		return projectPkgViews;
	}
	public void setProjectPkgViews(List<ProjectPkgView> projectPkgViews) {
		this.projectPkgViews = projectPkgViews;
	}
}
