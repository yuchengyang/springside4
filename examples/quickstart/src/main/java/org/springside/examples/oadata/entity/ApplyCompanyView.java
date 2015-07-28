package org.springside.examples.oadata.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "PRO_PROJECT_COMPANY")
public class ApplyCompanyView{
	private Long projectCompanyId;
	private Long projectId;
	private Long bidSectionId;
	private Long companyId;
	private String companyName;
	private Boolean buyPrequalificationDoc;
	private Integer buyPrequalificationDocNum;
	private Integer buyEPrequalificationDocNum;
	private Date buyPrequalificationDocDate;
	private Boolean buyBidDoc;
	private Integer buyBidDocNum;
	private Integer buyEBidDocNum;
	private Date buyBidDocDate;
	private Long tenderType;
	private Integer category;
	private Boolean attendBidOpen;
	private Boolean passPrequalification;
	private Boolean winBid = Boolean.valueOf(false);
	private BigDecimal tenderQuote;
	private Long tenderNum;
	private Integer recommendOrder;
	private String recommendReson;
	private Long joinParentId;
	private BigDecimal epreDocPrice;
	private BigDecimal preDocPrice;
	private BigDecimal bidDocPrice;
	private BigDecimal ebidDocPrice;
	private BigDecimal postage;
	private int optimisticVersion;
	private Long contactId;
	private Date updateDate;
	private Long updater;
	private Date createDate;
	private Long creator;
	private Boolean isInvited;
	private BigDecimal depositAmount;
	private Integer receiveMode;
	private Date depositDate;

	public ApplyCompanyView() {
	}

	@Id
	public Long getProjectCompanyId() {
		return this.projectCompanyId;
	}

	public void setProjectCompanyId(Long projectCompanyId) {
		/* 136 */this.projectCompanyId = projectCompanyId;
	}

	public Long getProjectId() {
		/* 142 */return this.projectId;
	}

	public void setProjectId(Long projectId) {
		/* 148 */this.projectId = projectId;
	}

	public Long getBidSectionId() {
		/* 154 */return this.bidSectionId;
	}

	public void setBidSectionId(Long bidSectionId) {
		/* 160 */this.bidSectionId = bidSectionId;
	}

	public Long getCompanyId() {
		/* 166 */return this.companyId;
	}

	public void setCompanyId(Long companyId) {
		/* 172 */this.companyId = companyId;
	}
	
	@Transient
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Boolean getBuyPrequalificationDoc() {
		/* 178 */return this.buyPrequalificationDoc;
	}

	public void setBuyPrequalificationDoc(Boolean buyPrequalificationDoc) {
		/* 184 */this.buyPrequalificationDoc = buyPrequalificationDoc;
	}

	public Integer getBuyPrequalificationDocNum() {
		/* 190 */return this.buyPrequalificationDocNum;
	}

	public void setBuyPrequalificationDocNum(Integer buyPrequalificationDocNum) {
		/* 196 */this.buyPrequalificationDocNum = buyPrequalificationDocNum;
	}

	public Date getBuyPrequalificationDocDate() {
		/* 202 */return this.buyPrequalificationDocDate;
	}

	public void setBuyPrequalificationDocDate(Date buyPrequalificationDocDate) {
		/* 208 */this.buyPrequalificationDocDate = buyPrequalificationDocDate;
	}

	public Boolean getBuyBidDoc() {
		/* 214 */return this.buyBidDoc;
	}

	public void setBuyBidDoc(Boolean buyBidDoc) {
		/* 220 */this.buyBidDoc = buyBidDoc;
	}

	public Integer getBuyBidDocNum() {
		/* 226 */return this.buyBidDocNum;
	}

	public void setBuyBidDocNum(Integer buyBidDocNum) {
		/* 232 */this.buyBidDocNum = buyBidDocNum;
	}

	public Date getBuyBidDocDate() {
		/* 238 */return this.buyBidDocDate;
	}

	public void setBuyBidDocDate(Date buyBidDocDate) {
		/* 244 */this.buyBidDocDate = buyBidDocDate;
	}

	public Long getTenderType() {
		/* 250 */return this.tenderType;
	}

	public void setTenderType(Long tenderType) {
		/* 256 */this.tenderType = tenderType;
	}

	public Boolean getAttendBidOpen() {
		/* 262 */return this.attendBidOpen;
	}

	public void setAttendBidOpen(Boolean attendBidOpen) {
		/* 268 */this.attendBidOpen = attendBidOpen;
	}

	public Boolean getPassPrequalification() {
		/* 274 */return this.passPrequalification;
	}

	public void setPassPrequalification(Boolean passPrequalification) {
		/* 280 */this.passPrequalification = passPrequalification;
	}

	public Boolean getWinBid() {
		/* 286 */return this.winBid;
	}

	public void setWinBid(Boolean winBid) {
		/* 292 */this.winBid = winBid;
	}

	public BigDecimal getTenderQuote() {
		/* 298 */return this.tenderQuote;
	}

	public void setTenderQuote(BigDecimal tenderQuote) {
		/* 304 */this.tenderQuote = tenderQuote;
	}

	public Long getTenderNum() {
		/* 310 */return this.tenderNum;
	}

	public void setTenderNum(Long tenderNum) {
		/* 316 */this.tenderNum = tenderNum;
	}

	public Integer getRecommendOrder() {
		/* 322 */return this.recommendOrder;
	}

	public void setRecommendOrder(Integer recommendOrder) {
		/* 328 */this.recommendOrder = recommendOrder;
	}

	public String getRecommendReson() {
		/* 334 */return this.recommendReson;
	}

	public void setRecommendReson(String recommendReson) {
		/* 340 */this.recommendReson = recommendReson;
	}

	public int getOptimisticVersion() {
		/* 346 */return this.optimisticVersion;
	}

	public void setOptimisticVersion(int optimisticVersion) {
		/* 352 */this.optimisticVersion = optimisticVersion;
	}

	public Date getUpdateDate() {
		/* 358 */return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		/* 364 */this.updateDate = updateDate;
	}

	public Long getUpdater() {
		/* 370 */return this.updater;
	}

	public void setUpdater(Long updater) {
		/* 376 */this.updater = updater;
	}

	public Date getCreateDate() {
		/* 382 */return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		/* 388 */this.createDate = createDate;
	}

	public Long getCreator() {
		/* 394 */return this.creator;
	}

	public void setCreator(Long creator) {
		/* 400 */this.creator = creator;
	}

	@Column(name="JOINT_PARENT_ID")
	public Long getJoinParentId() {
		/* 405 */return this.joinParentId;
	}

	public void setJoinParentId(Long joinParentId) {
		/* 410 */this.joinParentId = joinParentId;
	}

	@Column(name="E_PRE_DOC_PRICE")
	public BigDecimal getEpreDocPrice() {
		/* 415 */return this.epreDocPrice;
	}

	public void setEpreDocPrice(BigDecimal epreDocPrice) {
		/* 420 */this.epreDocPrice = epreDocPrice;
	}

	public BigDecimal getPreDocPrice() {
		/* 425 */return this.preDocPrice;
	}

	public void setPreDocPrice(BigDecimal preDocPrice) {
		/* 430 */this.preDocPrice = preDocPrice;
	}

	public BigDecimal getBidDocPrice() {
		/* 435 */return this.bidDocPrice;
	}

	public void setBidDocPrice(BigDecimal bidDocPrice) {
		/* 440 */this.bidDocPrice = bidDocPrice;
	}

	@Column(name="E_BID_DOC_PRICE")
	public BigDecimal getEbidDocPrice() {
		/* 445 */return this.ebidDocPrice;
	}

	public void setEbidDocPrice(BigDecimal ebidDocPrice) {
		/* 450 */this.ebidDocPrice = ebidDocPrice;
	}

	@Column(name="BUY_E_PREQUALIFICATION_DOC_NUM")
	public Integer getBuyEPrequalificationDocNum() {
		/* 455 */return this.buyEPrequalificationDocNum;
	}

	public void setBuyEPrequalificationDocNum(Integer buyEPrequalificationDocNum) {
		/* 460 */this.buyEPrequalificationDocNum = buyEPrequalificationDocNum;
	}

	
	@Column(name="BUY_E_BID_DOC_NUM")
	public Integer getBuyEBidDocNum() {
		/* 465 */return this.buyEBidDocNum;
	}

	public void setBuyEBidDocNum(Integer buyEBidDocNum) {
		/* 470 */this.buyEBidDocNum = buyEBidDocNum;
	}

	public Integer getCategory() {
		/* 475 */return this.category;
	}

	public void setCategory(Integer category) {
		/* 480 */this.category = category;
	}

	public Long getContactId() {
		/* 485 */return this.contactId;
	}

	public void setContactId(Long contactId) {
		/* 490 */this.contactId = contactId;
	}

	public Boolean getIsInvited() {
		/* 496 */return this.isInvited;
	}

	public void setIsInvited(Boolean isInvited) {
		/* 502 */this.isInvited = isInvited;
	}

	public BigDecimal getDepositAmount() {
		/* 507 */return this.depositAmount;
	}

	public void setDepositAmount(BigDecimal depositAmount) {
		/* 512 */this.depositAmount = depositAmount;
	}


	public BigDecimal getPostage() {
		/* 537 */return this.postage;
	}

	public void setPostage(BigDecimal postage) {
		/* 542 */this.postage = postage;
	}

	public Integer getReceiveMode() {
		/* 547 */return this.receiveMode;
	}

	public void setReceiveMode(Integer receiveMode) {
		/* 552 */this.receiveMode = receiveMode;
	}

	public Date getDepositDate() {
		/* 557 */return this.depositDate;
	}

	public void setDepositDate(Date depositDate) {
		/* 562 */this.depositDate = depositDate;
	}
}
