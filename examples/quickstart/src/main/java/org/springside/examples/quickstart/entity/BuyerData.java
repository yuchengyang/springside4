/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.quickstart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * Oracle需要每个Entity独立定义id的SEQUCENCE时，不继承于本类而改为实现一个Idable的接口。
 * 
 * @author calvin
 */
// JPA 基类的标识
@XmlRootElement(name="buyerInfo")
@Entity
@Table(name = "oa_buyer")
public class BuyerData  extends IdEntity{
	
	protected int customerId;   //--  customer_id AS `招标人id`,
	
	public static String SYNSTATUS_STANBY = "0";
	public static String SYNSTATUS_SUCCESS = "1";
	public static String SYNSTATUS_FAILURE = "2";
	
	@Transient
	private String buyerCode;
	
	private String customerName;//-- customer_name  AS `招标人名称`,
	
	@Transient
	private String registeredCapital;
	
	@Transient
	private String legalPerson;
	
	//customer_main_business
	@Transient
	private String mainProducts;
	
	private String customerParentorganization;//`,--customer_parent_organization  AS `上级单位`,
	private String customerIndustry;//` ,-customer_industry-  AS `所属行业id`,
	private String customerMail;//,-- customer_mail AS `企业邮箱`,
	private String customerAddress;//,-- customer_address AS `企业地址`,
	private String customerPostno;//,--customer_post_no AS `邮编`
	private String customerFax;// -- CUSTOMER_FAX传真*/
	private String customerTelephone;//CUSTOMER_TELEPHONE 电话
	
	private String synStatus;//同步状态 

	@XmlElement (name="buyerId")
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	@Transient
	@XmlElement (name="buyerCode")
	public String getBuyerCode() {
		return "OA_"+customerId;
	}
	
	@XmlElement (name="buyerName")
	@Column(name="customer_name")
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	@Column(name="customer_parent_organization")
	public String getCustomerParentorganization() {
		return customerParentorganization;
	}
	public void setCustomerParentorganization(String customerParentorganization) {
		this.customerParentorganization = customerParentorganization;
	}
	@XmlElement (name="belongIndustry")
	@Column(name="customer_industry")
	public String getCustomerIndustry() {
		return customerIndustry;
	}
	public void setCustomerIndustry(String customerIndustry) {
		this.customerIndustry = customerIndustry;
	}
	
	@XmlElement (name="email")
	@Column(name="customer_mail")
	public String getCustomerMail() {
		return customerMail;
	}
	public void setCustomerMail(String customerMail) {
		this.customerMail = customerMail;
	}
	@XmlElement (name="address")
	@Column(name="customer_address")
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	@XmlElement (name="postCode")
	@Column(name="customer_post_no")
	public String getCustomerPostno() {
		return customerPostno;
	}
	public void setCustomerPostno(String customerPostno) {
		this.customerPostno = customerPostno;
	}
	@XmlElement (name="fax")
	@Column(name="CUSTOMER_FAX")
	public String getCustomerFax() {
		return customerFax;
	}
	public void setCustomerFax(String customerFax) {
		this.customerFax = customerFax;
	}
	@XmlElement (name="telephone")
	@Column(name="CUSTOMER_TELEPHONE")
	public String getCustomerTelephone() {
		return customerTelephone;
	}
	public void setCustomerTelephone(String customerTelephone) {
		this.customerTelephone = customerTelephone;
	}
	@Column(name="SYN_STATUS")
	public String getSynStatus() {
		return synStatus;
	}
	public void setSynStatus(String synStatus) {
		this.synStatus = synStatus;
	}
}
