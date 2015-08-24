/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.quickstart.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.google.common.collect.Lists;

//JPA标识
@XmlRootElement(name="projectInfo")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="PROJ_TYPE", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("m")
@Table(name = "OA_PROJECT")
public class ProjectData extends IdEntity {
	
	public static final int SYNSTATUS_STANBY = 0;
	public static final int SYNSTATUS_BUYERINFO_SUCCESS = 1;//采购人
	public static final int SYNSTATUS_BASEINFO_SUCCESS = 2;//基本信息
	public static final int SYNSTATUS_DOC_SUCCESS = 3;//采购文件信息
	public static final int SYNSTATUS_BULLETIN_SUCCESS = 4;//公告信息
	
	public static final int USESTATUS_VALID = 1;//正常使用
	public static final int USESTATUS_INVALID = -1;//非正常使用（删除）
	
	public static String PROJTYPE_PROJ = "m";
	public static String PROJTYPE_PKG = "s";

	
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
    
    private String delegateCompanyName;//DELEGATE_COMPANY -- 缺少 委托单位

    
	private String customerCorporate = "";//rc.CUSTOMER_CORPORATE,	--  预算单位联系人
	private String customerTelephone = "";//   rc.CUSTOMER_TELEPHONE,预算单位联系电话
    private Date delegateDate;//DELEGATE_DATE   decimal(18)-- 缺少 立项日期
    private BigDecimal delegateAmount;//DELEGATE_AMOUNT-- 缺少 项目投资规模
    private String creatorName;//         creator_name    AS `项目经理`,
    private String creator;//         creator_name    AS `项目经理id`,
    
	private int synStatus;//同步状态 
	
	private int useStatus;//使用状态  1 -1 
	
    private String projType;//项目类型（项目 ：m ， 包   ：s）
	private List<ProjectPkgData> projectPkgDatas = Lists.newArrayList();
	
	private List<BulletinData> bulletinDatas = Lists.newArrayList();//文档
	private BulletinData bulletinDataSelected;//文档
	
    @Transient
    private String ebuyMethod;
    
	@Transient
	private String prequalification;//
    
	@Column(name="project_id")
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
	
	@XmlElement (name="projName")
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
	
	@Transient
	String categoryCode;
	
	@XmlElement(name="categoryCode")
	@Transient
	public String getCategoryCode(){
		
		if("753".equals(getBidType())){
			categoryCode =  "A";
		} else if("752".equals(getBidType())){
			categoryCode =  "B";
		} else{
			categoryCode =  "C";
		}
		return categoryCode;
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
	@XmlElement(name="content")
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
	@XmlElement(name="department")
	@Column(name="DEPARTMENT_ID")
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	@XmlElement(name="buyerId")
	@Column(name="DELEGATE_COMPANY")
	public String getDelegateCompany() {
		return delegateCompany;
	}
	
	public void setDelegateCompany(String delegateCompany) {
		this.delegateCompany = delegateCompany;
	}
	
	@XmlElement(name="buyerName")
	@Transient
	public String getDelegateCompanyName() {
		return delegateCompanyName;
	}
	public void setDelegateCompanyName(String delegateCompanyName) {
		this.delegateCompanyName = delegateCompanyName;
	}
	@XmlElement(name="createDate")
	@Transient
	public Date getCreateDate() {
		return getDelegateDate();
	}
	
	@XmlElement(name="entrustDate")
	@Column(name="DELEGATE_DATE")
	public Date getDelegateDate() {
		return delegateDate;
	}
	public void setDelegateDate(Date delegateDate) {
		this.delegateDate = delegateDate;
	}
	@XmlElement(name="totalBudget")
	@Transient
	public BigDecimal getTotalBudget() {
		return getDelegateAmount()!=null ? getDelegateAmount():new BigDecimal(0);
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
	
	@XmlElement(name="buyerLinkerName")
	@Transient
	public String getBuyerLinkerName() {
		return getCustomerCorporate()!=null ?getCustomerCorporate():"";
	}
	
	
	@Column(name="CUSTOMER_CORPORATE" )
	public String getCustomerCorporate() {
		return customerCorporate;
	}
	public void setCustomerCorporate(String customerCorporate) {
		this.customerCorporate = customerCorporate;
	}
	
	@XmlElement(name="buyerLinkerPhone"  )
	@Transient
	public String getBuyerLinkerPhone() {
		return getCustomerTelephone()!=null ?getCustomerTelephone():"";
	}
	
	@Column(name="CUSTOMER_TELEPHONE")
	public String getCustomerTelephone() {
		return customerTelephone;
	}
	public void setCustomerTelephone(String customerTelephone) {
		this.customerTelephone = customerTelephone;
	}
	
	@XmlElement (name="ebuyMethod")
	@Transient
	public String getEbuyMethod() {
		if("0".equals(getProjectSecretlevel())){
			ebuyMethod = "01";
		}else if("1".equals(getProjectSecretlevel())){
			ebuyMethod = "00";
		}else{
			ebuyMethod = "00";//其他走公开招标 TODO 
		}
		return ebuyMethod;
	}
	
	@Column(name="SYN_STATUS")
	public int getSynStatus() {
		return synStatus;
	}
	public void setSynStatus(int synStatus) {
		this.synStatus = synStatus;
	}
	
	@Column(name="USE_STATUS")                                                                                                                                     
	public int getUseStatus() {
		return useStatus;
	}
	public void setUseStatus(int useStatus) {
		this.useStatus = useStatus;
	}
	
	@Column(name="PROJ_TYPE" , insertable =false, updatable= false )
	public String getProjType() {
		return projType;
	}
	public void setProjType(String projType) {
		this.projType = projType;
	}

	@XmlTransient
	@OneToMany(mappedBy = "parentProject")
	public List<ProjectPkgData> getProjectPkgDatas() {
		return projectPkgDatas;
	}
	public void setProjectPkgDatas(List<ProjectPkgData> projectPkgDatas) {
		this.projectPkgDatas = projectPkgDatas;
	}
	
	@Transient
	public List<BulletinData> getBulletinDatas() {
		return bulletinDatas;
	}
	
	public void setBulletinDatas(List<BulletinData> bulletinDatas) {
		this.bulletinDatas = bulletinDatas;
	}
	
	@Transient
	public BulletinData getBulletinDataSelected() {
		return bulletinDataSelected;
	}
	
	public void setBulletinDataSelected(BulletinData bulletinDataSelected) {
		this.bulletinDataSelected = bulletinDataSelected;
	}
	
	@Transient 
	public String getSynStatusCN(){
		String synStatusCN = "待同步" ;
		switch ( this.synStatus ) {
			case ProjectData.SYNSTATUS_STANBY :{
				synStatusCN = "待同步";
				break;
			}
			case ProjectData.SYNSTATUS_BUYERINFO_SUCCESS :{
				synStatusCN = "采购人同步完毕";
				break;
			}
			case ProjectData.SYNSTATUS_BASEINFO_SUCCESS :{
				synStatusCN = "基本信息同步完毕";
				break;
			}
			case ProjectData.SYNSTATUS_DOC_SUCCESS :{
				synStatusCN = "采购文件同步完毕";
				break;
			}
			case ProjectData.SYNSTATUS_BULLETIN_SUCCESS :{
				synStatusCN = "采购公告同步完毕";
				break;
			}
		}
		return synStatusCN;
	}
	
	@Transient
	public String getPrequalification() {
		return prequalification;
	}
	
	public void setPrequalification(String prequalification) {
		this.prequalification = prequalification;
	}
}
