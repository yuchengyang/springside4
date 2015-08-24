package org.springside.examples.oadata.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name="supplierInfo")
@Entity
@Table(name = "RES_TENDER")
public class TenderView {
	private Long tenderId;
	private String tenderName;
	private String tenderBusinessLicenseNo;
	private String tenderRegistCapital;
	private String tenderQualificationLevel;
	private String tenderCorporate;
	private String tenderMainBusiness;
	private Long tenderIndustry;
	private Long tenderArea;
	private Long tenderOrganizationCategory;
	private String tenderParentOrganization;
	private String tenderTelephone;
	private String tenderFax;
	private String tenderMail;
	private String tenderPostNo;
	private String tenderAddress;
	private String remark;

	@Id
	public Long getTenderId() {
		/* 131 */return this.tenderId;
	}

	public void setTenderId(Long tenderId) {
		/* 137 */this.tenderId = tenderId;
	}

	public String getTenderName() {
		/* 143 */return this.tenderName;
	}

	public void setTenderName(String tenderName) {
		/* 149 */this.tenderName = tenderName;
	}

	public String getTenderBusinessLicenseNo() {
		/* 155 */return this.tenderBusinessLicenseNo;
	}

	public void setTenderBusinessLicenseNo(String tenderBusinessLicenseNo) {
		/* 161 */this.tenderBusinessLicenseNo = tenderBusinessLicenseNo;
	}

	public String getTenderRegistCapital() {
		/* 167 */return this.tenderRegistCapital;
	}

	public void setTenderRegistCapital(String tenderRegistCapital) {
		/* 173 */this.tenderRegistCapital = tenderRegistCapital;
	}

	public String getTenderQualificationLevel() {
		/* 179 */return this.tenderQualificationLevel;
	}

	public void setTenderQualificationLevel(String tenderQualificationLevel) {
		/* 185 */this.tenderQualificationLevel = tenderQualificationLevel;
	}

	public String getTenderCorporate() {
		/* 191 */return this.tenderCorporate;
	}

	public void setTenderCorporate(String tenderCorporate) {
		/* 197 */this.tenderCorporate = tenderCorporate;
	}

	public String getTenderMainBusiness() {
		/* 203 */return this.tenderMainBusiness;
	}

	public void setTenderMainBusiness(String tenderMainBusiness) {
		/* 209 */this.tenderMainBusiness = tenderMainBusiness;
	}

	public Long getTenderIndustry() {
		/* 215 */return this.tenderIndustry;
	}

	public void setTenderIndustry(Long tenderIndustry) {
		/* 221 */this.tenderIndustry = tenderIndustry;
	}

	public Long getTenderArea() {
		/* 227 */return this.tenderArea;
	}

	public void setTenderArea(Long tenderArea) {
		/* 233 */this.tenderArea = tenderArea;
	}

	public Long getTenderOrganizationCategory() {
		/* 239 */return this.tenderOrganizationCategory;
	}

	public void setTenderOrganizationCategory(Long tenderOrganizationCategory) {
		/* 245 */this.tenderOrganizationCategory = tenderOrganizationCategory;
	}

	public String getTenderParentOrganization() {
		/* 251 */return this.tenderParentOrganization;
	}

	public void setTenderParentOrganization(String tenderParentOrganization) {
		/* 257 */this.tenderParentOrganization = tenderParentOrganization;
	}

	public String getTenderTelephone() {
		/* 263 */return this.tenderTelephone;
	}

	public void setTenderTelephone(String tenderTelephone) {
		/* 269 */this.tenderTelephone = tenderTelephone;
	}

	public String getTenderFax() {
		/* 275 */return this.tenderFax;
	}

	public void setTenderFax(String tenderFax) {
		/* 281 */this.tenderFax = tenderFax;
	}

	public String getTenderMail() {
		/* 287 */return this.tenderMail;
	}

	public void setTenderMail(String tenderMail) {
		/* 293 */this.tenderMail = tenderMail;
	}

	public String getTenderPostNo() {
		/* 299 */return this.tenderPostNo;
	}

	public void setTenderPostNo(String tenderPostNo) {
		/* 305 */this.tenderPostNo = tenderPostNo;
	}

	public String getTenderAddress() {
		/* 311 */return this.tenderAddress;
	}

	public void setTenderAddress(String tenderAddress) {
		/* 317 */this.tenderAddress = tenderAddress;
	}

	public String getRemark() {
		/* 323 */return this.remark;
	}

	public void setRemark(String remark) {
		/* 329 */this.remark = remark;
	}

}

/*
 * Location: E:\Qfile\360031045\FileRecv\GXOA\gxoa\WEB-INF\classes.zip Qualified
 * Name: classes.com.guoxin.business.resource.pojo.Tender JD-Core Version: 0.6.0
 */